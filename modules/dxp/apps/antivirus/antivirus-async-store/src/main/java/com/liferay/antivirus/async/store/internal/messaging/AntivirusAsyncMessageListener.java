/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.antivirus.async.store.internal.messaging;

import com.liferay.antivirus.async.store.configuration.AntivirusAsyncConfiguration;
import com.liferay.antivirus.async.store.constants.AntivirusAsyncConstants;
import com.liferay.antivirus.async.store.constants.AntivirusAsyncDestinationNames;
import com.liferay.antivirus.async.store.internal.event.AntivirusAsyncEventListenerManager;
import com.liferay.antivirus.async.store.retry.AntivirusAsyncRetryScheduler;
import com.liferay.antivirus.async.store.util.AntivirusAsyncUtil;
import com.liferay.document.library.kernel.antivirus.AntivirusScanner;
import com.liferay.document.library.kernel.antivirus.AntivirusScannerException;
import com.liferay.document.library.kernel.antivirus.AntivirusVirusFoundException;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageRunnable;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portlet.documentlibrary.store.StoreFactory;

import java.io.InputStream;

import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.antivirus.async.store.configuration.AntivirusAsyncConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	property = "destination.name=" + AntivirusAsyncDestinationNames.ANTIVIRUS,
	service = MessageListener.class
)
public class AntivirusAsyncMessageListener implements MessageListener {

	@Override
	public void receive(Message message) {
		try {
			_receive(message);
		}
		catch (Exception exception) {
			_antivirusAsyncEventListenerManager.onProcessingError(
				message, exception);
		}
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		AntivirusAsyncConfiguration antivirusAsyncConfiguration =
			ConfigurableUtil.createConfigurable(
				AntivirusAsyncConfiguration.class, properties);

		DestinationConfiguration destinationConfiguration =
			DestinationConfiguration.createSerialDestinationConfiguration(
				AntivirusAsyncDestinationNames.ANTIVIRUS);

		int maximumQueueSize = antivirusAsyncConfiguration.maximumQueueSize();

		if (maximumQueueSize == 0) {
			maximumQueueSize = Integer.MAX_VALUE;
		}

		destinationConfiguration.setMaximumQueueSize(maximumQueueSize);

		destinationConfiguration.setRejectedExecutionHandler(
			(runnable, threadPoolExecutor) -> {
				MessageRunnable messageRunnable = (MessageRunnable)runnable;

				Message message = messageRunnable.getMessage();

				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Schedule ",
							AntivirusAsyncUtil.getFileIdentifier(message),
							" into persistent storage because the async ",
							"antivirus queue is overflowing: ",
							message.getValues()));
				}

				_antivirusAsyncRetryScheduler.schedule(message);
			});

		Destination destination = _destinationFactory.createDestination(
			destinationConfiguration);

		_destinationServiceRegistration = bundleContext.registerService(
			Destination.class, destination,
			MapUtil.singletonDictionary(
				"destination.name", destination.getName()));
	}

	@Deactivate
	protected void deactivate() {
		if (_destinationServiceRegistration != null) {
			_destinationServiceRegistration.unregister();
		}
	}

	private void _receive(Message message) throws Exception {
		Store store = StoreFactory.getStore();

		long companyId = message.getLong("companyId");
		long repositoryId = message.getLong("repositoryId");
		String fileName = message.getString("fileName");
		String versionLabel = message.getString("versionLabel");

		boolean fileExists = store.hasFile(
			companyId, repositoryId, fileName, versionLabel);

		if (!fileExists) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						AntivirusAsyncUtil.getFileIdentifier(message),
						" is no longer present: ", message.getValues()));
			}

			_antivirusAsyncEventListenerManager.onMissing(message);

			return;
		}

		try {
			InputStream inputStream = store.getFileAsStream(
				companyId, repositoryId, fileName, versionLabel);

			_antivirusScanner.scan(inputStream);

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						AntivirusAsyncUtil.getFileIdentifier(message),
						" was scanned successfully: ", message.getValues()));
			}

			_antivirusAsyncEventListenerManager.onSuccess(message);
		}
		catch (AntivirusScannerException antivirusScannerException) {
			int type = antivirusScannerException.getType();

			if (antivirusScannerException instanceof
					AntivirusVirusFoundException) {

				AntivirusVirusFoundException antivirusVirusFoundException =
					(AntivirusVirusFoundException)antivirusScannerException;

				// Quarantine original file

				store.addFile(
					companyId, AntivirusAsyncConstants.REPOSITORY_ID_QUARANTINE,
					fileName, versionLabel,
					store.getFileAsStream(
						companyId, repositoryId, fileName, versionLabel));

				// Delete original file

				store.deleteFile(
					companyId, repositoryId, fileName, versionLabel);

				_antivirusAsyncEventListenerManager.onVirusFound(
					message, antivirusVirusFoundException,
					antivirusVirusFoundException.getVirusName());
			}
			else if (type == AntivirusScannerException.SIZE_LIMIT_EXCEEDED) {
				_antivirusAsyncEventListenerManager.onSizeExceeded(
					message, antivirusScannerException);
			}
			else {
				throw antivirusScannerException;
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AntivirusAsyncMessageListener.class);

	@Reference
	private AntivirusAsyncEventListenerManager
		_antivirusAsyncEventListenerManager;

	@Reference
	private AntivirusAsyncRetryScheduler _antivirusAsyncRetryScheduler;

	@Reference
	private AntivirusScanner _antivirusScanner;

	@Reference
	private DestinationFactory _destinationFactory;

	private ServiceRegistration<Destination> _destinationServiceRegistration;

}
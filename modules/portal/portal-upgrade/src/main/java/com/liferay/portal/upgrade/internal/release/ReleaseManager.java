/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.upgrade.internal.release;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapListener;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBContext;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.db.DBProcessContext;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.RunnableUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Release;
import com.liferay.portal.output.stream.container.OutputStreamContainer;
import com.liferay.portal.output.stream.container.OutputStreamContainerFactory;
import com.liferay.portal.output.stream.container.OutputStreamContainerFactoryTracker;
import com.liferay.portal.service.ReleaseLocalService;
import com.liferay.portal.upgrade.internal.UpgradeInfo;
import com.liferay.portal.upgrade.internal.configuration.ReleaseManagerConfiguration;
import com.liferay.portal.upgrade.internal.graph.ReleaseGraphManager;

import java.io.IOException;
import java.io.OutputStream;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.felix.utils.log.Logger;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Miguel Pastor
 * @author Carlos Sierra Andrés
 */
@Component(
	configurationPid = "com.liferay.portal.upgrade.internal.configuration.ReleaseManagerConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = {
		"osgi.command.function=execute", "osgi.command.function=list",
		"osgi.command.scope=upgrade"
	},
	service = Object.class
)
public class ReleaseManager {

	public void execute(String bundleSymbolicName) {
		doExecute(bundleSymbolicName, _serviceTrackerMap);
	}

	public void execute(String bundleSymbolicName, String toVersionString) {
		String schemaVersionString = getSchemaVersionString(bundleSymbolicName);

		ReleaseGraphManager releaseGraphManager = new ReleaseGraphManager(
			_serviceTrackerMap.getService(bundleSymbolicName));

		executeUpgradeInfos(
			bundleSymbolicName,
			releaseGraphManager.getUpgradeInfos(
				schemaVersionString, toVersionString));
	}

	public void list() {
		for (String bundleSymbolicName : _serviceTrackerMap.keySet()) {
			list(bundleSymbolicName);
		}
	}

	public void list(String bundleSymbolicName) {
		List<UpgradeInfo> upgradeProcesses = _serviceTrackerMap.getService(
			bundleSymbolicName);

		System.out.println(
			"Registered upgrade processes for " + bundleSymbolicName + " " +
				getSchemaVersionString(bundleSymbolicName));

		for (UpgradeInfo upgradeProcess : upgradeProcesses) {
			System.out.println("\t" + upgradeProcess);
		}
	}

	@Reference(unbind = "-")
	public void setOutputStreamTracker(
		OutputStreamContainerFactoryTracker
			outputStreamContainerFactoryTracker) {

		_outputStreamContainerFactoryTracker =
			outputStreamContainerFactoryTracker;
	}

	@Activate
	protected void activate(
			final BundleContext bundleContext, Map<String, Object> properties)
		throws InvalidSyntaxException {

		_logger = new Logger(bundleContext);

		DB db = DBFactoryUtil.getDB();

		ServiceTrackerMapListener<String, UpgradeInfo, List<UpgradeInfo>>
			serviceTrackerMapListener = null;

		_releaseManagerConfiguration = Configurable.createConfigurable(
			ReleaseManagerConfiguration.class, properties);

		if (_releaseManagerConfiguration.autoUpgrade()) {
			serviceTrackerMapListener =
				new UpgradeInfoServiceTrackerMapListener();
		}

		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, UpgradeStep.class,
			"(&(upgrade.bundle.symbolic.name=*)(|(upgrade.db.type=any)" +
				"(upgrade.db.type=" + db.getType() + ")))",
			new PropertyServiceReferenceMapper<String, UpgradeStep>(
				"upgrade.bundle.symbolic.name"),
			new UpgradeServiceTrackerCustomizer(bundleContext),
			Collections.reverseOrder(
				new PropertyServiceReferenceComparator<UpgradeStep>(
					"upgrade.from.schema.version")),
			serviceTrackerMapListener);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	protected void doExecute(
		String bundleSymbolicName,
		ServiceTrackerMap<String, List<UpgradeInfo>> serviceTrackerMap) {

		ReleaseGraphManager releaseGraphManager = new ReleaseGraphManager(
			serviceTrackerMap.getService(bundleSymbolicName));

		String schemaVersionString = getSchemaVersionString(bundleSymbolicName);

		List<List<UpgradeInfo>> upgradeInfosList =
			releaseGraphManager.getUpgradeInfosList(schemaVersionString);

		int size = upgradeInfosList.size();

		if (size > 1) {
			throw new IllegalStateException(
				"There are " + size + " possible end nodes for " +
					schemaVersionString);
		}

		if (size == 0) {
			return;
		}

		executeUpgradeInfos(bundleSymbolicName, upgradeInfosList.get(0));
	}

	protected void executeUpgradeInfos(
		final String bundleSymbolicName, final List<UpgradeInfo> upgradeInfos) {

		OutputStreamContainerFactory outputStreamContainerFactory =
			_outputStreamContainerFactoryTracker.
				getOutputStreamContainerFactory();

		OutputStreamContainer outputStreamContainer =
			outputStreamContainerFactory.create(
				"upgrade-" + bundleSymbolicName);

		OutputStream outputStream = outputStreamContainer.getOutputStream();

		RunnableUtil.runWithSwappedSystemOut(
			new UpgradeInfosRunnable(
				bundleSymbolicName, upgradeInfos, outputStream),
			outputStream);

		try {
			outputStream.close();
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		Release release = _releaseLocalService.fetchRelease(bundleSymbolicName);

		if (release != null) {
			_releasePublisher.publish(release);
		}
	}

	protected String getSchemaVersionString(String bundleSymbolicName) {
		Release release = _releaseLocalService.fetchRelease(bundleSymbolicName);

		if ((release == null) || Validator.isNull(release.getSchemaVersion())) {
			return "0.0.0";
		}

		return release.getSchemaVersion();
	}

	@Reference(unbind = "-")
	protected void setReleaseLocalService(
		ReleaseLocalService releaseLocalService) {

		_releaseLocalService = releaseLocalService;
	}

	@Reference(unbind = "-")
	protected void setReleasePublisher(ReleasePublisher releasePublisher) {
		_releasePublisher = releasePublisher;
	}

	private static Logger _logger;

	private volatile OutputStreamContainerFactoryTracker
		_outputStreamContainerFactoryTracker;
	private volatile ReleaseLocalService _releaseLocalService;
	private ReleaseManagerConfiguration _releaseManagerConfiguration;
	private volatile ReleasePublisher _releasePublisher;
	private ServiceTrackerMap<String, List<UpgradeInfo>> _serviceTrackerMap;

	private class UpgradeInfoServiceTrackerMapListener
		implements ServiceTrackerMapListener
			<String, UpgradeInfo, List<UpgradeInfo>> {

		@Override
		public void keyEmitted(
			ServiceTrackerMap<String, List<UpgradeInfo>> serviceTrackerMap,
			final String key, UpgradeInfo upgradeInfo,
			List<UpgradeInfo> upgradeInfos) {

			doExecute(key, serviceTrackerMap);
		}

	}

	private class UpgradeInfosRunnable implements Runnable {

		public UpgradeInfosRunnable(
			String bundleSymbolicName, List<UpgradeInfo> upgradeInfos,
			OutputStream outputStream) {

			_bundleSymbolicName = bundleSymbolicName;
			_upgradeInfos = upgradeInfos;
			_outputStream = outputStream;
		}

		@Override
		public void run() {
			for (UpgradeInfo upgradeInfo : _upgradeInfos) {
				UpgradeStep upgradeStep = upgradeInfo.getUpgradeStep();

				try {
					upgradeStep.upgrade(
						new DBProcessContext() {

							@Override
							public DBContext getDBContext() {
								return new DBContext();
							}

							@Override
							public OutputStream getOutputStream() {
								return _outputStream;
							}

						});

					_releaseLocalService.updateRelease(
						_bundleSymbolicName,
						upgradeInfo.getToSchemaVersionString(),
						upgradeInfo.getFromSchemaVersionString());
				}
				catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}

		private final String _bundleSymbolicName;
		private final OutputStream _outputStream;
		private final List<UpgradeInfo> _upgradeInfos;

	};

	private class UpgradeServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<UpgradeStep, UpgradeInfo> {

		public UpgradeServiceTrackerCustomizer(BundleContext bundleContext) {
			_bundleContext = bundleContext;
		}

		@Override
		public UpgradeInfo addingService(
			ServiceReference<UpgradeStep> serviceReference) {

			String fromSchemaVersionString =
				(String)serviceReference.getProperty(
					"upgrade.from.schema.version");
			String toSchemaVersionString = (String)serviceReference.getProperty(
				"upgrade.to.schema.version");

			UpgradeStep upgradeStep = _bundleContext.getService(
				serviceReference);

			if (upgradeStep == null) {
				_logger.log(
					Logger.LOG_WARNING,
					"Skipping service " + serviceReference +
						" because it does not implement UpgradeStep");

				return null;
			}

			return new UpgradeInfo(
				fromSchemaVersionString, toSchemaVersionString, upgradeStep);
		}

		@Override
		public void modifiedService(
			ServiceReference<UpgradeStep> serviceReference,
			UpgradeInfo upgradeInfo) {
		}

		@Override
		public void removedService(
			ServiceReference<UpgradeStep> serviceReference,
			UpgradeInfo upgradeInfo) {

			_bundleContext.ungetService(serviceReference);
		}

		private final BundleContext _bundleContext;

	}

}
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

package com.liferay.multi.factor.authentication.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.multi.factor.authentication.email.otp.configuration.MFAEmailOTPConfiguration;
import com.liferay.multi.factor.authentication.spi.checker.setup.SetupMFAChecker;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.Dictionary;
import java.util.Map;

import javax.servlet.ServletContext;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Tomas Polesovsky
 */
@Component(
	configurationPid = "com.liferay.multi.factor.authentication.email.otp.configuration.MFAEmailOTPConfiguration.scoped",
	configurationPolicy = ConfigurationPolicy.REQUIRE, service = {}
)
public class MFASetupUserAccountCheckerTracker {

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_bundleContext = bundleContext;

		MFAEmailOTPConfiguration mfaEmailOTPConfiguration =
			ConfigurableUtil.createConfigurable(
				MFAEmailOTPConfiguration.class, properties);

		if (mfaEmailOTPConfiguration.enabled()) {
			long companyId = GetterUtil.getLong(properties.get("companyId"));

			String filterString = StringBundler.concat(
				"(&(companyId=", companyId, ")(objectClass=",
				SetupMFAChecker.class.getName(), "))");

			_serviceTracker = ServiceTrackerFactory.open(
				bundleContext, filterString,
				new MFACheckerSetupServiceTrackerCustomizer());
		}
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceTracker != null) {
			_serviceTracker.close();
		}
	}

	private BundleContext _bundleContext;
	private ServiceTracker
		<Object, ServiceRegistration<ScreenNavigationEntry<User>>>
			_serviceTracker;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.multi.factor.authentication.web)"
	)
	private ServletContext _servletContext;

	private class MFACheckerSetupServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<Object, ServiceRegistration<ScreenNavigationEntry<User>>> {

		@Override
		public ServiceRegistration<ScreenNavigationEntry<User>> addingService(
			ServiceReference<Object> serviceReference) {

			Object setupMFAChecker = _bundleContext.getService(
				serviceReference);

			if (setupMFAChecker == null) {
				return null;
			}

			return (ServiceRegistration)_bundleContext.registerService(
				ScreenNavigationEntry.class,
				new MFASetupUserAccountScreenNavigationEntry(
					serviceReference, _servletContext,
					(SetupMFAChecker)setupMFAChecker),
				_buildProperties(serviceReference));
		}

		@Override
		public void modifiedService(
			ServiceReference<Object> serviceReference,
			ServiceRegistration<ScreenNavigationEntry<User>>
				serviceRegistration) {

			serviceRegistration.setProperties(
				_buildProperties(serviceReference));
		}

		@Override
		public void removedService(
			ServiceReference<Object> serviceReference,
			ServiceRegistration<ScreenNavigationEntry<User>>
				serviceRegistration) {

			serviceRegistration.unregister();

			_bundleContext.ungetService(serviceReference);
		}

		private Dictionary<String, Object> _buildProperties(
			ServiceReference<Object> serviceReference) {

			return HashMapDictionaryBuilder.<String, Object>put(
				"screen.navigation.entry.order",
				GetterUtil.getInteger(
					serviceReference.getProperty(
						"user.account.screen.navigation.entry.order"),
					Integer.MAX_VALUE)
			).build();
		}

	}

}
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

package com.liferay.multi.factor.authentication.ip.address.internal.checker;

import com.liferay.multi.factor.authentication.ip.address.internal.audit.MFAIPAddressAuditMessageBuilder;
import com.liferay.multi.factor.authentication.ip.address.internal.configuration.MFAIPAddressConfiguration;
import com.liferay.multi.factor.authentication.spi.checker.headless.HeadlessMFAChecker;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.access.control.AccessControlUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

/**
 * @author Marta Medio
 */
@Component(
	configurationPid = "com.liferay.multi.factor.authentication.ip.address.internal.configuration.MFAIPAddressConfiguration.scoped",
	service = {}
)
public class IPAddressHeadlessMFAChecker implements HeadlessMFAChecker {

	@Override
	public boolean verifyHeadlessRequest(
		HttpServletRequest httpServletRequest, long userId) {

		User user = _userLocalService.fetchUser(userId);

		if (user == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Requested IP address verification for nonexistent user " +
						userId);
			}

			MFAIPAddressAuditMessageBuilder mfaIPAddressAuditMessageBuilder =
				_mfaIPAddressAuditMessageBuilder;

			if (mfaIPAddressAuditMessageBuilder != null) {
				mfaIPAddressAuditMessageBuilder.routeAuditMessage(
					mfaIPAddressAuditMessageBuilder.
						buildNonexistentUserVerificationFailureAuditMessage(
							CompanyThreadLocal.getCompanyId(), userId,
							_getClassName()));
			}

			return false;
		}

		if (AccessControlUtil.isAccessAllowed(
				httpServletRequest, _allowedIpAddressesAndNetmasks)) {

			MFAIPAddressAuditMessageBuilder mfaIPAddressAuditMessageBuilder =
				_mfaIPAddressAuditMessageBuilder;

			if (mfaIPAddressAuditMessageBuilder != null) {
				mfaIPAddressAuditMessageBuilder.routeAuditMessage(
					mfaIPAddressAuditMessageBuilder.
						buildVerificationSuccessAuditMessage(
							user, _getClassName()));
			}

			return true;
		}

		MFAIPAddressAuditMessageBuilder mfaIPAddressAuditMessageBuilder =
			_mfaIPAddressAuditMessageBuilder;

		if (mfaIPAddressAuditMessageBuilder != null) {
			mfaIPAddressAuditMessageBuilder.routeAuditMessage(
				mfaIPAddressAuditMessageBuilder.
					buildVerificationFailureAuditMessage(
						user, _getClassName(), "IP is not allowed"));
		}

		return false;
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		MFAIPAddressConfiguration mfaIPAddressConfiguration =
			ConfigurableUtil.createConfigurable(
				MFAIPAddressConfiguration.class, properties);

		if (!mfaIPAddressConfiguration.enabled()) {
			return;
		}

		_allowedIpAddressesAndNetmasks = new HashSet<>(
			Arrays.asList(
				mfaIPAddressConfiguration.allowedIPAddressAndNetMask()));

		_serviceRegistration = bundleContext.registerService(
			HeadlessMFAChecker.class, this,
			new HashMapDictionary<>(properties));
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration == null) {
			return;
		}

		_serviceRegistration.unregister();
	}

	private String _getClassName() {
		Class<?> clazz = getClass();

		return clazz.getName();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IPAddressHeadlessMFAChecker.class);

	private Set<String> _allowedIpAddressesAndNetmasks;

	@Reference(cardinality = ReferenceCardinality.OPTIONAL)
	private MFAIPAddressAuditMessageBuilder _mfaIPAddressAuditMessageBuilder;

	private ServiceRegistration<HeadlessMFAChecker> _serviceRegistration;

	@Reference
	private UserLocalService _userLocalService;

}
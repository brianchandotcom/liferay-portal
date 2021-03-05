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

package com.liferay.multi.factor.authentication.web.internal.configuration.persistence.listener;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.multi.factor.authentication.email.otp.configuration.MFAEmailOTPConfiguration;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Dictionary;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marta Medio
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.multi.factor.authentication.web.internal.system.configuration.MFASystemConfiguration",
	service = ConfigurationModelListener.class
)
public class MFASystemConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onAfterSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		boolean mfaDisableGlobally = GetterUtil.getBoolean(
			properties.get("disableGlobally"));

		if (mfaDisableGlobally == _mfaDisableGlobally) {
			return;
		}

		_companyLocalService.forEachCompanyId(
			companyId -> {
				try {
					MFAEmailOTPConfiguration mfaEmailOTPConfiguration =
						_configurationProvider.getCompanyConfiguration(
							MFAEmailOTPConfiguration.class, companyId);

					if (mfaEmailOTPConfiguration.enabled()) {
						_sendNotificationToInstanceAdministrators(
							companyId, mfaDisableGlobally);
					}
				}
				catch (ConfigurationException configurationException) {
					_log.error(
						"Unable to get multi-factor authentication " +
							"configuration for company " + companyId,
						configurationException);
				}
				catch (PortalException portalException) {
					_log.error(
						"Failed to send notifications to administrators of " +
							"company " + companyId,
						portalException);
				}
			});

		_mfaDisableGlobally = mfaDisableGlobally;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_mfaDisableGlobally = GetterUtil.getBoolean(
			properties.get("disableGlobally"));
	}

	private void _sendNotificationToInstanceAdministrators(
			long companyId, boolean mfaDisableGlobally)
		throws PortalException {

		Role role = _roleLocalService.getRole(
			companyId, RoleConstants.ADMINISTRATOR);

		for (User user : _userLocalService.getRoleUsers(role.getRoleId())) {
			long userId = user.getUserId();

			_userNotificationEventLocalService.sendUserNotificationEvents(
				userId, ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
				UserNotificationDeliveryConstants.TYPE_WEBSITE, false,
				JSONUtil.put(
					"classPK", ConfigurationAdminPortletKeys.INSTANCE_SETTINGS
				).put(
					"mfaDisableGlobally", mfaDisableGlobally
				).put(
					"userId", userId
				));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MFASystemConfigurationModelListener.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	private boolean _mfaDisableGlobally;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}
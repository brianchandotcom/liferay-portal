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

package com.liferay.analytics.connector.web.internal.configuration.persistence.listener;

import com.liferay.analytics.connector.web.internal.configuration.AnalyticsConnectorConfiguration;
import com.liferay.analytics.connector.web.internal.constants.AnalyticsConnectorConstants;
import com.liferay.analytics.connector.web.internal.security.auth.verifier.AnalyticsConnectorAuthVerifier;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.service.ContactService;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.PortalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;

import java.util.Collections;
import java.util.Dictionary;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shinn Lok
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.analytics.connector.web.internal.configuration.AnalyticsConnectorConfiguration.scoped",
	service = ConfigurationModelListener.class
)
public class AnalyticsConnectorConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onAfterSave(String pid, Dictionary<String, Object> properties) {
		if (Validator.isNull((String)properties.get("code"))) {
			_disable((long)properties.get("companyId"));
		}
		else {
			_enable((long)properties.get("companyId"));
		}
	}

	@Activate
	protected void activate(ComponentContext componentContext)
		throws Exception {

		_componentContext = componentContext;

		if (_hasConfiguration()) {
			_enableAuthVerifier();
		}
	}

	private void _addAnalyticsAdmin(long companyId) throws Exception {
		User user = _userLocalService.fetchUserByScreenName(
			companyId, AnalyticsConnectorConstants.SCREEN_NAME_ANALYTICS_ADMIN);

		if (user != null) {
			return;
		}

		Company company = _companyLocalService.getCompany(companyId);

		Role role = _roleLocalService.getRole(
			companyId, "Analytics Administrator");

		user = _userLocalService.addUser(
			0, companyId, true, null, null, false,
			AnalyticsConnectorConstants.SCREEN_NAME_ANALYTICS_ADMIN,
			"analytics.administrator@" + company.getMx(), 0, "",
			LocaleUtil.getDefault(), "Analytics", "", "Administrator", 0, 0,
			true, 0, 1, 1970, "", null, null, new long[] {role.getRoleId()},
			null, false, new ServiceContext());

		_userLocalService.updateUser(user);
	}

	private void _addSAPEntry(long companyId) throws Exception {
		String sapEntryName = _SAP_ENTRY_OBJECT[0];

		SAPEntry sapEntry = _sapEntryLocalService.fetchSAPEntry(
			companyId, sapEntryName);

		if (sapEntry != null) {
			return;
		}

		_sapEntryLocalService.addSAPEntry(
			_userLocalService.getDefaultUserId(companyId), _SAP_ENTRY_OBJECT[1],
			false, true, sapEntryName,
			Collections.singletonMap(LocaleUtil.getDefault(), sapEntryName),
			new ServiceContext());
	}

	private void _deleteAnalyticsAdmin(long companyId) throws Exception {
		User user = _userLocalService.fetchUserByScreenName(
			companyId, AnalyticsConnectorConstants.SCREEN_NAME_ANALYTICS_ADMIN);

		if (user != null) {
			_userLocalService.deleteUser(user);
		}
	}

	private void _deleteSAPEntry(long companyId) throws Exception {
		SAPEntry sapEntry = _sapEntryLocalService.fetchSAPEntry(
			companyId, AnalyticsConnectorConstants.SERVICE_ACCESS_POLICY_NAME);

		if (sapEntry != null) {
			_sapEntryLocalService.deleteSAPEntry(sapEntry);
		}
	}

	private void _disable(long companyId) {
		try {
			_deleteAnalyticsAdmin(companyId);
			_deleteSAPEntry(companyId);
			_disableAuthVerifier();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private void _disableAuthVerifier() throws Exception {
		if (!_hasConfiguration() && _authVerifierEnabled) {
			_componentContext.disableComponent(
				AnalyticsConnectorAuthVerifier.class.getName());

			_authVerifierEnabled = false;
		}
	}

	private void _enable(long companyId) {
		try {
			_addAnalyticsAdmin(companyId);
			_addSAPEntry(companyId);
			_enableAuthVerifier();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private void _enableAuthVerifier() {
		if (!_authVerifierEnabled) {
			_componentContext.enableComponent(
				AnalyticsConnectorAuthVerifier.class.getName());

			_authVerifierEnabled = true;
		}
	}

	private boolean _hasConfiguration() throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(service.pid=" + AnalyticsConnectorConfiguration.class.getName() +
				"*)");

		for (Configuration configuration : configurations) {
			Dictionary<String, Object> properties =
				configuration.getProperties();

			if (Validator.isNotNull(properties.get("code"))) {
				return true;
			}
		}

		return false;
	}

	private static final String[] _SAP_ENTRY_OBJECT = {
		AnalyticsConnectorConstants.SERVICE_ACCESS_POLICY_NAME,
		StringBundler.concat(
			"com.liferay.portal.security.audit.storage.service.",
			"AuditEventService#getAuditEvents\n",
			ContactService.class.getName(), "#getContact\n",
			CompanyService.class.getName(), "#updatePreferences\n",
			GroupService.class.getName(), "#getGroup\n",
			GroupService.class.getName(), "#getGroups\n",
			GroupService.class.getName(), "#getGroupsCount\n",
			GroupService.class.getName(), "#getGtGroups\n",
			OrganizationService.class.getName(), "#fetchOrganization\n",
			OrganizationService.class.getName(), "#getGtOrganizations\n",
			OrganizationService.class.getName(), "#getOrganization\n",
			OrganizationService.class.getName(), "#getOrganizations\n",
			OrganizationService.class.getName(), "#getOrganizationsCount\n",
			OrganizationService.class.getName(), "#getUserOrganizations\n",
			PortalService.class.getName(), "#getBuildNumber\n",
			UserService.class.getName(), "#getCompanyUsers\n",
			UserService.class.getName(), "#getCompanyUsersCount\n",
			UserService.class.getName(), "#getCurrentUser\n",
			UserService.class.getName(), "#getGtCompanyUsers\n",
			UserService.class.getName(), "#getGtOrganizationUsers\n",
			UserService.class.getName(), "#getGtUserGroupUsers\n",
			UserService.class.getName(), "#getOrganizationUsers\n",
			UserService.class.getName(), "#getOrganizationUsersCount\n",
			UserService.class.getName(),
			"#getOrganizationsAndUserGroupsUsersCount\n",
			UserService.class.getName(), "#getUserById\n",
			UserService.class.getName(), "#getUserGroupUsers\n",
			UserGroupService.class.getName(), "#fetchUserGroup\n",
			UserGroupService.class.getName(), "#getGtUserGroups\n",
			UserGroupService.class.getName(), "#getUserGroup\n",
			UserGroupService.class.getName(), "#getUserGroups\n",
			UserGroupService.class.getName(), "#getUserGroupsCount\n",
			UserGroupService.class.getName(), "#getUserUserGroups")
	};

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsConnectorConfigurationModelListener.class);

	private boolean _authVerifierEnabled;

	@Reference
	private CompanyLocalService _companyLocalService;

	private ComponentContext _componentContext;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private SAPEntryLocalService _sapEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
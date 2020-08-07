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

package com.liferay.analytics.settings.internal.configuration;

import com.liferay.analytics.message.sender.constants.AnalyticsMessagesDestinationNames;
import com.liferay.analytics.message.sender.constants.AnalyticsMessagesProcessorCommand;
import com.liferay.analytics.message.sender.model.listener.EntityModelListener;
import com.liferay.analytics.message.storage.service.AnalyticsMessageLocalService;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.configuration.AnalyticsConfigurationTracker;
import com.liferay.analytics.settings.internal.model.AnalyticsUserImpl;
import com.liferay.analytics.settings.internal.security.auth.verifier.AnalyticsSecurityAuthVerifier;
import com.liferay.analytics.settings.internal.util.EntityModelListenerTracker;
import com.liferay.analytics.settings.security.constants.AnalyticsSecurityConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(
	configurationPid = "com.liferay.analytics.settings.configuration.AnalyticsConfiguration",
	immediate = true,
	property = Constants.SERVICE_PID + "=com.liferay.analytics.settings.configuration.AnalyticsConfiguration.scoped",
	service = {AnalyticsConfigurationTracker.class, ManagedServiceFactory.class}
)
public class AnalyticsConfigurationTrackerImpl
	implements AnalyticsConfigurationTracker, ManagedServiceFactory {

	@Override
	public void deleted(String pid) {
		long companyId = getCompanyId(pid);

		_unmapPid(pid);

		_disable(companyId);
	}

	@Override
	public AnalyticsConfiguration getAnalyticsConfiguration(long companyId) {
		return _analyticsConfigurations.getOrDefault(
			companyId, _systemAnalyticsConfiguration);
	}

	@Override
	public AnalyticsConfiguration getAnalyticsConfiguration(String pid) {
		Long companyId = _pidCompanyIdMapping.get(pid);

		if (companyId == null) {
			return _systemAnalyticsConfiguration;
		}

		return getAnalyticsConfiguration(companyId);
	}

	@Override
	public Dictionary<String, Object> getAnalyticsConfigurationProperties(
		long companyId) {

		if (!isActive()) {
			return null;
		}

		Set<Map.Entry<String, Long>> entries = _pidCompanyIdMapping.entrySet();

		Stream<Map.Entry<String, Long>> stream = entries.stream();

		String pid = stream.filter(
			entry -> Objects.equals(entry.getValue(), companyId)
		).map(
			Map.Entry::getKey
		).findFirst(
		).orElse(
			null
		);

		if (pid == null) {
			return null;
		}

		try {
			Configuration configuration = _configurationAdmin.getConfiguration(
				pid, StringPool.QUESTION);

			return configuration.getProperties();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get configuration for company " + companyId,
					exception);
			}
		}

		return null;
	}

	@Override
	public Map<Long, AnalyticsConfiguration> getAnalyticsConfigurations() {
		return _analyticsConfigurations;
	}

	@Override
	public long getCompanyId(String pid) {
		return _pidCompanyIdMapping.getOrDefault(pid, CompanyConstants.SYSTEM);
	}

	@Override
	public String getName() {
		return "com.liferay.analytics.settings.configuration." +
			"AnalyticsConfiguration.scoped";
	}

	@Override
	public boolean isActive() {
		if (!_active && _hasConfiguration()) {
			_active = true;
		}
		else if (_active && !_hasConfiguration()) {
			_active = false;
		}

		return _active;
	}

	@Override
	public void updated(String pid, Dictionary<String, ?> dictionary) {
		_unmapPid(pid);

		long companyId = GetterUtil.getLong(
			dictionary.get("companyId"), CompanyConstants.SYSTEM);

		if (companyId != CompanyConstants.SYSTEM) {
			_pidCompanyIdMapping.put(pid, companyId);

			_analyticsConfigurations.put(
				companyId,
				ConfigurableUtil.createConfigurable(
					AnalyticsConfiguration.class, dictionary));
		}

		if (!_initializedCompanyIds.contains(companyId)) {
			_initializedCompanyIds.add(companyId);

			if (Validator.isNotNull(dictionary.get("previousToken"))) {
				return;
			}
		}

		if (Validator.isNull(dictionary.get("token"))) {
			if (Validator.isNotNull(dictionary.get("previousToken"))) {
				_disable((Long)dictionary.get("companyId"));
			}
		}
		else {
			if (Validator.isNull(dictionary.get("previousToken"))) {
				_enable((Long)dictionary.get("companyId"));
			}

			_sync(dictionary);
		}
	}

	@Activate
	@Modified
	protected void activate(
		ComponentContext componentContext, Map<String, Object> properties) {

		_componentContext = componentContext;

		_systemAnalyticsConfiguration = ConfigurableUtil.createConfigurable(
			AnalyticsConfiguration.class, properties);
	}

	private void _activate() {
		if (!_active) {
			_active = true;
		}

		if (!_authVerifierEnabled) {
			_componentContext.enableComponent(
				AnalyticsSecurityAuthVerifier.class.getName());

			_authVerifierEnabled = true;
		}
	}

	private void _addAnalyticsAdmin(long companyId) throws Exception {
		User user = _userLocalService.fetchUserByScreenName(
			companyId, AnalyticsSecurityConstants.SCREEN_NAME_ANALYTICS_ADMIN);

		if (user != null) {
			return;
		}

		Company company = _companyLocalService.getCompany(companyId);

		Role role = _roleLocalService.getRole(
			companyId, "Analytics Administrator");

		user = _userLocalService.addUser(
			0, companyId, true, null, null, false,
			AnalyticsSecurityConstants.SCREEN_NAME_ANALYTICS_ADMIN,
			"analytics.administrator@" + company.getMx(),
			LocaleUtil.getDefault(), "Analytics", "", "Administrator", 0, 0,
			true, 0, 1, 1970, "", null, null, new long[] {role.getRoleId()},
			null, false, new ServiceContext());

		_userLocalService.updateUser(user);
	}

	private void _addAnalyticsMessages(List<? extends BaseModel> baseModels) {
		if (baseModels.isEmpty()) {
			return;
		}

		Message message = new Message();

		message.put("command", AnalyticsMessagesProcessorCommand.ADD);

		BaseModel<?> baseModel = baseModels.get(0);

		message.put(
			"entityModelListener",
			_entityModelListenerTracker.getEntityModelListener(
				baseModel.getModelClassName()));

		message.setPayload(baseModels);

		if (_log.isInfoEnabled()) {
			_log.info("Queueing add analytics messages message");
		}

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				_messageBus.sendMessage(
					AnalyticsMessagesDestinationNames.
						ANALYTICS_MESSAGES_PROCESSOR,
					message);

				return null;
			});
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

	private void _addUsersAnalyticsMessages(List<User> users) {
		List<AnalyticsUserImpl> analyticsUsers = new ArrayList<>(users.size());

		List<Contact> contacts = new ArrayList<>(users.size());

		for (User user : users) {
			Map<String, long[]> memberships = new HashMap<>();

			for (EntityModelListener<?> entityModelListener :
					_entityModelListenerTracker.getEntityModelListeners()) {

				try {
					long[] membershipIds = entityModelListener.getMembershipIds(
						user);

					if (membershipIds.length == 0) {
						continue;
					}

					memberships.put(
						entityModelListener.getModelClassName(), membershipIds);
				}
				catch (Exception exception) {
					_log.error(exception, exception);
				}
			}

			analyticsUsers.add(new AnalyticsUserImpl(user, memberships));

			Contact contact = user.fetchContact();

			if (contact != null) {
				contacts.add(contact);
			}
		}

		_addAnalyticsMessages(analyticsUsers);

		_addAnalyticsMessages(contacts);
	}

	private void _deactivate() {
		if (_active && !_hasConfiguration()) {
			_active = false;
		}

		if (_authVerifierEnabled && !_hasConfiguration()) {
			_componentContext.disableComponent(
				AnalyticsSecurityAuthVerifier.class.getName());

			_authVerifierEnabled = false;
		}
	}

	private void _deleteAnalyticsAdmin(long companyId) throws Exception {
		User user = _userLocalService.fetchUserByScreenName(
			companyId, AnalyticsSecurityConstants.SCREEN_NAME_ANALYTICS_ADMIN);

		if (user != null) {
			_userLocalService.deleteUser(user);
		}
	}

	private void _deleteSAPEntry(long companyId) throws Exception {
		SAPEntry sapEntry = _sapEntryLocalService.fetchSAPEntry(
			companyId, AnalyticsSecurityConstants.SERVICE_ACCESS_POLICY_NAME);

		if (sapEntry != null) {
			_sapEntryLocalService.deleteSAPEntry(sapEntry);
		}
	}

	private void _disable(long companyId) {
		try {
			if (companyId != CompanyConstants.SYSTEM) {
				_analyticsMessageLocalService.deleteAnalyticsMessages(
					companyId);

				_deleteAnalyticsAdmin(companyId);
				_deleteSAPEntry(companyId);
			}

			_deactivate();
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}
	}

	private void _enable(long companyId) {
		try {
			_activate();
			_addAnalyticsAdmin(companyId);
			_addSAPEntry(companyId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}
	}

	private boolean _hasConfiguration() {
		Configuration[] configurations = null;

		try {
			configurations = _configurationAdmin.listConfigurations(
				"(service.pid=" + AnalyticsConfiguration.class.getName() +
					"*)");
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to list analytics configurations", exception);
			}
		}

		if (configurations == null) {
			return false;
		}

		for (Configuration configuration : configurations) {
			Dictionary<String, Object> properties =
				configuration.getProperties();

			if (Validator.isNotNull(properties.get("token"))) {
				return true;
			}
		}

		return false;
	}

	private void _sync(Dictionary<String, ?> dictionary) {
		if (Validator.isNotNull(dictionary.get("token")) &&
			Validator.isNull(dictionary.get("previousToken"))) {

			Collection<EntityModelListener<?>> entityModelListeners =
				_entityModelListenerTracker.getEntityModelListeners();

			for (EntityModelListener<?> entityModelListener :
					entityModelListeners) {

				try {
					entityModelListener.syncAll(
						(Long)dictionary.get("companyId"));
				}
				catch (Exception exception) {
					_log.error(exception, exception);
				}
			}
		}

		if (GetterUtil.getBoolean(dictionary.get("syncAllContacts"))) {
			if (!GetterUtil.getBoolean(
					dictionary.get("previousSyncAllContacts"))) {

				_syncContacts((Long)dictionary.get("companyId"));
			}
		}
		else {
			_syncOrganizationUsers(
				(String[])dictionary.get("syncedOrganizationIds"));
			_syncUserGroupUsers((String[])dictionary.get("syncedUserGroupIds"));
		}

		Message message = new Message();

		message.put("command", AnalyticsMessagesProcessorCommand.SEND);
		message.put("companyId", dictionary.get("companyId"));

		if (_log.isInfoEnabled()) {
			_log.info("Queueing send analytics messages message");
		}

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				_messageBus.sendMessage(
					AnalyticsMessagesDestinationNames.
						ANALYTICS_MESSAGES_PROCESSOR,
					message);

				return null;
			});
	}

	private void _syncContacts(long companyId) {
		int count = _userLocalService.getCompanyUsersCount(companyId);

		int pages = count / _DEFAULT_DELTA;

		for (int i = 0; i <= pages; i++) {
			int start = i * _DEFAULT_DELTA;

			int end = start + _DEFAULT_DELTA;

			if (end > count) {
				end = count;
			}

			List<User> users = _userLocalService.getCompanyUsers(
				companyId, start, end);

			_addUsersAnalyticsMessages(users);
		}
	}

	private void _syncOrganizationUsers(String[] organizationIds) {
		for (String organizationId : organizationIds) {
			int count = _userLocalService.getOrganizationUsersCount(
				GetterUtil.getLong(organizationId));

			int pages = count / _DEFAULT_DELTA;

			for (int i = 0; i <= pages; i++) {
				int start = i * _DEFAULT_DELTA;

				int end = start + _DEFAULT_DELTA;

				if (end > count) {
					end = count;
				}

				try {
					List<User> users = _userLocalService.getOrganizationUsers(
						GetterUtil.getLong(organizationId), start, end);

					_addUsersAnalyticsMessages(users);
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to get organization users for " +
								"organization " + organizationId,
							exception);
					}
				}
			}
		}
	}

	private void _syncUserGroupUsers(String[] userGroupIds) {
		for (String userGroupId : userGroupIds) {
			int count = _userLocalService.getUserGroupUsersCount(
				GetterUtil.getLong(userGroupId));

			int pages = count / _DEFAULT_DELTA;

			for (int i = 0; i <= pages; i++) {
				int start = i * _DEFAULT_DELTA;

				int end = start + _DEFAULT_DELTA;

				if (end > count) {
					end = count;
				}

				List<User> users = _userLocalService.getUserGroupUsers(
					GetterUtil.getLong(userGroupId), start, end);

				_addUsersAnalyticsMessages(users);
			}
		}
	}

	private void _unmapPid(String pid) {
		Long companyId = _pidCompanyIdMapping.remove(pid);

		if (companyId != null) {
			_analyticsConfigurations.remove(companyId);
		}
	}

	private static final int _DEFAULT_DELTA = 500;

	private static final String[] _SAP_ENTRY_OBJECT = {
		AnalyticsSecurityConstants.SERVICE_ACCESS_POLICY_NAME,
		StringBundler.concat(
			"com.liferay.segments.asah.rest.internal.resource.v1_0.",
			"ExperimentResourceImpl#deleteExperiment\n",
			"com.liferay.segments.asah.rest.internal.resource.v1_0.",
			"ExperimentRunResourceImpl#postExperimentRun\n",
			"com.liferay.segments.asah.rest.internal.resource.v1_0.",
			"StatusResourceImpl#postExperimentStatus")
	};

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsConfigurationTrackerImpl.class);

	private boolean _active;
	private final Map<Long, AnalyticsConfiguration> _analyticsConfigurations =
		new ConcurrentHashMap<>();

	@Reference
	private AnalyticsMessageLocalService _analyticsMessageLocalService;

	private boolean _authVerifierEnabled;

	@Reference
	private CompanyLocalService _companyLocalService;

	private ComponentContext _componentContext;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private EntityModelListenerTracker _entityModelListenerTracker;

	private final Set<Long> _initializedCompanyIds = new HashSet<>();

	@Reference
	private MessageBus _messageBus;

	private final Map<String, Long> _pidCompanyIdMapping =
		new ConcurrentHashMap<>();

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private SAPEntryLocalService _sapEntryLocalService;

	private volatile AnalyticsConfiguration _systemAnalyticsConfiguration;

	@Reference
	private UserLocalService _userLocalService;

}
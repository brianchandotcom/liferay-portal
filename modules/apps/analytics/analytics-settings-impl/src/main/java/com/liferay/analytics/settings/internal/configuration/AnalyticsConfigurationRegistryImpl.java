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

import com.liferay.analytics.batch.exportimport.AnalyticsDXPEntityBatchExporter;
import com.liferay.analytics.batch.exportimport.constants.AnalyticsDXPEntityBatchExporterConstants;
import com.liferay.analytics.message.sender.constants.AnalyticsMessagesDestinationNames;
import com.liferay.analytics.message.sender.constants.AnalyticsMessagesProcessorCommand;
import com.liferay.analytics.message.sender.model.AnalyticsMessage;
import com.liferay.analytics.message.sender.model.listener.EntityModelListener;
import com.liferay.analytics.message.storage.service.AnalyticsMessageLocalService;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.configuration.AnalyticsConfigurationRegistry;
import com.liferay.analytics.settings.internal.model.AnalyticsUserImpl;
import com.liferay.analytics.settings.internal.util.EntityModelListenerRegistry;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.analytics.settings.security.constants.AnalyticsSecurityConstants;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
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
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;

import java.nio.charset.Charset;

import java.util.ArrayList;
import java.util.Arrays;
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

import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(
	configurationPid = "com.liferay.analytics.settings.configuration.AnalyticsConfiguration",
	property = Constants.SERVICE_PID + "=com.liferay.analytics.settings.configuration.AnalyticsConfiguration.scoped",
	service = {
		AnalyticsConfigurationRegistry.class, ManagedServiceFactory.class
	}
)
public class AnalyticsConfigurationRegistryImpl
	implements AnalyticsConfigurationRegistry, ManagedServiceFactory {

	@Override
	public void deleted(String pid) {
		long companyId = getCompanyId(pid);

		_unmapPid(pid);

		long curCompanyId = CompanyThreadLocal.getCompanyId();

		CompanyThreadLocal.setCompanyId(companyId);

		try {
			_disable(companyId);
		}
		finally {
			CompanyThreadLocal.setCompanyId(curCompanyId);
		}
	}

	@Override
	public AnalyticsConfiguration getAnalyticsConfiguration(long companyId) {
		return _analyticsConfigurations.getOrDefault(
			companyId, _systemAnalyticsConfiguration);
	}

	@Override
	public AnalyticsConfiguration getAnalyticsConfiguration(String pid) {
		Long companyId = _companyIds.get(pid);

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

		for (Map.Entry<String, Long> entry : _companyIds.entrySet()) {
			if (Objects.equals(entry.getValue(), companyId)) {
				try {
					Configuration configuration =
						_configurationAdmin.getConfiguration(
							entry.getKey(), StringPool.QUESTION);

					return configuration.getProperties();
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to get configuration for company " +
								companyId,
							exception);
					}

					break;
				}
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
		return _companyIds.getOrDefault(pid, CompanyConstants.SYSTEM);
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

		long curCompanyId = CompanyThreadLocal.getCompanyId();

		long companyId = GetterUtil.getLong(
			dictionary.get("companyId"), CompanyConstants.SYSTEM);

		CompanyThreadLocal.setCompanyId(companyId);

		try {
			_updated(companyId, pid, dictionary);
		}
		finally {
			CompanyThreadLocal.setCompanyId(curCompanyId);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_systemAnalyticsConfiguration = ConfigurableUtil.createConfigurable(
			AnalyticsConfiguration.class, properties);
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
			true, 0, 1, 1970, "", UserConstants.TYPE_REGULAR, null, null,
			new long[] {role.getRoleId()}, null, false, new ServiceContext());

		_userLocalService.updateUser(user);
	}

	private void _addAnalyticsMessages(
		String action, List<? extends BaseModel> baseModels, long companyId) {

		if (baseModels.isEmpty()) {
			return;
		}

		Message message = new Message();

		message.put("action", action);
		message.put("command", AnalyticsMessagesProcessorCommand.ADD);

		BaseModel<?> baseModel = baseModels.get(0);

		message.put(
			"entityModelListener",
			_entityModelListenerRegistry.getEntityModelListener(
				baseModel.getModelClassName()));

		message.put("principalName", _getAnalyticsAdminUserId(companyId));

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
			_userLocalService.getGuestUserId(companyId), _SAP_ENTRY_OBJECT[1],
			false, true, sapEntryName,
			Collections.singletonMap(LocaleUtil.getDefault(), sapEntryName),
			new ServiceContext());
	}

	private void _addUsersAnalyticsMessages(long companyId, List<User> users) {
		List<AnalyticsUserImpl> analyticsUsers = new ArrayList<>(users.size());

		List<Contact> contacts = new ArrayList<>(users.size());

		for (User user : users) {
			Map<String, long[]> memberships = new HashMap<>();

			for (EntityModelListener<?> entityModelListener :
					_entityModelListenerRegistry.getEntityModelListeners()) {

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
					_log.error(exception);
				}
			}

			analyticsUsers.add(new AnalyticsUserImpl(user, memberships));

			Contact contact = user.fetchContact();

			if (contact != null) {
				contacts.add(contact);
			}
		}

		_addAnalyticsMessages("update", analyticsUsers, companyId);

		_addAnalyticsMessages("update", contacts, companyId);
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
				_analyticsDXPEntityBatchExporter.unscheduleExportTriggers(
					companyId,
					ArrayUtil.append(
						AnalyticsDXPEntityBatchExporterConstants.
							DISPATCH_TRIGGER_NAMES_DXP_ENTITIES,
						new String[] {
							AnalyticsDXPEntityBatchExporterConstants.
								DISPATCH_TRIGGER_NAME_ACCOUNT_ENTRY_DXP_ENTITIES,
							AnalyticsDXPEntityBatchExporterConstants.
								DISPATCH_TRIGGER_NAME_ORDER,
							AnalyticsDXPEntityBatchExporterConstants.
								DISPATCH_TRIGGER_NAME_PRODUCT,
							AnalyticsDXPEntityBatchExporterConstants.
								DISPATCH_TRIGGER_NAME_USER_DXP_ENTITIES
						}));

				_analyticsMessageLocalService.deleteAnalyticsMessages(
					companyId);

				_deleteAnalyticsAdmin(companyId);
				_deleteSAPEntry(companyId);
			}

			if (_active && !_hasConfiguration()) {
				_active = false;
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private void _enable(long companyId) {
		try {
			_active = true;

			_addAnalyticsAdmin(companyId);
			_addSAPEntry(companyId);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private void _firstSync(long companyId) {
		try {
			Set<String> dispatchTriggerNames = new HashSet<>();

			if (FeatureFlagManagerUtil.isEnabled("LRAC-10632")) {
				Collections.addAll(
					dispatchTriggerNames,
					AnalyticsDXPEntityBatchExporterConstants.
						DISPATCH_TRIGGER_NAMES_DXP_ENTITIES);

				if (_analyticsSettingsManager.syncedContactSettingsEnabled(
						companyId)) {

					dispatchTriggerNames.add(
						AnalyticsDXPEntityBatchExporterConstants.
							DISPATCH_TRIGGER_NAME_USER_DXP_ENTITIES);
				}
			}

			if (_analyticsSettingsManager.syncedAccountSettingsEnabled(
					companyId)) {

				dispatchTriggerNames.add(
					AnalyticsDXPEntityBatchExporterConstants.
						DISPATCH_TRIGGER_NAME_ACCOUNT_ENTRY_DXP_ENTITIES);
			}

			if (_analyticsSettingsManager.syncedCommerceSettingsEnabled(
					companyId)) {

				Collections.addAll(
					dispatchTriggerNames,
					AnalyticsDXPEntityBatchExporterConstants.
						DISPATCH_TRIGGER_NAME_ACCOUNT_ENTRY_DXP_ENTITIES,
					AnalyticsDXPEntityBatchExporterConstants.
						DISPATCH_TRIGGER_NAME_ORDER,
					AnalyticsDXPEntityBatchExporterConstants.
						DISPATCH_TRIGGER_NAME_PRODUCT);
			}

			_analyticsDXPEntityBatchExporter.scheduleExportTriggers(
				companyId, dispatchTriggerNames.toArray(new String[0]));

			_analyticsDXPEntityBatchExporter.export(
				companyId, dispatchTriggerNames.toArray(new String[0]));

			AnalyticsConfiguration analyticsConfiguration =
				getAnalyticsConfiguration(companyId);

			Collection<EntityModelListener<?>> entityModelListeners =
				_entityModelListenerRegistry.getEntityModelListeners();

			for (EntityModelListener<?> entityModelListener :
					entityModelListeners) {

				entityModelListener.syncAll(companyId);
			}

			_syncDefaultFields(
				companyId, analyticsConfiguration.syncedContactFieldNames(),
				analyticsConfiguration.syncedUserFieldNames());

			_syncUserCustomFields(
				companyId, analyticsConfiguration.syncedUserFieldNames());

			if (GetterUtil.getBoolean(
					analyticsConfiguration.syncAllContacts())) {

				_syncContacts(companyId);
			}
			else {
				_syncOrganizationUsers(
					companyId, analyticsConfiguration.syncedOrganizationIds());
				_syncUserGroupUsers(
					companyId, analyticsConfiguration.syncedUserGroupIds());
			}

			Message message = new Message();

			message.put("command", AnalyticsMessagesProcessorCommand.SEND);
			message.put("companyId", companyId);
			message.put("principalName", _getAnalyticsAdminUserId(companyId));

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

			_analyticsSettingsManager.updateCompanyConfiguration(
				companyId, Collections.singletonMap("firstSync", false));
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private long _getAnalyticsAdminUserId(long companyId) {
		User user = _userLocalService.fetchUserByScreenName(
			companyId, AnalyticsSecurityConstants.SCREEN_NAME_ANALYTICS_ADMIN);

		if (user == null) {
			return 0;
		}

		return user.getUserId();
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

	private void _sync(long companyId, Dictionary<String, ?> dictionary) {
		try {
			if (Validator.isNotNull(dictionary.get("token")) &&
				Validator.isNull(dictionary.get("previousToken"))) {

				Collection<EntityModelListener<?>> entityModelListeners =
					_entityModelListenerRegistry.getEntityModelListeners();

				for (EntityModelListener<?> entityModelListener :
						entityModelListeners) {

					entityModelListener.syncAll(companyId);
				}
			}

			if (FeatureFlagManagerUtil.isEnabled("LRAC-10632") ||
				!FeatureFlagManagerUtil.isEnabled("LRAC-10757")) {

				Set<String> refreshDispatchTriggerNames = new HashSet<>();
				Set<String> unscheduleDispatchTriggerNames = new HashSet<>();

				if (_analyticsSettingsManager.syncedAccountSettingsChanged(
						companyId)) {

					if (_analyticsSettingsManager.syncedAccountSettingsEnabled(
							companyId)) {

						refreshDispatchTriggerNames.add(
							AnalyticsDXPEntityBatchExporterConstants.
								DISPATCH_TRIGGER_NAME_ACCOUNT_ENTRY_DXP_ENTITIES);
					}
					else {
						unscheduleDispatchTriggerNames.add(
							AnalyticsDXPEntityBatchExporterConstants.
								DISPATCH_TRIGGER_NAME_ACCOUNT_ENTRY_DXP_ENTITIES);
					}
				}

				if (_analyticsSettingsManager.syncedAccountSettingsEnabled(
						companyId) &&
					_analyticsSettingsManager.syncedAccountFieldsChanged(
						companyId)) {

					refreshDispatchTriggerNames.add(
						AnalyticsDXPEntityBatchExporterConstants.
							DISPATCH_TRIGGER_NAME_ACCOUNT_ENTRY_DXP_ENTITIES);
				}

				if (_analyticsSettingsManager.syncedCommerceSettingsChanged(
						companyId)) {

					if (_analyticsSettingsManager.syncedCommerceSettingsEnabled(
							companyId)) {

						Collections.addAll(
							refreshDispatchTriggerNames,
							AnalyticsDXPEntityBatchExporterConstants.
								DISPATCH_TRIGGER_NAME_ORDER,
							AnalyticsDXPEntityBatchExporterConstants.
								DISPATCH_TRIGGER_NAME_PRODUCT);
					}
					else {
						Collections.addAll(
							unscheduleDispatchTriggerNames,
							AnalyticsDXPEntityBatchExporterConstants.
								DISPATCH_TRIGGER_NAME_ORDER,
							AnalyticsDXPEntityBatchExporterConstants.
								DISPATCH_TRIGGER_NAME_PRODUCT);
					}
				}

				if (_analyticsSettingsManager.syncedCommerceSettingsEnabled(
						companyId)) {

					if (_analyticsSettingsManager.syncedOrderFieldsChanged(
							companyId)) {

						refreshDispatchTriggerNames.add(
							AnalyticsDXPEntityBatchExporterConstants.
								DISPATCH_TRIGGER_NAME_ORDER);
					}

					if (_analyticsSettingsManager.syncedProductFieldsChanged(
							companyId)) {

						refreshDispatchTriggerNames.add(
							AnalyticsDXPEntityBatchExporterConstants.
								DISPATCH_TRIGGER_NAME_PRODUCT);
					}
				}

				if (FeatureFlagManagerUtil.isEnabled("LRAC-10632")) {
					if (_analyticsSettingsManager.syncedContactSettingsChanged(
							companyId)) {

						if (_analyticsSettingsManager.
								syncedContactSettingsEnabled(companyId)) {

							refreshDispatchTriggerNames.add(
								AnalyticsDXPEntityBatchExporterConstants.
									DISPATCH_TRIGGER_NAME_USER_DXP_ENTITIES);
						}
						else {
							unscheduleDispatchTriggerNames.add(
								AnalyticsDXPEntityBatchExporterConstants.
									DISPATCH_TRIGGER_NAME_USER_DXP_ENTITIES);
						}
					}

					if (_analyticsSettingsManager.syncedContactSettingsEnabled(
							companyId) &&
						_analyticsSettingsManager.syncedUserFieldsChanged(
							companyId)) {

						refreshDispatchTriggerNames.add(
							AnalyticsDXPEntityBatchExporterConstants.
								DISPATCH_TRIGGER_NAME_USER_DXP_ENTITIES);
					}
				}

				if (!refreshDispatchTriggerNames.isEmpty()) {
					_analyticsDXPEntityBatchExporter.refreshExportTriggers(
						companyId,
						refreshDispatchTriggerNames.toArray(new String[0]));

					if (FeatureFlagManagerUtil.isEnabled("LRAC-10632") &&
						FeatureFlagManagerUtil.isEnabled("LRAC-10757")) {

						_analyticsDXPEntityBatchExporter.export(
							companyId,
							new String[] {
								AnalyticsDXPEntityBatchExporterConstants.
									DISPATCH_TRIGGER_NAME_USER_DXP_ENTITIES
							});
					}
				}

				if (!unscheduleDispatchTriggerNames.isEmpty()) {
					_analyticsDXPEntityBatchExporter.unscheduleExportTriggers(
						companyId,
						unscheduleDispatchTriggerNames.toArray(new String[0]));
				}

				if (FeatureFlagManagerUtil.isEnabled("LRAC-10632")) {
					return;
				}
			}

			String[] previousSyncedContactFieldNames =
				GetterUtil.getStringValues(
					dictionary.get("previousSyncedContactFieldNames"));

			Arrays.sort(previousSyncedContactFieldNames);

			String[] previousSyncedUserFieldNames = GetterUtil.getStringValues(
				dictionary.get("previousSyncedUserFieldNames"));

			Arrays.sort(previousSyncedUserFieldNames);

			String[] syncedContactFieldNames = GetterUtil.getStringValues(
				dictionary.get("syncedContactFieldNames"));

			Arrays.sort(syncedContactFieldNames);

			String[] syncedUserFieldNames = GetterUtil.getStringValues(
				dictionary.get("syncedUserFieldNames"));

			Arrays.sort(syncedUserFieldNames);

			if (!Arrays.equals(
					previousSyncedUserFieldNames, syncedUserFieldNames)) {

				_syncUserCustomFields(companyId, syncedUserFieldNames);
			}

			if (!Arrays.equals(
					previousSyncedContactFieldNames, syncedContactFieldNames) ||
				!Arrays.equals(
					previousSyncedUserFieldNames, syncedUserFieldNames)) {

				_syncDefaultFields(
					companyId, syncedContactFieldNames, syncedUserFieldNames);
			}

			if (GetterUtil.getBoolean(dictionary.get("syncAllContacts"))) {
				if (!GetterUtil.getBoolean(
						dictionary.get("previousSyncAllContacts")) ||
					!Arrays.equals(
						previousSyncedContactFieldNames,
						syncedContactFieldNames) ||
					!Arrays.equals(
						previousSyncedUserFieldNames, syncedUserFieldNames)) {

					_syncContacts(companyId);
				}
			}
			else {
				_syncOrganizationUsers(
					companyId,
					(String[])dictionary.get("syncedOrganizationIds"));
				_syncUserGroupUsers(
					companyId, (String[])dictionary.get("syncedUserGroupIds"));
			}

			Message message = new Message();

			message.put("command", AnalyticsMessagesProcessorCommand.SEND);
			message.put("companyId", dictionary.get("companyId"));
			message.put("principalName", _getAnalyticsAdminUserId(companyId));

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
		catch (Exception exception) {
			_log.error(exception);
		}
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

			_addUsersAnalyticsMessages(companyId, users);
		}
	}

	private void _syncDefaultFields(
		long companyId, String[] syncedContactFieldNames,
		String[] syncedUserFieldNames) {

		for (Map.Entry<String, String> entry : _defaultFieldNames.entrySet()) {
			String fieldName = entry.getKey();

			if (!ArrayUtil.contains(syncedContactFieldNames, fieldName) &&
				!ArrayUtil.contains(syncedUserFieldNames, fieldName)) {

				continue;
			}

			JSONObject jsonObject = JSONUtil.put(
				"className", User.class.getName()
			).put(
				"companyId", companyId
			).put(
				"dataType", entry.getValue()
			).put(
				"name", fieldName
			);

			try {
				AnalyticsMessage.Builder analyticsMessageBuilder =
					AnalyticsMessage.builder(User.class.getName() + ".field");

				analyticsMessageBuilder.action("add");
				analyticsMessageBuilder.object(jsonObject);

				String analyticsMessageJSON =
					analyticsMessageBuilder.buildJSONString();

				_analyticsMessageLocalService.addAnalyticsMessage(
					companyId, _userLocalService.getGuestUserId(companyId),
					analyticsMessageJSON.getBytes(Charset.defaultCharset()));
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to add analytics message " +
							jsonObject.toString(),
						exception);
				}
			}
		}
	}

	private void _syncOrganizationUsers(
		long companyId, String[] organizationIds) {

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

					_addUsersAnalyticsMessages(companyId, users);
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

	private void _syncUserCustomFields(
		long companyId, String[] syncedUserFieldNames) {

		List<ExpandoColumn> expandoColumns = new ArrayList<>();

		List<ExpandoColumn> defaultTableColumns =
			_expandoColumnLocalService.getDefaultTableColumns(
				companyId, User.class.getName());

		for (ExpandoColumn defaultTableColumn : defaultTableColumns) {
			if (ArrayUtil.contains(
					syncedUserFieldNames, defaultTableColumn.getName())) {

				expandoColumns.add(defaultTableColumn);
			}
		}

		if (!expandoColumns.isEmpty()) {
			_addAnalyticsMessages("add", expandoColumns, companyId);
		}
	}

	private void _syncUserGroupUsers(long companyId, String[] userGroupIds) {
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

				_addUsersAnalyticsMessages(companyId, users);
			}
		}
	}

	private void _unmapPid(String pid) {
		Long companyId = _companyIds.remove(pid);

		if (companyId != null) {
			_analyticsConfigurations.remove(companyId);
		}
	}

	private void _updated(
		long companyId, String pid, Dictionary<String, ?> dictionary) {

		if (companyId != CompanyConstants.SYSTEM) {
			_analyticsConfigurations.put(
				companyId,
				ConfigurableUtil.createConfigurable(
					AnalyticsConfiguration.class, dictionary));
			_companyIds.put(pid, companyId);
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

			AnalyticsConfiguration analyticsConfiguration =
				getAnalyticsConfiguration(companyId);

			if (!FeatureFlagManagerUtil.isEnabled("LRAC-10757") &&
				analyticsConfiguration.wizardMode()) {

				return;
			}

			if (!FeatureFlagManagerUtil.isEnabled("LRAC-10757") &&
				analyticsConfiguration.firstSync()) {

				_firstSync(companyId);
			}
			else {
				_sync((Long)dictionary.get("companyId"), dictionary);
			}
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
		AnalyticsConfigurationRegistryImpl.class);

	private static final Map<String, String> _defaultFieldNames =
		HashMapBuilder.put(
			"agreedToTermsOfUse", "boolean"
		).put(
			"birthday", "date"
		).put(
			"classNameId", "Integer"
		).put(
			"classPK", "Integer"
		).put(
			"comments", "Text"
		).put(
			"companyId", "Integer"
		).put(
			"contactId", "Integer"
		).put(
			"createDate", "date"
		).put(
			"emailAddress", "Text"
		).put(
			"emailAddressVerified", "boolean"
		).put(
			"employeeNumber", "Text"
		).put(
			"employeeStatusId", "Text"
		).put(
			"externalReferenceCode", "Text"
		).put(
			"facebookId", "Integer"
		).put(
			"facebookSn", "Text"
		).put(
			"firstName", "Text"
		).put(
			"googleUserId", "Text"
		).put(
			"greeting", "Text"
		).put(
			"hoursOfOperation", "Text"
		).put(
			"jabberSn", "Text"
		).put(
			"jobClass", "Text"
		).put(
			"jobTitle", "Text"
		).put(
			"languageId", "Text"
		).put(
			"lastName", "Text"
		).put(
			"ldapServerId", "Integer"
		).put(
			"male", "boolean"
		).put(
			"middleName", "Text"
		).put(
			"modifiedDate", "date"
		).put(
			"openId", "Text"
		).put(
			"parentContactId", "Integer"
		).put(
			"portraitId", "Integer"
		).put(
			"prefixListTypeId", "Integer"
		).put(
			"screenName", "Text"
		).put(
			"skypeSn", "Text"
		).put(
			"smsSn", "Text"
		).put(
			"status", "Integer"
		).put(
			"suffixListTypeId", "Integer"
		).put(
			"timeZoneId", "Text"
		).put(
			"twitterSn", "Text"
		).put(
			"userId", "Integer"
		).put(
			"userName", "Text"
		).put(
			"uuid", "Text"
		).build();

	private boolean _active;
	private final Map<Long, AnalyticsConfiguration> _analyticsConfigurations =
		new ConcurrentHashMap<>();

	@Reference
	private AnalyticsDXPEntityBatchExporter _analyticsDXPEntityBatchExporter;

	@Reference
	private AnalyticsMessageLocalService _analyticsMessageLocalService;

	@Reference
	private AnalyticsSettingsManager _analyticsSettingsManager;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	private final Map<String, Long> _companyIds = new ConcurrentHashMap<>();

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private EntityModelListenerRegistry _entityModelListenerRegistry;

	@Reference
	private ExpandoColumnLocalService _expandoColumnLocalService;

	private final Set<Long> _initializedCompanyIds = new HashSet<>();

	@Reference
	private MessageBus _messageBus;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private SAPEntryLocalService _sapEntryLocalService;

	private volatile AnalyticsConfiguration _systemAnalyticsConfiguration;

	@Reference
	private UserLocalService _userLocalService;

}
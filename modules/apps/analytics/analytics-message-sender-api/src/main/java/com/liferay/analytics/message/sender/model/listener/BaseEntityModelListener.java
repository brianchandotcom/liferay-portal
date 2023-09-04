/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.sender.model.listener;

import com.liferay.analytics.message.sender.model.AnalyticsMessage;
import com.liferay.analytics.message.sender.util.AnalyticsExpandoBridgeUtil;
import com.liferay.analytics.message.storage.service.AnalyticsMessageLocalService;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.configuration.AnalyticsConfigurationRegistry;
import com.liferay.analytics.settings.security.constants.AnalyticsSecurityConstants;
import com.liferay.expando.kernel.model.ExpandoRow;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.model.TreeModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.nio.charset.Charset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@ProviderType
public abstract class BaseEntityModelListener<T extends BaseModel<T>>
	extends BaseModelListener<T> implements EntityModelListener<T> {

	@Override
	public void addAnalyticsMessage(
		String eventType, List<String> includeAttributeNames, T model) {

		String modelClassName = model.getModelClassName();

		if (modelClassName.equals(Contact.class.getName())) {
			Contact contact = (Contact)model;

			User user = userLocalService.fetchUser(contact.getClassPK());

			if ((!StringUtil.equalsIgnoreCase(eventType, "delete") &&
				 !isUserActive(user)) ||
				isUserExcluded(user)) {

				return;
			}
		}
		else if (modelClassName.equals(User.class.getName())) {
			User user = (User)model;

			if ((!StringUtil.equalsIgnoreCase(eventType, "delete") &&
				 !isUserActive(user)) ||
				isUserExcluded(user)) {

				return;
			}
		}
		else if (isExcluded(model)) {
			return;
		}

		JSONObject jsonObject = serialize(model, includeAttributeNames);

		ShardedModel shardedModel = (ShardedModel)model;

		if (modelClassName.equals(ExpandoRow.class.getName())) {
			ExpandoRow expandoRow = (ExpandoRow)model;

			if (isCustomField(
					Organization.class.getName(), expandoRow.getTableId())) {

				modelClassName = Organization.class.getName();
			}
			else {
				modelClassName = User.class.getName();
			}
		}

		try {
			AnalyticsMessage.Builder analyticsMessageBuilder =
				AnalyticsMessage.builder(modelClassName);

			analyticsMessageBuilder.action(eventType);
			analyticsMessageBuilder.object(jsonObject);

			String analyticsMessageJSON =
				analyticsMessageBuilder.buildJSONString();

			analyticsMessageLocalService.addAnalyticsMessage(
				shardedModel.getCompanyId(),
				userLocalService.getGuestUserId(shardedModel.getCompanyId()),
				analyticsMessageJSON.getBytes(Charset.defaultCharset()));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to add analytics message " + jsonObject.toString(),
					exception);
			}
		}
	}

	@Override
	public long[] getMembershipIds(User user) throws Exception {
		return new long[0];
	}

	@Override
	public String getModelClassName() {
		return null;
	}

	@Override
	public void onAfterAddAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException {

		if (FeatureFlagManagerUtil.isEnabled("LRAC-10632") ||
			!analyticsConfigurationRegistry.isActive()) {

			return;
		}

		_onAfterUpdateAssociation(
			classPK, associationClassName, associationClassPK,
			"addAssociation");
	}

	@Override
	public void onAfterCreate(T model) throws ModelListenerException {
		if (FeatureFlagManagerUtil.isEnabled("LRAC-10632") ||
			!analyticsConfigurationRegistry.isActive()) {

			return;
		}

		ShardedModel shardedModel = (ShardedModel)model;

		addAnalyticsMessage(
			"add", getAttributeNames(shardedModel.getCompanyId()), model);
	}

	@Override
	public void onAfterRemoveAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException {

		if (FeatureFlagManagerUtil.isEnabled("LRAC-10632") ||
			!analyticsConfigurationRegistry.isActive()) {

			return;
		}

		_onAfterUpdateAssociation(
			classPK, associationClassName, associationClassPK,
			"deleteAssociation");
	}

	@Override
	public void onBeforeRemove(T model) throws ModelListenerException {
		if (FeatureFlagManagerUtil.isEnabled("LRAC-10632") ||
			!analyticsConfigurationRegistry.isActive()) {

			return;
		}

		addAnalyticsMessage("delete", new ArrayList<>(), model);
	}

	@Override
	public void onBeforeUpdate(T originalModel, T model)
		throws ModelListenerException {

		if (FeatureFlagManagerUtil.isEnabled("LRAC-10632") ||
			!analyticsConfigurationRegistry.isActive()) {

			return;
		}

		ShardedModel shardedModel = (ShardedModel)model;

		try {
			List<String> modifiedAttributeNames = _getModifiedAttributeNames(
				getAttributeNames(shardedModel.getCompanyId()), model,
				getModel((long)model.getPrimaryKeyObj()));

			if (modifiedAttributeNames.isEmpty()) {
				return;
			}

			addAnalyticsMessage(
				"update", getAttributeNames(shardedModel.getCompanyId()),
				model);
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	@Override
	public void syncAll(long companyId) throws Exception {
		ActionableDynamicQuery actionableDynamicQuery =
			getActionableDynamicQuery();

		if (actionableDynamicQuery == null) {
			return;
		}

		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setPerformActionMethod(
			(T model) -> addAnalyticsMessage(
				"add", getAttributeNames(companyId), model));

		actionableDynamicQuery.performActions();
	}

	protected ActionableDynamicQuery getActionableDynamicQuery() {
		return null;
	}

	protected abstract T getModel(long id) throws Exception;

	protected List<String> getOrganizationAttributeNames() {
		return _organizationAttributeNames;
	}

	protected abstract String getPrimaryKeyName();

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getUserAttributeNames(long)}
	 */
	@Deprecated
	protected List<String> getUserAttributeNames() {
		return null;
	}

	protected List<String> getUserAttributeNames(long companyId) {
		AnalyticsConfiguration analyticsConfiguration =
			analyticsConfigurationRegistry.getAnalyticsConfiguration(companyId);

		if (ArrayUtil.isEmpty(analyticsConfiguration.syncedUserFieldNames())) {
			return _userAttributeNames;
		}

		List<String> attributeNames = new ArrayList<>();

		attributeNames.add("expando");
		attributeNames.add("memberships");

		for (String name : _userAttributeNames) {
			if (ArrayUtil.contains(
					analyticsConfiguration.syncedUserFieldNames(), name)) {

				attributeNames.add(name);
			}
		}

		return attributeNames;
	}

	protected boolean isCustomField(String className, long tableId) {
		long classNameId = classNameLocalService.getClassNameId(className);

		try {
			ExpandoTable expandoTable = expandoTableLocalService.getTable(
				tableId);

			if (Objects.equals(
					ExpandoTableConstants.DEFAULT_TABLE_NAME,
					expandoTable.getName()) &&
				(expandoTable.getClassNameId() == classNameId)) {

				return true;
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get expando table " + tableId, exception);
			}
		}

		return false;
	}

	protected boolean isExcluded(T model) {
		ShardedModel shardedModel = (ShardedModel)model;

		Dictionary<String, Object> analyticsConfigurationProperties =
			analyticsConfigurationRegistry.getAnalyticsConfigurationProperties(
				shardedModel.getCompanyId());

		if (analyticsConfigurationProperties == null) {
			return true;
		}

		return false;
	}

	protected boolean isUserActive(User user) {
		if ((user == null) ||
			Objects.equals(
				user.getStatus(), WorkflowConstants.STATUS_INACTIVE)) {

			return false;
		}

		return true;
	}

	protected boolean isUserExcluded(User user) {
		if ((user == null) ||
			Objects.equals(
				user.getScreenName(),
				AnalyticsSecurityConstants.SCREEN_NAME_ANALYTICS_ADMIN)) {

			return true;
		}

		AnalyticsConfiguration analyticsConfiguration =
			analyticsConfigurationRegistry.getAnalyticsConfiguration(
				user.getCompanyId());

		if (analyticsConfiguration.syncAllContacts()) {
			return false;
		}

		long[] organizationIds = null;

		try {
			organizationIds = user.getOrganizationIds();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return true;
		}

		for (long organizationId : organizationIds) {
			if (ArrayUtil.contains(
					analyticsConfiguration.syncedOrganizationIds(),
					String.valueOf(organizationId))) {

				return false;
			}
		}

		for (long userGroupId : user.getUserGroupIds()) {
			if (ArrayUtil.contains(
					analyticsConfiguration.syncedUserGroupIds(),
					String.valueOf(userGroupId))) {

				return false;
			}
		}

		return true;
	}

	protected JSONObject serialize(
		BaseModel<?> baseModel, List<String> includeAttributeNames) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		Map<String, Object> modelAttributes = baseModel.getModelAttributes();

		for (String includeAttributeName : includeAttributeNames) {
			if (includeAttributeName.equals("associations") &&
				StringUtil.equals(
					baseModel.getModelClassName(), User.class.getName())) {

				Map<String, long[]> memberships = new HashMap<>();

				User user = (User)baseModel;

				try {
					List<Group> groups = user.getSiteGroups();

					if (!groups.isEmpty()) {
						memberships.put(
							Group.class.getName(),
							TransformUtil.transformToLongArray(
								groups, Group::getGroupId));
					}
				}
				catch (Exception exception) {
					_log.error(exception);
				}

				try {
					long[] membershipIds = user.getOrganizationIds();

					if (membershipIds.length != 0) {
						memberships.put(
							Organization.class.getName(), membershipIds);
					}
				}
				catch (Exception exception) {
					_log.error(exception);
				}

				long[] membershipIds = user.getRoleIds();

				if (membershipIds.length != 0) {
					memberships.put(Role.class.getName(), membershipIds);
				}

				membershipIds = user.getTeamIds();

				if (membershipIds.length != 0) {
					memberships.put(Team.class.getName(), membershipIds);
				}

				membershipIds = user.getUserGroupIds();

				if (membershipIds.length != 0) {
					memberships.put(UserGroup.class.getName(), membershipIds);
				}

				jsonObject.put("memberships", memberships);

				continue;
			}
			else if (includeAttributeName.equals("expando")) {
				jsonObject.put(
					"expando",
					() -> {
						if (StringUtil.equals(
								baseModel.getModelClassName(),
								User.class.getName())) {

							ShardedModel shardedModel = (ShardedModel)baseModel;

							AnalyticsConfiguration analyticsConfiguration =
								analyticsConfigurationRegistry.
									getAnalyticsConfiguration(
										shardedModel.getCompanyId());

							return AnalyticsExpandoBridgeUtil.getAttributes(
								baseModel.getExpandoBridge(),
								ListUtil.fromArray(
									analyticsConfiguration.
										syncedUserFieldNames()));
						}

						return AnalyticsExpandoBridgeUtil.getAttributes(
							baseModel.getExpandoBridge(), null);
					});

				continue;
			}
			else if (includeAttributeName.equals("treePath") &&
					 (baseModel instanceof TreeModel)) {

				TreeModel treeModel = (TreeModel)baseModel;

				String treePath = treeModel.getTreePath();

				String[] ids = StringUtil.split(
					treePath.substring(1), StringPool.SLASH);

				jsonObject.put(
					"nameTreePath", _buildNameTreePath(ids)
				).put(
					"parentName",
					() -> {
						if (ids.length > 1) {
							return _getName(
								GetterUtil.getLong(ids[ids.length - 2]));
						}

						return null;
					}
				);

				continue;
			}

			Object value = modelAttributes.get(includeAttributeName);

			if (value instanceof Date) {
				Date date = (Date)value;

				jsonObject.put(includeAttributeName, date.getTime());
			}
			else {
				if (includeAttributeName.equals("name")) {
					value = _getName(String.valueOf(value));
				}

				jsonObject.put(includeAttributeName, value);
			}
		}

		return jsonObject.put(
			getPrimaryKeyName(),
			() -> {
				if (modelAttributes.containsKey(getPrimaryKeyName())) {
					return baseModel.getPrimaryKeyObj();
				}

				return null;
			});
	}

	protected void updateConfigurationProperties(
		long companyId, String configurationPropertyName, String modelId,
		String preferencePropertyName) {

		Dictionary<String, Object> configurationProperties =
			analyticsConfigurationRegistry.getAnalyticsConfigurationProperties(
				companyId);

		if (configurationProperties == null) {
			return;
		}

		String[] modelIds = (String[])configurationProperties.get(
			configurationPropertyName);

		if (!ArrayUtil.contains(modelIds, modelId)) {
			return;
		}

		modelIds = ArrayUtil.remove(modelIds, modelId);

		if (Validator.isNotNull(preferencePropertyName)) {
			try {
				companyService.updatePreferences(
					companyId,
					UnicodePropertiesBuilder.create(
						true
					).put(
						preferencePropertyName,
						StringUtil.merge(modelIds, StringPool.COMMA)
					).build());
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to update preferences for company " + companyId,
						exception);
				}
			}
		}

		configurationProperties.put(configurationPropertyName, modelIds);

		try {
			configurationProvider.saveCompanyConfiguration(
				AnalyticsConfiguration.class, companyId,
				configurationProperties);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to update configuration for company " + companyId,
					exception);
			}
		}
	}

	@Reference
	protected AnalyticsConfigurationRegistry analyticsConfigurationRegistry;

	@Reference
	protected AnalyticsMessageLocalService analyticsMessageLocalService;

	@Reference
	protected ClassNameLocalService classNameLocalService;

	@Reference
	protected CompanyService companyService;

	@Reference
	protected ConfigurationProvider configurationProvider;

	@Reference
	protected ExpandoTableLocalService expandoTableLocalService;

	@Reference
	protected UserLocalService userLocalService;

	private String _buildNameTreePath(String[] ids) {
		int size = ids.length;

		StringBundler sb = new StringBundler((ids.length * 4) + 1);

		sb.append(_getName(GetterUtil.getLong(ids[0])));

		for (int i = 1; i < size; i++) {
			sb.append(StringPool.SPACE);
			sb.append(StringPool.GREATER_THAN);
			sb.append(StringPool.SPACE);
			sb.append(_getName(GetterUtil.getLong(ids[i])));
		}

		return sb.toString();
	}

	private List<String> _getModifiedAttributeNames(
		List<String> attributeNames, T model, T originalModel) {

		List<String> modifiedAttributeNames = new ArrayList<>();

		for (String attributeName : attributeNames) {
			if (attributeName.equalsIgnoreCase("expando") ||
				attributeName.equalsIgnoreCase("memberships") ||
				(attributeName.equalsIgnoreCase("modifiedDate") &&
				 !Objects.equals(
					 model.getModelClassName(), ExpandoRow.class.getName()))) {

				continue;
			}

			String value = String.valueOf(
				BeanPropertiesUtil.getObject(model, attributeName));
			String originalValue = String.valueOf(
				BeanPropertiesUtil.getObject(originalModel, attributeName));

			if (!Objects.equals(value, originalValue)) {
				modifiedAttributeNames.add(attributeName);
			}
		}

		return modifiedAttributeNames;
	}

	private String _getName(long id) {
		try {
			T model = getModel(GetterUtil.getLong(id));

			Map<String, Object> modelAttributes = model.getModelAttributes();

			return _getName(String.valueOf(modelAttributes.get("name")));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}

			return null;
		}
	}

	private String _getName(String name) {
		if (!name.startsWith("<?xml")) {
			return name;
		}

		Locale locale = LocaleUtil.getDefault();

		return LocalizationUtil.getLocalization(name, locale.getLanguage());
	}

	private void _onAfterUpdateAssociation(
		Object classPK, String associationClassName, Object associationClassPK,
		String eventType) {

		String modelClassName = getModelClassName();

		if ((modelClassName == null) ||
			!associationClassName.equals(User.class.getName())) {

			return;
		}

		try {
			T model = getModel((long)classPK);

			if (isExcluded(model)) {
				return;
			}

			User user = userLocalService.fetchUser((long)associationClassPK);

			if (!eventType.equals("deleteAssociation") &&
				(!isUserActive(user) || isUserExcluded(user))) {

				return;
			}

			if (!eventType.equals("deleteAssociation")) {
				List<String> userAttributeNames = getUserAttributeNames(
					user.getCompanyId());

				userAttributeNames.add("associations");
				userAttributeNames.add("userId");

				addAnalyticsMessage("update", userAttributeNames, (T)user);

				if (user.fetchContact() != null) {
					AnalyticsConfiguration analyticsConfiguration =
						analyticsConfigurationRegistry.
							getAnalyticsConfiguration(user.getCompanyId());

					addAnalyticsMessage(
						"update",
						Arrays.asList(
							analyticsConfiguration.syncedContactFieldNames()),
						(T)user.fetchContact());
				}
			}

			Map<String, Object> modelAttributes = model.getModelAttributes();

			long companyId = (long)modelAttributes.get("companyId");

			AnalyticsMessage.Builder analyticsMessageBuilder =
				AnalyticsMessage.builder(getModelClassName());

			analyticsMessageBuilder.action(eventType);
			analyticsMessageBuilder.object(
				JSONUtil.put(
					"classPK", classPK
				).put(
					"emailAddress", user.getEmailAddress()
				).put(
					"userId", associationClassPK
				));

			String analyticsMessageJSON =
				analyticsMessageBuilder.buildJSONString();

			analyticsMessageLocalService.addAnalyticsMessage(
				companyId, userLocalService.getGuestUserId(companyId),
				analyticsMessageJSON.getBytes(Charset.defaultCharset()));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					String.format(
						"Unable to get %s %s", modelClassName, classPK),
					exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseEntityModelListener.class);

	private static final List<String> _organizationAttributeNames =
		Arrays.asList(
			"expando", "modifiedDate", "name", "parentOrganizationId",
			"treePath", "type");
	private static final List<String> _userAttributeNames = Arrays.asList(
		"agreedToTermsOfUse", "comments", "companyId", "contactId",
		"createDate", "emailAddress", "emailAddressVerified", "expando",
		"externalReferenceCode", "facebookId", "firstName", "googleUserId",
		"greeting", "jobTitle", "languageId", "lastName", "ldapServerId",
		"memberships", "middleName", "modifiedDate", "openId", "portraitId",
		"screenName", "status", "timeZoneId", "uuid");

}
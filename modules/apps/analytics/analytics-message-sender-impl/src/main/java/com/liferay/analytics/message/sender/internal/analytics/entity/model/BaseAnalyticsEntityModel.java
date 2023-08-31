/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.sender.internal.analytics.entity.model;

import com.liferay.analytics.message.sender.internal.util.AnalyticsModelUtil;
import com.liferay.analytics.message.sender.model.AnalyticsMessage;
import com.liferay.analytics.message.sender.model.listener.AnalyticsEntityModel;
import com.liferay.analytics.message.sender.util.AnalyticsExpandoBridgeUtil;
import com.liferay.analytics.message.storage.service.AnalyticsMessageLocalService;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.configuration.AnalyticsConfigurationRegistry;
import com.liferay.expando.kernel.model.ExpandoRow;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
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
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.nio.charset.Charset;

import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Joao Victor Alves
 */
public abstract class BaseAnalyticsEntityModel<T extends BaseModel<T>>
	implements AnalyticsEntityModel<T> {

	@Override
	public void addAnalyticsMessage(
		String eventType, List<String> includeAttributeNames, T model) {

		String modelClassName = model.getModelClassName();

		if (modelClassName.equals(Contact.class.getName())) {
			Contact contact = (Contact)model;

			User user = userLocalService.fetchUser(contact.getClassPK());

			if ((!StringUtil.equalsIgnoreCase(eventType, "delete") &&
				 !AnalyticsModelUtil.isUserActive(user)) ||
				AnalyticsModelUtil.isUserExcluded(
					analyticsConfigurationRegistry.getAnalyticsConfiguration(
						user.getCompanyId()),
					user)) {

				return;
			}
		}
		else if (modelClassName.equals(User.class.getName())) {
			User user = (User)model;

			if ((!StringUtil.equalsIgnoreCase(eventType, "delete") &&
				 !AnalyticsModelUtil.isUserActive(user)) ||
				AnalyticsModelUtil.isUserExcluded(
					analyticsConfigurationRegistry.getAnalyticsConfiguration(
						user.getCompanyId()),
					user)) {

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

			if (AnalyticsModelUtil.isCustomField(
					classNameLocalService.getClassNameId(
						Organization.class.getName()),
					expandoTableLocalService.fetchExpandoTable(
						expandoRow.getTableId()))) {

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
	public boolean isExcluded(T model) {
		ShardedModel shardedModel = (ShardedModel)model;

		Dictionary<String, Object> analyticsConfigurationProperties =
			analyticsConfigurationRegistry.getAnalyticsConfigurationProperties(
				shardedModel.getCompanyId());

		if (analyticsConfigurationProperties == null) {
			return true;
		}

		return false;
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

	protected String getName(long id) {
		try {
			T model = getModel(GetterUtil.getLong(id));

			Map<String, Object> modelAttributes = model.getModelAttributes();

			return getName(String.valueOf(modelAttributes.get("name")));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}

			return null;
		}
	}

	protected String getName(String name) {
		if (!name.startsWith("<?xml")) {
			return name;
		}

		Locale locale = LocaleUtil.getDefault();

		return LocalizationUtil.getLocalization(name, locale.getLanguage());
	}

	protected abstract String getPrimaryKeyName();

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
							return getName(
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
					value = getName(String.valueOf(value));
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

	@Reference
	protected AnalyticsConfigurationRegistry analyticsConfigurationRegistry;

	@Reference
	protected AnalyticsMessageLocalService analyticsMessageLocalService;

	@Reference
	protected ClassNameLocalService classNameLocalService;

	@Reference
	protected ExpandoTableLocalService expandoTableLocalService;

	@Reference
	protected UserLocalService userLocalService;

	private String _buildNameTreePath(String[] ids) {
		int size = ids.length;

		StringBundler sb = new StringBundler((ids.length * 4) + 1);

		sb.append(getName(GetterUtil.getLong(ids[0])));

		for (int i = 1; i < size; i++) {
			sb.append(StringPool.SPACE);
			sb.append(StringPool.GREATER_THAN);
			sb.append(StringPool.SPACE);
			sb.append(getName(GetterUtil.getLong(ids[i])));
		}

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseAnalyticsEntityModel.class);

}
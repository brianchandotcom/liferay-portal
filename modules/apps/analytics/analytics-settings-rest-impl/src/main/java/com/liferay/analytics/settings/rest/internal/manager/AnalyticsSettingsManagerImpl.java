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

package com.liferay.analytics.settings.rest.internal.manager;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.rest.constants.FieldAccountConstants;
import com.liferay.analytics.settings.rest.constants.FieldOrderConstants;
import com.liferay.analytics.settings.rest.constants.FieldPeopleConstants;
import com.liferay.analytics.settings.rest.constants.FieldProductConstants;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.settings.SettingsDescriptor;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(immediate = true, service = AnalyticsSettingsManager.class)
public class AnalyticsSettingsManagerImpl implements AnalyticsSettingsManager {

	public void deleteCompanyConfiguration(long companyId)
		throws ConfigurationException {

		List<Group> groups = ListUtil.concat(
			_groupLocalService.getGroups(
				companyId, GroupConstants.ANY_PARENT_GROUP_ID, true),
			_groupLocalService.getGroups(
				companyId, "com.liferay.commerce.product.model.CommerceChannel",
				0));

		for (Group group : groups) {
			UnicodeProperties typeSettingsUnicodeProperties =
				group.getTypeSettingsProperties();

			if (typeSettingsUnicodeProperties.remove("analyticsChannelId") !=
					null) {

				_groupLocalService.updateGroup(group);
			}
		}

		_configurationProvider.deleteCompanyConfiguration(
			AnalyticsConfiguration.class, companyId);
	}

	public AnalyticsConfiguration getAnalyticsConfiguration(long companyId)
		throws ConfigurationException {

		return _configurationProvider.getCompanyConfiguration(
			AnalyticsConfiguration.class, companyId);
	}

	public Long[] getCommerceChannelIds(
			String analyticsChannelId, long companyId)
		throws Exception {

		AnalyticsConfiguration analyticsConfiguration =
			getAnalyticsConfiguration(companyId);

		List<Long> commerceChannelIds = new ArrayList<>();

		for (String commerceChannelId :
				analyticsConfiguration.syncedCommerceChannelIds()) {

			Group group = _groupLocalService.fetchGroup(
				companyId, _commerceChannelClassNameId,
				GetterUtil.getLong(commerceChannelId));

			if (group == null) {
				continue;
			}

			UnicodeProperties typeSettingsUnicodeProperties =
				group.getTypeSettingsProperties();

			if (Objects.equals(
					analyticsChannelId,
					typeSettingsUnicodeProperties.getProperty(
						"analyticsChannelId"))) {

				commerceChannelIds.add(GetterUtil.getLong(commerceChannelId));
			}
		}

		return commerceChannelIds.toArray(new Long[0]);
	}

	public Long[] getSiteIds(String analyticsChannelId, long companyId)
		throws Exception {

		AnalyticsConfiguration analyticsConfiguration =
			getAnalyticsConfiguration(companyId);

		List<Long> groupIds = new ArrayList<>();

		for (String groupId : analyticsConfiguration.syncedGroupIds()) {
			Group group = _groupLocalService.fetchGroup(
				GetterUtil.getLong(groupId));

			if (group == null) {
				continue;
			}

			UnicodeProperties typeSettingsUnicodeProperties =
				group.getTypeSettingsProperties();

			if (Objects.equals(
					analyticsChannelId,
					typeSettingsUnicodeProperties.getProperty(
						"analyticsChannelId"))) {

				groupIds.add(GetterUtil.getLong(groupId));
			}
		}

		return groupIds.toArray(new Long[0]);
	}

	public boolean isAnalyticsEnabled(long companyId) throws Exception {
		AnalyticsConfiguration analyticsConfiguration =
			getAnalyticsConfiguration(companyId);

		if (Validator.isNull(
				analyticsConfiguration.liferayAnalyticsDataSourceId()) ||
			Validator.isNull(
				analyticsConfiguration.
					liferayAnalyticsFaroBackendSecuritySignature()) ||
			Validator.isNull(
				analyticsConfiguration.liferayAnalyticsFaroBackendURL())) {

			return false;
		}

		return true;
	}

	public String[] updateCommerceChannelIds(
			String analyticsChannelId, long companyId,
			Long[] dataSourceCommerceChannelIds)
		throws Exception {

		_updateTypeSetting(
			analyticsChannelId, _commerceChannelClassNameId, companyId,
			dataSourceCommerceChannelIds, false);

		AnalyticsConfiguration analyticsConfiguration =
			getAnalyticsConfiguration(companyId);

		Set<String> commerceChannelIds = SetUtil.fromArray(
			analyticsConfiguration.syncedCommerceChannelIds());

		for (Long dataSourceCommerceChannelId : dataSourceCommerceChannelIds) {
			commerceChannelIds.add(String.valueOf(dataSourceCommerceChannelId));
		}

		Long[] removeCommerceChannelIds = ArrayUtil.filter(
			getCommerceChannelIds(analyticsChannelId, companyId),
			commerceChannelId -> !ArrayUtil.contains(
				dataSourceCommerceChannelIds, commerceChannelId));

		_updateTypeSetting(
			analyticsChannelId, _commerceChannelClassNameId, companyId,
			removeCommerceChannelIds, true);

		Stream<String> commerceChannelIdsStream = commerceChannelIds.stream();

		return commerceChannelIdsStream.filter(
			commerceChannelId -> !ArrayUtil.contains(
				removeCommerceChannelIds, String.valueOf(commerceChannelId))
		).toArray(
			String[]::new
		);
	}

	public void updateCompanyConfiguration(
			long companyId, Map<String, Object> properties)
		throws Exception {

		Map<String, Object> configurationProperties = new HashMap<>();

		Configuration configuration = _getFactoryConfiguration(
			_getConfigurationPid(), ExtendedObjectClassDefinition.Scope.COMPANY,
			companyId);

		if (configuration != null) {
			configurationProperties = _toMap(configuration.getProperties());
		}

		SettingsDescriptor settingsDescriptor =
			_settingsFactory.getSettingsDescriptor(_getConfigurationPid());

		Set<String> allKeys = settingsDescriptor.getAllKeys();

		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			if (allKeys.contains(entry.getKey())) {
				configurationProperties.put(entry.getKey(), entry.getValue());
			}
		}

		for (String multiValuedKey : settingsDescriptor.getMultiValuedKeys()) {
			configurationProperties.computeIfAbsent(
				multiValuedKey,
				key -> _defaults.getOrDefault(key, new String[0]));
		}

		_configurationProvider.saveCompanyConfiguration(
			AnalyticsConfiguration.class, companyId,
			_toDictionary(configurationProperties));
	}

	public String[] updateSiteIds(
			String analyticsChannelId, long companyId, Long[] dataSourceSiteIds)
		throws Exception {

		_updateTypeSetting(
			analyticsChannelId, _groupClassNameId, companyId, dataSourceSiteIds,
			false);

		AnalyticsConfiguration analyticsConfiguration =
			getAnalyticsConfiguration(companyId);

		Set<String> siteIds = SetUtil.fromArray(
			analyticsConfiguration.syncedGroupIds());

		for (Long dataSourceSiteId : dataSourceSiteIds) {
			siteIds.add(String.valueOf(dataSourceSiteId));
		}

		Long[] removeSiteIds = ArrayUtil.filter(
			getSiteIds(analyticsChannelId, companyId),
			siteId -> !ArrayUtil.contains(dataSourceSiteIds, siteId));

		_updateTypeSetting(
			analyticsChannelId, _groupClassNameId, companyId, removeSiteIds,
			true);

		Stream<String> siteIdsStream = siteIds.stream();

		return siteIdsStream.filter(
			siteId -> !ArrayUtil.contains(removeSiteIds, String.valueOf(siteId))
		).toArray(
			String[]::new
		);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_commerceChannelClassNameId = _portal.getClassNameId(
			"com.liferay.commerce.product.model.CommerceChannel");

		_groupClassNameId = _portal.getClassNameId(Group.class);
	}

	private String _getConfigurationPid() {
		Class<?> clazz = AnalyticsConfiguration.class;

		Meta.OCD ocd = clazz.getAnnotation(Meta.OCD.class);

		return ocd.id();
	}

	private Configuration _getFactoryConfiguration(
			String factoryPid, ExtendedObjectClassDefinition.Scope scope,
			Serializable scopePK)
		throws Exception {

		try {
			String filterString = StringBundler.concat(
				"(&(service.factoryPid=", factoryPid, ".scoped)(",
				scope.getPropertyKey(), "=", scopePK, "))");

			Configuration[] configurations =
				_configurationAdmin.listConfigurations(filterString);

			if (configurations != null) {
				return configurations[0];
			}

			return null;
		}
		catch (InvalidSyntaxException | IOException exception) {
			_log.error(exception);

			throw new ConfigurationException(
				"Unable to retrieve factory configuration " + factoryPid,
				exception);
		}
	}

	private Dictionary<String, Object> _toDictionary(Map<String, Object> map) {
		return new HashMapDictionary<>(map);
	}

	private Map<String, Object> _toMap(Dictionary<String, Object> dictionary) {
		if (dictionary == null) {
			return Collections.emptyMap();
		}

		List<String> keys = Collections.list(dictionary.keys());

		Stream<String> stream = keys.stream();

		return stream.collect(
			Collectors.toMap(Function.identity(), dictionary::get));
	}

	private <T> void _updateTypeSetting(
			String analyticsChannelId, long classNameId, long companyId,
			T[] classPKs, boolean remove)
		throws Exception {

		for (T classPK : classPKs) {
			Group group = _groupLocalService.fetchGroup(
				companyId, classNameId, GetterUtil.getLong(classPK));

			if (group == null) {
				continue;
			}

			UnicodeProperties typeSettingsUnicodeProperties =
				group.getTypeSettingsProperties();

			if (remove) {
				if (!analyticsChannelId.equals(
						typeSettingsUnicodeProperties.get(
							"analyticsChannelId"))) {

					continue;
				}

				typeSettingsUnicodeProperties.remove("analyticsChannelId");
			}
			else {
				if (analyticsChannelId.equals(
						typeSettingsUnicodeProperties.get(
							"analyticsChannelId"))) {

					continue;
				}

				typeSettingsUnicodeProperties.setProperty(
					"analyticsChannelId", analyticsChannelId);
			}

			_groupLocalService.updateGroup(group);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsSettingsManagerImpl.class);

	private static final Map<String, String[]> _defaults = HashMapBuilder.put(
		"syncedAccountFieldNames", FieldAccountConstants.FIELD_ACCOUNT_NAMES
	).put(
		"syncedCategoryFieldNames", FieldProductConstants.FIELD_CATEGORY_NAMES
	).put(
		"syncedContactFieldNames", FieldPeopleConstants.FIELD_CONTACT_NAMES
	).put(
		"syncedOrderFieldNames", FieldOrderConstants.FIELD_ORDER_NAMES
	).put(
		"syncedOrderItemFieldNames", FieldOrderConstants.FIELD_ORDER_ITEM_NAMES
	).put(
		"syncedProductChannelFieldNames",
		FieldProductConstants.FIELD_PRODUCT_CHANNEL_NAMES
	).put(
		"syncedProductFieldNames", FieldProductConstants.FIELD_PRODUCT_NAMES
	).put(
		"syncedUserFieldNames", FieldPeopleConstants.FIELD_USER_NAMES
	).build();

	private long _commerceChannelClassNameId;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private ConfigurationProvider _configurationProvider;

	private long _groupClassNameId;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SettingsFactory _settingsFactory;

}
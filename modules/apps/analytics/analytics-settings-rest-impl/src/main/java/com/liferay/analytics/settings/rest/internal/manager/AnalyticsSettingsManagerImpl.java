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
import com.liferay.analytics.settings.rest.internal.constants.FieldAccountConstants;
import com.liferay.analytics.settings.rest.internal.constants.FieldPeopleConstants;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.settings.SettingsDescriptor;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionary;
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

		_configurationProvider.deleteCompanyConfiguration(
			AnalyticsConfiguration.class, companyId);
	}

	public AnalyticsConfiguration getAnalyticsConfiguration(long companyId)
		throws ConfigurationException {

		return _configurationProvider.getCompanyConfiguration(
			AnalyticsConfiguration.class, companyId);
	}

	public Long[] getCommerceChannelIds(String channelId, long companyId)
		throws Exception {

		AnalyticsConfiguration analyticsConfiguration =
			getAnalyticsConfiguration(companyId);

		return _getChannelIds(
			analyticsConfiguration.syncedCommerceChannelIds(), channelId,
			commerceChannelId -> _groupLocalService.fetchGroup(
				companyId, _commerceChannelClassNameId, commerceChannelId));
	}

	public Long[] getSiteIds(String channelId, long companyId)
		throws Exception {

		AnalyticsConfiguration analyticsConfiguration =
			getAnalyticsConfiguration(companyId);

		return _getChannelIds(
			analyticsConfiguration.syncedGroupIds(), channelId,
			groupId -> _groupLocalService.fetchGroup(groupId));
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
			String channelId, long companyId,
			Long[] dataSourceCommerceChannelIds)
		throws Exception {

		_updateTypeSetting(
			channelId,
			commerceChannelId -> _groupLocalService.fetchGroup(
				companyId, _commerceChannelClassNameId, commerceChannelId),
			dataSourceCommerceChannelIds, false);

		AnalyticsConfiguration analyticsConfiguration =
			getAnalyticsConfiguration(companyId);

		Set<String> commerceChannelIds = SetUtil.fromArray(
			analyticsConfiguration.syncedCommerceChannelIds());

		for (Long dataSourceCommerceChannelId : dataSourceCommerceChannelIds) {
			commerceChannelIds.add(String.valueOf(dataSourceCommerceChannelId));
		}

		Long[] removeChannelIds = ArrayUtil.filter(
			_getChannelIds(
				analyticsConfiguration.syncedCommerceChannelIds(), channelId,
				commerceChannelId -> _groupLocalService.fetchGroup(
					companyId, _commerceChannelClassNameId, commerceChannelId)),
			commerceChannelId -> !ArrayUtil.contains(
				dataSourceCommerceChannelIds, commerceChannelId));

		_updateTypeSetting(
			channelId,
			commerceChannelId -> _groupLocalService.fetchGroup(
				companyId, _commerceChannelClassNameId, commerceChannelId),
			removeChannelIds, true);

		Stream<String> commerceChannelIdsStream = commerceChannelIds.stream();

		return commerceChannelIdsStream.filter(
			commerceChannelId -> !ArrayUtil.contains(
				removeChannelIds, String.valueOf(commerceChannelId))
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
			String channelId, long companyId, Long[] dataSourceSiteIds)
		throws Exception {

		_updateTypeSetting(
			channelId, siteId -> _groupLocalService.fetchGroup(siteId),
			dataSourceSiteIds, false);

		AnalyticsConfiguration analyticsConfiguration =
			getAnalyticsConfiguration(companyId);

		Set<String> siteIds = SetUtil.fromArray(
			analyticsConfiguration.syncedGroupIds());

		for (Long dataSourceSiteId : dataSourceSiteIds) {
			siteIds.add(String.valueOf(dataSourceSiteId));
		}

		Long[] removeSiteIds = ArrayUtil.filter(
			_getChannelIds(
				analyticsConfiguration.syncedGroupIds(), channelId,
				siteId -> _groupLocalService.fetchGroup(siteId)),
			siteId -> !ArrayUtil.contains(dataSourceSiteIds, siteId));

		_updateTypeSetting(
			channelId, siteId -> _groupLocalService.fetchGroup(siteId),
			removeSiteIds, true);

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
	}

	private Long[] _getChannelIds(
		String[] analyticsConfigurationIds, String channelId,
		Function<Long, Group> fetchGroupFunction) {

		List<Long> ids = new ArrayList<>();

		for (String groupId : analyticsConfigurationIds) {
			Group group = fetchGroupFunction.apply(GetterUtil.getLong(groupId));

			if (group == null) {
				continue;
			}

			UnicodeProperties typeSettingsUnicodeProperties =
				group.getTypeSettingsProperties();

			if (Objects.equals(
					channelId,
					typeSettingsUnicodeProperties.getProperty(
						"analyticsChannelId"))) {

				ids.add(GetterUtil.getLong(groupId));
			}
		}

		return ids.toArray(new Long[0]);
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
			String channelId, Function<Long, Group> fetchGroupFunction,
			T[] groupIds, boolean remove)
		throws Exception {

		for (T groupId : groupIds) {
			Group group = fetchGroupFunction.apply(GetterUtil.getLong(groupId));

			if (group == null) {
				continue;
			}

			UnicodeProperties typeSettingsUnicodeProperties =
				group.getTypeSettingsProperties();

			String analyticsChannelId = typeSettingsUnicodeProperties.get(
				"analyticsChannelId");

			if (remove) {
				if (!channelId.equals(analyticsChannelId)) {
					continue;
				}

				typeSettingsUnicodeProperties.remove("analyticsChannelId");
			}
			else {
				if (channelId.equals(analyticsChannelId)) {
					continue;
				}

				typeSettingsUnicodeProperties.setProperty(
					"analyticsChannelId", channelId);
			}

			_groupLocalService.updateGroup(group);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsSettingsManagerImpl.class);

	private static final Map<String, String[]> _defaults = HashMapBuilder.put(
		"syncedAccountFieldNames", FieldAccountConstants.FIELD_ACCOUNT_NAMES
	).put(
		"syncedContactFieldNames", FieldPeopleConstants.FIELD_CONTACT_NAMES
	).put(
		"syncedUserFieldNames", FieldPeopleConstants.FIELD_USER_NAMES
	).build();

	private long _commerceChannelClassNameId;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SettingsFactory _settingsFactory;

}
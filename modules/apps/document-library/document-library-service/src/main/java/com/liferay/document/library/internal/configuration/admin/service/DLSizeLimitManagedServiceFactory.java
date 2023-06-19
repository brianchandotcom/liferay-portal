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

package com.liferay.document.library.internal.configuration.admin.service;

import com.liferay.document.library.internal.configuration.DLSizeLimitConfiguration;
import com.liferay.document.library.internal.util.MimeTypeSizeLimitUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Dictionary;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import org.osgi.framework.Constants;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	configurationPid = "com.liferay.document.library.internal.configuration.DLSizeLimitConfiguration",
	property = Constants.SERVICE_PID + "=com.liferay.document.library.internal.configuration.DLSizeLimitConfiguration.scoped",
	service = {
		DLSizeLimitManagedServiceFactory.class, ManagedServiceFactory.class
	}
)
public class DLSizeLimitManagedServiceFactory implements ManagedServiceFactory {

	@Override
	public void deleted(String pid) {
		_unmapPid(pid);
	}

	public long getCompanyFileMaxSize(long companyId) {
		DLSizeLimitConfiguration dlSizeLimitConfiguration =
			_getCompanyDLSizeLimitConfiguration(companyId);

		return dlSizeLimitConfiguration.fileMaxSize();
	}

	public long getCompanyMaxSizeToCopy(long companyId) {
		DLSizeLimitConfiguration dlSizeLimitConfiguration =
			_getCompanyDLSizeLimitConfiguration(companyId);

		return dlSizeLimitConfiguration.maxSizeToCopy();
	}

	public Map<String, Long> getCompanyMimeTypeSizeLimit(long companyId) {
		return _companyMimeTypeSizeLimitsMap.computeIfAbsent(
			companyId, this::_computeCompanyMimeTypeSizeLimit);
	}

	public long getCompanyMimeTypeSizeLimit(long companyId, String mimeType) {
		if (Validator.isNull(mimeType)) {
			return 0;
		}

		Map<String, Long> map = _companyMimeTypeSizeLimitsMap.computeIfAbsent(
			companyId, this::_computeCompanyMimeTypeSizeLimit);

		long sizeLimit = map.getOrDefault(mimeType, 0L);

		if (sizeLimit != 0) {
			return sizeLimit;
		}

		List<String> parts = StringUtil.split(mimeType, CharPool.SLASH);

		return map.getOrDefault(String.format("%s/*", parts.get(0)), 0L);
	}

	public long getGroupFileMaxSize(long groupId) {
		DLSizeLimitConfiguration dlSizeLimitConfiguration =
			_getGroupDLSizeLimitConfiguration(groupId);

		return dlSizeLimitConfiguration.fileMaxSize();
	}

	public long getGroupMaxSizeToCopy(long groupId) {
		DLSizeLimitConfiguration dlSizeLimitConfiguration =
			_getGroupDLSizeLimitConfiguration(groupId);

		return dlSizeLimitConfiguration.maxSizeToCopy();
	}

	public Map<String, Long> getGroupMimeTypeSizeLimit(long groupId) {
		return _groupMimeTypeSizeLimitsMap.computeIfAbsent(
			groupId, this::_computeGroupMimeTypeSizeLimit);
	}

	public long getGroupMimeTypeSizeLimit(long groupId, String mimeType) {
		if (Validator.isNull(mimeType)) {
			return 0;
		}

		Map<String, Long> map = _groupMimeTypeSizeLimitsMap.computeIfAbsent(
			groupId, this::_computeGroupMimeTypeSizeLimit);

		long sizeLimit = map.getOrDefault(mimeType, 0L);

		if (sizeLimit != 0) {
			return sizeLimit;
		}

		List<String> parts = StringUtil.split(mimeType, CharPool.SLASH);

		return map.getOrDefault(String.format("%s/*", parts.get(0)), 0L);
	}

	@Override
	public String getName() {
		return "com.liferay.document.library.internal.configuration." +
			"DLSizeLimitConfiguration.scoped";
	}

	public long getSystemFileMaxSize() {
		return _systemDLSizeLimitConfiguration.fileMaxSize();
	}

	public long getSystemMaxSizeToCopy() {
		return _systemDLSizeLimitConfiguration.maxSizeToCopy();
	}

	public Map<String, Long> getSystemMimeTypeSizeLimit() {
		return _computeMimeTypeSizeLimit(_systemDLSizeLimitConfiguration);
	}

	@Override
	public void updated(String pid, Dictionary<String, ?> dictionary)
		throws ConfigurationException {

		_unmapPid(pid);

		long companyId = GetterUtil.getLong(
			dictionary.get("companyId"), CompanyConstants.SYSTEM);

		if (companyId != CompanyConstants.SYSTEM) {
			_updateCompanyConfiguration(companyId, pid, dictionary);
		}

		long groupId = GetterUtil.getLong(
			dictionary.get("groupId"), GroupConstants.DEFAULT_PARENT_GROUP_ID);

		if (groupId != GroupConstants.DEFAULT_PARENT_GROUP_ID) {
			_updateGroupConfiguration(groupId, pid, dictionary);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_companyMimeTypeSizeLimitsMap = new ConcurrentHashMap<>();
		_groupMimeTypeSizeLimitsMap = new ConcurrentHashMap<>();
		_systemDLSizeLimitConfiguration = ConfigurableUtil.createConfigurable(
			DLSizeLimitConfiguration.class, properties);
	}

	private Map<String, Long> _computeCompanyMimeTypeSizeLimit(long companyId) {
		return _computeMimeTypeSizeLimit(
			_getCompanyDLSizeLimitConfiguration(companyId));
	}

	private Map<String, Long> _computeGroupMimeTypeSizeLimit(long groupId) {
		return _computeMimeTypeSizeLimit(
			_getGroupDLSizeLimitConfiguration(groupId));
	}

	private Map<String, Long> _computeMimeTypeSizeLimit(
		DLSizeLimitConfiguration dlSizeLimitConfiguration) {

		Map<String, Long> mimeTypeSizeLimits = new LinkedHashMap<>();

		for (String mimeTypeSizeLimit :
				dlSizeLimitConfiguration.mimeTypeSizeLimit()) {

			MimeTypeSizeLimitUtil.parseMimeTypeSizeLimit(
				mimeTypeSizeLimit, mimeTypeSizeLimits::put);
		}

		return mimeTypeSizeLimits;
	}

	private DLSizeLimitConfiguration _getCompanyDLSizeLimitConfiguration(
		long companyId) {

		return _getDLSizeLimitConfiguration(
			companyId, _companyConfigurationBeans,
			() -> _systemDLSizeLimitConfiguration);
	}

	private DLSizeLimitConfiguration _getDLSizeLimitConfiguration(
		long key, Map<Long, DLSizeLimitConfiguration> configurationBeans,
		Supplier<DLSizeLimitConfiguration> supplier) {

		if (configurationBeans.containsKey(key)) {
			return configurationBeans.get(key);
		}

		return supplier.get();
	}

	private DLSizeLimitConfiguration _getGroupDLSizeLimitConfiguration(
		long groupId) {

		return _getDLSizeLimitConfiguration(
			groupId, _groupConfigurationBeans,
			() -> {
				Group group = _groupLocalService.fetchGroup(groupId);

				long companyId = CompanyThreadLocal.getCompanyId();

				if (group != null) {
					companyId = group.getCompanyId();
				}

				return _getCompanyDLSizeLimitConfiguration(companyId);
			});
	}

	private void _unmapPid(String pid) {
		if (_companyIds.containsKey(pid)) {
			long companyId = _companyIds.remove(pid);

			_companyConfigurationBeans.remove(companyId);
			_companyMimeTypeSizeLimitsMap.remove(companyId);

			_groupConfigurationBeans.clear();
			_groupIds.clear();
			_groupMimeTypeSizeLimitsMap.clear();
		}
		else if (_groupIds.containsKey(pid)) {
			long groupId = _groupIds.remove(pid);

			_groupConfigurationBeans.remove(groupId);
			_groupMimeTypeSizeLimitsMap.remove(groupId);
		}
	}

	private void _updateCompanyConfiguration(
		long companyId, String pid, Dictionary<String, ?> dictionary) {

		_companyConfigurationBeans.put(
			companyId,
			ConfigurableUtil.createConfigurable(
				DLSizeLimitConfiguration.class, dictionary));
		_companyIds.put(pid, companyId);
		_companyMimeTypeSizeLimitsMap.remove(companyId);
	}

	private void _updateGroupConfiguration(
		long groupId, String pid, Dictionary<String, ?> dictionary) {

		_groupConfigurationBeans.put(
			groupId,
			ConfigurableUtil.createConfigurable(
				DLSizeLimitConfiguration.class, dictionary));
		_groupIds.put(pid, groupId);
		_groupMimeTypeSizeLimitsMap.remove(groupId);
	}

	private final Map<Long, DLSizeLimitConfiguration>
		_companyConfigurationBeans = new ConcurrentHashMap<>();
	private final Map<String, Long> _companyIds = new ConcurrentHashMap<>();
	private volatile Map<Long, Map<String, Long>> _companyMimeTypeSizeLimitsMap;
	private final Map<Long, DLSizeLimitConfiguration> _groupConfigurationBeans =
		new ConcurrentHashMap<>();
	private final Map<String, Long> _groupIds = new ConcurrentHashMap<>();

	@Reference
	private GroupLocalService _groupLocalService;

	private volatile Map<Long, Map<String, Long>> _groupMimeTypeSizeLimitsMap;
	private volatile DLSizeLimitConfiguration _systemDLSizeLimitConfiguration;

}
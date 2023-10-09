/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.configuration.helper;

import com.liferay.document.library.configuration.DLFileEntryConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;

import java.util.Dictionary;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Galluzzi
 */
@Component(
	configurationPid = "com.liferay.document.library.configuration.DLFileEntryConfiguration",
	service = DLFileEntryConfigurationHelper.class
)
public class DLFileEntryConfigurationHelper {

	public int getCompanyMaxNumberOfPages(long companyId) {
		DLFileEntryConfiguration dlFileEntryConfiguration =
			_getCompanyDLFileEntryConfiguration(companyId);

		return dlFileEntryConfiguration.maxNumberOfPages();
	}

	public long getCompanyPreviewableProcessorMaxSize(long companyId) {
		DLFileEntryConfiguration dlFileEntryConfiguration =
			_getCompanyDLFileEntryConfiguration(companyId);

		return dlFileEntryConfiguration.previewableProcessorMaxSize();
	}

	public Set<Long> getGroupIds() {
		return _groupConfigurationBeans.keySet();
	}

	public int getGroupMaxNumberOfPages(long groupId) {
		DLFileEntryConfiguration dlFileEntryConfiguration =
			_getGroupDLFileEntryConfiguration(groupId);

		return dlFileEntryConfiguration.maxNumberOfPages();
	}

	public long getGroupPreviewableProcessorMaxSize(long groupId) {
		DLFileEntryConfiguration dlFileEntryConfiguration =
			_getGroupDLFileEntryConfiguration(groupId);

		return dlFileEntryConfiguration.previewableProcessorMaxSize();
	}

	public int getSystemMaxNumberOfPages() {
		return _systemDLFileEntryConfiguration.maxNumberOfPages();
	}

	public long getSystemPreviewableProcessorMaxSize() {
		return _systemDLFileEntryConfiguration.previewableProcessorMaxSize();
	}

	public void unmapPid(String pid) {
		if (_companyIds.containsKey(pid)) {
			long companyId = _companyIds.remove(pid);

			_companyConfigurationBeans.remove(companyId);

			_groupConfigurationBeans.clear();
			_groupIds.clear();
		}
		else if (_groupIds.containsKey(pid)) {
			long groupId = _groupIds.remove(pid);

			_groupConfigurationBeans.remove(groupId);
		}
	}

	public void updateCompanyConfiguration(
		long companyId, String pid, Dictionary<String, ?> dictionary) {

		_companyConfigurationBeans.put(
			companyId,
			ConfigurableUtil.createConfigurable(
				DLFileEntryConfiguration.class, dictionary));
		_companyIds.put(pid, companyId);
	}

	public void updateGroupConfiguration(
		long groupId, String pid, Dictionary<String, ?> dictionary) {

		_groupConfigurationBeans.put(
			groupId,
			ConfigurableUtil.createConfigurable(
				DLFileEntryConfiguration.class, dictionary));
		_groupIds.put(pid, groupId);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_systemDLFileEntryConfiguration = ConfigurableUtil.createConfigurable(
			DLFileEntryConfiguration.class, properties);
	}

	private DLFileEntryConfiguration _getCompanyDLFileEntryConfiguration(
		long companyId) {

		return _getDLFileEntryConfiguration(
			companyId, _companyConfigurationBeans,
			() -> _systemDLFileEntryConfiguration);
	}

	private DLFileEntryConfiguration _getDLFileEntryConfiguration(
		long key, Map<Long, DLFileEntryConfiguration> configurationBeans,
		Supplier<DLFileEntryConfiguration> supplier) {

		if (configurationBeans.containsKey(key)) {
			return configurationBeans.get(key);
		}

		return supplier.get();
	}

	private DLFileEntryConfiguration _getGroupDLFileEntryConfiguration(
		long groupId) {

		return _getDLFileEntryConfiguration(
			groupId, _groupConfigurationBeans,
			() -> {
				Group group = _groupLocalService.fetchGroup(groupId);

				long companyId = CompanyThreadLocal.getCompanyId();

				if (group != null) {
					companyId = group.getCompanyId();
				}

				return _getCompanyDLFileEntryConfiguration(companyId);
			});
	}

	private final Map<Long, DLFileEntryConfiguration>
		_companyConfigurationBeans = new ConcurrentHashMap<>();
	private final Map<String, Long> _companyIds = new ConcurrentHashMap<>();
	private final Map<Long, DLFileEntryConfiguration> _groupConfigurationBeans =
		new ConcurrentHashMap<>();
	private final Map<String, Long> _groupIds = new ConcurrentHashMap<>();

	@Reference
	private GroupLocalService _groupLocalService;

	private volatile DLFileEntryConfiguration _systemDLFileEntryConfiguration;

}
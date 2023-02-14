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

package com.liferay.document.library.preview.pdf.internal.configuration.admin.service;

import com.liferay.document.library.preview.pdf.exception.PDFPreviewException;
import com.liferay.document.library.preview.pdf.internal.configuration.PDFPreviewConfiguration;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(
	configurationPid = "com.liferay.document.library.preview.pdf.internal.configuration.PDFPreviewConfiguration",
	property = Constants.SERVICE_PID + "=com.liferay.document.library.preview.pdf.internal.configuration.PDFPreviewConfiguration.scoped",
	service = {
		ManagedServiceFactory.class, PDFPreviewManagedServiceFactory.class
	}
)
public class PDFPreviewManagedServiceFactory implements ManagedServiceFactory {

	@Override
	public void deleted(String pid) {
		_unmapPid(pid);
	}

	public long getMaxNumberOfPages(String scope, long scopePK)
		throws PortalException {

		if (scope.equals(
				ExtendedObjectClassDefinition.Scope.COMPANY.getValue())) {

			long companyMaxNumberOfPages = _getCompanyMaxNumberOfPages(scopePK);

			long systemMaxNumberOfPages = _getSystemMaxNumberOfPages();

			if ((companyMaxNumberOfPages != 0) &&
				((systemMaxNumberOfPages == 0) ||
				 (companyMaxNumberOfPages < systemMaxNumberOfPages))) {

				return companyMaxNumberOfPages;
			}

			return systemMaxNumberOfPages;
		}
		else if (scope.equals(
					ExtendedObjectClassDefinition.Scope.GROUP.getValue())) {

			long groupMaxNumberOfPages = _getGroupMaxNumberOfPages(scopePK);

			Group group = _groupLocalService.getGroup(scopePK);

			long companyMaxNumberOfPages = _getCompanyMaxNumberOfPages(
				group.getCompanyId());

			long systemMaxNumberOfPages = _getSystemMaxNumberOfPages();

			if ((groupMaxNumberOfPages != 0) &&
				((systemMaxNumberOfPages == 0) ||
				 (groupMaxNumberOfPages < systemMaxNumberOfPages)) &&
				((companyMaxNumberOfPages == 0) ||
				 (groupMaxNumberOfPages < companyMaxNumberOfPages))) {

				return groupMaxNumberOfPages;
			}

			if ((companyMaxNumberOfPages != 0) &&
				((systemMaxNumberOfPages == 0) ||
				 (companyMaxNumberOfPages < systemMaxNumberOfPages))) {

				return companyMaxNumberOfPages;
			}

			return systemMaxNumberOfPages;
		}
		else if (scope.equals(
					ExtendedObjectClassDefinition.Scope.SYSTEM.getValue())) {

			return _getSystemMaxNumberOfPages();
		}

		throw new IllegalArgumentException("Unsupported scope: " + scope);
	}

	@Override
	public String getName() {
		return "com.liferay.document.library.preview.pdf.internal." +
			"configuration.PDFPreviewConfiguration.scoped";
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

	public void updatePDFPreview(
			long maxNumberOfPages, String scope, long scopePK)
		throws Exception {

		long systemMaxNumberOfPages = _getSystemMaxNumberOfPages();

		if (scope.equals(
				ExtendedObjectClassDefinition.Scope.COMPANY.getValue())) {

			if ((systemMaxNumberOfPages != 0) && (maxNumberOfPages != 0) &&
				(systemMaxNumberOfPages < maxNumberOfPages)) {

				throw new PDFPreviewException(systemMaxNumberOfPages);
			}

			_updateCompanyPDFPreviewConfiguration(scopePK, maxNumberOfPages);
		}
		else if (scope.equals(
					ExtendedObjectClassDefinition.Scope.GROUP.getValue())) {

			if ((systemMaxNumberOfPages != 0) && (maxNumberOfPages != 0) &&
				(systemMaxNumberOfPages < maxNumberOfPages)) {

				throw new PDFPreviewException(systemMaxNumberOfPages);
			}

			Group group = _groupLocalService.getGroup(scopePK);

			long companyMaxNumberOfPages = _getCompanyMaxNumberOfPages(
				group.getCompanyId());

			if ((companyMaxNumberOfPages != 0) && (maxNumberOfPages != 0) &&
				(companyMaxNumberOfPages < maxNumberOfPages)) {

				throw new PDFPreviewException(companyMaxNumberOfPages);
			}

			_updateGroupPDFPreviewConfiguration(scopePK, maxNumberOfPages);
		}
		else if (scope.equals(
					ExtendedObjectClassDefinition.Scope.SYSTEM.getValue())) {

			_updateSystemPDFPreviewConfiguration(maxNumberOfPages);
		}
		else {
			throw new PortalException("Unsupported scope: " + scope);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_systemPDFPreviewConfiguration = ConfigurableUtil.createConfigurable(
			PDFPreviewConfiguration.class, properties);
	}

	private long _getCompanyMaxNumberOfPages(long companyId) {
		PDFPreviewConfiguration pdfPreviewConfiguration =
			_getCompanyPDFPreviewConfiguration(companyId);

		return pdfPreviewConfiguration.maxNumberOfPages();
	}

	private PDFPreviewConfiguration _getCompanyPDFPreviewConfiguration(
		long companyId) {

		return _getPDFPreviewConfiguration(
			companyId, _companyConfigurationBeans,
			() -> _systemPDFPreviewConfiguration);
	}

	private long _getGroupMaxNumberOfPages(long groupId) {
		PDFPreviewConfiguration pdfPreviewConfiguration =
			_getGroupPDFPreviewConfiguration(groupId);

		return pdfPreviewConfiguration.maxNumberOfPages();
	}

	private PDFPreviewConfiguration _getGroupPDFPreviewConfiguration(
		long groupId) {

		return _getPDFPreviewConfiguration(
			groupId, _groupConfigurationBeans,
			() -> {
				Group group = _groupLocalService.fetchGroup(groupId);

				long companyId = CompanyThreadLocal.getCompanyId();

				if (group != null) {
					companyId = group.getCompanyId();
				}

				return _getCompanyPDFPreviewConfiguration(companyId);
			});
	}

	private PDFPreviewConfiguration _getPDFPreviewConfiguration(
		long key, Map<Long, PDFPreviewConfiguration> configurationBeans,
		Supplier<PDFPreviewConfiguration> supplier) {

		if (configurationBeans.containsKey(key)) {
			return configurationBeans.get(key);
		}

		return supplier.get();
	}

	private Configuration _getScopedConfiguration(
			ExtendedObjectClassDefinition.Scope scope, long scopePK)
		throws Exception {

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			String.format(
				"(&(service.factoryPid=%s)(%s=%d))",
				PDFPreviewConfiguration.class.getName() + ".scoped",
				scope.getPropertyKey(), scopePK));

		if (configurations == null) {
			return null;
		}

		return configurations[0];
	}

	private long _getSystemMaxNumberOfPages() {
		return _systemPDFPreviewConfiguration.maxNumberOfPages();
	}

	private void _unmapPid(String pid) {
		if (_companyIds.containsKey(pid)) {
			long companyId = _companyIds.remove(pid);

			_companyConfigurationBeans.remove(companyId);
		}

		if (_groupIds.containsKey(pid)) {
			long groupId = _groupIds.remove(pid);

			_groupConfigurationBeans.remove(groupId);
		}
	}

	private void _updateCompanyConfiguration(
		long companyId, String pid, Dictionary<String, ?> dictionary) {

		_companyConfigurationBeans.put(
			companyId,
			ConfigurableUtil.createConfigurable(
				PDFPreviewConfiguration.class, dictionary));
		_companyIds.put(pid, companyId);
	}

	private void _updateCompanyPDFPreviewConfiguration(
			long companyId, long maxNumberOfPages)
		throws Exception {

		_updateScopedConfiguration(
			maxNumberOfPages, ExtendedObjectClassDefinition.Scope.COMPANY,
			companyId);
	}

	private void _updateGroupConfiguration(
		long groupId, String pid, Dictionary<String, ?> dictionary) {

		_groupConfigurationBeans.put(
			groupId,
			ConfigurableUtil.createConfigurable(
				PDFPreviewConfiguration.class, dictionary));
		_groupIds.put(pid, groupId);
	}

	private void _updateGroupPDFPreviewConfiguration(
			long groupId, long maxNumberOfPages)
		throws Exception {

		_updateScopedConfiguration(
			maxNumberOfPages, ExtendedObjectClassDefinition.Scope.GROUP,
			groupId);
	}

	private void _updateScopedConfiguration(
			long maxNumberOfPages, ExtendedObjectClassDefinition.Scope scope,
			long scopePK)
		throws Exception {

		Dictionary<String, Object> properties;
		Configuration configuration = _getScopedConfiguration(scope, scopePK);

		if (configuration == null) {
			configuration = _configurationAdmin.createFactoryConfiguration(
				PDFPreviewConfiguration.class.getName() + ".scoped",
				StringPool.QUESTION);

			properties = HashMapDictionaryBuilder.<String, Object>put(
				scope.getPropertyKey(), scopePK
			).build();
		}
		else {
			properties = configuration.getProperties();
		}

		properties.put("maxNumberOfPages", maxNumberOfPages);

		configuration.update(properties);
	}

	private void _updateSystemPDFPreviewConfiguration(long maxNumberOfPages)
		throws Exception {

		Configuration configuration = _configurationAdmin.getConfiguration(
			PDFPreviewConfiguration.class.getName(), StringPool.QUESTION);

		Dictionary<String, Object> properties = configuration.getProperties();

		if (properties == null) {
			properties = new HashMapDictionary<>();
		}

		properties.put("maxNumberOfPages", maxNumberOfPages);

		configuration.update(properties);
	}

	private final Map<Long, PDFPreviewConfiguration>
		_companyConfigurationBeans = new ConcurrentHashMap<>();
	private final Map<String, Long> _companyIds = new ConcurrentHashMap<>();

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	private final Map<Long, PDFPreviewConfiguration> _groupConfigurationBeans =
		new ConcurrentHashMap<>();
	private final Map<String, Long> _groupIds = new ConcurrentHashMap<>();

	@Reference
	private GroupLocalService _groupLocalService;

	private volatile PDFPreviewConfiguration _systemPDFPreviewConfiguration;

}
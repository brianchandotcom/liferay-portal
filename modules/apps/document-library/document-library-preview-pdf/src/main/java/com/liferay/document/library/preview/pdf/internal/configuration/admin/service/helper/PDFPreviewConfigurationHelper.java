/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.preview.pdf.internal.configuration.admin.service.helper;

import com.liferay.document.library.preview.pdf.exception.PDFPreviewException;
import com.liferay.document.library.preview.pdf.internal.configuration.PDFPreviewConfiguration;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Joao Victor Alves
 */
@Component(
	configurationPid = "com.liferay.document.library.preview.pdf.internal.configuration.PDFPreviewConfiguration",
	service = PDFPreviewConfigurationHelper.class
)
public class PDFPreviewConfigurationHelper {

	public int getMaxLimitOfPages(String scope, long scopePK)
		throws PortalException {

		if (scope.equals(
				ExtendedObjectClassDefinition.Scope.SYSTEM.getValue())) {

			return 0;
		}

		if (scope.equals(
				ExtendedObjectClassDefinition.Scope.COMPANY.getValue())) {

			return _getSystemMaxNumberOfPages();
		}

		if (scope.equals(
				ExtendedObjectClassDefinition.Scope.GROUP.getValue())) {

			Group group = _groupLocalService.fetchGroup(scopePK);

			int systemMaxNumberOfPages = _getSystemMaxNumberOfPages();

			if (group == null) {
				return systemMaxNumberOfPages;
			}

			int companyMaxNumberOfPages = _getCompanyMaxNumberOfPages(
				group.getCompanyId());

			if ((companyMaxNumberOfPages != 0) &&
				((systemMaxNumberOfPages == 0) ||
				 (companyMaxNumberOfPages < systemMaxNumberOfPages))) {

				return companyMaxNumberOfPages;
			}

			return systemMaxNumberOfPages;
		}

		throw new IllegalArgumentException("Unsupported scope: " + scope);
	}

	public int getMaxNumberOfPages(String scope, long scopePK)
		throws PortalException {

		if (scope.equals(
				ExtendedObjectClassDefinition.Scope.COMPANY.getValue())) {

			int companyMaxNumberOfPages = _getCompanyMaxNumberOfPages(scopePK);
			int systemMaxNumberOfPages = _getSystemMaxNumberOfPages();

			if ((companyMaxNumberOfPages != 0) &&
				((systemMaxNumberOfPages == 0) ||
				 (companyMaxNumberOfPages < systemMaxNumberOfPages))) {

				return companyMaxNumberOfPages;
			}

			return systemMaxNumberOfPages;
		}
		else if (scope.equals(
					ExtendedObjectClassDefinition.Scope.GROUP.getValue())) {

			int groupMaxNumberOfPages = _getGroupMaxNumberOfPages(scopePK);

			Group group = _groupLocalService.fetchGroup(scopePK);

			int systemMaxNumberOfPages = _getSystemMaxNumberOfPages();

			if (group == null) {
				return systemMaxNumberOfPages;
			}

			int companyMaxNumberOfPages = _getCompanyMaxNumberOfPages(
				group.getCompanyId());

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

	public void removeCompanyConfigurationBeans(long companyId) {
		_companyConfigurationBeans.remove(companyId);
	}

	public void removeGroupConfigurationBeans(long groupId) {
		_groupConfigurationBeans.remove(groupId);
	}

	public void updateCompanyConfigurationBeans(
		long companyId, PDFPreviewConfiguration pdfPreviewConfiguration) {

		_companyConfigurationBeans.put(companyId, pdfPreviewConfiguration);
	}

	public void updateGroupConfigurationBeans(
		long groupId, PDFPreviewConfiguration pdfPreviewConfiguration) {

		_groupConfigurationBeans.put(groupId, pdfPreviewConfiguration);
	}

	public void updatePDFPreview(
			int maxNumberOfPages, String scope, long scopePK)
		throws Exception {

		int systemMaxNumberOfPages = _getSystemMaxNumberOfPages();

		if (scope.equals(
				ExtendedObjectClassDefinition.Scope.COMPANY.getValue())) {

			if (_isMaxNumberOfPagesLimitExceeded(
					systemMaxNumberOfPages, maxNumberOfPages)) {

				throw new PDFPreviewException(systemMaxNumberOfPages);
			}

			_updateCompanyPDFPreviewConfiguration(scopePK, maxNumberOfPages);
		}
		else if (scope.equals(
					ExtendedObjectClassDefinition.Scope.GROUP.getValue())) {

			if (_isMaxNumberOfPagesLimitExceeded(
					systemMaxNumberOfPages, maxNumberOfPages)) {

				throw new PDFPreviewException(systemMaxNumberOfPages);
			}

			Group group = _groupLocalService.getGroup(scopePK);

			int companyMaxNumberOfPages = _getCompanyMaxNumberOfPages(
				group.getCompanyId());

			if (_isMaxNumberOfPagesLimitExceeded(
					companyMaxNumberOfPages, maxNumberOfPages)) {

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

	private int _getCompanyMaxNumberOfPages(long companyId) {
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

	private int _getGroupMaxNumberOfPages(long groupId) {
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

		PDFPreviewConfiguration pdfPreviewConfiguration =
			configurationBeans.get(key);

		if (pdfPreviewConfiguration != null) {
			return pdfPreviewConfiguration;
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

	private int _getSystemMaxNumberOfPages() {
		return _systemPDFPreviewConfiguration.maxNumberOfPages();
	}

	private boolean _isMaxNumberOfPagesLimitExceeded(int limit, int value) {
		if ((limit != 0) && ((value == 0) || (limit < value))) {
			return true;
		}

		return false;
	}

	private void _updateCompanyPDFPreviewConfiguration(
			long companyId, int maxNumberOfPages)
		throws Exception {

		_updateScopedConfiguration(
			maxNumberOfPages, ExtendedObjectClassDefinition.Scope.COMPANY,
			companyId);
	}

	private void _updateGroupPDFPreviewConfiguration(
			long groupId, int maxNumberOfPages)
		throws Exception {

		_updateScopedConfiguration(
			maxNumberOfPages, ExtendedObjectClassDefinition.Scope.GROUP,
			groupId);
	}

	private void _updateScopedConfiguration(
			int maxNumberOfPages, ExtendedObjectClassDefinition.Scope scope,
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

	private void _updateSystemPDFPreviewConfiguration(int maxNumberOfPages)
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

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	private final Map<Long, PDFPreviewConfiguration> _groupConfigurationBeans =
		new ConcurrentHashMap<>();

	@Reference
	private GroupLocalService _groupLocalService;

	private volatile PDFPreviewConfiguration _systemPDFPreviewConfiguration;

}
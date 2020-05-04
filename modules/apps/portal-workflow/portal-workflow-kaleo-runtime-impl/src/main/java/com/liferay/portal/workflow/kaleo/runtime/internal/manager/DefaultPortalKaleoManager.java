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

package com.liferay.portal.workflow.kaleo.runtime.internal.manager;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactory;
import com.liferay.portal.workflow.kaleo.runtime.internal.BaseKaleoBean;
import com.liferay.portal.workflow.kaleo.runtime.manager.PortalKaleoManager;

import java.io.InputStream;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = PortalKaleoManager.class)
public class DefaultPortalKaleoManager
	extends BaseKaleoBean implements PortalKaleoManager {

	@Override
	public void deleteKaleoData(long companyId) throws Exception {
		kaleoDefinitionLocalService.deleteCompanyKaleoDefinitions(companyId);

		kaleoLogLocalService.deleteCompanyKaleoLogs(companyId);
	}

	@Override
	public void deployDefaultDefinitionLink(String assetClassName)
		throws Exception {

		ActionableDynamicQuery actionableDynamicQuery =
			companyLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			(Company company) -> {
				try {
					long companyId = company.getCompanyId();

					User defaultUser = userLocalService.getDefaultUser(
						companyId);

					Group companyGroup = groupLocalService.getCompanyGroup(
						companyId);

					String definitionName = _DEFINITION_NAME;

					if (_definitionAssets.containsKey(assetClassName)) {
						definitionName = _definitionAssets.get(assetClassName);
					}

					ServiceContext serviceContext = new ServiceContext();

					serviceContext.setCompanyId(companyId);

					deployDefaultDefinitionLink(
						defaultUser, companyId, companyGroup, assetClassName,
						definitionName);
				}
				catch (Exception exception) {
					throw new SystemException(exception);
				}
			});

		actionableDynamicQuery.performActions();
	}

	@Override
	public void deployDefaultDefinitionLinks() throws Exception {
		ActionableDynamicQuery actionableDynamicQuery =
			companyLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property systemProperty = PropertyFactoryUtil.forName("system");

				dynamicQuery.add(systemProperty.eq(Boolean.FALSE));
			});
		actionableDynamicQuery.setPerformActionMethod(
			(Company company) -> {
				try {
					deployDefaultDefinitionLinks(company.getCompanyId());
				}
				catch (Exception exception) {
					throw new SystemException(exception);
				}
			});

		actionableDynamicQuery.performActions();
	}

	@Override
	public void deployDefaultDefinitionLinks(long companyId) throws Exception {
		User defaultUser = userLocalService.getDefaultUser(companyId);

		Group companyGroup = groupLocalService.getCompanyGroup(companyId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(companyId);

		for (Map.Entry<String, String> entry : _definitionAssets.entrySet()) {
			String assetClassName = entry.getKey();
			String definitionName = entry.getValue();

			deployDefaultDefinitionLink(
				defaultUser, companyId, companyGroup, assetClassName,
				definitionName);
		}
	}

	@Override
	public void deployDefaultDefinitions() throws Exception {
		ActionableDynamicQuery actionableDynamicQuery =
			companyLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			(Company company) -> {
				try {
					deployDefaultDefinitions(company.getCompanyId());
				}
				catch (Exception exception) {
					throw new SystemException(exception);
				}
			});

		actionableDynamicQuery.performActions();
	}

	@Override
	public void deployDefaultDefinitions(long companyId) throws Exception {
		for (Map.Entry<String, String> entry : _definitionFiles.entrySet()) {
			String definitionName = entry.getKey();

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);

			int kaleoDefinitionsCount =
				kaleoDefinitionLocalService.getKaleoDefinitionsCount(
					definitionName, serviceContext);

			if (kaleoDefinitionsCount > 0) {
				return;
			}

			Class<?> clazz = getClass();

			ClassLoader classLoader = clazz.getClassLoader();

			String fileName = entry.getValue();

			InputStream inputStream = classLoader.getResourceAsStream(fileName);

			if (inputStream == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Unable to find definition file for ",
							definitionName, " with file name ", fileName));
				}

				return;
			}

			User defaultUser = userLocalService.getDefaultUser(companyId);

			_workflowDefinitionManager.deployWorkflowDefinition(
				serviceContext.getCompanyId(), defaultUser.getUserId(),
				_getLocalizedTitle(companyId, definitionName), definitionName,
				FileUtil.getBytes(inputStream));
		}
	}

	@Override
	public void deployDefaultRoles() throws Exception {
		ActionableDynamicQuery actionableDynamicQuery =
			companyLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			(Company company) -> {
				try {
					deployDefaultRoles(company.getCompanyId());
				}
				catch (Exception exception) {
					throw new SystemException(exception);
				}
			});

		actionableDynamicQuery.performActions();
	}

	@Override
	public void deployDefaultRoles(long companyId) throws Exception {
		User defaultUser = userLocalService.getDefaultUser(companyId);

		for (Map.Entry<String, String> entry : _defaultRoles.entrySet()) {
			String name = entry.getKey();

			Role role = roleLocalService.fetchRole(companyId, name);

			if (role != null) {
				continue;
			}

			Map<Locale, String> descriptionMap = HashMapBuilder.put(
				LocaleUtil.getDefault(), entry.getValue()
			).build();

			roleLocalService.addRole(
				defaultUser.getUserId(), null, 0, name, null, descriptionMap,
				RoleConstants.TYPE_REGULAR, null, null);
		}
	}

	@Override
	public void deployKaleoDefaults() throws Exception {
		deployDefaultRoles();
		deployDefaultDefinitions();
		deployDefaultDefinitionLinks();
	}

	@Override
	public void deployKaleoDefaults(long companyId) throws Exception {
		deployDefaultRoles(companyId);
		deployDefaultDefinitions(companyId);
		deployDefaultDefinitionLinks(companyId);
	}

	public void setDefaultRoles(Map<String, String> defaultRoles) {
		_defaultRoles.putAll(defaultRoles);
	}

	public void setDefinitionAssets(Map<String, String> definitionAssets) {
		_definitionAssets.putAll(definitionAssets);
	}

	public void setDefinitionFiles(Map<String, String> definitionFiles) {
		_definitionFiles.putAll(definitionFiles);
	}

	protected void deployDefaultDefinitionLink(
			User defaultUser, long companyId, Group companyGroup,
			String assetClassName, String workflowDefinitionName)
		throws PortalException {

		WorkflowDefinitionLink workflowDefinitionLink =
			workflowDefinitionLinkLocalService.
				fetchDefaultWorkflowDefinitionLink(
					companyId, assetClassName, 0, 0);

		if (workflowDefinitionLink != null) {
			return;
		}

		List<WorkflowDefinition> workflowDefinitions =
			_workflowDefinitionManager.getActiveWorkflowDefinitions(
				companyId, workflowDefinitionName, 0, 20,
				workflowComparatorFactory.getDefinitionNameComparator(false));

		if (workflowDefinitions.isEmpty()) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No workflow definitions found for " +
						workflowDefinitionName);
			}

			return;
		}

		WorkflowDefinition workflowDefinition = workflowDefinitions.get(0);

		workflowDefinitionLinkLocalService.addWorkflowDefinitionLink(
			defaultUser.getUserId(), companyId, companyGroup.getGroupId(),
			assetClassName, 0, 0, workflowDefinition.getName(),
			workflowDefinition.getVersion());
	}

	@Reference
	protected CompanyLocalService companyLocalService;

	@Reference
	protected GroupLocalService groupLocalService;

	@Reference
	protected RoleLocalService roleLocalService;

	@Reference
	protected UserLocalService userLocalService;

	@Reference(target = "(proxy.bean=false)")
	protected WorkflowComparatorFactory workflowComparatorFactory;

	private String _getLocalizedTitle(long companyId, String definitionName)
		throws PortalException {

		if (!Objects.equals(_DEFINITION_NAME, definitionName)) {
			return LocalizationUtil.updateLocalization(
				StringPool.BLANK, "title", definitionName,
				LocaleUtil.toLanguageId(LocaleUtil.getDefault()));
		}

		LocalizedValuesMap localizedValuesMap = new LocalizedValuesMap();

		Group companyGroup = groupLocalService.getCompanyGroup(companyId);

		for (Locale availableLocale :
				LanguageUtil.getAvailableLocales(companyGroup.getGroupId())) {

			localizedValuesMap.put(
				availableLocale,
				_language.get(
					ResourceBundleUtil.getModuleAndPortalResourceBundle(
						availableLocale, DefaultPortalKaleoManager.class),
					_DEFINITION_KEY));
		}

		return LocalizationUtil.getXml(localizedValuesMap, "title");
	}

	private static final String _DEFINITION_KEY = "single-approver";

	private static final String _DEFINITION_NAME = "Single Approver";

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultPortalKaleoManager.class);

	private final Map<String, String> _defaultRoles = new HashMap<>();
	private final Map<String, String> _definitionAssets = new HashMap<>();
	private final Map<String, String> _definitionFiles = HashMapBuilder.put(
		_DEFINITION_NAME, "META-INF/definitions/single-approver-definition.xml"
	).build();

	@Reference
	private Language _language;

	@Reference(target = "(proxy.bean=false)")
	private WorkflowDefinitionManager _workflowDefinitionManager;

}
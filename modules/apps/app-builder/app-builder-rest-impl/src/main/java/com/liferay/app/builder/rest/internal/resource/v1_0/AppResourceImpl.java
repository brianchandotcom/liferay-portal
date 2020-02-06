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

package com.liferay.app.builder.rest.internal.resource.v1_0;

import com.liferay.app.builder.constants.AppBuilderAppConstants;
import com.liferay.app.builder.deploy.AppDeployer;
import com.liferay.app.builder.deploy.AppDeployerTracker;
import com.liferay.app.builder.exception.AppBuilderAppStatusException;
import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.model.AppBuilderAppDeployment;
import com.liferay.app.builder.rest.constant.v1_0.DeploymentAction;
import com.liferay.app.builder.rest.dto.v1_0.App;
import com.liferay.app.builder.rest.dto.v1_0.AppDeployment;
import com.liferay.app.builder.rest.internal.constants.AppBuilderActionKeys;
import com.liferay.app.builder.rest.internal.jaxrs.exception.InvalidAppException;
import com.liferay.app.builder.rest.internal.jaxrs.exception.NoSuchDataListViewException;
import com.liferay.app.builder.rest.internal.odata.entity.v1_0.AppBuilderAppEntityModel;
import com.liferay.app.builder.rest.internal.resource.v1_0.util.LocalizedValueUtil;
import com.liferay.app.builder.rest.resource.v1_0.AppResource;
import com.liferay.app.builder.service.AppBuilderAppDeploymentLocalService;
import com.liferay.app.builder.service.AppBuilderAppLocalService;
import com.liferay.app.builder.util.comparator.AppBuilderAppCreateDateComparator;
import com.liferay.app.builder.util.comparator.AppBuilderAppModifiedDateComparator;
import com.liferay.app.builder.util.comparator.AppBuilderAppNameComparator;
import com.liferay.data.engine.model.DEDataListView;
import com.liferay.data.engine.service.DEDataListViewLocalService;
import com.liferay.dynamic.data.mapping.exception.NoSuchStructureLayoutException;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/app.properties",
	scope = ServiceScope.PROTOTYPE, service = AppResource.class
)
public class AppResourceImpl
	extends BaseAppResourceImpl implements EntityModelResource {

	@Override
	public void deleteApp(Long appId) throws Exception {
		_checkPermissions(appId, ActionKeys.DELETE);

		_appBuilderAppLocalService.deleteAppBuilderApp(appId);
	}

	@Override
	public App getApp(Long appId) throws Exception {
		_checkPermissions(appId, ActionKeys.VIEW);

		return _toApp(_appBuilderAppLocalService.getAppBuilderApp(appId));
	}

	@Override
	public Page<App> getDataDefinitionAppsPage(
			Long dataDefinitionId, String keywords, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		if (pagination.getPageSize() > 250) {
			throw new BadRequestException(
				LanguageUtil.format(
					contextAcceptLanguage.getPreferredLocale(),
					"page-size-is-greater-than-x", 250));
		}

		if (ArrayUtil.isEmpty(sorts)) {
			sorts = new Sort[] {
				new Sort(
					Field.getSortableFieldName(Field.MODIFIED_DATE),
					Sort.STRING_TYPE, true)
			};
		}

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		if (Validator.isNull(keywords)) {
			return Page.of(
				transform(
					_appBuilderAppLocalService.getAppBuilderApps(
						ddmStructure.getGroupId(),
						contextCompany.getCompanyId(),
						ddmStructure.getStructureId(),
						pagination.getStartPosition(),
						pagination.getEndPosition(),
						_toOrderByComparator(
							(Sort)ArrayUtil.getValue(sorts, 0))),
					this::_toApp),
				pagination,
				_appBuilderAppLocalService.getAppBuilderAppsCount(
					ddmStructure.getGroupId(), contextCompany.getCompanyId(),
					ddmStructure.getStructureId()));
		}

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> {
			},
			null, AppBuilderApp.class, keywords, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute(
					Field.CLASS_NAME_ID,
					_portal.getClassNameId(AppBuilderApp.class));
				searchContext.setAttribute(Field.NAME, keywords);
				searchContext.setAttribute("ddmStructureId", dataDefinitionId);
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setGroupIds(
					new long[] {ddmStructure.getGroupId()});
			},
			sorts,
			document -> _toApp(
				_appBuilderAppLocalService.getAppBuilderApp(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	@Override
	public Page<App> getSiteAppsPage(
			Long siteId, String keywords, Pagination pagination, Sort[] sorts)
		throws Exception {

		if (pagination.getPageSize() > 250) {
			throw new BadRequestException(
				LanguageUtil.format(
					contextAcceptLanguage.getPreferredLocale(),
					"page-size-is-greater-than-x", 250));
		}

		if (ArrayUtil.isEmpty(sorts)) {
			sorts = new Sort[] {
				new Sort(
					Field.getSortableFieldName(Field.MODIFIED_DATE),
					Sort.STRING_TYPE, true)
			};
		}

		if (Validator.isNull(keywords)) {
			return Page.of(
				transform(
					_appBuilderAppLocalService.getAppBuilderApps(
						siteId, pagination.getStartPosition(),
						pagination.getEndPosition(),
						_toOrderByComparator(
							(Sort)ArrayUtil.getValue(sorts, 0))),
					this::_toApp),
				pagination,
				_appBuilderAppLocalService.getAppBuilderAppsCount(siteId));
		}

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> {
			},
			null, AppBuilderApp.class, keywords, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute(
					Field.CLASS_NAME_ID,
					_portal.getClassNameId(AppBuilderApp.class));
				searchContext.setAttribute(Field.NAME, keywords);
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setGroupIds(new long[] {siteId});
			},
			sorts,
			document -> _toApp(
				_appBuilderAppLocalService.getAppBuilderApp(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	public App postDataDefinitionApp(Long dataDefinitionId, App app)
		throws Exception {

		_checkPortletPermissions();

		_validate(
			app.getDataLayoutId(), app.getDataListViewId(), app.getName(),
			app.getStatus());

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);
		AppBuilderAppConstants.Status appBuilderAppConstantsStatus =
			AppBuilderAppConstants.Status.parse(app.getStatus());

		AppBuilderApp appBuilderApp =
			_appBuilderAppLocalService.addAppBuilderApp(
				ddmStructure.getGroupId(), contextCompany.getCompanyId(),
				PrincipalThreadLocal.getUserId(), dataDefinitionId,
				GetterUtil.getLong(app.getDataLayoutId()),
				GetterUtil.getLong(app.getDataListViewId()),
				LocalizedValueUtil.toLocaleStringMap(app.getName()),
				appBuilderAppConstantsStatus.getValue());

		app.setId(appBuilderApp.getAppBuilderAppId());

		for (AppDeployment appDeployment : app.getAppDeployments()) {
			_appBuilderAppDeploymentLocalService.addAppBuilderAppDeployment(
				app.getId(), _toJSONString(appDeployment.getSettings()),
				appDeployment.getType());

			AppDeployer appDeployer = _appDeployerTracker.getAppDeployer(
				appDeployment.getType());

			appDeployer.deploy(app.getId());
		}

		_resourceLocalService.addResources(
			contextCompany.getCompanyId(), contextCompany.getGroupId(),
			contextUser.getUserId(), AppBuilderApp.class.getName(),
			appBuilderApp.getPrimaryKey(), false, false, false);

		return _toApp(appBuilderApp);
	}

	@Override
	public App putApp(Long appId, App app) throws Exception {
		_checkPermissions(appId, ActionKeys.UPDATE);

		_validate(
			app.getDataLayoutId(), app.getDataListViewId(), app.getName(),
			app.getStatus());

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			app.getDataDefinitionId());
		AppBuilderAppConstants.Status appBuilderAppConstantsStatus =
			AppBuilderAppConstants.Status.parse(app.getStatus());

		AppBuilderApp appBuilderApp =
			_appBuilderAppLocalService.updateAppBuilderApp(
				PrincipalThreadLocal.getUserId(), appId,
				ddmStructure.getStructureId(),
				GetterUtil.getLong(app.getDataLayoutId()),
				GetterUtil.getLong(app.getDataListViewId()),
				LocalizedValueUtil.toLocaleStringMap(app.getName()),
				appBuilderAppConstantsStatus.getValue());

		List<AppBuilderAppDeployment> appBuilderAppDeployments =
			_appBuilderAppDeploymentLocalService.getAppBuilderAppDeployments(
				appId);

		for (AppBuilderAppDeployment appBuilderAppDeployment :
				appBuilderAppDeployments) {

			_appBuilderAppDeploymentLocalService.deleteAppBuilderAppDeployment(
				appBuilderAppDeployment.getAppBuilderAppDeploymentId());
		}

		for (AppDeployment appDeployment : app.getAppDeployments()) {
			_appBuilderAppDeploymentLocalService.addAppBuilderAppDeployment(
				appId, _toJSONString(appDeployment.getSettings()),
				appDeployment.getType());

			AppDeployer appDeployer = _appDeployerTracker.getAppDeployer(
				appDeployment.getType());

			if ((appDeployer != null) &&
				Objects.equals(
					appBuilderAppConstantsStatus,
					AppBuilderAppConstants.Status.DEPLOYED)) {

				appDeployer.deploy(appId);
			}
		}

		return _toApp(appBuilderApp);
	}

	@Override
	public Response putAppDeployment(
			Long appId, DeploymentAction deploymentAction)
		throws Exception {

		_checkPermissions(appId, ActionKeys.UPDATE);

		List<AppBuilderAppDeployment> appBuilderAppDeployments =
			_appBuilderAppDeploymentLocalService.getAppBuilderAppDeployments(
				appId);

		for (AppBuilderAppDeployment appBuilderAppDeployment :
				appBuilderAppDeployments) {

			AppDeployer appDeployer = _appDeployerTracker.getAppDeployer(
				appBuilderAppDeployment.getType());

			if (appDeployer != null) {
				if (deploymentAction.equals(DeploymentAction.DEPLOY)) {
					appDeployer.deploy(appId);
				}
				else {
					appDeployer.undeploy(appId);
				}
			}
		}

		Response.ResponseBuilder responseBuilder = Response.accepted();

		return responseBuilder.build();
	}

	private void _checkPermissions(long appId, String actionId)
		throws PortalException {

		if (_portletResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(),
				contextCompany.getGroupId(), ActionKeys.MANAGE)) {

			return;
		}

		_modelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), appId, actionId);
	}

	private void _checkPortletPermissions() throws PortalException {
		if (_portletResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(),
				contextCompany.getGroupId(), ActionKeys.MANAGE)) {

			return;
		}

		_portletResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			contextCompany.getGroupId(), AppBuilderActionKeys.ADD_APP);
	}

	@Reference(
		target = "(model.class.name=com.liferay.app.builder.model.AppBuilderApp)",
		unbind = "-"
	)
	private void _setModelResourcePermission(
		ModelResourcePermission<AppBuilderApp> modelResourcePermission) {

		_modelResourcePermission = modelResourcePermission;
	}

	private App _toApp(AppBuilderApp appBuilderApp) throws Exception {
		AppBuilderAppConstants.Status appBuilderAppConstantsStatus =
			AppBuilderAppConstants.Status.parse(appBuilderApp.getStatus());

		return new App() {
			{
				appDeployments = transformToArray(
					_appBuilderAppDeploymentLocalService.
						getAppBuilderAppDeployments(
							appBuilderApp.getAppBuilderAppId()),
					appBuilderAppDeployment -> new AppDeployment() {
						{
							settings = _toSettings(
								appBuilderAppDeployment.getSettings());
							type = appBuilderAppDeployment.getType();
						}
					},
					AppDeployment.class);
				dataDefinitionId = appBuilderApp.getDdmStructureId();
				dataLayoutId = appBuilderApp.getDdmStructureLayoutId();
				dataListViewId = appBuilderApp.getDeDataListViewId();
				dateCreated = appBuilderApp.getCreateDate();
				dateModified = appBuilderApp.getModifiedDate();
				id = appBuilderApp.getAppBuilderAppId();
				name = LocalizedValueUtil.toStringObjectMap(
					appBuilderApp.getNameMap());
				siteId = appBuilderApp.getGroupId();
				status = appBuilderAppConstantsStatus.getLabel();
				userId = appBuilderApp.getUserId();
			}
		};
	}

	private String _toJSONString(Map<String, Object> map) {
		if (MapUtil.isEmpty(map)) {
			return StringPool.BLANK;
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		for (Map.Entry<String, Object> entry : map.entrySet()) {
			jsonObject.put(entry.getKey(), entry.getValue());
		}

		return jsonObject.toJSONString();
	}

	private OrderByComparator<AppBuilderApp> _toOrderByComparator(Sort sort) {
		boolean ascending = !sort.isReverse();

		String sortFieldName = sort.getFieldName();

		if (StringUtil.startsWith(sortFieldName, "createDate")) {
			return new AppBuilderAppCreateDateComparator(ascending);
		}
		else if (StringUtil.startsWith(sortFieldName, "localized_name")) {
			return new AppBuilderAppNameComparator(ascending);
		}

		return new AppBuilderAppModifiedDateComparator(ascending);
	}

	private Map<String, Object> _toSettings(String settings) throws Exception {
		return new HashMap<String, Object>() {
			{
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					settings);

				Set<String> keys = jsonObject.keySet();

				Iterator<String> iterator = keys.iterator();

				while (iterator.hasNext()) {
					String key = iterator.next();

					put(key, jsonObject.get(key));
				}
			}
		};
	}

	private void _validate(
			Long dataLayoutId, Long dataListViewId, Map<String, Object> name,
			String status)
		throws Exception {

		if ((dataLayoutId == null) && (dataListViewId == null)) {
			throw new InvalidAppException(
				"An app must have one data engine data layout or one data " +
					"engine data list view");
		}

		if (dataLayoutId != null) {
			DDMStructureLayout ddmStructureLayout =
				_ddmStructureLayoutLocalService.fetchStructureLayout(
					dataLayoutId);

			if (ddmStructureLayout == null) {
				throw new NoSuchStructureLayoutException(
					"Data layout " + dataLayoutId + " does not exist");
			}
		}

		if (dataListViewId != null) {
			DEDataListView deDataListView =
				_deDataListViewLocalService.fetchDEDataListView(dataListViewId);

			if (deDataListView == null) {
				throw new NoSuchDataListViewException(
					"Data list view " + dataListViewId + " does not exist");
			}
		}

		if (MapUtil.isNotEmpty(name)) {
			for (Object value : name.values()) {
				String localizedName = (String)value;

				if (Validator.isNull(localizedName)) {
					throw new InvalidAppException("The app name is null");
				}

				if (localizedName.length() > 30) {
					throw new InvalidAppException(
						"The app name has more than 30 characters");
				}

				Matcher matcher = _invalidAppNameCharsPattern.matcher(
					localizedName);

				if (matcher.matches()) {
					throw new InvalidAppException(
						"The app name must not contain special characters");
				}
			}
		}

		if (Validator.isNull(AppBuilderAppConstants.Status.parse(status))) {
			throw new AppBuilderAppStatusException("Invalid status " + status);
		}
	}

	private static final EntityModel _entityModel =
		new AppBuilderAppEntityModel();
	private static final Pattern _invalidAppNameCharsPattern = Pattern.compile(
		".*[$&+,:;=\\\\?@#|/'<>.^*()%!-].*");

	@Reference
	private AppBuilderAppDeploymentLocalService
		_appBuilderAppDeploymentLocalService;

	@Reference
	private AppBuilderAppLocalService _appBuilderAppLocalService;

	@Reference
	private AppDeployerTracker _appDeployerTracker;

	@Reference
	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DEDataListViewLocalService _deDataListViewLocalService;

	private ModelResourcePermission<AppBuilderApp> _modelResourcePermission;

	@Reference
	private Portal _portal;

	@Reference(target = "(resource.name=com.liferay.app.builder)")
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private ResourceLocalService _resourceLocalService;

}
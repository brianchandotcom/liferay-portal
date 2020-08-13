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
import com.liferay.app.builder.constants.AppBuilderConstants;
import com.liferay.app.builder.deploy.AppDeployer;
import com.liferay.app.builder.deploy.AppDeployerTracker;
import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.model.AppBuilderAppDeployment;
import com.liferay.app.builder.model.AppBuilderAppVersion;
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
import com.liferay.app.builder.service.AppBuilderAppVersionLocalService;
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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
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
import java.util.stream.Stream;

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
		_modelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), appId,
			ActionKeys.DELETE);

		_appBuilderAppLocalService.deleteAppBuilderApp(appId);
	}

	@Override
	public App getApp(Long appId) throws Exception {
		_modelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), appId,
			ActionKeys.VIEW);

		return _toApp(_appBuilderAppLocalService.getAppBuilderApp(appId));
	}

	@Override
	public Page<App> getAppsPage(
			Boolean active, String[] deploymentTypes, String keywords,
			String scope, Long[] userIds, Pagination pagination, Sort[] sorts)
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

		if (Objects.isNull(active) && ArrayUtil.isEmpty(deploymentTypes) &&
			Validator.isNull(keywords) && Validator.isNull(scope) &&
			ArrayUtil.isEmpty(userIds)) {

			return Page.of(
				transform(
					_appBuilderAppLocalService.getCompanyAppBuilderApps(
						contextCompany.getCompanyId(),
						pagination.getStartPosition(),
						pagination.getEndPosition(),
						_toOrderByComparator(sorts[0])),
					this::_toApp),
				pagination,
				_appBuilderAppLocalService.getCompanyAppBuilderAppsCount(
					contextCompany.getCompanyId()));
		}

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				if (Objects.nonNull(active)) {
					booleanFilter.add(
						new TermFilter("active", String.valueOf(active)),
						BooleanClauseOccur.MUST);
				}

				if (ArrayUtil.isNotEmpty(deploymentTypes)) {
					BooleanQuery deploymentTypesBooleanQuery =
						new BooleanQueryImpl();

					for (String deploymentType : deploymentTypes) {
						deploymentTypesBooleanQuery.addTerm(
							"deploymentTypes", deploymentType);
					}

					booleanFilter.add(
						new QueryFilter(deploymentTypesBooleanQuery),
						BooleanClauseOccur.MUST);
				}

				if (ArrayUtil.isNotEmpty(userIds)) {
					TermsFilter userIdTermsFilter = new TermsFilter("userId");

					userIdTermsFilter.addValues(
						Stream.of(
							userIds
						).map(
							String::valueOf
						).toArray(
							String[]::new
						));

					booleanFilter.add(
						userIdTermsFilter, BooleanClauseOccur.MUST);
				}

				if (Validator.isNotNull(scope)) {
					BooleanQuery scopeBooleanQuery = new BooleanQueryImpl();

					scopeBooleanQuery.addTerm("scope", scope);

					booleanFilter.add(
						new QueryFilter(scopeBooleanQuery),
						BooleanClauseOccur.MUST);
				}
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
			},
			sorts,
			document -> _toApp(
				_appBuilderAppLocalService.fetchAppBuilderApp(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	public Page<App> getDataDefinitionAppsPage(
			Long dataDefinitionId, String keywords, String scope,
			Pagination pagination, Sort[] sorts)
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

		if (Validator.isNull(keywords) && Validator.isNull(scope)) {
			return Page.of(
				transform(
					_appBuilderAppLocalService.getAppBuilderApps(
						ddmStructure.getGroupId(),
						contextCompany.getCompanyId(),
						ddmStructure.getStructureId(),
						pagination.getStartPosition(),
						pagination.getEndPosition(),
						_toOrderByComparator(sorts[0])),
					this::_toApp),
				pagination,
				_appBuilderAppLocalService.getAppBuilderAppsCount(
					ddmStructure.getGroupId(), contextCompany.getCompanyId(),
					ddmStructure.getStructureId()));
		}

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> {
				if (Validator.isNotNull(scope)) {
					BooleanFilter booleanFilter =
						booleanQuery.getPreBooleanFilter();

					BooleanQuery scopeBooleanQuery = new BooleanQueryImpl();

					scopeBooleanQuery.addTerm("scope", scope);

					booleanFilter.add(
						new QueryFilter(scopeBooleanQuery),
						BooleanClauseOccur.MUST);
				}
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
				_appBuilderAppLocalService.fetchAppBuilderApp(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	@Override
	public Page<App> getSiteAppsPage(
			Long siteId, String keywords, String scope, Pagination pagination,
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

		if (Validator.isNull(keywords) && Validator.isNull(scope)) {
			return Page.of(
				transform(
					_appBuilderAppLocalService.getAppBuilderApps(
						siteId, pagination.getStartPosition(),
						pagination.getEndPosition(),
						_toOrderByComparator(sorts[0])),
					this::_toApp),
				pagination,
				_appBuilderAppLocalService.getAppBuilderAppsCount(siteId));
		}

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> {
				if (Validator.isNotNull(scope)) {
					BooleanFilter booleanFilter =
						booleanQuery.getPreBooleanFilter();

					BooleanQuery scopeBooleanQuery = new BooleanQueryImpl();

					scopeBooleanQuery.addTerm("scope", scope);

					booleanFilter.add(
						new QueryFilter(scopeBooleanQuery),
						BooleanClauseOccur.MUST);
				}
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
				_appBuilderAppLocalService.fetchAppBuilderApp(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	public App postDataDefinitionApp(Long dataDefinitionId, App app)
		throws Exception {

		if (!_portletResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(),
				contextCompany.getGroupId(), ActionKeys.MANAGE)) {

			_portletResourcePermission.check(
				PermissionThreadLocal.getPermissionChecker(),
				contextCompany.getGroupId(), AppBuilderActionKeys.ADD_APP);
		}

		_validate(
			app.getActive(), app.getDataLayoutId(), app.getDataListViewId(),
			app.getName());

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		AppBuilderApp appBuilderApp = null;

		if (Objects.isNull(app.getDataRecordCollectionId())) {
			appBuilderApp = _appBuilderAppLocalService.addAppBuilderApp(
				ddmStructure.getGroupId(), contextCompany.getCompanyId(),
				PrincipalThreadLocal.getUserId(), app.getActive(),
				dataDefinitionId, GetterUtil.getLong(app.getDataLayoutId()),
				GetterUtil.getLong(app.getDataListViewId()),
				LocalizedValueUtil.toLocaleStringMap(app.getName()),
				GetterUtil.getString(
					app.getScope(), AppBuilderAppConstants.SCOPE_STANDARD));
		}
		else {
			appBuilderApp = _appBuilderAppLocalService.addAppBuilderApp(
				ddmStructure.getGroupId(), contextCompany.getCompanyId(),
				PrincipalThreadLocal.getUserId(), app.getActive(),
				app.getDataRecordCollectionId(), dataDefinitionId,
				GetterUtil.getLong(app.getDataLayoutId()),
				GetterUtil.getLong(app.getDataListViewId()),
				LocalizedValueUtil.toLocaleStringMap(app.getName()),
				GetterUtil.getString(
					app.getScope(), AppBuilderAppConstants.SCOPE_STANDARD));
		}

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
		_modelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), appId,
			ActionKeys.UPDATE);

		_validate(
			app.getActive(), app.getDataLayoutId(), app.getDataListViewId(),
			app.getName());

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			app.getDataDefinitionId());

		AppBuilderApp appBuilderApp =
			_appBuilderAppLocalService.updateAppBuilderApp(
				PrincipalThreadLocal.getUserId(), appId, app.getActive(),
				ddmStructure.getStructureId(),
				GetterUtil.getLong(app.getDataLayoutId()),
				GetterUtil.getLong(app.getDataListViewId()),
				LocalizedValueUtil.toLocaleStringMap(app.getName()));

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

			if ((appDeployer != null) && app.getActive()) {
				appDeployer.deploy(appId);
			}
		}

		return _toApp(appBuilderApp);
	}

	@Override
	public Response putAppDeploy(Long appId) throws Exception {
		_modelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), appId,
			ActionKeys.UPDATE);

		for (AppBuilderAppDeployment appBuilderAppDeployment :
				_appBuilderAppDeploymentLocalService.
					getAppBuilderAppDeployments(appId)) {

			AppDeployer appDeployer = _appDeployerTracker.getAppDeployer(
				appBuilderAppDeployment.getType());

			if (appDeployer != null) {
				appDeployer.deploy(appId);
			}
		}

		Response.ResponseBuilder responseBuilder = Response.accepted();

		return responseBuilder.build();
	}

	@Override
	public Response putAppUndeploy(Long appId) throws Exception {
		_modelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), appId,
			ActionKeys.UPDATE);

		for (AppBuilderAppDeployment appBuilderAppDeployment :
				_appBuilderAppDeploymentLocalService.
					getAppBuilderAppDeployments(appId)) {

			AppDeployer appDeployer = _appDeployerTracker.getAppDeployer(
				appBuilderAppDeployment.getType());

			if (appDeployer != null) {
				appDeployer.undeploy(appId);
			}
		}

		Response.ResponseBuilder responseBuilder = Response.accepted();

		return responseBuilder.build();
	}

	@Reference(
		target = "(model.class.name=com.liferay.app.builder.model.AppBuilderApp)",
		unbind = "-"
	)
	private void _setModelResourcePermission(
		ModelResourcePermission<AppBuilderApp> modelResourcePermission) {

		_modelResourcePermission = modelResourcePermission;
	}

	private App _toApp(AppBuilderApp appBuilderApp) {
		if (appBuilderApp == null) {
			return null;
		}

		return new App() {
			{
				active = appBuilderApp.isActive();
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
				dataRecordCollectionId = appBuilderApp.getDdlRecordSetId();
				dateCreated = appBuilderApp.getCreateDate();
				dateModified = appBuilderApp.getModifiedDate();
				id = appBuilderApp.getAppBuilderAppId();
				name = LocalizedValueUtil.toStringObjectMap(
					appBuilderApp.getNameMap());
				siteId = appBuilderApp.getGroupId();
				userId = appBuilderApp.getUserId();

				setDataDefinitionName(
					() -> {
						DDMStructure ddmStructure =
							_ddmStructureLocalService.getDDMStructure(
								appBuilderApp.getDdmStructureId());

						return ddmStructure.getName(
							contextAcceptLanguage.getPreferredLocale());
					});
				setVersion(
					() -> {
						AppBuilderAppVersion latestAppBuilderAppVersion =
							_appBuilderAppVersionLocalService.
								getLatestAppBuilderAppVersion(
									appBuilderApp.getAppBuilderAppId());

						return latestAppBuilderAppVersion.getVersion();
					});
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
			Boolean active, Long dataLayoutId, Long dataListViewId,
			Map<String, Object> name)
		throws Exception {

		if (Objects.isNull(active)) {
			throw new InvalidAppException("Active is null");
		}

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
			}
		}
	}

	private static final EntityModel _entityModel =
		new AppBuilderAppEntityModel();

	@Reference
	private AppBuilderAppDeploymentLocalService
		_appBuilderAppDeploymentLocalService;

	@Reference
	private AppBuilderAppLocalService _appBuilderAppLocalService;

	@Reference
	private AppBuilderAppVersionLocalService _appBuilderAppVersionLocalService;

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

	@Reference(
		target = "(resource.name=" + AppBuilderConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private ResourceLocalService _resourceLocalService;

}
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

import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.rest.dto.v1_0.App;
import com.liferay.app.builder.rest.internal.jaxrs.exception.NoSuchDataListViewException;
import com.liferay.app.builder.rest.internal.odata.entity.v1_0.AppBuilderAppEntityModel;
import com.liferay.app.builder.rest.internal.resource.v1_0.util.LocalizedValueUtil;
import com.liferay.app.builder.rest.resource.v1_0.AppResource;
import com.liferay.app.builder.service.AppBuilderAppLocalService;
import com.liferay.data.engine.rest.client.resource.v1_0.DataListViewResource;
import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;
import com.liferay.dynamic.data.mapping.exception.NoSuchStructureLayoutException;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MultivaluedMap;

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
		_appBuilderAppLocalService.deleteAppBuilderApp(appId);
	}

	@Override
	public App getApp(Long appId) throws Exception {
		return _toApp(_appBuilderAppLocalService.getAppBuilderApp(appId));
	}

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

		return SearchUtil.search(
			booleanQuery -> {
			},
			null, AppBuilderApp.class, keywords, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute("ddmStructureId", dataDefinitionId);
				searchContext.setAttribute(
					Field.CLASS_NAME_ID,
					_portal.getClassNameId(AppBuilderApp.class));
				searchContext.setAttribute(Field.NAME, keywords);
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setGroupIds(
					new long[] {ddmStructure.getGroupId()});
			},
			document -> _toApp(
				_appBuilderAppLocalService.getAppBuilderApp(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))),
			sorts);
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

		return SearchUtil.search(
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
			document -> _toApp(
				_appBuilderAppLocalService.getAppBuilderApp(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))),
			sorts);
	}

	@Override
	public App postDataDefinitionApp(Long dataDefinitionId, App app)
		throws Exception {

		_validate(
			dataDefinitionId, app.getDataLayoutId(), app.getDataListViewId());

		return _toApp(
			_appBuilderAppLocalService.addAppBuilderApp(
				app.getSiteId(), contextCompany.getCompanyId(), app.getUserId(),
				dataDefinitionId, app.getDataLayoutId(),
				app.getDataListViewId(),
				LocalizedValueUtil.toLocaleStringMap(app.getName()),
				_toJSON(app.getSettings())));
	}

	@Override
	public App putApp(Long appId, App app) throws Exception {
		_validate(
			app.getDataDefinitionId(), app.getDataLayoutId(),
			app.getDataListViewId());

		return _toApp(
			_appBuilderAppLocalService.updateAppBuilderApp(
				app.getUserId(), appId, app.getDataDefinitionId(),
				app.getDataLayoutId(), app.getDataListViewId(),
				LocalizedValueUtil.toLocaleStringMap(app.getName()),
				_toJSON(app.getSettings())));
	}

	private App _toApp(AppBuilderApp appBuilderApp) throws Exception {
		return new App() {
			{
				dataDefinitionId = appBuilderApp.getDdmStructureId();
				dataLayoutId = appBuilderApp.getDdmStructureLayoutId();
				dataListViewId = appBuilderApp.getDeDataListViewId();
				dateCreated = appBuilderApp.getCreateDate();
				dateModified = appBuilderApp.getModifiedDate();
				id = appBuilderApp.getAppBuilderAppId();
				name = LocalizedValueUtil.toStringObjectMap(
					appBuilderApp.getNameMap());
				settings = _toSettings(appBuilderApp.getSettings());
				siteId = appBuilderApp.getGroupId();
				userId = appBuilderApp.getUserId();
			}
		};
	}

	private String _toJSON(Map<String, Object> settings) {
		return JSONUtil.put(
			"deploymentStatus", settings.get("deploymentStatus")
		).put(
			"deploymentTypes", settings.get("deploymentTypes")
		).toString();
	}

	private Map<String, Object> _toSettings(String settings) throws Exception {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(settings);

		return new HashMap<String, Object>() {
			{
				put("deploymentStatus", jsonObject.get("deploymentStatus"));
				put(
					"deploymentTypes",
					JSONUtil.toObjectList(
						(JSONArray)jsonObject.get("deploymentTypes")));
			}
		};
	}

	private void _validate(
			long ddmStructureId, long ddmStructureLayoutId,
			long deDataListViewId)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.fetchStructure(
			ddmStructureId);

		if (ddmStructure == null) {
			throw new NoSuchStructureException(
				"Dynamic data mapping structure " + ddmStructureId +
					" does not exist");
		}

		DDMStructureLayout ddmStructureLayout =
			_ddmStructureLayoutLocalService.fetchStructureLayout(
				ddmStructureLayoutId);

		if (ddmStructureLayout == null) {
			throw new NoSuchStructureLayoutException(
				"Dynamic data mapping structure layout " +
					ddmStructureLayoutId + " does not exist");
		}

		String sessionId = CookieKeys.getCookie(
			contextHttpServletRequest, CookieKeys.JSESSIONID);

		DataListViewResource dataListViewResource =
			DataListViewResource.builder(
			).endpoint(
				_portal.getHost(contextHttpServletRequest),
				contextHttpServletRequest.getServerPort(),
				contextHttpServletRequest.getScheme()
			).header(
				"Cookie", "JSESSIONID=" + sessionId
			).parameter(
				"p_auth", AuthTokenUtil.getToken(contextHttpServletRequest)
			).build();

		try {
			dataListViewResource.getDataListView(deDataListViewId);
		}
		catch (Exception e) {
			throw new NoSuchDataListViewException(
				"Data engine data list view " + deDataListViewId +
					" does not exist",
				e);
		}
	}

	private static final EntityModel _entityModel =
		new AppBuilderAppEntityModel();

	@Reference
	private AppBuilderAppLocalService _appBuilderAppLocalService;

	@Reference
	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private Portal _portal;

}
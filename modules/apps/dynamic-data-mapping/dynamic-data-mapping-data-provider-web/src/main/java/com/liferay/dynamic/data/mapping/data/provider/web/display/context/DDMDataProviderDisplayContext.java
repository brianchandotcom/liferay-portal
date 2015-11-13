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

package com.liferay.dynamic.data.mapping.data.provider.web.display.context;

import com.liferay.dynamic.data.mapping.constants.DDMActionKeys;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderSettings;
import com.liferay.dynamic.data.mapping.data.provider.web.display.context.util.DDMDataProviderRequestHelper;
import com.liferay.dynamic.data.mapping.data.provider.web.search.DDMDataProviderSearchTerms;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceLocalService;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.dynamic.data.mapping.service.permission.DDMDataProviderInstancePermission;
import com.liferay.dynamic.data.mapping.service.permission.DDMPermission;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.util.PortalUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Leonardo Barros
 */
public class DDMDataProviderDisplayContext {

	public DDMDataProviderDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		DDMDataProviderInstanceService ddmDataProviderInstanceService,
		DDMDataProviderInstanceLocalService ddmDataProviderInstanceLocalService,
		DDMFormRenderer ddmFormRenderer,
		DDMFormValuesJSONDeserializer ddmFormValuesJSONDeserializer,
		UserLocalService userLocalService,
		Map<String, DDMDataProviderSettings> ddmDataProvidersMap) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_ddmDataProviderInstanceService = ddmDataProviderInstanceService;
		_ddmDataProviderInstanceLocalService =
			ddmDataProviderInstanceLocalService;
		_ddmFormRenderer = ddmFormRenderer;
		_ddmFormValuesJSONDeserializer = ddmFormValuesJSONDeserializer;
		_userLocalService = userLocalService;
		_ddmDataProvidersMap = ddmDataProvidersMap;

		_ddmDataProviderRequestHelper = new DDMDataProviderRequestHelper(
			renderRequest);
	}

	public DDMDataProviderInstance getDataProviderInstance()
		throws PortalException {

		if (_ddmDataProviderInstance != null) {
			return _ddmDataProviderInstance;
		}

		long dataProviderInstanceId = ParamUtil.getLong(
			_renderRequest, "dataProviderInstanceId");

		_ddmDataProviderInstance =
			_ddmDataProviderInstanceLocalService.fetchDDMDataProviderInstance(
				dataProviderInstanceId);

		return _ddmDataProviderInstance;
	}

	public String getDataProviderInstanceDefinition() throws PortalException {
		String dataProviderType = ParamUtil.getString(
			_renderRequest, "dataProviderType");

		DDMDataProviderSettings ddmDataProviderSettings =
			_ddmDataProvidersMap.get(dataProviderType);

		Class<?> clazz = ddmDataProviderSettings.getSettings();

		DDMForm ddmForm = DDMFormFactory.create(clazz);

		DDMFormRenderingContext ddmFormRenderingContext =
			createDDMFormRenderingContext();

		if (_ddmDataProviderInstance != null) {
			DDMFormValues ddmFormValues =
				_ddmFormValuesJSONDeserializer.deserialize(
					ddmForm, _ddmDataProviderInstance.getDefinition());

			ddmFormRenderingContext.setDDMFormValues(ddmFormValues);
		}

		return _ddmFormRenderer.render(ddmForm, ddmFormRenderingContext);
	}

	public Set<String> getDataProviderTypes() {
		return _ddmDataProvidersMap.keySet();
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view.jsp");
		portletURL.setParameter(
			"groupId",
			String.valueOf(_ddmDataProviderRequestHelper.getScopeGroupId()));

		return portletURL;
	}

	public List<DDMDataProviderInstance> getSearchContainerResults(
			SearchContainer<DDMDataProviderInstance> searchContainer)
		throws PortalException {

		DDMDataProviderSearchTerms searchTerms =
			(DDMDataProviderSearchTerms)searchContainer.getSearchTerms();

		if (searchTerms.isAdvancedSearch()) {
			return _ddmDataProviderInstanceService.search(
				_ddmDataProviderRequestHelper.getCompanyId(),
				new long[] {_ddmDataProviderRequestHelper.getScopeGroupId()},
				searchTerms.getName(), searchTerms.getDescription(),
				searchTerms.isAndOperator(), searchContainer.getStart(),
				searchContainer.getEnd(),
				searchContainer.getOrderByComparator());
		}
		else {
			return _ddmDataProviderInstanceService.search(
				_ddmDataProviderRequestHelper.getCompanyId(),
				new long[] {_ddmDataProviderRequestHelper.getScopeGroupId()},
				searchTerms.getKeywords(), searchContainer.getStart(),
				searchContainer.getEnd(),
				searchContainer.getOrderByComparator());
		}
	}

	public int getSearchContainerTotal(
			SearchContainer<DDMDataProviderInstance> searchContainer)
		throws PortalException {

		DDMDataProviderSearchTerms searchTerms =
			(DDMDataProviderSearchTerms)searchContainer.getSearchTerms();

		if (searchTerms.isAdvancedSearch()) {
			return _ddmDataProviderInstanceService.searchCount(
				_ddmDataProviderRequestHelper.getCompanyId(),
				new long[] {_ddmDataProviderRequestHelper.getScopeGroupId()},
				searchTerms.getName(), searchTerms.getDescription(),
				searchTerms.isAndOperator());
		}
		else {
			return _ddmDataProviderInstanceService.searchCount(
				_ddmDataProviderRequestHelper.getCompanyId(),
				new long[] {_ddmDataProviderRequestHelper.getScopeGroupId()},
				searchTerms.getKeywords());
		}
	}

	public String getUserPortraitURL(long userId) throws PortalException {
		User user = _userLocalService.getUser(userId);
		return user.getPortraitURL(
			_ddmDataProviderRequestHelper.getThemeDisplay());
	}

	public boolean isShowAddDataProviderButton() {
		return DDMPermission.contains(
			_ddmDataProviderRequestHelper.getPermissionChecker(),
			_ddmDataProviderRequestHelper.getScopeGroupId(),
			DDMActionKeys.ADD_DATA_PROVIDER_INSTANCE,
			DDMDataProviderInstance.class.getName());
	}

	public boolean isShowDeleteDataProviderIcon(
			DDMDataProviderInstance dataProviderInstance)
		throws PortalException {

		return DDMDataProviderInstancePermission.contains(
			_ddmDataProviderRequestHelper.getPermissionChecker(),
			dataProviderInstance, ActionKeys.DELETE);
	}

	public boolean isShowEditDataProviderIcon(
			DDMDataProviderInstance dataProviderInstance)
		throws PortalException {

		return DDMDataProviderInstancePermission.contains(
			_ddmDataProviderRequestHelper.getPermissionChecker(),
			dataProviderInstance, ActionKeys.UPDATE);
	}

	public boolean isShowPermissionsIcon(
		DDMDataProviderInstance dataProviderInstance) {

		return DDMDataProviderInstancePermission.contains(
			_ddmDataProviderRequestHelper.getPermissionChecker(),
			dataProviderInstance, ActionKeys.PERMISSIONS);
	}

	protected DDMFormRenderingContext createDDMFormRenderingContext() {
		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setHttpServletRequest(
			PortalUtil.getHttpServletRequest(_renderRequest));
		ddmFormRenderingContext.setHttpServletResponse(
			PortalUtil.getHttpServletResponse(_renderResponse));
		ddmFormRenderingContext.setLocale(
			_ddmDataProviderRequestHelper.getLocale());
		ddmFormRenderingContext.setPortletNamespace(
			_renderResponse.getNamespace());

		return ddmFormRenderingContext;
	}

	private DDMDataProviderInstance _ddmDataProviderInstance;
	private final DDMDataProviderInstanceLocalService
		_ddmDataProviderInstanceLocalService;
	private final DDMDataProviderInstanceService
		_ddmDataProviderInstanceService;
	private final DDMDataProviderRequestHelper _ddmDataProviderRequestHelper;
	private final Map<String, DDMDataProviderSettings> _ddmDataProvidersMap;
	private final DDMFormRenderer _ddmFormRenderer;
	private final DDMFormValuesJSONDeserializer _ddmFormValuesJSONDeserializer;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final UserLocalService _userLocalService;

}
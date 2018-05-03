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

package com.liferay.dynamic.data.mapping.web.internal.display.context;

import com.liferay.dynamic.data.mapping.configuration.DDMGroupServiceConfiguration;
import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.storage.StorageAdapterRegistry;
import com.liferay.dynamic.data.mapping.util.DDMDisplay;
import com.liferay.dynamic.data.mapping.util.DDMDisplayRegistry;
import com.liferay.dynamic.data.mapping.util.DDMDisplayTabItem;
import com.liferay.dynamic.data.mapping.util.DDMTemplateHelper;
import com.liferay.dynamic.data.mapping.web.configuration.DDMWebConfiguration;
import com.liferay.dynamic.data.mapping.web.internal.context.util.DDMWebRequestHelper;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rafael Praxedes
 */
public class DDMDisplayContext {

	public DDMDisplayContext(
			RenderRequest renderRequest, DDMDisplayRegistry ddmDisplayRegistry,
			DDMTemplateHelper ddmTemplateHelper,
			DDMWebConfiguration ddmWebConfiguration,
			StorageAdapterRegistry storageAdapterRegistry)
		throws PortalException {

		_renderRequest = renderRequest;
		_ddmDisplayRegistry = ddmDisplayRegistry;
		_ddmTemplateHelper = ddmTemplateHelper;
		_ddmWebConfiguration = ddmWebConfiguration;
		_storageAdapterRegistry = storageAdapterRegistry;

		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(renderRequest);

		_ddmWebRequestHelper = new DDMWebRequestHelper(httpServletRequest);
	}

	public boolean autogenerateStructureKey() {
		return _ddmWebConfiguration.autogenerateStructureKey();
	}

	public boolean autogenerateTemplateKey() {
		return _ddmWebConfiguration.autogenerateTemplateKey();
	}

	public boolean changeableDefaultLanguage() {
		return _ddmWebConfiguration.changeableDefaultLanguage();
	}

	public DDMStructure fetchStructure(DDMTemplate template) {
		return _ddmTemplateHelper.fetchStructure(template);
	}

	public String getAutocompleteJSON(
			HttpServletRequest request, String language)
		throws Exception {

		return _ddmTemplateHelper.getAutocompleteJSON(request, language);
	}

	public DDMDisplay getDDMDisplay(String portletId) {
		return _ddmDisplayRegistry.getDDMDisplay(portletId);
	}

	public DDMGroupServiceConfiguration getDDMGroupServiceConfiguration() {
		return _ddmWebRequestHelper.getDDMGroupServiceConfiguration();
	}

	public List<NavigationItem> getNavigationItem(String label) {
		return new NavigationItemList() {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(true);
						navigationItem.setHref(StringPool.BLANK);
						navigationItem.setLabel(label);
					});
			}
		};
	}

	public List<NavigationItem> getNavigationItems(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			final DDMDisplay ddmDisplay)
		throws Exception {

		return new NavigationItemList() {
			{
				for (DDMDisplayTabItem ddmDisplayTabItem :
						ddmDisplay.getTabItems()) {

					String ddmDisplayTabItemTitle = GetterUtil.getString(
						ddmDisplayTabItem.getTitle(
							liferayPortletRequest, liferayPortletResponse));

					DDMDisplayTabItem defaultDDMDisplayTabItem =
						ddmDisplay.getDefaultTabItem();

					String defaultDDMDisplayTabItemTitle = GetterUtil.getString(
						defaultDDMDisplayTabItem.getTitle(
							liferayPortletRequest, liferayPortletResponse));

					String ddmDisplayTabItemHref = GetterUtil.getString(
						ddmDisplayTabItem.getURL(
							liferayPortletRequest, liferayPortletResponse));

					add(
						navigationItem -> {
							navigationItem.setActive(
								Objects.equals(
									ddmDisplayTabItemTitle,
									defaultDDMDisplayTabItemTitle));
							navigationItem.setHref(ddmDisplayTabItemHref);
							navigationItem.setLabel(ddmDisplayTabItemTitle);
						});
				}
			}
		};
	}

	public String getOrderByCol() {
		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(_renderRequest);

		String orderByCol = ParamUtil.getString(_renderRequest, "orderByCol");

		if (Validator.isNull(orderByCol)) {
			orderByCol = portalPreferences.getValue(
				DDMPortletKeys.DYNAMIC_DATA_MAPPING, "entries-order-by-col",
				"modified-date");
		}
		else {
			portalPreferences.setValue(
				DDMPortletKeys.DYNAMIC_DATA_MAPPING, "entries-order-by-col",
				orderByCol);
		}

		return orderByCol;
	}

	public String getOrderByType() {
		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(_renderRequest);

		String orderByType = ParamUtil.getString(_renderRequest, "orderByType");

		if (Validator.isNull(orderByType)) {
			orderByType = portalPreferences.getValue(
				DDMPortletKeys.DYNAMIC_DATA_MAPPING, "entries-order-by-type",
				"asc");
		}
		else {
			portalPreferences.setValue(
				DDMPortletKeys.DYNAMIC_DATA_MAPPING, "entries-order-by-type",
				orderByType);
		}

		return orderByType;
	}

	public Set<String> getStorageTypes() {
		return _storageAdapterRegistry.getStorageTypes();
	}

	public boolean isAutocompleteEnabled(String language) {
		return _ddmTemplateHelper.isAutocompleteEnabled(language);
	}

	public String[] smallImageExtensions() {
		DDMGroupServiceConfiguration ddmGroupServiceConfiguration =
			_ddmWebRequestHelper.getDDMGroupServiceConfiguration();

		return ddmGroupServiceConfiguration.smallImageExtensions();
	}

	public int smallImageMaxSize() {
		DDMGroupServiceConfiguration ddmGroupServiceConfiguration =
			_ddmWebRequestHelper.getDDMGroupServiceConfiguration();

		return ddmGroupServiceConfiguration.smallImageMaxSize();
	}

	private final DDMDisplayRegistry _ddmDisplayRegistry;
	private final DDMTemplateHelper _ddmTemplateHelper;
	private final DDMWebConfiguration _ddmWebConfiguration;
	private final DDMWebRequestHelper _ddmWebRequestHelper;
	private final RenderRequest _renderRequest;
	private final StorageAdapterRegistry _storageAdapterRegistry;

}
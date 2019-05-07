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

package com.liferay.user.associated.data.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.user.associated.data.web.internal.constants.UADConstants;
import com.liferay.user.associated.data.web.internal.display.ViewUADEntitiesDisplay;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Drew Brokke
 */
public class ViewUADEntitiesManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public ViewUADEntitiesManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		HttpServletRequest httpServletRequest,
		ViewUADEntitiesDisplay viewUADEntitiesDisplay) {

		super(
			liferayPortletRequest, liferayPortletResponse, httpServletRequest,
			viewUADEntitiesDisplay.getSearchContainer());

		_viewUADEntitiesDisplay = viewUADEntitiesDisplay;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setHref(
							StringBundler.concat(
								"javascript:", getNamespace(),
								"doAnonymizeMultiple();"));
						dropdownItem.setLabel(
							LanguageUtil.get(request, "anonymize"));
					});

				add(
					dropdownItem -> {
						dropdownItem.setHref(
							StringBundler.concat(
								"javascript:", getNamespace(),
								"doDeleteMultiple();"));
						dropdownItem.setLabel(
							LanguageUtil.get(request, "delete"));
					});
			}
		};
	}

	@Override
	public String getClearResultsURL() {
		PortletURL portletURL = getPortletURL();

		portletURL.setParameter("keywords", (String)null);

		return portletURL.toString();
	}

	@Override
	public String getComponentId() {
		return StringBundler.concat(
			"viewUADEntitiesManagementToolbar", StringPool.UNDERLINE,
			StringUtil.randomId());
	}

	@Override
	public String getInfoPanelId() {
		return "infoPanelId";
	}

	@Override
	public String getSearchActionURL() {
		PortletURL portletURL = getPortletURL();

		return portletURL.toString();
	}

	@Override
	public Boolean isShowInfoButton() {
		String applicationKey = _viewUADEntitiesDisplay.getApplicationKey();

		if (applicationKey.equals(UADConstants.ALL_APPLICATIONS)) {
			return false;
		}

		return true;
	}

	@Override
	public Boolean isShowSearch() {
		return true;
	}

	@Override
	protected Map<String, String> getOrderByEntriesMap() {
		return searchContainer.getOrderableHeaders();
	}

	@Override
	protected PortletURL getPortletURL() {
		PortletURL portletURL = searchContainer.getIteratorURL();

		try {
			portletURL = PortletURLUtil.clone(
				portletURL, liferayPortletResponse);
		}
		catch (PortletException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe, pe);
			}

			portletURL = liferayPortletResponse.createRenderURL();

			portletURL.setParameters(portletURL.getParameterMap());
		}

		String[] parameterNames = {
			"keywords", "orderByCol", "orderByType", "cur", "delta"
		};

		for (String parameterName : parameterNames) {
			String value = ParamUtil.getString(request, parameterName);

			if (Validator.isNotNull(value)) {
				portletURL.setParameter(parameterName, (String)null);
				portletURL.setParameter(parameterName, value);
			}
		}

		return portletURL;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ViewUADEntitiesManagementToolbarDisplayContext.class);

	private final ViewUADEntitiesDisplay _viewUADEntitiesDisplay;

}
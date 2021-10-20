/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.web.internal.blueprint.admin.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Petteri Karttunen
 */
public abstract class BaseBlueprintManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public BaseBlueprintManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer<?> searchContainer, String displayStyle) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			searchContainer);

		this.displayStyle = displayStyle;

		themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public String getClearResultsURL() {
		return PortletURLBuilder.create(
			getPortletURL()
		).setMVCRenderCommandName(
			getMVCRenderCommandName()
		).setKeywords(
			StringPool.BLANK
		).setTabs1(
			ParamUtil.getString(liferayPortletRequest, "tabs1", "sxpBlueprints")
		).setParameter(
			"orderByCol", getOrderByCol()
		).setParameter(
			"orderByType", getOrderByCol()
		).buildString();
	}

	@Override
	public String getDefaultEventHandler() {
		return "BASE_BLUEPRINT_MANAGEMENT_TOOLBAR_DEFAULT_EVENT_HANDLER";
	}

	@Override
	public String getSearchActionURL() {
		return PortletURLBuilder.create(
			getPortletURL()
		).setMVCRenderCommandName(
			getMVCRenderCommandName()
		).setTabs1(
			ParamUtil.getString(liferayPortletRequest, "tabs1", "sxpBlueprints")
		).setParameter(
			"orderByCol", getOrderByCol()
		).setParameter(
			"orderByType", getOrderByCol()
		).buildString();
	}

	@Override
	public List<ViewTypeItem> getViewTypeItems() {
		PortletURL portletURL = PortletURLBuilder.createRenderURL(
			liferayPortletResponse
		).setMVCRenderCommandName(
			getMVCRenderCommandName()
		).setTabs1(
			ParamUtil.getString(liferayPortletRequest, "tabs1", "sxpBlueprints")
		).setParameter(
			"orderByCol", searchContainer.getOrderByCol()
		).setParameter(
			"orderByCol", searchContainer.getOrderByCol()
		).build();

		if (searchContainer.getDelta() > 0) {
			portletURL.setProperty(
				"delta", String.valueOf(searchContainer.getDelta()));
		}

		if (searchContainer.getCur() > 0) {
			portletURL.setProperty(
				"cur", String.valueOf(searchContainer.getCur()));
		}

		return new ViewTypeItemList(portletURL, displayStyle) {

			private static final long serialVersionUID = 1L;

			{
				addListViewTypeItem();

				addTableViewTypeItem();
			}
		};
	}

	protected String createActionURL(String actionName, String cmd) {
		return PortletURLBuilder.createActionURL(
			liferayPortletResponse
		).setActionName(
			actionName
		).setCMD(
			() -> {
				if (!Validator.isBlank(cmd)) {
					return Constants.ADD;
				}

				return null;
			}
		).setRedirect(
			currentURLObj
		).setTabs1(
			ParamUtil.getString(liferayPortletRequest, "tabs1", "sxpBlueprints")
		).buildString();
	}

	protected PortletURL getCurrentSortingURL() {
		PortletURL portletURL = PortletURLBuilder.create(
			getPortletURL()
		).setMVCRenderCommandName(
			getMVCRenderCommandName()
		).setTabs1(
			ParamUtil.getString(liferayPortletRequest, "tabs1", "sxpBlueprints")
		).setParameter(
			SearchContainer.DEFAULT_CUR_PARAM, "0"
		).build();

		String keywords = ParamUtil.getString(
			liferayPortletRequest, "keywords");

		if (!Validator.isBlank(keywords)) {
			portletURL.setProperty("keywords", keywords);
		}

		return portletURL;
	}

	protected abstract String getMVCRenderCommandName();

	@Override
	protected List<DropdownItem> getOrderByDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setActive(
					Objects.equals(getOrderByCol(), Field.TITLE));
				dropdownItem.setHref(
					getCurrentSortingURL(), "orderByCol", Field.TITLE);
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "title"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.setActive(
					Objects.equals(getOrderByCol(), Field.CREATE_DATE));
				dropdownItem.setHref(
					getCurrentSortingURL(), "orderByCol", Field.CREATE_DATE);
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "created"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.setActive(
					Objects.equals(getOrderByCol(), Field.MODIFIED_DATE));
				dropdownItem.setHref(
					getCurrentSortingURL(), "orderByCol", Field.MODIFIED_DATE);
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "modified"));
			}
		).build();
	}

	protected final String displayStyle;
	protected final ThemeDisplay themeDisplay;

}
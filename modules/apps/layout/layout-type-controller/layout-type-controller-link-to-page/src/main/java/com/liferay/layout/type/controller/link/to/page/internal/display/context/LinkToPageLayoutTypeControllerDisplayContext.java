/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.type.controller.link.to.page.internal.display.context;

import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.layout.item.selector.LayoutItemSelectorCriterion;
import com.liferay.layout.type.controller.link.to.page.internal.constants.LinkToPageLayoutTypeControllerWebKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.PortletURL;

/**
 * @author Pavel Savinov
 */
public class LinkToPageLayoutTypeControllerDisplayContext {

	public LinkToPageLayoutTypeControllerDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_setSelectedLayout();
	}

	public String getEventName() {
		return _liferayPortletResponse.getNamespace() + "selectLinkToPage";
	}

	public String getItemSelectorURL() throws Exception {
		ItemSelector itemSelector =
			(ItemSelector)_liferayPortletRequest.getAttribute(
				LinkToPageLayoutTypeControllerWebKeys.ITEM_SELECTOR);

		LayoutItemSelectorCriterion layoutItemSelectorCriterion =
			new LayoutItemSelectorCriterion();

		layoutItemSelectorCriterion.setCheckDisplayPage(false);
		layoutItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new UUIDItemSelectorReturnType());
		layoutItemSelectorCriterion.setEnableCurrentPage(false);
		layoutItemSelectorCriterion.setShowBreadcrumb(false);

		boolean privateLayout = ParamUtil.getBoolean(
			_liferayPortletRequest, "privateLayout");

		layoutItemSelectorCriterion.setShowPrivatePages(privateLayout);
		layoutItemSelectorCriterion.setShowPublicPages(!privateLayout);

		PortletURL itemSelectorURL = PortletURLBuilder.create(
			itemSelector.getItemSelectorURL(
				RequestBackedPortletURLFactoryUtil.create(
					_liferayPortletRequest),
				getEventName(), layoutItemSelectorCriterion)
		).setParameter(
			"layoutUuid", getLinkToLayoutUuid()
		).buildPortletURL();

		long selPlid = ParamUtil.getLong(_liferayPortletRequest, "selPlid");

		itemSelectorURL.setParameter("selPlid", String.valueOf(selPlid));

		return itemSelectorURL.toString();
	}

	public String getLinkToLayoutExternalReferenceCode() {
		if (_linkToLayoutExternalReferenceCode != null) {
			return _linkToLayoutExternalReferenceCode;
		}

		Layout layout = _getLayout();

		if (layout == null) {
			_linkToLayoutExternalReferenceCode = StringPool.BLANK;

			return _linkToLayoutExternalReferenceCode;
		}

		_linkToLayoutExternalReferenceCode = layout.getTypeSettingsProperty(
			"linkToLayoutExternalReferenceCode", StringPool.BLANK);

		return _linkToLayoutExternalReferenceCode;
	}

	public String getLinkToLayoutName() throws Exception {
		if (_selectedLayout != null) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_liferayPortletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			return _selectedLayout.getBreadcrumb(themeDisplay.getLocale());
		}

		return getLinkToLayoutExternalReferenceCode();
	}

	public String getLinkToLayoutUuid() {
		if (_selectedLayout != null) {
			return _selectedLayout.getUuid();
		}

		return ParamUtil.getString(_liferayPortletRequest, "layoutUuid");
	}

	private Layout _getLayout() {
		if (_layout != null) {
			return _layout;
		}

		_layout = (Layout)_liferayPortletRequest.getAttribute(
			WebKeys.SEL_LAYOUT);

		return _layout;
	}

	private void _setSelectedLayout() {
		Layout layout = _getLayout();

		if (layout != null) {
			String linkToLayoutExternalReferenceCode =
				layout.getTypeSettingsProperty(
					"linkToLayoutExternalReferenceCode");

			if (Validator.isNotNull(linkToLayoutExternalReferenceCode)) {
				_selectedLayout =
					LayoutLocalServiceUtil.fetchLayoutByExternalReferenceCode(
						linkToLayoutExternalReferenceCode, layout.getGroupId());

				return;
			}

			long linkToLayoutId = GetterUtil.getLong(
				layout.getTypeSettingsProperty("linkToLayoutId"));

			_selectedLayout = LayoutLocalServiceUtil.fetchLayout(
				layout.getGroupId(), layout.isPrivateLayout(), linkToLayoutId);
		}
	}

	private Layout _layout;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _linkToLayoutExternalReferenceCode;
	private Layout _selectedLayout;

}
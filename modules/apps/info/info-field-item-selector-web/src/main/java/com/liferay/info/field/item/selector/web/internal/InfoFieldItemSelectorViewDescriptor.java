/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.field.item.selector.web.internal;

import com.liferay.info.field.InfoField;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.item.selector.TableItemView;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class InfoFieldItemSelectorViewDescriptor
	implements ItemSelectorViewDescriptor<InfoField<?>> {

	public InfoFieldItemSelectorViewDescriptor(
		HttpServletRequest httpServletRequest,
		InfoItemServiceRegistry infoItemServiceRegistry,
		PortletURL portletURL) {

		_httpServletRequest = httpServletRequest;
		_infoItemServiceRegistry = infoItemServiceRegistry;
		_portletURL = portletURL;
	}

	@Override
	public String getDefaultDisplayStyle() {
		return "list";
	}

	@Override
	public String[] getDisplayViews() {
		return new String[0];
	}

	@Override
	public ItemSelectorViewDescriptor.ItemDescriptor getItemDescriptor(
		InfoField<?> infoField) {

		return new InfoFieldItemDescriptor(_httpServletRequest, infoField);
	}

	@Override
	public ItemSelectorReturnType getItemSelectorReturnType() {
		return new UUIDItemSelectorReturnType();
	}

	public SearchContainer<InfoField<?>> getSearchContainer()
		throws PortalException {

		PortletRequest portletRequest =
			(PortletRequest)_httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		SearchContainer<InfoField<?>> searchContainer = new SearchContainer<>(
			portletRequest, _portletURL, null, "there-are-no-info-fields");

		String itemType = ParamUtil.getString(_httpServletRequest, "itemType");

		InfoItemFormProvider<?> infoItemFormProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFormProvider.class, itemType);

		if (infoItemFormProvider == null) {
			return searchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		InfoForm infoForm = infoItemFormProvider.getInfoForm(
			itemType, themeDisplay.getScopeGroupId());

		List<InfoField<?>> infoFields = infoForm.getAllInfoFields();

		String keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		if (Validator.isNotNull(keywords)) {
			infoFields = ListUtil.filter(
				infoFields,
				infoField -> {
					String label = StringUtil.toLowerCase(
						infoField.getLabel(themeDisplay.getLocale()));

					return label.contains(StringUtil.toLowerCase(keywords));
				});
		}

		searchContainer.setResultsAndTotal(infoFields);

		return searchContainer;
	}

	@Override
	public TableItemView getTableItemView(InfoField<?> infoField) {
		return new InfoFieldTableItemView(infoField);
	}

	@Override
	public boolean isMultipleSelection() {
		return true;
	}

	@Override
	public boolean isShowBreadcrumb() {
		return false;
	}

	@Override
	public boolean isShowSearch() {
		return true;
	}

	private final HttpServletRequest _httpServletRequest;
	private final InfoItemServiceRegistry _infoItemServiceRegistry;
	private final PortletURL _portletURL;

}
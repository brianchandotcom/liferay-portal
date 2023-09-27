/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.web.internal.display.context;

import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.web.internal.display.context.util.CETLabelUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.SelectOption;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.PortletCategory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.WebAppPool;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class EditClientExtensionEntryDisplayContext<T extends CET> {

	public EditClientExtensionEntryDisplayContext(
		T cet, ClientExtensionEntry clientExtensionEntry,
		PortletRequest portletRequest) {

		_cet = cet;
		_clientExtensionEntry = clientExtensionEntry;
		_portletRequest = portletRequest;
	}

	public T getCET() {
		return _cet;
	}

	public String getCmd() {
		if (_clientExtensionEntry == null) {
			return Constants.ADD;
		}

		return Constants.UPDATE;
	}

	public String getDescription() {
		return BeanParamUtil.getString(
			_clientExtensionEntry, _portletRequest, "description");
	}

	public String getEditJSP() {
		return _cet.getEditJSP();
	}

	public String getExternalReferenceCode() {
		return BeanParamUtil.getString(
			_clientExtensionEntry, _portletRequest, "externalReferenceCode");
	}

	public String getHelpLabel() {
		ThemeDisplay themeDisplay = _getThemeDisplay();

		return CETLabelUtil.getHelpLabel(themeDisplay.getLocale(), getType());
	}

	public String getLearnResourceKey() {
		return CETLabelUtil.getLearnResourceKey(getType());
	}

	public String getName() {
		return BeanParamUtil.getString(
			_clientExtensionEntry, _portletRequest, "name");
	}

	public List<SelectOption> getPortletCategoryNameSelectOptions(
		String selectedPortletCategoryName) {

		List<SelectOption> selectOptions = new ArrayList<>();

		boolean found = false;

		if (Validator.isBlank(selectedPortletCategoryName)) {
			selectedPortletCategoryName = "category.client-extensions";
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletCategory rootPortletCategory = (PortletCategory)WebAppPool.get(
			themeDisplay.getCompanyId(), WebKeys.PORTLET_CATEGORY);

		for (PortletCategory portletCategory :
				rootPortletCategory.getCategories()) {

			selectOptions.add(
				new SelectOption(
					LanguageUtil.get(
						themeDisplay.getLocale(), portletCategory.getName()),
					portletCategory.getName(),
					selectedPortletCategoryName.equals(
						portletCategory.getName())));

			if (Objects.equals(
					portletCategory.getName(), "category.client-extensions")) {

				found = true;
			}
		}

		if (!found) {
			selectOptions.add(
				new SelectOption(
					LanguageUtil.get(
						themeDisplay.getLocale(), "category.client-extensions"),
					"category.client-extensions",
					Objects.equals(
						selectedPortletCategoryName,
						"category.client-extensions")));
		}

		return ListUtil.sort(
			selectOptions,
			new Comparator<SelectOption>() {

				@Override
				public int compare(
					SelectOption selectOption1, SelectOption selectOption2) {

					String label1 = selectOption1.getLabel();
					String label2 = selectOption2.getLabel();

					return label1.compareTo(label2);
				}

			});
	}

	public String getProperties() {
		return BeanParamUtil.getString(
			_clientExtensionEntry, _portletRequest, "properties");
	}

	public String getRedirect() {
		return ParamUtil.getString(_portletRequest, "redirect");
	}

	public String getSourceCodeURL() {
		return BeanParamUtil.getString(
			_clientExtensionEntry, _portletRequest, "sourceCodeURL");
	}

	public String[] getStrings(String urls) {
		String[] strings = StringUtil.split(urls, CharPool.NEW_LINE);

		if (strings.length == 0) {
			return _EMPTY_STRINGS;
		}

		return strings;
	}

	public String getTitle() {
		ThemeDisplay themeDisplay = _getThemeDisplay();

		if (_clientExtensionEntry == null) {
			return CETLabelUtil.getNewLabel(
				themeDisplay.getLocale(), _cet.getType());
		}

		return _cet.getName(themeDisplay.getLocale());
	}

	public String getType() {
		return BeanParamUtil.getString(
			_clientExtensionEntry, _portletRequest, "type");
	}

	public String getTypeLabel() {
		ThemeDisplay themeDisplay = _getThemeDisplay();

		return CETLabelUtil.getTypeLabel(themeDisplay.getLocale(), getType());
	}

	public boolean isNew() {
		if (_clientExtensionEntry == null) {
			return true;
		}

		return false;
	}

	public boolean isPropertiesVisible() {
		return _cet.hasProperties();
	}

	private HttpServletRequest _getHttpServletRequest() {
		return PortalUtil.getHttpServletRequest(_portletRequest);
	}

	private ThemeDisplay _getThemeDisplay() {
		HttpServletRequest httpServletRequest = _getHttpServletRequest();

		return (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	private static final String[] _EMPTY_STRINGS = {StringPool.BLANK};

	private final T _cet;
	private final ClientExtensionEntry _clientExtensionEntry;
	private final PortletRequest _portletRequest;

}
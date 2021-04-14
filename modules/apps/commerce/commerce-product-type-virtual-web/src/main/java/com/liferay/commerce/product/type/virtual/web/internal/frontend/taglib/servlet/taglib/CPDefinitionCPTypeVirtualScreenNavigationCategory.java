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

package com.liferay.commerce.product.type.virtual.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.portlet.action.ActionHelper;
import com.liferay.commerce.product.type.virtual.constants.VirtualCPTypeConstants;
import com.liferay.commerce.product.type.virtual.web.internal.display.context.CPDefinitionVirtualSettingDisplayContext;
import com.liferay.commerce.product.type.virtual.web.internal.portlet.action.CPDefinitionVirtualSettingActionHelper;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.item.selector.ItemSelector;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = {
		"screen.navigation.category.order:Integer=20",
		"screen.navigation.entry.order:Integer=20"
	},
	service = {ScreenNavigationCategory.class, ScreenNavigationEntry.class}
)
public class CPDefinitionCPTypeVirtualScreenNavigationCategory
	implements ScreenNavigationCategory, ScreenNavigationEntry<CPDefinition> {

	@Override
	public String getCategoryKey() {
		return VirtualCPTypeConstants.NAME;
	}

	@Override
	public String getEntryKey() {
		return VirtualCPTypeConstants.NAME;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "virtual");
	}

	@Override
	public String getScreenNavigationKey() {
		return "cp.definition.general";
	}

	@Override
	public boolean isVisible(User user, CPDefinition cpDefinition) {
		if (cpDefinition == null) {
			return false;
		}

		String productTypeName = cpDefinition.getProductTypeName();

		if (productTypeName.equals(VirtualCPTypeConstants.NAME)) {
			return true;
		}

		return false;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		CPDefinitionVirtualSettingDisplayContext
			cpDefinitionVirtualSettingDisplayContext =
				new CPDefinitionVirtualSettingDisplayContext(
					_actionHelper, httpServletRequest, _dlAppService,
					_journalArticleService,
					_cpDefinitionVirtualSettingActionHelper, _itemSelector);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			cpDefinitionVirtualSettingDisplayContext);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/edit_cp_definition_virtual_setting.jsp");
	}

	@Reference
	private ActionHelper _actionHelper;

	@Reference
	private CPDefinitionVirtualSettingActionHelper
		_cpDefinitionVirtualSettingActionHelper;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.product.type.virtual.web)"
	)
	private ServletContext _servletContext;

}
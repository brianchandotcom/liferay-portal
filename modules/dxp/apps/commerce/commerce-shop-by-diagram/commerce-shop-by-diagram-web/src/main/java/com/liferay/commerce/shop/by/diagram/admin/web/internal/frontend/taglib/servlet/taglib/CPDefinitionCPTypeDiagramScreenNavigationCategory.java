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

package com.liferay.commerce.shop.by.diagram.admin.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.portlet.action.ActionHelper;
import com.liferay.commerce.shop.by.diagram.admin.web.internal.display.context.CPDefinitionDiagramSettingDisplayContext;
import com.liferay.commerce.shop.by.diagram.admin.web.internal.product.type.DiagramCPType;
import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramSettingService;
import com.liferay.commerce.shop.by.diagram.type.CPDefinitionDiagramTypeRegistry;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
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
 * @author Andrea Sbarra
 */
@Component(
	enabled = false,
	property = {
		"screen.navigation.category.order:Integer=20",
		"screen.navigation.entry.order:Integer=20"
	},
	service = {ScreenNavigationCategory.class, ScreenNavigationEntry.class}
)
public class CPDefinitionCPTypeDiagramScreenNavigationCategory
	implements ScreenNavigationCategory, ScreenNavigationEntry<CPDefinition> {

	@Override
	public String getCategoryKey() {
		return DiagramCPType.NAME;
	}

	@Override
	public String getEntryKey() {
		return DiagramCPType.NAME;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, DiagramCPType.NAME);
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

		if (productTypeName.equals(DiagramCPType.NAME)) {
			return true;
		}

		return false;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		CPDefinitionDiagramSettingDisplayContext
			cpDefinitionDiagramSettingDisplayContext =
				new CPDefinitionDiagramSettingDisplayContext(
					_actionHelper, httpServletRequest,
					_cpDefinitionDiagramSettingService,
					_cpDefinitionDiagramTypeRegistry);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			cpDefinitionDiagramSettingDisplayContext);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/edit_cp_definition_diagram_setting.jsp");
	}

	@Reference
	private ActionHelper _actionHelper;

	@Reference
	private CPDefinitionDiagramSettingService
		_cpDefinitionDiagramSettingService;

	@Reference
	private CPDefinitionDiagramTypeRegistry _cpDefinitionDiagramTypeRegistry;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.shop.by.diagram.web)"
	)
	private ServletContext _servletContext;

}
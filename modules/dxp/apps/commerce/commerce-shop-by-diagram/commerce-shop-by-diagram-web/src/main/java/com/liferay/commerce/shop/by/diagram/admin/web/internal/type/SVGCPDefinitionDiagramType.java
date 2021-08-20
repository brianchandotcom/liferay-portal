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

package com.liferay.commerce.shop.by.diagram.admin.web.internal.type;

import com.liferay.commerce.product.portlet.action.ActionHelper;
import com.liferay.commerce.shop.by.diagram.admin.web.internal.display.context.CPDefinitionDiagramSettingDisplayContext;
import com.liferay.commerce.shop.by.diagram.configuration.CPDefinitionDiagramSettingImageConfiguration;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting;
import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramSettingService;
import com.liferay.commerce.shop.by.diagram.type.CPDefinitionDiagramType;
import com.liferay.commerce.shop.by.diagram.type.CPDefinitionDiagramTypeRegistry;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	configurationPid = "com.liferay.commerce.shop.by.diagram.configuration.CPDefinitionDiagramSettingImageConfiguration",
	enabled = false, immediate = true,
	property = {
		"commerce.product.definition.diagram.type.key=" + SVGCPDefinitionDiagramType.KEY,
		"commerce.product.definition.diagram.type.order:Integer=200"
	},
	service = CPDefinitionDiagramType.class
)
public class SVGCPDefinitionDiagramType implements CPDefinitionDiagramType {

	public static final String KEY = "diagram.type.svg";

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "svg");
	}

	@Override
	public void render(
			CPDefinitionDiagramSetting cpDefinitionDiagramSetting,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			new CPDefinitionDiagramSettingDisplayContext(
				_actionHelper, httpServletRequest,
				_cpDefinitionDiagramSettingImageConfiguration,
				_cpDefinitionDiagramSettingService,
				_cpDefinitionDiagramTypeRegistry, _dlURLHelper, _itemSelector));

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/diagram_type/svg.jsp");
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_cpDefinitionDiagramSettingImageConfiguration =
			ConfigurableUtil.createConfigurable(
				CPDefinitionDiagramSettingImageConfiguration.class, properties);
	}

	@Reference
	private ActionHelper _actionHelper;

	private volatile CPDefinitionDiagramSettingImageConfiguration
		_cpDefinitionDiagramSettingImageConfiguration;

	@Reference
	private CPDefinitionDiagramSettingService
		_cpDefinitionDiagramSettingService;

	@Reference
	private CPDefinitionDiagramTypeRegistry _cpDefinitionDiagramTypeRegistry;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.shop.by.diagram.web)"
	)
	private ServletContext _servletContext;

}
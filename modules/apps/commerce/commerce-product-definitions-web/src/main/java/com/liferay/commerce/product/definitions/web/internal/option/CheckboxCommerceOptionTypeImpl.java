/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.definitions.web.internal.option;

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.option.CommerceOptionType;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.ProductOption;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.template.react.renderer.ComponentDescriptor;
import com.liferay.portal.template.react.renderer.ReactRenderer;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.io.PrintWriter;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"commerce.option.type.display.order:Integer=700",
		"commerce.option.type.key=" + CheckboxCommerceOptionTypeImpl.KEY
	},
	service = CommerceOptionType.class
)
public class CheckboxCommerceOptionTypeImpl implements CommerceOptionType {

	public static final String KEY = "checkbox";

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "boolean");
	}

	@Override
	public boolean hasValues() {
		return false;
	}

	@Override
	public void render(
			CPDefinitionOptionRel cpDefinitionOptionRel,
			long defaultCPInstanceId, boolean forceRequired, String json,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		if (cpDefinitionOptionRel == null) {
			return;
		}

		PrintWriter printWriter = httpServletResponse.getWriter();

		printWriter.write("<div>");

		String moduleName = _npmResolver.resolveModuleName(
			"@liferay/commerce-product-definitions-web");

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_reactRenderer.renderReact(
			new ComponentDescriptor(moduleName + "/js/ProductOptionCheckbox"),
			HashMapBuilder.<String, Object>put(
				"componentId", StringUtil.randomId()
			).put(
				"forceRequired", forceRequired
			).put(
				"namespace", portletDisplay.getNamespace()
			).put(
				"productOption",
				_productOptionDTOConverter.toDTO(
					new DefaultDTOConverterContext(
						_dtoConverterRegistry,
						cpDefinitionOptionRel.getCPDefinitionOptionRelId(),
						_portal.getLocale(httpServletRequest), null,
						_portal.getUser(httpServletRequest)))
			).build(),
			httpServletRequest, printWriter);

		printWriter.write("</div>");
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private Language _language;

	@Reference
	private NPMResolver _npmResolver;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter.ProductOptionDTOConverter)"
	)
	private DTOConverter<CPDefinitionOptionRel, ProductOption>
		_productOptionDTOConverter;

	@Reference
	private ReactRenderer _reactRenderer;

}
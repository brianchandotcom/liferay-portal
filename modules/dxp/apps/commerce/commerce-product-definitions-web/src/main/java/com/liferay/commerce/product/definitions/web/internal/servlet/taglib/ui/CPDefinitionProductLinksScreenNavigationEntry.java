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

package com.liferay.commerce.product.definitions.web.internal.servlet.taglib.ui;

import com.liferay.commerce.product.configuration.CPDefinitionLinkTypeConfiguration;
import com.liferay.commerce.product.definitions.web.internal.display.context.CPDefinitionLinkDisplayContext;
import com.liferay.commerce.product.definitions.web.portlet.action.ActionHelper;
import com.liferay.commerce.product.definitions.web.servlet.taglib.ui.CPDefinitionScreenNavigationConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLinkService;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

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
 * @author Marco Leo
 */
@Component(
	configurationPid = "com.liferay.commerce.product.configuration.CPDefinitionLinkTypeConfiguration",
	property = "screen.navigation.entry.order:Integer=20",
	service = ScreenNavigationEntry.class
)
public class CPDefinitionProductLinksScreenNavigationEntry
	implements ScreenNavigationEntry<CPDefinition> {

	@Override
	public String getCategoryKey() {
		return CPDefinitionScreenNavigationConstants.
			CATEGORY_KEY_PRODUCT_RELATIONS;
	}

	@Override
	public String getEntryKey() {
		return _cpDefinitionLinkTypeConfiguration.type();
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(
			resourceBundle, _cpDefinitionLinkTypeConfiguration.type());
	}

	@Override
	public String getScreenNavigationKey() {
		return CPDefinitionScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_CP_DEFINITION_GENERAL;
	}

	@Override
	public boolean isVisible(User user, CPDefinition cpDefinition) {
		if (cpDefinition == null) {
			return false;
		}

		return true;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			httpServletRequest.setAttribute(
				"type", _cpDefinitionLinkTypeConfiguration.type());

			CPDefinitionLinkDisplayContext cpDefinitionLinkDisplayContext =
				new CPDefinitionLinkDisplayContext(
					_actionHelper, httpServletRequest, _cpDefinitionLinkService,
					_itemSelector, _cpDefinitionLinkTypeConfiguration.type());

			httpServletRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				cpDefinitionLinkDisplayContext);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		_jspRenderer.renderJSP(
			_setServletContext, httpServletRequest, httpServletResponse,
			"/definition_links.jsp");
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_cpDefinitionLinkTypeConfiguration =
			ConfigurableUtil.createConfigurable(
				CPDefinitionLinkTypeConfiguration.class, properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionProductLinksScreenNavigationEntry.class);

	@Reference
	private ActionHelper _actionHelper;

	@Reference
	private CPDefinitionLinkService _cpDefinitionLinkService;

	private volatile CPDefinitionLinkTypeConfiguration
		_cpDefinitionLinkTypeConfiguration;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.product.definitions.web)"
	)
	private ServletContext _setServletContext;

}
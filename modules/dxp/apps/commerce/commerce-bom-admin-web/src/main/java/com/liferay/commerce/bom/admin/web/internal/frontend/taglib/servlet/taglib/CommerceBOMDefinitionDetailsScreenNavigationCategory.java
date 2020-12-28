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

package com.liferay.commerce.bom.admin.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.commerce.bom.admin.web.internal.constants.CommerceBOMDefinitionScreenNavigationConstants;
import com.liferay.commerce.bom.admin.web.internal.display.context.CommerceBOMAdminDisplayContext;
import com.liferay.commerce.bom.constants.CommerceBOMActionKeys;
import com.liferay.commerce.bom.model.CommerceBOMDefinition;
import com.liferay.commerce.bom.model.CommerceBOMFolder;
import com.liferay.commerce.bom.service.CommerceBOMDefinitionService;
import com.liferay.commerce.bom.service.CommerceBOMFolderApplicationRelService;
import com.liferay.commerce.bom.service.CommerceBOMFolderService;
import com.liferay.commerce.product.configuration.AttachmentsConfiguration;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;
import java.util.Map;

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
	configurationPid = "com.liferay.commerce.product.configuration.AttachmentsConfiguration",
	enabled = false,
	property = {
		"screen.navigation.category.order:Integer=10",
		"screen.navigation.entry.order:Integer=10"
	},
	service = {ScreenNavigationCategory.class, ScreenNavigationEntry.class}
)
public class CommerceBOMDefinitionDetailsScreenNavigationCategory
	implements ScreenNavigationCategory,
			   ScreenNavigationEntry<CommerceBOMDefinition> {

	@Override
	public String getCategoryKey() {
		return CommerceBOMDefinitionScreenNavigationConstants.
			CATEGORY_KEY_DETAILS;
	}

	@Override
	public String getEntryKey() {
		return CommerceBOMDefinitionScreenNavigationConstants.ENTRY_KEY_DETAILS;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "details");
	}

	@Override
	public String getScreenNavigationKey() {
		return CommerceBOMDefinitionScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY;
	}

	@Override
	public boolean isVisible(
		User user, CommerceBOMDefinition commerceBOMDefinition) {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			if (commerceBOMDefinition == null) {
				PortletResourcePermission portletResourcePermission =
					_commerceBOMDefinitionModelResourcePermission.
						getPortletResourcePermission();

				return portletResourcePermission.contains(
					permissionChecker, null,
					CommerceBOMActionKeys.ADD_COMMERCE_BOM_DEFINITION);
			}

			return _commerceBOMDefinitionModelResourcePermission.contains(
				permissionChecker, commerceBOMDefinition, ActionKeys.UPDATE);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		return false;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		CommerceBOMAdminDisplayContext commerceBOMAdminDisplayContext =
			new CommerceBOMAdminDisplayContext(
				_attachmentsConfiguration,
				_commerceBOMDefinitionModelResourcePermission,
				_commerceBOMDefinitionService,
				_commerceBOMFolderApplicationRelService,
				_commerceBOMFolderModelResourcePermission,
				_commerceBOMFolderService, httpServletRequest, _itemSelector,
				null);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, commerceBOMAdminDisplayContext);

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse, "/definition/details.jsp");
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_attachmentsConfiguration = ConfigurableUtil.createConfigurable(
			AttachmentsConfiguration.class, properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceBOMDefinitionDetailsScreenNavigationCategory.class);

	private volatile AttachmentsConfiguration _attachmentsConfiguration;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.bom.model.CommerceBOMDefinition)"
	)
	private ModelResourcePermission<CommerceBOMDefinition>
		_commerceBOMDefinitionModelResourcePermission;

	@Reference
	private CommerceBOMDefinitionService _commerceBOMDefinitionService;

	@Reference
	private CommerceBOMFolderApplicationRelService
		_commerceBOMFolderApplicationRelService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.bom.model.CommerceBOMFolder)"
	)
	private ModelResourcePermission<CommerceBOMFolder>
		_commerceBOMFolderModelResourcePermission;

	@Reference
	private CommerceBOMFolderService _commerceBOMFolderService;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private JSPRenderer _jspRenderer;

}
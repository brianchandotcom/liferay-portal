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

package com.liferay.commerce.bom.admin.web.internal.portlet;

import com.liferay.commerce.bom.admin.web.internal.display.context.CommerceBOMAdminDisplayContext;
import com.liferay.commerce.bom.constants.CommerceBOMPortletKeys;
import com.liferay.commerce.bom.model.CommerceBOMDefinition;
import com.liferay.commerce.bom.model.CommerceBOMFolder;
import com.liferay.commerce.bom.service.CommerceBOMDefinitionService;
import com.liferay.commerce.bom.service.CommerceBOMFolderApplicationRelService;
import com.liferay.commerce.bom.service.CommerceBOMFolderService;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.users.admin.configuration.UserFileUploadsConfiguration;

import java.io.IOException;

import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	configurationPid = "com.liferay.user.admin.configuration.UserFileUploadsConfiguration",
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-commerce-bom-admin",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.layout-cacheable=false",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.preferences-unique-per-layout=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"javax.portlet.display-name=Commerce BOM Admin",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + CommerceBOMPortletKeys.COMMERCE_BOM_ADMIN,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = {CommerceBOMAdminPortlet.class, Portlet.class}
)
public class CommerceBOMAdminPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		CommerceBOMAdminDisplayContext commerceBOMAdminDisplayContext =
			new CommerceBOMAdminDisplayContext(
				null, _commerceBOMDefinitionModelResourcePermission,
				_commerceBOMDefinitionService,
				_commerceBOMFolderApplicationRelService,
				_commerceBOMFolderModelResourcePermission,
				_commerceBOMFolderService,
				_portal.getHttpServletRequest(renderRequest), _itemSelector,
				_userFileUploadsConfiguration);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, commerceBOMAdminDisplayContext);

		super.render(renderRequest, renderResponse);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_userFileUploadsConfiguration = ConfigurableUtil.createConfigurable(
			UserFileUploadsConfiguration.class, properties);
	}

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
	private Portal _portal;

	private volatile UserFileUploadsConfiguration _userFileUploadsConfiguration;

}
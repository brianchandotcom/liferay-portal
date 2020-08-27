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

package com.liferay.commerce.application.admin.web.internal.portlet;

import com.liferay.commerce.application.admin.web.internal.display.context.CommerceApplicationAdminDisplayContext;
import com.liferay.commerce.application.constants.CommerceApplicationPortletKeys;
import com.liferay.commerce.application.model.CommerceApplicationBrand;
import com.liferay.commerce.application.model.CommerceApplicationModel;
import com.liferay.commerce.application.service.CommerceApplicationBrandService;
import com.liferay.commerce.application.service.CommerceApplicationModelService;
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
		"com.liferay.portlet.css-class-wrapper=portlet-commerce-application-admin",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.layout-cacheable=false",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.preferences-unique-per-layout=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"javax.portlet.display-name=Commerce Application Admin",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + CommerceApplicationPortletKeys.COMMERCE_APPLICATION_ADMIN,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = {CommerceApplicationAdminPortlet.class, Portlet.class}
)
public class CommerceApplicationAdminPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		CommerceApplicationAdminDisplayContext
			commerceApplicationAdminDisplayContext =
				new CommerceApplicationAdminDisplayContext(
					_commerceApplicationBrandModelResourcePermission,
					_commerceApplicationBrandService,
					_commerceApplicationModelModelResourcePermission,
					_commerceApplicationModelService,
					_portal.getHttpServletRequest(renderRequest),
					_userFileUploadsConfiguration);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			commerceApplicationAdminDisplayContext);

		super.render(renderRequest, renderResponse);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_userFileUploadsConfiguration = ConfigurableUtil.createConfigurable(
			UserFileUploadsConfiguration.class, properties);
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.application.model.CommerceApplicationBrand)"
	)
	private ModelResourcePermission<CommerceApplicationBrand>
		_commerceApplicationBrandModelResourcePermission;

	@Reference
	private CommerceApplicationBrandService _commerceApplicationBrandService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.application.model.CommerceApplicationModel)"
	)
	private ModelResourcePermission<CommerceApplicationModel>
		_commerceApplicationModelModelResourcePermission;

	@Reference
	private CommerceApplicationModelService _commerceApplicationModelService;

	@Reference
	private Portal _portal;

	private volatile UserFileUploadsConfiguration _userFileUploadsConfiguration;

}
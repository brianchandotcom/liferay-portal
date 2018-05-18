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

package com.liferay.commerce.order.web.internal.portlet;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.order.CommerceOrderHelper;
import com.liferay.commerce.order.web.internal.display.context.CommerceOrderListDisplayContext;
import com.liferay.commerce.organization.service.CommerceOrganizationService;
import com.liferay.commerce.price.CommercePriceCalculation;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceOrderNoteService;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.messaging.proxy.ProxyModeThreadLocal;
import com.liferay.portal.kernel.messaging.proxy.ProxyModeThreadLocalCloseable;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-commerce-order",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.preferences-unique-per-layout=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"javax.portlet.display-name=Orders", "javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_ORDER,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = {CommerceOrderPortlet.class, Portlet.class}
)
public class CommerceOrderPortlet extends MVCPortlet {

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		try (ProxyModeThreadLocalCloseable proxyModeThreadLocalCloseable =
				new ProxyModeThreadLocalCloseable()) {

			ProxyModeThreadLocal.setForceSync(true);

			super.processAction(actionRequest, actionResponse);
		}
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			String mvcRenderCommandName = ParamUtil.getString(
				renderRequest, "mvcRenderCommandName");

			if (Validator.isNull(mvcRenderCommandName)) {
				CommerceOrderListDisplayContext
					commerceOrderListDisplayContext =
						new CommerceOrderListDisplayContext(
							_commerceOrderHelper, _commerceOrderLocalService,
							_commerceOrderNoteService,
							_commerceOrganizationService,
							_commercePriceCalculation, _groupLocalService,
							_jsonFactory, renderRequest);

				renderRequest.setAttribute(
					WebKeys.PORTLET_DISPLAY_CONTEXT,
					commerceOrderListDisplayContext);
			}

			super.render(renderRequest, renderResponse);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	@Reference
	private CommerceOrderHelper _commerceOrderHelper;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private CommerceOrderNoteService _commerceOrderNoteService;

	@Reference
	private CommerceOrganizationService _commerceOrganizationService;

	@Reference
	private CommercePriceCalculation _commercePriceCalculation;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

}
package com.liferay.commerce.product.web.internal.portlet.action;

import com.liferay.commerce.product.model.CommerceProductDefinition;
import com.liferay.commerce.product.web.internal.constants.CommerceProductPortletKeys;
import com.liferay.commerce.product.web.internal.constants.CommerceProductWebKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;

import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceProductPortletKeys.COMMERCE_PRODUCT_DEFINITIONS,
		"mvc.command.name=/commerce_product_definitions/info_panel"
	},
	service = MVCResourceCommand.class
)
public class CommerceProductDefinitionInfoPanelMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		List<CommerceProductDefinition> commerceProductDefinitions =
			ActionUtil.getCommerceProductDefinitions(resourceRequest);

		resourceRequest.setAttribute(
			CommerceProductWebKeys.COMMERCE_PRODUCT_DEFINITIONS,
			commerceProductDefinitions);

		include(
			resourceRequest, resourceResponse,
			"/commerce_product_definitions/info_panel.jsp");
	}

}
package com.liferay.commerce.product.options.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CommerceProductPortletKeys;
import com.liferay.commerce.product.constants.CommerceProductWebKeys;
import com.liferay.commerce.product.model.CommerceProductOption;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;

import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceProductPortletKeys.COMMERCE_PRODUCT_OPTIONS,
		"mvc.command.name=infoPanel"
	},
	service = MVCResourceCommand.class
)
public class CommerceProductOptionInfoPanelMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		List<CommerceProductOption> commerceProductOptions =
			_actionHelper.getCommerceProductOptions(resourceRequest);

		resourceRequest.setAttribute(
			CommerceProductWebKeys.COMMERCE_PRODUCT_OPTIONS,
			commerceProductOptions);

		include(resourceRequest, resourceResponse, "/info_panel.jsp");
	}

	@Reference
	private ActionHelper _actionHelper;

}
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

package com.liferay.commerce.discount.rule.purchased.any.internal;

import com.liferay.commerce.discount.model.CommerceDiscountRuleConstants;
import com.liferay.commerce.discount.rule.purchased.any.internal.display.context.PurchasedAnyCommerceDiscountRuleDisplayContext;
import com.liferay.commerce.discount.rule.type.CommerceDiscountRuleTypeJSPContributor;
import com.liferay.commerce.discount.service.CommerceDiscountRuleService;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.item.selector.ItemSelector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "commerce.discount.rule.type.jsp.contributor.key=" + CommerceDiscountRuleConstants.TYPE_PURCHASED_ANY
)
public class PurchasedAnyCommerceDiscountRuleTypeJSPContributor
	implements CommerceDiscountRuleTypeJSPContributor {

	@Override
	public void render(
			long commerceDiscountId, long commerceDiscountRuleId,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		PurchasedAnyCommerceDiscountRuleDisplayContext
			purchasedAnyCommerceDiscountRuleDisplayContext =
				new PurchasedAnyCommerceDiscountRuleDisplayContext(
					_commerceDiscountRuleService, _cpDefinitionService,
					httpServletRequest, _itemSelector);

		httpServletRequest.setAttribute(
			"view.jsp-purchasedAnyCommerceDiscountRuleDisplayContext",
			purchasedAnyCommerceDiscountRuleDisplayContext);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/view.jsp");
	}

	@Reference
	private CommerceDiscountRuleService _commerceDiscountRuleService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.discount.rule.purchased.any)"
	)
	private ServletContext _servletContext;

}
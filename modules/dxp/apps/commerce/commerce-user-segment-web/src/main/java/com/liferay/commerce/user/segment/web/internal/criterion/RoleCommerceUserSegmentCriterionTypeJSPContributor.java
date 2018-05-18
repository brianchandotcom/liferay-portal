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

package com.liferay.commerce.user.segment.web.internal.criterion;

import com.liferay.commerce.user.segment.criterion.CommerceUserSegmentCriterionTypeJSPContributor;
import com.liferay.commerce.user.segment.criterion.CommerceUserSegmentCriterionTypeJSPContributorRegistry;
import com.liferay.commerce.user.segment.criterion.CommerceUserSegmentCriterionTypeRegistry;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterionConstants;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentCriterionService;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentEntryService;
import com.liferay.commerce.user.segment.web.internal.display.context.RoleCommerceUserSegmentCriterionTypeDisplayContext;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.service.RoleLocalService;

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
	property = "commerce.user.segment.criterion.type.jsp.contributor.key=" + CommerceUserSegmentCriterionConstants.TYPE_ROLE
)
public class RoleCommerceUserSegmentCriterionTypeJSPContributor
	implements CommerceUserSegmentCriterionTypeJSPContributor {

	@Override
	public void render(
			long commerceUserSegmentEntryId,
			long commerceUserSegmentCriterionId,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		RoleCommerceUserSegmentCriterionTypeDisplayContext
			roleCommerceUserSegmentCriterionTypeDisplayContext =
				new RoleCommerceUserSegmentCriterionTypeDisplayContext(
					_commerceUserSegmentCriterionService,
					_commerceUserSegmentCriterionTypeJSPContributorRegistry,
					_commerceUserSegmentCriterionTypeRegistry,
					_commerceUserSegmentEntryService, httpServletRequest,
					_itemSelector, _roleLocalService);

		httpServletRequest.setAttribute(
			"role.jsp-portletDisplayContext",
			roleCommerceUserSegmentCriterionTypeDisplayContext);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/contributor/role.jsp");
	}

	@Reference
	private CommerceUserSegmentCriterionService
		_commerceUserSegmentCriterionService;

	@Reference
	private CommerceUserSegmentCriterionTypeJSPContributorRegistry
		_commerceUserSegmentCriterionTypeJSPContributorRegistry;

	@Reference
	private CommerceUserSegmentCriterionTypeRegistry
		_commerceUserSegmentCriterionTypeRegistry;

	@Reference
	private CommerceUserSegmentEntryService _commerceUserSegmentEntryService;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.user.segment.web)"
	)
	private ServletContext _servletContext;

}
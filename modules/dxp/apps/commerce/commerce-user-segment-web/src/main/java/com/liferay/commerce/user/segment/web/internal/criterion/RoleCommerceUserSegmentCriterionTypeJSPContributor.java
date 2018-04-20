/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.user.segment.web.internal.criterion;

import com.liferay.commerce.user.segment.constants.CommerceUserSegmentConstants;
import com.liferay.commerce.user.segment.criterion.CommerceUserSegmentCriterionTypeJSPContributor;
import com.liferay.commerce.user.segment.criterion.CommerceUserSegmentCriterionTypeJSPContributorRegistry;
import com.liferay.commerce.user.segment.criterion.CommerceUserSegmentCriterionTypeRegistry;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentCriterionService;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentEntryService;
import com.liferay.commerce.user.segment.web.internal.display.context.CommerceUserSegmentCriterionTypeRoleDisplayContext;
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
	property = "commerce.user.segment.criterion.type.jsp.contributor.key=" + CommerceUserSegmentConstants.CRITERION_TYPE_ROLE
)
public class RoleCommerceUserSegmentCriterionTypeJSPContributor
	implements CommerceUserSegmentCriterionTypeJSPContributor {

	public void render(
			long commerceUserSegmentEntryId,
			long commerceUserSegmentCriterionId,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		CommerceUserSegmentCriterionTypeRoleDisplayContext
			commerceUserSegmentCriterionTypeRoleDisplayContext =
				new CommerceUserSegmentCriterionTypeRoleDisplayContext(
					_commerceUserSegmentCriterionService,
					_commerceUserSegmentCriterionTypeJSPContributorRegistry,
					_commerceUserSegmentCriterionTypeRegistry,
					_commerceUserSegmentEntryService, httpServletRequest,
					_itemSelector, _roleLocalService);

		httpServletRequest.setAttribute(
			"role.jsp-portletDisplayContext",
			commerceUserSegmentCriterionTypeRoleDisplayContext);

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
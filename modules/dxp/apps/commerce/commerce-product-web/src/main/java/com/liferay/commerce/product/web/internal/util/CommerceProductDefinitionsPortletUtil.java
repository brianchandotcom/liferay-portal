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

package com.liferay.commerce.product.web.internal.util;

import com.liferay.commerce.product.model.CommerceProductDefinition;
import com.liferay.commerce.product.web.internal.util.comparator.CommerceProductDefinitionCreateDateComparator;
import com.liferay.commerce.product.web.internal.util.comparator.CommerceProductDefinitionDisplayDateComparator;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.PortletURL;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceProductDefinitionsPortletUtil {

	public static void addPortletBreadcrumbEntries(
			CommerceProductDefinition commerceProductDefinition, HttpServletRequest request,
			PortletURL portletURL)
			throws Exception {

		CommerceProductDefinition unescapedcommerceProductDefinition = commerceProductDefinition.toUnescapedModel();

		portletURL.setParameter("mvcPath", "/commerce_product_definitions/edit_product_definition.jsp");
		portletURL.setParameter(
				"groupId", String.valueOf(commerceProductDefinition.getGroupId()));
		portletURL.setParameter(
				"commerceProductDefinitionId", String.valueOf(commerceProductDefinition.getCommerceProductDefinitionId()));

		PortalUtil.addPortletBreadcrumbEntry(
				request, unescapedcommerceProductDefinition.getTitle(), portletURL.toString());
	}

	public static OrderByComparator<CommerceProductDefinition>
		getCommerceProductDefinitionOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceProductDefinition> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator =
				new CommerceProductDefinitionCreateDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("display-date")) {
			orderByComparator =
				new CommerceProductDefinitionDisplayDateComparator(orderByAsc);
		}

		return orderByComparator;
	}

}
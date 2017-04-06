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

package com.liferay.commerce.product.web.internal.portlet.action;

import com.liferay.commerce.product.model.CommerceProductDefinition;
import com.liferay.commerce.product.service.CommerceProductDefinitionLocalServiceUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ResourceRequest;

/**
 * @author Marco Leo
 */
public class ActionUtil {

	public static List<CommerceProductDefinition> getCommerceProductDefinitions(
			ResourceRequest request)
		throws Exception {

		long[] commerceProductDefinitionsIds = ParamUtil.getLongValues(
			request, "rowIdsCommerceProductDefinition");

		List<CommerceProductDefinition> commerceProductDefinitions =
			new ArrayList<>();

		for (long commerceProductDefinitionsId :
				commerceProductDefinitionsIds) {

			CommerceProductDefinition commerceProductDefinition =
				CommerceProductDefinitionLocalServiceUtil.
					getCommerceProductDefinition(commerceProductDefinitionsId);

			commerceProductDefinitions.add(commerceProductDefinition);
		}

		return commerceProductDefinitions;
	}

}
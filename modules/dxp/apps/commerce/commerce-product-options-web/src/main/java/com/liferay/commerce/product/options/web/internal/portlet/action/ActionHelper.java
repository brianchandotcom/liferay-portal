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

package com.liferay.commerce.product.options.web.internal.portlet.action;

import com.liferay.commerce.product.model.CommerceProductOption;
import com.liferay.commerce.product.service.CommerceProductOptionLocalService;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ResourceRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = ActionHelper.class)
public class ActionHelper {

	public List<CommerceProductOption> getCommerceProductOptions(
			ResourceRequest resourceRequest)
		throws Exception {

		long[] commerceProductOptionsIds = ParamUtil.getLongValues(
			resourceRequest, "rowIdsCommerceProductOption");

		List<CommerceProductOption> commerceProductOptions = new ArrayList<>();

		for (long commerceProductOptionsId : commerceProductOptionsIds) {
			CommerceProductOption commerceProductOption =
				_commerceProductOptionLocalService.getCommerceProductOption(
					commerceProductOptionsId);

			commerceProductOptions.add(commerceProductOption);
		}

		return commerceProductOptions;
	}

	@Reference
	private CommerceProductOptionLocalService
		_commerceProductOptionLocalService;

}
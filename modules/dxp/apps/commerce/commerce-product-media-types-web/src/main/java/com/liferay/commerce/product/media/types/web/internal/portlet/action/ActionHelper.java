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

package com.liferay.commerce.product.media.types.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPWebKeys;
import com.liferay.commerce.product.model.*;
import com.liferay.commerce.product.service.*;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.RenderRequest;
import javax.portlet.ResourceRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = ActionHelper.class)
public class ActionHelper {

	public CPMediaType getCPMediaType(RenderRequest renderRequest)
		throws PortalException {

		CPMediaType cpMediaType = (CPMediaType)renderRequest.getAttribute(
			CPWebKeys.COMMERCE_PRODUCT_MEDIA_TYPE);

		if (cpMediaType != null) {
			return cpMediaType;
		}

		long cpMediaTypeId = ParamUtil.getLong(renderRequest, "cpMediaTypeId");

		if (cpMediaTypeId > 0) {
			cpMediaType = _cpMediaTypeService.fetchCPMediaType(cpMediaTypeId);
		}

		if (cpMediaType != null) {
			renderRequest.setAttribute(
				CPWebKeys.COMMERCE_PRODUCT_MEDIA_TYPE, cpMediaType);
		}

		return cpMediaType;
	}

	public List<CPMediaType> getCPMediaType(ResourceRequest resourceRequest)
		throws PortalException {

		List<CPMediaType> cpMediaTypes = new ArrayList<>();

		long[] cpMediaTypeIds = ParamUtil.getLongValues(resourceRequest, "rowIds");

		for (long cpMediaTypeId : cpMediaTypeIds) {

			CPMediaType cpMediaType = _cpMediaTypeService.getCPMediaType(
				cpMediaTypeId);

			cpMediaTypes.add(cpMediaType);
		}

		return cpMediaTypes;
	}

	@Reference
	private CPMediaTypeService _cpMediaTypeService;

}
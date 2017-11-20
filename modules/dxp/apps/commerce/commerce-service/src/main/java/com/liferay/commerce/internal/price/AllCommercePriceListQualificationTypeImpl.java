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

package com.liferay.commerce.internal.price;

import com.liferay.commerce.price.CommercePriceListQualificationType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"commerce.price.list.qualification.type.key=" + AllCommercePriceListQualificationTypeImpl.KEY,
		"commerce.price.list.qualification.type.priority:Integer=-1"
	},
	service = CommercePriceListQualificationType.class
)
public class AllCommercePriceListQualificationTypeImpl
	implements CommercePriceListQualificationType {

	public static final String KEY = "all";

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getLabel(HttpServletRequest httpServletRequest) {
		return LanguageUtil.get(httpServletRequest, KEY);
	}

	@Override
	public boolean isQualified(HttpServletRequest httpServletRequest)
		throws PortalException {

		return true;
	}

}
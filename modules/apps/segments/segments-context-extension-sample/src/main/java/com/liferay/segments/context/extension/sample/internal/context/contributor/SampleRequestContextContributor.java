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

package com.liferay.segments.context.extension.sample.internal.context.contributor;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.segments.context.Context;
import com.liferay.segments.context.contributor.RequestContextContributor;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eduardo García
 */
@Component(
	immediate = true,
	property = {
		"request.context.contributor.key=" + SampleRequestContextContributor.KEY,
		"request.context.contributor.type=boolean"
	},
	service = RequestContextContributor.class
)
public class SampleRequestContextContributor
	implements RequestContextContributor {

	public static final String KEY = "sample";

	@Override
	public void contribute(
		Context context, HttpServletRequest httpServletRequest) {

		context.put(
			KEY,
			GetterUtil.getBoolean(
				httpServletRequest.getAttribute("sample.attribute")));
	}

}
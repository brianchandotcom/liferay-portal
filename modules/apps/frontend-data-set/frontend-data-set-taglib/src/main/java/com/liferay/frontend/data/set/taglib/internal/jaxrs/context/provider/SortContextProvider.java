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

package com.liferay.frontend.data.set.taglib.internal.jaxrs.context.provider;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(immediate = true, service = SortContextProvider.class)
@Provider
public class SortContextProvider implements ContextProvider<Sort> {

	@Override
	public Sort createContext(Message message) {
		HttpServletRequest httpServletRequest =
			(HttpServletRequest)message.getContextualProperty("HTTP.REQUEST");

		String sortString = ParamUtil.getString(
			httpServletRequest, "sort.field");
		String sortDir = ParamUtil.getString(httpServletRequest, "sort.dir");

		if (Validator.isNotNull(sortString) && Validator.isNotNull(sortDir)) {
			return SortFactoryUtil.create(
				StringUtil.trim(sortString), sortDir.equals("desc"));
		}

		String sort = ParamUtil.getString(httpServletRequest, "sort");

		if (Validator.isNotNull(sort)) {
			String[] sortArray = StringUtil.split(sort, StringPool.COLON);

			return SortFactoryUtil.create(
				StringUtil.trim(sortArray[0]), sortArray[1].equals("desc"));
		}

		return null;
	}

}
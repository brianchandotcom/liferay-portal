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

package com.liferay.frontend.data.set.internal.provider.search;

import com.liferay.frontend.data.set.provider.search.FDSKeywords;
import com.liferay.frontend.data.set.provider.search.FDSKeywordsFactory;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class FDSKeywordsFactoryImpl implements FDSKeywordsFactory {

	@Override
	public FDSKeywords create(HttpServletRequest httpServletRequest) {
		DefaultFDSKeywordsImpl defaultFDSKeywordsImpl =
			new DefaultFDSKeywordsImpl();

		defaultFDSKeywordsImpl.setKeywords(
			ParamUtil.getString(httpServletRequest, "search"));

		return defaultFDSKeywordsImpl;
	}

}
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

package com.liferay.frontend.taglib.liferay.ui.view.state;

import java.util.function.Supplier;

import javax.portlet.RenderURL;

/**
 * @author  Neil Griffin
 */
public interface SearchContainerURLFactory {

	public RenderURL create(
		int cur, int delta, String displayStyle, String keywords,
		String orderByCol, String orderByType,
		Supplier<RenderURL> renderURLSupplier, boolean resetCur, Type type);

	public enum Type {

		ADD_ENTRY, CLEAR_RESULTS, CURRENT_SORT, DISPLAY_STYLE, REVERSE_SORT,
		SEARCH

	}

}
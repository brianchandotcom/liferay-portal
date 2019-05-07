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

package com.liferay.portal.kernel.dao.search;

import com.liferay.petra.string.StringPool;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Raymond Augé
 */
public interface SearchEntry {

	public static final String DEFAULT_ALIGN = StringPool.BLANK;

	public static final int DEFAULT_COLSPAN = 1;

	public static final String DEFAULT_CSS_CLASS = StringPool.BLANK;

	public static final String DEFAULT_VALIGN = StringPool.BLANK;

	public String getAlign();

	public int getColspan();

	public String getCssClass();

	public int getIndex();

	public String getValign();

	public boolean isTruncate();

	public void print(
			Writer writer, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception;

	public void setAlign(String align);

	public void setColspan(int colspan);

	public void setCssClass(String cssClass);

	public void setIndex(int index);

	public void setTruncate(boolean truncate);

	public void setValign(String valign);

}
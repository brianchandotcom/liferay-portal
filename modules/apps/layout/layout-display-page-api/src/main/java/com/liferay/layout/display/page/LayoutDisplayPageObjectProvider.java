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

package com.liferay.layout.display.page;

import java.util.Locale;

/**
 * @author Jorge Ferrer
 */
public interface LayoutDisplayPageObjectProvider<T> {

	public long getClassNameId();

	public long getClassPK();

	public long getClassTypeId();

	public String getDescription(Locale locale);

	public T getDisplayObject();

	public long getGroupId();

	public String getKeywords(Locale locale);

	public String getTitle(Locale locale);

	public String getURLTitle(Locale locale);

}
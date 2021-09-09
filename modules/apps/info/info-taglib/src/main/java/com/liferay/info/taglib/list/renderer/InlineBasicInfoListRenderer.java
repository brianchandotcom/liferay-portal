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

package com.liferay.info.taglib.list.renderer;

import com.liferay.info.taglib.internal.list.renderer.BasicListInfoListStyle;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

/**
 * @author Pavel Savinov
 */
public interface InlineBasicInfoListRenderer<T>
	extends BasicInfoListRenderer<T> {

	@Override
	public default String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "inline-list");
	}

	@Override
	public default String getListStyle() {
		return BasicListInfoListStyle.INLINE.getKey();
	}

}
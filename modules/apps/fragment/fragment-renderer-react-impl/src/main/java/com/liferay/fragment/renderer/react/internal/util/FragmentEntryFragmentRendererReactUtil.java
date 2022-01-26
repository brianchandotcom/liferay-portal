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

package com.liferay.fragment.renderer.react.internal.util;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.Date;

/**
 * @author Víctor Galán
 */
public class FragmentEntryFragmentRendererReactUtil {

	public static String getModuleName(FragmentEntryLink fragmentEntryLink) {
		Date modifiedDate = fragmentEntryLink.getModifiedDate();

		return StringBundler.concat(
			"fragmentEntryLink/",
			String.valueOf(fragmentEntryLink.getFragmentEntryLinkId()),
			StringPool.DASH, String.valueOf(modifiedDate.getTime()));
	}

}
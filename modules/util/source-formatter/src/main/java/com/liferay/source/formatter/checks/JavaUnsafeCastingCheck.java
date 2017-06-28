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

package com.liferay.source.formatter.checks;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

/**
 * @author Hugo Huijser
 * @author Peter Shin
 */
public class JavaUnsafeCastingCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		for (Class<?> unsafeClass : _UNSAFE_CLASSES) {
			String s = "(" + unsafeClass.getSimpleName() + ")";

			if (content.contains(s)) {
				String message =
					"Unsafe casting for '" + unsafeClass.getSimpleName() + "'";

				addMessage(
					fileName, message, "unsafe_casting.markdown",
					getLineCount(content, content.indexOf(s)));
			}
		}

		return content;
	}

	private static final Class<?>[] _UNSAFE_CLASSES =
		{LiferayPortletRequest.class, LiferayPortletResponse.class};

}
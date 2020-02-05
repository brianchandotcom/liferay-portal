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

/**
 * @author Hugo Huijser
 * @author Peter Shin
 */
public class JavaUnsafeCastingCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		for (String unsafeClassName : _UNSAFE_CLASS_NAMES) {
			int pos = -1;

			while (true) {
				pos = content.indexOf("(" + unsafeClassName + ")", pos + 1);

				if (pos == -1) {
					break;
				}

				String message = "Unsafe casting for '" + unsafeClassName + "'";

				addMessage(fileName, message, getLineNumber(content, pos));
			}
		}

		return content;
	}

	private static final String[] _UNSAFE_CLASS_NAMES = {
		"LiferayPortletRequest", "LiferayPortletResponse"
	};

}
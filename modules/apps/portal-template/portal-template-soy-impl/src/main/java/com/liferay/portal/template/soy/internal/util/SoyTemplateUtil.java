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

package com.liferay.portal.template.soy.internal.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateConstants;

/**
 * @author Rafael Praxedes
 */
public class SoyTemplateUtil {

	public static long getBundleId(String templateId) {
		int pos = templateId.indexOf(TemplateConstants.BUNDLE_SEPARATOR);

		if (pos == -1) {
			if (_log.isDebugEnabled()) {
				String message = String.format(
					"The template ID \"%s\" does not map to a Soy template",
					templateId);

				_log.debug(message);
			}

			return -1;
		}

		return Long.valueOf(templateId.substring(0, pos));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SoyTemplateUtil.class);

}
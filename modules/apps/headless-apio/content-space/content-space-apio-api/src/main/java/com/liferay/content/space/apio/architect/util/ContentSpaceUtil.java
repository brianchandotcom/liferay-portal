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

package com.liferay.content.space.apio.architect.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;

import java.util.Locale;

/**
 * Utility class to calculate the name of the ContentSpace based on the existing
 * {@code Group}
 *
 * @author Javier Gamarra
 * @review
 */
public class ContentSpaceUtil {

	public static String getName(Group group, Locale locale) {
		try {
			return group.getDescriptiveName(locale);
		}
		catch (PortalException pe) {
			if (_log.isInfoEnabled()) {
				_log.info(pe, pe);
			}

			return group.getName(locale);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentSpaceUtil.class);

}
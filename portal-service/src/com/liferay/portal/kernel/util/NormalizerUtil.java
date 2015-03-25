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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class NormalizerUtil {

	public static Normalizer getNormalizer() {
		PortalRuntimePermission.checkGetBeanProperty(NormalizerUtil.class);

		return _normalizer;
	}

	public static String normalizeToAscii(String s) {
		return getNormalizer().normalizeToAscii(s);
	}

	public void setNormalizer(Normalizer normalizer) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_normalizer = normalizer;
	}

	private static Normalizer _normalizer;

}
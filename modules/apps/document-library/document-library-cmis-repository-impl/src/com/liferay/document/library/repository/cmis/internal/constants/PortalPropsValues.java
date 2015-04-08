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

package com.liferay.document.library.repository.cmis.internal.constants;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

/**
 * @author Adolfo Pérez
 */
public class PortalPropsValues {

	public static final int DL_REPOSITORY_CMIS_DELETE_DEPTH =
		GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.DL_REPOSITORY_CMIS_DELETE_DEPTH));

	public static final String DL_REPOSITORY_GUEST_PASSWORD =
		GetterUtil.getString(
			PropsUtil.get(PropsKeys.DL_REPOSITORY_GUEST_PASSWORD));

	public static final String DL_REPOSITORY_GUEST_USERNAME =
		GetterUtil.getString(
			PropsUtil.get(PropsKeys.DL_REPOSITORY_GUEST_USERNAME));

}
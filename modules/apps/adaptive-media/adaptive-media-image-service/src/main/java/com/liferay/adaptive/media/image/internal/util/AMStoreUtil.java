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

package com.liferay.adaptive.media.image.internal.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.repository.model.FileVersion;

/**
 * @author Roberto Díaz
 */
public class AMStoreUtil {

	public static String getFileVersionPath(
		FileVersion fileVersion, String configurationUuid) {

		StringBundler sb = new StringBundler(11);

		sb.append("adaptive/");
		sb.append(configurationUuid);
		sb.append("/");
		sb.append(fileVersion.getGroupId());
		sb.append("/");
		sb.append(fileVersion.getRepositoryId());
		sb.append("/");
		sb.append(fileVersion.getFileEntryId());
		sb.append("/");
		sb.append(fileVersion.getFileVersionId());
		sb.append("/");

		return sb.toString();
	}

}
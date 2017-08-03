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

package com.liferay.sharepoint.connector.operation;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.sharepoint.connector.SharepointVersion;

import java.util.Comparator;

/**
 * @author Iván Zaera
 */
public class SharepointVersionComparator
	implements Comparator<SharepointVersion> {

	@Override
	public int compare(
		SharepointVersion sharepointVersion1,
		SharepointVersion sharepointVersion2) {

		int[] versionParts1 = StringUtil.split(
			sharepointVersion1.getVersion(), StringPool.PERIOD, 0);
		int[] versionParts2 = StringUtil.split(
			sharepointVersion2.getVersion(), StringPool.PERIOD, 0);

		if (versionParts1[0] > versionParts2[0]) {
			return -1;
		}
		else if (versionParts1[0] < versionParts2[0]) {
			return 1;
		}
		else if (versionParts1[1] > versionParts2[1]) {
			return -1;
		}
		else if (versionParts1[1] < versionParts2[1]) {
			return 1;
		}

		return 0;
	}

}
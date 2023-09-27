/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.operation;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.sharepoint.soap.repository.connector.SharepointVersion;

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

		if (versionParts1[0] < versionParts2[0]) {
			return 1;
		}

		if (versionParts1[1] > versionParts2[1]) {
			return -1;
		}

		if (versionParts1[1] < versionParts2[1]) {
			return 1;
		}

		return 0;
	}

}
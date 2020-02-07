/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.sharepoint.soap.connector.operation;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.sharepoint.soap.connector.SharepointVersion;

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
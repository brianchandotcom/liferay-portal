/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.debug.upgrade.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.ReleaseManager;

/**
 * @author Shuyang Zhou
 */
public class PendingUpgradeUtil {

	public static String scan(ReleaseManager releaseManager) {
		String requiredMessage = releaseManager.getShortStatusMessage(true);

		String optionalMessage = releaseManager.getShortStatusMessage(false);

		if (requiredMessage.isEmpty()) {
			if (optionalMessage.isEmpty()) {
				return StringPool.BLANK;
			}

			return optionalMessage;
		}

		if (optionalMessage.isEmpty()) {
			return requiredMessage;
		}

		return StringBundler.concat(
			requiredMessage, StringPool.NEW_LINE, optionalMessage);
	}

}
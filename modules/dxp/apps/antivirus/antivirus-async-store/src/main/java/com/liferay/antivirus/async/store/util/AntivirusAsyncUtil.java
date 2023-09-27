/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.antivirus.async.store.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Raymond Augé
 */
public class AntivirusAsyncUtil {

	public static String getFileIdentifier(Message message) {
		String fileName = message.getString("fileName");
		String sourceFileName = message.getString("sourceFileName");

		if (Validator.isNotNull(sourceFileName)) {
			fileName = sourceFileName;
		}

		return StringBundler.concat(
			fileName, " (", message.getString("jobName"), ")");
	}

	public static String getJobName(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		versionLabel = StringUtil.replace(
			versionLabel, CharPool.PERIOD, CharPool.UNDERLINE);

		return StringBundler.concat(
			companyId, "-", repositoryId, "-", fileName, "-", versionLabel);
	}

}
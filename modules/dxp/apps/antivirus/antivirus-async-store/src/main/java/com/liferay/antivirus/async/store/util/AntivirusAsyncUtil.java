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
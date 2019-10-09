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

package com.liferay.document.library.kernel.store;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * The abstract base class for all file store implementations. Most, if not all
 * implementations should extend this class.
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Edward Han
 */
public abstract class BaseStore implements Store {

	protected void logFailedDeletion(
		long companyId, long repositoryId, String fileName, String versionLabel,
		Exception cause) {

		if ((_log.isWarnEnabled() && (cause != null)) ||
			(_log.isDebugEnabled() && (cause == null))) {

			StringBundler sb = new StringBundler(9);

			sb.append("Unable to delete file {companyId=");
			sb.append(companyId);
			sb.append(", repositoryId=");
			sb.append(repositoryId);
			sb.append(", fileName=");
			sb.append(fileName);

			if (Validator.isNotNull(versionLabel)) {
				sb.append(", versionLabel=");
				sb.append(versionLabel);
			}

			sb.append("} because it does not exist");

			if (_log.isWarnEnabled() && (cause != null)) {
				_log.warn(sb.toString(), cause);
			}

			if (_log.isDebugEnabled() && (cause == null)) {
				_log.debug(sb.toString());
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(BaseStore.class);

}
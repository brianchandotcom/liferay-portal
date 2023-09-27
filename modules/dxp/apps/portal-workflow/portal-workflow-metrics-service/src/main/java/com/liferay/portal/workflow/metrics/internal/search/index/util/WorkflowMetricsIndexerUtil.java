/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.internal.search.index.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author Rafael Praxedes
 */
public class WorkflowMetricsIndexerUtil {

	public static String digest(String indexType, Serializable... parts) {
		StringBundler sb = new StringBundler();

		for (Serializable part : parts) {
			sb.append(part);
		}

		return StringUtil.removeSubstring(indexType, "Type") +
			DigestUtils.sha256Hex(sb.toString());
	}

}
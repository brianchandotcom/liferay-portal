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

package com.liferay.portal.workflow.metrics.internal.search.index.util;

import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author Rafael Praxedes
 */
public class WorkflowMetricsIndexerUtil {

	public static String digest(String indexType, Serializable... parts) {
		StringBuilder sb = new StringBuilder();

		for (Serializable part : parts) {
			sb.append(part);
		}

		return StringUtil.removeSubstring(indexType, "Type") +
			DigestUtils.sha256Hex(sb.toString());
	}

}
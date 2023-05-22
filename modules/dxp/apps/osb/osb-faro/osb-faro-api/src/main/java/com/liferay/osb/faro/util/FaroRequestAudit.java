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

package com.liferay.osb.faro.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shinn Lok
 */
public class FaroRequestAudit {

	public void addChildFaroRequestAudit(FaroRequestAudit faroRequestAudit) {
		_faroRequestAudits.add(faroRequestAudit);
	}

	public boolean isEnabled() {
		return _log.isDebugEnabled();
	}

	public void setEndTime(long endTime) {
		_endTime = endTime;
	}

	public void setMethod(String method) {
		_method = method;
	}

	public void setStartTime(long startTime) {
		_startTime = startTime;
	}

	public void setStatusCode(int statusCode) {
		_statusCode = statusCode;
	}

	public void setURLPath(String urlPath) {
		_urlPath = urlPath;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(
			7 + (_faroRequestAudits.size() * 3));

		sb.append(_method);
		sb.append(StringPool.SPACE);
		sb.append(_urlPath);
		sb.append(" took ");
		sb.append(_endTime - _startTime);
		sb.append(" ms and returned ");
		sb.append(_statusCode);

		for (FaroRequestAudit faroRequestAudit : _faroRequestAudits) {
			sb.append(System.lineSeparator());
			sb.append("|-- ");
			sb.append(faroRequestAudit.toString());
		}

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FaroRequestAudit.class);

	private long _endTime;
	private final List<FaroRequestAudit> _faroRequestAudits = new ArrayList<>();
	private String _method;
	private long _startTime;
	private int _statusCode;
	private String _urlPath;

}
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

package com.liferay.portal.monitoring.statistics.portal;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.monitoring.MonitorNames;
import com.liferay.portal.monitoring.statistics.BaseDataSample;

/**
 * @author Rajesh Thiagarajan
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class PortalRequestDataSample extends BaseDataSample {

	public PortalRequestDataSample(
		long companyId, String user, String requestURI, String requestURL) {

		setCompanyId(companyId);
		setUser(user);
		setNamespace(MonitorNames.PORTAL);
		setName(requestURI);
		_requestURL = requestURL;
	}

	public String getRequestURL() {
		return _requestURL;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{requestURL=");
		sb.append(_requestURL);
		sb.append(", ");
		sb.append(super.toString());
		sb.append("}");

		return sb.toString();
	}

	private final String _requestURL;

}
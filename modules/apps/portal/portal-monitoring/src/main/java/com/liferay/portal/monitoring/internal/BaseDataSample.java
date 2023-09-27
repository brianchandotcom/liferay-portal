/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.monitoring.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.monitoring.DataSample;
import com.liferay.portal.kernel.monitoring.RequestStatus;

import java.io.Serializable;

import java.util.Map;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class BaseDataSample implements DataSample, Serializable {

	@Override
	public void capture(RequestStatus requestStatus) {
		if (_stopWatch != null) {
			_stopWatch.stop();

			_duration = _stopWatch.getTime();
		}

		if ((_timeout > 0) && (_duration >= _timeout) &&
			(requestStatus != RequestStatus.ERROR)) {

			_requestStatus = RequestStatus.TIMEOUT;
		}
		else {
			_requestStatus = requestStatus;
		}
	}

	@Override
	public Map<String, String> getAttributes() {
		return _attributes;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public String getDescription() {
		return _description;
	}

	@Override
	public long getDuration() {
		return _duration;
	}

	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public String getNamespace() {
		return _namespace;
	}

	@Override
	public RequestStatus getRequestStatus() {
		return _requestStatus;
	}

	@Override
	public long getTimeout() {
		return _timeout;
	}

	@Override
	public String getUser() {
		return _user;
	}

	@Override
	public void prepare() {
		if (_stopWatch == null) {
			_stopWatch = new StopWatch();
		}

		_stopWatch.start();
	}

	@Override
	public void setAttributes(Map<String, String> attributes) {
		_attributes = attributes;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@Override
	public void setDescription(String description) {
		_description = description;
	}

	@Override
	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@Override
	public void setNamespace(String namespace) {
		_namespace = namespace;
	}

	@Override
	public void setTimeout(long timeout) {
		_timeout = timeout;
	}

	@Override
	public void setUser(String user) {
		_user = user;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{attributes=", _attributes, ", companyId=", _companyId,
			", groupId=", _groupId, ", description=", _description,
			", duration=", _duration, ", name=", _name, ", namespace=",
			_namespace, ", requestStatus=", _requestStatus, ", stopWatch=",
			_stopWatch, ", timeout=", _timeout, ", user=", _user, "}");
	}

	private Map<String, String> _attributes;
	private long _companyId;
	private String _description;
	private long _duration;
	private long _groupId;
	private String _name;
	private String _namespace;
	private RequestStatus _requestStatus;
	private transient StopWatch _stopWatch;
	private long _timeout = -1;
	private String _user;

}
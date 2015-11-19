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

package com.liferay.portlet.flags.messaging;

import com.liferay.portal.service.ServiceContext;

import java.io.Serializable;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class FlagsRequest implements Serializable {

	public FlagsRequest() {
	}

	public FlagsRequest(
		String className, long classPK, String reporterEmailAddress,
		long reportedUserId, String contentTitle, String contentURL,
		String reason, ServiceContext serviceContext) {

		_className = className;
		_classPK = classPK;
		_reporterEmailAddress = reporterEmailAddress;
		_reportedUserId = reportedUserId;
		_contentTitle = contentTitle;
		_contentURL = contentURL;
		_reason = reason;
		_serviceContext = serviceContext;
	}

	public String getClassName() {
		return _className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public String getComments() {
		return _comments;
	}

	public String getContentTitle() {
		return _contentTitle;
	}

	public String getContentURL() {
		return _contentURL;
	}

	public String getReason() {
		return _reason;
	}

	public long getReportedUserId() {
		return _reportedUserId;
	}

	public String getReporterEmailAddress() {
		return _reporterEmailAddress;
	}

	public ServiceContext getServiceContext() {
		return _serviceContext;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setComments(String comments) {
		_comments = comments;
	}

	public void setContentTitle(String contentTitle) {
		_contentTitle = contentTitle;
	}

	public void setContentURL(String contentURL) {
		_contentURL = contentURL;
	}

	public void setReason(String reason) {
		this._reason = reason;
	}

	public void setReportedUserId(long reportedUserId) {
		_reportedUserId = reportedUserId;
	}

	public void setReporterEmailAddress(String reporterEmailAddress) {
		_reporterEmailAddress = reporterEmailAddress;
	}

	public void setServiceContext(ServiceContext serviceContext) {
		_serviceContext = serviceContext;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{className=");
		sb.append(_className);
		sb.append(", classPK=");
		sb.append(_classPK);
		sb.append(", comments=");
		sb.append(_comments);
		sb.append(", contentTitle=");
		sb.append(_contentTitle);
		sb.append(", contentURL=");
		sb.append(_contentURL);
		sb.append(", reason=");
		sb.append(_reason);
		sb.append(", reportedUserId=");
		sb.append(_reportedUserId);
		sb.append(", reporterEmailAddress=");
		sb.append(_reporterEmailAddress);
		sb.append(", serviceContext=");
		sb.append(_serviceContext);
		sb.append("}");

		return sb.toString();
	}

	private String _className;
	private long _classPK;
	private String _comments;
	private String _contentTitle;
	private String _contentURL;
	private String _reason;
	private long _reportedUserId;
	private String _reporterEmailAddress;
	private ServiceContext _serviceContext;

}
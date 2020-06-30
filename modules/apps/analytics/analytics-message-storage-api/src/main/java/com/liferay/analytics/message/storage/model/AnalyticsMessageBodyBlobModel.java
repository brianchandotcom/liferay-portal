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

package com.liferay.analytics.message.storage.model;

import java.sql.Blob;

/**
 * The Blob model class for lazy loading the body column in AnalyticsMessage.
 *
 * @author Brian Wing Shun Chan
 * @see AnalyticsMessage
 * @generated
 */
public class AnalyticsMessageBodyBlobModel {

	public AnalyticsMessageBodyBlobModel() {
	}

	public AnalyticsMessageBodyBlobModel(long analyticsMessageId) {
		_analyticsMessageId = analyticsMessageId;
	}

	public AnalyticsMessageBodyBlobModel(
		long analyticsMessageId, Blob bodyBlob) {

		_analyticsMessageId = analyticsMessageId;
		_bodyBlob = bodyBlob;
	}

	public long getAnalyticsMessageId() {
		return _analyticsMessageId;
	}

	public void setAnalyticsMessageId(long analyticsMessageId) {
		_analyticsMessageId = analyticsMessageId;
	}

	public Blob getBodyBlob() {
		return _bodyBlob;
	}

	public void setBodyBlob(Blob bodyBlob) {
		_bodyBlob = bodyBlob;
	}

	private long _analyticsMessageId;
	private Blob _bodyBlob;

}
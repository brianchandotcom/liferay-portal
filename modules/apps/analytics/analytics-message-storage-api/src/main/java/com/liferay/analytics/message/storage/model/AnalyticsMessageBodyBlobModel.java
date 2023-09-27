/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
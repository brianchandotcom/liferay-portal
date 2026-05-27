/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.pubsub;

/**
 * @author Amos Fong
 * @author Kyle Bischof
 */
public interface AttributeKeys {

	public static final String APP_ID = "appId";

	public static final String CLUSTER_ID = "clusterId";

	public static final String CONTENT_ENCODING = "contentEncoding";

	public static final String CONTENT_TYPE = "contentType";

	public static final String CORRELATION_ID = "correlationId";

	public static final String DELIVERY_MODE = "deliveryMode";

	public static final String EXPIRATION = "expiration";

	public static final String HEADERS = "headers";

	public static final String MESSAGE_ID = "messageId";

	public static final String PRIORITY = "priority";

	public static final String REPLY_TO = "replyTo";

	public static final String TIMESTAMP = "timestamp";

	public static final String TYPE = "type";

	public static final String USER_ID = "userId";

}
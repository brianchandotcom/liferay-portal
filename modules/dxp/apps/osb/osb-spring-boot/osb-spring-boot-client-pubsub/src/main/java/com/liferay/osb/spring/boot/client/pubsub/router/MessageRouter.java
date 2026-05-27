/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.pubsub.router;

import com.liferay.osb.spring.boot.client.pubsub.Message;

/**
 * @author Amos Fong
 * @author Kyle Bischof
 */
public interface MessageRouter {

	public void route(String topic, Message message);

}
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.distributed.messaging.publishing.broker;

import com.liferay.osb.distributed.messaging.Message;

/**
 * @author Amos Fong
 */
public interface MessageBroker {

	public void publish(String topic, Message message) throws Exception;

}
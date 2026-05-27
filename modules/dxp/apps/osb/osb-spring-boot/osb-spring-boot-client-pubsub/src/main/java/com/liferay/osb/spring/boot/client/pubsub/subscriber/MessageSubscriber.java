/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.distributed.messaging.subscribing;

import com.liferay.osb.distributed.messaging.Message;

/**
 * @author Amos Fong
 */
public interface MessageSubscriber {

	public void receive(Message message);

}
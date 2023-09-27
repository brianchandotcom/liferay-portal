/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.antivirus.async.store.event;

import com.liferay.portal.kernel.messaging.Message;

/**
 * @author Shuyang Zhou
 */
public interface AntivirusAsyncEventListener {

	public void receive(Message message);

}
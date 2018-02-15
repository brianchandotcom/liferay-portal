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

package com.liferay.lcs.messaging;

/**
 * Represents a Liferay Cloud Services Protocol handshake message. This message
 * is always sent from the LCS system to the LCS client. A successful handshake
 * results in a virtual session that lasts as long as the LCS system receives
 * heartbeat messages from the client.
 *
 * @author  Miguel Pastor
 * @author  Ivica Cardic
 * @author  Igor Beslic
 * @version 2.1.1
 * @since   LCS 0.1
 */
public class HandshakeMessage extends HeartbeatMessage {
}
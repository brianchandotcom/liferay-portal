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

package com.liferay.wsrp.internal.upgrade.v1_2_0;

import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.wsrp.constants.WSRPPortletKeys;

/**
 * @author Mate Thurzo
 */
public class UpgradeLastPublishDate
	extends com.liferay.portal.upgrade.v7_0_0.UpgradeLastPublishDate {

	protected void addLastPublishDateColumns() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			addLastPublishDateColumn("WSRP_WSRPConsumerPortlet");
			addLastPublishDateColumn("WSRP_WSRPConsumer");
			addLastPublishDateColumn("WSRP_WSRPProducer");
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		addLastPublishDateColumns();

		updateLastPublishDates(
			WSRPPortletKeys.WSRP_ADMIN, "WSRP_WSRPConsumerPortlet");
		updateLastPublishDates(WSRPPortletKeys.WSRP_ADMIN, "WSRP_WSRPConsumer");
		updateLastPublishDates(WSRPPortletKeys.WSRP_ADMIN, "WSRP_WSRPProducer");
	}

}
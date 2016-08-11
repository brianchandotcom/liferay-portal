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

package com.liferay.wsrp.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.wsrp.model.WSRPConsumer;
import com.liferay.wsrp.model.WSRPConsumerPortlet;
import com.liferay.wsrp.model.WSRPProducer;
import com.liferay.wsrp.service.WSRPConsumerLocalServiceUtil;
import com.liferay.wsrp.service.WSRPConsumerPortletLocalServiceUtil;
import com.liferay.wsrp.service.WSRPProducerLocalServiceUtil;

import java.util.List;

/**
 * @author Peter Fellwock
 */
public class UpgradeWSRP extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateWSRPConsumer();

		updateWSRPConsumerPortlet();

		updateWSRPProducer();
	}

	protected void updateWSRPConsumer() throws Exception {
		List<WSRPConsumer> wsrpConsumers =
			WSRPConsumerLocalServiceUtil.getWSRPConsumers(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (WSRPConsumer wsrpConsumer : wsrpConsumers) {
			WSRPConsumerLocalServiceUtil.updateWSRPConsumer(wsrpConsumer);
		}
	}

	protected void updateWSRPConsumerPortlet() throws Exception {
		List<WSRPConsumerPortlet> wsrpConsumerPortlets =
			WSRPConsumerPortletLocalServiceUtil.getWSRPConsumerPortlets(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (WSRPConsumerPortlet wsrpConsumerPortlet : wsrpConsumerPortlets) {
			WSRPConsumerPortletLocalServiceUtil.updateWSRPConsumerPortlet(
				wsrpConsumerPortlet);
		}
	}

	protected void updateWSRPProducer() throws Exception {
		runSQL(
			"update WSRP_WSRPProducer set version = '2.0' where version is " +
				"null or version = ''");

		List<WSRPProducer> wsrpProducers =
			WSRPProducerLocalServiceUtil.getWSRPProducers(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (WSRPProducer wsrpProducer : wsrpProducers) {
			WSRPProducerLocalServiceUtil.updateWSRPProducer(wsrpProducer);
		}
	}

}
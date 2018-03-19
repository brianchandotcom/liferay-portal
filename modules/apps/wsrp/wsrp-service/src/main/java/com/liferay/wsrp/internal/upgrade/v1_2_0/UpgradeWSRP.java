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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Shinn Lok
 */
public class UpgradeWSRP extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateWSRPProducers();

		updateWSRPConsumerPortlets();
		updateWSRPConsumers();
	}

	protected void updateWSRPConsumer(
			long wsrpConsumerId, String url, String wsdl)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"update WSRP_WSRPConsumer set url = ?, wsdl = ? where " +
					"wsrpConsumerId = ?")) {

			ps.setString(1, url);
			ps.setString(2, wsdl);
			ps.setLong(3, wsrpConsumerId);

			ps.executeUpdate();
		}
	}

	protected void updateWSRPConsumerPortlets() throws Exception {
		for (String[] portletIds : _RENAME_PORTLET_IDS_ARRAY) {
			String oldPortletId = portletIds[0];
			String newPortletId = portletIds[1];

			runSQL(
				StringBundler.concat(
					"update WSRP_WSRPConsumerPortlet set portletHandle = '",
					newPortletId, "' where portletHandle = '", oldPortletId,
					"'"));
		}
	}

	protected void updateWSRPConsumers() throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select wsrpConsumerId, url, wsdl from WSRP_WSRPConsumer");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long wsrpConsumerId = rs.getLong(1);
				String url = rs.getString(2);
				String wsdl = rs.getString(3);

				String uuid = url.substring(
					url.lastIndexOf(StringPool.SLASH) + 1);

				if (!_uuids.contains(uuid)) {
					continue;
				}

				url = url.replace("/wsrp-portlet/", "/o/wsrp-service/");
				wsdl = wsdl.replace("/wsrp-portlet/", "/o/wsrp-service/");

				updateWSRPConsumer(wsrpConsumerId, url, wsdl);
			}
		}
	}

	protected void updateWSRPProducer(
			Map<String, String> renamePortletIdsMap, long wsrpProducerId,
			String portletIds)
		throws Exception {

		String[] portletIdsArray = StringUtil.split(portletIds);

		for (int i = 0; i < portletIdsArray.length; i++) {
			String oldPortletId = portletIdsArray[i];

			String newPortletId = renamePortletIdsMap.get(oldPortletId);

			if (newPortletId == null) {
				continue;
			}

			portletIdsArray[i] = newPortletId;
		}

		portletIds = StringUtil.merge(portletIdsArray);

		runSQL(
			StringBundler.concat(
				"update WSRP_WSRPProducer set portletIds = '", portletIds,
				"' where wsrpProducerId = '", String.valueOf(wsrpProducerId),
				"'"));
	}

	protected void updateWSRPProducers() throws Exception {
		Map<String, String> renamePortletIdsMap = new HashMap<>();

		for (String[] renamePortletIds : _RENAME_PORTLET_IDS_ARRAY) {
			renamePortletIdsMap.put(renamePortletIds[0], renamePortletIds[1]);
		}

		try (PreparedStatement ps = connection.prepareStatement(
				"select uuid_, wsrpProducerId, portletIds from " +
					"WSRP_WSRPProducer");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				String uuid = rs.getString(1);
				long wsrpProducerId = rs.getLong(2);
				String portletIds = rs.getString(3);

				_uuids.add(uuid);

				updateWSRPProducer(
					renamePortletIdsMap, wsrpProducerId, portletIds);
			}
		}
	}

	private static final String[][] _RENAME_PORTLET_IDS_ARRAY = {
		{
			"1_WAR_mysubscriptionsportlet",
			"com_liferay_mysubscriptions_web_portlet_MySubscriptionsPortlet"
		},
		{
			"1_WAR_privatemessagingportlet",
			"com_liferay_social_privatemessaging_web_portlet_" +
				"PrivateMessagingPortlet"
		},
		{
			"1_WAR_twitterportlet",
			"com_liferay_twitter_web_portlet_TwitterPortlet"
		},
		{
			"16",
			"com_liferay_currency_converter_web_portlet_" +
				"CurrencyConverterPortlet"
		},
		{"23", "com_liferay_dictionary_web_portlet_DictionaryPortlet"},
		{"26", "com_liferay_translator_web_portlet_TranslatorPortlet"},
		{"27", "com_liferay_unit_converter_web_portlet_UnitConverterPortlet"},
		{
			"30",
			"com_liferay_network_utilities_web_portlet_NetworkUtilitiesPortlet"
		},
		{"47", "com_liferay_hello_world_web_portlet_HelloWorldPortlet"},
		{"50", "com_liferay_hello_velocity_web_portlet_HelloVelocityPortlet"},
		{"61", "com_liferay_loan_calculator_portlet_LoanCalculatorPortlet"},
		{"67", "com_liferay_amazon_rankings_web_portlet_AmazonRankingsPortlet"},
		{
			"70",
			"com_liferay_password_generator_web_portlet_" +
				"PasswordGeneratorPortlet"
		},
		{"97", "com_liferay_quick_note_web_portlet_QuickNotePortlet"}
	};

	private final List<String> _uuids = new ArrayList<>();

}
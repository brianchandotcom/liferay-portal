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

package com.liferay.commerce.internal.upgrade.v8_9_1;

import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Crescenzo Rega
 */
public class CommerceChannelAccountEntryRelUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select CChannelAccountEntryRel.CChannelAccountEntryRelId ",
					"from CChannelAccountEntryRel inner join ",
					"CommercePriceList on CChannelAccountEntryRel.classPK = ",
					"CommercePriceList.commercePriceListId inner join ",
					"ClassName_ on CChannelAccountEntryRel.classNameId = ",
					"ClassName_.classNameId where ClassName_.classNameId = ",
					ClassNameLocalServiceUtil.getClassNameId(
						CommerceDiscount.class)));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update CChannelAccountEntryRel set classNameId = ? " +
						"where CChannelAccountEntryRelId = ?")) {

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					long commerceChannelAccountEntryRelId = resultSet.getLong(
						"CChannelAccountEntryRelId");

					preparedStatement2.setLong(
						1,
						ClassNameLocalServiceUtil.getClassNameId(
							CommercePriceList.class));
					preparedStatement2.setLong(
						2, commerceChannelAccountEntryRelId);
					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

}
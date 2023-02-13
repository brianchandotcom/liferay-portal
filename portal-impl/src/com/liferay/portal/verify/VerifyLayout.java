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

package com.liferay.portal.verify;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Jorge Avalos
 */
public class VerifyLayout extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		_verifyLayoutFriendlyURL();
	}

	private String _getReservedLayoutFriendlyURLS() {
		String reservedLayoutFriendlyURLS = "";

		for (int i = 0; i < PropsValues.LAYOUT_FRIENDLY_URL_KEYWORDS.length;
			 i++) {

			String likeClause = PropsValues.LAYOUT_FRIENDLY_URL_KEYWORDS[i];

			if (PropsValues.LAYOUT_FRIENDLY_URL_KEYWORDS[i].contains(
					StringPool.STAR)) {

				likeClause = StringUtil.replace(
					likeClause, CharPool.STAR, CharPool.PERCENT);
			}

			if (PropsValues.LAYOUT_FRIENDLY_URL_KEYWORDS[i].contains("_")) {
				likeClause = StringUtil.replace(
					likeClause, CharPool.UNDERLINE, "!_");
			}

			if (reservedLayoutFriendlyURLS.isEmpty()) {
				reservedLayoutFriendlyURLS += StringBundler.concat(
					"LIKE '/", likeClause, "' ");
			}
			else {
				reservedLayoutFriendlyURLS += StringBundler.concat(
					"OR friendlyURL LIKE '/", likeClause, "' ");
			}

			if (likeClause.contains("!_")) {
				reservedLayoutFriendlyURLS += "ESCAPE \'!\'";
			}
		}

		return reservedLayoutFriendlyURLS;
	}

	private void _verifyLayoutFriendlyURL() throws Exception {
		if (PropsValues.LAYOUT_FRIENDLY_URL_KEYWORDS.length == 0) {
			return;
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select plid, friendlyURL from LayoutFriendlyURL where " +
					"friendlyURL " + _getReservedLayoutFriendlyURLS());
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long plid = resultSet.getLong(1);
				String friendlyURL = resultSet.getString(2);

				_log.error(
					StringBundler.concat(
						"Detected reserved friendly URL \"", friendlyURL,
						"\" Update the friendly URL for the layout with  plid ",
						plid, "."));
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(VerifyLayout.class);

}
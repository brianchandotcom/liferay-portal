/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.upgrade.v3_1_0;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class UrlSubjectUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (SafeCloseable safeCloseable = addTemporaryIndex(
				"MBMessage", false, "subject")) {

			_populateUrlSubject();
		}
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"MBMessage", "urlSubject VARCHAR(255) null")
		};
	}

	private String _getURLSubject(long id, String subject) {
		if (subject == null) {
			return String.valueOf(id);
		}

		subject = StringUtil.toLowerCase(subject.trim());

		if (Validator.isNull(subject) || Validator.isNumber(subject) ||
			subject.equals("rss")) {

			subject = String.valueOf(id);
		}
		else {
			subject = FriendlyURLNormalizerUtil.normalizeWithPeriodsAndSlashes(
				subject);
		}

		return subject.substring(0, Math.min(subject.length(), 254));
	}

	private void _populateUrlSubject() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select messageId, subject from MBMessage order by subject, " +
					"messageId asc");
			ResultSet resultSet = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update MBMessage set urlSubject = ? where messageId = " +
						"?")) {

			Map<String, IntegerWrapper> counts = new HashMap<>();

			while (resultSet.next()) {
				long messageId = resultSet.getLong(1);
				String subject = resultSet.getString(2);

				String suffix = StringPool.BLANK;

				String urlSubject = _getURLSubject(messageId, subject);

				IntegerWrapper count = counts.computeIfAbsent(
					urlSubject, key -> new IntegerWrapper(0));

				if (count.getValue() > 0) {
					suffix = StringPool.DASH + count.getValue();

					counts.put(urlSubject + suffix, new IntegerWrapper(1));
				}

				count.increment();

				preparedStatement2.setString(1, urlSubject + suffix);
				preparedStatement2.setLong(2, messageId);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

}
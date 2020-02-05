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

package com.liferay.knowledge.base.internal.upgrade.v1_3_4;

import com.liferay.knowledge.base.constants.KBCommentConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Adolfo Pérez
 */
public class UpgradeKBComment extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeSchema();
		upgradeKBComments();
	}

	protected void upgradeKBComments() throws Exception {
		if (!hasColumn("KBComment", "helpful")) {
			return;
		}

		runSQL(
			"update KBComment set userRating = " +
				KBCommentConstants.USER_RATING_LIKE + " where helpful = TRUE");

		runSQL(
			"update KBComment set userRating = " +
				KBCommentConstants.USER_RATING_DISLIKE +
					" where helpful = FALSE");

		runSQL("alter table KBComment drop column helpful");
	}

	protected void upgradeSchema() throws Exception {
		String template = StringUtil.read(
			UpgradeKBComment.class.getResourceAsStream(
				"dependencies/update.sql"));

		runSQLTemplateString(template, false);
	}

}
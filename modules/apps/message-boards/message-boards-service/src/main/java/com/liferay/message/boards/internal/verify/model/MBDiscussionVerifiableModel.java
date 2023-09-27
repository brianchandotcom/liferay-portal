/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.verify.model;

import com.liferay.portal.kernel.verify.model.VerifiableAuditedModel;
import com.liferay.portal.kernel.verify.model.VerifiableGroupedModel;

/**
 * @author Miguel Pastor
 */
public class MBDiscussionVerifiableModel
	implements VerifiableAuditedModel, VerifiableGroupedModel {

	@Override
	public String getJoinByTableName() {
		return "threadId";
	}

	@Override
	public String getPrimaryKeyColumnName() {
		return "discussionId";
	}

	@Override
	public String getRelatedModelName() {
		return "MBThread";
	}

	@Override
	public String getRelatedPKColumnName() {
		return "threadId";
	}

	@Override
	public String getRelatedPrimaryKeyColumnName() {
		return "threadId";
	}

	@Override
	public String getRelatedTableName() {
		return "MBThread";
	}

	@Override
	public String getTableName() {
		return "MBDiscussion";
	}

	@Override
	public boolean isAnonymousUserAllowed() {
		return false;
	}

	@Override
	public boolean isUpdateDates() {
		return true;
	}

}
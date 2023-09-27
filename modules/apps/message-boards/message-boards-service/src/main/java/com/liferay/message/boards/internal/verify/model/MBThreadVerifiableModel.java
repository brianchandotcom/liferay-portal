/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.verify.model;

import com.liferay.portal.kernel.verify.model.VerifiableAuditedModel;

/**
 * @author Miguel Pastor
 */
public class MBThreadVerifiableModel implements VerifiableAuditedModel {

	@Override
	public String getJoinByTableName() {
		return "rootMessageId";
	}

	@Override
	public String getPrimaryKeyColumnName() {
		return "threadId";
	}

	@Override
	public String getRelatedModelName() {
		return "MBMessage";
	}

	@Override
	public String getRelatedPKColumnName() {
		return "messageId";
	}

	@Override
	public String getTableName() {
		return "MBThread";
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
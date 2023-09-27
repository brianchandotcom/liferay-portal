/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify.model;

import com.liferay.portal.kernel.verify.model.VerifiableAuditedModel;

/**
 * @author Miguel Pastor
 */
public class RepositoryEntryVerifiableAuditedModel
	implements VerifiableAuditedModel {

	@Override
	public String getJoinByTableName() {
		return "repositoryId";
	}

	@Override
	public String getPrimaryKeyColumnName() {
		return "repositoryEntryId";
	}

	@Override
	public String getRelatedModelName() {
		return "Repository";
	}

	@Override
	public String getRelatedPKColumnName() {
		return "repositoryId";
	}

	@Override
	public String getTableName() {
		return "RepositoryEntry";
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
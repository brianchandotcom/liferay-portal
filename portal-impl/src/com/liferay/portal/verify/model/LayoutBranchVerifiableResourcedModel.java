/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify.model;

import com.liferay.portal.kernel.model.LayoutBranch;
import com.liferay.portal.kernel.verify.model.VerifiableResourcedModel;
import com.liferay.portal.model.impl.LayoutBranchModelImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutBranchVerifiableResourcedModel
	implements VerifiableResourcedModel {

	@Override
	public String getModelName() {
		return LayoutBranch.class.getName();
	}

	@Override
	public String getPrimaryKeyColumnName() {
		return "layoutBranchId";
	}

	@Override
	public String getTableName() {
		return LayoutBranchModelImpl.TABLE_NAME;
	}

	@Override
	public String getUserIdColumnName() {
		return "userId";
	}

}
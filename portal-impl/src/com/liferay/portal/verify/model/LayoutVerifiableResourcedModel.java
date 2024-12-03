/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify.model;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.verify.model.VerifiableResourcedModel;
import com.liferay.portal.model.impl.LayoutModelImpl;

/**
 * @author István András Dézsi
 */
public class LayoutVerifiableResourcedModel
	implements VerifiableResourcedModel {

	public String getGroupIdColumnName() {
		return "groupId";
	}

	@Override
	public String getModelName() {
		return Layout.class.getName();
	}

	@Override
	public String getPrimaryKeyColumnName() {
		return "plid";
	}

	public String getPrivateLayoutColumnName() {
		return "privateLayout";
	}

	@Override
	public String getTableName() {
		return LayoutModelImpl.TABLE_NAME;
	}

	@Override
	public String getUserIdColumnName() {
		return "userId";
	}

}
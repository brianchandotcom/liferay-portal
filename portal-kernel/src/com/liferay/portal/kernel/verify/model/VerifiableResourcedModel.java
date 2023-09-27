/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.verify.model;

/**
 * @author Miguel Pastor
 */
public interface VerifiableResourcedModel extends VerifiableModel {

	public String getModelName();

	@Override
	public String getPrimaryKeyColumnName();

	@Override
	public String getTableName();

	public String getUserIdColumnName();

}
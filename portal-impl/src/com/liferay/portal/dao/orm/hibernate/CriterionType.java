/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.orm.hibernate;

/**
 * @author Tina Tian
 */
public enum CriterionType {

	ALL_EQ, AND, BETWEEN, EQ, EQ_ALL, GE, GE_ALL, GE_SOME, GT, GT_ALL, GT_SOME,
	ILIKE, IN, IS_EMPTY, IS_NOT_EMPTY, IS_NOT_NULL, IS_NULL, LE, LE_ALL,
	LE_SOME, LIKE, LT, LT_ALL, LT_SOME, NE, NOT, NOT_IN, OR, SQL_RESTRICTION

}
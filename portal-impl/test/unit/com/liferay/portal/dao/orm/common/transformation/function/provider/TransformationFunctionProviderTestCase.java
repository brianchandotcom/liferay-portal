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

package com.liferay.portal.dao.orm.common.transformation.function.provider;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Manuel de la Peña
 */
public interface TransformationFunctionProviderTestCase {

	public default void mockDB(DB db) {
		PowerMockito.mockStatic(DBManagerUtil.class);

		PowerMockito.when(DBManagerUtil.getDB()).thenReturn(db);
	}

	public void testReplaceBitwiseCheck();

	public void testReplaceCastClobText();

	public void testReplaceCastLong();

	public void testReplaceCrossJoin();

	public void testReplaceInStr();

	public void testReplaceIntegerDivision();

	public void testReplaceMod();

	public void testReplaceNullDate();

	public void testReplaceReplace();

	public void testReplaceSubst();

}
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.info.item.updater.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.info.item.updater.InfoItemFieldValuesUpdater;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.web.internal.info.item.BaseObjectEntryInfoItemTestCase;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carolina Barbosa
 */
@RunWith(Arquillian.class)
public class ObjectEntryInfoItemFieldValuesUpdaterTest
	extends BaseObjectEntryInfoItemTestCase {

	@Test
	public void testUpdateFromInfoItemFieldValues() throws Exception {
		InfoItemFieldValuesUpdater<ObjectEntry> infoItemFieldValuesUpdater =
			infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFieldValuesUpdater.class,
				objectDefinition2.getClassName());

		String name1 = RandomTestUtil.randomString();
		String name2 = RandomTestUtil.randomString();

		infoItemFieldValuesUpdater.updateFromInfoItemFieldValues(
			objectEntry2, geInfoItemFieldValues(name1, name2));

		assertObjectEntryValues(name1, name2);
	}

}
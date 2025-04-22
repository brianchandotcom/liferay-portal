/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.info.item.creator.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.info.item.creator.InfoItemCreator;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.web.internal.info.item.BaseObjectEntryInfoItemTestCase;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carolina Barbosa
 */
@RunWith(Arquillian.class)
public class ObjectEntryInfoItemCreatorTest
	extends BaseObjectEntryInfoItemTestCase {

	@Test
	public void testCreateFromInfoItemFieldValues() throws Exception {
		InfoItemCreator<ObjectEntry> infoItemCreator =
			infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemCreator.class, objectDefinition2.getClassName());

		String name1 = RandomTestUtil.randomString();
		String name2 = RandomTestUtil.randomString();

		objectEntry2 = infoItemCreator.createFromInfoItemFieldValues(
			0, geInfoItemFieldValues(name1, name2),
			WorkflowConstants.STATUS_APPROVED);

		assertObjectEntryValues(name1, name2);
	}

}
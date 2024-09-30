/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.processor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Víctor Galán
 */
@RunWith(Arquillian.class)
public class FragmentEntryProcessorRegistryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE);

	@Test
	public void testGetAvailableTagsJSONArray() {
		JSONArray availableTagsJSONArray =
			_fragmentEntryProcessorRegistry.getAvailableTagsJSONArray();

		for (int i = 0; i < availableTagsJSONArray.length(); i++) {
			JSONObject tagsJSONArrayJSONObject =
				availableTagsJSONArray.getJSONObject(i);

			String name = tagsJSONArrayJSONObject.getString("name");

			Assert.assertFalse(name.startsWith("lfr-editable:"));
		}
	}

	@Inject
	private FragmentEntryProcessorRegistry _fragmentEntryProcessorRegistry;

}
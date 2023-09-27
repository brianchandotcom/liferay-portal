/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.item.selector.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.DownloadURLItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.item.selector.criteria.audio.criterion.AudioItemSelectorCriterion;
import com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class ItemSelectorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetItemSelectedEventName() {
		Assert.assertEquals(
			"eventName",
			_itemSelector.getItemSelectedEventName(
				StringBundler.concat(
					"http://localhost:8080/group/guest/~/control_panel/manage",
					"/-/select/file/eventName",
					"?_com_liferay_item_selector_web_portlet",
					"_ItemSelectorPortlet_0_json=",
					URLCodec.encodeURL(
						JSONUtil.put(
							"desiredItemSelectorReturnTypes",
							URLItemSelectorReturnType.class.getName()
						).toString()))));
	}

	@Test
	public void testGetItemSelectorCriteria() {
		List<ItemSelectorCriterion> itemSelectorCriteria =
			_itemSelector.getItemSelectorCriteria(
				StringBundler.concat(
					"http://localhost:8080/group/guest/~/control_panel/manage",
					"/-/select/file/eventName",
					"?_com_liferay_item_selector_web_portlet",
					"_ItemSelectorPortlet_0_json=",
					URLCodec.encodeURL(
						JSONUtil.put(
							"desiredItemSelectorReturnTypes",
							URLItemSelectorReturnType.class.getName()
						).toString())));

		Assert.assertEquals(
			itemSelectorCriteria.toString(), 1, itemSelectorCriteria.size());

		ItemSelectorCriterion itemSelectorCriterion = itemSelectorCriteria.get(
			0);

		Assert.assertTrue(
			itemSelectorCriterion instanceof FileItemSelectorCriterion);

		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			itemSelectorCriterion.getDesiredItemSelectorReturnTypes();

		Assert.assertEquals(
			desiredItemSelectorReturnTypes.toString(), 1,
			desiredItemSelectorReturnTypes.size());
		Assert.assertTrue(
			desiredItemSelectorReturnTypes.get(0) instanceof
				URLItemSelectorReturnType);
	}

	@Test
	public void testGetItemSelectorCriteriaWithTwoCriteria() {
		List<ItemSelectorCriterion> itemSelectorCriteria =
			_itemSelector.getItemSelectorCriteria(
				StringBundler.concat(
					"http://localhost:8080/group/guest/~/control_panel/manage",
					"/-/select/file%2Caudio/eventName",
					"?_com_liferay_item_selector_web_portlet",
					"_ItemSelectorPortlet_0_json=",
					URLCodec.encodeURL(
						JSONUtil.put(
							"desiredItemSelectorReturnTypes",
							URLItemSelectorReturnType.class.getName()
						).toString()),
					"&_com_liferay_item_selector_web_portlet",
					"_ItemSelectorPortlet_1_json=",
					URLCodec.encodeURL(
						JSONUtil.put(
							"desiredItemSelectorReturnTypes",
							DownloadURLItemSelectorReturnType.class.getName()
						).toString())));

		Assert.assertEquals(
			itemSelectorCriteria.toString(), 2, itemSelectorCriteria.size());

		ItemSelectorCriterion fileItemSelectorCriterion =
			itemSelectorCriteria.get(0);

		Assert.assertTrue(
			fileItemSelectorCriterion instanceof FileItemSelectorCriterion);

		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			fileItemSelectorCriterion.getDesiredItemSelectorReturnTypes();

		Assert.assertEquals(
			desiredItemSelectorReturnTypes.toString(), 1,
			desiredItemSelectorReturnTypes.size());
		Assert.assertTrue(
			desiredItemSelectorReturnTypes.get(0) instanceof
				URLItemSelectorReturnType);

		ItemSelectorCriterion audioItemSelectorCriterion =
			itemSelectorCriteria.get(1);

		Assert.assertTrue(
			audioItemSelectorCriterion instanceof AudioItemSelectorCriterion);

		desiredItemSelectorReturnTypes =
			audioItemSelectorCriterion.getDesiredItemSelectorReturnTypes();

		Assert.assertEquals(
			desiredItemSelectorReturnTypes.toString(), 1,
			desiredItemSelectorReturnTypes.size());
		Assert.assertTrue(
			desiredItemSelectorReturnTypes.get(0) instanceof
				DownloadURLItemSelectorReturnType);
	}

	@Test
	public void testGetItemSelectorCriteriaWithTwoCriteriaAndExplicitComma() {
		List<ItemSelectorCriterion> itemSelectorCriteria =
			_itemSelector.getItemSelectorCriteria(
				StringBundler.concat(
					"http://localhost:8080/group/guest/~/control_panel/manage",
					"/-/select/file,audio/eventName",
					"?_com_liferay_item_selector_web_portlet",
					"_ItemSelectorPortlet_0_json=",
					URLCodec.encodeURL(
						JSONUtil.put(
							"desiredItemSelectorReturnTypes",
							URLItemSelectorReturnType.class.getName()
						).toString()),
					"&_com_liferay_item_selector_web_portlet",
					"_ItemSelectorPortlet_1_json=",
					URLCodec.encodeURL(
						JSONUtil.put(
							"desiredItemSelectorReturnTypes",
							DownloadURLItemSelectorReturnType.class.getName()
						).toString())));

		Assert.assertEquals(
			itemSelectorCriteria.toString(), 2, itemSelectorCriteria.size());
	}

	@Inject
	private ItemSelector _itemSelector;

}
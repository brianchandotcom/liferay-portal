/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.action;

import com.liferay.frontend.data.set.internal.serializer.BaseFDSSerializer;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.data.set.serializer.FDSSerializer;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Daniel Sanz
 */
public class ItemsActionsCustomFDSSerializerImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_resetSerializer();
	}

	@Test
	public void testSerialize() throws Exception {

		// Different items actions

		_mockFDSItemsActions("fdsName1", new String[] {"New 1.1", "New 1.2"});
		_mockFDSItemsActions("fdsName2", new String[] {"New 2"});

		List<FDSActionDropdownItem> fdsActionDropdownItems1 =
			_fdsSerializer.serialize("fdsName1", _httpServletRequest);

		Assert.assertFalse(_containsLabel(fdsActionDropdownItems1, "New 2"));
		Assert.assertTrue(fdsActionDropdownItems1.size() == 2);
		Assert.assertTrue(_containsLabel(fdsActionDropdownItems1, "New 1.1"));
		Assert.assertTrue(_containsLabel(fdsActionDropdownItems1, "New 1.2"));

		List<FDSActionDropdownItem> fdsActionDropdownItems2 =
			_fdsSerializer.serialize("fdsName2", _httpServletRequest);

		Assert.assertFalse(_containsLabel(fdsActionDropdownItems2, "New 1.1"));
		Assert.assertFalse(_containsLabel(fdsActionDropdownItems2, "New 1.2"));
		Assert.assertTrue(fdsActionDropdownItems2.size() == 1);
		Assert.assertTrue(_containsLabel(fdsActionDropdownItems2, "New 2"));

		_resetSerializer();

		// No items actions

		_mockFDSItemsActions("fdsName", null);

		Assert.assertTrue(
			_fdsSerializer.serialize(
				"fdsName", _httpServletRequest
			).isEmpty());

		_resetSerializer();

		// Shared items actions

		String[] labels = {"New A", "New B"};

		_testSerialize("fdsName1", labels);
		_testSerialize("fdsName2", labels);
	}

	private boolean _containsLabel(
		List<FDSActionDropdownItem> fdsActionDropdownItems, String label) {

		for (DropdownItem dropdownItem : fdsActionDropdownItems) {
			if (label.equals((String)dropdownItem.get("label"))) {
				return true;
			}
		}

		return false;
	}

	private void _mockFDSItemsActions(String fdsName, String[] labels) {
		Mockito.when(
			_fdsSerializer.serialize(fdsName, _httpServletRequest)
		).thenCallRealMethod();

		BaseFDSSerializer baseFDSSerializer = (BaseFDSSerializer)_fdsSerializer;

		if (ArrayUtil.isEmpty(labels)) {
			Mockito.when(
				baseFDSSerializer.getSortedRelatedObjectEntries(
					Mockito.eq(fdsName), Mockito.eq("itemActionsOrder"),
					Mockito.eq(_httpServletRequest), Mockito.any(),
					Mockito.eq("dataSetToDataSetActions"))
			).thenReturn(
				Collections.emptySet()
			);

			return;
		}

		Set<ObjectEntry> objectEntries = new HashSet<>();

		for (String label : labels) {
			ObjectEntry objectEntry = new ObjectEntry();

			objectEntry.setProperties(
				HashMapBuilder.put(
					"label", (Object)label
				).build());

			objectEntries.add(objectEntry);
		}

		Mockito.when(
			baseFDSSerializer.getSortedRelatedObjectEntries(
				Mockito.eq(fdsName), Mockito.eq("itemActionsOrder"),
				Mockito.eq(_httpServletRequest), Mockito.any(),
				Mockito.eq("dataSetToDataSetActions"))
		).thenReturn(
			objectEntries
		);
	}

	private void _resetSerializer() throws Exception {
		_fdsSerializer = Mockito.mock(
			ItemsActionsCustomFDSSerializerImpl.class);
	}

	private void _testSerialize(String fdsName, String[] labels)
		throws Exception {

		_mockFDSItemsActions(fdsName, labels);

		List<FDSActionDropdownItem> fdsActionDropdownItems =
			_fdsSerializer.serialize(fdsName, _httpServletRequest);

		for (String label : labels) {
			Assert.assertTrue(_containsLabel(fdsActionDropdownItems, label));
		}

		Assert.assertTrue(labels.length == fdsActionDropdownItems.size());
	}

	private static FDSSerializer<List<FDSActionDropdownItem>> _fdsSerializer;
	private static final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);

}
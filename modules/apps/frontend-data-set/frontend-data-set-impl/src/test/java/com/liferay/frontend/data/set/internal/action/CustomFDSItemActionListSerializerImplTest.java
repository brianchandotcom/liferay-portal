/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.action;

import com.liferay.frontend.data.set.internal.serializer.BaseCustomFDSSerializer;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
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
public class CustomFDSItemActionListSerializerImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_resetSerializer();
	}

	@Test
	public void testSerialization() throws Exception {

		// different action lists

		_mockFDSItemActionObjectEntry(
			new String[] {"New 1.1", "New 1.2"}, "fdsName1");

		_mockFDSItemActionObjectEntry(new String[] {"New 2"}, "fdsName2");

		List<FDSActionDropdownItem> actionDropdownItems1 =
			_customFDSItemActionListSerializerImpl.serialize(
				"fdsName1", _httpServletRequest);

		List<FDSActionDropdownItem> actionDropdownItems2 =
			_customFDSItemActionListSerializerImpl.serialize(
				"fdsName2", _httpServletRequest);

		Assert.assertTrue(_containsLabel(actionDropdownItems1, "New 1.1"));
		Assert.assertTrue(_containsLabel(actionDropdownItems1, "New 1.2"));
		Assert.assertTrue(actionDropdownItems1.size() == 2);

		Assert.assertTrue(_containsLabel(actionDropdownItems2, "New 2"));
		Assert.assertTrue(actionDropdownItems2.size() == 1);

		Assert.assertFalse(_containsLabel(actionDropdownItems1, "New 2"));
		Assert.assertFalse(_containsLabel(actionDropdownItems2, "New 1.1"));
		Assert.assertFalse(_containsLabel(actionDropdownItems2, "New 1.2"));

		_resetSerializer();

		// no item action list

		_mockFDSItemActionObjectEntry(null, "fdsName");

		Assert.assertTrue(
			_customFDSItemActionListSerializerImpl.serialize(
				"fdsName", _httpServletRequest
			).isEmpty());

		_resetSerializer();

		// shared action lists

		String[] labels = {"New A", "New B"};

		String[] fdsNames = {"fdsName1", "fdsName2"};

		for (String fdsName : fdsNames) {
			_mockFDSItemActionObjectEntry(labels, fdsName);

			List<FDSActionDropdownItem> actionDropdownItems =
				_customFDSItemActionListSerializerImpl.serialize(
					fdsName, _httpServletRequest);

			for (String label : labels) {
				Assert.assertTrue(_containsLabel(actionDropdownItems, label));
			}

			Assert.assertTrue(labels.length == actionDropdownItems.size());
		}
	}

	private boolean _containsLabel(
		List<FDSActionDropdownItem> itemActionList, String label) {

		for (DropdownItem dropdownItem : itemActionList) {
			if (label.equals((String)dropdownItem.get("label"))) {
				return true;
			}
		}

		return false;
	}

	private void _mockFDSItemActionObjectEntry(
		String[] dropdownItemLabels, String fdsName) {

		Mockito.when(
			_customFDSItemActionListSerializerImpl.serialize(
				fdsName, _httpServletRequest)
		).thenCallRealMethod();

		BaseCustomFDSSerializer baseCustomFDSSerializer =
			(BaseCustomFDSSerializer)_customFDSItemActionListSerializerImpl;

		if (ArrayUtil.isEmpty(dropdownItemLabels)) {
			Mockito.when(
				baseCustomFDSSerializer.getItemActionObjectEntries(
					fdsName, _httpServletRequest)
			).thenReturn(
				Collections.emptySet()
			);

			return;
		}

		Set<ObjectEntry> objectEntries = new HashSet<>();

		for (String dropdownItemLabel : dropdownItemLabels) {
			ObjectEntry objectEntry = new ObjectEntry();

			objectEntry.setProperties(
				HashMapBuilder.put(
					"label", (Object)dropdownItemLabel
				).build());

			objectEntries.add(objectEntry);
		}

		Mockito.when(
			baseCustomFDSSerializer.getItemActionObjectEntries(
				fdsName, _httpServletRequest)
		).thenReturn(
			objectEntries
		);
	}

	private void _resetSerializer() throws Exception {
		_customFDSItemActionListSerializerImpl = Mockito.mock(
			CustomFDSItemActionListSerializerImpl.class);
	}

	private static CustomFDSItemActionListSerializerImpl
		_customFDSItemActionListSerializerImpl;
	private static final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);

}
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.action;

import com.liferay.frontend.data.set.internal.serializer.BaseCustomFDSSerializer;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
public class CustomFDSCreationMenuSerializerImplTest {

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

		// different creation menu

		_mockFDSCreationMenu(new String[] {"New 1.1", "New 1.2"}, "fdsName1");

		_mockFDSCreationMenu(new String[] {"New 2"}, "fdsName2");

		CreationMenu creationMenu1 =
			_customFDSCreationMenuSerializerImpl.serialize(
				"fdsName1", _httpServletRequest);

		CreationMenu creationMenu2 =
			_customFDSCreationMenuSerializerImpl.serialize(
				"fdsName2", _httpServletRequest);

		Assert.assertTrue(_containsTitle(creationMenu1, "New 1.1"));
		Assert.assertTrue(_containsTitle(creationMenu1, "New 1.2"));
		Assert.assertEquals(2, _itemCount(creationMenu1));

		Assert.assertTrue(_containsTitle(creationMenu2, "New 2"));
		Assert.assertEquals(1, _itemCount(creationMenu2));

		Assert.assertFalse(_containsTitle(creationMenu1, "New 2"));
		Assert.assertFalse(_containsTitle(creationMenu2, "New 1.1"));
		Assert.assertFalse(_containsTitle(creationMenu2, "New 1.2"));

		_resetSerializer();

		// no creation menu

		_mockFDSCreationMenu(null, "fdsName");

		Assert.assertTrue(
			_customFDSCreationMenuSerializerImpl.serialize(
				"fdsName", _httpServletRequest
			).isEmpty());

		_resetSerializer();

		// shared creation menu

		String[] titles = {"New A", "New B"};

		String[] fdsNames = {"fdsName1", "fdsName2"};

		for (String fdsName : fdsNames) {
			_mockFDSCreationMenu(titles, fdsName);

			CreationMenu creationMenu =
				_customFDSCreationMenuSerializerImpl.serialize(
					fdsName, _httpServletRequest);

			for (String title : titles) {
				Assert.assertTrue(_containsTitle(creationMenu, title));
			}

			Assert.assertEquals(titles.length, _itemCount(creationMenu));
		}
	}

	private boolean _containsTitle(CreationMenu creationMenu, String title) {
		for (DropdownItem dropdownItem :
				(List<DropdownItem>)creationMenu.get("primaryItems")) {

			Map<String, Object> data = (Map<String, Object>)dropdownItem.get(
				"data");

			if (title.equals((String)data.get("title"))) {
				return true;
			}
		}

		return false;
	}

	private int _itemCount(CreationMenu creationMenu) {
		List<DropdownItem> dropdownItems = (List<DropdownItem>)creationMenu.get(
			"primaryItems");

		return dropdownItems.size();
	}

	private void _mockFDSCreationMenu(
		String[] dropdownItemTitles, String fdsName) {

		Mockito.when(
			_customFDSCreationMenuSerializerImpl.serialize(
				fdsName, _httpServletRequest)
		).thenCallRealMethod();

		BaseCustomFDSSerializer baseCustomFDSSerializer =
			(BaseCustomFDSSerializer)_customFDSCreationMenuSerializerImpl;

		if (ArrayUtil.isEmpty(dropdownItemTitles)) {
			Mockito.when(
				baseCustomFDSSerializer.getCreationMenuObjectEntries(
					fdsName, _httpServletRequest)
			).thenReturn(
				Collections.emptySet()
			);

			return;
		}

		Set<ObjectEntry> objectEntries = new HashSet<>();

		for (String dropdownItemTitle : dropdownItemTitles) {
			ObjectEntry objectEntry = new ObjectEntry();

			objectEntry.setProperties(
				HashMapBuilder.put(
					"title", (Object)dropdownItemTitle
				).build());

			objectEntries.add(objectEntry);
		}

		Mockito.when(
			baseCustomFDSSerializer.getCreationMenuObjectEntries(
				fdsName, _httpServletRequest)
		).thenReturn(
			objectEntries
		);
	}

	private void _resetSerializer() {
		_customFDSCreationMenuSerializerImpl = Mockito.mock(
			CustomFDSCreationMenuSerializerImpl.class);
	}

	private static CustomFDSCreationMenuSerializerImpl
		_customFDSCreationMenuSerializerImpl;
	private static final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);

}
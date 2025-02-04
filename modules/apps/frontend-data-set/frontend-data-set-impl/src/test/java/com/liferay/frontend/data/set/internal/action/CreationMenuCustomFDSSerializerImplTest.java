/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.action;

import com.liferay.frontend.data.set.internal.serializer.BaseCustomFDSSerializer;
import com.liferay.frontend.data.set.serializer.FDSSerializer;
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
public class CreationMenuCustomFDSSerializerImplTest {

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

		// Different creation menu

		_mockFDSCreationMenu("fdsName1", new String[] {"New 1.1", "New 1.2"});
		_mockFDSCreationMenu("fdsName2", new String[] {"New 2"});

		CreationMenu creationMenu1 = _fdsSerializer.serialize(
			"fdsName1", _httpServletRequest);

		Assert.assertEquals(2, _getPrimaryItemsSize(creationMenu1));
		Assert.assertFalse(_containsTitle(creationMenu1, "New 2"));
		Assert.assertTrue(_containsTitle(creationMenu1, "New 1.1"));
		Assert.assertTrue(_containsTitle(creationMenu1, "New 1.2"));

		CreationMenu creationMenu2 = _fdsSerializer.serialize(
			"fdsName2", _httpServletRequest);

		Assert.assertEquals(1, _getPrimaryItemsSize(creationMenu2));
		Assert.assertFalse(_containsTitle(creationMenu2, "New 1.1"));
		Assert.assertFalse(_containsTitle(creationMenu2, "New 1.2"));
		Assert.assertTrue(_containsTitle(creationMenu2, "New 2"));

		_resetSerializer();

		// No creation menu

		_mockFDSCreationMenu("fdsName", null);

		Assert.assertTrue(
			_fdsSerializer.serialize(
				"fdsName", _httpServletRequest
			).isEmpty());

		_resetSerializer();

		// Shared creation menu

		String[] titles = {"New A", "New B"};

		_testSerialize("fdsName1", titles);
		_testSerialize("fdsName2", titles);
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

	private int _getPrimaryItemsSize(CreationMenu creationMenu) {
		List<DropdownItem> dropdownItems = (List<DropdownItem>)creationMenu.get(
			"primaryItems");

		return dropdownItems.size();
	}

	private void _mockFDSCreationMenu(String fdsName, String[] titles) {
		Mockito.when(
			_fdsSerializer.serialize(fdsName, _httpServletRequest)
		).thenCallRealMethod();

		BaseCustomFDSSerializer baseCustomFDSSerializer =
			(BaseCustomFDSSerializer)_fdsSerializer;

		if (ArrayUtil.isEmpty(titles)) {
			Mockito.when(
				baseCustomFDSSerializer.getCreationMenuObjectEntries(
					fdsName, _httpServletRequest)
			).thenReturn(
				Collections.emptySet()
			);

			return;
		}

		Set<ObjectEntry> objectEntries = new HashSet<>();

		for (String title : titles) {
			ObjectEntry objectEntry = new ObjectEntry();

			objectEntry.setProperties(
				HashMapBuilder.put(
					"title", (Object)title
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
		_fdsSerializer = Mockito.mock(
			CreationMenuCustomFDSSerializerImpl.class);
	}

	private void _testSerialize(String fdsName, String[] titles)
		throws Exception {

		_mockFDSCreationMenu(fdsName, titles);

		CreationMenu creationMenu = _fdsSerializer.serialize(
			fdsName, _httpServletRequest);

		for (String title : titles) {
			Assert.assertTrue(_containsTitle(creationMenu, title));
		}

		Assert.assertEquals(titles.length, _getPrimaryItemsSize(creationMenu));
	}

	private static FDSSerializer<CreationMenu> _fdsSerializer;
	private static final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);

}
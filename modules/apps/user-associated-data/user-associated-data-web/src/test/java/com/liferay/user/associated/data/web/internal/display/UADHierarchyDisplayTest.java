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

package com.liferay.user.associated.data.web.internal.display;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.user.associated.data.display.UADHierarchyDeclaration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Drew Brokke
 */
public class UADHierarchyDisplayTest {

	@Before
	public void setUp() {
		DummyEntryUADDisplay dummyEntryUADDisplay = new DummyEntryUADDisplay(
			_dummyEntryService);
		DummyContainerUADDisplay dummyContainerUADDisplay =
			new DummyContainerUADDisplay(_dummyContainerService);

		UADHierarchyDeclaration uadHierarchyDeclaration =
			new DummyUADHierarchyDeclaration(
				dummyEntryUADDisplay, dummyContainerUADDisplay);

		_uadHierarchyDisplay = new UADHierarchyDisplay(uadHierarchyDeclaration);

		_folderA = _dummyContainerService.create("dummyContainerA", _USER_ID);

		_dummyEntryService.create(
			"dummyEntryAA", _USER_ID_OTHER, _folderA.getId());
		_dummyEntryService.create("dummyEntryAB", _USER_ID, _folderA.getId());

		DummyContainer folderAA = _dummyContainerService.create(
			"dummyContainerAA", _USER_ID_OTHER, _folderA.getId());
		DummyContainer folderAB = _dummyContainerService.create(
			"dummyContainerAB", _USER_ID, _folderA.getId());

		_dummyEntryService.create(
			"dummyEntryAAA", _USER_ID_OTHER, folderAA.getId());
		_dummyEntryService.create(
			"dummyEntryABA", _USER_ID_OTHER, folderAB.getId());
		_dummyEntryService.create("dummyEntryAAB", _USER_ID, folderAA.getId());
		_dummyEntryService.create("dummyEntryABB", _USER_ID, folderAB.getId());

		_userFolderAndItemCountMap.put(_folderA.getId(), 3);
		_userFolderAndItemCountMap.put(folderAA.getId(), 1);
		_userFolderAndItemCountMap.put(folderAB.getId(), 1);

		DummyContainer folderB = _dummyContainerService.create(
			"dummyContainerB", _USER_ID);

		_dummyEntryService.create(
			"dummyEntryBA", _USER_ID_OTHER, folderB.getId());
		_dummyEntryService.create("dummyEntryBB", _USER_ID, folderB.getId());
		_dummyEntryService.create("dummyEntryBC", _USER_ID, folderB.getId());

		DummyContainer folderBA = _dummyContainerService.create(
			"dummyContainerBA", _USER_ID_OTHER, folderB.getId());
		DummyContainer folderBB = _dummyContainerService.create(
			"dummyContainerBA", _USER_ID, folderB.getId());

		_dummyEntryService.create(
			"dummyContainerBAA", _USER_ID_OTHER, folderBA.getId());
		_dummyEntryService.create(
			"dummyContainerBAB", _USER_ID, folderBA.getId());

		_userFolderAndItemCountMap.put(folderB.getId(), 4);
		_userFolderAndItemCountMap.put(folderBA.getId(), 1);
		_userFolderAndItemCountMap.put(folderBB.getId(), 0);

		DummyContainer folderC = _dummyContainerService.create(
			"dummyContainerC", _USER_ID_OTHER);

		_userFolderAndItemCountMap.put(folderC.getId(), 0);

		_dummyEntryService.create(
			"rootEntry", _USER_ID, DummyService.DEFAULT_CONTAINER_ID);

		_userFolderAndItemCountMap.put(DummyService.DEFAULT_CONTAINER_ID, 3);
	}

	@Test
	public void testCountAll() {
		Assert.assertEquals(11, _uadHierarchyDisplay.countAll(_USER_ID));
		Assert.assertEquals(8, _uadHierarchyDisplay.countAll(_USER_ID_OTHER));
	}

	@Test
	public void testFieldValueCount() throws Exception {
		List items = _uadHierarchyDisplay.search(
			DummyContainer.class, DummyService.DEFAULT_CONTAINER_ID, _USER_ID,
			null, "", null, null, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (Object item : items) {
			Map<String, Object> fieldValues =
				_uadHierarchyDisplay.getFieldValues(
					item, LocaleUtil.getDefault());

			String uuid = (String)fieldValues.get("uuid");

			Assert.assertNotNull(uuid);

			if (StringUtil.equals(uuid, _folderA.getUuid())) {
				Long count = (Long)fieldValues.get("count");

				Assert.assertEquals(4, count.intValue());
			}
		}
	}

	@Test
	public void testSearch() throws Exception {
		for (DummyContainer dummyContainer :
				_dummyContainerService.getEntities()) {

			List items = _uadHierarchyDisplay.search(
				DummyContainer.class, dummyContainer.getId(), _USER_ID, null,
				"", "name", "asc", QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			Assert.assertEquals(
				(int)_userFolderAndItemCountMap.get(dummyContainer.getId()),
				items.size());
		}
	}

	private static final long _USER_ID = 100;

	private static final long _USER_ID_OTHER = 200;

	private final DummyService<DummyContainer> _dummyContainerService =
		new DummyService<>(200, DummyContainer::new);
	private final DummyService<DummyEntry> _dummyEntryService =
		new DummyService<>(100, DummyEntry::new);
	private DummyContainer _folderA;
	private UADHierarchyDisplay _uadHierarchyDisplay;
	private final Map<Long, Integer> _userFolderAndItemCountMap =
		new HashMap<>();

}
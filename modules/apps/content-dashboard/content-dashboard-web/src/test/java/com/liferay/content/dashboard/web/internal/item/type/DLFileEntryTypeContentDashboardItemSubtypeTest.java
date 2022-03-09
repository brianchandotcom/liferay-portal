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

package com.liferay.content.dashboard.web.internal.item.type;

import com.liferay.content.dashboard.web.internal.info.item.provider.util.ClassNameClassPKInfoItemIdentifier;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.info.item.InfoItemReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Date;
import java.util.Locale;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Cristina González
 */
public class DLFileEntryTypeContentDashboardItemSubtypeTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testCreation() throws PortalException {
		DLFileEntryType dLFileEntryType = _getDLFileEntryType(
			"fileEntryTypeName");

		DLFileEntryTypeContentDashboardItemSubtype
			dLFileEntryTypeContentDashboardItemSubtype =
				new DLFileEntryTypeContentDashboardItemSubtype(
					dLFileEntryType, _getGroup("groupName"));

		Assert.assertEquals(
			"fileEntryTypeName (groupName)",
			dLFileEntryTypeContentDashboardItemSubtype.getFullLabel(
				LocaleUtil.US));
		Assert.assertEquals(
			"fileEntryTypeName",
			dLFileEntryTypeContentDashboardItemSubtype.getLabel(LocaleUtil.US));
		Assert.assertEquals(
			dLFileEntryType.getModifiedDate(),
			dLFileEntryTypeContentDashboardItemSubtype.getModifiedDate());
		Assert.assertEquals(
			dLFileEntryType.getUserId(),
			dLFileEntryTypeContentDashboardItemSubtype.getUserId());

		InfoItemReference infoItemReference =
			dLFileEntryTypeContentDashboardItemSubtype.getInfoItemReference();

		Assert.assertEquals(
			FileEntry.class.getName(), infoItemReference.getClassName());

		ClassNameClassPKInfoItemIdentifier classNameClassPKInfoItemIdentifier =
			(ClassNameClassPKInfoItemIdentifier)
				infoItemReference.getInfoItemIdentifier();

		Assert.assertEquals(
			DLFileEntryType.class.getName(),
			classNameClassPKInfoItemIdentifier.getClassName());
		Assert.assertEquals(
			dLFileEntryType.getFileEntryTypeId(),
			classNameClassPKInfoItemIdentifier.getClassPK());
	}

	@Test
	public void testEquals() throws PortalException {
		DLFileEntryType dLFileEntryType = _getDLFileEntryType(
			"fileEntryTypeName");

		DLFileEntryTypeContentDashboardItemSubtype
			dLFileEntryTypeContentDashboardItemSubtype1 =
				new DLFileEntryTypeContentDashboardItemSubtype(
					dLFileEntryType, _getGroup("groupName"));
		DLFileEntryTypeContentDashboardItemSubtype
			dLFileEntryTypeContentDashboardItemSubtype2 =
				new DLFileEntryTypeContentDashboardItemSubtype(
					dLFileEntryType, _getGroup("groupName"));

		Assert.assertTrue(
			dLFileEntryTypeContentDashboardItemSubtype1.equals(
				dLFileEntryTypeContentDashboardItemSubtype2));
	}

	@Test
	public void testNoEquals() throws PortalException {
		DLFileEntryType dLFileEntryType1 = _getDLFileEntryType(
			"fileEntryTypeName");

		DLFileEntryTypeContentDashboardItemSubtype
			dLFileEntryTypeContentDashboardItemSubtype1 =
				new DLFileEntryTypeContentDashboardItemSubtype(
					dLFileEntryType1, _getGroup("groupName"));

		DLFileEntryType dLFileEntryType2 = _getDLFileEntryType(
			"fileEntryTypeName");

		DLFileEntryTypeContentDashboardItemSubtype
			dLFileEntryTypeContentDashboardItemSubtype2 =
				new DLFileEntryTypeContentDashboardItemSubtype(
					dLFileEntryType2, _getGroup("groupName"));

		Assert.assertFalse(
			dLFileEntryTypeContentDashboardItemSubtype1.equals(
				dLFileEntryTypeContentDashboardItemSubtype2));
	}

	@Test
	public void testToJSONString() throws PortalException {
		DLFileEntryType dLFileEntryType = _getDLFileEntryType(
			"fileEntryTypeName");

		DLFileEntryTypeContentDashboardItemSubtype
			dLFileEntryTypeContentDashboardItemSubtype =
				new DLFileEntryTypeContentDashboardItemSubtype(
					dLFileEntryType, _getGroup("groupName"));

		InfoItemReference infoItemReference =
			dLFileEntryTypeContentDashboardItemSubtype.getInfoItemReference();

		ClassNameClassPKInfoItemIdentifier classNameClassPKInfoItemIdentifier =
			(ClassNameClassPKInfoItemIdentifier)
				infoItemReference.getInfoItemIdentifier();

		Assert.assertEquals(
			JSONUtil.put(
				"className", classNameClassPKInfoItemIdentifier.getClassName()
			).put(
				"classPK", classNameClassPKInfoItemIdentifier.getClassPK()
			).put(
				"entryClassName", infoItemReference.getClassName()
			).put(
				"title",
				dLFileEntryTypeContentDashboardItemSubtype.getFullLabel(
					LocaleUtil.US)
			).toJSONString(),
			dLFileEntryTypeContentDashboardItemSubtype.toJSONString(
				LocaleUtil.US));
	}

	private DLFileEntryType _getDLFileEntryType(String name) {
		DLFileEntryType dLFileEntryType = Mockito.mock(DLFileEntryType.class);

		Mockito.when(
			dLFileEntryType.getName(Mockito.any(Locale.class))
		).thenReturn(
			name
		);

		Mockito.when(
			dLFileEntryType.getModifiedDate()
		).thenReturn(
			new Date()
		);

		Mockito.when(
			dLFileEntryType.getFileEntryTypeId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			dLFileEntryType.getUserId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		return dLFileEntryType;
	}

	private Group _getGroup(String name) throws PortalException {
		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.getDescriptiveName(Mockito.any(Locale.class))
		).thenReturn(
			name
		);

		return group;
	}

}
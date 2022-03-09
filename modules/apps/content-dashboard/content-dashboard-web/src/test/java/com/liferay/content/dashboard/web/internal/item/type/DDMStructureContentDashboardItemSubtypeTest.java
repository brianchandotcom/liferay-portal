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
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.info.item.InfoItemReference;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
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
public class DDMStructureContentDashboardItemSubtypeTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testCreation() throws PortalException {
		DDMStructure ddmStructure = _getDDMStructure("structureName");

		DDMStructureContentDashboardItemSubtype
			ddmStructureContentDashboardItemSubtype =
				new DDMStructureContentDashboardItemSubtype(
					ddmStructure, _getGroup("groupName"));

		Assert.assertEquals(
			"structureName (groupName)",
			ddmStructureContentDashboardItemSubtype.getFullLabel(
				LocaleUtil.US));
		Assert.assertEquals(
			"structureName",
			ddmStructureContentDashboardItemSubtype.getLabel(LocaleUtil.US));

		InfoItemReference infoItemReference =
			ddmStructureContentDashboardItemSubtype.getInfoItemReference();

		Assert.assertEquals(
			JournalArticle.class.getName(), infoItemReference.getClassName());

		ClassNameClassPKInfoItemIdentifier classNameClassPKInfoItemIdentifier =
			(ClassNameClassPKInfoItemIdentifier)
				infoItemReference.getInfoItemIdentifier();

		Assert.assertEquals(
			DDMStructure.class.getName(),
			classNameClassPKInfoItemIdentifier.getClassName());
		Assert.assertEquals(
			ddmStructure.getStructureId(),
			classNameClassPKInfoItemIdentifier.getClassPK());
	}

	@Test
	public void testEquals() throws PortalException {
		DDMStructure ddmStructure = _getDDMStructure("structureName");

		DDMStructureContentDashboardItemSubtype
			ddmStructureContentDashboardItemSubtype1 =
				new DDMStructureContentDashboardItemSubtype(
					ddmStructure, _getGroup("groupName"));
		DDMStructureContentDashboardItemSubtype
			ddmStructureContentDashboardItemSubtype2 =
				new DDMStructureContentDashboardItemSubtype(
					ddmStructure, _getGroup("groupName"));

		Assert.assertTrue(
			ddmStructureContentDashboardItemSubtype1.equals(
				ddmStructureContentDashboardItemSubtype2));
	}

	@Test
	public void testNoEquals() throws PortalException {
		DDMStructure ddmStructure1 = _getDDMStructure("structureName");

		DDMStructureContentDashboardItemSubtype
			ddmStructureContentDashboardItemSubtype1 =
				new DDMStructureContentDashboardItemSubtype(
					ddmStructure1, _getGroup("groupName"));

		DDMStructure ddmStructure2 = _getDDMStructure("structureName");

		DDMStructureContentDashboardItemSubtype
			ddmStructureContentDashboardItemSubtype2 =
				new DDMStructureContentDashboardItemSubtype(
					ddmStructure2, _getGroup("groupName"));

		Assert.assertFalse(
			ddmStructureContentDashboardItemSubtype1.equals(
				ddmStructureContentDashboardItemSubtype2));
	}

	@Test
	public void testToJSONString() throws PortalException {
		DDMStructure ddmStructure = _getDDMStructure("structureName");

		DDMStructureContentDashboardItemSubtype
			ddmStructureContentDashboardItemSubtype =
				new DDMStructureContentDashboardItemSubtype(
					ddmStructure, _getGroup("groupName"));

		InfoItemReference infoItemReference =
			ddmStructureContentDashboardItemSubtype.getInfoItemReference();

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
				ddmStructureContentDashboardItemSubtype.getFullLabel(
					LocaleUtil.US)
			).toJSONString(),
			ddmStructureContentDashboardItemSubtype.toJSONString(
				LocaleUtil.US));
	}

	private DDMStructure _getDDMStructure(String name) {
		DDMStructure ddmStructure = Mockito.mock(DDMStructure.class);

		Mockito.when(
			ddmStructure.getName(Mockito.any(Locale.class))
		).thenReturn(
			name
		);

		Mockito.when(
			ddmStructure.getModifiedDate()
		).thenReturn(
			new Date()
		);

		Mockito.when(
			ddmStructure.getStructureId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			ddmStructure.getUserId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		return ddmStructure;
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
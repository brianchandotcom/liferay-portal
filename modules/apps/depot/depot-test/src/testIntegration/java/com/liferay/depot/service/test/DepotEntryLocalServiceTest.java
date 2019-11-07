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

package com.liferay.depot.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.exception.DepotEntryNameException;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class DepotEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddDepotEntry() throws Exception {
		DepotEntry depotEntry = _addDepotEntry("name", "description");

		Group group = _groupLocalService.getGroup(depotEntry.getGroupId());

		Assert.assertEquals(DepotEntry.class.getName(), group.getClassName());
		Assert.assertEquals(depotEntry.getDepotEntryId(), group.getClassPK());
		Assert.assertEquals(
			"description", group.getDescription(LocaleUtil.getDefault()));
		Assert.assertEquals("name", group.getName(LocaleUtil.getDefault()));
		Assert.assertEquals(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, group.getParentGroupId());
		Assert.assertEquals(GroupConstants.TYPE_DEPOT, group.getType());
		Assert.assertFalse(group.isSite());
	}

	@Test
	public void testAddDepotEntryWithNullName() throws Exception {
		try {
			_addDepotEntry(null, null);

			Assert.fail();
		}
		catch (DepotEntryNameException dene) {
		}
	}

	@Test(expected = NoSuchGroupException.class)
	public void testDeleteDepotEntry() throws Exception {
		DepotEntry depotEntry = _addDepotEntry("name", "description");

		_depotEntryLocalService.deleteDepotEntry(depotEntry);

		_depotEntries.remove(depotEntry);

		_groupLocalService.getGroup(depotEntry.getGroupId());
	}

	@Test
	public void testUpdateDepotEntry() throws Exception {
		DepotEntry depotEntry = _addDepotEntry("name", "description");

		UnicodeProperties formTypeSettingsProperties = new UnicodeProperties();

		formTypeSettingsProperties.put(
			PropsKeys.LOCALES,
			LocaleUtil.toLanguageId(LocaleUtil.getDefault()));

		_depotEntryLocalService.updateDepotEntry(
			depotEntry.getDepotEntryId(),
			HashMapBuilder.<Locale, String>put(
				LocaleUtil.getDefault(), "newName"
			).build(),
			HashMapBuilder.<Locale, String>put(
				LocaleUtil.getDefault(), "newDescription"
			).build(),
			formTypeSettingsProperties,
			ServiceContextTestUtil.getServiceContext());

		Group group = _groupLocalService.getGroup(depotEntry.getGroupId());

		Assert.assertEquals(
			"newDescription", group.getDescription(LocaleUtil.getDefault()));
		Assert.assertEquals("newName", group.getName(LocaleUtil.getDefault()));
	}

	@Test
	public void testUpdateDepotEntryDeleteDefaultLocale() throws Exception {
		DepotEntry depotEntry = _addDepotEntry("name", "description");

		UnicodeProperties formTypeSettingsProperties = new UnicodeProperties();

		Set<Locale> availableLocales = new HashSet<>();

		availableLocales.add(LocaleUtil.getDefault());
		availableLocales.add(LocaleUtil.fromLanguageId("es_ES"));

		String[] locales = LocaleUtil.toLanguageIds(availableLocales);

		formTypeSettingsProperties.setProperty(
			PropsKeys.LOCALES, StringUtil.merge(locales));

		_depotEntryLocalService.updateDepotEntry(
			depotEntry.getDepotEntryId(),
			HashMapBuilder.<Locale, String>put(
				LocaleUtil.getDefault(), "newName"
			).put(
				LocaleUtil.fromLanguageId("es_ES"), "nuevoNombre"
			).build(),
			HashMapBuilder.<Locale, String>put(
				LocaleUtil.getDefault(), "newDescription"
			).put(
				LocaleUtil.fromLanguageId("es_ES"), "nuevaDescripcion"
			).build(),
			formTypeSettingsProperties,
			ServiceContextTestUtil.getServiceContext());

		Group group = _groupLocalService.getGroup(depotEntry.getGroupId());

		Assert.assertEquals(
			"nuevaDescripcion",
			group.getDescription(LocaleUtil.fromLanguageId("es_ES")));
		Assert.assertEquals(
			"nuevoNombre", group.getName(LocaleUtil.fromLanguageId("es_ES")));
	}

	@Test
	public void testUpdateDepotEntryInheritLocale() throws Exception {
		DepotEntry depotEntry = _addDepotEntry("name", "description");

		UnicodeProperties formTypeSettingsProperties = new UnicodeProperties();

		formTypeSettingsProperties.setProperty("inheritLocales", "true");

		_depotEntryLocalService.updateDepotEntry(
			depotEntry.getDepotEntryId(),
			HashMapBuilder.<Locale, String>put(
				LocaleUtil.getDefault(), "newName"
			).build(),
			HashMapBuilder.<Locale, String>put(
				LocaleUtil.getDefault(), "newDescription"
			).build(),
			formTypeSettingsProperties,
			ServiceContextTestUtil.getServiceContext());

		Group group = _groupLocalService.getGroup(depotEntry.getGroupId());

		UnicodeProperties typeSettingsProperties =
			group.getTypeSettingsProperties();

		Assert.assertEquals(
			true,
			GetterUtil.getBoolean(
				typeSettingsProperties.getProperty("inheritLocales")));
		Assert.assertEquals(
			StringUtil.merge(
				LocaleUtil.toLanguageIds(_languaje.getAvailableLocales())),
			typeSettingsProperties.getProperty("locales"));
	}

	@Test
	public void testUpdateDepotEntryNoDescription() throws Exception {
		DepotEntry depotEntry = _addDepotEntry("name", "description");

		UnicodeProperties formTypeSettingsProperties = new UnicodeProperties();

		formTypeSettingsProperties.put(
			PropsKeys.LOCALES,
			LocaleUtil.toLanguageId(LocaleUtil.getDefault()));

		_depotEntryLocalService.updateDepotEntry(
			depotEntry.getDepotEntryId(),
			HashMapBuilder.<Locale, String>put(
				LocaleUtil.getDefault(), "newName"
			).build(),
			new HashMap<>(), formTypeSettingsProperties,
			ServiceContextTestUtil.getServiceContext());

		Group group = _groupLocalService.getGroup(depotEntry.getGroupId());

		Assert.assertEquals("newName", group.getName(LocaleUtil.getDefault()));
		Assert.assertEquals(
			StringPool.BLANK, group.getDescription(LocaleUtil.getDefault()));
	}

	@Test
	public void testUpdateDepotEntryNoName() throws Exception {
		DepotEntry depotEntry = _addDepotEntry("name", "description");

		_depotEntryLocalService.updateDepotEntry(
			depotEntry.getDepotEntryId(), new HashMap<>(), new HashMap<>(),
			new UnicodeProperties(),
			ServiceContextTestUtil.getServiceContext());

		Group group = _groupLocalService.getGroup(depotEntry.getGroupId());

		Assert.assertEquals(
			"Unnamed Repository", group.getName(LocaleUtil.getDefault()));
	}

	@Test(expected = LocaleException.class)
	public void testUpdateDepotEntryRequiresValidTypeSettingProperties()
		throws Exception {

		DepotEntry depotEntry = _addDepotEntry("name", "description");

		UnicodeProperties formTypeSettingsProperties = new UnicodeProperties();

		formTypeSettingsProperties.setProperty("inheritLocales", "false");
		formTypeSettingsProperties.setProperty(PropsKeys.LOCALES, null);

		_depotEntryLocalService.updateDepotEntry(
			depotEntry.getDepotEntryId(),
			HashMapBuilder.<Locale, String>put(
				LocaleUtil.getDefault(), "newName"
			).put(
				LocaleUtil.fromLanguageId("es_ES"), "nuevoNombre"
			).build(),
			HashMapBuilder.<Locale, String>put(
				LocaleUtil.getDefault(), "newDescription"
			).put(
				LocaleUtil.fromLanguageId("es_ES"), "descripcion"
			).build(),
			formTypeSettingsProperties,
			ServiceContextTestUtil.getServiceContext());
	}

	private DepotEntry _addDepotEntry(String name, String description)
		throws Exception {

		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.<Locale, String>put(
				LocaleUtil.getDefault(), name
			).build(),
			HashMapBuilder.<Locale, String>put(
				LocaleUtil.getDefault(), description
			).build(),
			ServiceContextTestUtil.getServiceContext());

		_depotEntries.add(depotEntry);

		return depotEntry;
	}

	@DeleteAfterTestRun
	private final List<DepotEntry> _depotEntries = new ArrayList<>();

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private Language _languaje;

}
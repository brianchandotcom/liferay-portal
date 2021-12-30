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

package com.liferay.dynamic.data.mapping.form.taglib.internal.servlet.taglib.util;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.model.impl.DDMStructureImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMStructureVersionImpl;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Pedro Queiroz
 */
@RunWith(PowerMockRunner.class)
public class DDMFormTaglibUtilTest {

	@Before
	public void setUp() throws Exception {
		_setUpDDMStructureLocalService();
		_setUpDDMStructureVersionLocalService();
	}

	@Test
	public void testGetDDMFormFromDDMStructure() {
		DDMForm ddmForm = _ddmStructure.getDDMForm();

		Assert.assertTrue(
			ddmForm.equals(
				_ddmFormTaglibUtil.getDDMForm(
					_ddmStructure.getStructureId(), 0)));
	}

	@Test
	public void testGetDDMFormFromDDMStructureVersion1() {
		DDMForm ddmForm = _ddmStructureVersion.getDDMForm();

		Assert.assertTrue(
			ddmForm.equals(
				_ddmFormTaglibUtil.getDDMForm(
					0, _ddmStructureVersion.getStructureId())));
	}

	@Test
	public void testGetDDMFormFromDDMStructureVersion2() {
		DDMForm ddmForm = _ddmStructureVersion.getDDMForm();

		Assert.assertTrue(
			ddmForm.equals(
				_ddmFormTaglibUtil.getDDMForm(
					_ddmStructure.getStructureId(),
					_ddmStructureVersion.getStructureId())));
	}

	@Test
	public void testGetEmptyDDMFormTest() {
		Assert.assertEquals(new DDMForm(), _ddmFormTaglibUtil.getDDMForm(0, 0));
	}

	private DDMStructure _createDDMStructure(DDMForm ddmForm) {
		DDMStructure ddmStructure = new DDMStructureImpl();

		ddmStructure.setDDMForm(ddmForm);
		ddmStructure.setStructureId(RandomTestUtil.randomLong());
		ddmStructure.setName(RandomTestUtil.randomString());

		return ddmStructure;
	}

	private DDMStructureVersion _createDDMStructureVersion(DDMForm ddmForm) {
		DDMStructureVersion ddmStructureVersion = new DDMStructureVersionImpl();

		ddmStructureVersion.setDDMForm(ddmForm);
		ddmStructureVersion.setStructureId(RandomTestUtil.randomLong());
		ddmStructureVersion.setName(RandomTestUtil.randomString());

		return ddmStructureVersion;
	}

	private void _setUpDDMStructureLocalService() throws Exception {
		_ddmStructure = _createDDMStructure(
			DDMFormTestUtil.createDDMForm("Text"));

		Field field = ReflectionUtil.getDeclaredField(
			DDMFormTaglibUtil.class, "_ddmStructureLocalService");

		Mockito.when(
			_ddmStructureLocalService.fetchDDMStructure(Mockito.anyLong())
		).thenReturn(
			_ddmStructure
		);

		field.set(_ddmFormTaglibUtil, _ddmStructureLocalService);
	}

	private void _setUpDDMStructureVersionLocalService() throws Exception {
		_ddmStructureVersion = _createDDMStructureVersion(
			DDMFormTestUtil.createDDMForm("Text1", "Text2"));

		Field field = ReflectionUtil.getDeclaredField(
			DDMFormTaglibUtil.class, "_ddmStructureVersionLocalService");

		Mockito.when(
			_ddmStructureVersionLocalService.fetchDDMStructureVersion(
				Mockito.anyLong())
		).thenReturn(
			_ddmStructureVersion
		);

		field.set(_ddmFormTaglibUtil, _ddmStructureVersionLocalService);
	}

	private final DDMFormTaglibUtil _ddmFormTaglibUtil =
		new DDMFormTaglibUtil();
	private DDMStructure _ddmStructure;

	@Mock
	private DDMStructureLocalService _ddmStructureLocalService;

	private DDMStructureVersion _ddmStructureVersion;

	@Mock
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;

}
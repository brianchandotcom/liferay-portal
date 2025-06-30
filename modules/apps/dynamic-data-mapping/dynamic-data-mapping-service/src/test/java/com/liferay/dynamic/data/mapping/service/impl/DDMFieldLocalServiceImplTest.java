/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.service.impl;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.impl.DDMFieldImpl;
import com.liferay.dynamic.data.mapping.service.DDMFieldLocalService;
import com.liferay.dynamic.data.mapping.service.persistence.DDMFieldAttributePersistence;
import com.liferay.dynamic.data.mapping.service.persistence.DDMFieldPersistence;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Anderson Luiz
 */
public class DDMFieldLocalServiceImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		Mockito.when(
			_ddmFieldPersistence.findByStorageId(Mockito.anyLong())
		).thenReturn(
			ListUtil.fromArray(new DDMFieldImpl())
		);

		Mockito.when(
			_ddmFieldAttributePersistence.findByStorageId(Mockito.anyLong())
		).thenReturn(
			Collections.emptyList()
		);
	}

	@Test
	public void testGetDDMFormValues() {
		Assert.assertNotNull(
			_ddmFieldLocalService.getDDMFormValues(
				new DDMForm(), RandomTestUtil.randomLong()));
	}

	@Mock
	private DDMFieldAttributePersistence _ddmFieldAttributePersistence;

	@InjectMocks
	private DDMFieldLocalService _ddmFieldLocalService =
		new DDMFieldLocalServiceImpl();

	@Mock
	private DDMFieldPersistence _ddmFieldPersistence;

}
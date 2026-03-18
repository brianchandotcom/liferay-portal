/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model.impl;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.bag.ObjectFieldBag;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.Serializable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Mikel Lorza
 * @author Nícolas Moura
 */
public class ObjectEntryImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_setUpObjectDefinition();
		_setUpObjectEntryImpl();
		_setUpObjectField();
		_setUpObjectFieldBag();
	}

	@Test
	public void testGetTitleValue() throws Exception {
		Assert.assertNull(_objectEntryImpl.getTitleValue("pt_BR", false));

		Mockito.when(
			_objectField.getI18nObjectFieldName()
		).thenReturn(
			"title_i18n"
		);

		Mockito.when(
			_objectField.isLocalized()
		).thenReturn(
			true
		);

		Assert.assertNull(_objectEntryImpl.getTitleValue("it_IT", false));
		Assert.assertEquals(
			"Title", _objectEntryImpl.getTitleValue("it_IT", true));
		Assert.assertEquals(
			"Título", _objectEntryImpl.getTitleValue("pt_BR", true));

		Mockito.doReturn(
			"pt_PT"
		).when(
			_objectEntryImpl
		).getDefaultLanguageId();

		Assert.assertNull(_objectEntryImpl.getTitleValue("it_IT", true));
	}

	private void _setUpObjectDefinition() {
		Mockito.when(
			_objectDefinition.getObjectFieldBag()
		).thenReturn(
			_objectFieldBag
		);

		Mockito.when(
			_objectDefinition.getTitleObjectFieldId()
		).thenReturn(
			1L
		);
	}

	private void _setUpObjectEntryImpl() {
		Mockito.doReturn(
			"en_US"
		).when(
			_objectEntryImpl
		).getDefaultLanguageId();

		Mockito.doReturn(
			HashMapBuilder.<String, Serializable>put(
				"title_i18n",
				HashMapBuilder.<String, Serializable>put(
					"en_US", "Title"
				).put(
					"pt_BR", "Título"
				).build()
			).build()
		).when(
			_objectEntryImpl
		).getIndexedValues();

		Mockito.doReturn(
			_objectDefinition
		).when(
			_objectEntryImpl
		).getObjectDefinition();
	}

	private void _setUpObjectField() {
		Mockito.when(
			_objectField.getName()
		).thenReturn(
			"title"
		);
	}

	private void _setUpObjectFieldBag() {
		Mockito.when(
			_objectFieldBag.getObjectField(1L)
		).thenReturn(
			_objectField
		);
	}

	private final ObjectDefinition _objectDefinition = Mockito.mock(
		ObjectDefinition.class);
	private final ObjectEntryImpl _objectEntryImpl = Mockito.spy(
		new ObjectEntryImpl());
	private final ObjectField _objectField = Mockito.mock(ObjectField.class);
	private final ObjectFieldBag _objectFieldBag = Mockito.mock(
		ObjectFieldBag.class);

}
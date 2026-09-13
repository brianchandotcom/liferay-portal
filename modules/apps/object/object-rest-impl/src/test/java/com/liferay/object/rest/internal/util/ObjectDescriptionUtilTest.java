/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.util;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Nathaly Gomes
 */
public class ObjectDescriptionUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		Mockito.when(
			_objectDefinition.getDefaultLanguageId()
		).thenReturn(
			_DEFAULT_LANGUAGE_ID
		);
	}

	@Test
	public void testGetDescriptionWhenDescriptionsAreBlank() {
		Mockito.when(
			_objectField.getDescription(_DEFAULT_LANGUAGE_ID, false)
		).thenReturn(
			StringPool.BLANK
		);

		Mockito.when(
			_objectField.getDescription(LocaleUtil.US, false)
		).thenReturn(
			StringPool.BLANK
		);

		Assert.assertNull(
			ObjectDescriptionUtil.getDescription(
				_objectDefinition, _objectField));
	}

	@Test
	public void testGetDescriptionWhenDescriptionsAreNull() {
		Assert.assertNull(
			ObjectDescriptionUtil.getDescription(
				_objectDefinition, _objectField));
	}

	@Test
	public void testGetDescriptionWhenEnglishDescriptionIsNull() {
		String description = RandomTestUtil.randomString();

		Mockito.when(
			_objectField.getDescription(_DEFAULT_LANGUAGE_ID, false)
		).thenReturn(
			description
		);

		Assert.assertEquals(
			description,
			ObjectDescriptionUtil.getDescription(
				_objectDefinition, _objectField));
	}

	@Test
	public void testGetDescriptionWhenEnglishDescriptionIsStoredInAnotherLanguage() {
		String description = RandomTestUtil.randomString();

		Mockito.when(
			_objectField.getDescription(_DEFAULT_LANGUAGE_ID, false)
		).thenReturn(
			description
		);

		Mockito.when(
			_objectField.getDescription(LocaleUtil.US, false)
		).thenReturn(
			StringPool.BLANK
		);

		Assert.assertEquals(
			description,
			ObjectDescriptionUtil.getDescription(
				_objectDefinition, _objectField));
	}

	@Test
	public void testGetDescriptionWithObjectDefinition() {
		String description = RandomTestUtil.randomString();

		Mockito.when(
			_objectDefinition.getDescription(_DEFAULT_LANGUAGE_ID, false)
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_objectDefinition.getDescription(LocaleUtil.US, false)
		).thenReturn(
			description
		);

		Assert.assertEquals(
			description,
			ObjectDescriptionUtil.getDescription(_objectDefinition));
	}

	@Test
	public void testGetDescriptionWithObjectField() {
		String description = RandomTestUtil.randomString();

		Mockito.when(
			_objectField.getDescription(_DEFAULT_LANGUAGE_ID, false)
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_objectField.getDescription(LocaleUtil.US, false)
		).thenReturn(
			description
		);

		Assert.assertEquals(
			description,
			ObjectDescriptionUtil.getDescription(
				_objectDefinition, _objectField));
	}

	@Test
	public void testGetDescriptionWithObjectRelationship() {
		String description = RandomTestUtil.randomString();

		Mockito.when(
			_objectRelationship.getDescription(_DEFAULT_LANGUAGE_ID, false)
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_objectRelationship.getDescription(LocaleUtil.US, false)
		).thenReturn(
			description
		);

		Assert.assertEquals(
			description,
			ObjectDescriptionUtil.getDescription(
				_objectDefinition, _objectRelationship));
	}

	private static final String _DEFAULT_LANGUAGE_ID = "pt_BR";

	private final ObjectDefinition _objectDefinition = Mockito.mock(
		ObjectDefinition.class);
	private final ObjectField _objectField = Mockito.mock(ObjectField.class);
	private final ObjectRelationship _objectRelationship = Mockito.mock(
		ObjectRelationship.class);

}
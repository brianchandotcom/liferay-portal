/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.model.listener;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.exception.ObjectDefinitionObjectFolderIdException;
import com.liferay.object.exception.ObjectDefinitionScopeException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Carolina Barbosa
 */
public class ObjectDefinitionModelListenerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testOnBeforeCreate() {
		AssertUtils.assertFailure(
			ModelListenerException.class,
			ObjectDefinitionScopeException.class.getName() +
				": CMS object definitions can only have scope \"depot\"",
			() -> _objectDefinitionModelListener.onBeforeCreate(
				_mockObjectDefinition(
					true, null, RandomTestUtil.randomString())));

		_objectDefinitionModelListener.onBeforeCreate(
			_mockObjectDefinition(
				true, null, ObjectDefinitionConstants.SCOPE_DEPOT));
	}

	@Test
	public void testOnBeforeUpdate() {
		AssertUtils.assertFailure(
			ModelListenerException.class,
			ObjectDefinitionObjectFolderIdException.class.getName() +
				": CMS object definitions cannot change their folder location",
			() -> _objectDefinitionModelListener.onBeforeUpdate(
				_mockObjectDefinition(
					true, RandomTestUtil.randomString(),
					ObjectDefinitionConstants.SCOPE_DEPOT),
				_mockObjectDefinition(
					true, RandomTestUtil.randomString(),
					ObjectDefinitionConstants.SCOPE_DEPOT)));
		AssertUtils.assertFailure(
			ModelListenerException.class,
			ObjectDefinitionScopeException.class.getName() +
				": CMS object definitions can only have scope \"depot\"",
			() -> _objectDefinitionModelListener.onBeforeUpdate(
				_mockObjectDefinition(
					RandomTestUtil.randomBoolean(), null, null),
				_mockObjectDefinition(
					true, null, RandomTestUtil.randomString())));

		String objectFolderExternalReferenceCode =
			RandomTestUtil.randomString();

		_objectDefinitionModelListener.onBeforeUpdate(
			_mockObjectDefinition(
				true, objectFolderExternalReferenceCode,
				ObjectDefinitionConstants.SCOPE_DEPOT),
			_mockObjectDefinition(
				true, objectFolderExternalReferenceCode,
				ObjectDefinitionConstants.SCOPE_DEPOT));

		_objectDefinitionModelListener.onBeforeUpdate(
			_mockObjectDefinition(RandomTestUtil.randomBoolean(), null, null),
			_mockObjectDefinition(
				true, null, ObjectDefinitionConstants.SCOPE_DEPOT));
	}

	private ObjectDefinition _mockObjectDefinition(
		boolean cms, String objectFolderExternalReferenceCode, String scope) {

		ObjectDefinition objectDefinition = Mockito.mock(
			ObjectDefinition.class);

		Mockito.when(
			objectDefinition.isCMS()
		).thenReturn(
			cms
		);

		Mockito.when(
			objectDefinition.getObjectFolderExternalReferenceCode()
		).thenReturn(
			objectFolderExternalReferenceCode
		);

		Mockito.when(
			objectDefinition.getScope()
		).thenReturn(
			scope
		);

		return objectDefinition;
	}

	private final ObjectDefinitionModelListener _objectDefinitionModelListener =
		new ObjectDefinitionModelListener();

}
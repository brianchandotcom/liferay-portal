/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.resource.v1_0;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.system.JaxRsApplicationDescriptor;
import com.liferay.object.system.SystemObjectDefinitionManager;
import com.liferay.object.system.SystemObjectDefinitionManagerRegistry;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.ws.rs.core.UriInfo;

import java.net.URI;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Gábor Komáromi
 */
public class RelatedObjectEntryResourceImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_companyId = RandomTestUtil.randomLong();

		_relatedObjectEntryResourceImpl = new RelatedObjectEntryResourceImpl();
		_objectDefinitionLocalService = Mockito.mock(
			ObjectDefinitionLocalService.class);
		_systemObjectDefinitionManagerRegistry = Mockito.mock(
			SystemObjectDefinitionManagerRegistry.class);
		_uriInfo = Mockito.mock(UriInfo.class);

		ReflectionTestUtil.setFieldValue(
			_relatedObjectEntryResourceImpl, "_objectDefinitionLocalService",
			_objectDefinitionLocalService);
		ReflectionTestUtil.setFieldValue(
			_relatedObjectEntryResourceImpl,
			"_systemObjectDefinitionManagerRegistry",
			_systemObjectDefinitionManagerRegistry);
		ReflectionTestUtil.setFieldValue(
			_relatedObjectEntryResourceImpl, "_uriInfo", _uriInfo);
	}

	@Test
	public void testGetSystemObjectDefinitionManagerWithCustomContextPath()
		throws Exception {

		SystemObjectDefinitionManager systemObjectDefinitionManager =
			_mockSystemObjectDefinitionManager(
				"/myportal/o/headless-admin-user/");

		Object result = ReflectionTestUtil.invoke(
			_relatedObjectEntryResourceImpl,
			"_getSystemObjectDefinitionManager",
			new Class<?>[] {long.class, String.class}, _companyId,
			"user-accounts");

		Assert.assertSame(systemObjectDefinitionManager, result);
	}

	@Test
	public void testGetSystemObjectDefinitionManagerWithRootContextPath()
		throws Exception {

		SystemObjectDefinitionManager systemObjectDefinitionManager =
			_mockSystemObjectDefinitionManager("/o/headless-admin-user/");

		Object result = ReflectionTestUtil.invoke(
			_relatedObjectEntryResourceImpl,
			"_getSystemObjectDefinitionManager",
			new Class<?>[] {long.class, String.class}, _companyId,
			"user-accounts");

		Assert.assertSame(systemObjectDefinitionManager, result);
	}

	private SystemObjectDefinitionManager _mockSystemObjectDefinitionManager(
			String basePath)
		throws Exception {

		Mockito.when(
			_uriInfo.getBaseUri()
		).thenReturn(
			URI.create("http://localhost:8080" + basePath)
		);

		ObjectDefinition objectDefinition = Mockito.mock(
			ObjectDefinition.class);

		Mockito.when(
			_objectDefinitionLocalService.
				getUnmodifiableSystemObjectDefinitions(_companyId)
		).thenReturn(
			Collections.singletonList(objectDefinition)
		);

		Mockito.when(
			objectDefinition.getName()
		).thenReturn(
			"User"
		);

		SystemObjectDefinitionManager systemObjectDefinitionManager =
			Mockito.mock(SystemObjectDefinitionManager.class);

		JaxRsApplicationDescriptor jaxRsApplicationDescriptor = Mockito.mock(
			JaxRsApplicationDescriptor.class);

		Mockito.when(
			_systemObjectDefinitionManagerRegistry.
				getSystemObjectDefinitionManager("User")
		).thenReturn(
			systemObjectDefinitionManager
		);

		Mockito.when(
			systemObjectDefinitionManager.getJaxRsApplicationDescriptor()
		).thenReturn(
			jaxRsApplicationDescriptor
		);

		Mockito.when(
			jaxRsApplicationDescriptor.getRESTContextPath()
		).thenReturn(
			"headless-admin-user/v1.0/user-accounts"
		);

		return systemObjectDefinitionManager;
	}

	private long _companyId;
	private ObjectDefinitionLocalService _objectDefinitionLocalService;
	private RelatedObjectEntryResourceImpl _relatedObjectEntryResourceImpl;
	private SystemObjectDefinitionManagerRegistry
		_systemObjectDefinitionManagerRegistry;
	private UriInfo _uriInfo;

}
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
import com.liferay.portal.kernel.util.PortalUtil;
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
		ReflectionTestUtil.setFieldValue(
			_relatedObjectEntryResourceImpl, "_objectDefinitionLocalService",
			_objectDefinitionLocalService);
		ReflectionTestUtil.setFieldValue(
			_relatedObjectEntryResourceImpl,
			"_systemObjectDefinitionManagerRegistry",
			_systemObjectDefinitionManagerRegistry);
		ReflectionTestUtil.setFieldValue(
			_relatedObjectEntryResourceImpl, "_uriInfo", _uriInfo);

		_setUpJaxRsApplicationDescriptor();
		_setUpObjectDefinition();
		_setUpObjectDefinitionLocalService();
		_setUpSystemObjectDefinitionManager();
		_setUpSystemObjectDefinitionManagerRegistry();
	}

	@Test
	public void testGetSystemObjectDefinitionManagerWithCustomContextPath()
		throws Exception {

		_setUpUriInfo("/myportal/o/headless-admin-user/");

		Object result = ReflectionTestUtil.invoke(
			_relatedObjectEntryResourceImpl,
			"_getSystemObjectDefinitionManager",
			new Class<?>[] {long.class, String.class}, _COMPANY_ID,
			"user-accounts");

		Assert.assertSame(_systemObjectDefinitionManager, result);
	}

	@Test
	public void testGetSystemObjectDefinitionManagerWithRootContextPath()
		throws Exception {

		_setUpUriInfo("/o/headless-admin-user/");

		Object result = ReflectionTestUtil.invoke(
			_relatedObjectEntryResourceImpl,
			"_getSystemObjectDefinitionManager",
			new Class<?>[] {long.class, String.class}, _COMPANY_ID,
			"user-accounts");

		Assert.assertSame(_systemObjectDefinitionManager, result);
	}

	private void _setUpJaxRsApplicationDescriptor() {
		Mockito.when(
			_jaxRsApplicationDescriptor.getRESTContextPath()
		).thenReturn(
			"headless-admin-user/v1.0/user-accounts"
		);
	}

	private void _setUpObjectDefinition() {
		Mockito.when(
			_objectDefinition.getName()
		).thenReturn(
			"User"
		);
	}

	private void _setUpObjectDefinitionLocalService() {
		Mockito.when(
			_objectDefinitionLocalService.
				getUnmodifiableSystemObjectDefinitions(_COMPANY_ID)
		).thenReturn(
			Collections.singletonList(_objectDefinition)
		);
	}

	private void _setUpSystemObjectDefinitionManager() {
		Mockito.when(
			_systemObjectDefinitionManager.getJaxRsApplicationDescriptor()
		).thenReturn(
			_jaxRsApplicationDescriptor
		);
	}

	private void _setUpSystemObjectDefinitionManagerRegistry() {
		Mockito.when(
			_systemObjectDefinitionManagerRegistry.
				getSystemObjectDefinitionManager("User")
		).thenReturn(
			_systemObjectDefinitionManager
		);
	}

	private void _setUpUriInfo(String basePath) {
		Mockito.when(
			_uriInfo.getBaseUri()
		).thenReturn(
			URI.create(
				"http://localhost:" + PortalUtil.getPortalServerPort(false) +
					basePath)
		);
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private final JaxRsApplicationDescriptor _jaxRsApplicationDescriptor =
		Mockito.mock(JaxRsApplicationDescriptor.class);
	private final ObjectDefinition _objectDefinition = Mockito.mock(
		ObjectDefinition.class);
	private final ObjectDefinitionLocalService _objectDefinitionLocalService =
		Mockito.mock(ObjectDefinitionLocalService.class);
	private final RelatedObjectEntryResourceImpl
		_relatedObjectEntryResourceImpl = new RelatedObjectEntryResourceImpl();
	private final SystemObjectDefinitionManager _systemObjectDefinitionManager =
		Mockito.mock(SystemObjectDefinitionManager.class);
	private final SystemObjectDefinitionManagerRegistry
		_systemObjectDefinitionManagerRegistry = Mockito.mock(
			SystemObjectDefinitionManagerRegistry.class);
	private final UriInfo _uriInfo = Mockito.mock(UriInfo.class);

}
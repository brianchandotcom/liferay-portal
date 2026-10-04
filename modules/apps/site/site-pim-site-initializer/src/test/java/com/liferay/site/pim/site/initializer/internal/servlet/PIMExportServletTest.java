/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.pim.site.initializer.internal.servlet;

import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.site.pim.site.initializer.connector.PIMConnector;
import com.liferay.site.pim.site.initializer.connector.PIMConnectorRegistry;
import com.liferay.site.pim.site.initializer.exception.PIMConnectorException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.Serializable;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Andrea Sbarra
 * @author Stefano Motta
 */
public class PIMExportServletTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		Mockito.when(
			_language.get(
				Mockito.any(HttpServletRequest.class), Mockito.anyString())
		).thenAnswer(
			invocationOnMock -> invocationOnMock.getArgument(1)
		);

		Mockito.when(
			_objectEntry.getValues()
		).thenReturn(
			HashMapBuilder.<String, Serializable>put(
				"key", _KEY
			).build()
		);

		Mockito.when(
			_objectEntryService.getObjectEntry(_OBJECT_ENTRY_ID)
		).thenReturn(
			_objectEntry
		);

		_permissionCheckerFactoryUtilMockedStatic.when(
			() -> PermissionCheckerFactoryUtil.create(_user)
		).thenReturn(
			_permissionChecker
		);

		Mockito.when(
			_pimConnectorRegistry.getPIMConnector(_KEY)
		).thenReturn(
			_pimConnector
		);

		ReflectionTestUtil.setFieldValue(
			_pimExportServlet, "_language", _language);
		ReflectionTestUtil.setFieldValue(
			_pimExportServlet, "_objectEntryService", _objectEntryService);
		ReflectionTestUtil.setFieldValue(
			_pimExportServlet, "_pimConnectorRegistry", _pimConnectorRegistry);
		ReflectionTestUtil.setFieldValue(_pimExportServlet, "_portal", _portal);

		Mockito.when(
			_portal.getUser(Mockito.any(HttpServletRequest.class))
		).thenReturn(
			_user
		);

		Mockito.when(
			_user.getUserId()
		).thenReturn(
			_USER_ID
		);
	}

	@After
	public void tearDown() {
		_permissionCheckerFactoryUtilMockedStatic.close();
	}

	@Test
	public void testDoGet() throws Exception {
		_testDoGet();
		_testDoGetWithGuestUser();
		_testDoGetWithInvalidPIMConnector();
		_testDoGetWithPIMConnectorException();
		_testDoGetWithUnexpectedException();
	}

	private void _assertError(
			String errorMessage,
			MockHttpServletResponse mockHttpServletResponse, int status)
		throws Exception {

		Assert.assertEquals(
			JSONUtil.put(
				"error", errorMessage
			).toString(),
			mockHttpServletResponse.getContentAsString());
		Assert.assertEquals(
			ContentTypes.APPLICATION_JSON,
			mockHttpServletResponse.getContentType());
		Assert.assertNull(
			mockHttpServletResponse.getHeader(HttpHeaders.CONTENT_DISPOSITION));
		Assert.assertEquals(status, mockHttpServletResponse.getStatus());
	}

	private MockHttpServletResponse _getMockHttpServletResponse()
		throws Exception {

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setParameter(
			"objectEntryId", String.valueOf(_OBJECT_ENTRY_ID));

		_pimExportServlet.doGet(
			mockHttpServletRequest, mockHttpServletResponse);

		return mockHttpServletResponse;
	}

	private void _testDoGet() throws Exception {
		String products = JSONUtil.putAll(
			JSONUtil.put("externalReferenceCode", "SKU-1")
		).toString();

		Mockito.doReturn(
			products
		).when(
			_pimConnector
		).export(
			_objectEntry
		);

		MockHttpServletResponse mockHttpServletResponse =
			_getMockHttpServletResponse();

		Assert.assertEquals(
			products, mockHttpServletResponse.getContentAsString());
		Assert.assertEquals(
			ContentTypes.APPLICATION_JSON,
			mockHttpServletResponse.getContentType());
		Assert.assertEquals(
			HttpHeaders.CONTENT_DISPOSITION_ATTACHMENT +
				"; filename=\"pim-products.json\"",
			mockHttpServletResponse.getHeader(HttpHeaders.CONTENT_DISPOSITION));
		Assert.assertEquals(
			HttpServletResponse.SC_OK, mockHttpServletResponse.getStatus());
	}

	private void _testDoGetWithGuestUser() throws Exception {
		Mockito.when(
			_user.isGuestUser()
		).thenReturn(
			true
		);

		_assertError(
			"you-do-not-have-permission-to-access-the-requested-resource",
			_getMockHttpServletResponse(), HttpServletResponse.SC_UNAUTHORIZED);

		Mockito.when(
			_user.isGuestUser()
		).thenReturn(
			false
		);
	}

	private void _testDoGetWithInvalidPIMConnector() throws Exception {
		Mockito.when(
			_pimConnectorRegistry.getPIMConnector(_KEY)
		).thenReturn(
			null
		);

		_assertError(
			"unable-to-get-a-pim-connector-with-the-given-key",
			_getMockHttpServletResponse(), HttpServletResponse.SC_BAD_REQUEST);

		Mockito.when(
			_pimConnectorRegistry.getPIMConnector(_KEY)
		).thenReturn(
			_pimConnector
		);
	}

	private void _testDoGetWithPIMConnectorException() throws Exception {
		Mockito.doThrow(
			new PIMConnectorException("a-required-channel-field-is-not-mapped")
		).when(
			_pimConnector
		).export(
			_objectEntry
		);

		_assertError(
			"a-required-channel-field-is-not-mapped",
			_getMockHttpServletResponse(), HttpServletResponse.SC_BAD_REQUEST);

		Mockito.doReturn(
			StringPool.BLANK
		).when(
			_pimConnector
		).export(
			_objectEntry
		);
	}

	private void _testDoGetWithUnexpectedException() throws Exception {
		Mockito.doThrow(
			new PortalException()
		).when(
			_objectEntryService
		).getObjectEntry(
			_OBJECT_ENTRY_ID
		);

		_assertError(
			"an-unexpected-error-occurred", _getMockHttpServletResponse(),
			HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

		Mockito.doReturn(
			_objectEntry
		).when(
			_objectEntryService
		).getObjectEntry(
			_OBJECT_ENTRY_ID
		);
	}

	private static final String _KEY = RandomTestUtil.randomString();

	private static final long _OBJECT_ENTRY_ID = RandomTestUtil.randomLong();

	private static final long _USER_ID = RandomTestUtil.randomLong();

	private final Language _language = Mockito.mock(Language.class);
	private final ObjectEntry _objectEntry = Mockito.mock(ObjectEntry.class);
	private final ObjectEntryService _objectEntryService = Mockito.mock(
		ObjectEntryService.class);
	private final PermissionChecker _permissionChecker = Mockito.mock(
		PermissionChecker.class);
	private final MockedStatic<PermissionCheckerFactoryUtil>
		_permissionCheckerFactoryUtilMockedStatic = Mockito.mockStatic(
			PermissionCheckerFactoryUtil.class);
	private final PIMConnector _pimConnector = Mockito.mock(PIMConnector.class);
	private final PIMConnectorRegistry _pimConnectorRegistry = Mockito.mock(
		PIMConnectorRegistry.class);
	private final PIMExportServlet _pimExportServlet = new PIMExportServlet();
	private final Portal _portal = Mockito.mock(Portal.class);
	private final User _user = Mockito.mock(User.class);

}
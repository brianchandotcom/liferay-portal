/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.instances.web.internal.portlet.action;

import com.liferay.headless.portal.instances.resource.v1_0.PortalInstanceResource;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineImportTaskResource;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineImportTaskResourceFactory;

import jakarta.portlet.ActionRequest;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Luis Ortiz
 */
public class DeleteInstanceMVCActionCommandTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		ReflectionTestUtil.setFieldValue(
			_deleteInstanceMVCActionCommand, "_componentServiceObjects",
			_componentServiceObjects);
		ReflectionTestUtil.setFieldValue(
			_deleteInstanceMVCActionCommand, "_portal", _portal);
		ReflectionTestUtil.setFieldValue(
			_deleteInstanceMVCActionCommand,
			"_vulcanBatchEngineImportTaskResourceFactory",
			_vulcanBatchEngineImportTaskResourceFactory);

		Mockito.when(
			_actionRequest.getParameter("portalInstanceId")
		).thenReturn(
			_PORTAL_INSTANCE_ID
		);

		Mockito.when(
			_componentServiceObjects.getService()
		).thenReturn(
			_portalInstanceResource
		);

		Mockito.when(
			_portal.getCompany(_actionRequest)
		).thenReturn(
			Mockito.mock(Company.class)
		);

		Mockito.when(
			_portal.getHttpServletRequest(_actionRequest)
		).thenReturn(
			_httpServletRequest
		);

		Mockito.when(
			_portal.getLocale(_actionRequest)
		).thenReturn(
			LocaleUtil.US
		);

		Mockito.when(
			_portal.getUser(_actionRequest)
		).thenReturn(
			Mockito.mock(User.class)
		);

		Mockito.when(
			_vulcanBatchEngineImportTaskResourceFactory.create()
		).thenReturn(
			_vulcanBatchEngineImportTaskResource
		);
	}

	@Test
	public void testDeletePortalInstanceForcesTheJSONContentType()
		throws Exception {

		Mockito.when(
			_httpServletRequest.getHeader("X-Other")
		).thenReturn(
			"delegated"
		);

		_deletePortalInstance();

		ArgumentCaptor<HttpServletRequest> argumentCaptor =
			ArgumentCaptor.forClass(HttpServletRequest.class);

		Mockito.verify(
			_portalInstanceResource
		).setContextHttpServletRequest(
			argumentCaptor.capture()
		);

		HttpServletRequest httpServletRequest = argumentCaptor.getValue();

		Assert.assertEquals(
			ContentTypes.APPLICATION_JSON,
			httpServletRequest.getHeader(HttpHeaders.CONTENT_TYPE));
		Assert.assertEquals(
			"delegated", httpServletRequest.getHeader("X-Other"));
	}

	@Test
	public void testDeletePortalInstanceSendsThePortalInstanceId()
		throws Exception {

		_deletePortalInstance();

		ArgumentCaptor<Object> argumentCaptor = ArgumentCaptor.forClass(
			Object.class);

		Mockito.verify(
			_portalInstanceResource
		).deletePortalInstanceBatch(
			Mockito.isNull(), argumentCaptor.capture()
		);

		List<Map<String, String>> maps =
			(List<Map<String, String>>)argumentCaptor.getValue();

		Assert.assertEquals(maps.toString(), 1, maps.size());

		Map<String, String> map = maps.get(0);

		Assert.assertEquals(_PORTAL_INSTANCE_ID, map.get("portalInstanceId"));
	}

	@Test
	public void testDeletePortalInstanceSetsThePreferredLocale()
		throws Exception {

		_deletePortalInstance();

		ArgumentCaptor<AcceptLanguage> argumentCaptor = ArgumentCaptor.forClass(
			AcceptLanguage.class);

		Mockito.verify(
			_portalInstanceResource
		).setContextAcceptLanguage(
			argumentCaptor.capture()
		);

		AcceptLanguage acceptLanguage = argumentCaptor.getValue();

		Assert.assertEquals(LocaleUtil.US, acceptLanguage.getPreferredLocale());
	}

	@Test
	public void testDeletePortalInstanceSetsTheVulcanBatchEngineResource()
		throws Exception {

		_deletePortalInstance();

		Mockito.verify(
			_portalInstanceResource
		).setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource
		);
	}

	@Test
	public void testDeletePortalInstanceUngetsTheService() throws Exception {
		_deletePortalInstance();

		Mockito.verify(
			_componentServiceObjects
		).ungetService(
			_portalInstanceResource
		);
	}

	@Test
	public void testDeletePortalInstanceUngetsTheServiceWhenTheBatchFails()
		throws Exception {

		Mockito.when(
			_portalInstanceResource.deletePortalInstanceBatch(
				Mockito.isNull(), Mockito.any())
		).thenThrow(
			new IllegalStateException()
		);

		try {
			_deletePortalInstance();

			Assert.fail();
		}
		catch (IllegalStateException illegalStateException) {
		}

		Mockito.verify(
			_componentServiceObjects
		).ungetService(
			_portalInstanceResource
		);
	}

	private void _deletePortalInstance() throws Exception {
		ReflectionTestUtil.invoke(
			_deleteInstanceMVCActionCommand, "_deletePortalInstance",
			new Class<?>[] {ActionRequest.class}, _actionRequest);
	}

	private static final String _PORTAL_INSTANCE_ID =
		RandomTestUtil.randomString();

	private final ActionRequest _actionRequest = Mockito.mock(
		ActionRequest.class);
	private final ComponentServiceObjects<PortalInstanceResource>
		_componentServiceObjects = Mockito.mock(ComponentServiceObjects.class);
	private final DeleteInstanceMVCActionCommand
		_deleteInstanceMVCActionCommand = new DeleteInstanceMVCActionCommand();
	private final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private final Portal _portal = Mockito.mock(Portal.class);
	private final PortalInstanceResource _portalInstanceResource = Mockito.mock(
		PortalInstanceResource.class);
	private final VulcanBatchEngineImportTaskResource
		_vulcanBatchEngineImportTaskResource = Mockito.mock(
			VulcanBatchEngineImportTaskResource.class);
	private final VulcanBatchEngineImportTaskResourceFactory
		_vulcanBatchEngineImportTaskResourceFactory = Mockito.mock(
			VulcanBatchEngineImportTaskResourceFactory.class);

}
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.instances.web.internal.portlet.action;

import com.liferay.portal.db.partition.util.DBPartitionUtil;
import com.liferay.portal.instances.configuration.PortalInstanceConfigurationExporter;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.exception.RequiredCompanyException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockActionRequest;
import com.liferay.portal.kernel.test.portlet.MockActionResponse;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;
import jakarta.portlet.PortletRequest;

import java.util.Locale;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Jorge Avalos
 */
public class ExportInstanceMVCActionCommandTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_jsonObject = new JSONObjectImpl();

		Mockito.when(
			_jsonFactory.createJSONObject()
		).thenReturn(
			_jsonObject
		);

		Mockito.when(
			_language.format(
				Mockito.nullable(Locale.class), Mockito.anyString(),
				Mockito.<Object>any())
		).thenAnswer(
			invocationOnMock ->
				invocationOnMock.getArgument(1) + ":" +
					invocationOnMock.getArgument(2)
		);

		ReflectionTestUtil.setFieldValue(
			_exportInstanceMVCActionCommand, "_companyService",
			_companyService);
		ReflectionTestUtil.setFieldValue(
			_exportInstanceMVCActionCommand, "_jsonFactory", _jsonFactory);
		ReflectionTestUtil.setFieldValue(
			_exportInstanceMVCActionCommand, "_language", _language);
		ReflectionTestUtil.setFieldValue(
			_exportInstanceMVCActionCommand,
			"_portalInstanceConfigurationExporter",
			_portalInstanceConfigurationExporter);

		_featureFlagManagerUtilMockedStatic = Mockito.mockStatic(
			FeatureFlagManagerUtil.class);

		_featureFlagManagerUtilMockedStatic.when(
			() -> FeatureFlagManagerUtil.isEnabled(
				Mockito.anyLong(), Mockito.eq("LPD-11342"))
		).thenReturn(
			true
		);

		_jsonPortletResponseUtilMockedStatic = Mockito.mockStatic(
			JSONPortletResponseUtil.class);

		_jsonPortletResponseUtilMockedStatic.when(
			() -> JSONPortletResponseUtil.writeJSON(
				Mockito.any(ActionRequest.class),
				Mockito.any(ActionResponse.class),
				Mockito.any(JSONObject.class))
		).then(
			invocationOnMock -> null
		);
	}

	@After
	public void tearDown() {
		_featureFlagManagerUtilMockedStatic.close();
		_jsonPortletResponseUtilMockedStatic.close();
	}

	@Test
	public void testConfigurationsExportedOnSuccess() throws Exception {
		long companyId = RandomTestUtil.randomLong();

		_exportInstanceMVCActionCommand.doProcessAction(
			_getMockActionRequest(companyId), new MockActionResponse());

		Assert.assertFalse(_jsonObject.has("error"));

		Mockito.verify(
			_portalInstanceConfigurationExporter
		).exportConfigurations(
			companyId
		);
	}

	@Test
	public void testErrorMessageForConfigurationExportFailure()
		throws Exception {

		long companyId = RandomTestUtil.randomLong();

		String message = RandomTestUtil.randomString();

		Mockito.doThrow(
			new IllegalStateException(message)
		).when(
			_portalInstanceConfigurationExporter
		).exportConfigurations(
			companyId
		);

		_exportInstanceMVCActionCommand.doProcessAction(
			_getMockActionRequest(companyId), new MockActionResponse());

		Assert.assertEquals(
			"export-failed-with-message-x:" + message,
			_jsonObject.getString("error"));
		Assert.assertFalse(_jsonObject.has("successMessage"));
	}

	@Test
	public void testErrorMessageForMustBeOmniadminException() throws Exception {
		PrincipalException.MustBeOmniadmin mustBeOmniadminException =
			new PrincipalException.MustBeOmniadmin(_permissionChecker);

		_assertErrorMessage(
			mustBeOmniadminException,
			"export-failed-with-message-x:" +
				GetterUtil.getString(mustBeOmniadminException.getMessage()));
	}

	@Test
	public void testErrorMessageForNullExceptionMessage() throws Exception {
		_assertErrorMessage(
			new RequiredCompanyException(), "export-failed-with-message-x:");
	}

	@Test
	public void testErrorMessageOnFailure() throws Exception {
		String message = RandomTestUtil.randomString();

		_assertErrorMessage(
			new RequiredCompanyException(message),
			"export-failed-with-message-x:" + message);
	}

	@Test
	public void testSchemaNameOnSuccess() throws Exception {
		long companyId = RandomTestUtil.randomLong();

		_exportInstanceMVCActionCommand.doProcessAction(
			_getMockActionRequest(companyId), new MockActionResponse());

		Assert.assertEquals(
			"the-instance-was-exported-to-the-schema-x:" +
				DBPartitionUtil.DATABASE_EXPORTED_PARTITION_SCHEMA_NAME_PREFIX +
					companyId,
			_jsonObject.getString("successMessage"));

		Assert.assertFalse(_jsonObject.has("error"));

		Mockito.verify(
			_companyService
		).exportCompany(
			companyId
		);

		_jsonPortletResponseUtilMockedStatic.verify(
			() -> JSONPortletResponseUtil.writeJSON(
				Mockito.any(ActionRequest.class),
				Mockito.any(ActionResponse.class), Mockito.eq(_jsonObject)));
	}

	@Test
	public void testUnsupportedOperationExceptionForDisabledFeatureFlag() {
		_featureFlagManagerUtilMockedStatic.when(
			() -> FeatureFlagManagerUtil.isEnabled(
				Mockito.anyLong(), Mockito.eq("LPD-11342"))
		).thenReturn(
			false
		);

		Assert.assertThrows(
			UnsupportedOperationException.class,
			() -> _exportInstanceMVCActionCommand.doProcessAction(
				_getMockActionRequest(RandomTestUtil.randomLong()),
				new MockActionResponse()));

		Mockito.verifyNoInteractions(_companyService);
	}

	private void _assertErrorMessage(
			Exception exception, String expectedErrorMessage)
		throws Exception {

		long companyId = RandomTestUtil.randomLong();

		Mockito.when(
			_companyService.exportCompany(companyId)
		).thenThrow(
			exception
		);

		_exportInstanceMVCActionCommand.doProcessAction(
			_getMockActionRequest(companyId), new MockActionResponse());

		Assert.assertEquals(
			expectedErrorMessage, _jsonObject.getString("error"));
		Assert.assertFalse(_jsonObject.has("successMessage"));

		_jsonPortletResponseUtilMockedStatic.verify(
			() -> JSONPortletResponseUtil.writeJSON(
				Mockito.any(ActionRequest.class),
				Mockito.any(ActionResponse.class), Mockito.eq(_jsonObject)));
	}

	private MockActionRequest _getMockActionRequest(long companyId) {
		MockActionRequest mockActionRequest = new MockActionRequest();

		mockActionRequest.addParameter("companyId", String.valueOf(companyId));
		mockActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, Mockito.mock(ThemeDisplay.class));

		return mockActionRequest;
	}

	private final CompanyService _companyService = Mockito.mock(
		CompanyService.class);

	private final ExportInstanceMVCActionCommand
		_exportInstanceMVCActionCommand = new ExportInstanceMVCActionCommand() {

			@Override
			protected void hideDefaultSuccessMessage(
				PortletRequest portletRequest) {
			}

		};

	private MockedStatic<FeatureFlagManagerUtil>
		_featureFlagManagerUtilMockedStatic;
	private final JSONFactory _jsonFactory = Mockito.mock(JSONFactory.class);
	private JSONObject _jsonObject;
	private MockedStatic<JSONPortletResponseUtil>
		_jsonPortletResponseUtilMockedStatic;
	private final Language _language = Mockito.mock(Language.class);
	private final PermissionChecker _permissionChecker = Mockito.mock(
		PermissionChecker.class);
	private final PortalInstanceConfigurationExporter
		_portalInstanceConfigurationExporter = Mockito.mock(
			PortalInstanceConfigurationExporter.class);

}
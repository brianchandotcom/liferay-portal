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

package com.liferay.dynamic.data.lists.form.web.internal.display.context;

import com.liferay.dynamic.data.lists.form.web.configuration.DDLFormWebConfiguration;
import com.liferay.dynamic.data.lists.form.web.internal.converter.DDMFormRulesToDDLFormRulesConverter;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetSettings;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetService;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.internal.servlet.DDMFormContextProviderServlet;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesJSONSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormJSONSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutJSONSerializer;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesMerger;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowEngineManager;

import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Matchers;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Adam Brandizzi
 */
public class DDLFormAdminDisplayContextTest extends PowerMockito {

	@Before
	public void setUp() throws PortalException {
		setUpPortalUtil();

		setUpDDLFormDisplayContext();
	}

	@Test
	public void testGetFormURLForRestrictedRecordSet() throws Exception {
		setRenderRequestParamenter(
			"recordSetId", String.valueOf(_RESTRICTED_RECORD_SET_ID));

		String formURL = _ddlFormAdminDisplayContext.getFormURL();

		Assert.assertEquals(getRestrictedFormURL(), formURL);
	}

	@Test
	public void testGetFormURLForSharedRecordSet() throws Exception {
		setRenderRequestParamenter(
			"recordSetId", String.valueOf(_SHARED_RECORD_SET_ID));

		String formURL = _ddlFormAdminDisplayContext.getFormURL();

		Assert.assertEquals(getSharedFormURL(), formURL);
	}

	@Test
	public void testGetRestrictedFormURL() throws Exception {
		String restrictedFormURL =
			_ddlFormAdminDisplayContext.getRestrictedFormURL();

		Assert.assertEquals(getRestrictedFormURL(), restrictedFormURL);
	}

	@Test
	public void testGetSharedFormURL() throws Exception {
		String sharedFormURL = _ddlFormAdminDisplayContext.getSharedFormURL();

		Assert.assertEquals(getSharedFormURL(), sharedFormURL);
	}

	protected String getFormURL(
		String friendlyURLPath, String pageFriendlyURLPath) {

		return _PORTAL_URL + friendlyURLPath + _GROUP_FRIENDLY_URL_PATH +
			pageFriendlyURLPath + _FORM_APPLICATION_PATH;
	}

	protected String getRestrictedFormURL() {
		return getFormURL(
			_PRIVATE_FRIENDLY_URL_PATH, _PRIVATE_PAGE_FRIENDLY_URL_PATH);
	}

	protected String getSharedFormURL() {
		return getFormURL(
			_PUBLIC_FRIENDLY_URL_PATH, _PUBLIC_PAGE_FRIENDLY_URL_PATH);
	}

	protected DDLRecordSet mockDDLRecordSet(
			long recordSetId, boolean requireAuthentication)
		throws PortalException {

		DDLRecordSet recordSet = mock(DDLRecordSet.class);

		when(
			recordSet.getRecordSetId()
		).thenReturn(
			recordSetId
		);

		DDLRecordSetSettings settings = mockDDLRecordSetSettings(
			requireAuthentication);

		when(
			recordSet.getSettingsModel()
		).thenReturn(
			settings
		);

		return recordSet;
	}

	protected DDLRecordSetService mockDDLRecordSetService()
		throws PortalException {

		DDLRecordSetService recordSetService = mock(DDLRecordSetService.class);

		DDLRecordSet sharedRecordSet = mockDDLRecordSet(
			_SHARED_RECORD_SET_ID, false);

		when(
			recordSetService.fetchRecordSet(Matchers.eq(_SHARED_RECORD_SET_ID))
		).thenReturn(
			sharedRecordSet
		);

		DDLRecordSet restrictedRecordSet = mockDDLRecordSet(
			_RESTRICTED_RECORD_SET_ID, true);

		when(
			recordSetService.fetchRecordSet(
				Matchers.eq(_RESTRICTED_RECORD_SET_ID))
		).thenReturn(
			restrictedRecordSet
		);

		return recordSetService;
	}

	protected DDLRecordSetSettings mockDDLRecordSetSettings(
		boolean requireAuthentication) {

		DDLRecordSetSettings settings = mock(DDLRecordSetSettings.class);

		when(
			settings.requireAuthentication()
		).thenReturn(
			requireAuthentication
		);

		return settings;
	}

	protected Group mockGroup() {
		Group group = mock(Group.class);

		when(
			group.getPathFriendlyURL(
				Matchers.eq(false), Matchers.any(ThemeDisplay.class))
		).thenReturn(
			_PUBLIC_FRIENDLY_URL_PATH
		);

		when(
			group.getPathFriendlyURL(
				Matchers.eq(true), Matchers.any(ThemeDisplay.class))
		).thenReturn(
			_PRIVATE_FRIENDLY_URL_PATH
		);

		return group;
	}

	protected HttpServletRequest mockHttpServletRequest() {
		ThemeDisplay themeDisplay = mockThemeDisplay();

		HttpServletRequest request = mock(HttpServletRequest.class);

		when(
			request.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);

		return request;
	}

	protected ThemeDisplay mockThemeDisplay() {
		ThemeDisplay themeDisplay = mock(ThemeDisplay.class);

		Group group = mockGroup();

		when(
			themeDisplay.getPortalURL()
		).thenReturn(
			_PORTAL_URL
		);

		when(
			themeDisplay.getSiteGroup()
		).thenReturn(
			group
		);

		return themeDisplay;
	}

	protected void setRenderRequestParamenter(String parameter, String value) {
		when(
			_renderRequest.getParameter(Matchers.eq(parameter))
		).thenReturn(
			value
		);
	}

	protected void setUpDDLFormDisplayContext() throws PortalException {
		_renderRequest = mock(RenderRequest.class);

		_ddlFormAdminDisplayContext = new DDLFormAdminDisplayContext(
			_renderRequest, mock(RenderResponse.class),
			mock(DDLFormWebConfiguration.class),
			mock(DDLRecordLocalService.class), mockDDLRecordSetService(),
			mock(DDMFormContextProviderServlet.class),
			mock(DDMFormFieldTypeServicesTracker.class),
			mock(DDMFormFieldTypesJSONSerializer.class),
			mock(DDMFormJSONSerializer.class),
			mock(DDMFormLayoutJSONSerializer.class),
			mock(DDMFormRenderer.class),
			mock(DDMFormRulesToDDLFormRulesConverter.class),
			mock(DDMFormValuesFactory.class), mock(DDMFormValuesMerger.class),
			mock(DDMStructureLocalService.class), mock(JSONFactory.class),
			mock(StorageEngine.class), mock(WorkflowEngineManager.class));
	}

	protected void setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		Portal portal = mock(Portal.class);

		HttpServletRequest request = mockHttpServletRequest();

		when(
			portal.getHttpServletRequest(Matchers.any(PortletRequest.class))
		).thenReturn(
			request
		);

		portalUtil.setPortal(portal);
	}

	private static final String _FORM_APPLICATION_PATH =
		Portal.FRIENDLY_URL_SEPARATOR + "form/";

	private static final String _GROUP_FRIENDLY_URL_PATH = "/forms";

	private static final String _PORTAL_URL = "http://localhost:9999";

	private static final String _PRIVATE_FRIENDLY_URL_PATH = "/group";

	private static final String _PRIVATE_PAGE_FRIENDLY_URL_PATH = "/shared";

	private static final String _PUBLIC_FRIENDLY_URL_PATH = "/web";

	private static final String _PUBLIC_PAGE_FRIENDLY_URL_PATH = "/shared";

	private static final long _RESTRICTED_RECORD_SET_ID =
		RandomTestUtil.randomLong();

	private static final long _SHARED_RECORD_SET_ID =
		RandomTestUtil.randomLong();

	private DDLFormAdminDisplayContext _ddlFormAdminDisplayContext;
	private RenderRequest _renderRequest;

}
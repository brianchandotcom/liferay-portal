/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.constants.ExportImportConstants;
import com.liferay.exportimport.rest.client.dto.v1_0.ExportPreview;
import com.liferay.exportimport.rest.client.dto.v1_0.PreviewPortletDataHandler;
import com.liferay.exportimport.rest.client.dto.v1_0.PreviewPortletDataHandlerSection;
import com.liferay.exportimport.rest.client.resource.v1_0.ExportPreviewResource;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectDefinitionSettingConstants;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectDefinitionSettingLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Daniel Raposo
 */
@RunWith(Arquillian.class)
public class ExportPreviewResourceTest
	extends BaseExportPreviewResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_companyObjectDefinition = _publishObjectDefinitionWithEntries(
			ObjectDefinitionConstants.SCOPE_COMPANY,
			GroupConstants.DEFAULT_PARENT_GROUP_ID);
		_depotObjectDefinition = _publishObjectDefinitionWithEntries(
			ObjectDefinitionConstants.SCOPE_DEPOT,
			testDepotEntryGroup.getGroupId());
		_siteObjectDefinition = _publishObjectDefinitionWithEntries(
			ObjectDefinitionConstants.SCOPE_SITE, testGroup.getGroupId());

		String password = RandomTestUtil.randomString();

		_user = UserTestUtil.addUser(testCompany, password);

		_exportPreviewResource = ExportPreviewResource.builder(
		).authentication(
			_user.getEmailAddress(), password
		).endpoint(
			testCompany.getVirtualHostname(),
			PortalUtil.getPortalServerPort(false), "http"
		).locale(
			LocaleUtil.getDefault()
		).build();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		_objectDefinitionLocalService.deleteObjectDefinition(
			_companyObjectDefinition);
		_objectDefinitionLocalService.deleteObjectDefinition(
			_depotObjectDefinition);
		_objectDefinitionLocalService.deleteObjectDefinition(
			_siteObjectDefinition);

		_userLocalService.deleteUser(_user);
	}

	@FeatureFlag("LPD-17564")
	@Override
	@Test
	public void testGetAssetLibraryExportPreview() throws Exception {
		assertHttpResponseStatusCode(
			404,
			_exportPreviewResource.getAssetLibraryExportPreviewHttpResponse(
				testDepotEntryGroup.getExternalReferenceCode(), null, null,
				null, null));

		_testGetExportPreviewWithDateFilter(
			testDateFilter ->
				exportPreviewResource.getAssetLibraryExportPreview(
					testDepotEntryGroup.getExternalReferenceCode(),
					testDateFilter.getEndDate(), testDateFilter.getLast(),
					testDateFilter.getRange(), testDateFilter.getStartDate()),
			_depotObjectDefinition);
		_testGetExportPreviewWithDifferentScope(
			exportPreviewResource.getAssetLibraryExportPreview(
				testDepotEntryGroup.getExternalReferenceCode(), null, null,
				null, null),
			_companyObjectDefinition, _siteObjectDefinition);
	}

	@FeatureFlag("LPD-17564")
	@Override
	@Test
	public void testGetAssetLibraryPortletExportPreview() throws Exception {
		String portletId = _depotObjectDefinition.getPortletId();

		assertHttpResponseStatusCode(
			404,
			_exportPreviewResource.
				getAssetLibraryPortletExportPreviewHttpResponse(
					testDepotEntryGroup.getExternalReferenceCode(), portletId,
					null, null, 0L, null, null));

		_testGetExportPreviewWithDateFilter(
			testDateFilter ->
				exportPreviewResource.getAssetLibraryPortletExportPreview(
					testDepotEntryGroup.getExternalReferenceCode(), portletId,
					testDateFilter.getEndDate(), testDateFilter.getLast(), 0L,
					testDateFilter.getRange(), testDateFilter.getStartDate()),
			_depotObjectDefinition);

		long plid = _addLayoutWithPortlet(testDepotEntryGroup, portletId);

		_testGetPortletExportPreview(
			exportPreviewResource.getAssetLibraryPortletExportPreview(
				testDepotEntryGroup.getExternalReferenceCode(), portletId, null,
				null, plid, null, null),
			portletId);
	}

	@Override
	@Test
	public void testGetExportPreview() throws Exception {
		assertHttpResponseStatusCode(
			404,
			_exportPreviewResource.getExportPreviewHttpResponse(
				null, null, null, null));

		_testGetExportPreviewWithDateFilter(
			testDateFilter -> exportPreviewResource.getExportPreview(
				testDateFilter.getEndDate(), testDateFilter.getLast(),
				testDateFilter.getRange(), testDateFilter.getStartDate()),
			_companyObjectDefinition);
		_testGetExportPreviewWithDifferentScope(
			exportPreviewResource.getExportPreview(null, null, null, null),
			_depotObjectDefinition, _siteObjectDefinition);
	}

	@Override
	@Test
	public void testGetSiteExportPreview() throws Exception {
		assertHttpResponseStatusCode(
			404,
			_exportPreviewResource.getSiteExportPreviewHttpResponse(
				testGroup.getExternalReferenceCode(), null, null, null, null));

		_testGetExportPreviewWithDateFilter(
			testDateFilter -> exportPreviewResource.getSiteExportPreview(
				testGroup.getExternalReferenceCode(),
				testDateFilter.getEndDate(), testDateFilter.getLast(),
				testDateFilter.getRange(), testDateFilter.getStartDate()),
			_siteObjectDefinition);
		_testGetExportPreviewWithDifferentScope(
			exportPreviewResource.getSiteExportPreview(
				testGroup.getExternalReferenceCode(), null, null, null, null),
			_companyObjectDefinition, _depotObjectDefinition);
	}

	@Override
	@Test
	public void testGetSitePortletExportPreview() throws Exception {
		String portletId = _siteObjectDefinition.getPortletId();

		assertHttpResponseStatusCode(
			404,
			_exportPreviewResource.getSitePortletExportPreviewHttpResponse(
				testGroup.getExternalReferenceCode(), portletId, null, null, 0L,
				null, null));

		_testGetExportPreviewWithDateFilter(
			testDateFilter -> exportPreviewResource.getSitePortletExportPreview(
				testGroup.getExternalReferenceCode(), portletId,
				testDateFilter.getEndDate(), testDateFilter.getLast(), 0L,
				testDateFilter.getRange(), testDateFilter.getStartDate()),
			_siteObjectDefinition);

		long plid = _addLayoutWithPortlet(testGroup, portletId);

		_testGetPortletExportPreview(
			exportPreviewResource.getSitePortletExportPreview(
				testGroup.getExternalReferenceCode(), portletId, null, null,
				plid, null, null),
			portletId);
	}

	private long _addLayoutWithPortlet(Group group, String portletId)
		throws Exception {

		Layout layout = LayoutTestUtil.addTypePortletLayout(group);

		LayoutTestUtil.addPortletToLayout(
			layout, portletId,
			HashMapBuilder.put(
				RandomTestUtil.randomString(),
				new String[] {RandomTestUtil.randomString()}
			).build());

		return layout.getPlid();
	}

	private void _addObjectEntry(
			ObjectDefinition objectDefinition, long groupId, Date modifiedDate)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setModifiedDate(modifiedDate);

		_objectEntryLocalService.addObjectEntry(
			groupId, TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId(),
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
			null,
			HashMapBuilder.<String, Serializable>put(
				"textField", RandomTestUtil.randomString()
			).build(),
			serviceContext);
	}

	private long _getAdditionCount(
		ExportPreview exportPreview, String portletId) {

		String name = "PORTLET_DATA_" + portletId;

		for (PreviewPortletDataHandlerSection previewPortletDataHandlerSection :
				exportPreview.getPreviewPortletDataHandlerSections()) {

			for (PreviewPortletDataHandler previewPortletDataHandler :
					previewPortletDataHandlerSection.
						getPreviewPortletDataHandlers()) {

				if (name.equals(previewPortletDataHandler.getName())) {
					return previewPortletDataHandler.getAdditionCount();
				}
			}
		}

		return 0L;
	}

	private PreviewPortletDataHandlerSection _getSection(
		ExportPreview exportPreview, String name) {

		for (PreviewPortletDataHandlerSection previewPortletDataHandlerSection :
				exportPreview.getPreviewPortletDataHandlerSections()) {

			if (name.equals(previewPortletDataHandlerSection.getName())) {
				return previewPortletDataHandlerSection;
			}
		}

		return null;
	}

	private ObjectDefinition _publishObjectDefinitionWithEntries(
			String scope, long groupId)
		throws Exception {

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				ObjectDefinitionTestUtil.getRandomName(),
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
						RandomTestUtil.randomString(), "textField", false)),
				scope);

		if (Objects.equals(scope, ObjectDefinitionConstants.SCOPE_DEPOT)) {
			_objectDefinitionSettingLocalService.addObjectDefinitionSetting(
				objectDefinition.getUserId(),
				objectDefinition.getObjectDefinitionId(),
				ObjectDefinitionSettingConstants.NAME_ACCEPT_ALL_GROUPS,
				StringPool.TRUE);
		}

		_addObjectEntry(objectDefinition, groupId, new Date());
		_addObjectEntry(
			objectDefinition, groupId,
			new Date(System.currentTimeMillis() - (25 * Time.HOUR)));

		return objectDefinition;
	}

	private void _testGetExportPreviewWithDateFilter(
			UnsafeFunction<TestDateFilter, ExportPreview, Exception>
				unsafeFunction,
			ObjectDefinition objectDefinition)
		throws Exception {

		long now = System.currentTimeMillis();

		String portletId = objectDefinition.getPortletId();

		Assert.assertEquals(
			2L,
			_getAdditionCount(
				unsafeFunction.apply(TestDateFilter.all()), portletId));
		Assert.assertEquals(
			1L,
			_getAdditionCount(
				unsafeFunction.apply(TestDateFilter.last(24)), portletId));
		Assert.assertEquals(
			2L,
			_getAdditionCount(
				unsafeFunction.apply(TestDateFilter.last(48)), portletId));
		Assert.assertEquals(
			1L,
			_getAdditionCount(
				unsafeFunction.apply(
					TestDateFilter.dateRange(
						new Date(now - Time.HOUR), new Date(now))),
				portletId));
		Assert.assertEquals(
			1L,
			_getAdditionCount(
				unsafeFunction.apply(
					TestDateFilter.dateRange(
						new Date(now - (26 * Time.HOUR)),
						new Date(now - (24 * Time.HOUR)))),
				portletId));
		Assert.assertEquals(
			0L,
			_getAdditionCount(
				unsafeFunction.apply(
					TestDateFilter.dateRange(
						new Date(now - (3 * Time.DAY)),
						new Date(now - (2 * Time.DAY)))),
				portletId));
	}

	private void _testGetExportPreviewWithDifferentScope(
		ExportPreview exportPreview, ObjectDefinition... objectDefinitions) {

		for (ObjectDefinition objectDefinition : objectDefinitions) {
			long additionCount = _getAdditionCount(
				exportPreview, objectDefinition.getPortletId());

			Assert.assertTrue(additionCount <= 0);
		}
	}

	private void _testGetPortletExportPreview(
		ExportPreview exportPreview, String portletId) {

		PreviewPortletDataHandlerSection contentSection = _getSection(
			exportPreview, ExportImportConstants.SECTION_KEY_CONTENT);

		PreviewPortletDataHandler[] contentPreviewPortletDataHandlers =
			contentSection.getPreviewPortletDataHandlers();

		Assert.assertEquals(
			Arrays.toString(contentPreviewPortletDataHandlers), 1,
			contentPreviewPortletDataHandlers.length);
		Assert.assertEquals(
			"PORTLET_DATA_" + portletId,
			contentPreviewPortletDataHandlers[0].getName());

		PreviewPortletDataHandlerSection configurationSection = _getSection(
			exportPreview, ExportImportConstants.SECTION_KEY_CONFIGURATION);

		PreviewPortletDataHandler[] configurationPreviewPortletDataHandlers =
			configurationSection.getPreviewPortletDataHandlers();

		Assert.assertEquals(
			Arrays.toString(configurationPreviewPortletDataHandlers), 1,
			configurationPreviewPortletDataHandlers.length);
		Assert.assertEquals(
			"PORTLET_CONFIGURATION_" + portletId,
			configurationPreviewPortletDataHandlers[0].getName());
		Assert.assertTrue(
			ArrayUtil.isNotEmpty(
				configurationPreviewPortletDataHandlers[0].
					getPreviewPortletDataHandlerControls()));
	}

	private ObjectDefinition _companyObjectDefinition;
	private ObjectDefinition _depotObjectDefinition;
	private ExportPreviewResource _exportPreviewResource;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectDefinitionSettingLocalService
		_objectDefinitionSettingLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	private ObjectDefinition _siteObjectDefinition;
	private User _user;

	@Inject
	private UserLocalService _userLocalService;

	private static class TestDateFilter {

		public static TestDateFilter all() {
			return new TestDateFilter(null, null, null, null);
		}

		public static TestDateFilter dateRange(Date startDate, Date endDate) {
			return new TestDateFilter(endDate, null, "dateRange", startDate);
		}

		public static TestDateFilter last(int hours) {
			return new TestDateFilter(null, hours, "last", null);
		}

		public Date getEndDate() {
			return _endDate;
		}

		public Integer getLast() {
			return _last;
		}

		public String getRange() {
			return _range;
		}

		public Date getStartDate() {
			return _startDate;
		}

		private TestDateFilter(
			Date endDate, Integer last, String range, Date startDate) {

			_endDate = endDate;
			_last = last;
			_range = range;
			_startDate = startDate;
		}

		private final Date _endDate;
		private final Integer _last;
		private final String _range;
		private final Date _startDate;

	}

}
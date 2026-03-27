/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.object.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.headless.object.client.dto.v1_0.ObjectEntryFolder;
import com.liferay.headless.object.resource.v1_0.ObjectEntryFolderResource;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegate;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.site.cms.site.initializer.test.util.CMSTestUtil;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mikel Lorza
 */
@FeatureFlag("LPD-17564")
@RunWith(Arquillian.class)
public class ObjectEntryFolderVulcanBatchEngineTaskItemDelegateTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_objectEntryFolderResource.setContextAcceptLanguage(
			new AcceptLanguage() {

				@Override
				public List<Locale> getLocales() {
					return Arrays.asList(LocaleUtil.getDefault());
				}

				@Override
				public String getPreferredLanguageId() {
					return LocaleUtil.toLanguageId(LocaleUtil.getDefault());
				}

				@Override
				public Locale getPreferredLocale() {
					return LocaleUtil.getDefault();
				}

			});

		_objectEntryFolderResource.setContextCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));

		_objectEntryFolderResource.setContextUser(
			UserTestUtil.addUser(
				_companyLocalService.getCompany(TestPropsValues.getCompanyId()),
				RoleConstants.CMS_ADMINISTRATOR));
	}

	@Test
	@TestInfo("LPD-83026")
	public void testRead() throws Exception {
		Group cmsGroup = CMSTestUtil.getOrAddGroup(
			ObjectEntryFolderVulcanBatchEngineTaskItemDelegateTest.class);

		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			null, DepotConstants.TYPE_SPACE,
			ServiceContextTestUtil.getServiceContext(
				cmsGroup.getGroupId(), TestPropsValues.getUserId()));

		Assert.assertEquals(
			2,
			_objectEntryFolderLocalService.getObjectEntryFoldersCount(
				depotEntry.getGroupId(), depotEntry.getCompanyId(),
				GroupConstants.DEFAULT_PARENT_GROUP_ID));

		VulcanBatchEngineTaskItemDelegate<ObjectEntryFolder>
			vulcanBatchEngineTaskItemDelegate =
				(VulcanBatchEngineTaskItemDelegate<ObjectEntryFolder>)
					_objectEntryFolderResource;

		Page<ObjectEntryFolder> page = vulcanBatchEngineTaskItemDelegate.read(
			null, Pagination.of(1, 2), null,
			HashMapBuilder.<String, Serializable>put(
				"siteId", depotEntry.getGroupId()
			).build(),
			null);

		Assert.assertEquals(0, page.getTotalCount());

		_objectEntryFolderLocalService.addObjectEntryFolder(
			null, depotEntry.getGroupId(), TestPropsValues.getUserId(),
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
			RandomTestUtil.randomString(),
			HashMapBuilder.put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			RandomTestUtil.randomString(), new ServiceContext());

		Assert.assertEquals(
			3,
			_objectEntryFolderLocalService.getObjectEntryFoldersCount(
				depotEntry.getGroupId(), depotEntry.getCompanyId(),
				GroupConstants.DEFAULT_PARENT_GROUP_ID));

		page = vulcanBatchEngineTaskItemDelegate.read(
			null, Pagination.of(1, 2), null,
			HashMapBuilder.<String, Serializable>put(
				"siteId", depotEntry.getGroupId()
			).build(),
			null);

		Assert.assertEquals(1, page.getTotalCount());
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

	@Inject
	private ObjectEntryFolderResource _objectEntryFolderResource;

}
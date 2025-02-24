/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.batch.exportimport.internal.engine.test;

import com.liferay.analytics.dxp.entity.rest.dto.v1_0.DXPEntity;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.pagination.Page;
import com.liferay.batch.engine.pagination.Pagination;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collection;
import java.util.Collections;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Caio Pinheiro
 */
@RunWith(Arquillian.class)
public class ExpandoColumnAnalyticsDXPEntityBatchEngineTaskItemDelegateTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());

		_companyId = TestPropsValues.getCompanyId();

		_user = UserTestUtil.addCompanyAdminUser(
			CompanyLocalServiceUtil.getCompany(_companyId));

		_group = GroupTestUtil.addGroup(
			_companyId, _user.getUserId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		_expandoTable = _expandoTableLocalService.addTable(
			_companyId, PortalUtil.getClassNameId(User.class),
			ExpandoTableConstants.DEFAULT_TABLE_NAME);

		_expandoColumnLocalService.addColumn(
			_expandoTable.getTableId(), "customField",
			ExpandoColumnConstants.STRING);
		_expandoColumnLocalService.addColumn(
			_expandoTable.getTableId(), "customField3",
			ExpandoColumnConstants.STRING);
		_expandoColumnLocalService.addColumn(
			_expandoTable.getTableId(), "customField4",
			ExpandoColumnConstants.STRING);
		_expandoColumnLocalService.addColumn(
			_expandoTable.getTableId(), "customField5",
			ExpandoColumnConstants.STRING);
		_expandoColumnLocalService.addColumn(
			_expandoTable.getTableId(), "customField6",
			ExpandoColumnConstants.STRING);
		_expandoColumnLocalService.addColumn(
			_expandoTable.getTableId(), "testField",
			ExpandoColumnConstants.STRING);

		ReflectionTestUtil.setFieldValue(
			_batchEngineTaskItemDelegate, "contextCompany",
			CompanyLocalServiceUtil.getCompany(_companyId));
	}

	@After
	public void tearDown() throws Exception {
		_expandoColumnLocalService.deleteColumns(_expandoTable.getTableId());
	}

	@Test
	public void testSendExpandoColumnAnalytics() throws Exception {
		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						_companyId, AnalyticsConfiguration.class.getName(),
						HashMapDictionaryBuilder.<String, Object>put(
							"syncAllContacts", true
						).put(
							"syncedUserFieldNames",
							new String[] {
								"customField", "customField5", "testField"
							}
						).build())) {

			Page<DXPEntity> resultPage = _batchEngineTaskItemDelegate.read(
				null, Pagination.of(1, 7), null, Collections.emptyMap(), null);

			Collection<DXPEntity> dxpEntitiesPage = resultPage.getItems();

			Assert.assertEquals(
				dxpEntitiesPage.toString(), 3, dxpEntitiesPage.size());
		}
	}

	@Inject
	private AnalyticsSettingsManager _analyticsSettingsManager;

	@Inject(
		filter = "component.name=com.liferay.analytics.batch.exportimport.internal.engine.ExpandoColumnAnalyticsDXPEntityBatchEngineTaskItemDelegate"
	)
	private BatchEngineTaskItemDelegate<DXPEntity> _batchEngineTaskItemDelegate;

	private long _companyId;

	@Inject
	private ExpandoColumnLocalService _expandoColumnLocalService;

	private ExpandoTable _expandoTable;

	@Inject
	private ExpandoTableLocalService _expandoTableLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private User _user;

}
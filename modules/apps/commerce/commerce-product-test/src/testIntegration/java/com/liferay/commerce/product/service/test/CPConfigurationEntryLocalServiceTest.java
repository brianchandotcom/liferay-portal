/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.model.CPConfigurationEntry;
import com.liferay.commerce.product.model.CPConfigurationList;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPConfigurationEntryLocalService;
import com.liferay.commerce.product.service.CPConfigurationListLocalService;
import com.liferay.commerce.product.service.CommerceCatalogService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.math.BigDecimal;

import java.util.List;

import org.frutilla.FrutillaRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Andrea Sbarra
 */
@RunWith(Arquillian.class)
public class CPConfigurationEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), _user.getUserId());

		_commerceCatalog = _commerceCatalogService.addCommerceCatalog(
			RandomTestUtil.randomString(),
			AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(), "USD", "en_US", _serviceContext);

		_cpConfigurationList =
			_cpConfigurationListLocalService.getMasterCPConfigurationList(
				_commerceCatalog.getGroupId());

		_cpDefinition = CPTestUtil.addCPDefinition(
			_commerceCatalog.getGroupId());
	}

	@After
	public void tearDown() throws Exception {
		_cpConfigurationListLocalService.deleteCPConfigurationLists(
			_serviceContext.getCompanyId());
	}

	@Test
	public void testAddCPConfigurationEntry() throws Exception {
		frutillaRule.scenario(
			"Add Product Configuration Entry"
		).given(
			"There is a Commerce Catalog and its master configuration"
		).when(
			"A Configuration Entry is added"
		).then(
			"The Configuration Entry is created"
		);

		String externalReferenceCode = RandomTestUtil.randomString();

		CPConfigurationEntry cpConfigurationEntry1 =
			_cpConfigurationEntryLocalService.addCPConfigurationEntry(
				externalReferenceCode, _user.getUserId(),
				_cpConfigurationList.getGroupId(),
				_portal.getClassNameId(CPDefinition.class),
				_cpDefinition.getCPDefinitionId(),
				_cpConfigurationList.getCPConfigurationListId(), "123", true, 0,
				"cpde", true, true, "lowstoc", BigDecimal.TEN, BigDecimal.ONE,
				BigDecimal.ONE, BigDecimal.ONE);

		Assert.assertNotNull(cpConfigurationEntry1);
		Assert.assertEquals(
			externalReferenceCode,
			cpConfigurationEntry1.getExternalReferenceCode());

		List<CPConfigurationEntry> cpConfigurationEntries =
			_cpConfigurationEntryLocalService.getCPConfigurationEntries(
				_cpConfigurationList.getCPConfigurationListId());

		CPConfigurationEntry cpConfigurationEntry2 = cpConfigurationEntries.get(
			0);

		Assert.assertEquals(
			cpConfigurationEntry1.getCPConfigurationEntryId(),
			cpConfigurationEntry2.getCPConfigurationEntryId());
	}

	@Rule
	public final FrutillaRule frutillaRule = new FrutillaRule();

	private CommerceCatalog _commerceCatalog;

	@Inject
	private CommerceCatalogService _commerceCatalogService;

	@Inject
	private CPConfigurationEntryLocalService _cpConfigurationEntryLocalService;

	private CPConfigurationList _cpConfigurationList;

	@Inject
	private CPConfigurationListLocalService _cpConfigurationListLocalService;

	private CPDefinition _cpDefinition;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private Portal _portal;

	private ServiceContext _serviceContext;
	private User _user;

}
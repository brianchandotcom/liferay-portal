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

package com.liferay.commerce.product.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPInstanceOptionValueRel;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CPInstanceOptionValueRelLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalServiceUtil;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.product.type.simple.constants.SimpleCPTypeConstants;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.frutilla.FrutillaRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Igor Beslic
 */
@RunWith(Arquillian.class)
public class CPInstanceOptionValueRelLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_company = CompanyTestUtil.addCompany();

		_commerceCatalog = CommerceCatalogLocalServiceUtil.addCommerceCatalog(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			LocaleUtil.US.getDisplayLanguage(), null,
			ServiceContextTestUtil.getServiceContext(_company.getGroupId()));
	}

	@Test
	public void testAddCPInstanceOptionValueRelOnBuildCPInstances()
		throws Exception {

		frutillaRule.scenario(
			"Build all product SKU combinations"
		).given(
			"I have a product definition"
		).when(
			"two SKU contributor options are added to definition"
		).and(
			"each option has four values"
		).and(
			"generate all product instance combinations is invoked"
		).then(
			"each approved product instance has four option values"
		);

		int cpOptionsCount = 4;
		int cpOptionValuesCount = 4;

		CPDefinition cpDefinition = CPTestUtil.addCPDefinitionFromCatalog(
			_commerceCatalog.getGroupId(), SimpleCPTypeConstants.NAME, true,
			true);

		CPTestUtil.addCPOption(
			_commerceCatalog.getGroupId(), cpDefinition.getCPDefinitionId(),
			cpOptionsCount, cpOptionValuesCount);

		_cpInstanceLocalService.buildCPInstances(
			cpDefinition.getCPDefinitionId(),
			ServiceContextTestUtil.getServiceContext(
				cpDefinition.getGroupId()));

		List<CPInstance> cpDefinitionInstances =
			_cpInstanceLocalService.getCPDefinitionInstances(
				cpDefinition.getCPDefinitionId(),
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		for (CPInstance cpInstance : cpDefinitionInstances) {
			List<CPInstanceOptionValueRel> cpInstanceCPInstanceOptionValueRels =
				_cpInstanceOptionValueRelLocalService.
					getCPInstanceCPInstanceOptionValueRels(
						cpInstance.getCPInstanceId());

			Assert.assertEquals(
				"Product instance option count", cpOptionsCount,
				cpInstanceCPInstanceOptionValueRels.size());
		}
	}

	@Test
	public void testGetCPDefinitionCPInstanceOptionValueRels()
		throws Exception {

		frutillaRule.scenario(
			"Get all product instance options"
		).given(
			"I have a product definition"
		).when(
			"three SKU contributor options are added to definition"
		).and(
			"each option has four values"
		).and(
			"generate all product instance combinations is invoked"
		).then(
			"243 product instances options are generated"
		);

		int cpOptionsCount = 3;
		int cpOptionValuesCount = 4;

		CPDefinition cpDefinition = CPTestUtil.addCPDefinitionFromCatalog(
			_commerceCatalog.getGroupId(), SimpleCPTypeConstants.NAME, true,
			true);

		CPTestUtil.addCPOption(
			_commerceCatalog.getGroupId(), cpDefinition.getCPDefinitionId(),
			cpOptionsCount, cpOptionValuesCount);

		_cpInstanceLocalService.buildCPInstances(
			cpDefinition.getCPDefinitionId(),
			ServiceContextTestUtil.getServiceContext(
				cpDefinition.getGroupId()));

		List<CPInstanceOptionValueRel> cpDefinitionCPInstanceOptionValueRels =
			_cpInstanceOptionValueRelLocalService.
				getCPDefinitionCPInstanceOptionValueRels(
					cpDefinition.getCPDefinitionId());

		Assert.assertEquals(
			"Product instance count",
			(int)Math.pow(cpOptionValuesCount, cpOptionsCount) * cpOptionsCount,
			cpDefinitionCPInstanceOptionValueRels.size());
	}

	@Rule
	public final FrutillaRule frutillaRule = new FrutillaRule();

	private CommerceCatalog _commerceCatalog;

	@Inject
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	@DeleteAfterTestRun
	private Company _company;

	@Inject
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Inject
	private CPInstanceLocalService _cpInstanceLocalService;

	@Inject
	private CPInstanceOptionValueRelLocalService
		_cpInstanceOptionValueRelLocalService;

}
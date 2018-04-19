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
import com.liferay.commerce.product.configuration.CPOptionConfiguration;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CPOptionLocalService;
import com.liferay.commerce.product.service.CPOptionValueLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.product.type.simple.constants.SimpleCPTypeConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.settings.SystemSettingsLocator;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luca Pellizzon
 */
@RunWith(Arquillian.class)
public class CPDefinitionAddTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddCPDefinition() throws Exception {
		CPDefinition cpDefinition = CPTestUtil.addCPDefinition(
			SimpleCPTypeConstants.NAME, false, false, _group.getGroupId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT, cpDefinition.getStatus());
	}

	@Test
	public void testAddCPDefinition_HDI() throws Exception {
		CPDefinition cpDefinition = CPTestUtil.addCPDefinition(
			SimpleCPTypeConstants.NAME, false, true, _group.getGroupId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT, cpDefinition.getStatus());

		List<CPInstance> cpInstances =
			_cpInstanceLocalService.getCPDefinitionInstances(
				cpDefinition.getCPDefinitionId());

		Assert.assertEquals(cpInstances.toString(), 1, cpInstances.size());

		CPInstance cpInstance = cpInstances.get(0);

		Assert.assertEquals(
			WorkflowConstants.STATUS_INACTIVE, cpInstance.getStatus());
	}

	@Test
	public void testAddCPDefinition_HDI_MultiSKUs() throws Exception {
		int skuContributors = 0;
		int numberOfOptions = 10;

		CPDefinition cpDefinition = CPTestUtil.addCPDefinition(
			SimpleCPTypeConstants.NAME, false, true, _group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		CPOptionConfiguration cpOptionConfiguration =
			ConfigurationProviderUtil.getConfiguration(
				CPOptionConfiguration.class,
				new SystemSettingsLocator(CPConstants.CP_OPTION_SERVICE_NAME));

		String[] ddmFormFieldTypesAllowed =
			cpOptionConfiguration.ddmFormFieldTypesAllowed();

		for (int i = 0; i < numberOfOptions; i++) {
			CPOption cpOption = _cpOptionLocalService.addCPOption(
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				ddmFormFieldTypesAllowed[0], RandomTestUtil.randomBoolean(),
				RandomTestUtil.randomBoolean(), RandomTestUtil.randomBoolean(),
				RandomTestUtil.randomString(), serviceContext);

			_cpOptionValueLocalService.addCPOptionValue(
				cpOption.getCPOptionId(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.nextDouble(), RandomTestUtil.randomString(),
				serviceContext);

			_cpDefinitionOptionRelLocalService.addCPDefinitionOptionRel(
				cpDefinition.getCPDefinitionId(), cpOption.getCPOptionId(),
				serviceContext);

			if (cpOption.isSkuContributor()) {
				skuContributors++;
			}
		}

		int cpOptionCount = _cpOptionLocalService.getCPOptionsCount(
			_group.getGroupId());

		Assert.assertEquals(numberOfOptions, cpOptionCount);

		int cpDefinitionOptionRelCount =
			_cpDefinitionOptionRelLocalService.getCPDefinitionOptionRelsCount(
				cpDefinition.getCPDefinitionId());

		Assert.assertEquals(numberOfOptions, cpDefinitionOptionRelCount);

		_cpInstanceLocalService.buildCPInstances(
			cpDefinition.getCPDefinitionId(), serviceContext);

		List<CPInstance> cpInstances =
			_cpInstanceLocalService.getCPDefinitionInstances(
				cpDefinition.getCPDefinitionId());

		Assert.assertEquals(
			cpInstances.toString(), skuContributors + 1, cpInstances.size());

		int end = _cpOptionLocalService.getCPOptionsCount(_group.getGroupId());
		int start = 0;

		List<CPOption> cpOptions = _cpOptionLocalService.getCPOptions(
			start, end);

		if (skuContributors > 0) {
			Assert.assertEquals(
				WorkflowConstants.STATUS_APPROVED, cpDefinition.getStatus());
		}

		CPInstance cpInstance = _cpInstanceLocalService.getCPInstance(
			cpDefinition.getCPDefinitionId(), "default");

		Assert.assertEquals(
			WorkflowConstants.STATUS_INACTIVE, cpInstance.getStatus());
	}

	@Test
	public void testAddCPDefinition_ISC() throws Exception {
		CPDefinition cpDefinition = CPTestUtil.addCPDefinition(
			SimpleCPTypeConstants.NAME, true, false, _group.getGroupId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT, cpDefinition.getStatus());

		int count = _cpInstanceLocalService.getCPDefinitionInstancesCount(
			cpDefinition.getCPDefinitionId(), WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(0, count);
	}

	@Test
	public void testAddCPDefinition_ISC_HDI() throws Exception {
		CPDefinition cpDefinition = CPTestUtil.addCPDefinition(
			SimpleCPTypeConstants.NAME, true, true, _group.getGroupId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, cpDefinition.getStatus());

		List<CPInstance> cpInstances =
			_cpInstanceLocalService.getCPDefinitionInstances(
				cpDefinition.getCPDefinitionId());

		int onlyOneApproved = 0;

		for (CPInstance cpInstance : cpInstances) {
			if (cpInstance.getStatus() == WorkflowConstants.STATUS_APPROVED) {
				onlyOneApproved++;
			}
		}

		Assert.assertEquals(1, onlyOneApproved);
	}

	@Inject
	private static CPDefinitionOptionRelLocalService
		_cpDefinitionOptionRelLocalService;

	@Inject
	private static CPInstanceLocalService _cpInstanceLocalService;

	@Inject
	private static CPOptionLocalService _cpOptionLocalService;

	@Inject
	private static CPOptionValueLocalService _cpOptionValueLocalService;

	@DeleteAfterTestRun
	private Group _group;

}
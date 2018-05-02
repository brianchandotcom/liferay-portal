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
import com.liferay.commerce.product.model.CPInstanceConstants;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CPOptionLocalService;
import com.liferay.commerce.product.service.CPOptionValueLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.product.type.simple.constants.SimpleCPTypeConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
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
		frutillaRule.scenario(
			"Add product definition"
		).given(
			"I add a product definition"
		).when(
			"ignoreSKUCombinations is false"
		).and(
			"hasDefaultInstance is false"
		).then(
			"product definition should be DRAFT"
		);

		CPDefinition cpDefinition = CPTestUtil.addCPDefinition(
			_group.getGroupId(), SimpleCPTypeConstants.NAME, false, false);

		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT, cpDefinition.getStatus());
	}

	@Test
	public void testAddCPDefinitionWithDefaultInstance() throws Exception {
		frutillaRule.scenario(
			"Add product definition"
		).given(
			"I add a product definition"
		).when(
			"ignoreSKUCombinations is false"
		).and(
			"hasDefaultInstance is true"
		).then(
			"product definition should be DRAFT"
		).and(
			"default product instance should be INACTIVE"
		);

		CPDefinition cpDefinition = CPTestUtil.addCPDefinition(
			_group.getGroupId(), SimpleCPTypeConstants.NAME, false, true);

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
	public void testAddCPDefinitionWithDefaultInstanceAndSKUs()
		throws Exception {

		frutillaRule.scenario(
			"Add product definition"
		).given(
			"I add a product definition"
		).when(
			"ignoreSKUCombinations is false"
		).and(
			"hasDefaultInstance is true"
		).and(
			"some product instances are added to the definition"
		).then(
			"product definition should be APPROVED"
		).and(
			"default product instance should be INACTIVE"
		);

		int cpOptionsCount = 2;
		int cpOptionValuesCount = 2;

		CPDefinition cpDefinition = CPTestUtil.addCPDefinition(
			_group.getGroupId(), SimpleCPTypeConstants.NAME, false, true);

		for (int i = 0; i < cpOptionsCount; i++) {
			CPOption cpOption = CPTestUtil.addCPOption(
				_group.getGroupId(), true);

			for (int j = 0; j < cpOptionValuesCount; i++) {
				CPTestUtil.addCPOptionValue(cpOption);
			}

			CPTestUtil.addCPDefinitionOptionRel(
				_group.getGroupId(), cpDefinition.getCPDefinitionId(),
				cpOption.getCPOptionId());
		}

		Assert.assertEquals(
			cpOptionsCount,
			_cpOptionLocalService.getCPOptionsCount(_group.getGroupId()));

		Assert.assertEquals(
			cpOptionsCount,
			_cpDefinitionOptionRelLocalService.getCPDefinitionOptionRelsCount(
				cpDefinition.getCPDefinitionId()));

		CPTestUtil.buildCPInstances(cpDefinition);

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, cpDefinition.getStatus());

		CPInstance cpInstance = _cpInstanceLocalService.getCPInstance(
			cpDefinition.getCPDefinitionId(), CPInstanceConstants.DEFAULT_SKU);

		Assert.assertEquals(
			WorkflowConstants.STATUS_INACTIVE, cpInstance.getStatus());
	}

	@Test
	public void testAddCPDefinitionWithIgnoreSKUCombinations()
		throws Exception {

		frutillaRule.scenario(
			"Add product definition"
		).given(
			"I add a product definition"
		).when(
			"ignoreSKUCombinations is true"
		).and(
			"hasDefaultInstance is false"
		).then(
			"product definition should be DRAFT"
		).and(
			"product definition should have no instances"
		);

		CPDefinition cpDefinition = CPTestUtil.addCPDefinition(
			_group.getGroupId(), SimpleCPTypeConstants.NAME, true, false);

		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT, cpDefinition.getStatus());

		int count = _cpInstanceLocalService.getCPDefinitionInstancesCount(
			cpDefinition.getCPDefinitionId(), WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(0, count);
	}

	@Test
	public void testAddCPDefinitionWithIgnoreSKUCombinationsAndDefaultInstance()
		throws Exception {

		frutillaRule.scenario(
			"Add product definition"
		).given(
			"I add a product definition"
		).when(
			"ignoreSKUCombinations is true"
		).and(
			"hasDefaultInstance is true"
		).then(
			"product definition should be APPROVED"
		).and(
			"default product instance should be APPROVED"
		);

		CPDefinition cpDefinition = CPTestUtil.addCPDefinition(
			_group.getGroupId(), SimpleCPTypeConstants.NAME, true, true);

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, cpDefinition.getStatus());

		List<CPInstance> cpInstances =
			_cpInstanceLocalService.getCPDefinitionInstances(
				cpDefinition.getCPDefinitionId());

		int approvedCPInstances = 0;

		for (CPInstance cpInstance : cpInstances) {
			if (cpInstance.isApproved()) {
				approvedCPInstances++;
			}
		}

		Assert.assertEquals(1, approvedCPInstances);
	}

	@Rule
	public final FrutillaRule frutillaRule = new FrutillaRule();

	@Inject
	private static CPDefinitionOptionRelLocalService
		_cpDefinitionOptionRelLocalService;

	@Inject
	private static CPDefinitionOptionValueRelLocalService
		_cpDefinitionOptionValueRelLocalService;

	@Inject
	private static CPInstanceLocalService _cpInstanceLocalService;

	@Inject
	private static CPOptionLocalService _cpOptionLocalService;

	@Inject
	private static CPOptionValueLocalService _cpOptionValueLocalService;

	@DeleteAfterTestRun
	private Group _group;

}
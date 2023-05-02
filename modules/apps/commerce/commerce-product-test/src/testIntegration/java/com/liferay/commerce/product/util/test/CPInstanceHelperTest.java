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

package com.liferay.commerce.product.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.exception.CPDefinitionIgnoreSKUCombinationsException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CPOptionLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalServiceUtil;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.product.type.simple.constants.SimpleCPTypeConstants;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.frutilla.FrutillaRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Igor Beslic
 * @author Alessio Antonio Rendina
 */
@RunWith(Arquillian.class)
public class CPInstanceHelperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_company = CompanyTestUtil.addCompany();
	}

	@Before
	public void setUp() throws Exception {
		_commerceCatalog = CommerceCatalogLocalServiceUtil.addCommerceCatalog(
			null, RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			LocaleUtil.US.getDisplayLanguage(),
			ServiceContextTestUtil.getServiceContext(_company.getGroupId()));
	}

	@After
	public void tearDown() throws Exception {
		List<CPDefinition> cpDefinitions =
			_cpDefinitionLocalService.getCPDefinitions(
				_commerceCatalog.getGroupId(), WorkflowConstants.STATUS_ANY,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CPDefinition cpDefinition : cpDefinitions) {
			_cpDefinitionLocalService.deleteCPDefinition(cpDefinition);
		}

		_cpOptionLocalService.deleteCPOptions(_company.getCompanyId());
	}

	@Test
	public void testFetchCPInstanceIgnoreSkuCombinationsFalse()
		throws Exception {

		frutillaRule.scenario(
			"Fetch CP instance with specified SKU contributor options"
		).given(
			StringBundler.concat(
				"I have a product definition with SKU contributor options ",
				"Option_1 and Option_2 with two values assigned to each of ",
				"them so there are Option_1_Value_1, Option_1_Value_2, ",
				"Option_2_Value_1, Option_2_Value_2.")
		).when(
			"There is only CP instance A that represents SKU value " +
				"combination Option_1_Value_2, Option_2_Value_1"
		).and(
			"serialized DDM form values contains combination " +
				"Option_1_Value_2, Option_2_Value_1"
		).then(
			"CP instance A must be fetched"
		).but(
			StringBundler.concat(
				"If serialized DDM form values contains combination other ",
				"than Option_1_Value_2, Option_2_Value_1 nothing should be ",
				"fetched")
		);

		int cpOptionsCount = 2;
		int cpOptionValuesCount = 2;

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

		CPInstance cpInstanceA = cpDefinitionInstances.get(2);

		List<String> deletedCPInstanceDDMFormSerializedValues =
			new ArrayList<>();

		for (CPInstance cpDefinitionInstance : cpDefinitionInstances) {
			if (cpDefinitionInstance.getCPInstanceId() ==
					cpInstanceA.getCPInstanceId()) {

				continue;
			}

			deletedCPInstanceDDMFormSerializedValues.add(
				_getSerializedDDMFormValues(cpDefinitionInstance));

			_cpInstanceLocalService.deleteCPInstance(cpDefinitionInstance);
		}

		cpDefinitionInstances =
			_cpInstanceLocalService.getCPDefinitionInstances(
				cpDefinition.getCPDefinitionId(),
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		Assert.assertEquals(
			"Product instance count", 1, cpDefinitionInstances.size());

		CPInstance fetchCPInstance = _cpInstanceHelper.fetchCPInstance(
			cpDefinition.getCPDefinitionId(),
			_getSerializedDDMFormValues(cpInstanceA));

		Assert.assertNotNull("Fetched CP instance exist", fetchCPInstance);

		Assert.assertEquals(
			"Fetched CP instance equals CP instance A",
			cpInstanceA.getCPInstanceId(), fetchCPInstance.getCPInstanceId());

		for (String deletedCPInstanceDDMFormSerializedValue :
				deletedCPInstanceDDMFormSerializedValues) {

			Assert.assertNull(
				_cpInstanceHelper.fetchCPInstance(
					cpDefinition.getCPDefinitionId(),
					deletedCPInstanceDDMFormSerializedValue));
		}
	}

	@Test
	public void testFetchCPInstanceIgnoreSkuCombinationsTrue()
		throws Exception {

		frutillaRule.scenario(
			"Fetch CP instance for product with no SKU contributor options"
		).given(
			"I have a product definition with no SKU contributor options"
		).when(
			"Preview of product is invoked"
		).then(
			"Product default ACTIVE CP instance must be returned"
		);

		CPDefinition cpDefinition = CPTestUtil.addCPDefinitionFromCatalog(
			_commerceCatalog.getGroupId(), SimpleCPTypeConstants.NAME, true,
			true);

		CPOption cpOption = CPTestUtil.addCPOption(
			_commerceCatalog.getGroupId(), false);

		CPTestUtil.addCPOptionValue(cpOption);
		CPTestUtil.addCPOptionValue(cpOption);

		CPTestUtil.addCPDefinitionOptionRel(
			_commerceCatalog.getGroupId(), cpDefinition.getCPDefinitionId(),
			cpOption.getCPOptionId());

		List<CPInstance> cpDefinitionInstances =
			_cpInstanceLocalService.getCPDefinitionInstances(
				cpDefinition.getCPDefinitionId(),
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		Assert.assertEquals(
			"Approved instances count", 1, cpDefinitionInstances.size());

		CPInstance expectedCPInstance = cpDefinitionInstances.get(0);

		CPInstance fetchCPInstance = _cpInstanceHelper.fetchCPInstance(
			cpDefinition.getCPDefinitionId(), StringPool.BLANK);

		Assert.assertEquals(
			"Default CP instance cpInstanceId",
			expectedCPInstance.getCPInstanceId(),
			fetchCPInstance.getCPInstanceId());

		_cpInstanceLocalService.deleteCPInstance(expectedCPInstance);

		cpDefinitionInstances =
			_cpInstanceLocalService.getCPDefinitionInstances(
				cpDefinition.getCPDefinitionId(),
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		Assert.assertEquals(
			"Product instance count", 0, cpDefinitionInstances.size());

		fetchCPInstance = _cpInstanceHelper.fetchCPInstance(
			cpDefinition.getCPDefinitionId(), StringPool.BLANK);

		Assert.assertNull(
			"Fetched CP instance does not exist", fetchCPInstance);
	}

	@Test
	public void testGetDefaultCPInstance() throws Exception {
		frutillaRule.scenario(
			"Inactivate product with stale option combination"
		).given(
			StringBundler.concat(
				"I have a product definition with one SKU contributor option ",
				"with three values assigned to it. There is product instance ",
				"A that refers to first option value")
		).when(
			"new SKU contributor option is added to definition"
		).and(
			"option has three values"
		).then(
			"product instance A should be inactivated"
		);

		CPDefinition cpDefinition = CPTestUtil.addCPDefinitionFromCatalog(
			_commerceCatalog.getGroupId(), SimpleCPTypeConstants.NAME, true,
			true);

		List<CPInstance> approvedCPDefinitionInstances =
			_cpInstanceLocalService.getCPDefinitionInstances(
				cpDefinition.getCPDefinitionId(),
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		Assert.assertEquals(
			"Product approved instances count", 1,
			approvedCPDefinitionInstances.size());

		CPInstance cpInstance = approvedCPDefinitionInstances.get(0);

		CPInstance defaultCPInstance = _cpInstanceHelper.getDefaultCPInstance(
			cpDefinition.getCPDefinitionId());

		Assert.assertEquals(
			"Default CP instance ID value", cpInstance.getCPInstanceId(),
			defaultCPInstance.getCPInstanceId());
	}

	@Test(expected = CPDefinitionIgnoreSKUCombinationsException.class)
	public void testGetDefaultCPInstanceIfSKUContributorOptionPresent()
		throws Exception {

		frutillaRule.scenario(
			"Failure if default CP instance is seeked on product with SKU " +
				"contributor options"
		).given(
			"I have a product definition with one SKU contributor option " +
				"with three values assigned to it."
		).when(
			"default CP Instance is seeked"
		).then(
			"Illegal argument exception occurs"
		);

		CPDefinition cpDefinition = CPTestUtil.addCPDefinitionFromCatalog(
			_commerceCatalog.getGroupId(), SimpleCPTypeConstants.NAME, true,
			true);

		CPOption cpOption = CPTestUtil.addCPOption(
			_commerceCatalog.getGroupId(), true);

		CPTestUtil.addCPOptionValue(cpOption);
		CPTestUtil.addCPOptionValue(cpOption);

		CPDefinitionOptionRel cpDefinitionOptionRel =
			CPTestUtil.addCPDefinitionOptionRel(
				_commerceCatalog.getGroupId(), cpDefinition.getCPDefinitionId(),
				cpOption.getCPOptionId());

		CPTestUtil.addCPDefinitionCPInstance(
			cpDefinition.getCPDefinitionId(),
			HashMapBuilder.<Long, List<Long>>put(
				cpDefinitionOptionRel.getCPDefinitionOptionRelId(),
				() -> {
					List<CPDefinitionOptionValueRel>
						cpDefinitionOptionValueRels =
							cpDefinitionOptionRel.
								getCPDefinitionOptionValueRels();

					CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
						cpDefinitionOptionValueRels.get(0);

					return Arrays.asList(
						cpDefinitionOptionValueRel.
							getCPDefinitionOptionValueRelId());
				}
			).build());

		List<CPInstance> approvedCPDefinitionInstances =
			_cpInstanceLocalService.getCPDefinitionInstances(
				cpDefinition.getCPDefinitionId(),
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		Assert.assertEquals(
			"Product approved instances count", 1,
			approvedCPDefinitionInstances.size());

		_cpInstanceHelper.getDefaultCPInstance(
			cpDefinition.getCPDefinitionId());
	}

	@Test
	public void testPublicStoreOptionsOrder() throws Exception {
		frutillaRule.scenario(
			"Check the order of product options on the front store"
		).given(
			"a product with multiple options with different priorities"
		).when(
			"the method renderOptions is called from CPContentHelper"
		).then(
			"the options Map are sorted ascending by priority."
		);

		CPDefinition cpDefinition = CPTestUtil.addCPDefinition(
			_commerceCatalog.getGroupId());

		CPDefinitionOptionRel cpDefinitionOptionRel1 =
			CPTestUtil.addCPDefinitionOptionRel(
				cpDefinition.getGroupId(), cpDefinition.getCPDefinitionId(),
				true, 2);

		cpDefinitionOptionRel1.setPriority(2);

		_cpDefinitionOptionRelLocalService.updateCPDefinitionOptionRel(
			cpDefinitionOptionRel1);

		CPDefinitionOptionRel cpDefinitionOptionRel2 =
			CPTestUtil.addCPDefinitionOptionRel(
				cpDefinition.getGroupId(), cpDefinition.getCPDefinitionId(),
				true, 2);

		cpDefinitionOptionRel2.setPriority(1);

		_cpDefinitionOptionRelLocalService.updateCPDefinitionOptionRel(
			cpDefinitionOptionRel2);

		Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			cpDefinitionOptionValueRelsMap =
				_cpInstanceHelper.getCPDefinitionOptionValueRelsMap(
					cpDefinition.getCPDefinitionId(), true, true);

		Assert.assertNotNull(cpDefinitionOptionValueRelsMap);
		Assert.assertEquals(
			cpDefinitionOptionValueRelsMap.toString(), 2,
			cpDefinitionOptionValueRelsMap.size());

		List<CPDefinitionOptionRel> keys = new ArrayList<>(
			cpDefinitionOptionValueRelsMap.keySet());

		CPDefinitionOptionRel orderedCPDefinitionOptionRel1 = keys.get(0);
		CPDefinitionOptionRel orderedCPDefinitionOptionRel2 = keys.get(1);

		Assert.assertEquals(
			cpDefinitionOptionRel2, orderedCPDefinitionOptionRel1);
		Assert.assertEquals(
			cpDefinitionOptionRel1, orderedCPDefinitionOptionRel2);
	}

	@Test
	public void testPublicStoreOptionsWithSamePriorityOrder() throws Exception {
		frutillaRule.scenario(
			"Check the order of product options on the front store"
		).given(
			"a product with multiple options with same priorities"
		).when(
			"the method renderOptions is called from CPContentHelper"
		).then(
			"the options Map are sorted ascending by name."
		);

		CPDefinition cpDefinition = CPTestUtil.addCPDefinition(
			_commerceCatalog.getGroupId());

		CPDefinitionOptionRel cpDefinitionOptionRel1 =
			CPTestUtil.addCPDefinitionOptionRel(
				cpDefinition.getGroupId(), cpDefinition.getCPDefinitionId(),
				true, 2);

		cpDefinitionOptionRel1.setName("Size");

		_cpDefinitionOptionRelLocalService.updateCPDefinitionOptionRel(
			cpDefinitionOptionRel1);

		CPDefinitionOptionRel cpDefinitionOptionRel2 =
			CPTestUtil.addCPDefinitionOptionRel(
				cpDefinition.getGroupId(), cpDefinition.getCPDefinitionId(),
				true, 2);

		cpDefinitionOptionRel2.setName("Color");

		_cpDefinitionOptionRelLocalService.updateCPDefinitionOptionRel(
			cpDefinitionOptionRel2);

		Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			cpDefinitionOptionValueRelsMap =
				_cpInstanceHelper.getCPDefinitionOptionValueRelsMap(
					cpDefinition.getCPDefinitionId(), true, true);

		Assert.assertNotNull(cpDefinitionOptionValueRelsMap);
		Assert.assertEquals(
			cpDefinitionOptionValueRelsMap.toString(), 2,
			cpDefinitionOptionValueRelsMap.size());

		List<CPDefinitionOptionRel> keys = new ArrayList<>(
			cpDefinitionOptionValueRelsMap.keySet());

		CPDefinitionOptionRel orderedCPDefinitionOptionRel1 = keys.get(0);
		CPDefinitionOptionRel orderedCPDefinitionOptionRel2 = keys.get(1);

		Assert.assertEquals(
			cpDefinitionOptionRel2, orderedCPDefinitionOptionRel1);
		Assert.assertEquals(
			cpDefinitionOptionRel1, orderedCPDefinitionOptionRel2);
	}

	@Rule
	public final FrutillaRule frutillaRule = new FrutillaRule();

	private String _getSerializedDDMFormValues(CPInstance cpInstance)
		throws Exception {

		Map<String, List<String>>
			cpDefinitionOptionRelKeysCPDefinitionOptionValueRelKeys =
				_cpDefinitionOptionRelLocalService.
					getCPDefinitionOptionRelKeysCPDefinitionOptionValueRelKeys(
						cpInstance.getCPInstanceId());

		if (cpDefinitionOptionRelKeysCPDefinitionOptionValueRelKeys.isEmpty()) {
			return "[]";
		}

		Set<String> optionKeys =
			cpDefinitionOptionRelKeysCPDefinitionOptionValueRelKeys.keySet();

		Iterator<String> iterator = optionKeys.iterator();

		StringBundler sb = new StringBundler(
			11 *
				cpDefinitionOptionRelKeysCPDefinitionOptionValueRelKeys.size());

		sb.append(StringPool.OPEN_BRACKET);

		while (iterator.hasNext()) {
			String optionKey = iterator.next();

			sb.append("{\"key\":\"");
			sb.append(optionKey);
			sb.append("\",\"value\":[");

			List<String> optionValues =
				cpDefinitionOptionRelKeysCPDefinitionOptionValueRelKeys.get(
					optionKey);

			Iterator<String> optionValueIterator = optionValues.iterator();

			while (optionValueIterator.hasNext()) {
				String optionValueKey = optionValueIterator.next();

				sb.append(StringPool.QUOTE);
				sb.append(optionValueKey);
				sb.append(StringPool.QUOTE);

				if (optionValueIterator.hasNext()) {
					sb.append(StringPool.COMMA);
				}
			}

			sb.append(StringPool.CLOSE_BRACKET);
			sb.append(StringPool.CLOSE_CURLY_BRACE);

			if (iterator.hasNext()) {
				sb.append(StringPool.COMMA);
			}
		}

		sb.append(StringPool.CLOSE_BRACKET);

		return sb.toString();
	}

	private static Company _company;

	private CommerceCatalog _commerceCatalog;

	@Inject
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	@Inject
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Inject
	private CPDefinitionOptionRelLocalService
		_cpDefinitionOptionRelLocalService;

	@Inject
	private CPInstanceHelper _cpInstanceHelper;

	@Inject
	private CPInstanceLocalService _cpInstanceLocalService;

	@Inject
	private CPOptionLocalService _cpOptionLocalService;

}
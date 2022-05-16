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

package com.liferay.headless.commerce.admin.site.setting.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.commerce.admin.site.setting.client.dto.v1_0.MeasurementUnit;
import com.liferay.headless.commerce.admin.site.setting.client.pagination.Page;
import com.liferay.headless.commerce.admin.site.setting.client.pagination.Pagination;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zoltán Takács
 */
@RunWith(Arquillian.class)
public class MeasurementUnitResourceTest
	extends BaseMeasurementUnitResourceTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();

		Page<MeasurementUnit> page =
			measurementUnitResource.getMeasurementUnitsPage(
				null, Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		if (totalCount > 0) {
			for (MeasurementUnit measurementUnit : page.getItems()) {
				measurementUnitResource.deleteMeasurementUnit(
					measurementUnit.getId());
			}
		}
	}

	@Override
	@Test
	public void testGetMeasurementUnit() throws Exception {
		MeasurementUnit postMeasurementUnit =
			testGetMeasurementUnit_addMeasurementUnit();

		MeasurementUnit getMeasurementUnit =
			measurementUnitResource.getMeasurementUnit(
				postMeasurementUnit.getId());

		assertEquals(postMeasurementUnit, getMeasurementUnit);
		assertValid(getMeasurementUnit);
	}

	@Override
	@Test
	public void testGetMeasurementUnitByExternalReferenceCode()
		throws Exception {

		MeasurementUnit postMeasurementUnit = _postMeasurementUnit(
			randomMeasurementUnit());

		MeasurementUnit getMeasurementUnit =
			measurementUnitResource.getMeasurementUnitByExternalReferenceCode(
				postMeasurementUnit.getExternalReferenceCode());

		assertEquals(postMeasurementUnit, getMeasurementUnit);
		assertValid(getMeasurementUnit);
	}

	@Override
	@Test
	public void testGetMeasurementUnitByKey() throws Exception {
		MeasurementUnit postMeasurementUnit = _postMeasurementUnit(
			randomMeasurementUnit());

		MeasurementUnit getMeasurementUnit =
			measurementUnitResource.getMeasurementUnitByKey(
				postMeasurementUnit.getKey());

		assertEquals(postMeasurementUnit, getMeasurementUnit);
		assertValid(getMeasurementUnit);
	}

	@Ignore
	@Override
	@Test
	public void testGetMeasurementUnitsByTypeWithSortDateTime() {
	}

	@Ignore
	@Override
	@Test
	public void testGetMeasurementUnitsByTypeWithSortDouble() {
	}

	@Ignore
	@Override
	@Test
	public void testGetMeasurementUnitsByTypeWithSortInteger() {
	}

	@Ignore
	@Override
	@Test
	public void testGetMeasurementUnitsByTypeWithSortString() {
	}

	@Ignore
	@Override
	@Test
	public void testGetMeasurementUnitsPageWithFilterDateTimeEquals() {
	}

	@Ignore
	@Override
	@Test
	public void testGetMeasurementUnitsPageWithFilterDoubleEquals() {
	}

	@Ignore
	@Override
	@Test
	public void testGetMeasurementUnitsPageWithFilterStringEquals() {
	}

	@Ignore
	@Override
	@Test
	public void testGetMeasurementUnitsPageWithSortDateTime() {
	}

	@Ignore
	@Override
	@Test
	public void testGetMeasurementUnitsPageWithSortDouble() {
	}

	@Ignore
	@Override
	@Test
	public void testGetMeasurementUnitsPageWithSortInteger() {
	}

	@Ignore
	@Override
	@Test
	public void testGetMeasurementUnitsPageWithSortString() {
	}

	@Override
	@Test
	public void testPatchMeasurementUnit() throws Exception {
		MeasurementUnit measurementUnit = _postMeasurementUnit(
			randomMeasurementUnit());

		measurementUnit.setExternalReferenceCode("externalReferenceCode1");
		measurementUnit.setPriority(Double.valueOf("9.0"));
		measurementUnit.setRate(Double.valueOf("1.0"));

		measurementUnitResource.patchMeasurementUnit(
			measurementUnit.getId(), measurementUnit);

		MeasurementUnit getMeasurementUnit =
			measurementUnitResource.getMeasurementUnit(measurementUnit.getId());

		Assert.assertTrue(equals(measurementUnit, getMeasurementUnit));
	}

	@Override
	@Test
	public void testPatchMeasurementUnitByExternalReferenceCode()
		throws Exception {

		MeasurementUnit measurementUnit = _postMeasurementUnit(
			randomMeasurementUnit());

		String oldExternalReferenceCode =
			measurementUnit.getExternalReferenceCode();

		measurementUnit.setExternalReferenceCode("externalReferenceCode1");
		measurementUnit.setPriority(Double.valueOf("9.0"));
		measurementUnit.setRate(Double.valueOf("1.0"));

		measurementUnitResource.patchMeasurementUnitByExternalReferenceCode(
			oldExternalReferenceCode, measurementUnit);

		MeasurementUnit getMeasurementUnit =
			measurementUnitResource.getMeasurementUnit(measurementUnit.getId());

		Assert.assertTrue(equals(measurementUnit, getMeasurementUnit));
	}

	@Override
	@Test
	public void testPatchMeasurementUnitByKey() throws Exception {
		MeasurementUnit measurementUnit = _postMeasurementUnit(
			randomMeasurementUnit());

		measurementUnit.setExternalReferenceCode("externalReferenceCode1");
		measurementUnit.setPriority(Double.valueOf("9.0"));
		measurementUnit.setRate(Double.valueOf("1.0"));

		measurementUnitResource.patchMeasurementUnitByKey(
			measurementUnit.getKey(), measurementUnit);

		MeasurementUnit getMeasurementUnit =
			measurementUnitResource.getMeasurementUnit(measurementUnit.getId());

		Assert.assertTrue(equals(measurementUnit, getMeasurementUnit));
	}

	@Override
	@Test
	public void testPostMeasurementUnit() throws Exception {
		MeasurementUnit randomMeasurementUnit = randomMeasurementUnit();

		randomMeasurementUnit.setRate(Double.valueOf("1.0"));

		randomMeasurementUnit.setType("Unit");

		MeasurementUnit postMeasurementUnit = _postMeasurementUnit(
			randomMeasurementUnit);

		assertEquals(randomMeasurementUnit, postMeasurementUnit);
		assertValid(postMeasurementUnit);
	}

	@Override
	protected void assertValid(MeasurementUnit measurementUnit)
		throws Exception {

		boolean valid = true;

		if (measurementUnit.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("companyId", additionalAssertFieldName)) {
				if (measurementUnit.getCompanyId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					additionalAssertFieldName, "externalReferenceCode")) {

				if (measurementUnit.getExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("key", additionalAssertFieldName)) {
				if (measurementUnit.getKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (measurementUnit.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("priority", additionalAssertFieldName)) {
				if (measurementUnit.getPriority() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("rate", additionalAssertFieldName)) {
				if (measurementUnit.getRate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (measurementUnit.getType() == null) {
					valid = false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		Assert.assertTrue(valid);
	}

	@Override
	protected boolean equals(
		MeasurementUnit measurementUnit1, MeasurementUnit measurementUnit2) {

		if (measurementUnit1 == measurementUnit2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("companyId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						measurementUnit1.getCompanyId(),
						measurementUnit2.getCompanyId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					additionalAssertFieldName, "externalReferenceCode")) {

				if (!Objects.deepEquals(
						measurementUnit1.getExternalReferenceCode(),
						measurementUnit2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("key", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						measurementUnit1.getKey(), measurementUnit2.getKey())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!equals(
						(Map)measurementUnit1.getName(),
						(Map)measurementUnit2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("priority", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						measurementUnit1.getPriority(),
						measurementUnit2.getPriority())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("rate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						measurementUnit1.getRate(),
						measurementUnit2.getRate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						measurementUnit1.getType(),
						measurementUnit2.getType())) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		return true;
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"companyId", "externalReferenceCode", "key", "name", "priority",
			"rate", "type"
		};
	}

	@Override
	protected MeasurementUnit randomMeasurementUnit() throws Exception {
		return new MeasurementUnit() {
			{
				companyId = testCompany.getCompanyId();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());

				key = StringUtil.toLowerCase(RandomTestUtil.randomString());

				name = HashMapBuilder.put(
					LocaleUtil.US.toString(), RandomTestUtil.randomString()
				).build();

				primary = RandomTestUtil.randomBoolean();
				priority = RandomTestUtil.randomDouble();
				rate = RandomTestUtil.randomDouble();
				type = _typesList.get(
					RandomTestUtil.randomInt(0, _typesList.size() - 1));
			}
		};
	}

	@Override
	protected MeasurementUnit testDeleteMeasurementUnit_addMeasurementUnit()
		throws Exception {

		return _postMeasurementUnit(randomMeasurementUnit());
	}

	@Override
	protected MeasurementUnit
			testDeleteMeasurementUnitByExternalReferenceCode_addMeasurementUnit()
		throws Exception {

		return _postMeasurementUnit(randomMeasurementUnit());
	}

	@Override
	protected MeasurementUnit
			testDeleteMeasurementUnitByKey_addMeasurementUnit()
		throws Exception {

		return _postMeasurementUnit(randomMeasurementUnit());
	}

	@Override
	protected MeasurementUnit testGetMeasurementUnit_addMeasurementUnit()
		throws Exception {

		return _postMeasurementUnit(randomMeasurementUnit());
	}

	@Override
	protected MeasurementUnit testGetMeasurementUnitsByType_addMeasurementUnit(
			String measurementUnitType, MeasurementUnit measurementUnit)
		throws Exception {

		measurementUnit.setType(measurementUnitType);

		return _postMeasurementUnit(measurementUnit);
	}

	@Override
	protected String testGetMeasurementUnitsByType_getMeasurementUnitType()
		throws Exception {

		return _typesList.get(
			RandomTestUtil.randomInt(0, _typesList.size() - 1));
	}

	@Override
	protected MeasurementUnit testGetMeasurementUnitsPage_addMeasurementUnit(
			MeasurementUnit measurementUnit)
		throws Exception {

		return _postMeasurementUnit(randomMeasurementUnit());
	}

	@Override
	protected MeasurementUnit
			testGraphQLDeleteMeasurementUnit_addMeasurementUnit()
		throws Exception {

		return _postMeasurementUnit(randomMeasurementUnit());
	}

	@Override
	protected MeasurementUnit testGraphQLGetMeasurementUnit_addMeasurementUnit()
		throws Exception {

		return _postMeasurementUnit(randomMeasurementUnit());
	}

	@Override
	protected MeasurementUnit
			testGraphQLGetMeasurementUnitByExternalReferenceCode_addMeasurementUnit()
		throws Exception {

		return _postMeasurementUnit(randomMeasurementUnit());
	}

	@Override
	protected MeasurementUnit
			testGraphQLGetMeasurementUnitByKey_addMeasurementUnit()
		throws Exception {

		return _postMeasurementUnit(randomMeasurementUnit());
	}

	@Override
	protected MeasurementUnit testGraphQLMeasurementUnit_addMeasurementUnit()
		throws Exception {

		return _postMeasurementUnit(randomMeasurementUnit());
	}

	@Override
	protected MeasurementUnit testPostMeasurementUnit_addMeasurementUnit(
			MeasurementUnit measurementUnit)
		throws Exception {

		return _postMeasurementUnit(measurementUnit);
	}

	private MeasurementUnit _postMeasurementUnit(
			MeasurementUnit randomMeasurementUnit)
		throws Exception {

		return measurementUnitResource.postMeasurementUnit(
			randomMeasurementUnit);
	}

	private static final List<String> _typesList = Collections.unmodifiableList(
		ListUtil.fromArray("0", "1", "2", "Dimensions", "Unit", "Weight"));

}
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

package com.liferay.headless.commerce.admin.catalog.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Pin;
import com.liferay.headless.commerce.admin.catalog.client.pagination.Page;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.odata.entity.EntityField;

import java.math.BigDecimal;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zoltán Takács
 */
@RunWith(Arquillian.class)
public class PinResourceTest extends BasePinResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_cpInstance = CPTestUtil.addCPInstanceWithRandomSku(
			testGroup.getGroupId(), BigDecimal.TEN);

		_cpDefinition = _cpInstance.getCPDefinition();

		_cProduct = _cpDefinition.getCProduct();
	}

	@Override
	@Test
	public void testPatchPin() throws Exception {
		Pin postPin = pinResource.postProductIdPin(
			_cProduct.getCProductId(), randomPin());

		Pin randomPatchPin = randomPatchPin();

		pinResource.patchPin(postPin.getId(), randomPatchPin);

		Pin expectedPin = postPin.clone();

		BeanTestUtil.copyProperties(randomPatchPin, expectedPin);

		Page<Pin> pinPage = pinResource.getProductIdPinsPage(
			_cProduct.getCProductId(), null, null, null);

		Pin getPin = pinPage.fetchFirstItem();

		assertEquals(expectedPin, getPin);
		assertValid(getPin);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"positionX", "positionY", "sequence"};
	}

	@Override
	protected Collection<EntityField> getEntityFields() throws Exception {
		try {
			return super.getEntityFields();
		}
		catch (NullPointerException nullPointerException) {
			Map<String, EntityField> entityFieldsMap = new HashMap<>();

			return entityFieldsMap.values();
		}
	}

	@Override
	protected Pin testDeletePin_addPin() throws Exception {
		return pinResource.postProductIdPin(
			_cProduct.getCProductId(), randomPin());
	}

	@Override
	protected Pin testGetProductByExternalReferenceCodePinsPage_addPin(
			String externalReferenceCode, Pin pin)
		throws Exception {

		return pinResource.postProductByExternalReferenceCodePin(
			externalReferenceCode, pin);
	}

	@Override
	protected String
			testGetProductByExternalReferenceCodePinsPage_getExternalReferenceCode()
		throws Exception {

		return _cProduct.getExternalReferenceCode();
	}

	@Override
	protected Pin testGetProductIdPinsPage_addPin(Long productId, Pin pin)
		throws Exception {

		return pinResource.postProductIdPin(productId, pin);
	}

	@Override
	protected Long testGetProductIdPinsPage_getProductId() throws Exception {
		return _cProduct.getCProductId();
	}

	@Override
	protected Pin testGraphQLPin_addPin() throws Exception {
		return pinResource.postProductIdPin(
			_cProduct.getCProductId(), randomPin());
	}

	@Override
	protected Pin testPostProductByExternalReferenceCodePin_addPin(Pin pin)
		throws Exception {

		return pinResource.postProductByExternalReferenceCodePin(
			_cProduct.getExternalReferenceCode(), pin);
	}

	@Override
	protected Pin testPostProductIdPin_addPin(Pin pin) throws Exception {
		return pinResource.postProductIdPin(_cProduct.getCProductId(), pin);
	}

	@DeleteAfterTestRun
	private CPDefinition _cpDefinition;

	@DeleteAfterTestRun
	private CPInstance _cpInstance;

	@DeleteAfterTestRun
	private CProduct _cProduct;

}
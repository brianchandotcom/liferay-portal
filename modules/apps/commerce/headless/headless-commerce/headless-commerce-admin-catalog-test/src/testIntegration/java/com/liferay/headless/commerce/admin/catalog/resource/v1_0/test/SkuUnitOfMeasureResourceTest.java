/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.SkuUnitOfMeasure;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlags;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@FeatureFlags("COMMERCE-11287")
@RunWith(Arquillian.class)
public class SkuUnitOfMeasureResourceTest
	extends BaseSkuUnitOfMeasureResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_cpInstance = CPTestUtil.addCPInstance(testGroup.getGroupId());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"active", "incrementalOrderQuantity", "name", "precision",
			"priority", "rate"
		};
	}

	@Override
	protected SkuUnitOfMeasure randomSkuUnitOfMeasure() throws Exception {
		return new SkuUnitOfMeasure() {
			{
				active = RandomTestUtil.randomBoolean();
				basePrice = BigDecimal.valueOf(RandomTestUtil.nextDouble());
				incrementalOrderQuantity = BigDecimal.valueOf(
					RandomTestUtil.randomInt(1, 10));
				key = StringUtil.toLowerCase(RandomTestUtil.randomString());
				name = LanguageUtils.getLanguageIdMap(
					RandomTestUtil.randomLocaleStringMap());
				precision = 0;
				primary = RandomTestUtil.randomBoolean();
				priority = RandomTestUtil.randomDouble();
				promoPrice = BigDecimal.valueOf(RandomTestUtil.nextDouble());
				rate = BigDecimal.valueOf(RandomTestUtil.randomInt(1, 10));
			}
		};
	}

	@Override
	protected SkuUnitOfMeasure testDeleteSkuUnitOfMeasure_addSkuUnitOfMeasure()
		throws Exception {

		return skuUnitOfMeasureResource.postSkuIdSkuUnitOfMeasure(
			_cpInstance.getCPInstanceId(), randomSkuUnitOfMeasure());
	}

	@Override
	protected SkuUnitOfMeasure
			testGetSkuByExternalReferenceCodeSkuUnitOfMeasuresPage_addSkuUnitOfMeasure(
				String externalReferenceCode, SkuUnitOfMeasure skuUnitOfMeasure)
		throws Exception {

		return skuUnitOfMeasureResource.
			postSkuByExternalReferenceCodeSkuUnitOfMeasure(
				externalReferenceCode, skuUnitOfMeasure);
	}

	@Override
	protected String
			testGetSkuByExternalReferenceCodeSkuUnitOfMeasuresPage_getExternalReferenceCode()
		throws Exception {

		return _cpInstance.getExternalReferenceCode();
	}

	@Override
	protected SkuUnitOfMeasure
			testGetSkuIdSkuUnitOfMeasuresPage_addSkuUnitOfMeasure(
				Long id, SkuUnitOfMeasure skuUnitOfMeasure)
		throws Exception {

		return skuUnitOfMeasureResource.postSkuIdSkuUnitOfMeasure(
			id, skuUnitOfMeasure);
	}

	@Override
	protected Long testGetSkuIdSkuUnitOfMeasuresPage_getId() throws Exception {
		return _cpInstance.getCPInstanceId();
	}

	@Override
	protected SkuUnitOfMeasure testGetSkuUnitOfMeasure_addSkuUnitOfMeasure()
		throws Exception {

		return skuUnitOfMeasureResource.postSkuIdSkuUnitOfMeasure(
			_cpInstance.getCPInstanceId(), randomSkuUnitOfMeasure());
	}

	@Override
	protected SkuUnitOfMeasure
			testGraphQLDeleteSkuUnitOfMeasure_addSkuUnitOfMeasure()
		throws Exception {

		return skuUnitOfMeasureResource.postSkuIdSkuUnitOfMeasure(
			_cpInstance.getCPInstanceId(), randomSkuUnitOfMeasure());
	}

	@Override
	protected SkuUnitOfMeasure
			testGraphQLGetSkuUnitOfMeasure_addSkuUnitOfMeasure()
		throws Exception {

		return skuUnitOfMeasureResource.postSkuIdSkuUnitOfMeasure(
			_cpInstance.getCPInstanceId(), randomSkuUnitOfMeasure());
	}

	@Override
	protected SkuUnitOfMeasure testGraphQLSkuUnitOfMeasure_addSkuUnitOfMeasure()
		throws Exception {

		return skuUnitOfMeasureResource.postSkuIdSkuUnitOfMeasure(
			_cpInstance.getCPInstanceId(), randomSkuUnitOfMeasure());
	}

	@Override
	protected SkuUnitOfMeasure testPatchSkuUnitOfMeasure_addSkuUnitOfMeasure()
		throws Exception {

		return skuUnitOfMeasureResource.postSkuIdSkuUnitOfMeasure(
			_cpInstance.getCPInstanceId(), randomSkuUnitOfMeasure());
	}

	@Override
	protected SkuUnitOfMeasure
			testPostSkuByExternalReferenceCodeSkuUnitOfMeasure_addSkuUnitOfMeasure(
				SkuUnitOfMeasure skuUnitOfMeasure)
		throws Exception {

		return skuUnitOfMeasureResource.
			postSkuByExternalReferenceCodeSkuUnitOfMeasure(
				_cpInstance.getExternalReferenceCode(), skuUnitOfMeasure);
	}

	@Override
	protected SkuUnitOfMeasure
			testPostSkuIdSkuUnitOfMeasure_addSkuUnitOfMeasure(
				SkuUnitOfMeasure skuUnitOfMeasure)
		throws Exception {

		return skuUnitOfMeasureResource.postSkuIdSkuUnitOfMeasure(
			_cpInstance.getCPInstanceId(), skuUnitOfMeasure);
	}

	@DeleteAfterTestRun
	private CPInstance _cpInstance;

}
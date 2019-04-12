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

package com.liferay.headless.admin.taxonomy.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.taxonomy.dto.v1_0.AssetType;
import com.liferay.headless.admin.taxonomy.dto.v1_0.TaxonomyVocabulary;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Objects;

import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class TaxonomyVocabularyResourceTest
	extends BaseTaxonomyVocabularyResourceTestCase {

	@Override
	protected boolean equals(
		TaxonomyVocabulary taxonomyVocabulary1,
		TaxonomyVocabulary taxonomyVocabulary2) {

		if (_equals(
				taxonomyVocabulary1.getAssetTypes(),
				taxonomyVocabulary2.getAssetTypes())) {

			return super.equals(taxonomyVocabulary1, taxonomyVocabulary2);
		}

		return false;
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"description", "name"};
	}

	@Override
	protected TaxonomyVocabulary randomTaxonomyVocabulary() {
		return new TaxonomyVocabulary() {
			{
				assetTypes = new AssetType[] {
					new AssetType() {
						{
							required = RandomTestUtil.randomBoolean();
							subtype = "AllAssetSubtypes";
							type = "AllAssetTypes";
						}
					}
				};
				description = RandomTestUtil.randomString();
				name = RandomTestUtil.randomString();
				siteId = testGroup.getGroupId();
			}
		};
	}

	private boolean _equals(AssetType[] assetTypes1, AssetType[] assetTypes2) {
		if (assetTypes1 == assetTypes2) {
			return true;
		}

		if ((assetTypes1 == null) || (assetTypes2 == null)) {
			return false;
		}

		if (assetTypes1.length != assetTypes2.length) {
			return false;
		}

		for (int i = 0; i < assetTypes1.length; i++) {
			AssetType assetType1 = assetTypes1[i];
			AssetType assetType2 = assetTypes2[i];

			if (assetType1 == assetType2) {
				continue;
			}

			if ((assetType1 == null) || (assetType2 == null)) {
				return false;
			}

			if (Objects.equals(
					assetType1.getRequired(), assetType2.getRequired()) &&
				Objects.equals(assetType1.getType(), assetType2.getType()) &&
				Objects.equals(
					assetType1.getSubtype(), assetType2.getSubtype())) {

				continue;
			}

			return false;
		}

		return true;
	}

}
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.frontend.data.set.filter;

import com.liferay.frontend.data.set.constants.FDSEntityFieldTypes;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Fábio Alves
 */
public class PersonaCategorySelectionFDSFilterTest
	extends BaseCategorySelectionFDSFilterTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetProperties() {
		Assert.assertEquals(
			FDSEntityFieldTypes.INTEGER,
			_personaCategorySelectionFDSFilter.getEntityFieldType());
		Assert.assertEquals(
			"cmpPersonaCategoryIds",
			_personaCategorySelectionFDSFilter.getId());
		Assert.assertEquals(
			"persona", _personaCategorySelectionFDSFilter.getLabel());
	}

	@Override
	protected String getAssetVocabularyExternalReferenceCode() {
		return "L_CMP_PERSONAS";
	}

	@Override
	protected BaseCategorySelectionFDSFilter getCategorySelectionFDSFilter() {
		return _personaCategorySelectionFDSFilter;
	}

	private final PersonaCategorySelectionFDSFilter
		_personaCategorySelectionFDSFilter =
			new PersonaCategorySelectionFDSFilter();

}
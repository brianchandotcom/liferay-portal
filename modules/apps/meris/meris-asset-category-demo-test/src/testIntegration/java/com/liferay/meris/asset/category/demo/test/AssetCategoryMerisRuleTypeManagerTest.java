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

package com.liferay.meris.asset.category.demo.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.meris.MerisRuleType;
import com.liferay.meris.MerisRuleTypeManager;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo Garcia
 */
@RunWith(Arquillian.class)
public class AssetCategoryMerisRuleTypeManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetMerisRuleTypes() {
		List<MerisRuleType> merisRuleTypes =
			_merisRuleTypeManager.getMerisRuleTypes(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertFalse(
			"No meris rule types were found", merisRuleTypes.isEmpty());
	}

	@Inject
	private static MerisRuleTypeManager _merisRuleTypeManager;

}
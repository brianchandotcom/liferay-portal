/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.feature.flag.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.feature.flag.FeatureFlag;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManager;
import com.liferay.portal.kernel.feature.flag.FeatureFlagType;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Thiago Buarque
 */
@RunWith(Arquillian.class)
public class CompanyModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testOnAfterCreate() throws Exception {
		String originalName = PrincipalThreadLocal.getName();

		try {
			Company company = CompanyTestUtil.addCompany();

			User user = UserTestUtil.addUser(
				company.getCompanyId(), TestPropsValues.getUserId(),
				RandomTestUtil.randomString(), LocaleUtil.getDefault(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				new long[0], ServiceContextTestUtil.getServiceContext());

			PrincipalThreadLocal.setName(user.getUserId());

			List<FeatureFlag> deprecationFeatureFlags =
				_featureFlagManager.getFeatureFlags(
					company.getCompanyId(),
					FeatureFlagType.DEPRECATION.getPredicate());

			for (FeatureFlag deprecationFeatureFlag : deprecationFeatureFlags) {
				Assert.assertFalse(deprecationFeatureFlag.isEnabled());
			}
		}
		finally {
			PrincipalThreadLocal.setName(originalName);
		}
	}

	@Inject
	private FeatureFlagManager _featureFlagManager;

}
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.fragment.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.fragment.client.dto.v1_0.FragmentSet;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class FragmentSetResourceTest extends BaseFragmentSetResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"description", "externalReferenceCode", "key", "marketplace", "name"
		};
	}

	@Override
	protected FragmentSet randomFragmentSet() throws Exception {
		FragmentSet fragmentSet = super.randomFragmentSet();

		fragmentSet.setDateCreated(new Date(System.currentTimeMillis()));
		fragmentSet.setDateModified(new Date(System.currentTimeMillis()));

		return fragmentSet;
	}

	@Override
	protected FragmentSet testGetSiteFragmentSetsPage_addFragmentSet(
			String siteExternalReferenceCode, FragmentSet fragmentSet)
		throws Exception {

		return fragmentSetResource.postSiteFragmentSet(
			siteExternalReferenceCode, fragmentSet);
	}

	@Override
	protected Map<String, Map<String, String>>
			testGetSiteFragmentSetsPage_getExpectedActions(
				String siteExternalReferenceCode)
		throws Exception {

		return new HashMap<>();
	}

	@Override
	protected FragmentSet testPostSiteFragmentSet_addFragmentSet(
			FragmentSet fragmentSet)
		throws Exception {

		return fragmentSetResource.postSiteFragmentSet(
			testGroup.getExternalReferenceCode(), fragmentSet);
	}

}
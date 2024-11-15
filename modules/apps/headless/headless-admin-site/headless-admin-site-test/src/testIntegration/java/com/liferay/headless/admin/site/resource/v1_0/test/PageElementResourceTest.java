/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.test.rule.FeatureFlags;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@FeatureFlags("LPD-35443")
@RunWith(Arquillian.class)
public class PageElementResourceTest extends BasePageElementResourceTestCase {

	@Ignore
	@Override
	@Test
	public void testDeleteSiteSiteByExternalReferenceCodePageElement()
		throws Exception {

		super.testDeleteSiteSiteByExternalReferenceCodePageElement();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodePageElement()
		throws Exception {

		super.testGetSiteSiteByExternalReferenceCodePageElement();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodePageElementPageElementsPage()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodePageElementPageElementsPage();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodePageExperiencePageElementsPage()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodePageExperiencePageElementsPage();
	}

	@Ignore
	@Override
	@Test
	public void testPatchSiteSiteByExternalReferenceCodePageElement()
		throws Exception {

		super.testPatchSiteSiteByExternalReferenceCodePageElement();
	}

	@Ignore
	@Override
	@Test
	public void testPostSiteSiteByExternalReferenceCodePageElementFragmentComposition()
		throws Exception {

		super.
			testPostSiteSiteByExternalReferenceCodePageElementFragmentComposition();
	}

	@Ignore
	@Override
	@Test
	public void testPostSiteSiteByExternalReferenceCodePageExperiencePageElement()
		throws Exception {

		super.
			testPostSiteSiteByExternalReferenceCodePageExperiencePageElement();
	}

	@Ignore
	@Override
	@Test
	public void testPutSiteSiteByExternalReferenceCodePageElement()
		throws Exception {

		super.testPutSiteSiteByExternalReferenceCodePageElement();
	}

}
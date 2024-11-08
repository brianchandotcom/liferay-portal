/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.site.client.dto.v1_0.MasterPage;
import com.liferay.portal.test.rule.FeatureFlags;

import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@FeatureFlags("LPD-35443")
@RunWith(Arquillian.class)
public class MasterPageResourceTest extends BaseMasterPageResourceTestCase {

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"externalReferenceCode", "name"};
	}

	@Override
	protected MasterPage
			testGetSiteSiteByExternalReferenceCodeMasterPagesPage_addMasterPage(
				String siteExternalReferenceCode, MasterPage masterPage)
		throws Exception {

		return masterPageResource.postSiteSiteByExternalReferenceCodeMasterPage(
			siteExternalReferenceCode, masterPage);
	}

	@Override
	protected String
			testGetSiteSiteByExternalReferenceCodeMasterPagesPage_getIrrelevantSiteExternalReferenceCode()
		throws Exception {

		return irrelevantGroup.getExternalReferenceCode();
	}

	@Override
	protected String
			testGetSiteSiteByExternalReferenceCodeMasterPagesPage_getSiteExternalReferenceCode()
		throws Exception {

		return testGroup.getExternalReferenceCode();
	}

	@Override
	protected MasterPage
			testPostSiteSiteByExternalReferenceCodeMasterPage_addMasterPage(
				MasterPage masterPage)
		throws Exception {

		return testGetSiteSiteByExternalReferenceCodeMasterPagesPage_addMasterPage(
			testGroup.getExternalReferenceCode(), masterPage);
	}

}
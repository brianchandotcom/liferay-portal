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

package com.liferay.lcs.portal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Igor Beslic
 */
@RunWith(PowerMockRunner.class)
public class SiteTypeTest extends PowerMockito {

	@Test
	public void testIsSpecificSiteType() {
		Assert.assertTrue(
			SiteType.isOrganization(SiteType.ORGANIZATION.getType()));
		Assert.assertFalse(SiteType.isOrganization(SiteType.SITE.getType()));
		Assert.assertTrue(SiteType.isSite(SiteType.SITE.getType()));
		Assert.assertFalse(SiteType.isSite(SiteType.ORGANIZATION.getType()));
	}

	@Test
	public void testToSiteType() {
		for (SiteType siteType : SiteType.values()) {
			Assert.assertEquals(
				siteType, SiteType.toSiteType(siteType.getType()));
		}

		Assert.assertEquals(SiteType.ORGANIZATION, SiteType.toSiteType(-1));
	}

}
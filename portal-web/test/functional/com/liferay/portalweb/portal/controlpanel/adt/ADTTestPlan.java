/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.controlpanel.adt;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.adt.assetpublisher.AssetPublisherTestPlan;
import com.liferay.portalweb.portal.controlpanel.adt.blogs.BlogsTestPlan;
import com.liferay.portalweb.portal.controlpanel.adt.categoriesnavigation.CategoriesNavigationTestPlan;
import com.liferay.portalweb.portal.controlpanel.adt.mediagallery.MediaGalleryTestPlan;
import com.liferay.portalweb.portal.controlpanel.adt.sitemap.SiteMapTestPlan;
import com.liferay.portalweb.portal.controlpanel.adt.tagsnavigation.TagsNavigationTestPlan;
import com.liferay.portalweb.portal.controlpanel.adt.wiki.WikiTestPlan;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ADTTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AssetPublisherTestPlan.suite());
		testSuite.addTest(BlogsTestPlan.suite());
		testSuite.addTest(CategoriesNavigationTestPlan.suite());
		testSuite.addTest(MediaGalleryTestPlan.suite());
		testSuite.addTest(SiteMapTestPlan.suite());
		testSuite.addTest(TagsNavigationTestPlan.suite());
		testSuite.addTest(WikiTestPlan.suite());

		return testSuite;
	}

}
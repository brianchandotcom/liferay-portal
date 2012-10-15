/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.shopping.coupon;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portlet.shopping.coupon.addcoupon.AddCouponTests;
import com.liferay.portalweb.portlet.shopping.coupon.addcouponcodenumber.AddCouponCodeNumberTests;
import com.liferay.portalweb.portlet.shopping.coupon.addcouponnamenull.AddCouponNameNullTests;
import com.liferay.portalweb.portlet.shopping.coupon.addcouponnamespace.AddCouponNameSpaceTests;
import com.liferay.portalweb.portlet.shopping.coupon.addcoupons.AddCouponsTests;
import com.liferay.portalweb.portlet.shopping.coupon.deletecoupon.DeleteCouponTests;
import com.liferay.portalweb.portlet.shopping.coupon.editcoupon.EditCouponTests;
import com.liferay.portalweb.portlet.shopping.coupon.searchcouponcode.SearchCouponCodeTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class CouponTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddCouponTests.suite());
		testSuite.addTest(AddCouponCodeNumberTests.suite());
		testSuite.addTest(AddCouponNameNullTests.suite());
		testSuite.addTest(AddCouponNameSpaceTests.suite());
		testSuite.addTest(AddCouponsTests.suite());
		testSuite.addTest(DeleteCouponTests.suite());
		testSuite.addTest(EditCouponTests.suite());
		testSuite.addTest(SearchCouponCodeTests.suite());

		return testSuite;
	}

}
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

package com.liferay.portal.test.rule;

import com.liferay.portal.kernel.test.rule.BaseTestRule;
import com.liferay.portal.test.rule.callback.SyntheticBundleTestCallback;

/**
 * Creates and installs a bundle from the named sub-package of a test class.
 *
 * <p>
 * For example, if the test class is <code>bar.FooTest</code>, invoking
 * <code>new SyntheticBundleRule("fee")</code> on <code>FooTest</code> creates a
 * bundle of the contents of the <code>bar.fee</code> package. This sub-package
 * should also contain a <code>bnd.bnd</code> file that describes the bundle.
 * When writing the bnd file, you can use the <code>${bundle.package}</code>
 * property to simplify the contents of the file.
 * </p>
 *
 * @author Raymond Augé
 */
public class SyntheticBundleRule extends BaseTestRule<Long, Long> {

	public SyntheticBundleRule(String bundlePackage) {
		super(new SyntheticBundleTestCallback(bundlePackage));
	}

}
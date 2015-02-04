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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;

import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Eduardo Garcia
 */
public class LayoutPrototypePropagationTest
	extends BasePrototypePropagationTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Override
	protected void doSetUp() throws Exception {
		prototypeLayout = layoutPrototypeLayout;

		journalArticle = globalJournalArticle;

		portletId = addPortletToLayout(
			TestPropsValues.getUserId(), layoutPrototypeLayout, journalArticle,
			"column-1");

		layout = LayoutTestUtil.addLayout(group, true, layoutPrototype, true);

		layout = propagateChanges(layout);
	}

	@Override
	protected void setLinkEnabled(boolean linkEnabled) throws Exception {
		layout.setLayoutPrototypeLinkEnabled(linkEnabled);

		layout = LayoutLocalServiceUtil.updateLayout(layout);
	}

}
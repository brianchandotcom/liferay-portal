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

package com.liferay.exportimport.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.kernel.service.LayoutFriendlyURLLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Locale;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo García
 */
@RunWith(Arquillian.class)
public class LayoutPrototypePropagationTest
	extends BasePrototypePropagationTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddLayoutFromLayoutPrototypeWithLinkDisabled()
		throws Exception {

		layout = LayoutTestUtil.addLayout(group, false, layoutPrototype, false);

		Locale locale = LocaleUtil.getDefault();

		LayoutFriendlyURL layoutFriendlyURL =
			LayoutFriendlyURLLocalServiceUtil.getLayoutFriendlyURL(
				layout.getPlid(), LanguageUtil.getLanguageId(locale));

		Assert.assertEquals(
			layoutFriendlyURL.getFriendlyURL(), layout.getFriendlyURL());
	}

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
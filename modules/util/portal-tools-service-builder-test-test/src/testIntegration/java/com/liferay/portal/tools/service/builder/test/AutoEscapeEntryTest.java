/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.bean.BeanProperties;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.tools.service.builder.test.model.AutoEscapeEntry;
import com.liferay.portal.tools.service.builder.test.service.persistence.AutoEscapeEntryPersistence;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Tina Tian
 */
@RunWith(Arquillian.class)
public class AutoEscapeEntryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAutoEscapeEntry() {
		AutoEscapeEntry autoEscapeEntry = _autoEscapeEntryPersistence.create(
			RandomTestUtil.nextLong());

		autoEscapeEntry.setAutoEscapeDisabledColumn("&");
		autoEscapeEntry.setAutoEscapeEnabledColumn("&");

		autoEscapeEntry = autoEscapeEntry.toEscapedModel();

		Assert.assertEquals(
			"&",
			_beanProperties.getObject(
				autoEscapeEntry, "autoEscapeDisabledColumn"));
		Assert.assertEquals(
			"&amp;",
			_beanProperties.getObject(
				autoEscapeEntry, "autoEscapeEnabledColumn"));
	}

	@Inject
	private AutoEscapeEntryPersistence _autoEscapeEntryPersistence;

	@Inject
	private BeanProperties _beanProperties;

}
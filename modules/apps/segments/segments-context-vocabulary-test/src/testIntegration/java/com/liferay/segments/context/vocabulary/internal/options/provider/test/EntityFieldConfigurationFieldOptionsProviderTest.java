/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.context.vocabulary.internal.options.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.configuration.admin.definition.ConfigurationFieldOptionsProvider;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.segments.context.Context;

import java.util.Objects;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class EntityFieldConfigurationFieldOptionsProviderTest {

	@ClassRule
	@Rule
	public static final TestRule testRule = new LiferayIntegrationTestRule();

	@Test
	public void testGetOptionsWithDoubleEntityField() {
		Assert.assertFalse(
			ListUtil.exists(
				_configurationFieldOptionsProvider.getOptions(),
				option -> Objects.equals(
					Context.DEVICE_SCREEN_RESOLUTION_HEIGHT,
					option.getValue())));
	}

	@Test
	public void testGetOptionsWithStringEntityField() {
		Assert.assertTrue(
			ListUtil.exists(
				_configurationFieldOptionsProvider.getOptions(),
				option -> Objects.equals(Context.BROWSER, option.getValue())));
	}

	@Inject(
		filter = "(&(configuration.pid=com.liferay.segments.context.vocabulary.internal.configuration.SegmentsContextVocabularyConfiguration)(configuration.field.name=entityFieldName))"
	)
	private ConfigurationFieldOptionsProvider
		_configurationFieldOptionsProvider;

}
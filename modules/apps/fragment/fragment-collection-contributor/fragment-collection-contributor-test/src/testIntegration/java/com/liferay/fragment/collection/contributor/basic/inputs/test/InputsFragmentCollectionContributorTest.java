/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.collection.contributor.basic.inputs.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.contributor.FragmentCollectionContributorRegistry;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

/**
 * @author Víctor Galán
 */
@RunWith(Arquillian.class)
public class InputsFragmentCollectionContributorTest {

	@ClassRule
	@Rule
	public static final TestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testInputFragmentsAvailable() {
		FragmentEntry fragmentEntry =
			_fragmentCollectionContributorRegistry.getFragmentEntry(
				"INPUTS-captcha");

		Assert.assertNotNull(fragmentEntry);

		fragmentEntry = _fragmentCollectionContributorRegistry.getFragmentEntry(
			"INPUTS-checkbox");

		Assert.assertNotNull(fragmentEntry);

		fragmentEntry = _fragmentCollectionContributorRegistry.getFragmentEntry(
			"INPUTS-date-input");

		Assert.assertNotNull(fragmentEntry);

		fragmentEntry = _fragmentCollectionContributorRegistry.getFragmentEntry(
			"INPUTS-date-time-input");

		Assert.assertNotNull(fragmentEntry);

		fragmentEntry = _fragmentCollectionContributorRegistry.getFragmentEntry(
			"INPUTS-file-upload");

		Assert.assertNotNull(fragmentEntry);

		fragmentEntry = _fragmentCollectionContributorRegistry.getFragmentEntry(
			"INPUTS-multiselect-list");

		Assert.assertNotNull(fragmentEntry);

		fragmentEntry = _fragmentCollectionContributorRegistry.getFragmentEntry(
			"INPUTS-numeric-input");

		Assert.assertNotNull(fragmentEntry);

		fragmentEntry = _fragmentCollectionContributorRegistry.getFragmentEntry(
			"INPUTS-rich-text-input");

		Assert.assertNotNull(fragmentEntry);

		fragmentEntry = _fragmentCollectionContributorRegistry.getFragmentEntry(
			"INPUTS-select-from-list");

		Assert.assertNotNull(fragmentEntry);

		fragmentEntry = _fragmentCollectionContributorRegistry.getFragmentEntry(
			"INPUTS-stepper");

		Assert.assertNotNull(fragmentEntry);

		fragmentEntry = _fragmentCollectionContributorRegistry.getFragmentEntry(
			"INPUTS-submit-button");

		Assert.assertNotNull(fragmentEntry);

		fragmentEntry = _fragmentCollectionContributorRegistry.getFragmentEntry(
			"INPUTS-text-input");

		Assert.assertNotNull(fragmentEntry);

		fragmentEntry = _fragmentCollectionContributorRegistry.getFragmentEntry(
			"INPUTS-textarea");

		Assert.assertNotNull(fragmentEntry);
	}

	@Inject
	private FragmentCollectionContributorRegistry
		_fragmentCollectionContributorRegistry;

}
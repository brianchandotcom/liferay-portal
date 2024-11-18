/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.editor.configuration.internal;

import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.journal.item.selector.criterion.JournalItemSelectorCriterion;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Sergio González
 */
public class JournalAMEditorConfigContributorTest
	extends BaseAMEditorConfigContributorTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Override
	protected BaseAMEditorConfigContributor getBaseAMEditorConfigContributor() {
		return new JournalAMEditorConfigContributor();
	}

	@Override
	protected ItemSelectorCriterion getItemSelectorCriterion() {
		return new JournalItemSelectorCriterion();
	}

	@Override
	protected String
		getItemSelectorCriterionFileEntryItemSelectorReturnTypeName() {

		return "journalItemSelectorCriterionFileEntryItemSelectorReturnType";
	}

}
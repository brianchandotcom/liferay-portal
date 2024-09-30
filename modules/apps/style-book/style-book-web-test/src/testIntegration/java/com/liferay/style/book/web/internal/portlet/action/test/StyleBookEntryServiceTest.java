/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Bárbara Cabrera
 */
@RunWith(Arquillian.class)
public class StyleBookEntryServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testCopyStyleBookEntry() throws Exception {
		StyleBookEntry sourceStyleBookEntry =
			_styleBookEntryService.addStyleBookEntry(
				null, _group.getGroupId(), RandomTestUtil.randomString(),
				"STYLE_BOOK_ENTRY_KEY", RandomTestUtil.randomString(),
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		StyleBookEntry targetStyleBookEntry =
			_styleBookEntryService.copyStyleBookEntry(
				_group.getGroupId(), sourceStyleBookEntry.getStyleBookEntryId(),
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertEquals(
			sourceStyleBookEntry.getFrontendTokensValues(),
			targetStyleBookEntry.getFrontendTokensValues());
		Assert.assertEquals(
			sourceStyleBookEntry.getGroupId(),
			targetStyleBookEntry.getGroupId());
		Assert.assertEquals(
			targetStyleBookEntry.getName(),
			StringBundler.concat(
				sourceStyleBookEntry.getName(), StringPool.SPACE,
				StringPool.OPEN_PARENTHESIS,
				_language.get(LocaleUtil.getSiteDefault(), "copy"),
				StringPool.CLOSE_PARENTHESIS));
		Assert.assertEquals(
			sourceStyleBookEntry.getPreviewFileEntryId(),
			targetStyleBookEntry.getPreviewFileEntryId());
		Assert.assertEquals(
			sourceStyleBookEntry.getUserId(), targetStyleBookEntry.getUserId());
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private Language _language;

	@Inject
	private StyleBookEntryService _styleBookEntryService;

}
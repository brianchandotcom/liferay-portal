/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.journal.internal.item.type.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.content.dashboard.info.item.ClassNameClassPKInfoItemIdentifier;
import com.liferay.content.dashboard.item.type.ContentDashboardItemSubtype;
import com.liferay.content.dashboard.item.type.ContentDashboardItemSubtypeFactory;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.info.item.InfoItemReference;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class DDMStructureContentDashboardItemSubtypeFactoryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(), 0);
	}

	@Test
	public void testCreate() throws Exception {
		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), 0);

		DDMStructure ddmStructure = journalArticle.getDDMStructure();

		ContentDashboardItemSubtype contentDashboardItemSubtype =
			_contentDashboardItemSubtypeFactory.create(
				ddmStructure.getStructureId());

		InfoItemReference infoItemReference =
			contentDashboardItemSubtype.getInfoItemReference();

		Assert.assertEquals(
			JournalArticle.class.getName(), infoItemReference.getClassName());

		ClassNameClassPKInfoItemIdentifier classNameClassPKInfoItemIdentifier =
			(ClassNameClassPKInfoItemIdentifier)
				infoItemReference.getInfoItemIdentifier();

		Assert.assertEquals(
			DDMStructure.class.getName(),
			classNameClassPKInfoItemIdentifier.getClassName());

		Assert.assertEquals(
			StringBundler.concat(
				ddmStructure.getName(LocaleUtil.US), StringPool.SPACE,
				StringPool.OPEN_PARENTHESIS,
				_group.getDescriptiveName(LocaleUtil.US),
				StringPool.CLOSE_PARENTHESIS),
			contentDashboardItemSubtype.getFullLabel(LocaleUtil.US));
		Assert.assertEquals(
			ddmStructure.getName(LocaleUtil.US),
			contentDashboardItemSubtype.getLabel(LocaleUtil.US));
	}

	@Inject(
		filter = "component.name=com.liferay.content.dashboard.journal.internal.item.type.DDMStructureContentDashboardItemSubtypeFactory"
	)
	private ContentDashboardItemSubtypeFactory
		_contentDashboardItemSubtypeFactory;

	@DeleteAfterTestRun
	private Group _group;

}
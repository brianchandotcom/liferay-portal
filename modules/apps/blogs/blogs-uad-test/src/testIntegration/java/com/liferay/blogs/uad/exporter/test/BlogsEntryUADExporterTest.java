/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.uad.exporter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.blogs.uad.test.BlogsEntryUADTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.exporter.UADExporter;
import com.liferay.user.associated.data.test.util.BaseUADExporterTestCase;
import com.liferay.user.associated.data.test.util.WhenHasStatusByUserIdField;

import java.util.ArrayList;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class BlogsEntryUADExporterTest
	extends BaseUADExporterTestCase<BlogsEntry>
	implements WhenHasStatusByUserIdField<BlogsEntry> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	public BlogsEntry addBaseModelWithStatusByUserId(
			long userId, long statusByUserId)
		throws Exception {

		BlogsEntry blogsEntry =
			BlogsEntryUADTestUtil.addBlogsEntryWithStatusByUserId(
				_blogsEntryLocalService, userId, statusByUserId);

		_blogsEntries.add(blogsEntry);

		return blogsEntry;
	}

	@Override
	protected BlogsEntry addBaseModel(long userId) throws Exception {
		BlogsEntry blogsEntry = BlogsEntryUADTestUtil.addBlogsEntry(
			_blogsEntryLocalService, userId);

		_blogsEntries.add(blogsEntry);

		return blogsEntry;
	}

	@Override
	protected String getPrimaryKeyName() {
		return "entryId";
	}

	@Override
	protected UADExporter<BlogsEntry> getUADExporter() {
		return _uadExporter;
	}

	@DeleteAfterTestRun
	private final List<BlogsEntry> _blogsEntries = new ArrayList<>();

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Inject(
		filter = "component.name=com.liferay.blogs.uad.exporter.BlogsEntryUADExporter"
	)
	private UADExporter<BlogsEntry> _uadExporter;

}
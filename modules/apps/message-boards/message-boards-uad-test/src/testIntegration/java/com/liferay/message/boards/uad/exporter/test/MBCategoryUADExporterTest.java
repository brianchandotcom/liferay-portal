/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.uad.exporter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.message.boards.uad.test.MBCategoryUADTestUtil;
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
public class MBCategoryUADExporterTest
	extends BaseUADExporterTestCase<MBCategory>
	implements WhenHasStatusByUserIdField<MBCategory> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	public MBCategory addBaseModelWithStatusByUserId(
			long userId, long statusByUserId)
		throws Exception {

		MBCategory mbCategory =
			MBCategoryUADTestUtil.addMBCategoryWithStatusByUserId(
				_mbCategoryLocalService, userId, statusByUserId);

		_mbCategories.add(mbCategory);

		return mbCategory;
	}

	@Override
	protected MBCategory addBaseModel(long userId) throws Exception {
		MBCategory mbCategory = MBCategoryUADTestUtil.addMBCategory(
			_mbCategoryLocalService, userId);

		_mbCategories.add(mbCategory);

		return mbCategory;
	}

	@Override
	protected String getPrimaryKeyName() {
		return "categoryId";
	}

	@Override
	protected UADExporter<MBCategory> getUADExporter() {
		return _uadExporter;
	}

	@DeleteAfterTestRun
	private final List<MBCategory> _mbCategories = new ArrayList<>();

	@Inject
	private MBCategoryLocalService _mbCategoryLocalService;

	@Inject(
		filter = "component.name=com.liferay.message.boards.uad.exporter.MBCategoryUADExporter"
	)
	private UADExporter<MBCategory> _uadExporter;

}
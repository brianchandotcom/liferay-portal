/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.uad.exporter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.model.LayoutSetBranchConstants;
import com.liferay.portal.kernel.service.LayoutSetBranchLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.exporter.UADExporter;
import com.liferay.user.associated.data.test.util.BaseUADExporterTestCase;

import java.util.ArrayList;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@Ignore
@RunWith(Arquillian.class)
public class LayoutSetBranchUADExporterTest
	extends BaseUADExporterTestCase<LayoutSetBranch> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected LayoutSetBranch addBaseModel(long userId) throws Exception {
		LayoutSetBranch layoutSetBranch =
			_layoutSetBranchLocalService.addLayoutSetBranch(
				userId, TestPropsValues.getGroupId(), false,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				false, LayoutSetBranchConstants.ALL_BRANCHES,
				ServiceContextTestUtil.getServiceContext());

		_layoutSetBranchs.add(layoutSetBranch);

		return layoutSetBranch;
	}

	@Override
	protected String getPrimaryKeyName() {
		return "layoutSetBranchId";
	}

	@Override
	protected UADExporter<LayoutSetBranch> getUADExporter() {
		return _uadExporter;
	}

	@Inject
	private LayoutSetBranchLocalService _layoutSetBranchLocalService;

	@DeleteAfterTestRun
	private final List<LayoutSetBranch> _layoutSetBranchs = new ArrayList<>();

	@Inject(
		filter = "component.name=com.liferay.layout.uad.exporter.LayoutSetBranchUADExporter"
	)
	private UADExporter<LayoutSetBranch> _uadExporter;

}
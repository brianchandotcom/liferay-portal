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

package com.liferay.layout.uad.exporter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.layout.uad.test.LayoutSetBranchUADTestHelper;

import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import com.liferay.user.associated.data.exporter.UADExporter;
import com.liferay.user.associated.data.test.util.BaseUADExporterTestCase;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Rule;

import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@RunWith(Arquillian.class)
public class LayoutSetBranchUADExporterTest extends BaseUADExporterTestCase<LayoutSetBranch> {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		_layoutSetBranchUADTestHelper.cleanUpDependencies(_layoutSetBranchs);
	}

	@Override
	protected LayoutSetBranch addBaseModel(long userId)
		throws Exception {
		LayoutSetBranch layoutSetBranch = _layoutSetBranchUADTestHelper.addLayoutSetBranch(userId);

		_layoutSetBranchs.add(layoutSetBranch);

		return layoutSetBranch;
	}

	@Override
	protected String getPrimaryKeyName() {
		return "layoutSetBranchId";
	}

	@Override
	protected UADExporter getUADExporter() {
		return _uadExporter;
	}

	@DeleteAfterTestRun
	private final List<LayoutSetBranch> _layoutSetBranchs = new ArrayList<LayoutSetBranch>();
	@Inject
	private LayoutSetBranchUADTestHelper _layoutSetBranchUADTestHelper;
	@Inject(filter = "component.name=*.LayoutSetBranchUADExporter")
	private UADExporter _uadExporter;
}
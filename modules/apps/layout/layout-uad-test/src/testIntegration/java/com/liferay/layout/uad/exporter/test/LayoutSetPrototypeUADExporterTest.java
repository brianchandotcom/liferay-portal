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
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.user.associated.data.exporter.UADExporter;
import com.liferay.user.associated.data.test.util.BaseUADExporterTestCase;

import java.util.ArrayList;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class LayoutSetPrototypeUADExporterTest
	extends BaseUADExporterTestCase<LayoutSetPrototype> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Override
	protected LayoutSetPrototype addBaseModel(long userId) throws Exception {
		LayoutSetPrototype layoutSetPrototype =
			_layoutSetPrototypeLocalService.addLayoutSetPrototype(
				userId, TestPropsValues.getCompanyId(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(), true, true,
				ServiceContextTestUtil.getServiceContext());

		_layoutSetPrototypes.add(layoutSetPrototype);

		return layoutSetPrototype;
	}

	@Override
	protected String getPrimaryKeyName() {
		return "layoutSetPrototypeId";
	}

	@Override
	protected UADExporter<LayoutSetPrototype> getUADExporter() {
		return _uadExporter;
	}

	@Inject
	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;

	@DeleteAfterTestRun
	private final List<LayoutSetPrototype> _layoutSetPrototypes =
		new ArrayList<>();

	@Inject(filter = "component.name=*.LayoutSetPrototypeUADExporter")
	private UADExporter<LayoutSetPrototype> _uadExporter;

}
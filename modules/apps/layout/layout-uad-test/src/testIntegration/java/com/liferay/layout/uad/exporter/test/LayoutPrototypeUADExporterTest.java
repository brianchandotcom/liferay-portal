/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.uad.exporter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalService;
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
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class LayoutPrototypeUADExporterTest
	extends BaseUADExporterTestCase<LayoutPrototype> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected LayoutPrototype addBaseModel(long userId) throws Exception {
		LayoutPrototype layoutPrototype =
			_layoutPrototypeLocalService.addLayoutPrototype(
				userId, TestPropsValues.getCompanyId(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(), true,
				ServiceContextTestUtil.getServiceContext());

		_layoutPrototypes.add(layoutPrototype);

		return layoutPrototype;
	}

	@Override
	protected String getPrimaryKeyName() {
		return "layoutPrototypeId";
	}

	@Override
	protected UADExporter<LayoutPrototype> getUADExporter() {
		return _uadExporter;
	}

	@Inject
	private LayoutPrototypeLocalService _layoutPrototypeLocalService;

	@DeleteAfterTestRun
	private final List<LayoutPrototype> _layoutPrototypes = new ArrayList<>();

	@Inject(
		filter = "component.name=com.liferay.layout.uad.exporter.LayoutPrototypeUADExporter"
	)
	private UADExporter<LayoutPrototype> _uadExporter;

}
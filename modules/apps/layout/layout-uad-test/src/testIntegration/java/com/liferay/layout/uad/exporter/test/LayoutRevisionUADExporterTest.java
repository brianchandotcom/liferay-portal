/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.uad.exporter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.uad.test.LayoutRevisionUADTestUtil;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.service.LayoutRevisionLocalService;
import com.liferay.portal.kernel.service.LayoutSetBranchLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.exporter.UADExporter;
import com.liferay.user.associated.data.test.util.BaseUADExporterTestCase;
import com.liferay.user.associated.data.test.util.WhenHasStatusByUserIdField;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@Ignore
@RunWith(Arquillian.class)
public class LayoutRevisionUADExporterTest
	extends BaseUADExporterTestCase<LayoutRevision>
	implements WhenHasStatusByUserIdField<LayoutRevision> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	public LayoutRevision addBaseModelWithStatusByUserId(
			long userId, long statusByUserId)
		throws Exception {

		LayoutRevision layoutRevision =
			LayoutRevisionUADTestUtil.addLayoutRevisionWithStatusByUserId(
				_layoutRevisionLocalService, _layoutSetBranchLocalService,
				_userLocalService, userId, statusByUserId);

		_layoutRevisions.add(layoutRevision);

		return layoutRevision;
	}

	@After
	public void tearDown() throws Exception {
		LayoutRevisionUADTestUtil.cleanUpDependencies(
			_layoutSetBranchLocalService, _layoutRevisions);
	}

	@Override
	protected LayoutRevision addBaseModel(long userId) throws Exception {
		LayoutRevision layoutRevision =
			LayoutRevisionUADTestUtil.addLayoutRevision(
				_layoutRevisionLocalService, _layoutSetBranchLocalService,
				userId);

		_layoutRevisions.add(layoutRevision);

		return layoutRevision;
	}

	@Override
	protected String getPrimaryKeyName() {
		return "layoutRevisionId";
	}

	@Override
	protected UADExporter<LayoutRevision> getUADExporter() {
		return _uadExporter;
	}

	@Inject
	private LayoutRevisionLocalService _layoutRevisionLocalService;

	@DeleteAfterTestRun
	private final List<LayoutRevision> _layoutRevisions = new ArrayList<>();

	@Inject
	private LayoutSetBranchLocalService _layoutSetBranchLocalService;

	@Inject(
		filter = "component.name=com.liferay.layout.uad.exporter.LayoutRevisionUADExporter"
	)
	private UADExporter<LayoutRevision> _uadExporter;

	@Inject
	private UserLocalService _userLocalService;

}
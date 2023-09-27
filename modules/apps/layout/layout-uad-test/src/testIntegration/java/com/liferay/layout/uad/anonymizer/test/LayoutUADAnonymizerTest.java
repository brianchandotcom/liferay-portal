/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.uad.anonymizer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.uad.test.LayoutUADTestUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.test.util.BaseHasAssetEntryUADAnonymizerTestCase;

import java.util.ArrayList;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class LayoutUADAnonymizerTest
	extends BaseHasAssetEntryUADAnonymizerTestCase<Layout> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected Layout addBaseModel(long userId) throws Exception {
		return addBaseModel(userId, true);
	}

	@Override
	protected Layout addBaseModel(long userId, boolean deleteAfterTestRun)
		throws Exception {

		Layout layout = LayoutUADTestUtil.addLayout(
			_layoutLocalService, userId);

		if (deleteAfterTestRun) {
			_layouts.add(layout);
		}

		return layout;
	}

	@Override
	protected UADAnonymizer<Layout> getUADAnonymizer() {
		return _uadAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {

		Layout layout = _layoutLocalService.getLayout(baseModelPK);

		String userName = layout.getUserName();

		if ((layout.getUserId() != user.getUserId()) &&
			!userName.equals(user.getFullName()) &&
			isAssetEntryAutoAnonymized(
				Layout.class.getName(), layout.getPlid(), user)) {

			return true;
		}

		return false;
	}

	@Override
	protected boolean isBaseModelDeleted(long baseModelPK) {
		if (_layoutLocalService.fetchLayout(baseModelPK) == null) {
			return true;
		}

		return false;
	}

	@Inject
	private LayoutLocalService _layoutLocalService;

	@DeleteAfterTestRun
	private final List<Layout> _layouts = new ArrayList<>();

	@Inject(
		filter = "component.name=com.liferay.layout.uad.anonymizer.LayoutUADAnonymizer"
	)
	private UADAnonymizer<Layout> _uadAnonymizer;

}
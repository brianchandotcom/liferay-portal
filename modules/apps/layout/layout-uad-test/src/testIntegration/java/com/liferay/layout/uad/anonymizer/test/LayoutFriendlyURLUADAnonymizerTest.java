/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.uad.anonymizer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.uad.test.LayoutFriendlyURLUADTestUtil;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutFriendlyURLLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.test.util.BaseUADAnonymizerTestCase;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class LayoutFriendlyURLUADAnonymizerTest
	extends BaseUADAnonymizerTestCase<LayoutFriendlyURL> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		LayoutFriendlyURLUADTestUtil.cleanUpDependencies(
			_layoutLocalService, _layoutFriendlyURLs);
	}

	@Override
	protected LayoutFriendlyURL addBaseModel(long userId) throws Exception {
		return addBaseModel(userId, true);
	}

	@Override
	protected LayoutFriendlyURL addBaseModel(
			long userId, boolean deleteAfterTestRun)
		throws Exception {

		LayoutFriendlyURL layoutFriendlyURL =
			LayoutFriendlyURLUADTestUtil.addLayoutFriendlyURL(
				_layoutFriendlyURLLocalService, _layoutLocalService, userId);

		if (deleteAfterTestRun) {
			_layoutFriendlyURLs.add(layoutFriendlyURL);
		}

		return layoutFriendlyURL;
	}

	@Override
	protected void deleteBaseModels(List<LayoutFriendlyURL> baseModels)
		throws Exception {

		LayoutFriendlyURLUADTestUtil.cleanUpDependencies(
			_layoutLocalService, baseModels);
	}

	@Override
	protected UADAnonymizer<LayoutFriendlyURL> getUADAnonymizer() {
		return _uadAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {

		LayoutFriendlyURL layoutFriendlyURL =
			_layoutFriendlyURLLocalService.getLayoutFriendlyURL(baseModelPK);

		String userName = layoutFriendlyURL.getUserName();

		if ((layoutFriendlyURL.getUserId() != user.getUserId()) &&
			!userName.equals(user.getFullName())) {

			return true;
		}

		return false;
	}

	@Override
	protected boolean isBaseModelDeleted(long baseModelPK) {
		LayoutFriendlyURL layoutFriendlyURL =
			_layoutFriendlyURLLocalService.fetchLayoutFriendlyURL(baseModelPK);

		if (layoutFriendlyURL == null) {
			return true;
		}

		return false;
	}

	@Inject
	private LayoutFriendlyURLLocalService _layoutFriendlyURLLocalService;

	@DeleteAfterTestRun
	private final List<LayoutFriendlyURL> _layoutFriendlyURLs =
		new ArrayList<>();

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject(
		filter = "component.name=com.liferay.layout.uad.anonymizer.LayoutFriendlyURLUADAnonymizer"
	)
	private UADAnonymizer<LayoutFriendlyURL> _uadAnonymizer;

}
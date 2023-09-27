/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.uad.anonymizer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.uad.test.LayoutRevisionUADTestUtil;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutRevisionLocalService;
import com.liferay.portal.kernel.service.LayoutSetBranchLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.test.util.BaseUADAnonymizerTestCase;
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
public class LayoutRevisionUADAnonymizerTest
	extends BaseUADAnonymizerTestCase<LayoutRevision>
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
		return addBaseModel(userId, true);
	}

	@Override
	protected LayoutRevision addBaseModel(
			long userId, boolean deleteAfterTestRun)
		throws Exception {

		LayoutRevision layoutRevision =
			LayoutRevisionUADTestUtil.addLayoutRevision(
				_layoutRevisionLocalService, _layoutSetBranchLocalService,
				userId);

		if (deleteAfterTestRun) {
			_layoutRevisions.add(layoutRevision);
		}

		return layoutRevision;
	}

	@Override
	protected void deleteBaseModels(List<LayoutRevision> baseModels)
		throws Exception {

		LayoutRevisionUADTestUtil.cleanUpDependencies(
			_layoutSetBranchLocalService, baseModels);
	}

	@Override
	protected UADAnonymizer<LayoutRevision> getUADAnonymizer() {
		return _uadAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {

		LayoutRevision layoutRevision =
			_layoutRevisionLocalService.getLayoutRevision(baseModelPK);

		String userName = layoutRevision.getUserName();
		String statusByUserName = layoutRevision.getStatusByUserName();

		if ((layoutRevision.getUserId() != user.getUserId()) &&
			!userName.equals(user.getFullName()) &&
			(layoutRevision.getStatusByUserId() != user.getUserId()) &&
			!statusByUserName.equals(user.getFullName())) {

			return true;
		}

		return false;
	}

	@Override
	protected boolean isBaseModelDeleted(long baseModelPK) {
		if (_layoutRevisionLocalService.fetchLayoutRevision(baseModelPK) ==
				null) {

			return true;
		}

		return false;
	}

	@Inject
	private LayoutRevisionLocalService _layoutRevisionLocalService;

	@DeleteAfterTestRun
	private final List<LayoutRevision> _layoutRevisions = new ArrayList<>();

	@Inject
	private LayoutSetBranchLocalService _layoutSetBranchLocalService;

	@Inject(
		filter = "component.name=com.liferay.layout.uad.anonymizer.LayoutRevisionUADAnonymizer"
	)
	private UADAnonymizer<LayoutRevision> _uadAnonymizer;

	@Inject
	private UserLocalService _userLocalService;

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.uad.anonymizer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.test.util.BaseUADAnonymizerTestCase;

import java.util.ArrayList;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class LayoutSetPrototypeUADAnonymizerTest
	extends BaseUADAnonymizerTestCase<LayoutSetPrototype> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Override
	protected LayoutSetPrototype addBaseModel(long userId) throws Exception {
		return addBaseModel(userId, true);
	}

	@Override
	protected LayoutSetPrototype addBaseModel(
			long userId, boolean deleteAfterTestRun)
		throws Exception {

		LayoutSetPrototype layoutSetPrototype =
			_layoutSetPrototypeLocalService.addLayoutSetPrototype(
				userId, TestPropsValues.getCompanyId(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(), true, true,
				ServiceContextTestUtil.getServiceContext());

		if (deleteAfterTestRun) {
			_layoutSetPrototypes.add(layoutSetPrototype);
		}

		return _layoutSetPrototypeLocalService.getLayoutSetPrototype(
			layoutSetPrototype.getLayoutSetPrototypeId());
	}

	@Override
	protected UADAnonymizer<LayoutSetPrototype> getUADAnonymizer() {
		return _uadAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {

		LayoutSetPrototype layoutSetPrototype =
			_layoutSetPrototypeLocalService.getLayoutSetPrototype(baseModelPK);

		String userName = layoutSetPrototype.getUserName();

		if ((layoutSetPrototype.getUserId() != user.getUserId()) &&
			!userName.equals(user.getFullName())) {

			return true;
		}

		return false;
	}

	@Override
	protected boolean isBaseModelDeleted(long baseModelPK) {
		LayoutSetPrototype layoutSetPrototype =
			_layoutSetPrototypeLocalService.fetchLayoutSetPrototype(
				baseModelPK);

		if (layoutSetPrototype == null) {
			return true;
		}

		return false;
	}

	@Inject
	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;

	@DeleteAfterTestRun
	private final List<LayoutSetPrototype> _layoutSetPrototypes =
		new ArrayList<>();

	@Inject(
		filter = "component.name=com.liferay.layout.uad.anonymizer.LayoutSetPrototypeUADAnonymizer"
	)
	private UADAnonymizer<LayoutSetPrototype> _uadAnonymizer;

}
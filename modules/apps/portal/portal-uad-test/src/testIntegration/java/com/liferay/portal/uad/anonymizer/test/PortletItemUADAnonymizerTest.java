/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.uad.anonymizer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.PortletItem;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.PortletItemLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
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
public class PortletItemUADAnonymizerTest
	extends BaseUADAnonymizerTestCase<PortletItem> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected PortletItem addBaseModel(long userId) throws Exception {
		return addBaseModel(userId, true);
	}

	@Override
	protected PortletItem addBaseModel(long userId, boolean deleteAfterTestRun)
		throws Exception {

		PortletItem portletItem = _portletItemLocalService.addPortletItem(
			userId, TestPropsValues.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		if (deleteAfterTestRun) {
			_portletItems.add(portletItem);
		}

		return portletItem;
	}

	@Override
	protected UADAnonymizer<PortletItem> getUADAnonymizer() {
		return _uadAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {

		PortletItem portletItem = _portletItemLocalService.getPortletItem(
			baseModelPK);

		String userName = portletItem.getUserName();

		if ((portletItem.getUserId() != user.getUserId()) &&
			!userName.equals(user.getFullName())) {

			return true;
		}

		return false;
	}

	@Override
	protected boolean isBaseModelDeleted(long baseModelPK) {
		if (_portletItemLocalService.fetchPortletItem(baseModelPK) == null) {
			return true;
		}

		return false;
	}

	@Inject
	private PortletItemLocalService _portletItemLocalService;

	@DeleteAfterTestRun
	private final List<PortletItem> _portletItems = new ArrayList<>();

	@Inject(
		filter = "component.name=com.liferay.portal.uad.anonymizer.PortletItemUADAnonymizer"
	)
	private UADAnonymizer<PortletItem> _uadAnonymizer;

}
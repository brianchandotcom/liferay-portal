/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.admin.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.set.prototype.helper.LayoutSetPrototypeHelper;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalService;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class ResetMergeFailCountMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testResetMergeFailCount() throws Exception {
		_layoutPrototype = LayoutTestUtil.addLayoutPrototype(
			RandomTestUtil.randomString());

		_layoutSetPrototypeHelper.setMergeFailCount(_layoutPrototype, 5);

		_layoutPrototype = _layoutPrototypeLocalService.fetchLayoutPrototype(
			_layoutPrototype.getLayoutPrototypeId());

		Assert.assertEquals(5, _layoutPrototype.getMergeFailCount());

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setParameter(
			"layoutPrototypeId",
			String.valueOf(_layoutPrototype.getLayoutPrototypeId()));

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());

		_layoutPrototype = _layoutPrototypeLocalService.fetchLayoutPrototype(
			_layoutPrototype.getLayoutPrototypeId());

		Assert.assertEquals(0, _layoutPrototype.getMergeFailCount());
	}

	@DeleteAfterTestRun
	private LayoutPrototype _layoutPrototype;

	@Inject
	private LayoutPrototypeLocalService _layoutPrototypeLocalService;

	@Inject
	private LayoutSetPrototypeHelper _layoutSetPrototypeHelper;

	@Inject(
		filter = "mvc.command.name=/layout_page_template_admin/reset_merge_fail_count"
	)
	private MVCActionCommand _mvcActionCommand;

}
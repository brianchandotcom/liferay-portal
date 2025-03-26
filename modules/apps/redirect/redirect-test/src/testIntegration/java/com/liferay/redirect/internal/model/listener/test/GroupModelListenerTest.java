/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.view.count.ViewCountManager;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.redirect.model.RedirectEntry;
import com.liferay.redirect.model.RedirectNotFoundEntry;
import com.liferay.redirect.service.RedirectEntryLocalService;
import com.liferay.redirect.service.RedirectNotFoundEntryLocalService;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jonathan McCann
 */
@RunWith(Arquillian.class)
public class GroupModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testOnAfterRemove() throws Exception {
		Group group = GroupTestUtil.addGroup();

		RedirectEntry redirectEntry =
			_redirectEntryLocalService.addRedirectEntry(
				group.getGroupId(), RandomTestUtil.randomString(), null, false,
				RandomTestUtil.randomString(),
				ServiceContextTestUtil.getServiceContext());

		RedirectNotFoundEntry redirectNotFoundEntry =
			_redirectNotFoundEntryLocalService.addOrUpdateRedirectNotFoundEntry(
				group, RandomTestUtil.randomString());

		Assert.assertNotNull(
			_redirectEntryLocalService.fetchRedirectEntry(
				redirectEntry.getRedirectEntryId()));
		Assert.assertNotNull(
			_redirectNotFoundEntryLocalService.fetchRedirectNotFoundEntry(
				redirectNotFoundEntry.getRedirectNotFoundEntryId()));
		Assert.assertEquals(
			1,
			_viewCountManager.getViewCount(
				group.getCompanyId(),
				_portal.getClassNameId(RedirectNotFoundEntry.class),
				redirectNotFoundEntry.getRedirectNotFoundEntryId()));

		_groupLocalService.deleteGroup(group);

		Assert.assertNull(
			_redirectEntryLocalService.fetchRedirectEntry(
				redirectEntry.getRedirectEntryId()));
		Assert.assertNull(
			_redirectNotFoundEntryLocalService.fetchRedirectNotFoundEntry(
				redirectNotFoundEntry.getRedirectNotFoundEntryId()));
		Assert.assertEquals(
			0,
			_viewCountManager.getViewCount(
				group.getCompanyId(),
				_portal.getClassNameId(RedirectNotFoundEntry.class),
				redirectNotFoundEntry.getRedirectNotFoundEntryId()));
	}

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private RedirectEntryLocalService _redirectEntryLocalService;

	@Inject
	private RedirectNotFoundEntryLocalService
		_redirectNotFoundEntryLocalService;

	@Inject
	private ViewCountManager _viewCountManager;

}
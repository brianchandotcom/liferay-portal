/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.staging.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.constants.BlogsPortletKeys;
import com.liferay.journal.constants.JournalContentPortletKeys;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Tamas Molnar
 */
@RunWith(Arquillian.class)
@Sync(cleanTransaction = true)
public class StagingGetScopeGroupIdTest extends BaseLocalStagingTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		GroupTestUtil.addGroup(TestPropsValues.getUserId(), liveLayout);
		GroupTestUtil.addGroup(TestPropsValues.getUserId(), stagingLayout);

		_liveLayoutScopeGroup = liveLayout.getScopeGroup();
		_stagingLayoutScopeGroup = stagingLayout.getScopeGroup();

		_stagingLayoutScopeGroup.setLiveGroupId(
			_liveLayoutScopeGroup.getGroupId());

		_stagingLayoutScopeGroup = GroupLocalServiceUtil.updateGroup(
			_stagingLayoutScopeGroup);

		_mockHttpServletRequest.setAttribute(WebKeys.LAYOUT, stagingLayout);
	}

	@Ignore
	@Test
	public void testGetScopeGroupId() throws Exception {
		_testGetScopeGroupId(
			false, false, JournalContentPortletKeys.JOURNAL_CONTENT);
		_testGetScopeGroupId(
			false, true, JournalContentPortletKeys.JOURNAL_CONTENT);
		_testGetScopeGroupId(
			true, false, JournalContentPortletKeys.JOURNAL_CONTENT);
		_testGetScopeGroupId(
			true, true, JournalContentPortletKeys.JOURNAL_CONTENT);

		_testGetScopeGroupId(false, false, BlogsPortletKeys.BLOGS);
		_testGetScopeGroupId(false, true, BlogsPortletKeys.BLOGS);
		_testGetScopeGroupId(true, false, BlogsPortletKeys.BLOGS);
		_testGetScopeGroupId(true, true, BlogsPortletKeys.BLOGS);
	}

	@Override
	protected String[] getNotStagedPortletIds() {
		return new String[] {BlogsPortletKeys.BLOGS_ADMIN};
	}

	private Group _getExpectedGroup(
		boolean checkStagingGroup, boolean layoutScopeGroup,
		String rootPortletId) {

		if (_isStagedPortlet(rootPortletId)) {
			if (layoutScopeGroup) {
				return _stagingLayoutScopeGroup;
			}

			return stagingGroup;
		}

		if (layoutScopeGroup) {
			if (checkStagingGroup) {
				return _stagingLayoutScopeGroup;
			}

			return _liveLayoutScopeGroup;
		}

		if (checkStagingGroup) {
			return stagingGroup;
		}

		return liveGroup;
	}

	private Map<String, String[]> _getLayoutScopedPreferenceMap() {
		return HashMapBuilder.put(
			"lfrScopeLayoutUuid",
			new String[] {String.valueOf(stagingLayout.getUuid())}
		).put(
			"lfrScopeType", new String[] {"layout"}
		).build();
	}

	private boolean _isStagedPortlet(String rootPortletId) {
		return !ArrayUtil.contains(getNotStagedPortletIds(), rootPortletId);
	}

	private void _testGetScopeGroupId(
			boolean checkStagingGroup, boolean layoutScopeGroup,
			String rootPortletId)
		throws Exception {

		String portletId = null;

		if (layoutScopeGroup) {
			portletId = LayoutTestUtil.addPortletToLayout(
				stagingLayout, rootPortletId, _getLayoutScopedPreferenceMap());
		}
		else {
			portletId = LayoutTestUtil.addPortletToLayout(
				stagingLayout, rootPortletId);
		}

		Group expectedGroup = _getExpectedGroup(
			checkStagingGroup, layoutScopeGroup, rootPortletId);

		Assert.assertEquals(
			expectedGroup.getGroupId(),
			PortalUtil.getScopeGroupId(
				_mockHttpServletRequest, portletId, checkStagingGroup));
	}

	private Group _liveLayoutScopeGroup;
	private final MockHttpServletRequest _mockHttpServletRequest =
		new MockHttpServletRequest();
	private Group _stagingLayoutScopeGroup;

}
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.util;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.Map;

import junit.framework.AssertionFailedError;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Thiago Buarque
 */
public class ConfigurationFilterStringUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetCompanyScopedFilterString() throws Exception {
		String filterString =
			ConfigurationFilterStringUtil.getCompanyScopedFilterString(
				null, null);

		_test(
			true, filterString,
			HashMapBuilder.put(
				"companyId", "any"
			).build());
		_test(
			true, filterString,
			HashMapBuilder.put(
				"dxp.lxc.liferay.com.virtualInstanceId", "any"
			).build());
		_test(
			false, filterString,
			HashMapBuilder.put(
				"companyId", "any"
			).put(
				"groupId", "any"
			).build());
		_test(
			false, filterString,
			HashMapBuilder.put(
				"companyId", "any"
			).put(
				"siteExternalReferenceCode", "any"
			).build());
		_test(
			false, filterString,
			HashMapBuilder.put(
				"companyId", "any"
			).put(
				"portletInstanceId", "any"
			).build());

		String companyId = String.valueOf(RandomTestUtil.randomLong());
		String virtualInstanceId = RandomTestUtil.randomString();

		filterString =
			ConfigurationFilterStringUtil.getCompanyScopedFilterString(
				companyId, virtualInstanceId);

		_test(
			true, filterString,
			HashMapBuilder.put(
				"companyId", companyId
			).build());
		_test(
			true, filterString,
			HashMapBuilder.put(
				"dxp.lxc.liferay.com.virtualInstanceId", virtualInstanceId
			).build());
		_test(
			true, filterString,
			HashMapBuilder.put(
				"companyId", companyId
			).put(
				"dxp.lxc.liferay.com.virtualInstanceId", virtualInstanceId
			).build());
		_test(
			false, filterString,
			HashMapBuilder.put(
				"companyId", "any"
			).build());
		_test(
			false, filterString,
			HashMapBuilder.put(
				"companyId", companyId
			).put(
				"groupId", "any"
			).build());
		_test(
			false, filterString,
			HashMapBuilder.put(
				"companyId", companyId
			).put(
				"siteExternalReferenceCode", "any"
			).build());
		_test(
			false, filterString,
			HashMapBuilder.put(
				"companyId", companyId
			).put(
				"portletInstanceId", "any"
			).build());
	}

	@Test
	public void testGetGroupScopedFilterString() throws Exception {
		String filterString =
			ConfigurationFilterStringUtil.getGroupScopedFilterString(
				null, null);

		_test(
			true, filterString,
			HashMapBuilder.put(
				"groupId", "any"
			).build());
		_test(
			true, filterString,
			HashMapBuilder.put(
				"siteExternalReferenceCode", "any"
			).build());
		_test(
			false, filterString,
			HashMapBuilder.put(
				"groupId", "any"
			).put(
				"portletInstanceId", "any"
			).build());

		String groupId = String.valueOf(RandomTestUtil.randomLong());
		String siteExternalReferenceCode = RandomTestUtil.randomString();

		filterString = ConfigurationFilterStringUtil.getGroupScopedFilterString(
			groupId, siteExternalReferenceCode);

		_test(
			true, filterString,
			HashMapBuilder.put(
				"groupId", groupId
			).build());
		_test(
			true, filterString,
			HashMapBuilder.put(
				"siteExternalReferenceCode", siteExternalReferenceCode
			).build());
		_test(
			false, filterString,
			HashMapBuilder.put(
				"groupId", "any"
			).build());
		_test(
			false, filterString,
			HashMapBuilder.put(
				"groupId", groupId
			).put(
				"portletInstanceId", "any"
			).build());
	}

	@Test
	public void testGetPortletScopedFilterString() throws Exception {
		String filterString =
			ConfigurationFilterStringUtil.getPortletScopedFilterString(
				null, null, null);

		_test(
			true, filterString,
			HashMapBuilder.put(
				"groupId", "any"
			).put(
				"portletInstanceId", "any"
			).build());
		_test(
			true, filterString,
			HashMapBuilder.put(
				"portletInstanceId", "any"
			).put(
				"siteExternalReferenceCode", "any"
			).build());
		_test(
			false, filterString,
			HashMapBuilder.put(
				"groupId", "any"
			).build());
		_test(
			false, filterString,
			HashMapBuilder.put(
				"siteExternalReferenceCode", "any"
			).build());

		String groupId = String.valueOf(RandomTestUtil.randomLong());
		String portletInstanceId = RandomTestUtil.randomString();
		String siteExternalReferenceCode = RandomTestUtil.randomString();

		filterString =
			ConfigurationFilterStringUtil.getPortletScopedFilterString(
				groupId, portletInstanceId, siteExternalReferenceCode);

		_test(
			true, filterString,
			HashMapBuilder.put(
				"groupId", groupId
			).put(
				"portletInstanceId", portletInstanceId
			).build());
		_test(
			true, filterString,
			HashMapBuilder.put(
				"portletInstanceId", portletInstanceId
			).put(
				"siteExternalReferenceCode", siteExternalReferenceCode
			).build());
		_test(
			false, filterString,
			HashMapBuilder.put(
				"groupId", "any"
			).put(
				"portletInstanceId", portletInstanceId
			).build());
		_test(
			false, filterString,
			HashMapBuilder.put(
				"portletInstanceId", portletInstanceId
			).build());
		_test(
			false, filterString,
			HashMapBuilder.put(
				"groupId", groupId
			).put(
				"portletInstanceId", "any"
			).build());
	}

	@Test
	public void testGetSystemScopedFilterString() throws Exception {
		String filterString =
			ConfigurationFilterStringUtil.getSystemScopedFilterString();

		_test(true, filterString, Collections.emptyMap());
		_test(
			true, filterString,
			HashMapBuilder.put(
				"companyId", "0"
			).build());
		_test(
			false, filterString,
			HashMapBuilder.put(
				"companyId", "any"
			).build());
		_test(
			false, filterString,
			HashMapBuilder.put(
				"dxp.lxc.liferay.com.virtualInstanceId", "any"
			).build());
		_test(
			false, filterString,
			HashMapBuilder.put(
				"groupId", "any"
			).build());
		_test(
			false, filterString,
			HashMapBuilder.put(
				"portletInstanceId", "any"
			).build());
		_test(
			false, filterString,
			HashMapBuilder.put(
				"siteExternalReferenceCode", "any"
			).build());
	}

	private void _test(
			boolean matches, String pidFilterString,
			Map<String, String> payload)
		throws Exception {

		Filter filter = FrameworkUtil.createFilter(pidFilterString);

		if (matches && !filter.matches(payload)) {
			throw new AssertionFailedError(
				filter + " does not match " + payload);
		}
		else if (!matches && filter.matches(payload)) {
			throw new AssertionFailedError(filter + " matches " + payload);
		}
	}

}
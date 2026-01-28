/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.util;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.time.LocalDate;
import java.time.ZoneId;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Carolina Barbosa
 */
public class TasksSectionUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		Mockito.when(
			_groupedModel.getGroupId()
		).thenReturn(
			_GROUP_ID
		);

		Mockito.when(
			_objectDefinition.getObjectDefinitionId()
		).thenReturn(
			_OBJECT_DEFINITION_ID
		);
	}

	@Test
	public void testGetSearchURL() {
		Assert.assertEquals(
			_getBaseSearchURL(),
			TasksSectionUtil.getSearchURL(null, _objectDefinition));
		Assert.assertEquals(
			_getSearchURL(),
			TasksSectionUtil.getSearchURL(_groupedModel, _objectDefinition));
	}

	@Test
	public void testGetSearchURLProperties() {
		Map<String, Object> properties =
			TasksSectionUtil.getSearchURLProperties(
				_groupedModel, _objectDefinition);

		Assert.assertEquals(
			_getSearchURL(" and cmpState eq 'blocked'"),
			GetterUtil.getString(properties.get("blockedCountURL")));
		Assert.assertEquals(
			_getSearchURL(" and cmpState eq 'inProgress'"),
			GetterUtil.getString(properties.get("inProgressCountURL")));
		Assert.assertEquals(
			_getSearchURL(
				StringBundler.concat(
					" and cmpDueDate lt ",
					LocalDate.now(
					).atStartOfDay(
						ZoneId.systemDefault()
					).toInstant(),
					" and cmpState ne 'done'")),
			GetterUtil.getString(properties.get("overdueCountURL")));
		Assert.assertEquals(
			_getSearchURL(),
			GetterUtil.getString(properties.get("totalCountURL")));
	}

	private String _getBaseSearchURL() {
		return StringBundler.concat(
			"/o/search/v1.0/search?emptySearch=true&filter=objectDefinitionId ",
			"eq ", _OBJECT_DEFINITION_ID);
	}

	private String _getSearchURL() {
		return _getSearchURL(StringPool.BLANK);
	}

	private String _getSearchURL(String filterString) {
		return StringBundler.concat(
			_getBaseSearchURL(), " and scopeGroupId eq ", _GROUP_ID,
			filterString);
	}

	private static final long _GROUP_ID = RandomTestUtil.randomLong();

	private static final long _OBJECT_DEFINITION_ID =
		RandomTestUtil.randomLong();

	private final GroupedModel _groupedModel = Mockito.mock(GroupedModel.class);
	private final ObjectDefinition _objectDefinition = Mockito.mock(
		ObjectDefinition.class);

}
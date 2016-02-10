/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.staging.security.spring;

import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.portal.kernel.model.Group;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Tomas Polesovsky
 */
@RunWith(PowerMockRunner.class)
public class LiveGroupStagingAdviceTest extends PowerMockito {

	@BeforeClass
	public static void setUpClass() {
		_liveGroupStagingAdvice = new LiveGroupStagingAdvice(null) {};

		Staging staging = setUpStaging();

		_liveGroupStagingAdvice.setStaging(staging);
	}

	@Test
	public void testReplaceArgumentWithGroup() throws Exception {
		Object[] argument = new Object[] {mockGroup(_STAGING_GROUP_ID)};

		_liveGroupStagingAdvice.replaceArgument(argument, 0);

		Assert.assertEquals(_LIVE_GROUP_ID, ((Group)argument[0]).getGroupId());

		argument = new Object[] {
			mockGroup(11111), mockGroup(_STAGING_GROUP_ID)
		};

		_liveGroupStagingAdvice.replaceArgument(argument, 1);

		Assert.assertEquals(11111, ((Group)argument[0]).getGroupId());
		Assert.assertEquals(_LIVE_GROUP_ID, ((Group)argument[1]).getGroupId());
	}

	@Test
	public void testReplaceArgumentWithGroupArray() throws Exception {
		Object[] argument = new Object[] {
			new Group[] {mockGroup(_STAGING_GROUP_ID)}
		};

		_liveGroupStagingAdvice.replaceArgument(argument, 0);

		Assert.assertEquals(
			_LIVE_GROUP_ID, ((Group[])argument[0])[0].getGroupId());

		argument = new Object[] {
			new Group[] {mockGroup(11111)},
			new Group[] {mockGroup(_STAGING_GROUP_ID)}
		};

		_liveGroupStagingAdvice.replaceArgument(argument, 1);

		Assert.assertEquals(11111, ((Group[])argument[0])[0].getGroupId());
		Assert.assertEquals(
			_LIVE_GROUP_ID, ((Group[])argument[1])[0].getGroupId());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testReplaceArgumentWithList() throws Exception {
		Object[] argument = new Object[] {
			Arrays.asList(new Group[] {mockGroup(_STAGING_GROUP_ID)})
		};

		_liveGroupStagingAdvice.replaceArgument(argument, 0);

		Assert.assertEquals(
			_LIVE_GROUP_ID, ((List<Group>)argument[0]).get(0).getGroupId());

		argument = new Object[] {
			Arrays.asList(new Group[] {mockGroup(11111)}),
			Arrays.asList(new Group[] {mockGroup(_STAGING_GROUP_ID)})
		};

		_liveGroupStagingAdvice.replaceArgument(argument, 1);

		Assert.assertEquals(
			11111, ((List<Group>)argument[0]).get(0).getGroupId());

		Assert.assertEquals(
			_LIVE_GROUP_ID, ((List<Group>)argument[1]).get(0).getGroupId());
	}

	@Test
	public void testReplaceArgumentWithLong() throws Exception {
		Object[] argument = new Object[] {_STAGING_GROUP_ID};

		_liveGroupStagingAdvice.replaceArgument(argument, 0);

		Assert.assertEquals(_LIVE_GROUP_ID, argument[0]);

		argument = new Object[] {11111, _STAGING_GROUP_ID};

		_liveGroupStagingAdvice.replaceArgument(argument, 1);

		Assert.assertEquals(11111, argument[0]);
		Assert.assertEquals(_LIVE_GROUP_ID, argument[1]);
	}

	@Test
	public void testReplaceArgumentWithLongArray() throws Exception {
		Object[] argument = new Object[] {new long[] {_STAGING_GROUP_ID}};

		_liveGroupStagingAdvice.replaceArgument(argument, 0);

		Assert.assertEquals(_LIVE_GROUP_ID, ((long[])argument[0])[0]);

		argument = new Object[] {
			new long[] {11111}, new long[] {_STAGING_GROUP_ID}
		};

		_liveGroupStagingAdvice.replaceArgument(argument, 1);

		Assert.assertEquals(11111, ((long[])argument[0])[0]);
		Assert.assertEquals(_LIVE_GROUP_ID, ((long[])argument[1])[0]);
	}

	@Test
	public void testReplaceArgumentWithLongObjectArray() throws Exception {
		Object[] argument = new Object[] {new Long[] {_STAGING_GROUP_ID}};

		_liveGroupStagingAdvice.replaceArgument(argument, 0);

		Assert.assertEquals(
			Long.valueOf(_LIVE_GROUP_ID), ((Long[])argument[0])[0]);

		argument = new Object[] {
			new Long[] {Long.valueOf(11111)}, new Long[] {_STAGING_GROUP_ID}
		};

		_liveGroupStagingAdvice.replaceArgument(argument, 1);

		Assert.assertEquals(Long.valueOf(11111), ((Long[])argument[0])[0]);
		Assert.assertEquals(
			Long.valueOf(_LIVE_GROUP_ID), ((Long[])argument[1])[0]);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testReplaceArgumentWithSet() throws Exception {
		Object[] argument = new Object[] {
			new HashSet(
				Arrays.asList(new Group[] {mockGroup(_STAGING_GROUP_ID)}))
		};

		_liveGroupStagingAdvice.replaceArgument(argument, 0);

		Assert.assertEquals(
			_LIVE_GROUP_ID,
			((Set<Group>)argument[0]).iterator().next().getGroupId());

		argument = new Object[] {
			new HashSet(Arrays.asList(new Group[] {mockGroup(11111)})),
			new HashSet(
				Arrays.asList(new Group[] {mockGroup(_STAGING_GROUP_ID)}))
		};

		_liveGroupStagingAdvice.replaceArgument(argument, 1);

		Assert.assertEquals(
			11111, ((Set<Group>)argument[0]).iterator().next().getGroupId());
		Assert.assertEquals(
			_LIVE_GROUP_ID,
			((Set<Group>)argument[1]).iterator().next().getGroupId());
	}

	@Test
	public void testReplaceGroupArgument() throws Exception {
		Group group;

		group = _liveGroupStagingAdvice.replaceGroupArgument(
			mockGroup(_STAGING_GROUP_ID));
		Assert.assertEquals(_LIVE_GROUP_ID, group.getGroupId());

		group = _liveGroupStagingAdvice.replaceGroupArgument(mockGroup(11111));
		Assert.assertEquals(11111, group.getGroupId());

		group = _liveGroupStagingAdvice.replaceGroupArgument(null);
		Assert.assertNull(group);
	}

	@Test
	public void testReplaceGroupIdArgument() throws Exception {
		long groupId;

		groupId = _liveGroupStagingAdvice.replaceGroupIdArgument(
			_STAGING_GROUP_ID);
		Assert.assertEquals(_LIVE_GROUP_ID, groupId);

		groupId = _liveGroupStagingAdvice.replaceGroupIdArgument(11111);
		Assert.assertEquals(11111, groupId);
	}

	protected static Group mockGroup(long groupId) {
		Group group = mock(Group.class);

		when(
			group.getGroupId()
		).thenReturn(
			groupId
		);

		return group;
	}

	protected static Staging setUpStaging() {
		Staging staging = mock(Staging.class);

		when(
			staging.getLiveGroup(Matchers.anyLong())
		).then(
			new Answer<Group>() {

				@Override
				public Group answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					Object[] args = invocationOnMock.getArguments();

					long groupId = (long)args[0];

					if (groupId == _STAGING_GROUP_ID) {
						return mockGroup(_LIVE_GROUP_ID);
					}

					return mockGroup(groupId);
				}

			}
		);

		when(
			staging.getLiveGroupId(Matchers.anyLong())
		).then(
			new Answer<Long>() {

				@Override
				public Long answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					Object[] args = invocationOnMock.getArguments();

					long groupId = (long)args[0];

					if (groupId == _STAGING_GROUP_ID) {
						return _LIVE_GROUP_ID;
					}

					return groupId;
				}

			}
		);

		return staging;
	}

	private static final long _LIVE_GROUP_ID = 12345;

	private static final long _STAGING_GROUP_ID = 54321;

	private static LiveGroupStagingAdvice _liveGroupStagingAdvice;

}
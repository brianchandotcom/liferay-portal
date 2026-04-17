/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.dsr.site.initializer.internal.model.listener;

import com.liferay.analytics.settings.rest.dto.v1_0.Channel;
import com.liferay.analytics.settings.rest.dto.v1_0.DataSource;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.analytics.settings.rest.resource.v1_0.ChannelResource;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Stefano Motta
 */
public class ObjectEntryModelListenerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		Mockito.when(
			_channelResourceBuilder.build()
		).thenReturn(
			_channelResource
		);

		Mockito.when(
			_channelResourceBuilder.checkPermissions(false)
		).thenReturn(
			_channelResourceBuilder
		);

		Mockito.when(
			_channelResourceBuilder.user(Mockito.any(User.class))
		).thenReturn(
			_channelResourceBuilder
		);

		Mockito.when(
			_channelResourceFactory.create()
		).thenReturn(
			_channelResourceBuilder
		);

		Mockito.when(
			_userLocalService.getUser(Mockito.anyLong())
		).thenReturn(
			Mockito.mock(User.class)
		);
	}

	@Test
	public void testPatchChannel() throws Exception {
		Mockito.when(
			_analyticsSettingsManager.isAnalyticsEnabled(_COMPANY_ID)
		).thenReturn(
			false
		);

		ReflectionTestUtil.invoke(
			_objectEntryModelListener, "_patchChannel",
			new Class<?>[] {long.class, ObjectDefinition.class, long.class},
			_COMPANY_ID, _objectDefinition, _USER_ID);

		Mockito.verifyNoInteractions(_channelResourceFactory);

		Mockito.clearInvocations(_channelResource);

		Mockito.when(
			_analyticsSettingsManager.isAnalyticsEnabled(_COMPANY_ID)
		).thenReturn(
			true
		);

		Mockito.when(
			_channelResource.getChannelsPage(
				Mockito.eq("DSR"), Mockito.any(), Mockito.any())
		).thenReturn(
			Page.of(Collections.emptyList())
		);

		Mockito.when(
			_channelResource.postChannel(Mockito.any(Channel.class))
		).thenReturn(
			new Channel()
		);

		ReflectionTestUtil.invoke(
			_objectEntryModelListener, "_patchChannel",
			new Class<?>[] {long.class, ObjectDefinition.class, long.class},
			_COMPANY_ID, _objectDefinition, _USER_ID);

		ArgumentCaptor<Channel> argumentCaptor = ArgumentCaptor.forClass(
			Channel.class);

		Mockito.verify(
			_channelResource
		).postChannel(
			argumentCaptor.capture()
		);

		Channel channel = argumentCaptor.getValue();

		Assert.assertEquals("DSR", channel.getName());

		Mockito.clearInvocations(_channelResource);

		channel.setChannelId(_CHANNEL_ID);

		Mockito.when(
			_channelResource.getChannelsPage(
				Mockito.eq("DSR"), Mockito.any(), Mockito.any())
		).thenReturn(
			Page.of(Collections.singletonList(channel))
		);

		String className = RandomTestUtil.randomString();

		Mockito.when(
			_objectDefinition.getClassName()
		).thenReturn(
			className
		);

		Mockito.when(
			_group.getGroupId()
		).thenReturn(
			_GROUP_ID
		);

		Mockito.when(
			_groupLocalService.getGroups(_COMPANY_ID, className, 0L)
		).thenReturn(
			Collections.singletonList(_group)
		);

		ReflectionTestUtil.invoke(
			_objectEntryModelListener, "_patchChannel",
			new Class<?>[] {long.class, ObjectDefinition.class, long.class},
			_COMPANY_ID, _objectDefinition, _USER_ID);

		argumentCaptor = ArgumentCaptor.forClass(Channel.class);

		Mockito.verify(
			_channelResource
		).patchChannel(
			argumentCaptor.capture()
		);

		channel = argumentCaptor.getValue();

		Assert.assertEquals(_CHANNEL_ID, channel.getChannelId());

		DataSource[] dataSources = channel.getDataSources();

		Assert.assertEquals(
			Arrays.toString(dataSources), 1, dataSources.length);
		Assert.assertArrayEquals(
			new Long[] {_GROUP_ID}, dataSources[0].getSiteIds());
	}

	private static final String _CHANNEL_ID = RandomTestUtil.randomString();

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final long _GROUP_ID = RandomTestUtil.randomLong();

	private static final long _USER_ID = RandomTestUtil.randomLong();

	private final AnalyticsSettingsManager _analyticsSettingsManager =
		Mockito.mock(AnalyticsSettingsManager.class);
	private final ChannelResource _channelResource = Mockito.mock(
		ChannelResource.class);
	private final ChannelResource.Builder _channelResourceBuilder =
		Mockito.mock(ChannelResource.Builder.class);
	private final ChannelResource.Factory _channelResourceFactory =
		Mockito.mock(ChannelResource.Factory.class);
	private final Group _group = Mockito.mock(Group.class);
	private final GroupLocalService _groupLocalService = Mockito.mock(
		GroupLocalService.class);
	private final ObjectDefinition _objectDefinition = Mockito.mock(
		ObjectDefinition.class);

	@InjectMocks
	private ObjectEntryModelListener _objectEntryModelListener;

	private final UserLocalService _userLocalService = Mockito.mock(
		UserLocalService.class);

}
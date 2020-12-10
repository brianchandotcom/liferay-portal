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

package com.liferay.document.library.external.video.resolver.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.video.external.shortcut.DLVideoExternalShortcut;
import com.liferay.document.library.video.external.shortcut.resolver.DLVideoExternalShortcutResolver;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class DLVideoExternalShortcutResolverTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testResolveFromFacebook() {
		Assert.assertNotNull(
			_dlVideoExternalShortcutResolver.resolve(
				"https://www.facebook.com/watch/?v=VIDEO_ID"));
		Assert.assertNotNull(
			_dlVideoExternalShortcutResolver.resolve(
				"https://www.facebook.com/USER_ID/videos/VIDEO_ID"));
	}

	@Test
	public void testResolveFromTwitch() {
		Assert.assertNotNull(
			_dlVideoExternalShortcutResolver.resolve(
				"https://www.twitch.tv/videos/VIDEO_ID"));
	}

	@Test
	public void testResolveFromVimeo() {
		Assert.assertNotNull(
			_dlVideoExternalShortcutResolver.resolve(
				"https://vimeo.com/VIDEO_ID"));
		Assert.assertNotNull(
			_dlVideoExternalShortcutResolver.resolve(
				"https://vimeo.com/album/ALBUM_ID/video/VIDEO_ID"));
		Assert.assertNotNull(
			_dlVideoExternalShortcutResolver.resolve(
				"https://vimeo.com/channels/CHANNEL_ID/VIDEO_ID"));
		Assert.assertNotNull(
			_dlVideoExternalShortcutResolver.resolve(
				"https://vimeo.com/groups/GROUP_ID/videos/VIDEO_ID"));
		Assert.assertNotNull(
			_dlVideoExternalShortcutResolver.resolve(
				"https://vimeo.com/showcase/SHOWCASE_ID/video/VIDEO_ID"));
	}

	@Test
	public void testResolveFromYouTube() {
		Assert.assertNotNull(
			_dlVideoExternalShortcutResolver.resolve(
				"https://www.youtube.com/watch?v=VIDEO_ID"));
		Assert.assertNotNull(
			_dlVideoExternalShortcutResolver.resolve(
				"https://youtu.be/VIDEO_ID"));

		DLVideoExternalShortcut dlVideoExternalShortcut =
			_dlVideoExternalShortcutResolver.resolve(
				"https://www.youtube.com/watch?v=VIDEO_ID&t=61");

		Assert.assertEquals(
			StringBundler.concat(
				"<iframe allow=\"autoplay; encrypted-media\" allowfullscreen ",
				"height=\"315\" frameborder=\"0\" ",
				"src=\"https://www.youtube.com/embed",
				"/VIDEO_ID?rel=0\" width=\"560\"></iframe>"),
			dlVideoExternalShortcut.getEmbeddableHTML());
	}

	@Inject
	private DLVideoExternalShortcutResolver _dlVideoExternalShortcutResolver;

}
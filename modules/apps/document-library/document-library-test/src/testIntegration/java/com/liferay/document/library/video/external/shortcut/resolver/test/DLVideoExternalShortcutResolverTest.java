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

package com.liferay.document.library.video.external.shortcut.resolver.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.video.external.shortcut.DLVideoExternalShortcut;
import com.liferay.document.library.video.external.shortcut.resolver.DLVideoExternalShortcutResolver;
import com.liferay.frontend.editor.embed.EditorEmbedProvider;
import com.liferay.frontend.editor.embed.constants.EditorEmbedProviderTypeConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

import org.springframework.mock.web.MockHttpServletRequest;

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
	public void testResolveFromAnEditorEmbedProvider() {
		Bundle bundle = FrameworkUtil.getBundle(
			DLVideoExternalShortcutResolverTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceRegistration<EditorEmbedProvider>
			editorEmbedProviderServiceRegistration =
				bundleContext.registerService(
					EditorEmbedProvider.class,
					new EditorEmbedProvider() {

						@Override
						public String getId() {
							return "test";
						}

						@Override
						public String getTpl() {
							return "<iframe>{embedId}</iframe>";
						}

						@Override
						public String[] getURLSchemes() {
							return new String[] {
								"http:\\/\\/test\\.example\\/(.*)"
							};
						}

					},
					MapUtil.singletonDictionary(
						"type", EditorEmbedProviderTypeConstants.VIDEO));

		try {
			Assert.assertEquals(
				"<iframe>VIDEO_ID</iframe>",
				_renderHTML("http://test.example/VIDEO_ID"));
		}
		finally {
			editorEmbedProviderServiceRegistration.unregister();
		}
	}

	@Test
	public void testResolveFromFacebook() {
		Assert.assertEquals(
			StringBundler.concat(
				"<iframe allowFullScreen=\"true\" allowTransparency=\"true\" ",
				"frameborder=\"0\" height=\"315\" ",
				"src=\"https://www.facebook.com/plugins/video.php?height=315&",
				"href=https%3A%2F%2Fwww.facebook.com%2Fwatch%2F%3Fv%3DVIDEO_ID",
				"&show_text=0&width=560\" scrolling=\"no\" style=\"border: ",
				"none; overflow: hidden;\" width=\"560\"></iframe>"),
			_renderHTML("https://www.facebook.com/watch/?v=VIDEO_ID"));
		Assert.assertEquals(
			StringBundler.concat(
				"<iframe allowFullScreen=\"true\" allowTransparency=\"true\" ",
				"frameborder=\"0\" height=\"315\" ",
				"src=\"https://www.facebook.com/plugins/video.php?height=315&",
				"href=https%3A%2F%2Fwww.facebook.com%2FUSER_ID%2Fvideos%2F",
				"VIDEO_ID&show_text=0&width=560\" scrolling=\"no\" ",
				"style=\"border: none; overflow: hidden;\" width=\"560\">",
				"</iframe>"),
			_renderHTML("https://www.facebook.com/USER_ID/videos/VIDEO_ID"));
		Assert.assertEquals(
			StringBundler.concat(
				"<iframe allowFullScreen=\"true\" allowTransparency=\"true\" ",
				"frameborder=\"0\" height=\"315\" ",
				"src=\"https://www.facebook.com/plugins/video.php?height=315&",
				"href=https%3A%2F%2Fm.facebook.com%2Fwatch%2F%3Fv%3DVIDEO_ID&",
				"show_text=0&width=560\" scrolling=\"no\" style=\"border: ",
				"none; overflow: hidden;\" width=\"560\"></iframe>"),
			_renderHTML("https://m.facebook.com/watch/?v=VIDEO_ID"));
		Assert.assertEquals(
			StringBundler.concat(
				"<iframe allowFullScreen=\"true\" allowTransparency=\"true\" ",
				"frameborder=\"0\" height=\"315\" ",
				"src=\"https://www.facebook.com/plugins/video.php?height=315&",
				"href=https%3A%2F%2Ffb.watch%2FVIDEO_ID%2F&",
				"show_text=0&width=560\" scrolling=\"no\" style=\"border: ",
				"none; overflow: hidden;\" width=\"560\"></iframe>"),
			_renderHTML("https://fb.watch/VIDEO_ID/"));
	}

	@Test
	public void testResolveFromTwitch() {
		Assert.assertEquals(
			StringBundler.concat(
				"<iframe allowfullscreen=\"true\" frameborder=\"0\" ",
				"height=\"315\" src=\"https://player.twitch.tv",
				"/?autoplay=false&video=VIDEO_ID&parent=", _HOST,
				"\" scrolling=\"no\" width=\"560\" ></iframe>"),
			_renderHTML("https://www.twitch.tv/videos/VIDEO_ID"));
		Assert.assertEquals(
			StringBundler.concat(
				"<iframe allowfullscreen=\"true\" frameborder=\"0\" ",
				"height=\"315\" src=\"https://player.twitch.tv",
				"/?autoplay=false&channel=CHANNEL_ID&parent=", _HOST,
				"\" scrolling=\"no\" width=\"560\" ></iframe>"),
			_renderHTML("https://www.twitch.tv/CHANNEL_ID"));
	}

	@Test
	public void testResolveFromVimeo() {
		String expectedIframe = StringBundler.concat(
			"<iframe allowfullscreen frameborder=\"0\" height=\"315\" ",
			"mozallowfullscreen src=\"https://player.vimeo.com/video",
			"/VIDEO_ID\" webkitallowfullscreen width=\"560\"></iframe>");

		Assert.assertEquals(
			expectedIframe, _renderHTML("https://vimeo.com/VIDEO_ID"));
		Assert.assertEquals(
			expectedIframe,
			_renderHTML("https://vimeo.com/album/ALBUM_ID/video/VIDEO_ID"));
		Assert.assertEquals(
			expectedIframe,
			_renderHTML("https://vimeo.com/channels/CHANNEL_ID/VIDEO_ID"));
		Assert.assertEquals(
			expectedIframe,
			_renderHTML("https://vimeo.com/groups/GROUP_ID/videos/VIDEO_ID"));
		Assert.assertEquals(
			expectedIframe,
			_renderHTML(
				"https://vimeo.com/showcase/SHOWCASE_ID/video/VIDEO_ID"));
		Assert.assertEquals(
			expectedIframe,
			_renderHTML("https://player.vimeo.com/video/VIDEO_ID"));
	}

	@Test
	public void testResolveFromYouTube() {
		String expectedIframe = StringBundler.concat(
			"<iframe allow=\"autoplay; encrypted-media\" allowfullscreen ",
			"height=\"315\" frameborder=\"0\" ",
			"src=\"https://www.youtube.com/embed",
			"/VIDEO_ID?rel=0\" width=\"560\"></iframe>");

		Assert.assertEquals(
			expectedIframe,
			_renderHTML("https://www.youtube.com/watch?v=VIDEO_ID"));
		Assert.assertEquals(
			expectedIframe, _renderHTML("https://youtu.be/VIDEO_ID"));
		Assert.assertEquals(
			expectedIframe,
			_renderHTML(
				"https://www.youtube.com/watch?v=VIDEO_ID&ab_channel=" +
					"CHANNEL_ID"));
		Assert.assertEquals(
			expectedIframe,
			_renderHTML(
				"https://www.youtube.com/watch?feature=player_embedded&v=" +
					"VIDEO_ID"));
		Assert.assertEquals(
			expectedIframe, _renderHTML("https://youtube.com/e/VIDEO_ID"));
		Assert.assertEquals(
			expectedIframe, _renderHTML("https://youtube.com/v/VIDEO_ID"));
		Assert.assertEquals(
			expectedIframe,
			_renderHTML("https://www.youtube.com/embed/VIDEO_ID?rel=0"));

		Assert.assertEquals(
			StringBundler.concat(
				"<iframe allow=\"autoplay; encrypted-media\" allowfullscreen ",
				"height=\"315\" frameborder=\"0\" ",
				"src=\"https://www.youtube.com/embed",
				"/VIDEO_ID?rel=0&start=61\" width=\"560\"></iframe>"),
			_renderHTML("https://www.youtube.com/watch?v=VIDEO_ID&t=61"));
	}

	private String _renderHTML(String url) {
		DLVideoExternalShortcut dlVideoExternalShortcut =
			_dlVideoExternalShortcutResolver.resolve(url);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader("Host", _HOST);

		return dlVideoExternalShortcut.renderHTML(mockHttpServletRequest);
	}

	private static final String _HOST = "localhost";

	@Inject
	private DLVideoExternalShortcutResolver _dlVideoExternalShortcutResolver;

}
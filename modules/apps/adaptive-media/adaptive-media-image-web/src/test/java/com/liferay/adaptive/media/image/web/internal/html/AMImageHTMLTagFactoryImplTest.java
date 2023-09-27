/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.web.internal.html;

import com.liferay.adaptive.media.image.media.query.Condition;
import com.liferay.adaptive.media.image.media.query.MediaQuery;
import com.liferay.adaptive.media.image.media.query.MediaQueryProvider;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Alejandro Tardín
 */
public class AMImageHTMLTagFactoryImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws PortalException {
		ReflectionTestUtil.setFieldValue(
			_amImageHTMLTagFactoryImpl, "_mediaQueryProvider",
			_mediaQueryProvider);

		Mockito.when(
			_fileEntry.getFileEntryId()
		).thenReturn(
			1234L
		);
	}

	@Test
	public void testCreatesAPictureTag() throws Exception {
		_addMediaQueries(_createMediaQuery(1989, "adaptiveURL"));

		String originalImgTag =
			"<img src=\"originalURL\" data-fileentryid=\"1234\"/>";

		StringBundler expectedSB = new StringBundler(5);

		expectedSB.append("<picture data-fileentryid=\"1234\">");
		expectedSB.append("<source media=\"(max-width:1989px)\" ");
		expectedSB.append("srcset=\"adaptiveURL\" />");
		expectedSB.append(originalImgTag);
		expectedSB.append("</picture>");

		String pictureTag = _amImageHTMLTagFactoryImpl.create(
			originalImgTag, _fileEntry);

		Assert.assertEquals(expectedSB.toString(), pictureTag);
	}

	@Test
	public void testCreatesAPictureTagWithSeveralMediaQueries()
		throws Exception {

		_addMediaQueries(
			_createMediaQuery(1986, "adaptiveURL1"),
			_createMediaQuery(1989, "adaptiveURL2"));

		String originalImgTag =
			"<img src=\"originalURL\" data-fileentryid=\"1234\"/>";

		StringBundler expectedSB = new StringBundler(7);

		expectedSB.append("<picture data-fileentryid=\"1234\">");
		expectedSB.append("<source media=\"(max-width:1986px)\" ");
		expectedSB.append("srcset=\"adaptiveURL1\" />");
		expectedSB.append("<source media=\"(max-width:1989px)\" ");
		expectedSB.append("srcset=\"adaptiveURL2\" />");
		expectedSB.append(originalImgTag);
		expectedSB.append("</picture>");

		String pictureTag = _amImageHTMLTagFactoryImpl.create(
			originalImgTag, _fileEntry);

		Assert.assertEquals(expectedSB.toString(), pictureTag);
	}

	@Test
	public void testNoSourceIsCreatedIfNoConditionIsPresent() throws Exception {
		_addMediaQueries(
			new MediaQuery(
				Collections.emptyList(), RandomTestUtil.randomString()));

		String originalImgTag =
			"<img src=\"originalURL\" data-fileentryid=\"1234\"/>";

		String pictureTag = _amImageHTMLTagFactoryImpl.create(
			originalImgTag, _fileEntry);

		Assert.assertEquals(
			StringBundler.concat(
				"<picture data-fileentryid=\"1234\">", originalImgTag,
				"</picture>"),
			pictureTag);
	}

	@Test
	public void testReturnsTheOriginalImgTagIfThereAreNoMediaQueries()
		throws Exception {

		_addMediaQueries();

		String originalImgTag =
			"<img src=\"originalURL\" data-fileentryid=\"1234\"/>";

		String pictureTag = _amImageHTMLTagFactoryImpl.create(
			originalImgTag, _fileEntry);

		Assert.assertSame(originalImgTag, pictureTag);
	}

	@Test
	public void testSupportsImageTagsWithNewLineCharacters() throws Exception {
		_addMediaQueries(_createMediaQuery(1989, "adaptiveURL"));

		StringBundler originalSB = new StringBundler(3);

		originalSB.append("<img data-fileentryid=\"1234\" ");
		originalSB.append(CharPool.NEW_LINE);
		originalSB.append("src=\"adaptable\"/>");

		StringBundler expectedSB = new StringBundler(5);

		expectedSB.append("<picture data-fileentryid=\"1234\">");
		expectedSB.append("<source media=\"(max-width:1989px)\" ");
		expectedSB.append("srcset=\"adaptiveURL\" />");
		expectedSB.append(originalSB.toString());
		expectedSB.append("</picture>");

		String pictureTag = _amImageHTMLTagFactoryImpl.create(
			originalSB.toString(), _fileEntry);

		Assert.assertEquals(expectedSB.toString(), pictureTag);
	}

	@Test
	public void testTheAttributeIsCaseInsensitive() throws Exception {
		_addMediaQueries(_createMediaQuery(1989, "adaptiveURL"));

		String originalImgTag =
			"<img src=\"originalURL\" datA-fileENTryID=\"1234\"/>";

		StringBundler expectedSB = new StringBundler(5);

		expectedSB.append("<picture data-fileentryid=\"1234\">");
		expectedSB.append("<source media=\"(max-width:1989px)\" ");
		expectedSB.append("srcset=\"adaptiveURL\" />");
		expectedSB.append(originalImgTag);
		expectedSB.append("</picture>");

		String pictureTag = _amImageHTMLTagFactoryImpl.create(
			originalImgTag, _fileEntry);

		Assert.assertEquals(expectedSB.toString(), pictureTag);
	}

	private void _addMediaQueries(MediaQuery... mediaQueries) throws Exception {
		Mockito.when(
			_mediaQueryProvider.getMediaQueries(_fileEntry)
		).thenReturn(
			Arrays.asList(mediaQueries)
		);
	}

	private MediaQuery _createMediaQuery(int width, String url)
		throws Exception {

		return new MediaQuery(
			Arrays.asList(new Condition("max-width", width + "px")), url);
	}

	private final AMImageHTMLTagFactoryImpl _amImageHTMLTagFactoryImpl =
		new AMImageHTMLTagFactoryImpl();
	private final FileEntry _fileEntry = Mockito.mock(FileEntry.class);
	private final MediaQueryProvider _mediaQueryProvider = Mockito.mock(
		MediaQueryProvider.class);

}
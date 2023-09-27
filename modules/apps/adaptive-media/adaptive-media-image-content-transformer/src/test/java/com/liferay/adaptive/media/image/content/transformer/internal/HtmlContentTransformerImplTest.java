/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.content.transformer.internal;

import com.liferay.adaptive.media.content.transformer.constants.ContentTransformerContentTypes;
import com.liferay.adaptive.media.image.html.AMImageHTMLTagFactory;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Alejandro Tardín
 * @author Sergio González
 */
public class HtmlContentTransformerImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws PortalException {
		ReflectionTestUtil.setFieldValue(
			_htmlContentTransformerImpl, "_amImageHTMLTagFactory",
			_amImageHTMLTagFactory);

		Mockito.when(
			_dlAppLocalService.getFileEntry(1989L)
		).thenReturn(
			_fileEntry
		);

		ReflectionTestUtil.setFieldValue(
			_htmlContentTransformerImpl, "_dlAppLocalService",
			_dlAppLocalService);
	}

	@Test
	public void testAlsoReplacesSeveralImagesInAMultilineString()
		throws Exception {

		Mockito.when(
			_amImageHTMLTagFactory.create(
				"<img data-fileentryid=\"1989\" src=\"adaptable\"/>",
				_fileEntry)
		).thenReturn(
			"<whatever></whatever>"
		);

		StringBundler expectedSB = new StringBundler(3);

		expectedSB.append("<div><div>");
		expectedSB.append("<whatever></whatever>");
		expectedSB.append("</div></div><br/>");

		StringBundler originalSB = new StringBundler(4);

		originalSB.append("<div><div>");
		originalSB.append("<img data-fileentryid=\"1989\" ");
		originalSB.append("src=\"adaptable\"/>");
		originalSB.append("</div></div><br/>");

		Assert.assertEquals(
			_duplicateWithNewLine(expectedSB.toString()),
			_htmlContentTransformerImpl.transform(
				_duplicateWithNewLine(originalSB.toString())));
	}

	@Test
	public void testContentTypeIsHTML() throws Exception {
		Assert.assertEquals(
			ContentTransformerContentTypes.HTML,
			_htmlContentTransformerImpl.getContentTransformerContentType());
	}

	@Test
	public void testReplacesAnAdaptableImgAfterANonadaptableOne()
		throws Exception {

		Mockito.when(
			_amImageHTMLTagFactory.create(
				"<img data-fileentryid=\"1989\" src=\"adaptable\"/>",
				_fileEntry)
		).thenReturn(
			"<whatever></whatever>"
		);

		Assert.assertEquals(
			"<img src=\"not-adaptable\"/><whatever></whatever>",
			_htmlContentTransformerImpl.transform(
				"<img src=\"not-adaptable\"/>" +
					"<img data-fileentryid=\"1989\" src=\"adaptable\"/>"));
	}

	@Test
	public void testReplacesTheAdaptableImagesWithTheAdaptiveTag()
		throws Exception {

		Mockito.when(
			_amImageHTMLTagFactory.create(
				"<img data-fileentryid=\"1989\" src=\"adaptable\"/>",
				_fileEntry)
		).thenReturn(
			"<whatever></whatever>"
		);

		Assert.assertEquals(
			"<whatever></whatever>",
			_htmlContentTransformerImpl.transform(
				"<img data-fileentryid=\"1989\" src=\"adaptable\"/>"));
	}

	@Test
	public void testReplacesTwoConsecutiveImageTags() throws Exception {
		Mockito.when(
			_amImageHTMLTagFactory.create(
				"<img data-fileentryid=\"1989\" src=\"adaptable\"/>",
				_fileEntry)
		).thenReturn(
			"<whatever></whatever>"
		);

		Assert.assertEquals(
			"<whatever></whatever><whatever></whatever>",
			_htmlContentTransformerImpl.transform(
				"<img data-fileentryid=\"1989\" src=\"adaptable\"/>" +
					"<img data-fileentryid=\"1989\" src=\"adaptable\"/>"));
	}

	@Test
	public void testReturnsNullForNullContent() throws Exception {
		Assert.assertNull(_htmlContentTransformerImpl.transform(null));
	}

	@Test
	public void testReturnsTheSameHTMLIfNoImagesArePresent() throws Exception {
		Assert.assertEquals(
			"<div><div>some <a>stuff</a></div></div>",
			_htmlContentTransformerImpl.transform(
				"<div><div>some <a>stuff</a></div></div>"));
	}

	@Test
	public void testReturnsTheSameHTMLIfThereAreNoAdaptableImagesPresent()
		throws Exception {

		Assert.assertEquals(
			"<div><div><img src=\"no.adaptable\"/></div></div>",
			_htmlContentTransformerImpl.transform(
				"<div><div><img src=\"no.adaptable\"/></div></div>"));
	}

	@Test
	public void testSupportsImageTagsWithNewLineCharacters() throws Exception {
		Mockito.when(
			_amImageHTMLTagFactory.create(
				"<img data-fileentryid=\"1989\" \nsrc=\"adaptable\"/>",
				_fileEntry)
		).thenReturn(
			"<whatever></whatever>"
		);

		Assert.assertEquals(
			"<whatever></whatever>",
			_htmlContentTransformerImpl.transform(
				StringBundler.concat(
					"<img data-fileentryid=\"1989\" ", CharPool.NEW_LINE,
					"src=\"adaptable\"/>")));
	}

	@Test
	public void testTheAttributeIsCaseInsensitive() throws Exception {
		Mockito.when(
			_amImageHTMLTagFactory.create(
				"<img data-fileentryid=\"1989\" src=\"adaptable\"/>",
				_fileEntry)
		).thenReturn(
			"<whatever></whatever>"
		);

		StringBundler originalSB = new StringBundler(4);

		originalSB.append("<div><div>");
		originalSB.append("<img data-fileentryid=\"1989\" ");
		originalSB.append("src=\"adaptable\"/>");
		originalSB.append("</div></div><br/>");

		Assert.assertEquals(
			"<div><div><whatever></whatever></div></div><br/>",
			_htmlContentTransformerImpl.transform(
				StringUtil.toLowerCase(originalSB.toString())));
	}

	private String _duplicateWithNewLine(String text) {
		return text + StringPool.NEW_LINE + text;
	}

	private final AMImageHTMLTagFactory _amImageHTMLTagFactory = Mockito.mock(
		AMImageHTMLTagFactory.class);
	private final DLAppLocalService _dlAppLocalService = Mockito.mock(
		DLAppLocalService.class);
	private final FileEntry _fileEntry = Mockito.mock(FileEntry.class);
	private final HtmlContentTransformerImpl _htmlContentTransformerImpl =
		new HtmlContentTransformerImpl();

}
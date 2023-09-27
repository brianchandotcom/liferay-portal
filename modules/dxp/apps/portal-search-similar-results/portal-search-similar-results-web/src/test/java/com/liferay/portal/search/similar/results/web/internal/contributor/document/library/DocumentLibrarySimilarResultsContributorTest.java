/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.similar.results.web.internal.contributor.document.library;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.similar.results.web.internal.builder.DestinationBuilderImpl;
import com.liferay.portal.search.similar.results.web.internal.builder.RouteBuilderImpl;
import com.liferay.portal.search.similar.results.web.internal.helper.HttpHelperImpl;
import com.liferay.portal.search.similar.results.web.spi.contributor.SimilarResultsContributor;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.DestinationHelper;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author André de Oliveira
 */
public class DocumentLibrarySimilarResultsContributorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testAssetNotFromDocumentLibrary() {
		String urlString = StringBundler.concat(
			"http://localhost:8080/web/guest/document-and-media/-",
			"/document_library/oagkfEivnD1J/view_file/39730",
			"?_com_liferay_document_library_web_portlet_DLPortlet_INSTANCE");

		DocumentLibrarySimilarResultsContributor
			documentLibrarySimilarResultsContributor =
				_createDocumentLibrarySimilarResultsContributor();

		documentLibrarySimilarResultsContributor.detectRoute(
			new RouteBuilderImpl(), () -> urlString);

		DestinationHelper destinationHelper = Mockito.mock(
			DestinationHelper.class);

		Mockito.doReturn(
			Mockito.mock(AssetRenderer.class)
		).when(
			destinationHelper
		).getAssetRenderer();

		Mockito.doReturn(
			"com.liferay.journal.model.JournalArticle"
		).when(
			destinationHelper
		).getClassName();

		Assert.assertEquals(
			urlString,
			writeDestination(
				urlString, documentLibrarySimilarResultsContributor,
				destinationHelper));
	}

	protected String writeDestination(
		String urlString, SimilarResultsContributor similarResultsContributor,
		DestinationHelper destinationHelper) {

		DestinationBuilderImpl destinationBuilderImpl =
			new DestinationBuilderImpl(urlString);

		similarResultsContributor.writeDestination(
			destinationBuilderImpl, destinationHelper);

		return destinationBuilderImpl.build();
	}

	private DocumentLibrarySimilarResultsContributor
		_createDocumentLibrarySimilarResultsContributor() {

		DocumentLibrarySimilarResultsContributor
			documentLibrarySimilarResultsContributor =
				new DocumentLibrarySimilarResultsContributor();

		ReflectionTestUtil.setFieldValue(
			documentLibrarySimilarResultsContributor, "_httpHelper",
			new HttpHelperImpl());

		return documentLibrarySimilarResultsContributor;
	}

}
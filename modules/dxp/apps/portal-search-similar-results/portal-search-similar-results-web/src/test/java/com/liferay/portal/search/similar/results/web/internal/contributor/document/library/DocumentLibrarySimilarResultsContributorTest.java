/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.similar.results.web.internal.contributor.document.library;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.search.similar.results.web.internal.builder.DestinationBuilderImpl;
import com.liferay.portal.search.similar.results.web.internal.builder.RouteBuilderImpl;
import com.liferay.portal.search.similar.results.web.internal.builder.TestHttp;
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
			new DestinationBuilderImpl(urlString, _http);

		similarResultsContributor.writeDestination(
			destinationBuilderImpl, destinationHelper);

		return destinationBuilderImpl.build();
	}

	private DocumentLibrarySimilarResultsContributor
		_createDocumentLibrarySimilarResultsContributor() {

		DocumentLibrarySimilarResultsContributor
			documentLibrarySimilarResultsContributor =
				new DocumentLibrarySimilarResultsContributor();

		documentLibrarySimilarResultsContributor.setHttpHelper(
			new HttpHelperImpl() {
				{
					setHttp(TestHttp.getInstance());
				}
			});

		return documentLibrarySimilarResultsContributor;
	}

	private final Http _http = TestHttp.getInstance();

}
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.rag.content.retriever;

import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import dev.langchain4j.rag.query.Query;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Feliphe Marinho
 */
public class LiferayWebSearchContentRetrieverTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testSearch() {
		_testSearch("http://127.0.0.1:8080");
		_testSearch("http://192.168.1.1");
	}

	private void _testSearch(String homePageURL) {
		try (MockedStatic<OAuth2ApplicationLocalServiceUtil>
				oAuth2ApplicationLocalServiceUtilMockedStatic =
					Mockito.mockStatic(
						OAuth2ApplicationLocalServiceUtil.class)) {

			OAuth2Application oAuth2Application = Mockito.mock(
				OAuth2Application.class);

			Mockito.when(
				oAuth2Application.getHomePageURL()
			).thenReturn(
				homePageURL
			);

			oAuth2ApplicationLocalServiceUtilMockedStatic.when(
				() -> OAuth2ApplicationLocalServiceUtil.getOAuth2Application(
					Mockito.anyLong())
			).thenReturn(
				oAuth2Application
			);

			LiferayWebSearchContentRetriever liferayWebSearchContentRetriever =
				new LiferayWebSearchContentRetriever(
					RandomTestUtil.randomString(), RandomTestUtil.randomLong(),
					RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
					RandomTestUtil.randomLong());

			SecurityException securityException = Assert.assertThrows(
				SecurityException.class,
				() -> liferayWebSearchContentRetriever.search(
					Mockito.mock(Query.class)));

			Assert.assertEquals(
				"Local links are not allowed: " + homePageURL,
				securityException.getMessage());
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

}
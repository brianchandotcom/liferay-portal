/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.search.rest.client.dto.v1_0.Suggestion;
import com.liferay.portal.search.rest.client.dto.v1_0.SuggestionsContributorConfiguration;
import com.liferay.portal.search.rest.client.dto.v1_0.SuggestionsContributorResults;
import com.liferay.portal.search.rest.client.pagination.Page;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Petteri Karttunen
 */
@RunWith(Arquillian.class)
public class SuggestionResourceTest extends BaseSuggestionResourceTestCase {

	@Override
	@Test
	public void testPostSuggestionsPage() throws Exception {
		String title = "Document Title";

		JournalTestUtil.addArticle(testGroup.getGroupId(), title, "");

		Page<SuggestionsContributorResults> suggestionPage =
			suggestionResource.postSuggestionsPage(
				"http://localhost:8080/web/guest/home", "%2Fsearch",
				testGroup.getGroupId(), "q",
				LayoutTestUtil.addTypePortletLayout(
					testGroup
				).getPlid(),
				"everything", "Document",
				new SuggestionsContributorConfiguration[] {
					new SuggestionsContributorConfiguration() {
						{
							contributorName = "basic";
							displayGroupName = "Suggestions";
						}
					}
				});

		Assert.assertEquals(1, suggestionPage.getTotalCount());

		SuggestionsContributorResults suggestionsContributorResults =
			suggestionPage.fetchFirstItem();

		Assert.assertEquals(
			"Suggestions", suggestionsContributorResults.getDisplayGroupName());

		Suggestion[] suggestions =
			suggestionsContributorResults.getSuggestions();

		Suggestion suggestion = suggestions[0];

		Assert.assertEquals(title, suggestion.getText());

		JSONObject suggestionAttributesJSONObject =
			JSONFactoryUtil.createJSONObject(
				String.valueOf(suggestion.getAttributes()));

		Assert.assertEquals(
			title, suggestionAttributesJSONObject.get("assetSearchSummary"));
	}

}
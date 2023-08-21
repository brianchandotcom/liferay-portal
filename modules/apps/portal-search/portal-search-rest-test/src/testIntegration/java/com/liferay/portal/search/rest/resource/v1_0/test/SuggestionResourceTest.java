/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.model.JournalArticle;
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
		String displayGroupName = "Suggestions";

		String title = "Document Title";

		_addJournalArticle(title);

		Page<SuggestionsContributorResults> suggestionPage =
			_postSuggestionsPage(
				"Document",
				new SuggestionsContributorConfiguration[] {
					_getSuggestionsContributorConfiguration(
						"basic", displayGroupName)
				});

		Assert.assertEquals(1, suggestionPage.getTotalCount());

		SuggestionsContributorResults suggestionsContributorResults =
			suggestionPage.fetchFirstItem();

		Assert.assertEquals(
			displayGroupName,
			suggestionsContributorResults.getDisplayGroupName());

		Suggestion[] suggestions =
			suggestionsContributorResults.getSuggestions();

		Suggestion suggestion = suggestions[0];

		Assert.assertEquals(title, suggestion.getText());

		JSONObject suggestionAttributesJSONObject =
			_getSuggestionAttributesAsJSONObject(suggestion);

		Assert.assertEquals(
			title, suggestionAttributesJSONObject.get("assetSearchSummary"));
	}

	private JournalArticle _addJournalArticle(String title) throws Exception {
		return JournalTestUtil.addArticle(testGroup.getGroupId(), title, "");
	}

	private JSONObject _getSuggestionAttributesAsJSONObject(
			Suggestion suggestion)
		throws Exception {

		Object suggestionAttributes = suggestion.getAttributes();

		return JSONFactoryUtil.createJSONObject(
			suggestionAttributes.toString());
	}

	private SuggestionsContributorConfiguration
		_getSuggestionsContributorConfiguration(
			String contributorName, String displayGroupName) {

		SuggestionsContributorConfiguration
			suggestionsContributorConfiguration =
				new SuggestionsContributorConfiguration();

		suggestionsContributorConfiguration.setContributorName(contributorName);

		suggestionsContributorConfiguration.setDisplayGroupName(
			displayGroupName);

		return suggestionsContributorConfiguration;
	}

	private Page<SuggestionsContributorResults> _postSuggestionsPage(
			String search,
			SuggestionsContributorConfiguration[]
				suggestionsContributorConfigurations)
		throws Exception {

		return suggestionResource.postSuggestionsPage(
			"http://localhost:8080/web/guest/home", "%2Fsearch",
			testGroup.getGroupId(), "q",
			LayoutTestUtil.addTypePortletLayout(
				testGroup
			).getPlid(),
			"everything", search, suggestionsContributorConfigurations);
	}

}
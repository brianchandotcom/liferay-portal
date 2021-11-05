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

package com.liferay.search.experiences.internal.blueprint.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Wade Cao
 */
@RunWith(Arquillian.class)
public class SXPBlueprintTextMatchOverMultipleFieldsTest
	extends BaseSXPBlueprintsTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		super.setUp();

		setUpSXPBlueprint(getClass());
	}

	@Test
	public void testSearchBestFieldsWithOperatorAnd() throws Exception {
		addJournalArticle(
			group.getGroupId(), "drink carbonated coca", "carbonated cola");
		addJournalArticle(
			group.getGroupId(), "drink carbonated pepsi cola",
			"carbonated cola cola");
		addJournalArticle(
			group.getGroupId(), "fruit punch", "non-carbonated cola");
		addJournalArticle(group.getGroupId(), "sprite", "carbonated cola cola");

		updateSXPBlueprint(
			_getConfigurationJSONString(1, 2, 1, "AUTO", "and", "best_fields"));

		assertSearch(
			"[drink carbonated coca, drink carbonated pepsi cola, sprite, " +
				"fruit punch]",
			"coca cola");
	}

	@Test
	public void testSearchBestFieldsWithOperatorOr() throws Exception {
		WikiNode wikiNode = WikiNodeLocalServiceUtil.addDefaultNode(
			user.getUserId(), serviceContext);

		_wikiPages.add(
			WikiPageLocalServiceUtil.addPage(
				user.getUserId(), wikiNode.getNodeId(), "lorem ipsum dolor",
				"ipsum sit", "Summary", false, serviceContext));
		_wikiPages.add(
			WikiPageLocalServiceUtil.addPage(
				user.getUserId(), wikiNode.getNodeId(), "lorem ipsum sit",
				"ipsum sit sit", "Summary", false, serviceContext));
		_wikiPages.add(
			WikiPageLocalServiceUtil.addPage(
				user.getUserId(), wikiNode.getNodeId(), "nunquis",
				"non-lorem ipsum sit", "Summary", false, serviceContext));

		updateSXPBlueprint(
			_getConfigurationJSONString(1, 1, 1, "0", "or", "best_fields"));

		assertSearch(
			"[lorem ipsum sit, lorem ipsum dolor, nunquis]", "ipsum sit sit");
	}

	@Test
	public void testSearchBoolPrefixWithOperator() throws Exception {
		_blogsEntries.add(
			BlogsEntryLocalServiceUtil.addEntry(
				user.getUserId(), "lorem ipsum sit", "ipsum sit sit",
				serviceContext));
		_blogsEntries.add(
			BlogsEntryLocalServiceUtil.addEntry(
				user.getUserId(), "lorem ipsum dolor", "ipsum sit",
				serviceContext));
		_blogsEntries.add(
			BlogsEntryLocalServiceUtil.addEntry(
				user.getUserId(), "amet", "ipsum sit sit", serviceContext));
		_blogsEntries.add(
			BlogsEntryLocalServiceUtil.addEntry(
				user.getUserId(), "nunquis", "non-lorem ipsum sit",
				serviceContext));

		updateSXPBlueprint(
			_getConfigurationJSONString(1, 2, 1, "0", "or", "bool_prefix"));

		assertSearchIgnoreRelevance(
			"[lorem ipsum dolor, lorem ipsum sit, nunquis]", "lorem dol");

		updateSXPBlueprint(
			_getConfigurationJSONString(1, 2, 1, "0", "and", "bool_prefix"));

		assertSearchIgnoreRelevance("[lorem ipsum dolor]", "lorem dol");
	}

	@Test
	public void testSearchCrossFieldsWithOperator() throws Exception {
		addJournalArticle(group.getGroupId(), "alpha beta", "foxtrot, golf");
		addJournalArticle(group.getGroupId(), "alpha edison", "hotel golf");
		addJournalArticle(group.getGroupId(), "beta charlie", "alpha");
		addJournalArticle(group.getGroupId(), "edison india", "beta");

		updateSXPBlueprint(
			_getConfigurationJSONString(1, 2, 1, null, "or", "cross_fields"));

		assertSearchIgnoreRelevance(
			"[alpha beta, alpha edison, beta charlie]", "alpha golf");

		updateSXPBlueprint(
			_getConfigurationJSONString(1, 2, 1, null, "and", "cross_fields"));

		assertSearchIgnoreRelevance("[alpha beta, alpha edison]", "alpha golf");
	}

	@Test
	public void testSearchMostFieldsWithOperator() throws Exception {
		_blogsEntries.add(
			BlogsEntryLocalServiceUtil.addEntry(
				user.getUserId(), "amet", "ipsum sit sit", serviceContext));
		_blogsEntries.add(
			BlogsEntryLocalServiceUtil.addEntry(
				user.getUserId(), "lorem ipsum dolor", "ipsum sit",
				serviceContext));
		_blogsEntries.add(
			BlogsEntryLocalServiceUtil.addEntry(
				user.getUserId(), "lorem ipsum sit", "ipsum sit sit",
				serviceContext));
		_blogsEntries.add(
			BlogsEntryLocalServiceUtil.addEntry(
				user.getUserId(), "nunquis", "non-lorem ipsum sit",
				serviceContext));

		updateSXPBlueprint(
			_getConfigurationJSONString(1, 1, 1, "0", "or", "most_fields"));

		assertSearch(
			"[lorem ipsum sit, lorem ipsum dolor, amet, nunquis]",
			"ipsum sit sit");

		updateSXPBlueprint(
			_getConfigurationJSONString(1, 1, 1, "0", "and", "most_fields"));

		assertSearch("[lorem ipsum sit, nunquis]", "sit lorem");
	}

	@Test
	public void testSearchPhrase() throws Exception {
		addJournalArticle(
			group.getGroupId(), "listen something", "do not listen to birds");
		addJournalArticle(
			group.getGroupId(), "listen to birds", "listen listen to birds");
		addJournalArticle(
			group.getGroupId(), "listen to planes", "listen to birds");
		addJournalArticle(
			group.getGroupId(), "silence", "listen listen to birds");

		updateSXPBlueprint(
			_getConfigurationJSONString(1, 2, 1, null, null, "phrase"));
		assertSearch("[listen to birds, silence]", "listen listen");
	}

	@Test
	public void testSearchPhrasePrefix() throws Exception {
		addJournalArticle(
			group.getGroupId(), "clouds",
			"simple things are beautiful sometimes");
		addJournalArticle(
			group.getGroupId(), "watch birds on the sky",
			"simple things are beautiful");
		addJournalArticle(
			group.getGroupId(), "watch planes on the sky",
			"simple things are not good");
		addJournalArticle(
			group.getGroupId(), "watch trains", "simple things are bad");

		updateSXPBlueprint(
			_getConfigurationJSONString(1, 2, 1, null, null, "phrase_prefix"));

		assertSearch(
			"[watch birds on the sky, clouds]", "simple things are beau");
	}

	private String _getConfigurationJSONString(
		int boost, int titleBoost, int contentBoost, String type,
		String configurationJSON) {

		configurationJSON = StringUtil.replace(
			configurationJSON, "${configuration.boost}", String.valueOf(boost));
		configurationJSON = StringUtil.replace(
			configurationJSON, "${configuration.fields.contentBoost}",
			"content${context.language_id}^" + contentBoost);
		configurationJSON = StringUtil.replace(
			configurationJSON, "${configuration.fields.titleBoost}",
			"localized_title${context.language_id}^" + titleBoost);

		return StringUtil.replace(
			configurationJSON, "${configuration.type}", type);
	}

	private String _getConfigurationJSONString(
		int boost, int titleBoost, int contentBoost, String fuzziness,
		String operator, String type) {

		String configurationJSON = getConfigurationJSONString(getClass());

		if (Validator.isBlank(fuzziness)) {
			configurationJSON = getConfigurationJSONString(
				getClass(), testName.getMethodName());
		}
		else {
			configurationJSON = StringUtil.replace(
				configurationJSON, "${configuration.fuzziness}", fuzziness);
		}

		if (Validator.isBlank(operator)) {
			configurationJSON = getConfigurationJSONString(
				getClass(), testName.getMethodName());
		}
		else {
			configurationJSON = StringUtil.replace(
				configurationJSON, "${configuration.operator}", operator);
		}

		return _getConfigurationJSONString(
			boost, titleBoost, contentBoost, type, configurationJSON);
	}

	@DeleteAfterTestRun
	private List<BlogsEntry> _blogsEntries = new ArrayList<>();

	@DeleteAfterTestRun
	private final List<WikiPage> _wikiPages = new ArrayList<>();

}
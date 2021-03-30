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

package com.liferay.portal.search.tuning.blueprints.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Collections;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Wade Cao
 */
@RunWith(Arquillian.class)
public class HideByExactTermMatchTest extends BaseBlueprintsTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testSearchHideContent() throws Exception {
		addJournalArticle("do not hide me", "");

		JournalFolder journalFolder = _addJournalFolder();

		addJournalArticle(
			group.getGroupId(), journalFolder.getFolderId(), "hide me", "");

		Blueprint blueprint = addCompanyBlueprint(
			Collections.singletonMap(
				LocaleUtil.US, getClass().getName() + "Blueprint"),
			Collections.singletonMap(LocaleUtil.US, ""),
			getConfigurationString((JSONObject[])null), "", 1);

		assertSearch(
			blueprint, null, "[hide me, do not hide me]", "hide me", null);

		assertSearch(
			blueprint,
			getConfigurationString(
				_getQueryElementJSONObject(journalFolder.getFolderId())),
			"[do not hide me]", "hide me",
			_getSelectedElementString(journalFolder.getFolderId()));
	}

	@Inject
	protected static JournalFolderLocalService journalFolderLocalService;

	private JournalFolder _addJournalFolder() throws Exception {
		return journalFolderLocalService.addFolder(
			user.getUserId(), group.getGroupId(), 0,
			RandomTestUtil.randomString(), StringPool.BLANK, serviceContext);
	}

	private JSONObject _getQueryElementJSONObject(long folderId) {
		return JSONUtil.put(
			"category", "hide"
		).put(
			"clauses",
			createJSONArray().put(
				JSONUtil.put(
					"context", "query"
				).put(
					"occur", "filter"
				).put(
					"query",
					JSONUtil.put(
						"query",
						JSONUtil.put(
							"bool",
							JSONUtil.put(
								"must_not",
								createJSONArray().put(
									JSONUtil.put(
										"term",
										JSONUtil.put(
											"folderId",
											JSONUtil.put(
												"value", folderId)))))))
				).put(
					"type", "wrapper"
				))
		).put(
			"conditions", createJSONArray()
		).put(
			"description",
			JSONUtil.put("en_US", "Hide results by an exact term match")
		).put(
			"enabled", true
		).put(
			"icon", "hidden"
		).put(
			"title", JSONUtil.put("en_US", "Hide by an Exact Term Match")
		);
	}

	private String _getSelectedElementString(long folderId) throws Exception {
		JSONObject elementTemplateJSONObject = getElementTemplateJSONObject(
			"/elements/hide-by-exact-term-match-test.json");

		return JSONUtil.put(
			"query_configuration",
			createJSONArray().put(
				JSONUtil.put(
					"elementTemplateJSON",
					elementTemplateJSONObject.get("elementTemplateJSON")
				).put(
					"uiConfigurationJSON",
					elementTemplateJSONObject.get("uiConfigurationJSON")
				).put(
					"uiConfigurationValues",
					JSONUtil.put(
						"field",
						JSONUtil.put(
							"field", "folderId"
						).put(
							"languageIdPosition", -1
						)
					).put(
						"value", folderId
					)
				))
		).toString();
	}

}
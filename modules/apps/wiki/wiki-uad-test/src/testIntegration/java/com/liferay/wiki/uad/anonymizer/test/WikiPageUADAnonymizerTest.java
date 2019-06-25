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

package com.liferay.wiki.uad.anonymizer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.test.util.BaseHasAssetEntryUADAnonymizerTestCase;
import com.liferay.user.associated.data.test.util.WhenHasStatusByUserIdField;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalService;
import com.liferay.wiki.uad.test.WikiPageUADTestHelper;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class WikiPageUADAnonymizerTest
	extends BaseHasAssetEntryUADAnonymizerTestCase<WikiPage>
	implements WhenHasStatusByUserIdField {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	public WikiPage addBaseModelWithStatusByUserId(
			long userId, long statusByUserId)
		throws Exception {

		WikiPage wikiPage =
			_wikiPageUADTestHelper.addWikiPageWithStatusByUserId(
				userId, statusByUserId);

		_wikiPages.add(wikiPage);

		return wikiPage;
	}

	@After
	public void tearDown() throws Exception {
		_wikiPageUADTestHelper.cleanUpDependencies(_wikiPages);
	}

	@Override
	protected WikiPage addBaseModel(long userId) throws Exception {
		return addBaseModel(userId, true);
	}

	@Override
	protected WikiPage addBaseModel(long userId, boolean deleteAfterTestRun)
		throws Exception {

		WikiPage wikiPage = _wikiPageUADTestHelper.addWikiPage(userId);

		if (deleteAfterTestRun) {
			_wikiPages.add(wikiPage);
		}

		return wikiPage;
	}

	@Override
	protected void deleteBaseModels(List<WikiPage> baseModels)
		throws Exception {

		_wikiPageUADTestHelper.cleanUpDependencies(baseModels);
	}

	@Override
	protected UADAnonymizer getUADAnonymizer() {
		return _uadAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {

		WikiPage wikiPage = _wikiPageLocalService.getWikiPage(baseModelPK);

		String userName = wikiPage.getUserName();
		String statusByUserName = wikiPage.getStatusByUserName();

		if ((wikiPage.getUserId() != user.getUserId()) &&
			!userName.equals(user.getFullName()) &&
			(wikiPage.getStatusByUserId() != user.getUserId()) &&
			!statusByUserName.equals(user.getFullName()) &&
			isAssetEntryAutoAnonymized(
				WikiPage.class.getName(), wikiPage.getResourcePrimKey(),
				user)) {

			return true;
		}

		return false;
	}

	@Override
	protected boolean isBaseModelDeleted(long baseModelPK) {
		if (_wikiPageLocalService.fetchWikiPage(baseModelPK) == null) {
			return true;
		}

		return false;
	}

	@Inject(filter = "component.name=*.WikiPageUADAnonymizer")
	private UADAnonymizer _uadAnonymizer;

	@Inject
	private WikiPageLocalService _wikiPageLocalService;

	@DeleteAfterTestRun
	private final List<WikiPage> _wikiPages = new ArrayList<>();

	@Inject
	private WikiPageUADTestHelper _wikiPageUADTestHelper;

}
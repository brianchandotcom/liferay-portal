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

package com.liferay.journal.subscriptions.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.constants.JournalConstants;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
import com.liferay.subscription.test.util.BaseSubscriptionLocalizedContentTestCase;

import javax.portlet.PortletPreferences;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
public class JournalSubscriptionLocalizedContentTest
	extends BaseSubscriptionLocalizedContentTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	@Override
	protected long addBaseModel(long userId, long containerModelId)
		throws Exception {

		JournalArticle article = JournalTestUtil.addArticle(
			userId, group.getGroupId(), containerModelId);

		return article.getResourcePrimKey();
	}

	@Override
	protected void addSubscriptionContainerModel(long containerModelId)
		throws Exception {

		JournalFolderLocalServiceUtil.subscribe(
			user.getUserId(), group.getGroupId(), containerModelId);
	}

	@Override
	protected User addUser() throws Exception {
		return UserTestUtil.addOmniAdminUser();
	}

	@Override
	protected String getPortletId() {
		return JournalPortletKeys.JOURNAL;
	}

	@Override
	protected String getServiceName() {
		return JournalConstants.SERVICE_NAME;
	}

	@Override
	protected String getSubscriptionAddedBodyPreferenceName() {
		return "emailArticleAddedBody";
	}

	@Override
	protected String getSubscriptionUpdatedBodyPreferenceName() {
		return "emailArticleUpdatedBody";
	}

	@Override
	protected void setBaseModelSubscriptionBodyPreferences(
			String bodyPreferenceName)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getStrictPortletSetup(
				layout, getServiceName());

		LocalizationUtil.setPreferencesValue(
			portletPreferences, bodyPreferenceName,
			LocaleUtil.toLanguageId(LocaleUtil.GERMANY), GERMAN_BODY);
		LocalizationUtil.setPreferencesValue(
			portletPreferences, bodyPreferenceName,
			LocaleUtil.toLanguageId(LocaleUtil.SPAIN), SPANISH_BODY);

		PortletPreferencesLocalServiceUtil.updatePreferences(
			group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
			PortletKeys.PREFS_PLID_SHARED, getServiceName(),
			portletPreferences);
	}

	@Override
	protected void updateBaseModel(long userId, long baseModelId)
		throws Exception {

		JournalArticle article =
			JournalArticleLocalServiceUtil.getLatestArticle(baseModelId);

		JournalTestUtil.updateArticleWithWorkflow(userId, article, true);
	}

}
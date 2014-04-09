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

package com.liferay.portlet.journal.action;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.ContentUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurationActionImpl extends DefaultConfigurationAction {

	@Override
	public void postProcess(
			long companyId, PortletRequest portletRequest,
			PortletPreferences portletPreferences)
		throws SystemException {

		String languageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());

		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailArticleAddedBody_" + languageId,
			ContentUtil.get(PropsValues.JOURNAL_EMAIL_ARTICLE_ADDED_BODY));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailArticleAddedSubject_" + languageId,
			ContentUtil.get(PropsValues.JOURNAL_EMAIL_ARTICLE_ADDED_SUBJECT));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailArticleApprovalDeniedBody_" + languageId,
			ContentUtil.get(
				PropsValues.JOURNAL_EMAIL_ARTICLE_APPROVAL_DENIED_BODY));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailArticleApprovalDeniedSubject_" + languageId,
			ContentUtil.get(
				PropsValues.JOURNAL_EMAIL_ARTICLE_APPROVAL_DENIED_SUBJECT));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailArticleApprovalGrantedBody_" + languageId,
			ContentUtil.get(
				PropsValues.JOURNAL_EMAIL_ARTICLE_APPROVAL_GRANTED_BODY));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailArticleApprovalGrantedSubject_" + languageId,
			ContentUtil.get(
				PropsValues.JOURNAL_EMAIL_ARTICLE_APPROVAL_GRANTED_SUBJECT));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailArticleApprovalRequestedBody_" + languageId,
			ContentUtil.get(
				PropsValues.JOURNAL_EMAIL_ARTICLE_APPROVAL_REQUESTED_BODY));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailArticleApprovalRequestedSubject_" + languageId,
			ContentUtil.get(
				PropsValues.JOURNAL_EMAIL_ARTICLE_APPROVAL_REQUESTED_SUBJECT));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailArticleReviewBody_" + languageId,
			ContentUtil.get(PropsValues.JOURNAL_EMAIL_ARTICLE_REVIEW_BODY));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailArticleReviewSubject_" + languageId,
			ContentUtil.get(PropsValues.JOURNAL_EMAIL_ARTICLE_REVIEW_SUBJECT));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailArticleUpdatedBody_" + languageId,
			ContentUtil.get(PropsValues.JOURNAL_EMAIL_ARTICLE_UPDATED_BODY));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailArticleUpdatedSubject_" + languageId,
			ContentUtil.get(PropsValues.JOURNAL_EMAIL_ARTICLE_UPDATED_SUBJECT));
	}

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		validateEmail(actionRequest, "emailArticleAdded");
		validateEmail(actionRequest, "emailArticleApprovalDenied");
		validateEmail(actionRequest, "emailArticleApprovalGranted");
		validateEmail(actionRequest, "emailArticleApprovalRequested");
		validateEmail(actionRequest, "emailArticleReview");
		validateEmail(actionRequest, "emailArticleUpdated");
		validateEmailFrom(actionRequest);

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

}
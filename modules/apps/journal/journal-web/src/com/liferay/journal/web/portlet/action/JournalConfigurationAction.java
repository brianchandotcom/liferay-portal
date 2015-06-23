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

package com.liferay.journal.web.portlet.action;

import com.liferay.journal.configuration.JournalServiceConfigurationUtil;
import com.liferay.journal.configuration.JournalServiceConfigurationValues;
import com.liferay.journal.web.constants.JournalPortletKeys;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.LocaleUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Garcia
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + JournalPortletKeys.JOURNAL},
	service = ConfigurationAction.class
)
public class JournalConfigurationAction extends DefaultConfigurationAction {

	@Override
	public String getJspPath(RenderRequest renderRequest) {
		return "/configuration.jsp";
	}

	@Override
	public void postProcess(
		long companyId, PortletRequest portletRequest,
		PortletPreferences portletPreferences) {

		String languageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());

		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailArticleAddedBody_" + languageId,
			JournalServiceConfigurationUtil.getContent(
				JournalServiceConfigurationValues.EMAIL_ARTICLE_ADDED_BODY));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailArticleAddedSubject_" + languageId,
			JournalServiceConfigurationUtil.getContent(
				JournalServiceConfigurationValues.EMAIL_ARTICLE_ADDED_SUBJECT));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailArticleApprovalDeniedBody_" + languageId,
			JournalServiceConfigurationUtil.getContent(
				JournalServiceConfigurationValues.
					EMAIL_ARTICLE_APPROVAL_DENIED_BODY));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailArticleApprovalDeniedSubject_" + languageId,
			JournalServiceConfigurationUtil.getContent(
				JournalServiceConfigurationValues.
					EMAIL_ARTICLE_APPROVAL_DENIED_SUBJECT));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailArticleApprovalGrantedBody_" + languageId,
			JournalServiceConfigurationUtil.getContent(
				JournalServiceConfigurationValues.
					EMAIL_ARTICLE_APPROVAL_GRANTED_BODY));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailArticleApprovalGrantedSubject_" + languageId,
			JournalServiceConfigurationUtil.getContent(
				JournalServiceConfigurationValues.
					EMAIL_ARTICLE_APPROVAL_GRANTED_SUBJECT));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailArticleApprovalRequestedBody_" + languageId,
			JournalServiceConfigurationUtil.getContent(
				JournalServiceConfigurationValues.
					EMAIL_ARTICLE_APPROVAL_REQUESTED_BODY));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailArticleApprovalRequestedSubject_" + languageId,
			JournalServiceConfigurationUtil.getContent(
				JournalServiceConfigurationValues.
					EMAIL_ARTICLE_APPROVAL_REQUESTED_SUBJECT));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailArticleMovedFromFolderBody_" + languageId,
			JournalServiceConfigurationUtil.getContent(
				JournalServiceConfigurationValues.
					EMAIL_ARTICLE_MOVED_FROM_FOLDER_BODY));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailArticleMovedFromFolderSubject_" + languageId,
			JournalServiceConfigurationUtil.getContent(
				JournalServiceConfigurationValues.
					EMAIL_ARTICLE_MOVED_FROM_FOLDER_SUBJECT));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailArticleMovedToFolderBody_" + languageId,
			JournalServiceConfigurationUtil.getContent(
				JournalServiceConfigurationValues.
					EMAIL_ARTICLE_MOVED_TO_FOLDER_BODY));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailArticleMovedToFolderSubject_" + languageId,
			JournalServiceConfigurationUtil.getContent(
				JournalServiceConfigurationValues.
					EMAIL_ARTICLE_MOVED_TO_FOLDER_SUBJECT));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailArticleReviewBody_" + languageId,
			JournalServiceConfigurationUtil.getContent(
				JournalServiceConfigurationValues.EMAIL_ARTICLE_REVIEW_BODY));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailArticleReviewSubject_" + languageId,
			JournalServiceConfigurationUtil.getContent(
				JournalServiceConfigurationValues.
					EMAIL_ARTICLE_REVIEW_SUBJECT));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailArticleUpdatedBody_" + languageId,
			JournalServiceConfigurationUtil.getContent(
				JournalServiceConfigurationValues.EMAIL_ARTICLE_UPDATED_BODY));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailArticleUpdatedSubject_" + languageId,
			JournalServiceConfigurationUtil.getContent(
				JournalServiceConfigurationValues.
					EMAIL_ARTICLE_UPDATED_SUBJECT));
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

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.journal.web)", unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

}
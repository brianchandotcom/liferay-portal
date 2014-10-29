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

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portlet.journal.DuplicateFeedIdException;
import com.liferay.portlet.journal.FeedContentFieldException;
import com.liferay.portlet.journal.FeedIdException;
import com.liferay.portlet.journal.FeedNameException;
import com.liferay.portlet.journal.FeedTargetLayoutFriendlyUrlException;
import com.liferay.portlet.journal.FeedTargetPortletIdException;
import com.liferay.portlet.journal.NoSuchFeedException;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.service.JournalFeedServiceUtil;
import com.liferay.util.RSSUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Raymond Augé
 */
public class EditFeedAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (Validator.isNull(cmd)) {
			return;
		}

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateFeed(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteFeeds(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof NoSuchFeedException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.journal.error");
			}
			else if (e instanceof DuplicateFeedIdException ||
					 e instanceof FeedContentFieldException ||
					 e instanceof FeedIdException ||
					 e instanceof FeedNameException ||
					 e instanceof FeedTargetLayoutFriendlyUrlException ||
					 e instanceof FeedTargetPortletIdException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		try {
			String cmd = ParamUtil.getString(renderRequest, Constants.CMD);

			if (!cmd.equals(Constants.ADD)) {
				ActionUtil.getFeed(renderRequest);
			}
		}
		catch (NoSuchFeedException nsfe) {

			// Let this slide because the user can manually input a feed id for
			// a new syndicated feed that does not yet exist.

		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward("portlet.journal.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(renderRequest, "portlet.journal.edit_feed"));
	}

	protected void deleteFeeds(ActionRequest actionRequest) throws Exception {
		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		String[] deleteFeedIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "deleteFeedIds"));

		for (int i = 0; i < deleteFeedIds.length; i++) {
			JournalFeedServiceUtil.deleteFeed(groupId, deleteFeedIds[i]);
		}
	}

	protected void updateFeed(ActionRequest actionRequest) throws Exception {
		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		String feedId = ParamUtil.getString(actionRequest, "feedId");
		boolean autoFeedId = ParamUtil.getBoolean(actionRequest, "autoFeedId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		String ddmStructureKey = ParamUtil.getString(
			actionRequest, "ddmStructureKey");
		String ddmTemplateKey = ParamUtil.getString(
			actionRequest, "ddmTemplateKey");
		String ddmRendererTemplateKey = ParamUtil.getString(
			actionRequest, "ddmRendererTemplateKey");
		int delta = ParamUtil.getInteger(actionRequest, "delta");
		String orderByCol = ParamUtil.getString(actionRequest, "orderByCol");
		String orderByType = ParamUtil.getString(actionRequest, "orderByType");
		String targetLayoutFriendlyUrl = ParamUtil.getString(
			actionRequest, "targetLayoutFriendlyUrl");
		String targetPortletId = ParamUtil.getString(
			actionRequest, "targetPortletId");
		String contentField = ParamUtil.getString(
			actionRequest, "contentField");
		String feedType = ParamUtil.getString(
			actionRequest, "feedType", RSSUtil.FEED_TYPE_DEFAULT);

		String feedFormat = RSSUtil.getFeedTypeFormat(feedType);
		double feedVersion = RSSUtil.getFeedTypeVersion(feedType);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			JournalFeed.class.getName(), actionRequest);

		if (cmd.equals(Constants.ADD)) {

			// Add feed

			JournalFeedServiceUtil.addFeed(
				groupId, feedId, autoFeedId, name, description, ddmStructureKey,
				ddmTemplateKey, ddmRendererTemplateKey, delta, orderByCol,
				orderByType, targetLayoutFriendlyUrl, targetPortletId,
				contentField, feedFormat, feedVersion, serviceContext);
		}
		else {

			// Update feed

			JournalFeedServiceUtil.updateFeed(
				groupId, feedId, name, description, ddmStructureKey,
				ddmTemplateKey, ddmRendererTemplateKey, delta, orderByCol,
				orderByType, targetLayoutFriendlyUrl, targetPortletId,
				contentField, feedFormat, feedVersion, serviceContext);
		}
	}

}
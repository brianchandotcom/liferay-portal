/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.NotificationThreadLocal;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ProgressTracker;
import com.liferay.portal.kernel.util.ProgressTrackerThreadLocal;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.wiki.NoSuchNodeException;
import com.liferay.portlet.wiki.service.WikiNodeServiceUtil;
import com.liferay.portlet.wiki.util.WikiCacheThreadLocal;
import com.liferay.portlet.wiki.util.WikiCacheUtil;

import java.io.InputStream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Jorge Ferrer
 */
public class ImportPagesAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		try {
			importPages(actionRequest, actionResponse);

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof NoSuchNodeException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.wiki.error");
			}
			else if (e instanceof PortalException) {
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
			ActionUtil.getNode(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchNodeException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward("portlet.wiki.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(renderRequest, "portlet.wiki.import_pages"));
	}

	protected void importPages(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(actionRequest);

		String importProgressId = ParamUtil.getString(
			uploadPortletRequest, "importProgressId");

		ProgressTracker progressTracker = new ProgressTracker(
			actionRequest, importProgressId);

		ProgressTrackerThreadLocal.setProgressTracker(progressTracker);

		progressTracker.start();

		long nodeId = ParamUtil.getLong(uploadPortletRequest, "nodeId");
		String importer = ParamUtil.getString(uploadPortletRequest, "importer");

		int filesCount = ParamUtil.getInteger(
			uploadPortletRequest, "filesCount", 10);

		InputStream[] inputStreams = new InputStream[filesCount];

		try {
			for (int i = 0; i < filesCount; i++) {
				inputStreams[i] = uploadPortletRequest.getFileAsStream(
					"file" + i);
			}

			NotificationThreadLocal.setEnabled(false);
			WikiCacheThreadLocal.setClearCache(false);

			WikiNodeServiceUtil.importPages(
				nodeId, importer, inputStreams,
				actionRequest.getParameterMap());
		}
		finally {
			for (InputStream inputStream : inputStreams) {
				StreamUtil.cleanUp(inputStream);
			}
		}

		WikiCacheUtil.clearCache(nodeId);

		progressTracker.finish();
	}

}
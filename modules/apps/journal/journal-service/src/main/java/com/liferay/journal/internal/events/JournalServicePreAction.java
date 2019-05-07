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

package com.liferay.journal.internal.events;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.journal.exception.NoSuchArticleException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Julio Camarero
 */
@Component(
	property = "key=servlet.service.events.pre", service = LifecycleAction.class
)
public class JournalServicePreAction extends Action {

	@Override
	public void run(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws ActionException {

		try {
			servicePre(httpServletRequest);
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	public void servicePre(HttpServletRequest httpServletRequest)
		throws PortalException {

		String strutsAction = _portal.getStrutsAction(httpServletRequest);

		if (!strutsAction.equals(_PATH_PORTAL_LAYOUT)) {
			return;
		}

		long mainJournalArticleId = ParamUtil.getLong(
			httpServletRequest, "p_j_a_id");

		if (mainJournalArticleId <= 0) {
			return;
		}

		try {
			JournalArticle mainJournalArticle =
				_journalArticleService.getArticle(mainJournalArticleId);

			AssetEntry layoutAssetEntry = _assetEntryLocalService.getEntry(
				JournalArticle.class.getName(),
				mainJournalArticle.getResourcePrimKey());

			httpServletRequest.setAttribute(
				WebKeys.LAYOUT_ASSET_ENTRY, layoutAssetEntry);
		}
		catch (NoSuchArticleException nsae) {
			if (_log.isWarnEnabled()) {
				_log.warn(nsae.getMessage());
			}
		}
	}

	@Reference(unbind = "-")
	protected void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalArticleService(
		JournalArticleService journalArticleService) {

		_journalArticleService = journalArticleService;
	}

	private static final String _PATH_PORTAL_LAYOUT = "/portal/layout";

	private static final Log _log = LogFactoryUtil.getLog(
		JournalServicePreAction.class);

	private AssetEntryLocalService _assetEntryLocalService;
	private JournalArticleService _journalArticleService;

	@Reference
	private Portal _portal;

}
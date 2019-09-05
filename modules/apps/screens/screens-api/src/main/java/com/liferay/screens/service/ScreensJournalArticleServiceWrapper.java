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

package com.liferay.screens.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ScreensJournalArticleService}.
 *
 * @author José Manuel Navarro
 * @see ScreensJournalArticleService
 * @generated
 */
public class ScreensJournalArticleServiceWrapper
	implements ScreensJournalArticleService,
			   ServiceWrapper<ScreensJournalArticleService> {

	public ScreensJournalArticleServiceWrapper(
		ScreensJournalArticleService screensJournalArticleService) {

		_screensJournalArticleService = screensJournalArticleService;
	}

	@Override
	public String getJournalArticleContent(
			long classPK, java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _screensJournalArticleService.getJournalArticleContent(
			classPK, locale);
	}

	@Override
	public String getJournalArticleContent(
			long classPK, long ddmTemplateId, java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _screensJournalArticleService.getJournalArticleContent(
			classPK, ddmTemplateId, locale);
	}

	@Override
	public String getJournalArticleContent(
			long groupId, String articleId, long ddmTemplateId,
			java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _screensJournalArticleService.getJournalArticleContent(
			groupId, articleId, ddmTemplateId, locale);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _screensJournalArticleService.getOSGiServiceIdentifier();
	}

	@Override
	public ScreensJournalArticleService getWrappedService() {
		return _screensJournalArticleService;
	}

	@Override
	public void setWrappedService(
		ScreensJournalArticleService screensJournalArticleService) {

		_screensJournalArticleService = screensJournalArticleService;
	}

	private ScreensJournalArticleService _screensJournalArticleService;

}
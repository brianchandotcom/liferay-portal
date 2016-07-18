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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ScreensDDLRecordService}.
 *
 * @author José Manuel Navarro
 * @see ScreensDDLRecordService
 * @generated
 */
@ProviderType
public class ScreensDDLRecordServiceWrapper implements ScreensDDLRecordService,
	ServiceWrapper<ScreensDDLRecordService> {
	public ScreensDDLRecordServiceWrapper(
		ScreensDDLRecordService screensDDLRecordService) {
		_screensDDLRecordService = screensDDLRecordService;
	}

	@Override
	public com.liferay.portal.kernel.json.JSONArray getDDLRecords(
		long ddlRecordSetId, java.util.Locale locale, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.lists.model.DDLRecord> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _screensDDLRecordService.getDDLRecords(ddlRecordSetId, locale,
			start, end, obc);
	}

	@Override
	public com.liferay.portal.kernel.json.JSONArray getDDLRecords(
		long ddlRecordSetId, long userId, java.util.Locale locale, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.lists.model.DDLRecord> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _screensDDLRecordService.getDDLRecords(ddlRecordSetId, userId,
			locale, start, end, obc);
	}

	@Override
	public com.liferay.portal.kernel.json.JSONObject getDDLRecord(
		long ddlRecordId, java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _screensDDLRecordService.getDDLRecord(ddlRecordId, locale);
	}

	@Override
	public int getDDLRecordsCount(long ddlRecordSetId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _screensDDLRecordService.getDDLRecordsCount(ddlRecordSetId);
	}

	@Override
	public int getDDLRecordsCount(long ddlRecordSetId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _screensDDLRecordService.getDDLRecordsCount(ddlRecordSetId,
			userId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _screensDDLRecordService.getOSGiServiceIdentifier();
	}

	@Override
	public ScreensDDLRecordService getWrappedService() {
		return _screensDDLRecordService;
	}

	@Override
	public void setWrappedService(
		ScreensDDLRecordService screensDDLRecordService) {
		_screensDDLRecordService = screensDDLRecordService;
	}

	private ScreensDDLRecordService _screensDDLRecordService;
}
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

package com.liferay.message.boards.uad.aggregator;

import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.uad.constants.MBUADConstants;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;

import com.liferay.user.associated.data.aggregator.DynamicQueryUADAggregator;
import com.liferay.user.associated.data.aggregator.UADAggregator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.Serializable;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(immediate = true, property =  {
	"model.class.name=" + MBUADConstants.CLASS_NAME_MB_MESSAGE}, service = UADAggregator.class)
public class MBMessageUADAggregator extends DynamicQueryUADAggregator<MBMessage> {
	@Override
	public String getApplicationName() {
		return MBUADConstants.APPLICATION_NAME;
	}

	@Override
	public MBMessage get(Serializable primaryKey) throws PortalException {
		return _mbMessageLocalService.getMBMessage(Long.valueOf(
				primaryKey.toString()));
	}

	@Override
	protected long doCount(DynamicQuery dynamicQuery) {
		return _mbMessageLocalService.dynamicQueryCount(dynamicQuery);
	}

	@Override
	protected DynamicQuery doGetDynamicQuery() {
		return _mbMessageLocalService.dynamicQuery();
	}

	@Override
	protected List<MBMessage> doGetRange(DynamicQuery dynamicQuery, int start,
		int end) {
		return _mbMessageLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@Override
	protected String[] doGetUserIdFieldNames() {
		return MBUADConstants.USER_ID_FIELD_NAMES_MB_MESSAGE;
	}

	@Reference
	private MBMessageLocalService _mbMessageLocalService;
}
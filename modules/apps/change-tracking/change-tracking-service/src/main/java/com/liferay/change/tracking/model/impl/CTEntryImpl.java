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

package com.liferay.change.tracking.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.change.tracking.model.CTEntryAggregate;
import com.liferay.change.tracking.service.CTEntryAggregateLocalServiceUtil;

import java.util.List;

/**
 * @author Daniel Kocsis
 */
@ProviderType
public class CTEntryImpl extends CTEntryBaseImpl {

	public CTEntryImpl() {
	}

	@Override
	public List<CTEntryAggregate> getCTEntryAggregates() {
		return CTEntryAggregateLocalServiceUtil.getCTEntryCTEntryAggregates(
			getCtEntryId());
	}

	@Override
	public boolean hasCTEntryAggregate() {
		int ctEntryCTEntryAggregatesCount =
			CTEntryAggregateLocalServiceUtil.getCTEntryCTEntryAggregatesCount(
				getCtEntryId());

		if (ctEntryCTEntryAggregatesCount == 0) {
			return false;
		}

		return true;
	}

}
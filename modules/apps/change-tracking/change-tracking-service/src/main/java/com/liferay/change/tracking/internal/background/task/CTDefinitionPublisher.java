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

package com.liferay.change.tracking.internal.background.task;

import com.liferay.change.tracking.engine.exception.CTEntryCollisionCTEngineException;
import com.liferay.change.tracking.internal.util.CTEntryCollisionUtil;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Preston Crary
 */
public class CTDefinitionPublisher implements CTPublisher {

	public CTDefinitionPublisher(
		CTEntryLocalService ctEntryLocalService, boolean ignoreCollision) {

		_ctEntryLocalService = ctEntryLocalService;
		_ignoreCollision = ignoreCollision;
	}

	@Override
	public void addCTEntry(CTEntry ctEntry) {
		_ctEntries.add(ctEntry);
	}

	@Override
	public void publish() throws Exception {
		for (CTEntry ctEntry : _ctEntries) {
			if (ctEntry.isCollision() && !_ignoreCollision) {
				throw new CTEntryCollisionCTEngineException(
					ctEntry.getCompanyId(), ctEntry.getCtEntryId());
			}

			_ctEntryLocalService.updateStatus(
				ctEntry.getCtEntryId(), WorkflowConstants.STATUS_APPROVED);

			CTEntryCollisionUtil.checkCollidingCTEntries(
				_ctEntryLocalService, ctEntry.getCompanyId(),
				ctEntry.getModelClassPK(), ctEntry.getModelResourcePrimKey());
		}
	}

	private final List<CTEntry> _ctEntries = new ArrayList<>();
	private final CTEntryLocalService _ctEntryLocalService;
	private final boolean _ignoreCollision;

}
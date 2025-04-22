/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.segments.internal.cache.SegmentsEntryCacheUtil;
import com.liferay.segments.model.SegmentsEntry;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rachael Koestartyo
 */
@Component(service = ModelListener.class)
public class SegmentsEntryModelListener
	extends BaseModelListener<SegmentsEntry> {

	@Override
	public void onAfterCreate(SegmentsEntry segmentsEntry)
		throws ModelListenerException {

		SegmentsEntryCacheUtil.clear();
	}

	@Override
	public void onAfterUpdate(
			SegmentsEntry originalSegmentsEntry, SegmentsEntry newSegmentsEntry)
		throws ModelListenerException {

		SegmentsEntryCacheUtil.clear();
	}

	@Override
	public void onBeforeRemove(SegmentsEntry segmentsEntry)
		throws ModelListenerException {

		SegmentsEntryCacheUtil.clear();
	}

}
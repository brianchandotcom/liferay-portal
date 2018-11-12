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

package com.liferay.segments.internal.model.listener;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.provider.SegmentsEntryProvider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Garcia
 */
@Component(immediate = true, service = ModelListener.class)
public class SegmentsEntryModelListener
	extends BaseModelListener<SegmentsEntry> {

	@Override
	public void onBeforeRemove(SegmentsEntry segmentsEntry) {
		clearCache(segmentsEntry);
	}

	@Override
	public void onBeforeUpdate(SegmentsEntry segmentsEntry) {
		clearCache(segmentsEntry);
	}

	protected void clearCache(SegmentsEntry segmentsEntry) {
		if (segmentsEntry == null) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Clearing cache for segments entry " +
					segmentsEntry.getSegmentsEntryId());
		}

		try {
			long[] segmentsEntryClassPKs =
				_segmentsEntryProvider.getSegmentsEntryClassPKs(
					segmentsEntry.getSegmentsEntryId());

			for (long segmentsEntryClassPK : segmentsEntryClassPKs) {
				_segmentsEntryProvider.clearCache(
					segmentsEntry.getType(), segmentsEntryClassPK);
			}
		}
		catch (PortalException pe) {
			_log.error(
				"Error while clearing cache for segments entry " +
					segmentsEntry.getSegmentsEntryId(),
				pe);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsEntryModelListener.class);

	@Reference
	private SegmentsEntryProvider _segmentsEntryProvider;

}
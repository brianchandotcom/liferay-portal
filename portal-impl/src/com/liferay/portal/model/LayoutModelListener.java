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

package com.liferay.portal.model;

import com.liferay.exportimport.kernel.staging.LayoutStagingUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutRevisionLocalServiceUtil;
import com.liferay.portal.servlet.filters.cache.CacheUtil;

/**
 * @author Alexander Chow
 * @author Raymond Augé
 */
public class LayoutModelListener extends BaseModelListener<Layout> {

	@Override
	public void onAfterCreate(Layout layout) {
		clearCache(layout);
	}

	@Override
	public void onAfterRemove(Layout layout) {
		clearCache(layout);
	}

	@Override
	public void onAfterUpdate(Layout originalLayout, Layout layout) {
		clearCache(layout);
	}

	@Override
	public void onBeforeRemove(Layout layout) throws ModelListenerException {
		try {
			if ((layout == null) ||
				!LayoutStagingUtil.isBranchingLayout(layout)) {

				return;
			}

			LayoutRevisionLocalServiceUtil.deleteLayoutLayoutRevisions(
				layout.getPlid());
		}
		catch (IllegalStateException illegalStateException) {
			if (_log.isDebugEnabled()) {
				_log.debug(illegalStateException, illegalStateException);
			}

			// This is only needed because of LayoutPersistenceTest but should
			// never happen in a deployed environment

		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
		catch (SystemException systemException) {
			throw new ModelListenerException(systemException);
		}
	}

	protected void clearCache(Layout layout) {
		if (layout == null) {
			return;
		}

		if (!layout.isPrivateLayout()) {
			CacheUtil.clearCache(layout.getCompanyId());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutModelListener.class);

}
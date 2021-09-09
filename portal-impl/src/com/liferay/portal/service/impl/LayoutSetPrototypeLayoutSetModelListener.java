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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.LayoutSetPrototypeUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Date;

/**
 * @author Raymond Augé
 */
public class LayoutSetPrototypeLayoutSetModelListener
	extends BaseModelListener<LayoutSet> {

	@Override
	public void onAfterCreate(LayoutSet layoutSet) {
		updateLayoutSetPrototype(layoutSet, layoutSet.getModifiedDate());
	}

	@Override
	public void onAfterRemove(LayoutSet layoutSet) {
		updateLayoutSetPrototype(layoutSet, new Date());
	}

	@Override
	public void onAfterUpdate(
		LayoutSet originalLayoutSet, LayoutSet layoutSet) {

		updateLayoutSetPrototype(layoutSet, layoutSet.getModifiedDate());
	}

	protected void updateLayoutSetPrototype(
		LayoutSet layoutSet, Date modifiedDate) {

		if (layoutSet == null) {
			return;
		}

		Group group = null;

		try {
			group = layoutSet.getGroup();

			if (!group.isLayoutSetPrototype()) {
				return;
			}
		}
		catch (PortalException portalException) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}

			return;
		}

		try {
			LayoutSetPrototype layoutSetPrototype =
				LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(
					group.getClassPK());

			layoutSetPrototype.setModifiedDate(modifiedDate);

			UnicodeProperties settingsUnicodeProperties =
				layoutSet.getSettingsProperties();

			settingsUnicodeProperties.remove("merge-fail-count");

			LayoutSetPrototypeUtil.update(layoutSetPrototype);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSetPrototypeLayoutSetModelListener.class);

}
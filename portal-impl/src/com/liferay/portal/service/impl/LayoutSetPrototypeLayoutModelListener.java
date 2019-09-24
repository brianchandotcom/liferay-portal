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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.sites.kernel.util.Sites;

import java.util.Date;

/**
 * @author Raymond Augé
 */
public class LayoutSetPrototypeLayoutModelListener
	extends BaseModelListener<Layout> {

	@Override
	public void onAfterCreate(Layout layout) {
		updateLayoutSetPrototype(layout, layout.getModifiedDate());
	}

	@Override
	public void onAfterRemove(Layout layout) {
		updateLayoutSetPrototype(layout, new Date());
	}

	@Override
	public void onAfterUpdate(Layout layout) {
		updateLayoutSetPrototype(layout, layout.getModifiedDate());
	}

	protected void updateLayoutSetPrototype(Layout layout, Date modifiedDate) {
		if (layout == null) {
			return;
		}

		Group group = layout.getGroup();

		if ((group == null) || !group.isLayoutSetPrototype()) {
			return;
		}

		try {
			LayoutSetPrototype layoutSetPrototype =
				LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(
					group.getClassPK());

			layoutSetPrototype.setModifiedDate(modifiedDate);

			LayoutSetPrototypeLocalServiceUtil.updateLayoutSetPrototype(
				layoutSetPrototype);

			LayoutSet layoutSet = layoutSetPrototype.getLayoutSet();

			layoutSet.setModifiedDate(modifiedDate);

			UnicodeProperties settingsProperties =
				layoutSet.getSettingsProperties();

			settingsProperties.remove(Sites.MERGE_FAIL_COUNT);

			LayoutSetLocalServiceUtil.updateLayoutSet(layoutSet);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSetPrototypeLayoutModelListener.class);

}
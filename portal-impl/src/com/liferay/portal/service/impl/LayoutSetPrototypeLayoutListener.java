/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.BaseModelListener;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.persistence.LayoutSetPrototypeUtil;

/**
 * @author Raymond Augé
 */
public class LayoutSetPrototypeLayoutListener
	extends BaseModelListener<Layout> {

	@Override
	public void onAfterCreate(Layout layout) {
		updateLayoutSetPrototype(layout);
	}

	@Override
	public void onAfterRemove(Layout layout) {
		updateLayoutSetPrototype(layout);
	}

	@Override
	public void onAfterUpdate(Layout layout) {
		updateLayoutSetPrototype(layout);
	}

	protected void updateLayoutSetPrototype(Layout layout) {
		try {
			Group group = layout.getGroup();

			if (!group.isLayoutSetPrototype()) {
				return;
			}

			LayoutSetPrototype layoutSetPrototype =
				LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(
					group.getClassPK());

			LayoutSet layoutSet = layoutSetPrototype.getLayoutSet();

			layoutSetPrototype.setModifiedDate(layout.getModifiedDate());

			UnicodeProperties settingsProperties =
				layoutSet.getSettingsProperties();

			settingsProperties.remove("merge-fail-count");

			LayoutSetLocalServiceUtil.updateLayoutSet(layoutSet, false);
			LayoutSetPrototypeUtil.update(layoutSetPrototype, false);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		LayoutSetPrototypeLayoutListener.class);

}
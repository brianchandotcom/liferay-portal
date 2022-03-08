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

package com.liferay.layout.internal.model.listener;

import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.layout.friendly.url.LayoutFriendlyURLEntryHelper;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = ModelListener.class)
public class LayoutModelListener extends BaseModelListener<Layout> {

	@Override
	public void onAfterRemove(Layout layout) {
		if (layout == null) {
			return;
		}

		_friendlyURLEntryLocalService.deleteFriendlyURLEntry(
			layout.getGroupId(),
			_layoutFriendlyURLEntryHelper.getClassNameId(
				layout.isPrivateLayout()),
			layout.getPlid());

		if (layout.isTypeAssetDisplay()) {
			_friendlyURLEntryLocalService.deleteFriendlyURLEntry(
				layout.getGroupId(),
				_layoutFriendlyURLEntryHelper.getClassNameId(
					!layout.isPrivateLayout()),
				layout.getPlid());
		}
	}

	@Override
	public void onBeforeRemove(Layout layout) throws ModelListenerException {
		_layoutClassedModelUsageLocalService.
			deleteLayoutClassedModelUsagesByPlid(layout.getPlid());
	}

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;

	@Reference
	private LayoutFriendlyURLEntryHelper _layoutFriendlyURLEntryHelper;

}
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

package com.liferay.friendly.url.internal.model.listener;

import com.liferay.friendly.url.internal.util.FriendlyURLLayoutUtil;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;

import java.util.Collections;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = ModelListener.class)
public class FriendlyURLLayoutFriendlyURLModelListener
	extends BaseModelListener<LayoutFriendlyURL> {

	@Override
	public void onAfterCreate(LayoutFriendlyURL layoutFriendlyURL)
		throws ModelListenerException {

		_addFriendlyURLEntry(layoutFriendlyURL);
	}

	@Override
	public void onAfterUpdate(LayoutFriendlyURL layoutFriendlyURL)
		throws ModelListenerException {

		_addFriendlyURLEntry(layoutFriendlyURL);
	}

	private void _addFriendlyURLEntry(LayoutFriendlyURL layoutFriendlyURL) {
		try {
			_friendlyURLEntryLocalService.addFriendlyURLEntry(
				layoutFriendlyURL.getGroupId(),
				FriendlyURLLayoutUtil.getLayoutClassNameId(
					layoutFriendlyURL.isPrivateLayout()),
				layoutFriendlyURL.getPlid(),
				Collections.singletonMap(
					layoutFriendlyURL.getLanguageId(),
					layoutFriendlyURL.getFriendlyURL()),
				ServiceContextThreadLocal.getServiceContext());
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

}
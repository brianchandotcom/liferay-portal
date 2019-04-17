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

package com.liferay.message.boards.web.internal.asset.model;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.message.boards.constants.MBPortletKeys;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS,
	service = AssetRendererFactory.class
)
public class MBThreadAssetRendererFactory
	extends BaseAssetRendererFactory<MBThread> {

	public static final String TYPE = "thread";

	public MBThreadAssetRendererFactory() {
		setCategorizable(false);
		setLinkable(false);
		setPortletId(MBPortletKeys.MESSAGE_BOARDS);
		setSearchable(false);
		setSelectable(false);
	}

	@Override
	public AssetRenderer<MBThread> getAssetRenderer(long classPK, int type)
		throws PortalException {

		return new MBThreadAssetRenderer(
			_mbThreadLocalService.getThread(classPK));
	}

	@Override
	public String getClassName() {
		return MBThread.class.getName();
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Reference
	private MBThreadLocalService _mbThreadLocalService;

}
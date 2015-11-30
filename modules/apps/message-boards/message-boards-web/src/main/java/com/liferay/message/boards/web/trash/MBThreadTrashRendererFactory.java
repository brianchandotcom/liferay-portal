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

package com.liferay.message.boards.web.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.trash.TrashRendererFactory;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBThreadLocalService;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.portlet.messageboards.model.MBThread"},
	service = TrashRendererFactory.class
)
public class MBThreadTrashRendererFactory implements TrashRendererFactory {

	@Override
	public TrashRenderer getTrashRenderer(long classPK) throws PortalException {
		MBThread thread = _mbThreadLocalService.getThread(classPK);

		MBThreadTrashRenderer mbThreadTrashRenderer = new MBThreadTrashRenderer(
			thread);

		mbThreadTrashRenderer.setServletContext(_servletContext);

		return mbThreadTrashRenderer;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.message.boards.web)", unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	@Reference(unbind = "-")
	protected void setMBThreadLocalService(
		MBThreadLocalService mbThreadLocalService) {

		_mbThreadLocalService = mbThreadLocalService;
	}

	private volatile MBThreadLocalService _mbThreadLocalService;
	private volatile ServletContext _servletContext;

}
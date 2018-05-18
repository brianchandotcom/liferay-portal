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

package com.liferay.commerce.order.web.internal.display.context;

import com.liferay.commerce.model.CommerceOrderNote;
import com.liferay.commerce.service.CommerceOrderNoteService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.RenderRequest;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceOrderNoteEditDisplayContext {

	public CommerceOrderNoteEditDisplayContext(
			CommerceOrderNoteService commerceOrderNoteService,
			RenderRequest renderRequest)
		throws PortalException {

		long commerceOrderNoteId = ParamUtil.getLong(
			renderRequest, "commerceOrderNoteId");

		_commerceOrderNote = commerceOrderNoteService.getCommerceOrderNote(
			commerceOrderNoteId);
	}

	public CommerceOrderNote getCommerceOrderNote() {
		return _commerceOrderNote;
	}

	private final CommerceOrderNote _commerceOrderNote;

}
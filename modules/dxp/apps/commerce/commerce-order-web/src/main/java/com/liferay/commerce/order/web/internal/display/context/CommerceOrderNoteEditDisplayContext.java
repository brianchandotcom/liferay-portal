/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
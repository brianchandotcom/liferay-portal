/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.CommerceOrderAttachmentAdminFDSActionContributor;
import com.liferay.commerce.order.web.internal.display.context.CommerceOrderAttachmentsDisplayContext;
import com.liferay.commerce.order.web.internal.display.context.CommerceOrderEditDisplayContext;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Tancredi Covioli
 */
@Component(
	property = "screen.navigation.entry.order:Integer=45",
	service = ScreenNavigationEntry.class
)
public class CommerceOrderAttachmentsScreenNavigationEntry
	extends CommerceOrderAttachmentsScreenNavigationCategory
	implements ScreenNavigationEntry<CommerceOrder> {

	@Override
	public String getEntryKey() {
		return getCategoryKey();
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		CommerceOrderEditDisplayContext commerceOrderEditDisplayContext =
			(CommerceOrderEditDisplayContext)httpServletRequest.getAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT);

		CommerceOrder commerceOrder =
			commerceOrderEditDisplayContext.getCommerceOrder();

		CommerceOrderAttachmentsDisplayContext
			commerceOrderAttachmentsDisplayContext =
				new CommerceOrderAttachmentsDisplayContext(
					commerceOrder,
					_commerceOrderAttachmentAdminFDSActionContributors,
					httpServletRequest, _language);

		httpServletRequest.setAttribute(
			CommerceOrderAttachmentsDisplayContext.class.getName(),
			commerceOrderAttachmentsDisplayContext);

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse,
			"/commerce_order/attachments.jsp");
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private List<CommerceOrderAttachmentAdminFDSActionContributor>
		_commerceOrderAttachmentAdminFDSActionContributors;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Language _language;

}
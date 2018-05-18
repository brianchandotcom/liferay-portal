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

package com.liferay.commerce.price.list.web.internal.servlet.taglib.ui;

import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BaseJSPFormNavigatorEntry;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "form.navigator.entry.order:Integer=100",
	service = FormNavigatorEntry.class
)
public class CommercePriceEntryDetailsFormNavigatorEntry
	extends BaseJSPFormNavigatorEntry<CommercePriceEntry> {

	@Override
	public String getCategoryKey() {
		return CommercePriceEntryFormNavigatorConstants.
			CATEGORY_KEY_COMMERCE_PRICE_ENTRY_DETAILS;
	}

	@Override
	public String getFormNavigatorId() {
		return CommercePriceEntryFormNavigatorConstants.
			FORM_NAVIGATOR_ID_COMMERCE_PRICE_ENTRY;
	}

	@Override
	public String getKey() {
		return "details";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "details");
	}

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(getJspPath());

		try {
			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (ServletException se) {
			throw new IOException("Unable to include " + getJspPath(), se);
		}
	}

	@Override
	protected String getJspPath() {
		return "/price_entry/details.jsp";
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.price.list.web)"
	)
	private ServletContext _servletContext;

}
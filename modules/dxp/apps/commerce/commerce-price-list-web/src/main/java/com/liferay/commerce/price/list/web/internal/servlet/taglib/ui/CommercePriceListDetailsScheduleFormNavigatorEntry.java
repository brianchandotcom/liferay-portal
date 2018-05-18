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

import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BaseJSPFormNavigatorEntry;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "form.navigator.entry.order:Integer=10",
	service = FormNavigatorEntry.class
)
public class CommercePriceListDetailsScheduleFormNavigatorEntry
	extends BaseJSPFormNavigatorEntry<CommercePriceList> {

	@Override
	public String getCategoryKey() {
		return CommercePriceListFormNavigatorConstants.
			CATEGORY_KEY_COMMERCE_PRICE_LIST_DETAILS;
	}

	@Override
	public String getFormNavigatorId() {
		return CommercePriceListFormNavigatorConstants.
			FORM_NAVIGATOR_ID_COMMERCE_PRICE_LIST;
	}

	@Override
	public String getKey() {
		return "schedule";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "schedule");
	}

	@Override
	protected String getJspPath() {
		return "/price_list/schedule.jsp";
	}

}
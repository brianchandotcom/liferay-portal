/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.layout.display.page;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Locale;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderLayoutDisplayPageObjectProvider
	implements LayoutDisplayPageObjectProvider<CommerceOrder> {

	public CommerceOrderLayoutDisplayPageObjectProvider(
		CommerceOrder commerceOrder, long groupId) {

		_commerceOrder = commerceOrder;
		_groupId = groupId;
	}

	@Override
	public String getClassName() {
		return CommerceOrder.class.getName();
	}

	@Override
	public long getClassNameId() {
		return PortalUtil.getClassNameId(CommerceOrder.class);
	}

	@Override
	public long getClassPK() {
		return _commerceOrder.getCommerceOrderId();
	}

	@Override
	public long getClassTypeId() {
		return 0;
	}

	@Override
	public String getDescription(Locale locale) {
		return StringPool.BLANK;
	}

	@Override
	public CommerceOrder getDisplayObject() {
		return _commerceOrder;
	}

	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public String getKeywords(Locale locale) {
		return StringPool.BLANK;
	}

	@Override
	public String getTitle(Locale locale) {
		return String.valueOf(_commerceOrder.getCommerceOrderId());
	}

	@Override
	public String getURLTitle(Locale locale) {
		return String.valueOf(_commerceOrder.getCommerceOrderId());
	}

	private final CommerceOrder _commerceOrder;
	private final long _groupId;

}
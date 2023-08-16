/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.admin.web.internal.frontend.taglib.clay.servlet.taglib;

import com.liferay.frontend.taglib.clay.servlet.taglib.BaseHorizontalCard;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.model.BaseModel;

import javax.portlet.RenderRequest;

/**
 * @author Yurena Cabrera
 */
public class DisplayPageTemplateCollectionHorizontalCard
	extends BaseHorizontalCard {

	public DisplayPageTemplateCollectionHorizontalCard(
		BaseModel<?> baseModel, RenderRequest renderRequest,
		RowChecker rowChecker) {

		super(baseModel, renderRequest, rowChecker);

		_layoutPageTemplateCollection = (LayoutPageTemplateCollection)baseModel;
	}

	@Override
	public String getIcon() {
		return "folder";
	}

	@Override
	public String getTitle() {
		return _layoutPageTemplateCollection.getName();
	}

	private final LayoutPageTemplateCollection _layoutPageTemplateCollection;

}
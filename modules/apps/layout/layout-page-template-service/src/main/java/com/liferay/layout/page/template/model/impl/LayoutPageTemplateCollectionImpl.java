/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.model.impl;

import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutPageTemplateCollectionImpl
	extends LayoutPageTemplateCollectionBaseImpl {

	@Override
	public LayoutPageTemplateCollection getAncestor() {
		if (getParentLayoutPageTemplateCollectionId() ==
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT) {

			return null;
		}

		return LayoutPageTemplateCollectionLocalServiceUtil.
			fetchLayoutPageTemplateCollection(
				getParentLayoutPageTemplateCollectionId());
	}

	@Override
	public List<LayoutPageTemplateCollection> getAncestors() {
		List<LayoutPageTemplateCollection> layoutPageTemplateCollections =
			new ArrayList<>();

		LayoutPageTemplateCollection layoutPageTemplateCollection = this;

		while (layoutPageTemplateCollection != null) {
			layoutPageTemplateCollections.add(layoutPageTemplateCollection);

			layoutPageTemplateCollection =
				layoutPageTemplateCollection.getAncestor();
		}

		return layoutPageTemplateCollections;
	}

}
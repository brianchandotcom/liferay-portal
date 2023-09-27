/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.model.listener;

import com.liferay.commerce.product.model.CPDisplayLayout;
import com.liferay.commerce.product.service.CPDisplayLayoutLocalService;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = ModelListener.class)
public class LayoutPageTemplateEntryModelListener
	extends BaseModelListener<LayoutPageTemplateEntry> {

	@Override
	public void onAfterRemove(LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws ModelListenerException {

		if (layoutPageTemplateEntry == null) {
			return;
		}

		List<CPDisplayLayout> cpDisplayLayouts =
			_cpDisplayLayoutLocalService.
				getCPDisplayLayoutsByGroupIdAndLayoutPageTemplateEntryUuid(
					layoutPageTemplateEntry.getGroupId(),
					layoutPageTemplateEntry.getUuid());

		for (CPDisplayLayout cpDisplayLayout : cpDisplayLayouts) {
			_cpDisplayLayoutLocalService.deleteCPDisplayLayout(cpDisplayLayout);
		}
	}

	@Reference
	private CPDisplayLayoutLocalService _cpDisplayLayoutLocalService;

}
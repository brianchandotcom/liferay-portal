/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.model.listener;

import com.liferay.commerce.product.model.CPDisplayLayout;
import com.liferay.commerce.product.service.CPDisplayLayoutLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ModelListener;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(service = ModelListener.class)
public class LayoutModelListener extends BaseModelListener<Layout> {

	@Override
	public void onAfterRemove(Layout layout) throws ModelListenerException {
		if (layout == null) {
			return;
		}

		List<CPDisplayLayout> cpDisplayLayouts =
			_cpDisplayLayoutLocalService.
				getCPDisplayLayoutsByGroupIdAndLayoutUuid(
					layout.getGroupId(), layout.getUuid());

		for (CPDisplayLayout cpDisplayLayout : cpDisplayLayouts) {
			_cpDisplayLayoutLocalService.deleteCPDisplayLayout(cpDisplayLayout);
		}
	}

	@Reference
	private CPDisplayLayoutLocalService _cpDisplayLayoutLocalService;

}
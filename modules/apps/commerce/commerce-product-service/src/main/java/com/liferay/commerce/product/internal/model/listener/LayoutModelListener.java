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
@Component(enabled = false, immediate = true, service = ModelListener.class)
public class LayoutModelListener extends BaseModelListener<Layout> {

	@Override
	public void onAfterRemove(Layout layout) throws ModelListenerException {
		if (layout == null) {
			return;
		}

		List<CPDisplayLayout> cpDisplayLayouts =
			_cpDisplayLayoutLocalService.
				fetchCPDisplayLayoutByGroupIdAndLayoutUuid(
					layout.getGroupId(), layout.getUuid());

		for (CPDisplayLayout cpDisplayLayout : cpDisplayLayouts) {
			_cpDisplayLayoutLocalService.deleteCPDisplayLayout(cpDisplayLayout);
		}
	}

	@Reference
	private CPDisplayLayoutLocalService _cpDisplayLayoutLocalService;

}
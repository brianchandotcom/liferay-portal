/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.model.listener;

import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.staging.model.listener.StagingModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = ModelListener.class)
public class LayoutPageTemplateCollectionStagingModelListener
	extends BaseModelListener<LayoutPageTemplateCollection> {

	@Override
	public void onAfterCreate(
			LayoutPageTemplateCollection layoutPageTemplateCollection)
		throws ModelListenerException {

		_stagingModelListener.onAfterCreate(layoutPageTemplateCollection);
	}

	@Override
	public void onAfterRemove(
			LayoutPageTemplateCollection layoutPageTemplateCollection)
		throws ModelListenerException {

		_stagingModelListener.onAfterRemove(layoutPageTemplateCollection);
	}

	@Override
	public void onAfterUpdate(
			LayoutPageTemplateCollection originalLayoutPageTemplateCollection,
			LayoutPageTemplateCollection layoutPageTemplateCollection)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(layoutPageTemplateCollection);
	}

	@Reference
	private StagingModelListener<LayoutPageTemplateCollection>
		_stagingModelListener;

}
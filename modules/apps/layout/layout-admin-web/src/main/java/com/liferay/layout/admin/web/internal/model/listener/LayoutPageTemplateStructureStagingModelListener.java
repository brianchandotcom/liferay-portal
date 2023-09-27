/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.model.listener;

import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
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
public class LayoutPageTemplateStructureStagingModelListener
	extends BaseModelListener<LayoutPageTemplateStructure> {

	@Override
	public void onAfterCreate(
			LayoutPageTemplateStructure layoutPageTemplateStructure)
		throws ModelListenerException {

		_stagingModelListener.onAfterCreate(layoutPageTemplateStructure);
	}

	@Override
	public void onAfterRemove(
			LayoutPageTemplateStructure layoutPageTemplateStructure)
		throws ModelListenerException {

		_stagingModelListener.onAfterRemove(layoutPageTemplateStructure);
	}

	@Override
	public void onAfterUpdate(
			LayoutPageTemplateStructure originalLayoutPageTemplateStructure,
			LayoutPageTemplateStructure layoutPageTemplateStructure)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(layoutPageTemplateStructure);
	}

	@Reference
	private StagingModelListener<LayoutPageTemplateStructure>
		_stagingModelListener;

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.model.listener;

import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
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
public class LayoutPageTemplateEntryStagingModelListener
	extends BaseModelListener<LayoutPageTemplateEntry> {

	@Override
	public void onAfterCreate(LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws ModelListenerException {

		_stagingModelListener.onAfterCreate(layoutPageTemplateEntry);
	}

	@Override
	public void onAfterRemove(LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws ModelListenerException {

		_stagingModelListener.onAfterRemove(layoutPageTemplateEntry);
	}

	@Override
	public void onAfterUpdate(
			LayoutPageTemplateEntry originalLayoutPageTemplateEntry,
			LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(layoutPageTemplateEntry);
	}

	@Reference
	private StagingModelListener<LayoutPageTemplateEntry> _stagingModelListener;

}
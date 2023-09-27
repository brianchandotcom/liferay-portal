/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.template.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.staging.model.listener.StagingModelListener;
import com.liferay.template.model.TemplateEntry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = ModelListener.class)
public class TemplateEntryStagingModelListener
	extends BaseModelListener<TemplateEntry> {

	@Override
	public void onAfterCreate(TemplateEntry templateEntry)
		throws ModelListenerException {

		_stagingModelListener.onAfterCreate(templateEntry);
	}

	@Override
	public void onAfterRemove(TemplateEntry templateEntry)
		throws ModelListenerException {

		_stagingModelListener.onAfterRemove(templateEntry);
	}

	@Override
	public void onAfterUpdate(
			TemplateEntry originalTemplateEntry, TemplateEntry templateEntry)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(templateEntry);
	}

	@Reference
	private StagingModelListener<TemplateEntry> _stagingModelListener;

}
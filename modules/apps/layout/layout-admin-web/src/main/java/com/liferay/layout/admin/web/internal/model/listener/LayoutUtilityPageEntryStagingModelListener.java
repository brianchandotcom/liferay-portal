/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.model.listener;

import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.staging.model.listener.StagingModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = ModelListener.class)
public class LayoutUtilityPageEntryStagingModelListener
	extends BaseModelListener<LayoutUtilityPageEntry> {

	@Override
	public void onAfterCreate(LayoutUtilityPageEntry layoutUtilityPageEntry)
		throws ModelListenerException {

		_stagingModelListener.onAfterCreate(layoutUtilityPageEntry);
	}

	@Override
	public void onAfterRemove(LayoutUtilityPageEntry layoutUtilityPageEntry)
		throws ModelListenerException {

		_stagingModelListener.onAfterRemove(layoutUtilityPageEntry);
	}

	@Override
	public void onAfterUpdate(
			LayoutUtilityPageEntry originalLayoutUtilityPageEntry,
			LayoutUtilityPageEntry layoutUtilityPageEntry)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(layoutUtilityPageEntry);
	}

	@Reference
	private StagingModelListener<LayoutUtilityPageEntry> _stagingModelListener;

}
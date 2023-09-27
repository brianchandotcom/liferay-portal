/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.web.internal.model.listener;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.staging.model.listener.StagingModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(service = ModelListener.class)
public class DDMStructureStagingModelListener
	extends BaseModelListener<DDMStructure> {

	@Override
	public void onAfterCreate(DDMStructure ddmStructure)
		throws ModelListenerException {

		_stagingModelListener.onAfterCreate(ddmStructure);
	}

	@Override
	public void onAfterRemove(DDMStructure ddmStructure)
		throws ModelListenerException {

		_stagingModelListener.onAfterRemove(ddmStructure);
	}

	@Override
	public void onAfterUpdate(
			DDMStructure originalDDMStructure, DDMStructure ddmStructure)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(ddmStructure);
	}

	@Reference
	private StagingModelListener<DDMStructure> _stagingModelListener;

}
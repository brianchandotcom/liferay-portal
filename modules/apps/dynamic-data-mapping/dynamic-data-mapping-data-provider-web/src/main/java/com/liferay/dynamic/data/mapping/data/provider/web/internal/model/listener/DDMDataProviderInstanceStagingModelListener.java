/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.data.provider.web.internal.model.listener;

import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
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
public class DDMDataProviderInstanceStagingModelListener
	extends BaseModelListener<DDMDataProviderInstance> {

	@Override
	public void onAfterCreate(DDMDataProviderInstance ddmDataProviderInstance)
		throws ModelListenerException {

		_stagingModelListener.onAfterCreate(ddmDataProviderInstance);
	}

	@Override
	public void onAfterRemove(DDMDataProviderInstance ddmDataProviderInstance)
		throws ModelListenerException {

		_stagingModelListener.onAfterRemove(ddmDataProviderInstance);
	}

	@Override
	public void onAfterUpdate(
			DDMDataProviderInstance originalDDMDataProviderInstance,
			DDMDataProviderInstance ddmDataProviderInstance)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(ddmDataProviderInstance);
	}

	@Reference
	private StagingModelListener<DDMDataProviderInstance> _stagingModelListener;

}
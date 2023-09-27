/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.internal.model.listener;

import com.liferay.depot.model.DepotEntryGroupRel;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.staging.model.listener.StagingModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = ModelListener.class)
public class DepotEntryGroupRelStagingModelListener
	extends BaseModelListener<DepotEntryGroupRel> {

	@Override
	public void onAfterCreate(DepotEntryGroupRel depotEntryGroupRel)
		throws ModelListenerException {

		_stagingModelListener.onAfterCreate(depotEntryGroupRel);
	}

	@Override
	public void onAfterRemove(DepotEntryGroupRel depotEntryGroupRel)
		throws ModelListenerException {

		_stagingModelListener.onAfterRemove(depotEntryGroupRel);
	}

	@Override
	public void onAfterUpdate(
			DepotEntryGroupRel originalDepotEntryGroupRel,
			DepotEntryGroupRel depotEntryGroupRel)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(depotEntryGroupRel);
	}

	@Reference
	private StagingModelListener<DepotEntryGroupRel> _stagingModelListener;

}
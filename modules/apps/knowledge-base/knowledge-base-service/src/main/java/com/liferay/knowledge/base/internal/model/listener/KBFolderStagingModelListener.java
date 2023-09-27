/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.model.listener;

import com.liferay.knowledge.base.model.KBFolder;
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
public class KBFolderStagingModelListener extends BaseModelListener<KBFolder> {

	@Override
	public void onAfterCreate(KBFolder kbFolder) throws ModelListenerException {
		_stagingModelListener.onAfterCreate(kbFolder);
	}

	@Override
	public void onAfterRemove(KBFolder kbFolder) throws ModelListenerException {
		_stagingModelListener.onAfterRemove(kbFolder);
	}

	@Override
	public void onAfterUpdate(KBFolder originalKBFolder, KBFolder kbFolder)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(kbFolder);
	}

	@Reference
	private StagingModelListener<KBFolder> _stagingModelListener;

}
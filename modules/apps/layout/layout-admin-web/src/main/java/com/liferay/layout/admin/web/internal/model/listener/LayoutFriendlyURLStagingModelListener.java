/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.staging.model.listener.StagingModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(service = ModelListener.class)
public class LayoutFriendlyURLStagingModelListener
	extends BaseModelListener<LayoutFriendlyURL> {

	@Override
	public void onAfterCreate(LayoutFriendlyURL layoutFriendlyURL)
		throws ModelListenerException {

		_stagingModelListener.onAfterCreate(layoutFriendlyURL);
	}

	@Override
	public void onAfterRemove(LayoutFriendlyURL layoutFriendlyURL)
		throws ModelListenerException {

		_stagingModelListener.onAfterRemove(layoutFriendlyURL);
	}

	@Override
	public void onAfterUpdate(
			LayoutFriendlyURL originalLayoutFriendlyURL,
			LayoutFriendlyURL layoutFriendlyURL)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(layoutFriendlyURL);
	}

	@Reference
	private StagingModelListener<LayoutFriendlyURL> _stagingModelListener;

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.model.listener;

import com.liferay.fragment.model.FragmentCollection;
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
public class FragmentCollectionStagingModelListener
	extends BaseModelListener<FragmentCollection> {

	@Override
	public void onAfterCreate(FragmentCollection fragmentCollection)
		throws ModelListenerException {

		_stagingModelListener.onAfterCreate(fragmentCollection);
	}

	@Override
	public void onAfterRemove(FragmentCollection fragmentCollection)
		throws ModelListenerException {

		_stagingModelListener.onAfterRemove(fragmentCollection);
	}

	@Override
	public void onAfterUpdate(
			FragmentCollection originalFragmentCollection,
			FragmentCollection fragmentCollection)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(fragmentCollection);
	}

	@Reference
	private StagingModelListener<FragmentCollection> _stagingModelListener;

}
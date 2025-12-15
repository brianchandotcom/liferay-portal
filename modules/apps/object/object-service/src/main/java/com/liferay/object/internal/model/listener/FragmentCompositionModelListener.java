/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.model.listener;

import com.liferay.fragment.model.FragmentComposition;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carolina Barbosa
 */
@Component(service = ModelListener.class)
public class FragmentCompositionModelListener
	extends BaseModelListener<FragmentComposition> {

	@Override
	public void onBeforeCreate(FragmentComposition fragmentComposition)
		throws ModelListenerException {

		fragmentComposition.setData(
			updateObjectDefinitionReferences(fragmentComposition.getData()));
	}

}
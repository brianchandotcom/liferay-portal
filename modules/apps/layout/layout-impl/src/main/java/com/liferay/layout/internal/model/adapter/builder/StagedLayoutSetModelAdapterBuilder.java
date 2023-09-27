/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.model.adapter.builder;

import com.liferay.layout.internal.model.adapter.StagedLayoutSetImpl;
import com.liferay.layout.set.model.adapter.StagedLayoutSet;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.adapter.builder.ModelAdapterBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Máté Thurzó
 */
@Component(service = ModelAdapterBuilder.class)
public class StagedLayoutSetModelAdapterBuilder
	implements ModelAdapterBuilder<LayoutSet, StagedLayoutSet> {

	@Override
	public StagedLayoutSet build(LayoutSet layoutSet) {
		return new StagedLayoutSetImpl(layoutSet);
	}

}
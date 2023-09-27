/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.staging.internal.model.adapter.builder;

import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.adapter.StagedTheme;
import com.liferay.portal.kernel.model.adapter.builder.ModelAdapterBuilder;
import com.liferay.portal.model.adapter.impl.StagedThemeImpl;

import org.osgi.service.component.annotations.Component;

/**
 * @author Máté Thurzó
 */
@Component(service = ModelAdapterBuilder.class)
public class StagedThemeModelAdapterBuilder
	implements ModelAdapterBuilder<Theme, StagedTheme> {

	@Override
	public StagedTheme build(Theme theme) {
		return new StagedThemeImpl(theme);
	}

}
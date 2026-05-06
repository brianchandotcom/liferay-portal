/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.site.generator.web.internal.frontend.data.set.view;

import com.liferay.content.site.generator.web.internal.constants.ContentSiteGeneratorFDSNames;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.cards.BaseCardsFDSView;
import com.liferay.frontend.data.set.view.cards.FDSCardSchema;
import com.liferay.frontend.data.set.view.cards.FDSCardSchemaBuilderFactory;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mylena Monte
 */
@Component(
	property = "frontend.data.set.name=" + ContentSiteGeneratorFDSNames.CONTENT_SITE_GENERATOR,
	service = FDSView.class
)
public class ContentSiteGeneratorCardsFDSView extends BaseCardsFDSView {

	@Override
	public String getDescription() {
		return "runStatus.name";
	}

	@Override
	public FDSCardSchema getFDSCardSchema(Locale locale) {
		return _fdsCardSchemaBuilderFactory.create(
		).build();
	}

	@Override
	public String getTitle() {
		return "name";
	}

	@Reference
	private FDSCardSchemaBuilderFactory _fdsCardSchemaBuilderFactory;

}
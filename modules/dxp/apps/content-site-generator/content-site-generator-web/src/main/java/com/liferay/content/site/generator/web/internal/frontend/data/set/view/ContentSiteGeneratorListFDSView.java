/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.site.generator.web.internal.frontend.data.set.view;

import com.liferay.content.site.generator.web.internal.constants.ContentSiteGeneratorFDSNames;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.list.BaseListFDSView;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mylena Monte
 */
@Component(
	property = "frontend.data.set.name=" + ContentSiteGeneratorFDSNames.CONTENT_SITE_GENERATOR,
	service = FDSView.class
)
public class ContentSiteGeneratorListFDSView extends BaseListFDSView {

	@Override
	public String getDescription() {
		return "runStatus.name";
	}

	@Override
	public String getTitle() {
		return "name";
	}

}
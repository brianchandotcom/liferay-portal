/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author David Truong
 */
@Component(service = CTDisplayRenderer.class)
public class FragmentEntryCTDisplayRender
	extends BaseCTDisplayRenderer<FragmentEntry> {

	@Override
	public Class<FragmentEntry> getModelClass() {
		return FragmentEntry.class;
	}

	@Override
	public String getTitle(Locale locale, FragmentEntry fragmentEntry)
		throws PortalException {

		return fragmentEntry.getName();
	}

	@Override
	protected void buildDisplay(DisplayBuilder<FragmentEntry> displayBuilder) {
		FragmentEntry fragmentEntry = displayBuilder.getModel();

		displayBuilder.display(
			"name", fragmentEntry.getName()
		).display(
			"create-date", fragmentEntry.getCreateDate()
		).display(
			"modified-date", fragmentEntry.getModifiedDate()
		).display(
			"css", fragmentEntry.getCss(), true, true
		).display(
			"html", fragmentEntry.getHtml(), true, true
		).display(
			"js", fragmentEntry.getJs(), true, true
		);
	}

}
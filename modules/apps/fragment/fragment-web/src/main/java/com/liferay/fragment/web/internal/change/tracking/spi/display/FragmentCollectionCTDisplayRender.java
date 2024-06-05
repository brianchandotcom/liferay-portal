/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author David Truong
 */
@Component(service = CTDisplayRenderer.class)
public class FragmentCollectionCTDisplayRender
	extends BaseCTDisplayRenderer<FragmentCollection> {

	@Override
	public Class<FragmentCollection> getModelClass() {
		return FragmentCollection.class;
	}

	@Override
	public String getTitle(Locale locale, FragmentCollection fragmentCollection)
		throws PortalException {

		return fragmentCollection.getName();
	}

	@Override
	protected void buildDisplay(
		DisplayBuilder<FragmentCollection> displayBuilder) {

		FragmentCollection fragmentCollection = displayBuilder.getModel();

		displayBuilder.display(
			"name", fragmentCollection.getName()
		).display(
			"create-date", fragmentCollection.getCreateDate()
		).display(
			"modified-date", fragmentCollection.getModifiedDate()
		);
	}

}
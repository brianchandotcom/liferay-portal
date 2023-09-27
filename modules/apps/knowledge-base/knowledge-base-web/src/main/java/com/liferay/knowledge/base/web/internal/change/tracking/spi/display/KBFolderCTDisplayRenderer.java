/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Vy Bui
 */
@Component(service = CTDisplayRenderer.class)
public class KBFolderCTDisplayRenderer extends BaseCTDisplayRenderer<KBFolder> {

	@Override
	public Class<KBFolder> getModelClass() {
		return KBFolder.class;
	}

	@Override
	public String getTitle(Locale locale, KBFolder kbFolder)
		throws PortalException {

		return kbFolder.getName();
	}

	@Override
	protected void buildDisplay(DisplayBuilder<KBFolder> displayBuilder) {
		KBFolder kbFolder = displayBuilder.getModel();

		displayBuilder.display(
			"name", kbFolder.getName()
		).display(
			"description", kbFolder.getDescription()
		).display(
			"created-by",
			() -> {
				String userName = kbFolder.getUserName();

				if (Validator.isNotNull(userName)) {
					return userName;
				}

				return null;
			}
		).display(
			"create-date", kbFolder.getCreateDate()
		).display(
			"last-modified", kbFolder.getModifiedDate()
		);
	}

}
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
	public String getTitle(Locale locale, KBFolder model)
		throws PortalException {

		return model.getName();
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
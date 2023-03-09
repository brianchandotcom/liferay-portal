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
import com.liferay.knowledge.base.model.KBTemplate;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Vy Bui
 */
@Component(service = CTDisplayRenderer.class)
public class KBTemplateCTDisplayRenderer
	extends BaseCTDisplayRenderer<KBTemplate> {

	@Override
	public Class<KBTemplate> getModelClass() {
		return KBTemplate.class;
	}

	@Override
	public String getTitle(Locale locale, KBTemplate kbTemplate)
		throws PortalException {

		return kbTemplate.getTitle();
	}

}
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

package com.liferay.dynamic.data.mapping.internal.storage;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.dynamic.data.mapping.storage.FieldRenderer;
import com.liferay.dynamic.data.mapping.storage.FieldRendererFactory;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.LayoutService;

import java.util.HashMap;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = {})
public class FieldRendererRegistrar {

	@Activate
	protected void activate() {
		_fieldRendererFactory.setFieldRenderers(
			new HashMap<String, FieldRenderer>() {
				{
					put("date", new DateFieldRenderer(_language));
					put(
						"document-library",
						new DocumentLibraryFieldRenderer(
							_dlAppService, _jsonFactory, _language));
					put(
						"geolocation",
						new GeolocationFieldRenderer(_jsonFactory, _language));
					put(
						"link-to-page",
						new LinkToPageFieldRenderer(
							_jsonFactory, _language, _layoutService));
					put("string", new StringFieldRenderer(_jsonFactory));
				}
			});
	}

	@Deactivate
	protected void deactivate() {
		_fieldRendererFactory.setFieldRenderers(null);
	}

	@Reference
	private DLAppService _dlAppService;

	private final FieldRendererFactory _fieldRendererFactory =
		new FieldRendererFactory();

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private LayoutService _layoutService;

}
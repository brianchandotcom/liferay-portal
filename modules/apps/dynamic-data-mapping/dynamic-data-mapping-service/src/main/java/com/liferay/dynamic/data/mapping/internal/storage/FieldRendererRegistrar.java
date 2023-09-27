/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.storage;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.dynamic.data.mapping.storage.FieldRenderer;
import com.liferay.dynamic.data.mapping.storage.FieldRendererFactory;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.util.HashMapBuilder;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = {})
public class FieldRendererRegistrar {

	@Activate
	protected void activate() {
		_fieldRendererFactory.setFieldRenderers(
			HashMapBuilder.<String, FieldRenderer>put(
				"date", new DateFieldRenderer(_language)
			).put(
				"document-library",
				new DocumentLibraryFieldRenderer(
					_dlAppService, _jsonFactory, _language)
			).put(
				"geolocation",
				new GeolocationFieldRenderer(_jsonFactory, _language)
			).put(
				"link-to-page",
				new LinkToPageFieldRenderer(
					_jsonFactory, _language, _layoutService)
			).put(
				"string", new StringFieldRenderer(_jsonFactory)
			).build());
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
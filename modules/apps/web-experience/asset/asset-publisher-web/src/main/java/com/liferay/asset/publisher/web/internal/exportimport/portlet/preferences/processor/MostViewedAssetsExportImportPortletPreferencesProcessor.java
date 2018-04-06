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

package com.liferay.asset.publisher.web.internal.exportimport.portlet.preferences.processor;

import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor;

import org.osgi.service.component.annotations.Component;

/**
 * Provides the implementation of
 * <code>ExportImportPortletPreferencesProcessor</code> (in the
 * <code>com.liferay.exportimport.api</code> module) for the Most Viewed Assets
 * portlet. This implementation provides specific export and import capabilities
 * and routines for processing portlet preferences while exporting or importing
 * Most Viewed Assets instances.
 *
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + AssetPublisherPortletKeys.MOST_VIEWED_ASSETS,
	service = ExportImportPortletPreferencesProcessor.class
)
public class MostViewedAssetsExportImportPortletPreferencesProcessor
	extends AssetPublisherExportImportPortletPreferencesProcessor {
}
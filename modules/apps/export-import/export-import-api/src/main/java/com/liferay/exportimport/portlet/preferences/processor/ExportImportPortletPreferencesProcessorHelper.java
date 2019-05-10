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

package com.liferay.exportimport.portlet.preferences.processor;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.model.Portlet;

import java.util.function.Function;

import javax.portlet.PortletPreferences;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Máté Thurzó
 */
@ProviderType
public interface ExportImportPortletPreferencesProcessorHelper {

	public void updateExportPortletPreferencesClassPKs(
			PortletDataContext portletDataContext, Portlet portlet,
			PortletPreferences portletPreferences, String key, String className,
			Function<String, String> exportPortletPreferencesNewValueFunction)
		throws Exception;

	public void updateImportPortletPreferencesClassPKs(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences, String key,
			long companyGroupId,
			Function<String, Long> importPortletPreferencesNewValueFunction)
		throws Exception;

}
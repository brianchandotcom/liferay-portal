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

package com.liferay.wysiwyg.web.internal.exportimport.portlet.preferences.processor;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.portlet.preferences.processor.Capability;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.wysiwyg.web.internal.constants.WYSIWYGPortletKeys;

import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Lianne Louie
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + WYSIWYGPortletKeys.WYSIWYG,
	service = ExportImportPortletPreferencesProcessor.class
)
public class WYSIWYGExportImportPortletPreferencesProcessor
	implements ExportImportPortletPreferencesProcessor {

	@Override
	public List<Capability> getExportCapabilities() {
		return null;
	}

	@Override
	public List<Capability> getImportCapabilities() {
		return null;
	}

	@Override
	public PortletPreferences processExportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		String message = portletPreferences.getValue(
			"message", StringPool.BLANK);

		try {
			portletPreferences.setValue(
				"message",
				StringUtil.replace(
					message, "/documents/" + portletDataContext.getGroupId(),
					"/documents/[$groupId$]"));
		}
		catch (ReadOnlyException roe) {
			throw new PortletDataException(
				"Unable to update WYSIWYG portlet preferences during export",
				roe);
		}

		return portletPreferences;
	}

	@Override
	public PortletPreferences processImportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		String message = portletPreferences.getValue(
			"message", StringPool.BLANK);

		try {
			portletPreferences.setValue(
				"message",
				StringUtil.replace(
					message, "/documents/[$groupId$]",
					"/documents/" + portletDataContext.getGroupId()));
		}
		catch (ReadOnlyException roe) {
			throw new PortletDataException(
				"Unable to update WYSIWYG portlet preferences during import",
				roe);
		}

		return portletPreferences;
	}

}
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

package com.liferay.blogs.web.lar.preferencesprocessor;

import com.liferay.blogs.web.constants.BlogsPortletKeys;
import com.liferay.exportimport.preferencesprocessor.ExportImportPreferencesProcessor;
import com.liferay.exportimport.preferencesprocessor.ExportImportPreferencesProcessorCapability;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataException;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + BlogsPortletKeys.BLOGS},
	service = ExportImportPreferencesProcessor.class
)
public class BlogsExportImportPreferencesProcessor
	implements ExportImportPreferencesProcessor {

	@Override
	public List<ExportImportPreferencesProcessorCapability>
		getExportCapabilities() {

		return new ArrayList<>(
			ListUtil.toList(
				new ExportImportPreferencesProcessorCapability[] {
					_blogsPortletDisplayTemplateExportPreferencesProcessorCapability
				}));
	}

	@Override
	public List<ExportImportPreferencesProcessorCapability>
		getImportCapabilities() {

		return new ArrayList<>(
			ListUtil.toList(
				new ExportImportPreferencesProcessorCapability[] {
					_blogsPortletDisplayTemplateImportPreferencesProcessorCapability
				}));
	}

	@Override
	public PortletPreferences processExportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		return null;
	}

	@Override
	public PortletPreferences processImportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		return null;
	}

	@Reference(unbind = "-")
	protected void
		setBlogsPortletDisplayTemplateExportPreferncesProcessorCapability(
			BlogsPortletDisplayTemplateExportPreferencesProcessorCapability
				blogsPortletDisplayTemplateExportPreferncesProcessorCapability) {

		_blogsPortletDisplayTemplateExportPreferencesProcessorCapability =
			blogsPortletDisplayTemplateExportPreferncesProcessorCapability;
	}

	@Reference(unbind = "-")
	protected void
		setBlogsPortletDisplayTemplateImportPreferncesProcessorCapability(
			BlogsPortletDisplayTemplateImportPreferencesProcessorCapability
				blogsPortletDisplayTemplateImportPreferncesProcessorCapability) {

		_blogsPortletDisplayTemplateImportPreferencesProcessorCapability =
			blogsPortletDisplayTemplateImportPreferncesProcessorCapability;
	}

	private BlogsPortletDisplayTemplateExportPreferencesProcessorCapability
		_blogsPortletDisplayTemplateExportPreferencesProcessorCapability;
	private BlogsPortletDisplayTemplateImportPreferencesProcessorCapability
		_blogsPortletDisplayTemplateImportPreferencesProcessorCapability;

}
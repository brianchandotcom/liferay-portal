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

package com.liferay.layout.content.page.editor.web.internal.util;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporter;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.segments.constants.SegmentsEntryConstants;

import java.io.InputStream;

import java.util.List;

import org.junit.Assert;

/**
 * @author Mikel Lorza
 */
public class SegmentsExperienceTestUtil {

	public static void addSegmentsExperienceData(
			String fileName, Layout layout,
			LayoutPageTemplateStructureLocalService
				layoutPageTemplateStructureLocalService,
			LayoutPageTemplatesImporter layoutPageTemplatesImporter,
			long segmentsExperienceId)
		throws Exception {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), layout.getPlid());

		String segmentsExperienceData =
			layoutPageTemplateStructure.getDefaultSegmentsExperienceData();

		LayoutStructure layoutStructure = LayoutStructure.of(
			segmentsExperienceData);

		String pageElementJSON = _readFileContent(fileName);

		if (segmentsExperienceId != SegmentsEntryConstants.ID_DEFAULT) {
			layoutPageTemplatesImporter.importPageElement(
				layout, layoutStructure, layoutStructure.getMainItemId(),
				pageElementJSON, 0, segmentsExperienceId);
		}
		else {
			layoutPageTemplatesImporter.importPageElement(
				layout, layoutStructure, layoutStructure.getMainItemId(),
				pageElementJSON, 0);
		}
	}

	public static void assertContentEquals(
		List<FragmentEntryLink> expectedFragmentEntryLinks,
		List<FragmentEntryLink> actualFragmentEntryLinks) {

		Assert.assertEquals(
			expectedFragmentEntryLinks.toString(), 1,
			expectedFragmentEntryLinks.size());

		Assert.assertEquals(
			actualFragmentEntryLinks.toString(), 1,
			actualFragmentEntryLinks.size());

		FragmentEntryLink expectedFragmentEntryLink =
			expectedFragmentEntryLinks.get(0);

		FragmentEntryLink actualFragmentEntryLink =
			actualFragmentEntryLinks.get(0);

		Assert.assertEquals(
			expectedFragmentEntryLink.getCss(),
			actualFragmentEntryLink.getCss());
		Assert.assertEquals(
			expectedFragmentEntryLink.getHtml(),
			actualFragmentEntryLink.getHtml());
		Assert.assertEquals(
			expectedFragmentEntryLink.getJs(), actualFragmentEntryLink.getJs());
		Assert.assertEquals(
			expectedFragmentEntryLink.getConfiguration(),
			actualFragmentEntryLink.getConfiguration());
		Assert.assertEquals(
			expectedFragmentEntryLink.getEditableValues(),
			actualFragmentEntryLink.getEditableValues());
	}

	private static String _readFileContent(String fileName) throws Exception {
		InputStream inputStream =
			SegmentsExperienceTestUtil.class.getResourceAsStream(
				"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

}
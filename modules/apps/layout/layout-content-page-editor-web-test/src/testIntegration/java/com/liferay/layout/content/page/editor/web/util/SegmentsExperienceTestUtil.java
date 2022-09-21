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

package com.liferay.layout.content.page.editor.web.util;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporter;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.InputStream;

import java.util.List;

import org.junit.Assert;

/**
 * @author Mikel Lorza
 */
public class SegmentsExperienceTestUtil {

	public static void addDataContentToExperience(
			String fileNameContent, Layout layout, long segmentsExperienceId,
			LayoutPageTemplatesImporter layoutPageTemplatesImporter)
		throws Exception {

		LayoutStructure layoutStructure = _getLayoutStructure(layout, 0);

		String pageElementJSON = _readFileContent(fileNameContent);

		if (segmentsExperienceId > 0) {
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

	public static void checkNewSegmentExperienceContent(
			Layout layout, long newSegmentsExperienceId,
			long sourceSegmentsExperienceId)
		throws Exception {

		List<FragmentEntryLink> sourceExperienceFragmentEntryLinks =
			FragmentEntryLinkLocalServiceUtil.
				getFragmentEntryLinksBySegmentsExperienceId(
					layout.getGroupId(), sourceSegmentsExperienceId,
					layout.getPlid());

		List<FragmentEntryLink> newExperienceFragmentEntryLinks =
			FragmentEntryLinkLocalServiceUtil.
				getFragmentEntryLinksBySegmentsExperienceId(
					layout.getGroupId(), newSegmentsExperienceId,
					layout.getPlid());

		Assert.assertTrue(sourceExperienceFragmentEntryLinks.size() == 1);
		Assert.assertTrue(newExperienceFragmentEntryLinks.size() == 1);

		FragmentEntryLink sourceExperienceFragmentEntryLink =
			sourceExperienceFragmentEntryLinks.get(0);

		FragmentEntryLink newExperienceFragmentEntryLink =
			newExperienceFragmentEntryLinks.get(0);

		Assert.assertEquals(
			sourceExperienceFragmentEntryLink.getCss(),
			newExperienceFragmentEntryLink.getCss());
		Assert.assertEquals(
			sourceExperienceFragmentEntryLink.getHtml(),
			newExperienceFragmentEntryLink.getHtml());
		Assert.assertEquals(
			sourceExperienceFragmentEntryLink.getJs(),
			newExperienceFragmentEntryLink.getJs());
		Assert.assertEquals(
			sourceExperienceFragmentEntryLink.getConfiguration(),
			newExperienceFragmentEntryLink.getConfiguration());
		Assert.assertEquals(
			sourceExperienceFragmentEntryLink.getEditableValues(),
			newExperienceFragmentEntryLink.getEditableValues());
	}

	private static String _getData(Layout layout, long segmentsExperienceId) {
		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), layout.getPlid());

		if (segmentsExperienceId > 0) {
			return layoutPageTemplateStructure.getData(segmentsExperienceId);
		}

		return layoutPageTemplateStructure.getDefaultSegmentsExperienceData();
	}

	private static LayoutStructure _getLayoutStructure(
			Layout layout, long segmentsExperienceId)
		throws Exception {

		return LayoutStructure.of(_getData(layout, segmentsExperienceId));
	}

	private static String _readFileContent(String fileName) throws Exception {
		InputStream inputStream =
			SegmentsExperienceTestUtil.class.getResourceAsStream(
				"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

}
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelLocalService;
import com.liferay.layout.provider.LayoutStructureProvider;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.test.rule.Inject;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.test.util.SegmentsTestUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Carolina Barbosa
 */
@RunWith(Arquillian.class)
public class LayoutPageTemplateStructureRelModelListenerTest
	extends BaseModelListenerTestCase {

	@Override
	@Test
	public void testOnBeforeCreate() throws Exception {
		super.testOnBeforeCreate();

		Layout layout = LayoutTestUtil.addTypeContentLayout(group);

		Layout draftLayout = layout.fetchDraftLayout();

		SegmentsExperience segmentsExperience =
			SegmentsTestUtil.addSegmentsExperience(
				group.getGroupId(), draftLayout.getPlid());

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				addLayoutPageTemplateStructure(
					companyAdminUser.getUserId(), group.getGroupId(),
					layout.getPlid(),
					segmentsExperience.getSegmentsExperienceId(),
					StringPool.BLANK, serviceContext);

		LayoutPageTemplateStructureRel layoutPageTemplateStructureRel =
			_layoutPageTemplateStructureRelLocalService.
				addLayoutPageTemplateStructureRel(
					companyAdminUser.getUserId(), group.getGroupId(),
					layoutPageTemplateStructure.
						getLayoutPageTemplateStructureId(),
					segmentsExperience.getSegmentsExperienceId(),
					JSONUtil.put(
						"itemType", objectDefinition1.getPortletId()
					).put(
						"key", objectDefinition1.getClassName()
					).toString(),
					serviceContext);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"itemType", objectDefinition2.getPortletId()
			).put(
				"key", objectDefinition2.getClassName()
			).toString(),
			layoutPageTemplateStructureRel.getData(), true);
	}

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject
	private LayoutPageTemplateStructureRelLocalService
		_layoutPageTemplateStructureRelLocalService;

	@Inject
	private LayoutStructureProvider _layoutStructureProvider;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}
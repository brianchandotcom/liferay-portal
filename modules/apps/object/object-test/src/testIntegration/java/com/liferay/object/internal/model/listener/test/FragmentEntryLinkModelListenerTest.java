/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Carolina Barbosa
 */
@RunWith(Arquillian.class)
public class FragmentEntryLinkModelListenerTest
	extends BaseModelListenerTestCase {

	@Override
	@Test
	public void testOnBeforeCreate() throws Exception {
		super.testOnBeforeCreate();

		Layout layout = LayoutTestUtil.addTypeContentLayout(group);

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				null, companyAdminUser.getUserId(), group.getGroupId(), null,
				RandomTestUtil.randomString(), null,
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(layout.getPlid()),
				layout.getPlid(), StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, StringPool.BLANK,
				JSONUtil.put(
					FragmentEntryProcessorConstants.
						KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
					JSONUtil.put(
						"element-text",
						JSONUtil.put(
							"className", objectDefinition1.getClassName()
						).put(
							"classNameId", objectDefinition1ClassNameId
						))
				).toString(),
				StringPool.BLANK, 0, StringPool.BLANK, 0, serviceContext);

		JSONAssert.assertEquals(
			JSONUtil.put(
				FragmentEntryProcessorConstants.
					KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
				JSONUtil.put(
					"element-text",
					JSONUtil.put(
						"className", objectDefinition2.getClassName()
					).put(
						"classNameId", objectDefinition2ClassNameId
					))
			).toString(),
			fragmentEntryLink.getEditableValues(), true);
	}

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}
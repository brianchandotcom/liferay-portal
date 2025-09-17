/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.editor.configuration.EditorConfiguration;
import com.liferay.portal.kernel.editor.configuration.EditorConfigurationFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Eudaldo Alonso
 */
@FeatureFlag("LPD-17564")
@RunWith(Arquillian.class)
@Sync
public class ViewAllSectionDisplayContextTest
	extends BaseFilesSectionDisplayContextTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Override
	public HashMap<String, Object> getBaseAdditionalProps() {
		HashMap<String, Object> additionalProps =
			super.getBaseAdditionalProps();

		return HashMapBuilder.<String, Object>putAll(
			additionalProps
		).put(
			"commentsProps",
			HashMapBuilder.<String, Object>put(
				"addCommentURL",
				GroupConstants.CMS_FRIENDLY_URL + "/add_content_item_comment"
			).put(
				"deleteCommentURL",
				GroupConstants.CMS_FRIENDLY_URL + "/delete_content_item_comment"
			).put(
				"editCommentURL",
				GroupConstants.CMS_FRIENDLY_URL + "/edit_content_item_comment"
			).put(
				"editorConfig",
				() -> {
					EditorConfiguration contentItemCommentEditorConfiguration =
						EditorConfigurationFactoryUtil.getEditorConfiguration(
							StringPool.BLANK, "contentItemCommentEditor",
							StringPool.BLANK, Collections.emptyMap(),
							themeDisplay,
							RequestBackedPortletURLFactoryUtil.create(
								mockHttpServletRequest));

					Map<String, Object> data =
						contentItemCommentEditorConfiguration.getData();

					return data.get("editorConfig");
				}
			).put(
				"getCommentsURL",
				GroupConstants.CMS_FRIENDLY_URL + "/get_asset_comments"
			).build()
		).build();
	}

	@Override
	protected Map<String, String> getExpectedCreationMenuItems()
		throws PortalException {

		return LinkedHashMapBuilder.put(
			"basic-content",
			getRedirect(
				"L_BASIC_WEB_CONTENT",
				ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENTS)
		).put(
			"single-file",
			getRedirect(
				"L_BASIC_DOCUMENT",
				ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_FILES)
		).put(
			"multiple-files", StringPool.BLANK
		).put(
			"Blog",
			getRedirect(
				"L_BLOG",
				ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENTS)
		).put(
			"Knowledge Base",
			getRedirect(
				"L_KNOWLEDGE_BASE",
				ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENTS)
		).put(
			"external-video-shortcut",
			getRedirect(
				"L_EXTERNAL_VIDEO",
				ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_FILES)
		).build();
	}

	@Override
	protected String getObjectFolderExternalReferenceCode() {
		if (RandomTestUtil.randomBoolean()) {
			return ObjectFolderConstants.
				EXTERNAL_REFERENCE_CODE_CONTENT_STRUCTURES;
		}

		return ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_FILE_TYPES;
	}

	@Override
	protected String[] getObjectFolderExternalReferenceCodes() {
		return new String[] {
			ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENT_STRUCTURES,
			ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_FILE_TYPES
		};
	}

	@Override
	protected Object getSectionDisplayContext(
			HttpServletRequest httpServletRequest)
		throws Exception {

		_fragmentRenderer.render(
			null, httpServletRequest, new MockHttpServletResponse());

		Object allSectionDisplayContext = httpServletRequest.getAttribute(
			"com.liferay.site.cms.site.initializer.internal.display.context." +
				"ViewAllSectionDisplayContext");

		Assert.assertNotNull(allSectionDisplayContext);

		return allSectionDisplayContext;
	}

	@Inject(
		filter = "component.name=com.liferay.site.cms.site.initializer.internal.fragment.renderer.ViewAllJSPSectionFragmentRenderer"
	)
	private FragmentRenderer _fragmentRenderer;

}
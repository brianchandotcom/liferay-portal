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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import jakarta.servlet.http.HttpServletRequest;

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
public class ViewFilesSectionDisplayContextTest
	extends BaseFilesSectionDisplayContextTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Override
	public HashMap<String, Object> getBaseAdditionalProps() throws Exception {
		return new HashMapBuilder<>().putAll(
			super.getBaseAdditionalProps()
		).put(
			"galleryViewEnabled", true
		).build();
	}

	@Override
	protected Map<String, String> getExpectedCreationMenuItems()
		throws PortalException {

		return LinkedHashMapBuilder.put(
			"single-file", getRedirect("L_CMS_BASIC_DOCUMENT")
		).put(
			"multiple-files", StringPool.BLANK
		).put(
			"folder", StringPool.BLANK
		).put(
			"external-video", getRedirect("L_CMS_EXTERNAL_VIDEO")
		).build();
	}

	@Override
	protected String getFilterString() {
		return "cmsRoot eq true and cmsSection eq 'files'";
	}

	@Override
	protected String getObjectFolderExternalReferenceCode() {
		return ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_FILE_TYPES;
	}

	@Override
	protected String getRootObjectEntryFolderExternalReferenceCode() {
		return ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_FILES;
	}

	@Override
	protected Object getSectionDisplayContext(
			HttpServletRequest httpServletRequest)
		throws Exception {

		_fragmentRenderer.render(
			null, httpServletRequest, new MockHttpServletResponse());

		Object filesSectionDisplayContext = httpServletRequest.getAttribute(
			"com.liferay.site.cms.site.initializer.internal.display.context." +
				"ViewFilesSectionDisplayContext");

		Assert.assertNotNull(filesSectionDisplayContext);

		return filesSectionDisplayContext;
	}

	@Inject(
		filter = "component.name=com.liferay.site.cms.site.initializer.internal.fragment.renderer.ViewFilesJSPSectionFragmentRenderer"
	)
	private FragmentRenderer _fragmentRenderer;

}
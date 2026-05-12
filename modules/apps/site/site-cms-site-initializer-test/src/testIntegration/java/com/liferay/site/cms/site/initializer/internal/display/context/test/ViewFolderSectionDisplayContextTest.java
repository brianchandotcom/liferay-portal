/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Marco Galluzzi
 */
@FeatureFlag("LPD-17564")
@RunWith(Arquillian.class)
public class ViewFolderSectionDisplayContextTest
	extends BaseDisplayContextTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testGetAdditionalProps() throws Exception {

		// No object entry folder in context

		Map<String, Object> additionalProps = _getAdditionalProps(
			getMockHttpServletRequest());

		Assert.assertFalse(
			additionalProps.toString(),
			additionalProps.containsKey("trashEnabled"));

		// Recycle bin is enabled

		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			null, DepotConstants.TYPE_SPACE,
			ServiceContextTestUtil.getServiceContext());

		ObjectEntryFolder objectEntryFolder =
			_objectEntryFolderLocalService.addObjectEntryFolder(
				null, depotEntry.getGroupId(), TestPropsValues.getUserId(),
				ObjectEntryFolderConstants.
					PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
				RandomTestUtil.randomString(),
				HashMapBuilder.put(
					LocaleUtil.getDefault(), StringUtil.randomString()
				).build(),
				StringUtil.randomString(),
				ServiceContextTestUtil.getServiceContext());

		_setTrashEnabled(depotEntry, true);

		additionalProps = _getAdditionalProps(
			getMockHttpServletRequest(objectEntryFolder));

		Assert.assertEquals(Boolean.TRUE, additionalProps.get("trashEnabled"));

		// Recycle bin is not enabled

		_setTrashEnabled(depotEntry, false);

		additionalProps = _getAdditionalProps(
			getMockHttpServletRequest(objectEntryFolder));

		Assert.assertEquals(Boolean.FALSE, additionalProps.get("trashEnabled"));
	}

	private Map<String, Object> _getAdditionalProps(
			HttpServletRequest httpServletRequest)
		throws Exception {

		_fragmentRenderer.render(
			null, httpServletRequest, new MockHttpServletResponse());

		Object viewFolderSectionDisplayContext =
			httpServletRequest.getAttribute(
				"com.liferay.site.cms.site.initializer.internal.display." +
					"context.ViewFolderSectionDisplayContext");

		Assert.assertNotNull(viewFolderSectionDisplayContext);

		return ReflectionTestUtil.invoke(
			viewFolderSectionDisplayContext, "getAdditionalProps",
			new Class<?>[0]);
	}

	private void _setTrashEnabled(DepotEntry depotEntry, boolean trashEnabled)
		throws Exception {

		Group depotEntryGroup = depotEntry.getGroup();

		UnicodeProperties unicodeProperties =
			depotEntryGroup.getTypeSettingsProperties();

		unicodeProperties.setProperty(
			"trashEnabled", String.valueOf(trashEnabled));

		_groupLocalService.updateGroup(
			depotEntryGroup.getGroupId(), unicodeProperties.toString());
	}

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject(
		filter = "component.name=com.liferay.site.cms.site.initializer.internal.fragment.renderer.ViewFolderJSPSectionFragmentRenderer"
	)
	private FragmentRenderer _fragmentRenderer;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

}
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.upgrade.v3_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.model.FragmentEntryVersion;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.test.util.FragmentTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.List;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class FragmentJakartaUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_fragmentCollection = FragmentTestUtil.addFragmentCollection(
			_group.getGroupId());

		_layout = LayoutTestUtil.addTypeContentLayout(_group);

		_upgradeStepRegistrator.register(
			(fromSchemaVersionString, toSchemaVersionString, upgradeSteps) -> {
				for (UpgradeStep upgradeStep : upgradeSteps) {
					Class<?> clazz = upgradeStep.getClass();

					if (Objects.equals(
							clazz.getName(),
							"com.liferay.fragment.internal.upgrade.v3_0_0." +
								"FragmentJakartaUpgradeProcess")) {

						_upgradeProcess = (UpgradeProcess)upgradeStep;
					}
				}
			});
	}

	@Test
	public void testUpgrade() throws Exception {
		FragmentEntry fragmentEntry = null;
		FragmentEntryLink fragmentEntryLink = null;

		try {
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), TestPropsValues.getUserId());

			fragmentEntry = _fragmentEntryLocalService.addFragmentEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), null,
				RandomTestUtil.randomString(), StringPool.BLANK, _JAVAX_HTML,
				RandomTestUtil.randomString(), false, _JAVAX_CONFIGURATION,
				null, 0, false, false, FragmentConstants.TYPE_COMPONENT, null,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

			fragmentEntry.setCss(RandomTestUtil.randomString());

			fragmentEntry = _fragmentEntryLocalService.updateFragmentEntry(
				fragmentEntry);

			fragmentEntryLink =
				_fragmentEntryLinkLocalService.addFragmentEntryLink(
					null, TestPropsValues.getUserId(), _group.getGroupId(), 0,
					fragmentEntry.getFragmentEntryId(),
					_segmentsExperienceLocalService.
						fetchDefaultSegmentsExperienceId(_layout.getPlid()),
					_layout.getPlid(), fragmentEntry.getCss(), _JAVAX_HTML,
					fragmentEntry.getJs(), _JAVAX_CONFIGURATION,
					_JAVAX_EDITABLE_VALUES, StringPool.BLANK, 0, null,
					fragmentEntry.getType(), serviceContext);

			_upgradeProcess.upgrade();

			_multiVMPool.clear();

			FragmentEntry updatedFragmentEntry =
				_fragmentEntryLocalService.getFragmentEntry(
					fragmentEntry.getFragmentEntryId());

			Assert.assertNotNull(updatedFragmentEntry);

			Assert.assertEquals(
				_JAKARTA_CONFIGURATION,
				updatedFragmentEntry.getConfiguration());
			Assert.assertEquals(_JAKARTA_HTML, updatedFragmentEntry.getHtml());

			FragmentEntryLink updatedFragmentEntryLink =
				_fragmentEntryLinkLocalService.getFragmentEntryLink(
					fragmentEntryLink.getFragmentEntryLinkId());

			Assert.assertNotNull(updatedFragmentEntryLink);

			Assert.assertEquals(
				_JAKARTA_CONFIGURATION,
				updatedFragmentEntryLink.getConfiguration());
			Assert.assertEquals(
				_JAKARTA_EDITABLE_VALUES,
				updatedFragmentEntryLink.getEditableValues());
			Assert.assertEquals(
				_JAKARTA_HTML, updatedFragmentEntryLink.getHtml());

			List<FragmentEntryVersion> fragmentEntryVersions =
				_fragmentEntryLocalService.getVersions(updatedFragmentEntry);

			Assert.assertFalse(fragmentEntryVersions.isEmpty());

			for (FragmentEntryVersion updatedFragmentEntryVersion :
					fragmentEntryVersions) {

				Assert.assertEquals(
					_JAKARTA_CONFIGURATION,
					updatedFragmentEntryVersion.getConfiguration());
				Assert.assertEquals(
					_JAKARTA_HTML, updatedFragmentEntryVersion.getHtml());
			}
		}
		finally {
			if (fragmentEntryLink != null) {
				_fragmentEntryLinkLocalService.deleteFragmentEntryLink(
					fragmentEntryLink);
			}

			if (fragmentEntry != null) {
				_fragmentEntryLocalService.deleteFragmentEntry(fragmentEntry);
			}
		}
	}

	private static final String _JAKARTA_CONFIGURATION = StringBundler.concat(
		"{\"fieldSets\": [{\"fields\": [{\"dataType\": \"string\",\"",
		"defaultValue\": false,\"label\": \"jakarta-servlet-test-",
		"FragmentJakartaUpgradeProcessTest\",\"name\": \"test1\",\"type\": ",
		"\"checkbox\"}]}]}");

	private static final String _JAKARTA_EDITABLE_VALUES =
		"{\"jakarta.servlet.test.FragmentJakartaUpgradeProcessTest\":{\"" +
			"editable\":true}}";

	private static final String _JAKARTA_HTML =
		"<#assign upgradeProcess = serviceLocator.findService(\"" +
			"jakarta.servlet.test.FragmentJakartaUpgradeProcessTest\")/>";

	private static final String _JAVAX_CONFIGURATION = StringBundler.concat(
		"{\"fieldSets\": [{\"fields\": [{\"dataType\": \"string\",\"",
		"defaultValue\": false,\"label\": \"javax-servlet-test-",
		"FragmentJakartaUpgradeProcessTest\",\"name\": \"test1\",\"type\": ",
		"\"checkbox\"}]}]}");

	private static final String _JAVAX_EDITABLE_VALUES =
		"{\"javax.servlet.test.FragmentJakartaUpgradeProcessTest\":{\"" +
			"editable\":true}}";

	private static final String _JAVAX_HTML =
		"<#assign upgradeProcess = serviceLocator.findService(\"" +
			"javax.servlet.test.FragmentJakartaUpgradeProcessTest\")/>";

	@Inject(
		filter = "component.name=com.liferay.fragment.internal.upgrade.registry.FragmentServiceUpgradeStepRegistrator"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@DeleteAfterTestRun
	private FragmentCollection _fragmentCollection;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private Layout _layout;

	@Inject
	private MultiVMPool _multiVMPool;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	private UpgradeProcess _upgradeProcess;

}
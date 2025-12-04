/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.segments;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.layout.content.page.editor.web.internal.util.layout.structure.LayoutStructureUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.language.LanguageImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperimentLocalServiceUtil;

import java.util.Collections;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

/**
 * @author David Arques
 */
public class SegmentsExperienceUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(new LanguageImpl());

		PortletPreferences mockPortletPreferences = Mockito.mock(
			PortletPreferences.class);

		_portletPreferencesLocalServiceUtilMockedStatic.when(
			() -> PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				Mockito.anyLong(), Mockito.anyInt(), Mockito.anyLong(),
				Mockito.anyString())
		).thenReturn(
			mockPortletPreferences
		);
	}

	@AfterClass
	public static void tearDownClass() {
		_portletPreferencesLocalServiceUtilMockedStatic.close();
		_segmentsExperimentLocalServiceUtilMockedStatic.close();
	}

	@Test
	public void testCopySegmentsExperienceData() throws Exception {
		long companyId = RandomTestUtil.randomLong();
		long copiedResourcePermissionId = RandomTestUtil.randomLong();
		long groupId = RandomTestUtil.randomLong();
		long plid = RandomTestUtil.randomLong();
		long resourceActionIds = RandomTestUtil.randomLong();
		long roleId = RandomTestUtil.randomLong();
		long sourceFragmentEntryLinkId = RandomTestUtil.randomLong();
		long sourceSegmentsExperienceId = RandomTestUtil.randomLong();
		long targetSegmentsExperienceId = RandomTestUtil.randomLong();
		long userId = RandomTestUtil.randomLong();

		String newNamespace = RandomTestUtil.randomString();
		String oldNamespace = RandomTestUtil.randomString();

		String portletId = RandomTestUtil.randomString() + "_INSTANCE_";

		String sourcePortletId = portletId + oldNamespace;

		String sourcePrimaryKey = RandomTestUtil.randomString();
		String sourceSegmentsExperienceKey = RandomTestUtil.randomString();

		String targetPortletId = portletId + newNamespace;

		String targetPrimaryKey = RandomTestUtil.randomString();

		CommentManager commentManager = Mockito.mock(CommentManager.class);
		PortletRegistry portletRegistry = Mockito.mock(PortletRegistry.class);

		Layout layout = Mockito.mock(Layout.class);

		Mockito.when(
			layout.getPlid()
		).thenReturn(
			plid
		);

		SegmentsExperience sourceSegmentsExperience = Mockito.mock(
			SegmentsExperience.class);
		SegmentsExperience targetSegmentsExperience = Mockito.mock(
			SegmentsExperience.class);

		Mockito.when(
			sourceSegmentsExperience.getSegmentsExperienceId()
		).thenReturn(
			sourceSegmentsExperienceId
		);

		Mockito.when(
			sourceSegmentsExperience.getSegmentsExperienceKey()
		).thenReturn(
			sourceSegmentsExperienceKey
		);

		Mockito.when(
			targetSegmentsExperience.getSegmentsExperienceId()
		).thenReturn(
			targetSegmentsExperienceId
		);

		FragmentEntryLink fragmentEntryLink = Mockito.mock(
			FragmentEntryLink.class);

		Mockito.when(
			fragmentEntryLink.getCompanyId()
		).thenReturn(
			companyId
		);

		Mockito.when(
			fragmentEntryLink.getFragmentEntryLinkId()
		).thenReturn(
			sourceFragmentEntryLinkId
		);

		Mockito.when(
			fragmentEntryLink.getNamespace()
		).thenReturn(
			oldNamespace
		);

		Mockito.when(
			fragmentEntryLink.getEditableValuesJSONObject()
		).thenReturn(
			JSONUtil.put(
				"instanceId", oldNamespace
			).put(
				"portletId", portletId
			)
		);

		Mockito.when(
			fragmentEntryLink.clone()
		).thenAnswer(
			inv -> {
				FragmentEntryLink clone = Mockito.mock(FragmentEntryLink.class);

				Mockito.when(
					clone.getNamespace()
				).thenReturn(
					newNamespace
				);

				Mockito.when(
					clone.getEditableValuesJSONObject()
				).thenReturn(
					JSONUtil.put(
						"instanceId", newNamespace
					).put(
						"portletId", portletId
					)
				);

				return clone;
			}
		);

		Mockito.when(
			portletRegistry.getFragmentEntryLinkPortletIds(fragmentEntryLink)
		).thenReturn(
			List.of(sourcePortletId)
		);

		String rootPortletId = PortletIdCodec.decodePortletName(portletId);

		LayoutStructure layoutStructure = Mockito.mock(LayoutStructure.class);
		FragmentStyledLayoutStructureItem fragmentStyledLayoutStructureItem =
			Mockito.mock(FragmentStyledLayoutStructureItem.class);

		Mockito.when(
			layoutStructure.getLayoutStructureItemByFragmentEntryLinkId(
				sourceFragmentEntryLinkId)
		).thenReturn(
			fragmentStyledLayoutStructureItem
		);

		try (MockedStatic<FragmentEntryLinkLocalServiceUtil>
				fragmentEntryLinkMockedStatic = Mockito.mockStatic(
					FragmentEntryLinkLocalServiceUtil.class);
			MockedStatic<LayoutStructureUtil> layoutStructureUtilMockedStatic =
				Mockito.mockStatic(LayoutStructureUtil.class);
			MockedStatic<LayoutPageTemplateStructureLocalServiceUtil>
				layoutPageTemplateStructureLocalServiceUtilMockedStatic =
					Mockito.mockStatic(
						LayoutPageTemplateStructureLocalServiceUtil.class);
			MockedStatic<ResourcePermissionLocalServiceUtil>
				resourcePermissionLocalServiceUtilMockedStatic =
					Mockito.mockStatic(
						ResourcePermissionLocalServiceUtil.class);
			MockedStatic<PortletPermissionUtil>
				portletPermissionUtilMockedStatic = Mockito.mockStatic(
					PortletPermissionUtil.class);
			MockedStatic<CounterLocalServiceUtil>
				counterLocalServiceUtilMockedStatic = Mockito.mockStatic(
					CounterLocalServiceUtil.class)) {

			fragmentEntryLinkMockedStatic.when(
				() ->
					FragmentEntryLinkLocalServiceUtil.
						getFragmentEntryLinksBySegmentsExperienceId(
							groupId, sourceSegmentsExperienceId, plid)
			).thenReturn(
				Collections.singletonList(fragmentEntryLink)
			);

			fragmentEntryLinkMockedStatic.when(
				() -> FragmentEntryLinkLocalServiceUtil.addFragmentEntryLink(
					Mockito.any())
			).thenAnswer(
				(Answer<FragmentEntryLink>)inv -> inv.getArgument(0)
			);

			layoutStructureUtilMockedStatic.when(
				() -> LayoutStructureUtil.getLayoutStructure(
					groupId, plid, sourceSegmentsExperienceId)
			).thenReturn(
				layoutStructure
			);

			ResourcePermission resourcePermission = Mockito.mock(
				ResourcePermission.class);

			Mockito.when(
				resourcePermission.getRoleId()
			).thenReturn(
				roleId
			);

			Mockito.when(
				resourcePermission.getActionIds()
			).thenReturn(
				resourceActionIds
			);

			LayoutPageTemplateStructure layoutPageTemplateStructure =
				Mockito.mock(LayoutPageTemplateStructure.class);

			JSONObject structureDataJSONObject = JSONUtil.put("items", "");

			Mockito.when(
				layoutStructure.toJSONObject()
			).thenReturn(
				structureDataJSONObject
			);

			Mockito.when(
				layoutPageTemplateStructure.getData(Mockito.anyLong())
			).thenReturn(
				structureDataJSONObject.toString()
			);

			layoutPageTemplateStructureLocalServiceUtilMockedStatic.when(
				() ->
					LayoutPageTemplateStructureLocalServiceUtil.
						fetchLayoutPageTemplateStructure(groupId, plid)
			).thenReturn(
				layoutPageTemplateStructure
			);

			resourcePermissionLocalServiceUtilMockedStatic.when(
				() -> ResourcePermissionLocalServiceUtil.getResourcePermissions(
					companyId, rootPortletId,
					ResourceConstants.SCOPE_INDIVIDUAL, sourcePrimaryKey)
			).thenReturn(
				Collections.singletonList(resourcePermission)
			);

			counterLocalServiceUtilMockedStatic.when(
				() -> CounterLocalServiceUtil.increment(
					ResourcePermission.class.getName())
			).thenReturn(
				copiedResourcePermissionId
			);

			resourcePermissionLocalServiceUtilMockedStatic.when(
				() ->
					ResourcePermissionLocalServiceUtil.createResourcePermission(
						copiedResourcePermissionId)
			).thenReturn(
				Mockito.mock(ResourcePermission.class)
			);

			ArgumentCaptor<ResourcePermission> addedPermission =
				ArgumentCaptor.forClass(ResourcePermission.class);

			resourcePermissionLocalServiceUtilMockedStatic.when(
				() -> ResourcePermissionLocalServiceUtil.addResourcePermission(
					addedPermission.capture())
			).thenReturn(
				null
			);

			portletPermissionUtilMockedStatic.when(
				() -> PortletPermissionUtil.getPrimaryKey(
					Mockito.anyLong(), Mockito.anyString())
			).thenAnswer(
				inv -> {
					String portletIdArg = inv.getArgument(1);

					if (portletIdArg.equals(sourcePortletId)) {
						return sourcePrimaryKey;
					}

					if (portletIdArg.equals(targetPortletId)) {
						return targetPrimaryKey;
					}

					return StringPool.BLANK;
				}
			);

			SegmentsExperienceUtil.copySegmentsExperienceData(
				commentManager, groupId, layout, portletRegistry,
				sourceSegmentsExperience, targetSegmentsExperience,
				key -> Mockito.mock(ServiceContext.class), userId);

			Assert.assertFalse(
				addedPermission.getAllValues(
				).isEmpty());

			ResourcePermission copiedResourcePermission =
				addedPermission.getValue();

			Mockito.verify(
				copiedResourcePermission
			).setCompanyId(
				companyId
			);

			Mockito.verify(
				copiedResourcePermission
			).setName(
				rootPortletId
			);

			Mockito.verify(
				copiedResourcePermission
			).setScope(
				ResourceConstants.SCOPE_INDIVIDUAL
			);

			Mockito.verify(
				copiedResourcePermission
			).setPrimKey(
				targetPrimaryKey
			);

			Mockito.verify(
				copiedResourcePermission
			).setRoleId(
				roleId
			);

			Mockito.verify(
				copiedResourcePermission
			).setActionIds(
				resourceActionIds
			);

			Mockito.verify(
				copiedResourcePermission
			).setViewActionId(
				resourcePermission.isViewActionId()
			);
		}
	}

	@Test
	public void testGetSegmentsExperienceJSONObject() {
		SegmentsExperience segmentsExperience = Mockito.mock(
			SegmentsExperience.class);

		Mockito.when(
			segmentsExperience.isActive()
		).thenReturn(
			RandomTestUtil.randomBoolean()
		);

		Mockito.when(
			segmentsExperience.getNameCurrentValue()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			segmentsExperience.getPriority()
		).thenReturn(
			RandomTestUtil.randomInt()
		);

		Mockito.when(
			segmentsExperience.getSegmentsEntryId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			segmentsExperience.getSegmentsExperienceId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Assert.assertEquals(
			JSONUtil.put(
				"active", segmentsExperience.isActive()
			).put(
				"name", segmentsExperience.getNameCurrentValue()
			).put(
				"priority", segmentsExperience.getPriority()
			).put(
				"segmentsEntryId", segmentsExperience.getSegmentsEntryId()
			).put(
				"segmentsExperienceId",
				segmentsExperience.getSegmentsExperienceId()
			).toString(),
			String.valueOf(
				SegmentsExperienceUtil.getSegmentsExperienceJSONObject(
					segmentsExperience)));
	}

	private static MockedStatic<PortletPreferencesLocalServiceUtil>
		_portletPreferencesLocalServiceUtilMockedStatic = Mockito.mockStatic(
			PortletPreferencesLocalServiceUtil.class);
	private static final MockedStatic<SegmentsExperimentLocalServiceUtil>
		_segmentsExperimentLocalServiceUtilMockedStatic = Mockito.mockStatic(
			SegmentsExperimentLocalServiceUtil.class);

}
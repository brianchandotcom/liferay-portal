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
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
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

import org.mockito.MockedStatic;
import org.mockito.Mockito;

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
		_counterLocalServiceUtilMockedStatic.close();
		_fragmentEntryLinkLocalServiceUtilMockedStatic.close();
		_layoutPageTemplateStructureLocalServiceUtilMockedStatic.close();
		_layoutStructureUtilMockedStatic.close();
		_portletLocalServiceUtilMockedStatic.close();
		_portletPermissionUtilMockedStatic.close();
		_portletPreferencesLocalServiceUtilMockedStatic.close();
		_resourcePermissionLocalServiceUtilMockedStatic.close();
		_segmentsExperimentLocalServiceUtilMockedStatic.close();
	}

	@Test
	public void testCopySegmentsExperienceData() throws Exception {
		Layout layout = _getLayout();
		SegmentsExperience sourceSegmentsExperience = _getSegmentsExperience();
		SegmentsExperience targetSegmentsExperience = _getSegmentsExperience();

		FragmentEntryLink fragmentEntryLink = _getFragmentEntryLink(
			_OLD_NAMESPACE);
		FragmentEntryLink newFragmentEntryLink = _getFragmentEntryLink(
			_NEW_NAMESPACE);

		Mockito.when(
			fragmentEntryLink.clone()
		).thenReturn(
			newFragmentEntryLink
		);

		_setUpFragmentEntryLinkLocalServiceUtil(
			fragmentEntryLink, layout.getPlid(),
			sourceSegmentsExperience.getSegmentsExperienceId());

		LayoutStructure layoutStructure = new LayoutStructure();

		layoutStructure.addFragmentStyledLayoutStructureItem(
			fragmentEntryLink.getFragmentEntryLinkId(), null, 0);

		_setUpLayoutPageTemplateStructureLocalServiceUtil(
			layoutStructure, layout.getPlid(),
			sourceSegmentsExperience.getSegmentsExperienceId());

		String sourcePortletId = PortletIdCodec.encode(
			_PORTLET_ID, _OLD_NAMESPACE);

		_setUpPortletRegistry(fragmentEntryLink, sourcePortletId);

		String targetPortletId = PortletIdCodec.encode(
			_PORTLET_ID, _NEW_NAMESPACE);

		_testCopySegmentsExperienceData(
			layout, layoutStructure, sourcePortletId, sourceSegmentsExperience,
			Collections.emptyList(), targetPortletId, null,
			targetSegmentsExperience);

		ResourcePermission sourceResourcePermission = _getResourcePermission();
		ResourcePermission targetResourcePermission = _getResourcePermission();

		_testCopySegmentsExperienceData(
			layout, layoutStructure, sourcePortletId, sourceSegmentsExperience,
			Collections.singletonList(sourceResourcePermission),
			targetPortletId, targetResourcePermission,
			targetSegmentsExperience);
	}

	@Test
	public void testGetSegmentsExperienceJSONObject() {
		SegmentsExperience segmentsExperience = _getSegmentsExperience();

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

	private FragmentEntryLink _getFragmentEntryLink(String namespace) {
		FragmentEntryLink fragmentEntryLink = Mockito.mock(
			FragmentEntryLink.class);

		Mockito.when(
			fragmentEntryLink.getCompanyId()
		).thenReturn(
			_COMPANY_ID
		);

		Mockito.when(
			fragmentEntryLink.getFragmentEntryLinkId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			fragmentEntryLink.getNamespace()
		).thenReturn(
			namespace
		);

		Mockito.when(
			fragmentEntryLink.getEditableValuesJSONObject()
		).thenReturn(
			JSONUtil.put(
				"instanceId", namespace
			).put(
				"portletId", _PORTLET_ID
			)
		);

		return fragmentEntryLink;
	}

	private Layout _getLayout() {
		Layout layout = Mockito.mock(Layout.class);

		Mockito.when(
			layout.getPlid()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		return layout;
	}

	private ResourcePermission _getResourcePermission() {
		ResourcePermission resourcePermission = Mockito.mock(
			ResourcePermission.class);

		Mockito.when(
			resourcePermission.getRoleId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			resourcePermission.getActionIds()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		return resourcePermission;
	}

	private SegmentsExperience _getSegmentsExperience() {
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

		Mockito.when(
			segmentsExperience.getSegmentsExperienceKey()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		return segmentsExperience;
	}

	private void _setUpFragmentEntryLinkLocalServiceUtil(
		FragmentEntryLink fragmentEntryLink, long plid,
		long segmentsExperienceId) {

		_fragmentEntryLinkLocalServiceUtilMockedStatic.when(
			() ->
				FragmentEntryLinkLocalServiceUtil.
					getFragmentEntryLinksBySegmentsExperienceId(
						_GROUP_ID, segmentsExperienceId, plid)
		).thenReturn(
			Collections.singletonList(fragmentEntryLink)
		);

		_fragmentEntryLinkLocalServiceUtilMockedStatic.when(
			() -> FragmentEntryLinkLocalServiceUtil.addFragmentEntryLink(
				Mockito.any())
		).thenAnswer(
			invocation -> invocation.getArgument(0, FragmentEntryLink.class)
		);
	}

	private void _setUpLayoutPageTemplateStructureLocalServiceUtil(
		LayoutStructure layoutStructure, long plid, long segmentsExperienceId) {

		_layoutStructureUtilMockedStatic.when(
			() -> LayoutStructureUtil.getLayoutStructure(
				_GROUP_ID, plid, segmentsExperienceId)
		).thenReturn(
			layoutStructure
		);

		LayoutPageTemplateStructure layoutPageTemplateStructure = Mockito.mock(
			LayoutPageTemplateStructure.class);

		Mockito.when(
			layoutPageTemplateStructure.getData(Mockito.anyLong())
		).thenReturn(
			layoutStructure.toString()
		);

		_layoutPageTemplateStructureLocalServiceUtilMockedStatic.when(
			() ->
				LayoutPageTemplateStructureLocalServiceUtil.
					fetchLayoutPageTemplateStructure(_GROUP_ID, plid)
		).thenReturn(
			layoutPageTemplateStructure
		);
	}

	private void _setUpPortletRegistry(
		FragmentEntryLink fragmentEntryLink, String portletId) {

		Mockito.when(
			_portletRegistry.getFragmentEntryLinkPortletIds(fragmentEntryLink)
		).thenReturn(
			List.of(portletId)
		);
	}

	private void _setUpResourcePermissionLocalServiceUtil(
		long companyId, long plid, String portletId, String sourcePortletId,
		List<ResourcePermission> sourceResourcePermissions,
		ResourcePermission targetResourcePermission) {

		_resourcePermissionLocalServiceUtilMockedStatic.when(
			() -> ResourcePermissionLocalServiceUtil.getResourcePermissions(
				companyId, portletId, ResourceConstants.SCOPE_INDIVIDUAL,
				PortletPermissionUtil.getPrimaryKey(plid, sourcePortletId))
		).thenReturn(
			sourceResourcePermissions
		);

		_resourcePermissionLocalServiceUtilMockedStatic.when(
			() -> ResourcePermissionLocalServiceUtil.createResourcePermission(
				Mockito.anyLong())
		).thenReturn(
			targetResourcePermission
		);
	}

	private void _testCopySegmentsExperienceData(
			Layout layout, LayoutStructure layoutStructure,
			String sourcePortletId, SegmentsExperience sourceSegmentsExperience,
			List<ResourcePermission> sourceResourcePermissions,
			String targetPortletId, ResourcePermission targetResourcePermission,
			SegmentsExperience targetSegmentsExperience)
		throws Exception {

		_layoutPageTemplateStructureLocalServiceUtilMockedStatic.
			clearInvocations();
		_layoutStructureUtilMockedStatic.clearInvocations();
		_resourcePermissionLocalServiceUtilMockedStatic.reset();

		_setUpResourcePermissionLocalServiceUtil(
			_COMPANY_ID, layout.getPlid(), _PORTLET_ID, sourcePortletId,
			sourceResourcePermissions, targetResourcePermission);

		SegmentsExperienceUtil.copySegmentsExperienceData(
			_commentManager, _GROUP_ID, layout, _portletRegistry,
			sourceSegmentsExperience, targetSegmentsExperience,
			key -> Mockito.mock(ServiceContext.class), _USER_ID);

		_layoutStructureUtilMockedStatic.verify(
			() -> LayoutStructureUtil.getLayoutStructure(
				_GROUP_ID, layout.getPlid(),
				sourceSegmentsExperience.getSegmentsExperienceId()));

		_layoutPageTemplateStructureLocalServiceUtilMockedStatic.verify(
			() ->
				LayoutPageTemplateStructureLocalServiceUtil.
					updateLayoutPageTemplateStructureData(
						_USER_ID, _GROUP_ID, layout.getPlid(),
						targetSegmentsExperience.getSegmentsExperienceId(),
						layoutStructure.toString()));

		_resourcePermissionLocalServiceUtilMockedStatic.verify(
			() -> ResourcePermissionLocalServiceUtil.getResourcePermissions(
				_COMPANY_ID, _PORTLET_ID, ResourceConstants.SCOPE_INDIVIDUAL,
				PortletPermissionUtil.getPrimaryKey(
					layout.getPlid(), sourcePortletId)));

		if (ListUtil.isEmpty(sourceResourcePermissions)) {
			_resourcePermissionLocalServiceUtilMockedStatic.verify(
				() ->
					ResourcePermissionLocalServiceUtil.createResourcePermission(
						Mockito.anyLong()),
				Mockito.never());

			return;
		}

		_resourcePermissionLocalServiceUtilMockedStatic.verify(
			() -> ResourcePermissionLocalServiceUtil.createResourcePermission(
				Mockito.anyLong()));

		Mockito.verify(
			targetResourcePermission
		).setCompanyId(
			_COMPANY_ID
		);

		Mockito.verify(
			targetResourcePermission
		).setName(
			_PORTLET_ID
		);

		Mockito.verify(
			targetResourcePermission
		).setScope(
			ResourceConstants.SCOPE_INDIVIDUAL
		);

		Mockito.verify(
			targetResourcePermission
		).setPrimKey(
			PortletPermissionUtil.getPrimaryKey(
				layout.getPlid(), targetPortletId)
		);

		ResourcePermission sourceResourcePermission =
			sourceResourcePermissions.get(0);

		Mockito.verify(
			targetResourcePermission
		).setRoleId(
			sourceResourcePermission.getRoleId()
		);

		Mockito.verify(
			targetResourcePermission
		).setActionIds(
			sourceResourcePermission.getActionIds()
		);

		Mockito.verify(
			targetResourcePermission
		).setViewActionId(
			sourceResourcePermission.isViewActionId()
		);
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final long _GROUP_ID = RandomTestUtil.randomLong();

	private static final String _NEW_NAMESPACE = RandomTestUtil.randomString();

	private static final String _OLD_NAMESPACE = RandomTestUtil.randomString();

	private static final String _PORTLET_ID = RandomTestUtil.randomString();

	private static final long _USER_ID = RandomTestUtil.randomLong();

	private static final MockedStatic<CounterLocalServiceUtil>
		_counterLocalServiceUtilMockedStatic = Mockito.mockStatic(
			CounterLocalServiceUtil.class);
	private static final MockedStatic<FragmentEntryLinkLocalServiceUtil>
		_fragmentEntryLinkLocalServiceUtilMockedStatic = Mockito.mockStatic(
			FragmentEntryLinkLocalServiceUtil.class);
	private static final MockedStatic
		<LayoutPageTemplateStructureLocalServiceUtil>
			_layoutPageTemplateStructureLocalServiceUtilMockedStatic =
				Mockito.mockStatic(
					LayoutPageTemplateStructureLocalServiceUtil.class);
	private static final MockedStatic<LayoutStructureUtil>
		_layoutStructureUtilMockedStatic = Mockito.mockStatic(
			LayoutStructureUtil.class);
	private static final MockedStatic<PortletLocalServiceUtil>
		_portletLocalServiceUtilMockedStatic = Mockito.mockStatic(
			PortletLocalServiceUtil.class);
	private static final MockedStatic<PortletPermissionUtil>
		_portletPermissionUtilMockedStatic = Mockito.mockStatic(
			PortletPermissionUtil.class);
	private static final MockedStatic<PortletPreferencesLocalServiceUtil>
		_portletPreferencesLocalServiceUtilMockedStatic = Mockito.mockStatic(
			PortletPreferencesLocalServiceUtil.class);
	private static final MockedStatic<ResourcePermissionLocalServiceUtil>
		_resourcePermissionLocalServiceUtilMockedStatic = Mockito.mockStatic(
			ResourcePermissionLocalServiceUtil.class);
	private static final MockedStatic<SegmentsExperimentLocalServiceUtil>
		_segmentsExperimentLocalServiceUtilMockedStatic = Mockito.mockStatic(
			SegmentsExperimentLocalServiceUtil.class);

	private final CommentManager _commentManager = Mockito.mock(
		CommentManager.class);
	private final PortletRegistry _portletRegistry = Mockito.mock(
		PortletRegistry.class);

}
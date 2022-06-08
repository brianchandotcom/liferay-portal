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

package com.liferay.layout.test.util;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.segments.service.SegmentsExperienceLocalServiceUtil;

import java.util.Collection;
import java.util.Iterator;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Lourdes Fernández Besada
 */
public class ContentLayoutTestUtil {

	public static FragmentEntryLink addFragmentEntryLinkToLayout(
			Layout layout, long fragmentEntryId, long segmentsExperienceId,
			String css, String html, String js, String configuration,
			String editableValues, String rendererKey)
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			FragmentEntryLinkServiceUtil.addFragmentEntryLink(
				layout.getGroupId(), 0, fragmentEntryId, segmentsExperienceId,
				layout.getPlid(), css, html, js, configuration, editableValues,
				StringPool.BLANK, 0, rendererKey,
				ServiceContextTestUtil.getServiceContext(
					layout.getGroupId(), TestPropsValues.getUserId()));

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), layout.getPlid());

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(segmentsExperienceId));

		layoutStructure.addFragmentStyledLayoutStructureItem(
			fragmentEntryLink.getFragmentEntryLinkId(),
			layoutStructure.getMainItemId(), 0);

		LayoutPageTemplateStructureLocalServiceUtil.
			updateLayoutPageTemplateStructureData(
				layout.getGroupId(), layout.getPlid(), segmentsExperienceId,
				layoutStructure.toString());

		return fragmentEntryLink;
	}

	public static FragmentEntryLink addFragmentEntryLinkToLayout(
			Layout layout, long segmentsExperienceId, String editableValues)
		throws Exception {

		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.addFragmentEntry(
				TestPropsValues.getUserId(), layout.getGroupId(), 0,
				StringUtil.randomString(), StringUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), false, "{fieldSets: []}", null,
				0, FragmentConstants.TYPE_COMPONENT, null,
				WorkflowConstants.STATUS_APPROVED,
				ServiceContextTestUtil.getServiceContext(
					layout.getGroupId(), TestPropsValues.getUserId()));

		return addFragmentEntryLinkToLayout(
			layout, fragmentEntry.getFragmentEntryId(), segmentsExperienceId,
			fragmentEntry.getCss(), fragmentEntry.getHtml(),
			fragmentEntry.getJs(), fragmentEntry.getConfiguration(),
			editableValues, fragmentEntry.getFragmentEntryKey());
	}

	public static JSONObject addPortletToLayout(Layout layout, String portletId)
		throws Exception {

		MVCActionCommand addPortletMVCActionCommand = getMVCActionCommand(
			"/layout_content_page_editor/add_portlet");

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			getMockLiferayPortletActionRequest(
				CompanyLocalServiceUtil.getCompany(layout.getCompanyId()),
				GroupLocalServiceUtil.getGroup(layout.getGroupId()), layout);

		mockLiferayPortletActionRequest.addParameter("portletId", portletId);

		return ReflectionTestUtil.invoke(
			addPortletMVCActionCommand, "_processAddPortlet",
			new Class<?>[] {ActionRequest.class, ActionResponse.class},
			mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());
	}

	public static MockLiferayPortletActionRequest
			getMockLiferayPortletActionRequest(
				Company company, Group group, Layout layout)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE,
			new MockLiferayPortletActionResponse());
		mockLiferayPortletActionRequest.setAttribute(WebKeys.LAYOUT, layout);
		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, getThemeDisplay(company, group, layout));

		mockLiferayPortletActionRequest.addParameter(
			"segmentsExperienceId",
			String.valueOf(
				SegmentsExperienceLocalServiceUtil.
					fetchDefaultSegmentsExperienceId(layout.getPlid())));

		return mockLiferayPortletActionRequest;
	}

	public static MVCActionCommand getMVCActionCommand(String mvcCommandName) {
		try {
			Bundle bundle = FrameworkUtil.getBundle(
				ContentLayoutTestUtil.class);

			BundleContext bundleContext = bundle.getBundleContext();

			Collection<ServiceReference<MVCActionCommand>>
				mvcActionCommandReferences = bundleContext.getServiceReferences(
					MVCActionCommand.class,
					"(mvc.command.name=" + mvcCommandName + ")");

			Iterator<ServiceReference<MVCActionCommand>> iterator =
				mvcActionCommandReferences.iterator();

			return bundleContext.getService(iterator.next());
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public static ThemeDisplay getThemeDisplay(
			Company company, Group group, Layout layout)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(company);
		themeDisplay.setLayout(layout);
		themeDisplay.setLayoutSet(layout.getLayoutSet());
		themeDisplay.setLayoutTypePortlet(
			(LayoutTypePortlet)layout.getLayoutType());
		themeDisplay.setLocale(PortalUtil.getSiteDefaultLocale(group));

		LayoutSet layoutSet = group.getPublicLayoutSet();

		themeDisplay.setLookAndFeel(layoutSet.getTheme(), null);

		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setPlid(layout.getPlid());
		themeDisplay.setRealUser(TestPropsValues.getUser());
		themeDisplay.setScopeGroupId(group.getGroupId());
		themeDisplay.setSiteGroupId(group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	public static void publishLayout(Layout draftLayout, Layout layout)
		throws Exception {

		MVCActionCommand publishLayoutMVCActionCommand = getMVCActionCommand(
			"/layout_content_page_editor/publish_layout");

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(layout.getGroupId());
		serviceContext.setUserId(TestPropsValues.getUserId());

		try {
			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			ReflectionTestUtil.invoke(
				publishLayoutMVCActionCommand, "_publishLayout",
				new Class<?>[] {
					Layout.class, Layout.class, ServiceContext.class, long.class
				},
				draftLayout, layout, serviceContext,
				TestPropsValues.getUserId());
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.test.util;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.layout.provider.LayoutStructureProvider;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
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
import com.liferay.portal.kernel.servlet.PortletServlet;
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
import com.liferay.portal.kernel.util.Validator;
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

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Lourdes Fernández Besada
 */
public class ContentLayoutTestUtil {

	public static JSONObject addFormToLayout(
			boolean addCaptcha, String classNameId, String classTypeId,
			Layout layout, LayoutStructureProvider layoutStructureProvider,
			long segmentsExperienceId, InfoField... infoFields)
		throws Exception {

		return addFormToLayout(
			addCaptcha, classNameId, classTypeId, _INPUT_HTML, layout,
			layoutStructureProvider, segmentsExperienceId, infoFields);
	}

	public static JSONObject addFormToLayout(
			boolean addCaptcha, String classNameId, String classTypeId,
			String inputHTML, Layout layout,
			LayoutStructureProvider layoutStructureProvider,
			long segmentsExperienceId, InfoField... infoFields)
		throws Exception {

		JSONObject jsonObject = addItemToLayout(
			JSONUtil.put(
				"classNameId", classNameId
			).put(
				"classTypeId", classTypeId
			).toString(),
			LayoutDataItemTypeConstants.TYPE_FORM, layout,
			layoutStructureProvider, segmentsExperienceId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				layout.getGroupId(), TestPropsValues.getUserId());
		String parentItemId = jsonObject.getString("addedItemId");

		for (int i = 0; i < infoFields.length; i++) {
			InfoField infoField = infoFields[i];

			InfoFieldType infoFieldType = infoField.getInfoFieldType();

			FragmentEntry fragmentEntry =
				FragmentEntryLocalServiceUtil.addFragmentEntry(
					TestPropsValues.getUserId(), layout.getGroupId(), 0,
					StringUtil.randomString(), StringUtil.randomString(),
					RandomTestUtil.randomString(), inputHTML,
					RandomTestUtil.randomString(), false, "{fieldSets: []}",
					null, 0, FragmentConstants.TYPE_INPUT,
					JSONUtil.put(
						"fieldTypes", JSONUtil.put(infoFieldType.getName())
					).toString(),
					WorkflowConstants.STATUS_APPROVED, serviceContext);

			addFragmentEntryLinkToLayout(
				JSONUtil.put(
					FragmentEntryProcessorConstants.
						KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR,
					JSONUtil.put("inputFieldId", infoField.getUniqueId())
				).toString(),
				fragmentEntry.getCss(), fragmentEntry.getConfiguration(),
				fragmentEntry.getFragmentEntryId(), fragmentEntry.getHtml(),
				fragmentEntry.getJs(), layout,
				fragmentEntry.getFragmentEntryKey(), fragmentEntry.getType(),
				parentItemId, i, segmentsExperienceId);
		}

		if (addCaptcha) {
			FragmentEntry fragmentEntry =
				FragmentEntryLocalServiceUtil.addFragmentEntry(
					TestPropsValues.getUserId(), layout.getGroupId(), 0,
					StringUtil.randomString(), StringUtil.randomString(),
					RandomTestUtil.randomString(), inputHTML,
					RandomTestUtil.randomString(), false, "{fieldSets: []}",
					null, 0, FragmentConstants.TYPE_INPUT,
					JSONUtil.put(
						"fieldTypes", JSONUtil.put("captcha")
					).toString(),
					WorkflowConstants.STATUS_APPROVED, serviceContext);

			addFragmentEntryLinkToLayout(
				StringPool.BLANK, fragmentEntry.getCss(),
				fragmentEntry.getConfiguration(),
				fragmentEntry.getFragmentEntryId(), fragmentEntry.getHtml(),
				fragmentEntry.getJs(), layout,
				fragmentEntry.getFragmentEntryKey(), fragmentEntry.getType(),
				parentItemId, infoFields.length, segmentsExperienceId);
		}

		jsonObject.put(
			"layoutData",
			layoutStructureProvider.getLayoutStructure(
				layout.getPlid(), segmentsExperienceId));

		return jsonObject;
	}

	public static String addFormToPublishedLayout(
			boolean addCaptcha, String classNameId, String classTypeId,
			Layout layout, LayoutStructureProvider layoutStructureProvider,
			InfoField<?>... infoField)
		throws Exception {

		Layout draftLayout = layout.fetchDraftLayout();

		JSONObject jsonObject = addFormToLayout(
			addCaptcha, classNameId, classTypeId, draftLayout,
			layoutStructureProvider,
			SegmentsExperienceLocalServiceUtil.fetchDefaultSegmentsExperienceId(
				draftLayout.getPlid()),
			infoField);

		publishLayout(draftLayout, layout);

		return jsonObject.getString("addedItemId");
	}

	public static FragmentEntryLink addFragmentEntryLinkToLayout(
			String editableValues, Layout layout, long segmentsExperienceId)
		throws Exception {

		return addFragmentEntryLinkToLayout(
			editableValues, layout, null, 0, segmentsExperienceId);
	}

	public static FragmentEntryLink addFragmentEntryLinkToLayout(
			String editableValues, Layout layout, String parentItemId,
			int position, long segmentsExperienceId)
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
			editableValues, fragmentEntry.getCss(),
			fragmentEntry.getConfiguration(),
			fragmentEntry.getFragmentEntryId(), fragmentEntry.getHtml(),
			fragmentEntry.getJs(), layout, fragmentEntry.getFragmentEntryKey(),
			fragmentEntry.getType(), parentItemId, position,
			segmentsExperienceId);
	}

	public static FragmentEntryLink addFragmentEntryLinkToLayout(
			String editableValues, String css, String configuration,
			long fragmentEntryId, String html, String js, Layout layout,
			String rendererKey, int type, String parentItemId, int position,
			long segmentsExperienceId)
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			FragmentEntryLinkServiceUtil.addFragmentEntryLink(
				layout.getGroupId(), 0, fragmentEntryId, segmentsExperienceId,
				layout.getPlid(), css, html, js, configuration, editableValues,
				StringPool.BLANK, 0, rendererKey, type,
				ServiceContextTestUtil.getServiceContext(
					layout.getGroupId(), TestPropsValues.getUserId()));

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), layout.getPlid());

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(segmentsExperienceId));

		if (Validator.isNull(parentItemId)) {
			layoutStructure.addFragmentStyledLayoutStructureItem(
				fragmentEntryLink.getFragmentEntryLinkId(),
				layoutStructure.getMainItemId(), position);
		}
		else {
			layoutStructure.addFragmentStyledLayoutStructureItem(
				fragmentEntryLink.getFragmentEntryLinkId(), parentItemId,
				position);
		}

		LayoutPageTemplateStructureLocalServiceUtil.
			updateLayoutPageTemplateStructureData(
				layout.getGroupId(), layout.getPlid(), segmentsExperienceId,
				layoutStructure.toString());

		return fragmentEntryLink;
	}

	public static FragmentEntryLink addFragmentEntryLinkToLayout(
			String editableValues, String css, String configuration,
			long fragmentEntryId, String html, String js, Layout layout,
			String rendererKey, long segmentsExperienceId, int type)
		throws Exception {

		return addFragmentEntryLinkToLayout(
			editableValues, css, configuration, fragmentEntryId, html, js,
			layout, rendererKey, type, null, 0, segmentsExperienceId);
	}

	public static JSONObject addItemToLayout(
			String itemConfig, String itemType, Layout layout,
			LayoutStructureProvider layoutStructureProvider,
			long segmentsExperienceId)
		throws Exception {

		LayoutStructure layoutStructure =
			layoutStructureProvider.getLayoutStructure(
				layout.getPlid(), segmentsExperienceId);

		return addItemToLayout(
			itemConfig, itemType, layout, layoutStructure.getMainItemId(), 0,
			segmentsExperienceId);
	}

	public static JSONObject addItemToLayout(
			String itemConfig, String itemType, Layout layout,
			String parentItemId, int position, long segmentsExperienceId)
		throws Exception {

		MVCActionCommand mvcActionCommand = getMVCActionCommand(
			"/layout_content_page_editor/add_item");

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			getMockLiferayPortletActionRequest(
				CompanyLocalServiceUtil.getCompany(layout.getCompanyId()),
				GroupLocalServiceUtil.getGroup(layout.getGroupId()), layout);

		mockLiferayPortletActionRequest.addParameter("itemType", itemType);
		mockLiferayPortletActionRequest.addParameter(
			"parentItemId", parentItemId);
		mockLiferayPortletActionRequest.addParameter(
			"position", String.valueOf(position));
		mockLiferayPortletActionRequest.addParameter(
			"segmentsExperienceId", String.valueOf(segmentsExperienceId));

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			mvcActionCommand, "_addItemToLayoutData",
			new Class<?>[] {ActionRequest.class},
			mockLiferayPortletActionRequest);

		mvcActionCommand = getMVCActionCommand(
			"/layout_content_page_editor/update_item_config");

		mockLiferayPortletActionRequest = getMockLiferayPortletActionRequest(
			CompanyLocalServiceUtil.getCompany(layout.getCompanyId()),
			GroupLocalServiceUtil.getGroup(layout.getGroupId()), layout);

		mockLiferayPortletActionRequest.addParameter("itemConfig", itemConfig);
		mockLiferayPortletActionRequest.addParameter(
			"itemId", jsonObject.getString("addedItemId"));

		JSONObject responseJSONObject = (JSONObject)ReflectionTestUtil.invoke(
			mvcActionCommand, "_updateItemConfig",
			new Class<?>[] {ActionRequest.class, ActionResponse.class},
			mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());

		jsonObject.put(
			"layoutData", responseJSONObject.getJSONObject("layoutData")
		).put(
			"pageContents", responseJSONObject.getJSONObject("pageContents")
		);

		return jsonObject;
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

	public static MockHttpServletRequest getMockHttpServletRequest(
			Company company, Group group, Layout layout)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(WebKeys.LAYOUT, layout);
		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, getThemeDisplay(company, group, layout));

		return mockHttpServletRequest;
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

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(WebKeys.LAYOUT, layout);

		ThemeDisplay themeDisplay = getThemeDisplay(company, group, layout);

		themeDisplay.setRequest(mockHttpServletRequest);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockLiferayPortletActionRequest.setAttribute(
			PortletServlet.PORTLET_SERVLET_REQUEST, mockHttpServletRequest);
		mockLiferayPortletActionRequest.setAttribute(WebKeys.LAYOUT, layout);
		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

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

		themeDisplay.setLookAndFeel(
			layoutSet.getTheme(), layoutSet.getColorScheme());

		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setPlid(layout.getPlid());
		themeDisplay.setRealUser(TestPropsValues.getUser());
		themeDisplay.setScopeGroupId(group.getGroupId());
		themeDisplay.setSiteGroupId(group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	public static JSONObject markItemForDeletionFromLayout(
			String itemId, Layout layout, String portletId)
		throws Exception {

		MVCActionCommand markItemForDeletionMVCActionCommand =
			getMVCActionCommand(
				"/layout_content_page_editor/mark_item_for_deletion");

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			getMockLiferayPortletActionRequest(
				CompanyLocalServiceUtil.getCompany(layout.getCompanyId()),
				GroupLocalServiceUtil.getGroup(layout.getGroupId()), layout);

		mockLiferayPortletActionRequest.addParameter("itemId", itemId);
		mockLiferayPortletActionRequest.addParameter(
			"portletIds", new String[] {portletId});

		return ReflectionTestUtil.invoke(
			markItemForDeletionMVCActionCommand, "doTransactionalCommand",
			new Class<?>[] {ActionRequest.class, ActionResponse.class},
			mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());
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
					ActionRequest.class, ActionResponse.class, Layout.class,
					Layout.class, ServiceContext.class, long.class
				},
				getMockLiferayPortletActionRequest(
					CompanyLocalServiceUtil.getCompany(layout.getCompanyId()),
					GroupLocalServiceUtil.getGroup(layout.getGroupId()),
					layout),
				new MockLiferayPortletActionResponse(), draftLayout, layout,
				serviceContext, TestPropsValues.getUserId());
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	private static final String _INPUT_HTML = StringBundler.concat(
		"<div class=\"${fragmentEntryLinkNamespace}-input\">",
		"<div id=\"${fragmentEntryLinkNamespace}-inputTemplateNode\">",
		"<p>InputName:${input.name}</p>",
		"<p>InputJSONObject:${input.toJSONObject()}</p></div></div>");

}
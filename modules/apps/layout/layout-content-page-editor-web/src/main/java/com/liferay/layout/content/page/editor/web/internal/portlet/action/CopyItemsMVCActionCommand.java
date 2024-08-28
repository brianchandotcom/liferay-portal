/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.exception.NoSuchEntryLinkException;
import com.liferay.fragment.listener.FragmentEntryLinkListener;
import com.liferay.fragment.listener.FragmentEntryLinkListenerRegistry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLinkService;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.web.internal.exception.NoninstanceablePortletException;
import com.liferay.layout.content.page.editor.web.internal.manager.ContentManager;
import com.liferay.layout.content.page.editor.web.internal.manager.FragmentEntryLinkManager;
import com.liferay.layout.content.page.editor.web.internal.util.layout.structure.LayoutStructureUtil;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.LockedLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferencesIds;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Georgel Pop
 */
@Component(
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/copy_items"
	},
	service = MVCActionCommand.class
)
public class CopyItemsMVCActionCommand
	extends BaseContentPageEditorTransactionalMVCActionCommand {

	@Override
	protected JSONObject doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONObject jsonObject =
			_addCopiedFragmentEntryLinkToLayoutDataJSONObject(
				actionRequest, actionResponse);

		SessionMessages.add(actionRequest, "fragmentEntryLinkCopy");

		return jsonObject;
	}

	@Override
	protected JSONObject processException(
		ActionRequest actionRequest, Exception exception) {

		if (exception instanceof LockedLayoutException) {
			return processLockedLayoutException(actionRequest);
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String errorMessage = StringPool.BLANK;

		if (exception instanceof NoSuchEntryLinkException) {
			errorMessage = _language.get(
				themeDisplay.getRequest(),
				"the-section-could-not-be-copied-because-it-has-been-deleted");
		}
		else if (exception instanceof NoninstanceablePortletException) {
			NoninstanceablePortletException noninstanceablePortletException =
				(NoninstanceablePortletException)exception;

			Portlet portlet = _portletLocalService.getPortletById(
				themeDisplay.getCompanyId(),
				noninstanceablePortletException.getPortletId());

			HttpServletRequest httpServletRequest =
				_portal.getHttpServletRequest(actionRequest);

			HttpSession httpSession = httpServletRequest.getSession();

			errorMessage = _language.format(
				themeDisplay.getRequest(),
				"the-layout-could-not-be-copied-because-it-contains-a-widget-" +
					"x-that-can-only-appear-once-in-the-page",
				_portal.getPortletTitle(
					portlet, httpSession.getServletContext(),
					themeDisplay.getLocale()));
		}
		else {
			errorMessage = _language.get(
				themeDisplay.getRequest(), "an-unexpected-error-occurred");
		}

		return JSONUtil.put("error", errorMessage);
	}

	private JSONObject _addCopiedFragmentEntryLinkToLayoutDataJSONObject(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long segmentsExperienceId = ParamUtil.getLong(
			actionRequest, "segmentsExperienceId");

		String parentItemId = ParamUtil.getString(
			actionRequest, "parentItemId");

		String[] itemIds = null;

		String itemId = ParamUtil.getString(actionRequest, "itemId");

		if (Validator.isNotNull(itemId)) {
			itemIds = new String[] {itemId};
		}
		else {
			itemIds = ParamUtil.getStringValues(actionRequest, "itemIds");
		}

		String[] finalItemIds = itemIds;

		Map<Long, Long> copiedFragmentEntryLinkIdsMap = new HashMap<>();
		List<String> copiedLayoutStructureItemIds = new ArrayList<>();

		LayoutStructureUtil.updateLayoutPageTemplateData(
			themeDisplay.getScopeGroupId(), segmentsExperienceId,
			themeDisplay.getPlid(),
			layoutStructure -> {
				List<LayoutStructureItem> copiedLayoutStructureItems =
					layoutStructure.copyLayoutStructureItems(
						Arrays.asList(finalItemIds), parentItemId);

				for (LayoutStructureItem copiedLayoutStructureItem :
						copiedLayoutStructureItems) {

					copiedLayoutStructureItemIds.add(
						copiedLayoutStructureItem.getItemId());

					if (!(copiedLayoutStructureItem instanceof
							FragmentStyledLayoutStructureItem)) {

						continue;
					}

					FragmentStyledLayoutStructureItem
						fragmentStyledLayoutStructureItem =
							(FragmentStyledLayoutStructureItem)
								copiedLayoutStructureItem;

					long originalFragmentEntryLinkId =
						fragmentStyledLayoutStructureItem.
							getFragmentEntryLinkId();

					long fragmentEntryLinkId = _copyFragmentEntryLink(
						actionRequest, originalFragmentEntryLinkId);

					layoutStructure.updateItemConfig(
						JSONUtil.put(
							"fragmentEntryLinkId", fragmentEntryLinkId),
						copiedLayoutStructureItem.getItemId());

					copiedFragmentEntryLinkIdsMap.put(
						fragmentEntryLinkId, originalFragmentEntryLinkId);
				}
			});

		for (Map.Entry<Long, Long> entry :
				copiedFragmentEntryLinkIdsMap.entrySet()) {

			FragmentEntryLink copiedFragmentEntryLink =
				_fragmentEntryLinkLocalService.getFragmentEntryLink(
					entry.getKey());

			FragmentEntryLink originalFragmentEntryLink =
				_fragmentEntryLinkLocalService.getFragmentEntryLink(
					entry.getValue());

			for (FragmentEntryLinkListener fragmentEntryLinkListener :
					_fragmentEntryLinkListenerRegistry.
						getFragmentEntryLinkListeners()) {

				fragmentEntryLinkListener.onCopyFragmentEntryLink(
					copiedFragmentEntryLink, originalFragmentEntryLink);
			}
		}

		LayoutStructure layoutStructure =
			LayoutStructureUtil.getLayoutStructure(
				themeDisplay.getScopeGroupId(), themeDisplay.getPlid(),
				segmentsExperienceId);

		JSONObject layoutDataJSONObject = layoutStructure.toJSONObject();

		return JSONUtil.put(
			"copiedFragmentEntryLinks",
			_getCopiedFragmentEntryLinksJSONArray(
				actionRequest, actionResponse,
				copiedFragmentEntryLinkIdsMap.keySet(), segmentsExperienceId,
				themeDisplay)
		).put(
			"copiedItemIds", copiedLayoutStructureItemIds
		).put(
			"layoutData", layoutDataJSONObject
		).put(
			"restrictedItemIds",
			_contentManager.getRestrictedItemIds(
				_portal.getHttpServletRequest(actionRequest),
				LayoutStructureUtil.getLayoutStructure(
					themeDisplay.getScopeGroupId(), themeDisplay.getPlid(),
					segmentsExperienceId),
				themeDisplay)
		);
	}

	private long _copyFragmentEntryLink(
			ActionRequest actionRequest, long fragmentEntryLinkId)
		throws PortalException {

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.getFragmentEntryLink(
				fragmentEntryLinkId);

		JSONObject editableValuesJSONObject = _jsonFactory.createJSONObject(
			fragmentEntryLink.getEditableValues());

		String portletId = editableValuesJSONObject.getString("portletId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		String namespace = StringUtil.randomId();

		if (Validator.isNotNull(portletId)) {
			Portlet portlet = _portletLocalService.getPortletById(portletId);

			if (!portlet.isInstanceable()) {
				throw new NoninstanceablePortletException(portletId);
			}

			String oldInstanceId = editableValuesJSONObject.getString(
				"instanceId");

			editableValuesJSONObject.put("instanceId", namespace);

			_copyPortletPermissions(
				fragmentEntryLink.getCompanyId(),
				fragmentEntryLink.getGroupId(), namespace, oldInstanceId,
				fragmentEntryLink.getPlid(), portletId);
			_copyPortletPreferences(
				serviceContext.getRequest(), portletId, oldInstanceId,
				namespace);
		}

		if (fragmentEntryLink.isTypeInput()) {
			JSONObject freemarkerFragmentEntryProcessorJSONObject =
				editableValuesJSONObject.getJSONObject(
					FragmentEntryProcessorConstants.
						KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR);

			if (freemarkerFragmentEntryProcessorJSONObject != null) {
				freemarkerFragmentEntryProcessorJSONObject.remove(
					"inputFieldId");
			}
		}

		FragmentEntryLink copiedFragmentEntryLink =
			_fragmentEntryLinkService.addFragmentEntryLink(
				null, fragmentEntryLink.getGroupId(), 0,
				fragmentEntryLink.getFragmentEntryId(),
				fragmentEntryLink.getSegmentsExperienceId(),
				fragmentEntryLink.getPlid(), fragmentEntryLink.getCss(),
				fragmentEntryLink.getHtml(), fragmentEntryLink.getJs(),
				fragmentEntryLink.getConfiguration(),
				editableValuesJSONObject.toString(), namespace, 0,
				fragmentEntryLink.getRendererKey(), fragmentEntryLink.getType(),
				serviceContext);

		return copiedFragmentEntryLink.getFragmentEntryLinkId();
	}

	private void _copyPortletPermissions(
			long companyId, long groupId, String newInstanceId,
			String oldInstanceId, long plid, String portletId)
		throws PortalException {

		String sourceResourcePrimKey = PortletPermissionUtil.getPrimaryKey(
			plid, PortletIdCodec.encode(portletId, oldInstanceId));
		String targetResourcePrimKey = PortletPermissionUtil.getPrimaryKey(
			plid, PortletIdCodec.encode(portletId, newInstanceId));
		List<String> actionIds = ResourceActionsUtil.getPortletResourceActions(
			portletId);

		List<Role> roles = _roleLocalService.getGroupRelatedRoles(groupId);

		for (Role role : roles) {
			List<String> actions =
				_resourcePermissionLocalService.
					getAvailableResourcePermissionActionIds(
						companyId, portletId,
						ResourceConstants.SCOPE_INDIVIDUAL,
						sourceResourcePrimKey, role.getRoleId(), actionIds);

			_resourcePermissionLocalService.setResourcePermissions(
				companyId, portletId, ResourceConstants.SCOPE_INDIVIDUAL,
				targetResourcePrimKey, role.getRoleId(),
				actions.toArray(new String[0]));
		}
	}

	private void _copyPortletPreferences(
			HttpServletRequest httpServletRequest, String portletId,
			String oldInstanceId, String newInstanceId)
		throws PortalException {

		PortletPreferences portletPreferences =
			_portletPreferencesFactory.getPortletPreferences(
				httpServletRequest,
				PortletIdCodec.encode(portletId, oldInstanceId));

		PortletPreferencesIds portletPreferencesIds =
			_portletPreferencesFactory.getPortletPreferencesIds(
				httpServletRequest,
				PortletIdCodec.encode(portletId, oldInstanceId));

		_portletPreferencesLocalService.addPortletPreferences(
			portletPreferencesIds.getCompanyId(),
			portletPreferencesIds.getOwnerId(),
			portletPreferencesIds.getOwnerType(),
			portletPreferencesIds.getPlid(),
			PortletIdCodec.encode(portletId, newInstanceId), null,
			PortletPreferencesFactoryUtil.toXML(portletPreferences));
	}

	private JSONArray _getCopiedFragmentEntryLinksJSONArray(
			ActionRequest actionRequest, ActionResponse actionResponse,
			Set<Long> copiedFragmentEntryLinkIds, long segmentsExperienceId,
			ThemeDisplay themeDisplay)
		throws Exception {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		LayoutStructure layoutStructure =
			LayoutStructureUtil.getLayoutStructure(
				themeDisplay.getScopeGroupId(), themeDisplay.getPlid(),
				segmentsExperienceId);

		for (long fragmentEntryLinkId : copiedFragmentEntryLinkIds) {
			jsonArray.put(
				_fragmentEntryLinkManager.getFragmentEntryLinkJSONObject(
					_fragmentEntryLinkLocalService.getFragmentEntryLink(
						fragmentEntryLinkId),
					_portal.getHttpServletRequest(actionRequest),
					_portal.getHttpServletResponse(actionResponse),
					layoutStructure));
		}

		return jsonArray;
	}

	@Reference
	private ContentManager _contentManager;

	@Reference
	private FragmentEntryLinkListenerRegistry
		_fragmentEntryLinkListenerRegistry;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentEntryLinkManager _fragmentEntryLinkManager;

	@Reference
	private FragmentEntryLinkService _fragmentEntryLinkService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private PortletPreferencesFactory _portletPreferencesFactory;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}
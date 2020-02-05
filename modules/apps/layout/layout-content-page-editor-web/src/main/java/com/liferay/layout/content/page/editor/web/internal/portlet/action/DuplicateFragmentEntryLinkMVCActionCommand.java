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

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.exception.NoSuchEntryLinkException;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.renderer.constants.FragmentRendererConstants;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLinkService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.web.internal.excecption.NoninstanceablePortletException;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.PortletIdException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferencesIds;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/content_layout/duplicate_fragment_entry_link"
	},
	service = MVCActionCommand.class
)
public class DuplicateFragmentEntryLinkMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONObject jsonObject = _duplicateFragmentEntryLink(actionRequest);

		hideDefaultSuccessMessage(actionRequest);

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
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

	private JSONObject _duplicateFragmentEntryLink(
		ActionRequest actionRequest) {

		long fragmentEntryLinkId = ParamUtil.getLong(
			actionRequest, "fragmentEntryLinkId");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			FragmentEntryLink fragmentEntryLink =
				_fragmentEntryLinkLocalService.getFragmentEntryLink(
					fragmentEntryLinkId);

			JSONObject editableValuesJSONObject =
				JSONFactoryUtil.createJSONObject(
					fragmentEntryLink.getEditableValues());

			String portletId = editableValuesJSONObject.getString("portletId");

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				actionRequest);

			if (Validator.isNotNull(portletId)) {
				Portlet portlet = _portletLocalService.getPortletById(
					portletId);

				if (!portlet.isInstanceable()) {
					throw new PortletIdException();
				}

				String oldInstanceId = editableValuesJSONObject.getString(
					"instanceId");

				String newInstanceId = PortletIdCodec.generateInstanceId();

				editableValuesJSONObject.put("instanceId", newInstanceId);

				_copyPortletPreferences(
					serviceContext.getRequest(), portletId, oldInstanceId,
					newInstanceId);
			}

			FragmentEntryLink duplicateFragmentEntryLink =
				_fragmentEntryLinkService.addFragmentEntryLink(
					fragmentEntryLink.getGroupId(),
					fragmentEntryLink.getOriginalFragmentEntryLinkId(),
					fragmentEntryLink.getFragmentEntryId(),
					fragmentEntryLink.getClassNameId(),
					fragmentEntryLink.getClassPK(), fragmentEntryLink.getCss(),
					fragmentEntryLink.getHtml(), fragmentEntryLink.getJs(),
					fragmentEntryLink.getConfiguration(),
					editableValuesJSONObject.toString(), StringUtil.randomId(),
					0, fragmentEntryLink.getRendererKey(), serviceContext);

			DefaultFragmentRendererContext fragmentRendererContext =
				new DefaultFragmentRendererContext(duplicateFragmentEntryLink);

			fragmentRendererContext.setLocale(serviceContext.getLocale());
			fragmentRendererContext.setMode(FragmentEntryLinkConstants.EDIT);

			jsonObject.put(
				"configuration",
				JSONFactoryUtil.createJSONObject(
					_fragmentRendererController.getConfiguration(
						fragmentRendererContext))
			).put(
				"defaultConfigurationValues",
				_fragmentEntryConfigurationParser.
					getConfigurationDefaultValuesJSONObject(
						duplicateFragmentEntryLink.getConfiguration())
			).put(
				"editableValues",
				JSONFactoryUtil.createJSONObject(
					duplicateFragmentEntryLink.getEditableValues())
			).put(
				"fragmentEntryLinkId",
				duplicateFragmentEntryLink.getFragmentEntryLinkId()
			);

			FragmentEntry fragmentEntry = _getFragmentEntry(
				fragmentEntryLink.getFragmentEntryId(),
				fragmentEntryLink.getRendererKey(), serviceContext);

			String fragmentEntryKey = null;
			String name = null;

			if (fragmentEntry != null) {
				fragmentEntryKey = fragmentEntry.getFragmentEntryKey();
				name = fragmentEntry.getName();
			}
			else {
				String rendererKey = fragmentEntryLink.getRendererKey();

				if (Validator.isNull(rendererKey)) {
					rendererKey =
						FragmentRendererConstants.
							FRAGMENT_ENTRY_FRAGMENT_RENDERER_KEY;
				}

				FragmentRenderer fragmentRenderer =
					_fragmentRendererTracker.getFragmentRenderer(rendererKey);

				fragmentEntryKey = fragmentRenderer.getKey();

				name = fragmentRenderer.getLabel(serviceContext.getLocale());

				if (Validator.isNotNull(portletId)) {
					name = _portal.getPortletTitle(
						portletId, serviceContext.getLocale());
				}
			}

			jsonObject.put(
				"content",
				_fragmentRendererController.render(
					fragmentRendererContext, serviceContext.getRequest(),
					serviceContext.getResponse())
			).put(
				"fragmentEntryKey", fragmentEntryKey
			).put(
				"name", name
			);

			SessionMessages.add(actionRequest, "fragmentEntryLinkDuplicated");
		}
		catch (PortalException portalException) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			String errorMessage = StringPool.BLANK;

			if (portalException instanceof NoSuchEntryLinkException) {
				errorMessage = LanguageUtil.get(
					themeDisplay.getRequest(),
					"the-section-could-not-be-duplicated-because-it-has-been-" +
						"deleted");
			}
			else if (portalException instanceof
						NoninstanceablePortletException) {

				NoninstanceablePortletException
					noninstanceablePortletException =
						(NoninstanceablePortletException)portalException;

				HttpServletRequest httpServletRequest =
					_portal.getHttpServletRequest(actionRequest);

				HttpSession session = httpServletRequest.getSession();

				ServletContext servletContext = session.getServletContext();

				Portlet portlet = _portletLocalService.getPortletById(
					themeDisplay.getCompanyId(),
					noninstanceablePortletException.getPortletId());

				errorMessage = LanguageUtil.format(
					themeDisplay.getRequest(),
					"the-layout-could-not-be-duplicated-because-it-contains-" +
						"a-widget-x-that-can-only-appear-once-in-the-page",
					_portal.getPortletTitle(
						portlet, servletContext, themeDisplay.getLocale()));
			}
			else {
				errorMessage = LanguageUtil.get(
					themeDisplay.getRequest(), "an-unexpected-error-occurred");
			}

			jsonObject.put("error", errorMessage);
		}

		return jsonObject;
	}

	private FragmentEntry _getContributedFragmentEntry(
		String fragmentEntryKey, Locale locale) {

		Map<String, FragmentEntry> fragmentEntries =
			_fragmentCollectionContributorTracker.getFragmentEntries(locale);

		return fragmentEntries.get(fragmentEntryKey);
	}

	private FragmentEntry _getFragmentEntry(
		long fragmentEntryId, String rendererKey,
		ServiceContext serviceContext) {

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.fetchFragmentEntry(fragmentEntryId);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		return _getContributedFragmentEntry(
			rendererKey, serviceContext.getLocale());
	}

	@Reference
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentEntryLinkService _fragmentEntryLinkService;

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference
	private FragmentRendererController _fragmentRendererController;

	@Reference
	private FragmentRendererTracker _fragmentRendererTracker;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private PortletPreferencesFactory _portletPreferencesFactory;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

}
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

package com.liferay.change.tracking.web.internal.portlet.action;

import com.liferay.change.tracking.closure.CTClosureFactory;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTCollectionService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.change.tracking.service.CTSchemaVersionLocalService;
import com.liferay.change.tracking.web.internal.configuration.CTConfiguration;
import com.liferay.change.tracking.web.internal.constants.CTWebKeys;
import com.liferay.change.tracking.web.internal.display.BasePersistenceRegistry;
import com.liferay.change.tracking.web.internal.display.CTDisplayRendererRegistry;
import com.liferay.change.tracking.web.internal.display.context.PublicationsDisplayContext;
import com.liferay.change.tracking.web.internal.display.context.ViewChangesDisplayContext;
import com.liferay.change.tracking.web.internal.scheduler.PublishScheduler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Samuel Trong Tran
 */
@Component(
	configurationPid = "com.liferay.change.tracking.web.internal.configuration.CTConfiguration",
	property = {
		"javax.portlet.name=" + CTPortletKeys.PUBLICATIONS,
		"mvc.command.name=/change_tracking/view_changes"
	},
	service = MVCRenderCommand.class
)
public class ViewChangesMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		CTPreferences ctPreferences =
			_ctPreferencesLocalService.fetchCTPreferences(
				themeDisplay.getCompanyId(), themeDisplay.getUserId());

		long activeCtCollectionId = CTConstants.CT_COLLECTION_ID_PRODUCTION;

		if (ctPreferences != null) {
			activeCtCollectionId = ctPreferences.getCtCollectionId();
		}

		long ctCollectionId = ParamUtil.getLong(
			renderRequest, "ctCollectionId");

		CTCollection ctCollection = _ctCollectionLocalService.fetchCTCollection(
			ctCollectionId);

		try {
			if ((ctCollection == null) ||
				!_ctCollectionModelResourcePermission.contains(
					themeDisplay.getPermissionChecker(), ctCollection,
					ActionKeys.VIEW)) {

				return "/publications/view_publications.jsp";
			}

			ViewChangesDisplayContext viewChangesDisplayContext =
				new ViewChangesDisplayContext(
					activeCtCollectionId, _basePersistenceRegistry,
					_ctClosureFactory, ctCollection, _ctCollectionLocalService,
					_ctConfiguration, _ctDisplayRendererRegistry,
					_ctEntryLocalService, _ctSchemaVersionLocalService,
					_groupLocalService, _language, _portal,
					new PublicationsDisplayContext(
						_ctCollectionLocalService, _ctCollectionService,
						_ctDisplayRendererRegistry, _ctEntryLocalService,
						_ctPreferencesLocalService,
						_portal.getHttpServletRequest(renderRequest), _language,
						renderRequest, renderResponse),
					_publishScheduler, renderRequest, renderResponse,
					_userLocalService);

			renderRequest.setAttribute(
				CTWebKeys.VIEW_CHANGES_DISPLAY_CONTEXT,
				viewChangesDisplayContext);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}

			return "/publications/view_publications.jsp";
		}

		return "/publications/view_changes.jsp";
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_ctConfiguration = ConfigurableUtil.createConfigurable(
			CTConfiguration.class, properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ViewChangesMVCRenderCommand.class);

	@Reference
	private BasePersistenceRegistry _basePersistenceRegistry;

	@Reference
	private CTClosureFactory _ctClosureFactory;

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.change.tracking.model.CTCollection)"
	)
	private ModelResourcePermission<CTCollection>
		_ctCollectionModelResourcePermission;

	@Reference
	private CTCollectionService _ctCollectionService;

	private volatile CTConfiguration _ctConfiguration;

	@Reference
	private CTDisplayRendererRegistry _ctDisplayRendererRegistry;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

	@Reference
	private CTSchemaVersionLocalService _ctSchemaVersionLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile PublishScheduler _publishScheduler;

	@Reference
	private UserLocalService _userLocalService;

}
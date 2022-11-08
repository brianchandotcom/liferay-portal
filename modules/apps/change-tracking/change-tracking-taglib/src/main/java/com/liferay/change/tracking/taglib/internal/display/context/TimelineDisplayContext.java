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

package com.liferay.change.tracking.taglib.internal.display.context;

import com.liferay.change.tracking.configuration.CTSettingsConfiguration;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalServiceUtil;
import com.liferay.change.tracking.spi.history.CTCollectionHistoryProvider;
import com.liferay.change.tracking.taglib.internal.security.permission.resource.CTCollectionPermission;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Noor Najjar
 */
public class TimelineDisplayContext {

	public TimelineDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		String className, long classPK) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_classPK = classPK;

		_classNameId = PortalUtil.getClassNameId(className);

		_themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<CTCollection> getCTCollections() throws PortalException {
		CTCollectionHistoryProvider<?> ctCollectionHistoryProvider =
			CTCollectionHistoryProviderRegistry.getCTCollectionHistoryProvider(
				_classNameId);

		return ctCollectionHistoryProvider.getCTCollections(
			_classNameId, _classPK);
	}

	public CTCollection getCurrentCTCollection() throws PortalException {
		long ctCollectionId = CTCollectionThreadLocal.getCTCollectionId();

		if (ctCollectionId != CTConstants.CT_COLLECTION_ID_PRODUCTION) {
			return CTCollectionLocalServiceUtil.getCTCollection(
				CTCollectionThreadLocal.getCTCollectionId());
		}

		return null;
	}

	public Map<String, Object> getDropdownReactData(CTCollection ctCollection)
		throws Exception {

		Map<String, Object> data = new HashMap<>();

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if ((ctCollection.getStatus() != WorkflowConstants.STATUS_EXPIRED) &&
			CTCollectionPermission.contains(
				permissionChecker, ctCollection, ActionKeys.UPDATE)) {

			data.put(
				"editURL",
				PortletURLBuilder.create(
					PortalUtil.getControlPanelPortletURL(
						PortalUtil.getHttpServletRequest(_renderRequest),
						_themeDisplay.getScopeGroup(),
						CTPortletKeys.PUBLICATIONS, 0, 0,
						PortletRequest.RENDER_PHASE)
				).setMVCRenderCommandName(
					"/change_tracking/edit_ct_collection"
				).setRedirect(
					_themeDisplay.getURLCurrent()
				).setParameter(
					"ctCollectionId", ctCollection.getCtCollectionId()
				).buildString());

			data.put(
				"revertURL",
				PortletURLBuilder.create(
					PortalUtil.getControlPanelPortletURL(
						PortalUtil.getHttpServletRequest(_renderRequest),
						_themeDisplay.getScopeGroup(),
						CTPortletKeys.PUBLICATIONS, 0, 0,
						PortletRequest.RENDER_PHASE)
				).setMVCRenderCommandName(
					"/change_tracking/undo_ct_collection"
				).setRedirect(
					_themeDisplay.getURLCurrent()
				).setParameter(
					"ctCollectionId", ctCollection.getCtCollectionId()
				).setParameter(
					"revert", Boolean.TRUE
				).buildString());
		}

		data.put(
			"reviewURL",
			PortletURLBuilder.create(
				PortalUtil.getControlPanelPortletURL(
					PortalUtil.getHttpServletRequest(_renderRequest),
					_themeDisplay.getScopeGroup(), CTPortletKeys.PUBLICATIONS,
					0, 0, PortletRequest.RENDER_PHASE)
			).setMVCRenderCommandName(
				"/change_tracking/view_changes"
			).setRedirect(
				_themeDisplay.getURLCurrent()
			).setParameter(
				"ctCollectionId", ctCollection.getCtCollectionId()
			).buildString());

		if ((ctCollection.getStatus() != WorkflowConstants.STATUS_APPROVED) &&
			CTCollectionPermission.contains(
				permissionChecker, ctCollection, ActionKeys.DELETE)) {

			data.put(
				"deleteURL",
				_getDeleteHref(
					PortalUtil.getHttpServletRequest(_renderRequest),
					_themeDisplay.getURLCurrent(),
					ctCollection.getCtCollectionId()));
		}

		return data;
	}

	public String getStatusLabel(int status) {
		if (status == WorkflowConstants.STATUS_APPROVED) {
			return "published";
		}
		else if (status == WorkflowConstants.STATUS_EXPIRED) {
			return "out-of-date";
		}
		else if (status == WorkflowConstants.STATUS_DRAFT) {
			return "in-progress";
		}
		else if (status == WorkflowConstants.STATUS_DENIED) {
			return "failed";
		}
		else if (status == WorkflowConstants.STATUS_SCHEDULED) {
			return "scheduled";
		}

		return StringPool.BLANK;
	}

	public String getStatusMessage(CTCollection ctCollection) {
		if (ctCollection == null) {
			return StringPool.BLANK;
		}

		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(_renderRequest);

		if (ctCollection.getStatus() == WorkflowConstants.STATUS_APPROVED) {
			Date modifiedDate = ctCollection.getStatusDate();

			return LanguageUtil.format(
				httpServletRequest, "published-x-ago-by-x",
				new String[] {
					LanguageUtil.getTimeDescription(
						httpServletRequest,
						System.currentTimeMillis() - modifiedDate.getTime(),
						true),
					HtmlUtil.escape(ctCollection.getUserName())
				});
		}

		if (ctCollection.getStatus() == WorkflowConstants.STATUS_DRAFT) {
			Date modifiedDate = ctCollection.getModifiedDate();

			return LanguageUtil.format(
				httpServletRequest, "modified-x-ago-by-x",
				new String[] {
					LanguageUtil.getTimeDescription(
						httpServletRequest,
						System.currentTimeMillis() - modifiedDate.getTime(),
						true),
					HtmlUtil.escape(ctCollection.getUserName())
				});
		}

		if (ctCollection.getStatus() == WorkflowConstants.STATUS_SCHEDULED) {
			try {
				SchedulerResponse schedulerResponse =
					SchedulerEngineHelperUtil.getScheduledJob(
						String.valueOf(ctCollection.getCtCollectionId()),
						_CT_COLLECTION_SCHEDULED_PUBLISH,
						StorageType.PERSISTED);

				if (schedulerResponse == null) {
					return null;
				}

				Date scheduledDate = SchedulerEngineHelperUtil.getStartTime(
					schedulerResponse);

				return LanguageUtil.format(
					httpServletRequest, "schedule-to-publish-in-x-by-x",
					new String[] {
						LanguageUtil.getTimeDescription(
							httpServletRequest,
							scheduledDate.getTime() -
								System.currentTimeMillis(),
							true),
						HtmlUtil.escape(ctCollection.getUserName())
					});
			}
			catch (SchedulerException schedulerException) {
				_log.error(schedulerException);
			}
		}

		return StringPool.BLANK;
	}

	public String getStatusStyle(int status) {
		if (status == WorkflowConstants.STATUS_EXPIRED) {
			return "warning";
		}

		return WorkflowConstants.getStatusStyle(status);
	}

	public boolean isPublicationsEnabled() {
		boolean publicationsEnabled = false;

		try {
			CTSettingsConfiguration ctSettingsConfiguration =
				ConfigurationProviderUtil.getCompanyConfiguration(
					CTSettingsConfiguration.class,
					_themeDisplay.getCompanyId());

			publicationsEnabled = ctSettingsConfiguration.enabled();
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return publicationsEnabled;
	}

	private String _getDeleteHref(
		HttpServletRequest httpServletRequest, String redirect,
		long ctCollectionId) {

		return StringBundler.concat(
			"javascript:Liferay.Util.openConfirmModal({message: '",
			LanguageUtil.get(
				httpServletRequest,
				"are-you-sure-you-want-to-delete-this-publication"),
			"', onConfirm: (isConfirmed) => {if (isConfirmed) {",
			"submitForm(document.hrefFm, '",
			PortletURLBuilder.create(
				PortalUtil.getControlPanelPortletURL(
					httpServletRequest, _themeDisplay.getScopeGroup(),
					CTPortletKeys.PUBLICATIONS, 0, 0,
					PortletRequest.ACTION_PHASE)
			).setActionName(
				"/change_tracking/delete_ct_collection"
			).setRedirect(
				redirect
			).setParameter(
				"ctCollectionId", ctCollectionId
			).buildString(),
			"');} else {self.focus();}}});");
	}

	private static final String _CT_COLLECTION_SCHEDULED_PUBLISH =
		"liferay/ct_collection_scheduled_publish";

	private static final Log _log = LogFactoryUtil.getLog(
		TimelineDisplayContext.class);

	private final long _classNameId;
	private final long _classPK;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}
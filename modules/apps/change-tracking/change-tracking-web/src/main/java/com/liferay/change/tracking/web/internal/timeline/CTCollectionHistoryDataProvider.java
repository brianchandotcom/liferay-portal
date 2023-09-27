/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.timeline;

import com.liferay.change.tracking.constants.CTDestinationNames;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.web.internal.security.permission.resource.CTCollectionPermission;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Noor Najjar
 */
public class CTCollectionHistoryDataProvider {

	public CTCollectionHistoryDataProvider(
		CTCollection ctCollection, HttpServletRequest httpServletRequest) {

		_ctCollection = ctCollection;
		_httpServletRequest = httpServletRequest;
	}

	public String getStatusMessage() {
		if (_ctCollection == null) {
			return StringPool.BLANK;
		}

		if (_ctCollection.getStatus() == WorkflowConstants.STATUS_APPROVED) {
			Date statusDate = _ctCollection.getStatusDate();

			return LanguageUtil.format(
				_httpServletRequest, "published-x-ago-by-x",
				new String[] {
					LanguageUtil.getTimeDescription(
						_httpServletRequest,
						System.currentTimeMillis() - statusDate.getTime(),
						true),
					HtmlUtil.escape(_ctCollection.getUserName())
				});
		}
		else if (_ctCollection.getStatus() == WorkflowConstants.STATUS_DRAFT) {
			Date modifiedDate = _ctCollection.getModifiedDate();

			return LanguageUtil.format(
				_httpServletRequest, "modified-x-ago-by-x",
				new String[] {
					LanguageUtil.getTimeDescription(
						_httpServletRequest,
						System.currentTimeMillis() - modifiedDate.getTime(),
						true),
					HtmlUtil.escape(_ctCollection.getUserName())
				});
		}
		else if (_ctCollection.getStatus() ==
					WorkflowConstants.STATUS_SCHEDULED) {

			try {
				SchedulerResponse schedulerResponse =
					SchedulerEngineHelperUtil.getScheduledJob(
						String.valueOf(_ctCollection.getCtCollectionId()),
						CTDestinationNames.CT_COLLECTION_SCHEDULED_PUBLISH,
						StorageType.PERSISTED);

				if (schedulerResponse == null) {
					return null;
				}

				Date scheduledDate = SchedulerEngineHelperUtil.getStartTime(
					schedulerResponse);

				return LanguageUtil.format(
					_httpServletRequest, "schedule-to-publish-in-x-by-x",
					new String[] {
						LanguageUtil.getTimeDescription(
							_httpServletRequest,
							scheduledDate.getTime() -
								System.currentTimeMillis(),
							true),
						HtmlUtil.escape(_ctCollection.getUserName())
					});
			}
			catch (SchedulerException schedulerException) {
				_log.error(schedulerException);
			}
		}

		return StringPool.BLANK;
	}

	public Map<String, Object> getTimelineDropdownMenuData(
			ThemeDisplay themeDisplay)
		throws PortalException {

		Map<String, Object> data = new HashMap<>();

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (_ctCollection.getStatus() != WorkflowConstants.STATUS_APPROVED) {
			if (CTCollectionPermission.contains(
					permissionChecker, _ctCollection, ActionKeys.UPDATE)) {

				data.put(
					"editURL",
					PortletURLBuilder.create(
						PortalUtil.getControlPanelPortletURL(
							_httpServletRequest, themeDisplay.getScopeGroup(),
							CTPortletKeys.PUBLICATIONS, 0, 0,
							PortletRequest.RENDER_PHASE)
					).setMVCRenderCommandName(
						"/change_tracking/edit_ct_collection"
					).setRedirect(
						themeDisplay.getURLCurrent()
					).setParameter(
						"ctCollectionId", _ctCollection.getCtCollectionId()
					).buildString());
			}

			if (CTCollectionPermission.contains(
					permissionChecker, _ctCollection, ActionKeys.DELETE)) {

				data.put(
					"deleteURL",
					_getDeleteHref(
						_httpServletRequest, themeDisplay.getURLCurrent(),
						_ctCollection.getCtCollectionId(), themeDisplay));
			}
		}
		else {
			data.put(
				"revertURL",
				PortletURLBuilder.create(
					PortalUtil.getControlPanelPortletURL(
						_httpServletRequest, themeDisplay.getScopeGroup(),
						CTPortletKeys.PUBLICATIONS, 0, 0,
						PortletRequest.RENDER_PHASE)
				).setMVCRenderCommandName(
					"/change_tracking/undo_ct_collection"
				).setRedirect(
					themeDisplay.getURLCurrent()
				).setParameter(
					"ctCollectionId", _ctCollection.getCtCollectionId()
				).setParameter(
					"revert", Boolean.TRUE
				).buildString());
		}

		if (CTCollectionPermission.contains(
				permissionChecker, _ctCollection, ActionKeys.VIEW)) {

			data.put(
				"reviewURL",
				PortletURLBuilder.create(
					PortalUtil.getControlPanelPortletURL(
						_httpServletRequest, themeDisplay.getScopeGroup(),
						CTPortletKeys.PUBLICATIONS, 0, 0,
						PortletRequest.RENDER_PHASE)
				).setMVCRenderCommandName(
					"/change_tracking/view_changes"
				).setRedirect(
					themeDisplay.getURLCurrent()
				).setParameter(
					"ctCollectionId", _ctCollection.getCtCollectionId()
				).buildString());
		}

		return data;
	}

	private String _getDeleteHref(
		HttpServletRequest httpServletRequest, String redirect,
		long ctCollectionId, ThemeDisplay themeDisplay) {

		return StringBundler.concat(
			"javascript:Liferay.Util.openConfirmModal({message: '",
			LanguageUtil.get(
				httpServletRequest,
				"are-you-sure-you-want-to-delete-this-publication"),
			"', onConfirm: (isConfirmed) => {if (isConfirmed) {",
			"submitForm(document.hrefFm, '",
			PortletURLBuilder.create(
				PortalUtil.getControlPanelPortletURL(
					httpServletRequest, themeDisplay.getScopeGroup(),
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

	private static final Log _log = LogFactoryUtil.getLog(
		CTCollectionHistoryDataProvider.class);

	private final CTCollection _ctCollection;
	private final HttpServletRequest _httpServletRequest;

}
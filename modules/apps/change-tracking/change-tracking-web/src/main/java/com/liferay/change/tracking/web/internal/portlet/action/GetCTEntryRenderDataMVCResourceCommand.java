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

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.change.tracking.web.internal.constants.CTPortletKeys;
import com.liferay.change.tracking.web.internal.display.CTDisplayRendererRegistry;
import com.liferay.change.tracking.web.internal.display.DisplayContextImpl;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.change.tracking.sql.CTSQLModeThreadLocal;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.taglib.servlet.PipingServletResponse;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CTPortletKeys.PUBLICATIONS,
		"mvc.command.name=/change_tracking/get_ct_entry_render_data"
	},
	service = MVCResourceCommand.class
)
public class GetCTEntryRenderDataMVCResourceCommand
	extends BaseMVCResourceCommand {

	public static final String TYPE_AFTER = "after";

	public static final String TYPE_BEFORE = "before";

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		try {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				_getCTEntryRenderDataJSONObject(
					resourceRequest, resourceResponse));
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"error",
					_language.get(
						_portal.getHttpServletRequest(resourceRequest),
						"an-unexpected-error-occurred")));
		}
	}

	private <T extends BaseModel<T>> JSONObject _getCTEntryRenderDataJSONObject(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		long ctEntryId = ParamUtil.getLong(resourceRequest, "ctEntryId");

		CTEntry ctEntry = _ctEntryLocalService.getCTEntry(ctEntryId);

		CTCollection ctCollection = _ctCollectionLocalService.getCTCollection(
			ctEntry.getCtCollectionId());

		CTDisplayRenderer<T> ctDisplayRenderer =
			_ctDisplayRendererRegistry.getCTDisplayRenderer(
				ctEntry.getModelClassNameId());

		if (ctDisplayRenderer.hasContent()) {
			jsonObject.put("hasContent", true);
		}
		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			resourceRequest);
		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(resourceResponse);

		T rightModel = null;

		if (ctEntry.getChangeType() != CTConstants.CT_CHANGE_TYPE_DELETION) {
			long ctCollectionId = ctCollection.getCtCollectionId();

			if (ctCollection.getStatus() == WorkflowConstants.STATUS_APPROVED) {
				ctCollectionId = _ctEntryLocalService.getCTRowCTCollectionId(
					ctEntry);
			}

			CTSQLModeThreadLocal.CTSQLMode ctSQLMode =
				_ctDisplayRendererRegistry.getCTSQLMode(
					ctCollectionId, ctEntry);

			rightModel = _ctDisplayRendererRegistry.fetchCTModel(
				ctCollectionId, ctSQLMode, ctEntry.getModelClassNameId(),
				ctEntry.getModelClassPK());

			if (rightModel != null) {
				jsonObject.put(
					"rightRender",
					_getData(
						httpServletRequest, httpServletResponse, ctCollectionId,
						ctDisplayRenderer, ctEntryId, ctSQLMode, rightModel,
						TYPE_AFTER));

				if (ctDisplayRenderer.hasContent()) {
					String content = ctDisplayRenderer.getContent(
						_portal.getLiferayPortletRequest(resourceRequest),
						_portal.getLiferayPortletResponse(resourceResponse),
						rightModel);

					if (content != null) {
						jsonObject.put("rightContent", content);
					}
				}
			}
		}

		if ((ctEntry.getChangeType() == CTConstants.CT_CHANGE_TYPE_ADDITION) &&
			(rightModel != null) && ctDisplayRenderer.isVersioned()) {

			T leftModel = ctDisplayRenderer.getPreviousVersion(rightModel);

			if (leftModel != null) {
				jsonObject.put(
					"leftRender",
					_getData(
						httpServletRequest, httpServletResponse,
						ctEntry.getCtCollectionId(), ctDisplayRenderer,
						ctEntryId, CTSQLModeThreadLocal.CTSQLMode.DEFAULT,
						leftModel, TYPE_AFTER));

				if (ctDisplayRenderer.hasContent()) {
					String content = ctDisplayRenderer.getPreviousContent(
						_portal.getLiferayPortletRequest(resourceRequest),
						_portal.getLiferayPortletResponse(resourceResponse),
						rightModel, leftModel);

					if (content != null) {
						jsonObject.put("leftContent", content);
					}
				}
			}
		}
		else if (ctEntry.getChangeType() !=
					CTConstants.CT_CHANGE_TYPE_ADDITION) {

			long ctCollectionId = CTConstants.CT_COLLECTION_ID_PRODUCTION;

			if (ctCollection.getStatus() == WorkflowConstants.STATUS_APPROVED) {
				ctCollectionId = ctEntry.getCtCollectionId();
			}

			CTSQLModeThreadLocal.CTSQLMode ctSQLMode =
				_ctDisplayRendererRegistry.getCTSQLMode(
					ctCollectionId, ctEntry);

			T leftModel = _ctDisplayRendererRegistry.fetchCTModel(
				ctCollectionId, ctSQLMode, ctEntry.getModelClassNameId(),
				ctEntry.getModelClassPK());

			if (leftModel != null) {
				jsonObject.put(
					"leftRender",
					_getData(
						httpServletRequest, httpServletResponse, ctCollectionId,
						ctDisplayRenderer, ctEntryId, ctSQLMode, leftModel,
						TYPE_BEFORE));
			}
		}

		return jsonObject;
	}

	private <T extends BaseModel<T>> String _getData(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, long ctCollectionId,
			CTDisplayRenderer<T> ctDisplayRenderer, long ctEntryId,
			CTSQLModeThreadLocal.CTSQLMode ctSQLMode, T model, String type)
		throws Exception {

		try (SafeClosable safeClosable1 =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId);
			SafeClosable safeClosable2 = CTSQLModeThreadLocal.setCTSQLMode(
				ctSQLMode);
			UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter()) {

			PipingServletResponse pipingServletResponse =
				new PipingServletResponse(
					httpServletResponse, unsyncStringWriter);

			ctDisplayRenderer.render(
				new DisplayContextImpl<>(
					httpServletRequest, pipingServletResponse, model, ctEntryId,
					type));

			StringBundler sb = unsyncStringWriter.getStringBundler();

			return sb.toString();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}
		}

		ctDisplayRenderer = _ctDisplayRendererRegistry.getDefaultRenderer();

		try (UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter()) {
			PipingServletResponse pipingServletResponse =
				new PipingServletResponse(
					httpServletResponse, unsyncStringWriter);

			ctDisplayRenderer.render(
				new DisplayContextImpl<>(
					httpServletRequest, pipingServletResponse, model, ctEntryId,
					type));

			StringBundler sb = unsyncStringWriter.getStringBundler();

			return sb.toString();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetCTEntryRenderDataMVCResourceCommand.class);

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTDisplayRendererRegistry _ctDisplayRendererRegistry;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}
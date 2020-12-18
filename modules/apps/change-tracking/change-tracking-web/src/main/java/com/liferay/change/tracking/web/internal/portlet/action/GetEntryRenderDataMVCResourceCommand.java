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
import com.liferay.change.tracking.web.internal.display.BasePersistenceRegistry;
import com.liferay.change.tracking.web.internal.display.CTDisplayRendererRegistry;
import com.liferay.change.tracking.web.internal.display.DisplayContextImpl;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.change.tracking.sql.CTSQLModeThreadLocal;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.diff.DiffHtmlUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
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
		"mvc.command.name=/change_tracking/get_entry_render_data"
	},
	service = MVCResourceCommand.class
)
public class GetEntryRenderDataMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		try {
			long ctEntryId = ParamUtil.getLong(resourceRequest, "ctEntryId");

			if (ctEntryId > 0) {
				JSONPortletResponseUtil.writeJSON(
					resourceRequest, resourceResponse,
					_getCTEntryRenderDataJSONObject(
						resourceRequest, resourceResponse, ctEntryId));

				return;
			}

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				_getProductionRenderDataJSONObject(
					resourceRequest, resourceResponse));
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"errorMessage",
					_language.get(
						_portal.getHttpServletRequest(resourceRequest),
						"an-unexpected-error-occurred")));
		}
	}

	private <T extends BaseModel<T>> String _getContent(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		CTDisplayRenderer<T> ctDisplayRenderer, T model) {

		try {
			return ctDisplayRenderer.getContent(
				liferayPortletRequest, liferayPortletResponse, model);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return null;
	}

	private <T extends BaseModel<T>> JSONObject _getCTEntryRenderDataJSONObject(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse,
			long ctEntryId)
		throws Exception {

		CTEntry ctEntry = _ctEntryLocalService.getCTEntry(ctEntryId);

		CTCollection ctCollection = _ctCollectionLocalService.getCTCollection(
			ctEntry.getCtCollectionId());

		CTDisplayRenderer<T> ctDisplayRenderer =
			_ctDisplayRendererRegistry.getCTDisplayRenderer(
				ctEntry.getModelClassNameId());

		String changeType = "modified";

		if (ctEntry.getChangeType() == CTConstants.CT_CHANGE_TYPE_ADDITION) {
			changeType = "added";
		}
		else if (ctEntry.getChangeType() ==
					CTConstants.CT_CHANGE_TYPE_DELETION) {

			changeType = "deleted";
		}

		JSONObject jsonObject = JSONUtil.put(
			"changeType", changeType
		).put(
			"content", ctDisplayRenderer.hasContent()
		).put(
			"versioned", ctDisplayRenderer.isVersioned()
		);

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			resourceRequest);
		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(resourceResponse);
		LiferayPortletRequest liferayPortletRequest =
			_portal.getLiferayPortletRequest(resourceRequest);
		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(resourceResponse);

		long rightCtCollectionId = ctCollection.getCtCollectionId();
		String rightContent = null;
		String rightRender = null;
		T rightModel = null;

		if (ctEntry.getChangeType() != CTConstants.CT_CHANGE_TYPE_DELETION) {
			jsonObject.put(
				"rightTitle",
				StringBundler.concat(
					_language.get(httpServletRequest, "publication"), ": ",
					ctCollection.getName()));

			if (ctCollection.getStatus() == WorkflowConstants.STATUS_APPROVED) {
				rightCtCollectionId =
					_ctEntryLocalService.getCTRowCTCollectionId(ctEntry);
			}

			CTSQLModeThreadLocal.CTSQLMode ctSQLMode =
				_ctDisplayRendererRegistry.getCTSQLMode(
					rightCtCollectionId, ctEntry);

			rightModel = _ctDisplayRendererRegistry.fetchCTModel(
				rightCtCollectionId, ctSQLMode, ctEntry.getModelClassNameId(),
				ctEntry.getModelClassPK());

			if (rightModel != null) {
				rightRender = _getRender(
					httpServletRequest, httpServletResponse,
					rightCtCollectionId, ctDisplayRenderer, ctEntryId,
					ctSQLMode, rightModel, CTConstants.TYPE_AFTER);

				jsonObject.put("rightRender", rightRender);

				if (ctDisplayRenderer.hasContent()) {
					rightContent = _getContent(
						liferayPortletRequest, liferayPortletResponse,
						ctDisplayRenderer, rightModel);

					if (rightContent != null) {
						jsonObject.put("rightContent", rightContent);
					}
				}
			}
		}

		if ((ctEntry.getChangeType() == CTConstants.CT_CHANGE_TYPE_ADDITION) &&
			(rightModel != null) && ctDisplayRenderer.isVersioned()) {

			T leftModel = null;

			try (SafeClosable safeClosable =
					CTCollectionThreadLocal.setCTCollectionId(
						rightCtCollectionId)) {

				leftModel = ctDisplayRenderer.getPreviousVersion(rightModel);
			}

			if (leftModel != null) {
				jsonObject.put(
					"leftTitle",
					StringBundler.concat(
						_language.get(httpServletRequest, "version"), ": ",
						ctDisplayRenderer.getVersionName(leftModel))
				).put(
					"rightTitle",
					StringBundler.concat(
						_language.get(httpServletRequest, "version"), ": ",
						ctDisplayRenderer.getVersionName(rightModel))
				);

				if (ctDisplayRenderer.hasContent()) {
					String leftContent = _getPreviousContent(
						liferayPortletRequest, liferayPortletResponse,
						ctDisplayRenderer, rightModel, leftModel);

					if (leftContent != null) {
						jsonObject.put("leftContent", leftContent);

						if (rightContent != null) {
							jsonObject.put(
								"unifiedContent",
								DiffHtmlUtil.diff(
									new UnsyncStringReader(leftContent),
									new UnsyncStringReader(rightContent)));
						}
					}
				}

				String leftRender = _getRender(
					httpServletRequest, httpServletResponse,
					rightCtCollectionId, ctDisplayRenderer, ctEntryId,
					CTSQLModeThreadLocal.CTSQLMode.DEFAULT, leftModel,
					CTConstants.TYPE_AFTER);

				jsonObject.put("leftRender", leftRender);

				if (rightRender != null) {
					jsonObject.put(
						"unifiedRender",
						DiffHtmlUtil.diff(
							new UnsyncStringReader(leftRender),
							new UnsyncStringReader(rightRender)));
				}
			}
		}
		else if (ctEntry.getChangeType() !=
					CTConstants.CT_CHANGE_TYPE_ADDITION) {

			jsonObject.put(
				"leftTitle", _language.get(httpServletRequest, "production"));

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
				String leftContent = null;

				if (ctDisplayRenderer.hasContent()) {
					if (rightModel != null) {
						leftContent = _getPreviousContent(
							liferayPortletRequest, liferayPortletResponse,
							ctDisplayRenderer, rightModel, leftModel);
					}
					else {
						leftContent = _getContent(
							liferayPortletRequest, liferayPortletResponse,
							ctDisplayRenderer, leftModel);
					}
				}

				if (leftContent != null) {
					jsonObject.put("leftContent", leftContent);

					if (rightContent != null) {
						jsonObject.put(
							"unifiedContent",
							DiffHtmlUtil.diff(
								new UnsyncStringReader(leftContent),
								new UnsyncStringReader(rightContent)));
					}
				}

				String leftRender = _getRender(
					httpServletRequest, httpServletResponse, ctCollectionId,
					ctDisplayRenderer, ctEntryId, ctSQLMode, leftModel,
					CTConstants.TYPE_BEFORE);

				jsonObject.put("leftRender", leftRender);

				if (rightRender != null) {
					jsonObject.put(
						"unifiedRender",
						DiffHtmlUtil.diff(
							new UnsyncStringReader(leftRender),
							new UnsyncStringReader(rightRender)));
				}
			}
		}

		return jsonObject;
	}

	private <T extends BaseModel<T>> String _getPreviousContent(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		CTDisplayRenderer<T> ctDisplayRenderer, T currentModel,
		T previousModel) {

		try {
			return ctDisplayRenderer.getPreviousContent(
				liferayPortletRequest, liferayPortletResponse, currentModel,
				previousModel);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return null;
	}

	private <T extends BaseModel<T>> JSONObject
			_getProductionRenderDataJSONObject(
				ResourceRequest resourceRequest,
				ResourceResponse resourceResponse)
		throws Exception {

		long modelClassNameId = ParamUtil.getLong(
			resourceRequest, "modelClassNameId");
		long modelClassPK = ParamUtil.getLong(resourceRequest, "modelClassPK");

		T model = _ctDisplayRendererRegistry.fetchCTModel(
			modelClassNameId, modelClassPK);

		if (model == null) {
			model = _basePersistenceRegistry.fetchBaseModel(
				modelClassNameId, modelClassPK);
		}

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			resourceRequest);
		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(resourceResponse);

		JSONObject jsonObject = JSONUtil.put(
			"changeType", "production"
		).put(
			"leftTitle",
			_language.get(
				_portal.getHttpServletRequest(resourceRequest), "production")
		);

		jsonObject.put(
			"leftRender",
			_getRender(
				httpServletRequest, httpServletResponse,
				CTConstants.CT_COLLECTION_ID_PRODUCTION,
				_ctDisplayRendererRegistry.getCTDisplayRenderer(
					modelClassNameId),
				0, CTSQLModeThreadLocal.CTSQLMode.DEFAULT, model,
				CTConstants.TYPE_BEFORE));

		return jsonObject;
	}

	private <T extends BaseModel<T>> String _getRender(
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
		GetEntryRenderDataMVCResourceCommand.class);

	@Reference
	private BasePersistenceRegistry _basePersistenceRegistry;

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
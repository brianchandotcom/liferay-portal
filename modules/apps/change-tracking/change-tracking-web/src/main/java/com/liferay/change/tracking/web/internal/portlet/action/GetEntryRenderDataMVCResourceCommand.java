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
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.change.tracking.web.internal.display.BasePersistenceRegistry;
import com.liferay.change.tracking.web.internal.display.CTDisplayRendererRegistry;
import com.liferay.change.tracking.web.internal.display.DisplayContextImpl;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.change.tracking.sql.CTSQLModeThreadLocal;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.diff.DiffHtmlUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.json.JSONArray;
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
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Locale;

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
		long ctCollectionId, CTDisplayRenderer<T> ctDisplayRenderer,
		CTSQLModeThreadLocal.CTSQLMode ctSQLMode,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Locale locale, T model) {

		try (SafeCloseable safeCloseable1 =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollectionId);
			SafeCloseable safeCloseable2 =
				CTSQLModeThreadLocal.setCTSQLModeWithSafeCloseable(ctSQLMode)) {

			return ctDisplayRenderer.getContent(
				httpServletRequest, httpServletResponse, locale, model);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return null;
		}
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

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			resourceRequest);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(resourceResponse);

		String[] availableLanguageIds = null;
		String defaultLanguageId = null;
		String editURL = null;
		JSONObject localizedTitlesJSONObject =
			JSONFactoryUtil.createJSONObject();
		String rightContent = null;
		JSONObject rightLocalizedContentJSONObject = null;
		JSONObject rightLocalizedRenderJSONObject = null;
		String rightRender = null;
		T rightModel = null;
		String rightTitle = null;

		if (ctEntry.getChangeType() != CTConstants.CT_CHANGE_TYPE_DELETION) {
			rightTitle = _language.get(httpServletRequest, "publication");

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
				boolean activeCTCollection = ParamUtil.getBoolean(
					resourceRequest, "activeCTCollection");

				if (activeCTCollection) {
					editURL = ctDisplayRenderer.getEditURL(
						httpServletRequest, rightModel);
				}

				availableLanguageIds =
					_ctDisplayRendererRegistry.getAvailableLanguageIds(
						ctCollectionId, ctSQLMode, rightModel,
						ctEntry.getModelClassNameId());

				defaultLanguageId =
					_ctDisplayRendererRegistry.getDefaultLanguageId(
						rightModel, ctEntry.getModelClassNameId());

				if (ArrayUtil.isNotEmpty(availableLanguageIds)) {
					for (String languageId : availableLanguageIds) {
						localizedTitlesJSONObject.put(
							languageId,
							_ctDisplayRendererRegistry.getTitle(
								ctCollectionId, ctSQLMode,
								LocaleUtil.fromLanguageId(languageId),
								rightModel, ctEntry.getModelClassNameId()));
					}

					rightLocalizedContentJSONObject =
						_getLocalizedContentJSONObject(
							availableLanguageIds, ctCollectionId,
							ctDisplayRenderer, ctSQLMode, httpServletRequest,
							httpServletResponse, rightModel);
					rightLocalizedRenderJSONObject =
						_getLocalizedRenderJSONObject(
							availableLanguageIds, httpServletRequest,
							httpServletResponse, ctCollectionId,
							ctDisplayRenderer, ctEntryId, ctSQLMode, rightModel,
							CTConstants.TYPE_AFTER);
				}
				else {
					rightContent = _getContent(
						ctCollectionId, ctDisplayRenderer, ctSQLMode,
						httpServletRequest, httpServletResponse,
						themeDisplay.getLocale(), rightModel);
					rightRender = _getRender(
						httpServletRequest, httpServletResponse, ctCollectionId,
						ctDisplayRenderer, ctEntryId, ctSQLMode,
						themeDisplay.getLocale(), rightModel,
						CTConstants.TYPE_AFTER);
				}
			}
		}

		long leftCtCollectionId = CTConstants.CT_COLLECTION_ID_PRODUCTION;

		if (ctCollection.getStatus() == WorkflowConstants.STATUS_APPROVED) {
			leftCtCollectionId = ctEntry.getCtCollectionId();
		}

		CTSQLModeThreadLocal.CTSQLMode leftCTSQLMode =
			_ctDisplayRendererRegistry.getCTSQLMode(
				leftCtCollectionId, ctEntry);

		String leftContent = null;
		JSONObject leftLocalizedContentJSONObject = null;
		JSONObject leftLocalizedRenderJSONObject = null;
		T leftModel = null;
		String leftRender = null;
		String leftTitle = null;

		if ((ctEntry.getChangeType() == CTConstants.CT_CHANGE_TYPE_ADDITION) &&
			(rightModel != null)) {

			String rightVersionName = ctDisplayRenderer.getVersionName(
				rightModel);

			if (Validator.isNotNull(rightVersionName)) {
				try (SafeCloseable safeCloseable1 =
						CTCollectionThreadLocal.
							setCTCollectionIdWithSafeCloseable(
								leftCtCollectionId);
					SafeCloseable safeCloseable2 =
						CTSQLModeThreadLocal.setCTSQLModeWithSafeCloseable(
							leftCTSQLMode)) {

					leftModel = ctDisplayRenderer.fetchLatestVersionedModel(
						rightModel);
				}

				if (leftModel != null) {
					String leftVersionName = ctDisplayRenderer.getVersionName(
						leftModel);

					if (Validator.isNull(leftVersionName)) {
						leftTitle = _language.get(
							httpServletRequest, "production");
					}
					else {
						leftTitle = StringBundler.concat(
							_language.get(httpServletRequest, "version"), ": ",
							leftVersionName, " (",
							_language.get(httpServletRequest, "production"),
							")");
					}

					rightTitle = StringBundler.concat(
						_language.get(httpServletRequest, "version"), ": ",
						rightVersionName, " (",
						_language.get(httpServletRequest, "publication"), ")");

					if (ArrayUtil.isNotEmpty(availableLanguageIds)) {
						leftLocalizedContentJSONObject =
							_getLocalizedContentJSONObject(
								availableLanguageIds, leftCtCollectionId,
								ctDisplayRenderer, leftCTSQLMode,
								httpServletRequest, httpServletResponse,
								leftModel);
						leftLocalizedRenderJSONObject =
							_getLocalizedRenderJSONObject(
								availableLanguageIds, httpServletRequest,
								httpServletResponse, leftCtCollectionId,
								ctDisplayRenderer, ctEntryId, leftCTSQLMode,
								leftModel, CTConstants.TYPE_AFTER);
					}
					else {
						leftContent = _getContent(
							leftCtCollectionId, ctDisplayRenderer,
							leftCTSQLMode, httpServletRequest,
							httpServletResponse, themeDisplay.getLocale(),
							leftModel);
						leftRender = _getRender(
							httpServletRequest, httpServletResponse,
							leftCtCollectionId, ctDisplayRenderer, ctEntryId,
							leftCTSQLMode, themeDisplay.getLocale(), leftModel,
							CTConstants.TYPE_AFTER);
					}
				}
			}
		}
		else if (ctEntry.getChangeType() !=
					CTConstants.CT_CHANGE_TYPE_ADDITION) {

			leftTitle = _language.get(httpServletRequest, "production");

			leftModel = _ctDisplayRendererRegistry.fetchCTModel(
				leftCtCollectionId, leftCTSQLMode,
				ctEntry.getModelClassNameId(), ctEntry.getModelClassPK());

			if (leftModel != null) {
				if (ctEntry.getChangeType() ==
						CTConstants.CT_CHANGE_TYPE_DELETION) {

					availableLanguageIds =
						_ctDisplayRendererRegistry.getAvailableLanguageIds(
							leftCtCollectionId, leftCTSQLMode, leftModel,
							ctEntry.getModelClassNameId());

					defaultLanguageId =
						_ctDisplayRendererRegistry.getDefaultLanguageId(
							leftModel, ctEntry.getModelClassNameId());
				}

				if (ArrayUtil.isNotEmpty(availableLanguageIds)) {
					for (String languageId : availableLanguageIds) {
						localizedTitlesJSONObject.put(
							languageId,
							_ctDisplayRendererRegistry.getTitle(
								leftCtCollectionId, leftCTSQLMode,
								LocaleUtil.fromLanguageId(languageId),
								leftModel, ctEntry.getModelClassNameId()));
					}

					leftLocalizedContentJSONObject =
						_getLocalizedContentJSONObject(
							availableLanguageIds, leftCtCollectionId,
							ctDisplayRenderer, leftCTSQLMode,
							httpServletRequest, httpServletResponse, leftModel);
					leftLocalizedRenderJSONObject =
						_getLocalizedRenderJSONObject(
							availableLanguageIds, httpServletRequest,
							httpServletResponse, leftCtCollectionId,
							ctDisplayRenderer, ctEntryId, leftCTSQLMode,
							leftModel, CTConstants.TYPE_AFTER);
				}
				else {
					leftContent = _getContent(
						leftCtCollectionId, ctDisplayRenderer, leftCTSQLMode,
						httpServletRequest, httpServletResponse,
						themeDisplay.getLocale(), leftModel);
					leftRender = _getRender(
						httpServletRequest, httpServletResponse,
						leftCtCollectionId, ctDisplayRenderer, ctEntryId,
						leftCTSQLMode, themeDisplay.getLocale(), leftModel,
						CTConstants.TYPE_BEFORE);
				}
			}
		}

		if ((ctEntry.getChangeType() == CTConstants.CT_CHANGE_TYPE_DELETION) &&
			(leftModel != null)) {

			String leftVersionName = ctDisplayRenderer.getVersionName(
				leftModel);

			if (Validator.isNotNull(leftVersionName)) {
				long ctCollectionId = ctCollection.getCtCollectionId();

				if (ctCollection.getStatus() ==
						WorkflowConstants.STATUS_APPROVED) {

					ctCollectionId =
						_ctEntryLocalService.getCTRowCTCollectionId(ctEntry);
				}

				CTSQLModeThreadLocal.CTSQLMode ctSQLMode =
					CTSQLModeThreadLocal.CTSQLMode.DEFAULT;

				try (SafeCloseable safeCloseable1 =
						CTCollectionThreadLocal.
							setCTCollectionIdWithSafeCloseable(ctCollectionId);
					SafeCloseable safeCloseable2 =
						CTSQLModeThreadLocal.setCTSQLModeWithSafeCloseable(
							ctSQLMode)) {

					rightModel = ctDisplayRenderer.fetchLatestVersionedModel(
						leftModel);
				}

				if (rightModel != null) {
					String rightVersionName = ctDisplayRenderer.getVersionName(
						rightModel);

					if (Validator.isNull(rightVersionName)) {
						rightTitle = _language.get(
							httpServletRequest, "publication");
					}
					else {
						rightTitle = StringBundler.concat(
							_language.get(httpServletRequest, "version"), ": ",
							rightVersionName, " (",
							_language.get(httpServletRequest, "publication"),
							")");
					}

					leftTitle = StringBundler.concat(
						_language.get(httpServletRequest, "version"), ": ",
						leftVersionName, " (",
						_language.get(httpServletRequest, "deleted"), ")");

					availableLanguageIds =
						_ctDisplayRendererRegistry.getAvailableLanguageIds(
							ctCollectionId, ctSQLMode, rightModel,
							ctEntry.getModelClassNameId());

					if (ArrayUtil.isNotEmpty(availableLanguageIds)) {
						rightLocalizedContentJSONObject =
							_getLocalizedContentJSONObject(
								availableLanguageIds, ctCollectionId,
								ctDisplayRenderer, ctSQLMode,
								httpServletRequest, httpServletResponse,
								rightModel);
						rightLocalizedRenderJSONObject =
							_getLocalizedRenderJSONObject(
								availableLanguageIds, httpServletRequest,
								httpServletResponse, ctCollectionId,
								ctDisplayRenderer, ctEntryId, ctSQLMode,
								rightModel, CTConstants.TYPE_AFTER);
					}
					else {
						rightContent = _getContent(
							ctCollectionId, ctDisplayRenderer, ctSQLMode,
							httpServletRequest, httpServletResponse,
							themeDisplay.getLocale(), rightModel);
						rightRender = _getRender(
							httpServletRequest, httpServletResponse,
							ctCollectionId, ctDisplayRenderer, ctEntryId,
							ctSQLMode, themeDisplay.getLocale(), rightModel,
							CTConstants.TYPE_AFTER);
					}
				}
			}
		}

		JSONObject jsonObject = JSONUtil.put("changeType", changeType);

		if (defaultLanguageId != null) {
			jsonObject.put(
				"defaultLocale", _getLocaleJSONObject(defaultLanguageId));
		}

		if (editURL != null) {
			jsonObject.put("editURL", editURL);
		}

		if (leftLocalizedContentJSONObject != null) {
			jsonObject.put(
				"leftLocalizedContent", leftLocalizedContentJSONObject);
		}

		if (leftLocalizedRenderJSONObject != null) {
			jsonObject.put(
				"leftLocalizedRender", leftLocalizedRenderJSONObject);
		}

		if (leftContent != null) {
			jsonObject.put("leftContent", leftContent);
		}

		if (leftRender != null) {
			jsonObject.put("leftRender", leftRender);
		}

		if (leftTitle != null) {
			jsonObject.put("leftTitle", leftTitle);
		}

		if (rightContent != null) {
			jsonObject.put("rightContent", rightContent);
		}

		if (rightLocalizedContentJSONObject != null) {
			jsonObject.put(
				"rightLocalizedContent", rightLocalizedContentJSONObject);
		}

		if (rightLocalizedRenderJSONObject != null) {
			jsonObject.put(
				"rightLocalizedRender", rightLocalizedRenderJSONObject);
		}

		if (rightRender != null) {
			jsonObject.put("rightRender", rightRender);
		}

		if (rightTitle != null) {
			jsonObject.put("rightTitle", rightTitle);
		}

		if ((leftContent != null) && (rightContent != null)) {
			jsonObject.put(
				"unifiedContent",
				DiffHtmlUtil.diff(
					new UnsyncStringReader(leftContent),
					new UnsyncStringReader(rightContent)));
		}

		if ((leftLocalizedContentJSONObject != null) &&
			(rightLocalizedContentJSONObject != null)) {

			JSONObject unifiedLocalizedContentJSONObject =
				JSONFactoryUtil.createJSONObject();

			for (String languageId : availableLanguageIds) {
				String leftLocalizedContent =
					leftLocalizedContentJSONObject.getString(languageId);
				String rightLocalizedContent =
					rightLocalizedContentJSONObject.getString(languageId);

				if ((leftLocalizedContent != null) &&
					(rightLocalizedContent != null)) {

					unifiedLocalizedContentJSONObject.put(
						languageId,
						DiffHtmlUtil.diff(
							new UnsyncStringReader(leftLocalizedContent),
							new UnsyncStringReader(rightLocalizedContent)));
				}
			}

			jsonObject.put(
				"unifiedLocalizedContent", unifiedLocalizedContentJSONObject);
		}

		if ((leftLocalizedRenderJSONObject != null) &&
			(rightLocalizedRenderJSONObject != null)) {

			JSONObject unifiedLocalizedRenderJSONObject =
				JSONFactoryUtil.createJSONObject();

			for (String languageId : availableLanguageIds) {
				String leftLocalizedRender =
					leftLocalizedRenderJSONObject.getString(languageId);
				String rightLocalizedRender =
					rightLocalizedRenderJSONObject.getString(languageId);

				if ((leftLocalizedRender != null) &&
					(rightLocalizedRender != null)) {

					unifiedLocalizedRenderJSONObject.put(
						languageId,
						DiffHtmlUtil.diff(
							new UnsyncStringReader(leftLocalizedRender),
							new UnsyncStringReader(rightLocalizedRender)));
				}
			}

			jsonObject.put(
				"unifiedLocalizedRender", unifiedLocalizedRenderJSONObject);
		}

		if ((leftRender != null) && (rightRender != null)) {
			jsonObject.put(
				"unifiedRender",
				DiffHtmlUtil.diff(
					new UnsyncStringReader(leftRender),
					new UnsyncStringReader(rightRender)));
		}

		if (ArrayUtil.isNotEmpty(availableLanguageIds)) {
			JSONArray localesJSONArray = JSONFactoryUtil.createJSONArray();

			for (String languageId : availableLanguageIds) {
				localesJSONArray.put(_getLocaleJSONObject(languageId));
			}

			jsonObject.put(
				"locales", localesJSONArray
			).put(
				"localizedTitles", localizedTitlesJSONObject
			);
		}

		return jsonObject;
	}

	private JSONObject _getLocaleJSONObject(String languageId) {
		return JSONUtil.put(
			"label", languageId
		).put(
			"symbol",
			StringUtil.replace(
				StringUtil.toLowerCase(languageId), CharPool.UNDERLINE,
				CharPool.DASH)
		);
	}

	private <T extends BaseModel<T>> JSONObject _getLocalizedContentJSONObject(
		String[] availableLanguageIds, long ctCollectionId,
		CTDisplayRenderer<T> ctDisplayRenderer,
		CTSQLModeThreadLocal.CTSQLMode ctSQLMode,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, T model) {

		JSONObject jsonObject = null;

		try (SafeCloseable safeCloseable1 =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollectionId);
			SafeCloseable safeCloseable2 =
				CTSQLModeThreadLocal.setCTSQLModeWithSafeCloseable(ctSQLMode)) {

			for (String languageId : availableLanguageIds) {
				String content = ctDisplayRenderer.getContent(
					httpServletRequest, httpServletResponse,
					LocaleUtil.fromLanguageId(languageId), model);

				if (content != null) {
					if (jsonObject == null) {
						jsonObject = JSONFactoryUtil.createJSONObject();
					}

					jsonObject.put(languageId, content);
				}
			}

			return jsonObject;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return null;
		}
	}

	private <T extends BaseModel<T>> JSONObject _getLocalizedRenderJSONObject(
			String[] availableLanguageIds,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, long ctCollectionId,
			CTDisplayRenderer<T> ctDisplayRenderer, long ctEntryId,
			CTSQLModeThreadLocal.CTSQLMode ctSQLMode, T model, String type)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		for (String languageId : availableLanguageIds) {
			jsonObject.put(
				languageId,
				_getRender(
					httpServletRequest, httpServletResponse, ctCollectionId,
					ctDisplayRenderer, ctEntryId, ctSQLMode,
					LocaleUtil.fromLanguageId(languageId), model, type));
		}

		return jsonObject;
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

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

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
				0, CTSQLModeThreadLocal.CTSQLMode.DEFAULT,
				themeDisplay.getLocale(), model, CTConstants.TYPE_BEFORE));

		return jsonObject;
	}

	private <T extends BaseModel<T>> String _getRender(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, long ctCollectionId,
			CTDisplayRenderer<T> ctDisplayRenderer, long ctEntryId,
			CTSQLModeThreadLocal.CTSQLMode ctSQLMode, Locale locale, T model,
			String type)
		throws Exception {

		try (SafeCloseable safeCloseable1 =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollectionId);
			SafeCloseable safeCloseable2 =
				CTSQLModeThreadLocal.setCTSQLModeWithSafeCloseable(ctSQLMode);
			UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter()) {

			PipingServletResponse pipingServletResponse =
				new PipingServletResponse(
					httpServletResponse, unsyncStringWriter);

			ctDisplayRenderer.render(
				new DisplayContextImpl<>(
					httpServletRequest, pipingServletResponse, ctEntryId,
					locale, model, type));

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
					httpServletRequest, pipingServletResponse, ctEntryId,
					locale, model, type));

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
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

package com.liferay.change.tracking.web.internal.display.context;

import com.liferay.change.tracking.closure.CTClosure;
import com.liferay.change.tracking.closure.CTClosureFactory;
import com.liferay.change.tracking.constants.CTActionKeys;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.mapping.CTMappingTableInfo;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.model.CTEntryTable;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.service.CTSchemaVersionLocalService;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.change.tracking.web.internal.configuration.CTConfiguration;
import com.liferay.change.tracking.web.internal.display.BasePersistenceRegistry;
import com.liferay.change.tracking.web.internal.display.CTClosureUtil;
import com.liferay.change.tracking.web.internal.display.CTDisplayRendererRegistry;
import com.liferay.change.tracking.web.internal.display.CTModelDisplayRendererAdapter;
import com.liferay.change.tracking.web.internal.scheduler.PublishScheduler;
import com.liferay.change.tracking.web.internal.scheduler.ScheduledPublishInfo;
import com.liferay.change.tracking.web.internal.security.permission.resource.CTCollectionPermission;
import com.liferay.change.tracking.web.internal.security.permission.resource.CTPermission;
import com.liferay.change.tracking.web.internal.util.PublicationsPortletURLUtil;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.change.tracking.sql.CTSQLModeThreadLocal;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;

import java.io.Serializable;

import java.text.Format;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Samuel Trong Tran
 */
public class ViewChangesDisplayContext {

	public ViewChangesDisplayContext(
		long activeCTCollectionId,
		BasePersistenceRegistry basePersistenceRegistry,
		CTClosureFactory ctClosureFactory, CTCollection ctCollection,
		CTCollectionLocalService ctCollectionLocalService,
		CTConfiguration ctConfiguration,
		CTDisplayRendererRegistry ctDisplayRendererRegistry,
		CTEntryLocalService ctEntryLocalService,
		CTSchemaVersionLocalService ctSchemaVersionLocalService,
		GroupLocalService groupLocalService, Language language, Portal portal,
		PublicationsDisplayContext publicationsDisplayContext,
		PublishScheduler publishScheduler, RenderRequest renderRequest,
		RenderResponse renderResponse, UserLocalService userLocalService) {

		_activeCTCollectionId = activeCTCollectionId;
		_basePersistenceRegistry = basePersistenceRegistry;
		_ctClosureFactory = ctClosureFactory;
		_ctCollection = ctCollection;
		_ctCollectionLocalService = ctCollectionLocalService;
		_ctConfiguration = ctConfiguration;
		_ctDisplayRendererRegistry = ctDisplayRendererRegistry;
		_ctEntryLocalService = ctEntryLocalService;
		_ctSchemaVersionLocalService = ctSchemaVersionLocalService;
		_groupLocalService = groupLocalService;
		_language = language;
		_portal = portal;
		_publicationsDisplayContext = publicationsDisplayContext;
		_publishScheduler = publishScheduler;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_userLocalService = userLocalService;

		_httpServletRequest = _portal.getHttpServletRequest(renderRequest);

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getBackURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		if (_ctCollection.getStatus() == WorkflowConstants.STATUS_APPROVED) {
			portletURL.setParameter(
				"mvcRenderCommandName", "/change_tracking/view_history");
		}
		else if (_ctCollection.getStatus() ==
					WorkflowConstants.STATUS_SCHEDULED) {

			portletURL.setParameter(
				"mvcRenderCommandName", "/change_tracking/view_scheduled");
		}

		return portletURL.toString();
	}

	public Map<String, Object> getReactData() throws Exception {
		JSONObject contextViewJSONObject = null;

		CTClosure ctClosure = null;

		if (_ctCollection.getStatus() != WorkflowConstants.STATUS_APPROVED) {
			try {
				ctClosure = _ctClosureFactory.create(
					_ctCollection.getCtCollectionId());
			}
			catch (Exception exception) {
				contextViewJSONObject = JSONUtil.put(
					"errorMessage",
					_language.get(
						_httpServletRequest, "context-view-is-unavailable"));

				_log.error(exception, exception);
			}
		}

		Map<Long, Set<Long>> classNameIdClassPKsMap = new HashMap<>();
		Map<ModelInfoKey, ModelInfo> modelInfoMap = new HashMap<>();

		if (ctClosure == null) {
			List<CTEntry> ctEntries =
				_ctEntryLocalService.getCTCollectionCTEntries(
					_ctCollection.getCtCollectionId());

			int modelKeyCounter = 1;

			for (CTEntry ctEntry : ctEntries) {
				modelInfoMap.put(
					new ModelInfoKey(
						ctEntry.getModelClassNameId(),
						ctEntry.getModelClassPK()),
					new ModelInfo(modelKeyCounter++));

				Set<Long> classPKs = classNameIdClassPKsMap.computeIfAbsent(
					ctEntry.getModelClassNameId(), key -> new HashSet<>());

				classPKs.add(ctEntry.getModelClassPK());
			}
		}
		else {
			int[] modelKeyCounterHolder = {1};

			Map<Long, List<Long>> rootPKsMap = ctClosure.getRootPKsMap();

			Queue<Map.Entry<Long, List<Long>>> queue = new LinkedList<>(
				rootPKsMap.entrySet());

			Map.Entry<Long, List<Long>> entry = null;

			while ((entry = queue.poll()) != null) {
				long classNameId = entry.getKey();

				Set<Long> classPKs = classNameIdClassPKsMap.computeIfAbsent(
					classNameId, key -> new HashSet<>());

				classPKs.addAll(entry.getValue());

				for (long classPK : entry.getValue()) {
					ModelInfoKey modelInfoKey = new ModelInfoKey(
						classNameId, classPK);

					if (!modelInfoMap.containsKey(modelInfoKey)) {
						modelInfoMap.put(
							modelInfoKey,
							new ModelInfo(modelKeyCounterHolder[0]++));

						Map<Long, List<Long>> childPKsMap =
							ctClosure.getChildPKsMap(classNameId, classPK);

						if (!childPKsMap.isEmpty()) {
							queue.addAll(childPKsMap.entrySet());
						}
					}
				}
			}
		}

		Map<Long, String> typeNameCacheMap = new HashMap<>();

		for (Map.Entry<Long, Set<Long>> entry :
				classNameIdClassPKsMap.entrySet()) {

			_populateEntryValues(
				modelInfoMap, entry.getKey(), entry.getValue(),
				typeNameCacheMap);
		}

		if (ctClosure != null) {
			long groupClassNameId = _portal.getClassNameId(Group.class);

			for (long groupId :
					classNameIdClassPKsMap.getOrDefault(
						groupClassNameId, Collections.emptySet())) {

				_populateModelInfoGroupIds(
					ctClosure, modelInfoMap, groupClassNameId, groupId);
			}
		}

		Set<Long> rootClassNameIds = _getRootClassNameIds(ctClosure);

		return HashMapBuilder.<String, Object>put(
			"activeCTCollection",
			_ctCollection.getCtCollectionId() == _activeCTCollectionId
		).put(
			"changes",
			() -> {
				JSONArray changesJSONArray = JSONFactoryUtil.createJSONArray();

				for (ModelInfo modelInfo : modelInfoMap.values()) {
					if (modelInfo._ctEntry) {
						changesJSONArray.put(modelInfo._modelKey);
					}
				}

				return changesJSONArray;
			}
		).put(
			"changeTypesFromURL",
			ParamUtil.getString(_renderRequest, "changeTypes")
		).put(
			"collaboratorsData",
			_publicationsDisplayContext.getCollaboratorsReactData(_ctCollection)
		).put(
			"columnFromURL", ParamUtil.getString(_renderRequest, "column")
		).put(
			"contextView",
			_getContextViewJSONObject(
				ctClosure, modelInfoMap, rootClassNameIds,
				contextViewJSONObject, typeNameCacheMap)
		).put(
			"ctCollectionId", _ctCollection.getCtCollectionId()
		).put(
			"ctMappingInfos",
			() -> {
				JSONArray ctMappingInfosJSONArray =
					JSONFactoryUtil.createJSONArray();

				List<CTMappingTableInfo> ctMappingTableInfos =
					_ctCollectionLocalService.getCTMappingTableInfos(
						_ctCollection.getCtCollectionId());

				for (CTMappingTableInfo ctMappingTableInfo :
						ctMappingTableInfos) {

					String description = StringPool.BLANK;

					List<Map.Entry<Long, Long>> addedMappings =
						ctMappingTableInfo.getAddedMappings();

					if (!addedMappings.isEmpty()) {
						description = StringBundler.concat(
							addedMappings.size(), StringPool.SPACE,
							_language.get(_themeDisplay.getLocale(), "added"));
					}

					List<Map.Entry<Long, Long>> removedMappings =
						ctMappingTableInfo.getRemovedMappings();

					if (!removedMappings.isEmpty()) {
						if (Validator.isNotNull(description)) {
							description = description.concat(", ");
						}

						description = StringBundler.concat(
							description, removedMappings.size(),
							StringPool.SPACE,
							_language.get(
								_themeDisplay.getLocale(), "removed"));
					}

					ctMappingInfosJSONArray.put(
						JSONUtil.put(
							"description", description
						).put(
							"name",
							StringBundler.concat(
								_ctDisplayRendererRegistry.getTypeName(
									_themeDisplay.getLocale(),
									_portal.getClassNameId(
										ctMappingTableInfo.
											getLeftModelClass())),
								" & ",
								_ctDisplayRendererRegistry.getTypeName(
									_themeDisplay.getLocale(),
									_portal.getClassNameId(
										ctMappingTableInfo.
											getRightModelClass())))
						).put(
							"tableName", ctMappingTableInfo.getTableName()
						));
				}

				return ctMappingInfosJSONArray;
			}
		).put(
			"currentUserId", _themeDisplay.getUserId()
		).put(
			"dataURL",
			() -> {
				ResourceURL dataURL = _renderResponse.createResourceURL();

				dataURL.setParameter("localize", Boolean.TRUE.toString());
				dataURL.setResourceID("/change_tracking/get_entry_render_data");

				return dataURL.toString();
			}
		).put(
			"defaultLocale",
			JSONUtil.put(
				"label", _themeDisplay.getLanguageId()
			).put(
				"symbol",
				StringUtil.replace(
					StringUtil.toLowerCase(_themeDisplay.getLanguageId()),
					CharPool.UNDERLINE, CharPool.DASH)
			)
		).put(
			"deleteCTCommentURL",
			() -> {
				ResourceURL deleteCTCommentURL =
					_renderResponse.createResourceURL();

				deleteCTCommentURL.setParameter(
					"ctCollectionId",
					String.valueOf(_ctCollection.getCtCollectionId()));
				deleteCTCommentURL.setResourceID(
					"/change_tracking/delete_ct_comment");

				return deleteCTCommentURL.toString();
			}
		).put(
			"deltaFromURL", ParamUtil.getString(_renderRequest, "delta")
		).put(
			"description",
			() -> {
				if (_ctCollection.getStatus() ==
						WorkflowConstants.STATUS_APPROVED) {

					String description = _ctCollection.getDescription();

					if (Validator.isNotNull(description)) {
						description = description.concat(" | ");
					}

					Format format = FastDateFormatFactoryUtil.getDateTime(
						_themeDisplay.getLocale(), _themeDisplay.getTimeZone());

					return description.concat(
						_language.format(
							_httpServletRequest, "published-by-x-on-x",
							new Object[] {
								_ctCollection.getUserName(),
								format.format(_ctCollection.getStatusDate())
							},
							false));
				}
				else if (_ctCollection.getStatus() ==
							WorkflowConstants.STATUS_SCHEDULED) {

					String description = _ctCollection.getDescription();

					if (_publishScheduler == null) {
						return description;
					}

					ScheduledPublishInfo scheduledPublishInfo =
						_publishScheduler.getScheduledPublishInfo(
							_ctCollection);

					if (scheduledPublishInfo != null) {
						Format format = FastDateFormatFactoryUtil.getDateTime(
							_themeDisplay.getLocale(),
							_themeDisplay.getTimeZone());

						if (Validator.isNotNull(description)) {
							description = description.concat(" | ");
						}

						description = description.concat(
							_language.format(
								_httpServletRequest, "publishing-x",
								new Object[] {
									format.format(
										scheduledPublishInfo.getStartDate())
								},
								false));

						User user = _userLocalService.fetchUser(
							scheduledPublishInfo.getUserId());

						if (user != null) {
							return StringBundler.concat(
								description, " | ",
								_language.format(
									_httpServletRequest, "scheduled-by-x",
									new Object[] {user.getFullName()}, false));
						}

						return description;
					}

					return StringPool.BLANK;
				}

				return _ctCollection.getDescription();
			}
		).put(
			"discardURL",
			PortletURLBuilder.createRenderURL(
				_renderResponse
			).setMVCRenderCommandName(
				"/change_tracking/view_discard"
			).setRedirect(
				_themeDisplay.getURLCurrent()
			).setParameter(
				"ctCollectionId", _ctCollection.getCtCollectionId()
			).buildString()
		).put(
			"dropdownItems",
			_getDropdownItemsJSONArray(_themeDisplay.getPermissionChecker())
		).put(
			"entryFromURL", ParamUtil.getString(_renderRequest, "entry")
		).put(
			"expired",
			(_ctCollection.getStatus() == WorkflowConstants.STATUS_EXPIRED) ||
			((_ctCollection.getStatus() == WorkflowConstants.STATUS_APPROVED) &&
			 !_ctSchemaVersionLocalService.isLatestCTSchemaVersion(
				 _ctCollection.getSchemaVersionId()))
		).put(
			"getCTCommentsURL",
			() -> {
				ResourceURL getCTCommentsURL =
					_renderResponse.createResourceURL();

				getCTCommentsURL.setParameter(
					"ctCollectionId",
					String.valueOf(_ctCollection.getCtCollectionId()));
				getCTCommentsURL.setResourceID(
					"/change_tracking/get_ct_comments");

				return getCTCommentsURL.toString();
			}
		).put(
			"keywordsFromURL", ParamUtil.getString(_renderRequest, "keywords")
		).put(
			"modelData",
			() -> {
				JSONObject modelDataJSONObject =
					JSONFactoryUtil.createJSONObject();

				for (ModelInfo modelInfo : modelInfoMap.values()) {
					if (modelInfo._jsonObject != null) {
						modelDataJSONObject.put(
							String.valueOf(modelInfo._modelKey),
							modelInfo._jsonObject);
					}
				}

				return modelDataJSONObject;
			}
		).put(
			"name", _ctCollection.getName()
		).put(
			"namespace", _renderResponse.getNamespace()
		).put(
			"navigationFromURL",
			ParamUtil.getString(_renderRequest, "navigation")
		).put(
			"orderByTypeFromURL",
			ParamUtil.getString(_renderRequest, "orderByType")
		).put(
			"pageFromURL", ParamUtil.getString(_renderRequest, "page")
		).put(
			"publishURL",
			() -> {
				if ((_ctCollection.getStatus() !=
						WorkflowConstants.STATUS_DRAFT) ||
					!CTCollectionPermission.contains(
						_themeDisplay.getPermissionChecker(), _ctCollection,
						CTActionKeys.PUBLISH)) {

					return null;
				}

				return PortletURLBuilder.createRenderURL(
					_renderResponse
				).setMVCRenderCommandName(
					"/change_tracking/view_conflicts"
				).setParameter(
					"ctCollectionId", _ctCollection.getCtCollectionId()
				).buildString();
			}
		).put(
			"rescheduleURL",
			() -> {
				if ((_ctCollection.getStatus() !=
						WorkflowConstants.STATUS_SCHEDULED) ||
					!CTCollectionPermission.contains(
						_themeDisplay.getPermissionChecker(), _ctCollection,
						CTActionKeys.PUBLISH)) {

					return null;
				}

				return PortletURLBuilder.createRenderURL(
					_renderResponse
				).setMVCRenderCommandName(
					"/change_tracking/reschedule_publication"
				).setParameter(
					"ctCollectionId", _ctCollection.getCtCollectionId()
				).buildString();
			}
		).put(
			"revertURL",
			() -> {
				if ((_ctCollection.getStatus() !=
						WorkflowConstants.STATUS_APPROVED) ||
					!CTPermission.contains(
						_themeDisplay.getPermissionChecker(),
						CTActionKeys.ADD_PUBLICATION)) {

					return null;
				}

				return PortletURLBuilder.createRenderURL(
					_renderResponse
				).setMVCRenderCommandName(
					"/change_tracking/undo_ct_collection"
				).setParameter(
					"ctCollectionId", _ctCollection.getCtCollectionId()
				).setParameter(
					"revert", true
				).buildString();
			}
		).put(
			"rootDisplayClasses",
			() -> {
				JSONArray rootDisplayClassesJSONArray =
					JSONFactoryUtil.createJSONArray();

				for (long rootClassNameId : rootClassNameIds) {
					if (classNameIdClassPKsMap.containsKey(rootClassNameId)) {
						rootDisplayClassesJSONArray.put(
							_getTypeName(
								_themeDisplay.getLocale(), rootClassNameId,
								typeNameCacheMap));
					}
				}

				return rootDisplayClassesJSONArray;
			}
		).put(
			"scheduleURL",
			() -> {
				if ((_ctCollection.getStatus() !=
						WorkflowConstants.STATUS_DRAFT) ||
					!PropsValues.SCHEDULER_ENABLED ||
					!CTCollectionPermission.contains(
						_themeDisplay.getPermissionChecker(), _ctCollection,
						CTActionKeys.PUBLISH)) {

					return null;
				}

				return PortletURLBuilder.createRenderURL(
					_renderResponse
				).setMVCRenderCommandName(
					"/change_tracking/view_conflicts"
				).setParameter(
					"ctCollectionId", _ctCollection.getCtCollectionId()
				).setParameter(
					"schedule", true
				).buildString();
			}
		).put(
			"showHideableFromURL",
			ParamUtil.getBoolean(_renderRequest, "showHideable")
		).put(
			"siteNames",
			() -> {
				JSONObject siteNamesJSONObject =
					JSONFactoryUtil.createJSONObject();

				for (ModelInfo modelInfo : modelInfoMap.values()) {
					if (modelInfo._jsonObject == null) {
						continue;
					}

					long groupId = modelInfo._jsonObject.getLong("groupId");

					String groupIdString = String.valueOf(groupId);

					if (!siteNamesJSONObject.has(groupIdString)) {
						Group group = _groupLocalService.fetchGroup(groupId);

						if (group == null) {
							siteNamesJSONObject.put(
								groupIdString,
								_language.get(
									_themeDisplay.getLocale(), "global"));
						}
						else {
							siteNamesJSONObject.put(
								groupIdString,
								group.getName(_themeDisplay.getLocale()));
						}
					}
				}

				return siteNamesJSONObject;
			}
		).put(
			"sitesFromURL", ParamUtil.getString(_renderRequest, "sites")
		).put(
			"spritemap", _themeDisplay.getPathThemeImages() + "/clay/icons.svg"
		).put(
			"statusLabel",
			_language.get(
				_themeDisplay.getLocale(),
				_publicationsDisplayContext.getStatusLabel(
					_ctCollection.getStatus()))
		).put(
			"statusStyle",
			_publicationsDisplayContext.getStatusStyle(
				_ctCollection.getStatus())
		).put(
			"typeNames",
			() -> {
				JSONObject typeNamesJSONObject =
					JSONFactoryUtil.createJSONObject();

				for (long classNameId : classNameIdClassPKsMap.keySet()) {
					String typeName = _getTypeName(
						_themeDisplay.getLocale(), classNameId,
						typeNameCacheMap);

					typeNamesJSONObject.put(
						String.valueOf(classNameId), typeName);
				}

				return typeNamesJSONObject;
			}
		).put(
			"typesFromURL", ParamUtil.getString(_renderRequest, "types")
		).put(
			"unscheduleURL",
			() -> {
				if ((_ctCollection.getStatus() !=
						WorkflowConstants.STATUS_SCHEDULED) ||
					!CTCollectionPermission.contains(
						_themeDisplay.getPermissionChecker(), _ctCollection,
						CTActionKeys.PUBLISH)) {

					return null;
				}

				return PortletURLBuilder.createActionURL(
					_renderResponse
				).setActionName(
					"/change_tracking/unschedule_publication"
				).setParameter(
					"ctCollectionId", _ctCollection.getCtCollectionId()
				).buildString();
			}
		).put(
			"updateCTCommentURL",
			() -> {
				ResourceURL updateCTCommentURL =
					_renderResponse.createResourceURL();

				updateCTCommentURL.setParameter(
					"ctCollectionId",
					String.valueOf(_ctCollection.getCtCollectionId()));
				updateCTCommentURL.setResourceID(
					"/change_tracking/update_ct_comment");

				return updateCTCommentURL.toString();
			}
		).put(
			"userInfo",
			DisplayContextUtil.getUserInfoJSONObject(
				CTEntryTable.INSTANCE.userId.eq(UserTable.INSTANCE.userId),
				CTEntryTable.INSTANCE, _themeDisplay, _userLocalService,
				CTEntryTable.INSTANCE.ctCollectionId.eq(
					_ctCollection.getCtCollectionId()))
		).put(
			"usersFromURL", ParamUtil.getString(_renderRequest, "users")
		).build();
	}

	private JSONObject _getContextViewJSONObject(
		CTClosure ctClosure, Map<ModelInfoKey, ModelInfo> modelInfoMap,
		Set<Long> rootClassNameIds, JSONObject defaultContextViewJSONObject,
		Map<Long, String> typeNameCacheMap) {

		if (ctClosure == null) {
			return defaultContextViewJSONObject;
		}

		JSONObject everythingJSONObject = JSONUtil.put("nodeId", 0);

		Set<Integer> rootModelKeys = new HashSet<>();
		Map<Long, JSONArray> rootDisplayMap = new HashMap<>();

		int nodeIdCounter = 1;

		Queue<ParentModel> queue = new LinkedList<>();

		queue.add(
			new ParentModel(everythingJSONObject, ctClosure.getRootPKsMap()));

		ParentModel parentModel = null;

		while ((parentModel = queue.poll()) != null) {
			if (parentModel._jsonObject == null) {
				continue;
			}

			JSONArray childrenJSONArray = JSONFactoryUtil.createJSONArray();

			for (Map.Entry<Long, List<Long>> entry :
					parentModel._childPKsMap.entrySet()) {

				long modelClassNameId = entry.getKey();

				for (long modelClassPK : entry.getValue()) {
					ModelInfo modelInfo = modelInfoMap.get(
						new ModelInfoKey(modelClassNameId, modelClassPK));

					int modelKey = modelInfo._modelKey;

					int nodeId = nodeIdCounter++;

					JSONObject jsonObject = JSONUtil.put(
						"modelKey", modelKey
					).put(
						"nodeId", nodeId
					);

					childrenJSONArray.put(jsonObject);

					if (rootClassNameIds.contains(modelClassNameId) &&
						rootModelKeys.add(modelKey)) {

						JSONArray jsonArray = rootDisplayMap.computeIfAbsent(
							modelClassNameId,
							key -> JSONFactoryUtil.createJSONArray());

						// Copy JSON object to prevent appending children

						jsonArray.put(
							JSONUtil.put(
								"modelKey", modelKey
							).put(
								"nodeId", nodeId
							));
					}

					Map<Long, List<Long>> childPKsMap =
						ctClosure.getChildPKsMap(
							modelClassNameId, modelClassPK);

					if (!childPKsMap.isEmpty()) {
						queue.add(new ParentModel(jsonObject, childPKsMap));
					}
				}
			}

			parentModel._jsonObject.put("children", childrenJSONArray);
		}

		JSONObject contextViewJSONObject = JSONUtil.put(
			"everything", everythingJSONObject);

		for (Map.Entry<Long, JSONArray> entry : rootDisplayMap.entrySet()) {
			String typeName = _getTypeName(
				_themeDisplay.getLocale(), entry.getKey(), typeNameCacheMap);

			contextViewJSONObject.put(
				typeName, JSONUtil.put("children", entry.getValue()));
		}

		return contextViewJSONObject;
	}

	private JSONArray _getDropdownItemsJSONArray(
			PermissionChecker permissionChecker)
		throws Exception {

		if ((_ctCollection.getStatus() != WorkflowConstants.STATUS_DRAFT) &&
			(_ctCollection.getStatus() != WorkflowConstants.STATUS_EXPIRED)) {

			return null;
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		if (CTCollectionPermission.contains(
				permissionChecker, _ctCollection, ActionKeys.UPDATE)) {

			if (_ctCollection.getCtCollectionId() != _activeCTCollectionId) {
				jsonArray.put(
					JSONUtil.put(
						"disabled",
						_ctCollection.getStatus() ==
							WorkflowConstants.STATUS_EXPIRED
					).put(
						"href",
						PublicationsPortletURLUtil.getHref(
							_renderResponse.createActionURL(),
							ActionRequest.ACTION_NAME,
							"/change_tracking/checkout_ct_collection",
							"redirect", _themeDisplay.getURLCurrent(),
							"ctCollectionId",
							String.valueOf(_ctCollection.getCtCollectionId()))
					).put(
						"label",
						_language.get(
							_httpServletRequest, "work-on-publication")
					).put(
						"symbolLeft", "radio-button"
					));
			}

			if (_ctCollection.getStatus() != WorkflowConstants.STATUS_EXPIRED) {
				jsonArray.put(
					JSONUtil.put(
						"href",
						PublicationsPortletURLUtil.getHref(
							_renderResponse.createRenderURL(),
							"mvcRenderCommandName",
							"/change_tracking/edit_ct_collection", "redirect",
							_themeDisplay.getURLCurrent(), "ctCollectionId",
							String.valueOf(_ctCollection.getCtCollectionId()))
					).put(
						"label", _language.get(_httpServletRequest, "edit")
					).put(
						"symbolLeft", "pencil"
					));
			}
		}

		if ((_ctCollection.getStatus() != WorkflowConstants.STATUS_EXPIRED) &&
			CTCollectionPermission.contains(
				permissionChecker, _ctCollection, ActionKeys.PERMISSIONS)) {

			jsonArray.put(
				JSONUtil.put(
					"href",
					PublicationsPortletURLUtil.getPermissionsHref(
						_httpServletRequest, _ctCollection, _language)
				).put(
					"label", _language.get(_httpServletRequest, "permissions")
				).put(
					"symbolLeft", "password-policies"
				));
		}

		if (CTCollectionPermission.contains(
				permissionChecker, _ctCollection, ActionKeys.DELETE)) {

			jsonArray.put(
				JSONUtil.put("type", "divider")
			).put(
				JSONUtil.put(
					"href",
					PublicationsPortletURLUtil.getDeleteHref(
						_httpServletRequest, _renderResponse, getBackURL(),
						_ctCollection.getCtCollectionId(), _language)
				).put(
					"label", _language.get(_httpServletRequest, "delete")
				).put(
					"symbolLeft", "times-circle"
				)
			);
		}

		return jsonArray;
	}

	private String _getMissingModelMessage(
		long classPK, long modelClassNameId) {

		return StringBundler.concat(
			"Missing model from ", _ctCollection.getName(), ": {classPK=",
			classPK, ", ctCollectionId=", _ctCollection.getCtCollectionId(),
			", modelClassNameId=", modelClassNameId, "}");
	}

	private Set<Long> _getRootClassNameIds(CTClosure ctClosure) {
		if (ctClosure == null) {
			return Collections.emptySet();
		}

		Set<Long> rootClassNameIds = new LinkedHashSet<>();

		for (String className : _ctConfiguration.rootDisplayClassNames()) {
			rootClassNameIds.add(_portal.getClassNameId(className));
		}

		for (String childClassName :
				_ctConfiguration.rootDisplayChildClassNames()) {

			for (long parentClassNameId :
					CTClosureUtil.getParentClassNameIds(
						ctClosure, _portal.getClassNameId(childClassName))) {

				rootClassNameIds.add(parentClassNameId);
			}
		}

		return rootClassNameIds;
	}

	private <T extends BaseModel<T>> String _getTitle(
		long ctCollectionId, CTSQLModeThreadLocal.CTSQLMode ctSQLMode,
		Locale locale, T model, long modelClassNameId,
		Map<Long, String> typeNameCacheMap) {

		CTDisplayRenderer<T> ctDisplayRenderer =
			_ctDisplayRendererRegistry.getCTDisplayRenderer(modelClassNameId);

		if (ctDisplayRenderer instanceof CTModelDisplayRendererAdapter) {
			return StringBundler.concat(
				_getTypeName(locale, modelClassNameId, typeNameCacheMap),
				StringPool.SPACE, model.getPrimaryKeyObj());
		}

		return _ctDisplayRendererRegistry.getTitle(
			ctCollectionId, ctSQLMode, locale, model, modelClassNameId);
	}

	private <T extends BaseModel<T>> String _getTypeName(
		Locale locale, long modelClassNameId,
		Map<Long, String> typeNameCacheMap) {

		CTDisplayRenderer<T> ctDisplayRenderer =
			_ctDisplayRendererRegistry.getCTDisplayRenderer(modelClassNameId);

		if (ctDisplayRenderer instanceof CTModelDisplayRendererAdapter) {
			return typeNameCacheMap.computeIfAbsent(
				modelClassNameId,
				key -> _ctDisplayRendererRegistry.getTypeName(
					locale, modelClassNameId));
		}

		return ctDisplayRenderer.getTypeName(locale);
	}

	private <T extends BaseModel<T>> boolean _isSite(T model) {
		if (model instanceof Group) {
			Group group = (Group)model;

			if (group.isCompany()) {
				return false;
			}

			return group.isSite();
		}

		return false;
	}

	private <T extends BaseModel<T>> void _populateEntryValues(
			Map<ModelInfoKey, ModelInfo> modelInfoMap, long modelClassNameId,
			Set<Long> classPKs, Map<Long, String> typeNameCacheMap)
		throws Exception {

		Map<Serializable, T> baseModelMap = null;
		Map<Serializable, T> ctModelMap = null;

		Map<Serializable, CTEntry> ctEntryMap = new HashMap<>();

		for (CTEntry ctEntry :
				_ctEntryLocalService.getCTEntries(
					_ctCollection.getCtCollectionId(), modelClassNameId)) {

			ctEntryMap.put(ctEntry.getModelClassPK(), ctEntry);
		}

		for (long classPK : classPKs) {
			ModelInfo modelInfo = modelInfoMap.get(
				new ModelInfoKey(modelClassNameId, classPK));

			CTEntry ctEntry = ctEntryMap.get(classPK);

			if (ctEntry == null) {
				if (baseModelMap == null) {
					baseModelMap = _basePersistenceRegistry.fetchBaseModelMap(
						modelClassNameId, classPKs);
				}

				T model = baseModelMap.get(classPK);

				if (model == null) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"Missing model from production: {classPK=",
								classPK, ", modelClassNameId=",
								modelClassNameId, "}"));
					}

					continue;
				}

				modelInfo._jsonObject = JSONUtil.put(
					"hideable",
					_ctDisplayRendererRegistry.isHideable(
						model, modelClassNameId)
				).put(
					"modelClassNameId", modelClassNameId
				).put(
					"modelClassPK", classPK
				).put(
					"modelKey", modelInfo._modelKey
				).put(
					"title",
					_getTitle(
						CTConstants.CT_COLLECTION_ID_PRODUCTION,
						CTSQLModeThreadLocal.CTSQLMode.DEFAULT,
						_themeDisplay.getLocale(), model, modelClassNameId,
						typeNameCacheMap)
				);

				modelInfo._site = _isSite(model);
			}
			else {
				long ctCollectionId =
					_ctDisplayRendererRegistry.getCtCollectionId(
						_ctCollection, ctEntry);

				CTSQLModeThreadLocal.CTSQLMode ctSQLMode =
					_ctDisplayRendererRegistry.getCTSQLMode(
						ctCollectionId, ctEntry);

				T model = null;

				try {
					if ((ctCollectionId == _ctCollection.getCtCollectionId()) &&
						(ctSQLMode == CTSQLModeThreadLocal.CTSQLMode.DEFAULT)) {

						if (ctModelMap == null) {
							ctModelMap =
								_ctDisplayRendererRegistry.fetchCTModelMap(
									_ctCollection.getCtCollectionId(),
									CTSQLModeThreadLocal.CTSQLMode.DEFAULT,
									modelClassNameId, classPKs);
						}

						model = ctModelMap.get(classPK);
					}
					else {
						model = _ctDisplayRendererRegistry.fetchCTModel(
							ctCollectionId, ctSQLMode, modelClassNameId,
							classPK);
					}
				}
				catch (SystemException systemException) {
					if (systemException.getCause() instanceof ORMException) {
						if (_ctCollection.getStatus() !=
								WorkflowConstants.STATUS_EXPIRED) {

							_log.error(
								_getMissingModelMessage(
									classPK, modelClassNameId),
								systemException.getCause());
						}
						else if (_log.isDebugEnabled()) {
							_log.debug(
								_getMissingModelMessage(
									classPK, modelClassNameId),
								systemException.getCause());
						}

						continue;
					}

					throw systemException;
				}

				if (model == null) {
					if ((ctEntry.getChangeType() !=
							CTConstants.CT_CHANGE_TYPE_DELETION) &&
						_log.isWarnEnabled()) {

						_log.warn(
							_getMissingModelMessage(classPK, modelClassNameId));
					}

					continue;
				}

				Date modifiedDate = ctEntry.getModifiedDate();

				modelInfo._ctEntry = true;

				modelInfo._jsonObject = JSONUtil.put(
					"changeType", ctEntry.getChangeType()
				).put(
					"ctEntryId", ctEntry.getCtEntryId()
				).put(
					"hideable",
					_ctDisplayRendererRegistry.isHideable(
						model, modelClassNameId)
				).put(
					"modelClassNameId", ctEntry.getModelClassNameId()
				).put(
					"modelClassPK", ctEntry.getModelClassPK()
				).put(
					"modelKey", modelInfo._modelKey
				).put(
					"modifiedTime", modifiedDate.getTime()
				).put(
					"timeDescription",
					_language.getTimeDescription(
						_httpServletRequest,
						System.currentTimeMillis() - modifiedDate.getTime(),
						true)
				).put(
					"title",
					_getTitle(
						ctCollectionId, ctSQLMode, _themeDisplay.getLocale(),
						model, modelClassNameId, typeNameCacheMap)
				).put(
					"userId", ctEntry.getUserId()
				);

				if (model instanceof GroupedModel) {
					GroupedModel groupedModel = (GroupedModel)model;

					modelInfo._jsonObject.put(
						"groupId", groupedModel.getGroupId());
				}

				modelInfo._site = _isSite(model);
			}
		}
	}

	private void _populateModelInfoGroupIds(
		CTClosure ctClosure, Map<ModelInfoKey, ModelInfo> modelInfoMap,
		long groupClassNameId, long groupId) {

		ModelInfo groupModelInfo = modelInfoMap.get(
			new ModelInfoKey(groupClassNameId, groupId));

		if (!groupModelInfo._site) {
			return;
		}

		Map<Long, List<Long>> pksMap = ctClosure.getChildPKsMap(
			groupClassNameId, groupId);

		Deque<Map.Entry<Long, ? extends Collection<Long>>> queue =
			new LinkedList<>(pksMap.entrySet());

		Map.Entry<Long, ? extends Collection<Long>> entry = null;

		while ((entry = queue.poll()) != null) {
			long classNameId = entry.getKey();

			for (long classPK : entry.getValue()) {
				ModelInfo modelInfo = modelInfoMap.get(
					new ModelInfoKey(classNameId, classPK));

				if (modelInfo._jsonObject != null) {
					modelInfo._jsonObject.put("groupId", groupId);
				}

				Map<Long, ? extends Collection<Long>> childPKsMap =
					ctClosure.getChildPKsMap(classNameId, classPK);

				if (!childPKsMap.isEmpty()) {
					queue.addAll(childPKsMap.entrySet());
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ViewChangesDisplayContext.class);

	private final long _activeCTCollectionId;
	private final BasePersistenceRegistry _basePersistenceRegistry;
	private final CTClosureFactory _ctClosureFactory;
	private final CTCollection _ctCollection;
	private final CTCollectionLocalService _ctCollectionLocalService;
	private final CTConfiguration _ctConfiguration;
	private final CTDisplayRendererRegistry _ctDisplayRendererRegistry;
	private final CTEntryLocalService _ctEntryLocalService;
	private final CTSchemaVersionLocalService _ctSchemaVersionLocalService;
	private final GroupLocalService _groupLocalService;
	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final Portal _portal;
	private final PublicationsDisplayContext _publicationsDisplayContext;
	private final PublishScheduler _publishScheduler;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;
	private final UserLocalService _userLocalService;

	private static class ModelInfo {

		private ModelInfo(int modelKey) {
			_modelKey = modelKey;
		}

		private boolean _ctEntry;
		private JSONObject _jsonObject;
		private final int _modelKey;
		private boolean _site;

	}

	private static class ModelInfoKey {

		@Override
		public boolean equals(Object object) {
			if (object instanceof ModelInfoKey) {
				ModelInfoKey modelInfoKey = (ModelInfoKey)object;

				if ((modelInfoKey._classNameId == _classNameId) &&
					(modelInfoKey._classPK == _classPK)) {

					return true;
				}
			}

			return false;
		}

		@Override
		public int hashCode() {
			return HashUtil.hash((int)_classNameId, _classPK);
		}

		private ModelInfoKey(long classNameId, long classPK) {
			_classNameId = classNameId;
			_classPK = classPK;
		}

		private final long _classNameId;
		private final long _classPK;

	}

	private static class ParentModel {

		private ParentModel(
			JSONObject jsonObject, Map<Long, List<Long>> childPKsMap) {

			_jsonObject = jsonObject;
			_childPKsMap = childPKsMap;
		}

		private final Map<Long, List<Long>> _childPKsMap;
		private final JSONObject _jsonObject;

	}

}
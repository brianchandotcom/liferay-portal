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
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.web.internal.configuration.CTConfiguration;
import com.liferay.change.tracking.web.internal.display.BasePersistenceRegistry;
import com.liferay.change.tracking.web.internal.display.CTClosureUtil;
import com.liferay.change.tracking.web.internal.display.CTDisplayRendererRegistry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.change.tracking.sql.CTSQLModeThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

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
		long activeCtCollectionId,
		BasePersistenceRegistry basePersistenceRegistry,
		CTClosureFactory ctClosureFactory, CTCollection ctCollection,
		CTConfiguration ctConfiguration,
		CTDisplayRendererRegistry ctDisplayRendererRegistry,
		CTEntryLocalService ctEntryLocalService, Language language,
		Portal portal, RenderRequest renderRequest,
		RenderResponse renderResponse, UserLocalService userLocalService) {

		_activeCtCollectionId = activeCtCollectionId;
		_basePersistenceRegistry = basePersistenceRegistry;
		_ctClosureFactory = ctClosureFactory;
		_ctCollection = ctCollection;
		_ctConfiguration = ctConfiguration;
		_ctDisplayRendererRegistry = ctDisplayRendererRegistry;
		_ctEntryLocalService = ctEntryLocalService;
		_language = language;
		_portal = portal;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_userLocalService = userLocalService;

		int count = _ctEntryLocalService.getCTCollectionCTEntriesCount(
			_ctCollection.getCtCollectionId());

		if (count > 0) {
			_hasChanges = true;
		}
		else {
			_hasChanges = false;
		}

		_httpServletRequest = _portal.getHttpServletRequest(renderRequest);

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getBackURL() {
		String backURL = ParamUtil.getString(_renderRequest, "backURL");

		if (Validator.isNotNull(backURL)) {
			return backURL;
		}

		PortletURL portletURL = _renderResponse.createRenderURL();

		return portletURL.toString();
	}

	public CTCollection getCtCollection() {
		return _ctCollection;
	}

	public Map<String, Object> getReactData() throws PortalException {
		JSONObject entryDataJSONObject = _getEntryDataJSONObject();

		JSONObject entriesJSONObject = entryDataJSONObject.getJSONObject(
			"entries");

		JSONArray changesJSONArray = JSONFactoryUtil.createJSONArray();
		JSONObject userInfoJSONObject = JSONFactoryUtil.createJSONObject();

		for (String key : entriesJSONObject.keySet()) {
			JSONObject entryJSONObject = entriesJSONObject.getJSONObject(key);

			if (entryJSONObject.has("ctEntryId")) {
				changesJSONArray.put(key);

				long userId = entryJSONObject.getLong("userId");

				if (!userInfoJSONObject.has(String.valueOf(userId))) {
					String portraitURL = null;
					String userName = StringPool.BLANK;

					User user = _userLocalService.fetchUser(userId);

					if (user != null) {
						if (user.getPortraitId() != 0) {
							try {
								portraitURL = user.getPortraitURL(
									_themeDisplay);
							}
							catch (PortalException portalException) {
								_log.error(portalException, portalException);
							}
						}

						userName = user.getFullName();
					}

					userInfoJSONObject.put(
						String.valueOf(userId),
						JSONUtil.put(
							"portraitURL", portraitURL
						).put(
							"userName", userName
						));
				}
			}
		}

		ResourceURL renderCTEntryURL = _renderResponse.createResourceURL();

		renderCTEntryURL.setResourceID("/change_lists/render_ct_entry");

		renderCTEntryURL.setParameter(
			"ctCollectionId",
			String.valueOf(_ctCollection.getCtCollectionId()));

		ResourceURL renderDiffURL = _renderResponse.createResourceURL();

		renderDiffURL.setResourceID("/change_lists/render_diff");

		return HashMapBuilder.<String, Object>put(
			"changes", changesJSONArray
		).put(
			"contextView", entryDataJSONObject.getJSONObject("contextView")
		).put(
			"entries", entriesJSONObject
		).put(
			"renderCTEntryURL", renderCTEntryURL.toString()
		).put(
			"renderDiffURL", renderDiffURL.toString()
		).put(
			"rootDisplayClasses",
			entryDataJSONObject.getJSONArray("rootDisplayClasses")
		).put(
			"spritemap",
			_themeDisplay.getPathThemeImages() + "/lexicon/icons.svg"
		).put(
			"typeNames", entryDataJSONObject.getJSONObject("typeNames")
		).put(
			"userInfo", userInfoJSONObject
		).build();
	}

	public String getStatusLabel(int status) {
		if (status == WorkflowConstants.STATUS_APPROVED) {
			return "published";
		}
		else if (status == WorkflowConstants.STATUS_DRAFT) {
			return "in-progress";
		}
		else if (status == WorkflowConstants.STATUS_DENIED) {
			return "failed";
		}

		return StringPool.BLANK;
	}

	public boolean hasChanges() {
		return _hasChanges;
	}

	private void _addRootDisplayNode(
		JSONObject node, List<JSONObject> rootDisplayNodes) {

		if (rootDisplayNodes == null) {
			return;
		}

		for (JSONObject rootDisplayNode : rootDisplayNodes) {
			if (rootDisplayNode.getInt("entryId") == node.getInt("entryId")) {
				return;
			}
		}

		rootDisplayNodes.add(node);
	}

	private JSONArray _getChildren(
		AtomicInteger nodeIdCounter, Map<Long, List<Long>> childPKsMap) {

		JSONArray childrenJSONArray = JSONFactoryUtil.createJSONArray();

		for (Map.Entry<Long, List<Long>> entry : childPKsMap.entrySet()) {
			for (long classPK : entry.getValue()) {
				childrenJSONArray.put(
					JSONUtil.put(
						"modelClassNameId", entry.getKey()
					).put(
						"modelClassPK", classPK
					).put(
						"nodeId", nodeIdCounter.getAndIncrement()
					));
			}
		}

		return childrenJSONArray;
	}

	private Map<Long, Map<Long, Integer>> _getCTEntriesEntryIdMapMap() {
		Map<Long, Map<Long, Integer>> entryIdMapMap = new HashMap<>();

		List<CTEntry> ctEntries = _ctEntryLocalService.getCTCollectionCTEntries(
			_ctCollection.getCtCollectionId());

		int entryIdCounter = 1;

		for (CTEntry ctEntry : ctEntries) {
			Map<Long, Integer> entryIdMap = entryIdMapMap.computeIfAbsent(
				ctEntry.getModelClassNameId(), key -> new HashMap<>());

			entryIdMap.put(ctEntry.getModelClassPK(), entryIdCounter++);
		}

		return entryIdMapMap;
	}

	private Map<Serializable, CTEntry> _getCTEntryMap(long classNameId) {
		Map<Serializable, CTEntry> ctEntryMap = new HashMap<>();

		for (CTEntry ctEntry :
				_ctEntryLocalService.getCTEntries(
					_ctCollection.getCtCollectionId(), classNameId)) {

			ctEntryMap.put(ctEntry.getModelClassPK(), ctEntry);
		}

		return ctEntryMap;
	}

	private <T extends BaseModel<T>> List<JSONObject> _getEntries(
			Map<Long, Integer> entryIdMap, long modelClassNameId)
		throws PortalException {

		Map<Serializable, T> baseModelMap =
			_basePersistenceRegistry.fetchBaseModelMap(
				modelClassNameId, new ArrayList<>(entryIdMap.keySet()));

		Map<Serializable, CTEntry> ctEntryMap = _getCTEntryMap(
			modelClassNameId);

		List<JSONObject> entries = new ArrayList<>();

		for (Map.Entry<Long, Integer> entry : entryIdMap.entrySet()) {
			long modelClassPK = entry.getKey();

			CTEntry ctEntry = ctEntryMap.get(modelClassPK);

			if (ctEntry == null) {
				T baseModel = baseModelMap.get(modelClassPK);

				if (baseModel == null) {
					baseModel = _ctDisplayRendererRegistry.fetchCTModel(
						CTConstants.CT_COLLECTION_ID_PRODUCTION,
						CTSQLModeThreadLocal.CTSQLMode.DEFAULT,
						modelClassNameId, modelClassPK);
				}

				entries.add(
					JSONUtil.put(
						"entryId", entry.getValue()
					).put(
						"modelClassNameId", modelClassNameId
					).put(
						"modelClassPK", modelClassPK
					).put(
						"title",
						_ctDisplayRendererRegistry.getTitle(
							CTConstants.CT_COLLECTION_ID_PRODUCTION,
							CTSQLModeThreadLocal.CTSQLMode.DEFAULT,
							_themeDisplay.getLocale(), baseModel,
							modelClassNameId)
					));
			}
			else {
				JSONArray dropdownItemsJSONArray =
					JSONFactoryUtil.createJSONArray();

				if ((_ctCollection.getCtCollectionId() ==
						_activeCtCollectionId) &&
					(ctEntry.getChangeType() !=
						CTConstants.CT_CHANGE_TYPE_DELETION)) {

					String editURL = _ctDisplayRendererRegistry.getEditURL(
						_httpServletRequest, ctEntry);

					if (Validator.isNotNull(editURL)) {
						dropdownItemsJSONArray.put(
							JSONUtil.put(
								"href", editURL
							).put(
								"label",
								_language.get(_httpServletRequest, "edit")
							));
					}
				}

				Date modifiedDate = ctEntry.getModifiedDate();

				entries.add(
					JSONUtil.put(
						"ctEntryId", ctEntry.getCtEntryId()
					).put(
						"description",
						_ctDisplayRendererRegistry.getEntryDescription(
							_httpServletRequest, ctEntry)
					).put(
						"dropdownItems", dropdownItemsJSONArray
					).put(
						"entryId", entry.getValue()
					).put(
						"modelClassNameId", ctEntry.getModelClassNameId()
					).put(
						"modelClassPK", ctEntry.getModelClassPK()
					).put(
						"modifiedTime", modifiedDate.getTime()
					).put(
						"timeDescription",
						_language.format(
							_httpServletRequest, "x-ago",
							new Object[] {
								_language.getTimeDescription(
									_httpServletRequest,
									System.currentTimeMillis() -
										modifiedDate.getTime(),
									true)
							},
							false)
					).put(
						"title",
						_ctDisplayRendererRegistry.getTitle(
							_ctCollection, ctEntry, _themeDisplay.getLocale())
					).put(
						"userId", ctEntry.getUserId()
					));
			}
		}

		return entries;
	}

	private JSONObject _getEntryDataJSONObject() throws PortalException {
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

		Map<Long, Map<Long, Integer>> entryIdMapMap = new HashMap<>();
		Map<Long, List<JSONObject>> rootDisplayMap = new LinkedHashMap<>();

		if (ctClosure == null) {
			entryIdMapMap = _getCTEntriesEntryIdMapMap();
		}
		else {
			JSONObject everythingJSONObject = JSONUtil.put("nodeId", 0);

			for (String className : _ctConfiguration.rootDisplayClassNames()) {
				rootDisplayMap.put(
					_portal.getClassNameId(className), new ArrayList<>());
			}

			for (String childClassName :
					_ctConfiguration.rootDisplayChildClassNames()) {

				for (long parentClassNameId :
						CTClosureUtil.getParentClassNameIds(
							ctClosure,
							_portal.getClassNameId(childClassName))) {

					rootDisplayMap.computeIfAbsent(
						parentClassNameId, key -> new ArrayList<>());
				}
			}

			int entryIdCounter = 1;
			AtomicInteger nodeIdCounter = new AtomicInteger(1);

			Deque<JSONObject> deque = new LinkedList<>();

			deque.push(everythingJSONObject);

			JSONObject jsonObject = null;

			while ((jsonObject = deque.poll()) != null) {
				Map<Long, List<Long>> childPKsMap = null;

				if (jsonObject.getInt("nodeId") == 0) {
					childPKsMap = ctClosure.getRootPKsMap();
				}
				else {
					long modelClassNameId = jsonObject.getLong(
						"modelClassNameId");
					long modelClassPK = jsonObject.getLong("modelClassPK");

					childPKsMap = ctClosure.getChildPKsMap(
						modelClassNameId, modelClassPK);

					Map<Long, Integer> entryIdMap =
						entryIdMapMap.computeIfAbsent(
							modelClassNameId, key -> new HashMap<>());

					Integer entryId = entryIdMap.get(modelClassPK);

					if (entryId == null) {
						entryId = entryIdCounter++;

						entryIdMap.put(modelClassPK, entryId);
					}

					jsonObject.put("entryId", entryId);

					_addRootDisplayNode(
						jsonObject, rootDisplayMap.get(modelClassNameId));

					jsonObject.remove("modelClassNameId");
					jsonObject.remove("modelClassPK");
				}

				JSONArray childrenJSONArray = _getChildren(
					nodeIdCounter, childPKsMap);

				if (childrenJSONArray.length() == 0) {
					continue;
				}

				for (int i = 0; i < childrenJSONArray.length(); i++) {
					deque.push(childrenJSONArray.getJSONObject(i));
				}

				jsonObject.put("children", childrenJSONArray);
			}

			contextViewJSONObject = JSONUtil.put(
				"everything", everythingJSONObject);
		}

		JSONObject entriesJSONObject = JSONFactoryUtil.createJSONObject();
		JSONObject typeNamesJSONObject = JSONFactoryUtil.createJSONObject();

		for (Map.Entry<Long, Map<Long, Integer>> entry :
				entryIdMapMap.entrySet()) {

			List<JSONObject> entryJSONObjects = _getEntries(
				entry.getValue(), entry.getKey());

			for (JSONObject entryJSONObject : entryJSONObjects) {
				entriesJSONObject.put(
					String.valueOf(entryJSONObject.remove("entryId")),
					entryJSONObject);
			}

			String typeName = _ctDisplayRendererRegistry.getTypeName(
				_themeDisplay.getLocale(), entry.getKey());

			typeNamesJSONObject.put(String.valueOf(entry.getKey()), typeName);
		}

		JSONArray rootDisplayClassesJSONArray =
			JSONFactoryUtil.createJSONArray();

		if (ctClosure != null) {
			for (Map.Entry<Long, List<JSONObject>> entry :
					rootDisplayMap.entrySet()) {

				List<JSONObject> rootDisplayNodes = entry.getValue();

				if (!rootDisplayNodes.isEmpty()) {
					JSONArray nodesJSONArray =
						JSONFactoryUtil.createJSONArray();

					for (JSONObject rootDisplayNode : rootDisplayNodes) {
						nodesJSONArray.put(
							JSONUtil.put(
								"entryId", rootDisplayNode.getInt("entryId")
							).put(
								"nodeId", rootDisplayNode.getInt("nodeId")
							));
					}

					String typeName = typeNamesJSONObject.getString(
						String.valueOf(entry.getKey()));

					contextViewJSONObject.put(typeName, nodesJSONArray);

					rootDisplayClassesJSONArray.put(typeName);
				}
			}
		}

		return JSONUtil.put(
			"contextView", contextViewJSONObject
		).put(
			"entries", entriesJSONObject
		).put(
			"rootDisplayClasses", rootDisplayClassesJSONArray
		).put(
			"typeNames", typeNamesJSONObject
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ViewChangesDisplayContext.class);

	private final long _activeCtCollectionId;
	private final BasePersistenceRegistry _basePersistenceRegistry;
	private final CTClosureFactory _ctClosureFactory;
	private final CTCollection _ctCollection;
	private final CTConfiguration _ctConfiguration;
	private final CTDisplayRendererRegistry _ctDisplayRendererRegistry;
	private final CTEntryLocalService _ctEntryLocalService;
	private final boolean _hasChanges;
	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final Portal _portal;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;
	private final UserLocalService _userLocalService;

}
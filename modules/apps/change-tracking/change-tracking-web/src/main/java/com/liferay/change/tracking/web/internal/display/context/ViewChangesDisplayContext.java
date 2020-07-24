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
import com.liferay.change.tracking.model.CTEntryTable;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.web.internal.configuration.CTConfiguration;
import com.liferay.change.tracking.web.internal.display.BasePersistenceRegistry;
import com.liferay.change.tracking.web.internal.display.CTClosureUtil;
import com.liferay.change.tracking.web.internal.display.CTDisplayRendererRegistry;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
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
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

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
		Map<EntryKey, EntryValue> entryMap = new HashMap<>();

		if (ctClosure == null) {
			List<CTEntry> ctEntries =
				_ctEntryLocalService.getCTCollectionCTEntries(
					_ctCollection.getCtCollectionId());

			int entryIdCounter = 1;

			for (CTEntry ctEntry : ctEntries) {
				entryMap.put(
					new EntryKey(
						ctEntry.getModelClassNameId(),
						ctEntry.getModelClassPK()),
					new EntryValue(entryIdCounter++));

				Set<Long> classPKs = classNameIdClassPKsMap.computeIfAbsent(
					ctEntry.getModelClassNameId(), key -> new HashSet<>());

				classPKs.add(ctEntry.getModelClassPK());
			}
		}
		else {
			int[] entryIdCounterHolder = {1};

			Map<Long, List<Long>> rootPKsMap = ctClosure.getRootPKsMap();

			Queue<Map.Entry<Long, List<Long>>> queue = new LinkedList<>(
				rootPKsMap.entrySet());

			Map.Entry<Long, List<Long>> entry = null;

			while ((entry = queue.poll()) != null) {
				long classNameId = entry.getKey();

				Set<Long> classPKs = classNameIdClassPKsMap.computeIfAbsent(
					classNameId, key -> new HashSet<>());

				classPKs.addAll(entry.getValue());

				for (long pk : entry.getValue()) {
					EntryKey entryKey = new EntryKey(classNameId, pk);

					if (!entryMap.containsKey(entryKey)) {
						entryMap.put(
							entryKey,
							new EntryValue(entryIdCounterHolder[0]++));

						Map<Long, List<Long>> childPKsMap =
							ctClosure.getChildPKsMap(classNameId, pk);

						if (!childPKsMap.isEmpty()) {
							queue.addAll(childPKsMap.entrySet());
						}
					}
				}
			}
		}

		for (Map.Entry<Long, Set<Long>> entry :
				classNameIdClassPKsMap.entrySet()) {

			_populateEntryValues(entryMap, entry.getKey(), entry.getValue());
		}

		Set<Long> rootClassNameIds = _getRootClassNameIds(ctClosure);

		return HashMapBuilder.<String, Object>put(
			"changes",
			() -> {
				JSONArray changesJSONArray = JSONFactoryUtil.createJSONArray();

				for (EntryValue entryValue : entryMap.values()) {
					if (entryValue._ctEntry) {
						changesJSONArray.put(entryValue._entryId);
					}
				}

				return changesJSONArray;
			}
		).put(
			"contextView",
			_getContextViewJSONObject(
				ctClosure, entryMap, rootClassNameIds, contextViewJSONObject)
		).put(
			"entries",
			() -> {
				JSONObject entriesJSONObject =
					JSONFactoryUtil.createJSONObject();

				for (EntryValue entryValue : entryMap.values()) {
					entriesJSONObject.put(
						String.valueOf(entryValue._entryId),
						entryValue._jsonObject);
				}

				return entriesJSONObject;
			}
		).put(
			"renderCTEntryURL",
			() -> {
				ResourceURL renderCTEntryURL =
					_renderResponse.createResourceURL();

				renderCTEntryURL.setResourceID("/change_lists/render_ct_entry");

				renderCTEntryURL.setParameter(
					"ctCollectionId",
					String.valueOf(_ctCollection.getCtCollectionId()));

				return renderCTEntryURL.toString();
			}
		).put(
			"renderDiffURL",
			() -> {
				ResourceURL renderDiffURL = _renderResponse.createResourceURL();

				renderDiffURL.setResourceID("/change_lists/render_diff");

				return renderDiffURL.toString();
			}
		).put(
			"rootDisplayClasses",
			() -> {
				JSONArray rootDisplayClassesJSONArray =
					JSONFactoryUtil.createJSONArray();

				for (long rootClassNameId : rootClassNameIds) {
					if (classNameIdClassPKsMap.containsKey(rootClassNameId)) {
						rootDisplayClassesJSONArray.put(
							_ctDisplayRendererRegistry.getTypeName(
								_themeDisplay.getLocale(), rootClassNameId));
					}
				}

				return rootDisplayClassesJSONArray;
			}
		).put(
			"spritemap",
			_themeDisplay.getPathThemeImages() + "/lexicon/icons.svg"
		).put(
			"typeNames",
			() -> {
				JSONObject typeNamesJSONObject =
					JSONFactoryUtil.createJSONObject();

				for (long classNameId : classNameIdClassPKsMap.keySet()) {
					String typeName = _ctDisplayRendererRegistry.getTypeName(
						_themeDisplay.getLocale(), classNameId);

					typeNamesJSONObject.put(
						String.valueOf(classNameId), typeName);
				}

				return typeNamesJSONObject;
			}
		).put(
			"userInfo", _getUserInfoJSONObject()
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

	private JSONObject _getContextViewJSONObject(
		CTClosure ctClosure, Map<EntryKey, EntryValue> entryMap,
		Set<Long> rootClassNameIds, JSONObject defaultContextViewJSONObject) {

		if (ctClosure == null) {
			return defaultContextViewJSONObject;
		}

		JSONObject everythingJSONObject = JSONUtil.put("nodeId", 0);

		Set<Integer> rootEntryIds = new HashSet<>();
		Map<Long, JSONArray> rootDisplayMap = new HashMap<>();

		int nodeIdCounter = 1;

		Queue<ParentEntry> queue = new LinkedList<>();

		queue.add(
			new ParentEntry(everythingJSONObject, ctClosure.getRootPKsMap()));

		ParentEntry parentEntry = null;

		while ((parentEntry = queue.poll()) != null) {
			JSONArray childrenJSONArray = JSONFactoryUtil.createJSONArray();

			for (Map.Entry<Long, List<Long>> entry :
					parentEntry._childPKsMap.entrySet()) {

				long modelClassNameId = entry.getKey();

				for (long modelClassPK : entry.getValue()) {
					EntryValue entryValue = entryMap.get(
						new EntryKey(modelClassNameId, modelClassPK));

					int entryId = entryValue._entryId;

					int nodeId = nodeIdCounter++;

					JSONObject jsonObject = JSONUtil.put(
						"entryId", entryId
					).put(
						"nodeId", nodeId
					);

					childrenJSONArray.put(jsonObject);

					if (rootClassNameIds.contains(modelClassNameId) &&
						rootEntryIds.add(entryId)) {

						JSONArray jsonArray = rootDisplayMap.computeIfAbsent(
							modelClassNameId,
							key -> JSONFactoryUtil.createJSONArray());

						// Copy jsonObject to prevent appending children

						jsonArray.put(
							JSONUtil.put(
								"entryId", entryId
							).put(
								"nodeId", nodeId
							));
					}

					Map<Long, List<Long>> childPKsMap =
						ctClosure.getChildPKsMap(
							modelClassNameId, modelClassPK);

					if (!childPKsMap.isEmpty()) {
						queue.add(new ParentEntry(jsonObject, childPKsMap));
					}
				}
			}

			parentEntry._jsonObject.put("children", childrenJSONArray);
		}

		JSONObject contextViewJSONObject = JSONUtil.put(
			"everything", everythingJSONObject);

		for (Map.Entry<Long, JSONArray> entry : rootDisplayMap.entrySet()) {
			String typeName = _ctDisplayRendererRegistry.getTypeName(
				_themeDisplay.getLocale(), entry.getKey());

			contextViewJSONObject.put(typeName, entry.getValue());
		}

		return contextViewJSONObject;
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

	private JSONObject _getUserInfoJSONObject() {
		JSONObject userInfoJSONObject = JSONFactoryUtil.createJSONObject();

		List<User> users = _userLocalService.dslQuery(
			DSLQueryFactoryUtil.select(
				UserTable.INSTANCE
			).from(
				UserTable.INSTANCE
			).innerJoinON(
				CTEntryTable.INSTANCE,
				CTEntryTable.INSTANCE.userId.eq(UserTable.INSTANCE.userId)
			).where(
				CTEntryTable.INSTANCE.ctCollectionId.eq(
					_ctCollection.getCtCollectionId())
			));

		for (User user : users) {
			JSONObject userJSONObject = JSONUtil.put(
				"userName", user.getFullName());

			if (user.getPortraitId() != 0) {
				try {
					userJSONObject.put(
						"portraitURL", user.getPortraitURL(_themeDisplay));
				}
				catch (PortalException portalException) {
					_log.error(portalException, portalException);
				}
			}

			userInfoJSONObject.put(
				String.valueOf(user.getUserId()), userJSONObject);
		}

		return userInfoJSONObject;
	}

	private <T extends BaseModel<T>> void _populateEntryValues(
			Map<EntryKey, EntryValue> entryMap, long modelClassNameId,
			Set<Long> classPKs)
		throws PortalException {

		Map<Serializable, T> baseModelMap =
			_basePersistenceRegistry.fetchBaseModelMap(
				modelClassNameId, classPKs);

		Map<Serializable, CTEntry> ctEntryMap = new HashMap<>();

		for (CTEntry ctEntry :
				_ctEntryLocalService.getCTEntries(
					_ctCollection.getCtCollectionId(), modelClassNameId)) {

			ctEntryMap.put(ctEntry.getModelClassPK(), ctEntry);
		}

		for (long classPK : classPKs) {
			EntryValue entryValue = entryMap.get(
				new EntryKey(modelClassNameId, classPK));

			CTEntry ctEntry = ctEntryMap.get(classPK);

			if (ctEntry == null) {
				T baseModel = baseModelMap.get(classPK);

				if (baseModel == null) {
					baseModel = _ctDisplayRendererRegistry.fetchCTModel(
						CTConstants.CT_COLLECTION_ID_PRODUCTION,
						CTSQLModeThreadLocal.CTSQLMode.DEFAULT,
						modelClassNameId, classPK);
				}

				entryValue._jsonObject = JSONUtil.put(
					"entryId", entryValue._entryId
				).put(
					"modelClassNameId", modelClassNameId
				).put(
					"modelClassPK", classPK
				).put(
					"title",
					_ctDisplayRendererRegistry.getTitle(
						CTConstants.CT_COLLECTION_ID_PRODUCTION,
						CTSQLModeThreadLocal.CTSQLMode.DEFAULT,
						_themeDisplay.getLocale(), baseModel, modelClassNameId)
				);
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

				entryValue._ctEntry = true;

				entryValue._jsonObject = JSONUtil.put(
					"ctEntryId", ctEntry.getCtEntryId()
				).put(
					"description",
					_ctDisplayRendererRegistry.getEntryDescription(
						_httpServletRequest, ctEntry)
				).put(
					"dropdownItems", dropdownItemsJSONArray
				).put(
					"entryId", entryValue._entryId
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
				);
			}
		}
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

	private static class EntryKey {

		@Override
		public boolean equals(Object object) {
			if (object instanceof EntryKey) {
				EntryKey entryKey = (EntryKey)object;

				if ((entryKey._classNameId == _classNameId) &&
					(entryKey._classPK == _classPK)) {

					return true;
				}
			}

			return false;
		}

		@Override
		public int hashCode() {
			return HashUtil.hash((int)_classNameId, _classPK);
		}

		private EntryKey(long classNameId, long classPK) {
			_classNameId = classNameId;
			_classPK = classPK;
		}

		private final long _classNameId;
		private final long _classPK;

	}

	private static class EntryValue {

		private EntryValue(int entryId) {
			_entryId = entryId;
		}

		private boolean _ctEntry;
		private final int _entryId;
		private JSONObject _jsonObject;

	}

	private static class ParentEntry {

		private ParentEntry(
			JSONObject jsonObject, Map<Long, List<Long>> childPKsMap) {

			_jsonObject = jsonObject;
			_childPKsMap = childPKsMap;
		}

		private final Map<Long, List<Long>> _childPKsMap;
		private final JSONObject _jsonObject;

	}

}
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

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.reference.closure.CTClosure;
import com.liferay.change.tracking.reference.closure.CTClosureFactory;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.web.internal.configuration.CTConfiguration;
import com.liferay.change.tracking.web.internal.display.BasePersistenceRegistry;
import com.liferay.change.tracking.web.internal.display.CTClosureUtil;
import com.liferay.change.tracking.web.internal.display.CTDisplayRendererRegistry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.change.tracking.sql.CTSQLModeThreadLocal;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
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

		int count = _ctEntryLocalService.getCTCollectionCTEntriesCount(
			_ctCollection.getCtCollectionId());

		if (count > 0) {
			_hasChanges = true;
		}
		else {
			_hasChanges = false;
		}

		_language = language;

		_portal = portal;

		_httpServletRequest = _portal.getHttpServletRequest(renderRequest);

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_userLocalService = userLocalService;
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
		return HashMapBuilder.<String, Object>put(
			"changes", _getChangesJSONObject()
		).put(
			"contextView", _getContextViewJSONObject()
		).put(
			"spritemap",
			_themeDisplay.getPathThemeImages() + "/lexicon/icons.svg"
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
			if ((rootDisplayNode.getLong("modelClassNameId") == node.getLong(
					"modelClassNameId")) &&
				(rootDisplayNode.getLong("modelClassPK") == node.getLong(
					"modelClassPK"))) {

				return;
			}
		}

		rootDisplayNodes.add(node);
	}

	private JSONObject _getChangesJSONObject() throws PortalException {
		int counter = 1;

		JSONArray children = JSONFactoryUtil.createJSONArray();

		List<CTEntry> ctEntries = _ctEntryLocalService.getCTCollectionCTEntries(
			_ctCollection.getCtCollectionId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		for (CTEntry ctEntry : ctEntries) {
			String title = _ctDisplayRendererRegistry.getTitle(
				_ctCollection, ctEntry, _themeDisplay.getLocale());

			JSONArray dropdownItemsJSONArray =
				JSONFactoryUtil.createJSONArray();

			if ((_ctCollection.getCtCollectionId() == _activeCtCollectionId) &&
				(ctEntry.getChangeType() !=
					CTConstants.CT_CHANGE_TYPE_DELETION)) {

				String editURL = _ctDisplayRendererRegistry.getEditURL(
					_httpServletRequest, ctEntry);

				if (Validator.isNotNull(editURL)) {
					dropdownItemsJSONArray.put(
						JSONUtil.put(
							"href", editURL
						).put(
							"label", _language.get(_httpServletRequest, "edit")
						));
				}
			}

			Date modifiedDate = ctEntry.getModifiedDate();

			String portraitURL = null;

			User user = _userLocalService.fetchUser(ctEntry.getUserId());

			if ((user != null) && (user.getPortraitId() != 0)) {
				try {
					portraitURL = user.getPortraitURL(_themeDisplay);
				}
				catch (PortalException portalException) {
					_log.error(portalException, portalException);
				}
			}

			ResourceURL renderURL = _renderResponse.createResourceURL();

			renderURL.setResourceID("/change_lists/render_diff");

			renderURL.setParameter(
				"ctEntryId", String.valueOf(ctEntry.getCtEntryId()));

			children.put(
				JSONUtil.put(
					"description",
					_ctDisplayRendererRegistry.getEntryDescription(
						_httpServletRequest, ctEntry)
				).put(
					"dropdownItems", dropdownItemsJSONArray
				).put(
					"id", counter++
				).put(
					"modelClassNameId", ctEntry.getModelClassNameId()
				).put(
					"modelClassPK", ctEntry.getModelClassPK()
				).put(
					"modifiedTime", modifiedDate.getTime()
				).put(
					"portraitURL", portraitURL
				).put(
					"renderURL", renderURL.toString()
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
					"title", title
				).put(
					"typeName", _getTypeName(ctEntry.getModelClassNameId())
				).put(
					"userId", ctEntry.getUserId()
				).put(
					"userName", ctEntry.getUserName()
				));
		}

		return JSONUtil.put(
			"children", children
		).put(
			"id", 0
		).put(
			"title", _language.get(_httpServletRequest, "home")
		);
	}

	private JSONArray _getChildren(
			AtomicInteger nodeIdCounter, Map<Long, List<Long>> childPKsMap)
		throws PortalException {

		JSONArray children = JSONFactoryUtil.createJSONArray();

		for (Map.Entry<Long, List<Long>> entry : childPKsMap.entrySet()) {
			long classNameId = entry.getKey();
			List<Long> classPKs = entry.getValue();

			Map<Serializable, ? extends BaseModel<?>> baseModelMap =
				_basePersistenceRegistry.fetchBaseModelMap(
					classNameId, classPKs);

			Map<Serializable, CTEntry> ctEntryMap = _getCTEntryMap(classNameId);

			for (long classPK : classPKs) {
				CTEntry ctEntry = ctEntryMap.get(classPK);

				JSONObject childJSONObject = JSONUtil.put(
					"modelClassNameId", classNameId
				).put(
					"modelClassPK", classPK
				).put(
					"typeName", _getTypeName(classNameId)
				);

				JSONArray dropdownItemsJSONArray =
					JSONFactoryUtil.createJSONArray();
				ResourceURL renderURL = _renderResponse.createResourceURL();

				if (ctEntry == null) {
					renderURL.setResourceID("/change_lists/render_ct_entry");

					renderURL.setParameter(
						"modelClassNameId", String.valueOf(classNameId));
					renderURL.setParameter(
						"modelClassPK", String.valueOf(classPK));

					childJSONObject.put(
						"title", _getTitle(baseModelMap, classNameId, classPK));
				}
				else {
					childJSONObject.put(
						"description",
						_ctDisplayRendererRegistry.getEntryDescription(
							_httpServletRequest, ctEntry));

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

					renderURL.setResourceID("/change_lists/render_diff");

					renderURL.setParameter(
						"ctEntryId", String.valueOf(ctEntry.getCtEntryId()));

					childJSONObject.put(
						"title",
						_ctDisplayRendererRegistry.getTitle(
							_ctCollection, ctEntry, _themeDisplay.getLocale()));
				}

				childJSONObject.put(
					"dropdownItems", dropdownItemsJSONArray
				).put(
					"id", nodeIdCounter.getAndIncrement()
				).put(
					"renderURL", renderURL.toString()
				);

				children.put(childJSONObject);
			}
		}

		return children;
	}

	private JSONObject _getContextViewJSONObject() throws PortalException {
		if (_ctCollection.getStatus() == WorkflowConstants.STATUS_APPROVED) {
			return null;
		}

		CTClosure ctClosure = null;

		try {
			ctClosure = _ctClosureFactory.create(
				_ctCollection.getCtCollectionId());
		}
		catch (IllegalArgumentException | IllegalStateException exception) {
			_log.error(exception, exception);

			return JSONUtil.put(
				"errorMessage",
				_language.get(
					_httpServletRequest, "context-view-is-unavailable"));
		}

		JSONObject everythingJSONObject = JSONUtil.put(
			"id", 0
		).put(
			"title", _language.get(_httpServletRequest, "home")
		);

		JSONArray rootDisplayClassesJSONArray =
			JSONFactoryUtil.createJSONArray();

		JSONObject contextViewJSONObject = JSONUtil.put(
			"everything", everythingJSONObject
		).put(
			"rootDisplayClasses", rootDisplayClassesJSONArray
		);

		Map<Long, List<JSONObject>> rootDisplayMap = new LinkedHashMap<>();

		for (String className : _ctConfiguration.rootDisplayClassNames()) {
			long classNameId = _portal.getClassNameId(className);

			List<JSONObject> rootNodes = new ArrayList<>();

			rootDisplayMap.put(classNameId, rootNodes);
		}

		for (String childClassName :
				_ctConfiguration.rootDisplayChildClassNames()) {

			for (long parentClassNameId :
					CTClosureUtil.getParentClassNameIds(
						ctClosure, _portal.getClassNameId(childClassName))) {

				if (rootDisplayMap.containsKey(parentClassNameId)) {
					continue;
				}

				List<JSONObject> rootNodes = new ArrayList<>();

				rootDisplayMap.put(parentClassNameId, rootNodes);
			}
		}

		AtomicInteger nodeIdCounter = new AtomicInteger(1);

		Deque<JSONObject> deque = new LinkedList<>();

		deque.push(everythingJSONObject);

		JSONObject jsonObject = null;

		while ((jsonObject = deque.poll()) != null) {
			Map<Long, List<Long>> childPKsMap = null;

			if (jsonObject.getInt("id") == 0) {
				childPKsMap = ctClosure.getRootPKsMap();
			}
			else {
				childPKsMap = ctClosure.getChildPKsMap(
					jsonObject.getLong("modelClassNameId"),
					jsonObject.getLong("modelClassPK"));
			}

			if (childPKsMap.isEmpty()) {
				continue;
			}

			JSONArray children = _getChildren(nodeIdCounter, childPKsMap);

			if (children.length() == 0) {
				continue;
			}

			for (int i = 0; i < children.length(); i++) {
				JSONObject child = children.getJSONObject(i);

				_addRootDisplayNode(
					child,
					rootDisplayMap.get(child.getLong("modelClassNameId")));

				deque.push(child);
			}

			jsonObject.put("children", children);
		}

		for (Map.Entry<Long, List<JSONObject>> entry :
				rootDisplayMap.entrySet()) {

			List<JSONObject> rootDisplayNodes = entry.getValue();

			if (rootDisplayNodes.isEmpty()) {
				continue;
			}

			JSONArray nodeIds = JSONFactoryUtil.createJSONArray();

			for (JSONObject rootDisplayNode : rootDisplayNodes) {
				nodeIds.put(rootDisplayNode.getInt("id"));
			}

			String typeName = _getTypeName(entry.getKey());

			contextViewJSONObject.put(typeName, nodeIds);

			rootDisplayClassesJSONArray.put(typeName);
		}

		return contextViewJSONObject;
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

	private <T extends BaseModel<T>> String _getTitle(
		Map<Serializable, ? extends BaseModel<?>> baseModelMap,
		long classNameId, long classPK) {

		T baseModel = (T)baseModelMap.get(classPK);

		if (baseModel == null) {
			baseModel = _ctDisplayRendererRegistry.fetchCTModel(
				classNameId, classPK);
		}

		return _ctDisplayRendererRegistry.getTitle(
			CTConstants.CT_COLLECTION_ID_PRODUCTION,
			CTSQLModeThreadLocal.CTSQLMode.DEFAULT, _themeDisplay.getLocale(),
			baseModel, classNameId);
	}

	private String _getTypeName(long classNameId) {
		return _typeNameMap.computeIfAbsent(
			classNameId,
			key -> _ctDisplayRendererRegistry.getTypeName(
				_themeDisplay.getLocale(), classNameId));
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
	private final Map<Long, String> _typeNameMap = new HashMap<>();
	private final UserLocalService _userLocalService;

}
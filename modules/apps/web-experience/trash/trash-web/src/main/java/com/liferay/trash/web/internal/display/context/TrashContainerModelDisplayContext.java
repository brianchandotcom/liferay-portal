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

package com.liferay.trash.web.internal.display.context;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.ContainerModel;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides utility methods moved from the trash entry model container's JSP
 * file to reduce the complexity of that particular view.
 *
 * @author Jürgen Kappler
 */
public class TrashContainerModelDisplayContext {

	public TrashContainerModelDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_request = PortalUtil.getHttpServletRequest(liferayPortletRequest);
	}

	public String getBackURL() {
		if (Validator.isNotNull(_backURL)) {
			return _backURL;
		}

		_backURL = ParamUtil.getString(_request, "backURL", getRedirect());

		return _backURL;
	}

	public String getClassName() {
		if (Validator.isNotNull(_className)) {
			return _className;
		}

		_className = PortalUtil.getClassName(getClassNameId());

		return _className;
	}

	public long getClassNameId() {
		if (_classNameId != null) {
			return _classNameId;
		}

		_classNameId = ParamUtil.getLong(_request, "classNameId");

		return _classNameId;
	}

	public long getClassPK() {
		if (_classPK != null) {
			return _classPK;
		}

		_classPK = ParamUtil.getLong(_request, "classPK");

		return _classPK;
	}

	public String getContainerModelClassName() {
		if (Validator.isNotNull(_containerModelClassName)) {
			return _containerModelClassName;
		}

		_containerModelClassName = PortalUtil.getClassName(
			getContainerModelClassNameId());

		return _containerModelClassName;
	}

	public long getContainerModelClassNameId() {
		if (_containerModelClassNameId != null) {
			return _containerModelClassNameId;
		}

		String containerModelClassName = StringPool.BLANK;

		TrashHandler trashHandler = getTrashHandler();

		if (trashHandler != null) {
			containerModelClassName = trashHandler.getContainerModelClassName(
				getClassPK());
		}

		_containerModelClassNameId = ParamUtil.getLong(
			_request, "containerModelClassNameId",
			PortalUtil.getClassNameId(containerModelClassName));

		return _containerModelClassNameId;
	}

	public long getContainerModelId() {
		if (_containerModelId != null) {
			return _containerModelId;
		}

		_containerModelId = ParamUtil.getLong(_request, "containerModelId");

		return _containerModelId;
	}

	public String getContainerModelName() {
		if (Validator.isNotNull(_containerModelName)) {
			return _containerModelName;
		}

		TrashHandler trashHandler = getTrashHandler();

		if (trashHandler != null) {
			_containerModelName = trashHandler.getContainerModelName();
		}

		return _containerModelName;
	}

	public List<ContainerModel> getContainerModels() throws PortalException {
		if (ListUtil.isNotEmpty(_containerModels)) {
			return _containerModels;
		}

		SearchContainer searchContainer = getSearchContainer();

		TrashHandler trashHandler = getTrashHandler();

		if ((searchContainer != null) && (trashHandler != null)) {
			_containerModels = trashHandler.getContainerModels(
				getClassPK(), getContainerModelId(), searchContainer.getStart(),
				searchContainer.getEnd());
		}

		return _containerModels;
	}

	public int getContainerModelsCount() throws PortalException {
		if (_containerModelsCount != null) {
			return _containerModelsCount;
		}

		int containerModelsCount = 0;

		TrashHandler trashHandler = getTrashHandler();

		if (trashHandler != null) {
			containerModelsCount = trashHandler.getContainerModelsCount(
				getClassPK(), getContainerModelId());
		}

		_containerModelsCount = containerModelsCount;

		return _containerModelsCount;
	}

	public PortletURL getContainerURL() {
		String currentURL = (String)_request.getAttribute(WebKeys.CURRENT_URL);

		PortletURL containerURL = _liferayPortletResponse.createRenderURL();

		containerURL.setParameter("mvcPath", "/view_container_model.jsp");
		containerURL.setParameter("redirect", getRedirect());
		containerURL.setParameter("backURL", currentURL);
		containerURL.setParameter(
			"classNameId", String.valueOf(getClassNameId()));
		containerURL.setParameter("classPK", String.valueOf(getClassPK()));

		return containerURL;
	}

	public String getEventName() {
		if (Validator.isNotNull(_eventName)) {
			return _eventName;
		}

		_eventName = ParamUtil.getString(
			_request, "eventName",
			_liferayPortletResponse.getNamespace() + "selectContainer");

		return _eventName;
	}

	public Object[] getMissingContainerMessageArguments()
		throws PortalException {

		if (Validator.isNotNull(_missingContainerMessageArguments)) {
			return _missingContainerMessageArguments;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String trashRendererTitle = StringPool.BLANK;

		TrashRenderer trashRenderer = getTrashRenderer();

		if (trashRenderer != null) {
			trashRendererTitle = trashRenderer.getTitle(
				themeDisplay.getLocale());
		}

		_missingContainerMessageArguments = new Object[] {
			LanguageUtil.get(_request, getContainerModelName()),
			HtmlUtil.escape(trashRendererTitle)
		};

		return _missingContainerMessageArguments;
	}

	public String getRedirect() {
		if (Validator.isNotNull(_redirect)) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_request, "redirect");

		return _redirect;
	}

	public SearchContainer getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		PortletURL containerURL = getContainerURL();

		containerURL.setParameter(
			"containerModelId", String.valueOf(getContainerModelId()));

		_searchContainer = new SearchContainer(
			_liferayPortletRequest, null, null,
			SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA,
			containerURL, null, null);

		return _searchContainer;
	}

	public TrashHandler getTrashHandler() {
		if (_trashHandler != null) {
			return _trashHandler;
		}

		_trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getClassName());

		return _trashHandler;
	}

	public TrashRenderer getTrashRenderer() throws PortalException {
		if (_trashRenderer != null) {
			return _trashRenderer;
		}

		TrashHandler trashHandler = getTrashHandler();

		if (trashHandler != null) {
			_trashRenderer = trashHandler.getTrashRenderer(getClassPK());
		}

		return _trashRenderer;
	}

	public boolean isShowBackIcon() throws PortalException {
		if (_isShowBackIcon != null) {
			return _isShowBackIcon;
		}

		TrashHandler containerTrashHandler =
			TrashHandlerRegistryUtil.getTrashHandler(
				getContainerModelClassName());

		ContainerModel containerModel = null;

		if (getContainerModelId() > 0) {
			containerModel = containerTrashHandler.getContainerModel(
				getContainerModelId());
		}

		boolean isShowBackIcon = false;

		if (containerModel != null) {
			isShowBackIcon = true;
		}

		_isShowBackIcon = isShowBackIcon;

		return _isShowBackIcon;
	}

	private String _backURL;
	private String _className;
	private Long _classNameId;
	private Long _classPK;
	private String _containerModelClassName;
	private Long _containerModelClassNameId;
	private Long _containerModelId;
	private String _containerModelName;
	private List<ContainerModel> _containerModels;
	private Integer _containerModelsCount;
	private String _eventName;
	private Boolean _isShowBackIcon;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private Object[] _missingContainerMessageArguments;
	private String _redirect;
	private final HttpServletRequest _request;
	private SearchContainer _searchContainer;
	private TrashHandler _trashHandler;
	private TrashRenderer _trashRenderer;

}
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

package com.liferay.trash.web.display.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ContainerModel;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.trash.kernel.model.TrashEntry;
import com.liferay.trash.kernel.service.TrashEntryLocalServiceUtil;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class TrashDisplayContext {

	public TrashDisplayContext(
		HttpServletRequest request,
		LiferayPortletResponse liferayPortletResponse) {

		_request = request;
		_liferayPortletResponse = liferayPortletResponse;
	}

	public String getClassName() {
		TrashEntry trashEntry = getTrashEntry();

		if (trashEntry != null) {
			return trashEntry.getClassName();
		}

		String className = StringPool.BLANK;

		long classNameId = getClassNameId();

		if (classNameId > 0) {
			className = PortalUtil.getClassName(getClassNameId());
		}

		return className;
	}

	public long getClassNameId() {
		TrashEntry trashEntry = getTrashEntry();

		if (trashEntry != null) {
			return trashEntry.getClassNameId();
		}

		return ParamUtil.getLong(_request, "classNameId");
	}

	public long getClassPK() {
		TrashEntry trashEntry = getTrashEntry();

		if (trashEntry != null) {
			return trashEntry.getClassPK();
		}

		return ParamUtil.getLong(_request, "classPK");
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(_request, "orderByCol", "list");

		return _displayStyle;
	}

	public String getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_request, "orderByCol", "removed-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(_request, "orderByType", "asc");

		return _orderByType;
	}

	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		long trashEntryId = ParamUtil.getLong(_request, "trashEntryId");

		if (trashEntryId > 0) {
			portletURL.setParameter("mvcPath", "/view_content.jsp");
			portletURL.setParameter(
				"trashEntryId", String.valueOf(trashEntryId));
		}

		String displayStyle = ParamUtil.getString(_request, "displayStyle");

		if (Validator.isNotNull(displayStyle)) {
			portletURL.setParameter("displayStyle", getDisplayStyle());
		}

		String keywords = ParamUtil.getString(_request, "keywords");

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		return portletURL;
	}

	public TrashEntry getTrashEntry() {
		if (_trashEntry != null) {
			return _trashEntry;
		}

		long trashEntryId = ParamUtil.getLong(_request, "trashEntryId");

		long classNameId = ParamUtil.getLong(_request, "classNameId");
		long classPK = ParamUtil.getLong(_request, "classPK");

		if (trashEntryId > 0) {
			_trashEntry = TrashEntryLocalServiceUtil.fetchEntry(trashEntryId);
		}
		else if(classNameId > 0 && classPK > 0) {
			String className = PortalUtil.getClassName(classNameId);

			if (Validator.isNotNull(className)) {
				_trashEntry = TrashEntryLocalServiceUtil.fetchEntry(
					className, classPK);
			}
		}

		return _trashEntry;
	}

	public long getTrashEntryId() {
		TrashEntry trashEntry = getTrashEntry();

		return (trashEntry != null) ? trashEntry.getEntryId() : 0;
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

		long classPK = getClassPK();

		if (classPK > 0) {
			_trashRenderer = trashHandler.getTrashRenderer(getClassPK());
		}

		return _trashRenderer;
	}

	public String getViewContentRedirectURL() throws PortalException {
		String redirect = ParamUtil.getString(_request, "redirect");

		if (Validator.isNull(redirect)) {
			TrashHandler trashHandler = getTrashHandler();

			ContainerModel parentContainerModel =
				trashHandler.getParentContainerModel(getClassPK());

			PortletURL redirectURL = _liferayPortletResponse.createRenderURL();

			if ((parentContainerModel != null) && (getClassNameId() > 0)) {
				String parentContainerModelClassName =
					parentContainerModel.getModelClassName();

				redirectURL.setParameter("mvcPath", "/view_content.jsp");
				redirectURL.setParameter(
					"classNameId",
					String.valueOf(
						PortalUtil.getClassNameId(
							parentContainerModelClassName)));
				redirectURL.setParameter(
					"classPK",
					String.valueOf(parentContainerModel.getContainerModelId()));
			}

			redirect = redirectURL.toString();
		}

		return redirect;
	}

	private String _displayStyle;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _orderByCol;
	private String _orderByType;
	private final HttpServletRequest _request;
	private TrashEntry _trashEntry;
	private TrashHandler _trashHandler;
	private TrashRenderer _trashRenderer;

}
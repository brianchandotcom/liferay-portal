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

package com.liferay.document.library.repository.cmis.internal.model;

import com.liferay.document.library.repository.cmis.internal.CMISRepository;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.commons.data.AllowableActions;
import org.apache.chemistry.opencmis.commons.enums.Action;

/**
 * @author Alexander Chow
 */
public abstract class CMISModel {

	public abstract long getCompanyId();

	public String getDescription() {
		return StringPool.BLANK;
	}

	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), getModelClassName(), getPrimaryKey());
	}

	public abstract String getModelClassName();

	public abstract long getPrimaryKey();

	public void setParentFolder(Folder parentFolder) {
		_parentFolder = parentFolder;
	}

	protected boolean containsPermission(CmisObject cmisObject, String actionId)
		throws RepositoryException {

		CMISRepository cmisRepository = getCmisRepository();

		if (cmisRepository.isRefreshBeforePermissionCheck()) {
			cmisObject.refresh();
		}

		if (_unsupportedActionKeys.contains(actionId)) {
			return false;
		}

		Action action = _mappedActionKeys.get(actionId);

		if (action == null) {
			throw new RepositoryException(
				"Unexpected permission action " + actionId);
		}

		AllowableActions allowableActions = cmisObject.getAllowableActions();

		Set<Action> allowableActionsSet =
			allowableActions.getAllowableActions();

		return allowableActionsSet.contains(action);
	}

	protected abstract CMISRepository getCmisRepository();

	@SuppressWarnings("unused")
	protected Folder getParentFolder() throws PortalException {
		return _parentFolder;
	}

	protected User getUser(String createdBy) {
		User user = null;

		try {
			Company company = CompanyLocalServiceUtil.getCompany(
				getCompanyId());

			String authType = company.getAuthType();

			if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
				user = UserLocalServiceUtil.getUser(
					GetterUtil.getLong(createdBy));
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
				user = UserLocalServiceUtil.getUserByEmailAddress(
					getCompanyId(), createdBy);
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
				user = UserLocalServiceUtil.getUserByScreenName(
					getCompanyId(), createdBy);
			}
		}
		catch (Exception e) {
		}

		if (user == null) {
			try {
				user = UserLocalServiceUtil.getDefaultUser(getCompanyId());
			}
			catch (Exception e) {
			}
		}

		return user;
	}

	private static final Map<String, Action> _mappedActionKeys =
		new HashMap<String, Action>() {
			{
				put(ActionKeys.ACCESS, Action.CAN_GET_FOLDER_TREE);
				put(ActionKeys.ADD_DISCUSSION, Action.CAN_GET_PROPERTIES);
				put(ActionKeys.ADD_DOCUMENT, Action.CAN_CREATE_DOCUMENT);
				put(ActionKeys.ADD_FOLDER, Action.CAN_CREATE_FOLDER);
				put(ActionKeys.ADD_SUBFOLDER, Action.CAN_CREATE_FOLDER);
				put(ActionKeys.DELETE, Action.CAN_DELETE_OBJECT);
				put(ActionKeys.DELETE_DISCUSSION, Action.CAN_DELETE_OBJECT);
				put(ActionKeys.UPDATE, Action.CAN_UPDATE_PROPERTIES);
				put(ActionKeys.UPDATE_DISCUSSION, Action.CAN_UPDATE_PROPERTIES);
				put(ActionKeys.VIEW, Action.CAN_GET_PROPERTIES);
			}
		};
	private static final Set<String> _unsupportedActionKeys = new HashSet<>(
		Arrays.asList(
			ActionKeys.ADD_SHORTCUT, ActionKeys.OVERRIDE_CHECKOUT,
			ActionKeys.PERMISSIONS, ActionKeys.SUBSCRIBE));

	private Folder _parentFolder;

}
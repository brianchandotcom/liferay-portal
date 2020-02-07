/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.sharepoint.soap.repository.model;

import com.liferay.document.library.repository.external.ExtRepositoryModel;
import com.liferay.document.library.repository.external.ExtRepositoryObject;
import com.liferay.petra.string.StringPool;
import com.liferay.sharepoint.connector.SharepointObject;

import java.util.Date;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Iván Zaera
 */
public abstract class SharepointWSObject
	implements ExtRepositoryModel, ExtRepositoryObject {

	public SharepointWSObject(SharepointObject sharepointObject) {
		this.sharepointObject = sharepointObject;
	}

	@Override
	public boolean containsPermission(
		ExtRepositoryPermission extRepositoryPermission) {

		if (_unsupportedExtRepositoryPermissions.contains(
				extRepositoryPermission)) {

			return false;
		}

		SharepointObject.Permission permission = _permissions.get(
			extRepositoryPermission);

		if (permission == null) {
			throw new UnsupportedOperationException(
				"Unsupported permission " + extRepositoryPermission);
		}

		Set<SharepointObject.Permission> permissions =
			sharepointObject.getPermissions();

		return permissions.contains(permission);
	}

	@Override
	public Date getCreateDate() {
		return sharepointObject.getCreatedDate();
	}

	@Override
	public String getDescription() {
		return StringPool.BLANK;
	}

	@Override
	public String getExtension() {
		return sharepointObject.getExtension();
	}

	@Override
	public String getExtRepositoryModelKey() {
		return String.valueOf(sharepointObject.getSharepointObjectId());
	}

	@Override
	public Date getModifiedDate() {
		return sharepointObject.getLastModifiedDate();
	}

	@Override
	public String getOwner() {
		return sharepointObject.getAuthor();
	}

	public SharepointObject getSharepointObject() {
		return sharepointObject;
	}

	@Override
	public long getSize() {
		return sharepointObject.getSize();
	}

	protected SharepointObject sharepointObject;

	private static final Map
		<ExtRepositoryPermission, SharepointObject.Permission> _permissions =
			new EnumMap<ExtRepositoryPermission, SharepointObject.Permission>(
				ExtRepositoryPermission.class) {

				{
					put(
						ExtRepositoryPermission.ACCESS,
						SharepointObject.Permission.VIEW_LIST_ITEMS);
					put(
						ExtRepositoryPermission.ADD_DOCUMENT,
						SharepointObject.Permission.ADD_LIST_ITEMS);
					put(
						ExtRepositoryPermission.ADD_FOLDER,
						SharepointObject.Permission.ADD_LIST_ITEMS);
					put(
						ExtRepositoryPermission.ADD_SUBFOLDER,
						SharepointObject.Permission.ADD_LIST_ITEMS);
					put(
						ExtRepositoryPermission.DELETE,
						SharepointObject.Permission.DELETE_LIST_ITEMS);
					put(
						ExtRepositoryPermission.UPDATE,
						SharepointObject.Permission.EDIT_LIST_ITEMS);
					put(
						ExtRepositoryPermission.VIEW,
						SharepointObject.Permission.VIEW_LIST_ITEMS);
				}
			};

	private static final Set<ExtRepositoryPermission>
		_unsupportedExtRepositoryPermissions = EnumSet.of(
			ExtRepositoryPermission.ADD_DISCUSSION,
			ExtRepositoryPermission.ADD_SHORTCUT,
			ExtRepositoryPermission.DELETE_DISCUSSION,
			ExtRepositoryPermission.PERMISSIONS,
			ExtRepositoryPermission.UPDATE_DISCUSSION);

}
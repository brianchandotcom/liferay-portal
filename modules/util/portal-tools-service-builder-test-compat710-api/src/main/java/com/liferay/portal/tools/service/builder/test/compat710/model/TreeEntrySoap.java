/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat710.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class TreeEntrySoap implements Serializable {

	public static TreeEntrySoap toSoapModel(TreeEntry model) {
		TreeEntrySoap soapModel = new TreeEntrySoap();

		soapModel.setTreeEntryId(model.getTreeEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setParentTreeEntryId(model.getParentTreeEntryId());
		soapModel.setLeftTreeEntryId(model.getLeftTreeEntryId());
		soapModel.setRightTreeEntryId(model.getRightTreeEntryId());

		return soapModel;
	}

	public static TreeEntrySoap[] toSoapModels(TreeEntry[] models) {
		TreeEntrySoap[] soapModels = new TreeEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static TreeEntrySoap[][] toSoapModels(TreeEntry[][] models) {
		TreeEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new TreeEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new TreeEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static TreeEntrySoap[] toSoapModels(List<TreeEntry> models) {
		List<TreeEntrySoap> soapModels = new ArrayList<TreeEntrySoap>(
			models.size());

		for (TreeEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new TreeEntrySoap[soapModels.size()]);
	}

	public TreeEntrySoap() {
	}

	public long getPrimaryKey() {
		return _treeEntryId;
	}

	public void setPrimaryKey(long pk) {
		setTreeEntryId(pk);
	}

	public long getTreeEntryId() {
		return _treeEntryId;
	}

	public void setTreeEntryId(long treeEntryId) {
		_treeEntryId = treeEntryId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getParentTreeEntryId() {
		return _parentTreeEntryId;
	}

	public void setParentTreeEntryId(long parentTreeEntryId) {
		_parentTreeEntryId = parentTreeEntryId;
	}

	public long getLeftTreeEntryId() {
		return _leftTreeEntryId;
	}

	public void setLeftTreeEntryId(long leftTreeEntryId) {
		_leftTreeEntryId = leftTreeEntryId;
	}

	public long getRightTreeEntryId() {
		return _rightTreeEntryId;
	}

	public void setRightTreeEntryId(long rightTreeEntryId) {
		_rightTreeEntryId = rightTreeEntryId;
	}

	private long _treeEntryId;
	private long _groupId;
	private long _parentTreeEntryId;
	private long _leftTreeEntryId;
	private long _rightTreeEntryId;

}
// SB-Hash:-314043692
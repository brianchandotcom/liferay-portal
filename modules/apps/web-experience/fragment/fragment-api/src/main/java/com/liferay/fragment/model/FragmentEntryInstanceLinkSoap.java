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

package com.liferay.fragment.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class FragmentEntryInstanceLinkSoap implements Serializable {
	public static FragmentEntryInstanceLinkSoap toSoapModel(
		FragmentEntryInstanceLink model) {
		FragmentEntryInstanceLinkSoap soapModel = new FragmentEntryInstanceLinkSoap();

		soapModel.setFragmentEntryInstanceLinkId(model.getFragmentEntryInstanceLinkId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setFragmentEntryId(model.getFragmentEntryId());
		soapModel.setLayoutPageTemplateEntryId(model.getLayoutPageTemplateEntryId());
		soapModel.setPosition(model.getPosition());

		return soapModel;
	}

	public static FragmentEntryInstanceLinkSoap[] toSoapModels(
		FragmentEntryInstanceLink[] models) {
		FragmentEntryInstanceLinkSoap[] soapModels = new FragmentEntryInstanceLinkSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static FragmentEntryInstanceLinkSoap[][] toSoapModels(
		FragmentEntryInstanceLink[][] models) {
		FragmentEntryInstanceLinkSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new FragmentEntryInstanceLinkSoap[models.length][models[0].length];
		}
		else {
			soapModels = new FragmentEntryInstanceLinkSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static FragmentEntryInstanceLinkSoap[] toSoapModels(
		List<FragmentEntryInstanceLink> models) {
		List<FragmentEntryInstanceLinkSoap> soapModels = new ArrayList<FragmentEntryInstanceLinkSoap>(models.size());

		for (FragmentEntryInstanceLink model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new FragmentEntryInstanceLinkSoap[soapModels.size()]);
	}

	public FragmentEntryInstanceLinkSoap() {
	}

	public long getPrimaryKey() {
		return _fragmentEntryInstanceLinkId;
	}

	public void setPrimaryKey(long pk) {
		setFragmentEntryInstanceLinkId(pk);
	}

	public long getFragmentEntryInstanceLinkId() {
		return _fragmentEntryInstanceLinkId;
	}

	public void setFragmentEntryInstanceLinkId(long fragmentEntryInstanceLinkId) {
		_fragmentEntryInstanceLinkId = fragmentEntryInstanceLinkId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getFragmentEntryId() {
		return _fragmentEntryId;
	}

	public void setFragmentEntryId(long fragmentEntryId) {
		_fragmentEntryId = fragmentEntryId;
	}

	public long getLayoutPageTemplateEntryId() {
		return _layoutPageTemplateEntryId;
	}

	public void setLayoutPageTemplateEntryId(long layoutPageTemplateEntryId) {
		_layoutPageTemplateEntryId = layoutPageTemplateEntryId;
	}

	public int getPosition() {
		return _position;
	}

	public void setPosition(int position) {
		_position = position;
	}

	private long _fragmentEntryInstanceLinkId;
	private long _groupId;
	private long _fragmentEntryId;
	private long _layoutPageTemplateEntryId;
	private int _position;
}
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
public class FragmentEntryLinkSoap implements Serializable {
	public static FragmentEntryLinkSoap toSoapModel(FragmentEntryLink model) {
		FragmentEntryLinkSoap soapModel = new FragmentEntryLinkSoap();

		soapModel.setFragmentEntryLinkId(model.getFragmentEntryLinkId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setOriginalFragmentEntryLinkId(model.getOriginalFragmentEntryLinkId());
		soapModel.setFragmentEntryId(model.getFragmentEntryId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setCss(model.getCss());
		soapModel.setHtml(model.getHtml());
		soapModel.setJs(model.getJs());
		soapModel.setEditableValues(model.getEditableValues());
		soapModel.setPosition(model.getPosition());

		return soapModel;
	}

	public static FragmentEntryLinkSoap[] toSoapModels(
		FragmentEntryLink[] models) {
		FragmentEntryLinkSoap[] soapModels = new FragmentEntryLinkSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static FragmentEntryLinkSoap[][] toSoapModels(
		FragmentEntryLink[][] models) {
		FragmentEntryLinkSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new FragmentEntryLinkSoap[models.length][models[0].length];
		}
		else {
			soapModels = new FragmentEntryLinkSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static FragmentEntryLinkSoap[] toSoapModels(
		List<FragmentEntryLink> models) {
		List<FragmentEntryLinkSoap> soapModels = new ArrayList<FragmentEntryLinkSoap>(models.size());

		for (FragmentEntryLink model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new FragmentEntryLinkSoap[soapModels.size()]);
	}

	public FragmentEntryLinkSoap() {
	}

	public long getPrimaryKey() {
		return _fragmentEntryLinkId;
	}

	public void setPrimaryKey(long pk) {
		setFragmentEntryLinkId(pk);
	}

	public long getFragmentEntryLinkId() {
		return _fragmentEntryLinkId;
	}

	public void setFragmentEntryLinkId(long fragmentEntryLinkId) {
		_fragmentEntryLinkId = fragmentEntryLinkId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getOriginalFragmentEntryLinkId() {
		return _originalFragmentEntryLinkId;
	}

	public void setOriginalFragmentEntryLinkId(long originalFragmentEntryLinkId) {
		_originalFragmentEntryLinkId = originalFragmentEntryLinkId;
	}

	public long getFragmentEntryId() {
		return _fragmentEntryId;
	}

	public void setFragmentEntryId(long fragmentEntryId) {
		_fragmentEntryId = fragmentEntryId;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public String getCss() {
		return _css;
	}

	public void setCss(String css) {
		_css = css;
	}

	public String getHtml() {
		return _html;
	}

	public void setHtml(String html) {
		_html = html;
	}

	public String getJs() {
		return _js;
	}

	public void setJs(String js) {
		_js = js;
	}

	public String getEditableValues() {
		return _editableValues;
	}

	public void setEditableValues(String editableValues) {
		_editableValues = editableValues;
	}

	public int getPosition() {
		return _position;
	}

	public void setPosition(int position) {
		_position = position;
	}

	private long _fragmentEntryLinkId;
	private long _groupId;
	private long _originalFragmentEntryLinkId;
	private long _fragmentEntryId;
	private long _classNameId;
	private long _classPK;
	private String _css;
	private String _html;
	private String _js;
	private String _editableValues;
	private int _position;
}
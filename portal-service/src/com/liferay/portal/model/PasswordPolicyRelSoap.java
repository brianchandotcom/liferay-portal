/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class PasswordPolicyRelSoap implements Serializable {
	public static PasswordPolicyRelSoap toSoapModel(PasswordPolicyRel model) {
		PasswordPolicyRelSoap soapModel = new PasswordPolicyRelSoap();

		soapModel.setPasswordPolicyRelId(model.getPasswordPolicyRelId());
		soapModel.setPasswordPolicyId(model.getPasswordPolicyId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setMvccVersion(model.getMvccVersion());

		return soapModel;
	}

	public static PasswordPolicyRelSoap[] toSoapModels(
		PasswordPolicyRel[] models) {
		PasswordPolicyRelSoap[] soapModels = new PasswordPolicyRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static PasswordPolicyRelSoap[][] toSoapModels(
		PasswordPolicyRel[][] models) {
		PasswordPolicyRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new PasswordPolicyRelSoap[models.length][models[0].length];
		}
		else {
			soapModels = new PasswordPolicyRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static PasswordPolicyRelSoap[] toSoapModels(
		List<PasswordPolicyRel> models) {
		List<PasswordPolicyRelSoap> soapModels = new ArrayList<PasswordPolicyRelSoap>(models.size());

		for (PasswordPolicyRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new PasswordPolicyRelSoap[soapModels.size()]);
	}

	public PasswordPolicyRelSoap() {
	}

	public long getPrimaryKey() {
		return _passwordPolicyRelId;
	}

	public void setPrimaryKey(long pk) {
		setPasswordPolicyRelId(pk);
	}

	public long getPasswordPolicyRelId() {
		return _passwordPolicyRelId;
	}

	public void setPasswordPolicyRelId(long passwordPolicyRelId) {
		_passwordPolicyRelId = passwordPolicyRelId;
	}

	public long getPasswordPolicyId() {
		return _passwordPolicyId;
	}

	public void setPasswordPolicyId(long passwordPolicyId) {
		_passwordPolicyId = passwordPolicyId;
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

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	private long _passwordPolicyRelId;
	private long _passwordPolicyId;
	private long _classNameId;
	private long _classPK;
	private long _mvccVersion;
}
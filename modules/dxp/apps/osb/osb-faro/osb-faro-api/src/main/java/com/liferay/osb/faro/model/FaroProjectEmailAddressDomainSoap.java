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

package com.liferay.osb.faro.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Matthew Kong
 * @generated
 */
public class FaroProjectEmailAddressDomainSoap implements Serializable {

	public static FaroProjectEmailAddressDomainSoap toSoapModel(
		FaroProjectEmailAddressDomain model) {

		FaroProjectEmailAddressDomainSoap soapModel =
			new FaroProjectEmailAddressDomainSoap();

		soapModel.setFaroProjectEmailAddressDomainId(
			model.getFaroProjectEmailAddressDomainId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setFaroProjectId(model.getFaroProjectId());
		soapModel.setEmailAddressDomain(model.getEmailAddressDomain());

		return soapModel;
	}

	public static FaroProjectEmailAddressDomainSoap[] toSoapModels(
		FaroProjectEmailAddressDomain[] models) {

		FaroProjectEmailAddressDomainSoap[] soapModels =
			new FaroProjectEmailAddressDomainSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static FaroProjectEmailAddressDomainSoap[][] toSoapModels(
		FaroProjectEmailAddressDomain[][] models) {

		FaroProjectEmailAddressDomainSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new FaroProjectEmailAddressDomainSoap
				[models.length][models[0].length];
		}
		else {
			soapModels = new FaroProjectEmailAddressDomainSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static FaroProjectEmailAddressDomainSoap[] toSoapModels(
		List<FaroProjectEmailAddressDomain> models) {

		List<FaroProjectEmailAddressDomainSoap> soapModels =
			new ArrayList<FaroProjectEmailAddressDomainSoap>(models.size());

		for (FaroProjectEmailAddressDomain model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new FaroProjectEmailAddressDomainSoap[soapModels.size()]);
	}

	public FaroProjectEmailAddressDomainSoap() {
	}

	public long getPrimaryKey() {
		return _faroProjectEmailAddressDomainId;
	}

	public void setPrimaryKey(long pk) {
		setFaroProjectEmailAddressDomainId(pk);
	}

	public long getFaroProjectEmailAddressDomainId() {
		return _faroProjectEmailAddressDomainId;
	}

	public void setFaroProjectEmailAddressDomainId(
		long faroProjectEmailAddressDomainId) {

		_faroProjectEmailAddressDomainId = faroProjectEmailAddressDomainId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getFaroProjectId() {
		return _faroProjectId;
	}

	public void setFaroProjectId(long faroProjectId) {
		_faroProjectId = faroProjectId;
	}

	public String getEmailAddressDomain() {
		return _emailAddressDomain;
	}

	public void setEmailAddressDomain(String emailAddressDomain) {
		_emailAddressDomain = emailAddressDomain;
	}

	private long _faroProjectEmailAddressDomainId;
	private long _groupId;
	private long _faroProjectId;
	private String _emailAddressDomain;

}
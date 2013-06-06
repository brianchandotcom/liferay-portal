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

package com.liferay.portlet.social.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SocialRelationSoap implements Serializable {
	public static SocialRelationSoap toSoapModel(SocialRelation model) {
		SocialRelationSoap soapModel = new SocialRelationSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setRelationId(model.getRelationId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setUserId1(model.getUserId1());
		soapModel.setUserId2(model.getUserId2());
		soapModel.setType(model.getType());

		return soapModel;
	}

	public static SocialRelationSoap[] toSoapModels(SocialRelation[] models) {
		SocialRelationSoap[] soapModels = new SocialRelationSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SocialRelationSoap[][] toSoapModels(SocialRelation[][] models) {
		SocialRelationSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SocialRelationSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SocialRelationSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SocialRelationSoap[] toSoapModels(List<SocialRelation> models) {
		List<SocialRelationSoap> soapModels = new ArrayList<SocialRelationSoap>(models.size());

		for (SocialRelation model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SocialRelationSoap[soapModels.size()]);
	}

	public SocialRelationSoap() {
	}

	public long getPrimaryKey() {
		return _relationId;
	}

	public void setPrimaryKey(long pk) {
		setRelationId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getRelationId() {
		return _relationId;
	}

	public void setRelationId(long relationId) {
		_relationId = relationId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(long createDate) {
		_createDate = createDate;
	}

	public long getUserId1() {
		return _userId1;
	}

	public void setUserId1(long userId1) {
		_userId1 = userId1;
	}

	public long getUserId2() {
		return _userId2;
	}

	public void setUserId2(long userId2) {
		_userId2 = userId2;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	private String _uuid;
	private long _relationId;
	private long _companyId;
	private long _createDate;
	private long _userId1;
	private long _userId2;
	private int _type;
}
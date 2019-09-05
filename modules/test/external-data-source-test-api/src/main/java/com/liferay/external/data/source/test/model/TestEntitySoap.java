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

package com.liferay.external.data.source.test.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class TestEntitySoap implements Serializable {

	public static TestEntitySoap toSoapModel(TestEntity model) {
		TestEntitySoap soapModel = new TestEntitySoap();

		soapModel.setId(model.getId());
		soapModel.setData(model.getData());

		return soapModel;
	}

	public static TestEntitySoap[] toSoapModels(TestEntity[] models) {
		TestEntitySoap[] soapModels = new TestEntitySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static TestEntitySoap[][] toSoapModels(TestEntity[][] models) {
		TestEntitySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new TestEntitySoap[models.length][models[0].length];
		}
		else {
			soapModels = new TestEntitySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static TestEntitySoap[] toSoapModels(List<TestEntity> models) {
		List<TestEntitySoap> soapModels = new ArrayList<TestEntitySoap>(
			models.size());

		for (TestEntity model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new TestEntitySoap[soapModels.size()]);
	}

	public TestEntitySoap() {
	}

	public long getPrimaryKey() {
		return _id;
	}

	public void setPrimaryKey(long pk) {
		setId(pk);
	}

	public long getId() {
		return _id;
	}

	public void setId(long id) {
		_id = id;
	}

	public String getData() {
		return _data;
	}

	public void setData(String data) {
		_data = data;
	}

	private long _id;
	private String _data;

}
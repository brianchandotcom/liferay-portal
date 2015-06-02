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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.StagedModel;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;

/**
 * @author Mate Thurzo
 */
public class MockBookmarksFolder implements StagedModel {

	@Override
	public Object clone() {
		return null;
	}

	@Override
	public long getCompanyId() {
		try {
			return TestPropsValues.getCompanyId();
		}
		catch (PortalException pe) {
			return 0;
		}
	}

	@Override
	public Date getCreateDate() {
		return null;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return null;
	}

	@Override
	public Class<?> getModelClass() {
		return MockBookmarksFolder.class;
	}

	@Override
	public String getModelClassName() {
		return MockBookmarksFolder.class.getName();
	}

	@Override
	public Date getModifiedDate() {
		return null;
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		if (_primaryKeyObj == null) {
			try {
				_primaryKeyObj = RandomTestUtil.nextLong();
			}
			catch (Exception e) {
				_primaryKeyObj = 2L;
			}
		}

		return _primaryKeyObj;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(MockBookmarksFolder.class.getName());
	}

	@Override
	public String getUuid() {
		if (Validator.isNull(_uuid)) {
			_uuid = PortalUUIDUtil.generate();
		}

		return _uuid;
	}

	@Override
	public void setCompanyId(long companyId) {
	}

	@Override
	public void setCreateDate(Date date) {
	}

	@Override
	public void setModifiedDate(Date date) {
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
	}

	@Override
	public void setUuid(String uuid) {
	}

	private Serializable _primaryKeyObj;
	private String _uuid;

}
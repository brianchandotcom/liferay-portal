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

package com.liferay.mobile.device.rules.kernel;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.exportimport.kernel.lar.StagedModelType;

import java.util.Date;
import java.util.Map;

/**
 * @author Daniel Kocsis
 */
public interface MDRRuleGroupInstance {

	public Object clone();

	public long getCompanyId();

	public Date getCreateDate();

	public ExpandoBridge getExpandoBridge();

	public long getGroupId();

	public Date getLastPublishDate();

	public Map<String, Object> getModelAttributes();

	public Date getModifiedDate();

	public long getPrimaryKey();

	public int getPriority();

	public long getRuleGroupId();

	public long getRuleGroupInstanceId();

	public StagedModelType getStagedModelType();

	public long getUserId();

	public String getUserName();

	public String getUserUuid();

	public String getUuid();

}
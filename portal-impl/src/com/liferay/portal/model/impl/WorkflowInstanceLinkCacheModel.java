/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.WorkflowInstanceLink;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing WorkflowInstanceLink in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowInstanceLink
 * @generated
 */
public class WorkflowInstanceLinkCacheModel implements CacheModel<WorkflowInstanceLink>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{workflowInstanceLinkId=");
		sb.append(workflowInstanceLinkId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", workflowInstanceId=");
		sb.append(workflowInstanceId);
		sb.append("}");

		return sb.toString();
	}

	public WorkflowInstanceLink toEntityModel() {
		WorkflowInstanceLinkImpl workflowInstanceLinkImpl = new WorkflowInstanceLinkImpl();

		workflowInstanceLinkImpl.setWorkflowInstanceLinkId(workflowInstanceLinkId);
		workflowInstanceLinkImpl.setGroupId(groupId);
		workflowInstanceLinkImpl.setCompanyId(companyId);
		workflowInstanceLinkImpl.setUserId(userId);

		if (userName == null) {
			workflowInstanceLinkImpl.setUserName(StringPool.BLANK);
		}
		else {
			workflowInstanceLinkImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			workflowInstanceLinkImpl.setCreateDate(null);
		}
		else {
			workflowInstanceLinkImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			workflowInstanceLinkImpl.setModifiedDate(null);
		}
		else {
			workflowInstanceLinkImpl.setModifiedDate(new Date(modifiedDate));
		}

		workflowInstanceLinkImpl.setClassNameId(classNameId);
		workflowInstanceLinkImpl.setClassPK(classPK);
		workflowInstanceLinkImpl.setWorkflowInstanceId(workflowInstanceId);

		workflowInstanceLinkImpl.resetOriginalValues();

		return workflowInstanceLinkImpl;
	}

	public long workflowInstanceLinkId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long workflowInstanceId;
}
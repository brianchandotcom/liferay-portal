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

package com.liferay.portal.kernel.dao.orm;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.transaction.TransactionConfig;

/**
 * @author Brian Wing Shun Chan
 */
public interface ActionableDynamicQuery {

	public AddCriteriaMethod getAddCriteriaMethod();

	public AddOrderCriteriaMethod getAddOrderCriteriaMethod();

	public PerformActionMethod<?> getPerformActionMethod();

	public PerformCountMethod getPerformCountMethod();

	public boolean isParallel();

	public void performActions() throws PortalException;

	public long performCount() throws PortalException;

	public void setAddCriteriaMethod(AddCriteriaMethod addCriteriaMethod);

	public void setAddOrderCriteriaMethod(
		AddOrderCriteriaMethod addOrderCriteriaMethod);

	public void setBaseLocalService(BaseLocalService baseLocalService);

	public void setClassLoader(ClassLoader classLoader);

	public void setCompanyId(long companyId);

	public void setGroupId(long groupId);

	public void setGroupIdPropertyName(String groupIdPropertyName);

	public void setInterval(int interval);

	public void setModelClass(Class<?> modelClass);

	public void setParallel(boolean parallel);

	public void setPerformActionMethod(
		PerformActionMethod<?> performActionMethod);

	public void setPerformCountMethod(PerformCountMethod performCountMethod);

	public void setPrimaryKeyPropertyName(String primaryKeyPropertyName);

	public void setTransactionConfig(TransactionConfig transactionConfig);

	public interface AddCriteriaMethod {

		public void addCriteria(DynamicQuery dynamicQuery);

	}

	public interface AddOrderCriteriaMethod {

		public void addOrderCriteria(DynamicQuery dynamicQuery);

	}

	public interface PerformActionMethod<T> {

		public void performAction(T t) throws PortalException;

	}

	public interface PerformCountMethod {

		public long performCount() throws PortalException;

	}

}
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

package com.liferay.portal.tools.service.builder.test.service.impl;

import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.dao.jdbc.OutputBlob;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.tools.service.builder.test.model.LazyBlobEntity;
import com.liferay.portal.tools.service.builder.test.service.base.LazyBlobEntityLocalServiceBaseImpl;

/**
 * @author Kyle Miho
 */
public class LazyBlobEntityLocalServiceImpl
	extends LazyBlobEntityLocalServiceBaseImpl {

	@Override
	public LazyBlobEntity addLazyBlobEntity(
		long groupId, byte[] bytes, ServiceContext serviceContext) {

		long lazyBlobEntityId = counterLocalService.increment();

		LazyBlobEntity lazyBlobEntity = lazyBlobEntityPersistence.create(
			lazyBlobEntityId);

		lazyBlobEntity.setUuid(serviceContext.getUuid());
		lazyBlobEntity.setGroupId(groupId);

		UnsyncByteArrayInputStream unsyncByteArrayInputStream =
			new UnsyncByteArrayInputStream(bytes);

		OutputBlob outputBlob = new OutputBlob(
			unsyncByteArrayInputStream, bytes.length);

		lazyBlobEntity.setBlob1(outputBlob);
		lazyBlobEntity.setBlob2(outputBlob);

		lazyBlobEntityPersistence.update(lazyBlobEntity);

		return lazyBlobEntity;
	}

}
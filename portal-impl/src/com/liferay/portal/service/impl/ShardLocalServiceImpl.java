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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Shard;
import com.liferay.portal.service.base.ShardLocalServiceBaseImpl;
import com.liferay.portal.util.PropsValues;

/**
 * @author Brian Wing Shun Chan
 */
public class ShardLocalServiceImpl extends ShardLocalServiceBaseImpl {

	@Override
	public Shard addShard(String className, long classPK, String name) {
		long classNameId = classNameLocalService.getClassNameId(className);

		if (Validator.isNull(name)) {
			name = PropsValues.SHARD_DEFAULT_NAME;
		}

		long shardId = counterLocalService.increment();

		Shard shard = shardPersistence.create(shardId);

		shard.setClassNameId(classNameId);
		shard.setClassPK(classPK);
		shard.setName(name);

		shardPersistence.update(shard);

		return shard;
	}

	@Override
	public Shard getShard(String className, long classPK)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(className);

		return shardPersistence.findByC_C(classNameId, classPK);
	}

}
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
import com.liferay.portal.model.Shard;

import java.io.Serializable;

/**
 * The cache model class for representing Shard in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Shard
 * @generated
 */
public class ShardCacheModel implements CacheModel<Shard>, Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{shardId=");
		sb.append(shardId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		return sb.toString();
	}

	public Shard toEntityModel() {
		ShardImpl shardImpl = new ShardImpl();

		shardImpl.setShardId(shardId);
		shardImpl.setClassNameId(classNameId);
		shardImpl.setClassPK(classPK);

		if (name == null) {
			shardImpl.setName(StringPool.BLANK);
		}
		else {
			shardImpl.setName(name);
		}

		shardImpl.resetOriginalValues();

		return shardImpl;
	}

	public long shardId;
	public long classNameId;
	public long classPK;
	public String name;
}
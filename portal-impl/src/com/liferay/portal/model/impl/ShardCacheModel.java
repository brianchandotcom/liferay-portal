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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.Shard;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing Shard in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Shard
 * @generated
 */
public class ShardCacheModel implements CacheModel<Shard>, Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{shardId=");
		sb.append(shardId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", name=");
		sb.append(name);
		sb.append(", mvccVersion=");
		sb.append(mvccVersion);
		sb.append("}");

		return sb.toString();
	}

	@Override
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

		shardImpl.setMvccVersion(mvccVersion);

		shardImpl.resetOriginalValues();

		return shardImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		shardId = objectInput.readLong();
		classNameId = objectInput.readLong();
		classPK = objectInput.readLong();
		name = objectInput.readUTF();
		mvccVersion = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(shardId);
		objectOutput.writeLong(classNameId);
		objectOutput.writeLong(classPK);

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeLong(mvccVersion);
	}

	public long shardId;
	public long classNameId;
	public long classPK;
	public String name;
	public long mvccVersion;
}
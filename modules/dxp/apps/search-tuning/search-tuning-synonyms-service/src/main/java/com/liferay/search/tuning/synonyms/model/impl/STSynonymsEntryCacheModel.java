/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.tuning.synonyms.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.search.tuning.synonyms.model.STSynonymsEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing STSynonymsEntry in entity cache.
 *
 * @author Bryan Engler
 * @generated
 */
public class STSynonymsEntryCacheModel
	implements CacheModel<STSynonymsEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof STSynonymsEntryCacheModel)) {
			return false;
		}

		STSynonymsEntryCacheModel stSynonymsEntryCacheModel =
			(STSynonymsEntryCacheModel)object;

		if ((STSynonymsEntryId ==
				stSynonymsEntryCacheModel.STSynonymsEntryId) &&
			(mvccVersion == stSynonymsEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, STSynonymsEntryId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", STSynonymsEntryId=");
		sb.append(STSynonymsEntryId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public STSynonymsEntry toEntityModel() {
		STSynonymsEntryImpl stSynonymsEntryImpl = new STSynonymsEntryImpl();

		stSynonymsEntryImpl.setMvccVersion(mvccVersion);
		stSynonymsEntryImpl.setSTSynonymsEntryId(STSynonymsEntryId);
		stSynonymsEntryImpl.setCompanyId(companyId);

		stSynonymsEntryImpl.resetOriginalValues();

		return stSynonymsEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		STSynonymsEntryId = objectInput.readLong();

		companyId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(STSynonymsEntryId);

		objectOutput.writeLong(companyId);
	}

	public long mvccVersion;
	public long STSynonymsEntryId;
	public long companyId;

}
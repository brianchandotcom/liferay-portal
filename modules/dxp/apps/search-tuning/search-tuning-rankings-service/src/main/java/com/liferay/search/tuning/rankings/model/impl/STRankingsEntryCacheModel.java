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

package com.liferay.search.tuning.rankings.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.search.tuning.rankings.model.STRankingsEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing STRankingsEntry in entity cache.
 *
 * @author Bryan Engler
 * @generated
 */
public class STRankingsEntryCacheModel
	implements CacheModel<STRankingsEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof STRankingsEntryCacheModel)) {
			return false;
		}

		STRankingsEntryCacheModel stRankingsEntryCacheModel =
			(STRankingsEntryCacheModel)object;

		if ((STRankingsEntryId ==
				stRankingsEntryCacheModel.STRankingsEntryId) &&
			(mvccVersion == stRankingsEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, STRankingsEntryId);

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
		sb.append(", STRankingsEntryId=");
		sb.append(STRankingsEntryId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public STRankingsEntry toEntityModel() {
		STRankingsEntryImpl stRankingsEntryImpl = new STRankingsEntryImpl();

		stRankingsEntryImpl.setMvccVersion(mvccVersion);
		stRankingsEntryImpl.setSTRankingsEntryId(STRankingsEntryId);
		stRankingsEntryImpl.setCompanyId(companyId);

		stRankingsEntryImpl.resetOriginalValues();

		return stRankingsEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		STRankingsEntryId = objectInput.readLong();

		companyId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(STRankingsEntryId);

		objectOutput.writeLong(companyId);
	}

	public long mvccVersion;
	public long STRankingsEntryId;
	public long companyId;

}
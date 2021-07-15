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

package com.liferay.portal.search.tuning.rankings.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.search.tuning.rankings.model.Ranking;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing Ranking in entity cache.
 *
 * @author Bryan Engler
 * @generated
 */
public class RankingCacheModel
	implements CacheModel<Ranking>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RankingCacheModel)) {
			return false;
		}

		RankingCacheModel rankingCacheModel = (RankingCacheModel)object;

		if ((rankingId == rankingCacheModel.rankingId) &&
			(mvccVersion == rankingCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, rankingId);

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
		StringBundler sb = new StringBundler(9);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", rankingId=");
		sb.append(rankingId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", json=");
		sb.append(json);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Ranking toEntityModel() {
		RankingImpl rankingImpl = new RankingImpl();

		rankingImpl.setMvccVersion(mvccVersion);
		rankingImpl.setRankingId(rankingId);
		rankingImpl.setCompanyId(companyId);

		if (json == null) {
			rankingImpl.setJson("");
		}
		else {
			rankingImpl.setJson(json);
		}

		rankingImpl.resetOriginalValues();

		return rankingImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		rankingId = objectInput.readLong();

		companyId = objectInput.readLong();
		json = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(rankingId);

		objectOutput.writeLong(companyId);

		if (json == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(json);
		}
	}

	public long mvccVersion;
	public long rankingId;
	public long companyId;
	public String json;

}
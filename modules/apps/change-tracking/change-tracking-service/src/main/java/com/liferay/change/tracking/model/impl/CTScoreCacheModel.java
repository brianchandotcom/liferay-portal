/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.model.impl;

import com.liferay.change.tracking.model.CTScore;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing CTScore in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CTScoreCacheModel
	implements CacheModel<CTScore>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CTScoreCacheModel)) {
			return false;
		}

		CTScoreCacheModel ctScoreCacheModel = (CTScoreCacheModel)object;

		if ((ctScoreId == ctScoreCacheModel.ctScoreId) &&
			(mvccVersion == ctScoreCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, ctScoreId);

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
		StringBundler sb = new StringBundler(11);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctScoreId=");
		sb.append(ctScoreId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", score=");
		sb.append(score);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CTScore toEntityModel() {
		CTScoreImpl ctScoreImpl = new CTScoreImpl();

		ctScoreImpl.setMvccVersion(mvccVersion);
		ctScoreImpl.setCtScoreId(ctScoreId);
		ctScoreImpl.setCompanyId(companyId);
		ctScoreImpl.setCtCollectionId(ctCollectionId);
		ctScoreImpl.setScore(score);

		ctScoreImpl.resetOriginalValues();

		return ctScoreImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctScoreId = objectInput.readLong();

		companyId = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		score = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctScoreId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeInt(score);
	}

	public long mvccVersion;
	public long ctScoreId;
	public long companyId;
	public long ctCollectionId;
	public int score;

}
// LIFERAY-SERVICE-BUILDER-HASH:297991787
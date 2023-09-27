/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.model.impl;

import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing CTPreferences in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CTPreferencesCacheModel
	implements CacheModel<CTPreferences>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CTPreferencesCacheModel)) {
			return false;
		}

		CTPreferencesCacheModel ctPreferencesCacheModel =
			(CTPreferencesCacheModel)object;

		if ((ctPreferencesId == ctPreferencesCacheModel.ctPreferencesId) &&
			(mvccVersion == ctPreferencesCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, ctPreferencesId);

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
		StringBundler sb = new StringBundler(15);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctPreferencesId=");
		sb.append(ctPreferencesId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", previousCtCollectionId=");
		sb.append(previousCtCollectionId);
		sb.append(", confirmationEnabled=");
		sb.append(confirmationEnabled);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CTPreferences toEntityModel() {
		CTPreferencesImpl ctPreferencesImpl = new CTPreferencesImpl();

		ctPreferencesImpl.setMvccVersion(mvccVersion);
		ctPreferencesImpl.setCtPreferencesId(ctPreferencesId);
		ctPreferencesImpl.setCompanyId(companyId);
		ctPreferencesImpl.setUserId(userId);
		ctPreferencesImpl.setCtCollectionId(ctCollectionId);
		ctPreferencesImpl.setPreviousCtCollectionId(previousCtCollectionId);
		ctPreferencesImpl.setConfirmationEnabled(confirmationEnabled);

		ctPreferencesImpl.resetOriginalValues();

		return ctPreferencesImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctPreferencesId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		previousCtCollectionId = objectInput.readLong();

		confirmationEnabled = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctPreferencesId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(previousCtCollectionId);

		objectOutput.writeBoolean(confirmationEnabled);
	}

	public long mvccVersion;
	public long ctPreferencesId;
	public long companyId;
	public long userId;
	public long ctCollectionId;
	public long previousCtCollectionId;
	public boolean confirmationEnabled;

}
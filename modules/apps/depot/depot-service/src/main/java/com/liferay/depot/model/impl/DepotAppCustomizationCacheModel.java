/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.model.impl;

import com.liferay.depot.model.DepotAppCustomization;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing DepotAppCustomization in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DepotAppCustomizationCacheModel
	implements CacheModel<DepotAppCustomization>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DepotAppCustomizationCacheModel)) {
			return false;
		}

		DepotAppCustomizationCacheModel depotAppCustomizationCacheModel =
			(DepotAppCustomizationCacheModel)object;

		if ((depotAppCustomizationId ==
				depotAppCustomizationCacheModel.depotAppCustomizationId) &&
			(mvccVersion == depotAppCustomizationCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, depotAppCustomizationId);

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
		StringBundler sb = new StringBundler(13);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", depotAppCustomizationId=");
		sb.append(depotAppCustomizationId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", depotEntryId=");
		sb.append(depotEntryId);
		sb.append(", enabled=");
		sb.append(enabled);
		sb.append(", portletId=");
		sb.append(portletId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DepotAppCustomization toEntityModel() {
		DepotAppCustomizationImpl depotAppCustomizationImpl =
			new DepotAppCustomizationImpl();

		depotAppCustomizationImpl.setMvccVersion(mvccVersion);
		depotAppCustomizationImpl.setDepotAppCustomizationId(
			depotAppCustomizationId);
		depotAppCustomizationImpl.setCompanyId(companyId);
		depotAppCustomizationImpl.setDepotEntryId(depotEntryId);
		depotAppCustomizationImpl.setEnabled(enabled);

		if (portletId == null) {
			depotAppCustomizationImpl.setPortletId("");
		}
		else {
			depotAppCustomizationImpl.setPortletId(portletId);
		}

		depotAppCustomizationImpl.resetOriginalValues();

		return depotAppCustomizationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		depotAppCustomizationId = objectInput.readLong();

		companyId = objectInput.readLong();

		depotEntryId = objectInput.readLong();

		enabled = objectInput.readBoolean();
		portletId = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(depotAppCustomizationId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(depotEntryId);

		objectOutput.writeBoolean(enabled);

		if (portletId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(portletId);
		}
	}

	public long mvccVersion;
	public long depotAppCustomizationId;
	public long companyId;
	public long depotEntryId;
	public boolean enabled;
	public String portletId;

}
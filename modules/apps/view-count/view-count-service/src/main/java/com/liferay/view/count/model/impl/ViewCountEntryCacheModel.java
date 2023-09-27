/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.view.count.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.view.count.model.ViewCountEntry;
import com.liferay.view.count.service.persistence.ViewCountEntryPK;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing ViewCountEntry in entity cache.
 *
 * @author Preston Crary
 * @generated
 */
public class ViewCountEntryCacheModel
	implements CacheModel<ViewCountEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ViewCountEntryCacheModel)) {
			return false;
		}

		ViewCountEntryCacheModel viewCountEntryCacheModel =
			(ViewCountEntryCacheModel)object;

		if (viewCountEntryPK.equals(
				viewCountEntryCacheModel.viewCountEntryPK)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, viewCountEntryPK);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{companyId=");
		sb.append(companyId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", viewCount=");
		sb.append(viewCount);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ViewCountEntry toEntityModel() {
		ViewCountEntryImpl viewCountEntryImpl = new ViewCountEntryImpl();

		viewCountEntryImpl.setCompanyId(companyId);
		viewCountEntryImpl.setClassNameId(classNameId);
		viewCountEntryImpl.setClassPK(classPK);
		viewCountEntryImpl.setViewCount(viewCount);

		viewCountEntryImpl.resetOriginalValues();

		return viewCountEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		companyId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		viewCount = objectInput.readLong();

		viewCountEntryPK = new ViewCountEntryPK(
			companyId, classNameId, classPK);
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(companyId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeLong(viewCount);
	}

	public long companyId;
	public long classNameId;
	public long classPK;
	public long viewCount;
	public transient ViewCountEntryPK viewCountEntryPK;

}
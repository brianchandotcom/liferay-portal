/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.view.count.service.persistence;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;

import java.io.Serializable;

/**
 * @author Preston Crary
 * @generated
 */
public class ViewCountEntryPK
	implements Comparable<ViewCountEntryPK>, Serializable {

	public long companyId;
	public long classNameId;
	public long classPK;

	public ViewCountEntryPK() {
	}

	public ViewCountEntryPK(long companyId, long classNameId, long classPK) {
		this.companyId = companyId;
		this.classNameId = classNameId;
		this.classPK = classPK;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getClassNameId() {
		return classNameId;
	}

	public void setClassNameId(long classNameId) {
		this.classNameId = classNameId;
	}

	public long getClassPK() {
		return classPK;
	}

	public void setClassPK(long classPK) {
		this.classPK = classPK;
	}

	@Override
	public int compareTo(ViewCountEntryPK pk) {
		if (pk == null) {
			return -1;
		}

		int value = 0;

		if (companyId < pk.companyId) {
			value = -1;
		}
		else if (companyId > pk.companyId) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		if (classNameId < pk.classNameId) {
			value = -1;
		}
		else if (classNameId > pk.classNameId) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		if (classPK < pk.classPK) {
			value = -1;
		}
		else if (classPK > pk.classPK) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ViewCountEntryPK)) {
			return false;
		}

		ViewCountEntryPK pk = (ViewCountEntryPK)object;

		if ((companyId == pk.companyId) && (classNameId == pk.classNameId) &&
			(classPK == pk.classPK)) {

			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int hashCode = 0;

		hashCode = HashUtil.hash(hashCode, companyId);
		hashCode = HashUtil.hash(hashCode, classNameId);
		hashCode = HashUtil.hash(hashCode, classPK);

		return hashCode;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(8);

		sb.append("{");

		sb.append("companyId=");

		sb.append(companyId);
		sb.append(", classNameId=");

		sb.append(classNameId);
		sb.append(", classPK=");

		sb.append(classPK);

		sb.append("}");

		return sb.toString();
	}

}
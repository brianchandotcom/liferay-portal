/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.forms.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing KaleoProcessLink in entity cache.
 *
 * @author Marcellus Tavares
 * @generated
 */
public class KaleoProcessLinkCacheModel
	implements CacheModel<KaleoProcessLink>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof KaleoProcessLinkCacheModel)) {
			return false;
		}

		KaleoProcessLinkCacheModel kaleoProcessLinkCacheModel =
			(KaleoProcessLinkCacheModel)object;

		if (kaleoProcessLinkId ==
				kaleoProcessLinkCacheModel.kaleoProcessLinkId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, kaleoProcessLinkId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{kaleoProcessLinkId=");
		sb.append(kaleoProcessLinkId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", kaleoProcessId=");
		sb.append(kaleoProcessId);
		sb.append(", workflowTaskName=");
		sb.append(workflowTaskName);
		sb.append(", DDMTemplateId=");
		sb.append(DDMTemplateId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public KaleoProcessLink toEntityModel() {
		KaleoProcessLinkImpl kaleoProcessLinkImpl = new KaleoProcessLinkImpl();

		kaleoProcessLinkImpl.setKaleoProcessLinkId(kaleoProcessLinkId);
		kaleoProcessLinkImpl.setCompanyId(companyId);
		kaleoProcessLinkImpl.setKaleoProcessId(kaleoProcessId);

		if (workflowTaskName == null) {
			kaleoProcessLinkImpl.setWorkflowTaskName("");
		}
		else {
			kaleoProcessLinkImpl.setWorkflowTaskName(workflowTaskName);
		}

		kaleoProcessLinkImpl.setDDMTemplateId(DDMTemplateId);

		kaleoProcessLinkImpl.resetOriginalValues();

		return kaleoProcessLinkImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		kaleoProcessLinkId = objectInput.readLong();

		companyId = objectInput.readLong();

		kaleoProcessId = objectInput.readLong();
		workflowTaskName = objectInput.readUTF();

		DDMTemplateId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(kaleoProcessLinkId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(kaleoProcessId);

		if (workflowTaskName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(workflowTaskName);
		}

		objectOutput.writeLong(DDMTemplateId);
	}

	public long kaleoProcessLinkId;
	public long companyId;
	public long kaleoProcessId;
	public String workflowTaskName;
	public long DDMTemplateId;

}
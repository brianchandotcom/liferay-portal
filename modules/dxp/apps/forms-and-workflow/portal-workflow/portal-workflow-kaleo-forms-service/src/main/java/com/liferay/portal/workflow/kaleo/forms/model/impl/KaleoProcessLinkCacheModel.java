/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.workflow.kaleo.forms.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing KaleoProcessLink in entity cache.
 *
 * @author Marcellus Tavares
 * @see KaleoProcessLink
 * @generated
 */
@ProviderType
public class KaleoProcessLinkCacheModel implements CacheModel<KaleoProcessLink>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof KaleoProcessLinkCacheModel)) {
			return false;
		}

		KaleoProcessLinkCacheModel kaleoProcessLinkCacheModel = (KaleoProcessLinkCacheModel)obj;

		if (kaleoProcessLinkId == kaleoProcessLinkCacheModel.kaleoProcessLinkId) {
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
		StringBundler sb = new StringBundler(9);

		sb.append("{kaleoProcessLinkId=");
		sb.append(kaleoProcessLinkId);
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

		kaleoProcessId = objectInput.readLong();
		workflowTaskName = objectInput.readUTF();

		DDMTemplateId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(kaleoProcessLinkId);

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
	public long kaleoProcessId;
	public String workflowTaskName;
	public long DDMTemplateId;
}
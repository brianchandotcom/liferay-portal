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

package com.liferay.portal.workflow.kaleo.forms.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Marcellus Tavares
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class KaleoProcessLinkSoap implements Serializable {

	public static KaleoProcessLinkSoap toSoapModel(KaleoProcessLink model) {
		KaleoProcessLinkSoap soapModel = new KaleoProcessLinkSoap();

		soapModel.setKaleoProcessLinkId(model.getKaleoProcessLinkId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setKaleoProcessId(model.getKaleoProcessId());
		soapModel.setWorkflowTaskName(model.getWorkflowTaskName());
		soapModel.setDDMTemplateId(model.getDDMTemplateId());

		return soapModel;
	}

	public static KaleoProcessLinkSoap[] toSoapModels(
		KaleoProcessLink[] models) {

		KaleoProcessLinkSoap[] soapModels =
			new KaleoProcessLinkSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static KaleoProcessLinkSoap[][] toSoapModels(
		KaleoProcessLink[][] models) {

		KaleoProcessLinkSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new KaleoProcessLinkSoap[models.length][models[0].length];
		}
		else {
			soapModels = new KaleoProcessLinkSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static KaleoProcessLinkSoap[] toSoapModels(
		List<KaleoProcessLink> models) {

		List<KaleoProcessLinkSoap> soapModels =
			new ArrayList<KaleoProcessLinkSoap>(models.size());

		for (KaleoProcessLink model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new KaleoProcessLinkSoap[soapModels.size()]);
	}

	public KaleoProcessLinkSoap() {
	}

	public long getPrimaryKey() {
		return _kaleoProcessLinkId;
	}

	public void setPrimaryKey(long pk) {
		setKaleoProcessLinkId(pk);
	}

	public long getKaleoProcessLinkId() {
		return _kaleoProcessLinkId;
	}

	public void setKaleoProcessLinkId(long kaleoProcessLinkId) {
		_kaleoProcessLinkId = kaleoProcessLinkId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getKaleoProcessId() {
		return _kaleoProcessId;
	}

	public void setKaleoProcessId(long kaleoProcessId) {
		_kaleoProcessId = kaleoProcessId;
	}

	public String getWorkflowTaskName() {
		return _workflowTaskName;
	}

	public void setWorkflowTaskName(String workflowTaskName) {
		_workflowTaskName = workflowTaskName;
	}

	public long getDDMTemplateId() {
		return _DDMTemplateId;
	}

	public void setDDMTemplateId(long DDMTemplateId) {
		_DDMTemplateId = DDMTemplateId;
	}

	private long _kaleoProcessLinkId;
	private long _companyId;
	private long _kaleoProcessId;
	private String _workflowTaskName;
	private long _DDMTemplateId;

}
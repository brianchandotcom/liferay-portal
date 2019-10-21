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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link KaleoProcessLink}.
 * </p>
 *
 * @author Marcellus Tavares
 * @see KaleoProcessLink
 * @generated
 */
public class KaleoProcessLinkWrapper
	extends BaseModelWrapper<KaleoProcessLink>
	implements KaleoProcessLink, ModelWrapper<KaleoProcessLink> {

	public KaleoProcessLinkWrapper(KaleoProcessLink kaleoProcessLink) {
		super(kaleoProcessLink);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("kaleoProcessLinkId", getKaleoProcessLinkId());
		attributes.put("companyId", getCompanyId());
		attributes.put("kaleoProcessId", getKaleoProcessId());
		attributes.put("workflowTaskName", getWorkflowTaskName());
		attributes.put("DDMTemplateId", getDDMTemplateId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long kaleoProcessLinkId = (Long)attributes.get("kaleoProcessLinkId");

		if (kaleoProcessLinkId != null) {
			setKaleoProcessLinkId(kaleoProcessLinkId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long kaleoProcessId = (Long)attributes.get("kaleoProcessId");

		if (kaleoProcessId != null) {
			setKaleoProcessId(kaleoProcessId);
		}

		String workflowTaskName = (String)attributes.get("workflowTaskName");

		if (workflowTaskName != null) {
			setWorkflowTaskName(workflowTaskName);
		}

		Long DDMTemplateId = (Long)attributes.get("DDMTemplateId");

		if (DDMTemplateId != null) {
			setDDMTemplateId(DDMTemplateId);
		}
	}

	/**
	 * Returns the company ID of this kaleo process link.
	 *
	 * @return the company ID of this kaleo process link
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the ddm template ID of this kaleo process link.
	 *
	 * @return the ddm template ID of this kaleo process link
	 */
	@Override
	public long getDDMTemplateId() {
		return model.getDDMTemplateId();
	}

	@Override
	public KaleoProcess getKaleoProcess()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getKaleoProcess();
	}

	/**
	 * Returns the kaleo process ID of this kaleo process link.
	 *
	 * @return the kaleo process ID of this kaleo process link
	 */
	@Override
	public long getKaleoProcessId() {
		return model.getKaleoProcessId();
	}

	/**
	 * Returns the kaleo process link ID of this kaleo process link.
	 *
	 * @return the kaleo process link ID of this kaleo process link
	 */
	@Override
	public long getKaleoProcessLinkId() {
		return model.getKaleoProcessLinkId();
	}

	/**
	 * Returns the primary key of this kaleo process link.
	 *
	 * @return the primary key of this kaleo process link
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the workflow task name of this kaleo process link.
	 *
	 * @return the workflow task name of this kaleo process link
	 */
	@Override
	public String getWorkflowTaskName() {
		return model.getWorkflowTaskName();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a kaleo process link model instance should use the <code>KaleoProcessLink</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this kaleo process link.
	 *
	 * @param companyId the company ID of this kaleo process link
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the ddm template ID of this kaleo process link.
	 *
	 * @param DDMTemplateId the ddm template ID of this kaleo process link
	 */
	@Override
	public void setDDMTemplateId(long DDMTemplateId) {
		model.setDDMTemplateId(DDMTemplateId);
	}

	/**
	 * Sets the kaleo process ID of this kaleo process link.
	 *
	 * @param kaleoProcessId the kaleo process ID of this kaleo process link
	 */
	@Override
	public void setKaleoProcessId(long kaleoProcessId) {
		model.setKaleoProcessId(kaleoProcessId);
	}

	/**
	 * Sets the kaleo process link ID of this kaleo process link.
	 *
	 * @param kaleoProcessLinkId the kaleo process link ID of this kaleo process link
	 */
	@Override
	public void setKaleoProcessLinkId(long kaleoProcessLinkId) {
		model.setKaleoProcessLinkId(kaleoProcessLinkId);
	}

	/**
	 * Sets the primary key of this kaleo process link.
	 *
	 * @param primaryKey the primary key of this kaleo process link
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the workflow task name of this kaleo process link.
	 *
	 * @param workflowTaskName the workflow task name of this kaleo process link
	 */
	@Override
	public void setWorkflowTaskName(String workflowTaskName) {
		model.setWorkflowTaskName(workflowTaskName);
	}

	@Override
	protected KaleoProcessLinkWrapper wrap(KaleoProcessLink kaleoProcessLink) {
		return new KaleoProcessLinkWrapper(kaleoProcessLink);
	}

}
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

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link KaleoProcessLink}.
 * </p>
 *
 * @author Marcellus Tavares
 * @see KaleoProcessLink
 * @generated
 */
@ProviderType
public class KaleoProcessLinkWrapper implements KaleoProcessLink,
	ModelWrapper<KaleoProcessLink> {
	public KaleoProcessLinkWrapper(KaleoProcessLink kaleoProcessLink) {
		_kaleoProcessLink = kaleoProcessLink;
	}

	@Override
	public Class<?> getModelClass() {
		return KaleoProcessLink.class;
	}

	@Override
	public String getModelClassName() {
		return KaleoProcessLink.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("kaleoProcessLinkId", getKaleoProcessLinkId());
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

	@Override
	public Object clone() {
		return new KaleoProcessLinkWrapper((KaleoProcessLink)_kaleoProcessLink.clone());
	}

	@Override
	public int compareTo(KaleoProcessLink kaleoProcessLink) {
		return _kaleoProcessLink.compareTo(kaleoProcessLink);
	}

	/**
	* Returns the ddm template ID of this kaleo process link.
	*
	* @return the ddm template ID of this kaleo process link
	*/
	@Override
	public long getDDMTemplateId() {
		return _kaleoProcessLink.getDDMTemplateId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _kaleoProcessLink.getExpandoBridge();
	}

	@Override
	public KaleoProcess getKaleoProcess()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kaleoProcessLink.getKaleoProcess();
	}

	/**
	* Returns the kaleo process ID of this kaleo process link.
	*
	* @return the kaleo process ID of this kaleo process link
	*/
	@Override
	public long getKaleoProcessId() {
		return _kaleoProcessLink.getKaleoProcessId();
	}

	/**
	* Returns the kaleo process link ID of this kaleo process link.
	*
	* @return the kaleo process link ID of this kaleo process link
	*/
	@Override
	public long getKaleoProcessLinkId() {
		return _kaleoProcessLink.getKaleoProcessLinkId();
	}

	/**
	* Returns the primary key of this kaleo process link.
	*
	* @return the primary key of this kaleo process link
	*/
	@Override
	public long getPrimaryKey() {
		return _kaleoProcessLink.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _kaleoProcessLink.getPrimaryKeyObj();
	}

	/**
	* Returns the workflow task name of this kaleo process link.
	*
	* @return the workflow task name of this kaleo process link
	*/
	@Override
	public String getWorkflowTaskName() {
		return _kaleoProcessLink.getWorkflowTaskName();
	}

	@Override
	public int hashCode() {
		return _kaleoProcessLink.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _kaleoProcessLink.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _kaleoProcessLink.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _kaleoProcessLink.isNew();
	}

	@Override
	public void persist() {
		_kaleoProcessLink.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_kaleoProcessLink.setCachedModel(cachedModel);
	}

	/**
	* Sets the ddm template ID of this kaleo process link.
	*
	* @param DDMTemplateId the ddm template ID of this kaleo process link
	*/
	@Override
	public void setDDMTemplateId(long DDMTemplateId) {
		_kaleoProcessLink.setDDMTemplateId(DDMTemplateId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_kaleoProcessLink.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_kaleoProcessLink.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_kaleoProcessLink.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the kaleo process ID of this kaleo process link.
	*
	* @param kaleoProcessId the kaleo process ID of this kaleo process link
	*/
	@Override
	public void setKaleoProcessId(long kaleoProcessId) {
		_kaleoProcessLink.setKaleoProcessId(kaleoProcessId);
	}

	/**
	* Sets the kaleo process link ID of this kaleo process link.
	*
	* @param kaleoProcessLinkId the kaleo process link ID of this kaleo process link
	*/
	@Override
	public void setKaleoProcessLinkId(long kaleoProcessLinkId) {
		_kaleoProcessLink.setKaleoProcessLinkId(kaleoProcessLinkId);
	}

	@Override
	public void setNew(boolean n) {
		_kaleoProcessLink.setNew(n);
	}

	/**
	* Sets the primary key of this kaleo process link.
	*
	* @param primaryKey the primary key of this kaleo process link
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_kaleoProcessLink.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_kaleoProcessLink.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the workflow task name of this kaleo process link.
	*
	* @param workflowTaskName the workflow task name of this kaleo process link
	*/
	@Override
	public void setWorkflowTaskName(String workflowTaskName) {
		_kaleoProcessLink.setWorkflowTaskName(workflowTaskName);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<KaleoProcessLink> toCacheModel() {
		return _kaleoProcessLink.toCacheModel();
	}

	@Override
	public KaleoProcessLink toEscapedModel() {
		return new KaleoProcessLinkWrapper(_kaleoProcessLink.toEscapedModel());
	}

	@Override
	public String toString() {
		return _kaleoProcessLink.toString();
	}

	@Override
	public KaleoProcessLink toUnescapedModel() {
		return new KaleoProcessLinkWrapper(_kaleoProcessLink.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _kaleoProcessLink.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof KaleoProcessLinkWrapper)) {
			return false;
		}

		KaleoProcessLinkWrapper kaleoProcessLinkWrapper = (KaleoProcessLinkWrapper)obj;

		if (Objects.equals(_kaleoProcessLink,
					kaleoProcessLinkWrapper._kaleoProcessLink)) {
			return true;
		}

		return false;
	}

	@Override
	public KaleoProcessLink getWrappedModel() {
		return _kaleoProcessLink;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _kaleoProcessLink.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _kaleoProcessLink.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_kaleoProcessLink.resetOriginalValues();
	}

	private final KaleoProcessLink _kaleoProcessLink;
}
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

package com.liferay.dynamic.data.mapping.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DDMTemplateLink}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplateLink
 * @generated
 */
@ProviderType
public class DDMTemplateLinkWrapper implements DDMTemplateLink,
	ModelWrapper<DDMTemplateLink> {
	public DDMTemplateLinkWrapper(DDMTemplateLink ddmTemplateLink) {
		_ddmTemplateLink = ddmTemplateLink;
	}

	@Override
	public Class<?> getModelClass() {
		return DDMTemplateLink.class;
	}

	@Override
	public String getModelClassName() {
		return DDMTemplateLink.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("templateLinkId", getTemplateLinkId());
		attributes.put("companyId", getCompanyId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("templateId", getTemplateId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long templateLinkId = (Long)attributes.get("templateLinkId");

		if (templateLinkId != null) {
			setTemplateLinkId(templateLinkId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long templateId = (Long)attributes.get("templateId");

		if (templateId != null) {
			setTemplateId(templateId);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new DDMTemplateLinkWrapper((DDMTemplateLink)_ddmTemplateLink.clone());
	}

	@Override
	public int compareTo(
		com.liferay.dynamic.data.mapping.model.DDMTemplateLink ddmTemplateLink) {
		return _ddmTemplateLink.compareTo(ddmTemplateLink);
	}

	/**
	* Returns the fully qualified class name of this d d m template link.
	*
	* @return the fully qualified class name of this d d m template link
	*/
	@Override
	public java.lang.String getClassName() {
		return _ddmTemplateLink.getClassName();
	}

	/**
	* Returns the class name ID of this d d m template link.
	*
	* @return the class name ID of this d d m template link
	*/
	@Override
	public long getClassNameId() {
		return _ddmTemplateLink.getClassNameId();
	}

	/**
	* Returns the class p k of this d d m template link.
	*
	* @return the class p k of this d d m template link
	*/
	@Override
	public long getClassPK() {
		return _ddmTemplateLink.getClassPK();
	}

	/**
	* Returns the company ID of this d d m template link.
	*
	* @return the company ID of this d d m template link
	*/
	@Override
	public long getCompanyId() {
		return _ddmTemplateLink.getCompanyId();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ddmTemplateLink.getExpandoBridge();
	}

	/**
	* Returns the primary key of this d d m template link.
	*
	* @return the primary key of this d d m template link
	*/
	@Override
	public long getPrimaryKey() {
		return _ddmTemplateLink.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _ddmTemplateLink.getPrimaryKeyObj();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMTemplate getTemplate()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmTemplateLink.getTemplate();
	}

	/**
	* Returns the template ID of this d d m template link.
	*
	* @return the template ID of this d d m template link
	*/
	@Override
	public long getTemplateId() {
		return _ddmTemplateLink.getTemplateId();
	}

	/**
	* Returns the template link ID of this d d m template link.
	*
	* @return the template link ID of this d d m template link
	*/
	@Override
	public long getTemplateLinkId() {
		return _ddmTemplateLink.getTemplateLinkId();
	}

	@Override
	public int hashCode() {
		return _ddmTemplateLink.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _ddmTemplateLink.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _ddmTemplateLink.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _ddmTemplateLink.isNew();
	}

	@Override
	public void persist() {
		_ddmTemplateLink.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_ddmTemplateLink.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_ddmTemplateLink.setClassName(className);
	}

	/**
	* Sets the class name ID of this d d m template link.
	*
	* @param classNameId the class name ID of this d d m template link
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_ddmTemplateLink.setClassNameId(classNameId);
	}

	/**
	* Sets the class p k of this d d m template link.
	*
	* @param classPK the class p k of this d d m template link
	*/
	@Override
	public void setClassPK(long classPK) {
		_ddmTemplateLink.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this d d m template link.
	*
	* @param companyId the company ID of this d d m template link
	*/
	@Override
	public void setCompanyId(long companyId) {
		_ddmTemplateLink.setCompanyId(companyId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_ddmTemplateLink.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_ddmTemplateLink.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ddmTemplateLink.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public void setNew(boolean n) {
		_ddmTemplateLink.setNew(n);
	}

	/**
	* Sets the primary key of this d d m template link.
	*
	* @param primaryKey the primary key of this d d m template link
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_ddmTemplateLink.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_ddmTemplateLink.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the template ID of this d d m template link.
	*
	* @param templateId the template ID of this d d m template link
	*/
	@Override
	public void setTemplateId(long templateId) {
		_ddmTemplateLink.setTemplateId(templateId);
	}

	/**
	* Sets the template link ID of this d d m template link.
	*
	* @param templateLinkId the template link ID of this d d m template link
	*/
	@Override
	public void setTemplateLinkId(long templateLinkId) {
		_ddmTemplateLink.setTemplateLinkId(templateLinkId);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.dynamic.data.mapping.model.DDMTemplateLink> toCacheModel() {
		return _ddmTemplateLink.toCacheModel();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMTemplateLink toEscapedModel() {
		return new DDMTemplateLinkWrapper(_ddmTemplateLink.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _ddmTemplateLink.toString();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMTemplateLink toUnescapedModel() {
		return new DDMTemplateLinkWrapper(_ddmTemplateLink.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _ddmTemplateLink.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMTemplateLinkWrapper)) {
			return false;
		}

		DDMTemplateLinkWrapper ddmTemplateLinkWrapper = (DDMTemplateLinkWrapper)obj;

		if (Validator.equals(_ddmTemplateLink,
					ddmTemplateLinkWrapper._ddmTemplateLink)) {
			return true;
		}

		return false;
	}

	@Override
	public DDMTemplateLink getWrappedModel() {
		return _ddmTemplateLink;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _ddmTemplateLink.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _ddmTemplateLink.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_ddmTemplateLink.resetOriginalValues();
	}

	private final DDMTemplateLink _ddmTemplateLink;
}
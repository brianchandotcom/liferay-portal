/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link DDMTemplateLink}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplateLink
 * @generated
 */
public class DDMTemplateLinkWrapper
	extends BaseModelWrapper<DDMTemplateLink>
	implements DDMTemplateLink, ModelWrapper<DDMTemplateLink> {

	public DDMTemplateLinkWrapper(DDMTemplateLink ddmTemplateLink) {
		super(ddmTemplateLink);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("templateLinkId", getTemplateLinkId());
		attributes.put("companyId", getCompanyId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("templateId", getTemplateId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

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
	public DDMTemplateLink cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the fully qualified class name of this ddm template link.
	 *
	 * @return the fully qualified class name of this ddm template link
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this ddm template link.
	 *
	 * @return the class name ID of this ddm template link
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this ddm template link.
	 *
	 * @return the class pk of this ddm template link
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this ddm template link.
	 *
	 * @return the company ID of this ddm template link
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the ct collection ID of this ddm template link.
	 *
	 * @return the ct collection ID of this ddm template link
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the mvcc version of this ddm template link.
	 *
	 * @return the mvcc version of this ddm template link
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this ddm template link.
	 *
	 * @return the primary key of this ddm template link
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public DDMTemplate getTemplate()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getTemplate();
	}

	/**
	 * Returns the template ID of this ddm template link.
	 *
	 * @return the template ID of this ddm template link
	 */
	@Override
	public long getTemplateId() {
		return model.getTemplateId();
	}

	/**
	 * Returns the template link ID of this ddm template link.
	 *
	 * @return the template link ID of this ddm template link
	 */
	@Override
	public long getTemplateLinkId() {
		return model.getTemplateLinkId();
	}

	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this ddm template link.
	 *
	 * @param classNameId the class name ID of this ddm template link
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this ddm template link.
	 *
	 * @param classPK the class pk of this ddm template link
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this ddm template link.
	 *
	 * @param companyId the company ID of this ddm template link
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the ct collection ID of this ddm template link.
	 *
	 * @param ctCollectionId the ct collection ID of this ddm template link
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the mvcc version of this ddm template link.
	 *
	 * @param mvccVersion the mvcc version of this ddm template link
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this ddm template link.
	 *
	 * @param primaryKey the primary key of this ddm template link
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the template ID of this ddm template link.
	 *
	 * @param templateId the template ID of this ddm template link
	 */
	@Override
	public void setTemplateId(long templateId) {
		model.setTemplateId(templateId);
	}

	/**
	 * Sets the template link ID of this ddm template link.
	 *
	 * @param templateLinkId the template link ID of this ddm template link
	 */
	@Override
	public void setTemplateLinkId(long templateLinkId) {
		model.setTemplateLinkId(templateLinkId);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<DDMTemplateLink, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<DDMTemplateLink, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected DDMTemplateLinkWrapper wrap(DDMTemplateLink ddmTemplateLink) {
		return new DDMTemplateLinkWrapper(ddmTemplateLink);
	}

}
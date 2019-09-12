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

package com.liferay.view.count.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ViewCountEntry}.
 * </p>
 *
 * @author Preston Crary
 * @see ViewCountEntry
 * @generated
 */
public class ViewCountEntryWrapper
	extends BaseModelWrapper<ViewCountEntry>
	implements ViewCountEntry, ModelWrapper<ViewCountEntry> {

	public ViewCountEntryWrapper(ViewCountEntry viewCountEntry) {
		super(viewCountEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("companyId", getCompanyId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("viewCount", getViewCount());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
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

		Long viewCount = (Long)attributes.get("viewCount");

		if (viewCount != null) {
			setViewCount(viewCount);
		}
	}

	/**
	 * Returns the fully qualified class name of this view count entry.
	 *
	 * @return the fully qualified class name of this view count entry
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this view count entry.
	 *
	 * @return the class name ID of this view count entry
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this view count entry.
	 *
	 * @return the class pk of this view count entry
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this view count entry.
	 *
	 * @return the company ID of this view count entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the primary key of this view count entry.
	 *
	 * @return the primary key of this view count entry
	 */
	@Override
	public com.liferay.view.count.service.persistence.ViewCountEntryPK
		getPrimaryKey() {

		return model.getPrimaryKey();
	}

	/**
	 * Returns the view count of this view count entry.
	 *
	 * @return the view count of this view count entry
	 */
	@Override
	public long getViewCount() {
		return model.getViewCount();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a view count entry model instance should use the <code>ViewCountEntry</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this view count entry.
	 *
	 * @param classNameId the class name ID of this view count entry
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this view count entry.
	 *
	 * @param classPK the class pk of this view count entry
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this view count entry.
	 *
	 * @param companyId the company ID of this view count entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the primary key of this view count entry.
	 *
	 * @param primaryKey the primary key of this view count entry
	 */
	@Override
	public void setPrimaryKey(
		com.liferay.view.count.service.persistence.ViewCountEntryPK
			primaryKey) {

		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the view count of this view count entry.
	 *
	 * @param viewCount the view count of this view count entry
	 */
	@Override
	public void setViewCount(long viewCount) {
		model.setViewCount(viewCount);
	}

	@Override
	protected ViewCountEntryWrapper wrap(ViewCountEntry viewCountEntry) {
		return new ViewCountEntryWrapper(viewCountEntry);
	}

}
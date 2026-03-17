/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat720.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link LikeFinderEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LikeFinderEntry
 * @generated
 */
public class LikeFinderEntryWrapper
	extends BaseModelWrapper<LikeFinderEntry>
	implements LikeFinderEntry, ModelWrapper<LikeFinderEntry> {

	public LikeFinderEntryWrapper(LikeFinderEntry likeFinderEntry) {
		super(likeFinderEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("likeFinderEntryId", getLikeFinderEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("ownerId", getOwnerId());
		attributes.put("ownerType", getOwnerType());
		attributes.put("portletId", getPortletId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long likeFinderEntryId = (Long)attributes.get("likeFinderEntryId");

		if (likeFinderEntryId != null) {
			setLikeFinderEntryId(likeFinderEntryId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long ownerId = (Long)attributes.get("ownerId");

		if (ownerId != null) {
			setOwnerId(ownerId);
		}

		Integer ownerType = (Integer)attributes.get("ownerType");

		if (ownerType != null) {
			setOwnerType(ownerType);
		}

		String portletId = (String)attributes.get("portletId");

		if (portletId != null) {
			setPortletId(portletId);
		}
	}

	/**
	 * Returns the company ID of this like finder entry.
	 *
	 * @return the company ID of this like finder entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the like finder entry ID of this like finder entry.
	 *
	 * @return the like finder entry ID of this like finder entry
	 */
	@Override
	public long getLikeFinderEntryId() {
		return model.getLikeFinderEntryId();
	}

	/**
	 * Returns the owner ID of this like finder entry.
	 *
	 * @return the owner ID of this like finder entry
	 */
	@Override
	public long getOwnerId() {
		return model.getOwnerId();
	}

	/**
	 * Returns the owner type of this like finder entry.
	 *
	 * @return the owner type of this like finder entry
	 */
	@Override
	public int getOwnerType() {
		return model.getOwnerType();
	}

	/**
	 * Returns the portlet ID of this like finder entry.
	 *
	 * @return the portlet ID of this like finder entry
	 */
	@Override
	public String getPortletId() {
		return model.getPortletId();
	}

	/**
	 * Returns the primary key of this like finder entry.
	 *
	 * @return the primary key of this like finder entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this like finder entry.
	 *
	 * @param companyId the company ID of this like finder entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the like finder entry ID of this like finder entry.
	 *
	 * @param likeFinderEntryId the like finder entry ID of this like finder entry
	 */
	@Override
	public void setLikeFinderEntryId(long likeFinderEntryId) {
		model.setLikeFinderEntryId(likeFinderEntryId);
	}

	/**
	 * Sets the owner ID of this like finder entry.
	 *
	 * @param ownerId the owner ID of this like finder entry
	 */
	@Override
	public void setOwnerId(long ownerId) {
		model.setOwnerId(ownerId);
	}

	/**
	 * Sets the owner type of this like finder entry.
	 *
	 * @param ownerType the owner type of this like finder entry
	 */
	@Override
	public void setOwnerType(int ownerType) {
		model.setOwnerType(ownerType);
	}

	/**
	 * Sets the portlet ID of this like finder entry.
	 *
	 * @param portletId the portlet ID of this like finder entry
	 */
	@Override
	public void setPortletId(String portletId) {
		model.setPortletId(portletId);
	}

	/**
	 * Sets the primary key of this like finder entry.
	 *
	 * @param primaryKey the primary key of this like finder entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	protected LikeFinderEntryWrapper wrap(LikeFinderEntry likeFinderEntry) {
		return new LikeFinderEntryWrapper(likeFinderEntry);
	}

}
// SB-Hash:-1808305723
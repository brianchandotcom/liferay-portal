/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.sync.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DLSyncEvent}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLSyncEvent
 * @generated
 */
public class DLSyncEventWrapper
	extends BaseModelWrapper<DLSyncEvent>
	implements DLSyncEvent, ModelWrapper<DLSyncEvent> {

	public DLSyncEventWrapper(DLSyncEvent dlSyncEvent) {
		super(dlSyncEvent);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("syncEventId", getSyncEventId());
		attributes.put("companyId", getCompanyId());
		attributes.put("modifiedTime", getModifiedTime());
		attributes.put("event", getEvent());
		attributes.put("type", getType());
		attributes.put("typePK", getTypePK());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long syncEventId = (Long)attributes.get("syncEventId");

		if (syncEventId != null) {
			setSyncEventId(syncEventId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long modifiedTime = (Long)attributes.get("modifiedTime");

		if (modifiedTime != null) {
			setModifiedTime(modifiedTime);
		}

		String event = (String)attributes.get("event");

		if (event != null) {
			setEvent(event);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Long typePK = (Long)attributes.get("typePK");

		if (typePK != null) {
			setTypePK(typePK);
		}
	}

	@Override
	public DLSyncEvent cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this dl sync event.
	 *
	 * @return the company ID of this dl sync event
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the event of this dl sync event.
	 *
	 * @return the event of this dl sync event
	 */
	@Override
	public String getEvent() {
		return model.getEvent();
	}

	/**
	 * Returns the modified time of this dl sync event.
	 *
	 * @return the modified time of this dl sync event
	 */
	@Override
	public long getModifiedTime() {
		return model.getModifiedTime();
	}

	/**
	 * Returns the primary key of this dl sync event.
	 *
	 * @return the primary key of this dl sync event
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the sync event ID of this dl sync event.
	 *
	 * @return the sync event ID of this dl sync event
	 */
	@Override
	public long getSyncEventId() {
		return model.getSyncEventId();
	}

	/**
	 * Returns the type of this dl sync event.
	 *
	 * @return the type of this dl sync event
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	 * Returns the type pk of this dl sync event.
	 *
	 * @return the type pk of this dl sync event
	 */
	@Override
	public long getTypePK() {
		return model.getTypePK();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this dl sync event.
	 *
	 * @param companyId the company ID of this dl sync event
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the event of this dl sync event.
	 *
	 * @param event the event of this dl sync event
	 */
	@Override
	public void setEvent(String event) {
		model.setEvent(event);
	}

	/**
	 * Sets the modified time of this dl sync event.
	 *
	 * @param modifiedTime the modified time of this dl sync event
	 */
	@Override
	public void setModifiedTime(long modifiedTime) {
		model.setModifiedTime(modifiedTime);
	}

	/**
	 * Sets the primary key of this dl sync event.
	 *
	 * @param primaryKey the primary key of this dl sync event
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the sync event ID of this dl sync event.
	 *
	 * @param syncEventId the sync event ID of this dl sync event
	 */
	@Override
	public void setSyncEventId(long syncEventId) {
		model.setSyncEventId(syncEventId);
	}

	/**
	 * Sets the type of this dl sync event.
	 *
	 * @param type the type of this dl sync event
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	 * Sets the type pk of this dl sync event.
	 *
	 * @param typePK the type pk of this dl sync event
	 */
	@Override
	public void setTypePK(long typePK) {
		model.setTypePK(typePK);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected DLSyncEventWrapper wrap(DLSyncEvent dlSyncEvent) {
		return new DLSyncEventWrapper(dlSyncEvent);
	}

}
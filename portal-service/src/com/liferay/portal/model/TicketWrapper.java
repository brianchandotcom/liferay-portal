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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Ticket}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Ticket
 * @generated
 */
@ProviderType
public class TicketWrapper implements Ticket, ModelWrapper<Ticket> {
	public TicketWrapper(Ticket ticket) {
		_ticket = ticket;
	}

	@Override
	public Class<?> getModelClass() {
		return Ticket.class;
	}

	@Override
	public String getModelClassName() {
		return Ticket.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ticketId", getTicketId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("key", getKey());
		attributes.put("type", getType());
		attributes.put("extraInfo", getExtraInfo());
		attributes.put("expirationDate", getExpirationDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ticketId = (Long)attributes.get("ticketId");

		if (ticketId != null) {
			setTicketId(ticketId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		String key = (String)attributes.get("key");

		if (key != null) {
			setKey(key);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String extraInfo = (String)attributes.get("extraInfo");

		if (extraInfo != null) {
			setExtraInfo(extraInfo);
		}

		Date expirationDate = (Date)attributes.get("expirationDate");

		if (expirationDate != null) {
			setExpirationDate(expirationDate);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new TicketWrapper((Ticket)_ticket.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.Ticket ticket) {
		return _ticket.compareTo(ticket);
	}

	/**
	* Returns the fully qualified class name of this ticket.
	*
	* @return the fully qualified class name of this ticket
	*/
	@Override
	public java.lang.String getClassName() {
		return _ticket.getClassName();
	}

	/**
	* Returns the class name ID of this ticket.
	*
	* @return the class name ID of this ticket
	*/
	@Override
	public long getClassNameId() {
		return _ticket.getClassNameId();
	}

	/**
	* Returns the class p k of this ticket.
	*
	* @return the class p k of this ticket
	*/
	@Override
	public long getClassPK() {
		return _ticket.getClassPK();
	}

	/**
	* Returns the company ID of this ticket.
	*
	* @return the company ID of this ticket
	*/
	@Override
	public long getCompanyId() {
		return _ticket.getCompanyId();
	}

	/**
	* Returns the create date of this ticket.
	*
	* @return the create date of this ticket
	*/
	@Override
	public Date getCreateDate() {
		return _ticket.getCreateDate();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ticket.getExpandoBridge();
	}

	/**
	* Returns the expiration date of this ticket.
	*
	* @return the expiration date of this ticket
	*/
	@Override
	public Date getExpirationDate() {
		return _ticket.getExpirationDate();
	}

	/**
	* Returns the extra info of this ticket.
	*
	* @return the extra info of this ticket
	*/
	@Override
	public java.lang.String getExtraInfo() {
		return _ticket.getExtraInfo();
	}

	/**
	* Returns the key of this ticket.
	*
	* @return the key of this ticket
	*/
	@Override
	public java.lang.String getKey() {
		return _ticket.getKey();
	}

	/**
	* Returns the mvcc version of this ticket.
	*
	* @return the mvcc version of this ticket
	*/
	@Override
	public long getMvccVersion() {
		return _ticket.getMvccVersion();
	}

	/**
	* Returns the primary key of this ticket.
	*
	* @return the primary key of this ticket
	*/
	@Override
	public long getPrimaryKey() {
		return _ticket.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _ticket.getPrimaryKeyObj();
	}

	/**
	* Returns the ticket ID of this ticket.
	*
	* @return the ticket ID of this ticket
	*/
	@Override
	public long getTicketId() {
		return _ticket.getTicketId();
	}

	/**
	* Returns the type of this ticket.
	*
	* @return the type of this ticket
	*/
	@Override
	public int getType() {
		return _ticket.getType();
	}

	@Override
	public int hashCode() {
		return _ticket.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _ticket.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _ticket.isEscapedModel();
	}

	@Override
	public boolean isExpired() {
		return _ticket.isExpired();
	}

	@Override
	public boolean isNew() {
		return _ticket.isNew();
	}

	@Override
	public void persist() {
		_ticket.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_ticket.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_ticket.setClassName(className);
	}

	/**
	* Sets the class name ID of this ticket.
	*
	* @param classNameId the class name ID of this ticket
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_ticket.setClassNameId(classNameId);
	}

	/**
	* Sets the class p k of this ticket.
	*
	* @param classPK the class p k of this ticket
	*/
	@Override
	public void setClassPK(long classPK) {
		_ticket.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this ticket.
	*
	* @param companyId the company ID of this ticket
	*/
	@Override
	public void setCompanyId(long companyId) {
		_ticket.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this ticket.
	*
	* @param createDate the create date of this ticket
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_ticket.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		_ticket.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_ticket.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ticket.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the expiration date of this ticket.
	*
	* @param expirationDate the expiration date of this ticket
	*/
	@Override
	public void setExpirationDate(Date expirationDate) {
		_ticket.setExpirationDate(expirationDate);
	}

	/**
	* Sets the extra info of this ticket.
	*
	* @param extraInfo the extra info of this ticket
	*/
	@Override
	public void setExtraInfo(java.lang.String extraInfo) {
		_ticket.setExtraInfo(extraInfo);
	}

	/**
	* Sets the key of this ticket.
	*
	* @param key the key of this ticket
	*/
	@Override
	public void setKey(java.lang.String key) {
		_ticket.setKey(key);
	}

	/**
	* Sets the mvcc version of this ticket.
	*
	* @param mvccVersion the mvcc version of this ticket
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_ticket.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_ticket.setNew(n);
	}

	/**
	* Sets the primary key of this ticket.
	*
	* @param primaryKey the primary key of this ticket
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_ticket.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_ticket.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the ticket ID of this ticket.
	*
	* @param ticketId the ticket ID of this ticket
	*/
	@Override
	public void setTicketId(long ticketId) {
		_ticket.setTicketId(ticketId);
	}

	/**
	* Sets the type of this ticket.
	*
	* @param type the type of this ticket
	*/
	@Override
	public void setType(int type) {
		_ticket.setType(type);
	}

	@Override
	public CacheModel<com.liferay.portal.model.Ticket> toCacheModel() {
		return _ticket.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.Ticket toEscapedModel() {
		return new TicketWrapper(_ticket.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _ticket.toString();
	}

	@Override
	public com.liferay.portal.model.Ticket toUnescapedModel() {
		return new TicketWrapper(_ticket.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _ticket.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof TicketWrapper)) {
			return false;
		}

		TicketWrapper ticketWrapper = (TicketWrapper)obj;

		if (Validator.equals(_ticket, ticketWrapper._ticket)) {
			return true;
		}

		return false;
	}

	@Override
	public Ticket getWrappedModel() {
		return _ticket;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _ticket.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _ticket.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_ticket.resetOriginalValues();
	}

	private final Ticket _ticket;
}
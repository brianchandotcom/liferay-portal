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

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

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
public class TicketWrapper
	extends BaseModelWrapper<Ticket> implements ModelWrapper<Ticket>, Ticket {

	public TicketWrapper(Ticket ticket) {
		super(ticket);
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

	/**
	 * Returns the fully qualified class name of this ticket.
	 *
	 * @return the fully qualified class name of this ticket
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this ticket.
	 *
	 * @return the class name ID of this ticket
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this ticket.
	 *
	 * @return the class pk of this ticket
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this ticket.
	 *
	 * @return the company ID of this ticket
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this ticket.
	 *
	 * @return the create date of this ticket
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the expiration date of this ticket.
	 *
	 * @return the expiration date of this ticket
	 */
	@Override
	public Date getExpirationDate() {
		return model.getExpirationDate();
	}

	/**
	 * Returns the extra info of this ticket.
	 *
	 * @return the extra info of this ticket
	 */
	@Override
	public String getExtraInfo() {
		return model.getExtraInfo();
	}

	/**
	 * Returns the key of this ticket.
	 *
	 * @return the key of this ticket
	 */
	@Override
	public String getKey() {
		return model.getKey();
	}

	/**
	 * Returns the mvcc version of this ticket.
	 *
	 * @return the mvcc version of this ticket
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this ticket.
	 *
	 * @return the primary key of this ticket
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the ticket ID of this ticket.
	 *
	 * @return the ticket ID of this ticket
	 */
	@Override
	public long getTicketId() {
		return model.getTicketId();
	}

	/**
	 * Returns the type of this ticket.
	 *
	 * @return the type of this ticket
	 */
	@Override
	public int getType() {
		return model.getType();
	}

	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a ticket model instance should use the <code>Ticket</code> interface instead.
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
	 * Sets the class name ID of this ticket.
	 *
	 * @param classNameId the class name ID of this ticket
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this ticket.
	 *
	 * @param classPK the class pk of this ticket
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this ticket.
	 *
	 * @param companyId the company ID of this ticket
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this ticket.
	 *
	 * @param createDate the create date of this ticket
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the expiration date of this ticket.
	 *
	 * @param expirationDate the expiration date of this ticket
	 */
	@Override
	public void setExpirationDate(Date expirationDate) {
		model.setExpirationDate(expirationDate);
	}

	/**
	 * Sets the extra info of this ticket.
	 *
	 * @param extraInfo the extra info of this ticket
	 */
	@Override
	public void setExtraInfo(String extraInfo) {
		model.setExtraInfo(extraInfo);
	}

	/**
	 * Sets the key of this ticket.
	 *
	 * @param key the key of this ticket
	 */
	@Override
	public void setKey(String key) {
		model.setKey(key);
	}

	/**
	 * Sets the mvcc version of this ticket.
	 *
	 * @param mvccVersion the mvcc version of this ticket
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this ticket.
	 *
	 * @param primaryKey the primary key of this ticket
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the ticket ID of this ticket.
	 *
	 * @param ticketId the ticket ID of this ticket
	 */
	@Override
	public void setTicketId(long ticketId) {
		model.setTicketId(ticketId);
	}

	/**
	 * Sets the type of this ticket.
	 *
	 * @param type the type of this ticket
	 */
	@Override
	public void setType(int type) {
		model.setType(type);
	}

	@Override
	protected TicketWrapper wrap(Ticket ticket) {
		return new TicketWrapper(ticket);
	}

}
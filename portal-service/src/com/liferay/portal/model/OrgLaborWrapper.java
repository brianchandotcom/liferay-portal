/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link OrgLabor}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       OrgLabor
 * @generated
 */
public class OrgLaborWrapper implements OrgLabor, ModelWrapper<OrgLabor> {
	public OrgLaborWrapper(OrgLabor orgLabor) {
		_orgLabor = orgLabor;
	}

	public Class<?> getModelClass() {
		return OrgLabor.class;
	}

	public String getModelClassName() {
		return OrgLabor.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("orgLaborId", getOrgLaborId());
		attributes.put("organizationId", getOrganizationId());
		attributes.put("typeId", getTypeId());
		attributes.put("sunOpen", getSunOpen());
		attributes.put("sunClose", getSunClose());
		attributes.put("monOpen", getMonOpen());
		attributes.put("monClose", getMonClose());
		attributes.put("tueOpen", getTueOpen());
		attributes.put("tueClose", getTueClose());
		attributes.put("wedOpen", getWedOpen());
		attributes.put("wedClose", getWedClose());
		attributes.put("thuOpen", getThuOpen());
		attributes.put("thuClose", getThuClose());
		attributes.put("friOpen", getFriOpen());
		attributes.put("friClose", getFriClose());
		attributes.put("satOpen", getSatOpen());
		attributes.put("satClose", getSatClose());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		Long orgLaborId = (Long)attributes.get("orgLaborId");

		if (orgLaborId != null) {
			setOrgLaborId(orgLaborId);
		}

		Long organizationId = (Long)attributes.get("organizationId");

		if (organizationId != null) {
			setOrganizationId(organizationId);
		}

		Integer typeId = (Integer)attributes.get("typeId");

		if (typeId != null) {
			setTypeId(typeId);
		}

		Integer sunOpen = (Integer)attributes.get("sunOpen");

		if (sunOpen != null) {
			setSunOpen(sunOpen);
		}

		Integer sunClose = (Integer)attributes.get("sunClose");

		if (sunClose != null) {
			setSunClose(sunClose);
		}

		Integer monOpen = (Integer)attributes.get("monOpen");

		if (monOpen != null) {
			setMonOpen(monOpen);
		}

		Integer monClose = (Integer)attributes.get("monClose");

		if (monClose != null) {
			setMonClose(monClose);
		}

		Integer tueOpen = (Integer)attributes.get("tueOpen");

		if (tueOpen != null) {
			setTueOpen(tueOpen);
		}

		Integer tueClose = (Integer)attributes.get("tueClose");

		if (tueClose != null) {
			setTueClose(tueClose);
		}

		Integer wedOpen = (Integer)attributes.get("wedOpen");

		if (wedOpen != null) {
			setWedOpen(wedOpen);
		}

		Integer wedClose = (Integer)attributes.get("wedClose");

		if (wedClose != null) {
			setWedClose(wedClose);
		}

		Integer thuOpen = (Integer)attributes.get("thuOpen");

		if (thuOpen != null) {
			setThuOpen(thuOpen);
		}

		Integer thuClose = (Integer)attributes.get("thuClose");

		if (thuClose != null) {
			setThuClose(thuClose);
		}

		Integer friOpen = (Integer)attributes.get("friOpen");

		if (friOpen != null) {
			setFriOpen(friOpen);
		}

		Integer friClose = (Integer)attributes.get("friClose");

		if (friClose != null) {
			setFriClose(friClose);
		}

		Integer satOpen = (Integer)attributes.get("satOpen");

		if (satOpen != null) {
			setSatOpen(satOpen);
		}

		Integer satClose = (Integer)attributes.get("satClose");

		if (satClose != null) {
			setSatClose(satClose);
		}
	}

	/**
	* Returns the primary key of this org labor.
	*
	* @return the primary key of this org labor
	*/
	public long getPrimaryKey() {
		return _orgLabor.getPrimaryKey();
	}

	/**
	* Sets the primary key of this org labor.
	*
	* @param primaryKey the primary key of this org labor
	*/
	public void setPrimaryKey(long primaryKey) {
		_orgLabor.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the org labor ID of this org labor.
	*
	* @return the org labor ID of this org labor
	*/
	public long getOrgLaborId() {
		return _orgLabor.getOrgLaborId();
	}

	/**
	* Sets the org labor ID of this org labor.
	*
	* @param orgLaborId the org labor ID of this org labor
	*/
	public void setOrgLaborId(long orgLaborId) {
		_orgLabor.setOrgLaborId(orgLaborId);
	}

	/**
	* Returns the organization ID of this org labor.
	*
	* @return the organization ID of this org labor
	*/
	public long getOrganizationId() {
		return _orgLabor.getOrganizationId();
	}

	/**
	* Sets the organization ID of this org labor.
	*
	* @param organizationId the organization ID of this org labor
	*/
	public void setOrganizationId(long organizationId) {
		_orgLabor.setOrganizationId(organizationId);
	}

	/**
	* Returns the type ID of this org labor.
	*
	* @return the type ID of this org labor
	*/
	public int getTypeId() {
		return _orgLabor.getTypeId();
	}

	/**
	* Sets the type ID of this org labor.
	*
	* @param typeId the type ID of this org labor
	*/
	public void setTypeId(int typeId) {
		_orgLabor.setTypeId(typeId);
	}

	/**
	* Returns the sun open of this org labor.
	*
	* @return the sun open of this org labor
	*/
	public int getSunOpen() {
		return _orgLabor.getSunOpen();
	}

	/**
	* Sets the sun open of this org labor.
	*
	* @param sunOpen the sun open of this org labor
	*/
	public void setSunOpen(int sunOpen) {
		_orgLabor.setSunOpen(sunOpen);
	}

	/**
	* Returns the sun close of this org labor.
	*
	* @return the sun close of this org labor
	*/
	public int getSunClose() {
		return _orgLabor.getSunClose();
	}

	/**
	* Sets the sun close of this org labor.
	*
	* @param sunClose the sun close of this org labor
	*/
	public void setSunClose(int sunClose) {
		_orgLabor.setSunClose(sunClose);
	}

	/**
	* Returns the mon open of this org labor.
	*
	* @return the mon open of this org labor
	*/
	public int getMonOpen() {
		return _orgLabor.getMonOpen();
	}

	/**
	* Sets the mon open of this org labor.
	*
	* @param monOpen the mon open of this org labor
	*/
	public void setMonOpen(int monOpen) {
		_orgLabor.setMonOpen(monOpen);
	}

	/**
	* Returns the mon close of this org labor.
	*
	* @return the mon close of this org labor
	*/
	public int getMonClose() {
		return _orgLabor.getMonClose();
	}

	/**
	* Sets the mon close of this org labor.
	*
	* @param monClose the mon close of this org labor
	*/
	public void setMonClose(int monClose) {
		_orgLabor.setMonClose(monClose);
	}

	/**
	* Returns the tue open of this org labor.
	*
	* @return the tue open of this org labor
	*/
	public int getTueOpen() {
		return _orgLabor.getTueOpen();
	}

	/**
	* Sets the tue open of this org labor.
	*
	* @param tueOpen the tue open of this org labor
	*/
	public void setTueOpen(int tueOpen) {
		_orgLabor.setTueOpen(tueOpen);
	}

	/**
	* Returns the tue close of this org labor.
	*
	* @return the tue close of this org labor
	*/
	public int getTueClose() {
		return _orgLabor.getTueClose();
	}

	/**
	* Sets the tue close of this org labor.
	*
	* @param tueClose the tue close of this org labor
	*/
	public void setTueClose(int tueClose) {
		_orgLabor.setTueClose(tueClose);
	}

	/**
	* Returns the wed open of this org labor.
	*
	* @return the wed open of this org labor
	*/
	public int getWedOpen() {
		return _orgLabor.getWedOpen();
	}

	/**
	* Sets the wed open of this org labor.
	*
	* @param wedOpen the wed open of this org labor
	*/
	public void setWedOpen(int wedOpen) {
		_orgLabor.setWedOpen(wedOpen);
	}

	/**
	* Returns the wed close of this org labor.
	*
	* @return the wed close of this org labor
	*/
	public int getWedClose() {
		return _orgLabor.getWedClose();
	}

	/**
	* Sets the wed close of this org labor.
	*
	* @param wedClose the wed close of this org labor
	*/
	public void setWedClose(int wedClose) {
		_orgLabor.setWedClose(wedClose);
	}

	/**
	* Returns the thu open of this org labor.
	*
	* @return the thu open of this org labor
	*/
	public int getThuOpen() {
		return _orgLabor.getThuOpen();
	}

	/**
	* Sets the thu open of this org labor.
	*
	* @param thuOpen the thu open of this org labor
	*/
	public void setThuOpen(int thuOpen) {
		_orgLabor.setThuOpen(thuOpen);
	}

	/**
	* Returns the thu close of this org labor.
	*
	* @return the thu close of this org labor
	*/
	public int getThuClose() {
		return _orgLabor.getThuClose();
	}

	/**
	* Sets the thu close of this org labor.
	*
	* @param thuClose the thu close of this org labor
	*/
	public void setThuClose(int thuClose) {
		_orgLabor.setThuClose(thuClose);
	}

	/**
	* Returns the fri open of this org labor.
	*
	* @return the fri open of this org labor
	*/
	public int getFriOpen() {
		return _orgLabor.getFriOpen();
	}

	/**
	* Sets the fri open of this org labor.
	*
	* @param friOpen the fri open of this org labor
	*/
	public void setFriOpen(int friOpen) {
		_orgLabor.setFriOpen(friOpen);
	}

	/**
	* Returns the fri close of this org labor.
	*
	* @return the fri close of this org labor
	*/
	public int getFriClose() {
		return _orgLabor.getFriClose();
	}

	/**
	* Sets the fri close of this org labor.
	*
	* @param friClose the fri close of this org labor
	*/
	public void setFriClose(int friClose) {
		_orgLabor.setFriClose(friClose);
	}

	/**
	* Returns the sat open of this org labor.
	*
	* @return the sat open of this org labor
	*/
	public int getSatOpen() {
		return _orgLabor.getSatOpen();
	}

	/**
	* Sets the sat open of this org labor.
	*
	* @param satOpen the sat open of this org labor
	*/
	public void setSatOpen(int satOpen) {
		_orgLabor.setSatOpen(satOpen);
	}

	/**
	* Returns the sat close of this org labor.
	*
	* @return the sat close of this org labor
	*/
	public int getSatClose() {
		return _orgLabor.getSatClose();
	}

	/**
	* Sets the sat close of this org labor.
	*
	* @param satClose the sat close of this org labor
	*/
	public void setSatClose(int satClose) {
		_orgLabor.setSatClose(satClose);
	}

	public boolean isNew() {
		return _orgLabor.isNew();
	}

	public void setNew(boolean n) {
		_orgLabor.setNew(n);
	}

	public boolean isCachedModel() {
		return _orgLabor.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_orgLabor.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _orgLabor.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _orgLabor.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_orgLabor.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _orgLabor.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_orgLabor.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new OrgLaborWrapper((OrgLabor)_orgLabor.clone());
	}

	public int compareTo(com.liferay.portal.model.OrgLabor orgLabor) {
		return _orgLabor.compareTo(orgLabor);
	}

	@Override
	public int hashCode() {
		return _orgLabor.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.OrgLabor> toCacheModel() {
		return _orgLabor.toCacheModel();
	}

	public com.liferay.portal.model.OrgLabor toEscapedModel() {
		return new OrgLaborWrapper(_orgLabor.toEscapedModel());
	}

	public com.liferay.portal.model.OrgLabor toUnescapedModel() {
		return new OrgLaborWrapper(_orgLabor.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _orgLabor.toString();
	}

	public java.lang.String toXmlString() {
		return _orgLabor.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_orgLabor.persist();
	}

	public com.liferay.portal.model.ListType getType()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _orgLabor.getType();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public OrgLabor getWrappedOrgLabor() {
		return _orgLabor;
	}

	public OrgLabor getWrappedModel() {
		return _orgLabor;
	}

	public void resetOriginalValues() {
		_orgLabor.resetOriginalValues();
	}

	private OrgLabor _orgLabor;
}
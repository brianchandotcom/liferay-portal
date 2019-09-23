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

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link OrgLabor}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OrgLabor
 * @generated
 */
public class OrgLaborWrapper
	extends BaseModelWrapper<OrgLabor>
	implements ModelWrapper<OrgLabor>, OrgLabor {

	public OrgLaborWrapper(OrgLabor orgLabor) {
		super(orgLabor);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("orgLaborId", getOrgLaborId());
		attributes.put("companyId", getCompanyId());
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

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long orgLaborId = (Long)attributes.get("orgLaborId");

		if (orgLaborId != null) {
			setOrgLaborId(orgLaborId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long organizationId = (Long)attributes.get("organizationId");

		if (organizationId != null) {
			setOrganizationId(organizationId);
		}

		Long typeId = (Long)attributes.get("typeId");

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
	 * Returns the company ID of this org labor.
	 *
	 * @return the company ID of this org labor
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the fri close of this org labor.
	 *
	 * @return the fri close of this org labor
	 */
	@Override
	public int getFriClose() {
		return model.getFriClose();
	}

	/**
	 * Returns the fri open of this org labor.
	 *
	 * @return the fri open of this org labor
	 */
	@Override
	public int getFriOpen() {
		return model.getFriOpen();
	}

	/**
	 * Returns the mon close of this org labor.
	 *
	 * @return the mon close of this org labor
	 */
	@Override
	public int getMonClose() {
		return model.getMonClose();
	}

	/**
	 * Returns the mon open of this org labor.
	 *
	 * @return the mon open of this org labor
	 */
	@Override
	public int getMonOpen() {
		return model.getMonOpen();
	}

	/**
	 * Returns the mvcc version of this org labor.
	 *
	 * @return the mvcc version of this org labor
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the organization ID of this org labor.
	 *
	 * @return the organization ID of this org labor
	 */
	@Override
	public long getOrganizationId() {
		return model.getOrganizationId();
	}

	/**
	 * Returns the org labor ID of this org labor.
	 *
	 * @return the org labor ID of this org labor
	 */
	@Override
	public long getOrgLaborId() {
		return model.getOrgLaborId();
	}

	/**
	 * Returns the primary key of this org labor.
	 *
	 * @return the primary key of this org labor
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the sat close of this org labor.
	 *
	 * @return the sat close of this org labor
	 */
	@Override
	public int getSatClose() {
		return model.getSatClose();
	}

	/**
	 * Returns the sat open of this org labor.
	 *
	 * @return the sat open of this org labor
	 */
	@Override
	public int getSatOpen() {
		return model.getSatOpen();
	}

	/**
	 * Returns the sun close of this org labor.
	 *
	 * @return the sun close of this org labor
	 */
	@Override
	public int getSunClose() {
		return model.getSunClose();
	}

	/**
	 * Returns the sun open of this org labor.
	 *
	 * @return the sun open of this org labor
	 */
	@Override
	public int getSunOpen() {
		return model.getSunOpen();
	}

	/**
	 * Returns the thu close of this org labor.
	 *
	 * @return the thu close of this org labor
	 */
	@Override
	public int getThuClose() {
		return model.getThuClose();
	}

	/**
	 * Returns the thu open of this org labor.
	 *
	 * @return the thu open of this org labor
	 */
	@Override
	public int getThuOpen() {
		return model.getThuOpen();
	}

	/**
	 * Returns the tue close of this org labor.
	 *
	 * @return the tue close of this org labor
	 */
	@Override
	public int getTueClose() {
		return model.getTueClose();
	}

	/**
	 * Returns the tue open of this org labor.
	 *
	 * @return the tue open of this org labor
	 */
	@Override
	public int getTueOpen() {
		return model.getTueOpen();
	}

	@Override
	public ListType getType()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getType();
	}

	/**
	 * Returns the type ID of this org labor.
	 *
	 * @return the type ID of this org labor
	 */
	@Override
	public long getTypeId() {
		return model.getTypeId();
	}

	/**
	 * Returns the wed close of this org labor.
	 *
	 * @return the wed close of this org labor
	 */
	@Override
	public int getWedClose() {
		return model.getWedClose();
	}

	/**
	 * Returns the wed open of this org labor.
	 *
	 * @return the wed open of this org labor
	 */
	@Override
	public int getWedOpen() {
		return model.getWedOpen();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a org labor model instance should use the <code>OrgLabor</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this org labor.
	 *
	 * @param companyId the company ID of this org labor
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the fri close of this org labor.
	 *
	 * @param friClose the fri close of this org labor
	 */
	@Override
	public void setFriClose(int friClose) {
		model.setFriClose(friClose);
	}

	/**
	 * Sets the fri open of this org labor.
	 *
	 * @param friOpen the fri open of this org labor
	 */
	@Override
	public void setFriOpen(int friOpen) {
		model.setFriOpen(friOpen);
	}

	/**
	 * Sets the mon close of this org labor.
	 *
	 * @param monClose the mon close of this org labor
	 */
	@Override
	public void setMonClose(int monClose) {
		model.setMonClose(monClose);
	}

	/**
	 * Sets the mon open of this org labor.
	 *
	 * @param monOpen the mon open of this org labor
	 */
	@Override
	public void setMonOpen(int monOpen) {
		model.setMonOpen(monOpen);
	}

	/**
	 * Sets the mvcc version of this org labor.
	 *
	 * @param mvccVersion the mvcc version of this org labor
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the organization ID of this org labor.
	 *
	 * @param organizationId the organization ID of this org labor
	 */
	@Override
	public void setOrganizationId(long organizationId) {
		model.setOrganizationId(organizationId);
	}

	/**
	 * Sets the org labor ID of this org labor.
	 *
	 * @param orgLaborId the org labor ID of this org labor
	 */
	@Override
	public void setOrgLaborId(long orgLaborId) {
		model.setOrgLaborId(orgLaborId);
	}

	/**
	 * Sets the primary key of this org labor.
	 *
	 * @param primaryKey the primary key of this org labor
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the sat close of this org labor.
	 *
	 * @param satClose the sat close of this org labor
	 */
	@Override
	public void setSatClose(int satClose) {
		model.setSatClose(satClose);
	}

	/**
	 * Sets the sat open of this org labor.
	 *
	 * @param satOpen the sat open of this org labor
	 */
	@Override
	public void setSatOpen(int satOpen) {
		model.setSatOpen(satOpen);
	}

	/**
	 * Sets the sun close of this org labor.
	 *
	 * @param sunClose the sun close of this org labor
	 */
	@Override
	public void setSunClose(int sunClose) {
		model.setSunClose(sunClose);
	}

	/**
	 * Sets the sun open of this org labor.
	 *
	 * @param sunOpen the sun open of this org labor
	 */
	@Override
	public void setSunOpen(int sunOpen) {
		model.setSunOpen(sunOpen);
	}

	/**
	 * Sets the thu close of this org labor.
	 *
	 * @param thuClose the thu close of this org labor
	 */
	@Override
	public void setThuClose(int thuClose) {
		model.setThuClose(thuClose);
	}

	/**
	 * Sets the thu open of this org labor.
	 *
	 * @param thuOpen the thu open of this org labor
	 */
	@Override
	public void setThuOpen(int thuOpen) {
		model.setThuOpen(thuOpen);
	}

	/**
	 * Sets the tue close of this org labor.
	 *
	 * @param tueClose the tue close of this org labor
	 */
	@Override
	public void setTueClose(int tueClose) {
		model.setTueClose(tueClose);
	}

	/**
	 * Sets the tue open of this org labor.
	 *
	 * @param tueOpen the tue open of this org labor
	 */
	@Override
	public void setTueOpen(int tueOpen) {
		model.setTueOpen(tueOpen);
	}

	/**
	 * Sets the type ID of this org labor.
	 *
	 * @param typeId the type ID of this org labor
	 */
	@Override
	public void setTypeId(long typeId) {
		model.setTypeId(typeId);
	}

	/**
	 * Sets the wed close of this org labor.
	 *
	 * @param wedClose the wed close of this org labor
	 */
	@Override
	public void setWedClose(int wedClose) {
		model.setWedClose(wedClose);
	}

	/**
	 * Sets the wed open of this org labor.
	 *
	 * @param wedOpen the wed open of this org labor
	 */
	@Override
	public void setWedOpen(int wedOpen) {
		model.setWedOpen(wedOpen);
	}

	@Override
	protected OrgLaborWrapper wrap(OrgLabor orgLabor) {
		return new OrgLaborWrapper(orgLabor);
	}

}
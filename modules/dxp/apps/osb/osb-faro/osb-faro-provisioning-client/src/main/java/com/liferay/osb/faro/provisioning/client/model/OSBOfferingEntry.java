/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.provisioning.client.model;

import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;

import java.util.Date;

/**
 * @author Matthew Kong
 */
public class OSBOfferingEntry {

	public OSBOfferingEntry() {
	}

	public OSBOfferingEntry(ProductPurchase productPurchase) {
		_offeringEntryId = productPurchase.getKey();

		_productEntryId = productPurchase.getProductKey();

		_quantity = productPurchase.getQuantity();

		_startDate = productPurchase.getStartDate();

		ProductPurchase.Status status = productPurchase.getStatus();

		if (status.equals(ProductPurchase.Status.APPROVED)) {
			_status = 1;
		}

		_supportEndDate = productPurchase.getEndDate();
	}

	public long getAccountEntryId() {
		return _accountEntryId;
	}

	public Date getActualStartDate() {
		if (_actualStartDate == null) {
			return null;
		}

		return new Date(_actualStartDate.getTime());
	}

	public long getLicenseLifetime() {
		return _licenseLifetime;
	}

	public long getMaxConcurrentUsers() {
		return _maxConcurrentUsers;
	}

	public long getMaxUsers() {
		return _maxUsers;
	}

	public Date getModifiedDate() {
		if (_modifiedDate == null) {
			return null;
		}

		return new Date(_modifiedDate.getTime());
	}

	public String getOfferingEntryId() {
		return _offeringEntryId;
	}

	public long getOrderEntryId() {
		return _orderEntryId;
	}

	public String getPlatform() {
		return _platform;
	}

	public String getPlatformVersion() {
		return _platformVersion;
	}

	public String getProductDescription() {
		return _productDescription;
	}

	public String getProductEntryId() {
		return _productEntryId;
	}

	public int getQuantity() {
		return _quantity;
	}

	public int getSizing() {
		return _sizing;
	}

	public Date getStartDate() {
		if (_startDate == null) {
			return null;
		}

		return new Date(_startDate.getTime());
	}

	public int getStatus() {
		return _status;
	}

	public Date getSupportEndDate() {
		if (_supportEndDate == null) {
			return null;
		}

		return new Date(_supportEndDate.getTime());
	}

	public long getSupportLifetime() {
		return _supportLifetime;
	}

	public long getSupportResponseId() {
		return _supportResponseId;
	}

	public int getType() {
		return _type;
	}

	public long getUserId() {
		return _userId;
	}

	public String getUserName() {
		return _userName;
	}

	public String getVersion() {
		return _version;
	}

	public boolean isLicenses() {
		return _licenses;
	}

	public boolean isSupportTickets() {
		return _supportTickets;
	}

	public void setAccountEntryId(long accountEntryId) {
		_accountEntryId = accountEntryId;
	}

	public void setActualStartDate(Date actualStartDate) {
		if (actualStartDate != null) {
			_actualStartDate = new Date(actualStartDate.getTime());
		}
	}

	public void setLicenseLifetime(long licenseLifetime) {
		_licenseLifetime = licenseLifetime;
	}

	public void setLicenses(boolean licenses) {
		_licenses = licenses;
	}

	public void setMaxConcurrentUsers(long maxConcurrentUsers) {
		_maxConcurrentUsers = maxConcurrentUsers;
	}

	public void setMaxUsers(long maxUsers) {
		_maxUsers = maxUsers;
	}

	public void setModifiedDate(Date modifiedDate) {
		if (modifiedDate != null) {
			_modifiedDate = new Date(modifiedDate.getTime());
		}
	}

	public void setOfferingEntryId(String offeringEntryId) {
		_offeringEntryId = offeringEntryId;
	}

	public void setOrderEntryId(long orderEntryId) {
		_orderEntryId = orderEntryId;
	}

	public void setPlatform(String platform) {
		_platform = platform;
	}

	public void setPlatformVersion(String platformVersion) {
		_platformVersion = platformVersion;
	}

	public void setProductDescription(String productDescription) {
		_productDescription = productDescription;
	}

	public void setProductEntryId(String productEntryId) {
		_productEntryId = productEntryId;
	}

	public void setQuantity(int quantity) {
		_quantity = quantity;
	}

	public void setSizing(int sizing) {
		_sizing = sizing;
	}

	public void setStartDate(Date startDate) {
		if (startDate != null) {
			_startDate = new Date(startDate.getTime());
		}
	}

	public void setStatus(int status) {
		_status = status;
	}

	public void setSupportEndDate(Date supportEndDate) {
		if (supportEndDate != null) {
			_supportEndDate = new Date(supportEndDate.getTime());
		}
	}

	public void setSupportLifetime(long supportLifetime) {
		_supportLifetime = supportLifetime;
	}

	public void setSupportResponseId(long supportResponseId) {
		_supportResponseId = supportResponseId;
	}

	public void setSupportTickets(boolean supportTickets) {
		_supportTickets = supportTickets;
	}

	public void setType(int type) {
		_type = type;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public void setVersion(String version) {
		_version = version;
	}

	private long _accountEntryId;
	private Date _actualStartDate;
	private long _licenseLifetime;
	private boolean _licenses;
	private long _maxConcurrentUsers;
	private long _maxUsers;
	private Date _modifiedDate;
	private String _offeringEntryId;
	private long _orderEntryId;
	private String _platform;
	private String _platformVersion;
	private String _productDescription;
	private String _productEntryId;
	private int _quantity;
	private int _sizing;
	private Date _startDate;
	private int _status;
	private Date _supportEndDate;
	private long _supportLifetime;
	private long _supportResponseId;
	private boolean _supportTickets;
	private int _type;
	private long _userId;
	private String _userName;
	private String _version;

}
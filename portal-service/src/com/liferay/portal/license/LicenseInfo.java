/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.license;

import java.util.Date;

/**
 * @author Amos Fong
 */
public class LicenseInfo {

	public LicenseInfo(
		String accountEntryName, String owner, String description,
		String productEntryName, String productId, String productVersion,
		String licenseEntryName, String licenseEntryType, String licenseVersion,
		Date startDate, Date expirationDate, int maxServers,
		int maxHttpSessions, long maxConcurrentUsers, long maxUsers,
		String hostName, String[] ipAddresses, String[] macAddresses,
		String[] serverIds) {

		_accountEntryName = accountEntryName;
		_description = description;
		_expirationDate = expirationDate;
		_hostName = hostName;
		_ipAddresses = ipAddresses;
		_licenseEntryName = licenseEntryName;
		_licenseEntryType = licenseEntryType;
		_licenseVersion = licenseVersion;
		_macAddresses = macAddresses;
		_maxConcurrentUsers = maxConcurrentUsers;
		_maxHttpSessions = maxHttpSessions;
		_maxServers = maxServers;
		_maxUsers = maxUsers;
		_owner = owner;
		_productEntryName = productEntryName;
		_productId = productId;
		_productVersion = productVersion;
		_serverIds = serverIds;
		_startDate = startDate;
	}

	public String getAccountEntryName() {
		return _accountEntryName;
	}

	public String getDescription() {
		return _description;
	}

	public Date getExpirationDate() {
		return _expirationDate;
	}

	public String getHostName() {
		return _hostName;
	}

	public String[] getIpAddresses() {
		return _ipAddresses;
	}

	public String getLicenseEntryName() {
		return _licenseEntryName;
	}

	public String getLicenseEntryType() {
		return _licenseEntryType;
	}

	public String getLicenseVersion() {
		return _licenseVersion;
	}

	public String[] getMacAddresses() {
		return _macAddresses;
	}

	public long getMaxConcurrentUsers() {
		return _maxConcurrentUsers;
	}

	public int getMaxHttpSessions() {
		return _maxHttpSessions;
	}

	public int getMaxServers() {
		return _maxServers;
	}

	public long getMaxUsers() {
		return _maxUsers;
	}

	public String getOwner() {
		return _owner;
	}

	public String getProductEntryName() {
		return _productEntryName;
	}

	public String getProductId() {
		return _productId;
	}

	public String getProductVersion() {
		return _productVersion;
	}

	public String[] getServerIds() {
		return _serverIds;
	}

	public Date getStartDate() {
		return _startDate;
	}

	private String _accountEntryName;
	private String _description;
	private Date _expirationDate;
	private String _hostName;
	private String[] _ipAddresses;
	private String _licenseEntryName;
	private String _licenseEntryType;
	private String _licenseVersion;
	private String[] _macAddresses;
	private long _maxConcurrentUsers;
	private int _maxHttpSessions;
	private int _maxServers;
	private long _maxUsers;
	private String _owner;
	private String _productEntryName;
	private String _productId;
	private String _productVersion;
	private String[] _serverIds;
	private Date _startDate;

}
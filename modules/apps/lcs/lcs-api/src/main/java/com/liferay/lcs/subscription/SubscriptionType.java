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

package com.liferay.lcs.subscription;

/**
 * @author  Igor Beslic
 * @version LCS 1.7.1
 * @since   LCS 1.3
 */
public enum SubscriptionType {

	BACKUP("backup"), CLUSTER("cluster"), DEVELOPER("developer"),
	DEVELOPER_CLUSTER("developer-cluster"), ELASTIC("elastic"),
	ENTERPRISE("enterprise"), LIMITED("limited"),
	NON_PRODUCTION("non-production"), OEM("oem"), PER_USER("per-user"),
	PRODUCTION("production"), TRIAL("trial"), UNDEFINED("no-subscriptions");

	public static SubscriptionType toSubscriptionType(String licenseEntryType) {
		if (licenseEntryType == null) {
			return UNDEFINED;
		}
		else if (licenseEntryType.equals(BACKUP.getLicenseEntryType())) {
			return BACKUP;
		}
		else if (licenseEntryType.equals(CLUSTER.getLicenseEntryType())) {
			return CLUSTER;
		}
		else if (licenseEntryType.equals(DEVELOPER.getLicenseEntryType())) {
			return DEVELOPER;
		}
		else if (licenseEntryType.equals(
					DEVELOPER_CLUSTER.getLicenseEntryType())) {

			return DEVELOPER_CLUSTER;
		}
		else if (licenseEntryType.equals(ELASTIC.getLicenseEntryType())) {
			return ELASTIC;
		}
		else if (licenseEntryType.equals(ENTERPRISE.getLicenseEntryType())) {
			return ENTERPRISE;
		}
		else if (licenseEntryType.equals(LIMITED.getLicenseEntryType())) {
			return LIMITED;
		}
		else if (licenseEntryType.equals(
					NON_PRODUCTION.getLicenseEntryType())) {

			return NON_PRODUCTION;
		}
		else if (licenseEntryType.equals(OEM.getLicenseEntryType())) {
			return OEM;
		}
		else if (licenseEntryType.equals(PER_USER.getLicenseEntryType())) {
			return PER_USER;
		}
		else if (licenseEntryType.equals(PRODUCTION.getLicenseEntryType())) {
			return PRODUCTION;
		}
		else if (licenseEntryType.equals(TRIAL.getLicenseEntryType())) {
			return TRIAL;
		}

		return UNDEFINED;
	}

	public String getLabel() {
		return _label;
	}

	public String getLicenseEntryType() {
		return _licenseEntryType;
	}

	private SubscriptionType(String licenseEntryType) {
		_licenseEntryType = licenseEntryType;

		_label = licenseEntryType;
	}

	private final String _label;
	private final String _licenseEntryType;

}
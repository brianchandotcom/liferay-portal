/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type ApplicationPolicyDetailsType = {
	dataJSON: string;
	email: string;
	phone: string;
};

export type PolicyDetailsType = {
	annualMileage: number;
	creditRating: string;
	dateOfBirth: string;
	features: string;
	firstName: string;
	gender: string;
	highestEducation: string;
	make: string;
	maritalStatus: string;
	model: string;
	occupation: string;
	ownership: string;
	primaryUsage: string;
	year: string;
};

export type dataJSONType = {
	contactInfo: {
		dateOfBirth: '';
	};
	coverage: {
		form: [];
	};
	driverInfo: {
		form: [];
	};
	vehicleInfo: {
		form: [];
	};
};

export type InfoPanelType = {[keys: string]: string};

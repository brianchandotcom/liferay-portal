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

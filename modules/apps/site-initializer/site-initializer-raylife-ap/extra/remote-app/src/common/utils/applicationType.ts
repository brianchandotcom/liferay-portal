/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ContactInfoFormTypes,
	CoverageFormTypes,
	DriverInfoFormTypes,
	VehicleInfoFormTypes,
} from '../../routes/applications/context/NewApplicationAutoContextProvider';

export type NewApplicationFormStepsType = {
	externalReferenceCode: string;
	steps: {
		contactInfo: {
			form: ContactInfoFormTypes;
			index: number;
			name: string;
		};
		coverage: {
			form: CoverageFormTypes;
			index: number;
			name: string;
		};
		driverInfo: {
			form: DriverInfoFormTypes[];
			index: number;
			name: string;
		};
		review: {
			index: number;
			name: string;
		};
		vehicleInfo: {
			form: VehicleInfoFormTypes[];
			index: number;
			name: string;
		};
	};
};

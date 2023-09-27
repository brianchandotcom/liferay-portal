/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ActivitiesComponent from '../../../../../common/components/activities-table-list';
import sortedDateByDescOrder from '../../../../../common/utils/sortedDateByDescOrder';

const policyDataActivities = [
	{
		activity: 'Requested driving history',
		by: 'Scott Producer (Producer)',
		date: 'Apr 15, 2022',
		message:
			'I contacted the insured with the request from the underwriter for a full driving history. Waiting for reply.	',
	},
	{
		activity: 'Inquiry for documents.',
		by: 'Scott Producer (Producer)',
		date: 'Jan 21, 2022',
		message:
			'For further risk assessment, we need more information regarding the insureds driving history. Please contact the insured and request complete driving history including any drivers education classes.',
	},
	{
		activity: 'Application submitted',
		by: 'Scott Producer (Producer)',
		date: 'Jan 24, 2022',
		message:
			'The insured called me this morning at 10am requesting a multi-line application for both auto and home. I submitted the application on behalf of the insured, and the application is currently waiting for underwriting process to begin.',
	},
	{
		activity: 'Requested driving history',
		by: 'Scott Producer (Producer)',
		date: 'Jan 20, 2022',
		message:
			'I contacted the insured with the request from the underwriter for a full driving history. Waiting for reply.	',
	},
	{
		activity: 'Requested driving history',
		by: 'Scott Producer (Producer)',
		date: 'Feb 28, 2021',
		message:
			'I contacted the insured with the request from the underwriter for a full driving history. Waiting for reply.	',
	},
].sort(sortedDateByDescOrder);

const PolicyDetailsActivities = () => {
	return <ActivitiesComponent activitiesData={policyDataActivities} />;
};

export default PolicyDetailsActivities;

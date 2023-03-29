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

export default policyDataActivities;

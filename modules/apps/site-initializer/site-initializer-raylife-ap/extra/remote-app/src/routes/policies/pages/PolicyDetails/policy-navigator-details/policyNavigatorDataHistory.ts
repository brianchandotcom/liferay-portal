/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type InfoPanelType = {[keys: string]: string};

const arrayOfHistory: InfoPanelType[] = [
	{
		date: '1/22/2018',
		description: 'Ticket (Speeding)',
		detail_Amount: 'Unknown',
		detail_Injuries: 'No',
		detail_details: 'Further ticket details unknown.',
	},
	{
		date: '1/20/2018',
		description: 'Ticket (Illegal Maneuvering)',
		detail_Amount: 'Unknown',
		detail_Injuries: 'No',
		detail_details: 'Further ticket details unknown.',
	},
	{
		date: '3/15/2018',
		description: 'Ticket (Distracted Driving)',
		detail_Amount: 'Unknown',
		detail_Injuries: 'No',
		detail_details: 'Unknown',
	},
	{
		date: '4/10/2019',
		description:
			'Accident (Insured made an illegal u-turn in a busy intersection when the accident happened.)',
		detail_Amount: '$5,700.00',
		detail_Injuries: 'No',
		detail_details:
			'Insured made an illegal u-turn at intersection and was hit by an oncoming car on the passenger side front door. The insured was at fault and had to pay $5,200 out-of-pocket (after $500 deductible) for repairs.',
	},
];

export default arrayOfHistory;

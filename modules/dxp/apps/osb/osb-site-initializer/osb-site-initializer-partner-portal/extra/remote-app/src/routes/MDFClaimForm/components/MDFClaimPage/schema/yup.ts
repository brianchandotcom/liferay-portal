/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {array, mixed, number, object, string} from 'yup';

const permitedDocument = {
	types: [
		'image/jpg',
		'image/jpeg',
		'image/gif',
		'image/png',
		'application/pdf',
	],
	maxSize: 100000,
};

const claimSchema = object({
	totalClaimAmount: number()
		.moreThan(0, 'Need be bigger than 0')
		.required('Required')
		.test(
			'is-greater-than-the-requested-amount',
			'Total Claim Amount cannot be greater than Total MDF Requested Amount',
			(totalClaimAmount, testContext) =>
				Number(totalClaimAmount) <=
				Number(testContext.parent.totalrequestedAmount)
		),
	reimbursementInvoice: mixed()
		.required('Required')
		.test(
			'fileSize',
			'File Size is too large',
			(reimbursementInvoice) =>
				reimbursementInvoice?.size <= permitedDocument.maxSize
		)
		.test('fileType', 'Unsupported File Format', (reimbursementInvoice) =>
			permitedDocument.types.includes(reimbursementInvoice?.type)
		),
	activities: array()
		.test(
			'needAtLeatOneSelectedActivity',
			'Need at least one selected activity',
			(activities) =>
				activities?.some((activity) => activity.selected) ? true : false
		)
		.test(
			'needMoreThanOneSelectedActivity',
			'Need at least one invoice uploaded',
			(activities) =>
				activities?.some(
					(activity) =>
						activity.selected &&
						activity.budgets.some((budget: any) =>
							budget.invoice ? true : false
						)
				)
					? true
					: false
		)
		.of(
			object({
				listQualifiedLeads: mixed().when('selected', {
					is: (selected: boolean) => selected,
					then: (schema) =>
						schema
							.test(
								'fileSize',
								'File Size is too large',
								(listQualifiedLeads) => {
									return permitedDocument.types.includes(
										listQualifiedLeads?.type
									);
								}
							)
							.test(
								'fileType',
								'Unsupported File Format',
								(listQualifiedLeads) =>
									permitedDocument.types.includes(
										listQualifiedLeads?.type
									)
							)
							.required('Required'),
				}),
				metrics: string().when('selected', {
					is: (selected: boolean) => selected,
					then: (schema) => schema.required('Required'),
				}),
			})
		),
});

export default claimSchema;

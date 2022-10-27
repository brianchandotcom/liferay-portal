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

import {array, boolean, mixed, number, object, string} from 'yup';

const KB_TO_MB = 1024;
const MAX_MB = KB_TO_MB * 3;

const validDocument = {
	imageDocumentsTypes: [
		'image/jpg',
		'image/jpeg',
		'image/tiff',
		'image/png',
		'application/pdf',
		'application/msword',
		'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
	],
	listOfLeadsDocumentsTypes: [
		'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
		'text/csv',
	],
	maxSize: MAX_MB,
};

const claimSchema = object({
	activities: array()
		.of(
			object({
				listQualifiedLeads: mixed().when('selected', {
					is: (selected: boolean) => selected,
					then: (schema) =>
						schema
							.test(
								'fileSize',
								'File Size is too large',
								(listQualifiedLeads) =>
									listQualifiedLeads
										? Math.ceil(
												listQualifiedLeads.size / 1000
										  ) <= validDocument.maxSize
										: true
							)
							.test(
								'fileType',
								'Unsupported File Format, upload a valid format *csv *xlsx *xls ',
								(listQualifiedLeads) =>
									listQualifiedLeads
										? validDocument.listOfLeadsDocumentsTypes.includes(
												listQualifiedLeads.type
										  )
										: true
							),
				}),
				metrics: string().max(
					350,
					'You have exceeded the character limit'
				),
				selected: boolean(),
			})
		)
		.test(
			'needAtLeatOneSelectedActivity',
			'Need at least one selected activity',
			(activities) =>
				Boolean(activities?.some((activity) => activity.selected))
		),

	reimbursementInvoice: mixed()
		.required('Required')
		.test('fileSize', 'File Size is too large', (reimbursementInvoice) =>
			reimbursementInvoice
				? Math.ceil(reimbursementInvoice.size / 1000) <=
				  validDocument.maxSize
				: true
		)
		.test(
			'fileType',
			'Unsupported File Format, upload a valid format *jpg *jpeg *gif *png *pdf',
			(reimbursementInvoice) =>
				reimbursementInvoice
					? validDocument.imageDocumentsTypes.includes(
							reimbursementInvoice.type
					  )
					: true
		),
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
});

export default claimSchema;

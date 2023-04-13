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

const validateDocument = {
	fileSize: {
		maxSize: MAX_MB,
		message: 'File Size is too large',
	},
	imageDocument: {
		message:
			'Unsupported File Format, upload a valid format *jpg *jpeg *tiff *png *pdf *doc *docx',
		types: [
			'image/jpg',
			'image/jpeg',
			'image/tiff',
			'image/png',
			'application/pdf',
			'application/msword',
			'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
		],
	},
	listOfLeadsDocuments: {
		message:
			'Unsupported File Format, upload a valid format *csv *xlsx *xls',
		types: [
			'application/vnd.ms-excel',
			'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
			'text/csv',
		],
	},
};

const claimSchema = object({
	activities: array()
		.of(
			object({
				budgets: array().when('selected', {
					is: (selected: boolean) => selected,
					then: (schema) =>
						schema.of(
							object({
								invoice: mixed().when('selected', {
									is: (selected: boolean) => selected,
									then: (schema) =>
										schema
											.test(
												'fileSize',
												validateDocument.fileSize
													.message,
												(invoice) => {
													if (
														invoice &&
														!invoice.id
													) {
														return invoice
															? Math.ceil(
																	invoice.size /
																		1000
															  ) <=
																	validateDocument
																		.fileSize
																		.maxSize
															: false;
													}

													return true;
												}
											)
											.required()
											.test(
												'fileType',
												validateDocument.imageDocument
													.message,
												(invoice) => {
													if (
														invoice &&
														!invoice.id
													) {
														return invoice
															? validateDocument.imageDocument.types.includes(
																	invoice.type
															  )
															: false;
													}

													return true;
												}
											)
											.required(),
								}),
								invoiceAmount: number().when('selected', {
									is: (selected: boolean) => selected,
									then: (schema) =>
										schema
											.moreThan(
												0,
												'Need be bigger than 0'
											)
											.test(
												'biggerAmount',
												'Invoice amount is larger than the MDF requested amount',
												(invoiceAmount, testContext) =>
													Number(invoiceAmount) <=
													Number(
														testContext.parent
															.requestAmount
													)
											)
											.required(),
								}),
								requestAmount: number(),
							})
						),
				}),
				listOfQualifiedLeads: mixed().when('selected', {
					is: (selected: boolean) => selected,
					then: (schema) =>
						schema
							.test(
								'fileSize',
								validateDocument.fileSize.message,
								(listOfQualifiedLeads) => {
									if (
										listOfQualifiedLeads &&
										!listOfQualifiedLeads.id
									) {
										return listOfQualifiedLeads
											? Math.ceil(
													listOfQualifiedLeads.size /
														1000
											  ) <=
													validateDocument.fileSize
														.maxSize
											: false;
									}

									return true;
								}
							)
							.test(
								'fileType',
								validateDocument.listOfLeadsDocuments.message,
								(listOfQualifiedLeads) => {
									if (
										listOfQualifiedLeads &&
										!listOfQualifiedLeads.id
									) {
										return listOfQualifiedLeads
											? validateDocument.listOfLeadsDocuments.types.includes(
													listOfQualifiedLeads.type
											  )
											: false;
									}

									return true;
								}
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
			'Need at least one activity selected',
			(activities) =>
				Boolean(activities?.some((activity) => activity.selected))
		)
		.test(
			'needMoreThanOneBudgetSelected',
			'Need at least one budget selected',
			(activities) =>
				Boolean(
					activities?.some((activity) =>
						Boolean(
							activity.budgets?.some((budget) => budget.selected)
						)
					)
				)
		)
		.test(
			'selectedActivityNeedsAtLeastOneBudget',
			'Need at least one budget per activity selected',
			(activities) => {
				return Boolean(
					!activities
						?.map((activity) => {
							return activity.selected
								? activity.selected &&
										activity?.budgets?.some(
											(budget) => budget.selected
										)
								: true;
						})
						.includes(false)
				);
			}
		)
		.test(
			'needMoreThanOneBudgetInvoice',
			'Need at least one budget invoice added',
			(activities) =>
				Boolean(
					activities?.some((activity) =>
						Boolean(
							activity.budgets?.some((budget) => budget.invoice)
						)
					)
				)
		),
	amountClaimed: number()
		.moreThan(0, 'Need be bigger than 0')
		.required('Required')
		.test(
			'is-greater-than-the-requested-amount',
			'Total Claim Amount cannot be greater than Total MDF Requested Amount',
			(amountClaimed, testContext) =>
				Number(amountClaimed) <=
				Number(testContext.parent.mdfRequestedAmount)
		),
	reimbursementInvoice: mixed()
		.required('Required')
		.test(
			'fileSize',
			validateDocument.fileSize.message,
			(reimbursementInvoice) => {
				if (reimbursementInvoice && !reimbursementInvoice.id) {
					return reimbursementInvoice
						? Math.ceil(reimbursementInvoice.size / 1000) <=
								validateDocument.fileSize.maxSize
						: false;
				}

				return true;
			}
		)
		.test(
			'fileType',
			validateDocument.imageDocument.message,
			(reimbursementInvoice) => {
				if (reimbursementInvoice && !reimbursementInvoice.id) {
					return reimbursementInvoice
						? validateDocument.imageDocument.types.includes(
								reimbursementInvoice.type
						  )
						: false;
				}

				return true;
			}
		),
});

export default claimSchema;

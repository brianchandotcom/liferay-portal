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

import {TypeActivityKey} from '../../../../../common/enums/TypeActivityKey';
import LiferayPicklist from '../../../../../common/interfaces/liferayPicklist';
import {validateDocument} from './constants/validateDocument';
import {getAllContentsFieldsValidation} from './fieldValidation/getAllContentsFieldsValidation';
import {getEventCollateralsValidation} from './fieldValidation/getEventCollateralsValidation';
import {getEventInvitationsValidation} from './fieldValidation/getEventInvitationsValidation';
import {getEventPhotosValidation} from './fieldValidation/getEventPhotosValidation';
import {getImagesValidation} from './fieldValidation/getImagesValidation';

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
												validateDocument.document
													.message,
												(invoice) => {
													if (
														invoice &&
														!invoice.id
													) {
														return invoice
															? validateDocument.document.types.includes(
																	invoice.type
															  )
															: false;
													}

													return true;
												}
											)
											.required('Required'),
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
											.required('Required'),
								}),
								requestAmount: number(),
							})
						),
				}),
				eventProgram: mixed().when(['selected', 'typeActivity'], {
					is: (selected: boolean, typeActivity: LiferayPicklist) =>
						selected && typeActivity.key === TypeActivityKey.EVENT,
					then: (schema) =>
						schema
							.required('Required')
							.test(
								'fileSize',
								validateDocument.fileSize.message,
								(eventProgram) => {
									if (eventProgram && !eventProgram.id) {
										return eventProgram
											? Math.ceil(
													eventProgram.size / 1000
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
								validateDocument.document.message,
								(eventProgram) => {
									if (eventProgram && !eventProgram.id) {
										return eventProgram
											? validateDocument.document.types.includes(
													eventProgram.type
											  )
											: false;
									}

									return true;
								}
							),
				}),
				listOfQualifiedLeads: mixed()
					.when('selected', {
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
														validateDocument
															.fileSize.maxSize
												: false;
										}

										return true;
									}
								)
								.test(
									'fileType',
									validateDocument.listOfLeadsDocuments
										.message,
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
					})
					.when(['selected', 'typeActivity'], {
						is: (
							selected: boolean,
							typeActivity: LiferayPicklist
						) =>
							(selected &&
								typeActivity.key === TypeActivityKey.EVENT) ||
							(selected &&
								typeActivity.key ===
									TypeActivityKey.MISCELLANEOUS_MARKETING),
						then: (schema) => schema.required('Required'),
					}),
				metrics: string().when(['selected', 'typeActivity'], {
					is: (selected: boolean, typeActivity: LiferayPicklist) =>
						selected &&
						typeActivity.key === TypeActivityKey.DIGITAL_MARKETING,
					then: (schema) =>
						schema
							.required('Required')
							.max(350, 'You have exceeded the character limit'),
				}),
				proofOfPerformance: object().when(
					['selected', 'typeActivity'],
					(selected: boolean, typeActivity) => {
						let targetFields = {};

						if (selected) {
							switch (typeActivity.key) {
								case TypeActivityKey.EVENT:
									targetFields = {
										...getEventInvitationsValidation,
										...getEventPhotosValidation,
										...getEventCollateralsValidation,
									};
									break;
								case TypeActivityKey.DIGITAL_MARKETING:
									targetFields = {
										...getAllContentsFieldsValidation,
									};
									break;
								case TypeActivityKey.CONTENT_MARKETING:
									targetFields = {
										...getAllContentsFieldsValidation,
									};
									break;
								default:
									targetFields = {
										...getAllContentsFieldsValidation,
										...getImagesValidation,
									};
									break;
							}
						}

						return object(targetFields);
					}
				),
				selected: boolean(),
				telemarketingMetrics: string().when(
					['selected', 'typeActivity'],
					{
						is: (
							selected: boolean,
							typeActivity: LiferayPicklist
						) =>
							selected &&
							typeActivity.key ===
								TypeActivityKey.MISCELLANEOUS_MARKETING,
						then: (schema) =>
							schema
								.required('Required')
								.max(
									350,
									'You have exceeded the character limit'
								),
					}
				),
				videoLink: string().when(['selected', 'typeActivity'], {
					is: (selected: boolean, typeActivity: LiferayPicklist) =>
						selected &&
						typeActivity.key === TypeActivityKey.CONTENT_MARKETING,
					then: (schema) =>
						schema
							.required('Required')
							.max(250, 'You have exceeded the character limit'),
				}),
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
			validateDocument.document.message,
			(reimbursementInvoice) => {
				if (reimbursementInvoice && !reimbursementInvoice.id) {
					return reimbursementInvoice
						? validateDocument.document.types.includes(
								reimbursementInvoice.type
						  )
						: false;
				}

				return true;
			}
		),
	totalClaimAmount: number()
		.moreThan(0, 'Need be bigger than 0')
		.required('Required')
		.test(
			'is-greater-than-the-requested-amount',
			'Total Claim Amount cannot be greater than Total MDF Requested Amount',
			(totalClaimAmount, testContext) =>
				Number(totalClaimAmount) <=
				Number(testContext.parent.totalMDFRequestedAmount)
		),
});

export default claimSchema;

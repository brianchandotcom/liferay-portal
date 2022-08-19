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

import dayjs from 'dayjs';
import {array, date, object, string} from 'yup';

import {TypeActivityExternalReferenceCode} from '../../../../../../common/enums/typeActivityExternalReferenceCode';

const activitiesSchema = object({
	activities: array()
		.of(
			object({
				name: string()
					.max(350, 'You have exceeded the character limit')
					.required('Required'),
				r_typeActivityToActivities_c_typeActivityId: string().required(
					'Required'
				),
				description: string().when(
					'r_typeActivityToActivities_c_typeActivityId',
					{
						is: (value: string) =>
							value === TypeActivityExternalReferenceCode.EVENT,
						then: (schema) =>
							schema
								.max(
									350,
									'You have exceeded the character limit'
								)
								.required('Required'),
					}
				),
				location: string().when(
					'r_typeActivityToActivities_c_typeActivityId',
					{
						is: (value: string) =>
							value === TypeActivityExternalReferenceCode.EVENT,
						then: (schema) =>
							schema
								.max(
									350,
									'You have exceeded the character limit'
								)
								.required('Required'),
					}
				),
				venueName: string().when(
					'r_typeActivityToActivities_c_typeActivityId',
					{
						is: (value: string) =>
							value === TypeActivityExternalReferenceCode.EVENT,
						then: (schema) =>
							schema
								.max(
									350,
									'You have exceeded the character limit'
								)
								.required('Required'),
					}
				),
				liferayBranding: string().when(
					'r_typeActivityToActivities_c_typeActivityId',
					{
						is: (value: string) =>
							value === TypeActivityExternalReferenceCode.EVENT,
						then: (schema) =>
							schema
								.max(
									350,
									'You have exceeded the character limit'
								)
								.required('Required'),
					}
				),
				liferayParticipationRequirements: string().when(
					'r_typeActivityToActivities_c_typeActivityId',
					{
						is: (value: string) =>
							value === TypeActivityExternalReferenceCode.EVENT,
						then: (schema) =>
							schema
								.max(
									350,
									'You have exceeded the character limit'
								)
								.required('Required'),
					}
				),
				sourceAndSizeOfInviteeList: string().when(
					'r_typeActivityToActivities_c_typeActivityId',
					{
						is: (value: string) =>
							value === TypeActivityExternalReferenceCode.EVENT,
						then: (schema) =>
							schema
								.max(
									350,
									'You have exceeded the character limit'
								)
								.required('Required'),
					}
				),
				activityPromotion: string().when(
					'r_typeActivityToActivities_c_typeActivityId',
					{
						is: (value: string) =>
							value === TypeActivityExternalReferenceCode.EVENT,
						then: (schema) =>
							schema
								.max(
									350,
									'You have exceeded the character limit'
								)
								.required('Required'),
					}
				),
				overallMessageContentCTA: string().when(
					'r_typeActivityToActivities_c_typeActivityId',
					{
						is: (value: string) =>
							value ===
							TypeActivityExternalReferenceCode.DIGITAL_MARKETING,
						then: (schema) =>
							schema
								.max(
									350,
									'You have exceeded the character limit'
								)
								.required('Required'),
					}
				),
				specificSites: string().when(
					'r_typeActivityToActivities_c_typeActivityId',
					{
						is: (value: string) =>
							value ===
							TypeActivityExternalReferenceCode.DIGITAL_MARKETING,
						then: (schema) =>
							schema
								.max(
									350,
									'You have exceeded the character limit'
								)
								.required('Required'),
					}
				),
				assetsLiferayRequired: string().when(
					'r_typeActivityToActivities_c_typeActivityId',
					{
						is: (value: string) =>
							value ===
							TypeActivityExternalReferenceCode.DIGITAL_MARKETING,
						then: (schema) => schema.required('Required'),
					}
				),
				howLiferayBrandUsed: string().when(
					'r_typeActivityToActivities_c_typeActivityId',
					{
						is: (value: string) =>
							value ===
							TypeActivityExternalReferenceCode.DIGITAL_MARKETING,
						then: (schema) => schema.required('Required'),
					}
				),
				gatedLandingPage: string().when(
					'r_typeActivityToActivities_c_typeActivityId',
					{
						is: (value: string) =>
							value ===
							TypeActivityExternalReferenceCode.CONTENT_MARKETING,
						then: (schema) => schema.required('Required'),
					}
				),
				primaryThemeOrMessage: string().when(
					'r_typeActivityToActivities_c_typeActivityId',
					{
						is: (value: string) =>
							value ===
							TypeActivityExternalReferenceCode.CONTENT_MARKETING,
						then: (schema) =>
							schema
								.max(
									350,
									'You have exceeded the character limit'
								)
								.required('Required'),
					}
				),
				goalOfContent: string().when(
					'r_typeActivityToActivities_c_typeActivityId',
					{
						is: (value: string) =>
							value ===
							TypeActivityExternalReferenceCode.CONTENT_MARKETING,
						then: (schema) =>
							schema
								.max(
									350,
									'You have exceeded the character limit'
								)
								.required('Required'),
					}
				),
				hiringOutsideWriterOrAgency: string().when(
					'r_typeActivityToActivities_c_typeActivityId',
					{
						is: (value: string) =>
							value ===
							TypeActivityExternalReferenceCode.CONTENT_MARKETING,
						then: (schema) => schema.required('Required'),
					}
				),
				r_tacticToActivities_c_tacticId: string().required('Required'),
				startDate: date()
					.test('Year test', 'cannot be today', (value) => {
						const currentTime = dayjs();
						const chosenDate = dayjs(value);

						return currentTime.diff(chosenDate) <= 0;
					})
					.required('Required'),
				endDate: date()
					.test(
						'Year test',
						'End date must be less than six month after start date',
						(value, testContext) => {
							const startDate = dayjs(
								testContext.parent.startDate
							);

							const endDate = dayjs(value);

							return endDate.diff(startDate, 'day') <= 60;
						}
					)
					.required('Required'),
				budgets: array().min(1, 'Required'),
				leadGenerated: string().required('Required'),
				targetofLeads: string()
					.max(350, 'You have exceeded the character limit')
					.required('Required'),
				leadFollowUpStrategies: array().min(1, 'Required'),
				detailsLeadFollowUp: string()
					.max(350, 'You have exceeded the character limit')
					.required('Required'),
			})
		)
		.min(1),
});

export default activitiesSchema;

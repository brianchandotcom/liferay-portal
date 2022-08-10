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

import React, {useCallback, useMemo, useState} from 'react';

import PRMForm from '../../../../../common/components/PRMForm';
import PRMFormik from '../../../../../common/components/PRMFormik';
import {LiferayPicklistName} from '../../../../../common/enums/liferayPicklistName';
import MDFRequestActivity from '../../../../../common/interfaces/mdfRequestActivity';
import getBooleanEntries from '../../../../../common/utils/getBooleanEntries';
import BudgetBreakdownSection from './components/BudgetBreakdownSection';
import ContentMarketingFields from './components/ContentMarketingFields';
import DigitalMarketingFields from './components/DigitalMarketingFields';
import EventFields from './components/EventFields';
import MiscellaneousMarketingFields from './components/MiscellaneousMarketingFields';
import {TypeActivityExternalReferenceCode} from './enums/typeActivityExternalReferenceCode';
import useDynamicFieldEntries from './hooks/useDynamicFieldEntries';
import useSelectedTypeActivity from './hooks/useSelectedTypeActivity';
import useTacticsTypeActivity from './hooks/useTacticsTypeActivity';

interface IProps {
	currentActivity: MDFRequestActivity;
	currentIndex: number;
	setFieldValue: (
		field: string,
		value: any,
		shouldValidate?: boolean | undefined
	) => void;
}

type TypeOfActivityComponent = {
	[key in string]?: JSX.Element;
};

const Form = ({currentActivity, currentIndex, setFieldValue}: IProps) => {
	const [tacticsByTypeActivity, setTacticsByTypeActivity] = useState<
		React.OptionHTMLAttributes<HTMLOptionElement>[]
	>();

	const {fieldEntries, typeOfActivities} = useDynamicFieldEntries();

	const selectedTypeActivity = useSelectedTypeActivity(
		currentActivity,
		typeOfActivities
	);

	const typeOfActivitiesEntries = useMemo(
		() =>
			typeOfActivities?.map((typeOfActivity) => ({
				label: typeOfActivity.name,
				value: String(typeOfActivity.id),
			})),
		[typeOfActivities]
	);

	useTacticsTypeActivity(
		selectedTypeActivity,
		useCallback(
			(tactics) =>
				setTacticsByTypeActivity(
					tactics.map((tatic) => ({
						label: tatic.name,
						value: tatic.id,
					}))
				),
			[]
		)
	);

	const typeOfActivityComponents: TypeOfActivityComponent = {
		[TypeActivityExternalReferenceCode.DIGITAL_MARKETING]: (
			<DigitalMarketingFields index={currentIndex} />
		),
		[TypeActivityExternalReferenceCode.CONTENT_MARKETING]: (
			<ContentMarketingFields index={currentIndex} />
		),
		[TypeActivityExternalReferenceCode.EVENT]: (
			<EventFields index={currentIndex} />
		),
		[TypeActivityExternalReferenceCode.MISCELLANEOUS_MARKETING]: (
			<MiscellaneousMarketingFields index={currentIndex} />
		),
	};

	return (
		<>
			<PRMForm.Section title="Campaign Activity">
				<PRMFormik.Field
					component={PRMForm.InputText}
					label="Activity name"
					name={`activities[${currentIndex}].name`}
					required
				/>

				<PRMForm.Group>
					<PRMFormik.Field
						component={PRMForm.Select}
						label="Type of Activity"
						name={`activities[${currentIndex}].r_typeActivityToActivities_c_typeActivityId`}
						options={typeOfActivitiesEntries}
						required
					/>

					<PRMFormik.Field
						component={PRMForm.Select}
						emptyOptionMessage="Select a Type of Activity"
						label="Tactic"
						name={`activities[${currentIndex}].r_tacticToActivities_c_tacticId`}
						options={tacticsByTypeActivity}
						required
					/>
				</PRMForm.Group>

				{
					typeOfActivityComponents[
						selectedTypeActivity?.externalReferenceCode || ''
					]
				}

				<PRMForm.Group>
					<PRMFormik.Field
						component={PRMForm.DatePicker}
						label="Start Date"
						name={`activities[${currentIndex}].startDate`}
						required
					/>

					<PRMFormik.Field
						component={PRMForm.DatePicker}
						label="End Date"
						name={`activities[${currentIndex}].endDate`}
						required
					/>
				</PRMForm.Group>
			</PRMForm.Section>
			<PRMFormik.Array
				budgets={currentActivity?.budgets}
				component={BudgetBreakdownSection}
				currentActivityIndex={currentIndex}
				expenseEntries={
					fieldEntries[LiferayPicklistName.BUDGET_EXPENSES]
				}
				name={`activities[${currentIndex}].budgets`}
				setFieldValue={setFieldValue}
			/>
			<PRMForm.Section title="Lead List">
				<PRMFormik.Field
					component={PRMForm.RadioGroup}
					items={getBooleanEntries()}
					label="Is a lead list an outcome of this activity?"
					name={`activities[${currentIndex}].leadGenerated`}
					required
					small
				/>

				<PRMFormik.Field
					component={PRMForm.InputText}
					label="Target # of Leads"
					name={`activities[${currentIndex}].targetofLeads`}
					required
				/>

				<PRMFormik.Field
					component={PRMForm.CheckboxGroup}
					items={
						fieldEntries[
							LiferayPicklistName.LEAD_FOLLOW_UP_STRATEGIES
						]
					}
					label="Lead Follow Up strategy (select all that apply)"
					name={`activities[${currentIndex}].leadFollowUpStrategies`}
					required
				/>

				<PRMFormik.Field
					component={PRMForm.InputText}
					description="(i) Please describe the follow-up plan in detail:  Do you need any assets from Liferay (i.e.  landing page, collateral, content) Will Liferay participate in the follow up? If so, please provide details"
					label="Details on Lead Follow Up. What to include (i)"
					name={`activities[${currentIndex}].detailsLeadFollowUp`}
					required
				/>
			</PRMForm.Section>
		</>
	);
};

export default Form;

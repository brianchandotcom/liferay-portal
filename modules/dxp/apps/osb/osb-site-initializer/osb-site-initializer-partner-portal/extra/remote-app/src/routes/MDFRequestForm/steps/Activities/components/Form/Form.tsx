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

import {useCallback} from 'react';

import PRMForm from '../../../../../../common/components/PRMForm';
import PRMFormik from '../../../../../../common/components/PRMFormik';
import {LiferayPicklistName} from '../../../../../../common/enums/liferayPicklistName';
import {TypeActivityExternalReferenceCode} from '../../../../../../common/enums/typeActivityExternalReferenceCode';
import MDFRequestActivity from '../../../../../../common/interfaces/mdfRequestActivity';
import getBooleanEntries from '../../../../../../common/utils/getBooleanEntries';
import BudgetBreakdownSection from './components/BudgetBreakdownSection';
import ContentMarketingFields from './components/ContentMarketingFields';
import DigitalMarketingFields from './components/DigitalMarketingFields';
import EventFields from './components/EventFields';
import MiscellaneousMarketingFields from './components/MiscellaneousMarketingFields';
import useDynamicFieldEntries from './hooks/useDynamicFieldEntries';
import useTacticsOptions from './hooks/useTacticsOptions';
import useTypeActivityOptions from './hooks/useTypeActivityOptions';

interface IProps {
	currentActivity: MDFRequestActivity;
	currentActivityIndex: number;
	setFieldValue: (
		field: string,
		value: any,
		shouldValidate?: boolean | undefined
	) => void;
}

type TypeActivityComponent = {
	[key in string]?: JSX.Element;
};

const Form = ({
	currentActivity,
	currentActivityIndex,
	setFieldValue,
}: IProps) => {
	const {fieldEntries, typeActivities} = useDynamicFieldEntries();

	const {
		onTypeActivitySelected,
		selectedTypeActivity,
		tacticsBySelectedTypeActivity,
		typeActivitiesOptions,
	} = useTypeActivityOptions(
		typeActivities,
		useCallback(
			(selectedTypeActivity) => {
				setFieldValue(
					`activities[${currentActivityIndex}].typeActivity`,
					selectedTypeActivity
				);

				setFieldValue(`activities[${currentActivityIndex}].tactic`, {});
			},
			[currentActivityIndex, setFieldValue]
		)
	);

	const {onTacticSelected, tacticsOptions} = useTacticsOptions(
		tacticsBySelectedTypeActivity,
		useCallback(
			(selectedTactic) =>
				setFieldValue(
					`activities[${currentActivityIndex}].tactic`,
					selectedTactic
				),
			[currentActivityIndex, setFieldValue]
		)
	);

	const typeActivityComponents: TypeActivityComponent = {
		[TypeActivityExternalReferenceCode.DIGITAL_MARKETING]: (
			<DigitalMarketingFields
				currentActivityIndex={currentActivityIndex}
			/>
		),
		[TypeActivityExternalReferenceCode.CONTENT_MARKETING]: (
			<ContentMarketingFields
				currentActivityIndex={currentActivityIndex}
			/>
		),
		[TypeActivityExternalReferenceCode.EVENT]: (
			<EventFields currentActivityIndex={currentActivityIndex} />
		),
		[TypeActivityExternalReferenceCode.MISCELLANEOUS_MARKETING]: (
			<MiscellaneousMarketingFields
				currentActivityIndex={currentActivityIndex}
			/>
		),
	};

	return (
		<>
			<PRMForm.Section title="Campaign Activity">
				<PRMFormik.Field
					component={PRMForm.InputText}
					label="Activity name"
					name={`activities[${currentActivityIndex}].name`}
					required
				/>

				<PRMForm.Group>
					<PRMFormik.Field
						component={PRMForm.Select}
						label="Type of Activity"
						name={`activities[${currentActivityIndex}].typeActivity`}
						onChange={onTypeActivitySelected}
						options={typeActivitiesOptions}
						required
					/>

					<PRMFormik.Field
						component={PRMForm.Select}
						emptyOptionMessage="Select a Type of Activity"
						label="Tactic"
						name={`activities[${currentActivityIndex}].tactic`}
						onChange={onTacticSelected}
						options={tacticsOptions}
						required
					/>
				</PRMForm.Group>

				{
					typeActivityComponents[
						selectedTypeActivity?.externalReferenceCode || ''
					]
				}

				<PRMForm.Group>
					<PRMFormik.Field
						component={PRMForm.DatePicker}
						label="Start Date"
						name={`activities[${currentActivityIndex}].startDate`}
						required
					/>

					<PRMFormik.Field
						component={PRMForm.DatePicker}
						label="End Date"
						name={`activities[${currentActivityIndex}].endDate`}
						required
					/>
				</PRMForm.Group>
			</PRMForm.Section>
			<PRMFormik.Array
				budgets={currentActivity?.budgets}
				component={BudgetBreakdownSection}
				currentActivityIndex={currentActivityIndex}
				expenseEntries={
					fieldEntries[LiferayPicklistName.BUDGET_EXPENSES]
				}
				name={`activities[${currentActivityIndex}].budgets`}
				setFieldValue={setFieldValue}
			/>
			<PRMForm.Section title="Lead List">
				<PRMFormik.Field
					component={PRMForm.RadioGroup}
					items={getBooleanEntries()}
					label="Is a lead list an outcome of this activity?"
					name={`activities[${currentActivityIndex}].leadGenerated`}
					required
					small
				/>

				<PRMFormik.Field
					component={PRMForm.InputText}
					label="Target # of Leads"
					name={`activities[${currentActivityIndex}].targetOfLeads`}
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
					name={`activities[${currentActivityIndex}].leadFollowUpStrategies`}
					required
				/>

				<PRMFormik.Field
					component={PRMForm.InputText}
					description="(i) Please describe the follow-up plan in detail:  Do you need any assets from Liferay (i.e.  landing page, collateral, content) Will Liferay participate in the follow up? If so, please provide details"
					label="Details on Lead Follow Up. What to include (i)"
					name={`activities[${currentActivityIndex}].detailsLeadFollowUp`}
					required
				/>
			</PRMForm.Section>
		</>
	);
};

export default Form;

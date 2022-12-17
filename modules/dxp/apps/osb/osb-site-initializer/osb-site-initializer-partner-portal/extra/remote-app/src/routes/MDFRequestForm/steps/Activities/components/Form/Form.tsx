/* eslint-disable react-hooks/exhaustive-deps */
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

import {useFormikContext} from 'formik';
import {useCallback, useEffect} from 'react';

import PRMForm from '../../../../../../common/components/PRMForm';
import PRMFormik from '../../../../../../common/components/PRMFormik';
import {TypeActivityKey} from '../../../../../../common/enums/TypeActivityKey';
import {LiferayPicklistName} from '../../../../../../common/enums/liferayPicklistName';
import MDFRequest from '../../../../../../common/interfaces/mdfRequest';
import MDFRequestActivity from '../../../../../../common/interfaces/mdfRequestActivity';
import getNewActivity from '../../utils/getNewActivity';
import BudgetBreakdownSection from './components/BudgetBreakdownSection';
import ContentMarketingFields from './components/ContentMarketingFields';
import DigitalMarketingFields from './components/DigitalMarketingFields';
import EventFields from './components/EventFields';
import LeadListSection from './components/LeadListSection';
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
	const {fieldEntries} = useDynamicFieldEntries();
	const {values} = useFormikContext<MDFRequest>();

	const {
		onTypeActivitySelected,
		selectedTypeActivity,
		tacticsBySelectedTypeActivity,
		typeActivitiesOptions,
	} = useTypeActivityOptions(
		fieldEntries[LiferayPicklistName.TYPE_OF_ACTIVITY],
		fieldEntries[LiferayPicklistName.TACTIC],
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

	const {
		onTacticSelected,
		selectedTactic,
		tacticsOptions,
	} = useTacticsOptions(
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

	useEffect(() => {
		if (values.activities[currentActivityIndex].typeActivity) {
			setFieldValue(
				`activities[${currentActivityIndex}].activityDescription`,
				getNewActivity().activityDescription
			);

			const displaySection =
				selectedTypeActivity?.value === TypeActivityKey.EVENT
					? 'true'
					: '';

			setFieldValue(
				`activities[${currentActivityIndex}].activityDescription.leadGenerated`,
				displaySection
			);
		}
	}, [
		values.activities[currentActivityIndex].typeActivity,
		values.activities[currentActivityIndex].tactic,
		setFieldValue,
	]);

	const typeActivityComponents: TypeActivityComponent = {
		[TypeActivityKey.DIGITAL_MARKETING]: (
			<DigitalMarketingFields
				currentActivityIndex={currentActivityIndex}
				tactic={selectedTactic?.label}
			/>
		),
		[TypeActivityKey.CONTENT_MARKETING]: (
			<ContentMarketingFields
				currentActivityIndex={currentActivityIndex}
			/>
		),
		[TypeActivityKey.EVENT]: (
			<EventFields
				currentActivityIndex={currentActivityIndex}
				tactic={selectedTactic?.label}
			/>
		),
		[TypeActivityKey.MISCELLANEOUS_MARKETING]: (
			<MiscellaneousMarketingFields
				currentActivityIndex={currentActivityIndex}
				tactic={selectedTactic?.label}
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
						String(selectedTypeActivity?.value) || ''
					]
				}

				<LeadListSection
					currentActivityIndex={currentActivityIndex}
					fieldEntries={fieldEntries}
					selectedTypeActivity={String(selectedTypeActivity?.value)}
				/>

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
		</>
	);
};

export default Form;

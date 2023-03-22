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

import PRMForm from '../../../../../../../../common/components/PRMForm';
import PRMFormik from '../../../../../../../../common/components/PRMFormik';
import {LiferayPicklistName} from '../../../../../../../../common/enums/liferayPicklistName';
import MDFRequest from '../../../../../../../../common/interfaces/mdfRequest';
import getBooleanEntries from '../../../../../../../../common/utils/getBooleanEntries';
import {EntryField} from '../../../../../../../../common/utils/getEntriesByListTypeDefinitions';

interface IProps {
	currentActivityIndex: number;
	fieldEntries: EntryField;
	selectedTypeActivity?: string;
}

const LeadListSection = ({
	currentActivityIndex,
	fieldEntries,
	selectedTypeActivity,
}: IProps) => {
	const {setFieldValue, values} = useFormikContext<MDFRequest>();

	const onLeadListOutcomeSelected = (
		event: React.ChangeEvent<HTMLInputElement>
	) => {
		const leadListValue = event.target.value;

		setFieldValue(
			`activities[${currentActivityIndex}].activityDescription.leadGenerated`,
			leadListValue
		);

		if (leadListValue) {
			setFieldValue(
				`activities[${currentActivityIndex}].activityDescription.targetOfLeads`,
				''
			);
			setFieldValue(
				`activities[${currentActivityIndex}].activityDescription.leadFollowUpStrategies`,
				[]
			);
			setFieldValue(
				`activities[${currentActivityIndex}].activityDescription.detailsLeadFollowUp`,
				''
			);
		}
	};

	return (
		<PRMForm.Section title="Lead List">
			{!selectedTypeActivity && (
				<PRMFormik.Field
					component={PRMForm.RadioGroup}
					items={getBooleanEntries()}
					label="Is a lead list an outcome of this activity?"
					name={`activities[${currentActivityIndex}].activityDescription.leadGenerated`}
					onChange={onLeadListOutcomeSelected}
					required
					small
				/>
			)}

			{values.activities[currentActivityIndex].activityDescription
				?.leadGenerated === 'true' && (
				<>
					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Target # of Leads"
						name={`activities[${currentActivityIndex}].activityDescription.targetOfLeads`}
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
						name={`activities[${currentActivityIndex}].activityDescription.leadFollowUpStrategies`}
						required
					/>

					<PRMFormik.Field
						component={PRMForm.InputText}
						description="(i) Please describe the follow-up plan in detail:  Do you need any assets from Liferay (i.e.  landing page, collateral, content) Will Liferay participate in the follow up? If so, please provide details"
						label="Details on Lead Follow Up. What to include (i)"
						name={`activities[${currentActivityIndex}].activityDescription.detailsLeadFollowUp`}
						required
					/>
				</>
			)}
		</PRMForm.Section>
	);
};

export default LeadListSection;

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

import ClayButton from '@clayui/button';
import {Field} from 'formik';

import PRMForm from '../../components/PRMForm';
import {listTypeDefinitionName} from '../../services/liferay/list-type-definitions/constants/listTypeDefinitionName';
import useDynamicFieldEntries from './hooks/useDynamicFieldEntries';

interface IProps {
	onCancel?(event: React.MouseEvent<HTMLButtonElement>): void;
	onContinue?(event: React.MouseEvent<HTMLButtonElement>): void;
}

const Goals = ({onCancel, onContinue}: IProps) => {
	const {
		additionalOptionsEntries,
		companiesEntries,
		fieldEntries,
	} = useDynamicFieldEntries();

	return (
		<PRMForm name="Goals" title="Campaign Information">
			<PRMForm.Section title="Partner">
				<PRMForm.Group>
					<Field
						component={PRMForm.Select}
						label="Company Name"
						name="r_company_accountEntryId"
						options={companiesEntries}
						required
					/>

					<Field
						component={PRMForm.Select}
						label="Country"
						name="country"
						options={fieldEntries[listTypeDefinitionName.COUNTRIES]}
						required
					/>
				</PRMForm.Group>
			</PRMForm.Section>

			<PRMForm.Section title="Campaign">
				<Field
					component={PRMForm.InputText}
					label="Provide a name and short description of the overall campaign"
					name="overallCampaign"
					required
				/>

				<div className="border border-neutral-3 mb-4 p-3 rounded-lg">
					<Field
						component={PRMForm.CheckboxGroup}
						items={
							fieldEntries[
								listTypeDefinitionName
									.LIFERAY_BUSINESS_SALES_GOALS
							]
						}
						label="Select Liferay business/sales goals this Campaign serves (choose up to three)"
						name="liferayBusinessSalesGoals"
						required
					/>
				</div>
			</PRMForm.Section>

			<PRMForm.Section title="Target Market">
				<div className="border border-neutral-3 mb-4 p-3 rounded-lg">
					<Field
						component={PRMForm.CheckboxGroup}
						items={
							fieldEntries[listTypeDefinitionName.TARGETS_MARKET]
						}
						label="Please select the target market(s) for this campaign (choose up to three)"
						name="targetsMarket"
						required
					/>
				</div>

				<Field
					component={PRMForm.RadioGroup}
					items={additionalOptionsEntries}
					label="Additional options? Choose one if applicable"
					name="r_additionalOption_mdfRequest"
				/>

				<div className="border border-neutral-3 mb-4 p-3 rounded-lg">
					<Field
						component={PRMForm.CheckboxGroup}
						items={
							fieldEntries[
								listTypeDefinitionName.TARGETS_AUDIENCE_ROLE
							]
						}
						label="Choose your target audience/role (Select all that apply)"
						name="targetsAudienceRole"
						required
					/>
				</div>
			</PRMForm.Section>

			<PRMForm.Footer>
				<div className="mr-auto pl-0 py-3">
					<ClayButton
						className="pl-0"
						displayType={null}
						type="submit"
					>
						Save as Draft
					</ClayButton>
				</div>

				<div className="p-2">
					<ClayButton
						className="mr-4"
						displayType="secondary"
						onClick={onCancel}
					>
						Cancel
					</ClayButton>

					<ClayButton onClick={onContinue}>Continue</ClayButton>
				</div>
			</PRMForm.Footer>
		</PRMForm>
	);
};

export default Goals;

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

import Button from '@clayui/button';
import {useFormikContext} from 'formik';
import {useCallback} from 'react';

import PRMForm from '../../../../common/components/PRMForm';
import PRMFormik from '../../../../common/components/PRMFormik';
import PRMFormikPageProps from '../../../../common/components/PRMFormik/interfaces/prmFormikPageProps';
import {LiferayPicklistName} from '../../../../common/enums/liferayPicklistName';
import useCompanyOptions from '../../../../common/hooks/useCompanyOptions';
import DealRegistration from '../../../../common/interfaces/dealRegistration';
import getPicklistOptions from '../../../../common/utils/getPicklistOptions';
import {StepType} from '../../enums/stepType';
import useDynamicFieldEntries from '../../hooks/useDynamicFieldEntries';
import DealRegistrationStepProps from '../../interfaces/dealRegistrationStepProps';

const General = ({
	onCancel,
	onContinue,
	onSaveAsDraft,
}: PRMFormikPageProps & DealRegistrationStepProps) => {
	const {
		isSubmitting,
		setFieldValue,
		values,
		...formikHelpers
	} = useFormikContext<DealRegistration>();

	const {companiesEntries, fieldEntries} = useDynamicFieldEntries();

	const {companyOptions, onCompanySelected} = useCompanyOptions(
		companiesEntries,
		useCallback(
			(country, company) => {
				setFieldValue('partnerAccount', company);
			},
			[setFieldValue]
		)
	);

	const {
		onSelected: onCountrySelected,
		options: countryOptions,
	} = getPicklistOptions(
		fieldEntries[LiferayPicklistName.REGIONS],
		(selected) => setFieldValue('prospect.country', selected)
	);

	const {
		onSelected: onIndustrySelected,
		options: industryOptions,
	} = getPicklistOptions(
		fieldEntries[LiferayPicklistName.INDUSTRIES],
		(selected) => setFieldValue('prospect.industry', selected)
	);

	const {
		onSelected: onDepartmentSelected,
		options: departmentOptions,
	} = getPicklistOptions(
		fieldEntries[LiferayPicklistName.DEPARTMENTS],
		(selected) => setFieldValue('primaryProspect.department', selected)
	);

	const {
		onSelected: onJobRoleSelected,
		options: jobRoleOptions,
	} = getPicklistOptions(
		fieldEntries[LiferayPicklistName.JOB_ROLES],
		(selected) => setFieldValue('primaryProspect.jobRole', selected)
	);

	const {
		onSelected: onStateSelected,
		options: stateOptions,
	} = getPicklistOptions(
		fieldEntries[LiferayPicklistName.STATES],
		(selected) => setFieldValue('prospect.state', selected)
	);

	return (
		<PRMForm name="general" title="Deal Registration">
			<PRMForm.Section title="General Details">
				<PRMForm.Group>
					<PRMFormik.Field
						component={PRMForm.Select}
						label="Partner Account Name"
						name="partnerAccount"
						onChange={onCompanySelected}
						options={companyOptions}
						required
					/>

					<PRMFormik.Field
						component={PRMForm.Select}
						label="MDF Activity Associated"
						name="mdfActivityAssociated"
					/>
				</PRMForm.Group>
			</PRMForm.Section>

			<PRMForm.Section title="Prospect Information">
				<PRMFormik.Field
					component={PRMForm.InputText}
					label="Account Name"
					name="prospect.accountName"
					required
				/>

				<PRMFormik.Field
					component={PRMForm.Select}
					label="Industry"
					name="prospect.industry"
					onChange={onIndustrySelected}
					options={industryOptions}
					required
				/>

				<PRMFormik.Field
					component={PRMForm.InputText}
					label="Address"
					name="prospect.address"
					required
				/>

				<PRMForm.Group>
					<PRMFormik.Field
						component={PRMForm.InputText}
						label="City"
						name="prospect.city"
						required
					/>

					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Postal Code"
						name="prospect.postalCode"
						required
					/>
				</PRMForm.Group>

				<PRMForm.Group>
					<PRMFormik.Field
						component={PRMForm.Select}
						label="Country"
						name="prospect.country"
						onChange={onCountrySelected}
						options={countryOptions}
						required
					/>

					{values.prospect?.country.name === 'US' && (
						<PRMFormik.Field
							component={PRMForm.Select}
							label="State"
							name="prospect.state"
							onChange={onStateSelected}
							options={stateOptions}
							required
						/>
					)}
				</PRMForm.Group>

				<PRMForm.Section title="Primary Prospect Contact">
					<PRMForm.Group>
						<PRMFormik.Field
							component={PRMForm.InputText}
							label="First Name"
							name="primaryProspect.firstName"
							required
						/>

						<PRMFormik.Field
							component={PRMForm.InputText}
							label="Last Name"
							name="primaryProspect.lastName"
							required
						/>
					</PRMForm.Group>

					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Email Address"
						name="primaryProspect.emailAddress"
						required
					/>

					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Phone"
						name="primaryProspect.phone"
						required
					/>

					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Business Unit"
						name="primaryProspect.businessUnit"
						required
					/>

					<PRMFormik.Field
						component={PRMForm.Select}
						label="Department"
						name="primaryProspect.department"
						onChange={onDepartmentSelected}
						options={departmentOptions}
						required
					/>

					<PRMFormik.Field
						component={PRMForm.Select}
						label="Job Role"
						name="primaryProspect.jobRole"
						onChange={onJobRoleSelected}
						options={jobRoleOptions}
						required
					/>
				</PRMForm.Section>

				<PRMForm.Section title="Additional Contacts">
					<PRMForm.Group>
						<PRMFormik.Field
							component={PRMForm.InputText}
							label="First Name"
							name="additionalContact.firstName"
						/>

						<PRMFormik.Field
							component={PRMForm.InputText}
							label="Last Name"
							name="additionalContact.lastName"
						/>
					</PRMForm.Group>

					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Email Address"
						name="additionalContact.emailAddress"
					/>
				</PRMForm.Section>

				<PRMForm.Section title="Deal Information">
					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Additional Information about the Opportunity"
						name="additionalInformationAboutTheOpportunity"
					/>
				</PRMForm.Section>

				<PRMForm.Section title="Project Information">
					<PRMFormik.Field
						component={PRMForm.CheckboxGroup}
						items={
							fieldEntries[
								LiferayPicklistName.PROJECT_INFORMATIONS
							]
						}
						label="Project Need (Select all that apply)"
						name="projectNeed"
						required
					/>

					<PRMFormik.Field
						component={PRMForm.CheckboxGroup}
						items={
							fieldEntries[LiferayPicklistName.PROJECT_CATEGORIES]
						}
						label="Project Solution Categories (Select all that apply)"
						name="projectCategories"
						required
					/>
				</PRMForm.Section>

				<PRMForm.Section title="Business Objectives">
					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Project Timeline"
						name="projectTimeline"
						required
					/>
				</PRMForm.Section>
			</PRMForm.Section>

			<PRMForm.Footer>
				<div className="d-flex mr-auto">
					<Button
						disabled={isSubmitting}
						displayType={null}
						onClick={() => onSaveAsDraft?.(values, formikHelpers)}
					>
						Save as Draft
					</Button>

					<Button
						className="mr-4"
						displayType="secondary"
						onClick={onCancel}
					>
						Cancel
					</Button>
				</div>

				<Button
					onClick={() => onContinue?.(formikHelpers, StepType.REVIEW)}
				>
					Proceed
				</Button>
			</PRMForm.Footer>
		</PRMForm>
	);
};

export default General;

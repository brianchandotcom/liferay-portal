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
import DealRegistration from '../../../../common/interfaces/dealRegistration';
import useCompanyOptions from '../../../MDFRequestForm/steps/Goals/hooks/useCompanyOptions';
import getPicklistOptions from '../../../MDFRequestForm/utils/getPicklistOptions';
import useDynamicFieldEntries from '../../hooks/useDynamicFieldEntries';
import DealRegistrationStepProps from '../../interfaces/dealRegistrationStepProps';

const General = ({
	onCancel,
}: PRMFormikPageProps & DealRegistrationStepProps<DealRegistration>) => {
	const {isSubmitting, setFieldValue, values} = useFormikContext<
		DealRegistration
	>();

	const {companiesEntries, fieldEntries} = useDynamicFieldEntries();

	const {companyOptions, onCompanySelected} = useCompanyOptions(
		companiesEntries,
		useCallback(
			(country, company) => {
				setFieldValue('partnerAccountName', company);
				setFieldValue('prospect.country', country);
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
		(selected) => setFieldValue('prospect.department', selected)
	);

	const {
		onSelected: onJobRoleSelected,
		options: jobRoleOptions,
	} = getPicklistOptions(
		fieldEntries[LiferayPicklistName.JOB_ROLES],
		(selected) => setFieldValue('prospect.jobRole', selected)
	);

	return (
		<PRMForm name="general" title="Deal Registration">
			<PRMForm.Section title="General Details">
				<PRMForm.Group>
					<PRMFormik.Field
						component={PRMForm.Select}
						label="Partner Account Name"
						name="partnerAccountName"
						onChange={onCompanySelected}
						options={companyOptions}
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
				/>

				<PRMFormik.Field
					component={PRMForm.Select}
					label="Industry"
					name="prospect.industry"
					onChange={onIndustrySelected}
					options={industryOptions}
				/>

				<PRMFormik.Field
					component={PRMForm.InputText}
					label="Address"
					name="prospect.address"
				/>

				<PRMForm.Group>
					<PRMFormik.Field
						component={PRMForm.InputText}
						label="City"
						name="prospect.city"
					/>

					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Postal Code"
						name="prospect.postalCode"
					/>
				</PRMForm.Group>

				<PRMForm.Group>
					<PRMFormik.Field
						component={PRMForm.Select}
						label="Country"
						name="prospect.country"
						onChange={onCountrySelected}
						options={countryOptions}
					/>

					{values.prospect?.country?.name === 'US' && (
						<PRMFormik.Field
							component={PRMForm.Select}
							label="State"
							name="prospect.state"
						/>
					)}
				</PRMForm.Group>

				<PRMForm.Section title="Primary Prospect Contact">
					<PRMForm.Group>
						<PRMFormik.Field
							component={PRMForm.InputText}
							label="First Name"
							name="primaryProspect.firstName"
						/>

						<PRMFormik.Field
							component={PRMForm.InputText}
							label="Last Name"
							name="primaryProspect.lastName"
						/>
					</PRMForm.Group>

					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Email Address"
						name="primaryProspect.emailAddress"
					/>

					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Phone"
						name="primaryProspect.phone"
					/>

					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Business Unit"
						name="primaryProspect.businessUnit"
					/>

					<PRMFormik.Field
						component={PRMForm.Select}
						label="Department"
						name="prospect.department"
						onChange={onDepartmentSelected}
						options={departmentOptions}
					/>

					<PRMFormik.Field
						component={PRMForm.Select}
						label="Job Role"
						name="prospect.jobRole"
						onChange={onJobRoleSelected}
						options={jobRoleOptions}
					/>
				</PRMForm.Section>

				<PRMForm.Section title="Additional Contacts">
					<PRMForm.Group>
						<PRMFormik.Field
							component={PRMForm.InputText}
							label="First Name"
							name="aditionalContact.firstName"
						/>

						<PRMFormik.Field
							component={PRMForm.InputText}
							label="Last Name"
							name="aditionalContact.lastName"
						/>
					</PRMForm.Group>

					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Email Address"
						name="aditionalContact.emailAddress"
					/>
				</PRMForm.Section>

				<PRMForm.Section title="Deal Information">
					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Additional Information about the Opportunity"
						name="dealInformation.additionalInformationAboutTheOpportunity"
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
						name="projectInformation.projectNeed"
					/>
				</PRMForm.Section>

				<PRMForm.Section title="Project Solution Categories (Select all that apply)">
					<PRMFormik.Field
						component={PRMForm.CheckboxGroup}
						items={
							fieldEntries[LiferayPicklistName.PROJECT_CATEGORIES]
						}
						name="projectSolution.categories"
					/>
				</PRMForm.Section>

				<PRMForm.Section title="Business Objectives">
					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Project Timeline"
						name="businessObjective.projectTimeline"
					/>
				</PRMForm.Section>
			</PRMForm.Section>

			<PRMForm.Footer>
				<div className="d-flex mr-auto">
					<Button disabled={isSubmitting} displayType={null}>
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

				<Button>Proceed</Button>
			</PRMForm.Footer>
		</PRMForm>
	);
};

export default General;

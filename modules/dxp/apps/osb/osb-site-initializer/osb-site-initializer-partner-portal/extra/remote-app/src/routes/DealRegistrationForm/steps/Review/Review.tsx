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
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useFormikContext} from 'formik';

import PRMForm from '../../../../common/components/PRMForm';
import PRMFormikPageProps from '../../../../common/components/PRMFormik/interfaces/prmFormikPageProps';
import Table from '../../../../common/components/Table';
import DealRegistration from '../../../../common/interfaces/dealRegistration';
import {StepType} from '../../enums/stepType';
import DealRegistrationStepProps from '../../interfaces/dealRegistrationStepProps';

const Review = ({
	onCancel,
	onPrevious,
}: PRMFormikPageProps & DealRegistrationStepProps) => {
	const {isSubmitting, status, values} = useFormikContext<DealRegistration>();

	return (
		<>
			<PRMForm
				className="bg-neutral-0"
				name="review"
				title="Review Deal Registration"
			>
				<Table
					className="bg-brand-primary-lighten-6 border-top table-striped"
					columns={[
						{
							columnKey: 'title',
							label: 'General Details',
						},
						{
							columnKey: 'value',
							label: '',
						},
					]}
					rows={[
						{
							title: 'Partner Account Name',
							value: values.partnerAccount.name,
						},
						{
							title: 'MDF Activity Associated',
							value: values.mdfActivityAssociated.name,
						},
					]}
				/>

				<Table
					className="bg-brand-primary-lighten-6 border-top table-striped"
					columns={[
						{
							columnKey: 'title',
							label: 'Prospect Information',
						},
						{
							columnKey: 'value',
							label: '',
						},
					]}
					rows={[
						{
							title: 'Account Name',
							value: values.prospect?.accountName,
						},
						{
							title: 'Industry',
							value: values.prospect?.industry?.name,
						},
						{
							title: 'Address',
							value: values.prospect?.address,
						},
						{
							title: 'City',
							value: values.prospect?.city,
						},
						{
							title: 'Postal Code',
							value: values.prospect?.postalCode,
						},
						{
							title: 'State',
							value: values.prospect?.state?.name,
						},
						{
							title: 'Country',
							value: values.prospect?.country?.name,
						},
					]}
				/>

				<Table
					className="bg-brand-primary-lighten-6 border-top table-striped"
					columns={[
						{
							columnKey: 'title',
							label: 'Primary Prospect Contact',
						},
						{
							columnKey: 'value',
							label: '',
						},
					]}
					rows={[
						{
							title: 'First Name',
							value: values.primaryProspect?.firstName,
						},
						{
							title: 'Last Name',
							value: values.primaryProspect?.lastName,
						},
						{
							title: 'Email Address',
							value: values.primaryProspect?.emailAddress,
						},
						{
							title: 'Phone',
							value: values.primaryProspect?.phone,
						},
						{
							title: 'Department',
							value: values.primaryProspect?.department?.name,
						},
						{
							title: 'Job Role',
							value: values.primaryProspect?.jobRole?.name,
						},
					]}
				/>

				<Table
					className="bg-brand-primary-lighten-6 border-top table-striped"
					columns={[
						{
							columnKey: 'title',
							label: 'Additional Contacts',
						},
						{
							columnKey: 'value',
							label: '',
						},
					]}
					rows={[
						{
							title: 'First Name',
							value: values.additionalContact?.firstName,
						},
						{
							title: 'Last Name',
							value: values.additionalContact?.lastName,
						},
						{
							title: 'Email Address',
							value: values.additionalContact?.emailAddress,
						},
					]}
				/>

				<Table
					className="bg-brand-primary-lighten-6 border-top table-striped"
					columns={[
						{
							columnKey: 'title',
							label: 'Deal Information',
						},
						{
							columnKey: 'value',
							label: '',
						},
					]}
					rows={[
						{
							title: 'Additional Information',
							value:
								values.additionalInformationAboutTheOpportunity,
						},
					]}
				/>

				<Table
					className="bg-brand-primary-lighten-6 border-top table-striped"
					columns={[
						{
							columnKey: 'title',
							label: 'Project Information',
						},
						{
							columnKey: 'value',
							label: '',
						},
					]}
					rows={[
						{
							title: 'Project Need',
							value: values.projectNeed.join(', '),
						},
						{
							title: 'Project Solution Categories',
							value: values.projectCategories.join(', '),
						},
					]}
				/>

				<Table
					className="bg-brand-primary-lighten-6 border-top table-striped"
					columns={[
						{
							columnKey: 'title',
							label: 'Business Objectives',
						},
						{
							columnKey: 'value',
							label: '',
						},
					]}
					rows={[
						{
							title: 'Project Timeline',
							value: values.projectTimeline,
						},
					]}
				/>

				<PRMForm.Footer>
					<div className="d-flex justify-content-between mr-auto">
						<Button
							className="mr-4"
							disabled={status || isSubmitting}
							displayType={null}
							onClick={() => onPrevious?.(StepType.GENERAL)}
						>
							Back
						</Button>
					</div>

					<div className="d-flex justify-content-between px-2 px-md-0">
						<Button
							className="mr-4"
							disabled={status || isSubmitting}
							displayType="secondary"
							onClick={onCancel}
						>
							Cancel
						</Button>

						<Button
							className="inline-item inline-item-after"
							disabled={status || isSubmitting}
							type="submit"
						>
							Proceed
							{isSubmitting && (
								<ClayLoadingIndicator className="inline-item inline-item-after ml-2" />
							)}
						</Button>
					</div>
				</PRMForm.Footer>
			</PRMForm>
		</>
	);
};

export default Review;

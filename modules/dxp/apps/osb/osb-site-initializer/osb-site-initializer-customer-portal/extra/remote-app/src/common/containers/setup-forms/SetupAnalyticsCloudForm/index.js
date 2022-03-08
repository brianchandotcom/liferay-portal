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

import ClayForm from '@clayui/form';
import {Formik} from 'formik';
import {useEffect, useState} from 'react';
import {isValidEmail} from '../../../../common/utils/validations.form';
import {Button, Input, Select} from '../../../components';
import Layout from '../Layout';

const SetupAnalyticsCloudPage = ({errors, handlePage, leftButton, touched}) => {
	const [baseButtonDisabled, setBaseButtonDisabled] = useState(true);

	useEffect(() => {
		const hasTouched = !Object.keys(touched).length;
		const hasError = Object.keys(errors).length;

		setBaseButtonDisabled(hasTouched || hasError);
	}, [touched, errors]);

	return (
		<Layout
			className="pt-1 px-3"
			footerProps={{
				leftButton: (
					<Button
						borderless
						className="text-neutral-10"
						disabled={baseButtonDisabled}
						onClick={() => handlePage()}
					>
						{leftButton}
					</Button>
				),
				middleButton: <Button displayType="primary">Submit</Button>,
			}}
			headerProps={{
				helper:
					'We’ll need a few details to finish creating your Analytics Cloud Workspace',
				title: 'Set up Analytics Cloud',
			}}
		>
			<ClayForm.Group className="pb-1">
				<Input
					groupStyle="pb-1"
					helper="This user will create and manage the Analytics Cloud Workspace and must have a liferay.com account. The owner Email can be updated vis Support ticket if needed."
					label="Owner Email"
					name="analytics.ownerEmail"
					placeholder="user@company.com"
					required
					type="email"
					validations={[(value) => isValidEmail(value)]}
				/>

				<Input
					groupStyle="pb-1"
					helper="Lowercase letters and numbers only. Project IDs cannot be changed."
					label="Workspace Name"
					name="analytics.workspaceName"
					placeholder="superbank1"
					required
					type="text"
				/>

				<Select
					groupStyle="pb-1"
					helper="Select a server location for your data to be stored."
					label="Data Center Location"
					name="analytics.dataCenterLocation"
					options={['Name', 'Type', 'Age']}
					required
				/>

				<Input
					groupStyle="pb-1"
					helper="Please note that the friendly URL cannot be changed once added."
					label="Workspace Friendly URL"
					name="analytics.workspaceURL"
					placeholder="/myurl"
					type="text"
				/>

				<Input
					groupStyle="pb-1"
					helper="Anyone with an email address at the provided domains can request access to your Workspace. If multiple, separate domains by commas."
					label="Allowed Email Domains"
					name="analytics.allowedEmailDomains"
					placeholder="@mycompany.com"
					type="email"
				/>

				<Input
					groupStyle="pb-1"
					helper="Enter the timezone to be used for all data reporting in your Workspace."
					label="Time Zone"
					name="analytics.timeZone"
					placeholder="UTC-04:00"
					type="text"
				/>

				<Input
					groupStyle="pb-1"
					helper="This user will be the recepient of any high priority communications."
					label="Incident Report Contact"
					name="analytics.incidentReportContact"
					placeholder="user@company.com"
					required
					type="email"
				/>
			</ClayForm.Group>

			<Button
				className="btn-outline-primary ml-3 my-2 rounded-xs"
				prependIcon="plus"
				small
			>
				Add Incident Report Contact
			</Button>
		</Layout>
	);
};

const SetupAnalyticsCloudForm = (props) => {
	return (
		<Formik
			initialValues={{
				analytics: {
					allowedEmailDomains: '',
					dataCenterLocation: '',
					incidentReportContact: '',
					ownerEmail: '',
					timeZone: '',
					workspaceName: '',
					workspaceURL: '',
				},
			}}
			validateOnChange
		>
			{(formikProps) => (
				<SetupAnalyticsCloudPage {...props} {...formikProps} />
			)}
		</Formik>
	);
};

export default SetupAnalyticsCloudForm;

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

import {useMutation, useQuery} from '@apollo/client';
import ClayForm from '@clayui/form';
import {Formik} from 'formik';
import {useEffect, useMemo, useState} from 'react';
import {
	addSetupDXPCloud,
	getSetupDXPCloudInfo,
} from '../../../../common/services/liferay/graphql/queries';
import {PARAMS_KEYS} from '../../../../common/services/liferay/search-params';
import {getLiferaySiteName} from '../../../../common/services/liferay/utils';
import {API_BASE_URL} from '../../../../common/utils';
import {isLowercaseAndNumbers} from '../../../../common/utils/validations.form';
import {getInitialDxpAdmin} from '../../../utils/constants';
import BaseButton from '../../BaseButton';
import Input from '../../Input';
import Select from '../../Select';
import AdminInputs from '../components/AdminInputs';
import Layout from '../components/Layout';

const SetupDXPCloudPage = ({
	errors,
	handlePage,
	leftButton,
	project,
	setFieldValue,
	touched,
	values,
}) => {
	const [baseButtonDisabled, setBaseButtonDisabled] = useState(true);

	const {data} = useQuery(getSetupDXPCloudInfo, {
		variables: {
			accountSubscriptionsFilter: `(accountKey eq '${project.accountKey}') and (hasDisasterDataCenterRegion eq true)`,
		},
	});

	const dXPCDataCenterRegions = useMemo(
		() =>
			data?.c?.dXPCDataCenterRegions?.items.map(
				({dxpcDataCenterRegionId, name}) => ({
					label: name,
					value: dxpcDataCenterRegionId,
				})
			) || [],
		[data]
	);

	const hasDisasterRecovery = !!data?.c?.accountSubscriptions?.items?.length;

	useEffect(() => {
		if (dXPCDataCenterRegions.length) {
			setFieldValue('dxp.dataCenterRegion', dXPCDataCenterRegions[0]);

			if (hasDisasterRecovery) {
				setFieldValue(
					'dxp.disasterDataCenterRegion',
					dXPCDataCenterRegions[0]
				);
			}
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [dXPCDataCenterRegions, hasDisasterRecovery]);

	const handleSkip = () => {
		window.location.href = `${API_BASE_URL}/${getLiferaySiteName()}/overview?${
			PARAMS_KEYS.PROJECT_APPLICATION_EXTERNAL_REFERENCE_CODE
		}=${project.accountKey}`;
	};

	useEffect(() => {
		const hasTouched = !Object.keys(touched).length;
		const hasError = Object.keys(errors).length;

		setBaseButtonDisabled(hasTouched || hasError);
	}, [touched, errors]);

	const [sendEmailData, {called, error}] = useMutation(addSetupDXPCloud);

	const sendEmail = () => {
		const dxp = values?.dxp;

		if (!called && dxp) {
			sendEmailData({
				variables: {
					SetupDXPCloud: {
						admins: JSON.stringify(dxp.admins),
						dataCenterRegion: JSON.stringify(dxp.dataCenterRegion),
						disasterDataCenterRegion: JSON.stringify(
							dxp.disasterDataCenterRegion
						),
						projectId: JSON.stringify(dxp.projectId),
					},
					scopeKey: Liferay.ThemeDisplay.getScopeGroupId(),
				},
			});

			if (!error) {
				handlePage();
			}
		}
	};

	return (
		<Layout
			className="pt-1 px-3"
			footerProps={{
				leftButton: (
					<BaseButton borderless onClick={handleSkip}>
						{leftButton}
					</BaseButton>
				),
				middleButton: (
					<BaseButton
						disabled={baseButtonDisabled}
						displayType="primary"
						onClick={() => sendEmail()}
					>
						Submit
					</BaseButton>
				),
			}}
			headerProps={{
				helper:
					'We’ll need a few details to finish building your DXP environment(s).',
				title: 'Set up DXP Cloud',
			}}
		>
			<div className="d-flex justify-content-between mb-2 pb-1 pl-3">
				<div className="mr-4 pr-2">
					<label>Project Name</label>

					<p className="dxp-cloud-project-name text-neutral-6 text-paragraph-lg">
						<strong>
							{project.name.length > 71
								? project.name.substring(0, 71) + '...'
								: project.name}
						</strong>
					</p>
				</div>

				<div className="flex-fill">
					<label>Liferay DXP Version</label>

					<p className="text-neutral-6 text-paragraph-lg">
						<strong>{project.dxpVersion}</strong>
					</p>
				</div>
			</div>

			<ClayForm.Group className="mb-0">
				<ClayForm.Group className="mb-0 pb-1">
					<Input
						groupStyle="pb-1"
						helper="Lowercase letters and numbers only. The Project ID cannot be changed."
						label="Project ID"
						name="dxp.projectId"
						required
						type="text"
						validations={[(value) => isLowercaseAndNumbers(value)]}
					/>

					<Select
						groupStyle="mb-0"
						label="Primary Data Center Region"
						name="dxp.dataCenterRegion"
						options={dXPCDataCenterRegions}
						required
					/>

					{!!hasDisasterRecovery && (
						<Select
							groupStyle="mb-0 pt-2"
							label="Disaster Recovery Data Center Region"
							name="dxp.disasterDataCenterRegion"
							options={dXPCDataCenterRegions}
							required
						/>
					)}
				</ClayForm.Group>

				{values.dxp.admins.map((admin, index) => (
					<AdminInputs admin={admin} id={index} key={index} />
				))}
			</ClayForm.Group>

			<BaseButton
				borderless
				className="ml-3 my-2 text-brand-primary"
				onClick={() => {
					setFieldValue('dxp.admins', [
						...values.dxp.admins,
						getInitialDxpAdmin(),
					]);
					setBaseButtonDisabled(true);
				}}
				prependIcon="plus"
				small
			>
				Add Another Admin
			</BaseButton>
		</Layout>
	);
};

const SetupDXPCloud = (props) => {
	return (
		<Formik
			initialValues={{
				dxp: {
					admins: [getInitialDxpAdmin()],
					dataCenterRegion: {},
					disasterDataCenterRegion: {},
					projectId: '',
				},
			}}
			validateOnChange
		>
			{(formikProps) => <SetupDXPCloudPage {...props} {...formikProps} />}
		</Formik>
	);
};

export default SetupDXPCloud;

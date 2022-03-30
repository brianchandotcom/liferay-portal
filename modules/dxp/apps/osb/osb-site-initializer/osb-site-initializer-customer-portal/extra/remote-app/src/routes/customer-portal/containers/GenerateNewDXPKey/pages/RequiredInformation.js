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

import ClayIcon from '@clayui/icon';
import {FieldArray, Formik} from 'formik';
import {useEffect, useState} from 'react';
import {Link, useNavigate} from 'react-router-dom';
import {Button, Input} from '../../../../../common/components';
import Layout from '../../../../../common/containers/setup-forms/Layout';
import {createNewGenerateKey} from '../../../../../common/services/liferay/rest/raysource/LicenseKeys';
import getInitialGenerateNewDXPKey from '../../../../../common/utils/constants/getInitialGenerateNewDXPKey';
import GenerateCardLayout from '../GenerateCardLayout';
import KeyInputs from '../KeyInputs';
import KeySelect from '../KeySelect';

const RequiredInformation = ({
	accountKey,
	errors,
	infoSelectedKey,
	licenseKeyDownloadURL,
	sessionId,
	setStep,
	touched,
	urlPreviousPage,
	values,
}) => {
	const [baseButtonDisabled, setBaseButtonDisabled] = useState(true);
	const [addButtonDisabled, setAddButtonDisabled] = useState(false);
	const [availableKeys, setAvailableKeys] = useState(1);
	const navigate = useNavigate();

	const hasTouched = !Object.keys(touched).length;
	const hasError = Object.keys(errors).length;

	const avaliableKeysMaximumCount =
		infoSelectedKey.selectedSubscription?.quantity;
	const usedKeysCount =
		infoSelectedKey.selectedSubscription?.provisionedCount;

	useEffect(() => {
		const newUsedKeys = usedKeysCount + +values?.keys?.length;
		const hasFilledAtLeastOneField = values?.keys?.every((key) => {
			const fieldValues = Object.values(key).filter(Boolean);

			return fieldValues.length > 0;
		});

		const verificationDisabledType = infoSelectedKey.hasNotPermanentLicence
			? !values.name || !values.maxClusterNodes
			: !hasFilledAtLeastOneField || hasError;

		setBaseButtonDisabled(verificationDisabledType);
		setAddButtonDisabled(newUsedKeys === avaliableKeysMaximumCount);
	}, [
		avaliableKeysMaximumCount,
		hasError,
		infoSelectedKey.hasNotPermanentLicence,
		usedKeysCount,
		values?.keys,
		values.maxClusterNodes,
		values.name,
	]);

	const submitKey = async () => {
		const productName = `${infoSelectedKey?.productType} ${infoSelectedKey?.licenseEntryType}`;

		const licenseKey = {
			accountKey,
			active: true,
			description: values?.description,
			expirationDate: infoSelectedKey?.selectedSubscription.endDate,
			licenseEntryType: 'production',
			maxClusterNodes: values?.maxClusterNodes || 0,
			name: values?.name,
			productKey: infoSelectedKey?.selectedSubscription.productKey,
			productName,
			productPurchaseKey:
				infoSelectedKey?.selectedSubscription.productPurchaseKey,
			productVersion: infoSelectedKey?.productVersion,
			sizing: `Sizing ${infoSelectedKey?.selectedSubscription.instanceSize}`,
			startDate: infoSelectedKey?.selectedSubscription.startDate,
		};

		if (infoSelectedKey.hasNotPermanentLicence) {
			await createNewGenerateKey(
				accountKey,
				licenseKeyDownloadURL,
				sessionId,
				licenseKey
			);
		}
		else {
			await Promise.all(
				values?.keys?.map(({hostName, ipAddresses, macAddresses}) => {
					licenseKey.macAddresses = macAddresses.replace('\n', ',');
					licenseKey.hostName = hostName.replace('\n', ',');
					licenseKey.ipAddresses = ipAddresses.replace('\n', ',');

					return createNewGenerateKey(
						accountKey,
						licenseKeyDownloadURL,
						sessionId,
						licenseKey
					);
				})
			);
		}

		navigate(urlPreviousPage, {state: {newKeyGeneratedAlert: true}});
	};

	return (
		<div className="d-flex justify-content-end">
			<Layout
				footerProps={{
					footerClass: 'mx-5 mb-2',
					leftButton: (
						<Link to={urlPreviousPage}>
							<Button
								className="btn btn-borderless btn-style-neutral"
								displayType="secondary"
							>
								Cancel
							</Button>
						</Link>
					),
					rightButton: (
						<div>
							<Button
								className="btn btn-secondary mr-3"
								displayType="secundary"
								onClick={() => setStep(0)}
							>
								Previous
							</Button>

							<Button
								disabled={baseButtonDisabled}
								displayType="primary"
								onClick={() => submitKey()}
							>
								{infoSelectedKey.hasNotPermanentLicence
									? `Generate Cluster (${values.maxClusterNodes} Keys)`
									: `Generate ${availableKeys} Key${
											availableKeys > 1 ? 's' : ''
									  }`}
							</Button>
						</div>
					),
				}}
				headerProps={{
					headerClass: 'ml-5 my-4',
					helper:
						'Fill out the information required to generate the activation key',
					title: 'Generate Activation Key(s)',
				}}
				layoutType="cp-required-info"
			>
				<FieldArray
					name="keys"
					render={({pop, push}) => (
						<>
							<div className="px-6">
								<h4>Environment Details</h4>

								<div className="dropdown-divider mb-4 mt-2"></div>

								<div className="mb-2">
									<div className="cp-input-generate-label">
										<Input
											label="Environment Name"
											name="name"
											required
											type="text"
										/>
									</div>
								</div>

								<div className="mb-2">
									<div className="cp-input-generate-label">
										<Input
											label="Description"
											name="description"
											type="text"
										/>
									</div>
								</div>
							</div>

							{!infoSelectedKey.hasNotPermanentLicence ? (
								<div className="px-6">
									<h4>Activation Key Server Details</h4>

									<div className="dropdown-divider mb-4 mt-2"></div>

									{values?.keys?.map((_, index) => (
										<KeyInputs id={index} key={index} />
									))}

									{values?.keys?.length > 1 && (
										<Button
											className="btn btn-secondary mb-3 mr-3 mt-4 py-2"
											displayType="secundary"
											onClick={() => {
												pop();
												setAvailableKeys(
													(previousAdmins) =>
														previousAdmins - 1
												);
												setBaseButtonDisabled(
													hasTouched || hasError
												);
											}}
										>
											<ClayIcon
												className="cp-button-icon-plus mr-2"
												symbol="hr"
											/>
											Remove Activation Key
										</Button>
									)}

									<Button
										className="btn btn-secondary mb-3 mt-4 py-2"
										disabled={addButtonDisabled}
										displayType="secundary"
										onClick={() => {
											push(getInitialGenerateNewDXPKey());

											setAvailableKeys(
												(
													previousAvailableAdminsRoles
												) =>
													previousAvailableAdminsRoles +
													1
											);
										}}
									>
										<ClayIcon
											className="cp-button-icon-plus mr-2"
											symbol="plus"
										/>
										Add Activation Key
									</Button>

									<div className="dropdown-divider"></div>
								</div>
							) : (
								<div className="cp-input-generate-label px-6">
									<KeySelect
										minAvaliableKeysCount={
											infoSelectedKey.selectedSubscription
												?.provisionedCount
										}
									/>
								</div>
							)}
						</>
					)}
				/>
			</Layout>

			<GenerateCardLayout infoSelectedKey={infoSelectedKey} />
		</div>
	);
};

const RequiredInformationForm = (props) => {
	return (
		<Formik
			initialValues={{
				description: '',
				keys: [getInitialGenerateNewDXPKey()],
				maxClusterNodes: '',
				name: '',
			}}
		>
			{(formikProps) => (
				<RequiredInformation {...props} {...formikProps} />
			)}
		</Formik>
	);
};

export default RequiredInformationForm;

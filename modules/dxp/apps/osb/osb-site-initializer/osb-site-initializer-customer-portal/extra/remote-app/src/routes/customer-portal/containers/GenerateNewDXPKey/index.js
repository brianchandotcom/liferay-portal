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

import ClayAlert from '@clayui/alert';
import {ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React, {useEffect, useState} from 'react';
import {Link} from 'react-router-dom';
import {Button} from '../../../../common/components';
import {Radio} from '../../../../common/components/Radio';
import Layout from '../../../../common/containers/setup-forms/Layout';
import {useApplicationProvider} from '../../../../common/context/AppPropertiesProvider';
import {getNewGenerateKeyFormValues} from '../../../../common/services/liferay/rest/raysource/LicenseKeys';
import getCurrentEndDate from '../../../../common/utils/getCurrentEndDate';
import {useCustomerPortal} from '../../context';
import {PAGE_TYPES, PRODUCT_TYPES} from '../../utils/constants';
import GenerateNewKeySkeleton from './Skeleton';

const ACTIVATION_ROOT_ROUTER = 'activation';
const INICIAL_SELECTED_VERSION = '7.0';
const INICIAL_SELECTED_TYPE = 'Backup';
const DXP_PRODUCT = 'DXP ';

const GenerateNewDXPKey = ({accountKey, productGroupName, sessionId}) => {
	const {licenseKeyDownloadURL} = useApplicationProvider();
	const [{subscriptionGroups}] = useCustomerPortal();

	const [selected, setSelected] = useState();
	const [generateFormValues, setGenerateFormValues] = useState();
	const [selectedVersion, setSelectedVersion] = useState(
		INICIAL_SELECTED_VERSION
	);
	const [selectedKeyType, setSelectedKeyType] = useState(
		INICIAL_SELECTED_TYPE
	);

	const handleComeBackPage = `/${accountKey}/${ACTIVATION_ROOT_ROUTER}/${PAGE_TYPES.dxp}`;

	useEffect(() => {
		const fetchGenerateFormData = async () => {
			const data = await getNewGenerateKeyFormValues(
				accountKey,
				licenseKeyDownloadURL,
				PRODUCT_TYPES.dxp,
				sessionId
			);

			if (data) {
				setGenerateFormValues(data);
			}
		};
		fetchGenerateFormData();
	}, [accountKey, licenseKeyDownloadURL, productGroupName, sessionId]);

	const productVersions = generateFormValues?.versions?.sort((a, b) =>
		a.label - b.label > 0 ? 1 : -1
	);

	const productKeyTypes = generateFormValues?.versions?.map(({types}) =>
		types
			.map((licenseKey) =>
				licenseKey.licenseEntryName.replace(DXP_PRODUCT, '')
			)
			.sort()
	);

	const returnSelectedVersionIndex = productVersions
		?.map((label) => label.label)
		.indexOf(selectedVersion);

	const selectedProductKey =
		productVersions &&
		productVersions[returnSelectedVersionIndex]?.types?.find(
			(key) => key.licenseEntryName === `${DXP_PRODUCT}${selectedKeyType}`
		).productKey;

	const getSubscriptionTerms = generateFormValues?.subscriptionTerms?.filter(
		(key) => key.productKey === selectedProductKey
	);

	const hasNotPermanentLicence =
		!generateFormValues?.allowPermanentLicenses ||
		selectedKeyType?.includes('Virtual Cluster');

	return (
		<>
			{generateFormValues ? (
				<Layout
					footerProps={{
						footerClass: 'mx-5 mb-2',

						leftButton: (
							<Link to={handleComeBackPage}>
								<Button
									className="btn btn-borderless btn-style-neutral"
									displayType="secondary"
								>
									Cancel
								</Button>
							</Link>
						),
						middleButton: (
							<Button disabled={!selected} displayType="primary">
								Next
							</Button>
						),
					}}
					headerProps={{
						headerClass: 'ml-5 mt-4 mb-3',
						helper:
							'Select the subscription and key type you would like to generate.',
						title: 'Generate Activation Key(s)',
					}}
					layoutType="cp-generateKey"
				>
					<div className="px-6">
						<div className="d-flex justify-content-between mb-2">
							<div className="mr-3 w-100">
								<label htmlFor="basicInput">Product</label>

								<div className="cp-select-card position-relative">
									<ClaySelect
										className="cp-select-card mr-2"
										disabled={true}
									>
										{subscriptionGroups.map((product) => (
											<ClaySelect.Option
												key={product.name}
												label={product.name}
											/>
										))}
									</ClaySelect>

									<ClayIcon
										className="select-icon"
										symbol="caret-bottom"
									/>
								</div>
							</div>

							<div className="ml-3 w-100">
								<label htmlFor="basicInput">Version</label>

								<div className="position-relative">
									<ClaySelect
										className="mr-2"
										onChange={({target}) => {
											setSelectedVersion(target.value);
										}}
										value={selectedVersion}
									>
										{productVersions?.map((version) => (
											<ClaySelect.Option
												key={version.label}
												label={version.label}
											/>
										))}
									</ClaySelect>

									<ClayIcon
										className="select-icon"
										symbol="caret-bottom"
									/>
								</div>
							</div>
						</div>

						<div className="mt-4 w-100">
							<label htmlFor="basicInput">Key Type</label>

							<div className="position-relative">
								<ClaySelect
									className="mr-2 pr-6 w-100"
									onChange={({target}) => {
										setSelectedKeyType(target.value);
									}}
									value={selectedKeyType}
								>
									{productKeyTypes &&
										productKeyTypes[
											returnSelectedVersionIndex
										]?.map((keyType) => (
											<ClaySelect.Option
												key={keyType}
												label={keyType}
											/>
										))}
								</ClaySelect>

								<ClayIcon
									className="select-icon"
									symbol="caret-bottom"
								/>
							</div>
						</div>

						<div>
							<div className="mb-3 mt-4">
								<h5>Subscription</h5>
							</div>

							<div>
								{getSubscriptionTerms.map(
									(subscriptionterm, index) => {
										const wasSelected =
											selected === subscriptionterm;
										const getCurrentStartAndEndDate = `${getCurrentEndDate(
											subscriptionterm.startDate
										)} - ${getCurrentEndDate(
											subscriptionterm.endDate
										)}`;
										const displayAlertType = hasNotPermanentLicence ? (
											<ClayAlert
												className="px-4 py-3"
												displayType="info"
											>
												<span className="text-paragraph">
													Activation Key(s) will be
													valid{' '}
												</span>

												<b className="text-paragraph">
													{`${getCurrentEndDate(
														subscriptionterm.startDate
													)} `}
													-
													{` ${getCurrentEndDate(
														subscriptionterm.endDate
													)}`}
												</b>
											</ClayAlert>
										) : (
											<ClayAlert
												className="px-4 py-3"
												displayType="info"
											>
												<span className="text-paragraph">
													Activation Key(s) will be
													valid{' '}
												</span>

												<b className="text-paragraph">
													indefinitely starting
													{` ${getCurrentEndDate(
														subscriptionterm.startDate
													)}, `}
												</b>

												<span className="text-paragraph">
													or until manually
													deactivated.
												</span>
											</ClayAlert>
										);

										return (
											<Radio
												description={`Key activation available: ${subscriptionterm.provisionedCount} of ${subscriptionterm.quantity}`}
												hasCustomAlert={
													wasSelected &&
													displayAlertType
												}
												isActivationKeyAvailable={
													subscriptionterm.provisionedCount >
													0
												}
												key={index}
												label={
													getCurrentStartAndEndDate
												}
												onChange={(event) =>
													setSelected(
														event.target.value
													)
												}
												selected={wasSelected}
												subtitle={`Instance size: ${subscriptionterm.instanceSize}`}
												value={subscriptionterm}
											/>
										);
									}
								)}
							</div>

							<div className="dropdown-divider mt-3"></div>
						</div>
					</div>
				</Layout>
			) : (
				<GenerateNewKeySkeleton />
			)}
		</>
	);
};

GenerateNewDXPKey.Skeleton = GenerateNewKeySkeleton;
export default GenerateNewDXPKey;

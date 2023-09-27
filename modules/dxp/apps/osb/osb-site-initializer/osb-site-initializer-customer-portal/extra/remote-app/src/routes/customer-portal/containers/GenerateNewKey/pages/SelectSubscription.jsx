/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {useCallback, useEffect, useMemo, useState} from 'react';
import {Link} from 'react-router-dom';
import useSWR from 'swr';
import i18n from '~/common/I18n';
import {Button} from '~/common/components';
import {Radio} from '~/common/components/Radio';
import Layout from '~/common/containers/setup-forms/Layout';
import {useAppPropertiesContext} from '~/common/contexts/AppPropertiesContext';
import {getNewGenerateKeyFormValues} from '~/common/services/liferay/rest/raysource/LicenseKeys';
import {FORMAT_DATE_TYPES} from '~/common/utils/constants';
import getDateCustomFormat from '~/common/utils/getDateCustomFormat';
import {useCustomerPortal} from '../../../context';
import GenerateNewKeySkeleton from '../Skeleton';
import {getLicenseKeyEndDatesByLicenseType} from '../utils/licenseKeyEndDateUtil';

const SelectSubscription = ({
	accountKey,
	infoSelectedKey,
	productGroupName,
	sessionId,
	setInfoSelectedKey,
	setStep,
	urlPreviousPage,
}) => {
	const [{subscriptionGroups}] = useCustomerPortal();
	const {featureFlags, provisioningServerAPI} = useAppPropertiesContext();

	const {data: generateFormValues, isLoading} = useSWR(
		sessionId ? `/${accountKey}/${productGroupName}/form-values` : null,
		() =>
			getNewGenerateKeyFormValues(
				accountKey,
				provisioningServerAPI,
				productGroupName,
				sessionId
			)
	);

	const [selectedSubscription, setSelectedSubscription] = useState(
		infoSelectedKey?.selectedSubscription
	);
	const [selectedVersion, setSelectedVersion] = useState(
		infoSelectedKey?.productVersion
	);
	const [selectedKeyType, setSelectedKeyType] = useState(
		infoSelectedKey?.licenseEntryType
	);

	const [hasKeyComplementary, setHasKeyComplementary] = useState(false);

	const doesNotAllowPermanentLicense = !generateFormValues?.allowPermanentLicenses;

	const allowComplimentary = generateFormValues?.allowComplimentary;

	const hasNotPermanentLicence =
		selectedKeyType?.includes('Virtual Cluster') ||
		selectedKeyType?.includes('OEM') ||
		selectedKeyType?.includes('Enterprise');

	const typesProduct = generateFormValues?.versions[0]?.types;

	const handleProduct = useCallback(() => {
		const filteredTypes = typesProduct?.find(
			(type) =>
				type.licenseEntryDisplayName ===
				productGroupName + ' ' + selectedKeyType
		);

		return filteredTypes?.productKey;
	}, [typesProduct, productGroupName, selectedKeyType]);

	const mockedValuesForComplimentaryKeys = useMemo(() => {
		return {
			instanceSize: 4,
			productKey: handleProduct(),
			provisionedCount: 0,
			quantity: 5,
		};
	}, [handleProduct]);

	const productVersions = useMemo(() => {
		if (generateFormValues?.versions) {
			return generateFormValues.versions.sort((a, b) =>
				a.label >= b.label ? 1 : -1
			);
		}

		return [];
	}, [generateFormValues?.versions]);

	useEffect(() => {
		if (productVersions?.length && !infoSelectedKey?.productVersion) {
			setSelectedVersion(productVersions[0].label);
		}
	}, [infoSelectedKey?.productVersion, productVersions]);

	const selectedVersionIndex = useMemo(() => {
		if (selectedVersion) {
			return productVersions
				?.map((label) => label.label)
				.indexOf(selectedVersion);
		}

		return 0;
	}, [productVersions, selectedVersion]);

	const productKeyTypes = useMemo(
		() =>
			productVersions?.map(({types}) =>
				types
					.map((licenseKey) =>
						licenseKey.licenseEntryDisplayName.replace(
							`${productGroupName} `,
							''
						)
					)
					.sort()
			),
		[productGroupName, productVersions]
	);

	useEffect(() => {
		if (productKeyTypes?.length && !infoSelectedKey?.licenseEntryType) {
			setSelectedKeyType(productKeyTypes[selectedVersionIndex][0]);
		}
	}, [
		infoSelectedKey?.licenseEntryType,
		productKeyTypes,
		selectedVersionIndex,
	]);

	const selectedProductKey = useMemo(
		() =>
			productVersions &&
			productVersions[selectedVersionIndex]?.types?.find(
				(key) =>
					key.licenseEntryDisplayName.replace(
						`${productGroupName} `,
						''
					) === selectedKeyType
			)?.productKey,
		[
			productGroupName,
			productVersions,
			selectedKeyType,
			selectedVersionIndex,
		]
	);

	const subscriptionTerms = useMemo(
		() =>
			generateFormValues?.subscriptionTerms?.filter(
				(key) => key.productKey === selectedProductKey
			),
		[generateFormValues?.subscriptionTerms, selectedProductKey]
	);

	const getCustomAlert = (subscriptionTerm) => (
		<ClayAlert className="px-4 py-3" displayType="info">
			<span className="text-paragraph">
				{hasNotPermanentLicence || doesNotAllowPermanentLicense
					? i18n.sub('activation-keys-will-be-valid-x-x', [
							getDateCustomFormat(
								subscriptionTerm.startDate,
								FORMAT_DATE_TYPES.day2DMonthSYearN
							),
							getDateCustomFormat(
								getLicenseKeyEndDatesByLicenseType({
									...infoSelectedKey,
									selectedSubscription: {
										...subscriptionTerm,
									},
								}),
								FORMAT_DATE_TYPES.day2DMonthSYearN
							),
					  ])
					: i18n.sub(
							'activation-keys-will-be-valid-indefinitely-starting-x-or-until-manually-deactivated',
							[
								getDateCustomFormat(
									subscriptionTerm.startDate,
									FORMAT_DATE_TYPES.day2DMonthSYearN
								),
							]
					  )}
			</span>
		</ClayAlert>
	);

	if (!generateFormValues || !accountKey || !sessionId || isLoading) {
		return <GenerateNewKeySkeleton />;
	}

	return (
		<Layout
			footerProps={{
				footerClass: 'mx-5 mb-2',
				leftButton: (
					<Link to={urlPreviousPage}>
						<Button
							aria-label={i18n.translate('cancel')}
							className="btn btn-borderless btn-style-neutral"
							displayType="secondary"
						>
							{i18n.translate('cancel')}
						</Button>
					</Link>
				),
				middleButton: (
					<Button
						aria-label={i18n.translate('next')}
						disabled={
							!selectedSubscription ||
							!Object.keys(selectedSubscription).length
						}
						displayType="primary"
						onClick={() => {
							setInfoSelectedKey((previousInfoSelectedKey) => ({
								...previousInfoSelectedKey,
								doesNotAllowPermanentLicense,
								hasNotPermanentLicence,
								selectedSubscription: {
									...selectedSubscription,
								},
							}));
							setStep(hasKeyComplementary ? 1 : 2);
						}}
					>
						{i18n.translate('next')}
					</Button>
				),
			}}
			headerProps={{
				headerClass: 'ml-5 mt-4 mb-3',
				helper: i18n.translate(
					'select-the-subscription-and-key-type-you-would-like-to-generate'
				),
				title: i18n.translate('generate-activation-keys'),
			}}
			layoutType="cp-generateKey"
		>
			<div className="px-6">
				<div className="d-flex justify-content-between mb-2">
					<div className="mr-3 w-100">
						<label htmlFor="basicInput">
							{i18n.translate('product')}
						</label>

						<div className="cp-select-card position-relative">
							<ClaySelect
								className="cp-select-card mr-2"
								disabled={true}
							>
								{subscriptionGroups?.map((product) => (
									<ClaySelect.Option
										key={product.name}
										label={productGroupName}
									/>
								))}
							</ClaySelect>

							<ClayIcon
								aria-label="Caret Icon Bottom"
								className="select-icon"
								symbol="caret-bottom"
							/>
						</div>
					</div>

					<div className="ml-3 w-100">
						<label htmlFor="basicInput">
							{i18n.translate('version')}
						</label>

						<div className="position-relative">
							<ClaySelect
								className="mr-2"
								onChange={({target}) => {
									setInfoSelectedKey({
										licenseEntryType: selectedKeyType,
										productType: productGroupName,
										productVersion: target.value,
									});
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
								aria-label="Caret Icon Bottom"
								className="select-icon"
								symbol="caret-bottom"
							/>
						</div>
					</div>
				</div>

				<div className="mt-4 w-100">
					<label htmlFor="basicInput">
						{i18n.translate('key-type')}
					</label>

					<div className="position-relative">
						<ClaySelect
							className="mr-2 pr-6 w-100"
							onChange={({target}) => {
								setSelectedKeyType(target.value);
								setSelectedSubscription({});
								setHasKeyComplementary(false);
							}}
							value={selectedKeyType}
						>
							{productKeyTypes &&
								productKeyTypes[
									selectedVersionIndex
								]?.map((keyType) => (
									<ClaySelect.Option
										key={keyType}
										label={keyType}
									/>
								))}
						</ClaySelect>

						<ClayIcon
							aria-label="Caret Icon Bottom"
							className="select-icon"
							symbol="caret-bottom"
						/>
					</div>
				</div>

				<div>
					<div className="mb-3 mt-4">
						<h5>{i18n.translate('subscription')}</h5>
					</div>

					<div>
						{subscriptionTerms
							?.filter((subscriptionTerm) => {
								return (
									new Date() < new Date(subscriptionTerm.endDate) &&
									subscriptionTerm
								);
							})
							.sort(
								(
									firstSubscriptionTerm,
									secondSubscriptionTerm
								) => {
									const firstAvailableKeysQty =
										firstSubscriptionTerm.quantity -
										firstSubscriptionTerm.provisionedCount;

									const secondAvailableKeysQty =
										secondSubscriptionTerm.quantity -
										secondSubscriptionTerm.provisionedCount;

									return (
										secondAvailableKeysQty -
										firstAvailableKeysQty
									);
								}
							)
							?.map((subscriptionTerm, index) => {
								const selected =
									JSON.stringify(selectedSubscription) ===
									JSON.stringify({
										...subscriptionTerm,
										index,
									});
								const currentStartAndEndDate = `${getDateCustomFormat(
									subscriptionTerm.startDate,
									FORMAT_DATE_TYPES.day2DMonthSYearN
								)} - ${getDateCustomFormat(
									subscriptionTerm.endDate,
									FORMAT_DATE_TYPES.day2DMonthSYearN
								)}`;

								const infoSelectedKey = {
									index,
									licenseEntryType: selectedKeyType,
									productType: productGroupName,
									productVersion: selectedVersion,
								};

								const displayAlertType = getCustomAlert(
									subscriptionTerm
								);

								let numberOfActivationKeysAvailable =
									subscriptionTerm.quantity -
									subscriptionTerm.provisionedCount;
								numberOfActivationKeysAvailable =
									numberOfActivationKeysAvailable < 0
										? 0
										: numberOfActivationKeysAvailable;

								return (
									<Radio
										description={i18n.sub(
											'key-activation-available-x-of-x',
											[
												numberOfActivationKeysAvailable,
												subscriptionTerm.quantity,
											]
										)}
										hasCustomAlert={
											selected && displayAlertType
										}
										isActivationKeyAvailable={
											subscriptionTerm.quantity -
												subscriptionTerm.provisionedCount >
											0
										}
										key={index}
										label={currentStartAndEndDate}
										onChange={(event) => {
											setSelectedSubscription({
												...event.target.value,
												index,
											});
											setInfoSelectedKey(infoSelectedKey);
											setHasKeyComplementary(false);
										}}
										selected={selected}
										subtitle={i18n.sub('instance-size-x', [
											subscriptionTerm?.instanceSize || 1,
										])}
										value={subscriptionTerm}
									/>
								);
							})}
					</div>

					{featureFlags.includes('LPS-148342') && allowComplimentary && (
						<Radio
							isActivationKeyAvailable={5}
							label="Complimentary"
							onChange={(event) => {
								setSelectedSubscription({
									...event.target.value,
								});
								setHasKeyComplementary(true);

								setInfoSelectedKey({
									licenseEntryType: selectedKeyType,
									productType: productGroupName,
									productVersion: selectedVersion,
								});
							}}
							selected={hasKeyComplementary}
							subtitle={i18n.translate(
								'choose-this-option-if-you-want-an-activation-key-for-30-days'
							)}
							value={mockedValuesForComplimentaryKeys}
						/>
					)}

					<div className="dropdown-divider mt-3"></div>
				</div>
			</div>
		</Layout>
	);
};

SelectSubscription.Skeleton = GenerateNewKeySkeleton;
export default SelectSubscription;

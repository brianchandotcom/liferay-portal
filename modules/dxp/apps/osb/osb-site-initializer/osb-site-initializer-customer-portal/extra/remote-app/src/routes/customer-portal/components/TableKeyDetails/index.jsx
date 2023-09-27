/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useEffect, useState} from 'react';

import {useAppPropertiesContext} from '~/common/contexts/AppPropertiesContext';
import PopoverIconButton from '~/routes/customer-portal/components/PopoverIconButton';
import i18n from '../../../../common/I18n';
import {DXPIcon} from '../../../../common/icons/navigation-menu';
import {FORMAT_DATE_TYPES} from '../../../../common/utils/constants';
import getDateCustomFormat from '../../../../common/utils/getDateCustomFormat';
import {
	getFormatedProductName,
	getProductDescription,
	getProductName,
	getStatusActivationTag,
	hasVirtualCluster,
} from '../../containers/ActivationKeysTable/utils';

const HOST_NAME = i18n.translate('host-name');
const IP_ADDRESSES = i18n.translate('ip-addresses');
const MAC_ADDRESSES = i18n.translate('mac-addresses');

const NO_EXPIRATION_DATE = 100;

const TableKeyDetails = ({activationKeys, setValueToCopyToClipboard}) => {
	const [actionToCopy, setActionToCopy] = useState('');
	const instanceSizeFormated = activationKeys.sizing?.slice(7, 8);

	const {articleWhatIsMyInstanceSizingValueURL} = useAppPropertiesContext();

	const now = new Date();

	const hasVirtualClusterForActivationKeys = hasVirtualCluster(
		activationKeys?.licenseEntryType
	);
	const statusActivationTag = getStatusActivationTag(activationKeys);

	const unlimitedLicenseDate = now.setFullYear(
		now.getFullYear() + NO_EXPIRATION_DATE
	);

	const handleExpiredDate =
		new Date(activationKeys.expirationDate) >=
		new Date(unlimitedLicenseDate)
			? 'Does Not Expire'
			: getDateCustomFormat(
					activationKeys.expirationDate,
					FORMAT_DATE_TYPES.day2DMonthSYearN
			  );

	useEffect(() => {
		if (actionToCopy) {
			navigator.clipboard.writeText(actionToCopy);
		}
	}, [actionToCopy]);

	const handleCopyToClipboard = (value) => {
		setValueToCopyToClipboard(value);
	};

	return (
		<>
			<div className="container">
				<div className="row">
					<div className="col-5">
						<h5>Environment</h5>
					</div>

					<div className="col-4">
						<h5>Server</h5>
					</div>

					<div className="col-3">
						<h5>Activation Status</h5>
					</div>
				</div>

				<div className="row">
					<div className="col-2">
						<p className="text-neutral-8 text-paragraph-sm">
							Product
						</p>
					</div>

					<div className="col-3">
						<p className="text-neutral-8 text-paragraph-sm">
							Version
						</p>
					</div>

					<div className="col-4">
						<p className="text-neutral-8 text-paragraph-sm">
							Key Type
						</p>
					</div>

					<div className="col-3">
						<p className="text-neutral-8 text-paragraph-sm">
							Status
						</p>
					</div>
				</div>

				<div className="row">
					<div className="col-2">
						<p className="align-items-center bg-brand-primary-lighten-5 cp-key-details-paragraph d-flex px-3 py-2 rounded">
							<DXPIcon className="mr-2" />

							{getFormatedProductName(
								activationKeys?.productName
							)}
						</p>
					</div>

					<div className="col-3">
						<p className="bg-brand-primary-lighten-5 cp-key-details-paragraph px-3 py-2 rounded">
							{activationKeys.productVersion}
						</p>
					</div>

					<div className="col-4">
						<p className="bg-neutral-1 cp-key-details-paragraph px-3 py-2 rounded">
							{hasVirtualClusterForActivationKeys
								? 'Virtual Cluster'
								: 'On-Premise'}
						</p>
					</div>

					<div className="col-3">
						<p
							className={`cp-key-details-paragraph label-tonal-${statusActivationTag?.color} px-3 py-2 rounded`}
						>
							{statusActivationTag?.title}
						</p>
					</div>
				</div>

				<div className="row">
					<div className="col-5">
						<p className="text-neutral-8 text-paragraph-sm">
							Environment Type
						</p>
					</div>

					<div className="col-4">
						<p className="text-neutral-8 text-paragraph-sm">
							{hasVirtualClusterForActivationKeys
								? 'Cluster Nodes'
								: HOST_NAME}
						</p>
					</div>

					<div className="col-3">
						<p className="text-neutral-8 text-paragraph-sm">
							Start Date
						</p>
					</div>
				</div>

				<div className="row">
					<div className="col-2">
						<p className="bg-brand-primary-lighten-5 cp-key-details-paragraph px-3 py-2 rounded">
							{getProductName(activationKeys)}
						</p>
					</div>

					<div className="col-3">
						<p className="bg-brand-primary-lighten-5 cp-key-details-paragraph px-3 py-2 rounded">
							{getProductDescription(
								activationKeys?.complimentary
							)}
						</p>
					</div>

					<div className="col-4">
						<p className="bg-neutral-1 cp-key-details-paragraph d-flex px-3 py-2 rounded">
							{hasVirtualClusterForActivationKeys
								? activationKeys.maxClusterNodes
								: activationKeys.hostName || '-'}

							{activationKeys.hostName && (
								<ClayIcon
									className="cp-copy-clipboard-icon ml-3 mt-1 text-neutral-5"
									onClick={() =>
										handleCopyToClipboard(
											HOST_NAME,
											setActionToCopy(
												activationKeys.hostName
											)
										)
									}
									symbol="copy"
								/>
							)}
						</p>
					</div>

					<div className="col-3">
						<p className="bg-neutral-1 cp-key-details-paragraph px-3 py-2 rounded">
							{getDateCustomFormat(
								activationKeys.createDate,
								FORMAT_DATE_TYPES.day2DMonthSYearN
							)}
						</p>
					</div>
				</div>

				<div
					className={classNames('row', {
						'justify-content-between': hasVirtualClusterForActivationKeys,
					})}
				>
					<div className="col-5">
						<p className="text-neutral-8 text-paragraph-sm">
							{i18n.translate('instance-size')}

							<PopoverIconButton
								popoverLink={{
									textLink: i18n.translate(
										'learn-more-about-instance-sizing'
									),
									url: articleWhatIsMyInstanceSizingValueURL,
								}}
							/>
						</p>
					</div>

					{!hasVirtualClusterForActivationKeys && (
						<div className="col-4">
							<p className="text-neutral-8 text-paragraph-sm">
								{IP_ADDRESSES}
							</p>
						</div>
					)}

					<div className="col-3">
						<p className="text-neutral-8 text-paragraph-sm">
							Expiration Date
						</p>
					</div>
				</div>

				<div
					className={classNames('row', {
						'justify-content-between': hasVirtualClusterForActivationKeys,
					})}
				>
					<div className="col-5">
						<p className="bg-brand-primary-lighten-5 cp-key-details-paragraph px-3 py-2 rounded">
							{instanceSizeFormated}
						</p>
					</div>

					{!hasVirtualClusterForActivationKeys && (
						<div className="col-4">
							<p className="bg-neutral-1 cp-key-details-paragraph d-flex px-3 py-2 rounded">
								{activationKeys.ipAddresses || '-'}

								{activationKeys.ipAddresses && (
									<ClayIcon
										className="cp-copy-clipboard-icon ml-3 mt-1 text-neutral-5"
										onClick={() =>
											handleCopyToClipboard(
												IP_ADDRESSES,
												setActionToCopy(
													activationKeys.ipAddresses
												)
											)
										}
										symbol="copy"
									/>
								)}
							</p>
						</div>
					)}

					<div className="col-3">
						<p className="bg-neutral-1 cp-key-details-paragraph px-3 py-2 rounded">
							{handleExpiredDate}
						</p>
					</div>
				</div>

				{!hasVirtualClusterForActivationKeys && (
					<>
						<div className="justify-content-center row">
							<div className="col-2">
								<p className="text-neutral-8 text-paragraph-sm">
									{MAC_ADDRESSES}
								</p>
							</div>
						</div>
						<div className="justify-content-center row">
							<div className="col-4 ml-8">
								<p className="bg-neutral-1 cp-key-details-paragraph d-flex px-3 py-2 rounded">
									{activationKeys.macAddresses || '-'}

									{activationKeys.macAddresses && (
										<ClayIcon
											className="cp-copy-clipboard-icon ml-3 mt-1 text-neutral-5"
											onClick={() =>
												handleCopyToClipboard(
													MAC_ADDRESSES,
													setActionToCopy(
														activationKeys.macAddresses
													)
												)
											}
											symbol="copy"
										/>
									)}
								</p>
							</div>
						</div>
					</>
				)}
			</div>
		</>
	);
};
export default TableKeyDetails;

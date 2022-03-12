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
import classNames from 'classnames';
import {useEffect, useState} from 'react';
import getCurrentEndDate from '../../../../../../common/utils/getCurrentEndDate';
import {
	getFormatedProductName,
	getProductDescription,
	getProductName,
	getStatusActivationTag,
	hasVirtualCluster,
} from '../../utils/index';

const HOST_NAME = 'Host Name';
const IP_ADDRESSES = 'IP Addresses';
const MAC_ADDRESSES = 'Mac Addresses';
const SUBSCRIPTION_IMAGE_FILE = 'dxp_icon.svg';

const NO_EXPIRATION_DATE = 100;

const TableKeyDetails = ({
	assetsPath,
	currentActivationKey,
	setValueToCopyToClipboard,
}) => {
	const [actionToCopy, setActionToCopy] = useState('');
	const instanceSizeFormated = currentActivationKey.sizing?.slice(7, 8);

	const now = new Date();

	const hasVirtualClusterForActivationKeys = hasVirtualCluster(
		currentActivationKey?.licenseEntryType
	);
	const statusActivationTag = getStatusActivationTag(currentActivationKey);

	const unlimitedLicenseDate = now.setFullYear(
		now.getFullYear() + NO_EXPIRATION_DATE
	);

	const handleExpiredDate =
		new Date(currentActivationKey.expirationDate) >=
		new Date(unlimitedLicenseDate)
			? 'Does Not Expire'
			: getCurrentEndDate(currentActivationKey.expirationDate);

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
							<img
								className="mr-2"
								src={`${assetsPath}/assets/navigation-menu/${SUBSCRIPTION_IMAGE_FILE}`}
							/>

							{getFormatedProductName(
								currentActivationKey?.productName
							)}
						</p>
					</div>

					<div className="col-3">
						<p className="bg-brand-primary-lighten-5 cp-key-details-paragraph px-3 py-2 rounded">
							{currentActivationKey.productVersion}
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
							{getProductName(currentActivationKey)}
						</p>
					</div>

					<div className="col-3">
						<p className="bg-brand-primary-lighten-5 cp-key-details-paragraph px-3 py-2 rounded">
							{getProductDescription(
								currentActivationKey?.complimentary
							)}
						</p>
					</div>

					<div className="col-4">
						<p className="bg-neutral-1 cp-key-details-paragraph d-flex px-3 py-2 rounded">
							{hasVirtualClusterForActivationKeys
								? currentActivationKey.maxClusterNodes
								: currentActivationKey.hostName || '-'}

							{currentActivationKey.hostName && (
								<ClayIcon
									className="cp-copy-clipboard-icon ml-3 mt-1 text-neutral-5"
									onClick={() =>
										handleCopyToClipboard(
											HOST_NAME,
											setActionToCopy(
												currentActivationKey.hostName
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
							{getCurrentEndDate(currentActivationKey.startDate)}
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
							Instance Size
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
								{currentActivationKey.ipAddresses || '-'}

								{currentActivationKey.ipAddresses && (
									<ClayIcon
										className="cp-copy-clipboard-icon ml-3 mt-1 text-neutral-5"
										onClick={() =>
											handleCopyToClipboard(
												IP_ADDRESSES,
												setActionToCopy(
													currentActivationKey.ipAddresses
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
									{currentActivationKey.macAddresses || '-'}

									{currentActivationKey.macAddresses && (
										<ClayIcon
											className="cp-copy-clipboard-icon ml-3 mt-1 text-neutral-5"
											onClick={() =>
												handleCopyToClipboard(
													MAC_ADDRESSES,
													setActionToCopy(
														currentActivationKey.macAddresses
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

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
import getCurrentEndDate from '../../../../common/utils/getCurrentEndDate';
import {
	getFormatedProductName,
	getProductDescription,
	getProductName,
	getStatusActivationTag,
	hasVirtualCluster,
} from '../../containers/DXPActivationKeysTable/utils/constants';

const HOST_NAME = 'Host Name';
const IP_ADDRESSES = 'IP Addresses';
const MAC_ADDRESSES = 'Mac Addresses';
const SUBSCRIPTION_IMAGE_FILE = 'dxp_icon.svg';

const NO_EXPIRATION_DATE = 100;

const TableKeyDetails = ({
	activationKeys,
	assetsPath,
	setValueToCopyToClipboard,
}) => {
	const instanceSizeFormated = activationKeys.sizing.slice(7, 8);

	const unlimitedLicenseDate = new Date().setFullYear(
		new Date().getFullYear() + NO_EXPIRATION_DATE
	);

	const handleExpiredDate =
		new Date(activationKeys.expirationDate) >=
		new Date(unlimitedLicenseDate)
			? 'Does Not Expire'
			: getCurrentEndDate(activationKeys.expirationDate);

	const handleCopyToClipboard = (value, action) => {
		navigator.clipboard.writeText(action);

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
						<p className="align-items-center bg-brand-primary-lighten-5 d-flex key-details-paragraph px-3 py-2 rounded">
							<img
								className="mr-2"
								src={`${assetsPath}/assets/navigation-menu/${SUBSCRIPTION_IMAGE_FILE}`}
							/>

							{getFormatedProductName(activationKeys)}
						</p>
					</div>

					<div className="col-3">
						<p className="bg-brand-primary-lighten-5 key-details-paragraph px-3 py-2 rounded">
							{activationKeys.productVersion}
						</p>
					</div>

					<div className="col-4">
						<p className="bg-neutral-1 key-details-paragraph px-3 py-2 rounded">
							{hasVirtualCluster(activationKeys)
								? 'Virtual Cluster'
								: 'On-Premise'}
						</p>
					</div>

					<div className="col-3">
						<p
							className={`key-details-paragraph label-tonal-${
								getStatusActivationTag(activationKeys).color
							} px-3 py-2 rounded`}
						>
							{getStatusActivationTag(activationKeys).title}
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
							{hasVirtualCluster(activationKeys)
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
						<p className="bg-brand-primary-lighten-5 key-details-paragraph px-3 py-2 rounded">
							{getProductName(activationKeys)}
						</p>
					</div>

					<div className="col-3">
						<p className="bg-brand-primary-lighten-5 key-details-paragraph px-3 py-2 rounded">
							{getProductDescription(activationKeys)}
						</p>
					</div>

					<div className="col-4">
						<p className="bg-neutral-1 d-flex key-details-paragraph px-3 py-2 rounded">
							{hasVirtualCluster(activationKeys)
								? `${activationKeys.maxClusterNodes}`
								: activationKeys.hostName || '-'}

							{activationKeys.hostName && (
								<ClayIcon
									className="copy-clipboard-icon ml-3 mt-1 text-neutral-5"
									onClick={() =>
										handleCopyToClipboard(
											HOST_NAME,
											activationKeys.hostName
										)
									}
									symbol="copy"
								/>
							)}
						</p>
					</div>

					<div className="col-3">
						<p className="bg-neutral-1 key-details-paragraph px-3 py-2 rounded">
							{getCurrentEndDate(activationKeys.createDate)}
						</p>
					</div>
				</div>

				<div
					className={classNames('row', {
						'justify-content-between': hasVirtualCluster(
							activationKeys
						),
					})}
				>
					<div className="col-5">
						<p className="text-neutral-8 text-paragraph-sm">
							Instance Size
						</p>
					</div>

					{!hasVirtualCluster(activationKeys) && (
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
						'justify-content-between': hasVirtualCluster(
							activationKeys
						),
					})}
				>
					<div className="col-5">
						<p className="bg-brand-primary-lighten-5 key-details-paragraph px-3 py-2 rounded">
							{instanceSizeFormated}
						</p>
					</div>

					{!hasVirtualCluster(activationKeys) && (
						<div className="col-4">
							<p className="bg-neutral-1 d-flex key-details-paragraph px-3 py-2 rounded">
								{activationKeys.ipAddresses || '-'}

								{activationKeys.ipAddresses && (
									<ClayIcon
										className="copy-clipboard-icon ml-3 mt-1 text-neutral-5"
										onClick={() =>
											handleCopyToClipboard(
												IP_ADDRESSES,
												activationKeys.ipAddresses
											)
										}
										symbol="copy"
									/>
								)}
							</p>
						</div>
					)}

					<div className="col-3">
						<p className="bg-neutral-1 key-details-paragraph px-3 py-2 rounded">
							{handleExpiredDate}
						</p>
					</div>
				</div>

				{!hasVirtualCluster(activationKeys) && (
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
								<p className="bg-neutral-1 d-flex key-details-paragraph px-3 py-2 rounded">
									{activationKeys.macAddresses || '-'}

									{activationKeys.macAddresses && (
										<ClayIcon
											className="copy-clipboard-icon ml-3 mt-1 text-neutral-5"
											onClick={() =>
												handleCopyToClipboard(
													MAC_ADDRESSES,
													activationKeys.macAddresses
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

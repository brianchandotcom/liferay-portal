/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useEffect, useState} from 'react';

import {useAppPropertiesContext} from '~/common/contexts/AppPropertiesContext';
import PopoverIconButton from '~/routes/customer-portal/components/PopoverIconButton';
import i18n from '../../../../../../common/I18n';
import {
	DXPIcon,
	PortalIcon,
} from '../../../../../../common/icons/navigation-menu';
import {FORMAT_DATE_TYPES} from '../../../../../../common/utils/constants';
import getDateCustomFormat from '../../../../../../common/utils/getDateCustomFormat';
import {PRODUCT_TYPES} from '../../../../utils/constants';
import {
	getEnvironmentType,
	getFormatedProductName,
	getInstanceSize,
	getProductDescription,
	getStatusActivationTag,
	hasVirtualCluster,
} from '../../utils/index';

const HOST_NAME = i18n.translate('host-name');
const IP_ADDRESSES = i18n.translate('ip-addresses');
const MAC_ADDRESSES = i18n.translate('mac-addresses');

const YEARS_FOR_PERMANENT_KEYS = 90;

const TableKeyDetails = ({currentActivationKey, setValueToCopyToClipboard}) => {
	const [actionToCopy, setActionToCopy] = useState('');
	const instanceSizeFormated = getInstanceSize(currentActivationKey.sizing);

	const {articleWhatIsMyInstanceSizingValueURL} = useAppPropertiesContext();

	const now = new Date();

	const hasVirtualClusterForActivationKeys = hasVirtualCluster(
		currentActivationKey?.licenseEntryType
	);
	const statusActivationTag = getStatusActivationTag(currentActivationKey);

	const unlimitedLicenseDate = now.setFullYear(
		now.getFullYear() + YEARS_FOR_PERMANENT_KEYS
	);

	const formatedProductName = getFormatedProductName(
		currentActivationKey?.productName
	);

	const handleExpiredDate =
		new Date(currentActivationKey.expirationDate) >=
		new Date(unlimitedLicenseDate)
			? i18n.translate('does-not-expire')
			: getDateCustomFormat(
					currentActivationKey.expirationDate,
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

	const Logo =
		formatedProductName === PRODUCT_TYPES.portal ? PortalIcon : DXPIcon;

	return (
		<div className="container">
			<div className="row">
				<div className="col-5">
					<h5>{i18n.translate('environment')}</h5>
				</div>

				<div className="col-4">
					<h5>{i18n.translate('server')}</h5>
				</div>

				<div className="col-3">
					<h5>{i18n.translate('activation-status')}</h5>
				</div>
			</div>

			<div className="row">
				<div className="col-2">
					<p className="text-neutral-8 text-paragraph-sm">
						{i18n.translate('product')}
					</p>
				</div>

				<div className="col-3">
					<p className="text-neutral-8 text-paragraph-sm">
						{i18n.translate('version')}
					</p>
				</div>

				<div className="col-4">
					<p className="text-neutral-8 text-paragraph-sm">
						{i18n.translate('key-type')}
					</p>
				</div>

				<div className="col-3">
					<p className="text-neutral-8 text-paragraph-sm">
						{i18n.translate('status')}
					</p>
				</div>
			</div>

			<div className="row">
				<div className="col-2">
					<p className="align-items-center bg-brand-primary-lighten-5 cp-key-details-paragraph d-flex px-3 py-2 rounded">
						<Logo className="mr-2" />

						{formatedProductName}
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
							? i18n.translate('virtual-cluster')
							: i18n.translate('on-premise')}
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
						{i18n.translate('environment-type')}
					</p>
				</div>

				<div className="col-4">
					<p className="text-neutral-8 text-paragraph-sm">
						{hasVirtualClusterForActivationKeys
							? i18n.translate('cluster-nodes')
							: HOST_NAME}
					</p>
				</div>

				<div className="col-3">
					<p className="text-neutral-8 text-paragraph-sm">
						{i18n.translate('start-date')}
					</p>
				</div>
			</div>

			<div className="row">
				<div className="col-2">
					<p className="bg-brand-primary-lighten-5 cp-key-details-paragraph px-3 py-2 rounded">
						{getEnvironmentType(currentActivationKey.productName)}
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
						{getDateCustomFormat(
							currentActivationKey.startDate,
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
					{!!currentActivationKey.sizing && (
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
					)}
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
						{i18n.translate('expiration-date')}
					</p>
				</div>
			</div>

			<div
				className={classNames('row', {
					'justify-content-between': hasVirtualClusterForActivationKeys,
				})}
			>
				<div className="col-5">
					{!!currentActivationKey.sizing && (
						<p className="bg-brand-primary-lighten-5 cp-key-details-paragraph px-3 py-2 rounded">
							{instanceSizeFormated}
						</p>
					)}
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
	);
};
export default TableKeyDetails;

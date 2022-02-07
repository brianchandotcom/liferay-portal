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

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useMemo, useState} from 'react';
import {Button, ButtonDropDown} from '../../../../../common/components';
import {ALERT_DOWNLOAD_TYPE} from '../../../utils/constants/alertDownloadType';
import {AUTO_CLOSE_DOWNLOAD_ALERT_TIME} from '../../../utils/constants/autoCloseDownloadAlertTime';
import {ACTIVATION_KEYS_ACTIONS_ITEMS} from '../utils/constants/activationKeysActionsItems';
import {ALERT_ACTIVATION_AGGREGATED_KEYS_DOWNLOAD_TEXT} from '../utils/constants/alertAggregateKeysDownloadText';
import {
	downloadActivationLicenseKey,
	downloadAggregatedActivationDownloadKey,
} from '../utils/downloadActivationLicenseKey';

const PRODUCTION_ENVIRONMENT = 'production';
const PRODUCTION_VERSION = 7.1;

const DXPActivationKeysTableHeader = ({
	activationKeys,
	licenseKeyDownloadURL,
	selectedKeys,
	sessionId,
}) => {
	const selectedKeysIDs = selectedKeys
		.map((selectedKey) => `licenseKeyIds=${selectedKey}`)
		.join('&');

	const [
		activationKeysDownloadStatus,
		setActivationKeysDownloadStatus,
	] = useState('');

	const isAbleToDownloadAggregateKeys = useMemo(() => {
		const [
			firstSelectedKey,
			...restSelectedKeys
		] = selectedKeys.map((key) =>
			activationKeys.find((activationKey) => activationKey.id === key)
		);

		const keyCanBeDownloaded = restSelectedKeys.every(
			(selectedKey) =>
				(Number(firstSelectedKey.productVersion) >=
					PRODUCTION_VERSION &&
					Number(selectedKey.productVersion) >= PRODUCTION_VERSION) ||
				(firstSelectedKey.licenseEntryType === PRODUCTION_ENVIRONMENT &&
					firstSelectedKey.sizing === selectedKey.sizing &&
					firstSelectedKey.startDate === selectedKey.startDate &&
					firstSelectedKey.expirationDate ===
						selectedKey.expirationDate &&
					firstSelectedKey.productVersion ===
						selectedKey.productVersion)
		);

		return keyCanBeDownloaded;
	}, [activationKeys, selectedKeys]);

	const ACTIVATION_KEYS_DOWNLOAD_ITEMS = [
		{
			disabled: !isAbleToDownloadAggregateKeys,
			icon: (
				<ClayIcon className="mr-1 text-neutral-4" symbol="document" />
			),
			label: 'Aggregate Key (single file)',
			onClick: async () => {
				const downloadedAggregated = await downloadAggregatedActivationDownloadKey(
					selectedKeysIDs,
					licenseKeyDownloadURL,
					sessionId
				);
				setActivationKeysDownloadStatus(
					downloadedAggregated
						? ALERT_DOWNLOAD_TYPE.success
						: ALERT_DOWNLOAD_TYPE.danger
				);
			},
		},
	];

	const getCurrentButton = () => {
		if (selectedKeys.length > 1) {
			return (
				<ButtonDropDown
					items={ACTIVATION_KEYS_DOWNLOAD_ITEMS}
					label="Download"
					menuElementAttrs={{
						className: 'p-0',
					}}
				/>
			);
		}

		if (selectedKeys.length) {
			return (
				<Button
					className="btn btn-primary"
					onClick={async () => {
						const downloadedKey = await downloadActivationLicenseKey(
							selectedKeys,
							licenseKeyDownloadURL,
							sessionId
						);

						setActivationKeysDownloadStatus(
							downloadedKey
								? ALERT_DOWNLOAD_TYPE.success
								: ALERT_DOWNLOAD_TYPE.danger
						);
					}}
				>
					Download
				</Button>
			);
		}

		return (
			<ButtonDropDown
				items={ACTIVATION_KEYS_ACTIONS_ITEMS}
				label="Actions"
				menuElementAttrs={{
					className: 'p-0',
				}}
			/>
		);
	};

	return (
		<div>
			<div className="align-items-center bg-neutral-1 d-flex mb-2 p-3 rounded">
				{!!selectedKeys.length && (
					<>
						<p className="font-weight-semi-bold m-0 ml-auto text-neutral-10">
							{`${selectedKeys.length} Keys Selected`}
						</p>

						<Button className="btn-outline-danger cp-deactivate-button mx-2">
							Deactivate
						</Button>
					</>
				)}

				<div
					className={classNames({
						'ml-auto': !selectedKeys.length,
					})}
				>
					{getCurrentButton()}
				</div>
			</div>

			{activationKeysDownloadStatus && (
				<ClayAlert.ToastContainer>
					<ClayAlert
						autoClose={
							AUTO_CLOSE_DOWNLOAD_ALERT_TIME[
								activationKeysDownloadStatus
							]
						}
						className="cp-activation-key-download-alert"
						displayType={
							ALERT_DOWNLOAD_TYPE[activationKeysDownloadStatus]
						}
						onClose={() => setActivationKeysDownloadStatus('')}
					>
						{
							ALERT_ACTIVATION_AGGREGATED_KEYS_DOWNLOAD_TEXT[
								activationKeysDownloadStatus
							]
						}
					</ClayAlert>
				</ClayAlert.ToastContainer>
			)}

			{!isAbleToDownloadAggregateKeys && (
				<ClayAlert displayType="info" title="">
					To download an aggregate key, select keys with identical
					<b>{' Type, Start Date, End Date, '}</b>
					and
					<b>{' Instance Size'} </b>
				</ClayAlert>
			)}
		</div>
	);
};

export default DXPActivationKeysTableHeader;

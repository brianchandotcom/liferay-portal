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

import classNames from 'classnames';
import {useMemo, useState} from 'react';
import {Button, ButtonDropDown} from '../../../../../common/components';
import {ALERT_DOWNLOAD_TYPE} from '../../../utils/constants/alertDownloadType';
import {AUTO_CLOSE_DOWNLOAD_ALERT_TIME} from '../../../utils/constants/autoCloseDownloadAlertTime';
import {DOWNLOADABLE_LICENSE_KEYS} from '../utils/constants';
import {ALERT_ACTIVATION_AGGREGATED_KEYS_DOWNLOAD_TEXT} from '../utils/constants/alertAggregateKeysDownloadText';
import {getActivationKeyDownload} from '../utils/getActivationKeyDownload';
import {getActivationKeysActionsItems} from '../utils/getActivationKeysActionsItems';
import {getActivationKeysDownloadItems} from '../utils/getActivationKeysDownloadItems';

const DXPActivationKeysTableHeader = ({
	accountKey,
	activationKeys,
	licenseKeyDownloadURL,
	selectedKeys,
	sessionId,
}) => {
	const [
		activationKeysDownloadStatus,
		setActivationKeysDownloadStatus,
	] = useState('');

	const selectedKeysIDs = selectedKeys
		.map((selectedKey) => `licenseKeyIds=${selectedKey}`)
		.join('&');

	const isAbleToDownloadAggregateKeys = useMemo(() => {
		const [
			firstSelectedKey,
			...restSelectedKeys
		] = selectedKeys.map((key) =>
			activationKeys.find((activationKey) => activationKey.id === key)
		);

		const keyCanBeDownloaded = restSelectedKeys.every(
			(selectedKey) =>
				DOWNLOADABLE_LICENSE_KEYS.above71(
					firstSelectedKey,
					selectedKey
				) ||
				DOWNLOADABLE_LICENSE_KEYS.below71(firstSelectedKey, selectedKey)
		);

		return keyCanBeDownloaded;
	}, [activationKeys, selectedKeys]);

	const handleAlertStatus = (hasSuccessfullyDownloadedKeys) => {
		setActivationKeysDownloadStatus(
			hasSuccessfullyDownloadedKeys
				? ALERT_DOWNLOAD_TYPE.success
				: ALERT_DOWNLOAD_TYPE.danger
		);
	};

	const ACTIVATION_KEYS_ACTION_ITEMS = getActivationKeysActionsItems(
		accountKey,
		licenseKeyDownloadURL,
		sessionId,
		handleAlertStatus
	);

	const ACTIVATION_KEYS_DOWNLOAD_ITEMS = getActivationKeysDownloadItems(
		isAbleToDownloadAggregateKeys,
		selectedKeysIDs,
		licenseKeyDownloadURL,
		sessionId,
		handleAlertStatus
	);

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
					className="btn btn-secondary"
					onClick={async () =>
						getActivationKeyDownload(
							selectedKeys,
							licenseKeyDownloadURL,
							sessionId,
							handleAlertStatus
						)
					}
				>
					Download
				</Button>
			);
		}

		return (
			<ButtonDropDown
				items={ACTIVATION_KEYS_ACTION_ITEMS}
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

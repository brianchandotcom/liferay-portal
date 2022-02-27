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
import {useMemo, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import {Button, ButtonDropDown} from '../../../../../../common/components';
import {AUTO_CLOSE_ALERT_TIME, PAGE_TYPES} from '../../../../utils/constants';
import {ALERT_DOWNLOAD_TYPE} from '../../../../utils/constants/alertDownloadType';
import {useActivationKeys} from '../../context';
import {ALERT_ACTIVATION_AGGREGATED_KEYS_DOWNLOAD_TEXT} from '../../utils/constants/alertAggregateKeysDownloadText';
import {DOWNLOADABLE_LICENSE_KEYS} from '../../utils/constants/downlodableLicenseKeys';
import {getActivationKeyDownload} from '../../utils/getActivationKeyDownload';
import {getActivationKeysActionsItems} from '../../utils/getActivationKeysActionsItems';
import {getActivationKeysDownloadItems} from '../../utils/getActivationKeysDownloadItems';
import DeactivateButton from '../Deactivate';

import Filter from '../Filter';

const dxpNewRedirectLink = PAGE_TYPES.dxpNew.split('_')[1];

const DXPActivationKeysTableHeader = ({
	accountKey,
	licenseKeyDownloadURL,
	project,
	selectedKeys,
	sessionId,
}) => {
	const navigate = useNavigate();
	const [{activationKeys}] = useActivationKeys();

	const [
		activationKeysDownloadStatus,
		setActivationKeysDownloadStatus,
	] = useState('');

	const [deactivateKeysStatus, setDeactivateKeysStatus] = useState('');

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
				DOWNLOADABLE_LICENSE_KEYS.above71DXPVersion(
					firstSelectedKey,
					selectedKey
				) ||
				DOWNLOADABLE_LICENSE_KEYS.below71DXPVersion(
					firstSelectedKey,
					selectedKey
				)
		);

		return keyCanBeDownloaded;
	}, [activationKeys, selectedKeys]);

	const selectedKeysObjects = activationKeys.filter((key) => {
		return selectedKeys.includes(key.id);
	});

	const handleAlertStatus = (hasSuccessfullyDownloadedKeys) => {
		setActivationKeysDownloadStatus(
			hasSuccessfullyDownloadedKeys
				? ALERT_DOWNLOAD_TYPE.success
				: ALERT_DOWNLOAD_TYPE.danger
		);
	};

	const handleRedirectPage = () => navigate(dxpNewRedirectLink);
	const activationKeysActionsItems = getActivationKeysActionsItems(
		accountKey,
		licenseKeyDownloadURL,
		sessionId,
		handleAlertStatus,
		handleRedirectPage
	);

	const activationKeysDownloadItems = getActivationKeysDownloadItems(
		isAbleToDownloadAggregateKeys,
		selectedKeysIDs,
		licenseKeyDownloadURL,
		sessionId,
		handleAlertStatus,
		selectedKeysObjects,
		project.name
	);

	const getCurrentButton = () => {
		if (selectedKeys.length > 1) {
			return (
				<ButtonDropDown
					items={activationKeysDownloadItems}
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
					onClick={async () =>
						getActivationKeyDownload(
							selectedKeys,
							licenseKeyDownloadURL,
							sessionId,
							handleAlertStatus,
							selectedKeysObjects[0]?.productName,
							selectedKeysObjects[0]?.productVersion,
							project.name
						)
					}
				>
					Download
				</Button>
			);
		}

		return (
			<ButtonDropDown
				items={activationKeysActionsItems}
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
				<Filter />

				<div className="align-items-center d-flex ml-auto">
					{!!selectedKeys.length && (
						<>
							<p className="font-weight-semi-bold m-0 ml-auto text-neutral-10">
								{`${selectedKeys.length} Keys Selected`}
							</p>

							<DeactivateButton
								deactivateKeysStatus={deactivateKeysStatus}
								selectedKeys={selectedKeys}
								sessionId={sessionId}
								setDeactivateKeysStatus={
									setDeactivateKeysStatus
								}
							/>
						</>
					)}

					{getCurrentButton()}
				</div>
			</div>

			{activationKeysDownloadStatus && (
				<ClayAlert.ToastContainer>
					<ClayAlert
						autoClose={
							AUTO_CLOSE_ALERT_TIME[activationKeysDownloadStatus]
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

			{deactivateKeysStatus === ALERT_DOWNLOAD_TYPE.success && (
				<ClayAlert.ToastContainer>
					<ClayAlert
						autoClose={AUTO_CLOSE_ALERT_TIME.success}
						className="cp-activation-key-download-alert px-4 py-3 text-paragraph"
						displayType={ALERT_DOWNLOAD_TYPE[deactivateKeysStatus]}
						onClose={() => setDeactivateKeysStatus('')}
					>
						Activation Key(s) were deactivated successfully.
					</ClayAlert>
				</ClayAlert.ToastContainer>
			)}

			{!isAbleToDownloadAggregateKeys && (
				<ClayAlert displayType="info">
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

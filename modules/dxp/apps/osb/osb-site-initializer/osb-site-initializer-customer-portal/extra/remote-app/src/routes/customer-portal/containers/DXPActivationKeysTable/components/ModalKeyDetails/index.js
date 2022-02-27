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
import ClayModal from '@clayui/modal';
import React, {useState} from 'react';
import Button from '../../../../../../common/components/Button';
import {ALERT_DOWNLOAD_TYPE} from '../../../../utils/constants/alertDownloadType';
import {AUTO_CLOSE_ALERT_TIME} from '../../../../utils/constants/autoCloseAlertTime';
import {ALERT_ACTIVATION_AGGREGATED_KEYS_DOWNLOAD_TEXT} from '../../utils/constants/alertAggregateKeysDownloadText';
import {downloadActivationLicenseKey} from '../../utils/downloadActivationLicenseKey';
import TableKeyDetails from '../TableKeyDetails';

const ModalKeyDetails = ({
	assetsPath,
	currentActivationKey,
	licenseKeyDownloadURL,
	observer,
	onClose,
	project,
	sessionId,
}) => {
	const [valueToCopyToClipboard, setValueToCopyToClipboard] = useState('');

	const [
		activationKeysDownloadStatusModal,
		setActivationKeysDownloadStatusModal,
	] = useState('');

	const handleAlertStatus = (hasSuccessfullyDownloadedKeys) => {
		setActivationKeysDownloadStatusModal(
			hasSuccessfullyDownloadedKeys
				? ALERT_DOWNLOAD_TYPE.success
				: ALERT_DOWNLOAD_TYPE.danger
		);
	};

	return (
		<ClayModal center observer={observer} size="lg">
			<div className="pt-4 px-4">
				<div className="d-flex justify-content-between mb-4">
					<div className="flex-row mb-1">
						<h6 className="text-brand-primary">
							ACTIVATION KEY DETAILS
						</h6>

						<h2 className="text-neutral-10">
							{currentActivationKey.name}
						</h2>

						<p>{currentActivationKey.description}</p>
					</div>

					<Button
						appendIcon="times"
						aria-label="close"
						className="align-self-start"
						displayType="unstyled"
						onClick={onClose}
					/>
				</div>

				<TableKeyDetails
					assetsPath={assetsPath}
					currentActivationKey={currentActivationKey}
					setValueToCopyToClipboard={setValueToCopyToClipboard}
				/>

				<div className="d-flex justify-content-end my-4">
					<Button displayType="secondary" onClick={onClose}>
						Close
					</Button>

					<Button
						appendIcon="download"
						className="ml-2"
						onClick={async () => {
							const isAbleToDownloadKey = await downloadActivationLicenseKey(
								currentActivationKey.id,
								licenseKeyDownloadURL,
								sessionId,
								currentActivationKey.productName,
								currentActivationKey.productVersion,
								project.name
							);
							handleAlertStatus(isAbleToDownloadKey);
						}}
					>
						Download Key
					</Button>
				</div>
			</div>

			{valueToCopyToClipboard && (
				<ClayAlert.ToastContainer>
					<ClayAlert
						autoClose={AUTO_CLOSE_ALERT_TIME.success}
						displayType="success"
						onClose={() => setValueToCopyToClipboard(false)}
					>
						{valueToCopyToClipboard} copied to clipboard
					</ClayAlert>
				</ClayAlert.ToastContainer>
			)}

			{activationKeysDownloadStatusModal && (
				<ClayAlert.ToastContainer>
					<ClayAlert
						autoClose={
							AUTO_CLOSE_ALERT_TIME[
								activationKeysDownloadStatusModal
							]
						}
						className="cp-activation-key-download-alert"
						displayType={
							ALERT_DOWNLOAD_TYPE[
								activationKeysDownloadStatusModal
							]
						}
						onClose={() => setActivationKeysDownloadStatusModal('')}
					>
						{
							ALERT_ACTIVATION_AGGREGATED_KEYS_DOWNLOAD_TEXT[
								activationKeysDownloadStatusModal
							]
						}
					</ClayAlert>
				</ClayAlert.ToastContainer>
			)}
		</ClayModal>
	);
};
export default ModalKeyDetails;

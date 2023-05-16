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
import {ClayToggle} from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal from '@clayui/modal';
import React, {useEffect, useState} from 'react';
import i18n from '../../../../../../common/I18n';
import Button from '../../../../../../common/components/Button';
import {useAppPropertiesContext} from '../../../../../../common/contexts/AppPropertiesContext';
import {Liferay} from '../../../../../../common/services/liferay';
import {
	deleteSubscriptionInKey,
	getSubscriptionInKey,
	putSubscriptionInKey,
} from '../../../../../../common/services/liferay/rest/raysource/LicenseKeys';
import {ALERT_DOWNLOAD_TYPE} from '../../../../utils/constants/alertDownloadType';
import {AUTO_CLOSE_ALERT_TIME} from '../../../../utils/constants/autoCloseAlertTime';
import {ALERT_ACTIVATION_AGGREGATED_KEYS_DOWNLOAD_TEXT} from '../../utils/constants/alertAggregateKeysDownloadText';
import {downloadActivationLicenseKey} from '../../utils/downloadActivationLicenseKey';
import TableKeyDetails from '../TableKeyDetails';

const openToast = (title, {type = 'success'} = {}) =>
	Liferay.Util.openToast({
		title: i18n.translate(title),
		type,
	});

const ModalKeyDetails = ({
	currentActivationKey,
	observer,
	onClose,
	project,
	sessionId,
}) => {
	const {provisioningServerAPI} = useAppPropertiesContext();
	const [clipboardValue, setClipboardValue] = useState('');
	const [isLoading, setIsLoading] = useState(false);
	const [
		activationKeysDownloadStatusModal,
		setActivationKeysDownloadStatusModal,
	] = useState('');
	const [toggledSubscription, setToggleSubscription] = useState(false);
	const [hasErrorSubscription, setHasErrorSubscription] = useState(false);

	const handleAlertStatus = (hasSuccessfullyDownloadedKeys) => {
		setActivationKeysDownloadStatusModal(
			hasSuccessfullyDownloadedKeys
				? ALERT_DOWNLOAD_TYPE.success
				: ALERT_DOWNLOAD_TYPE.danger
		);
	};

	const {featureFlags} = useAppPropertiesContext();

	useEffect(() => {
		setIsLoading(true);

		getSubscriptionInKey(
			provisioningServerAPI,
			currentActivationKey.id,
			sessionId
		)
			.then((result) => {
				setToggleSubscription(result);
				setHasErrorSubscription(false);
			})
			.catch(() => {
				openToast('get-subscription-failed', {type: 'danger'});

				setHasErrorSubscription(true);
			})
			.finally(() => {
				setIsLoading(false);
			});
	}, [currentActivationKey.id, provisioningServerAPI, sessionId]);

	const handleToggle = () => setToggleSubscription((toggled) => !toggled);

	const handleSubscriptionInKey = async (status) => {
		handleToggle();

		const fn = status ? deleteSubscriptionInKey : putSubscriptionInKey;

		try {
			await fn(provisioningServerAPI, currentActivationKey.id, sessionId);

			openToast('success');
		} catch {
			setTimeout(() => {
				handleToggle();
				openToast('subscription-failed', {type: 'danger'});
			}, 500);
		}
	};

	return (
		<ClayModal center observer={observer} size="lg">
			<div className="pt-4 px-4">
				<div className="d-flex justify-content-between mb-4">
					<div className="flex-row mb-1">
						<h6 className="text-brand-primary">
							{i18n.translate('activation-key-details')}
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
					currentActivationKey={currentActivationKey}
					setValueToCopyToClipboard={setClipboardValue}
				/>
			</div>

			{featureFlags.includes('LPS-185063') &&
				(isLoading ? (
					<ClayLoadingIndicator />
				) : (
					<>
						<div className="dropdown-divider"></div>

						<div className="pt-3 px-4">
							<ClayToggle
								disabled={hasErrorSubscription}
								label={
									<span className="text-neutral-10">
										{i18n.sub('expiration-notifications')}
									</span>
								}
								onClick={() =>
									handleSubscriptionInKey(toggledSubscription)
								}
								toggled={toggledSubscription}
							/>

							<p className="pt-2 text-neutral-8">
								{i18n.sub(
									'enable-notifications-through-email-when-this-activation-key-is-about-to-expire-x-days-before-x-days-before-and-on-the-day-of-expiration-you-can-unsubscribe-at-any-time',
									[30, 15]
								)}
							</p>
						</div>

						<div className="dropdown-divider"></div>
					</>
				))}

			<div className="pr-4">
				<div className="d-flex justify-content-end my-4">
					<Button displayType="secondary" onClick={onClose}>
						{i18n.translate('close')}
					</Button>

					<Button
						appendIcon="download"
						className="ml-2"
						onClick={async () => {
							const isAbleToDownloadKey = await downloadActivationLicenseKey(
								currentActivationKey.id,
								provisioningServerAPI,
								sessionId,
								currentActivationKey.productName,
								currentActivationKey.productVersion,
								project.name
							);

							handleAlertStatus(isAbleToDownloadKey);
						}}
					>
						{i18n.translate('download-key')}
					</Button>
				</div>
			</div>

			{clipboardValue && (
				<ClayAlert.ToastContainer>
					<ClayAlert
						autoClose={AUTO_CLOSE_ALERT_TIME.success}
						displayType="success"
						onClose={() => setClipboardValue(false)}
					>
						{i18n.sub('x-copied-to-clipboard', [clipboardValue])}
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

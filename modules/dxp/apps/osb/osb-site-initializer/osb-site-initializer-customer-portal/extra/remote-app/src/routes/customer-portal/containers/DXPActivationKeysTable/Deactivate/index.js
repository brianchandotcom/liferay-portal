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
import {Button as ClayButton} from '@clayui/core';
import {useModal} from '@clayui/modal';
import {useState} from 'react';
import {useApplicationProvider} from '../../../../../common/context/AppPropertiesProvider';
import {putDeactivateKeys} from '../../../../../common/services/liferay/rest/raysource/LicenseKeys';
import {
	ALERT_DOWNLOAD_TYPE,
	AUTO_CLOSE_DOWNLOAD_ALERT_TIME,
	STATUS_CODE,
} from '../../../utils/constants';
import DeactivateKeysModal from './Modal';

const DeactivateButton = ({selectedKeys, sessionId, setActivationKeys}) => {
	const {licenseKeyDownloadURL} = useApplicationProvider();
	const [deactivateKeysStatus, setDeactivateKeysStatus] = useState('');
	const [isDeactivating, setIsDeactivating] = useState(false);
	const [isVisibleModal, setIsVisibleModal] = useState(false);
	const {observer, onClose} = useModal({
		onClose: () => {
			setIsVisibleModal(false);
			setDeactivateKeysStatus('');
		},
	});

	const deactivateKeysConfirm = async () => {
		setIsDeactivating(true);
		const licenseKeyIds = selectedKeys
			.map((selectedKey) => `licenseKeyIds=${selectedKey}`)
			.join('&');

		const response = await putDeactivateKeys(
			licenseKeyDownloadURL,
			licenseKeyIds,
			sessionId
		);

		if (response.status === STATUS_CODE.sucess) {
			setIsDeactivating(false);
			setIsVisibleModal(false);
			setActivationKeys((previousActivationKeys) =>
				previousActivationKeys.filter(
					(activationKeys) => !selectedKeys.includes(activationKeys)
				)
			);

			return setDeactivateKeysStatus(ALERT_DOWNLOAD_TYPE.success);
		}

		setIsDeactivating(false);
		setDeactivateKeysStatus(ALERT_DOWNLOAD_TYPE.danger);
	};

	return (
		<>
			{deactivateKeysStatus === ALERT_DOWNLOAD_TYPE.success && (
				<ClayAlert.ToastContainer>
					<ClayAlert
						autoClose={AUTO_CLOSE_DOWNLOAD_ALERT_TIME.success}
						className="cp-activation-key-download-alert px-4 py-3 text-paragraph"
						displayType={ALERT_DOWNLOAD_TYPE[deactivateKeysStatus]}
						onClose={() => setDeactivateKeysStatus('')}
					>
						Activation Key(s) were deactivated successfully.
					</ClayAlert>
				</ClayAlert.ToastContainer>
			)}
			{isVisibleModal && (
				<DeactivateKeysModal
					deactivateKeysConfirm={deactivateKeysConfirm}
					deactivateKeysStatus={deactivateKeysStatus}
					isDeactivating={isDeactivating}
					observer={observer}
					onClose={onClose}
				/>
			)}

			<ClayButton
				className="btn-outline-danger cp-deactivate-button mx-2 px-3 py-2"
				onClick={() => setIsVisibleModal(true)}
			>
				Deactivate
			</ClayButton>
		</>
	);
};

export default DeactivateButton;

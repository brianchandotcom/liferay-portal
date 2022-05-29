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

import {Button as ClayButton} from '@clayui/core';
import {useModal} from '@clayui/modal';
import {useState} from 'react';
import i18n from '../../../../../../common/I18n';
import {useAppPropertiesContext} from '../../../../../../common/contexts/AppPropertiesContext';
import {putDeactivateKeys} from '../../../../../../common/services/liferay/rest/raysource/LicenseKeys';
import {ALERT_DOWNLOAD_TYPE, STATUS_CODE} from '../../../../utils/constants';
import ConfirmationMessageModal from './ConfirmationMessageModal';
import DeactivateKeysModal from './Modal';

const DeactivateButton = ({
	deactivateKeysStatus,
	filterCheckedActivationKeys,
	handleDeactivate,
	sessionId,
	setDeactivateKeysStatus,
}) => {
	const {provisioningServerAPI} = useAppPropertiesContext();
	const [isDeactivating, setIsDeactivating] = useState(false);
	const [isVisibleModal, setIsVisibleModal] = useState(false);
	const [alreadyDeactivated, setAlreadyDeactivated] = useState(false);
	const {observer, onClose} = useModal({
		onClose: () => {
			setIsVisibleModal(false);
			setDeactivateKeysStatus('');
		},
	});

	const deactivateKeysConfirm = async () => {
		setIsDeactivating(true);

		const response = await putDeactivateKeys(
			provisioningServerAPI,
			filterCheckedActivationKeys,
			sessionId
		);

		if (response.status === STATUS_CODE.successNoContent) {
			setIsDeactivating(false);
			setAlreadyDeactivated(true);

			return;
		}

		setIsDeactivating(false);
		setDeactivateKeysStatus(ALERT_DOWNLOAD_TYPE.danger);
	};

	const confirmKeyNoLongerVisible = () => {
		setIsVisibleModal(false);
		setAlreadyDeactivated(false);
		handleDeactivate();

		return setDeactivateKeysStatus(ALERT_DOWNLOAD_TYPE.success);
	};

	return (
		<>
			{isVisibleModal &&
				(alreadyDeactivated ? (
					<ConfirmationMessageModal
						confirmKeyNoLongerVisible={confirmKeyNoLongerVisible}
						observer={observer}
					/>
				) : (
					<DeactivateKeysModal
						deactivateKeysConfirm={deactivateKeysConfirm}
						deactivateKeysStatus={deactivateKeysStatus}
						isDeactivating={isDeactivating}
						observer={observer}
						onClose={onClose}
					/>
				))}

			<ClayButton
				className="btn-outline-danger cp-deactivate-button mx-2 px-3 py-2"
				onClick={() => setIsVisibleModal(true)}
			>
				{i18n.translate('deactivate')}
			</ClayButton>
		</>
	);
};

export default DeactivateButton;

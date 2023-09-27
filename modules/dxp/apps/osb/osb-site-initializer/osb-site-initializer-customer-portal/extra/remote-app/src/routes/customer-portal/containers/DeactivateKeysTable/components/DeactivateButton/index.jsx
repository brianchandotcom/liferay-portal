/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Button as ClayButton} from '@clayui/core';
import {useModal} from '@clayui/modal';
import {useState} from 'react';
import {useNavigate} from 'react-router-dom';
import i18n from '../../../../../../common/I18n';
import {useAppPropertiesContext} from '../../../../../../common/contexts/AppPropertiesContext';
import {putDeactivateKeys} from '../../../../../../common/services/liferay/rest/raysource/LicenseKeys';
import {ALERT_DOWNLOAD_TYPE, STATUS_CODE} from '../../../../utils/constants';
import ConfirmationMessageModal from '../../../ActivationKeysTable/components/Deactivate/ConfirmationMessageModal';
import DeactivateKeysModal from '../../../ActivationKeysTable/components/Deactivate/Modal';

const DeactivateButton = ({
	activationKeysByStatusPaginatedChecked,
	deactivateKeysStatus,
	filterCheckedActivationKeys,
	handleDeactivate,
	sessionId,
	setDeactivateKeysStatus,
	urlPreviousPage,
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
	const navigate = useNavigate();

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

		navigate(urlPreviousPage, {state: {deactivateKeyAlert: true}});

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
				className="mx-2 px-3 py-2"
				disabled={
					activationKeysByStatusPaginatedChecked.length ? false : true
				}
				displayType="primary"
				onClick={() => setIsVisibleModal(true)}
			>
				{` ${
					activationKeysByStatusPaginatedChecked.length > 1
						? i18n.sub('deactivate-x-keys', [
								activationKeysByStatusPaginatedChecked.length,
						  ])
						: i18n.sub('deactivate-x-key', [
								activationKeysByStatusPaginatedChecked.length,
						  ])
				}`}
			</ClayButton>
		</>
	);
};

export default DeactivateButton;

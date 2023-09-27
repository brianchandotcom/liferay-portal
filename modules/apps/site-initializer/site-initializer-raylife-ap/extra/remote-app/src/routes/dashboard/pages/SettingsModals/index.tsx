/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useModal} from '@clayui/modal';
import {useEffect, useState} from 'react';

import {Liferay} from '../../../../common/services/liferay/liferay';
import Modal from '../../../applications/components/Modal';
import WhatsNewStatus from './WhatsNewSettings';

const WhatsNewModal = () => {
	const [visible, setVisible] = useState(false);

	const {observer, onClose} = useModal({
		onClose: () => setVisible(false),
	});

	const ButtonsForSettingsModal = () => (
		<>
			<ClayButton
				className="mr-2 text-uppercase"
				displayType="secondary"
				onClick={() => onClose()}
			>
				Cancel
			</ClayButton>
			<ClayButton className="text-uppercase" displayType="primary">
				Save
			</ClayButton>
		</>
	);

	useEffect(() => {
		Liferay.Util.LocalStorage.removeItem('raylife-ap-storage');

		const handler = () => setVisible(!visible);

		Liferay.on('openSettingsModalEvent', handler);

		return () => Liferay.detach('openSettingsModalEvent', handler);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [visible]);

	return (
		<>
			<Modal
				Buttons={() => <ButtonsForSettingsModal />}
				modalStyle="modal-clay"
				observer={observer}
				size="md"
				title="Settings"
				visible={visible}
			>
				<WhatsNewStatus />
			</Modal>
		</>
	);
};

export default WhatsNewModal;

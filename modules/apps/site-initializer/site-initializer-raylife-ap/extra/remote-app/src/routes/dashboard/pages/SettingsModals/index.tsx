/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
		localStorage.removeItem('raylife-ap-storage');
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

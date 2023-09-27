/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayModalProvider, useModal} from '@clayui/modal';
import React, {useEffect, useState} from 'react';

import ModalBasicWithFieldName from './ModalBasicWithFieldName';

interface IProps extends React.HTMLAttributes<HTMLElement> {
	apiURL: string;
	observer: unknown;
	onClose: () => void;
}

const ModalWithProvider: React.FC<IProps> = ({apiURL}) => {
	const [visibleModal, setVisibleModal] = useState<boolean>();
	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	useEffect(() => {
		const openModal = () => setVisibleModal(true);

		Liferay.on('addObjectView', openModal);

		return () => {
			Liferay.detach('addObjectView', openModal);
		};
	}, []);

	return (
		<ClayModalProvider>
			{visibleModal && (
				<ModalBasicWithFieldName
					apiURL={apiURL}
					inputId="listObjectCustomViewName"
					label={Liferay.Language.get('new-view')}
					observer={observer}
					onClose={onClose}
				/>
			)}
		</ClayModalProvider>
	);
};

export default ModalWithProvider;

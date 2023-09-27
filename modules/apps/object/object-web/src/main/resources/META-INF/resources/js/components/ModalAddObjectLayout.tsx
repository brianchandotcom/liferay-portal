/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayModalProvider, useModal} from '@clayui/modal';
import {Observer} from '@clayui/modal/lib/types';
import React, {useEffect, useState} from 'react';

import ModalBasicWithFieldName from './ModalBasicWithFieldName';
interface IProps extends React.HTMLAttributes<HTMLElement> {
	apiURL: string;
	observer: Observer;
	onClose: () => void;
}

const ModalWithProvider: React.FC<IProps> = ({apiURL}) => {
	const [visibleModal, setVisibleModal] = useState<boolean>(false);
	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	useEffect(() => {
		const openModal = () => setVisibleModal(true);

		Liferay.on('addObjectLayout', openModal);

		return () => {
			Liferay.detach('addObjectLayout', openModal);
		};
	}, []);

	return (
		<ClayModalProvider>
			{visibleModal && (
				<ModalBasicWithFieldName
					apiURL={apiURL}
					inputId="listObjectLayoutName"
					label={Liferay.Language.get('new-layout')}
					observer={observer}
					onClose={onClose}
				/>
			)}
		</ClayModalProvider>
	);
};

export default ModalWithProvider;

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

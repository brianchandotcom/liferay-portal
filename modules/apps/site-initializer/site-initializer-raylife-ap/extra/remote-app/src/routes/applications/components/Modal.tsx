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

import ClayModal from '@clayui/modal';
import React, {ReactNode} from 'react';

type ModalProps = {
	Buttons: () => JSX.Element;
	children: ReactNode;
	modalStyle: string;
	observer: Observer;
	size: any;
	title: string;
	visible: boolean;
};

export declare type Observer = {
	dispatch: (type: ObserverType) => void;
	mutation: [boolean, boolean];
};

export declare enum ObserverType {
	Close = 0,
	Open = 1,
}

const Modal: React.FC<ModalProps> = ({
	Buttons,
	children,
	modalStyle,
	observer,
	size,
	title,
	visible,
}) => {
	return (
		<div className="modal-container">
			{visible && (
				<ClayModal
					className={modalStyle}
					observer={observer}
					size={size}
				>
					<ClayModal.Header>{title}</ClayModal.Header>

					{children}

					<ClayModal.Footer last={<Buttons />} />
				</ClayModal>
			)}
		</div>
	);
};

export default Modal;

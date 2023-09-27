/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

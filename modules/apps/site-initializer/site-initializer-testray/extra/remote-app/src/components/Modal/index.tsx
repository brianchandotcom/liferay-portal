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
import React, {ReactElement} from 'react';

type ModalProps = {
	className?: string;
	first?: ReactElement;
	last?: ReactElement;
	observer: any;
	size?: 'full-screen' | 'lg' | 'sm';
	subtitle?: string;
	title?: string;
	visible: boolean;
} & React.HTMLAttributes<HTMLElement>;

const Modal: React.FC<ModalProps> = ({
	children,
	first,
	last,
	observer,
	size,
	subtitle,
	title,
	visible,
}) => {
	if (!visible) {
		return null;
	}

	return (
		<ClayModal center observer={observer} size={size}>
			<ClayModal.Header>
				<ClayModal.Title>{title}</ClayModal.Title>
			</ClayModal.Header>

			{subtitle && (
				<ClayModal.SubtitleSection>
					<ClayModal.Subtitle className="legend-text mt-2 pl-4 pr-4">
						{subtitle}
					</ClayModal.Subtitle>
				</ClayModal.SubtitleSection>
			)}

			<ClayModal.Body>{children}</ClayModal.Body>

			{first || (last && <ClayModal.Footer first={first} last={last} />)}
		</ClayModal>
	);
};

export default Modal;

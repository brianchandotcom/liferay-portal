/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBadge from '@clayui/badge';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import React from 'react';

interface CardStyleModalProps {
	badgeText?: string;
	body: string;
	buttons: Array<{
		displayType: 'primary' | 'secondary';
		icon?: string;
		label: string;
		onClick?: () => void;
	}>;
	imageSrc: string;
	onCloseModal?: () => void;
	title: string;
}

const CardStyleModal: React.FC<CardStyleModalProps> = ({
	badgeText,
	body,
	buttons,
	imageSrc,
	onCloseModal,
	title,
}) => {
	const {observer, onClose} = useModal({
		onClose: () => {
			onCloseModal?.();
		},
	});

	return (
		<ClayModal center observer={observer}>
			<ClayModal.Body className="c-p-0">
				<ClayButton
					aria-label={Liferay.Language.get('close')}
					className="close"
					displayType="unstyled"
					onClick={onClose}
				>
					<ClayIcon symbol="times" />
				</ClayButton>

				<div className="aspect-ratio aspect-ratio-16-to-9 bg-primary-l3">
					<div className="aspect-ratio-item aspect-ratio-item-center-middle aspect-ratio-item-fluid">
						<img alt="" src={imageSrc} />
					</div>
				</div>

				{badgeText ? (
					<ClayBadge
						className="c-mt-4 c-mx-4 text-uppercase"
						displayType="primary"
						label={badgeText}
						translucent
					/>
				) : null}

				<ClayModal.Title className="c-mx-4">{title}</ClayModal.Title>

				<p className="c-m-4">{body}</p>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						{buttons.map((button, index) => (
							<ClayButton
								displayType={button.displayType}
								key={index}
								onClick={() => {
									onClose();

									if (button.onClick) {
										button.onClick();
									}
								}}
							>
								{button.icon ? (
									<ClayIcon
										className="inline-item inline-item-before"
										symbol={button.icon}
									/>
								) : null}

								{button.label}
							</ClayButton>
						))}
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
};

export default CardStyleModal;

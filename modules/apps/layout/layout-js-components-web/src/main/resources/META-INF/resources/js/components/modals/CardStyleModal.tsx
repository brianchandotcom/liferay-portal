/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import ClayModal, {useModal} from '@clayui/modal';
import {FeatureIndicator} from 'frontend-js-components-web';
import React from 'react';

interface CardStyleModalProps {
	body: string;
	buttons: Array<{
		displayType: 'primary' | 'secondary';
		href?: string;
		icon?: string;
		label: string;
		onClick?: () => void;
	}>;
	imageSrc: string;
	onCloseModal?: () => void;
	showEnterpriseIndicator?: boolean;
	title: string;
}

const CardStyleModal: React.FC<CardStyleModalProps> = ({
	body,
	buttons,
	imageSrc,
	onCloseModal = () => {},
	showEnterpriseIndicator,
	title,
}) => {
	const {observer, onClose} = useModal({
		onClose: onCloseModal,
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

				{showEnterpriseIndicator ? (
					<FeatureIndicator className="mt-4 mx-4" type="enterprise" />
				) : null}

				<ClayModal.Title className="c-mx-4">{title}</ClayModal.Title>

				<p className="c-m-4 text-secondary">{body}</p>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						{buttons.map((button, index) =>
							button.href ? (
								<ClayLink
									className={`btn btn-${button.displayType}`}
									displayType="unstyled"
									href={button.href}
									key={index}
									role="button"
									target="_blank"
								>
									{button.label}

									{button.icon ? (
										<ClayIcon
											className="inline-item inline-item-after"
											symbol={button.icon}
										/>
									) : null}
								</ClayLink>
							) : (
								<ClayButton
									displayType={button.displayType}
									key={index}
									onClick={() => {
										if (button.onClick) {
											button.onClick();
										}

										onClose();
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
							)
						)}
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
};

export default CardStyleModal;

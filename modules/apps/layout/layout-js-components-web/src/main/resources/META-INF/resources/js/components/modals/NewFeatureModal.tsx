/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import React from 'react';

interface NewFeatureModalProps {
	body: string;
	heading: string;
	imageSrc: string;
	onCloseModal?: () => void;
	onPrimaryButtonClick?: () => void;
	primaryButtonIcon?: string;
	primaryButtonLabel?: string;
	secondaryButtonLabel?: string;
}

const NewFeatureModal: React.FC<NewFeatureModalProps> = ({
	body,
	heading,
	imageSrc,
	onCloseModal,
	onPrimaryButtonClick,
	primaryButtonIcon,
	primaryButtonLabel,
	secondaryButtonLabel,
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

				<ClayModal.Title className="c-mx-4">{heading}</ClayModal.Title>

				<p className="c-m-4">{body}</p>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						{secondaryButtonLabel ? (
							<ClayButton
								displayType="secondary"
								onClick={onClose}
							>
								{secondaryButtonLabel}
							</ClayButton>
						) : null}

						{primaryButtonLabel ? (
							<ClayButton
								displayType="primary"
								onClick={() => {
									onPrimaryButtonClick?.();
									onClose();
								}}
							>
								{primaryButtonIcon ? (
									<ClayIcon
										className="inline-item inline-item-before"
										symbol={primaryButtonIcon}
									/>
								) : null}

								{primaryButtonLabel}
							</ClayButton>
						) : null}
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
};

export default NewFeatureModal;

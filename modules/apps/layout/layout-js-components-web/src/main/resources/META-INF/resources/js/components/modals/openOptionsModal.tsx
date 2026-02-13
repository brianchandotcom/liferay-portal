/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayRadio, ClayRadioGroup} from '@clayui/form';
import ClayModal from '@clayui/modal';
import {openModal} from 'frontend-js-components-web';
import React, {useState} from 'react';

type NonEmptyArray<T> = [T, ...T[]];

type Option = {
	label: string;
	value: string;
};

type Props = {
	buttonLabel?: string;
	center?: boolean;
	defaultValue?: string;
	onCloseFocusElement?: HTMLButtonElement | null;
	options: NonEmptyArray<Option>;
	text?: string;
	title: string;
};

export default function openOptionsModal({
	buttonLabel = Liferay.Language.get('done'),
	center = true,
	defaultValue,
	onCloseFocusElement,
	options,
	text,
	title,
}: Props): Promise<Option['value'] | null> {
	return new Promise((resolve) => {
		openModal({
			center,
			contentComponent: ({closeModal}: {closeModal: () => void}) =>
				ModalContent({
					buttonLabel,
					defaultValue,
					onCancel: () => {
						closeModal();

						resolve(null);
					},
					onConfirm: (selectedValue: string) => {
						closeModal();

						resolve(selectedValue);
					},
					options,
					text,
					title,
				}),
			onClose: () => {
				if (onCloseFocusElement) {
					onCloseFocusElement.focus();
				}
			},
		});
	});
}

function ModalContent({
	buttonLabel,
	defaultValue,
	onCancel,
	onConfirm,
	options,
	text,
	title,
}: {
	buttonLabel: string;
	defaultValue?: string;
	onCancel: () => void;
	onConfirm: (value: string) => void;
	options: NonEmptyArray<Option>;
	text?: string;
	title: string;
}) {
	const [selectedOption, setSelectedOption] = useState(
		defaultValue ?? options[0].value
	);

	return (
		<>
			<ClayModal.Header>{title}</ClayModal.Header>

			<ClayModal.Body>
				{text ? <p className="mb-3">{text}</p> : null}

				<ClayRadioGroup
					onChange={(value) => setSelectedOption(value as string)}
					value={selectedOption}
				>
					{options.map((option) => (
						<ClayRadio
							key={String(option.value)}
							label={option.label}
							value={option.value}
						/>
					))}
				</ClayRadioGroup>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType="secondary" onClick={onCancel}>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							displayType="primary"
							onClick={() => onConfirm(selectedOption)}
						>
							{buttonLabel}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
}

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayRadio, ClayRadioGroup} from '@clayui/form';
import ClayModal, {useModal} from '@clayui/modal';
import React, {useState} from 'react';

export const IMPORT_OPTIONS = [
	{
		label: Liferay.Language.get('do-not-add-existing-items'),
		value: 'do_not_import',
	},
	{
		label: Liferay.Language.get('overwrite-existing-items'),
		value: 'overwrite',
	},
	{
		label: Liferay.Language.get('keep-both'),
		value: 'keep_both',
	},
] as const;

const DEFAULT_IMPORT_OPTION = IMPORT_OPTIONS[0].value;

type ImportOption = (typeof IMPORT_OPTIONS)[number];
export type OverwriteStrategy = ImportOption['value'];

interface ImportOptionsModalProps {
	onCloseModal: () => void;
	onImport: (overwriteStrategy?: OverwriteStrategy) => void;
}

export default function ImportOptionsModal({
	onCloseModal,
	onImport,
}: ImportOptionsModalProps) {
	const [selectedOption, setSelectedOption] = useState<OverwriteStrategy>(
		DEFAULT_IMPORT_OPTION
	);

	const {observer, onClose} = useModal({
		onClose: onCloseModal,
	});

	return (
		<ClayModal observer={observer}>
			<ModalContent
				onClose={onClose}
				onImport={onImport}
				onOptionChange={(value: OverwriteStrategy) =>
					setSelectedOption(value)
				}
				selectedOption={selectedOption}
			/>
		</ClayModal>
	);
}

interface ModalContentProps {
	onClose: () => void;
	onImport: (overwriteStrategy?: OverwriteStrategy) => void;
	onOptionChange: (value: OverwriteStrategy) => void;
	selectedOption: OverwriteStrategy;
}

export function ModalContent({
	onClose,
	onImport,
	onOptionChange,
	selectedOption,
}: ModalContentProps) {
	return (
		<>
			<ClayModal.Header
				closeButtonAriaLabel={Liferay.Language.get('close')}
			>
				{Liferay.Language.get('manage-existing-items')}
			</ClayModal.Header>

			<ModalBody
				onOptionChange={onOptionChange}
				selectedOption={selectedOption}
			/>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType="secondary" onClick={onClose}>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							onClick={() => {
								onClose();
								onImport(selectedOption);
							}}
						>
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
}

interface ModalBodyProps {
	onOptionChange: (value: OverwriteStrategy) => void;
	selectedOption?: OverwriteStrategy;
}

export function ModalBody({onOptionChange, selectedOption}: ModalBodyProps) {
	return (
		<ClayModal.Body>
			<p className="c-mb-4 text-secondary">
				{Liferay.Language.get('one-or-more-items-already-exist')}
				&nbsp;
				{Liferay.Language.get('what-action-do-you-want-to-take')}
			</p>

			<ClayRadioGroup
				defaultValue={selectedOption ?? DEFAULT_IMPORT_OPTION}
				onChange={(value: string | number) =>
					onOptionChange(value as OverwriteStrategy)
				}
			>
				{IMPORT_OPTIONS.map((option) => (
					<ClayRadio
						key={option.value}
						label={option.label}
						value={option.value}
					/>
				))}
			</ClayRadioGroup>
		</ClayModal.Body>
	);
}

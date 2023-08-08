/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayRadio, ClayRadioGroup} from '@clayui/form';
import ClayModal from '@clayui/modal';
import React, {ReactText, useState} from 'react';

interface Props {
	observer: any;
	onImport: (overwriteStrategy?: OverwriteStrategy) => void;
	onOpenChange: (value: boolean) => void;
}

const OPTIONS = [
	{
		label: Liferay.Language.get('do-not-import-existing-items'),
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

export type OverwriteStrategy = typeof OPTIONS[number]['value'];

const DEFAULT_OPTION = OPTIONS[0];

function ImportOptionsModal({observer, onImport, onOpenChange}: Props) {
	const [selectedOption, setSelectedOption] = useState<OverwriteStrategy>(
		DEFAULT_OPTION.value
	);

	return (
		<ClayModal observer={observer}>
			<ClayModal.Header>
				{Liferay.Language.get('import-options')}
			</ClayModal.Header>

			<ClayModal.Body>
				<p className="c-mb-4 text-secondary">
					{Liferay.Language.get(
						'one-or-more-items-from-the-zip-already-exist-in-this-location.-what-action-do-you-want-to-take?'
					)}
				</p>

				<ClayRadioGroup
					defaultValue={DEFAULT_OPTION.value}
					onChange={(value: ReactText) =>
						setSelectedOption(value as OverwriteStrategy)
					}
				>
					{OPTIONS.map((option) => (
						<ClayRadio
							key={option.value}
							label={option.label}
							value={option.value}
						/>
					))}
				</ClayRadioGroup>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={() => onOpenChange(false)}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							onClick={() => {
								onImport(selectedOption);
								onOpenChange(false);
							}}
						>
							{Liferay.Language.get('import')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}

export default ImportOptionsModal;

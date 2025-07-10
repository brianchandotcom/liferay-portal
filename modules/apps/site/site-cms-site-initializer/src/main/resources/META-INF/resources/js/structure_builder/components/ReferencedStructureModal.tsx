/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import ClayMultiSelect from '@clayui/multi-select';
import classNames from 'classnames';
import {FieldFeedback, useId} from 'frontend-js-components-web';
import React, {useState} from 'react';

import {useCache} from '../contexts/CacheContext';
import {useSelector} from '../contexts/StateContext';
import selectStructureERC from '../selectors/selectStructureERC';
import selectStructureUuid from '../selectors/selectStructureUuid';
import {ObjectDefinitions} from '../types/ObjectDefinition';
import {ReferencedStructure, Structure} from '../types/Structure';
import {Uuid} from '../types/Uuid';
import {buildReferencedStructure} from '../utils/buildStructure';
import getRandomName from '../utils/getRandomName';

type Item = {
	label: string;
	value: string;
};

export default function ReferencedStructureModal({
	onAdd,
	onCloseModal,
}: {
	onAdd: (referencedStructures: ReferencedStructure[]) => void;
	onCloseModal: () => void;
}) {
	const {observer, onClose} = useModal({
		onClose: () => onCloseModal(),
	});

	const structureUuid = useSelector(selectStructureUuid);
	const structureERC = useSelector(selectStructureERC);

	const {data: objectDefinitions, status} = useCache('object-definitions');

	const [selection, setSelection] = useState<Item[]>([]);
	const [hasError, setHasError] = useState(false);

	const id = useId();

	return (
		<ClayModal observer={observer}>
			<ClayModal.Header>
				{Liferay.Language.get('referenced-structure')}
			</ClayModal.Header>

			<ClayModal.Body>
				<p className="text-secondary">
					{Liferay.Language.get(
						'select-the-structures-to-be-referenced'
					)}
				</p>

				<ClayForm.Group className={classNames({'has-error': hasError})}>
					<label htmlFor={id}>
						{Liferay.Language.get('structures')}

						<ClayIcon
							className="ml-1 reference-mark"
							focusable="false"
							role="presentation"
							symbol="asterisk"
						/>
					</label>

					<ClayMultiSelect
						id={id}
						items={selection}
						loadingState={status === 'saving' ? 1 : 0}
						onItemsChange={(selection: Item[]) => {
							setSelection(selection);

							setHasError(!selection.length);
						}}
						sourceItems={getItems(objectDefinitions)}
					/>

					{hasError ? (
						<FieldFeedback
							errorMessage={Liferay.Language.get(
								'this-field-is-required'
							)}
						/>
					) : null}
				</ClayForm.Group>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={onClose}
							type="button"
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							displayType="primary"
							onClick={() => {
								if (!selection.length) {
									setHasError(true);

									return;
								}

								const structures = buildStructures(
									selection,
									objectDefinitions,
									structureUuid,
									structureERC
								);

								onAdd(structures);

								onCloseModal();
							}}
						>
							{Liferay.Language.get('add')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}

function getItems(objectDefinitions: ObjectDefinitions): Item[] {
	return Object.values(objectDefinitions)
		.filter(
			(objectDefinition) =>
				objectDefinition.objectFolderExternalReferenceCode !==
				'L_CMS_STRUCTURE_REPEATABLE_GROUPS'
		)
		.map((objectDefinition) => ({
			label:
				objectDefinition.label[
					Liferay.ThemeDisplay.getDefaultLanguageId()
				] || '',
			value: objectDefinition.externalReferenceCode,
		}));
}

function buildStructures(
	selection: Item[],
	objectDefinitions: ObjectDefinitions,
	mainStructureUuid: Uuid,
	mainStructureERC: Structure['erc']
) {
	const ercs = selection.map(({value}) => value);

	return ercs.map((erc) => {
		const structure = buildReferencedStructure({
			ancestors: [mainStructureERC],
			erc,
			objectDefinitions,
			parent: mainStructureUuid,
			relationshipName: getRandomName(),
		});

		return structure;
	});
}

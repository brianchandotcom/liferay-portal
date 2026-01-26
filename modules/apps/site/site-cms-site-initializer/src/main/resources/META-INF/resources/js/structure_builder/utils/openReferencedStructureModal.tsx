/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal from '@clayui/modal';
import ClayMultiSelect from '@clayui/multi-select';
import classNames from 'classnames';
import {FieldFeedback, openModal, useId} from 'frontend-js-components-web';
import React, {Dispatch, useState} from 'react';

import {
	ObjectDefinition,
	ObjectDefinitions,
} from '../../common/types/ObjectDefinition';
import getLocalizedValue from '../../common/utils/getLocalizedValue';
import {CacheStatus} from '../contexts/CacheContext';
import {Action} from '../contexts/StateContext';
import {ReferencedStructure, Structure} from '../types/Structure';
import {Uuid} from '../types/Uuid';
import {buildReferencedStructure} from './buildStructure';
import getRandomName from './getRandomName';

type Item = {
	label: string;
	value: string;
};

export default function openReferencedStructureModal({
	dispatch,
	objectDefinitions,
	status,
	structure,
}: {
	dispatch: Dispatch<Action>;
	objectDefinitions: ObjectDefinitions;
	status: CacheStatus;
	structure: Structure;
}) {
	const addReferencedStructures = (
		referencedStructures: ReferencedStructure[]
	) =>
		dispatch({
			referencedStructures,
			type: 'add-referenced-structures',
		});

	openModal({
		center: true,
		contentComponent: ({closeModal}: {closeModal: () => void}) => (
			<ReferencedStructureModal
				closeModal={closeModal}
				objectDefinitions={objectDefinitions}
				onAdd={addReferencedStructures}
				status={status}
				structure={structure}
			/>
		),
	});
}

function ReferencedStructureModal({
	closeModal,
	objectDefinitions,
	onAdd,
	status,
	structure,
}: {
	closeModal: () => void;
	objectDefinitions: ObjectDefinitions;
	onAdd: (referencedStructures: ReferencedStructure[]) => void;
	status: CacheStatus;
	structure: Structure;
}) {
	const [selection, setSelection] = useState<Item[]>([]);
	const [hasError, setHasError] = useState(false);

	const id = useId();

	return (
		<>
			<ClayModal.Header
				closeButtonAriaLabel={Liferay.Language.get('close')}
			>
				{Liferay.Language.get('referenced-content-structure')}
			</ClayModal.Header>

			<ClayModal.Body>
				<p className="text-secondary">
					{Liferay.Language.get(
						'select-the-content-structures-to-be-referenced'
					)}
				</p>

				<ClayForm.Group className={classNames({'has-error': hasError})}>
					<label htmlFor={id}>
						{Liferay.Language.get('content-structures')}

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
						sourceItems={getItems(objectDefinitions, structure.erc)}
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
							onClick={closeModal}
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
									structure.uuid,
									structure.erc
								);

								onAdd(structures);

								closeModal();
							}}
						>
							{Liferay.Language.get('add')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
}

function getItems(
	objectDefinitions: ObjectDefinitions,
	mainStructureERC: Structure['erc']
): Item[] {
	const items = [];

	// Exclude objectDefinitions that are repeatable groups,
	// main objectDefinition itself and objectDefinitions
	// that have a circular dependency with the main one

	for (const objectDefinition of Object.values(objectDefinitions)) {
		if (
			objectDefinition.externalReferenceCode === mainStructureERC ||
			objectDefinition.objectFolderExternalReferenceCode ===
				'L_CMS_STRUCTURE_REPEATABLE_GROUPS' ||
			hasCircularDependency(
				objectDefinition,
				objectDefinitions,
				mainStructureERC
			)
		) {
			continue;
		}

		items.push({
			label: getLocalizedValue(objectDefinition.label),
			value: objectDefinition.externalReferenceCode,
		});
	}

	return items;
}

function hasCircularDependency(
	objectDefinition: ObjectDefinition,
	objectDefinitions: ObjectDefinitions,
	mainStructureERC: Structure['erc']
) {
	if (!objectDefinition.objectRelationships?.length) {
		return false;
	}

	for (const relationship of objectDefinition.objectRelationships) {
		if (
			relationship.objectDefinitionExternalReferenceCode2 ===
			mainStructureERC
		) {
			return true;
		}

		const hasDependency = hasCircularDependency(
			objectDefinitions[
				relationship.objectDefinitionExternalReferenceCode2
			],
			objectDefinitions,
			mainStructureERC
		);

		if (hasDependency) {
			return true;
		}
	}

	return false;
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

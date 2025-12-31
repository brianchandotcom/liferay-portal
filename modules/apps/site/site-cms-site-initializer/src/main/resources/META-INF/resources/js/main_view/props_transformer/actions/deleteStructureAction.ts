/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';

import ApiHelper from '../../../common/services/ApiHelper';
import StructureService from '../../../common/services/StructureService';
import {ObjectDefinition} from '../../../common/types/ObjectDefinition';
import {openCMSModal} from '../../../common/utils/openCMSModal';
import ObjectDefinitionService from '../../../structure_builder/services/ObjectDefinitionService';
import DeleteStructureModalContent from '../../modal/DeleteStructureModalContent';

const REPEATABLE_GROUPS_FOLDER_ERC = 'L_CMS_STRUCTURE_REPEATABLE_GROUPS';

export default async function deleteStructureAction({
	getObjectDefinitionDeleteInfoURL,
	loadData,
	name,
	relationships,
	status,
	structureId,
}: {
	getObjectDefinitionDeleteInfoURL: string;
	loadData: () => {};
	name: string;
	relationships: ObjectDefinition['objectRelationships'];
	status: number;
	structureId: number;
}) {
	const deleteStructure = async ({
		repeatableGroupIds,
	}: {
		repeatableGroupIds?: number[];
	} = {}) => {
		const response = await StructureService.deleteStructure({
			id: structureId,
			repeatableGroupIds,
		});

		if (response?.error) {
			openToast({
				message: Liferay.Language.get('an-unexpected-error-occurred'),
				type: 'danger',
			});
		}
		else {
			openToast({
				message: sub(
					Liferay.Language.get('x-was-deleted-successfully'),
					`<strong>${Liferay.Util.escapeHTML(name)}</strong>`
				),
				type: 'success',
			});

			loadData();
		}
	};

	if (status !== 0) {
		await deleteStructure();

		return;
	}

	const {data, error} = await ApiHelper.get<{
		hasObjectRelationship: boolean;
		objectEntriesCount: number;
	}>(getObjectDefinitionDeleteInfoURL);

	if (!data || error) {
		return;
	}

	const {hasObjectRelationship, objectEntriesCount} = data;

	const {referencedStructureIds, repeatableGroupIds} =
		await classifyRelationships(relationships);

	if (
		!canBeDeleted(
			hasObjectRelationship,
			referencedStructureIds,
			repeatableGroupIds
		)
	) {
		openCMSModal({
			bodyHTML: `<p>${sub(
				Liferay.Language.get(
					'x-is-currently-referenced-by-or-referencing-other-content-structures,-and-so-cannot-be-deleted'
				),
				`<strong>${Liferay.Util.escapeHTML(name)}</strong>`
			)}</p>`,
			buttons: [
				{
					displayType: 'warning',
					label: Liferay.Language.get('ok'),
					onClick: ({processClose}: {processClose: Function}) => {
						processClose();
					},
				},
			],
			size: 'md',
			status: 'warning',
			title: Liferay.Language.get('deletion-not-allowed'),
		});

		return;
	}

	openCMSModal({
		contentComponent: ({closeModal}: {closeModal: () => void}) =>
			DeleteStructureModalContent({
				closeModal,
				name,
				onDelete: () => deleteStructure({repeatableGroupIds}),
				usesCount: objectEntriesCount,
			}),
		size: 'md',
		status: 'danger',
	});
}

async function classifyRelationships(
	objectRelationships: ObjectDefinition['objectRelationships']
): Promise<{
	referencedStructureIds: number[];
	repeatableGroupIds: number[];
}> {
	const referencedStructureIds: number[] = [];
	const repeatableGroupIds: number[] = [];

	if (!objectRelationships?.length) {
		return {referencedStructureIds, repeatableGroupIds};
	}

	// Get all related object definitions

	const objectDefinitions =
		await ObjectDefinitionService.getRelatedObjectDefinitions({
			objectRelationships,
		});

	for (const objectDefinition of objectDefinitions) {

		// If it's a referenced structure, just push id to proper array

		if (
			objectDefinition.objectFolderExternalReferenceCode !==
			REPEATABLE_GROUPS_FOLDER_ERC
		) {
			referencedStructureIds.push(objectDefinition.id!);

			continue;
		}

		// If it's a repeatable group, push id to proper array and get nested ones

		if (objectDefinition.objectRelationships?.length) {
			const {repeatableGroupIds: nestedIds} = await classifyRelationships(
				objectDefinition.objectRelationships
			);

			repeatableGroupIds.push(...nestedIds);
		}

		repeatableGroupIds.push(objectDefinition.id!);
	}

	return {referencedStructureIds, repeatableGroupIds};
}

function canBeDeleted(
	hasObjectRelationship: boolean,
	referencedStructureIds: number[],
	repeatableGroupIds: number[]
) {

	// If it does not have relationships but hasObjectRelationship is true,
	// it means it's being referenced from another structure

	if (
		hasObjectRelationship &&
		!repeatableGroupIds.length &&
		!referencedStructureIds.length
	) {
		return false;
	}

	// Return whether it has referenced structures or not

	if (referencedStructureIds.length) {
		return false;
	}

	return true;
}

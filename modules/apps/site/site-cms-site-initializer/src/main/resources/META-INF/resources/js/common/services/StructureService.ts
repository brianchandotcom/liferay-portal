/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {State} from '../../structure_builder/contexts/StateContext';
import {Structure} from '../../structure_builder/types/Structure';
import buildGroupObjectDefinitions from '../../structure_builder/utils/buildGroupObjectDefinitions';
import buildObjectDefinition from '../../structure_builder/utils/buildObjectDefinition';
import getRandomId from '../../structure_builder/utils/getRandomId';
import ApiHelper from './ApiHelper';

async function createStructure({
	children,
	erc = getRandomId(),
	label,
	name,
	spaces,
	status,
	workflows,
}: {
	children: Structure['children'];
	erc?: Structure['erc'];
	label: Structure['label'];
	name: Structure['name'];
	spaces: Structure['spaces'];
	status: Structure['status'];
	workflows: Structure['workflows'];
}) {

	// Publish object definitions for repeatable groups

	const objectDefinitions = buildGroupObjectDefinitions({children});

	for (const objectDefinition of objectDefinitions) {
		const {error} = await ApiHelper.put(
			`/o/object-admin/v1.0/object-definitions/by-external-reference-code/${objectDefinition.externalReferenceCode}`,
			objectDefinition
		);

		if (error) {
			return {
				data: null,
				error: Liferay.Language.get(
					'an-unexpected-error-occurred-while-saving-or-publishing-the-content-structure'
				),
			};
		}
	}

	// Publish the main object definition

	const mainObjectDefinition = buildObjectDefinition({
		children,
		erc,
		label,
		name,
		spaces,
		status,
		workflows,
	});

	return await ApiHelper.post<{id: number}>(
		'/o/object-admin/v1.0/object-definitions',
		mainObjectDefinition
	);
}

async function updateStructure({
	children,
	erc,
	history,
	id,
	label,
	name,
	spaces,
	status,
	workflows,
}: {
	children: Structure['children'];
	erc: Structure['erc'];
	history: State['history'];
	id: Structure['id'];
	label: Structure['label'];
	name: Structure['name'];
	spaces: Structure['spaces'];
	status: Structure['status'];
	workflows: Structure['workflows'];
}) {
	const groupObjectDefinitions = buildGroupObjectDefinitions({children});

	const mainObjectDefinition = buildObjectDefinition({
		children,
		erc,
		id,
		label,
		name,
		spaces,
		status,
		workflows,
	});

	const pathMain = Liferay.ThemeDisplay.getPathMain();

	const formData = new FormData();

	formData.append('objectDefinition', JSON.stringify(mainObjectDefinition));

	formData.append(
		'deletedObjectRelationshipERCs',
		history.deletedRelationshipERCs.join(',')
	);

	formData.append(
		'repeatableGroupObjectDefinitions',
		JSON.stringify(groupObjectDefinitions)
	);

	formData.append(
		'deletedRepeatableGroupsERCs',
		history.deletedGroupERCs.join(',')
	);

	const response = await ApiHelper.postFormData(
		formData,
		`${pathMain}/cms/update-structure`
	);

	if (response?.error) {
		return {
			error: Liferay.Language.get(
				'an-unexpected-error-occurred-while-saving-or-publishing-the-content-structure'
			),
		};
	}

	return response;
}

async function deleteStructure({id}: {id: Structure['id']}) {
	const pathMain = Liferay.ThemeDisplay.getPathMain();

	const formData = new FormData();

	formData.append('objectDefinitionId', String(id));

	const response = await ApiHelper.postFormData(
		formData,
		`${pathMain}/cms/delete-structure`
	);

	if (response?.error) {
		return {error: response.error};
	}
}

async function updateStructureWorkflow({
	structureIds,
	workflow,
}: {
	structureIds: string[];
	workflow: string;
}) {
	return {
		error: false,
		structureIds,
		workflow,
	};
}

export default {
	createStructure,
	deleteStructure,
	updateStructure,
	updateStructureWorkflow,
};

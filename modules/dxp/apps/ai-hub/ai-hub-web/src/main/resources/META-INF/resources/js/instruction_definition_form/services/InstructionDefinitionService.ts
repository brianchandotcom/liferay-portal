/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

import {InstructionDefinition} from '../types/InstructionDefinition';

const INSTRUCTION_DEFINITION_BASE_URI = '/o/ai-hub/instruction-definitions';

const INSTRUCTION_DEFINITION_BY_ERC_URI = `${INSTRUCTION_DEFINITION_BASE_URI}/by-external-reference-code/`;

async function getInstructionDefinition(externalReferenceCode: string) {
	const response = await fetch(
		`${INSTRUCTION_DEFINITION_BY_ERC_URI}${externalReferenceCode}`,
		{
			method: 'GET',
		}
	);

	if (!response.ok) {
		throw new Error();
	}

	return response.json();
}

async function postInstructionDefinition(
	instructionDefinition: InstructionDefinition
) {
	const response = await fetch(INSTRUCTION_DEFINITION_BASE_URI, {
		body: JSON.stringify(instructionDefinition),
		headers: {
			'Content-Type': 'application/json',
		},
		method: 'POST',
	});

	if (!response.ok) {
		const errorBody = await response.json().catch(() => ({}));

		throw new Error(errorBody?.detail || errorBody?.title || '');
	}

	return response.json();
}

async function putInstructionDefinition(
	instructionDefinition: InstructionDefinition
) {
	const response = await fetch(
		`${INSTRUCTION_DEFINITION_BY_ERC_URI}${instructionDefinition.externalReferenceCode}`,
		{
			body: JSON.stringify(instructionDefinition),
			headers: {
				'Content-Type': 'application/json',
			},
			method: 'PUT',
		}
	);

	return response.json();
}

export {
	getInstructionDefinition,
	postInstructionDefinition,
	putInstructionDefinition,
};

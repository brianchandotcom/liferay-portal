/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type IdentifierField = 'classPK' | 'externalReferenceCode';

export type MappingMode = 'literal' | 'content' | 'context' | 'backend';

export interface IContentMappedTokenValue {
	className: string;
	classPK: string;
	externalReferenceCode: string;
	fieldId: IdentifierField | '';
	source?: 'content';
	title?: string;
}

export interface IContextMappedTokenValue {
	fieldId: IdentifierField | '';
	source: 'context';
}

export interface IBackendMappedTokenValue {
	source: 'backend-resolved';
}

export type IMappedTokenValue =
	| IBackendMappedTokenValue
	| IContentMappedTokenValue
	| IContextMappedTokenValue;

export type TokenMapping = string | IMappedTokenValue;

export function isMappedTokenValue(
	value: TokenMapping
): value is IMappedTokenValue {
	return typeof value === 'object' && value !== null;
}

export function isContextMapped(
	value: IMappedTokenValue
): value is IContextMappedTokenValue {
	return value.source === 'context';
}

export function isBackendMapped(
	value: IMappedTokenValue
): value is IBackendMappedTokenValue {
	return value.source === 'backend-resolved';
}

export function getMappingMode(value: TokenMapping): MappingMode {
	if (!isMappedTokenValue(value)) {
		return 'literal';
	}

	if (isBackendMapped(value)) {
		return 'backend';
	}

	return isContextMapped(value) ? 'context' : 'content';
}

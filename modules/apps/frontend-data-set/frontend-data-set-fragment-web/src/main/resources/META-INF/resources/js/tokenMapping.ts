/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type IdentifierField = 'classPK' | 'externalReferenceCode';

export enum EMappingMode {
	AUTO_RESOLVED = 'autoResolved',
	CONTENT = 'content',
	CONTEXT = 'context',
	LITERAL = 'literal',
}

export interface IContentMappedTokenValue {
	className: string;
	classPK: string;
	externalReferenceCode: string;
	fieldId: IdentifierField | '';
	mappingMode: EMappingMode.CONTENT;
	title?: string;
}

export interface IContextMappedTokenValue {
	fieldId: IdentifierField | '';
	mappingMode: EMappingMode.CONTEXT;
}

export interface IAutoResolvedTokenValue {
	mappingMode: EMappingMode.AUTO_RESOLVED;
}

export type IMappedTokenValue =
	| IAutoResolvedTokenValue
	| IContentMappedTokenValue
	| IContextMappedTokenValue;

export type TokenMapping = string | IMappedTokenValue;

export function isMappedTokenValue(
	value: TokenMapping
): value is IMappedTokenValue {
	return typeof value === 'object' && value !== null;
}

export function isAutoResolved(
	value: TokenMapping
): value is IAutoResolvedTokenValue {
	return (
		isMappedTokenValue(value) &&
		value.mappingMode === EMappingMode.AUTO_RESOLVED
	);
}

export function isContentMapped(
	value: TokenMapping
): value is IContentMappedTokenValue {
	return (
		isMappedTokenValue(value) && value.mappingMode === EMappingMode.CONTENT
	);
}

export function isContextMapped(
	value: TokenMapping
): value is IContextMappedTokenValue {
	return (
		isMappedTokenValue(value) && value.mappingMode === EMappingMode.CONTEXT
	);
}

export function getMappingMode(value: TokenMapping): EMappingMode {
	if (!isMappedTokenValue(value)) {
		return EMappingMode.LITERAL;
	}

	return value.mappingMode;
}

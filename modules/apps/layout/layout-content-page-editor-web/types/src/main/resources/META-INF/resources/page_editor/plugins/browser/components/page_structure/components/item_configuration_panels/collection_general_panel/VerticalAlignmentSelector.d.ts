/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

declare const VERTICAL_ALIGNMENT_OPTIONS: readonly [
	{
		readonly label: string;
		readonly value: 'start';
	},
	{
		readonly label: string;
		readonly value: 'center';
	},
	{
		readonly label: string;
		readonly value: 'end';
	}
];
export declare type VerticalAlignmentOption = typeof VERTICAL_ALIGNMENT_OPTIONS[number]['value'];
interface Props {
	collectionVerticalAlignmentId: string;
	handleConfigurationChanged: (change: {
		verticalAlignment: VerticalAlignmentOption;
	}) => void;
	value?: VerticalAlignmentOption;
}
export declare function VerticalAlignmentSelector({
	collectionVerticalAlignmentId,
	handleConfigurationChanged,
	value,
}: Props): JSX.Element;
export {};

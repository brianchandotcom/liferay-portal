/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface Props {
	onCloseModal: () => void;
	onImport: (overwriteStrategy?: OverwriteStrategy) => void;
}
declare const OPTIONS: readonly [
	{
		readonly label: string;
		readonly value: 'do_not_import';
	},
	{
		readonly label: string;
		readonly value: 'overwrite';
	},
	{
		readonly label: string;
		readonly value: 'keep_both';
	}
];
export declare type OverwriteStrategy = typeof OPTIONS[number]['value'];
declare function ImportOptionsModal({
	onCloseModal,
	onImport,
}: Props): JSX.Element;
export default ImportOptionsModal;

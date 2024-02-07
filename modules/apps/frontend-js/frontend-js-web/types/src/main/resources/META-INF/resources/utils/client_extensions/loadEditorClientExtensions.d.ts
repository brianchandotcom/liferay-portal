/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface IConfig {
	editorTransformerURLs: Array<string>;
}
export default function loadEditorClientExtensions({
	config,
	onLoad,
}: {
	config: IConfig;
	onLoad: ({transformedConfig}: {transformedConfig: IConfig}) => void;
}): void;
export {};

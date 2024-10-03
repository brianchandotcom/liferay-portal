/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import loadClientExtensions from './loadClientExtensions';

interface IConfig {
	editorTransformerURLs: Array<string>;
}

export default function loadEditorClientExtensions({
	config,
	onLoad,
}: {
	config: IConfig;
	onLoad: ({transformedConfig}: {transformedConfig: IConfig}) => void;
}) {
	loadClientExtensions([
		{
			clientExtensionDefinitions: config.editorTransformerURLs.map(
				(url) => ({
					importDeclaration: `default from ${url}`,
				})
			),
			onLoad: (bindingContexts) => {
				let transformedConfig = JSON.parse(JSON.stringify(config));

				bindingContexts.forEach(
					({binding: editorTransformer, error}) => {
						if (process.env.NODE_ENV === 'development' && error) {
							console.error(error);
						}

						const editorConfigTransformer =
							editorTransformer?.editorConfigTransformer;

						if (editorConfigTransformer) {
							transformedConfig =
								editorConfigTransformer(transformedConfig);
						}
					}
				);

				onLoad({transformedConfig});
			},
		},
	]);
}

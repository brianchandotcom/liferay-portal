/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export async function loadModule(
	importDeclarationOrAMDModule: string
): Promise<any> {
	if (importDeclarationOrAMDModule.includes(' from ')) {
		const [moduleName, symbolName] = getModuleAndSymbolNames(
			importDeclarationOrAMDModule
		);

		// @ts-ignore
		const module = await import(/* webpackIgnore: true */ moduleName);

		return module[symbolName];
	}
	else {
		return new Promise((resolve, reject) => {
			Liferay.Loader.require(
				importDeclarationOrAMDModule,
				(jsModule: any) => resolve(jsModule.default || jsModule),
				(error: string) => reject(error)
			);
		});
	}
}

function getModuleAndSymbolNames(importDeclaration: string): [string, string] {
	const parts = importDeclaration.split(' from ');

	const moduleName = parts[1].trim();
	let symbolName = parts[0].trim();

	if (symbolName.startsWith('{') && symbolName.endsWith('}')) {
		symbolName = symbolName.substring(1, symbolName.length - 1).trim();
	}

	return [moduleName, symbolName];
}

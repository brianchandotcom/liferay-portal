/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const fs = require('fs');
const path = require('path');

function isRootPackageJson(filePath) {
	return path.basename(filePath) === 'modules';
}

function findPackageJson(startDir) {
	let currentDir = path.resolve(startDir);

	while (true) {
		const filePath = path.join(currentDir, 'package.json');

		if (fs.existsSync(filePath)) {
			return filePath;
		}

		currentDir = path.dirname(currentDir);

		if (isRootPackageJson(currentDir)) {
			return null;
		}
	}
}

const REGEX_NPM_ORG_PACKAGE_NAME = /^@[^/]+\/[^/]+/;
const REGEX_NPM_PACKAGE_NAME = /^[^/]+/;

module.exports = {
	create(context) {
		const allowedDependencies = context.options[0] || [];

		let dependencies = [];

		const pkgJsonPath = findPackageJson(context.getFilename());

		if (pkgJsonPath) {
			const pkgJson = JSON.parse(fs.readFileSync(pkgJsonPath));

			const dependencyNames = Object.keys(pkgJson.dependencies || {});
			const devDependencyNames = Object.keys(
				pkgJson.devDependencies || {}
			);

			dependencies = [...dependencyNames, ...devDependencyNames];

			if (pkgJson.name) {
				dependencies.push(pkgJson.name);
			}
		}

		function check(dependencyName, node) {
			if (
				!dependencyName.startsWith('.') &&
				!dependencyName.startsWith('/') &&
				!dependencyName.startsWith('~')
			) {
				dependencyName =
					dependencyName[0] === '@'
						? dependencyName.match(REGEX_NPM_ORG_PACKAGE_NAME)[0]
						: dependencyName.match(REGEX_NPM_PACKAGE_NAME)[0];

				if (
					![...dependencies, ...allowedDependencies].includes(
						dependencyName
					)
				) {
					context.report({
						message: `Dependency named '${dependencyName}' is not specified in your package.json (${pkgJsonPath})`,
						node,
					});
				}
			}
		}

		return {
			CallExpression(node) {
				if (
					pkgJsonPath &&
					node.callee.type === 'Identifier' &&
					node.callee.name === 'require'
				) {
					const argument = node.arguments && node.arguments[0];

					if (
						argument &&
						argument.type === 'Literal' &&
						typeof argument.value === 'string'
					) {
						check(argument.value, node);
					}
				}
			},

			ImportDeclaration(node) {
				if (pkgJsonPath) {
					check(node.source.value, node);
				}
			},
		};
	},

	meta: {
		docs: {
			category: 'Best Practices',
			description: 'imports must come before other statements',
			recommended: false,
			url:
				'https://github.com/liferay/liferay-frontend-guidelines/issues/60',
		},
		fixable: null,
		schema: [
			{
				type: 'array',
			},
		],
		type: 'problem',
	},
};

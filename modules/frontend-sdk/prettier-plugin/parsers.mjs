/**
 * SPDX-FileCopyrightText: © 2020 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

import {format} from 'prettier';
import {parsers as babelParsers} from 'prettier/plugins/babel';
import {parsers as typescriptParsers} from 'prettier/plugins/typescript';

import {linesAroundComments} from './rules/lines-around-comments.mjs';
import {newlineBeforeBlockStatement} from './rules/newline-before-block-statements.mjs';

function transformParser(parserName, defaultParser) {
	return {
		...defaultParser,
		astFormat: 'liferay-style-ast',
		parse: async (text, options) => {
			try {
				let plugins = options?.plugins || [];

				/*
				 * We need to filter out our own plugin before calling default prettier
				 */
				plugins = plugins.filter(
					(plugin) => !plugin.printers['liferay-style-ast']
				);

				let formattedText = await format(text, {
					...options,
					plugins,
				});

				let ast = defaultParser.parse(formattedText, options);

				formattedText = linesAroundComments(
					formattedText,
					ast,
					parserName,
					options
				);

				ast = defaultParser.parse(formattedText, options);

				formattedText = newlineBeforeBlockStatement(
					formattedText,
					ast,
					parserName,
					options
				);

				return {
					body: formattedText,
					type: 'FormattedText',
				};
			} catch (err) {
				console.log('ERROR', options.filepath, err);
			}
		},
	};
}

export const parsers = {
	babel: transformParser('babel', babelParsers.babel),
	typescript: transformParser('typescript', typescriptParsers.typescript),
};

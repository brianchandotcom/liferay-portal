/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {cleanup, render, screen} from '@testing-library/react';
import React from 'react';

(global as any).Liferay = {
	Language: {
		get: (key: string) => key,
	},
};

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/components/FDSItemTitle',
	() => {
		const React = require('react');

		return {
			__esModule: true,
			default: ({value}: {value: any}) =>
				React.createElement('span', null, value),
		};
	}
);

jest.mock('@clayui/label', () => {
	const React = require('react');

	return {
		__esModule: true,
		default: ({children}: {children: any}) =>
			React.createElement('span', null, children),
	};
});

function loadInstructionDefinitionItemTitle() {
	let component: any;

	jest.isolateModules(() => {
		component =
			require('../../../src/main/resources/META-INF/resources/js/instruction_definition_item_title/InstructionDefinitionItemTitle').default;
	});

	return component;
}

describe('InstructionDefinitionItemTitle', () => {
	afterEach(() => {
		cleanup();
	});

	it('renders a "Provided by Liferay" label when the entry is a system instruction', () => {
		const InstructionDefinitionItemTitle =
			loadInstructionDefinitionItemTitle();

		render(
			<InstructionDefinitionItemTitle
				actions={[]}
				itemData={{system: true}}
				itemId={1}
				value="AI Transparency"
			/>
		);

		expect(screen.getByText('AI Transparency')).toBeInTheDocument();
		expect(screen.getByText('provided-by-liferay')).toBeInTheDocument();
	});

	it('renders only the title when the entry is not a system instruction', () => {
		const InstructionDefinitionItemTitle =
			loadInstructionDefinitionItemTitle();

		render(
			<InstructionDefinitionItemTitle
				actions={[]}
				itemData={{system: false}}
				itemId={1}
				value="AI Transparency"
			/>
		);

		expect(screen.getByText('AI Transparency')).toBeInTheDocument();
		expect(
			screen.queryByText('provided-by-liferay')
		).not.toBeInTheDocument();
	});
});

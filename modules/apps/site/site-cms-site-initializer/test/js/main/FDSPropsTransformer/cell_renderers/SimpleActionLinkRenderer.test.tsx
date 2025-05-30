/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';
import React from 'react';

import SimpleActionLinkRenderer from '../../../../../src/main/resources/META-INF/resources/js/main/FDSPropsTransformer/cell_renderers/SimpleActionLinkRenderer';

const testAction = {
	data: {id: 'view'},
	href: 'http://www.test.com',
};

const testActionFolder = {
	data: {id: 'viewFolder'},
	href: 'http://www.test.com/folder',
};

const test = {
	actions: [testAction, testActionFolder],
	itemData: {entryClassName: 'com.liferay.object.model.ObjectEntry'},
	itemDataFolder: {
		entryClassName: 'com.liferay.object.model.ObjectEntryFolder',
	},
	options: {
		actionId: 'view',
	},
	value: 'Test',
};

describe('SimpleActionLinkRenderer. Render the value only.', () => {
	it('there are no actions', () => {
		render(
			<SimpleActionLinkRenderer
				actions={[]}
				itemData={test.itemData}
				options={test.options}
				value={test.value}
			/>
		);

		expect(screen.queryByRole('link')).not.toBeInTheDocument();

		expect(screen.getByText(test.value)).toBeInTheDocument();
	});

	it('empty actionId string', () => {
		render(
			<SimpleActionLinkRenderer
				actions={test.actions}
				itemData={test.itemData}
				options={{actionId: ''}}
				value={test.value}
			/>
		);

		expect(screen.queryByRole('link')).not.toBeInTheDocument();

		expect(screen.getByText(test.value)).toBeInTheDocument();
	});

	it('actionId not in available actions', () => {
		render(
			<SimpleActionLinkRenderer
				actions={test.actions}
				itemData={test.itemData}
				options={{actionId: 'edit'}}
				value={test.value}
			/>
		);

		expect(screen.queryByRole('link')).not.toBeInTheDocument();

		expect(screen.getByText(test.value)).toBeInTheDocument();
	});
});

describe('SimpleActionLinkRenderer. Render the link.', () => {
	it('non-folder action', () => {
		render(
			<SimpleActionLinkRenderer
				actions={test.actions}
				itemData={test.itemData}
				options={test.options}
				value={test.value}
			/>
		);

		expect(screen.getByRole('link', {name: test.value})).toHaveAttribute(
			'href',
			testAction.href
		);
	});

	it('folder action', () => {
		render(
			<SimpleActionLinkRenderer
				actions={test.actions}
				itemData={test.itemDataFolder}
				options={test.options}
				value={test.value}
			/>
		);

		expect(screen.getByRole('link', {name: test.value})).toHaveAttribute(
			'href',
			testActionFolder.href
		);
	});
});

/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';
import React from 'react';

import AssetVersionRenderer from '../../../../../src/main/resources/META-INF/resources/js/main_view/props_transformer/cell_renderers/AssetVersionRenderer';

const testIrrelevantAction = {
	data: {id: 'view'},
};

const testViewContentAction = {
	data: {id: 'view-content'},
	href: 'http://www.test.com',
};

const testContentProps = {
	actions: [testViewContentAction],
	itemData: {
		systemProperties: {
			version: {
				number: '1',
			},
		},
		title: 'Content Title',
	},
	value: 'Content Title',
};

const testFileProps = {
	actions: [],
	itemData: {
		file: {
			thumbnailURL: '/thumbs/image1?version=1.0&imageThumbnail=1',
		},
		systemProperties: {
			version: {
				number: '1',
			},
		},
		title: 'File Title',
	},
	value: 'File Title',
};

describe('AssetVersionRenderer', () => {
	it('render a content version link', () => {
		render(<AssetVersionRenderer {...testContentProps} />);

		expect(
			screen.getByRole('button', {name: testContentProps.value})
		).toBeInTheDocument();
	});

	it('not render a content version link without actions', () => {
		render(<AssetVersionRenderer {...testContentProps} actions={[]} />);

		expect(screen.queryByRole('button')).not.toBeInTheDocument();

		expect(screen.getByText(testContentProps.value)).toBeInTheDocument();
	});

	it('not render a content version link with irrelevant action', () => {
		render(
			<AssetVersionRenderer
				{...testContentProps}
				actions={[testIrrelevantAction]}
			/>
		);

		expect(screen.queryByRole('button')).not.toBeInTheDocument();

		expect(screen.getByText(testContentProps.value)).toBeInTheDocument();
	});

	it('render a file version link', () => {
		render(<AssetVersionRenderer {...testFileProps} />);

		expect(
			screen.getByRole('button', {name: testFileProps.value})
		).toBeInTheDocument();
	});
});

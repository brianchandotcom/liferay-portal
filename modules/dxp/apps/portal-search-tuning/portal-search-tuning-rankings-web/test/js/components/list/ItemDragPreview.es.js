/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ItemDragPreview from '../../../../src/main/resources/META-INF/resources/js/components/list/ItemDragPreview.es';
import React from 'react';
import {render} from '@testing-library/react';
import {mockDocument} from '../../mock-data';
import '@testing-library/jest-dom/extend-expect';

// Mock pinned document since only pinned results can be dragged.

const MOCK_DOCUMENT = mockDocument(1, {pinned: true});

/**
 * Tests a text string if the value is displayed in the component.
 * @param {string} text The text to test.
 */
function testText(text) {
	const {getByText} = render(<ItemDragPreview {...MOCK_DOCUMENT} />);

	expect(getByText(text, {exact: false})).toBeInTheDocument();
}

describe('ItemDragPreview', () => {
	it('displays the title', () => {
		testText(MOCK_DOCUMENT.title);
	});

	it('displays the description', () => {
		testText(MOCK_DOCUMENT.description);
	});

	it('displays the author', () => {
		testText(MOCK_DOCUMENT.author);
	});

	it('displays the clicks', () => {
		testText(`${MOCK_DOCUMENT.clicks}`);
	});

	it('displays the date', () => {
		testText(`${MOCK_DOCUMENT.date}`);
	});

	it('displays the drag handle', () => {
		const {getByTestId} = render(<ItemDragPreview {...MOCK_DOCUMENT} />);

		expect(getByTestId('DRAG_ICON')).toBeVisible();
	});
});

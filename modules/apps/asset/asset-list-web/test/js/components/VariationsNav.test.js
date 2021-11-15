/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';

import {saveVariationsListPriorityService} from '../../../src/main/resources/META-INF/resources/js/api/index';
import VariationsNav from '../../../src/main/resources/META-INF/resources/js/components/VariationsNav/index';
import {
	emptyStateNoSegments,
	emptyStateOneAvailableSegments,
	listWithFourVariationsAndNoMoreSegmentsEntries,
	listWithTwoVariations,
} from '../mocks/variationsNavProps';

const _getComponent = (props) => {
	return <VariationsNav {...props} />;
};

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/api/index.js',
	() => ({
		saveVariationsListPriorityService: jest.fn(),
	})
);

describe('VariationsNav Empty State', () => {
	beforeEach(() => {
		window.Liferay.Util.navigate = jest.fn();
		window['openSelectSegmentsEntryDialogMethod'] = jest.fn();
	});

	afterEach(() => {
		jest.restoreAllMocks();
		cleanup();
	});

	it('shows a navigation button if no segments are available', () => {
		const {container, getByText} = render(
			_getComponent(emptyStateNoSegments)
		);

		const navigateToAddNewSegmentButton = getByText(
			'Add your first segment'
		);

		expect(navigateToAddNewSegmentButton).toBeInTheDocument();
		expect(container.getElementsByClassName('c-empty-state').length).toBe(
			1
		);

		fireEvent.click(navigateToAddNewSegmentButton);
		expect(window.Liferay.Util.navigate).toHaveBeenCalledWith(
			'http://localhost:8080/create-new-segment-demo-url'
		);
	});

	it('shows a create variation button, initially disabled', () => {
		const {getByText, rerender} = render(
			_getComponent(emptyStateOneAvailableSegments)
		);

		const createVariationButton = getByText('New Personalized Variation');

		expect(createVariationButton).toBeInTheDocument();
		expect(createVariationButton).toBeDisabled();

		rerender(
			_getComponent({
				...emptyStateOneAvailableSegments,
				validAssetListEntry: true,
			})
		);

		expect(createVariationButton).not.toBeDisabled();
		fireEvent.click(createVariationButton);
		expect(
			window['openSelectSegmentsEntryDialogMethod']
		).toHaveBeenCalled();
	});
});

describe('VariationsNav With segments', () => {
	beforeEach(() => {
		window['openSelectSegmentsEntryDialogMethod'] = jest.fn();
		window.confirm = jest.fn(() => true);
		window.submitForm = jest.fn();

		window.Liferay.Util.openToast = jest.fn();
	});

	afterEach(() => {
		jest.restoreAllMocks();
		cleanup();
	});

	it('shows a variations nav list with the proper title', () => {
		const {getByText} = render(_getComponent(listWithTwoVariations));
		expect(getByText('personalized-variations')).toBeInTheDocument();
	});

	it('shows a variations nav list with the add new variation button, if available', () => {
		const {getByTitle} = render(_getComponent(listWithTwoVariations));

		const addNewVariationButton = getByTitle('Add new variation');

		expect(addNewVariationButton).toBeInTheDocument();
		expect(
			addNewVariationButton.getElementsByClassName('lexicon-icon-plus')
				.length
		).toBe(1);

		fireEvent.click(addNewVariationButton);
		expect(
			window['openSelectSegmentsEntryDialogMethod']
		).toHaveBeenCalled();
	});

	it('shows a variations nav list and hides the add new variation button, if no more entries available', () => {
		const {queryByTitle} = render(
			_getComponent(listWithFourVariationsAndNoMoreSegmentsEntries)
		);

		const addNewVariationButton = queryByTitle('Add new variation');
		expect(addNewVariationButton).not.toBeInTheDocument();
	});

	it('shows a variations nav list with the proper items list', () => {
		const {container, getByText} = render(
			_getComponent(listWithTwoVariations)
		);

		expect(getByText('Anyone')).toBeInTheDocument();
		expect(getByText('Liferayers')).toBeInTheDocument();

		expect(container.getElementsByClassName('active').length).toBe(1);

		expect(
			container.getElementsByClassName('lexicon-icon-drag').length
		).toBe(2);
		expect(
			container.getElementsByClassName('lexicon-icon-ellipsis-v').length
		).toBe(2);
	});

	it('shows a variations nav list with an action menu for each item', () => {
		const {getAllByText} = render(_getComponent(listWithTwoVariations));

		expect(getAllByText('Prioritize').length).toBe(2);
		expect(getAllByText('Deprioritize').length).toBe(2);

		const deleteButtons = getAllByText('Delete');
		expect(deleteButtons.length).toBe(2);
		expect(deleteButtons[0]).toBeDisabled();
		expect(deleteButtons[1]).not.toBeDisabled();

		fireEvent.click(deleteButtons[1]);
		expect(window.confirm).toHaveBeenCalled();
		expect(window.submitForm).toHaveBeenCalledWith(
			undefined,
			'delete-asset-list-entry-url-1'
		);
	});

	it('responds to dnd events', () => {
		const {getAllByRole} = render(_getComponent(listWithTwoVariations));
		let nodes = getAllByRole('listitem');

		expect(nodes[0].innerHTML).toContain('Anyone');
		expect(nodes[1].innerHTML).toContain('Liferayers');

		fireEvent.dragStart(nodes[0]);
		fireEvent.dragEnter(nodes[1]);
		fireEvent.dragOver(nodes[1]);
		fireEvent.drop(nodes[1]);

		nodes = getAllByRole('listitem');

		expect(nodes[0].innerHTML).toContain('Liferayers');
		expect(nodes[1].innerHTML).toContain('Anyone');

		expect(saveVariationsListPriorityService).toHaveBeenCalled();
	});

	it('responds to reorder on click event', () => {
		const {getAllByRole, getAllByText} = render(
			_getComponent(listWithTwoVariations)
		);
		let nodes = getAllByRole('listitem');

		expect(nodes[0].innerHTML).toContain('Anyone');
		expect(nodes[1].innerHTML).toContain('Liferayers');

		const prioritizeVariationButtons = getAllByText('Prioritize');
		fireEvent.click(prioritizeVariationButtons[1]);

		nodes = getAllByRole('listitem');

		expect(nodes[0].innerHTML).toContain('Liferayers');
		expect(nodes[1].innerHTML).toContain('Anyone');

		expect(saveVariationsListPriorityService).toHaveBeenCalled();
	});
});

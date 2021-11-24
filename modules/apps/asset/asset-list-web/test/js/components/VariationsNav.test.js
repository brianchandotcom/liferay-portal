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
	() => {
		const moduleMock = jest.fn();
		moduleMock.mockReturnValue({
			ok: true,
			status: 200,
		});

		return {
			saveVariationsListPriorityService: moduleMock,
		};
	}
);

describe('VariationsNav Initial State', () => {
	beforeEach(() => {
		window['openSelectSegmentsEntryDialogMethod'] = jest.fn();
	});

	afterEach(() => {
		jest.restoreAllMocks();
		cleanup();
	});

	it('shows an initial state with the proper texts', () => {
		const {getByText} = render(_getComponent(emptyStateNoSegments));

		expect(getByText('personalized-variations')).toBeInTheDocument();
		expect(
			getByText(
				'create-personalized-variations-of-the-collections-for-different-segments'
			)
		).toBeInTheDocument();
		expect(
			getByText('you-need-segments-to-create-a-personalized-variation', {
				exact: false,
			})
		).toBeInTheDocument();
		expect(getByText('create-your-first-segment')).toBeInTheDocument();
	});

	it('shows an initial state with the Add variation button disabled', () => {
		const {getByTitle} = render(_getComponent(emptyStateNoSegments));

		const addNewVariationButton = getByTitle('create-variation');
		expect(addNewVariationButton).toBeInTheDocument();
		expect(addNewVariationButton).toBeDisabled();
	});

	it('shows an initial state with a link to navigate to segments', () => {
		const {getByText} = render(_getComponent(emptyStateNoSegments));

		const navigateToSegmentsLink = getByText('create-your-first-segment');

		expect(navigateToSegmentsLink).toBeInTheDocument();
		expect(navigateToSegmentsLink).toHaveAttribute(
			'href',
			'http://localhost:8080/create-new-segment-demo-url'
		);
	});

	it('shows the default variation', () => {
		const {getByText} = render(_getComponent(emptyStateNoSegments));
		expect(getByText('Anyone')).toBeInTheDocument();
		expect(getByText('prioritize')).toBeInTheDocument();
		expect(getByText('deprioritize')).toBeInTheDocument();
		expect(getByText('delete')).toBeInTheDocument();
	});

	it('shows a create variation button, initially disabled', () => {
		const {getByTitle, rerender} = render(
			_getComponent(emptyStateOneAvailableSegments)
		);

		const addNewVariationButton = getByTitle('create-variation');

		expect(
			addNewVariationButton.getElementsByClassName('lexicon-icon-plus')
				.length
		).toBe(1);

		expect(addNewVariationButton).toBeInTheDocument();
		expect(addNewVariationButton).toBeDisabled();

		rerender(
			_getComponent({
				...emptyStateOneAvailableSegments,
				assetListEntryValid: true,
			})
		);

		expect(addNewVariationButton).not.toBeDisabled();
		fireEvent.click(addNewVariationButton);
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

	it('shows a variations nav list and disables the add new variation button, if no more entries available', () => {
		const {getByTitle} = render(
			_getComponent(listWithFourVariationsAndNoMoreSegmentsEntries)
		);

		const addNewVariationButton = getByTitle('create-variation');
		expect(addNewVariationButton).toBeDisabled();
	});

	it('shows a variations nav list with the proper items list', () => {
		const {container, getByText} = render(
			_getComponent(listWithTwoVariations)
		);

		const itemOne = getByText('Anyone');
		const itemTwo = getByText('Liferayers');

		expect(itemOne).toBeInTheDocument();
		expect(itemTwo).toBeInTheDocument();

		expect(container.getElementsByClassName('active').length).toBe(1);

		expect(
			container.getElementsByClassName('lexicon-icon-ellipsis-v').length
		).toBe(2);

		fireEvent.mouseOver(itemOne);
		expect(
			container.getElementsByClassName('lexicon-icon-drag').length
		).toBe(1);
		fireEvent.mouseOut(itemOne);
		expect(
			container.getElementsByClassName('lexicon-icon-drag').length
		).toBe(0);
	});

	it('shows a variations nav list with an action menu for each item', () => {
		const {getAllByText} = render(_getComponent(listWithTwoVariations));

		expect(getAllByText('prioritize').length).toBe(2);
		expect(getAllByText('deprioritize').length).toBe(2);

		const deleteButtons = getAllByText('delete');
		expect(deleteButtons.length).toBe(2);
		expect(deleteButtons[0]).toBeDisabled();
		expect(deleteButtons[1]).not.toBeDisabled();

		fireEvent.click(deleteButtons[1]);
		expect(window.confirm).toHaveBeenCalledWith(
			'are-you-sure-you-want-to-delete-this'
		);
		expect(window.submitForm).toHaveBeenCalledWith(
			undefined,
			'delete-asset-list-entry-url-1'
		);
	});

	it('shows a variations nav list with a non deletable default item', () => {
		const {getAllByText} = render(_getComponent(listWithTwoVariations));

		const deleteButtons = getAllByText('delete');
		expect(deleteButtons.length).toBe(2);
		expect(deleteButtons[0]).toBeDisabled();

		fireEvent.click(deleteButtons[0]);
		expect(window.confirm).not.toHaveBeenCalled();
		expect(window.submitForm).not.toHaveBeenCalledWith();
	});
});

describe('VariationsNav', () => {
	beforeEach(() => {
		global.Liferay.Util.openToast = jest.fn();
	});

	afterEach(() => {
		jest.restoreAllMocks();
		cleanup();
	});

	it('responds to dnd events', async () => {
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

		expect(await saveVariationsListPriorityService).toHaveBeenCalled();

		expect(global.Liferay.Util.openToast).toHaveBeenCalledWith({
			message: Liferay.Language.get(
				'your-request-completed-successfully'
			),
			type: 'success',
		});
	});

	it('responds to reorder on click event', async () => {
		const {getAllByRole, getAllByText} = render(
			_getComponent(listWithTwoVariations)
		);
		let nodes = getAllByRole('listitem');

		expect(nodes[0].innerHTML).toContain('Anyone');
		expect(nodes[1].innerHTML).toContain('Liferayers');

		const prioritizeVariationButtons = getAllByText('prioritize');
		fireEvent.click(prioritizeVariationButtons[1]);

		nodes = getAllByRole('listitem');

		expect(nodes[0].innerHTML).toContain('Liferayers');
		expect(nodes[1].innerHTML).toContain('Anyone');

		expect(await saveVariationsListPriorityService).toHaveBeenCalled();

		expect(global.Liferay.Util.openToast).toHaveBeenCalledWith({
			message: Liferay.Language.get(
				'your-request-completed-successfully'
			),
			type: 'success',
		});
	});

	it('throws an error if the API call fails', async () => {
		saveVariationsListPriorityService.mockReturnValue({
			ok: false,
			status: 500,
		});

		const {getAllByRole, getAllByText} = render(
			_getComponent(listWithTwoVariations)
		);
		let nodes = getAllByRole('listitem');

		expect(nodes[0].innerHTML).toContain('Anyone');
		expect(nodes[1].innerHTML).toContain('Liferayers');

		const prioritizeVariationButtons = getAllByText('prioritize');
		fireEvent.click(prioritizeVariationButtons[1]);

		nodes = getAllByRole('listitem');

		expect(nodes[0].innerHTML).toContain('Liferayers');
		expect(nodes[1].innerHTML).toContain('Anyone');

		expect(await saveVariationsListPriorityService).toHaveBeenCalled();

		expect(global.Liferay.Util.openToast).toHaveBeenCalledWith({
			message: Liferay.Language.get('an-unexpected-error-occurred'),
			type: 'danger',
		});
	});
});

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
		expect(getByText('no-personalized-variations-yet')).toBeInTheDocument();
		expect(
			getByText('no-personalized-variations-were-found')
		).toBeInTheDocument();
	});

	it('shows an initial state without the default variation', () => {
		const {queryByText} = render(_getComponent(emptyStateNoSegments));

		expect(queryByText('Anyone')).not.toBeInTheDocument();
	});

	it('shows the proper texts when a segment is available and no variations are created', () => {
		const {getByText} = render(
			_getComponent(emptyStateOneAvailableSegments)
		);

		expect(getByText('personalized-variations')).toBeInTheDocument();
		expect(getByText('no-personalized-variations-yet')).toBeInTheDocument();
		expect(
			getByText('no-personalized-variations-were-found')
		).toBeInTheDocument();
	});

	it('shows an add-personalized-variation button, when a segment is available and no variations are created', () => {
		const {getByText} = render(
			_getComponent(emptyStateOneAvailableSegments)
		);

		const addPersonalizedVariationButton = getByText(
			'add-personalized-variation'
		);

		expect(addPersonalizedVariationButton).toBeInTheDocument();
		expect(addPersonalizedVariationButton).not.toBeDisabled();

		fireEvent.click(addPersonalizedVariationButton);
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

	it('shows a variations nav list with the proper title and description', () => {
		const {getByText} = render(_getComponent(listWithTwoVariations));
		expect(getByText('personalized-variations')).toBeInTheDocument();
		expect(
			getByText(
				'create-personalized-variations-of-the-collections-for-different-segments'
			)
		).toBeInTheDocument();
	});

	it('shows a variations nav list with a create-variation button, if segments available', () => {
		const {queryByTitle} = render(
			_getComponent(listWithFourVariationsAndNoMoreSegmentsEntries)
		);

		const addNewVariationButton = queryByTitle('create-variation');
		expect(addNewVariationButton).not.toBeInTheDocument();
	});

	it('shows a variations nav list with a create-variation button hidden, if no more segments are available', () => {
		const {getByTitle} = render(_getComponent(listWithTwoVariations));

		const addNewVariationButton = getByTitle('create-variation');
		expect(addNewVariationButton).toBeInTheDocument();
		expect(addNewVariationButton).not.toBeDisabled();
	});

	it('shows a variations nav list with the proper items list UI, normal and hover state', () => {
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

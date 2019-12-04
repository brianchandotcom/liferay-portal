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

import {
	cleanup,
	render,
	wait,
	waitForElement,
	waitForElementToBeRemoved,
	within
} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import {
	EDIT_SEGMENTS_EXPERIENCE,
	DELETE_SEGMENTS_EXPERIENCE,
	UPDATE_SEGMENTS_EXPERIENCE_PRIORITY
} from '../../../../../src/main/resources/META-INF/resources/js/actions/actions.es';
import ExperienceSelector from '../../../../../src/main/resources/META-INF/resources/js/components/toolbar/segmentsExperiences/ExperiencesSelector.es';
import StateProvider from '../../../../../src/main/resources/META-INF/resources/js/store/StateProvider.es';
import StoreContext from '../../../../../src/main/resources/META-INF/resources/js/store/StoreContext.es';
import Store from '../../../../../src/main/resources/META-INF/resources/js/store/store.es';

import '@testing-library/jest-dom/extend-expect';

const mockState = {
	availableSegmentsEntries: {
		'test-segment-id-00': {
			name: 'A segment 0',
			segmentsEntryId: 'test-segment-id-00'
		},
		'test-segment-id-01': {
			name: 'A segment 1',
			segmentsEntryId: 'test-segment-id-01'
		}
	},
	availableSegmentsExperiences: {
		0: {
			active: true,
			hasLockedSegmentsExperiment: false,
			name: 'Default Experience',
			priority: -1,
			segmentsEntryId: 'test-segment-id-00',
			segmentsExperienceId: '0',
			segmentsExperimentStatus: undefined,
			segmentsExperimentURL: 'https//:default-experience.com'
		},
		'test-segment-id-01': {
			active: true,
			hasLockedSegmentsExperiment: false,
			name: 'Experience #1',
			priority: 3,
			segmentsEntryId: 'test-segment-id-00',
			segmentsExperienceId: 'test-experience-id-01',
			segmentsExperimentStatus: undefined,
			segmentsExperimentURL: 'https//:experience-1.com'
		},
		'test-segment-id-02': {
			active: true,
			hasLockedSegmentsExperiment: false,
			name: 'Experience #2',
			priority: 1,
			segmentsEntryId: 'test-segment-id-01',
			segmentsExperienceId: 'test-experience-id-02',
			segmentsExperimentStatus: undefined,
			segmentsExperimentURL: 'https//:experience-2.com'
		}
	},
	classPK: 'test-classPK',
	defaultSegmentsExperienceId: '0',
	editSegmentsEntryURL: 'https://test-url.com/',
	hasEditSegmentsEntryPermission: true,
	hasUpdatePermissions: true,
	lockedSegmentsExperience: true,
	portletNamespace: 'test-portlet-namespace',
	segmentsExperienceId: '0',
	selectedSegmentsEntryId: undefined
};

const RenderSegmentsExperienceSelector = (initialState, reducer = () => {}) => {
	const store = new Store(
		{
			...initialState
		},
		reducer
	);

	return render(
		<StoreContext.Provider value={store}>
			<StateProvider>
				<ExperienceSelector />
			</StateProvider>
		</StoreContext.Provider>,
		{
			baseElement: document.body
		}
	);
};

describe('ExperienceSelector', () => {
	afterEach(cleanup);

	it('renders a list of Experiences ordered by priority', async () => {
		const {
			getAllByRole,
			getByLabelText,
			getByRole
		} = RenderSegmentsExperienceSelector(mockState);

		const dropDownButton = getByLabelText('experience');

		userEvent.click(dropDownButton);

		await waitForElement(() => getByRole('list'));

		const listedExperiences = getAllByRole('listitem');

		expect(listedExperiences.length).toBe(3);

		/**
		 * Experiences are ordered by priority
		 */
		expect(
			within(listedExperiences[0]).getByText('Experience #1')
		).toBeInTheDocument();
		expect(
			within(listedExperiences[1]).getByText('Experience #2')
		).toBeInTheDocument();
		expect(
			within(listedExperiences[2]).getByText('Default Experience')
		).toBeInTheDocument();
	});

	it('dispatches expected action when editing an Experience', async () => {
		const mockReducer = jest.fn(state => Promise.resolve(state));

		const {
			getAllByTitle,
			getByLabelText,
			getByRole,
			getByText
		} = RenderSegmentsExperienceSelector(mockState, mockReducer);

		const dropDownButton = getByLabelText('experience');

		userEvent.click(dropDownButton);

		await waitForElement(() => getByRole('list'));

		const editExperiencesButtons = getAllByTitle('edit-experience');

		expect(editExperiencesButtons.length).toBe(2);

		userEvent.click(editExperiencesButtons[0]);

		await waitForElement(() => getByLabelText('name'));

		const nameInput = getByLabelText('name');
		const segmentSelect = getByLabelText('audience');

		expect(nameInput.value).toBe('Experience #1');
		expect(segmentSelect.value).toBe('test-segment-id-00');

		userEvent.type(nameInput, 'Modified Experience #1');

		userEvent.click(getByText('save'));

		await wait(() => expect(mockReducer).toHaveBeenCalledTimes(1));

		expect(mockReducer).toBeCalledWith(
			expect.objectContaining({}),
			expect.objectContaining({
				name: 'Modified Experience #1',
				type: EDIT_SEGMENTS_EXPERIENCE
			})
		);

		await waitForElementToBeRemoved(() => getByText('save'));
	});

	it('dispatches expected action when removing an Experience', async () => {
		const mockReducer = jest.fn(state => Promise.resolve(state));
		global.confirm = jest.fn(() => true);

		const {
			getAllByTitle,
			getByLabelText,
			getByRole
		} = RenderSegmentsExperienceSelector(mockState, mockReducer);

		const dropDownButton = getByLabelText('experience');

		userEvent.click(dropDownButton);

		await waitForElement(() => getByRole('list'));

		const removeExperiencesButtons = getAllByTitle('delete-experience');

		expect(removeExperiencesButtons.length).toBe(2);

		userEvent.click(removeExperiencesButtons[1]);

		await wait(() => expect(global.confirm).toHaveBeenCalledTimes(1));

		expect(mockReducer).toHaveBeenCalledTimes(1);

		expect(mockReducer).toBeCalledWith(
			expect.objectContaining({}),
			expect.objectContaining({
				segmentsExperienceId: 'test-experience-id-02',
				type: DELETE_SEGMENTS_EXPERIENCE
			})
		);
	});

	it('dispatches expected action when prioritizing an Experience', async () => {
		const mockReducer = jest.fn(state => Promise.resolve(state));

		const {
			getAllByTitle,
			getByLabelText,
			getByRole
		} = RenderSegmentsExperienceSelector(mockState, mockReducer);

		const dropDownButton = getByLabelText('experience');

		userEvent.click(dropDownButton);

		await waitForElement(() => getByRole('list'));

		const prioritizeExperienceButton = getAllByTitle(
			'prioritize-experience'
		);

		expect(prioritizeExperienceButton.length).toBe(2);

		expect(prioritizeExperienceButton[0].disabled).toBe(true);
		expect(prioritizeExperienceButton[1].disabled).toBe(false);

		userEvent.click(prioritizeExperienceButton[1]);

		await wait(() => expect(mockReducer).toHaveBeenCalledTimes(1));
		expect(mockReducer).toBeCalledWith(
			expect.objectContaining({}),
			expect.objectContaining({
				direction: 'up',
				segmentsExperienceId: 'test-experience-id-02',
				type: UPDATE_SEGMENTS_EXPERIENCE_PRIORITY
			})
		);
	});

	it('dispatches expected action when deprioritizing an Experience', async () => {
		const mockReducer = jest.fn(state => Promise.resolve(state));

		const {
			getAllByTitle,
			getByLabelText,
			getByRole
		} = RenderSegmentsExperienceSelector(mockState, mockReducer);

		const dropDownButton = getByLabelText('experience');

		userEvent.click(dropDownButton);

		await waitForElement(() => getByRole('list'));

		const prioritizeExperienceButton = getAllByTitle(
			'deprioritize-experience'
		);

		expect(prioritizeExperienceButton.length).toBe(2);

		expect(prioritizeExperienceButton[0].disabled).toBe(false);
		expect(prioritizeExperienceButton[1].disabled).toBe(true);

		userEvent.click(prioritizeExperienceButton[0]);

		await wait(() => expect(mockReducer).toHaveBeenCalledTimes(1));
		expect(mockReducer).toBeCalledWith(
			expect.objectContaining({}),
			expect.objectContaining({
				direction: 'down',
				segmentsExperienceId: 'test-experience-id-01',
				type: UPDATE_SEGMENTS_EXPERIENCE_PRIORITY
			})
		);
	});

	it('displays a help hint on the locked icon for a locked Experience', async () => {
		const mockStateWithLockedExperience = {
			...mockState,
			availableSegmentsExperiences: {
				...mockState.availableSegmentsExperiences,
				'test-experience-id-03': {
					active: true,
					hasLockedSegmentsExperiment: true,
					name: 'Experience #3',
					priority: 5,
					segmentsEntryId: 'test-segment-id-00',
					segmentsExperienceId: 'test-experience-id-03',
					segmentsExperimentStatus: {
						label: 'running',
						value: 3
					},
					segmentsExperimentURL: 'https//:locked-experience.com'
				}
			}
		};
		const {
			getByLabelText,
			getByRole,
			getByText
		} = RenderSegmentsExperienceSelector(mockStateWithLockedExperience);

		const dropDownButton = getByLabelText('experience');

		userEvent.click(dropDownButton);

		await waitForElement(() => getByRole('list'));

		const experience = getByText('Experience #3');

		const lockIcon = within(experience).getByRole('presentation');

		userEvent.click(lockIcon);

		getByText('experience-locked');
		getByText('edit-is-not-allowed-for-this-experience');
	});
});

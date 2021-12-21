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

import {useModal} from '@clayui/modal';
import {act, render, waitFor} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import {ReviewExperimentModal} from '../../../src/main/resources/META-INF/resources/js/components/ReviewExperimentModal.es';
import SegmentsExperimentContext from '../../../src/main/resources/META-INF/resources/js/context.es';
import {StateContext} from '../../../src/main/resources/META-INF/resources/js/state/context.es';

import '@testing-library/jest-dom/extend-expect';

const variants = [
	{
		control: true,
		name: 'Control',
		segmentsExperienceId: 'experience-001',
		segmentsExperimentId: 'experiment-001',
		segmentsExperimentRelId: 'experiment-rel-001',
		split: 0,
	},
	{
		control: true,
		name: 'Variant 2',
		segmentsExperienceId: 'experience-002',
		segmentsExperimentId: 'experiment-001',
		segmentsExperimentRelId: 'experiment-rel-002',
		split: 0,
	},
	{
		control: true,
		name: 'Variant 3',
		segmentsExperienceId: 'experience-003',
		segmentsExperimentId: 'experiment-001',
		segmentsExperimentRelId: 'experiment-rel-003',
		split: 0,
	},
	{
		control: true,
		name: 'Variant 4',
		segmentsExperienceId: 'experience-004',
		segmentsExperimentId: 'experiment-001',
		segmentsExperimentRelId: 'experiment-rel-004',
		split: 0,
	},
];
const onRun = jest.fn();

const imagesPath = 'path';

const experiment = {
	confidenceLevel: 0,
	description: '',
	editable: true,
	goal: {
		label: 'Click',
		target: 'element',
		value: 'click',
	},
	name: 'Experiment name',
	segmentsEntryName: 'Experience Segment',
	segmentsExperienceId: 'experience-001',
	segmentsExperimentId: 'experiment-001',
	status: {
		label: 'draft',
		value: '0',
	},
};

const ModalWrapper = ({onCloseMock, onRun, variants}) => {
	const {observer: modalObserver, onClose: onModalClose} = useModal({
		onClose: onCloseMock,
	});

	return (
		<>
			<ReviewExperimentModal
				modalObserver={modalObserver}
				onModalClose={onModalClose}
				onRun={onRun}
				variants={variants}
			/>
		</>
	);
};

const renderReviewExperimentModal = ({
	getEstimatedTimeMock,
	onCloseMock = () => {},
}) => {
	return render(
		<SegmentsExperimentContext.Provider
			value={{
				APIService: {
					getEstimatedTime: getEstimatedTimeMock,
				},
				imagesPath,
			}}
		>
			<StateContext.Provider
				value={{
					experiment,
				}}
			>
				<ModalWrapper
					onCloseMock={onCloseMock}
					onRun={onRun}
					variants={variants}
				/>
			</StateContext.Provider>
		</SegmentsExperimentContext.Provider>,
		{
			baseElement: document.body,
		}
	);
};

const getEstimatedTimeMockFactory = (days) => () => {
	return Promise.resolve({segmentsExperimentEstimatedDaysDuration: days});
};

describe('ReviewExperimentModal', () => {
	describe('Estimated days', () => {
		afterEach(() => {
			jest.clearAllTimers();
		});

		beforeAll(() => {
			jest.useFakeTimers();
		});

		it('Triggers on first render', async () => {
			const getEstimatedTimeMock = jest.fn(
				getEstimatedTimeMockFactory(10)
			);

			renderReviewExperimentModal({
				getEstimatedTimeMock,
			});

			act(() => jest.runAllTimers());

			await waitFor(() =>
				expect(getEstimatedTimeMock).toHaveBeenCalledTimes(1)
			);

			expect(getEstimatedTimeMock).toHaveBeenCalledWith(
				expect.objectContaining({
					confidenceLevel: 95,
				})
			);
		});

		it('Triggers on confidence level change', async () => {
			const getEstimatedTimeMock = jest.fn(
				getEstimatedTimeMockFactory(10)
			);
			const {
				findByDisplayValue,
				getByDisplayValue,
			} = renderReviewExperimentModal({
				getEstimatedTimeMock,
			});

			act(() => jest.runAllTimers());

			await findByDisplayValue('95');

			expect(getEstimatedTimeMock).toHaveBeenCalledTimes(1);

			userEvent.type(getByDisplayValue('95'), '90');

			act(() => jest.runAllTimers());

			await waitFor(() =>
				expect(getEstimatedTimeMock).toHaveBeenCalledTimes(2)
			);

			expect(getEstimatedTimeMock).toHaveBeenCalledWith(
				expect.objectContaining({confidenceLevel: 90})
			);
		});

		it('Triggers on every split level change', async () => {
			const getEstimatedTimeMock = jest.fn(
				getEstimatedTimeMockFactory(9)
			);
			const {getAllByDisplayValue} = renderReviewExperimentModal({
				getEstimatedTimeMock,
			});

			act(() => jest.runAllTimers());

			expect(getEstimatedTimeMock).toHaveBeenCalledTimes(1);

			const splitValueInputs = getAllByDisplayValue('25');

			userEvent.type(splitValueInputs[0], '30');

			act(() => jest.runAllTimers());

			await waitFor(() =>
				expect(getEstimatedTimeMock).toHaveBeenCalledTimes(2)
			);

			userEvent.type(splitValueInputs[1], '15');

			act(() => jest.runAllTimers());

			await waitFor(() =>
				expect(getEstimatedTimeMock).toHaveBeenCalledTimes(3)
			);
		});

		it('Informs user about an error', async () => {
			const getEstimatedTimeMock = jest.fn(() => Promise.reject());

			const {findByText} = renderReviewExperimentModal({
				getEstimatedTimeMock,
			});

			act(() => jest.runAllTimers());

			await waitFor(() =>
				expect(getEstimatedTimeMock).toHaveBeenCalledTimes(1)
			);

			await findByText('not-available');
		});

		it('Informs user about estimation', async () => {
			const getEstimatedTimeMock = jest.fn(
				getEstimatedTimeMockFactory(20)
			);

			const {findByText} = renderReviewExperimentModal({
				getEstimatedTimeMock,
			});

			act(() => jest.runAllTimers());

			await waitFor(() =>
				expect(getEstimatedTimeMock).toHaveBeenCalledTimes(1)
			);

			await findByText('20-days');
		});
	});
});

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

import '@testing-library/jest-dom/extend-expect';
import {
	cleanup,
	fireEvent,
	waitForDomChange,
	waitForElement,
	wait,
	waitForElementToBeRemoved
} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import renderApp from '../renderApp.es';
import {
	segmentsExperiment,
	segmentsExperiences,
	segmentsVariants,
	DEFAULT_ESTIMATED_DAYS
} from '../fixtures.es';
import {INITIAL_CONFIDENCE_LEVEL} from '../../../src/main/resources/META-INF/resources/js/util/percentages.es';
import {
	STATUS_FINISHED_WINNER,
	STATUS_COMPLETED,
	STATUS_RUNNING,
	STATUS_TERMINATED,
	STATUS_FINISHED_NO_WINNER
} from '../../../src/main/resources/META-INF/resources/js/util/statuses.es';

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/util/toasts.es',
	() => {
		return {
			openErrorToast: () => {},
			openSuccessToast: () => {}
		};
	}
);

describe('SegmentsExperimentsSidebar', () => {
	afterEach(cleanup);

	it('renders info message ab testing panel only available for content pages', () => {
		const {getByText} = renderApp({
			type: 'widget'
		});

		const message = getByText(
			'ab-test-is-available-only-for-content-pages'
		);

		expect(message).not.toBe(null);
	});

	it('renders ab testing panel with experience selected and zero experiments', () => {
		const {getByText, getByDisplayValue} = renderApp({
			initialSegmentsExperiences: segmentsExperiences
		});

		getByDisplayValue(segmentsExperiences[0].name);
		getByText('no-active-tests-were-found-for-the-selected-experience');
		getByText('create-test-help-message');
		getByText('create-test');
	});

	it('renders ab testing panel with experience selected and an experiment', () => {
		const {getByText, getByDisplayValue} = renderApp({
			initialSegmentsExperiences: segmentsExperiences,
			initialSegmentsExperiment: segmentsExperiment
		});

		const defaultExperience = getByDisplayValue(
			segmentsExperiences[0].name
		);
		expect(defaultExperience).not.toBe(null);

		getByText(segmentsExperiment.name);

		const createTestHelpMessage = getByText('review-and-run-test');
		expect(createTestHelpMessage).toHaveAttribute('disabled');

		getByText('edit');
	});

	it('renders modal to create experiment when the user clicks on create test button', async () => {
		const {getByText} = renderApp({
			initialSegmentsExperiences: segmentsExperiences
		});

		const createTestButton = getByText('create-test');

		fireEvent.click(createTestButton);

		await waitForElement(() => getByText('create-new-test'));

		getByText('test-name');
		getByText('description');
		getByText('save');
		getByText('cancel');
	});

	it('renders experiment status label', () => {
		const {getByText} = renderApp({
			initialSegmentsExperiment: segmentsExperiment
		});

		const statusLabel = getByText(segmentsExperiment.status.label);
		expect(statusLabel).not.toBe(null);
	});

	it("renders experiment without actions when it's not editable", () => {
		segmentsExperiment.editable = false;

		const {queryByTestId} = renderApp({
			initialSegmentsExperiment: segmentsExperiment
		});

		expect(queryByTestId('segments-experiments-drop-down')).toBe(null);

		segmentsExperiment.editable = true;
	});
});

describe('Variants', () => {
	afterEach(cleanup);

	it('renders no variants message', () => {
		const {getByText} = renderApp({
			initialSegmentsExperiences: segmentsExperiences,
			initialSegmentsExperiment: segmentsExperiment,
			initialSegmentsVariants: [segmentsVariants[0]],
			selectedSegmentsExperienceId:
				segmentsExperiment.segmentsExperimentId
		});

		const noVariantsMessage = getByText(
			'no-variants-have-been-created-for-this-test'
		);
		const variantsHelp = getByText('variants-help');

		expect(noVariantsMessage).not.toBe(null);
		expect(variantsHelp).not.toBe(null);
	});

	it('renders variant list', () => {
		const {getByText} = renderApp({
			initialSegmentsExperiences: segmentsExperiences,
			initialSegmentsExperiment: segmentsExperiment,
			initialSegmentsVariants: segmentsVariants,
			selectedSegmentsExperienceId:
				segmentsExperiment.segmentsExperimentId
		});

		const control = getByText('variant-control');
		const variant = getByText(segmentsVariants[1].name);

		expect(control).not.toBe(null);
		expect(variant).not.toBe(null);
	});

	it('create variant button', async () => {
		const {APIServiceMocks, getByText, getByLabelText} = renderApp({
			initialSegmentsExperiences: segmentsExperiences,
			initialSegmentsExperiment: segmentsExperiment,
			initialSegmentsVariants: segmentsVariants,
			selectedSegmentsExperienceId:
				segmentsExperiment.segmentsExperimentId
		});
		const {createVariant} = APIServiceMocks;

		const button = getByText('create-variant');
		expect(button).not.toBe(null);

		userEvent.click(button);

		await waitForElement(() => getByText('create-new-variant'));

		const variantNameInput = getByLabelText('name');
		expect(variantNameInput.value).toBe('');

		await userEvent.type(variantNameInput, 'Variant Name');

		const saveButton = getByText('save');

		userEvent.click(saveButton);

		await waitForElementToBeRemoved(() => getByLabelText('name'));
		await wait(() => getByText('Variant Name'));

		expect(createVariant).toHaveBeenCalledWith(
			expect.objectContaining({
				name: 'Variant Name'
			})
		);
	});

	it("renders variants without create variant button when it's not editable", () => {
		segmentsExperiment.editable = false;

		const {queryByTestId} = renderApp({
			initialSegmentsExperiment: segmentsExperiment
		});

		expect(queryByTestId('create-variant')).toBe(null);

		segmentsExperiment.editable = true;
	});
});

describe('Run and review test', () => {
	afterEach(cleanup);

	it('can view Review Experiment Modal', async () => {
		const {getByText, getByDisplayValue, getAllByDisplayValue} = renderApp({
			initialSegmentsExperiences: segmentsExperiences,
			initialSegmentsExperiment: segmentsExperiment,
			initialSegmentsVariants: segmentsVariants
		});

		getByDisplayValue(segmentsExperiences[0].name);

		getByText(segmentsExperiment.name);

		const createTestHelpMessage = getByText('review-and-run-test');
		expect(createTestHelpMessage).not.toHaveAttribute('disabled');

		userEvent.click(createTestHelpMessage);

		await waitForElement(() => getByText('traffic-split'));

		const confidenceSlider = getAllByDisplayValue(
			INITIAL_CONFIDENCE_LEVEL.toString()
		);
		const splitSliders = getAllByDisplayValue('50');

		expect(confidenceSlider.length).toBe(1);
		expect(splitSliders.length).toBe(2);
	});

	it('can view estimation time', async () => {
		const {APIServiceMocks, getByText} = renderApp({
			initialSegmentsExperiences: segmentsExperiences,
			initialSegmentsExperiment: segmentsExperiment,
			initialSegmentsVariants: segmentsVariants
		});
		const {getEstimatedTime} = APIServiceMocks;

		const runTestButton = getByText('review-and-run-test');
		userEvent.click(runTestButton);

		await waitForElement(() => getByText('traffic-split'));

		await waitForElement(() => getByText(DEFAULT_ESTIMATED_DAYS.message));
		expect(getEstimatedTime).toHaveBeenCalledTimes(1);
	});

	test("can run test that won't be editable", async () => {
		const {APIServiceMocks, queryAllByLabelText, getByText} = renderApp({
			initialSegmentsExperiences: segmentsExperiences,
			initialSegmentsExperiment: segmentsExperiment,
			initialSegmentsVariants: segmentsVariants
		});
		const {runExperiment} = APIServiceMocks;

		const actionButtons = queryAllByLabelText('show-actions');

		/*
		 * One _show actions button_ for the Experiment and one for the Variant
		 */
		expect(actionButtons.length).toBe(2);

		const runTestButton = getByText('review-and-run-test');

		userEvent.click(runTestButton);

		await waitForElement(() => getByText('traffic-split'));

		const confirmRunExperimentButton = getByText('run');

		userEvent.click(confirmRunExperimentButton);

		await waitForElement(() => getByText('test-running-message'));

		expect(runExperiment).toHaveBeenCalledWith(
			expect.objectContaining({
				confidenceLevel: INITIAL_CONFIDENCE_LEVEL / 100,
				segmentsExperimentId: segmentsExperiment.segmentsExperimentId,
				status: STATUS_RUNNING
			})
		);

		await waitForElement(() => getByText('ok'));
		const okButton = getByText('ok');

		userEvent.click(okButton);

		/*
		 * There are no action buttons on a running Experiment
		 */
		expect(queryAllByLabelText('show-actions').length).toBe(0);
	});

	it('Variants cannot be edited/deleted/added in a running experiment', async () => {
		const runningExperiment = {
			...segmentsExperiment,
			editable: false,
			status: {
				label: 'completed',
				status: STATUS_RUNNING
			}
		};

		const {queryAllByLabelText} = renderApp({
			initialSegmentsExperiences: segmentsExperiences,
			initialSegmentsExperiment: runningExperiment,
			initialSegmentsVariants: segmentsVariants
		});

		/*
		 * There is one traffic split label per variant
		 */
		expect(queryAllByLabelText('traffic-split').length).toBe(
			segmentsVariants.length
		);
		/*
		 * There is no show action button
		 */
		expect(queryAllByLabelText('show-actions').length).toBe(0);
	});
});

describe('Click Target selection', () => {
	test.todo(
		'Set Click Target section appears when draft test has goal set to click'
	);

	test.todo(
		'All clickable elements highlighted when the user clicks on Set Target Button'
	);

	test.todo(
		'A tooltip Click Element to Set as Click Target for your Goal appears when the user clicks on Set Target Button'
	);

	test.todo('Selectable as target elements show tooltips on hover');

	test.todo(
		'When the user Set Element as Click Target, then The element is set as the Click Target and the id of the element as a link'
	);

	test.todo(
		'Cancel selection click target element proccess when clickign out of selection zone'
	);

	test.todo(
		'When hovering over to invalid click target elements, the mouse is displayed in not-allowed mode'
	);

	test.todo(
		'The user can edit a selected click target in a draft experiment'
	);

	test.todo('The user can remove a selected click target in a draft element');

	test.todo(
		'The user clicks in the UI reference to the selected click target element, the page scrolls to make it visible'
	);
});

describe('Experiment history tab', () => {
	afterEach(cleanup);

	test('test is archived after terminating it', async () => {
		window.confirm = jest.fn(() => true);

		const runningExperiment = {
			...segmentsExperiment,
			editable: false,
			status: {
				label: 'completed',
				value: STATUS_RUNNING
			}
		};

		const {APIServiceMocks, getByText} = renderApp({
			initialSegmentsExperiences: segmentsExperiences,
			initialSegmentsExperiment: runningExperiment,
			initialSegmentsVariants: segmentsVariants
		});
		const {editExperimentStatus} = APIServiceMocks;

		const terminateButton = getByText('terminate-test');

		userEvent.click(terminateButton);

		expect(window.confirm).toBeCalled();
		expect(editExperimentStatus).toHaveBeenCalledWith(
			expect.objectContaining({
				status: STATUS_TERMINATED
			})
		);

		await waitForElementToBeRemoved(() => getByText('terminate-test'));

		/*
		 * Terminated test should be archived now
		 */
		await waitForElement(() => getByText('history (1)'));
		getByText('create-test');
	});

	test('test is archive after completing it', async () => {
		const noWinnerDeclaredExperiment = {
			...segmentsExperiment,
			editable: false,
			status: {
				label: 'no winner',
				value: STATUS_FINISHED_NO_WINNER
			}
		};

		const {APIServiceMocks, getByText, queryAllByText} = renderApp({
			initialSegmentsExperiences: segmentsExperiences,
			initialSegmentsExperiment: noWinnerDeclaredExperiment,
			initialSegmentsVariants: segmentsVariants
		});
		const {publishExperience} = APIServiceMocks;

		const publishButtons = queryAllByText('publish');

		/*
		 * Only the alternative variant has a publish button
		 */
		expect(publishButtons.length).toBe(1);

		getByText('discard-test');

		userEvent.click(publishButtons[0]);

		await waitForElement(() => getByText('completed'));

		expect(publishExperience).toHaveBeenCalledWith(
			expect.objectContaining({
				segmentsExperimentId:
					noWinnerDeclaredExperiment.segmentsExperimentId,
				status: STATUS_COMPLETED,
				winnerSegmentsExperienceId:
					segmentsVariants[1].segmentsExperienceId
			})
		);

		const historyTab = getByText('history (1)');

		userEvent.click(historyTab);

		await waitForDomChange();

		await waitForElement(() => getByText(segmentsExperiment.name));
	});

	test('tests have name, description and status label', async () => {
		const experimentHistory = [
			{
				...segmentsExperiment,
				description: 'archived 1 description',
				name: 'archived 1',
				segmentsExperimentId: 'h-1',
				status: {
					label: 'terminated',
					value: STATUS_TERMINATED
				}
			},
			{
				...segmentsExperiment,
				name: 'archived 2',
				segmentsExperimentId: 'h-2',
				status: {
					label: 'completed',
					value: STATUS_COMPLETED
				}
			}
		];

		const {getByText} = renderApp({
			initialExperimentHistory: experimentHistory
		});

		/*
		 * History tab has the number of arhived Experiments
		 */
		const historyTab = getByText('history (2)');

		userEvent.click(historyTab);

		await waitForDomChange();

		await waitForElement(() => getByText(experimentHistory[0].name));

		/*
		 * Experiment 1 is present in the UI
		 */
		getByText(experimentHistory[0].name);
		getByText(experimentHistory[0].description);
		getByText(experimentHistory[0].status.label);

		/*
		 * Experiment 2 is present in the UI
		 */
		getByText(experimentHistory[1].name);
		getByText(experimentHistory[1].description);
		getByText(experimentHistory[1].status.label);
	});
});

describe('Winner declared', () => {
	afterEach(cleanup);

	it('experiment has basic Winner Declared basic elements', () => {
		const {getByText, getAllByText} = renderApp({
			initialSegmentsExperiences: segmentsExperiences,
			initialSegmentsExperiment: {
				...segmentsExperiment,
				editable: false,
				status: {
					label: 'Winner Declared',
					value: STATUS_FINISHED_WINNER
				}
			},
			initialSegmentsVariants: segmentsVariants,
			winnerSegmentsVariantId: '1'
		});

		getByText('discard-test');
		getByText('Winner Declared');
		const allPublishButtons = getAllByText('publish');

		expect(allPublishButtons.length).toBe(segmentsVariants.length - 1);
	});

	it('variants publish action button action', async () => {
		const {APIServiceMocks, getByText} = renderApp({
			initialSegmentsExperiences: segmentsExperiences,
			initialSegmentsExperiment: {
				...segmentsExperiment,
				editable: false,
				status: {
					label: 'Winner Declared',
					value: STATUS_FINISHED_WINNER
				}
			},
			initialSegmentsVariants: segmentsVariants,
			winnerSegmentsVariantId: '1'
		});
		const {publishExperience} = APIServiceMocks;

		const publishButton = getByText('publish');

		userEvent.click(publishButton);

		expect(publishExperience).toHaveBeenCalledWith({
			segmentsExperimentId: segmentsExperiment.segmentsExperimentId,
			status: STATUS_COMPLETED,
			winnerSegmentsExperienceId: segmentsVariants[1].segmentsExperienceId
		});
		await waitForElement(() => getByText('completed'));
	});

	it('discard button action', async () => {
		const {APIServiceMocks, getByText} = renderApp({
			initialSegmentsExperiences: segmentsExperiences,
			initialSegmentsExperiment: {
				...segmentsExperiment,
				editable: false,
				status: {
					label: 'Winner Declared',
					value: STATUS_FINISHED_WINNER
				}
			},
			initialSegmentsVariants: segmentsVariants,
			winnerSegmentsVariantId: '1'
		});
		const {publishExperience} = APIServiceMocks;

		const publishButton = getByText('discard-test');

		userEvent.click(publishButton);

		expect(publishExperience).toHaveBeenCalledWith({
			segmentsExperimentId: segmentsExperiment.segmentsExperimentId,
			status: STATUS_COMPLETED,
			winnerSegmentsExperienceId: segmentsExperiment.segmentsExperienceId
		});

		await waitForElement(() => getByText('completed'));
	});
});

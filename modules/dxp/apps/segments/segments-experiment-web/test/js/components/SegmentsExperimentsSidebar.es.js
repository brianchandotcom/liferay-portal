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

import '@testing-library/jest-dom/extend-expect';
import {
	fireEvent,
	waitForElementToBeRemoved,
	within,
} from '@testing-library/react';
import userEvent from '@testing-library/user-event';

import {INITIAL_CONFIDENCE_LEVEL} from '../../../src/main/resources/META-INF/resources/js/util/percentages.es';
import {
	STATUS_COMPLETED,
	STATUS_FINISHED_NO_WINNER,
	STATUS_FINISHED_WINNER,
	STATUS_RUNNING,
	STATUS_TERMINATED,
} from '../../../src/main/resources/META-INF/resources/js/util/statuses.es';
import {
	controlVariant,
	segmentsExperiment,
	segmentsVariants,
} from '../fixtures.es';
import renderApp from '../renderApp.es';

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/util/toasts.es',
	() => {
		return {
			openErrorToast: () => {},
			openSuccessToast: () => {},
		};
	}
);

describe('SegmentsExperimentsSidebar', () => {
	it('Renders info message ab testing panel only available for content pages', () => {
		const {getByText} = renderApp({
			type: 'widget',
		});

		const message = getByText(
			'ab-test-is-available-only-for-content-pages'
		);

		expect(message).not.toBe(null);
	});

	it('Renders ab testing panel with zero experiments', () => {
		const {getByText} = renderApp();

		getByText('no-active-tests-were-found-for-the-selected-experience');
		getByText('create-test-help-message');
		getByText('create-test');
	});

	it('Renders ab testing panel with an experiment', () => {
		const {getByText} = renderApp({
			initialSegmentsExperiment: segmentsExperiment,
		});

		getByText(segmentsExperiment.name);
		getByText(segmentsExperiment.description);
		getByText(segmentsExperiment.segmentsEntryName);
		getByText(segmentsExperiment.goal.label);

		getByText('edit');
		getByText('delete');

		getByText('review-and-run-test');
		getByText('view-data-in-analytics-cloud');
	});

	it('Renders modal to create experiment when the user clicks on create test button', async () => {
		const {findByText, getByText} = renderApp();

		const createTestButton = getByText('create-test');

		fireEvent.click(createTestButton);

		await findByText('create-new-test');

		getByText('test-name');
		getByText('description');
		getByText('save');
		getByText('cancel');
	});

	it('Renders experiment status label', () => {
		const {getByText} = renderApp({
			initialSegmentsExperiment: segmentsExperiment,
		});

		const statusLabel = getByText(segmentsExperiment.status.label);
		expect(statusLabel).not.toBe(null);
	});

	it("Renders experiment without actions when it's not editable", () => {
		segmentsExperiment.editable = false;

		const {queryByTestId} = renderApp({
			initialSegmentsExperiment: segmentsExperiment,
		});

		expect(queryByTestId('segments-experiments-drop-down')).toBe(null);

		segmentsExperiment.editable = true;
	});

	it('Sidebar indicates mandatory sections in sidebar with asterisks icons', () => {
		const experiment = {
			...segmentsExperiment,
			goal: {
				label: 'Click',
				value: 'click',
			},
		};

		const {getByText} = renderApp({
			initialSegmentsExperiment: experiment,
		});

		const clickGoalSection = getByText('click-goal');
		within(clickGoalSection).getByRole('presentation');

		const variantsSection = getByText('variants');
		within(variantsSection).getByRole('presentation');
	});
});

describe('Variants', () => {
	it('Renders no variants message', () => {
		const {getByText} = renderApp({
			initialSegmentsExperiment: segmentsExperiment,
			initialSegmentsVariants: [segmentsVariants[0]],
			selectedSegmentsExperienceId:
				segmentsExperiment.segmentsExperimentId,
		});

		const noVariantsMessage = getByText(
			'no-variants-have-been-created-for-this-test'
		);
		const variantsHelp = getByText('variants-help');

		expect(noVariantsMessage).not.toBe(null);
		expect(variantsHelp).not.toBe(null);
	});

	it('Renders variant list', () => {
		const {getByText} = renderApp({
			initialSegmentsExperiment: segmentsExperiment,
			initialSegmentsVariants: segmentsVariants,
			selectedSegmentsExperienceId:
				segmentsExperiment.segmentsExperimentId,
		});

		const control = getByText('variant-control');
		const variant = getByText(segmentsVariants[1].name);

		expect(control).not.toBe(null);
		expect(variant).not.toBe(null);
	});

	it('Create variant button', async () => {
		const {
			APIServiceMocks,
			findByText,
			getByLabelText,
			getByText,
		} = renderApp({
			initialSegmentsExperiment: segmentsExperiment,
			initialSegmentsVariants: segmentsVariants,
			selectedSegmentsExperienceId:
				segmentsExperiment.segmentsExperimentId,
		});
		const {createVariant} = APIServiceMocks;

		const button = getByText('create-variant');
		expect(button).not.toBe(null);

		userEvent.click(button);

		await findByText('create-new-variant');

		const variantNameInput = getByLabelText('name');
		expect(variantNameInput.value).toBe('');

		await userEvent.type(variantNameInput, 'Variant Name');

		const saveButton = getByText('save');

		userEvent.click(saveButton);

		await waitForElementToBeRemoved(() => getByLabelText('name'));
		await findByText('Variant Name');

		expect(createVariant).toHaveBeenCalledWith(
			expect.objectContaining({
				name: 'Variant Name',
			})
		);
	});

	it("Renders variants without create variant button when it's not editable", () => {
		segmentsExperiment.editable = false;

		const {queryByTestId} = renderApp({
			initialSegmentsExperiment: segmentsExperiment,
		});

		expect(queryByTestId('create-variant')).toBe(null);

		segmentsExperiment.editable = true;
	});
});

describe('Review and Run test', () => {
	it('Can view review experiment modal', async () => {
		const {findByText, getAllByDisplayValue, getByText} = renderApp({
			initialSegmentsExperiment: segmentsExperiment,
			initialSegmentsVariants: segmentsVariants,
		});

		getByText(segmentsExperiment.name);

		const reviewAndRunTestButton = getByText('review-and-run-test');

		userEvent.click(reviewAndRunTestButton);

		await findByText('traffic-split');

		const confidenceSlider = getAllByDisplayValue(
			INITIAL_CONFIDENCE_LEVEL.toString()
		);
		const splitSliders = getAllByDisplayValue('50');

		expect(confidenceSlider.length).toBe(1);
		expect(splitSliders.length).toBe(2);
	});

	it('Error messages appears when the user clicks in review and run and there is no click target element selected', async () => {
		const experiment = {
			...segmentsExperiment,
			goal: {
				label: 'Click',
				value: 'click',
			},
		};

		const {getByText} = renderApp({
			initialSegmentsExperiment: experiment,
		});

		getByText(experiment.name);

		const reviewAndRunTestButton = getByText('review-and-run-test');

		userEvent.click(reviewAndRunTestButton);

		getByText('an-element-needs-to-be-selected');
	});

	it('Error messages appears when the user clicks in review and run and there is only the control variant created', async () => {
		const {getByText} = renderApp({
			initialSegmentsExperiment: segmentsExperiment,
			initialSegmentsVariants: controlVariant,
		});

		getByText(segmentsExperiment.name);

		const reviewAndRunTestButton = getByText('review-and-run-test');

		userEvent.click(reviewAndRunTestButton);

		getByText('a-variant-needs-to-be-created');
	});

	it("Can run test that won't be editable", async () => {
		const {
			APIServiceMocks,
			findByText,
			getByText,
			queryAllByLabelText,
		} = renderApp({
			initialSegmentsExperiment: segmentsExperiment,
			initialSegmentsVariants: segmentsVariants,
		});
		const {runExperiment} = APIServiceMocks;

		const actionButtons = queryAllByLabelText('show-actions');

		/*
		 * One _show actions button_ for the Experiment and one for the Variant
		 */
		expect(actionButtons.length).toBe(2);

		const reviewAndRunTestButton = getByText('review-and-run-test');

		userEvent.click(reviewAndRunTestButton);

		await findByText('traffic-split');

		const confirmRunExperimentButton = getByText('run');

		userEvent.click(confirmRunExperimentButton);

		await findByText('test-running-message');

		expect(runExperiment).toHaveBeenCalledWith(
			expect.objectContaining({
				confidenceLevel: INITIAL_CONFIDENCE_LEVEL / 100,
				segmentsExperimentId: segmentsExperiment.segmentsExperimentId,
				status: STATUS_RUNNING,
			})
		);

		await findByText('ok');
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
				label: 'running',
				status: STATUS_RUNNING,
			},
		};

		const {queryAllByLabelText} = renderApp({
			initialSegmentsExperiment: runningExperiment,
			initialSegmentsVariants: segmentsVariants,
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

describe('Experiment History Tab', () => {
	it('Experiment is archived after terminating it', async () => {
		window.confirm = jest.fn(() => true);

		const runningExperiment = {
			...segmentsExperiment,
			editable: false,
			status: {
				label: 'running',
				value: STATUS_RUNNING,
			},
		};

		const {APIServiceMocks, findByText, getByText} = renderApp({
			initialSegmentsExperiment: runningExperiment,
			initialSegmentsVariants: segmentsVariants,
		});
		const {editExperimentStatus} = APIServiceMocks;

		const terminateButton = getByText('terminate-test');

		userEvent.click(terminateButton);

		expect(window.confirm).toBeCalled();
		expect(editExperimentStatus).toHaveBeenCalledWith(
			expect.objectContaining({
				segmentsExperimentId: segmentsExperiment.segmentsExperimentId,
				status: STATUS_TERMINATED,
			})
		);

		await waitForElementToBeRemoved(() => getByText('terminate-test'));

		/*
		 * Terminated test should be archived now
		 */
		await findByText('history[record] (1)');
		getByText('create-test');
	});

	it('Experiment is archive after completing it', async () => {
		const noWinnerDeclaredExperiment = {
			...segmentsExperiment,
			editable: false,
			status: {
				label: 'no winner',
				value: STATUS_FINISHED_NO_WINNER,
			},
		};

		const {
			APIServiceMocks,
			findByText,
			getByText,
			queryAllByText,
		} = renderApp({
			initialSegmentsExperiment: noWinnerDeclaredExperiment,
			initialSegmentsVariants: segmentsVariants,
		});
		const {publishExperience} = APIServiceMocks;

		const publishButtons = queryAllByText('publish');

		/*
		 * Only the alternative variant has a publish button
		 */
		expect(publishButtons.length).toBe(1);

		getByText('discard-test');

		userEvent.click(publishButtons[0]);

		await findByText('completed');

		expect(publishExperience).toHaveBeenCalledWith(
			expect.objectContaining({
				segmentsExperimentId:
					noWinnerDeclaredExperiment.segmentsExperimentId,
				status: STATUS_COMPLETED,
				winnerSegmentsExperienceId:
					segmentsVariants[1].segmentsExperienceId,
			})
		);

		const historyTab = getByText('history[record] (1)');

		userEvent.click(historyTab);

		await findByText(segmentsExperiment.name);
	});

	it('Experiments have name, description and status label', async () => {
		const experimentHistory = [
			{
				...segmentsExperiment,
				description: 'archived 1 description',
				name: 'archived 1',
				segmentsExperimentId: 'h-1',
				status: {
					label: 'terminated',
					value: STATUS_TERMINATED,
				},
			},
			{
				...segmentsExperiment,
				name: 'archived 2',
				segmentsExperimentId: 'h-2',
				status: {
					label: 'completed',
					value: STATUS_COMPLETED,
				},
			},
		];

		const {findByText, getByText} = renderApp({
			initialExperimentHistory: experimentHistory,
		});

		/*
		 * History tab has the number of arhived Experiments
		 */
		const historyTab = getByText('history[record] (2)');

		userEvent.click(historyTab);

		await findByText(experimentHistory[0].name);

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

describe('No Winner Declared', () => {
	it('Experiment has basic no winner declared elements', () => {
		const {getAllByText, getByText} = renderApp({
			initialSegmentsExperiment: {
				...segmentsExperiment,
				editable: false,
				status: {
					label: 'No Winner Declared',
					value: STATUS_FINISHED_NO_WINNER,
				},
			},
			initialSegmentsVariants: segmentsVariants,
			winnerSegmentsVariantId: '',
		});

		getByText('discard-test');
		getByText('No Winner Declared');
		const allPublishButtons = getAllByText('publish');

		expect(allPublishButtons.length).toBe(segmentsVariants.length - 1);
	});

	it('Variant publish action button when confirming in no winner declared status', async () => {

		/**
		 * The user accepts the confirmation message
		 */
		global.confirm = jest.fn(() => true);

		const {APIServiceMocks, findByText, getByText} = renderApp({
			initialSegmentsExperiment: {
				...segmentsExperiment,
				editable: false,
				status: {
					label: 'No Winner Declared',
					value: STATUS_FINISHED_NO_WINNER,
				},
			},
			initialSegmentsVariants: segmentsVariants,
			winnerSegmentsVariantId: '',
		});
		const {publishExperience} = APIServiceMocks;

		const publishButton = getByText('publish');

		userEvent.click(publishButton);

		/**
		 * The user has accepted one confirmation message
		 */
		expect(global.confirm).toHaveBeenCalledTimes(1);

		expect(publishExperience).toHaveBeenCalledWith({
			segmentsExperimentId: segmentsExperiment.segmentsExperimentId,
			status: STATUS_COMPLETED,
			winnerSegmentsExperienceId:
				segmentsVariants[1].segmentsExperienceId,
		});

		await findByText('completed');
	});

	it('Variant publish action button when not confirming in no winner declared status', async () => {

		/**
		 * The user rejects the confirmation message
		 */
		global.confirm = jest.fn(() => false);

		const {APIServiceMocks, getByText} = renderApp({
			initialSegmentsExperiment: {
				...segmentsExperiment,
				editable: false,
				status: {
					label: 'No Winner Declared',
					value: STATUS_FINISHED_NO_WINNER,
				},
			},
			initialSegmentsVariants: segmentsVariants,
			winnerSegmentsVariantId: '',
		});
		const {publishExperience} = APIServiceMocks;

		const publishButton = getByText('publish');

		userEvent.click(publishButton);

		/**
		 * The user has rejected one confirmation message
		 */
		expect(global.confirm).toHaveBeenCalledTimes(1);

		/**
		 * API has not been called
		 */
		expect(publishExperience).toHaveBeenCalledTimes(0);
	});
});

describe('Winner declared', () => {
	it('Experiment has basic winner declared elements', () => {
		const {getAllByText, getByText} = renderApp({
			initialSegmentsExperiment: {
				...segmentsExperiment,
				editable: false,
				status: {
					label: 'Winner Declared',
					value: STATUS_FINISHED_WINNER,
				},
			},
			initialSegmentsVariants: segmentsVariants,
			winnerSegmentsVariantId: '1',
		});

		const winner = getByText('-is-the-winner-variant');
		within(winner).getByText('Variant');

		getByText('publish-winner');
		getByText('discard-test');
		getByText('Winner Declared');
		const allPublishButtons = getAllByText('publish');

		expect(allPublishButtons.length).toBe(segmentsVariants.length - 1);
	});

	it('Variant publish winner action button in alert in winner declared status', async () => {

		/**
		 * The user accepts the confirmation message
		 */
		global.confirm = jest.fn(() => true);

		const {APIServiceMocks, findByText, getByText} = renderApp({
			initialSegmentsExperiment: {
				...segmentsExperiment,
				editable: false,
				status: {
					label: 'Winner Declared',
					value: STATUS_FINISHED_WINNER,
				},
			},
			initialSegmentsVariants: segmentsVariants,
			winnerSegmentsVariantId: '1',
		});
		const {publishExperience} = APIServiceMocks;

		const publishWinnerButton = getByText('publish-winner');

		userEvent.click(publishWinnerButton);

		/**
		 * The user has accepted one confirmation message
		 */
		expect(global.confirm).toHaveBeenCalledTimes(1);

		expect(publishExperience).toHaveBeenCalledWith({
			segmentsExperimentId: segmentsExperiment.segmentsExperimentId,
			status: STATUS_COMPLETED,
			winnerSegmentsExperienceId:
				segmentsVariants[1].segmentsExperienceId,
		});

		await findByText('completed');
	});

	it('Variant publish action button when confirming in winner declared status', async () => {

		/**
		 * The user accepts the confirmation message
		 */
		global.confirm = jest.fn(() => true);

		const {APIServiceMocks, findByText, getByText} = renderApp({
			initialSegmentsExperiment: {
				...segmentsExperiment,
				editable: false,
				status: {
					label: 'Winner Declared',
					value: STATUS_FINISHED_WINNER,
				},
			},
			initialSegmentsVariants: segmentsVariants,
			winnerSegmentsVariantId: '1',
		});
		const {publishExperience} = APIServiceMocks;

		const publishButton = getByText('publish');

		userEvent.click(publishButton);

		/**
		 * The user has accepted one confirmation message
		 */
		expect(global.confirm).toHaveBeenCalledTimes(1);

		expect(publishExperience).toHaveBeenCalledWith({
			segmentsExperimentId: segmentsExperiment.segmentsExperimentId,
			status: STATUS_COMPLETED,
			winnerSegmentsExperienceId:
				segmentsVariants[1].segmentsExperienceId,
		});

		await findByText('completed');
	});

	it('Variant publish action button when not confirming in winner declared status', async () => {

		/**
		 * The user rejects the confirmation message
		 */
		global.confirm = jest.fn(() => false);

		const {APIServiceMocks, getByText} = renderApp({
			initialSegmentsExperiment: {
				...segmentsExperiment,
				editable: false,
				status: {
					label: 'Winner Declared',
					value: STATUS_FINISHED_WINNER,
				},
			},
			initialSegmentsVariants: segmentsVariants,
			winnerSegmentsVariantId: '1',
		});
		const {publishExperience} = APIServiceMocks;

		const publishButton = getByText('publish');

		userEvent.click(publishButton);

		/**
		 * The user has rejected one confirmation message
		 */
		expect(global.confirm).toHaveBeenCalledTimes(1);

		/**
		 * API has not been called
		 */
		expect(publishExperience).toHaveBeenCalledTimes(0);
	});

	it('Discard button action', async () => {
		const {APIServiceMocks, findByText, getByText} = renderApp({
			initialSegmentsExperiment: {
				...segmentsExperiment,
				editable: false,
				status: {
					label: 'Winner Declared',
					value: STATUS_FINISHED_WINNER,
				},
			},
			initialSegmentsVariants: segmentsVariants,
			winnerSegmentsVariantId: '1',
		});
		const {publishExperience} = APIServiceMocks;

		const publishButton = getByText('discard-test');

		userEvent.click(publishButton);

		expect(publishExperience).toHaveBeenCalledWith({
			segmentsExperimentId: segmentsExperiment.segmentsExperimentId,
			status: STATUS_COMPLETED,
			winnerSegmentsExperienceId: segmentsExperiment.segmentsExperienceId,
		});

		await findByText('completed');
	});
});

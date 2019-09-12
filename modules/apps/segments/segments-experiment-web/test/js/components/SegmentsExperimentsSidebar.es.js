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
	render,
	waitForElement,
	wait,
	waitForElementToBeRemoved
} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import SegmentsExperimentsSidebar from '../../../src/main/resources/META-INF/resources/js/components/SegmentsExperimentsSidebar.es';
import SegmentsExperimentsContext from '../../../src/main/resources/META-INF/resources/js/context.es';
import React from 'react';
import {
	segmentsExperiment,
	segmentsExperiences,
	segmentsGoals,
	segmentsVariants
} from '../fixtures.es';
import {INITIAL_CONFIDENCE_LEVEL} from '../../../src/main/resources/META-INF/resources/js/util/percentages.es';
import {
	STATUS_FINISHED_WINNER,
	STATUS_COMPLETED
} from '../../../src/main/resources/META-INF/resources/js/util/statuses.es';

function _renderSegmentsExperimentsSidebarComponent({
	classNameId = '',
	classPK = '',
	initialGoals = segmentsGoals,
	initialSegmentsExperiences = [],
	initialSegmentsExperiment,
	initialSegmentsVariants = [],
	APIService = {},
	selectedSegmentsExperienceId,
	type = 'content',
	winnerSegmentsVariantId = null
} = {}) {
	const {
		createExperiment = () => {},
		createVariant = () => {},
		deleteVariant = () => {},
		discardExperiment = () => {},
		editExperiment = () => {},
		editVariant = () => {},
		publishExperience = () => {}
	} = APIService;

	return render(
		<SegmentsExperimentsContext.Provider
			value={{
				APIService: {
					createExperiment,
					createVariant,
					deleteVariant,
					discardExperiment,
					editExperiment,
					editVariant,
					publishExperience
				},
				assetsPath: '',
				page: {
					classNameId,
					classPK,
					type
				}
			}}
		>
			<SegmentsExperimentsSidebar
				initialGoals={initialGoals}
				initialSegmentsExperiences={initialSegmentsExperiences}
				initialSegmentsExperiment={initialSegmentsExperiment}
				initialSegmentsVariants={initialSegmentsVariants}
				selectedSegmentsExperienceId={selectedSegmentsExperienceId}
				winnerSegmentsVariantId={winnerSegmentsVariantId}
			/>
		</SegmentsExperimentsContext.Provider>,
		{
			baseElement: document.body
		}
	);
}

describe('SegmentsExperimentsSidebar', () => {
	afterEach(cleanup);

	it('renders info message ab testing panel only available for content pages', () => {
		const {getByText} = _renderSegmentsExperimentsSidebarComponent({
			type: 'widget'
		});

		const message = getByText(
			'ab-test-is-available-only-for-content-pages'
		);

		expect(message).not.toBe(null);
	});

	it('renders ab testing panel with experience selected and zero experiments', () => {
		const {
			getByText,
			getByDisplayValue
		} = _renderSegmentsExperimentsSidebarComponent({
			initialSegmentsExperiences: segmentsExperiences
		});

		getByDisplayValue(segmentsExperiences[0].name);
		getByText('no-active-tests-were-found-for-the-selected-experience');
		getByText('create-test-help-message');
		getByText('create-test');
	});

	it('renders ab testing panel with experience selected and an experiment', () => {
		const {
			getByText,
			getByDisplayValue
		} = _renderSegmentsExperimentsSidebarComponent({
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

	it('renders modal to create experiment when the user clicks on create test button', async done => {
		const {getByText} = _renderSegmentsExperimentsSidebarComponent({
			initialSegmentsExperiences: segmentsExperiences
		});

		const createTestButton = getByText('create-test');

		fireEvent.click(createTestButton);

		await waitForElement(() => getByText('create-new-test'));

		getByText('test-name');
		getByText('description');
		getByText('save');
		getByText('cancel');
		done();
	});

	it('renders experiment status label', () => {
		const {getByText} = _renderSegmentsExperimentsSidebarComponent({
			initialSegmentsExperiment: segmentsExperiment
		});

		const statusLabel = getByText(segmentsExperiment.status.label);
		expect(statusLabel).not.toBe(null);
	});

	it("renders experiment without actions when it's not editable", () => {
		segmentsExperiment.editable = false;

		const {queryByTestId} = _renderSegmentsExperimentsSidebarComponent({
			initialSegmentsExperiment: segmentsExperiment
		});

		expect(queryByTestId('segments-experiments-drop-down')).toBe(null);

		segmentsExperiment.editable = true;
	});
});

describe('Variants', () => {
	afterEach(cleanup);

	it('renders no variants message', () => {
		const {getByText} = _renderSegmentsExperimentsSidebarComponent({
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
		const {getByText} = _renderSegmentsExperimentsSidebarComponent({
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

	it('create variant button', async done => {
		const createVariantMock = jest.fn(variant =>
			Promise.resolve({
				segmentsExperimentRel: {
					name: variant.name,
					segmentsExperienceId: JSON.stringify(Math.random()),
					segmentsExperimentId: JSON.stringify(Math.random()),
					segmentsExperimentRelId: JSON.stringify(Math.random()),
					split: 0.0
				}
			})
		);
		const {
			getByText,
			getByLabelText
		} = _renderSegmentsExperimentsSidebarComponent({
			APIService: {
				createVariant: createVariantMock
			},
			initialSegmentsExperiences: segmentsExperiences,
			initialSegmentsExperiment: segmentsExperiment,
			initialSegmentsVariants: segmentsVariants,
			selectedSegmentsExperienceId:
				segmentsExperiment.segmentsExperimentId
		});

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

		expect(createVariantMock).toHaveBeenCalledWith(
			expect.objectContaining({
				name: 'Variant Name'
			})
		);

		expect(getByText('Variant Name')).not.toBe(null);

		done();
	});

	it("renders variants without create variant button when it's not editable", () => {
		segmentsExperiment.editable = false;

		const {queryByTestId} = _renderSegmentsExperimentsSidebarComponent({
			initialSegmentsExperiment: segmentsExperiment
		});

		expect(queryByTestId('create-variant')).toBe(null);

		segmentsExperiment.editable = true;
	});
});

describe('Run and review test', () => {
	it('can view review Experiment Modal', async done => {
		const {
			getByText,
			getByDisplayValue,
			getAllByDisplayValue
		} = _renderSegmentsExperimentsSidebarComponent({
			initialSegmentsExperiences: segmentsExperiences,
			initialSegmentsExperiment: segmentsExperiment,
			initialSegmentsVariants: segmentsVariants
		});

		const defaultExperience = getByDisplayValue(
			segmentsExperiences[0].name
		);
		expect(defaultExperience).not.toBe(null);

		const experiment = getByText(segmentsExperiment.name);
		expect(experiment).not.toBe(null);

		const createTestHelpMessage = getByText('review-and-run-test');
		expect(createTestHelpMessage).not.toBe(null);
		expect(createTestHelpMessage).not.toHaveAttribute('disabled');

		userEvent.click(createTestHelpMessage);

		await waitForElement(() => getByText('traffic-split'));

		const confidenceSlider = getAllByDisplayValue(
			INITIAL_CONFIDENCE_LEVEL.toString()
		);
		const splitSliders = getAllByDisplayValue('50');

		expect(confidenceSlider.length).toBe(1);
		expect(splitSliders.length).toBe(2);
		done();
	});
});

describe('Winner declared', () => {
	afterEach(cleanup);

	it('experiment has basic Winner Declared basic elements', () => {
		const {
			getByText,
			getAllByText
		} = _renderSegmentsExperimentsSidebarComponent({
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

	it('variants publish action button action', async done => {
		const mockPublish = jest.fn(({status}) => {
			return Promise.resolve({
				segmentsExperiment: {
					...segmentsExperiment,
					status: {
						label: 'completed',
						value: status
					}
				}
			});
		});
		const {getByText} = _renderSegmentsExperimentsSidebarComponent({
			APIService: {
				publishExperience: mockPublish
			},
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

		const publishButton = getByText('publish');

		userEvent.click(publishButton);

		expect(mockPublish).toHaveBeenCalledWith({
			segmentsExperimentId: segmentsExperiment.segmentsExperimentId,
			status: STATUS_COMPLETED,
			winnerSegmentsExperienceId: segmentsVariants[1].segmentsExperienceId
		});
		await waitForElement(() => getByText('completed'));

		done();
	});

	it('discard button action', async done => {
		const mockDiscard = jest.fn(({status}) => {
			return Promise.resolve({
				segmentsExperiment: {
					...segmentsExperiment,
					status: {
						label: 'completed',
						value: status
					}
				}
			});
		});

		const {getByText} = _renderSegmentsExperimentsSidebarComponent({
			APIService: {
				discardExperiment: mockDiscard
			},
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

		const publishButton = getByText('discard-test');

		userEvent.click(publishButton);

		expect(mockDiscard).toHaveBeenCalledWith({
			segmentsExperimentId: segmentsExperiment.segmentsExperimentId,
			status: STATUS_COMPLETED
		});

		await waitForElement(() => getByText('completed'));

		done();
	});
});

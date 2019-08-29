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

function _renderSegmentsExperimentsSidebarComponent({
	classNameId = '',
	classPK = '',
	initialGoals = segmentsGoals,
	initialSegmentsExperiences = [],
	initialSegmentsExperiment,
	initialSegmentsVariants = [],
	segmentsExperimentsUtil = {},
	selectedSegmentsExperienceId,
	type = 'content'
} = {}) {
	const {
		createExperiment = () => {},
		createVariant = () => {},
		deleteVariant = () => {},
		editExperiment = () => {},
		editVariant = () => {}
	} = segmentsExperimentsUtil;

	return render(
		<SegmentsExperimentsContext.Provider
			value={{
				page: {
					classNameId,
					classPK,
					type
				},
				segmentsExperimentsUtil: {
					createExperiment,
					createVariant,
					deleteVariant,
					editExperiment,
					editVariant
				}
			}}
		>
			<SegmentsExperimentsSidebar
				initialGoals={initialGoals}
				initialSegmentsExperiences={initialSegmentsExperiences}
				initialSegmentsExperiment={initialSegmentsExperiment}
				initialSegmentsVariants={initialSegmentsVariants}
				selectedSegmentsExperienceId={selectedSegmentsExperienceId}
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

		const defaultExperience = getByDisplayValue(
			segmentsExperiences[0].name
		);
		expect(defaultExperience).not.toBe(null);

		const reviewAndRunExperimentButton = getByText(
			'no-active-tests-were-found-for-the-selected-experience'
		);
		expect(reviewAndRunExperimentButton).not.toBe(null);

		const createTestHelpMessage = getByText('create-test-help-message');
		expect(createTestHelpMessage).not.toBe(null);

		const createTestButton = getByText('create-test');
		expect(createTestButton).not.toBe(null);
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

		const experiment = getByText(segmentsExperiment.name);
		expect(experiment).not.toBe(null);

		const createTestHelpMessage = getByText('review-and-run-test');
		expect(createTestHelpMessage).toHaveAttribute('disabled');
		expect(createTestHelpMessage).not.toBe(null);

		const createTestButton = getByText('edit');
		expect(createTestButton).not.toBe(null);
	});

	it('renders modal to create experiment when the user clicks on create test button', () => {
		const {getByText} = _renderSegmentsExperimentsSidebarComponent({
			initialSegmentsExperiences: segmentsExperiences
		});

		const createTestButton = getByText('create-test');
		expect(createTestButton).not.toBe(null);

		fireEvent.click(createTestButton);

		const createNewTestTitle = getByText('create-new-test');
		expect(createNewTestTitle).not.toBe(null);

		const testNameField = getByText('test-name');
		expect(testNameField).not.toBe(null);

		const descriptionField = getByText('description');
		expect(descriptionField).not.toBe(null);

		const saveButton = getByText('save');
		expect(saveButton).not.toBe(null);

		const cancelButton = getByText('cancel');
		expect(cancelButton).not.toBe(null);
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

	it('create variant button', done => {
		const createVariantMock = jest.fn(
			variant =>
				new Promise(resolve => {
					return resolve({
						segmentsExperimentRel: {
							name: variant.name,
							segmentsExperienceId: JSON.stringify(Math.random()),
							segmentsExperimentId: JSON.stringify(Math.random()),
							segmentsExperimentRelId: JSON.stringify(
								Math.random()
							),
							split: 0.0
						}
					});
				})
		);
		const {
			getByText,
			getByLabelText
		} = _renderSegmentsExperimentsSidebarComponent({
			initialSegmentsExperiences: segmentsExperiences,
			initialSegmentsExperiment: segmentsExperiment,
			initialSegmentsVariants: segmentsVariants,
			segmentsExperimentsUtil: {
				createVariant: createVariantMock
			},
			selectedSegmentsExperienceId:
				segmentsExperiment.segmentsExperimentId
		});

		const button = getByText('create-variant');
		expect(button).not.toBe(null);

		userEvent.click(button);

		waitForElement(() => getByText('create-new-variant')).then(() => {
			const variantNameInput = getByLabelText('name');
			expect(variantNameInput.value).toBe('');

			userEvent.type(variantNameInput, 'Variant Name').then(() => {
				const saveButton = getByText('save');

				userEvent.click(saveButton);

				waitForElementToBeRemoved(() => getByLabelText('name')).then(
					() => {
						wait(() => expect(getByText('Variant Name'))).then(
							() => {
								expect(createVariantMock).toHaveBeenCalledWith(
									expect.objectContaining({
										name: 'Variant Name'
									})
								);

								expect(getByText('Variant Name')).not.toBe(
									null
								);

								done();
							}
						);
					}
				);
			});
		});
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
	it('can view review Experiment Modal', done => {
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

		waitForElement(() => getByText('review-and-run-test')).then(() => {
			const inputs = getAllByDisplayValue('50');
			expect(inputs.length).toBe(3);
			done();
		});
	});
});

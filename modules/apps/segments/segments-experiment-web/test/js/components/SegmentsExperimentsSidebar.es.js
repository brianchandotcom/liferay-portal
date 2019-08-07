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
import {cleanup, fireEvent, render} from '@testing-library/react';
import SegmentsExperimentsSidebar from '../../../src/main/resources/META-INF/resources/js/components/SegmentsExperimentsSidebar.es';
import SegmentsExperimentsContext from '../../../src/main/resources/META-INF/resources/js/context.es';
import React from 'react';
import {
	segmentsExperiment,
	segmentsExperiences,
	segmentsVariants
} from '../fixtures.es';

function _renderSegmentsExperimentsSidebarComponent({
	initialSegmentsExperiences = [],
	initialSegmentsExperiment,
	selectedSegmentsExperienceId,
	namespace = '',
	contentPageEditorNamespace = '',
	createSegmentsExperimentURL = '',
	editSegmentsExperimentURL = '',
	classNameId = '',
	classPK = '',
	type = 'content',
	initialSegmentsVariants = []
} = {}) {
	return render(
		<SegmentsExperimentsContext.Provider
			value={{
				endpoints: {
					createSegmentsExperimentURL,
					editSegmentsExperimentURL
				},
				contentPageEditorNamespace,
				namespace,
				page: {
					classNameId,
					classPK,
					type
				}
			}}
		>
			<SegmentsExperimentsSidebar
				initialSegmentsExperiences={initialSegmentsExperiences}
				initialSegmentsExperiment={initialSegmentsExperiment}
				initialSegmentsVariants={initialSegmentsVariants}
				selectedSegmentsExperienceId={selectedSegmentsExperienceId}
			/>
		</SegmentsExperimentsContext.Provider>
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

	it('renders _No Variants Message_', () => {
		const {getByText} = _renderSegmentsExperimentsSidebarComponent({
			initialSegmentsExperiences: segmentsExperiences,
			initialSegmentsVariants: [segmentsVariants[0]],
			initialSegmentsExperiment: segmentsExperiment,
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
			initialSegmentsVariants: segmentsVariants,
			initialSegmentsExperiment: segmentsExperiment,
			selectedSegmentsExperienceId:
				segmentsExperiment.segmentsExperimentId
		});

		const control = getByText('variant-control');
		const variant = getByText(segmentsVariants[1].name);

		expect(control).not.toBe(null);
		expect(variant).not.toBe(null);
	});
});

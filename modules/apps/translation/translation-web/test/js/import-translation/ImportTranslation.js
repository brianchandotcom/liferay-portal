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
import {cleanup, fireEvent, render, within} from '@testing-library/react';
import React from 'react';

import ImportTranslation from '../../../src/main/resources/META-INF/resources/js/import-translation/ImportTranslation';

const baseProps = {
	errorMessage: '',
	portletNamespace: '_mock_TranslationPortlet_',
	portletResource: 'portletResource',
	publishButtonLabel: 'Publish',
	redirect: 'http://redirect-url',
	saveButtonLabel: 'Save',
	title: '',
	workflowActionPublish: 1,
	workflowActionSaveDraft: 2,
	workflowPending: false,
};

const files = [...new Array(5)].map(
	(_, i) => new File(['(っ◕‿◕)っ'], `translated_content_${i}.xlf`)
);

const renderComponent = (props) => render(<ImportTranslation {...props} />);

describe('ImportTranslation', () => {
	afterEach(cleanup);

	describe('renders (default)', () => {
		it('publish and save buttons are disabled', () => {
			const {getByRole} = renderComponent(baseProps);

			expect(
				getByRole('button', {
					name: baseProps.publishButtonLabel,
				})
			).toBeDisabled();

			expect(
				getByRole('button', {
					name: baseProps.saveButtonLabel,
				})
			).toBeDisabled();
		});

		it('select file button is enabled and remove files button is not rendered', () => {
			const {getByRole, queryByRole} = renderComponent(baseProps);

			expect(
				getByRole('button', {
					name: 'select-files',
				})
			).toBeEnabled();

			expect(
				queryByRole('button', {
					name: 'remove-files',
				})
			).not.toBeInTheDocument();
		});

		describe('fire file selection', () => {
			let renderedComponent;

			beforeEach(() => {
				renderedComponent = renderComponent(baseProps);

				fireEvent.change(renderedComponent.getByTestId('filesInput'), {
					target: {files},
				});
			});

			it('render a list of files', () => {
				const {getByText} = renderedComponent;

				files.forEach(({name: filename}) => {
					expect(getByText(filename)).toBeInTheDocument();
				});
			});

			it('enable the save and publish button', () => {
				const {getByRole} = renderedComponent;

				expect(
					getByRole('button', {
						name: baseProps.publishButtonLabel,
					})
				).toBeEnabled();

				expect(
					getByRole('button', {
						name: baseProps.saveButtonLabel,
					})
				).toBeEnabled();
			});

			it('select files button changes to replace files and remove files button is present', () => {
				const {getByRole, queryByRole} = renderedComponent;

				expect(
					queryByRole('button', {
						name: 'select-files',
					})
				).not.toBeInTheDocument();

				expect(
					getByRole('button', {
						name: 'replace-files',
					})
				).toBeEnabled();

				expect(
					getByRole('button', {
						name: 'remove-files',
					})
				).toBeEnabled();
			});
		});
	});

	describe('renders with workflowPending and fire file selection', () => {
		it('enable the save button but not the publish button', () => {
			const {getByRole, getByTestId} = renderComponent({
				...baseProps,
				workflowPending: true,
			});

			fireEvent.change(getByTestId('filesInput'), {target: {files}});

			expect(
				getByRole('button', {
					name: baseProps.publishButtonLabel,
				})
			).toBeDisabled();

			expect(
				getByRole('button', {
					name: baseProps.saveButtonLabel,
				})
			).toBeEnabled();
		});
	});

	it('a danger alert with the error message is rendered', () => {
		const errorMessage =
			'Please enter a file with a valid xliff file extension (.xliff, .xlf, .zip).';

		const {getByRole} = renderComponent({
			...baseProps,
			errorMessage,
		});

		const alert = getByRole('alert');
		expect(within(alert).getByText(errorMessage)).toBeInTheDocument();
		expect(alert).toHaveClass('alert-danger');
	});
});

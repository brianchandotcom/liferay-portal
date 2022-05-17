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
import {cleanup, render} from '@testing-library/react';
import React from 'react';

import ExportTranslation from '../../src/main/resources/META-INF/resources/js/ExportTranslation';

const baseProps = {
	availableExportFileFormats: [{displayName: '', mimeType: ''}],
	availableSourceLocales: [{displayName: '', languageId: '0'}],
	availableTargetLocales: [{displayName: '', languageId: '0'}],
	defaultSourceLanguageId: 'en-US',
	exportTranslationURL: 'http://export-url',
};
const renderComponent = (props) => render(<ExportTranslation {...props} />);

describe('Export Translation', () => {
	afterEach(cleanup);

	it('renders the experiences selector when multiple pages are selected', () => {
		const {getByText} = renderComponent({
			...baseProps,
			multipleExperiences: true,
			multiplePagesSelected: true,
		});

		expect(getByText('export-experiences')).toBeInTheDocument();
	});

	it('renders a radio button to select all experiences, with default value selected', () => {
		const {getAllByRole} = renderComponent({
			...baseProps,
			multipleExperiences: true,
			multiplePagesSelected: true,
		});

		const radioButton = getAllByRole('radio');

		expect(radioButton[0].parentElement).toHaveTextContent(
			'default-experience'
		);
		expect(radioButton[1].parentElement).toHaveTextContent(
			'all-experience'
		);

		expect(radioButton[0]).toBeChecked();
	});
});

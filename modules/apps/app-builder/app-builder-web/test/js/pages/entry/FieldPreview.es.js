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

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import {AppContextProvider} from '../../../../src/main/resources/META-INF/resources/js/AppContext.es';
import FieldPreview, {
	SectionRenderer,
} from '../../../../src/main/resources/META-INF/resources/js/pages/entry/FieldPreview.es';
import {dataDefinitionWithAllTypes as dataDefinition} from '../../constants.es';

const dataRecords = {
	fieldDate: {
		en_US: '12/23/2020',
	},
	fieldNumber: {
		en_US: '1234',
	},
	fieldRadio: {
		en_US: 'Opo40716029',
	},
	fieldRegister: {
		en_US: `{"fileEntryId":123,"folderId":123,"groupId":123,"title":"registration_file.pdf"}`,
	},
	fieldSchoolGrade: {
		en_US: ['Opo72476108'],
	},
	fieldSchoolMark: {
		en_US: ['Opo17059431'],
	},
	fieldText: {
		en_US: 'Liferay',
	},
};

const FieldPreviewWrapper = ({fieldName}) => (
	<AppContextProvider userLanguageId="en_US">
		<FieldPreview
			dataDefinition={dataDefinition}
			dataRecordValues={dataRecords}
			defaultLanguageId="en_US"
			fieldName={fieldName}
		/>
	</AppContextProvider>
);

const location = window.location;

describe('EditEntry', () => {
	beforeEach(() => {
		jest.useFakeTimers();

		delete window.location;
		window.location = {};

		window.Liferay = {
			...window.Liferay,
			PortletKeys: {
				DOCUMENT_LIBRARY: 'DOC',
			},
			PortletURL: {
				createURL: jest.fn().mockImplementation(() => ({
					setParameter: jest.fn(),
					setPortletId: jest.fn(),
					setPortletMode: jest.fn(),
					setWindowState: jest.fn(),
					toString: jest
						.fn()
						.mockImplementation(
							() => 'http://localhost:8080/preview_url'
						),
				})),
			},
			Util: {
				...window.Liferay.Util,
				openWindow: jest.fn(),
			},
		};

		window.themeDisplay = {
			...window.themeDisplay,
			getLayoutRelativeControlPanelURL: jest.fn(),
			getScopeGroupId: jest.fn(),
		};

		cleanup();
	});

	afterEach(() => {
		cleanup();
		jest.clearAllTimers();
		jest.restoreAllMocks();
	});

	afterAll(() => {
		jest.useRealTimers();

		window.location = location;
	});

	it('renders FieldPreview for Date', async () => {
		const {queryByText} = render(
			<FieldPreviewWrapper fieldName="fieldDate" />
		);

		expect(queryByText('School Date')).toBeTruthy();
		expect(queryByText('12/23/2020')).toBeTruthy();
	});

	it('renders FieldPreview for Text', async () => {
		const {queryByText, rerender} = render(
			<FieldPreviewWrapper fieldName="fieldText" />
		);

		expect(queryByText('School')).toBeTruthy();
		expect(queryByText('Liferay')).toBeTruthy();

		dataDefinition.dataDefinitionFields[1].localizable = false;
		dataRecords.fieldText = 'Liferay School';

		rerender(<FieldPreviewWrapper fieldName="fieldText" />);

		expect(queryByText('Liferay School')).toBeTruthy();
	});

	it('renders FieldPreview for Number', async () => {
		const {queryByText} = render(
			<FieldPreviewWrapper fieldName="fieldNumber" />
		);

		expect(queryByText('School Number')).toBeTruthy();
		expect(queryByText('1234')).toBeTruthy();
	});

	it('renders FieldPreview for CheckBox', async () => {
		const {queryAllByText, queryByText, rerender} = render(
			<FieldPreviewWrapper fieldName="fieldSchoolMark" />
		);

		expect(queryByText('School Mark')).toBeTruthy();
		expect(queryAllByText(/Logo/)).toHaveLength(1);

		dataRecords.fieldSchoolMark.en_US.push('Logo B');

		rerender(<FieldPreviewWrapper fieldName="fieldSchoolMark" />);

		expect(queryAllByText(/Logo/)).toHaveLength(2);

		dataRecords.fieldSchoolMark.en_US.push('Logo C');

		expect(queryAllByText(/Logo/)).toHaveLength(2);
	});

	it('renders FieldPreview for Select', async () => {
		const {container, queryByText, rerender} = render(
			<FieldPreviewWrapper fieldName="fieldSchoolGrade" />
		);

		expect(queryByText('School Grade')).toBeTruthy();
		expect(queryByText('Grade A')).toBeTruthy();

		dataDefinition.dataDefinitionFields[2].repeatable = true;
		dataRecords.fieldSchoolGrade.en_US = [['Opo72476108'], ['Opo58255447']];

		rerender(<FieldPreviewWrapper fieldName="fieldSchoolGrade" />);

		expect(queryByText('Grade A, Grade B')).toBeTruthy();

		dataRecords.fieldSchoolGrade.en_US = ['[Opo72476108]', '[Opo58255447]'];

		rerender(<FieldPreviewWrapper fieldName="fieldSchoolGrade" />);

		expect(queryByText('Grade A, Grade B')).toBeTruthy();

		dataRecords.fieldSchoolGrade.en_US = ['[Opo72476108]', '[Opo58255447]'];
		dataDefinition.dataDefinitionFields[2].repeatable = true;
		dataDefinition.dataDefinitionFields[2].customProperties.multiple = true;

		rerender(<FieldPreviewWrapper fieldName="fieldSchoolGrade" />);

		expect(container.querySelectorAll('li')).toHaveLength(2);
		expect(queryByText('Grade A')).toBeTruthy();
		expect(queryByText('Grade B')).toBeTruthy();
	});

	it('renders FieldPreview for Radio', async () => {
		const {queryByText, rerender} = render(
			<FieldPreviewWrapper fieldName="fieldRadio" />
		);

		expect(queryByText('School Option')).toBeTruthy();
		expect(queryByText('Option A')).toBeTruthy();

		dataDefinition.dataDefinitionFields[3].repeatable = true;
		dataRecords.fieldRadio.en_US = ['Opo40716029', 'Opo72919976'];

		rerender(<FieldPreviewWrapper fieldName="fieldRadio" />);

		expect(queryByText('Option A, Option B')).toBeTruthy();
	});

	it('renders FieldPreview for DocumentLibrary', async () => {
		const {container, queryByText, rerender} = render(
			<FieldPreviewWrapper fieldName="fieldRegister" />
		);

		expect(queryByText('School Register')).toBeTruthy();
		expect(queryByText('registration_file.pdf')).toBeTruthy();
		expect(
			container.querySelector('.lexicon-icon-document-pdf')
		).toBeTruthy();

		dataRecords.fieldRegister.en_US = `{"fileEntryId":123,"folderId":123,"groupId":123,"title":"school_grades.doc"}`;

		rerender(<FieldPreviewWrapper fieldName="fieldRegister" />);

		expect(queryByText('school_grades.doc')).toBeTruthy();
		expect(
			container.querySelector('.lexicon-icon-document-text')
		).toBeTruthy();

		dataRecords.fieldRegister.en_US = `{"fileEntryId":123,"folderId":123,"groupId":123,"title":"school_photo.jpg"}`;

		rerender(<FieldPreviewWrapper fieldName="fieldRegister" />);

		expect(queryByText('school_photo.jpg')).toBeTruthy();
		expect(
			container.querySelector('.lexicon-icon-document-default')
		).toBeTruthy();

		const previewButton = queryByText('school_photo.jpg');

		expect(window.Liferay.Util.openWindow.mock.calls).toHaveLength(0);

		previewButton.click();

		const previewProps = window.Liferay.Util.openWindow.mock.calls[0][0];

		expect(previewProps.title).toBe('file-preview');
		expect(previewProps.uri).toBe('http://localhost:8080/preview_url');
		expect(window.Liferay.Util.openWindow.mock.calls).toHaveLength(1);

		const downloadButton = container.querySelector('[title="download"]');

		expect(window.location.href).toBeUndefined();

		downloadButton.click();

		expect(window.location.href).toBe(
			'//documents/123/123/school_photo.jpg?download=true'
		);
	});

	it('renders FieldPreview for Section', async () => {
		const {queryByText} = render(
			<AppContextProvider userLanguageId="en_US">
				<SectionRenderer
					collapsible
					dataDefinition={dataDefinition}
					fieldName="fieldSetAddress"
				>
					<b>Address CustomField</b>
				</SectionRenderer>
			</AppContextProvider>
		);

		expect(queryByText('FieldSet Address')).toBeTruthy();
		expect(queryByText('Address CustomField')).toBeTruthy();
	});
});

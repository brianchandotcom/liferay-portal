/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen} from '@testing-library/react';
import React from 'react';

import PageWithProvider from '../../../../../src/main/resources/META-INF/resources/js/core/components/PageRenderer/Page.es';
import {ConfigProvider} from '../../../../../src/main/resources/META-INF/resources/js/core/hooks/useConfig.es';

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/js/core/hooks/useForm.es',
	() => ({
		useForm: () => jest.fn(),
		useFormState: () => ({
			defaultLanguageId: 'en_US',
			history: {steps: [{}]},
		}),
	})
);

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/js/core/components/PageRenderer/Layout.es',
	() => ({
		Layout: () => null,
	})
);

const DDM_FORM_PORTLET_NAMESPACE =
	'_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormPortlet_';

const emptyPage = {rows: []};

const pageWithRequiredField = {
	rows: [
		{
			columns: [
				{
					fields: [
						{
							fieldName: 'textField',
							required: true,
							type: 'text',
						},
					],
				},
			],
		},
	],
};

describe('Page indicatesRequiredFieldsLabel', () => {
	it('renders indicatesRequiredFieldsLabel from config when provided', () => {
		const indicatesRequiredFieldsLabel = 'Indica campos obrigatórios';

		render(
			<ConfigProvider initialConfig={{indicatesRequiredFieldsLabel}}>
				<PageWithProvider
					activePage={0}
					editingLanguageId="pt_BR"
					page={emptyPage}
					pageIndex={0}
					pages={[pageWithRequiredField]}
					paginationMode=""
					portletNamespace={DDM_FORM_PORTLET_NAMESPACE}
				/>
			</ConfigProvider>
		);

		expect(
			screen.getByText(indicatesRequiredFieldsLabel)
		).toBeInTheDocument();
	});

	it('falls back to Liferay.Language.get when indicatesRequiredFieldsLabel is not in config', () => {
		render(
			<ConfigProvider initialConfig={{}}>
				<PageWithProvider
					activePage={0}
					editingLanguageId="en_US"
					page={emptyPage}
					pageIndex={0}
					pages={[pageWithRequiredField]}
					paginationMode=""
					portletNamespace={DDM_FORM_PORTLET_NAMESPACE}
				/>
			</ConfigProvider>
		);

		expect(
			screen.getByText('indicates-required-fields')
		).toBeInTheDocument();
	});
});

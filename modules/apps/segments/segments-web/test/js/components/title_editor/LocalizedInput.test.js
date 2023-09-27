/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import LocalizedInput from '../../../../src/main/resources/META-INF/resources/js/components/title_editor/LocalizedInput';

const LOCALIZED_DROPDOWN_BUTTON = 'localized-dropdown-button';

const LOCALIZED_MAIN_INPUT_ID = 'localized-main-input';

const PRE_EXISTING_VALUE = 'Testing default value';

const PRE_EXISTING_VALUE_ALT = 'Testing alternative default value';

describe('LocalizedInput', () => {
	describe('with minimal configuration', () => {
		afterEach(cleanup);

		it('renders', () => {
			const {asFragment} = setUpComponent();
			expect(asFragment()).toMatchSnapshot();
		});

		it('has main input with empty value', () => {
			const {getByTestId} = setUpComponent();
			const mainInput = getByTestId(LOCALIZED_MAIN_INPUT_ID);

			expect(mainInput.value).toBeFalsy();
		});

		function setUpComponent() {
			const {asFragment, getByTestId} = render(
				<LocalizedInput
					availableLanguages={{
						en_US: '',
						es_ES: '',
					}}
					initialLanguageId="en_US"
				/>
			);

			return {asFragment, getByTestId};
		}
	});

	describe('rendering with pre-existing value', () => {
		afterEach(cleanup);

		it('renders', () => {
			const {asFragment} = setUpComponent();
			expect(asFragment()).toMatchSnapshot();
		});

		it('has main input with value', () => {
			const {getByTestId} = setUpComponent();
			const mainInput = getByTestId(LOCALIZED_MAIN_INPUT_ID);
			expect(mainInput.value).toBe(PRE_EXISTING_VALUE);
		});

		it('switches main input value on language selection', () => {
			const {getByTestId, getByText} = setUpComponent();
			const dropdownButton = getByTestId(LOCALIZED_DROPDOWN_BUTTON);
			const mainInput = getByTestId(LOCALIZED_MAIN_INPUT_ID);

			expect(mainInput.value).toBe(PRE_EXISTING_VALUE);

			fireEvent.click(dropdownButton);

			const spanishSelector = getByText('es-ES');
			fireEvent.click(spanishSelector);

			expect(mainInput.value).toBe(PRE_EXISTING_VALUE_ALT);
		});

		function setUpComponent() {
			const testHelpers = render(
				<LocalizedInput
					availableLanguages={{
						ar_SA: 'Arabic (Saudi Arabia)',
						ca_ES: 'Catalan (Spain)',
						de_DE: 'German (Germany)',
						en_US: 'English (United States)',
						es_ES: 'Spanish (Spain)',
						fi_FI: 'Finnish (Finland)',
						fr_FR: 'French (France)',
						hu_HU: 'Hungarian (Hungary)',
						ja_JP: 'Japanese (Japan)',
						nl_NL: 'Dutch (Netherlands)',
						pt_BR: 'Portuguese (Brazil)',
						sv_SE: 'Swedish (Sweden)',
						zh_CN: 'Chinese (China)',
					}}
					defaultLang="en_US"
					initialLanguageId="en_US"
					initialValues={{
						en_US: PRE_EXISTING_VALUE,
						es_ES: PRE_EXISTING_VALUE_ALT,
					}}
				/>
			);

			return testHelpers;
		}
	});
});

import LocalizedInput from 'components/title_editor/LocalizedInput.es';
import React from 'react';
import {cleanup, fireEvent, render} from 'react-testing-library';

const LOCALIZED_DROPDOWN_BUTTON = 'localized-dropdown-button';

const LOCALIZED_MAIN_INPUT_ID = 'localized-main-input';

const PRE_EXISTING_VALUE = 'Testing default value';

const PRE_EXISTING_VALUE_ALT = 'Testing alternative default value';

describe('LocalizedInput', () => {
	describe('with minimal configuration', () => {
		afterEach(cleanup);

		it('should render', () => {
			const {asFragment} = setUpComponent();
			expect(asFragment()).toMatchSnapshot();
		});

		it('should have main input with empty value', () => {
			const {getByTestId} = setUpComponent();
			const mainInput = getByTestId(LOCALIZED_MAIN_INPUT_ID);

			expect(mainInput.value).toBeFalsy();
		});

		function setUpComponent() {
			const {asFragment, getByTestId} = render(
				<LocalizedInput
					availableLanguages={{
						en_US: '',
						es_ES: ''
					}}
					initialLanguageId='en_US'
				/>
			);
			return {asFragment, getByTestId};
		}
	});

	describe('should render with pre-existing value', () => {
		afterEach(cleanup);

		it('should render', () => {
			const {asFragment} = setUpComponent();
			expect(asFragment()).toMatchSnapshot();
		});

		it('should have main input with value', () => {
			const {getByTestId} = setUpComponent();
			const mainInput = getByTestId(LOCALIZED_MAIN_INPUT_ID);
			expect(mainInput.value).toBe(PRE_EXISTING_VALUE);
		});

		it('should have dropdown with options', () => {
			const {asFragment, getByTestId} = setUpComponent();
			const dropdownButton = getByTestId(LOCALIZED_DROPDOWN_BUTTON);
			fireEvent.click(dropdownButton);

			expect(asFragment()).toMatchSnapshot();
		});

		it('should switch main input value on language selection', () => {
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
						en_US: '',
						es_ES: ''
					}}
					initialLanguageId='en_US'
					initialValues={{
						en_US: PRE_EXISTING_VALUE,
						es_ES: PRE_EXISTING_VALUE_ALT
					}}
				/>
			);
			return testHelpers;
		}
	});
});

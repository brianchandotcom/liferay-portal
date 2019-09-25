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

import React from 'react';
import SynonymSetsForm from '../../../src/main/resources/META-INF/resources/js/components/SynonymSetsForm.es';
import {render} from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';

const FORM_NAME = 'testForm';

const INPUT_NAME = 'testInput';

const ORIGINAL_INPUT_NAME = 'testOriginalInput';

describe('SynonymSetsForm', () => {
	it('renders with no existing synonyms', () => {
		const {container} = render(
			<SynonymSetsForm
				formName={FORM_NAME}
				inputName={INPUT_NAME}
				originalInputName={ORIGINAL_INPUT_NAME}
				synonymSets=""
			/>
		);

		expect(container).not.toBeNull();
	});

	it('renders with existing synonyms', () => {
		const {container} = render(
			<SynonymSetsForm
				formName={FORM_NAME}
				inputName={INPUT_NAME}
				originalInputName={ORIGINAL_INPUT_NAME}
				synonymSets="one,two,three"
			/>
		);

		expect(container).not.toBeNull();
	});

	it('has title', () => {
		const {getByText} = render(
			<SynonymSetsForm
				formName={FORM_NAME}
				inputName={INPUT_NAME}
				originalInputName={ORIGINAL_INPUT_NAME}
				synonymSets="one,two,three"
			/>
		);

		expect(getByText('create-synonym-set')).toBeInTheDocument();
	});

	it('has description text', () => {
		const {getByText} = render(
			<SynonymSetsForm
				formName={FORM_NAME}
				inputName={INPUT_NAME}
				originalInputName={ORIGINAL_INPUT_NAME}
				synonymSets="one,two,three"
			/>
		);

		expect(
			getByText(
				'broaden-the-scope-of-search-by-treating-terms-equally-using-synonyms'
			)
		).toBeInTheDocument();
	});

	it('has help text', () => {
		const {getByText} = render(
			<SynonymSetsForm
				formName={FORM_NAME}
				inputName={INPUT_NAME}
				originalInputName={ORIGINAL_INPUT_NAME}
				synonymSets="one,two,three"
			/>
		);

		expect(
			getByText('type-a-comma-or-press-enter-to-input-a-synonym')
		).toBeInTheDocument();
	});

	it('has a form with label and input', () => {
		const {getByLabelText} = render(
			<SynonymSetsForm
				formName={FORM_NAME}
				inputName={INPUT_NAME}
				originalInputName={ORIGINAL_INPUT_NAME}
				synonymSets="one,two,three"
			/>
		);

		expect(getByLabelText('synonyms')).toBeInTheDocument();
	});

	it('enables the publish button when it has synonyms', () => {
		const {getByText} = render(
			<SynonymSetsForm
				formName={FORM_NAME}
				inputName={INPUT_NAME}
				originalInputName={ORIGINAL_INPUT_NAME}
				synonymSets="one,two,three"
			/>
		);

		expect(getByText('publish')).toBeEnabled();
	});

	it('disables the publish button when it has no synonyms', () => {
		const {getByText} = render(
			<SynonymSetsForm
				formName={FORM_NAME}
				inputName={INPUT_NAME}
				originalInputName={ORIGINAL_INPUT_NAME}
				synonymSets=""
			/>
		);

		expect(getByText('publish')).toBeDisabled();
	});

	it('shows listed synonym', () => {
		const {container} = render(
			<SynonymSetsForm
				formName={FORM_NAME}
				inputName={INPUT_NAME}
				originalInputName={ORIGINAL_INPUT_NAME}
				synonymSets="one"
			/>
		);

		const tagsElement = container.querySelectorAll('.label-item-expand');

		expect(tagsElement.length).toBe(1);
		expect(tagsElement[0]).toHaveTextContent('one');
	});

	it('shows listed synonyms', () => {
		const {container} = render(
			<SynonymSetsForm
				formName={FORM_NAME}
				inputName={INPUT_NAME}
				originalInputName={ORIGINAL_INPUT_NAME}
				synonymSets="one,two,three,four,five"
			/>
		);

		const tagsElement = container.querySelectorAll('.label-item-expand');

		expect(tagsElement.length).toBe(5);

		expect(tagsElement[0]).toHaveTextContent('one');
		expect(tagsElement[1]).toHaveTextContent('two');
		expect(tagsElement[2]).toHaveTextContent('three');
		expect(tagsElement[3]).toHaveTextContent('four');
		expect(tagsElement[4]).toHaveTextContent('five');
	});
});

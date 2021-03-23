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
import React, {createContext} from 'react';

import RepeatableField from '../../../../src/main/resources/META-INF/resources/js/components/form-renderer-custom-fields/RepeatableField.es';

const MyContext = createContext();

const RepeatableFieldWrapper = ({children, state}) => (
	<MyContext.Provider value={[state]}>
		<RepeatableField AppContext={MyContext}>{children}</RepeatableField>
	</MyContext.Provider>
);

describe('RepeatableField', () => {
	afterEach(() => {
		cleanup();
	});

	it('renders with fieldset focusedField', () => {
		const state = {
			focusedCustomObjectField: {fieldType: 'fieldset'},
			focusedField: {type: 'fieldset'},
		};

		const {asFragment, queryByText} = render(
			<RepeatableFieldWrapper state={state}>
				{() => <b>Original Component</b>}
			</RepeatableFieldWrapper>
		);

		expect(queryByText('Original Component')).toBeNull();

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders with text focusedField', () => {
		const state = {
			focusedCustomObjectField: {fieldType: 'text'},
			focusedField: {type: 'text'},
		};
		const {asFragment, queryByText} = render(
			<RepeatableFieldWrapper state={state}>
				{() => <b>Original Component</b>}
			</RepeatableFieldWrapper>
		);

		expect(queryByText('Original Component')).toBeTruthy();

		expect(asFragment()).toMatchSnapshot();
	});
});

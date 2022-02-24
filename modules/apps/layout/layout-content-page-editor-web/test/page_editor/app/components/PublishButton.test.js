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
import {fireEvent, render} from '@testing-library/react';
import React from 'react';

import PublishButton from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/PublishButton';
import {StyleErrorsContextProvider} from '../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StyleErrorsContext';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			pending: false,
			portletNamespace: 'portletNamespace',
			publishURL: 'publishURL',
			redirectURL: 'redirectURL',
		},
	})
);

const ERRORS = {
	defaultId: {background: {error: 'I am an error', value: 'error'}},
};

const renderComponent = ({
	handleSubmit = () => {},
	errors,
	canPublish = true,
}) => {
	const ref = React.createRef();

	return render(
		<StyleErrorsContextProvider initialState={errors}>
			<PublishButton
				canPublish={canPublish}
				formRef={ref}
				handleSubmit={handleSubmit}
				label="publish"
			/>
		</StyleErrorsContextProvider>
	);
};

describe('PublishButton', () => {
	it('renders PublishButton component', () => {
		const {getByLabelText} = renderComponent({});

		expect(getByLabelText('publish')).toBeInTheDocument();
	});

	it('calls handleSubmit when the button is clicked', () => {
		const handleSubmit = jest.fn((event) => event.preventDefault());
		const {getByLabelText} = renderComponent({handleSubmit});
		const button = getByLabelText('publish');

		fireEvent.click(button);

		expect(handleSubmit).toHaveBeenCalled();
	});

	it('opens a modal when the button is clicked and there are errors', async () => {
		const {getByLabelText} = renderComponent({errors: ERRORS});
		const button = getByLabelText('publish');

		fireEvent.click(button);

		expect(getByLabelText('style-errors-detected')).toBeInTheDocument();
	});

	it('does not allow to publish if canPublish is false', () => {
		const handleSubmit = jest.fn((event) => event.preventDefault());
		const {getByLabelText} = renderComponent({
			canPublish: false,
			handleSubmit,
		});
		const button = getByLabelText('publish');

		fireEvent.click(button);

		expect(handleSubmit).not.toHaveBeenCalled();
		expect(button).toBeDisabled();
	});
});

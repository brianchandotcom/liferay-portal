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
import {fireEvent, render, screen} from '@testing-library/react';
import React from 'react';

import PublishButton from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/PublishButton';
import {StyleErrorsContextProvider} from '../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StyleErrorsContext';
import openWarningModal from '../../../../src/main/resources/META-INF/resources/page_editor/app/utils/openWarningModal';
import useIsSomeFormIncomplete from '../../../../src/main/resources/META-INF/resources/page_editor/app/utils/useIsSomeFormIncomplete';

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

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/utils/useIsSomeFormIncomplete',
	() => jest.fn()
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/utils/openWarningModal',
	() => jest.fn()
);

const ERRORS = {
	defaultId: {background: {error: 'I am an error', value: 'error'}},
};

const renderComponent = ({
	onPublish = () => {},
	errors,
	canPublish = true,
} = {}) => {
	const ref = React.createRef();

	return render(
		<StyleErrorsContextProvider initialState={errors}>
			<PublishButton
				canPublish={canPublish}
				formRef={ref}
				label="publish"
				onPublish={onPublish}
			/>
		</StyleErrorsContextProvider>
	);
};

describe('PublishButton', () => {
	afterEach(() => {
		useIsSomeFormIncomplete.mockClear();
	});

	it('renders PublishButton component', () => {
		useIsSomeFormIncomplete.mockImplementation(() => () =>
			Promise.resolve(false)
		);

		renderComponent();

		expect(screen.getByLabelText('publish')).toBeInTheDocument();
	});

	it('calls onPublish when the button is clicked', async () => {
		const onPublish = jest.fn(() => {});

		renderComponent({onPublish});

		const button = screen.getByLabelText('publish');

		await fireEvent.click(button);

		expect(onPublish).toHaveBeenCalled();
	});

	it('opens a modal when the button is clicked and there are errors', async () => {
		renderComponent({errors: ERRORS});

		const button = screen.getByLabelText('publish');

		fireEvent.click(button);

		expect(
			screen.getByLabelText('style-errors-detected')
		).toBeInTheDocument();
	});

	it('does not allow to publish if canPublish is false', () => {
		const onPublish = jest.fn(() => {});

		renderComponent({
			canPublish: false,
			onPublish,
		});

		const button = screen.getByLabelText('publish');

		fireEvent.click(button);

		expect(onPublish).not.toHaveBeenCalled();
		expect(button).toBeDisabled();
	});
});

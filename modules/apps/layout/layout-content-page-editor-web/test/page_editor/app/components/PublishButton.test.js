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
import {act, fireEvent, render, screen} from '@testing-library/react';
import React from 'react';

import PublishButton from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/PublishButton';
import useCheckFormsValidity from '../../../../src/main/resources/META-INF/resources/page_editor/app/utils/useCheckFormsValidity';

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
	'../../../../src/main/resources/META-INF/resources/page_editor/app/components/FormValidationModal',
	() => ({FormValidationModal: () => 'Form Validation Modal'})
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/utils/useCheckFormsValidity',
	() => jest.fn()
);

const renderComponent = ({onPublish = () => {}, canPublish = true} = {}) => {
	const ref = React.createRef();

	return render(
		<PublishButton
			canPublish={canPublish}
			formRef={ref}
			label="publish"
			onPublish={onPublish}
		/>
	);
};

describe('PublishButton', () => {
	afterEach(() => {
		useCheckFormsValidity.mockClear();
	});

	it('renders PublishButton component', () => {
		useCheckFormsValidity.mockImplementation(() => () =>
			Promise.resolve(true)
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

	it('does not call onPublish when some form is invalid', async () => {
		useCheckFormsValidity.mockImplementation(() => () =>
			Promise.resolve(false)
		);

		const onPublish = jest.fn(() => {});

		renderComponent({onPublish});

		const button = screen.getByLabelText('publish');

		await act(async () => {
			fireEvent.click(button);
		});

		expect(onPublish).not.toHaveBeenCalled();
	});
});

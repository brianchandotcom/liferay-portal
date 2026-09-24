/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// eslint-disable-next-line @liferay/portal/no-cross-module-deep-import
import {checkAccessibility} from '@liferay/layout-js-components-web/test/__lib__/index';
import {fireEvent, render, screen} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom';

import FieldTimePicker from '../../../../../src/main/resources/META-INF/resources/revamp/js/components/forms/FieldTimePicker';

describe('FieldTimePicker', () => {
	afterEach(() => {
		(Liferay.ThemeDisplay.getBCP47LanguageId as jest.Mock).mockReturnValue(
			'en-US'
		);
	});

	it('shows a stored time on the 12 hour clock of a 12 hour portal locale', () => {
		render(
			<FieldTimePicker
				id="time"
				label="Time of Day"
				name="time"
				value="17:05"
			/>
		);

		expect(screen.getByLabelText('hours')).toHaveValue('05');
		expect(screen.getByLabelText('minutes')).toHaveValue('05');
		expect(screen.getByLabelText('am-pm')).toHaveValue('PM');
	});

	it('shows a stored time on the 24 hour clock of a 24 hour portal locale', () => {
		(Liferay.ThemeDisplay.getBCP47LanguageId as jest.Mock).mockReturnValue(
			'es-ES'
		);

		render(
			<FieldTimePicker
				id="time"
				label="Time of Day"
				name="time"
				value="17:05"
			/>
		);

		expect(screen.getByLabelText('hours')).toHaveValue('17');
		expect(screen.queryByLabelText('am-pm')).not.toBeInTheDocument();
	});

	it('reports a complete time on the 24 hour clock and a partial one as empty', () => {
		const onChange = jest.fn();

		render(
			<FieldTimePicker
				id="time"
				label="Time of Day"
				name="time"
				onChange={onChange}
			/>
		);

		fireEvent.keyDown(screen.getByLabelText('hours'), {key: '5'});

		expect(onChange).toHaveBeenLastCalledWith('');

		fireEvent.keyDown(screen.getByLabelText('minutes'), {key: '3'});
		fireEvent.keyDown(screen.getByLabelText('am-pm'), {key: 'ArrowUp'});

		expect(onChange).toHaveBeenLastCalledWith('17:03');
	});

	it('labels the field and disables every segment with it', () => {
		render(
			<FieldTimePicker
				disabled
				id="time"
				label="Time of Day"
				name="time"
				required
				value="09:45"
			/>
		);

		expect(screen.getByLabelText(/Time of Day/)).toBeDisabled();
		expect(screen.getByLabelText('minutes')).toBeDisabled();
	});

	it('shows an error message', () => {
		render(
			<FieldTimePicker
				errorMessage="This field is required."
				id="time"
				label="Time of Day"
				name="time"
			/>
		);

		expect(screen.getByText('This field is required.')).toBeInTheDocument();
	});

	it('calls onBlur once a segment loses focus', () => {
		const onBlur = jest.fn();

		render(
			<FieldTimePicker
				id="time"
				label="Time of Day"
				name="time"
				onBlur={onBlur}
			/>
		);

		fireEvent.blur(screen.getByLabelText('hours'));

		expect(onBlur).toHaveBeenCalledTimes(1);
	});

	it('has no accessibility violations', async () => {
		const {container} = render(
			<FieldTimePicker
				id="time"
				label="Time of Day"
				name="time"
				value="09:45"
			/>
		);

		await checkAccessibility({context: container});
	});
});

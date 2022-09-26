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
import userEvent from '@testing-library/user-event';
import React from 'react';

import {VIEWPORT_SIZES} from '../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/viewportSizes';
import SpacingBox from '../../../../src/main/resources/META-INF/resources/page_editor/common/components/SpacingBox';
import {StyleBookContextProvider} from '../../../../src/main/resources/META-INF/resources/page_editor/plugins/page-design-options/hooks/useStyleBook';
import StoreMother from '../../../../src/main/resources/META-INF/resources/page_editor/test-utils/StoreMother';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			availableViewportSizes: {
				desktop: {label: 'desktop'},
				landscapeMobile: {label: 'landscapeMobile'},
				portraitMobile: {label: 'portraitMobile'},
				tablet: {label: 'tablet'},
			},
			frontendTokens: {
				spacer0: {
					defaultValue: '3rem',
					label: 'Spacer 0',
					value: '3rem',
				},
				spacer10: {
					defaultValue: '5rem',
					label: 'Spacer 10',
					value: '5rem',
				},
			},
		},
	})
);

const SpacingBoxTest = ({
	itemConfig = {},
	canSetCustomValue = true,
	onChange = () => {},
	value = {},
	getState = () => ({}),
}) => (
	<StoreMother.Component getState={getState}>
		<StyleBookContextProvider>
			<SpacingBox
				canSetCustomValue={canSetCustomValue}
				fields={{
					marginBottom: {
						defaultValue: '0',
						label: 'margin-bottom',
						name: 'marginTop',
						typeOptions: {
							validValues: [
								{label: '0', value: '0'},
								{label: '10', value: '10'},
							],
						},
					},
					marginLeft: {
						defaultValue: '0',
						label: 'margin-left',
						name: 'marginLeft',
						typeOptions: {
							validValues: [
								{label: '0', value: '0'},
								{label: '10', value: '10'},
							],
						},
					},
					marginRight: {
						defaultValue: '0',
						label: 'margin-right',
						name: 'marginRight',
						typeOptions: {
							validValues: [
								{label: '0', value: '0'},
								{label: '10', value: '10'},
							],
						},
					},
					marginTop: {
						defaultValue: '0',
						label: 'margin-top',
						name: 'marginTop',
						typeOptions: {
							validValues: [
								{label: '0', value: '0'},
								{label: '10', value: '10'},
							],
						},
					},
					paddingBottom: {
						defaultValue: '0',
						label: 'padding-bottom',
						name: 'paddingBottom',
						typeOptions: {
							validValues: [
								{label: '0', value: '0'},
								{label: '10', value: '10'},
							],
						},
					},
					paddingLeft: {
						defaultValue: '0',
						label: 'padding-left',
						name: 'paddingLeft',
						typeOptions: {
							validValues: [
								{label: '0', value: '0'},
								{label: '10', value: '10'},
							],
						},
					},
					paddingRight: {
						defaultValue: '0',
						label: 'padding-right',
						name: 'paddingRight',
						typeOptions: {
							validValues: [
								{label: '0', value: '0'},
								{label: '5', value: '5'},
							],
						},
					},
					paddingTop: {
						defaultValue: '0',
						label: 'padding-top',
						name: 'paddingTop',
						typeOptions: {
							validValues: [
								{label: '0', value: '0'},
								{label: '10', value: '10'},
							],
						},
					},
				}}
				item={{config: itemConfig}}
				onChange={onChange}
				value={value}
			/>
		</StyleBookContextProvider>
	</StoreMother.Component>
);

describe('SpacingBox', () => {
	let _getComputedStyle;

	beforeEach(() => {
		_getComputedStyle = window.getComputedStyle;
	});

	afterEach(() => {
		window.getComputedStyle = _getComputedStyle;
	});

	it('renders given spacing values from StyleBook', () => {
		render(<SpacingBoxTest value={{marginTop: '10'}} />);

		expect(screen.getByLabelText('padding-left')).toHaveTextContent('3rem');
		expect(screen.getByLabelText('margin-top')).toHaveTextContent('5rem');
	});

	it('can be navigated with keyboard', () => {
		render(<SpacingBoxTest />);

		const grid = screen.getByRole('grid');

		screen.getByLabelText('margin-top').focus();

		fireEvent.keyDown(grid, {key: 'ArrowDown'});
		fireEvent.keyDown(grid, {key: 'ArrowRight'});
		fireEvent.keyDown(grid, {key: 'ArrowRight'});
		fireEvent.keyDown(grid, {key: 'ArrowDown'});
		fireEvent.keyDown(grid, {key: 'ArrowLeft'});
		fireEvent.keyDown(grid, {key: 'ArrowRight'});

		expect(screen.getByLabelText('padding-left')).toHaveFocus();
	});

	it('can be used to update spacing', () => {
		const onChange = jest.fn();
		render(<SpacingBoxTest onChange={onChange} />);

		fireEvent.click(screen.getByLabelText('padding-left'));
		fireEvent.click(screen.getByLabelText('set-padding-left-to-10'));

		expect(onChange).toHaveBeenCalledWith('paddingLeft', '10');
	});

	it('shows token value next to token name in the dropdown', () => {
		render(<SpacingBoxTest />);

		userEvent.click(screen.getByLabelText('padding-left'));

		expect(screen.getByText('5rem')).toBeInTheDocument();
	});

	it('focuses the selected option when the dropdown is opened', () => {
		render(<SpacingBoxTest value={{marginTop: '10'}} />);

		jest.useFakeTimers();

		fireEvent.click(screen.getByLabelText('margin-top'));

		act(() => {
			jest.runAllTimers();
		});

		jest.useRealTimers();

		expect(screen.getByText('Spacer 10').parentElement).toHaveFocus();
	});

	it('gets the corresponding value if the token value does not exist', () => {
		window.getComputedStyle = () => {
			return {
				getPropertyValue: (key) => {
					return {'padding-right': '111px'}[key];
				},
			};
		};

		render(<SpacingBoxTest />);

		userEvent.click(screen.getByLabelText('padding-right'));

		expect(screen.getByText('111px')).toBeInTheDocument();
	});

	describe('LenghtInput inside SpacingBox', () => {
		it('does not render the input when user does not have update permission', () => {
			render(<SpacingBoxTest canSetCustomValue={false} />);

			userEvent.click(screen.getByLabelText('padding-left'));

			expect(screen.queryByTitle('select-units')).not.toBeInTheDocument();
		});

		it('calls onChange when setting a custom value', () => {
			const onChange = jest.fn();
			render(<SpacingBoxTest onChange={onChange} />);

			const button = screen.getByLabelText('margin-top');

			userEvent.click(button);

			const input = screen.getByLabelText('margin-top', {
				selector: 'input',
			});

			userEvent.type(input, '12');
			fireEvent.blur(input);

			expect(onChange).toHaveBeenCalledWith('marginTop', '12px');
		});

		it('calls onChange and closes the dropdown when the Enter button is pressed', () => {
			const onChange = jest.fn();
			render(<SpacingBoxTest onChange={onChange} />);

			const button = screen.getByLabelText('padding-top');

			userEvent.click(button);

			const input = screen.getByLabelText('padding-top', {
				selector: 'input',
			});

			userEvent.type(input, '20');
			fireEvent.keyUp(input, {key: 'Enter'});

			expect(onChange).toHaveBeenCalledWith('paddingTop', '20px');
			expect(screen.queryByText('10rem')).not.toBeInTheDocument();
		});
	});

	describe('Reset button inside SpacingBox', () => {
		it('reset value when pressing the button', () => {
			const onChange = jest.fn();

			render(
				<SpacingBoxTest onChange={onChange} value={{marginTop: '10'}} />
			);

			const button = screen.getByLabelText('margin-top');

			userEvent.click(button);

			userEvent.click(screen.getByTitle('reset-to-initial-value'));

			expect(onChange).toHaveBeenCalledWith(
				'marginTop',
				null,
				expect.anything()
			);
		});

		it('renders correct label if we are in different viewport', () => {
			const onChange = jest.fn();

			render(
				<SpacingBoxTest
					getState={() => ({
						selectedViewportSize: VIEWPORT_SIZES.tablet,
					})}
					itemConfig={{marginTop: '2px'}}
					onChange={onChange}
					value={{marginTop: '10'}}
				/>
			);

			userEvent.click(screen.getByLabelText('margin-top'));

			expect(
				screen.getByTitle('reset-to-desktop-value')
			).toBeInTheDocument();
		});
	});
});

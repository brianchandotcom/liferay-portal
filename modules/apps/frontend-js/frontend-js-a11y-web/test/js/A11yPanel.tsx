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
import {cleanup, fireEvent, getByText, render} from '@testing-library/react';
import userEvents from '@testing-library/user-event';
import React, {useState} from 'react';

import Occurrence from '../../src/main/resources/META-INF/resources/components/Occurrence';
import {StackNavigator} from '../../src/main/resources/META-INF/resources/components/StackNavigator';
import Violation from '../../src/main/resources/META-INF/resources/components/Violation';
import Violations from '../../src/main/resources/META-INF/resources/components/Violations';
import {useFilterViolations} from '../../src/main/resources/META-INF/resources/hooks/useFilterViolations';
import violationsMock from './__fixtures__/violationsMock';

const Panel = () => {
	const [activePage, setActivePage] = useState(0);
	const [params, setParams] = useState();
	const [state, dispatch] = useFilterViolations(violationsMock);

	return (
		<div className="a11y-panel__sidebar sidebar sidebar-light">
			<StackNavigator
				activePage={activePage}
				onActiveChange={setActivePage}
				onParamsChange={setParams}
				params={params}
			>
				<Violations
					onFilterChange={(type, payload) =>
						dispatch({payload, type})
					}
					{...state}
				/>

				<Violation violations={state.violations} />

				<Occurrence violations={state.violations} />
			</StackNavigator>
		</div>
	);
};

const renderA11yToolSidebar = () => {
	return render(<Panel />);
};

describe('A11yPanel', () => {
	afterEach(() => {
		jest.restoreAllMocks();
		cleanup();
	});

	describe('Violations', () => {
		it('renders', () => {
			const {container} = renderA11yToolSidebar();

			expect(container).toBeDefined();
		});

		it('violations needs to be ordered by criticality in ascending order', () => {
			const {getAllByRole} = renderA11yToolSidebar();

			const prioritiesFixture = [
				'critical',
				'serious',
				'moderate',
				'minor',
			];

			const tabs = getAllByRole('tab');

			tabs.forEach((tab, index) =>
				expect(getByText(tab, prioritiesFixture[index])).toBeDefined()
			);
		});

		it('in violations list, a violation needs render a label with the respective number of occurrences', () => {
			const {getAllByRole} = renderA11yToolSidebar();

			const occurrencesCountFixture = [3, 2, 2, 3];

			const tabs = getAllByRole('tab');

			tabs.forEach((tab, index) =>
				expect(
					getByText(tab, String(occurrencesCountFixture[index]))
				).toBeInTheDocument()
			);
		});

		it('clicking in a tag will navigate to the desired violation description', () => {
			const {
				getAllByRole,
				getByText,
				queryByText,
			} = renderA11yToolSidebar();

			const [firstViolationTab] = getAllByRole('tab');

			// Navigates to "aria-required-parent-crit" violation

			userEvents.click(firstViolationTab);

			expect(getByText('aria-required-parent-crit')).toBeInTheDocument();

			expect(
				queryByText('aria-required-parent-min')
			).not.toBeInTheDocument();

			expect(
				getByText(
					'Ensures elements with an ARIA role that require parent roles are contained by them'
				)
			).toBeInTheDocument();
		});

		describe('Violations Filter', () => {
			describe('by Impact', () => {
				it('when selecting CRITICAL impact it shows only CRITICAL violations', () => {
					const {
						getAllByRole,
						getByLabelText,
						getByTestId,
					} = renderA11yToolSidebar();

					userEvents.click(getByLabelText('open-violations-filter'));

					// userEvents doesn't works properly with checkboxes at @testing-library/user-event@4.2.4

					fireEvent.click(getByTestId('critical'));

					expect(getByTestId('critical')).toBeChecked();
					expect(getByTestId('serious')).not.toBeChecked();
					expect(getByTestId('moderate')).not.toBeChecked();
					expect(getByTestId('minor')).not.toBeChecked();

					expect(getAllByRole('tab').length).toBe(1);
				});

				it('when selecting CRITICAL, SERIOUS impacts it shows only corresponding violations', () => {
					const {
						getAllByText,
						getByLabelText,
						getByTestId,
					} = renderA11yToolSidebar();

					userEvents.click(getByLabelText('open-violations-filter'));

					// userEvents doesn't works properly with checkboxes at @testing-library/user-event@4.2.4

					fireEvent.click(getByTestId('critical'));
					fireEvent.click(getByTestId('serious'));

					expect(getByTestId('critical')).toBeChecked();
					expect(getByTestId('serious')).toBeChecked();
					expect(getByTestId('moderate')).not.toBeChecked();
					expect(getByTestId('minor')).not.toBeChecked();

					expect(
						getAllByText(/aria-required-parent-crit/i).length
					).toBe(1);
					expect(
						getAllByText(/aria-required-parent-ser/i).length
					).toBe(1);
				});

				it('when selecting CRITICAL, SERIOUS, MODERATE impacts it shows only corresponding violations', () => {
					const {
						getAllByText,
						getByLabelText,
						getByTestId,
					} = renderA11yToolSidebar();

					userEvents.click(getByLabelText('open-violations-filter'));

					// userEvents doesn't works properly with checkboxes at @testing-library/user-event@4.2.4

					fireEvent.click(getByTestId('critical'));
					fireEvent.click(getByTestId('serious'));
					fireEvent.click(getByTestId('moderate'));

					expect(getByTestId('critical')).toBeChecked();
					expect(getByTestId('serious')).toBeChecked();
					expect(getByTestId('moderate')).toBeChecked();
					expect(getByTestId('minor')).not.toBeChecked();

					expect(
						getAllByText(/aria-required-parent-crit/i).length
					).toBe(1);
					expect(
						getAllByText(/aria-required-parent-ser/i).length
					).toBe(1);
					expect(
						getAllByText(/aria-required-parent-mod/i).length
					).toBe(1);
				});

				it('when selecting CRITICAL, SERIOUS, MODERATE, MINOR impacts it shows only corresponding violations', () => {
					const {
						getAllByText,
						getByLabelText,
						getByTestId,
					} = renderA11yToolSidebar();

					userEvents.click(getByLabelText('open-violations-filter'));

					// userEvents doesn't works properly with checkboxes at @testing-library/user-event@4.2.4

					fireEvent.click(getByTestId('critical'));
					fireEvent.click(getByTestId('serious'));
					fireEvent.click(getByTestId('moderate'));
					fireEvent.click(getByTestId('minor'));

					expect(getByTestId('critical')).toBeChecked();
					expect(getByTestId('serious')).toBeChecked();
					expect(getByTestId('moderate')).toBeChecked();
					expect(getByTestId('minor')).toBeChecked();

					expect(
						getAllByText(/aria-required-parent-crit/i).length
					).toBe(1);
					expect(
						getAllByText(/aria-required-parent-ser/i).length
					).toBe(1);
					expect(
						getAllByText(/aria-required-parent-mod/i).length
					).toBe(1);
					expect(
						getAllByText(/aria-required-parent-min/i).length
					).toBe(1);
				});
			});

			describe('by Category', () => {
				it('when clicking in a not match category, it will show the violations empty state', () => {
					const {
						getByLabelText,
						getByTestId,
						getByText,
					} = renderA11yToolSidebar();

					userEvents.click(getByLabelText('open-violations-filter'));

					// userEvents doesn't works properly with checkboxes at @testing-library/user-event@4.2.4

					fireEvent.click(getByTestId('best-practice'));

					expect(
						getByText(
							'there-are-no-accessibility-violations-in-this-page'
						)
					).toBeInTheDocument();
				});

				it('when clicking in a valid category, it will show the violations labelled with this category', () => {
					const {
						getAllByRole,
						getByLabelText,
						getByTestId,
						getByText,
					} = renderA11yToolSidebar();

					userEvents.click(getByLabelText('open-violations-filter'));

					// userEvents doesn't works properly with checkboxes at @testing-library/user-event@4.2.4

					fireEvent.click(getByTestId('wcag2aa'));

					expect(getAllByRole('tab').length).toBe(1);
					expect(
						getByText('aria-required-parent-mod')
					).toBeInTheDocument();
				});
			});
		});
	});

	describe('Violation', () => {
		it('list all occurrences for the given violation', () => {
			const {getAllByRole, getAllByText} = renderA11yToolSidebar();

			const [firstViolation] = getAllByRole('tab');

			// Navigate to the first violation

			userEvents.click(firstViolation);

			const occurrences = getAllByRole('tab');

			expect(occurrences.length).toBe(3);

			expect(getAllByText(/occurrence-[1-3]{1}$/).length).toBe(3);
		});

		it('navigates to the desired occurrence when clicking', () => {
			const {
				getAllByRole,
				getAllByText,
				getByText,
			} = renderA11yToolSidebar();

			const [firstViolation] = getAllByRole('tab');

			// Navigate to the first violation

			userEvents.click(firstViolation);

			const [firstOccurrence] = getAllByRole('tab');

			userEvents.click(firstOccurrence);

			expect(
				getByText(
					'open-developer-tools-in-the-browser-to-see-the-selected-occurrence'
				)
			).toBeInTheDocument();

			expect(getAllByText('occurrence-1')[0]).toBeInTheDocument();

			expect(getAllByText('occurrence-1')[2]).toBeUndefined();
		});
	});

	describe('Occurrence', () => {
		it('needs to provide a html selector and a code snippet for the desired occurrence', () => {
			const {getAllByRole, getByLabelText} = renderA11yToolSidebar();

			const [firstViolation] = getAllByRole('tab');

			// Navigate to the first violation

			userEvents.click(firstViolation);

			const [firstOccurrence] = getAllByRole('tab');

			// Navigate to the first occurrence

			userEvents.click(firstOccurrence);

			expect(getByLabelText('target-selector')).toBeInTheDocument();
			expect(getByLabelText('code-of-the-element')).toBeInTheDocument();
		});
	});

	describe('General Navigation', () => {
		const mockWarnings = jest
			.spyOn(global.console, 'error')
			.mockImplementation(() => null);

		it(`Violations -> Violation -> Go Back() navigates to Violations and doesn't throw anything on console`, () => {
			const {
				getAllByRole,
				getByLabelText,
				getByRole,
				getByText,
			} = renderA11yToolSidebar();

			const [firstViolation] = getAllByRole('tab');

			// Navigate to the first violation

			userEvents.click(firstViolation);

			const backElement = getByRole('button');

			userEvents.click(backElement);

			expect(mockWarnings).not.toBeCalled();

			expect(
				getByText(
					'check-the-list-of-violated-rules-highlighted-on-the-page'
				)
			).toBeInTheDocument();
			expect(
				getByLabelText('open-violations-filter')
			).toBeInTheDocument();
		});

		it(`Violations -> Violation -> Occurrence -> Go Back() navigates to Violation and doesn't throw anything on console`, () => {
			const {
				getAllByRole,
				getByRole,
				getByText,
				queryByText,
			} = renderA11yToolSidebar();

			const [firstViolation] = getAllByRole('tab');

			// Navigate to the first violation

			userEvents.click(firstViolation);

			const [firstOccurrence] = getAllByRole('tab');

			// Navigate to the first occurrence

			userEvents.click(firstOccurrence);

			const backElement = getByRole('button');

			// Back to the first violation panel

			userEvents.click(backElement);

			expect(mockWarnings).not.toBeCalled();

			expect(getByText('aria-required-parent-crit')).toBeInTheDocument();

			expect(getByText('details')).toBeInTheDocument();

			expect(
				queryByText('aria-required-parent-min')
			).not.toBeInTheDocument();
		});
	});
});

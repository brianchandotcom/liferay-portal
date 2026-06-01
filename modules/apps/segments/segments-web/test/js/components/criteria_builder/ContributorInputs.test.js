/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render, screen} from '@testing-library/react';

import '@testing-library/jest-dom';
import React from 'react';

import ContributorInputs from '../../../../src/main/resources/META-INF/resources/js/components/criteria_builder/ContributorInputs.es';

const userContributor = {
	conjunctionId: 'and',
	conjunctionInputId: 'user-conjunction',
	inputId: 'user-input',
	query: "(firstName eq 'Test')",
};

const organizationContributor = {
	conjunctionId: 'and',
	conjunctionInputId: 'organization-conjunction',
	inputId: 'organization-input',
	query: "(treePath like '%Organization Name%')",
};

describe('ContributorInputs', () => {
	afterEach(cleanup);

	it('renders nothing when contributors is empty', () => {
		const {container} = render(<ContributorInputs contributors={[]} />);

		expect(container).toBeEmptyDOMElement();
	});

	it('omits contributors with an empty query so deleted criteria are not submitted', () => {
		const deletedUserContributor = {
			...userContributor,
			query: '',
		};

		render(
			<ContributorInputs
				contributors={[deletedUserContributor, organizationContributor]}
			/>
		);

		expect(screen.queryByTestId(userContributor.inputId)).toBeNull();

		const organizationInput = screen.getByTestId(
			organizationContributor.inputId
		);

		expect(organizationInput).toHaveValue(organizationContributor.query);
	});

	it('submits the criteria as JSON instead of the OData query for audiences', () => {
		const audienceContributor = {
			...userContributor,
			criteriaMap: {
				conjunctionName: 'and',
				groupId: 'group_0',
				items: [
					{
						operatorName: 'eq',
						propertyName: 'firstName',
						value: 'Test',
					},
				],
			},
		};

		render(
			<ContributorInputs audiences contributors={[audienceContributor]} />
		);

		expect(screen.getByTestId(audienceContributor.inputId)).toHaveValue(
			JSON.stringify(audienceContributor.criteriaMap)
		);
	});

	it('renders one query and conjunction input for each contributor with a query', () => {
		const {container} = render(
			<ContributorInputs
				contributors={[userContributor, organizationContributor]}
			/>
		);

		expect(screen.getByTestId(userContributor.inputId)).toHaveValue(
			userContributor.query
		);
		expect(screen.getByTestId(organizationContributor.inputId)).toHaveValue(
			organizationContributor.query
		);

		expect(
			container.querySelector(`#${userContributor.conjunctionInputId}`)
		).toHaveValue(userContributor.conjunctionId);
		expect(
			container.querySelector(
				`#${organizationContributor.conjunctionInputId}`
			)
		).toHaveValue(organizationContributor.conjunctionId);
	});
});

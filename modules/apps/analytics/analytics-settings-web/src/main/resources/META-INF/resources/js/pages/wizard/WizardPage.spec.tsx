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
import {act, render, screen} from '@testing-library/react';
import fireEvent from '@testing-library/user-event';
import React from 'react';

import DefaultPage from '../default/DefaultPage';
import AttributesStep from './AttributesStep';
import PeopleStep from './PeopleStep';
import PropertyStep from './PropertyStep';
import WizardPage from './WizardPage';

describe('Wizard Page', () => {
	afterEach(() => {
		jest.restoreAllMocks();
	});

	const responseTokenConnect = {
		actions: {},
		facets: [],
		items: [],
		lastPage: 1,
		page: 1,
		pageSize: 20,
		totalCount: 4,
	};

	const responsePeopleMock = {
		syncAllAccounts: false,
		syncAllContacts: false,
		syncedAccountGroupIds: [],
		syncedOrganizationIds: [],
		syncedUserGroupIds: [],
	};

	const responseAttributesMock = {
		account: 25,
		order: 0,
		people: 43,
		product: 0,
	};

	it('render WizardPage without crashing', () => {
		const {container} = render(<WizardPage />);

		const wizardSheet = container.getElementsByClassName('sheet-lg');

		const wizardMultiStep = container.getElementsByClassName(
			'multi-step-nav'
		);

		expect(wizardSheet).toBeTruthy();

		expect(wizardMultiStep).toBeTruthy();
	});

	it('render titles and buttons of multiSteps without crashing', () => {
		const {container, getByRole} = render(<WizardPage />);

		const titleMultiSteps = container.querySelectorAll(
			'.multi-step-indicator-label'
		);

		const buttonConnect = getByRole('button', {name: /1/i});

		const buttonProperty = getByRole('button', {name: /2/i});

		const buttonPeople = getByRole('button', {name: /3/i});

		const buttonAttributes = getByRole('button', {name: /4/i});

		expect(titleMultiSteps[0]).toHaveTextContent('connect');

		expect(titleMultiSteps[1]).toHaveTextContent('property');

		expect(titleMultiSteps[2]).toHaveTextContent('people');

		expect(titleMultiSteps[3]).toHaveTextContent('attributes');

		expect(buttonConnect).toBeInTheDocument();

		expect(buttonProperty).toBeInTheDocument();

		expect(buttonPeople).toBeInTheDocument();

		expect(buttonAttributes).toBeInTheDocument();
	});

	it('not possible go to others steps without connect token', () => {
		const {getByRole} = render(<WizardPage />);

		const buttonConnect = getByRole('button', {name: /1/i});

		const buttonProperty = getByRole('button', {name: /2/i});

		const buttonPeople = getByRole('button', {name: /3/i});

		const buttonAttributes = getByRole('button', {name: /4/i});

		expect(buttonConnect).not.toHaveClass('active');

		expect(buttonProperty).not.toHaveClass('active');

		expect(buttonPeople).not.toHaveClass('active');

		expect(buttonAttributes).not.toHaveClass('active');
	});

	it('render property step with token connection', async () => {
		await act(async () => {
			const {getByRole, getByText} = render(<WizardPage />);

			fetch.mockResponse(JSON.stringify(responseTokenConnect));

			fireEvent.click(getByRole('button', {name: /2/i}));

			render(
				<PropertyStep onCancel={() => {}} onChangeStep={() => {}} />
			);

			const propertyStepDescription = getByText('property-description');

			const propertyStepTitle = getByText('property-assignment');

			expect(propertyStepDescription).toBeInTheDocument();

			expect(propertyStepTitle).toBeInTheDocument();
		});
	});

	it('after the property step, render people step with token connection', async () => {
		await act(async () => {
			fetch.mockResponseOnce(JSON.stringify(responseTokenConnect));

			render(
				<PropertyStep onCancel={() => {}} onChangeStep={() => {}} />
			);

			fireEvent.click(screen.getByRole('button', {name: /next/i}));

			fetch.mockResponseOnce(JSON.stringify(responsePeopleMock));

			const {getByText} = render(
				<PeopleStep onCancel={() => {}} onChangeStep={() => {}} />
			);

			const peopleStepTitle = getByText('sync-people');

			expect(peopleStepTitle).toBeInTheDocument();
		});
	});

	it('after the people step, render attributes step with token connection', async () => {
		await act(async () => {
			fetch.mockResponseOnce(JSON.stringify(responseTokenConnect));

			const {container, getByText} = render(
				<PropertyStep onCancel={() => {}} onChangeStep={() => {}} />
			);

			const buttonsNext = container.querySelectorAll('.btn');

			fireEvent.click(buttonsNext[0]);

			fetch.mockResponseOnce(JSON.stringify(responsePeopleMock));

			render(<PeopleStep onCancel={() => {}} onChangeStep={() => {}} />);

			fireEvent.click(buttonsNext[0]);

			fetch.mockResponseOnce(JSON.stringify(responseAttributesMock));

			render(
				<AttributesStep onCancel={() => {}} onChangeStep={() => {}} />
			);

			const attributesStepTitle = getByText('attributes');

			expect(attributesStepTitle).toBeInTheDocument();
		});
	});

	// TO DO: this test is failing fix later
	xit('click on finish button in the attributes step', async () => {
		await act(async () => {
			fetch.mockResponseOnce(JSON.stringify(responseTokenConnect));

			const {container} = render(
				<PropertyStep onCancel={() => {}} onChangeStep={() => {}} />
			);

			const buttonsNext = container.querySelectorAll('.btn');

			fireEvent.click(buttonsNext[0]);

			fetch.mockResponseOnce(JSON.stringify(responsePeopleMock));

			render(<PeopleStep onCancel={() => {}} onChangeStep={() => {}} />);

			fireEvent.click(buttonsNext[0]);

			fetch.mockResponseOnce(JSON.stringify(responseAttributesMock));

			render(
				<AttributesStep onCancel={() => {}} onChangeStep={() => {}} />
			);

			fireEvent.click(screen.getByRole('button', {name: /finish/i}));

			render(<DefaultPage />);
		});
	});
});

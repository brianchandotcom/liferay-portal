/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render, screen} from '@testing-library/react';
import {MemoryRouter} from 'react-router-dom';
import {AppPropertiesContext} from '../../../../../common/contexts/AppPropertiesContext';
import RequiredInformation from './RequiredInformation';

const featureFlags = ['LPS-180001'];

const infoSelectedKey = {
	doesNotAllowPermanentLicense: false,
	hasNotPermanentLicence: false,
	index: 3,
	licenseEntryType: '',
	productType: '',
	productVersion: '',
	selectedSubscription: {
		endDate: '',
		index: 3,
		instanceSize: 1,
		licenseKeyEndDate: '',
		perpetual: false,
		productKey: '',
		productPurchaseKey: '',
		provisionedCount: 0,
		quantity: 2,
		startDate: '',
	},
};

describe('when user selects the enterprise license type to generate a new key', () => {
	beforeEach(() => {
		render(
			<MemoryRouter>
				<AppPropertiesContext.Provider value={{featureFlags}}>
					<RequiredInformation
						infoSelectedKey={{
							...infoSelectedKey,
							hasNotPermanentLicence: true,
							licenseEntryType: 'Enterprise',
						}}
					/>
				</AppPropertiesContext.Provider>
			</MemoryRouter>
		);
	});

	afterEach(() => {
		cleanup();
	});

	it('has all the fields for this type of license', () => {
		const environmentNameField = screen.queryByText('Environment Name');
		const descriptionField = screen.queryByText('Description');
		const hostNameField = screen.queryByText('Host Name');
		const clusterNodesSelectionField = screen.queryByText('Cluster Nodes');
		const IPAdressField = screen.queryByText('IP Address');
		const MACAdressField = screen.queryByText('MAC Address');

		expect(environmentNameField).toBeInTheDocument();
		expect(descriptionField).toBeInTheDocument();
		expect(clusterNodesSelectionField).not.toBeInTheDocument();
		expect(hostNameField).not.toBeInTheDocument();
		expect(IPAdressField).not.toBeInTheDocument();
		expect(MACAdressField).not.toBeInTheDocument();
	});

	it('generate key button is disabled if required fields are not filled in', () => {
		const buttonGenerateKey = screen.getByRole('button', {
			name: 'Generate Cluster ( Keys)',
		});
		expect(buttonGenerateKey).toBeDisabled();
	});

	it('generate key button is enabled if required fields are filled', () => {
		const buttonGenerateKey = screen.getByRole('button', {
			name: 'Generate Cluster ( Keys)',
		});

		const inputEnvironmentName = screen.getByLabelText('Environment Name');

		fireEvent.change(inputEnvironmentName, {
			target: {value: 'EnvironmentName For Test'},
		});

		expect(buttonGenerateKey).toBeEnabled();
	});
});

describe('when user selects the oem license type to generate a new key', () => {
	beforeEach(() => {
		render(
			<MemoryRouter>
				<AppPropertiesContext.Provider value={{featureFlags}}>
					<RequiredInformation
						infoSelectedKey={{
							...infoSelectedKey,
							hasNotPermanentLicence: true,
							licenseEntryType: 'OEM',
						}}
					/>
				</AppPropertiesContext.Provider>
			</MemoryRouter>
		);
	});

	afterEach(() => {
		cleanup();
	});

	it('has all the fields for this type of license', () => {
		const environmentNameField = screen.queryByText('Environment Name');
		const descriptionField = screen.queryByText('Description');
		const hostNameField = screen.queryByText('Host Name');
		const clusterNodesSelectionField = screen.queryByText('Cluster Nodes');
		const IPAdressField = screen.queryByText('IP Address');
		const MACAdressField = screen.queryByText('MAC Address');

		expect(environmentNameField).toBeInTheDocument();
		expect(descriptionField).toBeInTheDocument();
		expect(clusterNodesSelectionField).not.toBeInTheDocument();
		expect(hostNameField).not.toBeInTheDocument();
		expect(IPAdressField).not.toBeInTheDocument();
		expect(MACAdressField).not.toBeInTheDocument();
	});

	it('generate key button is disabled if required fields are not filled in', () => {
		const buttonGenerateKey = screen.getByRole('button', {
			name: 'Generate Cluster ( Keys)',
		});
		expect(buttonGenerateKey).toBeDisabled();
	});

	it('generate key button is enabled if required fields are filled', () => {
		const buttonGenerateKey = screen.getByRole('button', {
			name: 'Generate Cluster ( Keys)',
		});

		const inputEnvironmentName = screen.getByLabelText('Environment Name');

		fireEvent.change(inputEnvironmentName, {
			target: {value: 'EnvironmentName For Test'},
		});

		expect(buttonGenerateKey).toBeEnabled();
	});
});

describe('when user selects the production license type to generate a new key', () => {
	beforeEach(() => {
		render(
			<MemoryRouter>
				<AppPropertiesContext.Provider value={{featureFlags}}>
					<RequiredInformation
						infoSelectedKey={{
							...infoSelectedKey,
							hasNotPermanentLicence: false,
						}}
					/>
				</AppPropertiesContext.Provider>
			</MemoryRouter>
		);
	});

	afterEach(() => {
		cleanup();
	});

	it('has all the fields for this type of license', () => {
		const environmentNameField = screen.queryByText('Environment Name');
		const descriptionField = screen.queryByText('Description');
		const clusterNodesSelectionField = screen.queryByText('Cluster Nodes');
		const hostNameField = screen.queryByText('Host Name');
		const IPAdressField = screen.queryByText('IP Address');
		const MACAdressField = screen.queryByText('MAC Address');

		expect(environmentNameField).toBeInTheDocument();
		expect(descriptionField).toBeInTheDocument();
		expect(clusterNodesSelectionField).not.toBeInTheDocument();
		expect(hostNameField).toBeInTheDocument();
		expect(IPAdressField).toBeInTheDocument();
		expect(MACAdressField).toBeInTheDocument();
	});

	it('generate key button is disabled if required fields are not filled in', () => {
		const buttonGenerateKey = screen.getByRole('button', {
			name: 'Generate 1 Key',
		});

		expect(buttonGenerateKey).toBeDisabled();
	});

	it('generate key button is enabled if required fields are filled', () => {
		const buttonGenerateKey = screen.getByRole('button', {
			name: 'Generate 1 Key',
		});

		const inputEnvironmentName = screen.getByLabelText('Environment Name');
		const inputHostName = screen.getByLabelText('Host Name');

		fireEvent.change(inputEnvironmentName, {
			target: {value: 'EnvironmentName For Test'},
		});

		fireEvent.change(inputHostName, {target: {value: 'HostName For Test'}});

		expect(buttonGenerateKey).toBeEnabled();
	});
});

describe('when user selects the virtual cluster license type to generate a new key', () => {
	beforeEach(() => {
		render(
			<MemoryRouter>
				<AppPropertiesContext.Provider value={{featureFlags}}>
					<RequiredInformation
						infoSelectedKey={{
							...infoSelectedKey,
							hasNotPermanentLicence: true,
							licenseEntryType: 'Backup (Virtual Cluster)',
						}}
					/>
				</AppPropertiesContext.Provider>
			</MemoryRouter>
		);
	});

	afterEach(() => {
		cleanup();
	});

	it('has all the fields for this type of license', () => {
		const environmentNameField = screen.queryByText('Environment Name');
		const descriptionField = screen.queryByText('Description');
		const clusterNodesSelectionField = screen.queryByText('Cluster Nodes');
		const hostNameField = screen.queryByText('Host Name');
		const IPAdressField = screen.queryByText('IP Address');
		const MACAdressField = screen.queryByText('MAC Address');

		expect(environmentNameField).toBeInTheDocument();
		expect(descriptionField).toBeInTheDocument();
		expect(clusterNodesSelectionField).toBeInTheDocument();
		expect(hostNameField).not.toBeInTheDocument();
		expect(IPAdressField).not.toBeInTheDocument();
		expect(MACAdressField).not.toBeInTheDocument();
	});

	it('generate key button is disabled if required fields are not filled in', () => {
		const buttonGenerateKey = screen.getByRole('button', {
			name: 'Generate Cluster ( Keys)',
		});
		expect(buttonGenerateKey).toBeDisabled();
	});

	it('generate key button is enabled if required fields are filled', () => {
		const buttonGenerateKey = screen.getByRole('button', {
			name: 'Generate Cluster ( Keys)',
		});

		const inputEnvironmentName = screen.getByLabelText('Environment Name');

		fireEvent.change(inputEnvironmentName, {
			target: {value: 'EnvironmentName For Test'},
		});

		const clusterNodesSelectionField = screen.getByLabelText(
			'Cluster Nodes'
		);

		fireEvent.change(clusterNodesSelectionField, {target: {value: '1'}});

		expect(buttonGenerateKey).toBeEnabled();
	});
});

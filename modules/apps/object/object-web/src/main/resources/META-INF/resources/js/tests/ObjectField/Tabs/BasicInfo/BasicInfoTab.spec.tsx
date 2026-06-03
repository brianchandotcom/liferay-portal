/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import React from 'react';

import {BasicInfoTab} from '../../../../components/ObjectField/Tabs/BasicInfo/BasicInfoTab';

jest.mock(
	'../../../../components/ObjectField/Tabs/BasicInfo/BasicInfoContainer',
	() => ({BasicInfoContainer: () => null})
);
jest.mock(
	'../../../../components/ObjectField/Tabs/BasicInfo/SearchableContainer',
	() => ({SearchableContainer: () => null})
);
jest.mock(
	'../../../../components/ObjectField/Tabs/BasicInfo/TranslationOptionsContainer',
	() => ({TranslationOptionsContainer: () => null})
);

const defaultProps: any = {
	containerWrapper: ({children}: any) => <div>{children}</div>,
	handleChange: jest.fn(),
	values: {
		businessType: 'Text',
		externalReferenceCode: 'ERC123',
		system: false,
	},
};

describe('BasicInfoTab', () => {
	it('renders the external reference code field on the object field page', () => {
		render(<BasicInfoTab {...defaultProps} />);

		expect(screen.getByText('external-reference-code')).toBeInTheDocument();

		const input = screen.getByDisplayValue('ERC123');

		expect(input).toBeInTheDocument();

		expect(input).toBeEnabled();
	});
});

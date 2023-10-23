/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';
import React from 'react';

import {RightSideBar} from '../../../components/ModelBuilder/RightSidebar/index';

jest.mock(
	'../../../components/ModelBuilder/ModelBuilderContext/objectFolderContext',
	() => {
		return {
			useObjectFolderContext() {
				return [
					{
						selectedObjectDefinitionNode: undefined,
						selectedObjectField: undefined,
						selectedObjectRelationship: undefined,
						showSidebars: true,
					},
				];
			},
		};
	}
);

describe('RightSidebarEmpty component', () => {
	it('renders label and description correctly', () => {
		render(<RightSideBar.Empty />);

		expect(
			screen.getByText('select-an-object-or-relationship')
		).toBeInTheDocument();
		expect(
			screen.getByText(
				'select-an-object-or-relationship-to-activate-this-panel'
			)
		).toBeInTheDocument();
	});
});

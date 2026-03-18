/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen} from '@testing-library/react';
import React from 'react';

import StructureSettings from '../../../../src/main/resources/META-INF/resources/js/structure_builder/components/settings/StructureSettings';
import {StructureType} from '../../../../src/main/resources/META-INF/resources/js/structure_builder/types/Structure';
import {MockCacheProvider} from '../mocks/MockCacheProvider';
import {DEFAULT_STRUCTURE, MockStateProvider} from '../mocks/MockStateProvider';

const renderComponent = ({
	type = 'L_CMS_CONTENT_STRUCTURES',
}: {
	type?: StructureType;
} = {}) => {
	return render(
		<MockStateProvider
			state={{
				structure: {
					...DEFAULT_STRUCTURE,
					type,
				},
			}}
		>
			<MockCacheProvider objectDefinitions={{}} spaces={[]}>
				<StructureSettings />
			</MockCacheProvider>
		</MockStateProvider>
	);
};

describe('StructureSettings', () => {
	it('renders a Content label when the structure is a content type', () => {
		renderComponent();

		expect(screen.getByText('content')).toHaveClass('label-item');
	});

	it('renders a File label when the structure is a file type', () => {
		renderComponent({
			type: 'L_CMS_FILE_TYPES',
		});

		expect(screen.getByText('file')).toHaveClass('label-item');
	});
});

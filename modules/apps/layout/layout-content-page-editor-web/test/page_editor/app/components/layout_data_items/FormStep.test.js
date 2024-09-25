/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';
import React from 'react';

import {FormStepWithControls} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/layout_data_items/FormStep';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import StoreMother from '../../../../../src/main/resources/META-INF/resources/page_editor/test_utils/StoreMother';

const DEFAULT_CONFIG = {classNameId: '0'};

describe('FormStep', () => {
	it('renders a form stepper with a dropzone and verify that the correct message appears when the dropzone is empty', () => {
		render(
			<StoreMother.Component>
				<FormStepWithControls
					item={{
						children: [],
						config: DEFAULT_CONFIG,
						itemId: 'formStep',
						type: LAYOUT_DATA_ITEM_TYPES.formStep,
					}}
				/>
			</StoreMother.Component>
		);

		expect(
			screen.getByText('drag-and-drop-fragments-or-widgets-here')
		).toBeInTheDocument();
	});
});

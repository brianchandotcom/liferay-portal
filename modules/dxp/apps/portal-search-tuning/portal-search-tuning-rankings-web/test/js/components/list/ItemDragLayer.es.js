/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ItemDragLayer from '../../../../src/main/resources/META-INF/resources/js/components/list/ItemDragLayer.es';
import React from 'react';
import {render} from '@testing-library/react';

describe('ItemDragLayer', () => {
	it('renders when dragging', () => {
		const {container} = render(
			<ItemDragLayer.DecoratedComponent dragging />
		);

		expect(container.firstChild).not.toBeNull();
		expect(container.firstChild).toBeVisible();
	});

	it('does not render by default', () => {
		const {container} = render(<ItemDragLayer.DecoratedComponent />);

		expect(container.firstChild).toBeNull();
	});
});

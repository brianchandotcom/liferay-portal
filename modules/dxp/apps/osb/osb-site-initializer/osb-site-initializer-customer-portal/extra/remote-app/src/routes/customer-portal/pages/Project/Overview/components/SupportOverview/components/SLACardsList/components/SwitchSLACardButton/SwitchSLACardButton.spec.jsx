/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ('License'). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {vi} from 'vitest';
import SwitchButton from '.';

describe('SwitchButton', () => {
	it('changes SlaCard when clicking the button', async () => {
		const functionMock = vi.fn();
		const user = userEvent.setup();

		render(<SwitchButton handleClick={functionMock} />);

		expect(screen.getByRole('button')).toBeInTheDocument();
		await user.click(screen.getByRole('button'));
		expect(functionMock).toHaveBeenCalled();
	});
});

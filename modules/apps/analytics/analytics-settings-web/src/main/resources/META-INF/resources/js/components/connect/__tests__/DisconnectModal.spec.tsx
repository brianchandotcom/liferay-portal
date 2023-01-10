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

import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';

import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';
import React from 'react';

// NOTE: to render properly in the tests, this Component is sligthly different from connect/DisconnetModal.tsx

const Component = () => {
	return (
		<>
			<ClayModal.Header>
				{Liferay.Language.get('disconnecting-data-source')}
			</ClayModal.Header>

			<ClayModal.Body>
				<p>
					<strong>
						{Liferay.Language.get(
							'are-you-sure-you-want-to-disconnect-your-analytics-cloud-workspace-from-this-dxp-instance'
						)}
					</strong>
				</p>

				<p className="text-secondary">
					{Liferay.Language.get(
						'this-will-stop-any-syncing-of-analytics-or-contact-data-to-your-analytics-cloud-workspace'
					)}
				</p>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType="secondary">
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton displayType="warning" onClick={() => {}}>
							{Liferay.Language.get('disconnect')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
};

describe('Disconnect Modal', () => {
	it('render Disconnect Modal without crashing', () => {
		render(<Component />);

		expect(
			screen.getByText(/disconnecting-data-source/i)
		).toBeInTheDocument();

		expect(
			screen.getByText(
				'are-you-sure-you-want-to-disconnect-your-analytics-cloud-workspace-from-this-dxp-instance'
			)
		).toBeInTheDocument();

		expect(
			screen.getByText(
				'this-will-stop-any-syncing-of-analytics-or-contact-data-to-your-analytics-cloud-workspace'
			)
		).toBeInTheDocument();

		expect(
			screen.getByRole('button', {name: /cancel/i})
		).toBeInTheDocument();

		expect(
			screen.getByRole('button', {name: /disconnect/i})
		).toBeInTheDocument();
	});
});

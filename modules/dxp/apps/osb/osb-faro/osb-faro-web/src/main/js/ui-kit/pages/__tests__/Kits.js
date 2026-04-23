import * as fs from 'fs';
import * as path from 'path';
import mockStore from 'test/mock-store';
import React from 'react';
import {MemoryRouter} from 'react-router-dom';
import {MockedProvider} from '@apollo/client/testing';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('Kits', () => {
	const files = fs
		.readdirSync(path.resolve(__dirname, '..'))
		.filter(file => file.match(/Kit\.(jsx|tsx)$/));

	files.forEach(file => {
		const kitName = file.replace(/\.(jsx|tsx)$/, '');

		describe(kitName, () => {
			const kitPath = `../${file}`;
			const Kit = require(kitPath).default;

			it('should render', () => {
				const {container} = render(
					<Provider store={mockStore()}>
						<MemoryRouter>
							<MockedProvider addTypename={false}>
								<Kit groupId='23' />
							</MockedProvider>
						</MemoryRouter>
					</Provider>
				);

				expect(container).toBeTruthy();
			});
		});
	});
});

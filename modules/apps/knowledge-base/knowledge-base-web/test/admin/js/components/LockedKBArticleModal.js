/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render} from '@testing-library/react';
import React from 'react';
import {act} from 'react-dom/test-utils';

import {LockedKBArticleModal} from '../../../../src/main/resources/META-INF/resources/js';

const bridgeComponentId = '_portletNamespace_LockedKBArticleModal';

describe('LockedKBArticleModal', () => {
	beforeAll(() => {
		jest.useFakeTimers();
	});

	beforeEach(() => {
		const components = {};

		Liferay.component = (id, component) => {
			components[id] = component;
		};
		Liferay.componentReady = (id) => Promise.resolve(components[id]);

		Liferay.destroyComponent = jest.fn();
	});

	it('does not render the modal first', () => {
		const result = render(<LockedKBArticleModal open={false} />);

		act(() => {
			jest.runAllTimers();
		});

		const title = result.queryByText('article-in-edition');

		expect(title).not.toBeInTheDocument();
	});

	it('renders the modal when try to edit/expire/delete a locked article', () => {
		const result = render(<LockedKBArticleModal open={true} />);

		act(() => {
			jest.runAllTimers();
		});

		const title = result.getByText('article-in-edition');
		const okButton = result.getByText('ok');

		expect(title).toBeInTheDocument();
		expect(okButton).toBeInTheDocument();
	});

	describe('when try to move a locked article', () => {
		let result;

		beforeEach(() => {
			result = render(
				<LockedKBArticleModal
					open={false}
					portletNamespace="_portletNamespace_"
				/>
			);

			return act(() =>
				Liferay.componentReady(bridgeComponentId).then(({open}) => {
					open();
				})
			);
		});

		it('renders the modal when called through the bridge component', async () => {
			act(() => {
				jest.runAllTimers();
			});

			const title = await result.getByText('article-in-edition');

			expect(title).toBeInTheDocument();
		});
	});
});

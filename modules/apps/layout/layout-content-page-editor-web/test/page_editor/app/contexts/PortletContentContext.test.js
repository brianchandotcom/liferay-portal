/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {renderHook} from '@testing-library/react-hooks';
import React from 'react';

import {
	PortletContentContextProvider,
	useAddPendingItem,
} from '../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/PortletContentContext';
import FragmentService from '../../../../src/main/resources/META-INF/resources/page_editor/app/services/FragmentService';
import StoreMother from '../../../../src/main/resources/META-INF/resources/page_editor/test_utils/StoreMother';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/services/FragmentService',
	() => ({
		renderFragmentEntryLinksContent: jest.fn(() =>
			Promise.resolve([
				{
					content: 'new content',
					fragmentEntryLinkId: '40626',
				},
				{
					content: 'new content',
					fragmentEntryLinkId: '40628',
				},
			])
		),
	})
);

const STATE = {
	current: () => {
		return {};
	},
};

const dispatch = () => {};

function getHook() {
	const wrapper = ({children}) => (
		<StoreMother.Component
			dispatch={dispatch}
			getState={() => {
				return STATE.current();
			}}
		>
			<PortletContentContextProvider>
				{children}
			</PortletContentContextProvider>
		</StoreMother.Component>
	);

	return renderHook(
		() => ({
			addPendingItem: useAddPendingItem(),
		}),
		{wrapper}
	);
}

describe('PortletContentContextProvider', () => {
	afterEach(() => {
		FragmentService.renderFragmentEntryLinksContent.mockClear();
	});

	it('does not call API for refresh portlets when there are no pending items', () => {
		getHook();

		expect(
			FragmentService.renderFragmentEntryLinksContent
		).not.toBeCalled();
	});

	it('call API for refresh portlets when changing viewport size', () => {
		const {rerender, result} = getHook();

		rerender();

		result.current.addPendingItem('12345');

		STATE.current = () => {
			return {selectedViewportSize: 'tablet'};
		};

		rerender();

		expect(FragmentService.renderFragmentEntryLinksContent).toBeCalledWith(
			expect.objectContaining({
				data: expect.arrayContaining([
					expect.objectContaining({fragmentEntryLinkId: '12345'}),
				]),
			})
		);

		// rerender();

	});

	it('call API for refresh portlets when changing segments experience', () => {
		const {rerender, result} = getHook();

		rerender();

		result.current.addPendingItem('12345');

		STATE.current = () => {
			return {segmentsExperienceId: '2'};
		};

		rerender();

		expect(FragmentService.renderFragmentEntryLinksContent).toBeCalledWith(
			expect.objectContaining({
				data: expect.arrayContaining([
					expect.objectContaining({fragmentEntryLinkId: '12345'}),
				]),
			})
		);

		// rerender();

	});
});

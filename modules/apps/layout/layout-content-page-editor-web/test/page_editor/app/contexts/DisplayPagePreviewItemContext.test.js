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

import {act, renderHook} from '@testing-library/react-hooks';
import React from 'react';

import {
	DisplayPagePreviewItemContextProvider,
	useDisplayPagePreviewItem,
	useDisplayPageRecentPreviewItemList,
	useSelectDisplayPagePreviewItem,
} from '../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/DisplayPagePreviewItemContext';

function getHook() {
	const wrapper = ({children}) => (
		<DisplayPagePreviewItemContextProvider>
			{children}
		</DisplayPagePreviewItemContextProvider>
	);

	return renderHook(
		() => ({
			item: useDisplayPagePreviewItem(),
			recentItemList: useDisplayPageRecentPreviewItemList(),
			selectItem: useSelectDisplayPagePreviewItem(),
		}),
		{wrapper}
	);
}

describe('DisplayPagePreviewItemContext', () => {
	it('provides the latest selected item', () => {
		const hook = getHook();

		act(() => {
			hook.result.current.selectItem({data: {}, label: 'First'});
		});

		expect(hook.result.current.item).toEqual({data: {}, label: 'First'});
	});

	it('provides a list of recent items', () => {
		const hook = getHook();

		act(() => {
			hook.result.current.selectItem({data: {}, label: 'Vero'});
			hook.result.current.selectItem({data: {}, label: 'Sandro'});
		});

		expect(hook.result.current.recentItemList).toEqual([
			{data: {}, label: 'Sandro'},
			{data: {}, label: 'Vero'},
		]);
	});

	it('avoids having duplicated items', () => {
		const hook = getHook();

		act(() => {
			hook.result.current.selectItem({data: {}, label: 'First'});
			hook.result.current.selectItem({data: {}, label: 'Second'});
			hook.result.current.selectItem({data: {}, label: 'First'});
		});

		expect(hook.result.current.recentItemList).toEqual([
			{data: {}, label: 'First'},
			{data: {}, label: 'Second'},
		]);
	});

	it('limits the size of recent items to 100', () => {
		const hook = getHook();

		act(() => {
			for (let i = 0; i < 110; i++) {
				hook.result.current.selectItem({data: {}, label: `Item ${i}`});
			}
		});

		expect(hook.result.current.recentItemList.length).toBe(100);
	});
});

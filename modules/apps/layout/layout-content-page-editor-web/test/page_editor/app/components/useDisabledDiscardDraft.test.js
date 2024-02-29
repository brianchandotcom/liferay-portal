/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {renderHook} from '@testing-library/react-hooks';
import React from 'react';

import useDisabledDiscardDraft from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/useDisabledDiscardDraft';
import {config} from '../../../../src/main/resources/META-INF/resources/page_editor/app/config/index';
import StoreMother from '../../../../src/main/resources/META-INF/resources/page_editor/test_utils/StoreMother';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/config/index',
	() => ({
		config: {
			isConversionDraft: false,
		},
	})
);

const INITIAL_STATE = {
	draft: false,
	network: {status: 1},
};

const wrapper = ({children, state = INITIAL_STATE}) => (
	<StoreMother.Component getState={() => state}>
		{children}
	</StoreMother.Component>
);

describe('useDisabledDiscardDraft', () => {
	it('returns true when when the main condition is not met', () => {
		const {result} = renderHook(() => useDisabledDiscardDraft(), {wrapper});

		expect(result.current).toEqual(true);
	});

	it('returns false when network status is equal to 0', () => {
		const {result} = renderHook(() => useDisabledDiscardDraft(), {
			initialProps: {
				state: {...INITIAL_STATE, network: {status: 0}},
			},
			wrapper,
		});

		expect(result.current).toEqual(false);
	});

	it('returns false when draft is true', () => {
		const {result} = renderHook(() => useDisabledDiscardDraft(), {
			initialProps: {
				state: {...INITIAL_STATE, draft: true},
			},
			wrapper,
		});

		expect(result.current).toEqual(false);
	});

	it('returns false when isConversionDraft is true', () => {
		config.isConversionDraft = true;

		const {result} = renderHook(() => useDisabledDiscardDraft(), {
			wrapper,
		});

		expect(result.current).toEqual(false);
	});
});

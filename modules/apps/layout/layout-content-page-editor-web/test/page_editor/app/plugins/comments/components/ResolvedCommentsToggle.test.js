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

import '@testing-library/jest-dom/extend-expect';
import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import {toggleShowResolvedComments} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/actions';
import {StoreAPIContextProvider} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/store/index';
import ResolvedCommentsToggle from '../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/comments/components/ResolvedCommentsToggle';

const renderComponent = (state = {}, dispatch) => {
	const {getByLabelText} = render(
		<StoreAPIContextProvider dispatch={dispatch} getState={() => state}>
			<ResolvedCommentsToggle />
		</StoreAPIContextProvider>
	);

	return getByLabelText('show-resolved-comments');
};

const RESOLVED_COMMENTS_STATE = {
	fragmentEntryLinks: {
		'fragment-entry-link-1': {comments: [{resolved: true}]}
	}
};

describe('ResolvedCommentsToggle', () => {
	afterEach(cleanup);

	it('is unchecked by default', () => {
		expect(renderComponent().checked).toBe(false);
	});

	it('is cheched if showResolvedComments is true', () => {
		expect(renderComponent({showResolvedComments: true}).checked).toBe(
			true
		);
	});

	it('is disabled if there are no resolved comments', () => {
		expect(renderComponent()).toBeDisabled();
	});

	it('is enabled if there are some resolved comments', () => {
		expect(renderComponent(RESOLVED_COMMENTS_STATE)).not.toBeDisabled();
	});

	it('dispatches toggleShowResolvedComments on change', () => {
		const dispatch = jest.fn();
		const checkbox = renderComponent(RESOLVED_COMMENTS_STATE, dispatch);

		fireEvent.click(checkbox);

		expect(dispatch).toHaveBeenCalledWith(
			toggleShowResolvedComments({showResolvedComments: true})
		);
	});
});

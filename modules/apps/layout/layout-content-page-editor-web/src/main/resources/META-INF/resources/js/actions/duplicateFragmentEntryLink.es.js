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

import {duplicateFragmentEntryLink} from '../utils/FragmentsEditorFetchUtils.es';
import {DUPLICATE_FRAGMENT_ENTRY_LINK} from './actions.es';
import {updatePageEditorLayoutDataAction} from './updatePageEditorLayoutData.es';
import {FRAGMENTS_EDITOR_ITEM_TYPES} from '../utils/constants';
import {updateActiveItemAction} from './updateActiveItem.es';

/**
 * @param {string} fragmentEntryLinkId
 * @param {string} fragmentEntryLinkRowType
 * @param {string} content
 * @review
 */
function duplicateFragmentEntryLinkAction(
	fragmentEntryLinkId,
	fragmentEntryLinkRowType,
	content
) {
	return function(dispatch) {
		let newFragmentEntryLinkId;

		duplicateFragmentEntryLink(fragmentEntryLinkId)
			.then(fragmentEntryLink => {
				newFragmentEntryLinkId = fragmentEntryLink.fragmentEntryLinkId;

				return dispatch(
					_duplicateFragmentEntryLinkAction(
						fragmentEntryLinkId,
						{...fragmentEntryLink, content},
						fragmentEntryLinkRowType,
						content
					)
				);
			})
			.then(() => dispatch(updatePageEditorLayoutDataAction()))
			.then(() =>
				dispatch(
					updateActiveItemAction(
						newFragmentEntryLinkId,
						FRAGMENTS_EDITOR_ITEM_TYPES.fragment
					)
				)
			);
	};
}

/**
 * @param {string} originalFragmentEntryLinkId
 * @param {object} fragmentEntryLink
 * @param {string} fragmentEntryLinkRowType
 */
function _duplicateFragmentEntryLinkAction(
	originalFragmentEntryLinkId,
	fragmentEntryLink,
	fragmentEntryLinkRowType
) {
	return {
		fragmentEntryLink,
		fragmentEntryLinkId: originalFragmentEntryLinkId,
		fragmentEntryLinkRowType,
		type: DUPLICATE_FRAGMENT_ENTRY_LINK
	};
}

export {duplicateFragmentEntryLinkAction};

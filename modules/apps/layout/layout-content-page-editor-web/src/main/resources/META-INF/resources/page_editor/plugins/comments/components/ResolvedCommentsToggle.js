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

import {ClayCheckbox} from '@clayui/form';
import React from 'react';

import toggleShowResolvedComments from '../../../app/actions/toggleShowResolvedComments';
import {useDispatch, useSelector} from '../../../app/store/index';

export default function ResolvedCommentsToggle() {
	const dispatch = useDispatch();

	const showResolvedComments = useSelector(
		state => state.showResolvedComments
	);

	const hasResolvedComments = useSelector(state =>
		Object.values(state.fragmentEntryLinks).some(
			fragmentEntryLink =>
				fragmentEntryLink.comments &&
				fragmentEntryLink.comments.some(comment => comment.resolved)
		)
	);

	return (
		<div className="pb-3 px-3">
			<ClayCheckbox
				checked={showResolvedComments}
				disabled={!showResolvedComments && !hasResolvedComments}
				label={Liferay.Language.get('show-resolved-comments')}
				onChange={event =>
					dispatch(
						toggleShowResolvedComments({
							showResolvedComments: Boolean(event.target.checked)
						})
					)
				}
			/>
		</div>
	);
}

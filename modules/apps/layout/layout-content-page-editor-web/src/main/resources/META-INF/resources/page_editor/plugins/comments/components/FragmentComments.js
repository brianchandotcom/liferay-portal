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
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import {StoreContext} from '../../../app/store/index';
import SidebarPanelContent from '../../../common/components/SidebarPanelContent';
import SidebarPanelHeader from '../../../common/components/SidebarPanelHeader';
import AppContext from '../../../core/AppContext';
import AddCommentForm from './AddCommentForm';
import FragmentComment from './FragmentComment';
import ResolvedCommentsToggle from './ResolvedCommentsToggle';

export default function FragmentComments({fragmentEntryLink}) {
	const {comments, fragmentEntryLinkId, name} = fragmentEntryLink;

	const {dispatch} = useContext(AppContext);
	const {showResolvedComments} = useContext(StoreContext);

	const fragmentEntryLinkComments = showResolvedComments
		? comments
		: comments.filter(({resolved}) => !resolved);

	return (
		<>
			<SidebarPanelHeader className="comments-sidebar-title">
				<ClayButton
					borderless
					className="text-dark"
					onClick={() => dispatch({type: 'clearActiveItem'})}
					small
				>
					<ClayIcon symbol="angle-left" />
				</ClayButton>

				<span>{name}</span>
			</SidebarPanelHeader>

			<SidebarPanelContent>
				<ResolvedCommentsToggle />

				<div>
					<AddCommentForm fragmentEntryLinkId={fragmentEntryLinkId} />

					{fragmentEntryLinkComments.map((_, i) => {
						const comment =
							fragmentEntryLinkComments[
								fragmentEntryLinkComments.length - 1 - i
							];

						return (
							<FragmentComment
								comment={comment}
								fragmentEntryLinkId={fragmentEntryLinkId}
								key={comment.commentId}
								onDelete={({commentId}) =>
									dispatch({
										commentId,
										fragmentEntryLinkId,
										type: 'deleteFragmentEntryLinkComment'
									})
								}
								onEdit={({commentId}) =>
									dispatch({
										commentId,
										fragmentEntryLinkId,
										type: 'editComment'
									})
								}
								onEditReply={parentCommentId => ({commentId}) =>
									dispatch({
										commentId,
										fragmentEntryLinkId,
										parentCommentId,
										type: 'editCommentReply'
									})}
							/>
						);
					})}
				</div>
			</SidebarPanelContent>
		</>
	);
}

FragmentComments.propTypes = {
	fragmentEntryLink: PropTypes.object.isRequired
};

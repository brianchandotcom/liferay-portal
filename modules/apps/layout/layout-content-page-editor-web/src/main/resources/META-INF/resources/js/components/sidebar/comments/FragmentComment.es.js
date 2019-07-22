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

/* eslint no-unused-vars: "warn" */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayDropDown from '@clayui/drop-down';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import UserIcon from '../../common/UserIcon.es';
import EditCommentForm from './EditCommentForm.es';

const FragmentComment = props => {
	const [dropDownActive, setDropDownActive] = useState(false);
	const [editing, setEditing] = useState(false);

	const _handleEditButtonClick = () => {
		setDropDownActive(false);
		setEditing(true);
	};

	return (
		<article className="fragments-editor__fragment-comment small">
			<div className="d-flex mb-2">
				<UserIcon {...props.author} />

				<div className="flex-grow-1 pl-2">
					<p className="m-0 text-truncate">
						<strong>{props.author.fullName}</strong>
					</p>

					<p className="m-0 text-secondary">
						{props.dateDescription}
					</p>
				</div>

				<ClayDropDown
					active={dropDownActive}
					onActiveChange={setDropDownActive}
					trigger={
						<ClayButton
							className="text-secondary btn-monospaced btn-sm"
							disabled={editing}
							displayType="unstyled"
						>
							<ClayIcon symbol="ellipsis-v" />
						</ClayButton>
					}
				>
					<ClayDropDown.ItemList>
						<ClayDropDown.Item
							href="#edit"
							onClick={_handleEditButtonClick}
						>
							{Liferay.Language.get('edit')}
						</ClayDropDown.Item>
					</ClayDropDown.ItemList>
				</ClayDropDown>
			</div>

			{editing ? (
				<EditCommentForm
					body={props.body}
					commentId={props.commentId}
					onCloseForm={() => setEditing(false)}
				/>
			) : (
				<p
					className="text-secondary"
					dangerouslySetInnerHTML={{__html: props.body}}
				/>
			)}
		</article>
	);
};

FragmentComment.propTypes = {
	author: PropTypes.shape({
		fullName: PropTypes.string,
		portraitURL: PropTypes.string
	}),

	commentId: PropTypes.string.isRequired,
	body: PropTypes.string,
	dateDescription: PropTypes.string
};

export {FragmentComment};
export default FragmentComment;

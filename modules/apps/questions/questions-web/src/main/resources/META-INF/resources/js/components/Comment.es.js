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
import React from 'react';

import {deleteMessage} from '../utils/client.es';

export default ({comment, commentChange}) => {
	const deleteComment = () => {
		deleteMessage(comment);
		commentChange(comment);
	};

	return (
		<div className="c-my-3 questions-reply row">
			<div className="align-items-md-center col-2 col-md-1 d-flex justify-content-end justify-content-md-center">
				<ClayIcon
					className="c-mt-3 c-mt-md-0 questions-reply-icon text-secondary"
					symbol="reply"
				/>
			</div>

			<div className="col-10 col-lg-11">
				<p className="c-mb-0">
					{comment.articleBody}
					{' - '}
					<span className="font-weight-bold">
						{comment.creator.name}
					</span>
				</p>

				{comment.actions.delete && (
					<ClayButton
						className="c-mt-3 font-weight-bold text-secondary"
						displayType="unstyled"
						onClick={deleteComment}
					>
						{Liferay.Language.get('delete')}
					</ClayButton>
				)}
			</div>
		</div>
	);
};

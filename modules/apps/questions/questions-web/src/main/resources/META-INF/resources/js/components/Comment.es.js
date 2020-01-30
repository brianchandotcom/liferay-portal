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
		<div className="autofit-padded autofit-row question-comment">
			<div className="autofit-col question-reply-icon-row">
				<ClayIcon className="question-reply-icon" symbol="reply" />
			</div>
			<div className="autofit-col autofit-col-expand">
				<hr className="question-comment-separator" />
				<p>
					{comment.articleBody} -{' '}
					<strong>{comment.creator.name}</strong>
				</p>
				<p>
					{comment.actions.delete && (
						<ClayButton
							displayType="unstyled"
							onClick={deleteComment}
						>
							{Liferay.Language.get('delete')}
						</ClayButton>
					)}
				</p>
			</div>
		</div>
	);
};

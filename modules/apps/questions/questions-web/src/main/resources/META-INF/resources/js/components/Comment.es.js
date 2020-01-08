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

import ClayIcon from '@clayui/icon';
import ClayButton from '@clayui/button';
import React from 'react';

import {deleteMessage} from '../utils/client.es';

export default ({comment, commentChange}) => {
	const deleteComment = () => {
		deleteMessage(comment);
		commentChange(comment);
	};

	return (
		<div className="question-comment autofit-row autofit-padded">
			<div className="autofit-col question-reply-icon-row">
				<ClayIcon className="question-reply-icon" symbol="reply"/>
			</div>
			<div className="autofit-col autofit-col-expand">
				<hr style={{marginLeft: 0, marginRight: 0}}/>
				<p>
					{comment.articleBody} - <strong>{comment.creator.name}</strong>
				</p>
				<ClayButton onClick={deleteComment} displayType="unstyled">{Liferay.Language.get('delete')}</ClayButton>
			</div>
		</div>
	);
};

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
import React, {useState} from 'react';

import {deleteMessage} from '../utils/client.es';

export default ({comment, commentChange}) => {
	const [showDelete, setShowDelete] = useState(false);

	const deleteComment = () => {
		deleteMessage(comment);
		commentChange(comment);
	};

	return (
		<div
			className="question-comment"
			onMouseLeave={() => setShowDelete(false)}
			onMouseOver={() => setShowDelete(true)}
		>
			<ClayIcon className="question-icon-rotate-180" symbol="reply" />{' '}
			{comment.articleBody} - <strong>{comment.creator.name}</strong>
			{showDelete && (
				<span onClick={deleteComment}>
					{' ' + Liferay.Language.get('delete')}
				</span>
			)}
		</div>
	);
};

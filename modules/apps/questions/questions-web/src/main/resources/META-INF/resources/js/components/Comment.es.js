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

import {useMutation} from '@apollo/client';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React, {useEffect, useState} from 'react';

import {deleteMessageQuery} from '../utils/client.es';
import lang from '../utils/lang.es';
import ArticleBodyRenderer from './ArticleBodyRenderer.es';
import Modal from './Modal.es';

export default ({comment, commentChange, editable = true}) => {
	const [dateModified, setDateModified] = useState('');
	const [showDeleteCommentModal, setShowDeleteCommentModal] = useState(false);

	const [deleteMessage] = useMutation(deleteMessageQuery, {
		onCompleted() {
			if (commentChange) {
				commentChange(comment);
			}
		},
		update(proxy) {
			proxy.evict(`MessageBoardMessage:${comment.id}`);
			proxy.gc();
		},
	});

	useEffect(() => {
		setDateModified(new Date(comment.dateModified).toLocaleDateString());
	}, [comment.dateModified]);

	return (
		<div className="c-my-3 questions-reply row">
			<div className="align-items-md-center col-2 col-md-1 d-flex justify-content-end justify-content-md-center">
				<ClayIcon
					className="c-mt-3 c-mt-md-0 questions-reply-icon text-secondary"
					symbol="reply"
				/>
			</div>

			<div className="col-10 col-lg-11">
				<span className="text-secondary">
					{lang.sub(Liferay.Language.get('replied-x'), [
						dateModified,
					])}
				</span>
				<div className="c-mb-0">
					<ArticleBodyRenderer
						{...comment}
						signature={comment.creator.name}
					/>
				</div>

				{editable && comment.actions.delete && (
					<>
						<ClayButton
							className="c-mt-3 font-weight-bold text-secondary"
							displayType="unstyled"
							onClick={() => {
								setShowDeleteCommentModal(true);
							}}
						>
							{Liferay.Language.get('delete')}
						</ClayButton>
						<Modal
							body={Liferay.Language.get(
								'do-you-want-to-delete–this-comment'
							)}
							callback={() => {
								deleteMessage({
									variables: {
										messageBoardMessageId: comment.id,
									},
								});
							}}
							onClose={() => {
								setShowDeleteCommentModal(false);
							}}
							status="warning"
							textPrimaryButton={Liferay.Language.get('delete')}
							title={Liferay.Language.get('delete-comment')}
							visible={showDeleteCommentModal}
						/>
					</>
				)}
			</div>
		</div>
	);
};

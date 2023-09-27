/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import {useMutation} from 'graphql-hooks';
import React, {useCallback, useContext, useRef, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../AppContext.es';
import {createCommentQuery, getUserActivityQuery} from '../utils/client.es';
import {deleteCacheKey, getContextLink} from '../utils/utils.es';
import Comment from './Comment.es';
import DefaultQuestionsEditor from './DefaultQuestionsEditor.es';
import SubscritionCheckbox from './SubscribeCheckbox.es';

export default withRouter(
	({
		comments,
		commentsChange,
		companyName,
		display,
		editable = true,
		entityId,
		match: {
			params: {questionId, sectionTitle},
		},
		onSubscription,
		question,
		showNewComment,
		showNewCommentChange,
		showSignature,
		styledItems = false,
	}) => {
		const context = useContext(AppContext);

		const editorRef = useRef('');

		const [allowSubscription, setAllowSubscription] = useState(false);
		const [isReplyButtonDisable, setIsReplyButtonDisable] = useState(false);

		const [createComment] = useMutation(createCommentQuery);

		const _commentChange = useCallback(
			(comment) => {
				if (commentsChange) {
					return commentsChange([
						...comments.filter((o) => o.id !== comment.id),
					]);
				}

				return null;
			},
			[commentsChange, comments]
		);

		const onCreateComment = async () => {
			const {data} = await createComment({
				fetchOptionsOverrides: getContextLink(
					`${sectionTitle}/${questionId}`
				),
				variables: {
					articleBody: editorRef.current.getContent(),
					parentMessageBoardMessageId: entityId,
				},
			});

			editorRef.current.clearContent();

			showNewCommentChange(false);

			commentsChange([
				...comments,
				data.createMessageBoardMessageMessageBoardMessage,
			]);

			onSubscription({allowSubscription});

			deleteCacheKey(getUserActivityQuery, {
				filter: `creatorId eq ${context.userId}`,
				page: 1,
				pageSize: 20,
				siteKey: context.siteKey,
			});
		};

		return (
			<div>
				{comments.map((comment) => (
					<Comment
						comment={comment}
						commentChange={_commentChange}
						companyName={companyName}
						display={display}
						editable={editable}
						hasCompanyMx={comment.hasCompanyMx}
						key={comment.id}
						showSignature={showSignature}
						styledItems={styledItems}
					/>
				))}

				{editable && showNewComment && (
					<>
						<ClayForm.Group small>
							<DefaultQuestionsEditor
								label={Liferay.Language.get('your-comment')}
								onContentLengthValid={setIsReplyButtonDisable}
								ref={editorRef}
							/>

							{!question.subscribed && (
								<SubscritionCheckbox
									checked={allowSubscription}
									setChecked={setAllowSubscription}
								/>
							)}

							<ClayButton.Group className="c-mt-3" spaced>
								<ClayButton
									aria-label={
										context.trustedUser
											? Liferay.Language.get(
													'add-comment'
											  )
											: Liferay.Language.get(
													'submit-for-publication'
											  )
									}
									disabled={isReplyButtonDisable}
									displayType="primary"
									onClick={onCreateComment}
								>
									{context.trustedUser
										? Liferay.Language.get('add-comment')
										: Liferay.Language.get(
												'submit-for-workflow'
										  )}
								</ClayButton>

								<ClayButton
									aria-label={Liferay.Language.get('cancel')}
									displayType="secondary"
									onClick={() => showNewCommentChange(false)}
								>
									{Liferay.Language.get('cancel')}
								</ClayButton>
							</ClayButton.Group>
						</ClayForm.Group>
					</>
				)}
			</div>
		);
	}
);

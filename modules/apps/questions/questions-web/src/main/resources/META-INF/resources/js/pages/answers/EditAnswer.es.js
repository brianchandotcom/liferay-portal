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
import {useMutation} from 'graphql-hooks';
import React, {useContext, useEffect, useRef, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import DefaultQuestionsEditor from '../../components/DefaultQuestionsEditor.es';
import {
	client,
	getMessageQuery,
	updateMessageQuery,
} from '../../utils/client.es';
import {getContextLink} from '../../utils/utils.es';

export default withRouter(
	({
		history,
		match: {
			params: {answerId, questionId, sectionTitle},
		},
	}) => {
		const context = useContext(AppContext);

		const [addUpdateMessage] = useMutation(updateMessageQuery);

		const [data, setData] = useState();
		const editorRef = useRef('');
		const [isUpdateButtonDisabled, setIsUpdateButtonDisabled] = useState(
			false
		);

		useEffect(() => {
			editorRef.current.setContent(
				data && data.messageBoardMessageByFriendlyUrlPath.articleBody
			);
		}, [data]);

		const onInstanceReady = () => {
			client
				.request({
					query: getMessageQuery,
					variables: {
						friendlyUrlPath: answerId,
						siteKey: context.siteKey,
					},
				})
				.then(({data}) => setData(data));
		};

		const handleClickMessage = () => {
			addUpdateMessage({
				fetchOptionsOverrides: getContextLink(
					`${sectionTitle}/${questionId}`
				),
				variables: {
					articleBody: editorRef.current.getContent(),
					messageBoardMessageId:
						data.messageBoardMessageByFriendlyUrlPath.id,
				},
			}).then(() => {
				editorRef.current.clearContent();
				history.goBack();
			});
		};

		const isAReplyMessage = answerId.includes('re-re-');

		return (
			<section className="c-mt-5 questions-section questions-sections-answer">
				<div className="questions-container row">
					<div className="c-mx-auto col-xl-10">
						<h1>
							{isAReplyMessage
								? Liferay.Language.get('edit-comment')
								: Liferay.Language.get('edit-answer')}
						</h1>

						<DefaultQuestionsEditor
							label={
								isAReplyMessage
									? Liferay.Language.get('your-comment')
									: Liferay.Language.get('your-answer')
							}
							onContentLengthValid={setIsUpdateButtonDisabled}
							onInstanceReady={onInstanceReady}
							ref={editorRef}
						/>

						<div className="c-mt-4 d-flex flex-column-reverse flex-sm-row">
							<ClayButton
								className="c-mt-4 c-mt-sm-0"
								disabled={isUpdateButtonDisabled}
								displayType="primary"
								onClick={handleClickMessage}
							>
								{context.trustedUser
									? isAReplyMessage
										? Liferay.Language.get(
												'update-your-comment'
										  )
										: Liferay.Language.get(
												'update-your-answer'
										  )
									: Liferay.Language.get(
											'submit-for-publication'
									  )}
							</ClayButton>

							<ClayButton
								className="c-ml-sm-3"
								displayType="secondary"
								onClick={() => history.goBack()}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>
						</div>
					</div>
				</div>
			</section>
		);
	}
);

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

import {useMutation, useQuery} from '@apollo/client';
import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React, {useContext, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import Link from '../../components/Link.es';
import QuestionsEditor from '../../components/QuestionsEditor';
import TagSelector from '../../components/TagSelector.es';
import TextLengthValidation from '../../components/TextLengthValidation.es';
import {getThreadContentQuery, updateThreadQuery} from '../../utils/client.es';
import {getContextLink, stripHTML} from '../../utils/utils.es';

export default withRouter(
	({
		history,
		match: {
			params: {questionId, sectionTitle},
		},
	}) => {
		const context = useContext(AppContext);

		const [articleBody, setArticleBody] = useState('');
		const [headline, setHeadline] = useState('');
		const [id, setId] = useState('');
		const [tags, setTags] = useState([]);
		const [tagsLoaded, setTagsLoaded] = useState(true);

		useQuery(getThreadContentQuery, {
			onCompleted({messageBoardThreadByFriendlyUrlPath}) {
				setArticleBody(messageBoardThreadByFriendlyUrlPath.articleBody);
				setHeadline(messageBoardThreadByFriendlyUrlPath.headline);
				setId(messageBoardThreadByFriendlyUrlPath.id);
				if (messageBoardThreadByFriendlyUrlPath.keywords) {
					setTags(
						messageBoardThreadByFriendlyUrlPath.keywords.map(
							(keyword) => ({
								label: keyword,
								value: keyword,
							})
						)
					);
				}
			},
			variables: {
				friendlyUrlPath: questionId,
				siteKey: context.siteKey,
			},
		});

		const [updateThread] = useMutation(updateThreadQuery, {
			context: getContextLink(`${sectionTitle}/${questionId}`),
			onCompleted() {
				history.goBack();
			},
			update(proxy) {
				proxy.evict(`MessageBoardThread:${id}`);
				proxy.gc();
			},
		});

		return (
			<section className="c-mt-5 questions-section questions-section-edit">
				<div className="questions-container row">
					<div className="c-mx-auto col-xl-10">
						<h1>{Liferay.Language.get('edit-question')}</h1>

						<ClayForm>
							<ClayForm.Group className="c-mt-4">
								<label htmlFor="basicInput">
									{Liferay.Language.get('title')}

									<span className="c-ml-2 reference-mark">
										<ClayIcon symbol="asterisk" />
									</span>
								</label>

								<ClayInput
									onChange={(event) =>
										setHeadline(event.target.value)
									}
									placeholder={Liferay.Language.get(
										'what-is-your-question'
									)}
									required
									type="text"
									value={headline}
								/>

								<ClayForm.FeedbackGroup>
									<ClayForm.FeedbackItem>
										<span className="small text-secondary">
											{Liferay.Language.get(
												'be-specific-and-imagine-you-are-asking-a-question-to-another-person'
											)}
										</span>
									</ClayForm.FeedbackItem>
								</ClayForm.FeedbackGroup>
							</ClayForm.Group>

							<ClayForm.Group className="c-mt-4">
								<label htmlFor="basicInput">
									{Liferay.Language.get('body')}

									<span className="c-ml-2 reference-mark">
										<ClayIcon symbol="asterisk" />
									</span>
								</label>

								<QuestionsEditor
									contents={articleBody}
									onChange={(event) => {
										setArticleBody(event.editor.getData());
									}}
								/>

								<ClayForm.FeedbackGroup>
									<ClayForm.FeedbackItem>
										<span className="small text-secondary">
											{Liferay.Language.get(
												'include-all-the-information-someone-would-need-to-answer-your-question'
											)}
										</span>
										<TextLengthValidation
											text={articleBody}
										/>
									</ClayForm.FeedbackItem>
								</ClayForm.FeedbackGroup>
							</ClayForm.Group>

							<ClayForm.Group className="c-mt-4">
								<TagSelector
									tags={tags}
									tagsChange={(tags) => setTags(tags)}
									tagsLoaded={setTagsLoaded}
								/>
							</ClayForm.Group>
						</ClayForm>

						<div className="c-mt-4 d-flex flex-column-reverse flex-sm-row">
							<ClayButton
								className="c-mt-4 c-mt-sm-0"
								disabled={
									!articleBody ||
									!headline ||
									!tagsLoaded ||
									stripHTML(articleBody).length < 15
								}
								displayType="primary"
								onClick={() => {
									updateThread({
										variables: {
											articleBody,
											headline,
											keywords: tags.map(
												(tag) => tag.value
											),
											messageBoardThreadId: id,
										},
									});
								}}
							>
								{Liferay.Language.get('update-your-question')}
							</ClayButton>

							<Link
								className="btn btn-secondary c-ml-sm-3"
								to={`/questions/${sectionTitle}/${questionId}`}
							>
								{Liferay.Language.get('cancel')}
							</Link>
						</div>
					</div>
				</div>
			</section>
		);
	}
);

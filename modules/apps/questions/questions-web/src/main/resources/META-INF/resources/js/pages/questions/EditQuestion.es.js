/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {useMutation, useQuery} from 'graphql-hooks';
import React, {useContext, useEffect, useRef, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import DefaultQuestionsEditor from '../../components/DefaultQuestionsEditor.es';
import Link from '../../components/Link.es';
import TagSelector from '../../components/TagSelector.es';
import {getThreadContentQuery, updateThreadQuery} from '../../utils/client.es';
import {getContextLink} from '../../utils/utils.es';

export default withRouter(
	({
		history,
		match: {
			params: {questionId, sectionTitle},
		},
	}) => {
		const context = useContext(AppContext);

		const editorRef = useRef('');
		const [hasEnoughContent, setHasEnoughContent] = useState(false);

		const [headline, setHeadline] = useState('');
		const [id, setId] = useState('');
		const [tags, setTags] = useState([]);
		const [tagsLoaded, setTagsLoaded] = useState(true);

		const {data = {}} = useQuery(getThreadContentQuery, {
			useCache: false,
			variables: {
				friendlyUrlPath: questionId,
				siteKey: context.siteKey,
			},
		});

		useEffect(() => {
			if (data.messageBoardThreadByFriendlyUrlPath) {
				editorRef.current.setContent(
					data.messageBoardThreadByFriendlyUrlPath.articleBody
				);
				setHeadline(data.messageBoardThreadByFriendlyUrlPath.headline);
				setId(data.messageBoardThreadByFriendlyUrlPath.id);
				if (data.messageBoardThreadByFriendlyUrlPath.keywords) {
					setTags(
						data.messageBoardThreadByFriendlyUrlPath.keywords.map(
							(keyword) => ({
								label: keyword,
								value: keyword,
							})
						)
					);
				}
			}
		}, [data]);

		const [updateThread] = useMutation(updateThreadQuery);

		return (
			<section className="c-mt-5 questions-section questions-section-edit">
				<div className="questions-container row">
					<div className="c-mx-auto col-xl-10">
						<h1>
							{Liferay.FeatureFlags['LPS-185892']
								? context.editQuestionPageTitle !== ''
									? context.editQuestionPageTitle
									: Liferay.Language.get('edit-question')
								: Liferay.Language.get('edit-question')}
						</h1>

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

							<DefaultQuestionsEditor
								additionalInformation={Liferay.Language.get(
									'include-all-the-information-someone-would-need-to-answer-your-question'
								)}
								label={Liferay.Language.get('body')}
								onContentLengthValid={setHasEnoughContent}
								ref={editorRef}
							/>

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
								aria-label={
									context.trustedUser
										? Liferay.FeatureFlags['LPS-185892']
											? context.updateYourQuestionButtonText !==
												''
												? context.updateYourQuestionButtonText
												: Liferay.Language.get(
														'update-your-question'
													)
											: Liferay.Language.get(
													'update-your-question'
												)
										: Liferay.Language.get(
												'submit-for-publication'
											)
								}
								className="c-mt-4 c-mt-sm-0"
								disabled={
									hasEnoughContent || !headline || !tagsLoaded
								}
								displayType="primary"
								onClick={() => {
									updateThread(
										{
											variables: {
												articleBody:
													editorRef.current.getContent(),
												headline,
												keywords: tags.map(
													(tag) => tag.value
												),
												messageBoardThreadId: id,
											},
										},
										{
											fetchOptionsOverrides:
												getContextLink(
													`${sectionTitle}/${questionId}`
												),
										}
									).then(() => history.goBack());
								}}
							>
								{context.trustedUser
									? Liferay.FeatureFlags['LPS-185892']
										? context.updateYourQuestionButtonText !==
											''
											? context.updateYourQuestionButtonText
											: Liferay.Language.get(
													'update-your-question'
												)
										: Liferay.Language.get(
												'update-your-question'
											)
									: Liferay.Language.get(
											'submit-for-publication'
										)}
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

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput, ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {useManualQuery, useMutation} from 'graphql-hooks';
import React, {useContext, useEffect, useRef, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import Alert from '../../components/Alert.es';
import DefaultQuestionsEditor from '../../components/DefaultQuestionsEditor.es';
import Link from '../../components/Link.es';
import TagSelector from '../../components/TagSelector.es';
import {
	createQuestionInASectionQuery,
	createQuestionInRootQuery,
	getMessageBoardSectionByFriendlyUrlPathQuery,
} from '../../utils/client.es';
import lang from '../../utils/lang.es';
import {
	deleteCache,
	getContextLink,
	historyPushWithSlug,
	useDebounceCallback,
} from '../../utils/utils.es';

const HEADLINE_MAX_LENGTH = 75;

export default withRouter(
	({
		history,
		match: {
			params: {sectionTitle},
		},
	}) => {
		const editorRef = useRef('');
		const [hasEnoughContent, setHasEnoughContent] = useState(false);
		const [headline, setHeadline] = useState('');
		const [error, setError] = useState({});
		const [isPostButtonDisable, setIsPostButtonDisable] = useState(true);
		const [sectionId, setSectionId] = useState();
		const [sections, setSections] = useState([]);
		const [tags, setTags] = useState([]);
		const [tagsLoaded, setTagsLoaded] = useState(true);

		const context = useContext(AppContext);
		const historyPushParser = historyPushWithSlug(history.push);

		const [debounceCallback] = useDebounceCallback(
			() => historyPushParser(`/questions/${sectionTitle}/`),
			500
		);

		const [createQuestionInASection] = useMutation(
			createQuestionInASectionQuery
		);

		const [createQuestionInRoot] = useMutation(createQuestionInRootQuery);
		const [getMessageBoardSectionByFriendlyUrlPath] = useManualQuery(
			getMessageBoardSectionByFriendlyUrlPathQuery,
			{
				variables: {
					friendlyUrlPath: sectionTitle,
					siteKey: context.siteKey,
				},
			}
		);

		useEffect(() => {
			setIsPostButtonDisable(
				hasEnoughContent || !headline || !tagsLoaded
			);
		}, [hasEnoughContent, headline, tagsLoaded]);

		useEffect(() => {
			getMessageBoardSectionByFriendlyUrlPath().then(({data}) => {
				const section = data.messageBoardSectionByFriendlyUrlPath;

				setSectionId((section && section.id) || +context.rootTopicId);

				if (section.parentMessageBoardSection) {
					setSections([
						{
							id: section.parentMessageBoardSection.id,
							title: section.parentMessageBoardSection.title,
						},
						...section.parentMessageBoardSection
							.messageBoardSections.items,
						...section.messageBoardSections.items,
					]);
				}
				else {
					setSections([
						{
							id: section.id,
							title: section.title,
						},
						...section.messageBoardSections.items,
					]);
				}
			});
		}, [
			context.rootTopicId,
			context.siteKey,
			sectionTitle,
			getMessageBoardSectionByFriendlyUrlPath,
		]);

		const processError = (error) => {
			if (error.message && error.message.includes('AssetTagException')) {
				error.message = lang.sub(
					Liferay.Language.get(
						'the-x-cannot-contain-the-following-invalid-characters-x'
					),
					[
						'Tag',
						' & \' @ \\\\ ] } : , = > / < \\n [ {  | + # ` ? \\" \\r ; / * ~',
					]
				);
			}

			setError(error);
		};

		const processResponse = (error) =>
			error ? processError(error.graphQLErrors[0]) : debounceCallback();

		const createQuestion = async () => {
			setIsPostButtonDisable(true);
			deleteCache();

			if (
				sectionTitle === context.rootTopicId &&
				+context.rootTopicId === 0
			) {
				createQuestionInRoot({
					fetchOptionsOverrides: getContextLink(sectionTitle),
					variables: {
						articleBody: editorRef.current.getContent(),
						headline,
						keywords: tags.map((tag) => tag.label),
						siteKey: context.siteKey,
					},
				})
					.then(({error}) => processResponse(error))
					.catch(processError);
			}
			else {
				createQuestionInASection({
					fetchOptionsOverrides: getContextLink(sectionTitle),
					variables: {
						articleBody: editorRef.current.getContent(),
						headline,
						keywords: tags.map((tag) => tag.label),
						messageBoardSectionId: sectionId,
					},
				})
					.then(({error}) => processResponse(error))
					.catch(processError);
			}

			setIsPostButtonDisable(false);
		};

		return (
			<section className="c-mt-5 questions-section questions-section-new">
				<div className="questions-container row">
					<div className="c-mx-auto col-xl-10">
						<h1>{Liferay.Language.get('new-question')}</h1>

						<ClayForm className="c-mt-5">
							<ClayForm.Group>
								<label htmlFor="basicInput">
									{Liferay.Language.get('title')}

									<span className="c-ml-2 reference-mark">
										<ClayIcon symbol="asterisk" />
									</span>
								</label>

								<ClayInput
									maxLength={HEADLINE_MAX_LENGTH}
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
										<div className="bd-highlight d-flex mb-3 text-secondary">
											<span className="bd-highlight d-flex justify-content-start mr-auto p-2 small">
												{Liferay.Language.get(
													'be-specific-and-imagine-you-are-asking-a-question-to-another-person'
												)}
											</span>

											<span className="bd-highlight p-2">{`${headline.length} / ${HEADLINE_MAX_LENGTH}`}</span>
										</div>
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

							{sections.length > 1 && (
								<ClayForm.Group className="c-mt-4">
									<label htmlFor="basicInput">
										{Liferay.Language.get('topic')}
									</label>

									<ClaySelect
										onChange={(event) =>
											setSectionId(event.target.value)
										}
									>
										{sections.map(({id, title}) => (
											<ClaySelect.Option
												key={id}
												label={title}
												selected={sectionId === id}
												value={id}
											/>
										))}
									</ClaySelect>
								</ClayForm.Group>
							)}

							<TagSelector
								className="c-mt-3"
								tags={tags}
								tagsChange={(tags) => setTags(tags)}
								tagsLoaded={setTagsLoaded}
							/>
						</ClayForm>

						<div className="c-mt-4 d-flex flex-column-reverse flex-sm-row">
							<ClayButton
								aria-label={
									context.trustedUser
										? Liferay.Language.get(
												'post-your-question'
										  )
										: Liferay.Language.get(
												'submit-for-publication'
										  )
								}
								className="c-mt-4 c-mt-sm-0"
								disabled={isPostButtonDisable}
								displayType="primary"
								onClick={() => {
									createQuestion();
								}}
							>
								{context.trustedUser
									? Liferay.Language.get('post-your-question')
									: Liferay.Language.get(
											'submit-for-workflow'
									  )}
							</ClayButton>

							<Link
								className="btn btn-secondary c-ml-sm-3"
								to={`/questions/${sectionTitle}`}
							>
								{Liferay.Language.get('cancel')}
							</Link>
						</div>
					</div>
				</div>

				<Alert info={error} />
			</section>
		);
	}
);

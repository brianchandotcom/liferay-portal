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
import ClayForm, {ClayInput, ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React, {useContext, useEffect, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import Alert from '../../components/Alert.es';
import Link from '../../components/Link.es';
import QuestionsEditor from '../../components/QuestionsEditor';
import TagSelector from '../../components/TagSelector.es';
import TextLengthValidation from '../../components/TextLengthValidation.es';
import {client, createQuestionQuery, getSections} from '../../utils/client.es';
import lang from '../../utils/lang.es';
import {
	getContextLink,
	historyPushWithSlug,
	slugToText,
	stripHTML,
	useDebounceCallback,
} from '../../utils/utils.es';

export default withRouter(
	({
		history,
		match: {
			params: {sectionTitle},
		},
	}) => {
		const [articleBody, setArticleBody] = useState('');
		const [headline, setHeadline] = useState('');
		const [error, setError] = useState({});
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

		const [createQuestion] = useMutation(createQuestionQuery, {
			context: getContextLink(sectionTitle),
			onCompleted() {
				client.resetStore();
				debounceCallback();
			},
		});

		useEffect(() => {
			getSections(slugToText(sectionTitle), context.siteKey).then(
				(section) => {
					setSectionId(section.id);
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
				}
			);
		}, [context.siteKey, sectionTitle]);

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

		return (
			<section className="c-mt-5 questions-section questions-section-new">
				<div className="questions-container">
					<div className="row">
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
										maxLength={75}
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
										onChange={(event) => {
											setArticleBody(
												event.editor.getData()
											);
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
									className="c-mt-4 c-mt-sm-0"
									disabled={
										!articleBody ||
										!headline ||
										!tagsLoaded ||
										stripHTML(articleBody).length < 15
									}
									displayType="primary"
									onClick={() => {
										createQuestion({
											variables: {
												articleBody,
												headline,
												keywords: tags.map(
													(tag) => tag.label
												),
												messageBoardSectionId: sectionId,
											},
										}).catch(processError);
									}}
								>
									{Liferay.Language.get('post-your-question')}
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
				</div>
				<Alert info={error} />
			</section>
		);
	}
);

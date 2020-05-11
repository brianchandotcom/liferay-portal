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
import ClayForm, {ClayInput, ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {Editor} from 'frontend-editor-ckeditor-web';
import React, {useContext, useEffect, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import Link from '../../components/Link.es';
import TagSelector from '../../components/TagSelector.es';
import useSection from '../../hooks/useSection.es';
import {createQuestion} from '../../utils/client.es';
import {
	getCKEditorConfig,
	historyPushWithSlug,
	onBeforeLoadCKEditor,
	slugToText,
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
		const [sectionId, setSectionId] = useState();
		const [sections, setSections] = useState([]);
		const [tags, setTags] = useState([]);
		const [tagsLoaded, setTagsLoaded] = useState(true);

		const context = useContext(AppContext);
		const historyPushParser = historyPushWithSlug(history.push);

		const section = useSection(slugToText(sectionTitle), context.siteKey);

		const [debounceCallback] = useDebounceCallback(
			() => historyPushParser(`/questions/${sectionTitle}/`),
			500
		);

		const submit = () =>
			createQuestion(
				articleBody,
				headline,
				sectionId || section.id,
				tags.map((tag) => tag.label)
			).then(() => debounceCallback());

		useEffect(() => {
			if (section && section.parentSection) {
				setSections([
					{
						id: section.parentSection.id,
						title: section.parentSection.title,
					},
					...section.parentSection.messageBoardSections.items,
				]);
			}
		}, [section, section.parentSection]);

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

									<Editor
										config={getCKEditorConfig()}
										onBeforeLoad={(editor) =>
											onBeforeLoadCKEditor(
												editor,
												context.imageBrowseURL
											)
										}
										onChange={(event) =>
											setArticleBody(
												event.editor.getData()
											)
										}
										required
									/>

									<ClayForm.FeedbackGroup>
										<ClayForm.FeedbackItem>
											<span className="small text-secondary">
												{Liferay.Language.get(
													'include-all-the-information-someone-would-need-to-answer-your-question'
												)}
											</span>
										</ClayForm.FeedbackItem>

										<ClayForm.Text>{''}</ClayForm.Text>
									</ClayForm.FeedbackGroup>
								</ClayForm.Group>

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
												selected={
													section && section.id === id
												}
												value={id}
											/>
										))}
									</ClaySelect>
								</ClayForm.Group>

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
										!articleBody || !headline || !tagsLoaded
									}
									displayType="primary"
									onClick={submit}
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
			</section>
		);
	}
);

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
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {Editor} from 'frontend-editor-ckeditor-web';
import React, {useContext, useState} from 'react';
import {Link, withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import TagSelector from '../../components/TagSelector.es';
import {createQuestion} from '../../utils/client.es';
import {
	getCKEditorConfig,
	onBeforeLoadCKEditor,
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
		const [tags, setTags] = useState([]);

		const context = useContext(AppContext);

		const [debounceCallback] = useDebounceCallback(
			() => history.push(`/questions/${sectionTitle}/`),
			500
		);

		const submit = () =>
			createQuestion(
				articleBody,
				headline,
				context.section.id,
				tags.map(tag => tag.value)
			).then(() => debounceCallback());

		return (
			<section className="c-mt-5 c-mx-auto col-xl-10">
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
							onChange={event => setHeadline(event.target.value)}
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
							onBeforeLoad={onBeforeLoadCKEditor}
							onChange={event =>
								setArticleBody(event.editor.getData())
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
							{Liferay.Language.get('tags')}
						</label>

						<TagSelector
							className="c-mt-3"
							tagsChange={tags => setTags(tags)}
						/>

						<ClayForm.FeedbackGroup>
							<ClayForm.FeedbackItem>
								<span className="small text-secondary">
									{Liferay.Language.get(
										'add-up-to-5-tags-to-describe-what-your-question-is-about'
									)}
								</span>
							</ClayForm.FeedbackItem>
						</ClayForm.FeedbackGroup>
					</ClayForm.Group>
				</ClayForm>

				<div className="c-mt-4 d-flex flex-column-reverse flex-sm-row">
					<ClayButton
						className="c-mt-4 c-mt-sm-0"
						disabled={!articleBody || !headline}
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
			</section>
		);
	}
);

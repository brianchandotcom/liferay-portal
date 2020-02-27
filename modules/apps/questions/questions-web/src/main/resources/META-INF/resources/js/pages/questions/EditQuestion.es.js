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
import React, {useState} from 'react';
import {Link, withRouter} from 'react-router-dom';

import {getThreadContent, updateThread} from '../../utils/client.es';
import {getCKEditorConfig, onBeforeLoadCKEditor} from '../../utils/utils.es';

export default withRouter(
	({
		history,
		match: {
			params: {questionId},
		},
	}) => {
		const [articleBody, setArticleBody] = useState('');
		const [headline, setHeadline] = useState('');
		const [tags, setTags] = useState('');

		const loadThread = () =>
			getThreadContent(questionId).then(
				({articleBody, headline, tags}) => {
					setArticleBody(articleBody);
					setHeadline(headline);
					if (tags) {
						setTags(tags.toString());
					}
				}
			);

		const submit = () =>
			updateThread(articleBody, headline, tags, questionId).then(() =>
				history.goBack()
			);

		return (
			<section className="c-mt-5 c-mx-auto col-xl-10">
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
							data={articleBody}
							onBeforeLoad={onBeforeLoadCKEditor}
							onChange={event =>
								setArticleBody(event.editor.getData())
							}
							onInstanceReady={loadThread}
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

						<ClayInput
							onChange={event => setTags(event.target.value)}
							placeholder={Liferay.Language.get('add-your-tags')}
							type="text"
							value={tags}
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
						{Liferay.Language.get('update-your-question')}
					</ClayButton>

					<Link
						className="btn btn-secondary c-ml-sm-3"
						to={`/questions/`}
					>
						{Liferay.Language.get('cancel')}
					</Link>
				</div>
			</section>
		);
	}
);

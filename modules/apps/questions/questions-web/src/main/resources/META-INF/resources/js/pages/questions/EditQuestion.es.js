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

import ClayForm, {ClayInput} from '@clayui/form';
import {Editor} from 'frontend-editor-ckeditor-web';
import React, {useEffect, useState} from 'react';
import {Link, withRouter} from 'react-router-dom';

import {getThreadContent, updateThread} from '../../utils/client.es';
import {getCKEditorConfig} from '../../utils/utils.es';

export default withRouter(
	({
		history,
		match: {
			params: {questionId}
		}
	}) => {
		const [articleBody, setArticleBody] = useState('');
		const [headline, setHeadline] = useState('');
		const [tags, setTags] = useState('');

		useEffect(() => {
			getThreadContent(questionId).then(
				({articleBody, headline, tags}) => {
					setArticleBody(articleBody);
					setHeadline(headline);
					setTags(tags.toString());
				}
			);
		}, [questionId]);

		const submit = () =>
			updateThread(articleBody, headline, tags, questionId).then(() =>
				history.goBack()
			);

		return (
			<>
				<h1>{Liferay.Language.get('edit-question')}</h1>

				<ClayForm>
					<ClayForm.Group className="form-group-sm">
						<label htmlFor="basicInput">
							{Liferay.Language.get('title')}
						</label>
						<ClayInput
							onChange={event => setHeadline(event.target.value)}
							placeholder={Liferay.Language.get(
								'what-is-your-programming-question'
							)}
							required
							type="text"
							value={headline}
						/>
						<ClayForm.FeedbackGroup>
							<ClayForm.FeedbackItem>
								{Liferay.Language.get(
									'be-specific-and-imagine-you-are-asking-a-question-to-another-developer'
								)}
							</ClayForm.FeedbackItem>
						</ClayForm.FeedbackGroup>
					</ClayForm.Group>
					<ClayForm.Group className="form-group-sm">
						<label htmlFor="basicInput">
							{Liferay.Language.get('body')}
						</label>

						<Editor
							config={getCKEditorConfig()}
							onBeforeLoad={CKEDITOR => {
								CKEDITOR.disableAutoInline = true;
							}}
							onChange={event =>
								setArticleBody(event.editor.getData())
							}
							required
							value={articleBody}
						/>

						<ClayForm.FeedbackGroup>
							<ClayForm.FeedbackItem>
								{Liferay.Language.get(
									'include-all-the-information-someone-would-need-to-answer-your-question'
								)}
							</ClayForm.FeedbackItem>
							<ClayForm.Text>{''}</ClayForm.Text>
						</ClayForm.FeedbackGroup>
					</ClayForm.Group>
					<ClayForm.Group className="form-group-sm">
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
								{Liferay.Language.get(
									'add-up-to-5-tags-to-describe-what-your-question-is-about'
								)}
							</ClayForm.FeedbackItem>
						</ClayForm.FeedbackGroup>
					</ClayForm.Group>
				</ClayForm>

				<div className="sheet-footer">
					<div className="btn-group-item">
						<div className="btn-group-item">
							<button
								className="btn btn-primary"
								disabled={!articleBody || !headline}
								onClick={submit}
							>
								{Liferay.Language.get('update-your-question')}
							</button>
						</div>
						<div className="btn-group-item">
							<Link to={'/questions/' + questionId}>
								<button className="btn btn-secondary">
									{Liferay.Language.get('cancel')}
								</button>
							</Link>
						</div>
					</div>
				</div>
			</>
		);
	}
);

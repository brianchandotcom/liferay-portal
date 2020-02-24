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

import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {Editor} from 'frontend-editor-ckeditor-web';
import React, {useState} from 'react';
import {withRouter} from 'react-router-dom';

import {getMessage, updateMessage} from '../../utils/client.es';
import {getCKEditorConfig, onBeforeLoadCKEditor} from '../../utils/utils.es';

export default withRouter(
	({
		history,
		match: {
			params: {answerId}
		}
	}) => {
		const [articleBody, setArticleBody] = useState('');

		const loadMessage = () =>
			getMessage(answerId).then(({articleBody}) =>
				setArticleBody(articleBody)
			);

		const submit = () => {
			updateMessage(articleBody, answerId).then(() => history.goBack());
		};

		return (
			<>
				<h1>{Liferay.Language.get('edit-answer')}</h1>

				<ClayForm>
					<ClayForm.Group className="form-group-sm">
						<label htmlFor="basicInput">
							{Liferay.Language.get('answer')}
							<span className="reference-mark">
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
							onInstanceReady={loadMessage}
							required
							type="text"
						/>
						<ClayForm.FeedbackGroup>
							<ClayForm.FeedbackItem>
								{Liferay.Language.get(
									'include-all-the-information-someone-would-need-to-answer-your-question'
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
								disabled={!articleBody}
								onClick={submit}
							>
								{Liferay.Language.get('update-your-answer')}
							</button>
						</div>
						<div className="btn-group-item">
							<button
								className="btn btn-secondary"
								onClick={() => history.goBack()}
							>
								{Liferay.Language.get('cancel')}
							</button>
						</div>
					</div>
				</div>
			</>
		);
	}
);

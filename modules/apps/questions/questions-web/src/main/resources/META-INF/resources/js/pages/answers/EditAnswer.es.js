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
import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {Editor} from 'frontend-editor-ckeditor-web';
import React, {useContext, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import {getMessage, updateMessage} from '../../utils/client.es';
import {getCKEditorConfig, onBeforeLoadCKEditor} from '../../utils/utils.es';

export default withRouter(
	({
		history,
		match: {
			params: {answerId},
		},
	}) => {
		const context = useContext(AppContext);

		const [articleBody, setArticleBody] = useState('');
		const [id, setId] = useState();

		const loadMessage = () =>
			getMessage(answerId, context.siteKey).then(({articleBody, id}) => {
				setArticleBody(articleBody);
				setId(id);
			});

		const submit = () => {
			updateMessage(articleBody, id).then(() => history.goBack());
		};

		return (
			<section className="c-mt-5 questions-section questions-sections-answer">
				<div className="questions-container">
					<div className="row">
						<div className="c-mx-auto col-xl-10">
							<h1>{Liferay.Language.get('edit-answer')}</h1>

							<ClayForm>
								<ClayForm.Group className="c-mt-4">
									<label htmlFor="basicInput">
										{Liferay.Language.get('answer')}

										<span className="c-ml-2 reference-mark">
											<ClayIcon symbol="asterisk" />
										</span>
									</label>

									<Editor
										config={getCKEditorConfig()}
										data={articleBody}
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
										onInstanceReady={loadMessage}
										required
										type="text"
									/>

									<ClayForm.FeedbackGroup>
										<ClayForm.FeedbackItem>
											<span className="small text-secondary">
												{Liferay.Language.get(
													'include-all-the-information-someone-would-need-to-answer-your-question'
												)}
											</span>
										</ClayForm.FeedbackItem>
									</ClayForm.FeedbackGroup>
								</ClayForm.Group>
							</ClayForm>

							<div className="c-mt-4 d-flex flex-column-reverse flex-sm-row">
								<ClayButton
									className="c-mt-4 c-mt-sm-0"
									disabled={!articleBody}
									displayType="primary"
									onClick={submit}
								>
									{Liferay.Language.get('update-your-answer')}
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
				</div>
			</section>
		);
	}
);

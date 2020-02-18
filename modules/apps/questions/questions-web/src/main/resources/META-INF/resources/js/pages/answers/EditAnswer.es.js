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
import React, {useEffect, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {getMessage, updateMessage} from '../../utils/client.es';

export default withRouter(
	({
		history,
		match: {
			params: {answerId}
		}
	}) => {
		const [articleBody, setArticleBody] = useState('');

		useEffect(() => {
			getMessage(answerId).then(({articleBody}) =>
				setArticleBody(articleBody)
			);
		}, [answerId]);

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
						</label>
						<ClayInput
							onChange={event =>
								setArticleBody(event.target.value)
							}
							placeholder={Liferay.Language.get(
								'what-is-your-question'
							)}
							required
							type="text"
							value={articleBody}
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

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
import React, {forwardRef, useEffect, useState} from 'react';

import {stripHTML} from '../utils/utils.es';
import QuestionsEditor from './QuestionsEditor';
import TextLengthValidation from './TextLengthValidation.es';

export default forwardRef(
	(
		{
			additionalInformation,
			label,
			onContentLengthValid,
			question,
			...otherProps
		},
		ref
	) => {
		const MIN_CHAR_NUMBER = 15;
		const [content, setContent] = useState('');

		ref.current = {
			clearContent: () => setContent(''),
			getContent: () => content,
			setContent: (content) => setContent(content),
		};

		useEffect(() => {
			onContentLengthValid(stripHTML(content).length < MIN_CHAR_NUMBER);
		}, [content, onContentLengthValid]);

		return (
			<>
				<ClayForm>
					<ClayForm.Group className="form-group-sm">
						<label htmlFor="basicInput">
							{label}

							<span className="c-ml-2 reference-mark">
								<ClayIcon symbol="asterisk" />
							</span>
						</label>

						<div className="c-mt-2">
							{question && question.locked && (
								<div className="question-locked-text">
									<span>
										<ClayIcon symbol="lock" />
									</span>
									{Liferay.Language.get(
										'this-question-is-closed-new-answers-and-comments-are-disabled'
									)}
								</div>
							)}
							<QuestionsEditor
								contents={content}
								cssClass={
									question && question.locked
										? 'question-locked'
										: ''
								}
								editorConfig={{
									readOnly: question && question.locked,
								}}
								onChange={(event) => {
									setContent(event.editor.getData());
								}}
								{...otherProps}
							/>
						</div>

						<ClayForm.FeedbackGroup>
							<ClayForm.FeedbackItem>
								{additionalInformation && (
									<span className="small text-secondary">
										{additionalInformation}
									</span>
								)}

								<TextLengthValidation text={content} />
							</ClayForm.FeedbackItem>
						</ClayForm.FeedbackGroup>
					</ClayForm.Group>
				</ClayForm>
			</>
		);
	}
);

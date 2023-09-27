/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
								<ClayIcon
									aria-label={Liferay.Language.get(
										'asterisk'
									)}
									symbol="asterisk"
								/>
							</span>
						</label>

						<div className="c-mt-2">
							{question && question.locked && (
								<div className="question-locked-text">
									<span>
										<ClayIcon
											aria-label={Liferay.Language.get(
												'lock'
											)}
											symbol="lock"
										/>
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
								onChange={(event) =>
									setContent(event.editor.getData())
								}
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

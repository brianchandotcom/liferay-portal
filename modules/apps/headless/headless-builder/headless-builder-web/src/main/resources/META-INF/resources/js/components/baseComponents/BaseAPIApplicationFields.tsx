/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text} from '@clayui/core';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';
import React, {Dispatch, SetStateAction, useState} from 'react';

import {limitStringInputLengh, makeURLPathString} from '../utils/string';

interface BaseAPIApplicationFieldsProps {
	basePath: string;
	data: Partial<APIApplicationUIData>;
	disableURLAutoFill?: boolean;
	displayError: ApplicationDataError;
	setData: Dispatch<SetStateAction<APIApplicationUIData>>;
}

export default function BaseAPIApplicationFields({
	basePath,
	data,
	disableURLAutoFill,
	displayError,
	setData,
}: BaseAPIApplicationFieldsProps) {
	const [userEditedURL, setUserEditedURL] = useState(
		disableURLAutoFill ?? false
	);

	const [baseURLContent, setBaseURLContent] = useState({
		errorMessage: Liferay.Language.get(
			'please-enter-a-title-so-we-can-create-an-url'
		),
		placeholder: Liferay.Language.get('automated-url'),
	});

	return (
		<>
			<ClayForm.Group
				className={classNames({
					'has-error': displayError.title,
				})}
			>
				<label>
					{Liferay.Language.get('title')}

					<span className="ml-1 reference-mark text-warning">
						<ClayIcon symbol="asterisk" />
					</span>
				</label>

				<ClayInput
					onChange={({target: {value}}) =>
						setData((previousData) => ({
							...previousData,
							title: value,
							...(!userEditedURL && {
								baseURL: makeURLPathString(value),
							}),
						}))
					}
					placeholder={Liferay.Language.get('enter-title')}
					value={data.title}
				/>

				<div className="feedback-container">
					<ClayForm.FeedbackGroup>
						{displayError.title && (
							<ClayForm.FeedbackItem className="mt-2">
								<ClayForm.FeedbackIndicator symbol="exclamation-full" />

								{Liferay.Language.get(
									'please-enter-an-api-title-to-continue'
								)}
							</ClayForm.FeedbackItem>
						)}
					</ClayForm.FeedbackGroup>
				</div>
			</ClayForm.Group>

			<ClayForm.Group
				className={classNames({
					'has-error': displayError.baseURL,
				})}
			>
				<label>
					{Liferay.Language.get('url')}

					<span className="ml-1 reference-mark text-warning">
						<ClayIcon symbol="asterisk" />
					</span>

					<ClayTooltipProvider>
						<span
							data-tooltip-align="top"
							title={Liferay.Language.get(
								'there-is-a-limit-of-255-characters-and-must-only-contain-numbers-letters-or-dashes'
							)}
						>
							&nbsp;
							<ClayIcon symbol="question-circle-full" />
						</span>
					</ClayTooltipProvider>
				</label>

				<br />

				<Text as="p" id="hostTextPreview" size={2} weight="lighter">
					{`${window.location.origin}${basePath}`}
				</Text>

				<ClayInput
					autoComplete="off"
					id="modalURLField"
					onChange={({target: {value}}) => {
						setUserEditedURL(true);
						setBaseURLContent({
							errorMessage: Liferay.Language.get(
								'please-enter-a-valid-url'
							),
							placeholder: '',
						});
						setData((previousData) => ({
							...previousData,
							baseURL: limitStringInputLengh(
								makeURLPathString(value),
								255
							),
						}));
					}}
					placeholder={baseURLContent.placeholder}
					value={data.baseURL}
				/>

				<ClayForm.FeedbackGroup>
					{displayError.baseURL ? (
						<ClayForm.FeedbackItem className="mt-2">
							<ClayForm.FeedbackIndicator symbol="exclamation-full" />

							{baseURLContent.errorMessage}
						</ClayForm.FeedbackItem>
					) : (
						<Text size={3} weight="lighter">
							{Liferay.Language.get('the-url-can-be-modified')}
						</Text>
					)}
				</ClayForm.FeedbackGroup>
			</ClayForm.Group>

			<ClayForm.Group>
				<label>{Liferay.Language.get('description')}</label>

				<textarea
					className="form-control"
					onChange={({target: {value}}) =>
						setData((previousData) => ({
							...previousData,
							description: value,
						}))
					}
					placeholder={Liferay.Language.get(
						'add-a-short-description-that-describes-this-api'
					)}
					value={data.description}
				/>
			</ClayForm.Group>
		</>
	);
}

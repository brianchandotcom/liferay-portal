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

import {Text} from '@clayui/core';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';
import React, {Dispatch, SetStateAction, useState} from 'react';

import {limitStringInputLengh, makeURLPathString} from '../utils/string';

type Data = {
	baseURL: string;
	description: string;
	title: string;
	version: string;
};

type DataError = {
	baseURL: boolean;
	title: boolean;
};

interface BaseAPIApplicationFieldsProps {
	data: Partial<Data>;
	displayError: DataError;
	setData: Dispatch<SetStateAction<Partial<Data>>>;
	urlAutoFill?: boolean;
}

export default function BaseAPIApplicationFields({
	data,
	displayError,
	setData,
	urlAutoFill,
}: BaseAPIApplicationFieldsProps) {
	const [userEditedURL, setUserEditedURL] = useState(false);

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
							...(urlAutoFill &&
								!userEditedURL && {
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
					{`${window.location.origin}/o/`}
				</Text>

				<ClayInput
					id="modalURLField"
					onChange={({target: {value}}) => {
						setUserEditedURL(true);
						setData((previousData) => ({
							...previousData,
							baseURL: limitStringInputLengh(
								makeURLPathString(value),
								255
							),
						}));
					}}
					placeholder={Liferay.Language.get('automated-url')}
					value={data.baseURL}
				/>

				<ClayForm.FeedbackGroup>
					{displayError.baseURL ? (
						<ClayForm.FeedbackItem className="mt-2">
							<ClayForm.FeedbackIndicator symbol="exclamation-full" />

							{Liferay.Language.get(
								'please-enter-a-title-so-we-can-create-an-url'
							)}
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

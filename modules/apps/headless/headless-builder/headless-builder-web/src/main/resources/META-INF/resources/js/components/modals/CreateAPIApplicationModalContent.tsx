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
import {Text} from '@clayui/core';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal from '@clayui/modal';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';
import {fetch, openToast} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

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

interface HandleCreateInModal {
	apiApplicationsURLPath: string;
	closeModal: voidReturn;
	loadData: voidReturn;
}

const headers = new Headers({
	'Accept': 'application/json',
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
	'Content-Type': 'application/json',
});

export function CreateAPIApplicationModalContent({
	apiApplicationsURLPath,
	closeModal,
	loadData,
}: HandleCreateInModal) {
	const [data, setData] = useState<Partial<Data>>({});
	const [displayError, setDisplayError] = useState<DataError>({
		baseURL: false,
		title: false,
	});
	const [userEditedURL, setUserEditedURL] = useState(false);

	useEffect(() => {
		for (const key in data) {
			if (data[key as keyof Data] !== '') {
				setDisplayError((previousErrors) => ({
					...previousErrors,
					[key]: false,
				}));
			}
		}
	}, [data]);

	async function postData() {
		fetch(apiApplicationsURLPath, {
			body: JSON.stringify({
				...data,
				applicationStatus: {key: 'unpublished'},
				version: '1.0',
			}),
			headers,
			method: 'POST',
		})
			.then(({ok}) => {
				if (ok) {
					closeModal();
					loadData();
					openToast({
						message: Liferay.Language.get(
							'your-request-completed-successfully'
						),
						type: 'success',
					});
				}
				else {
					throw new Error();
				}
			})
			.catch(() => {
				openToast({
					message: Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
					type: 'danger',
				});
			});
	}

	function validateData() {
		let isDataValid = true;
		const mandatoryFields = ['baseURL', 'title'];

		if (!Object.keys(data).length) {
			const errors = mandatoryFields.reduce(
				(errors, field) => ({...errors, [field]: true}),
				{}
			);
			setDisplayError(errors as DataError);

			isDataValid = false;
		}
		else {
			mandatoryFields.forEach((field) => {
				if (data[field as keyof Data]) {
					setDisplayError((previousErrors) => ({
						...previousErrors,
						[field]: false,
					}));
				}
				else {
					setDisplayError((previousErrors) => ({
						...previousErrors,
						[field]: true,
					}));
					isDataValid = false;
				}
			});
		}

		return isDataValid;
	}

	const handleCreate = () => {
		const isDataValid = validateData();

		if (isDataValid) {
			postData();
		}
		else {
			return;
		}
	};

	return (
		<>
			<ClayModal.Header>
				{Liferay.Language.get('new-api-application')}
			</ClayModal.Header>

			<div className="modal-body">
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
								{Liferay.Language.get(
									'the-url-can-be-modified'
								)}
							</Text>
						)}
					</ClayForm.FeedbackGroup>
				</ClayForm.Group>

				<ClayForm.Group>
					<label>{Liferay.Language.get('description')}</label>

					<textarea
						className="form-control"
						onBlur={({target: {value}}) =>
							setData((previousData) => ({
								...previousData,
								description: value,
							}))
						}
						placeholder={Liferay.Language.get(
							'add-a-short-description-that-describes-this-api'
						)}
					></textarea>
				</ClayForm.Group>
			</div>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							id="modalCancelButton"
							onClick={closeModal}
							type="button"
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							displayType="primary"
							id="modalCreateButton"
							onClick={handleCreate}
							type="button"
						>
							{Liferay.Language.get('create')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
}

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text} from '@clayui/core';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import React, {Dispatch, SetStateAction, useEffect, useState} from 'react';

import {Select} from '../fieldComponents/Select';
import {fetchJSON} from '../utils/fetchUtil';
import {
	makeURLPathStringWithForwardSlashes,
	removeLeadingForwardSlash,
} from '../utils/string';

type DataError = {
	description: boolean;
	path: boolean;
	scope: boolean;
};

interface BaseAPIApplicationFieldsProps {
	apiApplicationBaseURL: string;
	basePath: string;
	data: Partial<APIEndpointUIData>;
	displayError: DataError;
	editMode?: boolean;
	setData: Dispatch<SetStateAction<APIEndpointUIData>>;
}

export default function BaseAPIEndpointFields({
	apiApplicationBaseURL,
	basePath,
	data,
	displayError,
	editMode,
	setData,
}: BaseAPIApplicationFieldsProps) {
	const [scopeOptions, setScopeOptions] = useState<SelectOption[]>([]);
	const [selectedScope, setSelectedScope] = useState<SelectOption>();

	useEffect(() => {
		fetchJSON<FetchedListType>({
			input:
				'/o/headless-admin-list-type/v1.0/list-type-definitions/by-external-reference-code/SCOPE_PICKLIST',
		}).then((response) => {
			const options = response.listTypeEntries
				? response.listTypeEntries.map((entry) => ({
						label:
							entry.key === 'group'
								? Liferay.Language.get('site')
								: Liferay.Language.get('company'),
						value: entry.key,
				  }))
				: [];

			if (options.length) {
				setScopeOptions(options);
			}
		});
	}, []);

	useEffect(() => {
		if (data.scope?.key && scopeOptions.length) {
			setSelectedScope(
				scopeOptions.find((option) => option.value === data.scope?.key)
			);
		}
	}, [data, scopeOptions]);

	const handleSelectScope = (value: string) => {
		setData((previousValue) => ({
			...previousValue,
			scope: {key: value},
		}));

		setSelectedScope(scopeOptions.find((option) => option.value === value));
	};

	const endpointDescriptionLabel = Liferay.Language.get(
		'add-a-short-description-that-describes-this-endpoint'
	);

	const endpointPathHostTextPreview =
		selectedScope?.value === 'group'
			? `${window.location.origin}${basePath}${apiApplicationBaseURL}/sites/{siteId}`
			: `${window.location.origin}${basePath}${apiApplicationBaseURL}`;
	const endpointPathLabel = Liferay.Language.get('enter-path');

	return (
		<ClayForm>
			<ClayForm.Group
				className={classNames({
					'has-error': displayError.scope,
				})}
			>
				<label htmlFor="selectTrigger">
					{Liferay.Language.get('scope')}

					<span className="ml-1 reference-mark text-warning">
						<ClayIcon symbol="asterisk" />
					</span>
				</label>

				<Select
					cleanUp={() =>
						setData((previousValue) => {
							previousValue.scope = {key: '', name: ''};

							return {...previousValue};
						})
					}
					disabled={false}
					dropDownSearchAriaLabel={Liferay.Language.get(
						'search-for-an-object-definition-or-use-the-arrow-keys-to-navigate-and-select-an-object-definition-from-the-list'
					)}
					invalid={displayError.scope}
					onClick={handleSelectScope}
					options={scopeOptions}
					placeholder={Liferay.Language.get('select-scope')}
					required
					searchable={false}
					selectedOption={selectedScope}
					triggerAriaLabel={
						!selectedScope
							? Liferay.Language.get(
									Liferay.Language.get('select-scope')
							  )
							: sub(
									Liferay.Language.get('scope-x-is-selected'),
									selectedScope.label
							  )
					}
				/>

				<div className="feedback-container">
					<ClayForm.FeedbackGroup>
						{displayError.scope && (
							<ClayForm.FeedbackItem className="mt-2">
								<ClayForm.FeedbackIndicator symbol="exclamation-full" />

								<span id="selectScopeErrorMessage">
									{Liferay.Language.get(
										'please-select-a-scope'
									)}
								</span>
							</ClayForm.FeedbackItem>
						)}
					</ClayForm.FeedbackGroup>
				</div>
			</ClayForm.Group>

			<ClayForm.Group>
				<label htmlFor="endpointPathField">
					{Liferay.Language.get('path')}

					<span className="ml-1 reference-mark text-warning">
						<ClayIcon symbol="asterisk" />
					</span>
				</label>

				<Text as="p" id="hostTextPreview" size={2} weight="lighter">
					{endpointPathHostTextPreview}
				</Text>

				<ClayInput.Group
					className={classNames({
						'has-error': displayError.path,
					})}
				>
					<ClayInput.GroupItem prepend shrink>
						<ClayInput.GroupText>/</ClayInput.GroupText>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem append>
						<ClayInput
							aria-label={endpointPathLabel}
							id="endpointPathField"
							onChange={({target: {value}}) =>
								setData((previousData) => ({
									...previousData,
									path: makeURLPathStringWithForwardSlashes(
										value
									),
								}))
							}
							placeholder={endpointPathLabel}
							type="text"
							value={
								data.path
									? removeLeadingForwardSlash(data.path)
									: ''
							}
						/>
					</ClayInput.GroupItem>
				</ClayInput.Group>

				{(editMode ?? false) && (
					<ClayForm.FeedbackGroup>
						<Text size={3} weight="lighter">
							{Liferay.Language.get(
								'the-url-can-be-modified-to-ensure-uniqueness'
							)}
						</Text>
					</ClayForm.FeedbackGroup>
				)}
			</ClayForm.Group>

			<ClayForm.Group
				className={classNames({
					'has-error': displayError.description,
				})}
			>
				<label htmlFor="endpointDescriptionField">
					{Liferay.Language.get('description')}

					<span className="ml-1 reference-mark text-warning">
						<ClayIcon symbol="asterisk" />
					</span>
				</label>

				<textarea
					aria-label={endpointDescriptionLabel}
					autoComplete="off"
					className="form-control"
					id="endpointDescriptionField"
					onChange={({target: {value}}) =>
						setData((previousData) => ({
							...previousData,
							description: value,
						}))
					}
					placeholder={endpointDescriptionLabel}
					value={data.description}
				/>
			</ClayForm.Group>

			<div aria-live="assertive" className="sr-only">
				{(displayError.scope ||
					displayError.path ||
					displayError.description) && (
					<span>
						{Liferay.Language.get(
							'there-are-errors-on-the-form-please-check-if-any-mandatory-fields-have-not-been-completed'
						)}
					</span>
				)}
			</div>
		</ClayForm>
	);
}

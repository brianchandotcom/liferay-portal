/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import React, {Dispatch, SetStateAction, useEffect, useState} from 'react';

import {Select} from '../fieldComponents/Select';
import {getAllItems} from '../utils/fetchUtil';

interface BaseAPIApplicationFieldsProps {
	data: Partial<APISchemaUIData>;
	disableObjectSelect?: boolean;
	displayError: SchemaDataError;
	setData: Dispatch<SetStateAction<APISchemaUIData>>;
}

export default function BaseAPISchemaFields({
	data,
	disableObjectSelect,
	displayError,
	setData,
}: BaseAPIApplicationFieldsProps) {
	const [objectDefinitionsOptions, setObjectDefinitionsOptions] = useState<
		SelectOption[]
	>([]);
	const [selectedObjectDefinition, setSelectedObjectDefinition] = useState<
		SelectOption
	>();

	useEffect(() => {
		getAllItems<ObjectDefinition>({
			url: '/o/object-admin/v1.0/object-definitions',
		}).then((result) => {
			const options = result
				? result.map((objectDefinition) => ({
						label: objectDefinition.name,
						value: objectDefinition.externalReferenceCode,
				  }))
				: [];

			if (options.length) {
				setObjectDefinitionsOptions(options);
			}
		});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		if (data.mainObjectDefinitionERC && objectDefinitionsOptions.length) {
			setSelectedObjectDefinition(
				objectDefinitionsOptions.find(
					(option) => option.value === data.mainObjectDefinitionERC
				)
			);
		}
	}, [data, objectDefinitionsOptions]);

	const handleSelectObject = (value: string) => {
		setData((previousValue) => ({
			...previousValue,
			mainObjectDefinitionERC: value,
		}));

		setSelectedObjectDefinition(
			objectDefinitionsOptions.find((option) => option.value === value)
		);
	};

	const schemaDescriptionLabel = Liferay.Language.get(
		'add-a-short-description-that-describes-this-schema'
	);

	return (
		<ClayForm>
			<ClayForm.Group
				className={classNames({
					'has-error': displayError.name,
				})}
			>
				<label htmlFor="schemaNameField">
					{Liferay.Language.get('name')}

					<span className="ml-1 reference-mark text-warning">
						<ClayIcon symbol="asterisk" />
					</span>
				</label>

				<ClayInput
					aria-invalid={displayError.name}
					aria-required="true"
					autoComplete="off"
					id="schemaNameField"
					onChange={({target: {value}}) =>
						setData((previousData) => ({
							...previousData,
							name: value,
						}))
					}
					onKeyPress={(event) =>
						event.key === 'Enter' && event.preventDefault()
					}
					placeholder={Liferay.Language.get('enter-name')}
					value={data.name}
				/>

				<div className="feedback-container">
					<ClayForm.FeedbackGroup>
						{displayError.name && (
							<ClayForm.FeedbackItem className="mt-2">
								<ClayForm.FeedbackIndicator symbol="exclamation-full" />

								<span id="inputNameErrorMessage">
									{Liferay.Language.get(
										'please-enter-a-schema-name'
									)}
								</span>
							</ClayForm.FeedbackItem>
						)}
					</ClayForm.FeedbackGroup>
				</div>
			</ClayForm.Group>

			<ClayForm.Group
				className={classNames({
					'has-error': displayError.description,
				})}
			>
				<label htmlFor="schemaDescriptionField">
					{Liferay.Language.get('description')}
				</label>

				<textarea
					aria-label={schemaDescriptionLabel}
					autoComplete="off"
					className="form-control"
					id="schemaDescriptionField"
					onChange={({target: {value}}) =>
						setData((previousData) => ({
							...previousData,
							description: value,
						}))
					}
					placeholder={schemaDescriptionLabel}
					value={data.description}
				/>
			</ClayForm.Group>

			<ClayForm.Group
				className={classNames({
					'has-error': displayError.mainObjectDefinitionERC,
				})}
			>
				<label htmlFor="selectTrigger">
					{Liferay.Language.get('object')}

					<span className="ml-1 reference-mark text-warning">
						<ClayIcon symbol="asterisk" />
					</span>
				</label>

				<Select
					cleanUp={() =>
						setData((previousValue) => {
							previousValue.mainObjectDefinitionERC = '';

							return {...previousValue};
						})
					}
					disabled={disableObjectSelect}
					dropDownSearchAriaLabel={Liferay.Language.get(
						'search-for-an-object-definition-or-use-the-arrow-keys-to-navigate-and-select-an-object-definition-from-the-list'
					)}
					invalid={displayError.mainObjectDefinitionERC}
					onClick={handleSelectObject}
					options={objectDefinitionsOptions}
					placeholder={Liferay.Language.get(
						'select-an-object-definition'
					)}
					required
					selectedOption={selectedObjectDefinition}
					triggerAriaLabel={
						!selectedObjectDefinition
							? Liferay.Language.get(
									Liferay.Language.get(
										'select-an-object-definition'
									)
							  )
							: sub(
									Liferay.Language.get(
										'object-definition-x-is-selected'
									),
									selectedObjectDefinition.label
							  )
					}
				/>

				<div className="feedback-container">
					<ClayForm.FeedbackGroup>
						{displayError.mainObjectDefinitionERC && (
							<ClayForm.FeedbackItem className="mt-2">
								<ClayForm.FeedbackIndicator symbol="exclamation-full" />

								<span id="selectObjectErrorMessage">
									{Liferay.Language.get(
										'please-select-an-object'
									)}
								</span>
							</ClayForm.FeedbackItem>
						)}
					</ClayForm.FeedbackGroup>
				</div>
			</ClayForm.Group>

			<div aria-live="assertive" className="sr-only">
				{(displayError.name ||
					displayError.mainObjectDefinitionERC) && (
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

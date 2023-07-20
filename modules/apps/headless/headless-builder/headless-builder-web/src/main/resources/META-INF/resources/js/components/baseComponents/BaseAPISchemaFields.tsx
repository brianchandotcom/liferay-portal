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
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {Dispatch, SetStateAction, useEffect, useState} from 'react';

import {Select} from '../fieldComponents/Select';
import {getItems} from '../utils/fetchUtil';

type DataError = {
	description: boolean;
	mainObjectDefinitionERC: boolean;
	name: boolean;
};

interface BaseAPIApplicationFieldsProps {
	data: Partial<APIApplicationSchemaItem>;
	displayError: DataError;
	setData: Dispatch<SetStateAction<Partial<APIApplicationSchemaItem>>>;
}

export default function BaseAPISchemaFields({
	data,
	displayError,
	setData,
}: BaseAPIApplicationFieldsProps) {
	const [objectDefinitions, setObjectDefinitions] = useState<
		ObjectDefinition[] | undefined
	>();

	useEffect(() => {
		getItems<ObjectDefinition>({
			url: '/o/object-admin/v1.0/object-definitions',
		}).then((result) => {
			setObjectDefinitions(result);
		});
	}, []);

	const handleSelectObject = (value: string) => {
		setData((previousValue) => ({
			...previousValue,
			mainObjectDefinitionERC: value,
		}));
	};

	return (
		<>
			<ClayForm.Group
				className={classNames({
					'has-error': displayError.name,
				})}
			>
				<label>
					{Liferay.Language.get('name')}

					<span className="ml-1 reference-mark text-warning">
						<ClayIcon symbol="asterisk" />
					</span>
				</label>

				<ClayInput
					onChange={({target: {value}}) =>
						setData((previousData) => ({
							...previousData,
							name: value,
						}))
					}
					placeholder={Liferay.Language.get('enter-name')}
					value={data.name}
				/>

				<div className="feedback-container">
					<ClayForm.FeedbackGroup>
						{displayError.name && (
							<ClayForm.FeedbackItem className="mt-2">
								<ClayForm.FeedbackIndicator symbol="exclamation-full" />

								{Liferay.Language.get(
									'please-enter-a-schema-name'
								)}
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
				<label>
					{Liferay.Language.get('description')}

					<span className="ml-1 reference-mark text-warning">
						<ClayIcon symbol="asterisk" />
					</span>
				</label>

				<textarea
					className="form-control"
					onChange={({target: {value}}) =>
						setData((previousData) => ({
							...previousData,
							description: value,
						}))
					}
					placeholder={Liferay.Language.get(
						'add-a-short-description-that-describes-this-schema'
					)}
					value={data.description}
				/>

				<div className="feedback-container">
					<ClayForm.FeedbackGroup>
						{displayError.description && (
							<ClayForm.FeedbackItem className="mt-2">
								<ClayForm.FeedbackIndicator symbol="exclamation-full" />

								{Liferay.Language.get(
									'please-enter-a-schema-description'
								)}
							</ClayForm.FeedbackItem>
						)}
					</ClayForm.FeedbackGroup>
				</div>
			</ClayForm.Group>

			<ClayForm.Group
				className={classNames({
					'has-error': displayError.mainObjectDefinitionERC,
				})}
			>
				<label>
					{Liferay.Language.get('object')}

					<span className="ml-1 reference-mark text-warning">
						<ClayIcon symbol="asterisk" />
					</span>
				</label>

				<Select
					cleanUp={() =>
						setData((previousValue) => {
							delete previousValue.mainObjectDefinitionERC;

							return {...previousValue};
						})
					}
					onClick={handleSelectObject}
					options={
						objectDefinitions
							? objectDefinitions.map((objectDefinition) => ({
									label: objectDefinition.name,
									value:
										objectDefinition.externalReferenceCode,
							  }))
							: []
					}
					placeholder={Liferay.Language.get(
						'select-a-liferay-object'
					)}
				/>

				<div className="feedback-container">
					<ClayForm.FeedbackGroup>
						{displayError.mainObjectDefinitionERC && (
							<ClayForm.FeedbackItem className="mt-2">
								<ClayForm.FeedbackIndicator symbol="exclamation-full" />

								{Liferay.Language.get(
									'please-select-an-object'
								)}
							</ClayForm.FeedbackItem>
						)}
					</ClayForm.FeedbackGroup>
				</div>
			</ClayForm.Group>
		</>
	);
}

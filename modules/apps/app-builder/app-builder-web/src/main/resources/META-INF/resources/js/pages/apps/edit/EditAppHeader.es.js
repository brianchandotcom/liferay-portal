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

import {TranslationManager} from 'data-engine-taglib';
import React, {useContext} from 'react';

import {UpperToolbarInput} from '../../../components/upper-toolbar/UpperToolbar.es';
import EditAppContext, {UPDATE_NAME} from './EditAppContext.es';

export default ({
	availableLanguageIds,
	defaultLanguageId,
	editingLanguageId,
	onEditingLanguageIdChange,
}) => {
	const {
		dispatch,
		state: {
			app: {name},
		},
	} = useContext(EditAppContext);

	const onAppNameChange = ({target: {value}}) => {
		dispatch({
			name: {
				...name,
				[editingLanguageId]: value,
			},
			type: UPDATE_NAME,
		});
	};

	return (
		<>
			<div className="align-items-center bg-transparent card-header d-flex justify-content-between">
				<TranslationManager
					availableLanguageIds={availableLanguageIds.reduce(
						(acc, cur) => {
							acc[cur] = cur;

							return acc;
						},
						{}
					)}
					className="mr-1"
					defaultLanguageId={defaultLanguageId}
					editingLanguageId={editingLanguageId}
					onEditingLanguageIdChange={(editingLanguageId) => {
						onEditingLanguageIdChange(editingLanguageId);
					}}
					translatedLanguageIds={name}
				/>
				<UpperToolbarInput
					maxLength={30}
					onChange={onAppNameChange}
					placeholder={Liferay.Language.get('untitled-app')}
					value={name[editingLanguageId] || ''}
				/>
			</div>

			<h4 className="card-divider mb-4"></h4>
		</>
	);
};

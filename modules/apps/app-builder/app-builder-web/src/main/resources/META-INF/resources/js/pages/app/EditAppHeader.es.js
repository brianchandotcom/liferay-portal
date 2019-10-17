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

import React, {useContext} from 'react';

import {UpperToolbarInput} from '../../components/upper-toolbar/UpperToolbar.es';
import EditAppContext, {UPDATE_NAME} from './EditAppContext.es';

export default () => {
	const {
		dispatch,
		state: {
			app: {
				name: {en_US: appName}
			}
		}
	} = useContext(EditAppContext);

	const onAppNameChange = event => {
		const appName = event.target.value;

		dispatch({
			appName,
			type: UPDATE_NAME
		});
	};

	return (
		<>
			<div className="card-header align-items-center d-flex justify-content-between bg-transparent">
				<UpperToolbarInput
					onInput={onAppNameChange}
					placeholder={Liferay.Language.get('untitled-app')}
					value={appName}
				/>
			</div>

			<h4 className="card-divider mb-4"></h4>
		</>
	);
};

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

import {DataLayoutBuilderActions} from 'data-engine-taglib';
import React, {useContext} from 'react';

import {AppContext} from '../../AppContext.es';
import {ToastContext} from '../../components/toast/ToastContext.es';
import UpperToolbar from '../../components/upper-toolbar/UpperToolbar.es';
import FormViewContext from './FormViewContext.es';
import saveFormView from './saveFormView.es';

export default ({newCustomObject}) => {
	const [state, dispatch] = useContext(FormViewContext);
	const {dataDefinitionId, dataLayout} = state;

	const {basePortletURL} = useContext(AppContext);
	const listUrl = `${basePortletURL}/#/custom-object/${dataDefinitionId}/form-views`;

	const {addToast} = useContext(ToastContext);

	const onCancel = () => {
		if (newCustomObject) {
			Liferay.Util.navigate(basePortletURL);
		}
		else {
			Liferay.Util.navigate(listUrl);
		}
	};

	const onError = error => {
		const {title: message = ''} = error;

		addToast({
			displayType: 'danger',
			message: (
				<>
					{message}
					{'.'}
				</>
			),
			title: `${Liferay.Language.get('error')}:`
		});
	};

	const onInput = ({target}) => {
		const {value} = target;

		dispatch({
			payload: {name: {en_US: value}},
			type: DataLayoutBuilderActions.UPDATE_DATA_LAYOUT_NAME
		});
	};

	const onKeyDown = event => {
		if (event.keyCode === 13) {
			event.preventDefault();

			event.target.blur();
		}
	};

	const onSave = () => {
		saveFormView(state)
			.then(() => {
				Liferay.Util.navigate(listUrl);
			})
			.catch(error => {
				onError(error);
			});
	};

	const {
		name: {en_US: dataLayoutName = ''}
	} = dataLayout;

	return (
		<UpperToolbar>
			<UpperToolbar.Input
				onInput={onInput}
				onKeyDown={onKeyDown}
				placeholder={Liferay.Language.get('untitled-form-view')}
				value={dataLayoutName}
			/>
			<UpperToolbar.Group>
				<UpperToolbar.Button displayType="secondary" onClick={onCancel}>
					{Liferay.Language.get('cancel')}
				</UpperToolbar.Button>

				<UpperToolbar.Button
					disabled={dataLayoutName.trim() === ''}
					onClick={onSave}
				>
					{Liferay.Language.get('save')}
				</UpperToolbar.Button>
			</UpperToolbar.Group>
		</UpperToolbar>
	);
};

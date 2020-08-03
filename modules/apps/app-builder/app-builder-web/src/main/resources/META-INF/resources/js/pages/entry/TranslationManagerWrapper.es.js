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
import React, {useContext, useEffect, useState} from 'react';
import {createPortal} from 'react-dom';

import {AppContext} from '../../AppContext.es';
import {getItem} from '../../utils/client.es';
import {getLocalizedUserPreferenceValue} from '../../utils/lang.es';
import {navigateToEditPage} from './utils.es';

const storageKey = '@app-builder/standalone/language';

export const setStorageLocale = (value, appId) => {
	localStorage.setItem(`${storageKey}/${appId}`, value);
};

export const getStorageLocale = (appId) => {
	return (
		localStorage.getItem(`${storageKey}/${appId}`) ||
		Liferay.ThemeDisplay.getLanguageId()
	);
};

export default ({
	dataDefinitionId,
	reloadPage,
	setUserLanguageId,
	userLanguageId,
}) => {
	const {appId, basePortletURL, dataRecordId} = useContext(AppContext);
	const [{app, dataDefinition}, setState] = useState({
		app: {
			name: {},
		},
		dataDefinition: {
			availableLanguageIds: [],
			defaultLanguageId: '',
		},
	});
	const defaultLanguageId = dataDefinition.defaultLanguageId;

	const onEditingLanguageIdChange = (locale) => {
		setUserLanguageId(locale);
		setStorageLocale(locale, appId);

		if (reloadPage) {
			navigateToEditPage(basePortletURL, {dataRecordId, locale});
		}
	};

	useEffect(() => {
		if (appId && dataDefinitionId) {
			Promise.all([
				getItem(`/o/app-builder/v1.0/apps/${appId}`),
				getItem(
					`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}`
				),
			]).then(([app, dataDefinition]) => {
				setState({app, dataDefinition});
			});
		}
	}, [appId, dataDefinitionId]);

	const availableLanguageIds = dataDefinition.availableLanguageIds.reduce(
		(acc, cur) => {
			acc[cur] = cur;

			return acc;
		},
		{}
	);

	const getEditingLanguageId = () => {
		if (availableLanguageIds[userLanguageId]) {
			return userLanguageId;
		}

		return defaultLanguageId;
	};

	return (
		<div>
			{createPortal(
				getLocalizedUserPreferenceValue(
					app.name,
					userLanguageId,
					defaultLanguageId
				),
				document.querySelector('#appStandaloneName')
			)}

			{createPortal(
				<TranslationManager
					availableLanguageIds={availableLanguageIds}
					editingLanguageId={getEditingLanguageId()}
					onEditingLanguageIdChange={onEditingLanguageIdChange}
					showUserView
				/>,
				document.querySelector('#appTranslationManager')
			)}
		</div>
	);
};

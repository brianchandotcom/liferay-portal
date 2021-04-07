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

import {useIsMounted} from '@liferay/frontend-js-react-web';
import {
	FormSupport,
	convertToFormData,
	makeFetch,
	useConfig,
	useFormState,
} from 'data-engine-js-components-web';
import {sub} from 'dynamic-data-mapping-form-builder/js/util/strings.es';
import objectHash from 'object-hash';
import React, {useCallback, useContext, useEffect, useRef} from 'react';

import {useStateSync} from './useStateSync.es';

const AutoSaveContext = React.createContext({});

AutoSaveContext.displayName = 'AutoSaveContext';

const getStateHash = (state) =>
	objectHash(state, {
		algorithm: 'md5',
		unorderedObjects: true,
	});

const getFormData = ({name, portletNamespace, published, saveAsDraft}) => {
	const form = document.querySelector(`#${portletNamespace}editForm`);

	const formData = new FormData(form);

	formData.append(`${portletNamespace}name`, JSON.stringify(name));
	formData.append(`${portletNamespace}published`, JSON.stringify(published));
	formData.append(`${portletNamespace}saveAsDraft`, saveAsDraft);

	return convertToFormData(formData);
};

const defineIds = (portletNamespace, response) => {
	const formInstanceIdNode = document.querySelector(
		`#${portletNamespace}formInstanceId`
	);

	if (formInstanceIdNode && formInstanceIdNode.value === '0') {
		formInstanceIdNode.value = response.formInstanceId;
	}

	const ddmStructureIdNode = document.querySelector(
		`#${portletNamespace}ddmStructureId`
	);

	if (ddmStructureIdNode && ddmStructureIdNode.value === '0') {
		ddmStructureIdNode.value = response.ddmStructureId;
	}
};

const updateAutoSaveMessage = ({
	modifiedDate,
	portletNamespace,
	saveAsDraft,
}) => {
	const autoSaveMessageNode = document.querySelector(
		`#${portletNamespace}autosaveMessage`
	);

	autoSaveMessageNode.innerHTML = sub(
		saveAsDraft
			? Liferay.Language.get('draft-x')
			: Liferay.Language.get('saved-x'),
		[modifiedDate]
	);
};

const MILLISECONDS_TO_MINUTE = 60000;

/**
 * AutoSave performs a periodic routine in minutes to save the current form. Save will
 * only happen if the data on the form changes.
 *
 * Each time the rules are changed, the form is saved.
 */
export const AutoSaveProvider = ({children, interval, published, url}) => {
	const {portletNamespace} = useConfig();
	const {
		availableLanguageIds,
		defaultLanguageId,
		localizedDescription,
		localizedName,
		pages,
		paginationMode,
		rules,
		successPageSettings,
	} = useFormState();

	const doSyncInput = useStateSync();

	const isMounted = useIsMounted();

	const intervalIdRef = useRef(null);

	const pendingRequestRef = useRef(null);

	const lastKnownHashRef = useRef(null);

	const lastKnownHashRulesRef = useRef(null);

	const getCurrentStateHash = useCallback(
		() =>
			getStateHash({
				availableLanguageIds,
				defaultLanguageId,
				description: localizedDescription,
				name: localizedName,
				pages,
				paginationMode,
				rules,
				successPageSettings,
			}),
		[
			availableLanguageIds,
			defaultLanguageId,
			localizedDescription,
			localizedName,
			pages,
			paginationMode,
			rules,
			successPageSettings,
		]
	);

	const doSave = useCallback(
		(saveAsDraft = true) => {
			const lastKnownHash = getCurrentStateHash();

			doSyncInput();

			pendingRequestRef.current = makeFetch({
				body: getFormData({
					name: localizedName,
					portletNamespace,
					published,
					saveAsDraft,
				}),
				url,
			})
				.then((response) => {
					pendingRequestRef.current = null;

					defineIds(portletNamespace, response);

					lastKnownHashRef.current = lastKnownHash;

					updateAutoSaveMessage({
						modifiedDate: response.modifiedDate,
						portletNamespace,
						saveAsDraft,
					});

					return response;
				})
				.catch((error) => {
					pendingRequestRef.current = null;

					console.error(error);
				});

			return pendingRequestRef.current;
		},
		[
			doSyncInput,
			getCurrentStateHash,
			lastKnownHashRef,
			localizedName,
			pendingRequestRef,
			portletNamespace,
			published,
			url,
		]
	);

	const isSaved = useCallback(() => {
		return lastKnownHashRef.current === getCurrentStateHash();
	}, [lastKnownHashRef, getCurrentStateHash]);

	const performSave = useCallback(() => {
		if (isMounted) {
			if (pendingRequestRef.current) {
				pendingRequestRef.current
					.then(() => performSave())
					.catch((error) => console.error(error));
			}
			else if (!isSaved() && !FormSupport.isEmpty(pages)) {
				doSave(!published);
			}
		}
	}, [doSave, isMounted, isSaved, pages, pendingRequestRef, published]);

	useEffect(() => {
		if (interval > 0) {
			intervalIdRef.current = setInterval(
				() => performSave(),
				interval * MILLISECONDS_TO_MINUTE
			);
		}

		return () => {
			if (intervalIdRef.current) {
				clearInterval(intervalIdRef.current);
			}
		};
	}, [intervalIdRef, interval, performSave]);

	useEffect(() => {
		lastKnownHashRef.current = getCurrentStateHash();
		lastKnownHashRulesRef.current = getStateHash(rules);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		const currentKnownHashRules = getStateHash(rules);

		if (lastKnownHashRulesRef.current !== currentKnownHashRules) {
			lastKnownHashRulesRef.current = currentKnownHashRules;

			performSave();
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [lastKnownHashRulesRef, rules]);

	return (
		<AutoSaveContext.Provider value={{doSave, doSyncInput, isSaved}}>
			{children}
		</AutoSaveContext.Provider>
	);
};

export const useAutoSave = () => {
	return useContext(AutoSaveContext);
};

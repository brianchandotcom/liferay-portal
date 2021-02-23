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

import {openToast} from 'frontend-js-web';
import React, {useCallback, useEffect, useRef} from 'react';

import Pages from '../../core/components/Pages.es';
import {useConfig} from '../../core/hooks/useConfig.es';
import {useFormState} from '../../core/hooks/useForm.es';
import {createFormURL} from '../../util/form.es';
import {FormInfo} from './components/FormInfo.es';
import {ManagementToolbar} from './components/ManagementToolbar.es';
import {MetalSidebarAdapter} from './components/MetalSidebarAdapter.es';
import {TranslationManager} from './components/TranslationManager.es';
import {useAutoSave} from './hooks/useAutoSave.es';

export const FormBuilder = () => {
	const {
		formInstanceId,
		portletNamespace,
		publishFormInstanceURL,
		restrictedFormURL,
		sharedFormURL,
	} = useConfig();
	const {
		activePage,
		editingLanguageId,
		fieldSets,
		focusedField,
		pages,
		rules,
	} = useFormState();

	const {doSave, doSyncInput} = useAutoSave();

	const sidebarRef = useRef(null);

	useEffect(() => {
		if (Object.keys(focusedField).length && sidebarRef.current) {
			sidebarRef.current.current.open();
		}
	}, [focusedField]);

	useEffect(() => {
		const currentPage = pages[activePage];
		const isEmpty = currentPage.rows[0].columns[0].fields.length === 0;

		if (isEmpty && sidebarRef.current) {
			sidebarRef.current.current.open();
		}

		// We only want to cause this useEffect to be called again if the
		// number of pages changes and not the page data.
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [pages.length, activePage]);

	const onOpenSidebar = useCallback(() => {
		if (sidebarRef.current) {
			sidebarRef.current.current.open();
		}
	}, []);

	const onPreviewClick = useCallback(
		async (event) => {
			event.preventDefault();

			try {
				const settingsDDMForm = await Liferay.componentReady(
					'settingsDDMForm'
				);

				const fields = settingsDDMForm.reactComponentRef.current.getFields();

				const {value: requireAuthentication} = fields.find(
					({fieldName}) => fieldName === 'requireAuthentication'
				);

				await doSave(true);

				window.open(
					createFormURL('/preview', {
						formInstanceId,
						requireAuthentication,
						restrictedFormURL,
						sharedFormURL,
					}),
					'_blank'
				);
			}
			catch (_) {
				openToast({
					message: Liferay.Language.get(
						'your-request-failed-to-complete'
					),
					type: 'danger',
				});
			}
		},
		[doSave, formInstanceId, sharedFormURL, restrictedFormURL]
	);

	const onPublishClick = useCallback(
		(event) => {
			event.preventDefault();

			const form = document.getElementById(`${portletNamespace}editForm`);

			if (form) {
				form.setAttribute('action', publishFormInstanceURL);
			}

			doSyncInput();

			window.submitForm(form);
		},
		[doSyncInput, portletNamespace, publishFormInstanceURL]
	);

	const onSaveClick = useCallback(
		(event) => {
			event.preventDefault();

			doSyncInput();

			window.submitForm(
				document.getElementById(`${portletNamespace}editForm`)
			);
		},
		[portletNamespace, doSyncInput]
	);

	const onShareClick = useCallback(() => {}, []);

	return (
		<>
			<ManagementToolbar
				onPlusClick={onOpenSidebar}
				onPreviewClick={onPreviewClick}
				onPublishClick={onPublishClick}
				onSaveClick={onSaveClick}
				onShareClick={onShareClick}
				portletNamespace={portletNamespace}
			/>
			<TranslationManager />
			<FormInfo />
			<div className="ddm-form-builder">
				<div className="container ddm-paginated-builder top">
					<div className="ddm-form-builder-wrapper moveable resizeable">
						<div className="container ddm-form-builder">
							<Pages editable={true} />
						</div>
					</div>
				</div>
				<MetalSidebarAdapter
					editingLanguageId={editingLanguageId}
					fieldSets={fieldSets}
					focusedField={focusedField}
					ref={sidebarRef}
					rules={rules}
				/>
			</div>
		</>
	);
};

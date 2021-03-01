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
import ClayLink from '@clayui/link';
import {Context as ModalContext} from '@clayui/modal';
import {
	Pages,
	useConfig,
	useForm,
	useFormState,
} from 'dynamic-data-mapping-form-renderer';
import {EVENT_TYPES as CORE_EVENT_TYPES} from 'dynamic-data-mapping-form-renderer/js/core/actions/eventTypes.es';
import React, {useCallback, useContext, useEffect, useRef} from 'react';

import {FormInfo} from '../components/FormInfo.es';
import {ManagementToolbar} from '../components/ManagementToolbar.es';
import {MetalSidebarAdapter} from '../components/MetalSidebarAdapter.es';
import {TranslationManager} from '../components/TranslationManager.es';
import {useAutoSave} from '../hooks/useAutoSave.es';
import {useToast} from '../hooks/useToast.es';
import fieldDelete from '../thunks/fieldDelete.es';
import {createFormURL} from '../util/form.es';

export const FormBuilder = () => {
	const {
		formInstanceId,
		portletNamespace,
		publishFormInstanceURL,
		published,
		redirectURL,
		restrictedFormURL,
		sharedFormURL,
		showPublishAlert,
		view,
	} = useConfig();
	const {
		activePage,
		editingLanguageId,
		fieldSets,
		focusedField,
		pages,
		rules,
	} = useFormState();
	const [{onClose}, modalDispatch] = useContext(ModalContext);

	const dispatch = useForm();

	const {doSave, doSyncInput} = useAutoSave();

	const addToast = useToast();

	const sidebarRef = useRef(null);

	useEffect(() => {
		if (Object.keys(focusedField).length && sidebarRef.current) {
			sidebarRef.current.current.open();
		}
	}, [focusedField]);

	useEffect(() => {
		const currentPage = pages[activePage];
		const isEmpty = currentPage.rows[0]?.columns[0].fields.length === 0;

		if (isEmpty && sidebarRef.current) {
			sidebarRef.current.current.open();
		}

		// We only want to cause this useEffect to be called again if the
		// number of pages changes and not the page data.
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [pages.length, activePage]);

	const getFormUrl = useCallback(
		async (path) => {
			const settingsDDMForm = await Liferay.componentReady(
				'settingsDDMForm'
			);

			const fields = settingsDDMForm.reactComponentRef.current.getFields();

			const {value: requireAuthentication} = fields.find(
				({fieldName}) => fieldName === 'requireAuthentication'
			);

			return createFormURL(path, {
				formInstanceId,
				requireAuthentication,
				restrictedFormURL,
				sharedFormURL,
			});
		},
		[formInstanceId, restrictedFormURL, sharedFormURL]
	);

	useEffect(() => {
		if (showPublishAlert && published) {
			addToast({
				action: (
					<ClayButton
						alert
						onClick={async () => {
							const url = await getFormUrl();

							window.open(url, '_blank');
						}}
					>
						{Liferay.Language.get('open-form')}
					</ClayButton>
				),
				displayType: 'success',
				message: Liferay.Language.get(
					'the-form-was-published-successfully'
				),
				title: Liferay.Language.get('success'),
			});
		}
	}, [showPublishAlert, published, getFormUrl]);

	const onOpenSidebar = useCallback(() => {
		if (sidebarRef.current) {
			sidebarRef.current.current.open();
		}
	}, []);

	const onPreviewClick = useCallback(
		async (event) => {
			event.preventDefault();

			try {
				const url = await getFormUrl('/preview');

				await doSave(true);

				window.open(url, '_blank');
			}
			catch (_) {
				addToast({
					displayType: 'danger',
					message: Liferay.Language.get(
						'your-request-failed-to-complete'
					),
					title: Liferay.Language.get('error'),
				});
			}
		},
		[doSave, getFormUrl]
	);

	const subtmitForm = useCallback(
		(form) => {
			doSyncInput();

			window.submitForm(form);
		},
		[doSyncInput]
	);

	const onPublishClick = useCallback(
		(event) => {
			event.preventDefault();

			const form = document.getElementById(`${portletNamespace}editForm`);

			if (form) {
				form.setAttribute('action', publishFormInstanceURL);
			}

			subtmitForm(form);
		},
		[subtmitForm, portletNamespace, publishFormInstanceURL]
	);

	const onSaveClick = useCallback(
		(event) => {
			event.preventDefault();

			subtmitForm(document.getElementById(`${portletNamespace}editForm`));
		},
		[portletNamespace, subtmitForm]
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
							<Pages
								editable={true}
								fieldActions={[
									{
										action: (payload) =>
											dispatch({
												payload,
												type:
													CORE_EVENT_TYPES.FIELD
														.DUPLICATE,
											}),
										label: Liferay.Language.get(
											'duplicate'
										),
									},
									{
										action: (payload) =>
											dispatch(
												fieldDelete({
													action: {
														payload,
														type:
															CORE_EVENT_TYPES
																.FIELD.DELETE,
													},
													modalDispatch,
													onClose,
													rules,
												})
											),
										label: Liferay.Language.get('delete'),
									},
								]}
							/>
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

			{view === 'fieldSets' && (
				<div className="container container-fluid-1280">
					<div className="button-holder ddm-form-builder-buttons">
						<ClayButton
							onClick={() =>
								subtmitForm(
									document.getElementById(
										`${portletNamespace}editForm`
									)
								)
							}
						>
							{Liferay.Language.get('Save')}
						</ClayButton>
						<ClayLink button displayType="link" href={redirectURL}>
							{Liferay.Language.get('cancel')}
						</ClayLink>
					</div>
				</div>
			)}
		</>
	);
};

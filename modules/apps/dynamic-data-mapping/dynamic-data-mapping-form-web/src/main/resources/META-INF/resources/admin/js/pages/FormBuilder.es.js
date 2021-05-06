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
import classNames from 'classnames';
import {
	Pages,
	useConfig,
	useForm,
	useFormState,
} from 'data-engine-js-components-web';
import {EVENT_TYPES as CORE_EVENT_TYPES} from 'data-engine-js-components-web/js/core/actions/eventTypes.es';
import {DragLayer, MultiPanelSidebar} from 'data-engine-taglib';
import React, {
	useCallback,
	useContext,
	useEffect,
	useRef,
	useState,
} from 'react';

import {FormInfo} from '../components/FormInfo.es';
import {ManagementToolbar} from '../components/ManagementToolbar.es';
import {TranslationManager} from '../components/TranslationManager.es';
import {ShareFormModalBody} from '../components/share-form/ShareFormModalBody.es';
import {useAutoSave} from '../hooks/useAutoSave.es';
import {useToast} from '../hooks/useToast.es';
import fieldDelete from '../thunks/fieldDelete.es';
import {createFormURL} from '../util/form.es';
import {submitEmailContent} from '../util/submitEmailContent.es';

export const FormBuilder = () => {
	const {
		autocompleteUserURL,
		formInstanceId,
		portletNamespace,
		publishFormInstanceURL,
		published,
		redirectURL,
		restrictedFormURL,
		shareFormInstanceURL,
		sharedFormURL,
		showPublishAlert,
		sidebarPanels,
		view,
	} = useConfig();

	const {
		activePage,
		focusedField,
		localizedName,
		pages,
		rules,
	} = useFormState();
	const [{onClose}, modalDispatch] = useContext(ModalContext);

	const [{sidebarOpen, sidebarPanelId}, setSidebarState] = useState({
		sidebarOpen: true,
		sidebarPanelId: 'fields',
	});

	const dispatch = useForm();

	const emailContent = useRef({
		addresses: [],
		message: Liferay.Util.sub(
			Liferay.Language.get('please-fill-out-this-form-x'),
			sharedFormURL
		),
		subject: localizedName[themeDisplay.getLanguageId()],
	});

	const {doSave, doSyncInput} = useAutoSave();

	const addToast = useToast();

	/**
	 * Opens the sidebar whenever a field is focused
	 */
	useEffect(() => {
		const hasFocusedField = Object.keys(focusedField).length > 0;

		if (hasFocusedField) {
			setSidebarState(({sidebarPanelId}) => ({
				sidebarOpen: true,
				sidebarPanelId,
			}));
		}
	}, [focusedField]);

	useEffect(() => {
		const currentPage = pages[activePage];
		const isEmpty = currentPage.rows[0]?.columns[0].fields.length === 0;

		if (isEmpty) {
			setSidebarState(({sidebarPanelId}) => ({
				sidebarOpen: true,
				sidebarPanelId,
			}));
		}

		// We only want to cause this useEffect to be called again if the
		// number of pages changes and not the page data.
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [activePage, pages.length, setSidebarState]);

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
	}, [addToast, showPublishAlert, published, getFormUrl]);

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
		[addToast, doSave, getFormUrl]
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

	const onShareClick = useCallback(async () => {
		const url = await getFormUrl();

		if (published) {
			modalDispatch({
				payload: {
					body: (
						<ShareFormModalBody
							autocompleteUserURL={autocompleteUserURL}
							emailContent={emailContent}
							localizedName={localizedName}
							url={url}
						/>
					),
					footer: [
						null,
						null,
						<ClayButton.Group key={1} spaced>
							<ClayButton
								displayType="secondary"
								onClick={() => onClose()}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>
							<ClayButton
								displayType="primary"
								onClick={() => {
									submitEmailContent({
										...emailContent.current,
										portletNamespace,
										shareFormInstanceURL,
									});

									onClose();
								}}
							>
								{Liferay.Language.get('done')}
							</ClayButton>
						</ClayButton.Group>,
					],
					header: Liferay.Language.get('share'),
					size: 'lg',
				},
				type: 1,
			});
		}
	}, [
		autocompleteUserURL,
		getFormUrl,
		localizedName,
		modalDispatch,
		onClose,
		portletNamespace,
		published,
		shareFormInstanceURL,
	]);

	return (
		<>
			<ManagementToolbar
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
						<div
							className={classNames(
								'container ddm-form-builder',
								{
									'ddm-form-builder--sidebar-open': sidebarOpen,
								}
							)}
						>
							<DragLayer />
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

				<MultiPanelSidebar
					createPlugin={({panel, sidebarOpen, sidebarPanelId}) => ({
						panel,
						sidebarOpen,
						sidebarPanelId,
					})}
					currentPanelId={sidebarPanelId}
					onChange={setSidebarState}
					open={sidebarOpen}
					panels={[['fields']]}
					sidebarPanels={sidebarPanels}
					variant="light"
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

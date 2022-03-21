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
	EVENT_TYPES as CORE_EVENT_TYPES,
	Pages,
	PagesVisitor,
	addObjectFields,
	updateObjectFields,
	useConfig,
	useForm,
	useFormState,
} from 'data-engine-js-components-web';
import {DragLayer, MultiPanelSidebar} from 'data-engine-taglib';
import React, {
	useCallback,
	useContext,
	useEffect,
	useMemo,
	useRef,
	useState,
} from 'react';

import {FormInfo} from '../components/FormInfo.es';
import {ManagementToolbar} from '../components/ManagementToolbar.es';
import ModalObjectRestrictionsBody from '../components/ModalObjectRestrictionsBody';
import {TranslationManager} from '../components/TranslationManager.es';
import FormSettings from '../components/form-settings/FormSettings';
import {ShareFormModalBody} from '../components/share-form/ShareFormModalBody.es';
import {useAutoSave} from '../hooks/useAutoSave.es';
import {useToast} from '../hooks/useToast.es';
import {useValidateFormWithObjects} from '../hooks/useValidateFormWithObjects';
import fieldDelete from '../thunks/fieldDelete.es';
import {createFormURL} from '../util/form.es';
import {submitEmailContent} from '../util/submitEmailContent.es';
import ErrorList from './ErrorList';

export function FormBuilder() {
	const {
		autocompleteUserURL,
		portletNamespace,
		publishFormInstanceURL,
		published,
		redirectURL,
		shareFormInstanceURL,
		sharedFormURL,
		showPublishAlert,
		sidebarPanels,
		view,
	} = useConfig();

	const {
		activePage,
		focusedField,
		formSettingsContext,
		localizedName,
		objectFields,
		pages,
		rules,
	} = useFormState();

	const {dataDefinition} = useFormState({schema: ['dataDefinition']});

	const [errorList, setErrorList] = useState([]);

	const formSettingsPages = useMemo(() => {
		if (!formSettingsContext) {
			return [];
		}

		const pagesVisitor = new PagesVisitor(formSettingsContext.pages);

		return pagesVisitor.mapFields((field) => {
			if (field.valid) {
				return field;
			}

			return {
				...field,
				displayErrors: true,
			};
		});
	}, [formSettingsContext]);

	const [{onClose}, modalDispatch] = useContext(ModalContext);

	const [{sidebarOpen, sidebarPanelId}, setSidebarState] = useState({
		sidebarOpen: true,
		sidebarPanelId: 'fields',
	});

	const [visibleFormSettings, setVisibleFormSettings] = useState(false);

	const [session, setSession] = useState();

	const dispatch = useForm();

	const emailContentRef = useRef({
		addresses: [],
		message: '',
		subject: localizedName[themeDisplay.getLanguageId()],
	});

	const {doSave, doSyncInput} = useAutoSave();

	const addToast = useToast();

	// This hook is used to validate the Forms when the storage type object
	// is selected in the Forms settings

	const validateFormWithObjects = useValidateFormWithObjects();

	const removeErrorMessage = useCallback(
		(index) => {
			const errorMessages = [...errorList].splice(index, 1);

			setErrorList(errorMessages);
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[setErrorList]
	);

	useEffect(() => {
		const getSession = (attemps) => {
			if (Liferay.Session) {
				setSession(Liferay.Session);
			}
			else if (attemps > 0) {
				setTimeout(() => {
					getSession(--attemps);
				}, 500);
			}
		};

		getSession(10);
	}, []);

	useEffect(() => {
		if (session && !session.get('autoExtend')) {
			Liferay.Session.set('autoExtend', true);

			return () => Liferay.Session.set('autoExtend', false);
		}
	}, [session]);

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

	/**
	 * Adjusts alert messages size according to sidebarOpen state
	 */
	useEffect(() => {
		const alerts = document.querySelector(
			'.ddm-form-web__exception-container'
		);

		if (alerts) {
			alerts.className = classNames('ddm-form-web__exception-container', {
				'ddm-form-web__exception-container--sidebar-open': sidebarOpen,
			});
		}
	}, [sidebarOpen]);

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
			const formInstanceId = document.querySelector(
				`#${portletNamespace}formInstanceId`
			).value;

			return createFormURL(path, {
				formInstanceId,
				sharedFormURL,
			});
		},
		[portletNamespace, sharedFormURL]
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

			if (!dataDefinition.dataDefinitionFields.length) {
				setErrorList([
					Liferay.Language.get('please-add-at-least-one-field'),
				]);

				return;
			}

			if (errorList.length) {
				setErrorList([]);
			}

			try {
				await doSave(true);

				const url = await getFormUrl('/preview');

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
		[addToast, dataDefinition, doSave, errorList, getFormUrl]
	);

	const subtmitForm = useCallback(
		async (form) => {
			const openModalObjectRestrictions = (props) => {
				modalDispatch({
					payload: {
						body: <ModalObjectRestrictionsBody {...props} />,
						footer: [
							null,
							null,
							<ClayButton
								displayType="secondary"
								key={1}
								onClick={() => onClose()}
							>
								{Liferay.Language.get('close')}
							</ClayButton>,
						],
						header: Liferay.Language.get(
							'unmapped-object-required-fields'
						),
						status: 'danger',
					},
					type: 1,
				});
			};

			doSyncInput();

			const isValidToSubmitForm = await validateFormWithObjects(

				// This callback will be rendered when the Forms
				// validation result is false

				openModalObjectRestrictions
			);

			if (isValidToSubmitForm) {
				window.submitForm(form);
			}
		},
		[doSyncInput, modalDispatch, onClose, validateFormWithObjects]
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

			const publishButton = document.querySelector(
				'.lfr-ddm-publish-button'
			);

			publishButton.disabled = true;

			subtmitForm(document.getElementById(`${portletNamespace}editForm`));
		},
		[portletNamespace, subtmitForm]
	);

	const onShareClick = useCallback(async () => {
		const url = await getFormUrl();

		emailContentRef.current.message = Liferay.Util.sub(
			Liferay.Language.get('please-fill-out-this-form-x'),
			url
		);

		if (published) {
			modalDispatch({
				payload: {
					body: (
						<ShareFormModalBody
							autocompleteUserURL={autocompleteUserURL}
							emailContent={emailContentRef}
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
										...emailContentRef.current,
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

	useEffect(() => {
		if (!objectFields.length) {
			addObjectFields(dispatch);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<>
			<ManagementToolbar
				onPreviewClick={onPreviewClick}
				onPublishClick={onPublishClick}
				onSaveClick={onSaveClick}
				onSettingsClick={() => setVisibleFormSettings(true)}
				onShareClick={onShareClick}
				portletNamespace={portletNamespace}
			/>
			<TranslationManager />
			<ErrorList
				errorMessages={errorList}
				onRemove={removeErrorMessage}
				sidebarOpen={sidebarOpen}
			/>
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

			<FormSettings
				{...formSettingsContext}
				onCloseFormSettings={() => {
					setVisibleFormSettings(false);
					updateObjectFields(dispatch);
				}}
				pages={formSettingsPages}
				visibleFormSettings={visibleFormSettings}
			/>
		</>
	);
}

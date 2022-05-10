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

import '../../../css/main.scss';

import {fetch, openModal} from 'frontend-js-web';
import React, {
	useCallback,
	useEffect,
	useImperativeHandle,
	useRef,
} from 'react';

import Pages from '../../core/components/Pages.es';
import {INITIAL_CONFIG_STATE} from '../../core/config/initialConfigState.es';
import {INITIAL_STATE} from '../../core/config/initialState.es';
import {ConfigProvider, useConfig} from '../../core/hooks/useConfig.es';
import {FormProvider, useForm, useFormState} from '../../core/hooks/useForm.es';
import {
	activePageReducer,
	fieldReducer,
	languageReducer,
	pageValidationReducer,
	pagesStructureReducer,
} from '../../core/reducers/index.es';
import formValidate from '../../core/thunks/formValidate.es';
import pageLanguageUpdate from '../../core/thunks/pageLanguageUpdate.es';
import {evaluate} from '../../utils/evaluation.es';
import * as Fields from '../../utils/fields.es';
import {getFormId, getFormNode} from '../../utils/formId.es';
import {parseProps} from '../../utils/parseProps.es';
import {paginationReducer} from './reducers/index.es';

const DDM_FORM_PORTLET_NAMESPACE =
	'_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormPortlet_';

/**
 * This is a copy of the old implementation made in Metal.js, deals with
 * the submit of the Form in the User View, removes the default behavior
 * of the submit and uses Liferay.Util.submitForm.
 */
const useFormSubmit = ({apiRef, containerRef}) => {
	const {activePage, pages} = useFormState();
	const {portletNamespace, submittable, validateCSRFTokenURL} = useConfig();
	const isDDMFormPortletNamespace = portletNamespace.includes(
		DDM_FORM_PORTLET_NAMESPACE
	);

	const submitForm = useCallback(
		(event) => {
			apiRef.current
				.validate()
				.then((validForm) => {
					if (validForm) {
						AUI().use('liferay-form', () => {
							const liferayForm =
								event.target.id &&
								Liferay.Form.get(event.target.id);

							const validLiferayForm = !Object.keys(
								liferayForm?.formValidator?.errors ?? {}
							).length;

							if (!validLiferayForm) {
								Liferay.fire('ddmFormError', {
									formWrapperId: event.target.id,
								});

								return;
							}

							if (submittable) {
								Liferay.Util.submitForm(event.target);
							}

							Liferay.fire('ddmFormValid', {
								formWrapperId: event.target.id,
							});

							Liferay.fire('ddmFormSubmit', {
								formId: getFormId(
									getFormNode(containerRef.current)
								),
							});
						});
					}
					else {
						Liferay.fire('ddmFormError', {
							formWrapperId: event.target.id,
						});
					}
				})
				.catch((error) => {
					console.error(error);

					Liferay.fire('ddmFormError', {
						error,
						formWrapperId: event.target.id,
					});
				});
		},
		[apiRef, containerRef, submittable]
	);

	const handleFormSubmitted = useCallback(
		(event) => {
			event.preventDefault();

			if (isDDMFormPortletNamespace) {
				fetch(validateCSRFTokenURL, {
					method: 'GET',
				})
					.then((response) => response.json())
					.then((jsonResponse) => {
						if (jsonResponse.validCSRFToken) {
							submitForm(event);
						}
						else {
							showSessionExpiredModal();
						}
					})
					.catch((error) => {
						console.error(error);
					});
			}
			else {
				submitForm(event);
			}
		},
		[isDDMFormPortletNamespace, submitForm, validateCSRFTokenURL]
	);

	const showSessionExpiredModal = () => {
		openModal({
			bodyHTML: Liferay.ThemeDisplay.isSignedIn()
				? Liferay.Language.get(
						'you-need-to-be-signed-in-to-submit-this-form'
				  )
				: Liferay.Language.get(
						'you-need-to-reload-the-page-to-submit-this-form'
				  ),
			buttons: [
				{
					displayType: 'secondary',
					label: Liferay.Language.get('cancel'),
					type: 'cancel',
				},
				{
					label: Liferay.Language.get('ok'),
					onClick: () => {
						if (Liferay.ThemeDisplay.isSignedIn()) {
							location.href =
								themeDisplay.getPathMain() +
								'/portal/login?redirect=' +
								window.location.href;
						}
						else {
							window.location.reload();
						}
					},
				},
			],
			id: '<portlet:namespace />ddmFormSessionExpiredModal',
			title: Liferay.Language.get('your-session-has-expired'),
		});
	};

	useEffect(() => {
		if (containerRef.current) {
			Liferay.fire('ddmFormPageShow', {
				formId: getFormId(getFormNode(containerRef.current)),
				page: activePage,
				title: pages[activePage].title,
			});
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		if (containerRef.current) {
			const form = getFormNode(containerRef.current);

			if (form) {
				const onHandle = Liferay.on(
					'submitForm',
					(event) => {
						if (event.form && event.form.getDOM() === form) {
							event.preventDefault();
						}
					},
					this
				);

				form.addEventListener('submit', handleFormSubmitted);

				return () => {
					onHandle.detach();

					form.removeEventListener('submit', handleFormSubmitted);
				};
			}
		}
	}, [containerRef, handleFormSubmitted]);
};

/**
 * Exposes methods to manipulate some states within the application in
 * User View through the ref via React and exposed by the
 * Liferay.Component register.
 */
const usePublicAPI = ({apiRef, containerRef, unstable_onEventRef}) => {
	const {containerId, groupId, portletNamespace} = useConfig();
	const {
		activePage,
		ddmStructureLayoutId,
		defaultLanguageId,
		description,
		editingLanguageId,
		name,
		pages,
		paginationMode,
		readOnly,
		rules,
		successPageSettings,
		viewMode,
		...otherProps
	} = useFormState();
	const dispatch = useForm();

	const validate = useCallback(
		() =>
			dispatch(
				formValidate({
					activePage,
					defaultLanguageId,
					editingLanguageId,
					groupId,
					pages,
					portletNamespace,
					rules,
					viewMode,
				})
			),
		[
			dispatch,
			activePage,
			defaultLanguageId,
			editingLanguageId,
			groupId,
			pages,
			portletNamespace,
			rules,
			viewMode,
		]
	);

	useEffect(() => {
		Liferay.component(
			containerId,
			{
				reactComponentRef: apiRef,

				// unstable_onEvent allows listening to internal events of the
				// FormProvider through the public instance of the form. This
				// is still unstable and can change at any time.

				unstable_onEvent: (callback) => {
					if (unstable_onEventRef) {
						unstable_onEventRef.current = callback;
					}
					else {
						throw new Error(
							`Forms: unstable_onEvent is not available for this instance (Id: ${containerId}).`
						);
					}
				},
			},
			{
				destroyOnNavigate: true,
			}
		);

		return () => {
			Liferay.destroyComponent(containerId);
		};
	}, [apiRef, containerId, unstable_onEventRef]);

	useImperativeHandle(apiRef, () => ({
		evaluate: (editingLanguageId) =>
			evaluate(null, {
				defaultLanguageId,
				editingLanguageId,
				groupId,
				pages,
				portletNamespace,
				rules,
				viewMode,
			}),
		get: (key) => {
			const props = {
				activePage,
				defaultLanguageId,
				description,
				editingLanguageId,
				name,
				pages,
				paginationMode,
				portletNamespace,
				readOnly,
				rules,
				successPageSettings,
				...otherProps,
			};

			return props[key];
		},
		getFields: () => Fields.getFields(pages),
		getFormNode: () =>
			containerRef.current && getFormNode(containerRef.current),
		toJSON: () => ({
			defaultLanguageId,
			description,
			editingLanguageId,
			name,
			pages,
			paginationMode,
			portletNamespace,
			rules,
			successPageSettings,
		}),
		updateEditingLanguageId: ({
			defaultLanguageId: nextDefaultLanguageId = defaultLanguageId,
			editingLanguageId: nextEditingLanguageId = '',
			preserveValue,
		}) =>
			dispatch(
				pageLanguageUpdate({
					ddmStructureLayoutId,
					defaultLanguageId: nextDefaultLanguageId,
					nextEditingLanguageId,
					pages,
					portletNamespace,
					preserveValue,
					prevEditingLanguageId: editingLanguageId,
					readOnly,
				})
			),
		validate,
	}));
};

export const Form = React.forwardRef(({unstable_onEventRef}, ref) => {
	const containerRef = useRef(null);

	useFormSubmit({apiRef: ref, containerRef});
	usePublicAPI({apiRef: ref, containerRef, unstable_onEventRef});

	return <Pages editable={false} ref={containerRef} />;
});

Form.displayName = 'Form';

/**
 * Maps the representation of pagination mode in the builder environment
 * in the form view.
 */
const PAGINATION_MODE_MAPPED = {
	'multi-pages': 'wizard',
};

/**
 * Exports the default application to render the form
 * for the user.
 */
export const FormView = React.forwardRef((props, ref) => {
	const {config, state} = parseProps(props);

	// The application in User View will always expose the public
	// APIs through the registration of Liferay.Component regardless
	// of receiving a ref or not. We maintain a defaultRef as a
	// fallback for this case.

	const defaultRef = useRef(null);

	const unstable_onEventRef = useRef(null);

	return (
		<ConfigProvider config={config} initialConfig={INITIAL_CONFIG_STATE}>
			<FormProvider
				init={({paginationMode, ...otherProps}) => ({
					...otherProps,
					paginationMode:
						PAGINATION_MODE_MAPPED[paginationMode] ??
						paginationMode,
				})}
				initialState={INITIAL_STATE}
				onAction={(action) => {
					if (unstable_onEventRef.current) {
						unstable_onEventRef.current(action);
					}
				}}
				reducers={[
					activePageReducer,
					fieldReducer,
					languageReducer,
					pagesStructureReducer,
					pageValidationReducer,
					paginationReducer,
				]}
				value={state}
			>
				<Form
					ref={ref ?? defaultRef}
					unstable_onEventRef={unstable_onEventRef}
				/>
			</FormProvider>
		</ConfigProvider>
	);
});

FormView.displayName = 'FormView';

export default FormView;

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

import '../../css/main.scss';

import React, {
	useCallback,
	useEffect,
	useImperativeHandle,
	useRef,
} from 'react';

import Pages from '../components/Pages.es';
import {FormProvider, useForm} from '../hooks/useForm.es';
import formValidate from '../thunks/formValidate.es';
import pageLanguageUpdate from '../thunks/pageLanguageUpdate.es';
import {getConnectedReactComponentAdapter} from '../util/ReactComponentAdapter.es';
import {evaluate} from '../util/evaluation.es';
import {getFormId, getFormNode} from '../util/formId.es';

const Form = React.forwardRef(
	(
		{
			activePage,
			dataRecordValues,
			ddmStructureLayoutId,
			defaultLanguageId,
			defaultSiteLanguageId,
			description,
			editingLanguageId,
			groupId,
			name,
			pages,
			paginationMode,
			portletNamespace,
			readOnly,
			rules,
			successPageSettings,
			...otherProps
		},
		ref
	) => {
		const dispatch = useForm();
		const containerRef = useRef(null);

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
			]
		);

		const handleFormSubmitted = useCallback(
			(event) => {
				event.preventDefault();

				validate()
					.then((validForm) => {
						if (validForm) {
							const liferayForm =
								event.target.id &&
								Liferay.Form.get(event.target.id);

							const validLiferayForm = !Object.keys(
								liferayForm?.formValidator?.errors ?? {}
							).length;

							if (!validLiferayForm) {
								return;
							}

							Liferay.Util.submitForm(event.target);

							Liferay.fire('ddmFormSubmit', {
								formId: getFormId(
									getFormNode(containerRef.current)
								),
							});
						}
					})
					.catch((error) => {
						console.error(error);
					});
			},
			[containerRef, validate]
		);

		useImperativeHandle(ref, () => ({
			evaluate: (editingLanguageId) =>
				evaluate(null, {
					defaultLanguageId,
					editingLanguageId,
					groupId,
					pages,
					portletNamespace,
					rules,
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
				editingLanguageId: nextEditingLanguageId = '',
				preserveValue,
			}) =>
				dispatch(
					pageLanguageUpdate({
						ddmStructureLayoutId,
						defaultSiteLanguageId,
						nextEditingLanguageId,
						pages,
						portletNamespace,
						preserveValue,
						prevDataRecordValues: dataRecordValues,
						prevEditingLanguageId: editingLanguageId,
						readOnly,
					})
				),
			validate,
		}));

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
			let onHandle;

			const container = containerRef.current;

			if (container) {
				const form = getFormNode(container);

				if (form) {
					onHandle = Liferay.on(
						'submitForm',
						(event) => {
							if (event.form && event.form.getDOM() === form) {
								event.preventDefault();
							}
						},
						this
					);

					form.addEventListener('submit', handleFormSubmitted);
				}
			}

			return () => {
				if (onHandle) {
					onHandle.detach();
				}

				if (container) {
					const form = getFormNode(container);

					if (form) {
						form.removeEventListener('submit', handleFormSubmitted);
					}
				}
			};
		}, [containerRef, handleFormSubmitted]);

		return (
			<Pages
				activePage={activePage}
				defaultLanguageId={defaultLanguageId}
				description={description}
				editingLanguageId={editingLanguageId}
				groupId={groupId}
				name={name}
				pages={pages}
				paginationMode={paginationMode}
				portletNamespace={portletNamespace}
				readOnly={readOnly}
				rules={rules}
				successPageSettings={successPageSettings}
				{...otherProps}
				ref={containerRef}
			/>
		);
	}
);

Form.displayName = 'Form';

const FormEditor = React.forwardRef(
	(
		{
			onEvent,
			activePage = 0,
			defaultLanguageId = themeDisplay.getLanguageId(),
			...otherProps
		},
		ref
	) => (
		<FormProvider
			onEvent={onEvent}
			value={{...otherProps, activePage, defaultLanguageId}}
		>
			{(props) => <Form {...props} ref={ref} />}
		</FormProvider>
	)
);

FormEditor.displayName = 'FormEditor';

const FormProxy = React.forwardRef(({instance, ...otherProps}, ref) => (
	<FormEditor
		{...otherProps}
		onEvent={(type, payload) => instance.emit(type, payload)}
		ref={ref}
	/>
));

FormProxy.displayName = 'FormProxy';

export const ReactFormAdapter = getConnectedReactComponentAdapter(FormProxy);

export default FormEditor;

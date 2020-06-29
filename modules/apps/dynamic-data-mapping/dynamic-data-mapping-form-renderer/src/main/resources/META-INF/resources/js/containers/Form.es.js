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

import dom from 'metal-dom';
import Soy from 'metal-soy';
import React, {
	useCallback,
	useEffect,
	useImperativeHandle,
	useRef,
} from 'react';

import Pages from '../components/Pages.es';
import {FormProvider, useForm} from '../hooks/useForm.es';
import formValidate from '../thunks/formValidate.es';
import {getConnectedReactComponentAdapter} from '../util/ReactComponentAdapter.es';
import {evaluate} from '../util/evaluation.es';
import {getFormId, getFormNode} from '../util/formId.es';
import templates from './Form.soy';

function handleLiferayFormSubmitted(event) {
	if (event.form && event.form.getDOM() === this.form) {
		event.preventDefault();
	}
}

const Form = React.forwardRef((props, ref) => {
	const dispatch = useForm();
	const containerRef = useRef(null);

	const validate = useCallback(() => {
		const {
			activePage,
			defaultLanguageId,
			editingLanguageId,
			pages,
			portletNamespace,
			rules,
		} = props;

		return dispatch(
			formValidate({
				activePage,
				defaultLanguageId,
				editingLanguageId,
				pages,
				portletNamespace,
				rules,
			})
		);
	}, [dispatch, props]);

	const handleFormSubmitted = useCallback(
		(event) => {
			event.preventDefault();

			validate().then((validForm) => {
				if (validForm) {
					Liferay.Util.submitForm(event.target);

					Liferay.fire('ddmFormSubmit', {
						formId: getFormId(getFormNode(containerRef.current)),
					});
				}
			});
		},
		[containerRef, validate]
	);

	useImperativeHandle(ref, () => ({
		evaluate: () => {
			const {
				defaultLanguageId,
				editingLanguageId,
				pages,
				portletNamespace,
				rules,
			} = props;

			return evaluate(null, {
				defaultLanguageId,
				editingLanguageId,
				pages,
				portletNamespace,
				rules,
			});
		},
		get: (key) => props[key],
		getFormNode: () =>
			containerRef.current && getFormNode(containerRef.current),
		toJSON: () => {
			const {
				defaultLanguageId,
				description,
				editingLanguageId,
				name,
				pages,
				paginationMode,
				portletNamespace,
				rules,
				successPageSettings,
			} = props;

			return {
				defaultLanguageId,
				description,
				editingLanguageId,
				name,
				pages,
				paginationMode,
				portletNamespace,
				rules,
				successPageSettings,
			};
		},
		validate,
	}));

	useEffect(() => {
		if (containerRef.current) {
			Liferay.fire('ddmFormPageShow', {
				formId: getFormId(getFormNode(containerRef.current)),
				page: props.activePage,
				title: props.pages[props.activePage].title,
			});
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [containerRef]);

	useEffect(() => {
		let domDelegatedEventHandle = null;
		const form = null;

		if (containerRef.current) {
			const form = getFormNode(containerRef.current);

			if (form) {
				domDelegatedEventHandle = dom.on(
					form,
					'submit',
					handleFormSubmitted
				);
			}

			Liferay.on(
				'submitForm',
				handleLiferayFormSubmitted.bind({form}),
				this
			);
		}

		return () => {
			Liferay.detach(
				'submitForm',
				handleLiferayFormSubmitted.bind({form}),
				this
			);

			if (domDelegatedEventHandle) {
				domDelegatedEventHandle.removeListener();
			}
		};
	}, [containerRef, handleFormSubmitted]);

	return <Pages {...props} ref={containerRef} />;
});

Form.displayName = 'Form';

const FormProxy = React.forwardRef(
	(
		{
			instance,
			activePage = 0,
			defaultLanguageId = themeDisplay.getLanguageId(),
			...otherProps
		},
		ref
	) => (
		<FormProvider
			onEvent={(type, payload) => instance.emit(type, payload)}
			value={{...otherProps, activePage, defaultLanguageId}}
		>
			{(props) => <Form {...props} ref={ref} />}
		</FormProvider>
	)
);

FormProxy.displayName = 'FormProxy';

const ReactFormAdapter = getConnectedReactComponentAdapter(FormProxy);

Soy.register(ReactFormAdapter, templates);

export default ReactFormAdapter;

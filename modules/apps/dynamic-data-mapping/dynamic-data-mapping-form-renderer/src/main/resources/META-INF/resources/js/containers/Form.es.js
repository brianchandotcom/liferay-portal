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
import React, {useEffect, useImperativeHandle, useRef} from 'react';

import {EVENT_TYPES} from '../actions/types.es';
import FormRenderer from '../components/FormRenderer/FormRenderer.es';
import {FormProvider, useForm} from '../hooks/useForm.es';
import {getConnectedReactComponentAdapter} from '../util/ReactComponentAdapter.es';
import {evaluate} from '../util/evaluation.es';
import {getFormId, getFormNode} from '../util/formId.es';
import {PagesVisitor} from '../util/visitors.es';
import templates from './Form.soy';

function handleLiferayFormSubmitted(event) {
	if (event.form && event.form.getDOM() === this.form) {
		event.preventDefault();
	}
}

const Form = React.forwardRef((props, ref) => {
	const dispatch = useForm();
	const containerRef = useRef(null);

	const validate = () => {
		const {
			activePage,
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
		}).then((evaluatedPages) => {
			let validForm = true;
			const visitor = new PagesVisitor(evaluatedPages);

			visitor.mapFields(({valid}) => {
				if (!valid) {
					validForm = false;
				}
			});

			if (!validForm) {
				dispatch({
					payload: {
						newPages: evaluatedPages,
						pageIndex: activePage,
					},
					type: EVENT_TYPES.PAGE_VALIDATION_FAILED,
				});
			}

			return Promise.resolve(validForm);
		});
	};

	const handleFormSubmitted = (event) => {
		event.preventDefault();

		validate().then((validForm) => {
			if (validForm) {
				Liferay.Util.submitForm(event.target);

				Liferay.fire('ddmFormSubmit', {
					formId: getFormId(this.form),
				});
			}
		});
	};

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
		validate,
	}));

	useEffect(() => {
		if (containerRef.current) {
			const form = getFormNode(containerRef.current);

			if (form) {
				dom.on(form, 'submit', handleFormSubmitted.bind(this, form));
			}

			Liferay.on(
				'submitForm',
				handleLiferayFormSubmitted.bind({form}),
				this
			);

			Liferay.fire('ddmFormPageShow', {
				formId: getFormId(form),
				page: props.activePage ?? 0,
				title: props.pages[props.activePage ?? 0].title,
			});
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return <FormRenderer {...props} ref={containerRef} />;
});

Form.displayName = 'Form';

const FormProxy = React.forwardRef(({instance, ...otherProps}, ref) => (
	<FormProvider
		onEvent={(type, payload) => instance.emit(type, payload)}
		value={otherProps}
	>
		{(props) => <Form {...props} ref={ref} />}
	</FormProvider>
));

FormProxy.displayName = 'FormProxy';

const ReactFormAdapter = getConnectedReactComponentAdapter(FormProxy);

Soy.register(ReactFormAdapter, templates);

export default ReactFormAdapter;

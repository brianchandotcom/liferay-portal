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
import ClayForm from '@clayui/form';
import {withFormik} from 'formik';
import React, {useEffect} from 'react';

import CFInput from './form/CFInput';
import apiFetch from './util/apiFetch';
import {generateKey, getLocalStorage, setLocalStorage} from './util/storage';
import {getURL} from './util/url';
import {getSchemaType} from './util/util';

const schemaIterator = (schema, iterator) => {
	if (schema) {
		const {properties, required = []} = schema;

		return Object.keys(properties).map((name) => {
			const property = properties[name];

			if (!property.readOnly && !property['$ref']) {
				return iterator({
					defaultVal: property.default,
					name,
					required: required.includes(name),
					type: property.type,
				});
			}

			return null;
		});
	}
};

const APIFormBase = (props) => {
	const {
		contentType,
		handleSubmit,
		isSubmitting,
		methodData,
		schema,
		values,
	} = props;

	const {operationId, parameters, requestBody} = methodData;

	const schemaType = getSchemaType(requestBody);

	useEffect(() => {
		setLocalStorage(generateKey(operationId), values);
	}, [operationId, values]);

	return (
		<form onSubmit={handleSubmit}>
			<div className="sheet-section">
				{parameters && (
					<h3 className="sheet-subtitle">
						{Liferay.Language.get('parameters')}
					</h3>
				)}

				{parameters &&
					parameters.map(({name, required, schema}) => (
						<CFInput
							key={name}
							name={name}
							required={required}
							type={schema.type}
						/>
					))}

				{requestBody && (
					<h3 className="sheet-subtitle">{`Request Body (${contentType}: ${schemaType})`}</h3>
				)}

				{schemaType === 'object' && (
					<CFInput
						component="textarea"
						name="jsonObject"
						required={false}
						type={schemaType}
					/>
				)}

				{schemaIterator(
					schema,
					({defaultVal, name, required, type}) => (
						<CFInput
							key={name}
							name={name}
							placeholder={defaultVal}
							required={required}
							type={type}
						/>
					)
				)}

				<ClayForm.Group className="mt-5">
					<ClayButton
						className="btn-block"
						disabled={isSubmitting}
						displayType="primary"
						type="submit"
					>
						{Liferay.Language.get('execute')}
					</ClayButton>
				</ClayForm.Group>
			</div>
		</form>
	);
};

const formikAPIForm = withFormik({
	displayName: 'APIFormBase',
	handleSubmit: (values, {props, setSubmitting}) => {
		const {
			baseURL,
			contentType,
			headers,
			method,
			methodData,
			onResponse,
			path,
			schema,
		} = props;

		const data = {};

		const {parameters, requestBody} = methodData;

		if (requestBody) {
			schemaIterator(schema, ({name}) => {
				if (values[name] && values[name].length > 0) {
					data[name] = values[name];
				}
			});
		}

		const apiURL = getURL({baseURL, params: parameters, path, values});

		apiFetch(apiURL, method, data, contentType, headers)
			.then((response) => {
				onResponse({
					apiURL,
					data,
					response,
				});

				setSubmitting(false);
			})
			.catch((err) => {
				setSubmitting(false);
				if (process.env.NODE_ENV === 'development') {
					console.error(err);
				}
			});
	},
	mapPropsToValues: ({methodData, schema}) => {
		const {operationId, parameters} = methodData;

		const initialValues = {};

		const storedValues = getLocalStorage(generateKey(operationId));

		if (parameters) {
			parameters.forEach(({name}) => {
				initialValues[name] = storedValues[name] || '';
			});
		}

		schemaIterator(schema, ({name}) => {
			initialValues[name] = storedValues[name] || '';
		});

		return initialValues;
	},
	validate: (values, {methodData, schema}) => {
		const errors = {};

		if (methodData.parameters) {
			methodData.parameters.forEach(({name, required}) => {
				if (!!required && !values[name]) {
					errors[name] = 'Required';
				}
			});
		}

		schemaIterator(schema, ({name, required}) => {
			if (!!required && !values[name]) {
				errors[name] = 'Required';
			}
		});

		return errors;
	},
})(APIFormBase);

export default formikAPIForm;

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

import {
	PagesVisitor,
	formatFieldValue,
	setDataRecord,
} from 'data-engine-js-components-web';
import React, {useCallback, useEffect, useState} from 'react';

export function useDDMFormSubmit(ddmForm, onSubmit) {
	useEffect(() => {
		const formNode = ddmForm.reactComponentRef.current.getFormNode();

		formNode.addEventListener('submit', onSubmit);

		return () => formNode.removeEventListener('submit', onSubmit);
	}, [ddmForm, onSubmit]);
}

export function useDDMFormValidation(ddmForm, languageId) {
	return useCallback(
		(event) => {
			return new Promise((resolve, reject) => {
				if (typeof event.stopImmediatePropagation === 'function') {
					event.stopImmediatePropagation();
				}

				const ddmReactForm = ddmForm.reactComponentRef.current;

				ddmReactForm
					.validate()
					.then((isValidForm) => {
						if (!isValidForm) {
							return reject();
						}

						const visitor = new PagesVisitor(
							ddmReactForm.get('pages')
						);

						const dataRecordValues = {};

						visitor.mapFields(
							(field) => {
								const {
									editingLanguageId,
									localizedValue,
								} = field;

								if (
									localizedValue[languageId] === undefined &&
									editingLanguageId != languageId
								) {
									const newValue = formatFieldValue(
										field,
										true,
										languageId,
										editingLanguageId
									);

									if (!localizedValue[languageId]) {
										localizedValue[languageId] = newValue;
									}

									field.value = newValue;
								}

								setDataRecord(
									field,
									dataRecordValues,
									languageId
								);
							},
							true,
							true
						);

						resolve({dataRecordValues});
					})
					.catch(reject);
			});
		},
		[ddmForm.reactComponentRef, languageId]
	);
}

export default function withDDMForm(Component) {
	return ({containerElementId, ...props}) => {
		const [ddmForm, setDDMForm] = useState();

		if (!ddmForm) {
			Liferay.componentReady(containerElementId).then(setDDMForm);
		}

		return ddmForm ? <Component ddmForm={ddmForm} {...props} /> : null;
	};
}

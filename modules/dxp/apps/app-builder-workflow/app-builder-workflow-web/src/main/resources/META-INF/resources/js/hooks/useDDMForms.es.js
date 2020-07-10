/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {PagesVisitor} from 'dynamic-data-mapping-form-renderer';
import {useCallback, useEffect, useState} from 'react';

export function useDDMFormsSubmit(ddmForms, onSubmitCallback) {
	const getFormsValues = (reactForms) => {
		const dataRecord = {
			dataRecordValues: {},
		};

		const languageId = themeDisplay.getLanguageId();

		reactForms.forEach((reactForm) => {
			const visitor = new PagesVisitor(reactForm.get('pages'));

			visitor.mapFields(
				({fieldName, localizable, repeatable, value, visible}) => {
					if (!visible) {
						value = '';
					}

					if (localizable) {
						if (!dataRecord.dataRecordValues[fieldName]) {
							dataRecord.dataRecordValues[fieldName] = {
								[languageId]: [],
							};
						}

						if (repeatable) {
							dataRecord.dataRecordValues[fieldName][
								languageId
							].push(value);
						}
						else {
							dataRecord.dataRecordValues[fieldName] = {
								[languageId]: value,
							};
						}
					}
					else {
						dataRecord.dataRecordValues[fieldName] = value;
					}
				}
			);
		});

		return dataRecord;
	};

	const onSubmit = useCallback(
		(event) => {
			if (typeof event.stopImmediatePropagation === 'function') {
				event.stopImmediatePropagation();
			}

			const reactForms = ddmForms.map(
				(ddmForm) => ddmForm.reactComponentRef.current
			);

			validateForms(reactForms).then((isValid) => {
				if (isValid) {
					onSubmitCallback(event, getFormsValues(reactForms));
				}
			});
		},
		[ddmForms, onSubmitCallback]
	);

	const validateForms = (reactForms) => {
		return reactForms
			.map(({validate}) => validate)
			.reduce((promises, validate) => {
				return promises.then((result) =>
					validate().then(Array.prototype.concat.bind(result))
				);
			}, Promise.resolve([]))
			.then((validations) => validations.every((isValid) => isValid));
	};

	useEffect(() => {
		const forms = ddmForms.map((ddmForm) =>
			ddmForm.reactComponentRef.current.getFormNode()
		);

		forms.forEach((form) => form.addEventListener('submit', onSubmit));

		return () => {
			forms.forEach((form) =>
				form.removeEventListener('submit', onSubmit)
			);
		};
	}, [ddmForms, onSubmit]);

	return onSubmit;
}

export default function useDDMForms(containerElementIds, onSubmitCallback) {
	const [ddmForms, setDdmForms] = useState({});

	containerElementIds.forEach((containerElementId) => {
		if (!ddmForms[containerElementId]) {
			Liferay.componentReady(containerElementId).then((ddmForm) => {
				setDdmForms((prevForms) => ({
					...prevForms,
					[containerElementId]: ddmForm,
				}));
			});
		}
	});

	return useDDMFormsSubmit(Object.values(ddmForms), onSubmitCallback);
}

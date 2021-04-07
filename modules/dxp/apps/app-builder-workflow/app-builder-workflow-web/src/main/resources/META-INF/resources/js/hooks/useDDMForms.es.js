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

import {PagesVisitor, setDataRecord} from 'data-engine-js-components-web';
import {useCallback, useEffect, useMemo, useState} from 'react';

export function useDDMFormsSubmit(ddmForms, onSubmit) {
	useEffect(() => {
		const forms = ddmForms.map((ddmForm) =>
			ddmForm.reactComponentRef.current.getFormNode()
		);

		forms.forEach((formNode) =>
			formNode.addEventListener('submit', onSubmit)
		);

		return () => {
			forms.forEach((form) =>
				form.removeEventListener('submit', onSubmit)
			);
		};
	}, [ddmForms, onSubmit]);
}

export function useDDMFormsValidation(ddmForms, languageId) {
	const getFormsValues = useCallback(
		(ddmReactForms) => {
			const dataRecordValues = {};

			ddmReactForms.forEach((ddmReactForm) => {
				const visitor = new PagesVisitor(ddmReactForm.get('pages'));

				visitor.mapFields(
					(field) =>
						setDataRecord(field, dataRecordValues, languageId),
					true,
					true
				);
			});

			return {dataRecordValues};
		},
		[languageId]
	);

	const validateForms = (ddmReactForms) => {
		return ddmReactForms
			.map(({validate}) => validate)
			.reduce((promises, validate) => {
				return promises.then((result) =>
					validate().then(Array.prototype.concat.bind(result))
				);
			}, Promise.resolve([]))
			.then((validations) => validations.every((isValid) => isValid));
	};

	return useCallback(
		(event) => {
			return new Promise((resolve, reject) => {
				if (typeof event.stopImmediatePropagation === 'function') {
					event.stopImmediatePropagation();
				}

				const ddmReactForms = ddmForms.map(
					(ddmForm) => ddmForm.reactComponentRef.current
				);

				validateForms(ddmReactForms)
					.then((isValid) => {
						if (isValid) {
							resolve(getFormsValues(ddmReactForms));
						}
						else {
							reject();
						}
					})
					.catch(reject);
			});
		},
		[ddmForms, getFormsValues]
	);
}

export default function useDDMForms(containerElementIds) {
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

	return useMemo(() => Object.values(ddmForms), [ddmForms]);
}

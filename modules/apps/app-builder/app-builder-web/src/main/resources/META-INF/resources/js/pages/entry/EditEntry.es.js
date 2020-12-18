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
import {createResourceURL, fetch} from 'frontend-js-web';
import React, {useCallback, useContext, useEffect, useState} from 'react';

import {AppContext} from '../../AppContext.es';
import Button from '../../components/button/Button.es';
import {ControlMenuBase} from '../../components/control-menu/ControlMenu.es';
import useDataDefinition from '../../hooks/useDataDefinition.es';
import withDDMForm, {
	useDDMFormSubmit,
	useDDMFormValidation,
} from '../../hooks/withDDMForm.es';
import {errorToast, successToast} from '../../utils/toast.es';

export const EditEntry = ({
	dataDefinitionId,
	dataRecordId,
	ddmForm,
	redirect,
	userLanguageId,
}) => {
	const {
		appId,
		basePortletURL,
		baseResourceURL,
		namespace,
		portletId,
		showFormView,
		showTableView,
	} = useContext(AppContext);
	const {availableLanguageIds, defaultLanguageId} = useDataDefinition(
		dataDefinitionId
	);
	const [submitting, setSubmitting] = useState(false);

	const isFormViewOnly = showFormView && !showTableView;
	const urlParams = new URLSearchParams(window.location.href);
	const backURL = urlParams.get(`_${portletId}_backURL`) || basePortletURL;

	const onCancel = useCallback(() => {
		if (redirect) {
			Liferay.Util.navigate(redirect);
		}
		else {
			Liferay.Util.navigate(backURL);
		}
	}, [redirect, backURL]);

	const onError = () => {
		errorToast();
		setSubmitting(false);
	};

	const validateForm = useDDMFormValidation(
		ddmForm,
		defaultLanguageId,
		availableLanguageIds
	);

	const onSubmit = useCallback(
		(event) => {
			event.preventDefault();
			setSubmitting(true);

			const parseResponse = (response) =>
				new Promise((resolve, reject) => {
					if (response.ok) {
						resolve(response);
					}
					else {
						reject(response);
					}
				});

			validateForm(event)
				.then((dataRecord) => {
					if (dataRecordId !== '0') {
						fetch(
							createResourceURL(baseResourceURL, {
								p_p_resource_id:
									'/app_builder/update_data_record',
							}),
							{
								body: new URLSearchParams(
									Liferay.Util.ns(namespace, {
										dataRecord: JSON.stringify(dataRecord),
										dataRecordId,
									})
								),
								method: 'POST',
							}
						)
							.then(parseResponse)
							.then(() => {
								successToast(
									Liferay.Language.get('an-entry-was-updated')
								);
								onCancel();
							})
							.catch(onError);
					}
					else {
						fetch(
							createResourceURL(baseResourceURL, {
								p_p_resource_id: '/app_builder/add_data_record',
							}),
							{
								body: new URLSearchParams(
									Liferay.Util.ns(namespace, {
										appBuilderAppId: appId,
										dataRecord: JSON.stringify(dataRecord),
									})
								),
								method: 'POST',
							}
						)
							.then(parseResponse)
							.then(() => {
								successToast(
									Liferay.Language.get('an-entry-was-added')
								);
								onCancel();
							})
							.catch(onError);
					}
				})
				.catch(() => {
					setSubmitting(false);
				});
		},
		[
			appId,
			baseResourceURL,
			dataRecordId,
			namespace,
			onCancel,
			validateForm,
		]
	);

	useDDMFormSubmit(ddmForm, onSubmit);

	useEffect(() => {
		const ddmReactForm = ddmForm.reactComponentRef.current;

		ddmReactForm.updateEditingLanguageId({
			editingLanguageId: userLanguageId,
			preserveValue: true,
		});
	}, [ddmForm, userLanguageId]);

	return (
		<>
			<ControlMenuBase
				backURL={isFormViewOnly ? null : redirect || backURL}
				title={
					dataRecordId !== '0'
						? Liferay.Language.get('edit-entry')
						: Liferay.Language.get('add-entry')
				}
				url={location.href}
			/>

			<ClayButton.Group className="app-builder-form-buttons" spaced>
				<Button disabled={submitting} onClick={onSubmit}>
					{Liferay.Language.get('save')}
				</Button>

				{!isFormViewOnly && (
					<Button displayType="secondary" onClick={onCancel}>
						{Liferay.Language.get('cancel')}
					</Button>
				)}
			</ClayButton.Group>
		</>
	);
};

export default withDDMForm(EditEntry);

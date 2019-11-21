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
import {PagesVisitor} from 'dynamic-data-mapping-form-renderer/js/util/visitors.es';
import React, {useContext, useEffect, useCallback, useState} from 'react';

import {AppContext} from '../../AppContext.es';
import Button from '../../components/button/Button.es';
import {ControlMenuBase} from '../../components/control-menu/ControlMenu.es';
import {addItem, updateItem} from '../../utils/client.es';

export const EditEntry = ({dataDefinitionId, dataRecordId, ddmForm}) => {
	const {basePortletURL} = useContext(AppContext);

	const onCancel = useCallback(() => {
		Liferay.Util.navigate(basePortletURL);
	}, [basePortletURL]);

	const onSave = useCallback(() => {
		const {pages} = ddmForm;
		const visitor = new PagesVisitor(pages);

		const dataRecord = {
			dataRecordValues: {}
		};

		visitor.mapFields(({fieldName, value}) => {
			dataRecord.dataRecordValues[fieldName] = value;
		});

		if (dataRecordId !== '0') {
			updateItem(
				`/o/data-engine/v1.0/data-records/${dataRecordId}`,
				dataRecord
			).then(onCancel);
		} else {
			addItem(
				`/o/data-engine/v1.0/data-definitions/${dataDefinitionId}/data-records`,
				dataRecord
			).then(onCancel);
		}
	}, [dataDefinitionId, dataRecordId, ddmForm, onCancel]);

	useEffect(() => {
		const formNode = ddmForm.getFormNode();
		const onSubmit = () => onSave();

		formNode.addEventListener('submit', onSubmit);

		return () => formNode.removeEventListener('submit', onSubmit);
	}, [ddmForm, onSave]);

	return (
		<>
			<ControlMenuBase
				backURL={`${basePortletURL}/#/`}
				title={Liferay.Language.get('edit-entry')}
				url={location.href}
			/>

			<ClayButton.Group spaced>
				<Button onClick={onSave}>{Liferay.Language.get('save')}</Button>
				<Button displayType="secondary" onClick={onCancel}>
					{Liferay.Language.get('cancel')}
				</Button>
			</ClayButton.Group>
		</>
	);
};

export default ({editEntryContainerElementId, ...props}) => {
	const [ddmForm, setDDMForm] = useState();

	if (!ddmForm) {
		Liferay.componentReady(editEntryContainerElementId).then(setDDMForm);
	}

	return ddmForm ? <EditEntry ddmForm={ddmForm} {...props} /> : null;
};

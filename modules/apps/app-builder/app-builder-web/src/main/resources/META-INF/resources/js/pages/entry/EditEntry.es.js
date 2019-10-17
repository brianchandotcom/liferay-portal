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
import React, {useContext} from 'react';

import {AppContext} from '../../AppContext.es';
import Button from '../../components/button/Button.es';
import {addItem, updateItem} from '../../utils/client.es';

export default ({
	dataDefinitionId,
	dataRecordId,
	editEntryContainerElementId
}) => {
	const {basePortletURL} = useContext(AppContext);

	const onCancel = () => {
		Liferay.Util.navigate(basePortletURL);
	};

	const onSave = () => {
		const {pages} = Liferay.component(editEntryContainerElementId);
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
	};

	return (
		<ClayButton.Group spaced>
			<Button onClick={onSave}>{Liferay.Language.get('save')}</Button>
			<Button displayType="secondary" onClick={onCancel}>
				{Liferay.Language.get('cancel')}
			</Button>
		</ClayButton.Group>
	);
};

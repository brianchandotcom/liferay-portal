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

import ClayButton from '@clayui/button/lib/Button';
import {Context as ClayModalContext} from '@clayui/modal';
import ClayPanel from '@clayui/panel';
import {DataDefinitionUtils} from 'data-engine-taglib';
import React, {useContext} from 'react';

import {getItem} from '../../utils/client.es';
import {getLocalizedValue} from '../../utils/lang.es';
import FormViewContext from './FormViewContext.es';

export default (callback) => {
	const [{dataDefinition, dataDefinitionId, fieldTypes}] = useContext(
		FormViewContext
	);
	const [{onClose}, dispatchModal] = useContext(ClayModalContext);

	return (event) => {
		const {
			customProperties: {ddmStructureId},
			fieldType,
			label,
		} = DataDefinitionUtils.getDataDefinitionField(
			dataDefinition,
			event.fieldName
		);

		let {label: fieldTypeLabel} = fieldTypes.find(({name}) => {
			return name === fieldType;
		});

		if (fieldType === 'fieldset' && ddmStructureId) {
			fieldTypeLabel = Liferay.Language.get('fieldset');
		}

		return getItem(
			`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}/data-definition-field-links?fieldName=${event.fieldName}`
		).then(({items}) => {
			const {dataDefinition = {}, dataLayouts = [], dataListViews = []} =
				items[0] || {};

			dispatchModal({
				payload: {
					body: (
						<>
							<ClayPanel
								className="remove-object-field-panel"
								displayTitle={Liferay.Language.get('field')}
								displayType="secondary"
							>
								<ClayPanel.Body>
									<div>
										<label>
											{Liferay.Language.get('name')}:
										</label>
										<span>{event.fieldName}</span>
									</div>
									<div>
										<label>
											{Liferay.Language.get('label')}:
										</label>
										<span>
											{
												label[
													themeDisplay.getLanguageId()
												]
											}
										</span>
									</div>
									<div>
										<label>
											{Liferay.Language.get('type')}:
										</label>
										<span>{fieldTypeLabel}</span>
									</div>
								</ClayPanel.Body>
							</ClayPanel>

							<p className="remove-object-field-message">
								{dataLayouts.length === 0 &&
								dataListViews.length === 0
									? Liferay.Language.get(
											'delete-definition-field-confirmation'
									  )
									: Liferay.Language.get(
											'delete-definition-field-warning'
									  )}
							</p>

							{dataLayouts.length > 0 && (
								<ClayPanel
									className="remove-object-field-panel"
									displayTitle={Liferay.Language.get(
										'form-views'
									)}
									displayType="secondary"
								>
									<ClayPanel.Body>
										{dataLayouts.map(({name}, index) => (
											<label
												className="d-block"
												key={index}
											>{`${
												index + 1
											}. ${getLocalizedValue(
												dataDefinition.defaultLanguageId,
												name
											)}`}</label>
										))}
									</ClayPanel.Body>
								</ClayPanel>
							)}

							{dataListViews.length > 0 && (
								<ClayPanel
									className="remove-object-field-panel"
									displayTitle={Liferay.Language.get(
										'table-views'
									)}
									displayType="secondary"
								>
									<ClayPanel.Body>
										{dataListViews.map(({name}, index) => (
											<label
												className="d-block"
												key={index}
											>{`${
												index + 1
											}. ${getLocalizedValue(
												dataDefinition.defaultLanguageId,
												name
											)}`}</label>
										))}
									</ClayPanel.Body>
								</ClayPanel>
							)}
						</>
					),
					footer: [
						<></>,
						<></>,
						<ClayButton.Group key={0} spaced>
							<ClayButton
								displayType="secondary"
								key={1}
								onClick={onClose}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>
							<ClayButton
								key={2}
								onClick={() => {
									callback(event);

									onClose();
								}}
							>
								{Liferay.Language.get('delete')}
							</ClayButton>
						</ClayButton.Group>,
					],
					header: Liferay.Language.get('delete-from-object'),
					size: 'md',
					status: 'warning',
				},
				type: 1,
			});
		});
	};
};

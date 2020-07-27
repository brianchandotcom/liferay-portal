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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button/lib/Button';
import {Context as ClayModalContext} from '@clayui/modal';
import ClayPanel from '@clayui/panel';
import React, {useContext} from 'react';

import AppContext from '../../../AppContext.es';
import {getItem} from '../../../utils/client.es';
import {getDataDefinitionFieldSet} from '../../../utils/dataDefinition.es';
import {containsField} from '../../../utils/dataLayoutVisitor.es';

const getName = ({name = {}}) => {
	const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

	return name[defaultLanguageId] || Liferay.Language.get('untitled');
};

const FieldInfo = ({label, value}) => (
	<div>
		<label>{`${label}:`}</label>
		<span>{value}</span>
	</div>
);

const FieldListItems = ({items, name}) => {
	return items.map((item, index) => {
		if (!item[name].length) {
			return null;
		}

		return (
			<div className="mb-4" key={index}>
				<label>{getName(item.dataDefinition)}</label>

				<ol>
					{item[name].map((content, index) => (
						<li key={index}>{getName(content)}</li>
					))}
				</ol>
			</div>
		);
	});
};

export default () => {
	const [{dataDefinition, dataLayout}] = useContext(AppContext);
	const [{onClose}, dispatchModal] = useContext(ClayModalContext);

	const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

	return ({fieldSet, isDeleteAction, modal, onPropagate}) => {
		return getItem(
			`/o/data-engine/v2.0/data-definitions/${fieldSet.id}/data-definition-field-links`
		).then(({items: response}) => {
			const items = response;

			const dataDefinitionFieldSet = getDataDefinitionFieldSet(
				dataDefinition.dataDefinitionFields,
				fieldSet.id
			);

			const fieldInDataLayout =
				dataDefinitionFieldSet &&
				containsField(
					dataLayout.dataLayoutPages,
					dataDefinitionFieldSet.name
				);

			const item = items.find(
				({dataDefinition: linkedDataDefinition}) =>
					linkedDataDefinition.id === dataDefinition.id
			);

			if (item) {
				const {dataLayouts} = item;

				const dataLayoutIndex = dataLayouts.findIndex(
					({id}) => id === dataLayout.id
				);

				if (dataLayoutIndex === -1 && fieldInDataLayout) {
					dataLayouts.push(dataLayout);
				}
				else if (dataLayoutIndex !== -1 && !fieldInDataLayout) {
					dataLayouts.splice(dataLayoutIndex, 1);
				}
			}
			else if (fieldInDataLayout) {
				items.push({
					dataDefinition,
					dataLayouts: [dataLayout],
					dataListViews: [],
				});
			}

			const dataLayouts = [];
			const dataListViews = [];

			items.forEach((item) => {
				dataLayouts.push(...item.dataLayouts);
				dataListViews.push(...item.dataListViews);
			});

			const isFieldSetUsed =
				!!dataLayouts.length || !!dataListViews.length;

			if (!isDeleteAction && (!items.length || !isFieldSetUsed)) {
				return onPropagate(fieldSet);
			}

			return new Promise((resolve) => {
				const {
					actionMessage,
					fieldSetMessage,
					headerMessage,
					warningMessage,
					...otherModalProps
				} = modal;

				const payload = {
					payload: {
						body: (
							<>
								{isFieldSetUsed && (
									<>
										{warningMessage && (
											<ClayAlert displayType="warning">
												<strong>
													{Liferay.Language.get(
														'warning'
													)}
													:
												</strong>

												{warningMessage}
											</ClayAlert>
										)}

										{fieldSetMessage && (
											<p className="fieldset-message">
												{fieldSetMessage}
											</p>
										)}
									</>
								)}

								{isDeleteAction && !isFieldSetUsed && (
									<ClayPanel
										className="remove-object-field-panel"
										displayTitle={Liferay.Language.get(
											'field'
										)}
										displayType="secondary"
									>
										<ClayPanel.Body>
											{dataDefinitionFieldSet && (
												<FieldInfo
													label={Liferay.Language.get(
														'name'
													)}
													value={
														dataDefinitionFieldSet.name
													}
												/>
											)}

											<FieldInfo
												label={Liferay.Language.get(
													'label'
												)}
												value={
													fieldSet.name[
														defaultLanguageId
													]
												}
											/>

											<FieldInfo
												label={Liferay.Language.get(
													'value'
												)}
												value={Liferay.Language.get(
													'fieldset'
												)}
											/>
										</ClayPanel.Body>
									</ClayPanel>
								)}

								{dataLayouts.length > 0 && (
									<ClayPanel
										className="remove-object-field-panel"
										displayTitle={Liferay.Language.get(
											'form-views'
										)}
										displayType="secondary"
									>
										<ClayPanel.Body>
											<FieldListItems
												items={items}
												name="dataLayouts"
											/>
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
											<FieldListItems
												items={items}
												name="dataListViews"
											/>
										</ClayPanel.Body>
									</ClayPanel>
								)}

								{isDeleteAction && !isFieldSetUsed && (
									<p className="remove-object-field-message">
										{Liferay.Language.get(
											'are-you-sure-you-want-to-delete-this-fieldset-it-will-be-deleted-immediately'
										)}
									</p>
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
										onPropagate(fieldSet);

										onClose();
									}}
								>
									{actionMessage}
								</ClayButton>
							</ClayButton.Group>,
						],
						header: headerMessage,
						size: 'md',
						...otherModalProps,
					},
					type: 1,
				};

				resolve(dispatchModal(payload));
			});
		});
	};
};

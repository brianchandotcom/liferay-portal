/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {Context as ClayModalContext} from '@clayui/modal';
import ClayPanel from '@clayui/panel';
import {useFormState} from 'data-engine-js-components-web';
import React, {useContext} from 'react';

import {getItem} from '../../../utils/client.es';

const getName = ({name = {}}) => {
	const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

	return name[defaultLanguageId] || Liferay.Language.get('untitled');
};

const FieldInfo = ({label, value}) => (
	<div>
		<label className="use-propagate-field-set__field-info-label">{`${label}:`}</label>

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

const usePropagateFieldSet = () => {
	const [{onClose}, dispatch] = useContext(ClayModalContext);
	const {dataDefinition, dataLayout} = useFormState({
		schema: ['dataDefinition', 'dataLayout'],
	});

	const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

	return async ({fieldSet, isDeleteAction, modal, onPropagate}) => {
		const {items} = await getItem(
			`/o/data-engine/v2.0/data-definitions/${fieldSet.id}/data-definition-field-links`
		);

		const dataDefinitionField = dataDefinition.dataDefinitionFields.find(
			({customProperties: {ddmStructureId}}) =>
				ddmStructureId === fieldSet.id
		);

		if (dataDefinitionField) {
			const hasItem = items.some(
				({dataDefinition: {id}}) => id === dataDefinition.id
			);
			if (!hasItem) {
				items.push({
					dataDefinition,
					dataLayouts: [dataLayout],
					dataListViews: [],
				});
			}
		}

		const dataLayouts = [];
		const dataListViews = [];

		items.forEach((item) => {
			dataLayouts.push(...item.dataLayouts);
			dataListViews.push(...item.dataListViews);
		});

		const isFieldSetUsed = !!dataLayouts.length || !!dataListViews.length;

		if (!isDeleteAction && (!items.length || !isFieldSetUsed)) {
			return onPropagate(fieldSet);
		}

		const {
			actionMessage,
			allowReferencedDataDefinitionDeletion,
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
										<strong className="use-propagate-field-set__warning">
											{Liferay.Language.get('warning')}:
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
								displayTitle={Liferay.Language.get('field')}
								displayType="secondary"
							>
								<ClayPanel.Body>
									{dataDefinitionField && (
										<FieldInfo
											label={Liferay.Language.get('name')}
											value={dataDefinitionField.name}
										/>
									)}

									<FieldInfo
										label={Liferay.Language.get('label')}
										value={fieldSet.name[defaultLanguageId]}
									/>

									<FieldInfo
										label={Liferay.Language.get('value')}
										value={Liferay.Language.get('fieldset')}
									/>
								</ClayPanel.Body>
							</ClayPanel>
						)}

						{!!dataLayouts.length && (
							<ClayPanel
								className="remove-object-field-panel"
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

						{!!dataListViews.length && (
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
							disabled={
								!allowReferencedDataDefinitionDeletion &&
								!!items.length
							}
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

		dispatch(payload);
	};
};

export default usePropagateFieldSet;

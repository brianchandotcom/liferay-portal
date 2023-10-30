/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPanel from '@clayui/panel';
import {
	API,
	MultiSelectItem,
	MultipleSelect,
	SingleSelect,
	getLocalizableLabel,
} from '@liferay/object-js-components-web';
import React, {useEffect, useState} from 'react';

import './Attachments.scss';

interface AttachmentsProps {
	objectDefinitions: ObjectDefinition[];
	setValues: (values: Partial<NotificationTemplate>) => void;
	values: Partial<NotificationTemplate>;
}

interface ObjectDefinitionItem extends LabelValueObject {
	id: number;
}

export function Attachments({
	objectDefinitions,
	setValues,
	values,
}: AttachmentsProps) {
	const [attachmentsFields, setAttachmentsFields] = useState<
		MultiSelectItem[]
	>([]);
	const [objectDefinitionItems, setObjectDefinitionItems] = useState<
		ObjectDefinitionItem[]
	>([]);
	const [selectedEntityValue, setSelectedEntityValue] = useState<string>();

	const parseFields = (fields: ObjectField[]) => {
		const parsedFields: MultiSelectItem[] = [];

		const attachmentObjectFieldIds = new Set(
			values?.attachmentObjectFieldIds as number[]
		);

		const selectedObjectDefinitionItem = objectDefinitions.find(
			(objectDefinition) =>
				objectDefinition.externalReferenceCode === selectedEntityValue
		);

		fields.forEach(({id, label, name}) => {
			parsedFields.push({
				checked: attachmentObjectFieldIds.has(id as number),
				label: getLocalizableLabel(
					selectedObjectDefinitionItem?.defaultLanguageId as Locale,
					label,
					name
				),
				value: id?.toString() as string,
			});
		});

		return parsedFields;
	};

	const getAttachmentFields = async function fetchObjectFields(
		objectDefinitionExternalReferenceCode: string
	) {
		const items = await API.getObjectDefinitionByExternalReferenceCodeObjectFields(
			objectDefinitionExternalReferenceCode
		);

		const fields: ObjectField[] = items?.filter(
			(field) => field.businessType === 'Attachment'
		);

		setAttachmentsFields(parseFields(fields));
	};

	useEffect(() => {
		const currentObjectDefinition = objectDefinitions?.find(
			(item) => item.id === values.objectDefinitionId
		);

		const newObjectDefinitionItems: ObjectDefinitionItem[] = [];

		objectDefinitions.forEach(
			({
				defaultLanguageId,
				externalReferenceCode,
				id,
				label,
				name,
				system,
			}) => {
				if (!system) {
					newObjectDefinitionItems.push({
						id,
						label: getLocalizableLabel(
							defaultLanguageId,
							label,
							name
						),
						value: externalReferenceCode,
					});
				}
			}
		);

		setObjectDefinitionItems(newObjectDefinitionItems);

		setSelectedEntityValue(currentObjectDefinition?.externalReferenceCode);
	}, [objectDefinitions, values.objectDefinitionId]);

	useEffect(() => {
		const currentObjectDefinition = objectDefinitions?.find(
			(item) => item.id === values.objectDefinitionId
		);

		if (!currentObjectDefinition) {
			setValues({
				...values,
				attachmentObjectFieldIds: [],
				objectDefinitionId: null,
			});
		}

		setSelectedEntityValue(currentObjectDefinition?.externalReferenceCode);

		if (values.objectDefinitionId) {
			getAttachmentFields(
				values.objectDefinitionExternalReferenceCode as string
			);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [values.objectDefinitionId]);

	useEffect(() => {
		setValues({
			...values,
			attachmentObjectFieldIds: attachmentsFields
				.filter((field) => field.checked)
				.map((field) => field.value) as string[],
		});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [attachmentsFields]);

	return (
		<ClayPanel
			collapsable
			defaultExpanded
			displayTitle={Liferay.Language.get('attachments')}
			displayType="secondary"
			showCollapseIcon={true}
		>
			<ClayPanel.Body>
				<div className="lfr__notification-template-attachments">
					<div className="lfr__notification-template-attachments-fields">
						<SingleSelect
							disabled={values.system}
							items={objectDefinitionItems}
							label={Liferay.Language.get('data-source')}
							onSelectionChange={(externalReferenceCode) => {
								getAttachmentFields(
									externalReferenceCode as string
								);

								setSelectedEntityValue(
									externalReferenceCode as string
								);

								const selectedObjectDefinitionItem = objectDefinitionItems.find(
									(objectDefinitionItem) =>
										objectDefinitionItem.value ===
										externalReferenceCode
								);

								setValues({
									...values,
									objectDefinitionExternalReferenceCode: externalReferenceCode as string,
									objectDefinitionId:
										selectedObjectDefinitionItem?.id,
								});
							}}
							placeholder={Liferay.Language.get(
								'select-a-data-source'
							)}
							selectedKey={selectedEntityValue}
						/>
					</div>

					<div className="lfr__notification-template-attachments-fields">
						<MultipleSelect
							disabled={!selectedEntityValue || values.system}
							label={Liferay.Language.get('field')}
							options={attachmentsFields}
							placeholder={Liferay.Language.get('select-a-field')}
							setOptions={setAttachmentsFields}
						/>
					</div>
				</div>
			</ClayPanel.Body>
		</ClayPanel>
	);
}

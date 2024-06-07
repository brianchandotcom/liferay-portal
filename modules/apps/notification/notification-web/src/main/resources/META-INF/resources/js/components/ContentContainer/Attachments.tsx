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
	stringUtils,
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
		const attachmentObjectFieldIds = new Set(
			values?.attachmentObjectFieldIds as number[]
		);

		const selectedObjectDefinitionItem = objectDefinitions.find(
			(objectDefinition) =>
				objectDefinition.externalReferenceCode === selectedEntityValue
		);

		const parsedField = {
			children: fields.map(({id, label, name}) => {
				return {
					checked: attachmentObjectFieldIds.has(id as number),
					label: stringUtils.getLocalizableLabel(
						selectedObjectDefinitionItem?.defaultLanguageId as Locale,
						label,
						name
					),
					value: id?.toString() as string,
				};
			}),
			label: '',
			value: 'attachmentsFields',
		} as MultiSelectItem;

		return [parsedField];
	};

	const getAttachmentFields = async function fetchObjectFields(
		objectDefinitionExternalReferenceCode: string
	) {
		const items =
			await API.getObjectDefinitionByExternalReferenceCodeObjectFields(
				objectDefinitionExternalReferenceCode
			);

		const fields: ObjectField[] = items?.filter(
			(field) => field.businessType === 'Attachment'
		);

		setAttachmentsFields(parseFields(fields));
	};

	useEffect(() => {
		const currentObjectDefinition = objectDefinitions?.find(
			(objectDefinition) =>
				objectDefinition.externalReferenceCode ===
				values.objectDefinitionExternalReferenceCode
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
						label: stringUtils.getLocalizableLabel(
							defaultLanguageId,
							label,
							name
						),
						value: externalReferenceCode,
					});
				}
			}
		);

		if (!currentObjectDefinition) {
			setValues({
				...values,
				attachmentObjectFieldIds: [],
				objectDefinitionId: null,
			});
		}

		if (values.objectDefinitionId) {
			getAttachmentFields(
				values.objectDefinitionExternalReferenceCode as string
			);
		}

		setObjectDefinitionItems(newObjectDefinitionItems);
		setSelectedEntityValue(currentObjectDefinition?.externalReferenceCode);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [objectDefinitions, values.objectDefinitionId]);

	useEffect(() => {
		if (attachmentsFields.length) {
			setValues({
				...values,
				attachmentObjectFieldIds: attachmentsFields[0].children
					.filter((field) => field.checked)
					.map((field) => field.value) as string[],
			});
		}

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

								const selectedObjectDefinitionItem =
									objectDefinitionItems.find(
										(objectDefinitionItem) =>
											objectDefinitionItem.value ===
											externalReferenceCode
									);

								setValues({
									...values,
									objectDefinitionExternalReferenceCode:
										externalReferenceCode as string,
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

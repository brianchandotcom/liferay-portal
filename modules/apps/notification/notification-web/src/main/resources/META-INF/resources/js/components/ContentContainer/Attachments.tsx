/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPanel from '@clayui/panel';
import {
	API,
	AutoComplete,
	CustomItem,
	MultipleSelect,
	filterArrayByQuery,
	getLocalizableLabel,
} from '@liferay/object-js-components-web';
import React, {useEffect, useMemo, useState} from 'react';

import './Attachments.scss';

export function Attachments({setValues, values}: IProps) {
	const [objectDefinitions, setObjectDefinitions] = useState<
		ObjectDefinition[]
	>();
	const [attachmentsFields, setAttachmentsFields] = useState<CustomItem[]>(
		[]
	);
	const [query, setQuery] = useState<string>('');
	const [selectedEntity, setSelectedEntity] = useState<ObjectDefinition>();

	const filteredObjectDefinitions = useMemo(() => {
		if (objectDefinitions) {
			return filterArrayByQuery({
				array: objectDefinitions,
				query,
				str: 'label',
			});
		}
	}, [objectDefinitions, query]);

	const parseFields = (fields: ObjectField[]) => {
		const parsedFields: CustomItem[] = [];

		const attachmentObjectFieldIds = new Set(
			values?.attachmentObjectFieldIds as number[]
		);

		fields.forEach(({id, label, name}) => {
			parsedFields.push({
				checked: attachmentObjectFieldIds.has(id as number),
				label: getLocalizableLabel(
					selectedEntity?.defaultLanguageId as Locale,
					label,
					name
				),
				value: id?.toString(),
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
		const makeFetch = async () => {
			const objectDefinitions = await API.getAllObjectDefinitions();

			const currentObjectDefinition = objectDefinitions?.find(
				(item) => item.id === values.objectDefinitionId
			);

			setObjectDefinitions(
				objectDefinitions?.filter(({system}) => !system)
			);
			setSelectedEntity(currentObjectDefinition);
		};

		makeFetch();
	}, [values.objectDefinitionId]);

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

		setSelectedEntity(currentObjectDefinition);

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
						<AutoComplete<ObjectDefinition>
							emptyStateMessage={Liferay.Language.get(
								'no-data-sources-were-found'
							)}
							hasEmptyItem
							items={filteredObjectDefinitions ?? []}
							label={Liferay.Language.get('data-source')}
							onActive={(item) =>
								selectedEntity?.name === item.name
							}
							onChangeQuery={setQuery}
							onSelectEmptyStateItem={(emptyStateItem) => {
								setAttachmentsFields([]);
								setSelectedEntity(undefined);

								setValues({
									...values,
									objectDefinitionExternalReferenceCode:
										emptyStateItem.externalReferenceCode,
									objectDefinitionId: Number(
										emptyStateItem.id
									),
								});
							}}
							onSelectItem={(item) => {
								if (item.id) {
									getAttachmentFields(
										item.externalReferenceCode
									);
									setSelectedEntity(item);
								}
								else {
									setAttachmentsFields([]);
									setSelectedEntity(undefined);
								}

								setValues({
									...values,
									objectDefinitionExternalReferenceCode:
										item.externalReferenceCode,
									objectDefinitionId: item.id,
								});
							}}
							placeholder={Liferay.Language.get(
								'select-a-data-source'
							)}
							query={query}
							value={getLocalizableLabel(
								selectedEntity?.defaultLanguageId as Locale,
								selectedEntity?.label,
								selectedEntity?.name as string
							)}
						>
							{({defaultLanguageId, label, name}) => (
								<div className="d-flex justify-content-between">
									<div>
										{getLocalizableLabel(
											defaultLanguageId,
											label,
											name
										)}
									</div>
								</div>
							)}
						</AutoComplete>
					</div>

					<div className="lfr__notification-template-attachments-fields">
						<MultipleSelect
							disabled={!selectedEntity}
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

interface IProps {
	setValues: (values: Partial<NotificationTemplate>) => void;
	values: Partial<NotificationTemplate>;
}

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

import ClayPanel from '@clayui/panel';
import {
	API,
	AutoComplete,
	CustomItem,
	MultipleSelect,
	filterArrayByQuery,
} from '@liferay/object-js-components-web';
import React, {useEffect, useMemo, useState} from 'react';

import {defaultLanguageId} from '../util/constants';

import './Attachments.scss';
import {TNotificationTemplate} from './EditNotificationTemplate';

export function Attachments({setValues, values}: IProps) {
	const [objectDefinitions, setObjectDefinitions] = useState<
		ObjectDefinition[]
	>();
	const [attachmentsFields, setAttachmentsFields] = useState<CustomItem[]>(
		[]
	);
	const [
		selectedEntity,
		setSelectedEntity,
	] = useState<ObjectDefinition | null>();
	const [query, setQuery] = useState<string>('');

	const filteredObjectDefinitions = useMemo(() => {
		if (objectDefinitions) {
			return filterArrayByQuery(objectDefinitions, 'label', query);
		}
	}, [objectDefinitions, query]);

	const parseFields = (fields: ObjectField[]) => {
		const parsedFields: CustomItem[] = [];

		const attachmentObjectFieldIds = new Set(
			values?.attachmentObjectFieldIds as number[]
		);

		fields.forEach(({id, label}) => {
			parsedFields.push({
				checked: attachmentObjectFieldIds.has(id as number),
				label: label[defaultLanguageId] as string,
				value: id?.toString(),
			});
		});

		return parsedFields;
	};

	const getAttachmentFields = async function fetchObjectFields(
		objectDefinitionExternalReferenceCode: string
	) {
		const items = await API.getObjectFieldsByExternalReferenceCode(
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
							onChangeQuery={setQuery}
							onSelectEmptyStateItem={(emptyStateItem) => {
								setAttachmentsFields([]);
								setSelectedEntity(null);

								setValues({
									...values,
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
									setSelectedEntity(null);
								}

								setValues({
									...values,
									objectDefinitionId: item.id,
								});
							}}
							placeholder={Liferay.Language.get(
								'select-a-data-source'
							)}
							query={query}
							value={
								selectedEntity?.label[defaultLanguageId] ??
								selectedEntity?.name
							}
						>
							{({label, name}) => (
								<div className="d-flex justify-content-between">
									<div>
										{label[defaultLanguageId] ?? name}
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
	setValues: (values: Partial<TNotificationTemplate>) => void;
	values: Partial<TNotificationTemplate>;
}

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
	FormCustomSelect,
} from '@liferay/object-js-components-web';
import React, {useEffect, useMemo, useState} from 'react';

import './Attachments.scss';
import {TNotificationTemplate} from './EditNotificationTemplate';

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

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
		if (objectDefinitions?.length) {
			return objectDefinitions.filter(({label}) => {
				return label[defaultLanguageId]
					?.toLowerCase()
					?.includes(query.toLowerCase());
			});
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
		objectDefinitionId: number
	) {
		const items = await API.getObjectFields(objectDefinitionId);

		const fields: ObjectField[] = items?.filter(
			(field) => field.businessType === 'Attachment'
		);

		setAttachmentsFields(parseFields(fields));
	};

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
			getAttachmentFields(values.objectDefinitionId);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [values.objectDefinitionId]);

	useEffect(() => {
		API.getObjectDefinitions().then((items) => {
			const objectDefinitions = items.filter(({system}) => !system);

			setObjectDefinitions(objectDefinitions);
		});
	}, []);

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
						<AutoComplete
							emptyStateMessage={Liferay.Language.get(
								'no-data-sources-were-found'
							)}
							hasEmptyItem
							items={filteredObjectDefinitions ?? []}
							label={Liferay.Language.get('data-source')}
							onChangeQuery={setQuery}
							onSelectItem={(item: ObjectDefinition) => {
								if (item.id) {
									getAttachmentFields(item.id);
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
							value={selectedEntity?.label[defaultLanguageId]}
						>
							{({label}) => (
								<div className="d-flex justify-content-between">
									{label[defaultLanguageId] ? (
										<div>{label[defaultLanguageId]}</div>
									) : (
										<div>{label}</div>
									)}
								</div>
							)}
						</AutoComplete>
					</div>

					<div className="lfr__notification-template-attachments-fields">
						<FormCustomSelect
							disabled={!selectedEntity}
							label={Liferay.Language.get('field')}
							multipleChoice
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

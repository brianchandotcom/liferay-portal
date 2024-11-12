/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text} from '@clayui/core';
import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {stringUtils} from '@liferay/object-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {defaultFDSDataSetProps, formatActionURL} from '../../utils/fds';
import {getObjectFieldBusinessTypeLabel} from '../../utils/getObjectFieldBusinessTypeLabel';
import FDSSourceDataRenderer from '../FDSPropsTransformer/FDSSourceDataRenderer';
import LabelRenderer from '../LabelRenderer';
import ModalDeletionNotAllowed from '../ModalDeletionNotAllowed';
import {ModalAddObjectField} from './ModalAddObjectField';
import {ModalDeleteObjectField} from './ModalDeleteObjectField';
import {handleTriggerDeleteObjectField} from './deleteObjectFieldUtil';

import type {FDSItem, IFDSTableProps} from '../../utils/fds';

interface ObjectFieldItemData {
	itemData: ObjectField;
}

interface FieldsProps extends IFDSTableProps {
	baseResourceURL: string;
	creationLanguageId: Liferay.Language.Locale;
}

export default function Fields({
	apiURL,
	baseResourceURL,
	creationLanguageId,
	creationMenu,
	formName,
	id,
	items,
	objectDefinitionExternalReferenceCode,
	style,
	url,
}: FieldsProps) {
	const [deletedObjectField, setDeletedObjectField] =
		useState<ObjectField | null>(null);

	const [objectFieldDeleteInfo, setObjectFieldDeleteInfo] =
		useState<ObjectFieldDeleteInfoProps>({
			deleteLastPublishedObjectDefinitionObjectField: false,
			deleteObjectFieldObjectValidationRuleSetting: false,
			showObjectFieldDeletionConfirmationModal: false,
			showObjectFieldDeletionNotAllowedModal: false,
		});

	const [showAddFieldModal, setShowAddFieldModal] = useState(false);

	useEffect(() => {
		Liferay.on('addObjectField', () => setShowAddFieldModal(true));

		return () => Liferay.detach('addObjectField');
	}, []);

	function objectFieldBusinessTypeDataRenderer({
		itemData,
	}: ObjectFieldItemData) {
		return getObjectFieldBusinessTypeLabel(itemData.businessType);
	}

	function objectFieldLabelDataRenderer({
		itemData,
		openSidePanel,
		value,
	}: FDSItem<ObjectField>) {
		return (
			<LabelRenderer
				onClick={() => {
					openSidePanel({
						url: formatActionURL(url, itemData.id),
					});
				}}
				value={value}
			/>
		);
	}

	function objectFieldLocalizedDataRenderer({itemData}: ObjectFieldItemData) {
		return itemData.localized
			? Liferay.Language.get('yes')
			: Liferay.Language.get('no');
	}

	function objectFieldMandatoryDataRenderer({itemData}: ObjectFieldItemData) {
		return itemData.required
			? Liferay.Language.get('yes')
			: Liferay.Language.get('no');
	}

	const frontendDataSetProps = {
		...defaultFDSDataSetProps,
		apiURL,
		creationMenu,
		customDataRenderers: {
			FDSSourceDataRenderer,
			objectFieldBusinessTypeDataRenderer,
			objectFieldLabelDataRenderer,
			objectFieldLocalizedDataRenderer,
			objectFieldMandatoryDataRenderer,
		},
		formName,
		id,
		itemsActions: items,
		namespace:
			'_com_liferay_object_web_internal_object_definitions_portlet_ObjectDefinitionsPortlet_',
		onActionDropdownItemClick({
			action,
			itemData,
		}: {
			action: {data: {id: string}};
			itemData: ObjectField;
		}) {
			if (action.data.id === 'deleteObjectField') {
				const makeFetch = async () => {
					handleTriggerDeleteObjectField({
						baseResourceURL,
						objectFieldId: itemData?.id,
						objectFieldLabel: stringUtils.getLocalizableLabel(
							creationLanguageId!,
							itemData.label,
							itemData.name
						),
						onAfterDelete: () => {
							setTimeout(() => window.location.reload(), 1500);
						},
						setObjectFieldDeleteInfo,
					});

					setDeletedObjectField(itemData);
				};

				makeFetch();
			}
		},
		portletId:
			'com_liferay_object_web_internal_object_definitions_portlet_ObjectDefinitionsPortlet',
		style,
		views: [
			{
				contentRenderer: 'table',
				label: 'Table',
				name: 'table',
				schema: {
					fields: [
						{
							contentRenderer: 'objectFieldLabelDataRenderer',
							expand: false,
							fieldName: 'label',
							label: Liferay.Language.get('label'),
							localizeLabel: true,
							sortable: true,
						},
						{
							contentRenderer:
								'objectFieldBusinessTypeDataRenderer',
							expand: false,
							fieldName: 'businessType',
							label: Liferay.Language.get('type'),
							localizeLabel: true,
							sortable: false,
						},
						{
							contentRenderer: 'objectFieldMandatoryDataRenderer',
							expand: false,
							fieldName: 'mandatory',
							label: Liferay.Language.get('mandatory'),
							localizeLabel: true,
							sortable: false,
						},
						{
							contentRenderer: 'FDSSourceDataRenderer',
							expand: false,
							fieldName: 'source',
							label: Liferay.Language.get('source'),
							localizeLabel: true,
							sortable: false,
						},
						{
							contentRenderer: 'objectFieldLocalizedDataRenderer',
							expand: false,
							fieldName: 'localized',
							label: Liferay.Language.get('translatable'),
							localizeLabel: true,
							sortable: false,
						},
					],
				},
				thumbnail: 'table',
			},
		],
	};

	return (
		<>
			<FrontendDataSet {...frontendDataSetProps} />

			{showAddFieldModal && (
				<ModalAddObjectField
					baseResourceURL={baseResourceURL}
					creationLanguageId={
						creationLanguageId as Liferay.Language.Locale
					}
					objectDefinitionExternalReferenceCode={
						objectDefinitionExternalReferenceCode
					}
					onAfterSubmit={() => {
						setShowAddFieldModal(false);
						window.location.reload();
					}}
					setVisible={setShowAddFieldModal}
				/>
			)}

			{objectFieldDeleteInfo.showObjectFieldDeletionConfirmationModal && (
				<ModalDeleteObjectField
					handleOnClose={() =>
						setObjectFieldDeleteInfo(
							(prevState: ObjectFieldDeleteInfoProps) => ({
								...prevState,
								showObjectFieldDeletionConfirmationModal: false,
							})
						)
					}
					objectField={deletedObjectField as ObjectField}
					onAfterSubmit={() => {
						setTimeout(() => window.location.reload(), 1500);
					}}
					setObjectField={setDeletedObjectField}
				/>
			)}

			{!!deletedObjectField &&
				objectFieldDeleteInfo.showObjectFieldDeletionNotAllowedModal && (
					<ModalDeletionNotAllowed
						content={
							objectFieldDeleteInfo.deleteObjectFieldObjectValidationRuleSetting ? (
								<Text>
									{sub(
										Liferay.Language.get(
											'the-object-field-x-cannot-be-deleted-because-it-is-the-only-custom-object-field-of-the-published-object-definition'
										),
										`${stringUtils.getLocalizableLabel(
											creationLanguageId as Liferay.Language.Locale,
											deletedObjectField.label,
											deletedObjectField.name
										)}`
									)}
								</Text>
							) : (
								<Text>
									{sub(
										Liferay.Language.get(
											'the-object-field-x-cannot-be-deleted-because-it-is-used-in-a-unique-composite-key-validation'
										),
										`${stringUtils.getLocalizableLabel(
											creationLanguageId as Liferay.Language.Locale,
											deletedObjectField.label,
											deletedObjectField.name
										)}`
									)}
								</Text>
							)
						}
						onModalClose={() =>
							setObjectFieldDeleteInfo({
								...objectFieldDeleteInfo,
								showObjectFieldDeletionNotAllowedModal: false,
							})
						}
					/>
				)}
		</>
	);
}

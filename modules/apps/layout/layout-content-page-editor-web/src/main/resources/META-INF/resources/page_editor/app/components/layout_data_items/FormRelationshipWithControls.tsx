/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React from 'react';

import {FormLayoutDataItem} from '../../../types/layout_data/FormLayoutDataItem';
import {FormRelationshipLayoutDataItem} from '../../../types/layout_data/FormRelationshipLayoutDataItem';
import {
	LayoutData,
	LayoutDataItem,
} from '../../../types/layout_data/LayoutData';
import {config} from '../../config/index';
import {
	ObjectFieldSet,
	ObjectFields,
	useObjectFields,
} from '../../contexts/ObjectDataContext';
import {
	useDispatch,
	useSelector,
	useSelectorCallback,
} from '../../contexts/StoreContext';
import {ContainerWithControls} from '../../js-index';
import selectLanguageId from '../../selectors/selectLanguageId';
import updateItemConfig from '../../thunks/updateItemConfig';
import isItemEmpty from '../../utils/isItemEmpty';
import FormRelationship from './FormRelationship';

function getParent(
	item: LayoutDataItem,
	layoutData: LayoutData
): FormLayoutDataItem | FormRelationshipLayoutDataItem {
	const parent = layoutData.items[item.parentId];

	if (parent.type === 'form' || parent.type === 'form-relationship') {
		return parent;
	}

	return getParent(parent, layoutData);
}

function getFieldSets(
	fields: ObjectFields,
	parent: LayoutDataItem
): ObjectFieldSet[] {

	// Take relationship fieldSets directly if parent is a form relationship

	if (parent.type === 'form-relationship') {
		return fields.filter(
			(fieldSet) => 'relationship' in fieldSet && fieldSet.relationship
		) as ObjectFieldSet[];
	}

	// Ignore Basic Information fieldSet if parent is a form

	const fieldSet = fields.find(
		({name}) => name && name !== 'basic-information'
	) as ObjectFieldSet;

	return fieldSet.fields.filter(
		(fieldSet) => 'relationship' in fieldSet && fieldSet.relationship
	) as ObjectFieldSet[];
}

export default React.forwardRef<
	HTMLDivElement,
	{
		children: React.ReactNode;
		item: FormRelationshipLayoutDataItem;
		layoutData: LayoutData;
	}
>(({children, item, layoutData, ...rest}, ref) => {
	const parent = getParent(item, layoutData);

	return (
		<ContainerWithControls {...rest} item={item} ref={ref}>
			<FormRelationshipWithControls item={item} parent={parent}>
				{children}
			</FormRelationshipWithControls>
		</ContainerWithControls>
	);
});

function FormRelationshipWithControls({
	children,
	item,
	parent,
}: {
	children: React.ReactNode;
	item: FormRelationshipLayoutDataItem;
	parent: FormRelationshipLayoutDataItem | FormLayoutDataItem;
}) {
	const fields = useObjectFields(
		parent.type === 'form'
			? {
					classNameId: parent.config.classNameId,
					classTypeId: parent.config.classTypeId,
				}
			: {name: parent.config.contentType}
	);

	const isMapped = Boolean(item.config.contentType);

	if (!isMapped) {
		return (
			<UnmappedFormRelationship
				fieldSets={getFieldSets(fields, parent)}
				item={item}
			/>
		);
	}

	return (
		<>
			<MappedFormRelationship item={item}>
				{children}
			</MappedFormRelationship>

			<AddButton label={item.config.buttonLabel} />
		</>
	);
}

function UnmappedFormRelationship({
	fieldSets,
	item,
}: {
	fieldSets: ObjectFieldSet[];
	item: FormRelationshipLayoutDataItem;
}) {
	const dispatch = useDispatch();

	const options = fieldSets.map(({label, name}) => ({
		label,
		value: name,
	}));

	return (
		<div className="align-items-center bg-lighter d-flex flex-column page-editor__form-unmapped-state page-editor__no-fragments-state">
			<p className="page-editor__no-fragments-state__title">
				{Liferay.Language.get('map-your-form-relationship')}
			</p>

			<p className="mb-3 page-editor__no-fragments-state__message">
				{Liferay.Language.get('select-a-content-type')}
			</p>

			<div className="cadmin">
				<ClaySelectWithOption
					aria-label={Liferay.Language.get('select-a-content-type')}
					onChange={(event) => {
						dispatch(
							updateItemConfig({
								itemConfig: {
									...item.config,
									contentType: event.target.value,
								},
								itemIds: [item.itemId],
							})
						);
					}}
					options={[
						{
							label: Liferay.Language.get('none'),
							value: '0',
						},
						...options,
					]}
					sizing="sm"
				/>
			</div>
		</div>
	);
}

function MappedFormRelationship({
	children,
	item,
}: {
	children: React.ReactNode;
	item: FormRelationshipLayoutDataItem;
}) {
	const isEmpty = useSelectorCallback(
		(state) =>
			isItemEmpty(item, state.layoutData, state.selectedViewportSize),
		[item]
	);

	if (isEmpty) {
		return (
			<div className="page-editor__no-fragments-state text-center">
				<img
					className="page-editor__no-fragments-state__image"
					src={`${config.imagesPath}/drag_and_drop.svg`}
				/>

				<p className="page-editor__no-fragments-state__message">
					{Liferay.Language.get(
						'drag-and-drop-fragments-or-widgets-here'
					)}
				</p>
			</div>
		);
	}

	return <FormRelationship item={item}>{children}</FormRelationship>;
}

function AddButton({label}: {label: Liferay.Language.LocalizedValue<string>}) {
	const languageId = useSelector(selectLanguageId);

	const value =
		label?.[languageId] ??
		label?.[config.defaultLanguageId] ??
		Liferay.Language.get('add-new');

	return (
		<ClayButton
			aria-label={value ? '' : Liferay.Language.get('add-new')}
			borderless
			displayType="primary"
			size="sm"
		>
			<ClayIcon
				className={classNames('text-primary', {
					'mr-2': value,
				})}
				style={{transform: 'rotate(45deg)'}}
				symbol="times-circle-full"
			/>

			{value}
		</ClayButton>
	);
}

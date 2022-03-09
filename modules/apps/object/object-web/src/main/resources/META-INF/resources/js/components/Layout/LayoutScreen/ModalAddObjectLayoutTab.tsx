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

import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayLabel from '@clayui/label';
import ClayModal from '@clayui/modal';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';
import React, {useContext, useMemo, useState} from 'react';

import useForm from '../../../hooks/useForm';
import {separateCamelCase} from '../../../utils/string';
import AutoComplete from '../../Form/AutoComplete';
import Input from '../../Form/Input';
import LayoutContext, {TYPES as EVENT_TYPES} from '../context';
import {TObjectRelationship} from '../types';

import './ModalAddObjectLayoutTab.scss';

type TTabTypes = {
	[key: string]: {
		active: boolean;
		description: string;
		label: string;
	};
};

const TYPES = {
	FIELDS: 'fields',
	RELATIONSHIPS: 'relationships',
};

const types: TTabTypes = {
	[TYPES.FIELDS]: {
		active: true,
		description: Liferay.Language.get(
			'display-fields-and-one-to-one-relationships'
		),
		label: Liferay.Language.get('fields'),
	},
	[TYPES.RELATIONSHIPS]: {
		active: false,
		description: Liferay.Language.get('display-multiple-relationships'),
		label: Liferay.Language.get('relationships'),
	},
};

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

interface IModalAddObjectLayoutTabProps
	extends React.HTMLAttributes<HTMLElement> {
	observer: any;
	onClose: () => void;
}

interface ITabTypeProps extends React.HTMLAttributes<HTMLElement> {
	description: string;
	disabled?: boolean;
	disabledMessage?: string;
	label: string;
	onChangeType: (type: string) => void;
	selected: string;
	type: string;
}

const TabType: React.FC<ITabTypeProps> = ({
	description,
	disabled = false,
	label,
	onChangeType,
	selected,
	type,
}) => {
	const tabProps = {
		'data-tooltip-align': 'top',
		'onClick': () => {},
		'title': Liferay.Language.get(
			'the-first-tab-in-the-layout-cannot-be-a-relationship-tab'
		),
	};

	return (
		<ClayTooltipProvider>
			<div
				className={classNames('layout-tab__tab-types', {
					active: selected === type,
					disabled,
				})}
				key={type}
				onClick={() => onChangeType(type)}
				{...(disabled && tabProps)}
			>
				<h4 className="layout-tab__tab-types__title">{label}</h4>

				<span className="tab__tab-types__description">
					{description}
				</span>
			</div>
		</ClayTooltipProvider>
	);
};

const ModalAddObjectLayoutTab: React.FC<IModalAddObjectLayoutTabProps> = ({
	observer,
	onClose,
}) => {
	const [
		{
			objectLayout: {objectLayoutTabs},
			objectRelationships,
		},
		dispatch,
	] = useContext(LayoutContext);
	const [selectedType, setSelectedType] = useState(TYPES.FIELDS);
	const [query, setQuery] = useState<string>('');
	const [selectedRelationship, setSelectedRelationship] = useState<
		TObjectRelationship
	>();

	const filteredRelationships = useMemo(() => {
		return objectRelationships.filter(({inLayout, label, name}) => {
			return (
				(label[defaultLanguageId]
					.toLowerCase()
					?.match(query.toLowerCase()) ??
					name.toLowerCase()?.match(query.toLowerCase())) &&
				!inLayout
			);
		});
	}, [objectRelationships, query]);

	const onSubmit = (values: any) => {
		dispatch({
			payload: {
				name: {
					[defaultLanguageId]: values.name,
				},
				objectRelationshipId: values.objectRelationshipId,
			},
			type: EVENT_TYPES.ADD_OBJECT_LAYOUT_TAB,
		});

		onClose();
	};

	const onValidate = (values: any) => {
		const errors: any = {};

		if (!values.name) {
			errors.name = Liferay.Language.get('required');
		}

		if (
			!values.objectRelationshipId &&
			selectedType === TYPES.RELATIONSHIPS
		) {
			errors.objectRelationshipId = Liferay.Language.get('required');
		}

		return errors;
	};

	const {errors, handleChange, handleSubmit, setValues, values} = useForm({
		initialValues: {
			name: '',
			objectRelationshipId: 0,
		},
		onSubmit,
		validate: onValidate,
	});

	return (
		<ClayModal observer={observer}>
			<ClayForm onSubmit={handleSubmit}>
				<ClayModal.Header>
					{Liferay.Language.get('add-tab')}
				</ClayModal.Header>

				<ClayModal.Body>
					<Input
						error={errors.name}
						id="inputName"
						label={Liferay.Language.get('label')}
						name="name"
						onChange={handleChange}
						required
						value={values.name}
					/>

					<ClayForm.Group>
						<label className="mb-2">
							{Liferay.Language.get('type')}
						</label>

						{Object.keys(types).map((key) => {
							const {description, label} = types[key];

							return (
								<TabType
									description={description}
									disabled={
										objectLayoutTabs.length === 0 &&
										key === TYPES.RELATIONSHIPS
									}
									key={key}
									label={label}
									onChangeType={setSelectedType}
									selected={selectedType}
									type={key}
								/>
							);
						})}
					</ClayForm.Group>

					{selectedType === TYPES.RELATIONSHIPS && (
						<AutoComplete
							contentRight={
								<ClayLabel
									className="label-inside-custom-select"
									displayType="secondary"
								>
									{selectedRelationship?.type}
								</ClayLabel>
							}
							emptyStateMessage={Liferay.Language.get(
								'there-are-no-relationship-for-this-object'
							)}
							error={errors.objectRelationshipId}
							items={filteredRelationships}
							label={Liferay.Language.get('relationship')}
							onChangeQuery={setQuery}
							onSelectItem={(item) => {
								const {type} = item;
								const selectedItem = {
									...item,
									type: separateCamelCase(type),
								};

								setSelectedRelationship(selectedItem);
								setValues({
									objectRelationshipId: selectedItem.id,
								});
							}}
							query={query}
							required
							value={
								selectedRelationship?.label[
									defaultLanguageId
								] ?? selectedRelationship?.name
							}
						>
							{({label, name, type}) => (
								<div className="d-flex justify-content-between">
									<div>
										{label[defaultLanguageId] ?? name}
									</div>

									<div className="object-web-relationship-item-label">
										<ClayLabel displayType="secondary">
											{separateCamelCase(type)}
										</ClayLabel>
									</div>
								</div>
							)}
						</AutoComplete>
					)}
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group spaced>
							<ClayButton
								displayType="secondary"
								onClick={onClose}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton type="submit">
								{Liferay.Language.get('save')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</ClayForm>
		</ClayModal>
	);
};

export default ModalAddObjectLayoutTab;

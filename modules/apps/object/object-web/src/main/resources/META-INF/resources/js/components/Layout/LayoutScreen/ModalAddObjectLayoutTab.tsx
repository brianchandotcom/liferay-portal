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
import {Observer} from '@clayui/modal/lib/types';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {
	AutoComplete,
	FormError,
	Input,
	stringIncludesQuery,
	useForm,
} from '@liferay/object-js-components-web';
import classNames from 'classnames';
import React, {useMemo, useState} from 'react';

import {separateCamelCase} from '../../../utils/string';
import {TYPES as EVENT_TYPES, useLayoutContext} from '../objectLayoutContext';
import {TObjectLayoutTab, TObjectRelationship} from '../types';

import './ModalAddObjectLayoutTab.scss';

type TTabTypes = {
	[key: string]: {
		active: boolean;
		description: string;
		label: string;
	};
};

type TLabelInfo = {
	displayType: 'info' | 'secondary' | 'success';
	labelContent: string;
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

interface ModalAddObjectLayoutTabProps
	extends React.HTMLAttributes<HTMLElement> {
	observer: Observer;
	onClose: () => void;
}

interface TabTypeProps extends React.HTMLAttributes<HTMLElement> {
	description: string;
	disabled?: boolean;
	disabledMessage?: string;
	label: string;
	onChangeType: (type: string) => void;
	selected: string;
	type: string;
}

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

function TabType({
	description,
	disabled = false,
	label,
	onChangeType,
	selected,
	type,
}: TabTypeProps) {
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
}

function getRelationshipInfo(reverse: boolean, type: string): TLabelInfo {
	if (Liferay.FeatureFlags['LPS-158478']) {
		return {
			displayType: reverse ? 'info' : 'success',
			labelContent: reverse
				? Liferay.Language.get('child')
				: Liferay.Language.get('parent'),
		};
	}
	else {
		return {
			displayType: 'secondary',
			labelContent: type,
		};
	}
}

export function ModalAddObjectLayoutTab({
	observer,
	onClose,
}: ModalAddObjectLayoutTabProps) {
	const [
		{
			objectLayout: {objectLayoutTabs},
			objectRelationships,
		},
		dispatch,
	] = useLayoutContext();
	const [selectedType, setSelectedType] = useState(TYPES.FIELDS);
	const [query, setQuery] = useState<string>('');
	const [selectedRelationship, setSelectedRelationship] = useState<
		TObjectRelationship
	>();

	const filteredRelationships = useMemo(() => {
		return objectRelationships.filter(
			({inLayout, label, name}) =>
				(stringIncludesQuery(
					label[defaultLanguageId] as string,
					query
				) ??
					stringIncludesQuery(name, query)) &&
				!inLayout
		);
	}, [objectRelationships, query]);

	const selectedRelationshipInfo: TLabelInfo = useMemo(() => {
		return getRelationshipInfo(
			selectedRelationship?.reverse ?? false,
			selectedRelationship?.type ?? ''
		);
	}, [selectedRelationship]);

	const onSubmit = (values: TObjectLayoutTab) => {
		dispatch({
			payload: {
				name: {
					[defaultLanguageId]: values.name[defaultLanguageId],
				},
				objectRelationshipId: values.objectRelationshipId,
			},
			type: EVENT_TYPES.ADD_OBJECT_LAYOUT_TAB,
		});

		onClose();
	};

	const onValidate = (values: Partial<TObjectLayoutTab>) => {
		const errors: FormError<TObjectLayoutTab> = {};

		if (!values.name?.[defaultLanguageId]) {
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

	const {errors, handleSubmit, setValues, values} = useForm<TObjectLayoutTab>(
		{
			initialValues: {},
			onSubmit,
			validate: onValidate,
		}
	);

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
						onChange={({target: {value}}) => {
							setValues({
								name: {
									[defaultLanguageId]: value,
								},
							});
						}}
						required
						value={values.name?.[defaultLanguageId]}
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
										!objectLayoutTabs.length &&
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
									displayType={
										selectedRelationshipInfo.displayType
									}
								>
									{selectedRelationshipInfo.labelContent}
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
							{({label, name, reverse, type}) => {
								const relationshipInfo = getRelationshipInfo(
									reverse,
									type
								);

								return (
									<div className="d-flex justify-content-between">
										<div>
											{label[defaultLanguageId] ?? name}
										</div>

										<div className="object-web-relationship-item-label">
											<ClayLabel
												displayType={
													relationshipInfo.displayType
												}
											>
												{relationshipInfo.labelContent}
											</ClayLabel>
										</div>
									</div>
								);
							}}
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
}

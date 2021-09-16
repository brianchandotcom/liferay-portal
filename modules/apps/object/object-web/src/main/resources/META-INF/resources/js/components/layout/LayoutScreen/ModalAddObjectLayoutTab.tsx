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
import ClayForm, {ClayInput} from '@clayui/form';
import ClayLabel from '@clayui/label';
import ClayModal from '@clayui/modal';
import classNames from 'classnames';
import React, {useContext, useMemo, useState} from 'react';

import {normalizeLanguageId} from '../../../utils/string';
import RequiredMask from '../../RequiredMask';
import LayoutContext, {TYPES as EVENT_TYPES} from '../context';
import {TObjectRelationship} from '../types';
import AutoComplete from './AutoComplete';

type TTabTypes = {
	[key: string]: {
		active: boolean;
		description: string;
		label: string;
	};
};

interface ITabTypesProps extends React.HTMLAttributes<HTMLElement> {
	onChangeType: (type: string) => void;
}

const TYPES = {
	FIELDS: 'fields',
	RELATIONSHIPS: 'relationships',
};

const types: TTabTypes = {
	[TYPES.FIELDS]: {
		active: true,
		description: Liferay.Language.get(
			'to-display-fields-and-one-to-one-relationships'
		),
		label: Liferay.Language.get('fields'),
	},
	[TYPES.RELATIONSHIPS]: {
		active: false,
		description: Liferay.Language.get('to-display-multiple-relationships'),
		label: Liferay.Language.get('relationships'),
	},
};

const TabTypes: React.FC<ITabTypesProps> = ({onChangeType}) => {
	const [selectedType, setSelectedType] = useState<string>(TYPES.FIELDS);

	return (
		<>
			{Object.keys(types).map((key) => {
				const {description, label} = types[key];

				return (
					<div
						className={classNames('layout-tab__tab-types', {
							active: selectedType === key,
						})}
						key={key}
						onClick={() => {
							onChangeType(key);
							setSelectedType(key);
						}}
					>
						<h4 className="layout-tab__tab-types__title">
							{label}
						</h4>
						<span className="tab__tab-types__description">
							{description}
						</span>
					</div>
				);
			})}
		</>
	);
};

const defaultLanguageId = normalizeLanguageId(
	Liferay.ThemeDisplay.getDefaultLanguageId()
);

interface IModalAddObjectLayoutTabProps
	extends React.HTMLAttributes<HTMLElement> {
	observer: any;
	onClose: () => void;
}

const ModalAddObjectLayoutTab: React.FC<IModalAddObjectLayoutTabProps> = ({
	observer,
	onClose,
}) => {
	const [name, setName] = useState<string>('');
	const [{objectRelationships}, dispatch] = useContext(LayoutContext);
	const [selectedType, setSelectedType] = useState('');
	const [query, setQuery] = useState<string>('');
	const [selectedRelationship, setSelectedRelationship] = useState<
		TObjectRelationship
	>();

	const filteredRelationships = useMemo(() => {
		return objectRelationships.filter(({inLayout, label}) => {
			return label[defaultLanguageId].match(query) && !inLayout;
		});
	}, [objectRelationships, query]);

	return (
		<ClayModal observer={observer}>
			<ClayModal.Header>
				{Liferay.Language.get('add-tab')}
			</ClayModal.Header>
			<ClayModal.Body>
				<ClayForm.Group>
					<label htmlFor="inputName">
						{Liferay.Language.get('label')}

						<RequiredMask />
					</label>

					<ClayInput
						id="inputName"
						onChange={(event) => setName(event.target.value)}
						type="text"
						value={name}
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<label className="mb-2">
						{Liferay.Language.get('type')}
					</label>

					<TabTypes onChangeType={setSelectedType} />
				</ClayForm.Group>

				{selectedType === TYPES.RELATIONSHIPS && (
					<ClayForm.Group>
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
							items={filteredRelationships}
							onChangeQuery={setQuery}
							onSelectItem={setSelectedRelationship}
							query={query}
							required
							title={Liferay.Language.get('relationship')}
							value={
								selectedRelationship?.label[defaultLanguageId]
							}
						>
							{({label, type}) => (
								<div className="d-flex justify-content-between">
									<div>{label[defaultLanguageId]}</div>
									<div>
										<ClayLabel displayType="secondary">
											{type}
										</ClayLabel>
									</div>
								</div>
							)}
						</AutoComplete>
					</ClayForm.Group>
				)}
			</ClayModal.Body>
			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType="secondary" onClick={onClose}>
							{Liferay.Language.get('cancel')}
						</ClayButton>
						<ClayButton
							onClick={() => {
								dispatch({
									payload: {
										name: {
											[defaultLanguageId]: name,
										},
										objectRelationshipId:
											selectedRelationship?.id ?? 0,
									},
									type: EVENT_TYPES.ADD_OBJECT_LAYOUT_TAB,
								});

								onClose();
							}}
						>
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
};

export default ModalAddObjectLayoutTab;

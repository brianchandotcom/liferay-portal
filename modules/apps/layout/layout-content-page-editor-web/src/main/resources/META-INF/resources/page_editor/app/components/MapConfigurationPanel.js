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
import ClayForm, {ClayInput, ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React, {useContext, useState, useEffect} from 'react';

import {openInfoItemSelector} from '../../core/openInfoItemSelector';
import addMappedInfoItem from '../actions/addMappedInfoItem';
import {COMPATIBLE_TYPES} from '../config/constants/compatibleTypes';
import {ConfigContext} from '../config/index';
import {DispatchContext} from '../reducers/index';
import AssetService from '../services/AssetService';

const MAPPING_SOURCE_TYPE_IDS = {
	content: 'content',
	structure: 'structure'
};

const UNMAPPED_OPTION = {
	label: `-- ${Liferay.Language.get('unmapped')} --`,
	value: 'unmapped'
};

function loadPossibleFields({config, itemSelected}, selectedSourceTypeId) {
	let promise;

	if (selectedSourceTypeId === MAPPING_SOURCE_TYPE_IDS.structure) {
		// Mocking item's data
		const data = {
			classNameId: 'classNameId',
			classTypeId: 'classTypeId'
		};

		promise = AssetService.getAvailableMappingFields({
			...data,
			config
		});
	} else if (
		selectedSourceTypeId === MAPPING_SOURCE_TYPE_IDS.content &&
		itemSelected.classNameId &&
		itemSelected.classPK
	) {
		promise = AssetService.getAvailableAssetMappingFields({
			classNameId: itemSelected.classNameId,
			classPK: itemSelected.classPK,
			config
		});
	}

	// Mocking item's type to be a image
	if (promise) {
		return promise.then(response => {
			if (Array.isArray(response)) {
				return response.filter(
					field =>
						COMPATIBLE_TYPES['image'].indexOf(field.type) !== -1
				);
			}

			return [];
		});
	}

	return Promise.resolve([]);
}

export function MapConfigurationPanel({item}) {
	const config = useContext(ConfigContext);

	const dispatch = useContext(DispatchContext);

	const [fields, setFields] = useState([]);

	const [selectedContentName, setSelectedContentName] = useState(
		Liferay.Language.get('none')
	);

	const [selectedSourceTypeId, setSelectedSourceTypeId] = useState(
		MAPPING_SOURCE_TYPE_IDS.content
	);

	function handleAssetSelectButtonClicked(callback, config) {
		openInfoItemSelector(callback, config);
	}

	function onItemSelectorChanged(itemSelected, dispatchFn) {
		loadPossibleFields({config, itemSelected}, selectedSourceTypeId).then(
			newFields => {
				setFields(newFields);
			}
		);

		if (fields.length) {
			setSelectedContentName(itemSelected.title);
			dispatchFn(addMappedInfoItem(itemSelected));
		}
	}

	useEffect(() => {
		if (
			item &&
			config.mappingFieldsURL &&
			!item.editableValues.classNameId
		) {
			setSelectedSourceTypeId(MAPPING_SOURCE_TYPE_IDS.structure);
		}
	}, [item, config.mappingFieldsURL]);

	return (
		<>
			<div className="floating-toolbar-layout-background-image-panel__asset-select form-group">
				<label htmlFor="itemSelectorInput">
					{Liferay.Language.get('content')}
				</label>
				<div className="d-flex">
					<ClayInput
						className="form-control-sm mr-2"
						id="itemSelectorInput"
						readOnly
						type="text"
						value={selectedContentName}
					/>

					<ClayButton.Group>
						<ClayButton
							displayType="secondary"
							onClick={() =>
								handleAssetSelectButtonClicked(
									selectedInfoItem => {
										onItemSelectorChanged(
											selectedInfoItem,
											dispatch
										);
									},
									config
								)
							}
							small
						>
							<ClayIcon symbol="plus" />
						</ClayButton>
					</ClayButton.Group>
				</div>
			</div>
			<ClayForm.Group>
				<label htmlFor="floatingToolbarLayoutBackgroundImagePanelFieldSelect">
					{Liferay.Language.get('field')}
				</label>
				<ClaySelectWithOption
					aria-label={Liferay.Language.get('field')}
					className="form-control-sm"
					disabled={fields && fields.length > 0 ? false : true}
					id="floatingToolbarLayoutBackgroundImagePanelFieldSelect"
					onChange={({target: {value}}) => value}
					options={
						fields && fields.length
							? [
									UNMAPPED_OPTION,
									...fields.map(({label, type}) => ({
										label,
										value: type
									}))
							  ]
							: [UNMAPPED_OPTION]
					}
				/>
			</ClayForm.Group>
		</>
	);
}

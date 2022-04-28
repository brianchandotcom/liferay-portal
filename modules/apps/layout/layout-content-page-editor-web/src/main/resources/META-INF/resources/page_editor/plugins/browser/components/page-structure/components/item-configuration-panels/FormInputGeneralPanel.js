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

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {openToast} from 'frontend-js-web';
import React, {useEffect, useMemo, useState} from 'react';

import {ALLOWED_INPUT_TYPES} from '../../../../../../app/config/constants/allowedInputTypes';
import {COMMON_STYLES_ROLES} from '../../../../../../app/config/constants/commonStylesRoles';
import {EDITABLE_TYPES} from '../../../../../../app/config/constants/editableTypes';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../app/config/constants/freemarkerFragmentEntryProcessor';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../../app/config/constants/layoutDataItemTypes';
import {config} from '../../../../../../app/config/index';
import {
	useDispatch,
	useSelector,
	useSelectorCallback,
} from '../../../../../../app/contexts/StoreContext';
import selectSegmentsExperienceId from '../../../../../../app/selectors/selectSegmentsExperienceId';
import InfoItemService from '../../../../../../app/services/InfoItemService';
import updateEditableValues from '../../../../../../app/thunks/updateEditableValues';
import {setIn} from '../../../../../../app/utils/setIn';
import Collapse from '../../../../../../common/components/Collapse';
import MappingFieldSelector from '../../../../../../common/components/MappingFieldSelector';
import {CommonStyles} from './CommonStyles';

const FIELD_ID_CONFIGURATION_KEY = 'inputFieldId';

export function FormInputGeneralPanel({item}) {
	const dispatch = useDispatch();
	const [fields, setFields] = useState(null);
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

	const layoutDataItem = useSelectorCallback(
		(state) => state.layoutData.items[item.itemId],
		[item.itemId]
	);

	const fragmentEntryLink = useSelectorCallback(
		(state) =>
			state.fragmentEntryLinks[
				layoutDataItem?.config.fragmentEntryLinkId
			],
		[layoutDataItem?.config.fragmentEntryLinkId]
	);

	const formConfiguration = useSelectorCallback(
		(state) => {
			const findFormConfiguration = (childItem) => {
				const parentItem = state.layoutData.items[childItem?.parentId];

				if (!parentItem) {
					return null;
				}

				if (parentItem.type === LAYOUT_DATA_ITEM_TYPES.form) {
					const classNameId = parentItem.config?.classNameId;

					if (classNameId && classNameId !== '0') {
						return parentItem.config;
					}
					else {
						return {};
					}
				}

				return findFormConfiguration(parentItem);
			};

			return findFormConfiguration(state.layoutData.items[item.itemId]);
		},
		[item.itemId]
	);

	const inputType = useMemo(() => {
		const element = document.createElement('div');
		element.innerHTML = fragmentEntryLink.content;

		if (element.querySelector('select')) {
			return 'select';
		}
		else if (element.querySelector('textarea')) {
			return 'textarea';
		}

		return element.querySelector('input')?.type || 'text';
	}, [fragmentEntryLink.content]);

	const handleValueSelect = (event) =>
		dispatch(
			updateEditableValues({
				editableValues: setIn(
					fragmentEntryLink.editableValues,
					[
						FREEMARKER_FRAGMENT_ENTRY_PROCESSOR,
						FIELD_ID_CONFIGURATION_KEY,
					],
					event.target.value
				),
				fragmentEntryLinkId: fragmentEntryLink.fragmentEntryLinkId,
				languageId: config.defaultLanguageId,
				segmentsExperienceId,
			})
		);

	useEffect(() => {
		const {classNameId, classTypeId} = formConfiguration || {};

		if (!classNameId || !classTypeId) {
			return;
		}

		InfoItemService.getAvailableStructureMappingFields({
			classNameId,
			classTypeId,
			onNetworkStatus: () => {},
		})
			.then((nextFields) =>
				setFields(
					nextFields
						.map((fieldset) => ({
							...fieldset,
							fields: fieldset.fields.filter((field) =>
								ALLOWED_INPUT_TYPES[field.type]?.includes(
									inputType
								)
							),
						}))
						.filter((fieldset) => fieldset.fields.length)
				)
			)
			.catch((error) => {
				if (process.env.NODE_ENV === 'development') {
					console.error(error);
				}

				openToast({
					message: Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
					type: 'danger',
				});

				setFields([]);
			});
	}, [formConfiguration, inputType]);

	if (!formConfiguration) {
		return (
			<p className="alert alert-info text-center" role="alert">
				you-need-to-put-inputs-inside-a-form-item
			</p>
		);
	}

	if (!formConfiguration.classNameId || !formConfiguration.classTypeId) {
		return (
			<p className="alert alert-info text-center" role="alert">
				you-need-to-select-a-form-item-type-first
			</p>
		);
	}

	return (
		<>
			<div className="mb-3">
				<Collapse label="form-input-options" open>
					{fields ? (
						<MappingFieldSelector
							fieldType={EDITABLE_TYPES.text}
							fields={fields}
							onValueSelect={handleValueSelect}
							value={
								fragmentEntryLink.editableValues[
									FREEMARKER_FRAGMENT_ENTRY_PROCESSOR
								][FIELD_ID_CONFIGURATION_KEY] || ''
							}
						/>
					) : (
						<ClayLoadingIndicator />
					)}
				</Collapse>
			</div>

			<CommonStyles
				commonStylesValues={item.config.styles || {}}
				item={item}
				role={COMMON_STYLES_ROLES.general}
			/>
		</>
	);
}

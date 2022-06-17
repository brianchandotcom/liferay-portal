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

import React, {useCallback, useMemo} from 'react';

import {SelectField} from '../../../../../../app/components/fragment-configuration-fields/SelectField';
import {COMMON_STYLES_ROLES} from '../../../../../../app/config/constants/commonStylesRoles';
import {FORM_MAPPING_SOURCES} from '../../../../../../app/config/constants/formMappingSources';
import {
	useDispatch,
	useSelector,
} from '../../../../../../app/contexts/StoreContext';
import selectSegmentsExperienceId from '../../../../../../app/selectors/selectSegmentsExperienceId';
import InfoItemService from '../../../../../../app/services/InfoItemService';
import updateItemConfig from '../../../../../../app/thunks/updateItemConfig';
import {CACHE_KEYS} from '../../../../../../app/utils/cache';
import useCache from '../../../../../../app/utils/useCache';
import Collapse from '../../../../../../common/components/Collapse';
import {CommonStyles} from './CommonStyles';

export function FormGeneralPanel({item}) {
	const dispatch = useDispatch();
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

	const onValueSelect = useCallback(
		(nextConfig) =>
			dispatch(
				updateItemConfig({
					itemConfig: nextConfig,
					itemId: item.itemId,
					segmentsExperienceId,
				})
			),
		[dispatch, item.itemId, segmentsExperienceId]
	);

	return (
		<>
			<FormOptions item={item} onValueSelect={onValueSelect} />

			<CommonStyles
				commonStylesValues={item.config.styles || {}}
				item={item}
				role={COMMON_STYLES_ROLES.general}
			/>
		</>
	);
}

function FormOptions({item, onValueSelect}) {
	return (
		<div className="mb-3">
			<Collapse label={Liferay.Language.get('form-options')} open>
				<OtherTypeMapping item={item} onValueSelect={onValueSelect} />
			</Collapse>
		</div>
	);
}

function OtherTypeMapping({item, onValueSelect}) {
	const itemTypes = useCache({
		fetcher: () =>
			InfoItemService.getAvailableEditPageInfoItemFormProviders(),
		key: [CACHE_KEYS.itemTypes],
	});

	const availableItemTypes = useMemo(
		() =>
			itemTypes
				? [
						{
							label: Liferay.Language.get('none'),
							value: '',
						},
						...itemTypes,
				  ]
				: [],
		[itemTypes]
	);

	const selectedItemType = availableItemTypes.find(
		({value}) => value === item.config.classNameId
	);

	return (
		<>
			{availableItemTypes.length > 0 && (
				<SelectField
					disabled={availableItemTypes.length === 0}
					field={{
						label: Liferay.Language.get('content-type'),
						name: 'classNameId',
						typeOptions: {
							validValues: availableItemTypes,
						},
					}}
					onValueSelect={(_name, classNameId) => {
						const type = availableItemTypes.find(
							({value}) => value === classNameId
						);

						return onValueSelect({
							classNameId,
							classTypeId: type?.subtypes?.[0]?.value || '0',
							formConfig: FORM_MAPPING_SOURCES.otherContentType,
						});
					}}
					value={item.config.classNameId}
				/>
			)}

			{selectedItemType?.subtypes?.length > 0 && (
				<SelectField
					disabled={availableItemTypes.length === 0}
					field={{
						label: Liferay.Language.get('subtype'),
						name: 'classTypeId',
						typeOptions: {
							validValues: selectedItemType.subtypes,
						},
					}}
					onValueSelect={(_name, classTypeId) =>
						onValueSelect({
							classNameId: item.config.classNameId,
							classTypeId,
							formConfig: FORM_MAPPING_SOURCES.otherContentType,
						})
					}
					value={item.config.classTypeId}
				/>
			)}
		</>
	);
}

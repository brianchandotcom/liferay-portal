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

import React, {useCallback, useEffect, useState} from 'react';

import {SelectField} from '../../../../../../app/components/fragment-configuration-fields/SelectField';
import {COMMON_STYLES_ROLES} from '../../../../../../app/config/constants/commonStylesRoles';
import {
	useDispatch,
	useSelector,
} from '../../../../../../app/contexts/StoreContext';
import selectSegmentsExperienceId from '../../../../../../app/selectors/selectSegmentsExperienceId';
import InfoItemService from '../../../../../../app/services/InfoItemService';
import updateItemConfig from '../../../../../../app/thunks/updateItemConfig';
import Collapse from '../../../../../../common/components/Collapse';
import {CommonStyles} from './CommonStyles';

export function FormGeneralPanel({item}) {
	const [availableItemTypes, setItemTypes] = useState([]);
	const dispatch = useDispatch();
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

	useEffect(() => {
		InfoItemService.getAvailableInfoItemFormProviders().then(
			(itemTypes) => {
				setItemTypes([
					{
						label: Liferay.Language.get('none'),
						value: '',
					},
					...itemTypes,
				]);
			}
		);
	}, []);

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

	const selectedItemType = availableItemTypes.find(
		({value}) => value === item.config.classNameId
	);

	return (
		<>
			<div className="mb-3">
				<Collapse label={Liferay.Language.get('form-options')} open>
					{availableItemTypes.length > 0 && (
						<SelectField
							disabled={availableItemTypes.length === 0}
							field={{
								label: Liferay.Language.get('item-type'),
								name: 'classNameId',
								typeOptions: {
									validValues: availableItemTypes,
								},
							}}
							onValueSelect={(_name, classNameId) =>
								onValueSelect({classNameId, classTypeId: 0})
							}
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
									validValues: [
										{
											label: Liferay.Language.get('none'),
											value: '',
										},
										...selectedItemType?.subtypes,
									],
								},
							}}
							onValueSelect={(_name, classTypeId) =>
								onValueSelect({...item.config, classTypeId})
							}
							value={item.config.classTypeId}
						/>
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

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React from 'react';

import {VIEWPORT_SIZES} from '../../../../../../app/config/constants/viewportSizes';
import {
	useDispatch,
	useSelector,
} from '../../../../../../app/contexts/StoreContext';
import selectCanUpdateCSSAdvancedOptions from '../../../../../../app/selectors/selectCanUpdateCSSAdvancedOptions';
import updateItemConfig from '../../../../../../app/thunks/updateItemConfig';
import {getResponsiveConfig} from '../../../../../../app/utils/getResponsiveConfig';
import {FieldSet} from './FieldSet';
const FIELD_SET = {
	fields: [
		{
			label: Liferay.Language.get('css-classes'),
			name: 'cssClasses',
			type: 'cssClassSelector',
		},
		{
			label: Liferay.Language.get('custom-css'),
			name: 'customCSS',
			responsive: true,
			type: 'customCSS',
		},
	],
	label: Liferay.Language.get('css'),
};

export default function CSSFieldSet({item}) {
	const canUpdateCSSAdvancedOptions = useSelector(
		selectCanUpdateCSSAdvancedOptions
	);
	const languageId = useSelector((state) => state.languageId);
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);
	const dispatch = useDispatch();

	const itemConfig = getResponsiveConfig(item.config, selectedViewportSize);

	const onValueSelect = (name, value) => {
		let nextConfig = {[name]: value};

		if (
			selectedViewportSize !== VIEWPORT_SIZES.desktop &&
			name !== 'cssClasses'
		) {
			nextConfig = {[selectedViewportSize]: {[name]: value}};
		}

		dispatch(
			updateItemConfig({
				itemConfig: nextConfig,
				itemId: item.itemId,
			})
		);
	};

	return canUpdateCSSAdvancedOptions ? (
		<div
			className={classNames({
				'mt-3': selectedViewportSize === VIEWPORT_SIZES.desktop,
			})}
		>
			<FieldSet
				fields={FIELD_SET.fields}
				item={item}
				label={FIELD_SET.label}
				languageId={languageId}
				onValueSelect={onValueSelect}
				values={itemConfig}
			/>
		</div>
	) : null;
}

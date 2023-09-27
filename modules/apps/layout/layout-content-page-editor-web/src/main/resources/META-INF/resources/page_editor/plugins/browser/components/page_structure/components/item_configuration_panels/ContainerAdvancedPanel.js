/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {CheckboxField} from '../../../../../../app/components/fragment_configuration_fields/CheckboxField';
import {HideFromSearchField} from '../../../../../../app/components/fragment_configuration_fields/HideFromSearchField';
import {SelectField} from '../../../../../../app/components/fragment_configuration_fields/SelectField';
import {VIEWPORT_SIZES} from '../../../../../../app/config/constants/viewportSizes';
import {
	useDispatch,
	useSelector,
} from '../../../../../../app/contexts/StoreContext';
import updateItemConfig from '../../../../../../app/thunks/updateItemConfig';
import {getLayoutDataItemPropTypes} from '../../../../../../prop_types/index';
import CSSFieldSet from './CSSFieldSet';

const HTML_TAGS = [
	'div',
	'header',
	'nav',
	'section',
	'article',
	'main',
	'aside',
	'footer',
];

export default function ContainerAdvancedPanel({item}) {
	const dispatch = useDispatch();
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	return (
		<>
			{selectedViewportSize === VIEWPORT_SIZES.desktop && (
				<>
					<SelectField
						className="mb-1"
						field={{
							label: Liferay.Language.get('html-tag'),
							name: 'htmlTag',
							typeOptions: {
								validValues: HTML_TAGS.map((tag) => ({
									label: tag,
									value: tag,
								})),
							},
						}}
						onValueSelect={(name, value) => {
							const itemConfig = {
								[name]: value === 'div' ? '' : value,
							};

							dispatch(
								updateItemConfig({
									itemConfig,
									itemId: item.itemId,
								})
							);
						}}
						value={item.config.htmlTag}
					/>
					<p className="small text-secondary">
						{Liferay.Language.get(
							'misusing-this-setup-might-impact-seo'
						)}
					</p>

					<CheckboxField
						className="mb-2"
						field={{
							defaultValue: '',
							label: Liferay.Language.get(
								'set-content-visibility-to-auto'
							),
							name: 'contentVisibility',
							typeOptions: {
								customValues: {
									checked: 'auto',
									unchecked: '',
								},
							},
						}}
						onValueSelect={(name, value) => {
							dispatch(
								updateItemConfig({
									itemConfig: {
										[name]: value,
									},
									itemId: item.itemId,
								})
							);
						}}
						value={item.config.contentVisibility}
					/>
				</>
			)}

			<HideFromSearchField item={item} />

			<CSSFieldSet item={item} />
		</>
	);
}

ContainerAdvancedPanel.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
};

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
import {ItemSelectorDialog} from 'frontend-js-web';
import React, {useContext} from 'react';

import {ConfigContext} from '../config/index';

export function AssetSelectionPanel({
	backgroundImageTitle = Liferay.Language.get('none'),
	onItemSelectorChanged
}) {
	const {infoItemSelectorURL, portletNamespace} = useContext(ConfigContext);

	const openInfoItemSelector = (callback, destroyedCallback = null) => {
		const itemSelectorDialog = new ItemSelectorDialog({
			eventName: `${portletNamespace}selectInfoItem`,
			singleSelect: true,
			title: Liferay.Language.get('select'),
			url: infoItemSelectorURL
		});

		itemSelectorDialog.on('selectedItemChange', event => {
			const selectedItem = event.selectedItem;

			if (selectedItem && selectedItem.value) {
				const infoItem = JSON.parse(selectedItem.value);

				callback({
					className: infoItem.className,
					classNameId: infoItem.classNameId,
					classPK: infoItem.classPK,
					title: infoItem.title
				});
			}
		});

		itemSelectorDialog.on('visibleChange', event => {
			if (event.newVal === false && destroyedCallback) {
				destroyedCallback();
			}
		});

		itemSelectorDialog.open();
	};

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
						value={backgroundImageTitle}
					/>

					<ClayButton.Group>
						<ClayButton
							displayType="secondary"
							onClick={() => {
								openInfoItemSelector(selectedInfoItem => {
									onItemSelectorChanged(selectedInfoItem);
								});
							}}
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
					disabled
					id="floatingToolbarLayoutBackgroundImagePanelFieldSelect"
					onChange={({target: {value}}) => value}
					options={[
						{
							label: Liferay.Language.get('manual-selection'),
							value: 'manual_selection'
						},
						{
							label: Liferay.Language.get('content-mapping'),
							value: 'content_mapping'
						}
					]}
				/>
			</ClayForm.Group>
		</>
	);
}

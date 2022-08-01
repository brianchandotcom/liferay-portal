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

import ClayEmptyState from '@clayui/empty-state';
import {ReactPortal} from '@liferay/frontend-js-react-web';
import React from 'react';

import {config} from '../config/index';

export default function ItemConfigurationSidebar() {
	return (
		<ReactPortal className="cadmin">
			<div className="page-editor__item-configuration-sidebar">
				<div className="p-4 text-center">
					<ClayEmptyState
						description={Liferay.Language.get(
							'select-a-page-element-to-activate-this-panel'
						)}
						imgSrc={`${config.imagesPath}/no_item.svg`}
						small
						title={Liferay.Language.get('no-selection-yet')}
					/>
				</div>
			</div>
		</ReactPortal>
	);
}

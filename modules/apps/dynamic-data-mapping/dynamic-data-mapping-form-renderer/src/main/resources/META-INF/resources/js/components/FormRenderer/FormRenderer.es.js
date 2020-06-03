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

import './PageRenderer.soy';

import {ClayIconSpriteContext} from '@clayui/icon';
import classNames from 'classnames';
import Soy from 'metal-soy';
import React from 'react';
import {DndProvider} from 'react-dnd';
import HTML5Backend from 'react-dnd-html5-backend';

import {getConnectedReactComponentAdapter} from '../../util/ReactComponentAdapter.es';
import PageRenderer from '../PageRenderer/index';
import templates from './index.soy';

function getDisplayableValue({containerId, readOnly, viewMode}) {
	return (
		readOnly || !viewMode || document.getElementById(containerId) !== null
	);
}

function FormRenderer({
	activePage = 0,
	cancelLabel = Liferay.Language.get('cancel'),
	containerId,
	displayable: initialDisplayableValue,
	editable,
	editingLanguageId = themeDisplay.getLanguageId(),
	pages = [],
	paginationMode,
	readOnly,
	submitLabel = Liferay.Language.get('submit'),
	view,
	viewMode,
	...otherProps
}) {
	const currentPaginationMode = paginationMode ?? 'wizard';
	const displayable =
		initialDisplayableValue ||
		getDisplayableValue({containerId, readOnly, viewMode});
	const total = Array.isArray(pages) ? pages.length : 0;

	if (!displayable) {
		return null;
	}

	return (
		<div className={view === 'fieldSets' ? 'sheet' : ''}>
			<div
				className={classNames(
					'lfr-ddm-form-container position-relative',
					{
						'ddm-user-view-content': !editable,
					}
				)}
			>
				{pages.map((page, index) => (
					<PageRenderer
						{...otherProps}
						activePage={activePage}
						cancelLabel={cancelLabel}
						editable={editable}
						editingLanguageId={editingLanguageId}
						key={index}
						pageIndex={index}
						pages={pages}
						paginationMode={currentPaginationMode}
						readOnly={readOnly}
						submitLabel={submitLabel}
						total={total}
						view={view}
						viewMode={viewMode}
					/>
				))}
			</div>
		</div>
	);
}

const FormRendererProxy = ({instance, ...otherProps}) => (
	<DndProvider backend={HTML5Backend} context={window}>
		<ClayIconSpriteContext.Provider value={otherProps.spritemap}>
			<FormRenderer
				{...otherProps}
				onBlur={(event) => instance.emit('fieldBlurred', event)}
				onChange={(event) => instance.emit('fieldEdited', event)}
				onFocus={(event) => instance.emit('fieldFocused', event)}
			/>
		</ClayIconSpriteContext.Provider>
	</DndProvider>
);

const ReactComponentAdapter = getConnectedReactComponentAdapter(
	FormRendererProxy
);

Soy.register(ReactComponentAdapter, templates);

export default ReactComponentAdapter;

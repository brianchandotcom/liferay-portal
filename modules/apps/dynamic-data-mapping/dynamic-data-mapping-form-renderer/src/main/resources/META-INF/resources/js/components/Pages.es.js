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
import React, {useRef} from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import {useFieldTypesResource} from '../hooks/useResource.es';
import {ActionsProvider} from './Actions.es';
import Page from './PageRenderer/index';

function getDisplayableValue({containerId, readOnly, viewMode}) {
	return (
		readOnly || !viewMode || document.getElementById(containerId) !== null
	);
}

const Pages = React.forwardRef(
	(
		{
			activePage = 0,
			cancelLabel = Liferay.Language.get('cancel'),
			containerId,
			displayable: initialDisplayableValue,
			editable,
			editingLanguageId = themeDisplay.getLanguageId(),
			focusedField,
			pages = [],
			paginationMode = 'wizard',
			readOnly,
			submitLabel = Liferay.Language.get('submit'),
			view,
			viewMode,
			...otherProps
		},
		ref
	) => {
		const {resource: fieldTypes} = useFieldTypesResource();

		const containerFallbackRef = useRef();

		const displayable =
			initialDisplayableValue ||
			getDisplayableValue({containerId, readOnly, viewMode});

		if (!displayable) {
			return null;
		}

		const containerElementRef = ref ?? containerFallbackRef;

		return (
			<div
				className={classNames({sheet: view === 'fieldSets'})}
				ref={containerElementRef}
			>
				<div
					className={classNames(
						'lfr-ddm-form-container position-relative',
						{
							'ddm-user-view-content': !editable,
						}
					)}
				>
					<ActionsProvider focusedFieldId={focusedField?.fieldName}>
						{pages.map((page, index) => (
							<Page
								{...otherProps}
								activePage={activePage}
								cancelLabel={cancelLabel}
								containerElement={containerElementRef}
								editable={editable}
								editingLanguageId={editingLanguageId}
								fieldTypes={fieldTypes}
								key={index}
								page={page}
								pageIndex={index}
								pages={pages}
								paginationMode={paginationMode}
								readOnly={readOnly}
								submitLabel={submitLabel}
								total={pages.length}
								view={view}
								viewMode={viewMode}
							/>
						))}
					</ActionsProvider>
				</div>
			</div>
		);
	}
);

Pages.displayName = 'Pages';

const PagesWithProviders = React.forwardRef((props, ref) => (
	<DndProvider backend={HTML5Backend} context={window}>
		<ClayIconSpriteContext.Provider value={props.spritemap}>
			<Pages {...props} ref={ref} />
		</ClayIconSpriteContext.Provider>
	</DndProvider>
));

PagesWithProviders.displayName = 'PagesWithProviders';

export default React.memo(PagesWithProviders);

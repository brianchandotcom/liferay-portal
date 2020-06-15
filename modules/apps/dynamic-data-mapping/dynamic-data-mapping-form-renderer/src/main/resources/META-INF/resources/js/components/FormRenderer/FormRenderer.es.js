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

import {useResource} from '@clayui/data-provider';
import {ClayIconSpriteContext} from '@clayui/icon';
import classNames from 'classnames';
import React, {useRef} from 'react';
import {DndProvider} from 'react-dnd';
import HTML5Backend from 'react-dnd-html5-backend';

import {useStorage} from '../../hooks/useStorage.es';
import PageRenderer from '../PageRenderer/index';

function getDisplayableValue({containerId, readOnly, viewMode}) {
	return (
		readOnly || !viewMode || document.getElementById(containerId) !== null
	);
}

const endpoint = `${window.location.origin}/o/dynamic-data-mapping-form-field-types`;

const HEADERS = {
	Accept: 'application/json',
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
	'Content-Type': 'application/json',
};

const FormRenderer = React.forwardRef(
	(
		{
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
		},
		ref
	) => {
		const storage = useStorage();
		const {resource: fieldTypes} = useResource({
			fetchOptions: {
				headers: HEADERS,
			},
			fetchPolicy: 'cache-first',
			link: endpoint,
			storage,
			variables: {
				p_auth: Liferay.authToken,
			},
		});

		const containerFallbackRef = useRef();

		const currentPaginationMode = paginationMode ?? 'wizard';
		const displayable =
			initialDisplayableValue ||
			getDisplayableValue({containerId, readOnly, viewMode});
		const total = Array.isArray(pages) ? pages.length : 0;

		if (!displayable) {
			return null;
		}

		const containerElementRef = ref ?? containerFallbackRef;

		return (
			<div
				className={view === 'fieldSets' ? 'sheet' : ''}
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
					{pages.map((page, index) => (
						<PageRenderer
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
);

FormRenderer.displayName = 'FormRenderer';

const FormRendererWithProviders = React.forwardRef((props, ref) => (
	<DndProvider backend={HTML5Backend} context={window}>
		<ClayIconSpriteContext.Provider value={props.spritemap}>
			<FormRenderer {...props} ref={ref} />
		</ClayIconSpriteContext.Provider>
	</DndProvider>
));

FormRendererWithProviders.displayName = 'FormRendererWithProviders';

export default FormRendererWithProviders;

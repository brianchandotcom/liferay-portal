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

import classNames from 'classnames';
import React, {useRef} from 'react';

import {useConfig} from '../hooks/useConfig.es';
import {useFormState} from '../hooks/useForm.es';
import {useFieldTypesResource} from '../hooks/useResource.es';
import {ActionsProvider} from './Actions.es';
import Page from './PageRenderer/Page.es';

function getDisplayableValue({containerId, readOnly, viewMode}) {
	return (
		readOnly || !viewMode || document.getElementById(containerId) !== null
	);
}

const Pages = React.forwardRef(
	({editable, fieldActions, ...otherProps}, ref) => {
		const {containerId, portletNamespace, view} = useConfig();
		const {
			activePage,
			displayable: initialDisplayableValue,
			editingLanguageId,
			focusedField,
			forceAriaUpdate,
			invalidFormMessage,
			pages,
			paginationMode,
			persistDefaultValues,
			readOnly,
			viewMode,
		} = useFormState();

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
				<input
					key={portletNamespace + 'persistDefaultValues'}
					name={portletNamespace + 'persistDefaultValues'}
					type="hidden"
					value={persistDefaultValues}
				/>

				<div
					className={classNames(
						'lfr-ddm-form-container position-relative',
						{
							'ddm-user-view-content': !editable,
						}
					)}
				>
					<ActionsProvider
						actions={fieldActions}
						focusedFieldId={focusedField?.fieldName}
					>
						{pages.map((page, index) => (
							<Page
								{...otherProps}
								activePage={activePage}
								containerElement={containerElementRef}
								editable={editable}
								editingLanguageId={editingLanguageId}
								fieldTypes={fieldTypes}
								forceAriaUpdate={forceAriaUpdate}
								invalidFormMessage={invalidFormMessage}
								key={page.id}
								page={page}
								pageIndex={index}
								pages={pages}
								paginationMode={paginationMode}
								portletNamespace={portletNamespace}
								readOnly={readOnly}
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

export default React.memo(Pages);

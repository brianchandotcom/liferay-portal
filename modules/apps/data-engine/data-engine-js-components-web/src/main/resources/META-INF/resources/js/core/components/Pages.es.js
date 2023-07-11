/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			showPartialResultsToRespondents,
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
				aria-labelledby={`${portletNamespace}header`}
				className={classNames({sheet: view === 'fieldSets'})}
				ref={containerElementRef}
				role="group"
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
								key={index}
								page={page}
								pageIndex={index}
								pages={pages}
								paginationMode={paginationMode}
								portletNamespace={portletNamespace}
								readOnly={readOnly}
								showPartialResultsToRespondents={
									showPartialResultsToRespondents
								}
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

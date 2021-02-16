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

import React, {useCallback, useEffect, useRef} from 'react';

import Pages from '../../core/components/Pages.es';
import {useConfig} from '../../core/hooks/useConfig.es';
import {useFormState} from '../../core/hooks/useForm.es';
import {FormInfo} from './components/FormInfo.es';
import {ManagementToolbar} from './components/ManagementToolbar.es';
import {MetalSidebarAdapter} from './components/MetalSidebarAdapter.es';
import {TranslationManager} from './components/TranslationManager.es';

export const FormBuilder = () => {
	const {portletNamespace} = useConfig();
	const {
		activePage,
		editingLanguageId,
		fieldSets,
		focusedField,
		pages,
		rules,
	} = useFormState();

	const sidebarRef = useRef(null);

	useEffect(() => {
		if (Object.keys(focusedField).length && sidebarRef.current) {
			sidebarRef.current.current.open();
		}
	}, [focusedField]);

	useEffect(() => {
		const currentPage = pages[activePage];
		const isEmpty = currentPage.rows[0].columns[0].fields.length === 0;

		if (isEmpty && sidebarRef.current) {
			sidebarRef.current.current.open();
		}

		// We only want to cause this useEffect to be called again if the
		// number of pages changes and not the page data.
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [pages.length, activePage]);

	const onOpenSidebar = useCallback(() => {
		if (sidebarRef.current) {
			sidebarRef.current.current.open();
		}
	}, []);

	return (
		<>
			<ManagementToolbar
				onPlusClick={onOpenSidebar}
				portletNamespace={portletNamespace}
			/>
			<TranslationManager />
			<FormInfo />
			<div className="ddm-form-builder">
				<div className="container ddm-paginated-builder top">
					<div className="ddm-form-builder-wrapper moveable resizeable">
						<div className="container ddm-form-builder">
							<Pages editable={true} />
						</div>
					</div>
				</div>
				<MetalSidebarAdapter
					editingLanguageId={editingLanguageId}
					fieldSets={fieldSets}
					focusedField={focusedField}
					ref={sidebarRef}
					rules={rules}
				/>
			</div>
		</>
	);
};

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

import ClayIcon from '@clayui/icon';
import core from 'metal';
import React from 'react';

import {PageProvider} from '../../hooks/usePage.es';
import {PagesVisitor} from '../../util/visitors.es';
import * as DefaultVariant from './DefaultVariant.es';
import * as EditablePageHeader from './EditablePageHeader.es';
import * as EditorVariant from './EditorVariant.es';
import {Layout} from './Layout.es';
import * as MultiPages from './MultiPagesVariant.es';
import * as Paginated from './PaginatedVariant.es';
import * as SuccessPage from './SuccessVariant.es';
import * as Tabbed from './TabbedVariant.es';
import * as Wizard from './WizardVariant.es';

const LAYOUT_TYPES = {
	MULTI_PAGES: 'multi_pages',
	PAGINATED: 'paginated',
	SINGLE_PAGE: 'single-page',
	SUCCESS: 'success',
	TABBED: 'tabbed',
	WIZARD: 'wizard',
};

const LAYOUT_COMPONENTS_TYPES = {
	[LAYOUT_TYPES.MULTI_PAGES]: MultiPages,
	[LAYOUT_TYPES.PAGINATED]: Paginated,
	[LAYOUT_TYPES.SINGLE_PAGE]: {
		Container: DefaultVariant.Container,
	},
	[LAYOUT_TYPES.SUCCESS]: SuccessPage,
	[LAYOUT_TYPES.TABBED]: Tabbed,
	[LAYOUT_TYPES.WIZARD]: Wizard,
};

const PAGE_HEADER_TYPES = {
	EDITABLE: 'editable',
};

const PAGE_HEADER_COMPONENT_TYPE = {
	[PAGE_HEADER_TYPES.EDITABLE]: EditablePageHeader.PageHeader,
};

const DDM_FORM_PORTLET_NAMESPACE =
	'_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormPortlet_';

const isEmptyPage = ({rows}) => {
	let empty = false;

	if (!rows || !rows.length) {
		empty = true;
	}
	else {
		empty = !rows.some(({columns}) => {
			let hasFields = true;

			if (!columns) {
				hasFields = false;
			}
			else {
				hasFields = columns.some((column) => column.fields.length);
			}

			return hasFields;
		});
	}

	return empty;
};

const normalizePage = (page, editingLanguageId) => {
	if (core.isObject(page.description)) {
		page = {
			...page,
			description: page.description[editingLanguageId],
		};
	}
	if (core.isObject(page.title)) {
		page = {
			...page,
			title: page.title[editingLanguageId],
		};
	}

	return page;
};

const getVariant = ({page, pages, paginationMode, view, viewMode}) => {
	if (
		page.contentRenderer &&
		page.contentRenderer !== null &&
		page.contentRenderer !== ''
	) {
		return page.contentRenderer;
	}
	else if (paginationMode === null || paginationMode === '') {
		if (pages.length > 1) {
			return LAYOUT_TYPES.WIZARD;
		}
		else {
			return LAYOUT_TYPES.SINGLE_PAGE;
		}
	}
	else if (
		paginationMode === LAYOUT_TYPES.WIZARD &&
		!viewMode &&
		view === 'formBuilder'
	) {
		return LAYOUT_TYPES.MULTI_PAGES;
	}
	else {
		return paginationMode;
	}
};

/**
 * Renderer is the main component for dealing with Forms `pages` structure,
 * you can use the renderer to render fields from different layouts.
 *
 * Renderer supports some rendering modes by default
 * - multi_pages
 * - paginated
 * - single-page
 * - success
 * - tabbed
 * - wizard
 *
 * You can replace all major components or just a few to change the layout
 * structure, consider the following components
 * - Container
 * - Page
 * - Rows
 * - Row
 * - Column
 *
 * Use the `overrides` API to pass the components you want to replace,
 * an example of Page.
 * <Renderer
 * 	overrides={{
 * 	  Page: ({children}) => children
 * 	}}
 * />
 */
const Renderer = ({
	activePage = 0,
	editable = false,
	editingLanguageId,
	forceAriaUpdate,
	invalidFormMessage,
	overrides = {},
	page: defaultPage,
	pageIndex = 0,
	pages,
	paginationMode,
	portletNamespace,
	readOnly,
	showSubmitButton,
	strings,
	submitLabel,
	view,
	viewMode,
}) => {
	const empty = isEmptyPage(defaultPage);
	const page = normalizePage(defaultPage, editingLanguageId);

	const variant = getVariant({page, pages, paginationMode, view, viewMode});
	const variantComponents = LAYOUT_COMPONENTS_TYPES[variant] || {};

	const formBuilderVariant = editable ? EditorVariant : {};

	const Components = {
		...DefaultVariant,
		...formBuilderVariant,
		...variantComponents,
		...overrides,
	};

	const Header =
		PAGE_HEADER_COMPONENT_TYPE[page.headerRenderer] ||
		Components.PageHeader;

	let hasFieldRequired = false;

	if (pages?.[activePage]) {
		const visitor = new PagesVisitor([pages[activePage]]);

		visitor.mapFields((field) => {
			if (field.required) {
				hasFieldRequired = true;
			}
		});
	}

	const isDDMFormPortletNamespace =
		DDM_FORM_PORTLET_NAMESPACE === portletNamespace;

	return (
		<Components.Container
			activePage={activePage}
			editable={editable}
			empty={empty}
			page={page}
			pageIndex={pageIndex}
			pages={pages}
			readOnly={readOnly}
			showSubmitButton={showSubmitButton}
			strings={strings}
			submitLabel={submitLabel}
		>
			<Components.Page
				activePage={activePage}
				editable={editable}
				empty={empty}
				forceAriaUpdate={forceAriaUpdate}
				header={
					variant === LAYOUT_TYPES.SINGLE_PAGE ? null : (
						<Header
							description={page.description}
							placeholder={page.placeholder}
							title={page.title}
						/>
					)
				}
				invalidFormMessage={invalidFormMessage}
				page={page}
				pageIndex={pageIndex}
			>
				{hasFieldRequired && isDDMFormPortletNamespace && (
					<p aria-hidden="true" className="text-secondary">
						<span className="c-mr-1 reference-mark">
							<ClayIcon symbol="asterisk" />
						</span>
						{Liferay.Language.get('indicates-required-fields')}
					</p>
				)}
				<Layout
					components={Components}
					editable={editable}
					rows={page.rows}
				/>
			</Components.Page>
		</Components.Container>
	);
};

export default (props) => (
	<PageProvider value={props}>
		<Renderer {...props} />
	</PageProvider>
);

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

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import React from 'react';

import {PAGE_TYPES, usePage} from '../../hooks/usePage.es';
import {setValue} from '../../util/i18n.es';

export const Container = ({children, pages, strings}) => {
	const {
		dispatch,
		store: {editingLanguageId},
	} = usePage();

	return (
		<div className="page">
			<div className="ddm-page-success-layout sheet simple-page">
				<div className="form-builder-layout">
					<h5 className="pagination">
						{strings != null ? strings['success-page'] : ''}
					</h5>

					{children}
				</div>
			</div>

			<div className="ddm-paginated-builder-dropdown">
				<ClayDropDownWithItems
					items={[
						{
							label: Liferay.Language.get('remove-success-page'),
							onClick: () => {
								dispatch({
									payload: {
										body: {[editingLanguageId]: ''},
										enabled: false,
										title: {[editingLanguageId]: ''},
									},
									type: PAGE_TYPES.SUCCESS_CHANGED,
								});
								dispatch({
									payload: pages.length - 1,
									type: PAGE_TYPES.CHANGE_ACTIVE_PAGE,
								});
							},
						},
					]}
					trigger={<ClayButtonWithIcon symbol="ellipsis-v" />}
				/>
			</div>
		</div>
	);
};

export const Page = ({page}) => {
	const {dispatch, store} = usePage();
	const {successPageSettings} = page;
	const {editingLanguageId} = store;

	const {body, title} = {
		body:
			(successPageSettings.body &&
				successPageSettings.body[editingLanguageId]) ||
			'',
		title:
			(successPageSettings.title &&
				successPageSettings.title[editingLanguageId]) ||
			'',
	};

	const onChange = (event, setting) => {
		dispatch({
			payload: setValue(
				successPageSettings,
				editingLanguageId,
				setting,
				event.target.value
			),
			type: PAGE_TYPES.SUCCESS_CHANGED,
		});
	};

	return (
		<div className="active ddm-form-page form-builder-success-page lfr-ddm-form-page">
			<input
				className="form-builder-page-header-title form-control p-0"
				onChange={(event) => onChange(event, 'title')}
				type="text"
				value={title}
			/>

			<input
				className="form-builder-page-header-description form-control p-0"
				onChange={(event) => onChange(event, 'body')}
				type="text"
				value={body}
			/>
		</div>
	);
};

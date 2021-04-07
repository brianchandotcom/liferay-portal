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
import ClayLayout from '@clayui/layout';
import {usePrevious} from '@liferay/frontend-js-react-web';
import React, {useEffect, useState} from 'react';

import {EVENT_TYPES as CORE_EVENT_TYPES} from '../../../core/actions/eventTypes.es';
import {useForm, useFormState} from '../../../core/hooks/useForm.es';
import {setValue} from '../../../utils/i18n.es';
import {EVENT_TYPES} from '../eventTypes.es';

export const Container = ({children, pages, strings = {}}) => {
	const {editingLanguageId} = useFormState();
	const dispatch = useForm();

	return (
		<div className="page">
			<ClayLayout.Sheet className="ddm-page-success-layout simple-page">
				<div className="form-builder-layout">
					<h5 className="pagination">
						{strings['success-page'] ??
							Liferay.Language.get('success-page')}
					</h5>

					{children}
				</div>
			</ClayLayout.Sheet>

			<div className="ddm-paginated-builder-dropdown">
				<ClayDropDownWithItems
					className="dropdown-action"
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
									type: EVENT_TYPES.SUCCESS_PAGE,
								});
								dispatch({
									payload: {activePage: pages.length - 1},
									type: CORE_EVENT_TYPES.PAGE.CHANGE,
								});
							},
						},
					]}
					trigger={
						<ClayButtonWithIcon
							displayType="unstyled"
							symbol="ellipsis-v"
							title={Liferay.Language.get('page-options')}
						/>
					}
				/>
			</div>
		</div>
	);
};

Container.displayName = 'SuccessVariant.Container';

export const Page = ({page: {successPageSettings}}) => {
	const {defaultLanguageId, editingLanguageId} = useFormState();

	const dispatch = useForm();

	const prevEditingLanguageId = usePrevious(editingLanguageId);

	const {initialBody, initialTitle} = {
		initialBody:
			(successPageSettings.body &&
				(successPageSettings.body[editingLanguageId] ||
					successPageSettings.body[defaultLanguageId])) ||
			'',
		initialTitle:
			(successPageSettings.title &&
				(successPageSettings.title[editingLanguageId] ||
					successPageSettings.title[defaultLanguageId])) ||
			'',
	};

	const [body, setBody] = useState(initialBody);
	const [title, setTitle] = useState(initialTitle);

	useEffect(() => {
		if (prevEditingLanguageId !== editingLanguageId) {
			setBody(initialBody);
			setTitle(initialTitle);
		}
	}, [editingLanguageId, initialBody, initialTitle, prevEditingLanguageId]);

	const onChange = (event, setting) => {
		dispatch({
			payload: setValue(
				successPageSettings,
				editingLanguageId,
				setting,
				event.target.value
			),
			type: EVENT_TYPES.SUCCESS_PAGE,
		});
	};

	return (
		<div className="active ddm-form-page form-builder-success-page lfr-ddm-form-page">
			<input
				className="form-builder-page-header-title form-control p-0"
				maxLength="120"
				onChange={(event) => {
					setTitle(event.target.value);
					onChange(event, 'title');
				}}
				type="text"
				value={title}
			/>

			<input
				className="form-builder-page-header-description form-control p-0"
				maxLength="120"
				onChange={(event) => {
					setBody(event.target.value);
					onChange(event, 'body');
				}}
				type="text"
				value={body}
			/>
		</div>
	);
};

Page.displayName = 'SuccessVariant.Page';

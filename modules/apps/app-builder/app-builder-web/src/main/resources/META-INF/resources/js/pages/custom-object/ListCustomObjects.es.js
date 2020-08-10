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

import {createResourceURL, fetch} from 'frontend-js-web';
import React, {useContext, useEffect, useRef, useState} from 'react';

import {AppContext} from '../../AppContext.es';
import Button from '../../components/button/Button.es';
import {useKeyDown} from '../../hooks/index.es';
import isClickOutside from '../../utils/clickOutside.es';
import {addItem, parseResponse} from '../../utils/client.es';
import {errorToast, successToast} from '../../utils/toast.es';
import ListObjects from '../object/ListObjects.es';
import CustomObjectPopover from './CustomObjectPopover.es';

export default ({history}) => {
	const {basePortletURL, baseResourceURL, namespace} = useContext(AppContext);
	const addButtonRef = useRef();
	const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();
	const emptyStateButtonRef = useRef();
	const popoverRef = useRef();

	const [alignElement, setAlignElement] = useState(addButtonRef.current);
	const [isPopoverVisible, setPopoverVisible] = useState(false);

	const confirmDelete = ({id: dataDefinitionId}) => {
		return new Promise((resolve, reject) => {
			const confirmed = confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			);

			if (confirmed) {
				fetch(
					createResourceURL(baseResourceURL, {
						p_p_resource_id: '/objects/delete_data_definition',
					}),
					{
						body: new URLSearchParams(
							Liferay.Util.ns(namespace, {dataDefinitionId})
						),
						method: 'POST',
					}
				)
					.then(parseResponse)
					.then(() => resolve(true))
					.then(() =>
						successToast(
							Liferay.Language.get(
								'the-item-was-deleted-successfully'
							)
						)
					)
					.catch(({errorMessage}) => {
						errorToast(errorMessage);
						reject(true);
					});
			}
			else {
				resolve(false);
			}
		});
	};

	const onClickAddButton = ({currentTarget}) => {
		setAlignElement(currentTarget);

		if (isPopoverVisible && alignElement !== currentTarget) {
			return;
		}

		setPopoverVisible(!isPopoverVisible);
	};

	const onCancel = () => setPopoverVisible(false);

	const onSubmit = ({isAddFormView, name}) => {
		const addURL = `/o/data-engine/v2.0/data-definitions/by-content-type/app-builder`;

		return addItem(addURL, {
			availableLanguageIds: [defaultLanguageId],
			dataDefinitionFields: [],
			defaultLanguageId,
			name: {
				[defaultLanguageId]: name,
			},
		})
			.then(({id}) => {
				if (isAddFormView) {
					Liferay.Util.navigate(
						Liferay.Util.PortletURL.createRenderURL(
							basePortletURL,
							{
								dataDefinitionId: id,
								mvcRenderCommandName: '/edit_form_view',
								newCustomObject: true,
							}
						)
					);
				}
				else {
					history.push(`/custom-object/${id}/form-views/`);
				}
			})
			.catch((error) => {
				errorToast(error.message);

				return Promise.reject();
			});
	};

	useEffect(() => {
		const handler = ({target}) => {
			const isOutside = isClickOutside(
				target,
				addButtonRef.current,
				emptyStateButtonRef.current,
				popoverRef.current
			);

			if (isOutside) {
				setPopoverVisible(false);
			}
		};

		window.addEventListener('click', handler);

		return () => window.removeEventListener('click', handler);
	}, [addButtonRef, emptyStateButtonRef, popoverRef]);

	useKeyDown(() => {
		if (isPopoverVisible) {
			setPopoverVisible(false);
		}
	}, 27);

	return (
		<>
			<ListObjects
				history={history}
				listViewProps={{
					actions: [
						{
							action: confirmDelete,
							name: Liferay.Language.get('delete'),
						},
					],
					addButton: () => (
						<div ref={addButtonRef}>
							<Button
								className="nav-btn nav-btn-monospaced"
								onClick={onClickAddButton}
								symbol="plus"
								tooltip={Liferay.Language.get(
									'new-custom-object'
								)}
							/>
						</div>
					),
					emptyState: {
						button: () => (
							<Button
								displayType="secondary"
								onClick={onClickAddButton}
								ref={emptyStateButtonRef}
							>
								{Liferay.Language.get('new-custom-object')}
							</Button>
						),
						description: Liferay.Language.get(
							'custom-objects-define-the-types-of-data-your-business-application-needs'
						),
						title: Liferay.Language.get(
							'there-are-no-custom-objects-yet'
						),
					},
					endpoint: `/o/data-engine/v2.0/data-definitions/by-content-type/app-builder`,
				}}
				objectType="custom-object"
			/>

			<CustomObjectPopover
				alignElement={alignElement}
				onCancel={onCancel}
				onSubmit={onSubmit}
				ref={popoverRef}
				visible={isPopoverVisible}
			/>
		</>
	);
};

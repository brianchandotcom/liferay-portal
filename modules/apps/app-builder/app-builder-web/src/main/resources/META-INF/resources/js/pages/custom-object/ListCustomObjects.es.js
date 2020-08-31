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
import useQuery from '../../hooks/useQuery.es';
import isClickOutside from '../../utils/clickOutside.es';
import {addItem, parseResponse, updateItem} from '../../utils/client.es';
import {errorToast, successToast} from '../../utils/toast.es';
import {getValidName} from '../../utils/utils.es';
import ListObjects from '../object/ListObjects.es';
import CustomObjectPopover from './CustomObjectPopover.es';

export default ({history}) => {
	const {basePortletURL, baseResourceURL, namespace} = useContext(AppContext);
	const [editMode, setEditMode] = useState(null);
	const addButtonRef = useRef();
	const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();
	const emptyStateButtonRef = useRef();
	const popoverRef = useRef();

	const [alignElement, setAlignElement] = useState(addButtonRef.current);
	const [isPopoverVisible, setPopoverVisible] = useState(false);
	const [{showCustomObjectPopover}] = useQuery(history);

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

	const onCancelRenameAction = () => {
		return Promise.resolve(setEditMode(null));
	};

	const onRenameAction = ({originalItem}, value, refetch) => {
		updateItem(`/o/data-engine/v2.0/data-definitions/${originalItem.id}`, {
			...originalItem,
			name: {
				[originalItem.defaultLanguageId]: getValidName(
					Liferay.Language.get('untitled-custom-object'),
					value
				),
			},
		})
			.then(refetch)
			.then(onCancelRenameAction)
			.then(() =>
				successToast(
					Liferay.Language.get('the-object-was-renamed-successfully')
				)
			)
			.catch(({errorMessage}) => {
				errorToast(errorMessage);
			});
	};

	const renameAction = (item, refetch) => {
		const {id} = item;

		return new Promise((resolve) =>
			resolve(
				setEditMode({
					id,
					onCancel: onCancelRenameAction,
					onSave: (value) => onRenameAction(item, value, refetch),
				})
			)
		);
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
				[defaultLanguageId]: getValidName(
					Liferay.Language.get('untitled-custom-object'),
					name
				),
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
					successToast(
						Liferay.Language.get(
							'the-object-was-created-successfully'
						)
					);
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

	useEffect(() => {
		if (addButtonRef.current && showCustomObjectPopover) {
			setAlignElement(addButtonRef.current);
			setPopoverVisible(true);
		}
	}, [addButtonRef, showCustomObjectPopover]);

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
							name: 'divider',
						},
						{
							action: renameAction,
							name: Liferay.Language.get('rename'),
						},
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
					editMode,
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

/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {ClayCheckbox} from '@clayui/form';
import ClayManagementToolbar from '@clayui/management-toolbar';
import React, {useEffect, useState, useContext, useCallback} from 'react';

import PromisesResolver from '../../../../../shared/components/request/PromisesResolver.es';
import {sub} from '../../../../../shared/util/lang.es';
import {ModalContext} from '../../ModalContext.es';

const Header = ({items, totalCount}) => {
	const {bulkModal, setBulkModal} = useContext(ModalContext);

	const [selectAll, setSelectAll] = useState(false);
	const [toolbarOptions, setToolbarOptions] = useState({
		active: false,
		indeterminateCheckbox: false
	});

	const {selectedTasks} = bulkModal;

	useEffect(() => {
		const active = selectedTasks.length > 0;
		const label = !active ? Liferay.Language.get('select-all') : '';

		const selectedOnPage = selectedTasks.filter(item =>
			items.find(({id}) => id === item.id)
		);
		const allPageSelected = items.length === selectedOnPage.length;

		setSelectAll(totalCount > 0 && totalCount === selectedTasks.length);

		setToolbarOptions({
			active,
			checked: items.length > 0 && allPageSelected,
			indeterminateCheckbox: !allPageSelected && !selectAll && active,
			label: selectAll ? Liferay.Language.get('all-selected') : label
		});
	}, [items, selectedTasks, selectAll, totalCount]);

	const handleSelectAll = useCallback(
		checked => {
			let updatedItems;

			if (checked) {
				updatedItems = [
					...selectedTasks,
					...items.filter(
						item => !selectedTasks.find(({id}) => item.id === id)
					)
				];
			} else {
				updatedItems = selectedTasks.filter(
					item => !items.find(({id}) => item.id === id)
				);
			}

			setBulkModal({
				...bulkModal,
				selectedTasks: updatedItems
			});
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[items, selectedTasks]
	);

	return (
		<PromisesResolver.Resolved>
			<ClayManagementToolbar
				active={toolbarOptions.active}
				className={`${
					!toolbarOptions.active ? 'border-bottom' : ''
				} mb-0`}
			>
				<ClayManagementToolbar.ItemList>
					<ClayManagementToolbar.Item
						className="ml-3"
						style={{padding: '1.2rem 0'}}
					>
						<ClayCheckbox
							checked={toolbarOptions.checked}
							data-testid="selectAllCheckbox"
							indeterminate={toolbarOptions.indeterminateCheckbox}
							label={toolbarOptions.label}
							onChange={({target}) =>
								handleSelectAll(target.checked)
							}
						/>
					</ClayManagementToolbar.Item>

					{toolbarOptions.active && !selectAll && (
						<>
							<ClayManagementToolbar.Item>
								<span className="ml-0 mr-0 navbar-text">
									{sub(
										Liferay.Language.get(
											'x-of-x-items-selected'
										),
										[selectedTasks.length, items.length]
									)}
								</span>
							</ClayManagementToolbar.Item>

							<ClayManagementToolbar.Item>
								<button
									className="btn btn-link btn-sm font-weight-bold pl-0 text-primary"
									data-testid="selectRemaningItems"
									onClick={() => {
										handleSelectAll(true);
									}}
								>
									{Liferay.Language.get(
										'select-all-remanining-items'
									)}
								</button>
							</ClayManagementToolbar.Item>
						</>
					)}
				</ClayManagementToolbar.ItemList>
			</ClayManagementToolbar>
		</PromisesResolver.Resolved>
	);
};

export {Header};

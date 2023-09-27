/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import ClayModal, {useModal} from '@clayui/modal';
import {ManagementToolbarSearch} from '@liferay/object-js-components-web';
import {ManagementToolbar} from 'frontend-js-components-web';
import React, {useEffect, useMemo, useState} from 'react';

import './ModalAddColumns.scss';

function ModalAddColumns<T extends ModalItem>() {
	const [
		{
			disableRequired,
			disableRequiredChecked,
			getLabel,
			getName,
			header,
			items,
			onSave,
			searchTerm,
			selected,
			title,
		},
		setState,
	] = useState<IState<T>>({items: [], searchTerm: '', selected: []});

	const resetModal = () => {
		setState({items: [], searchTerm: '', selected: []});
	};

	const {observer} = useModal({
		onClose: resetModal,
	});

	useEffect(() => {
		const openModal = ({
			items = [],
			searchTerm = '',
			selected = [],
			...otherProps
		}: Partial<IState<T>>) => {
			setState({items, searchTerm, selected, ...otherProps});
		};

		Liferay.on('openModalAddColumns', openModal);

		return () =>
			Liferay.detach('openModalAddColumns', openModal as () => void);
	}, []);

	const filteredItems = useMemo(() => {
		const loweredTerm = searchTerm.toLowerCase();
		const selectedIds = new Set(selected.map(({id}) => id));

		const filtered: T[] = [];
		items.forEach((item) => {
			if (getName?.(item).toLowerCase().includes(loweredTerm)) {
				filtered.push({
					...item,
					checked:
						disableRequired &&
						item.required &&
						!disableRequiredChecked
							? true
							: selectedIds.has(item.id),
				});
			}
		});

		return filtered;
	}, [
		disableRequired,
		disableRequiredChecked,
		getName,
		searchTerm,
		selected,
		items,
	]);

	const toggleFieldCheckbox = (id: unknown, checked: boolean) => {
		let selectedItems: T[];
		if (checked) {
			const item = items.find((item) => item.id === id) as T;
			selectedItems = [...selected, item];
		}
		else {
			selectedItems = selected.filter((item) => item.id !== id);
		}
		setState((state) => ({...state, selected: selectedItems}));
	};

	return items.length ? (
		<ClayModal
			className="lfr-object__object-view-modal-add-columns"
			observer={observer}
		>
			<ClayModal.Header>{header}</ClayModal.Header>

			<ClayModal.Body>
				<div className="lfr-object__object-view-modal-add-columns-selection-title">
					{title}
				</div>

				<ManagementToolbar.Container>
					<ManagementToolbar.ItemList>
						<ManagementToolbar.Item>
							<ClayCheckbox
								checked={items.length === selected.length}
								indeterminate={
									!!selected.length &&
									items.length !== selected.length
								}
								onChange={() => {
									const requiredFields = selected.filter(
										(item) => item.required
									);
									const selectedItems =
										items.length - requiredFields.length ===
										selected.length - requiredFields.length
											? [...requiredFields]
											: [...items];
									setState((state) => ({
										...state,
										selected: selectedItems,
									}));
								}}
							/>
						</ManagementToolbar.Item>
					</ManagementToolbar.ItemList>

					<ManagementToolbarSearch
						query={searchTerm}
						setQuery={(searchTerm) =>
							setState((state) => ({...state, searchTerm}))
						}
					/>
				</ManagementToolbar.Container>
			</ClayModal.Body>

			<ClayList className="lfr-object__object-view-modal-add-columns-list">
				{filteredItems.map((item, index) => (
					<ClayList.Item flex key={`list-item-${index}`}>
						<ClayCheckbox
							checked={!!item.checked}
							disabled={
								disableRequired &&
								item.required &&
								!disableRequiredChecked
							}
							label={getLabel?.(item) ?? getName?.(item)}
							onChange={() => {
								toggleFieldCheckbox(item.id, !item.checked);
							}}
						/>

						{disableRequired && item.required && (
							<span className="lfr-object__object-view-modal-add-columns-reference-mark">
								<ClayIcon symbol="asterisk" />
							</span>
						)}
					</ClayList.Item>
				))}
			</ClayList>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={resetModal}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							displayType="primary"
							onClick={() => {
								onSave?.(selected);
								resetModal();
							}}
						>
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	) : null;
}

export default ModalAddColumns;

interface ModalItem {
	checked?: boolean;
	id?: unknown;
	label: LocalizedValue<string>;
	required?: boolean;
}

interface IState<T extends ModalItem> {
	disableRequired?: boolean;
	disableRequiredChecked?: boolean;
	getLabel?: (label: T) => string;
	getName?: (name: T) => string;
	header?: string;
	items: T[];
	onSave?: (selected: T[]) => void;
	searchTerm: string;
	selected: T[];
	title?: string;
}

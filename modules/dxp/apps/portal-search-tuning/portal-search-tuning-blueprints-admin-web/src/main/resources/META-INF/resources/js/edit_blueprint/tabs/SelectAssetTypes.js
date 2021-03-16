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

import ClayButton from '@clayui/button';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import ClayManagementToolbar from '@clayui/management-toolbar';
import ClayModal, {useModal} from '@clayui/modal';
import ClayTable from '@clayui/table';
import React, {useState} from 'react';

import {sub} from './../../utils/utils';

function SelectAssetTypes({
	onFrameworkConfigChange,
	searchableAssetTypes,
	selectedAssetTypes = [],
}) {
	const [visible, setVisible] = useState(false);
	const {observer, onClose} = useModal({
		onClose: () => setVisible(false),
	});
	const [modalSelectedAssetTypes, setModalSelectedAssetTypes] = useState(
		selectedAssetTypes
	);

	const _handleDelete = (asset) => () => {
		const newSelected = modalSelectedAssetTypes.filter(
			(item) => item !== asset
		);

		onFrameworkConfigChange({
			searchable_asset_types: newSelected,
		});
		setModalSelectedAssetTypes(newSelected);
	};

	const _handleModalDone = () => {
		onClose();

		onFrameworkConfigChange({
			searchable_asset_types: modalSelectedAssetTypes,
		});
	};

	const _handleRowCheck = (asset) => () => {
		const isSelected = modalSelectedAssetTypes.includes(asset);

		setModalSelectedAssetTypes(
			isSelected
				? modalSelectedAssetTypes.filter((item) => item !== asset)
				: [...modalSelectedAssetTypes, asset]
		);
	};

	return (
		<>
			<ClayButton
				className="select-asset-types"
				displayType="secondary"
				onClick={() => setVisible(true)}
				small
			>
				{Liferay.Language.get('select-asset-types')}
			</ClayButton>

			{selectedAssetTypes.length > 0 && (
				<ClayTable>
					<ClayTable.Body>
						{selectedAssetTypes.sort().map((asset) => (
							<ClayTable.Row key={asset}>
								<ClayTable.Cell expanded headingTitle>
									{asset}
								</ClayTable.Cell>

								<ClayTable.Cell>
									<ClayButton
										aria-label={Liferay.Language.get(
											'delete'
										)}
										className="secondary"
										displayType="unstyled"
										onClick={_handleDelete(asset)}
										small
									>
										<ClayIcon symbol="times" />
									</ClayButton>
								</ClayTable.Cell>
							</ClayTable.Row>
						))}
					</ClayTable.Body>
				</ClayTable>
			)}

			{visible && (
				<ClayModal
					className="blueprint-searchable-assets-modal modal-height-xl"
					observer={observer}
					size="lg"
				>
					<ClayModal.Header>
						{Liferay.Language.get('select-asset-types')}
					</ClayModal.Header>

					<ClayManagementToolbar
						className={
							modalSelectedAssetTypes.length > 0 &&
							'management-bar-primary'
						}
					>
						<div className="navbar-form navbar-form-autofit navbar-overlay">
							<ClayManagementToolbar.ItemList>
								<ClayManagementToolbar.Item>
									<ClayCheckbox
										checked={
											modalSelectedAssetTypes.length > 0
										}
										indeterminate={
											modalSelectedAssetTypes.length >
												0 &&
											modalSelectedAssetTypes.length !==
												searchableAssetTypes.length
										}
										onChange={() =>
											setModalSelectedAssetTypes(
												modalSelectedAssetTypes.length ===
													0
													? searchableAssetTypes
													: []
											)
										}
									/>
								</ClayManagementToolbar.Item>

								<ClayManagementToolbar.Item>
									{modalSelectedAssetTypes.length > 0 ? (
										<>
											<span className="component-text">
												{sub(
													Liferay.Language.get(
														'x-of-x-selected'
													),
													[
														modalSelectedAssetTypes.length,
														searchableAssetTypes.length,
													],
													false
												)}
											</span>

											<ClayLink
												className="component-text"
												displayType="primary"
												onClick={() => {
													setModalSelectedAssetTypes(
														searchableAssetTypes
													);
												}}
											>
												{Liferay.Language.get(
													'select-all'
												)}
											</ClayLink>
										</>
									) : (
										<span className="component-text">
											{Liferay.Language.get('select-all')}
										</span>
									)}
								</ClayManagementToolbar.Item>
							</ClayManagementToolbar.ItemList>
						</div>
					</ClayManagementToolbar>

					<ClayModal.Body scrollable>
						<ClayTable>
							<ClayTable.Body>
								{searchableAssetTypes.sort().map((asset) => {
									const isSelected = modalSelectedAssetTypes.includes(
										asset
									);

									return (
										<ClayTable.Row
											active={isSelected}
											className="cursor-pointer"
											key={asset}
											onClick={_handleRowCheck(asset)}
										>
											<ClayTable.Cell>
												<ClayCheckbox
													checked={isSelected}
													onChange={_handleRowCheck(
														asset
													)}
												/>
											</ClayTable.Cell>

											<ClayTable.Cell
												expanded
												headingTitle
											>
												{asset}
											</ClayTable.Cell>
										</ClayTable.Row>
									);
								})}
							</ClayTable.Body>
						</ClayTable>
					</ClayModal.Body>

					<ClayModal.Footer
						last={
							<ClayButton.Group spaced>
								<ClayButton
									displayType="secondary"
									onClick={onClose}
								>
									{Liferay.Language.get('cancel')}
								</ClayButton>

								<ClayButton onClick={_handleModalDone}>
									{Liferay.Language.get('done')}
								</ClayButton>
							</ClayButton.Group>
						}
					/>
				</ClayModal>
			)}
		</>
	);
}

export default React.memo(SelectAssetTypes);

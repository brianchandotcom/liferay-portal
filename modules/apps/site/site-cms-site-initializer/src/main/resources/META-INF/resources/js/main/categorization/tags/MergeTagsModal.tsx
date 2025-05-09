/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import Form, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal from '@clayui/modal';
import ClayMultiSelect from '@clayui/multi-select';
import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {useFormik} from 'formik';
import {openConfirmModal, openModal} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useEffect, useMemo, useState} from 'react';

import {executeAsyncItemAction} from '../../FDSPropsTransformer/utils/executeAsyncItemAction';
import SpaceSticker from '../../components/SpaceSticker';
import {required, validate} from '../../components/forms/validations';

type Tag = {
	label: string;
	value: any;
};

export default function MergeTagsModalContent({
	closeModal,
	loadData,
	tagId,
	tagName,
	tagsList,
}: {
	closeModal: () => void;
	loadData: () => {};
	tagId: number;
	tagName: string;
	tagsList: any;
}) {
	const [tags, setTags] = useState<Tag[]>([]);
	const [selectedTags, setSelectedTags] = useState<Tag[]>([]);
	const [checkedTags, setCheckedTags] = useState<Tag[]>([]);

	const allTags = useMemo(() => {
		return tagsList.map((item: any) => ({
			label: item.name,
			value: item.tagId,
		}));
	}, [tagsList]);

	useEffect(() => {
		setTags(allTags);

		const selectedTag = allTags.filter(
			(tag: {label: string}) => tag.label === tagName
		);

		setSelectedTags(selectedTag);
	}, [allTags, tagName, setSelectedTags, setTags]);

	const _handleTagChange = (items: Tag[]) => {
		setSelectedTags(tags.filter((item) => items.includes(item)));
	};

	const mergeTags = (values: any) => {
		const params = new URLSearchParams();

		for (const item of selectedTags) {
			if (Number(item.value) === Number(values.tagId)) {
				continue;
			}
			params.append('fromKeywordIds', item.value);
		}

		const url = `/o/headless-admin-taxonomy/v1.0/keywords/${values.tagId}/merge?${params}`;

		executeAsyncItemAction({
			method: 'PUT',
			refreshData: loadData,
			successMessage: sub(
				Liferay.Language.get('x-and-x-have-been-successfully-merged'),
				selectedTags.map((item) => item.label).join(', '),
				`${Liferay.Util.escapeHTML(tagName)}`
			),
			url,
		});

		closeModal();
	};

	const {handleSubmit} = useFormik({
		initialValues: {
			tagId,
			tagName,
		},
		onSubmit: (values) => {
			openConfirmModal({
				message: sub(
					Liferay.Language.get(
						'are-you-sure-you-want-to-merge-x-into-x'
					),
					selectedTags.map((item) => item.label).join(', '),
					`${Liferay.Util.escapeHTML(tagName)}`
				),
				onConfirm: (isConfirm: boolean) => {
					if (isConfirm) {
						mergeTags(values);
					}
				},
				status: 'info',
				title: Liferay.Language.get('confirm-merge-tags'),
			});
		},
		validate: (values) => {
			const errors = validate(
				{
					tagName: [required],
				},
				values
			);

			return errors;
		},
	});

	const SelectTagsDataSetModalContent = ({
		closeModal,
	}: {
		closeModal: () => void;
	}) => {
		const VIEWS_SPACE_TABLE_CELL_RENDERER_NAME =
			'ViewsSpaceTableCellRenderer';

		const ViewsSpaceTableCell = ({itemData}: {itemData: any}) => {
			const assetLibraryNames = itemData.assetLibraries.map(
				(assetLibrary: any) => assetLibrary.name
			);
			const assetLibraryIds = itemData.assetLibraries.map(
				(assetLibrary: any) => assetLibrary.id
			);

			if (assetLibraryIds.includes(-1)) {
				return (
					<span className="align-items-center d-flex space-renderer-sticker">
						<SpaceSticker name="All Spaces" size="sm" />
					</span>
				);
			}
			else {
				return (
					<>
						{assetLibraryNames.map(
							(name: string, index: number) => (
								<span
									className="align-items-center d-flex space-renderer-sticker"
									key={index}
								>
									<SpaceSticker name={name} size="sm" />
								</span>
							)
						)}
					</>
				);
			}
		};

		return (
			<>
				<div className="categorization-section">
					<ClayModal.Header>
						{Liferay.Language.get('merge-tags')}
					</ClayModal.Header>

					<ClayModal.Body className="merge-tags">
						<FrontendDataSet
							apiURL="/o/headless-admin-taxonomy/v1.0/keywords"
							bulkActions={[{}]}
							customRenderers={{
								tableCell: [
									{
										component: ViewsSpaceTableCell,
										name: VIEWS_SPACE_TABLE_CELL_RENDERER_NAME,
										type: 'internal',
									},
								],
							}}
							id="merge"
							onSelectedItemsChange={(
								selectedItems: React.SetStateAction<{
									selectedItems: any;
								}>
							) => {
								const items = JSON.parse(
									JSON.stringify(selectedItems)
								);

								const setTag: Tag[] = items.map(
									(item: {id: any; name: any}) => ({
										label: item.name,
										value: item.id,
									})
								);

								setCheckedTags(setTag);
							}}
							selectedItemsKey="id"
							selectionType="multiple"
							views={[
								{
									contentRenderer: 'table',
									label: Liferay.Language.get('table'),
									name: 'table',
									schema: {
										fields: [
											{
												fieldName: 'name',
												label: Liferay.Language.get(
													'name'
												),
												sortable: false,
											},
											{
												contentRenderer:
													VIEWS_SPACE_TABLE_CELL_RENDERER_NAME,
												fieldName: 'assetLibraries',
												label: Liferay.Language.get(
													'space'
												),
												sortable: false,
											},
										],
									},
									thumbnail: 'table',
								},
							]}
						/>
					</ClayModal.Body>

					<ClayModal.Footer
						last={
							<ClayButton.Group spaced>
								<ClayButton
									className="btn-cancel"
									displayType="secondary"
									onClick={() => closeModal()}
								>
									{Liferay.Language.get('cancel')}
								</ClayButton>

								<ClayButton
									onClick={() => {
										setSelectedTags(checkedTags);
										closeModal();
									}}
								>
									{Liferay.Language.get('save')}
								</ClayButton>
							</ClayButton.Group>
						}
					/>
				</div>
			</>
		);
	};

	const handleSelectButtonClick = () => {
		openModal({
			contentComponent: ({closeModal}: {closeModal: () => void}) => (
				<SelectTagsDataSetModalContent closeModal={closeModal} />
			),
			height: '70vh',
			size: 'lg',
		});
	};

	return (
		<form onSubmit={handleSubmit}>
			<ClayModal.Header>
				{Liferay.Language.get('merge-tags')}
			</ClayModal.Header>

			<ClayModal.Body>
				<ClayInput.Group>
					<ClayInput.GroupItem className="categorization-spaces">
						<label htmlFor="multiSelect">
							{Liferay.Language.get('tags')}

							<span className="ml-1 reference-mark">
								<ClayIcon symbol="asterisk" />
							</span>
						</label>

						<ClayMultiSelect
							inputName="multiSelect"
							items={selectedTags}
							onItemsChange={(items: Tag[]) => {
								_handleTagChange(items);
							}}
							sourceItems={allTags}
						/>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem className="c-mt-4" shrink>
						<ClayButton
							aria-haspopup="dialog"
							aria-label={Liferay.Language.get('select')}
							displayType="secondary"
							onClick={handleSelectButtonClick}
						>
							{Liferay.Language.get('select')}
						</ClayButton>
					</ClayInput.GroupItem>
				</ClayInput.Group>

				<Form.Group className="c-mt-3">
					<label htmlFor="picker">
						{Liferay.Language.get('into-this-tag')}

						<span className="ml-1 reference-mark">
							<ClayIcon symbol="asterisk" />
						</span>
					</label>

					<ClayInput readOnly value={tagName} />
				</Form.Group>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={closeModal}
							type="button"
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton displayType="primary" type="submit">
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</form>
	);
}

/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-components-web';
import {navigate, sub} from 'frontend-js-web';
import React, {ReactElement, useEffect, useState} from 'react';

import {IPermissionItem} from '../../components/forms/PermissionsTable';
import {
	displayCreateSuccessToast,
	displayEditSuccessToast,
	displaySystemErrorToast,
} from '../../util/ToastUtil';
import CategorizationContentContainer from '../components/CategorizationContentContainer';
import CategorizationManagementToolbar from '../components/CategorizationManagementToolbar';
import CategorizationPermissionService from '../../../services/CategorizationPermissionService';
import CategoryService from '../../../services/CategoryService';
import {DEFAULT_PERMISSIONS} from '../utils/CategorizationPermissionsUtil';
import EditCategoryGeneralInfoTab from './components/EditCategoryGeneralInfoTab';
import EditCategoryPropertiesTab from './components/EditCategoryPropertiesTab';

interface Props {
	backURL: string | URL;
	categoryByCategoryIdAPIURL: string;
	categoryByVocabularyIdAPIURL: string;
	categoryId: number;
	categoryPermissionsAPIURL: string;
	defaultLanguageId: string;
	isCreateNew: boolean;
	locales: any[];
	spritemap: string;
	vocabularyId: number;
}

const EditCategoryPage = ({
	backURL,
	categoryByCategoryIdAPIURL,
	categoryByVocabularyIdAPIURL,
	categoryId,
	categoryPermissionsAPIURL,
	defaultLanguageId,
	isCreateNew,
	locales,
	spritemap,
}: Props) => {
	const [category, setCategory] = useState<TaxonomyCategory>({
		name: '',
		name_i18n: {
			[defaultLanguageId.replace('_', '-')]: '',
		},
	});
	const [categoryPermissions, setCategoryPermissions] =
		useState<IPermissionItem[]>(DEFAULT_PERMISSIONS);
	const [nameInputError, setNameInputError] = useState<string>('');
	const [title, setTitle] = useState<string>('');

	useEffect(() => {
		const fetchInitialData = async () => {
			if (isCreateNew) {
				return;
			}
			else {
				try {
					const fetchedData = await CategoryService.getCategory(
						categoryByCategoryIdAPIURL,
						categoryId
					);

					setTitle(fetchedData.name);
					setCategory(fetchedData);
				}
				catch (error) {
					console.error(error);

					navigate(backURL);
				}
			}
		};

		void fetchInitialData();

		return () => {
			resetForm();
		};

		// eslint-disable-next-line react-compiler/react-compiler
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	function resetForm() {
		setCategory({
			name: '',
			name_i18n: {
				[defaultLanguageId]: '',
			},
		});
		setNameInputError('');
		setTitle('');
	}

	function validateForm() {
		if (category.name.trim() === '') {
			setNameInputError(
				sub(
					Liferay.Language.get('the-x-field-is-required'),
					Liferay.Language.get('name')
				)
			);
		}
		else {
			setNameInputError('');
		}
	}

	function getFormattedCategoryProperties(category: TaxonomyCategory) {
		return category.taxonomyCategoryProperties?.filter((row) => {
			return row.key.trim() !== '' && row.value.trim() !== '';
		});
	}

	async function handleSave() {
		validateForm();

		if (nameInputError !== '') {
			return;
		}

		try {
			if (isCreateNew) {
				const response = await CategoryService.createCategory(
					categoryByVocabularyIdAPIURL,
					{
						...category,
						taxonomyCategoryProperties:
							getFormattedCategoryProperties(category),
					}
				);

				await CategorizationPermissionService.putPermissions(
					categoryPermissionsAPIURL.replace(
						'{taxonomyCategoryId}',
						response.id
					),
					categoryPermissions
				);

				navigate(backURL);
				displayCreateSuccessToast(category.name);
			}
			else {
				openModal({
					bodyHTML: Liferay.Language.get(
						'edit-category-confirmation'
					),
					buttons: [
						{
							autoFocus: true,
							displayType: 'secondary',
							label: Liferay.Language.get('cancel'),
							type: 'cancel',
						},
						{
							displayType: 'primary',
							label: Liferay.Language.get('save'),
							onClick: async ({processClose}) => {
								processClose();

								await CategoryService.updateCategory(
									categoryByCategoryIdAPIURL,
									{
										...category,
										taxonomyCategoryProperties:
											getFormattedCategoryProperties(
												category
											),
									},
									'PUT'
								);

								navigate(backURL);
								displayEditSuccessToast(category.name);
							},
						},
					],
					status: 'warning',
					title: sub(
						Liferay.Language.get('edit-x'),
						'"' + category.name + '"'
					),
				});
			}
		}
		catch (error) {
			console.error(error);

			displaySystemErrorToast();
		}
	}

	async function handleSaveAndAddAnother() {
		validateForm();

		if (nameInputError !== '') {
			return;
		}

		try {
			await CategoryService.createCategory(
				categoryByVocabularyIdAPIURL,
				category
			);
		}
		catch (error) {
			console.error(error);

			displaySystemErrorToast();
		}

		window.location.reload();

		displayCreateSuccessToast(category.name);
	}

	const createMainContentMap = () => {
		const NAVIGATION_TABS = {
			GENERAL: 'general',
			IMAGES: 'images',
			PROPERTIES: 'properties',
		};

		const mainContentMap = new Map<string, ReactElement>();

		mainContentMap.set(
			NAVIGATION_TABS.GENERAL,
			<EditCategoryGeneralInfoTab
				category={category}
				defaultLanguageId={defaultLanguageId}
				locales={locales}
				nameInputError={nameInputError}
				setCategory={setCategory}
				setCategoryPermissions={setCategoryPermissions}
				setNameInputError={setNameInputError}
				showPermissions={isCreateNew}
				spritemap={spritemap}
			/>
		);
		mainContentMap.set(
			NAVIGATION_TABS.IMAGES,
			<div>Images Tab Placeholder Content</div>
		);
		mainContentMap.set(
			NAVIGATION_TABS.PROPERTIES,
			<EditCategoryPropertiesTab
				category={category}
				setCategory={setCategory}
				spritemap={spritemap}
			/>
		);

		return mainContentMap;
	};

	return (
		<div className="categorization-section">
			<div className="d-flex edit-vocabulary flex-column">
				<CategorizationManagementToolbar
					backURL={backURL}
					handleSave={handleSave}
					handleSaveAndAddAnother={
						isCreateNew ? handleSaveAndAddAnother : undefined
					}
					showSaveAndAddAnotherButton={isCreateNew}
					title={
						isCreateNew
							? Liferay.Language.get('new-category')
							: sub(Liferay.Language.get('edit-x'), title)
					}
				/>

				<CategorizationContentContainer
					mainContentMap={createMainContentMap()}
				/>
			</div>
		</div>
	);
};
export default EditCategoryPage;

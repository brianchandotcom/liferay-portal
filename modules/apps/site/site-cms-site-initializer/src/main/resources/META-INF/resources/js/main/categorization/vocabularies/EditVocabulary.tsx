/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {useModal} from '@clayui/modal';
import {ClayVerticalNav} from '@clayui/nav';
import {ManagementToolbar} from 'frontend-js-components-web';
import {navigate, sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import VocabularyService from '../services/VocabularyService';
import {IVocabulary} from '../types/IVocabulary';
import ConfirmChangesModal from './ConfirmChangesModal';
import EditAssociatedAssetTypes from './EditAssociatedAssetTypes';
import EditGeneralInfo from './EditGeneralInfo';

const NAVIGATION_TABS = {
	ASSET_TYPES: 'assetTypes',
	GENERAL: 'general',
};

export default function EditVocabulary({
	assetTypes,
	backURL,
	defaultLanguageId,
	locales,
	siteId,
	spritemap,
	vocabularyId,
}: {
	assetTypes: AssetType[];
	backURL: string;
	defaultLanguageId: string;
	locales: any[];
	siteId: number;
	spritemap: string;
	vocabularyId: number;
}) {
	const [activeVerticalNavKey, setActiveVerticalNavKey] = useState(
		NAVIGATION_TABS.GENERAL
	);
	const [spaceInputError, setSpaceInputError] = useState('');
	const [nameInputError, setNameInputError] = useState<string>('');
	const {observer, onOpenChange, open} = useModal();
	const [title, setTitle] = useState<string>('');
	const [vocabulary, setVocabulary] = useState<IVocabulary>({
		description: '',
		description_i18n: {
			[defaultLanguageId]: '',
		},
		name: '',
		name_i18n: {
			[defaultLanguageId]: '',
		},
	});

	const changeType = '';
	const isNew = Number(vocabularyId) === 0;

	useEffect(() => {
		const fetchData = async () => {
			if (isNew) {
				return;
			}
			else {
				try {
					const fetchedData =
						await VocabularyService.fetchVocabulary(vocabularyId);

					setTitle(fetchedData.name);
					setVocabulary(fetchedData);
				}
				catch (error) {
					console.error(error);
					navigate(backURL);
				}
			}
		};

		fetchData();
	}, [backURL, isNew, vocabularyId]);

	const _handleValidateInputs = () => {
		if (nameInputError || vocabulary.name === '') {
			setNameInputError(
				sub(
					Liferay.Language.get('the-x-field-is-required'),
					Liferay.Language.get('name')
				)
			);

			setActiveVerticalNavKey('general');

			return false;
		}

		if (spaceInputError) {
			setActiveVerticalNavKey('general');

			return false;
		}

		return true;
	};

	const _handleSave = async () => {
		try {
			if (!_handleValidateInputs()) {
				return;
			}

			if (isNew) {
				await VocabularyService.createVocabulary(siteId, vocabulary);
			}
			else {
				await VocabularyService.updateVocabulary(siteId, vocabulary);
			}

			await navigate(backURL);

			if (isNew) {
				Liferay.Util.openToast({
					message: Liferay.Util.sub(
						Liferay.Language.get('x-was-published-successfully'),
						vocabulary.name
					),
					type: 'success',
				});
			}
			else {
				Liferay.Util.openToast({
					message: Liferay.Util.sub(
						Liferay.Language.get('x-was-updated-successfully'),
						vocabulary.name
					),
					type: 'success',
				});
			}
		}
		catch (error) {
			Liferay.Util.openToast({
				message: Liferay.Language.get(
					'an-unexpected-system-error-occurred'
				),
				type: 'danger',
			});
		}
	};

	const _handleVerticalNavChange = (verticalNav: string) => {
		setActiveVerticalNavKey(verticalNav);
	};

	return (
		<div className="categorization-section">
			<div className="d-flex edit-vocabulary flex-column">
				<ManagementToolbar.Container>
					<ManagementToolbar.ItemList className="c-gap-3" expand>
						<ManagementToolbar.Item>
							<ClayButton
								aria-label={Liferay.Language.get('back')}
								className="btn btn-monospaced btn-outline-borderless btn-outline-secondary btn-sm"
								onClick={() => navigate(backURL)}
							>
								<ClayIcon symbol="angle-left" />
							</ClayButton>
						</ManagementToolbar.Item>

						<ManagementToolbar.Item className="nav-item-expand">
							<h2 className="font-weight-semi-bold m-0 text-5">
								{title
									? sub(Liferay.Language.get('edit-x'), title)
									: Liferay.Language.get('new-vocabulary')}
							</h2>
						</ManagementToolbar.Item>

						<ManagementToolbar.Item>
							<ClayButton
								className="btn btn-outline-borderless btn-outline-secondary btn-sm"
								onClick={() => navigate(backURL)}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>
						</ManagementToolbar.Item>

						<ManagementToolbar.Item>
							<ClayButton
								displayType="primary"
								onClick={_handleSave}
								size="sm"
							>
								{Liferay.Language.get('save')}
							</ClayButton>
						</ManagementToolbar.Item>
					</ManagementToolbar.ItemList>
				</ManagementToolbar.Container>

				<ClayLayout.ContainerFluid
					className="cms-parent-container m-0"
					size={false}
				>
					<ClayLayout.Row className="cms-container-child">
						<ClayLayout.Col
							className="categorization-vertical-nav p-0"
							md={3}
							sm={12}
						>
							<div className="p-4">
								<ClayVerticalNav
									items={[
										{
											active:
												activeVerticalNavKey ===
												NAVIGATION_TABS.GENERAL,
											label: Liferay.Language.get(
												'general'
											),
											onClick: () =>
												_handleVerticalNavChange(
													NAVIGATION_TABS.GENERAL
												),
										},
										{
											active:
												activeVerticalNavKey ===
												NAVIGATION_TABS.ASSET_TYPES,
											label: Liferay.Language.get(
												'associated-asset-types'
											),
											onClick: () =>
												_handleVerticalNavChange(
													NAVIGATION_TABS.ASSET_TYPES
												),
										},
									]}
								/>
							</div>
						</ClayLayout.Col>

						<ClayLayout.Col md={9} sm={12}>
							{activeVerticalNavKey === 'general' && (
								<EditGeneralInfo
									defaultLanguageId={defaultLanguageId}
									locales={locales}
									nameInputError={nameInputError}
									onChangeVocabulary={setVocabulary}
									setNameInputError={setNameInputError}
									setSpaceInputError={setSpaceInputError}
									spritemap={spritemap}
									vocabulary={vocabulary}
								/>
							)}

							{activeVerticalNavKey === 'assetTypes' && (
								<EditAssociatedAssetTypes
									assetTypes={assetTypes}
								/>
							)}
						</ClayLayout.Col>
					</ClayLayout.Row>
				</ClayLayout.ContainerFluid>

				<>
					{changeType && open && (
						<ConfirmChangesModal
							changeType={changeType}
							observer={observer}
							onOpenChange={onOpenChange}
							onSave={_handleSave}
						/>
					)}
				</>
			</div>
		</div>
	);
}

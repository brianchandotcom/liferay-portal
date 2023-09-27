/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayTable from '@clayui/table';
import classNames from 'classnames';
import {openSelectionModal, openToast} from 'frontend-js-web';
import React, {useMemo, useState} from 'react';

import {GlobalCETOptionsDropDown} from './GlobalCETOptionsDropDown';
import {GlobalCETOrderHelpIcon} from './GlobalCETOrderHelpIcon';

export default function GlobalCSSCETsConfiguration({
	globalCSSCETSelectorURL,
	globalCSSCETs: initialGlobalCSSCETs,
	isReadOnly,
	portletNamespace,
	selectGlobalCSSCETsEventName,
}: IProps) {
	const fixedGlobalCSSCETs = useMemo(
		() =>
			initialGlobalCSSCETs.filter(
				(globalCSSCET) => globalCSSCET.inherited
			),
		[initialGlobalCSSCETs]
	);

	const [globalCSSCETs, setGlobalCSSCETs] = useState(() =>
		initialGlobalCSSCETs.filter((globalCSSCET) => !globalCSSCET.inherited)
	);

	const allGlobalCSSCETs = useMemo(
		() => [...fixedGlobalCSSCETs, ...globalCSSCETs],
		[fixedGlobalCSSCETs, globalCSSCETs]
	);

	const deleteGlobalCSSCET = (deletedGlobalCSSCET: IGlobalCSSCET) => {
		setGlobalCSSCETs((previousGlobalCSSCETs) =>
			previousGlobalCSSCETs.filter(
				(globalCSSCET) =>
					globalCSSCET.cetExternalReferenceCode !==
					deletedGlobalCSSCET.cetExternalReferenceCode
			)
		);
	};

	const getDropDownButtonId = (globalCSSCET: IGlobalCSSCET) =>
		`${portletNamespace}_GlobalCSSCETsConfigurationOptionsButton_${globalCSSCET.cetExternalReferenceCode}`;

	const getDropDownItems = (globalCSSCET: IGlobalCSSCET) => {
		return [
			{
				label: Liferay.Language.get('delete'),
				onClick: () => deleteGlobalCSSCET(globalCSSCET),
				symbolLeft: 'trash',
			},
		];
	};

	const handleClick = () => {
		if (isReadOnly) {
			return;
		}

		openSelectionModal<{value: string[]}>({
			multiple: true,
			onSelect(selectedItems) {
				if (!selectedItems.value) {
					return;
				}

				setGlobalCSSCETs((previousGlobalCSSCETs) => {
					const duplicatedGlobalCSSCETs: IGlobalCSSCET[] = [];

					const nextGlobalCSSCETs = selectedItems.value
						.map((selectedItem) => ({
							inherited: false,
							inheritedLabel: '-',
							...(JSON.parse(selectedItem) as {
								cetExternalReferenceCode: string;
								name: string;
							}),
						}))
						.filter((nextGlobalCSSCET) => {
							const isDuplicated = previousGlobalCSSCETs.some(
								(previousGlobalCSSCET) =>
									nextGlobalCSSCET.cetExternalReferenceCode ===
									previousGlobalCSSCET.cetExternalReferenceCode
							);

							if (isDuplicated) {
								duplicatedGlobalCSSCETs.push(nextGlobalCSSCET);

								return false;
							}

							return true;
						});

					if (duplicatedGlobalCSSCETs.length) {
						openToast({
							autoClose: true,
							message: `${Liferay.Language.get(
								'some-client-extensions-were-not-added-because-they-are-already-applied-to-this-page'
							)} (${duplicatedGlobalCSSCETs
								.map((globalCSSCET) => globalCSSCET.name)
								.join(', ')})`,
							type: 'warning',
						});
					}

					return [
						...previousGlobalCSSCETs.filter(
							(previousGlobalCSSCET) =>
								!nextGlobalCSSCETs.some(
									(globalCSSCET) =>
										globalCSSCET.cetExternalReferenceCode ===
										previousGlobalCSSCET.cetExternalReferenceCode
								)
						),
						...nextGlobalCSSCETs,
					];
				});
			},
			selectEventName: selectGlobalCSSCETsEventName,
			title: Liferay.Language.get('select-css-client-extensions'),
			url: globalCSSCETSelectorURL,
		});
	};

	return (
		<div className="global-css-cets-configuration">
			{globalCSSCETs.map(({cetExternalReferenceCode}) => (
				<input
					key={cetExternalReferenceCode}
					name={`${portletNamespace}globalCSSCETExternalReferenceCodes`}
					type="hidden"
					value={cetExternalReferenceCode}
				/>
			))}

			<p className="text-secondary">
				{Liferay.Language.get(
					'extend-this-page-css-with-client-extensions.-they-will-be-loaded-after-the-theme-css-and-after-master-extensions'
				)}
			</p>

			<ClayButton
				className="c-mb-3"
				disabled={isReadOnly}
				displayType="secondary"
				onClick={handleClick}
				type="button"
			>
				<ClayIcon className="c-mr-2" symbol="plus" />

				{Liferay.Language.get('add-css-client-extensions')}
			</ClayButton>

			{allGlobalCSSCETs.length ? (
				<ClayTable>
					<ClayTable.Head>
						<ClayTable.Row>
							<ClayTable.Cell headingCell>
								<GlobalCETOrderHelpIcon
									buttonId={`${portletNamespace}_GlobalCSSCETsConfigurationOrderHelpIcon`}
									title={Liferay.Language.get(
										'loading-order'
									)}
								>
									{[
										Liferay.Language.get(
											'numbers-indicate-the-order-in-which-client-extensions-are-loaded'
										),
										Liferay.Language.get(
											'client-extensions-inherited-from-master-will-always-be-loaded-first'
										),
									].join(' ')}
								</GlobalCETOrderHelpIcon>
							</ClayTable.Cell>

							<ClayTable.Cell expanded headingCell>
								{Liferay.Language.get('name')}
							</ClayTable.Cell>

							<ClayTable.Cell expanded headingCell>
								{Liferay.Language.get('inherited')}
							</ClayTable.Cell>

							<ClayTable.Cell headingCell>
								<span className="sr-only">
									{Liferay.Language.get('options')}
								</span>
							</ClayTable.Cell>
						</ClayTable.Row>
					</ClayTable.Head>

					<ClayTable.Body>
						{allGlobalCSSCETs.map((globalCSSCET, index) => {
							const buttonId = getDropDownButtonId(globalCSSCET);
							const items = getDropDownItems(globalCSSCET);
							const order = index + 1;

							const disabled = globalCSSCET.inherited;

							return (
								<ClayTable.Row
									className={classNames({disabled})}
									key={globalCSSCET.cetExternalReferenceCode}
								>
									<ClayTable.Cell>{order}</ClayTable.Cell>

									<ClayTable.Cell expanded headingTitle>
										{globalCSSCET.name}
									</ClayTable.Cell>

									<ClayTable.Cell expanded>
										{globalCSSCET.inheritedLabel}
									</ClayTable.Cell>

									<ClayTable.Cell>
										{disabled ? null : (
											<GlobalCETOptionsDropDown
												dropdownItems={items}
												dropdownTriggerId={buttonId}
											/>
										)}
									</ClayTable.Cell>
								</ClayTable.Row>
							);
						})}
					</ClayTable.Body>
				</ClayTable>
			) : (
				<p className="text-secondary">
					{Liferay.Language.get(
						'no-css-client-extensions-were-loaded'
					)}
				</p>
			)}
		</div>
	);
}

interface IGlobalCSSCET {
	cetExternalReferenceCode: string;
	inherited: boolean;
	inheritedLabel: string;
	name: string;
}

interface IProps {
	globalCSSCETSelectorURL: string;
	globalCSSCETs: IGlobalCSSCET[];
	isReadOnly: boolean;
	portletNamespace: string;
	selectGlobalCSSCETsEventName: string;
}

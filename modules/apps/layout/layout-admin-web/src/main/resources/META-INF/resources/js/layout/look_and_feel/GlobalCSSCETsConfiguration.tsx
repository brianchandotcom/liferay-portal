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

import ClayButton from '@clayui/button';
import ClayTable from '@clayui/table';
import classNames from 'classnames';
import {openSelectionModal} from 'frontend-js-web';
import React, {useMemo, useState} from 'react';

import {GlobalCETOptionsDropDown} from './GlobalCETOptionsDropDown';
import {GlobalCETOrderHelpIcon} from './GlobalCETOrderHelpIcon';

export default function GlobalCSSCETsConfiguration({
	globalCSSCETSelectorURL,
	globalCSSCETs: initialGlobalCSSCETs,
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
		openSelectionModal<{value: string[]}>({
			multiple: true,
			onSelect(selectedItems) {
				if (!selectedItems.value) {
					return;
				}

				const items = selectedItems.value.map((selectedItem) =>
					JSON.parse(selectedItem)
				);

				setGlobalCSSCETs((previousGlobalCSSCETs) => {
					const nextGlobalCSSCETs = [
						...previousGlobalCSSCETs,
						...items,
					];

					return nextGlobalCSSCETs.filter(
						(globalCSSCET, index) =>
							nextGlobalCSSCETs.findIndex(
								({cetExternalReferenceCode}) =>
									globalCSSCET.cetExternalReferenceCode ===
									cetExternalReferenceCode
							) === index
					);
				});
			},
			selectEventName: selectGlobalCSSCETsEventName,
			title: Liferay.Language.get('select-css-extensions'),
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

			<h3 className="sheet-subtitle">
				{Liferay.Language.get('css-extensions')}
			</h3>

			<ClayButton
				className="mb-3"
				displayType="secondary"
				onClick={handleClick}
				small
				type="button"
			>
				{Liferay.Language.get('add-css-extensions')}
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
									{Liferay.Language.get(
										'numbers-indicate-the-order-in-which-client-extensions-are-loaded'
									)}
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
					{Liferay.Language.get('no-css-extensions-were-loaded')}
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
	portletNamespace: string;
	selectGlobalCSSCETsEventName: string;
}

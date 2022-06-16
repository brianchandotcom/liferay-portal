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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayTable from '@clayui/table';
import {openSelectionModal} from 'frontend-js-web';
import React, {useState} from 'react';

export default function GlobalCSSCETsConfiguration({
	globalCSSCETSelectorURL,
	globalCSSCETs: initialGlobalCSSCETs,
	portletNamespace,
	selectGlobalCSSCETsEventName,
}) {
	const [globalCSSCETs, setGlobalCSSCETs] = useState(initialGlobalCSSCETs);

	const deleteGlobalCSSCET = (deletedGlobalCSSCET) => {
		setGlobalCSSCETs((previousGlobalCSSCETs) =>
			previousGlobalCSSCETs.filter(
				(globalCSSCET) =>
					globalCSSCET.cetExternalReferenceCode !==
					deletedGlobalCSSCET.cetExternalReferenceCode
			)
		);
	};

	const getDropDownItems = (globalCSSCET) => {
		return [
			{
				label: Liferay.Language.get('delete'),
				onClick: () => deleteGlobalCSSCET(globalCSSCET),
				symbolLeft: 'trash',
			},
		];
	};

	const handleClick = () => {
		openSelectionModal({
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
		<>
			<input
				name={`${portletNamespace}globalCSSCETExternalReferenceCodes`}
				type="hidden"
				value={globalCSSCETs
					.map(
						(globalCSSCET) => globalCSSCET.cetExternalReferenceCode
					)
					.join(',')}
			/>

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

			{globalCSSCETs.length ? (
				<ClayTable>
					<ClayTable.Head>
						<ClayTable.Row>
							<ClayTable.Cell expanded headingCell>
								{Liferay.Language.get('name')}
							</ClayTable.Cell>

							<ClayTable.Cell headingCell>
								<span className="sr-only">
									{Liferay.Language.get('options')}
								</span>
							</ClayTable.Cell>
						</ClayTable.Row>
					</ClayTable.Head>

					<ClayTable.Body>
						{globalCSSCETs.map((globalCSSCET) => (
							<ClayTable.Row
								key={globalCSSCET.cetExternalReferenceCode}
							>
								<ClayTable.Cell expanded headingTitle>
									{globalCSSCET.name}
								</ClayTable.Cell>

								<ClayTable.Cell>
									<ClayDropDownWithItems
										items={getDropDownItems(globalCSSCET)}
										trigger={
											<ClayButtonWithIcon
												displayType="unstyled"
												small
												symbol="ellipsis-v"
											/>
										}
									/>
								</ClayTable.Cell>
							</ClayTable.Row>
						))}
					</ClayTable.Body>
				</ClayTable>
			) : (
				<p className="text-secondary">
					{Liferay.Language.get('no-css-extensions-were-loaded')}
				</p>
			)}
		</>
	);
}

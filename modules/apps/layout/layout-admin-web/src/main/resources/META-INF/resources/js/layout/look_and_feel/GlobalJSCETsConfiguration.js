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

export default function GlobalJSCETsConfiguration({
	globalJSCETSelectorURL,
	globalJSCETs: initialGlobalJSCETs,
	portletNamespace,
	selectGlobalJSCETsEventName,
}) {
	const [globalJSCETs, setGlobalJSCETs] = useState(initialGlobalJSCETs);

	const deleteGlobalJSCET = (deletedGlobalJSCET) => {
		setGlobalJSCETs((previousGlobalJSCETs) =>
			previousGlobalJSCETs.filter(
				(globalJSCET) =>
					globalJSCET.externalReferenceCode !==
					deletedGlobalJSCET.externalReferenceCode
			)
		);
	};

	const getDropDownItems = (globalJSCET) => {
		return [
			{
				label: Liferay.Language.get('delete'),
				onClick: () => deleteGlobalJSCET(globalJSCET),
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

				setGlobalJSCETs((previousGlobalJSCETs) => {
					const nextGlobalJSCETs = [
						...previousGlobalJSCETs,
						...items,
					];

					return nextGlobalJSCETs.filter(
						(globalJSCET, index) =>
							nextGlobalJSCETs.findIndex(
								({externalReferenceCode}) =>
									globalJSCET.externalReferenceCode ===
									externalReferenceCode
							) === index
					);
				});
			},
			selectEventName: selectGlobalJSCETsEventName,
			title: Liferay.Language.get('select-javascript-extensions'),
			url: globalJSCETSelectorURL,
		});
	};

	return (
		<>
			<input
				name={`${portletNamespace}globalJSCETExternalReferenceCodes`}
				type="hidden"
				value={globalJSCETs
					.map((globalJSCET) => globalJSCET.externalReferenceCode)
					.join(',')}
			/>

			<h3 className="sheet-subtitle">
				{Liferay.Language.get('javascript-extensions')}
			</h3>

			<ClayButton
				className="mb-3"
				displayType="secondary"
				onClick={handleClick}
				small
				type="button"
			>
				{Liferay.Language.get('add-javascript-extensions')}
			</ClayButton>

			{globalJSCETs.length ? (
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
						{globalJSCETs.map((globalJSCET) => (
							<ClayTable.Row
								key={globalJSCET.externalReferenceCode}
							>
								<ClayTable.Cell expanded headingTitle>
									{globalJSCET.name}
								</ClayTable.Cell>

								<ClayTable.Cell>
									<ClayDropDownWithItems
										items={getDropDownItems(globalJSCET)}
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
					{Liferay.Language.get(
						'no-javascript-extensions-were-loaded'
					)}
				</p>
			)}
		</>
	);
}

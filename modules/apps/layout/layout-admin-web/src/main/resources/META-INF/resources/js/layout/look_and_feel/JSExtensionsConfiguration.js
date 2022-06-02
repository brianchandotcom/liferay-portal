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

export default function JSExtensionsConfiguration({
	jsExtensionSelectorURL,
	jsExtensions: initialJSExtensions,
	portletNamespace,
	selectJSClientExtensionsEventName,
}) {
	const [jsExtensions, setJSExtensions] = useState(initialJSExtensions);

	const deleteJSExtension = (deletedJSExtension) => {
		setJSExtensions((previousJSExtensions) =>
			previousJSExtensions.filter(
				(jsExtension) =>
					jsExtension.clientExtensionEntryId !==
					deletedJSExtension.clientExtensionEntryId
			)
		);
	};

	const getDropDownItems = (jsExtension) => {
		return [
			{
				label: Liferay.Language.get('delete'),
				onClick: () => deleteJSExtension(jsExtension),
				symbolLeft: 'trash',
			},
		];
	};

	const handleClick = () => {
		openSelectionModal({
			multiple: true,
			onSelect(selectedItems) {
				const items = selectedItems.value.map((selectedItem) =>
					JSON.parse(selectedItem)
				);

				setJSExtensions((previousJSExtensions) => {
					const nextJSExtensions = [
						...previousJSExtensions,
						...items,
					];

					return nextJSExtensions.filter(
						(jsExtension, index) =>
							nextJSExtensions.findIndex(
								({clientExtensionEntryId}) =>
									jsExtension.clientExtensionEntryId ===
									clientExtensionEntryId
							) === index
					);
				});
			},
			selectEventName: selectJSClientExtensionsEventName,
			title: Liferay.Language.get('select-javascript-extensions'),
			url: jsExtensionSelectorURL,
		});
	};

	return (
		<>
			<input
				name={`${portletNamespace}jsExtensions`}
				type="hidden"
				value={jsExtensions
					.map((jsExtension) => jsExtension.clientExtensionEntryId)
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

			{jsExtensions.length ? (
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
						{jsExtensions.map((jsExtension) => (
							<ClayTable.Row
								key={jsExtension.clientExtensionEntryId}
							>
								<ClayTable.Cell expanded headingTitle>
									{jsExtension.name}
								</ClayTable.Cell>

								<ClayTable.Cell>
									<ClayDropDownWithItems
										items={getDropDownItems(jsExtension)}
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

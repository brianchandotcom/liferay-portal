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

export default function CSSExtensionConfiguration({
	cssExtensionSelectorURL,
	cssExtensions: initialCSSExtensions,
	portletNamespace,
	selectCSSClientExtensionsEventName,
}) {
	const [cssExtensions, setCSSExtensions] = useState(initialCSSExtensions);

	const deleteCSSExtension = (deletedCSSExtension) => {
		setCSSExtensions((previousCSSExtensions) =>
			previousCSSExtensions.filter(
				(cssExtension) =>
					cssExtension.clientExtensionEntryId !==
					deletedCSSExtension.clientExtensionEntryId
			)
		);
	};

	const getDropDownItems = (cssExtension) => {
		return [
			{
				label: Liferay.Language.get('delete'),
				onClick: () => deleteCSSExtension(cssExtension),
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

				setCSSExtensions((previousCSSExtensions) => {
					const nextCSSExtensions = [
						...previousCSSExtensions,
						...items,
					];

					return nextCSSExtensions.filter(
						(cssExtension, index) =>
							nextCSSExtensions.findIndex(
								({clientExtensionEntryId}) =>
									cssExtension.clientExtensionEntryId ===
									clientExtensionEntryId
							) === index
					);
				});
			},
			selectEventName: selectCSSClientExtensionsEventName,
			title: Liferay.Language.get('select-css-extensions'),
			url: cssExtensionSelectorURL,
		});
	};

	return (
		<>
			<input
				name={`${portletNamespace}cssExtensions`}
				type="hidden"
				value={cssExtensions
					.map((cssExtension) => cssExtension.clientExtensionEntryId)
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

			{cssExtensions.length ? (
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
						{cssExtensions.map((cssExtension) => (
							<ClayTable.Row
								key={cssExtension.clientExtensionEntryId}
							>
								<ClayTable.Cell expanded headingTitle>
									{cssExtension.name}
								</ClayTable.Cell>

								<ClayTable.Cell>
									<ClayDropDownWithItems
										items={getDropDownItems(cssExtension)}
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

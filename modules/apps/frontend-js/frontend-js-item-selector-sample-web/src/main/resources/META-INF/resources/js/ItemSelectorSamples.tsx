/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClaySticker from '@clayui/sticker';
import {ItemSelector} from 'frontend-js-item-selector-web';
import React from 'react';

import type {DisplayType} from '@clayui/sticker';

function SampleContainer({
	children,
	label,
}: {
	children: React.ReactNode;
	label: string;
}) {
	return (
		<div className="mt-4">
			<h2>{label}</h2>

			{children}
		</div>
	);
}

type Document = {
	id: string;
	title: string;
};

export default function ItemSelectorSamples() {
	return (
		<>
			<SampleContainer label="Single Select Documents - Paginated Items">
				<ItemSelector<Document>
					apiURL={`${location.origin}/o/headless-delivery/v1.0/sites/${Liferay.ThemeDisplay.getSiteGroupId()}/documents`}
				>
					{(item) => {
						return (
							<ItemSelector.Item
								key={item.id}
								textValue={item.title}
							>
								{item.title}
							</ItemSelector.Item>
						);
					}}
				</ItemSelector>
			</SampleContainer>

			<SampleContainer label="Single Select (Spaces)">
				<ItemSelector
					apiURL={`${location.origin}/o/headless-asset-library/v1.0/asset-libraries`}
				>
					{(item) => {
						return (
							<ItemSelector.Item
								key={item.id}
								textValue={item.name}
							>
								<span className="inline-item inline-item-before">
									<ClaySticker
										displayType={
											`outline-${item.id % 10}` as DisplayType
										}
										size="sm"
									>
										{item.name.substr(0, 1)}
									</ClaySticker>
								</span>

								<span className="inline-item inline-item-after">
									{item.name}
								</span>
							</ItemSelector.Item>
						);
					}}
				</ItemSelector>
			</SampleContainer>

			<SampleContainer label="Single Select (Users)">
				<ItemSelector
					apiURL={`${location.origin}/o/headless-admin-user/v1.0/user-accounts`}
					locator={{
						id: 'id',
						label: 'name',
						value: 'id',
					}}
				>
					{(item) => {
						return (
							<ItemSelector.Item
								key={item.id}
								textValue={item.name}
							>
								{item.name}
							</ItemSelector.Item>
						);
					}}
				</ItemSelector>
			</SampleContainer>
		</>
	);
}

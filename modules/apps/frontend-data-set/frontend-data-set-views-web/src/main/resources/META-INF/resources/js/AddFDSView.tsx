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
import ClayDropDown from '@clayui/drop-down';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {navigate} from 'frontend-js-web';
import fuzzy from 'fuzzy';
import React, {useState} from 'react';

import '../css/AddFDSView.scss';

type HeadlessResource = {
	bundleLabel: string;
	entityClassName: string;
	name: string;
	version: string;
};

interface IHeadlessResourceItemProps {
	headlessResource: HeadlessResource;
	query: string;
}

const FUZZY_OPTIONS = {
	post: '</strong>',
	pre: '<strong>',
};

const HeadlessResourceItem = ({
	headlessResource,
	query,
}: IHeadlessResourceItemProps) => {
	const fuzzyNameMatch = fuzzy.match(
		query,
		headlessResource.name,
		FUZZY_OPTIONS
	);

	const fuzzyBundleLabelMatch = fuzzy.match(
		query,
		headlessResource.bundleLabel,
		FUZZY_OPTIONS
	);

	return (
		<ClayLayout.ContentRow className="headless-resource">
			{fuzzyNameMatch ? (
				<span
					dangerouslySetInnerHTML={{
						__html: fuzzyNameMatch.rendered,
					}}
				/>
			) : (
				<span>{headlessResource.name}</span>
			)}

			<span className="context">
				{fuzzyBundleLabelMatch ? (
					<span
						dangerouslySetInnerHTML={{
							__html: fuzzyBundleLabelMatch.rendered,
						}}
					/>
				) : (
					<span>{headlessResource.bundleLabel}</span>
				)}

				{` ${headlessResource.version}`}
			</span>
		</ClayLayout.ContentRow>
	);
};

const RequiredMark = () => (
	<>
		<span className="inline-item-after reference-mark text-warning">
			<ClayIcon symbol="asterisk" />
		</span>
		<span className="hide-accessible sr-only">
			{Liferay.Language.get('required')}
		</span>
	</>
);

interface IDropdownMenuProps {
	initialHeadlessResources: Array<HeadlessResource>;
	setSelectedHeadlessResource: Function;
}

const DropdownMenu = ({
	initialHeadlessResources,
	setSelectedHeadlessResource,
}: IDropdownMenuProps) => {
	const [headlessResources, setHeadlessResources] = useState<
		Array<HeadlessResource>
	>(initialHeadlessResources || []);
	const [query, setQuery] = useState('');

	const onSearch = (query: string) => {
		setQuery(query);

		const regexp = new RegExp(query, 'i');

		setHeadlessResources(
			query
				? initialHeadlessResources.filter(
						({bundleLabel, name}: HeadlessResource) => {
							return (
								bundleLabel.match(regexp) || name.match(regexp)
							);
						}
				  ) || []
				: initialHeadlessResources
		);
	};

	return (
		<>
			<ClayDropDown.Search
				aria-label={Liferay.Language.get('search')}
				onChange={onSearch}
				value={query}
			/>

			<ClayDropDown.ItemList items={headlessResources} role="listbox">
				{(item: HeadlessResource) => (
					<ClayDropDown.Item
						key={item.entityClassName}
						onClick={() => {
							setSelectedHeadlessResource(item);
						}}
						roleItem="option"
					>
						<HeadlessResourceItem
							headlessResource={item}
							query={query}
						/>
					</ClayDropDown.Item>
				)}
			</ClayDropDown.ItemList>
		</>
	);
};

interface IAddFDSViewProps {
	fdsViewsURL: string;
	headlessResources: Array<HeadlessResource>;
	namespace: string;
}

const AddFDSView = ({
	fdsViewsURL,
	headlessResources: initialHeadlessResources,
	namespace,
}: IAddFDSViewProps) => {
	const [selectedHeadlessResource, setSelectedHeadlessResource] = useState<
		HeadlessResource
	>();

	const Dropdown = () => (
		<ClayDropDown
			menuElementAttrs={{className: 'headless-resources-dropdown-menu'}}
			trigger={
				<ClayButton
					aria-labelledby={`${namespace}fdsHeadlessResourcesLabel`}
					className="form-control form-control-select form-control-select-secondary"
					displayType="secondary"
					id={`${namespace}fdsHeadlessResourcesSelect`}
				>
					{selectedHeadlessResource ? (
						<HeadlessResourceItem
							headlessResource={selectedHeadlessResource}
							query=""
						/>
					) : (
						Liferay.Language.get('choose-an-option')
					)}
				</ClayButton>
			}
		>
			<DropdownMenu
				initialHeadlessResources={initialHeadlessResources}
				setSelectedHeadlessResource={setSelectedHeadlessResource}
			/>
		</ClayDropDown>
	);

	return (
		<ClayLayout.ContainerFluid formSize="lg">
			<ClayForm>
				<ClayLayout.Sheet className="pt-4" size="lg">
					<ClayLayout.SheetHeader>
						<h2 className="sheet-title">
							{Liferay.Language.get('new-dataset')}
						</h2>
					</ClayLayout.SheetHeader>

					<ClayLayout.SheetSection>
						<ClayForm.Group>
							<label htmlFor={`${namespace}fdsViewNameInput`}>
								{Liferay.Language.get('name')}

								<RequiredMark />
							</label>

							<ClayInput
								id={`${namespace}fdsViewNameInput`}
								type="text"
							/>
						</ClayForm.Group>

						<ClayForm.Group>
							<label
								htmlFor={`${namespace}fdsHeadlessResourcesSelect`}
								id={`${namespace}fdsHeadlessResourcesLabel`}
							>
								{Liferay.Language.get('provider')}

								<RequiredMark />
							</label>

							<Dropdown />
						</ClayForm.Group>
					</ClayLayout.SheetSection>

					<ClayLayout.SheetFooter>
						<ClayButton.Group spaced>
							<ClayButton>
								{Liferay.Language.get('save')}
							</ClayButton>

							<ClayButton
								displayType="secondary"
								onClick={() => {
									navigate(fdsViewsURL);
								}}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>
						</ClayButton.Group>
					</ClayLayout.SheetFooter>
				</ClayLayout.Sheet>
			</ClayForm>
		</ClayLayout.ContainerFluid>
	);
};

export default AddFDSView;
